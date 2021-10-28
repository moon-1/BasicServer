package KU.GraduationProject.BasicServer.service.dataProcessing;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.dto.AIProcessingDto.AIProcessingRequestDto;
import KU.GraduationProject.BasicServer.dto.AIProcessingDto.contourLengthDto;
import KU.GraduationProject.BasicServer.dto.projectDto.wallPlotLengthDto;
import KU.GraduationProject.BasicServer.dto.response.defaultResult;
import KU.GraduationProject.BasicServer.dto.response.responseMessage;
import KU.GraduationProject.BasicServer.dto.response.statusCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class requestAIProcessingDataService {

    @Value("${file.upload-dir}")
    private String directoryPath;

    @Value("${AIProcessingServer.Url}")
    private String AIServerUrl;

    private final uploadImageFileInfoRepository imageFileRepository;
    
    public void requestWallPlotLength(long imageFileId) throws JsonProcessingException {

        imageFile imageFile = imageFileRepository.findByImageFileId(imageFileId).get();
        ObjectMapper mapper = new ObjectMapper();
        AIProcessingRequestDto request = new AIProcessingRequestDto(imageFile.getFileDownloadUri(),imageFileId);

        String requestJson = mapper.writeValueAsString(request);

        var response = WebClient.create()
                .post()
                .uri(AIServerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestJson))
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);

        //return processingDataHandler(response);

    }


    
    private contourLengthDto processingDataHandler(String processingData){

        Gson gson = new Gson();
        contourLengthDto contourLength = new contourLengthDto();

        Object object = gson.fromJson(processingData, Object.class);
        LinkedTreeMap<?,?> processingDataJson = (LinkedTreeMap<?, ?>) object;

        contourLength.setImageFileId(((Long) processingDataJson.get("id")));

        LinkedTreeMap<?,?> length = (LinkedTreeMap<?,?>)processingDataJson.get("length");
        contourLength.setHorizontal(((Long) length.get("horizontal")));
        contourLength.setHorizontal(((Long) length.get("vertical")));

        return contourLength;
    }
    


}
