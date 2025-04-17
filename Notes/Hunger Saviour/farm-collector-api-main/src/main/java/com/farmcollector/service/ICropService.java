package com.farmcollector.service;

import com.farmcollector.entity.CropData;

import java.util.List;

public interface ICropService {
    List<CropData> saveAllCrops(List<CropData> crops);
}
