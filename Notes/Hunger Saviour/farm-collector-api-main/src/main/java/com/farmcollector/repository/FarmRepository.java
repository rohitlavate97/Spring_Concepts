package com.farmcollector.repository;

import com.farmcollector.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findBySeasonIgnoreCase(String season);
}
