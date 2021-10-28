package KU.GraduationProject.BasicServer.controller;

import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import KU.GraduationProject.BasicServer.service.project.imageFileHandlingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api("Image Server API V1")
@RestController
@RequiredArgsConstructor
public class imageFileController {

    private static final Logger logger = LoggerFactory.getLogger(imageFileController.class);

    private final imageFileHandlingService imageFileHandlingService;

    @ApiOperation(value = "이미지 업로드", notes = "도면 이미지 업로드 ")
    @PostMapping("/post/uploadFile")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        //return imageFileHandlingService.storeFile(file);
        return imageFileHandlingService.uploadFileToS3(file);
    }

    @GetMapping("/image/list")
    public ResponseEntity<Object> getImageFileList(){
        return imageFileHandlingService.getImageFileList();
    }

//    @PostMapping("/post/uploadMultipleFiles")
//    public List<Object> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }

    @ApiOperation(value = "도면 이미지 데이터 받기", notes= "id를 통해 이미지 다운로드 URL 얻기")
    @GetMapping("/post/{id}/files")
    public ResponseEntity<Object> downloadFilesInfo(@PathVariable Long id){
        return imageFileHandlingService.downloadFiles(id);
    }

    @ApiOperation(value = "도면 이미지 다운로드", notes= "URL을 통해 이미지 다운로드")
    @GetMapping("/post/downloadFile/{fileName:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = imageFileHandlingService.loadFileAsResource(fileName);
        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(defaultResult.res(statusCode.NO_CONTENT,
                    responseMessage.IMAGE_NOT_FOUND,
                    e.getMessage()), HttpStatus.OK);

        }
        if(contentType == null){
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}

