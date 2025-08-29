package com.ps.greenfleet.dto;

import com.ps.greenfleet.enums.VehicleType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateRequest {
    private String registrationNumber;
    private String make;
    private String model;
    private Integer year;
    private VehicleType type;
    private Integer capacityKg;
    private Double co2PerKm;
    private String tenantId; // optional
}
