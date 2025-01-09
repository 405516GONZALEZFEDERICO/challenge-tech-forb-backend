package ar.edu.utn.frc.tup.lc.iv.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPlantDto {
    private String name;
    private Integer readings;
    private Integer medAlerts;
    private Integer redAlerts;
    private Integer sensorsDisabled;
}
