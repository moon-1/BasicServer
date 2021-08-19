package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.project;
import KU.GraduationProject.BasicServer.domain.entity.user;
import KU.GraduationProject.BasicServer.domain.repository.projectRepositoryImpl;
import KU.GraduationProject.BasicServer.domain.repository.userRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class projectService {

    private static final Logger log = LoggerFactory.getLogger(projectService.class);

    private final projectRepositoryImpl projectRepository;

    private final userRepositoryImpl userRepository;

    public List<project> findAll(){
        List<project> projectList = new ArrayList<>();
        try{
            projectList = projectRepository.findAll();
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return projectList;
    }

    public Optional<project> findById(Long id){
        Optional<project> project = Optional.empty();
        try{
            project = projectRepository.findById(id);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return project;
    }

    public Long save(project project){
        project.setDate(new Date(System.currentTimeMillis()));
        projectRepository.save(project);
        return project.getProjectId();
    }

    public void editById(Long projectId, project update){
        checkIsProjectExist(projectId);
        try{
            Optional<project> project = projectRepository.findById(projectId);
            project.get().setName(update.getName());
            project.get().setFloorPlan(update.getFloorPlan());
            project.get().setDate(new Date(System.currentTimeMillis()));
            projectRepository.save(project.get());
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
    }

    private void checkIsProjectExist(Long projectId){
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalStateException("존재하지 않는 프로젝트 입니다");
        }
    }

    public List<project> findByUserId(Long userId){
        List<project> projectListByUser = new ArrayList<>();
        try{
//            List<project> projectList = projectRepository.findAll();
//            Optional<user> user = userRepository.findById(userId);
//            for(project project : projectList){
//                if(project.getUser().equals(user.get())){
//                    projectListByUser.add(project);
//                }
//            }
            projectListByUser = projectRepository.findByUser_userId(userId);
        }
        catch(Exception ex){
            log.error(ex.getMessage());
        }
        return projectListByUser;
    }
}
