package KU.GraduationProject.BasicServer.dto.modelDto;

import KU.GraduationProject.BasicServer.dto.createdProjectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class isModelExistProjectDto {

    @JsonProperty("isModelExist")
    private boolean isModelExist;

    @JsonProperty("model")
    private createdProjectDto createdProjectDto;

    public isModelExistProjectDto(boolean isModelExist){
        this.isModelExist = this.isModelExist;
    }
}
