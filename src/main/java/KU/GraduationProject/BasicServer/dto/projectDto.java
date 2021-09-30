package KU.GraduationProject.BasicServer.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class projectDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String title;

    @NotNull
    private Long imageFileId;

}
