package com.ps.greenfleet.model;

import com.ps.greenfleet.enums.DriverStatus;
import com.ps.greenfleet.enums.UserRole;
import com.ps.greenfleet.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.UUID;



@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO increment in DB
    private Long id;

//    // Nullable: independent driver has null tenant
//    @Column(name = "tenant_id")
//    private String tenantId;

    // Identity/Auth
    @Column(nullable = false)
    private String email;
    private String phone;

    @Column(name = "password_hash")
    private String password; // manage via PasswordEncoder

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private UserRole role = UserRole.DRIVER;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DriverStatus status = DriverStatus.ACTIVE;


    // Profile
    @Column(nullable = false)
    private String name;

    //private String profilePhotoUrl;

    private String licenseNumber;

    private String licenseExpiry;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType; // snapshot for sustainability analytics

    @Column(name = "vehicle_age_years")
    private Integer vehicleAgeYears;

    @Column(name = "capacity_kg")
    private BigDecimal capacityKg;

    @Embedded
    private PucCertificate pucCertificate;

    @Column(name = "co2_per_km", precision = 10, scale = 4)
    private BigDecimal co2PerKm; // kg per km

    @Column(name = "star_rating", precision = 3, scale = 2)
    private BigDecimal starRating; // 0.00 - 5.00

//    @ElementCollection
//    @CollectionTable(name = "driver_certifications", joinColumns = @JoinColumn(name = "driver_id"))
//    @Column(name = "certification")
//    private Set<String> certifications;


//    @Column(name = "assigned_vehicle_id")
//    private String assignedVehicleId; // FK -> vehicles.vehicle_id (optional for now)
//

}