package KU.GraduationProject.BasicServer.service.project;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.domain.repository.userRepository;
import KU.GraduationProject.BasicServer.dto.fileStorageProperties;
import KU.GraduationProject.BasicServer.dto.projectDto.imageListDto;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.dto.imageFileDto;
import KU.GraduationProject.BasicServer.util.securityUtil;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class imageFileHandlingService {

    private static final Logger log = LoggerFactory.getLogger(imageFileHandlingService.class);

    @Value("${file.upload-dir}")
    public String localDirectoryPath;

    @Value("${cloud.aws.s3.directoryPath}")
    public String directoryPath;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    private final Path fileStorageLocation;

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private uploadImageFileInfoRepository imageFileRepository;

    @Autowired
    public imageFileHandlingService(fileStorageProperties fileStorageProperties){

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch(Exception ex){
            log.error("디렉토리를 생성하지 못 하였습니다",ex.fillInStackTrace());
        }
    }

    public ResponseEntity<Object> uploadFileToS3(MultipartFile file) throws IOException{

        try{
            File uploadFile = convert(file)  // 파일 변환할 수 없으면 에러
                    .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
            String fileName = directoryPath + "/" + UUID.randomUUID() + StringUtils.cleanPath(file.getOriginalFilename());
            amazonS3Client.putObject(new PutObjectRequest(bucket,fileName,uploadFile).withCannedAcl(CannedAccessControlList.PublicReadWrite));
            String imageUrl = amazonS3Client.getUrl(bucket,fileName).toString();
            removeNewFile(uploadFile);

            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);

            imageFile imageFile = new imageFile();
            imageFile.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
            imageFile.setFileDownloadUri(imageUrl);
            imageFile.setFileType(file.getContentType());
            imageFile.setSize(file.getSize());
            imageFile.setUser(userInfo.get());

            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.UPLOAD_SUCCESS,
                    new imageFileDto(fileName,imageUrl,file.getContentType(),file.getSize(),imageFileRepository.save(imageFile).getImageFileId())
            ), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR, responseMessage.UPLOAD_FAIL,
                    e.getMessage()), HttpStatus.OK);
        }
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(localDirectoryPath + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public ResponseEntity<Object> getImageFileList(){
        try{
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);

            List<imageFile> imageFileList = imageFileRepository.findAllByUser_UserId(userInfo.get().getUserId());
            List<imageListDto> imageFiles = new ArrayList<>();
            for(imageFile imageFile : imageFileList){
                imageFiles.add(new imageListDto(imageFile.getImageFileId(),imageFile.getFileDownloadUri()));
            }
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.SHOW_IMAGE_LIST,imageFiles), HttpStatus.OK);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return new ResponseEntity(defaultResult.res(statusCode.INTERNAL_SERVER_ERROR, responseMessage.INTERNAL_SERVER_ERROR,
                    ex.getMessage()), HttpStatus.OK);
        }

    }

//    public ResponseEntity<Object> storeFile(MultipartFile file){
//
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//
//        try{
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(),targetLocation,StandardCopyOption.REPLACE_EXISTING);
//
//            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/post")
//                    .path("/downloadFile/")
//                    .path(fileName)
//                    .toUriString();
//
//            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);
//
//            imageFile imageFile = new imageFile();
//            imageFile.setFileName(fileName);
//            imageFile.setFileDownloadUri(fileDownloadUri);
//            imageFile.setFileType(file.getContentType());
//            imageFile.setSize(file.getSize());
//            imageFile.setUser(userInfo.get());
//
//            imageFileRepository.save(imageFile);
//
//            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.UPLOAD_SUCCESS,
//                    new imageFileDto(fileName,fileDownloadUri,file.getContentType(),file.getSize(),
//                            imageFileRepository.findByFileName(fileName).get().getImageFileId())
//            ), HttpStatus.OK);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.UPLOAD_FAIL,
//                    e.getMessage()), HttpStatus.OK);
//        }
//    }

    public ResponseEntity<Object> downloadFiles(Long id){
        try {
            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);

            imageFile imageFile = imageFileRepository.findByImageFileId(id).get();

            if(imageFile==null){
                return new ResponseEntity(defaultResult.res(statusCode.DB_ERROR, responseMessage.DB_ERROR, "Image file id :"+ id),
                        HttpStatus.OK);
            }
            if(imageFile.getUser() != userInfo.get()){
                return new ResponseEntity(defaultResult.res(statusCode.FORBIDDEN, responseMessage.FORBIDDEN_IMAGE, "Image file id :"+ id),
                        HttpStatus.OK);
            }

            imageFileDto imageFileDto =  new imageFileDto(
                    imageFile.getFileName(), imageFile.getFileDownloadUri(),
                    imageFile.getFileType(), imageFile.getSize(),
                    imageFileRepository.findByFileName(imageFile.getFileName()).get().getImageFileId());

            if(imageFileDto==null){
                return new ResponseEntity(defaultResult.res(statusCode.NOT_FOUND, responseMessage.IMAGE_NOT_FOUND),
                        HttpStatus.OK);
            }
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.DOWNLOAD_SUCCESS,
                    imageFileDto), HttpStatus.OK);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.INTERNAL_SERVER_ERROR,
                    e.getMessage()), HttpStatus.OK);
        }
    }

    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }
            else{
                throw new RuntimeException("존재하지 않는 파일입니다.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("존재하지 않는 파일입니다."+fileName,e);
        }
    }

}
