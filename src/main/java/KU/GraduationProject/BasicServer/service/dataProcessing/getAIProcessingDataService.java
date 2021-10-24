package KU.GraduationProject.BasicServer.service.dataProcessing;

import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.dto.AIProcessingDto.contourLengthDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class getAIProcessingDataService {

    @Value("${file.upload-dir}")
    private String directoryPath;

    //@Value
    private String AIServerUri = "";

    private final uploadImageFileInfoRepository imageFileRepository;
    
    public contourLengthDto getWallPlotLength(long imageFileId) throws JsonProcessingException {

        String fileName = imageFileRepository.findByImageFileId(imageFileId).get().getFileName();

        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("id", Long.toString(imageFileId));
        formData.add("url", directoryPath + "/" + fileName);

        String response = WebClient.create()
                .post()
                .uri(AIServerUri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        
        return processingDataHandler(response);

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
