package com.farmcollector.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "crops")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CropData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_id", nullable = false)
    @JsonBackReference
    private Farm farm;

    @Column(name = "crop_type", nullable = false)
    @NotBlank(message = "Crop type cannot be empty")
    private String cropType;

    @Column(name = "planting_area", nullable = false)
    @Min(value = 0, message = "Planting area must be greater than or equal to 0")
    private double plantingArea;

    @Column(name = "expected_product", nullable = false)
    @Min(value = 0, message = "Expected product must be greater than or equal to 0")
    private double expectedProduct;

    @Column(name = "actual_harvest")
    @Min(value = 0, message = "Actual harvest must be greater than or equal to 0")
    private double actualHarvest;

}
