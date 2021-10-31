package KU.GraduationProject.BasicServer.dto.modelDto;

import KU.GraduationProject.BasicServer.dto.projectDto.projectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class isModelExistDto {

    @JsonProperty("isModelExist")
    private boolean isModelExist;

    @JsonProperty("model")
    private projectDto projectDto;

    public isModelExistDto(boolean isModelExist){
        this.isModelExist = this.isModelExist;
    }
}
