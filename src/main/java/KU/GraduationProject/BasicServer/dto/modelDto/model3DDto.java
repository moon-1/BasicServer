package KU.GraduationProject.BasicServer.dto.modelDto;

import KU.GraduationProject.BasicServer.dto.projectDto.furnitureDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class model3DDto {

    private Long projectId;

    private List<furnitureDto> furnitures;
}
