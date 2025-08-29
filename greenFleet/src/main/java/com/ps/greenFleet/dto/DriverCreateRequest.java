package com.ps.greenfleet.dto;


import com.ps.greenfleet.enums.DriverStatus;
import com.ps.greenfleet.enums.UserRole;
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
public class DriverCreateRequest {
    private UUID tenantId; // nullable for independent


    @NotBlank
    @Email
    private String email;


    @NotBlank
    private String name;


    @NotBlank
    private String phone;


    @Size(min = 8)
    private String password; // handle hashing in service


    private String licenseNumber;
    private Instant licenseExpiry;


    private VehicleType vehicleType;
    @Min(0)
    private Integer vehicleAgeYears;


    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal capacityKg;


    private PucCertificate pucCertificate;


    @DecimalMin("0.0")
    private BigDecimal co2PerKm;


    @DecimalMin("0.0") @DecimalMax("5.0")
    private BigDecimal starRating;


    private Set<String> certifications;

    private DriverStatus status;

    private UserRole role;

}