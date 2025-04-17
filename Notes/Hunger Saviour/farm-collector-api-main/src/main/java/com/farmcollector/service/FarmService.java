package com.farmcollector.service;

import com.farmcollector.entity.CropData;
import com.farmcollector.entity.Farm;
import com.farmcollector.exception.FarmException;
import com.farmcollector.repository.CropRepository;
import com.farmcollector.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FarmService implements IFarmService {

    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;

    // Method to save the Planted data
    public Farm savePlantedData(Farm farm) {
        // Ensure each CropData's farm field is set using streams (set farm reference)
        if (farm.getCrops() != null) {
            farm.getCrops()
                    .forEach(crop -> crop.setFarm(farm));  // Setting the farm reference in each crop
        }
        // Now save the farm (crops will be saved due to CascadeType.ALL)
        return farmRepository.save(farm);
    }

    // Method to update the Harvested data
    public Farm updateHarvestedData(Long farmId, List<CropData> harvestedCrops) {
        Farm farm = farmRepository.findById(farmId).orElseThrow(() -> new RuntimeException("Farm not found!"));

        for (CropData harvestedCrop : harvestedCrops) {
            farm.getCrops().stream()
                    .filter(crop -> crop.getCropType().equalsIgnoreCase(harvestedCrop.getCropType()))
                    .findFirst()
                    .ifPresent(crop -> crop.setActualHarvest(harvestedCrop.getActualHarvest()));
        }

        return farmRepository.save(farm);
    }

    // Fetch farms for a given season
    public List<Farm> findBySeason(String season) {
        List<Farm> farms = farmRepository.findBySeasonIgnoreCase(season);
        if (farms.isEmpty()) {
            throw new FarmException("No farms found for the season: " + season, HttpStatus.NOT_FOUND);
        }
        return farms;
    }

    // Fetch crops for a given season
    public List<CropData> findByCropSeason(String season) {
        List<CropData> crops = cropRepository.findByFarm_SeasonIgnoreCase(season);
        if (crops.isEmpty()) {
            throw new FarmException("No crops found for the season: " + season, HttpStatus.NOT_FOUND);
        }
        return crops;
    }

    // Fetch report for each farm in a given season (Expected vs Actual)
    public String generateFarmReport(String season) {
        List<Farm> farms = findBySeason(season);
        StringBuilder report = new StringBuilder();

        for (Farm farm : farms) {
            report.append("Farm: ").append(farm.getFarmName()).append("\n");
            for (CropData crop : farm.getCrops()) {
                report.append("Crop: ").append(crop.getCropType())
                        .append(", Expected: ").append(crop.getExpectedProduct())
                        .append(", Actual: ").append(crop.getActualHarvest()).append("\n");
            }
        }
        return report.toString();
    }

    // Fetch report for each crop type in a given season (Expected vs Actual)
    public String generateCropReport(String season) {
        List<CropData> crops = findByCropSeason(season);
        StringBuilder report = new StringBuilder();

        // Group crops by type
        crops.stream()
                .collect(Collectors.groupingBy(CropData::getCropType))
                .forEach((cropType, cropList) -> {
                    report.append("Crop Type: ").append(cropType).append("\n");
                    cropList.forEach(crop -> {
                        report.append("Farm: ").append(crop.getFarm().getFarmName())
                                .append(", Expected: ").append(crop.getExpectedProduct())
                                .append(", Actual: ").append(crop.getActualHarvest()).append("\n");
                    });
                });

        return report.toString();
    }
}
