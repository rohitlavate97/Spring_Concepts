package com.farmcollector.service;

import com.farmcollector.entity.CropData;
import com.farmcollector.repository.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CropService implements ICropService {

    private final CropRepository cropRepository;

    public List<CropData> saveAllCrops(List<CropData> crops) {
        return cropRepository.saveAll(crops);
    }
}
