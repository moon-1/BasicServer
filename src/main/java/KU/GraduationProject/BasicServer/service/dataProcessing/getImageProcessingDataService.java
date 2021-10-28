package KU.GraduationProject.BasicServer.service.dataProcessing;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.pointType;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.contourDto;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.pointDto;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.wallDto;
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

import java.util.*;

@Service
@RequiredArgsConstructor
public class getImageProcessingDataService {

    @Value("${file.upload-dir}")
    private String directoryPath;

    @Value("${imageProcessingServer.Url}")
    private String imageProcessingServerUrl;

    private final saveImageProcessingDataService saveImageProcessingDataService;

    private final uploadImageFileInfoRepository imageFileRepository;

    public void getCoordinate(long imageFileId) throws JsonProcessingException {

        imageFile imageFile = imageFileRepository.findByImageFileId(imageFileId).get();

        MultiValueMap<String,String> formData = new LinkedMultiValueMap<>();
        formData.add("url", imageFile.getFileDownloadUri());
        formData.add("id", Long.toString(imageFileId));

        var response = WebClient.create()
                .post()
                .uri(imageProcessingServerUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        saveImageProcessingDataService.saveContourToDB(processingDataHandler(response),imageFile);

    }

    private List<contourDto> processingDataHandler(String processingData){

        Gson gson = new Gson();
        Object object = gson.fromJson(processingData, Object.class);
        LinkedTreeMap<?,?> processingDataJson = (LinkedTreeMap<?, ?>) object;
        List<contourDto> contourList = new ArrayList<>();
        var imageFileId = processingDataJson.get("id");
        LinkedTreeMap<?,?> points = (LinkedTreeMap<?,?>)processingDataJson.get("points");
        for(int i = 0 ; i < points.size() ; i++){
            contourDto contour = new contourDto();
            contour.setWall(makeWallList((ArrayList<?>)(points.get(Integer.toString(i)))));
            contourList.add(contour);
        }
        return contourList;
    }

    private ArrayList<wallDto> makeWallList(ArrayList<?> walls)
    {
        ArrayList<wallDto> wallList = new ArrayList<>();
        for(int i = 0 ; i < walls.size() ; i++){
            wallDto wallData = new wallDto();
            wallData.startPoint = new pointDto(((double)((ArrayList<?>)walls.get(i)).get(0)),
                    (double)((ArrayList<?>)walls.get(i)).get(1),pointType.start);

            if(i == walls.size()-1){
                wallData.endPoint = new pointDto(((double)((ArrayList<?>)walls.get(0)).get(0)),
                        (double)((ArrayList<?>)walls.get(0)).get(1),pointType.end);
            }else{
                wallData.endPoint = new pointDto(((double)((ArrayList<?>)walls.get(i+1)).get(0)),
                        (double)((ArrayList<?>)walls.get(i+1)).get(1),pointType.end);
            }

            wallList.add(wallData);
        }
        return wallList;
    }

}
