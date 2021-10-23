package KU.GraduationProject.BasicServer.dto.projectDto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class projectListDto {

    @NotNull
    private Long projectId;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private Date date;

    @NotNull
    private String imageFileUri;

    @NotNull
    private Long imageFileId;



}
