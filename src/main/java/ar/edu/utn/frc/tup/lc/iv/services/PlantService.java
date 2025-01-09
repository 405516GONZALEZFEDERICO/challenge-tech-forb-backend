package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.GetPlantDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.NewPlantDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PlantDto;

import java.util.List;

public interface PlantService {
    List<GetPlantDto> getPlants();
    PlantDto logicDown(Long id);
    PlantDto postPlant(NewPlantDto newPlantDto);
    PlantDto updatePlant(GetPlantDto getPlantDto);

}
