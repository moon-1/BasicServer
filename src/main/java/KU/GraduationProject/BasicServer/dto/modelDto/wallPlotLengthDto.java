package KU.GraduationProject.BasicServer.dto.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class wallPlotLengthDto {

    private int imageFileId;

    private int horizontal;

    private int vertical;

}
