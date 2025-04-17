package com.farmcollector.controller;

import com.farmcollector.entity.CropData;
import com.farmcollector.entity.Farm;
import com.farmcollector.service.FarmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FarmControllerTest {

    @Mock
    private FarmService farmService;

    @InjectMocks
    private FarmController farmController;

    private Farm farm;
    private List<CropData> cropList;

    @BeforeEach
    void setUp() {
        cropList = new ArrayList<>();
        cropList.add(new CropData(null, null, "Wheat", 100.0, 200.0, 180.0));
        farm = Farm.builder()
                .id(1L)
                .farmName("Sunrise Farms")
                .season("Fall")
                .crops(cropList)
                .build();
    }

    @Test
    void addPlantedData() {
        when(farmService.savePlantedData(farm)).thenReturn(farm);
        ResponseEntity<Farm> response = farmController.addPlantedData(farm);
        assertEquals(201, response.getStatusCodeValue());
        verify(farmService, times(1)).savePlantedData(farm);
    }

    @Test
    void updateHarvestedData() {
        when(farmService.updateHarvestedData(1L, cropList)).thenReturn(farm);
        ResponseEntity<Farm> response = farmController.updateHarvestedData(1L, cropList);
        assertEquals(200, response.getStatusCodeValue());
        verify(farmService, times(1)).updateHarvestedData(1L, cropList);
    }

    @Test
    void getFarmReportBySeason() {
        String expectedReport = "Sample report";
        when(farmService.generateFarmReport("Fall")).thenReturn(expectedReport);
        ResponseEntity<String> response = farmController.getFarmReportBySeason("Fall");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedReport, response.getBody());
        verify(farmService, times(1)).generateFarmReport("Fall");
    }

    @Test
    void getCropReportBySeason() {
        String expectedReport = "Sample crop report";
        when(farmService.generateCropReport("Fall")).thenReturn(expectedReport);
        ResponseEntity<String> response = farmController.getCropReportBySeason("Fall");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedReport, response.getBody());
        verify(farmService, times(1)).generateCropReport("Fall");
    }
}
