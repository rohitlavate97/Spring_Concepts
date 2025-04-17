package com.farmcollector.repository;

import com.farmcollector.entity.CropData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropRepository extends JpaRepository<CropData, Long> {
    List<CropData> findByFarm_SeasonIgnoreCase(String season);
}
