package KU.GraduationProject.BasicServer.dto.imageProcessingData;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class contourDto {

    private ArrayList<wallDto> wall;

}