package KU.GraduationProject.BasicServer.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class createdProjectDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    private Date createdTime;

    private Object model;

    private Long projectId;

    public createdProjectDto(String name,Date createdTime,Object model){
        this.name = name;
        this.createdTime = createdTime;
        this.model = model;
    }

}
