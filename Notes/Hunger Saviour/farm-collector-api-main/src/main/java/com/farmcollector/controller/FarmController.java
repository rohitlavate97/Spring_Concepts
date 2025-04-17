package com.farmcollector.controller;

import com.farmcollector.entity.CropData;
import com.farmcollector.entity.Farm;
import com.farmcollector.service.FarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/farms")
@Tag(name = "Farm API", description = "APIs for managing farm and crop data")
public class FarmController {

    private final FarmService farmService;

    // Planted API: Collect planting data (Expected Product)
    @Operation(
            summary = "Add planting data for a farm",
            description = "This API endpoint collects planting data (expected product) for a farm.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Farm data created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "409", description = "Farm name already exists in the database")
            }
    )
    @PostMapping("/planted")
    public ResponseEntity<Farm> addPlantedData(
            @Parameter(description = "Farm data including farm name and expected product")
            @Valid @RequestBody Farm farm
    ) {
        return new ResponseEntity<>(farmService.savePlantedData(farm), HttpStatus.CREATED);
    }

    // Harvested API: Update actual harvested data
    @Operation(
            summary = "Update harvested data for a farm",
            description = "This API endpoint updates the actual harvested data for the crops on a farm.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Farm data updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Farm not found")
            }
    )
    @PutMapping("/harvested/{farmId}")
    public ResponseEntity<Farm> updateHarvestedData(
            @Parameter(description = "ID of the farm to update")
            @PathVariable Long farmId,
            @Parameter(description = "List of harvested crops data")
            @RequestBody List<CropData> harvestedCrops
    ) {
        Farm updatedFarm = farmService.updateHarvestedData(farmId, harvestedCrops);
        return ResponseEntity.ok(updatedFarm);
    }

    // Report API: Get report for farms based on season
    @Operation(
            summary = "Get farm report by season",
            description = "This API endpoint retrieves a report for farms based on the specified season.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
                    @ApiResponse(responseCode = "404", description = "No data found for the specified season")
            }
    )
    @GetMapping("/report/farm/{season}")
    public ResponseEntity<String> getFarmReportBySeason(
            @Parameter(description = "Season to generate the farm report for")
            @PathVariable String season
    ) {
        return ResponseEntity.ok(farmService.generateFarmReport(season));
    }

    // Report API: Get report for crops based on season
    @Operation(
            summary = "Get crop report by season",
            description = "This API endpoint retrieves a report for crops based on the specified season.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
                    @ApiResponse(responseCode = "404", description = "No data found for the specified season")
            }
    )
    @GetMapping("/report/crop/{season}")
    public ResponseEntity<String> getCropReportBySeason(
            @Parameter(description = "Season to generate the crop report for")
            @PathVariable String season
    ) {
        return ResponseEntity.ok(farmService.generateCropReport(season));
    }
}
