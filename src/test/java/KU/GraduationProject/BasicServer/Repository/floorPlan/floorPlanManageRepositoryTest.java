package KU.GraduationProject.BasicServer.Repository.floorPlan;

import KU.GraduationProject.BasicServer.Domain.FloorPlan.floorPlan;
import KU.GraduationProject.BasicServer.Repository.FloorPlan.floorPlanManageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class floorPlanManageRepositoryTest {

    floorPlanManageRepository floorPlanManageRepository = new floorPlanManageRepository();

    @AfterEach
    void afterEach(){
        floorPlanManageRepository.deleteAll();
    }

    @Test
    void save(){

        //given
        floorPlan floorPlan = new floorPlan(1L,"seoul","GuriGalmae","A-6","59A-0530") ;

        //when
        floorPlan savedFloorPlan = floorPlanManageRepository.save(floorPlan);

        //then
        floorPlan findFloorPlan = floorPlanManageRepository.findById(savedFloorPlan.getId());
        assertThat(findFloorPlan).isEqualTo(savedFloorPlan);
    }

    @Test
    void  findById(){

        //given
        floorPlan floorPlan = new floorPlan(1L,"seoul","GuriGalmae","A-6","59A-0530") ;

        floorPlanManageRepository.save(floorPlan);

        //when
        floorPlan searchFloorPlan = floorPlanManageRepository.findById(floorPlan.getId());

        //then
        List<floorPlan> floorPlans = floorPlanManageRepository.findAll();
        assertThat(floorPlans.contains(searchFloorPlan)).isTrue();
    }

    @Test
    void findAll() {

        //given
        floorPlan floorPlan1 = new floorPlan(1L,"seoul","GuriGalmae","A-6","59A-0530") ;
        floorPlan floorPlan2 = new floorPlan(2L,"seoul","GuriGalmae","B-6","5B-003") ;


        floorPlanManageRepository.save(floorPlan1);
        floorPlanManageRepository.save(floorPlan2);

        //when
        List<floorPlan> floorPlans = floorPlanManageRepository.findAll();

        //then
        assertThat(floorPlans.size()).isEqualTo(2);
        assertThat(floorPlans).contains(floorPlan1,floorPlan2);
    }

}
