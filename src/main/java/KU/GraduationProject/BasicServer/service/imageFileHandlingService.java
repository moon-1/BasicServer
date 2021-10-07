package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.wallPlot3D;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.domain.repository.userRepository;
import KU.GraduationProject.BasicServer.domain.repository.wallPlot3DRepository;
import KU.GraduationProject.BasicServer.dto.fileStorageProperties;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.dto.imageFileDto;
import KU.GraduationProject.BasicServer.util.securityUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class imageFileHandlingService {

    private static final Logger log = LoggerFactory.getLogger(imageFileHandlingService.class);

    private final Path fileStorageLocation;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private uploadImageFileInfoRepository imageFileRepository;

//    @Autowired
//    private wallPlot3DRepository wallPlot3DRepository;

    @Autowired
    public imageFileHandlingService(fileStorageProperties fileStorageProperties){

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch(Exception ex){
            log.error("디렉토리를 생성하지 못 하였습니다",ex.fillInStackTrace());
        }
    }

    public ResponseEntity<Object> storeFile(MultipartFile file){

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(),targetLocation,StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/post")
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            var userInfo = securityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmail);

            imageFile imageFile = new imageFile();
            imageFile.setFileName(fileName);
            imageFile.setFileDownloadUri(fileDownloadUri);
            imageFile.setFileType(file.getContentType());
            imageFile.setSize(file.getSize());
            imageFile.setUser(userInfo.get());

            imageFileRepository.save(imageFile);
            //saveWallPlot3D(imageFile);

            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.UPLOAD_SUCCESS,
                    new imageFileDto(fileName,fileDownloadUri,file.getContentType(),file.getSize(),
                            imageFileRepository.findByFileName(fileName).get().getImageFileId())
            ), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.UPLOAD_FAIL,
                    e.getMessage()), HttpStatus.OK);
        }
    }

//    private void saveWallPlot3D(imageFile imageFile){
//
//        wallPlot3D wallPlot3D = new wallPlot3D();
//        wallPlot3D.setImageFile(imageFile);
//
//        wallPlot3DRepository.save(wallPlot3D);
//
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
