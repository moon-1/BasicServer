package KU.GraduationProject.BasicServer.dto.accountDto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class checkPasswordDto {

    @NotNull
    @Size(min = 3, max = 100)
    private String password;
}