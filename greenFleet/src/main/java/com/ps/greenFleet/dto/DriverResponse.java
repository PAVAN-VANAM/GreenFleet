package com.ps.greenfleet.dto;

import com.ps.greenfleet.enums.DriverStatus;
import com.ps.greenfleet.enums.UserRole;
import com.ps.greenfleet.enums.VehicleType;
import com.ps.greenfleet.model.PucCertificate;
import lombok.*;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverResponse {
    private UUID driverId;
    private UUID tenantId;
    private String email;
    private String phone;
    private String name;
    private String profilePhotoUrl;
    private UserRole role;
    private DriverStatus status;


    private String licenseNumber;
    private Instant licenseExpiry;


    private VehicleType vehicleType;
    private Integer vehicleAgeYears;
    private BigDecimal capacityKg;
    private PucCertificate pucCertificate;
    private BigDecimal co2PerKm;
    private BigDecimal starRating;
    private Set<String> certifications;
    private UUID assignedVehicleId;


    private Instant createdAt;
    private Instant updatedAt;
}