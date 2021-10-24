package KU.GraduationProject.BasicServer.service.dataProcessing;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.contour;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.point;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wall;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.*;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.contourDto;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.pointDto;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.wallDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class saveImageProcessingDataService {

    private final contourRepository contourRepository;

    private final wallRepository wallRepository;

    private final pointRepository pointRepository;

    public void saveContourToDB(List<contourDto> contourList, imageFile imageFile){

        for(contourDto contour : contourList){
            contour saveContour = new contour(imageFile);
            contourRepository.save(saveContour);

            for(wallDto wallDto : contour.getWall()){
                wall wall = wallRepository.save(new wall(saveContour));
                savePointToDB(wallDto.startPoint, wall);
                savePointToDB(wallDto.endPoint, wall);
            }
        }
    }

    private void savePointToDB(pointDto pointDto,wall wall){
        pointRepository.save(new point(pointDto.getX(),pointDto.getY(),pointDto.getPointType(),wall));
    }
}
