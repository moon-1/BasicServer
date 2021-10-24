package KU.GraduationProject.BasicServer.dto.projectDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class imageListDto {

    @NotNull
    private Long imageFileId;

    @NotNull
    private String url;

}
