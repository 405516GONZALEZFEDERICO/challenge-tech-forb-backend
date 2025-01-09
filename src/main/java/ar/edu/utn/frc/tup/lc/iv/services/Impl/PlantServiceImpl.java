package ar.edu.utn.frc.tup.lc.iv.services.Impl;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.GetPlantDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.NewPlantDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PlantDto;
import ar.edu.utn.frc.tup.lc.iv.entities.PlantEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.PlantRepository;
import ar.edu.utn.frc.tup.lc.iv.services.PlantService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private ModelMapper modelMapper;
    private  Random random = new Random();
    @Override
    public List<GetPlantDto> getPlants() {
    List<PlantEntity> plantEntities=this.plantRepository.findAll();
    if (plantEntities.isEmpty()){
        throw new EntityNotFoundException("Not exist plants");
    }else
        return modelMapper.map(plantEntities, new TypeToken<List<GetPlantDto>>(){}.getType());
    }

    @Override
    public PlantDto logicDown(Long id) {
        try {
            Optional<PlantEntity> plantEntityOptional = this.plantRepository.findById(id);
            if (plantEntityOptional.isPresent()) {
                PlantEntity plantEntity = plantEntityOptional.get();
                plantEntity.setStatus(false);
                PlantEntity updatedPlantEntity = this.plantRepository.save(plantEntity);
                return modelMapper.map(updatedPlantEntity, PlantDto.class);
            } else {
                throw new EntityNotFoundException("Plant with ID " + id + " not found");
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the plant status", e);
        }
    }

    @Override
    public PlantDto postPlant(NewPlantDto newPlantDto) {
        PlantEntity plantEntity=this.modelMapper.map(newPlantDto,PlantEntity.class);
        plantEntity.setStatus(true);
        plantEntity.setSensorsDisabled(generateSensorsDisabled());
        plantEntity.setMedAlerts(generateMediumAlert());
        plantEntity.setRedAlerts(generateRedAlert());
        plantEntity.setReadings(generateReading());
        this.plantRepository.save(plantEntity);
        return this.modelMapper.map(plantEntity,PlantDto.class);
    }

    @Override
    public PlantDto updatePlant(GetPlantDto getPlantDto) {
        return null;
    }

    private  int generateRandomNumber(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    private  int generateReading() {
        return generateRandomNumber(100, 500);
    }

    public  int generateMediumAlert() {
        return generateRandomNumber(1, 90);
    }

    public  int generateRedAlert() {
        return generateRandomNumber(1, 50);
    }

    public  int generateSensorsDisabled() {
        return generateRandomNumber(300,900);
    }


}
