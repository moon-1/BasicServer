package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.dto.fileStorageProperties;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.dto.imageFileDto;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class imageFileStorageService {

    private static final Logger log = LoggerFactory.getLogger(imageFileStorageService.class);

    private final Path fileStorageLocation;

    @Autowired
    private uploadImageFileInfoRepository imageFileRepository;

    @Autowired
    public imageFileStorageService(fileStorageProperties fileStorageProperties){

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
            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/post")
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            imageFile imageFile = new imageFile();
            imageFile.setFileName(fileName);
            imageFile.setFileDownloadUri(fileDownloadUri);
            imageFile.setFileType(file.getContentType());
            imageFile.setSize(file.getSize());

            imageFileRepository.save(imageFile);

            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.UPLOAD_SUCCESS,
                    new imageFileDto(fileName,fileDownloadUri,file.getContentType(),file.getSize())
            ), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.UPLOAD_FAIL,
                    e.getMessage()), HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> downloadFiles(Long id){
        try {
            List<imageFile> imageFiles = imageFileRepository.findAllByImageFileId(id);

            if(imageFiles==null){
                return new ResponseEntity(defaultResult.res(statusCode.DB_ERROR, responseMessage.DB_ERROR),
                        HttpStatus.OK);
            }
            List<imageFileDto> files = imageFiles.stream().map(fileInfo -> new imageFileDto(
                    fileInfo.getFileName(), fileInfo.getFileDownloadUri(),
                    fileInfo.getFileType(), fileInfo.getSize())
            ).collect(Collectors.toList());
            if(files==null){
                return new ResponseEntity(defaultResult.res(statusCode.NOT_FOUND, responseMessage.IMAGE_NOT_FOUND),
                        HttpStatus.OK);
            }
            return new ResponseEntity(defaultResult.res(statusCode.OK, responseMessage.DOWNLOAD_SUCCESS,
                    files), HttpStatus.OK);
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
