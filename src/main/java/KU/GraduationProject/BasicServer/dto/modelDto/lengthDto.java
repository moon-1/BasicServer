package KU.GraduationProject.BasicServer.dto.modelDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class lengthDto {

    private double horizontal;

    private double vertical;

    public lengthDto(double horizontal, double vertical){
        this.horizontal = horizontal;
        this.vertical = vertical;
    }
}
