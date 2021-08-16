package KU.GraduationProject.BasicServer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "wall")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class wall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wallId;
}
