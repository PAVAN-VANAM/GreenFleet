package com.ps.greenfleet.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyVehicleDTO {
    private Long id;
    private String vehicleType;
    private Integer capacity;
    private Integer age;
    private Boolean pucCertificate;
    private Double co2PerKm;
    private Integer starRating;
}
