package com.ps.greenfleet.dto;


import com.ps.greenfleet.enums.DriverStatus;
import com.ps.greenfleet.enums.VehicleType;
import com.ps.greenfleet.model.PucCertificate;
import jakarta.validation.constraints.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverUpdateRequest {
    private UUID tenantId; // can move between fleet and independent (null)


    @Email
    private String email;


    private String name;
    private String phone;
    private String password; // re-hash if provided


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
    private DriverStatus status;
}