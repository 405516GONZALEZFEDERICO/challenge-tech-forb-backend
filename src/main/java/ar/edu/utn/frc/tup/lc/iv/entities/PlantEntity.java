package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "plants")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    public String name;
    @Column
    public String country;
    @Column
    public Integer readings;
    @Column
    public Integer medAlerts;
    @Column
    public Integer redAlerts;
    @Column
    public Integer sensorsDisabled;
    @Column
    public boolean status;

}
