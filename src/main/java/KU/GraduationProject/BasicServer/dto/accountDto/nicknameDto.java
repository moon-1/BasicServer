package KU.GraduationProject.BasicServer.dto.accountDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class nicknameDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String nickname;

    @NotNull
    private Long userId;


}
