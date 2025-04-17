package com.farmcollector.dto;

import java.util.List;

public record ErrorDTO(String code, List<String> errorMessages) {
}
