package KU.GraduationProject.BasicServer.dto.AIProcessingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AIProcessingRequestDto {

    private String imageUrl;

    private Long imageFileId;
}
