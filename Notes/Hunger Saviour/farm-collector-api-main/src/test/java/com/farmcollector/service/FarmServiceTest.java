package com.farmcollector.service;

import com.farmcollector.entity.CropData;
import com.farmcollector.entity.Farm;
import com.farmcollector.exception.FarmException;
import com.farmcollector.repository.CropRepository;
import com.farmcollector.repository.FarmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FarmServiceTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private CropRepository cropRepository;

    @InjectMocks
    private FarmService farmService;

    private Farm farm;
    private CropData cropData;

    @BeforeEach
    void setUp() {
        cropData = new CropData(1L, new Farm(), "Wheat", 100.0, 200.0, 0.0);
        farm = new Farm(1L, "Sunshine Farms", "Fall", Collections.singletonList(cropData));
        cropData.setFarm(farm);
    }

    @Test
    void savePlantedDataTest() {
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);
        Farm savedFarm = farmService.savePlantedData(farm);
        assertNotNull(savedFarm);
        assertEquals("Sunshine Farms", savedFarm.getFarmName());
        verify(farmRepository, times(1)).save(farm);
    }

    @Test
    void updateHarvestedDataTest() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));

        // Mock the save method to return the modified farm object.
        when(farmRepository.save(any(Farm.class))).thenReturn(farm);

        CropData updatedCropData = new CropData(null, null, "Wheat", 100.0, 200.0, 180.0);
        Farm updatedFarm = farmService.updateHarvestedData(farm.getId(), Collections.singletonList(updatedCropData));

        assertNotNull(updatedFarm);
        assertTrue(updatedFarm.getCrops().stream()
                .anyMatch(c -> "Wheat".equals(c.getCropType()) && c.getActualHarvest() == 180.0));
    }

    @Test
    void updateHarvestedDataNotFoundTest() {
        when(farmRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> farmService.updateHarvestedData(1L, Collections.singletonList(cropData)));
        assertEquals("Farm not found!", exception.getMessage());
    }

    @Test
    void findBySeasonTest() {
        when(farmRepository.findBySeasonIgnoreCase(any(String.class))).thenReturn(Collections.singletonList(farm));
        List<Farm> foundFarms = farmService.findBySeason("Fall");
        assertFalse(foundFarms.isEmpty());
        assertEquals("Sunshine Farms", foundFarms.get(0).getFarmName());
    }

    @Test
    void findBySeasonNotFoundTest() {
        when(farmRepository.findBySeasonIgnoreCase("Winter")).thenReturn(Collections.emptyList());
        FarmException exception = assertThrows(FarmException.class, () -> farmService.findBySeason("Winter"));
        assertEquals("No farms found for the season: Winter", exception.getMessage());
    }

    @Test
    void findByCropSeasonTest() {
        when(cropRepository.findByFarm_SeasonIgnoreCase(any(String.class))).thenReturn(Collections.singletonList(cropData));
        List<CropData> foundCrops = farmService.findByCropSeason("Fall");
        assertFalse(foundCrops.isEmpty());
        assertEquals("Wheat", foundCrops.get(0).getCropType());
    }

    @Test
    void findByCropSeasonNotFoundTest() {
        when(cropRepository.findByFarm_SeasonIgnoreCase("Winter")).thenReturn(Collections.emptyList());
        FarmException exception = assertThrows(FarmException.class, () -> farmService.findByCropSeason("Winter"));
        assertEquals("No crops found for the season: Winter", exception.getMessage());
    }
}
