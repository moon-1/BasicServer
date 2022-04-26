package KU.GraduationProject.BasicServer.domain.entity.account;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
public class neighbor {

    @JsonIgnore
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long neighborId;

    @Column(length = 50)
    private String nickname;

    @Column
    private boolean isApprove;

    @ManyToOne(targetEntity = user.class)
    @JoinColumn(name = "userId")
    private user user;

    public neighbor(String nickname,boolean isApprove, user user){
        this.nickname = nickname;
        this.isApprove = isApprove;
        this.user = user;
    }

}
