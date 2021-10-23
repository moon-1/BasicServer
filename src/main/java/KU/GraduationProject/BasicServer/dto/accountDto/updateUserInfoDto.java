package KU.GraduationProject.BasicServer.dto.accountDto;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class updateUserInfoDto {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 1, max = 100)
    private String currentPassword;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 1, max = 100)
    private String newPassword;

    @NotNull
    @Size(min = 1, max = 50)
    private String nickname;

    @NotNull
    private Date birth;
}
