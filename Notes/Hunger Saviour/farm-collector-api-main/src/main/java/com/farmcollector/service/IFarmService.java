package com.farmcollector.service;

import com.farmcollector.entity.CropData;
import com.farmcollector.entity.Farm;

import java.util.List;

public interface IFarmService {
    Farm savePlantedData(Farm farm);
    Farm updateHarvestedData(Long farmId, List<CropData> harvestedCrops);
    String generateFarmReport(String season);
    String generateCropReport(String season);
}
