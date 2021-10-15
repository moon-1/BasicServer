package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.dto.imageProcessingData.contourDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class getAIProcessingDataService {

    public String getWallPlotLength(List<contourDto> contourList,Long wallPlot3DId) throws JsonProcessingException {

        String AIServerUri = "";

        ObjectMapper mapper = new ObjectMapper();
        String coordinateData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contourList);

        System.out.print(coordinateData);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("coordinateData",coordinateData);
        formData.add("3DModelId",Integer.toString(wallPlot3DId.intValue()));

        var response = WebClient.create()
                .post()
                .uri( AIServerUri )
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).
                body(BodyInserters.fromValue(formData))
                .exchange()
                .block();


        return response.toString();
    }


}
