package KU.GraduationProject.BasicServer.dto.projectDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class wallPlotLengthDto {

    private Long imageFileId;

    private double horizontal;

    private double vertical;

}
