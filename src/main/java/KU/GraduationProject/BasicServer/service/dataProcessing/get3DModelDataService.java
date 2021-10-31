package KU.GraduationProject.BasicServer.service.dataProcessing;

import KU.GraduationProject.BasicServer.domain.entity.floorPlans.contour;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.point;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.points.pointType;
import KU.GraduationProject.BasicServer.domain.entity.floorPlans.walls.wall;
import KU.GraduationProject.BasicServer.domain.entity.furnitures.furniture;
import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.*;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.pointDto;
import KU.GraduationProject.BasicServer.dto.imageProcessingDto.wallDto;
import KU.GraduationProject.BasicServer.dto.projectDto.furnitureDto;
import KU.GraduationProject.BasicServer.dto.modelDto.lengthDto;
import KU.GraduationProject.BasicServer.dto.projectDto.projectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class get3DModelDataService {

    @Autowired
    private contourRepository contourRepository;

    @Autowired
    private wallRepository wallRepository;

    @Autowired
    private pointRepository pointRepository;

    @Autowired
    private furnitureRepository furnitureRepository;

    @Autowired
    private uploadImageFileInfoRepository imageFileRepository;

    public projectDto get3DModel(Long imageFileId){
        projectDto projectDto = new projectDto();
        imageFile imageFile = imageFileRepository.getById(imageFileId);
        projectDto.setLengthDto(new lengthDto(imageFile.getHorizontal(),imageFile.getVertical()));
        projectDto.setWall(makeWallDto(imageFileId));

        return projectDto;
    }

    public ArrayList<wallDto> makeWallDto(Long imageFileId){

        ArrayList<wallDto> wallDtoList = new ArrayList<>();
        List<contour> contourList = contourRepository.findAllByImageFile_ImageFileId(imageFileId);

        for(contour contour : contourList){
            List<wall> wallList = wallRepository.findAllByContour_ContourId(contour.getContourId());
            for(wall wall : wallList){
                wallDto wallDto = new wallDto();
                List<point> pointList = pointRepository.findAllByWall_WallId(wall.getWallId());
                wallDto.startPoint = new pointDto(pointList.get(0).getX(),pointList.get(0).getY(), pointType.start);
                wallDto.endPoint =  new pointDto(pointList.get(1).getX(),pointList.get(1).getY(), pointType.end);
                wallDtoList.add(wallDto);
            }
        }
        return wallDtoList;
    }

    public ArrayList<furnitureDto> makeFurnitureDto(Long projectId){

        ArrayList<furnitureDto> furnitureDtoList = new ArrayList<>();
        List<furniture> furnitureList = furnitureRepository.findAllByProject_ProjectId(projectId);
        for(furniture furniture : furnitureList){
            furnitureDto furnitureDto = new furnitureDto();
            furnitureDto.setName(furniture.getName());
            furnitureDto.setX(furniture.getX());
            furnitureDto.setY(furnitureDto.getY());
            furnitureDtoList.add(furnitureDto);
        }
        return furnitureDtoList;
    }
}
