package KU.GraduationProject.BasicServer.dto.projectDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class newProjectDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private Long imageFileId;

}
