package KU.GraduationProject.BasicServer.domain.entity.account;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class authority {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}
