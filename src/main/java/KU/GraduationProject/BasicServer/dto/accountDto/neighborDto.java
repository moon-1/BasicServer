package KU.GraduationProject.BasicServer.dto.accountDto;

import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class neighborDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String nickname;

    @NotNull
    private Long neighborId;

    @NotNull
    private Long userId;

    @NotNull
    private boolean isApprove;
}
