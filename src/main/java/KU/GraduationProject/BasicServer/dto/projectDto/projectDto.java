package KU.GraduationProject.BasicServer.dto.projectDto;

import KU.GraduationProject.BasicServer.dto.imageProcessingDto.wallDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class projectDto {

    @JsonProperty("length")
    private KU.GraduationProject.BasicServer.dto.modelDto.lengthDto lengthDto;

    @JsonProperty("wall")
    private ArrayList<wallDto> wall;

    @JsonProperty("furnitures")
    private List<furnitureDto> furnitures;
}
