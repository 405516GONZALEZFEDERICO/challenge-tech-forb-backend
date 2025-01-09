package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.GetPlantDto;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PlantDto;
import ar.edu.utn.frc.tup.lc.iv.services.PlantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plant")
@Tag(name = "Authentication", description = "Endpoints for plant")
public class PlantController {
    @Autowired
    private PlantService plantService;
    @PatchMapping("/logicDown/{plantId}")
    public ResponseEntity<Object> logicDownPlant(@PathVariable Long plantId) {
        try {
            PlantDto plantDto = this.plantService.logicDown(plantId);
            if (plantDto != null) {
                return ResponseEntity.ok(plantDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant with ID " + plantId + " not found.");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant with ID " + plantId + " does not exist.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
    @GetMapping("/getPlants")
    public ResponseEntity<List<GetPlantDto>> getPlants() {
        try {
            List<GetPlantDto>plantDtos = this.plantService.getPlants();
            if (plantDtos != null) {
                return ResponseEntity.ok(plantDtos);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
