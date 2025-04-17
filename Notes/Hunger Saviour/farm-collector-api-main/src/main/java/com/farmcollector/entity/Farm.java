package com.farmcollector.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "farms")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farm_name", nullable = false, unique = true)
    @NotBlank(message = "Farm name cannot be empty")
    private String farmName;

    @Column(name = "season", nullable = false)
    @NotBlank(message = "Season cannot be empty")
    private String season;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CropData> crops;

}
