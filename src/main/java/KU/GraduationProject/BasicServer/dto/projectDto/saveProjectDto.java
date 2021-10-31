package KU.GraduationProject.BasicServer.dto.projectDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class saveProjectDto {

    private long projectId;

    @JsonProperty("furnitures")
    private List<furnitureDto> furnitures;
}
