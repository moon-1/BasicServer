package KU.GraduationProject.BasicServer.dto.accountDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class userDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 1, max = 100)
    private String password;

    @NotNull
    @Size(min = 1, max = 50)
    private String nickname;

    @NotNull
    private Date birth;
}
