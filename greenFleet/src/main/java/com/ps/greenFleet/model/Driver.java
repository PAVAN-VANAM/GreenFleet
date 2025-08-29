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
@Table(name = "drivers",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_drivers_email", columnNames = {"email"})
        },
        indexes = {
                @Index(name = "idx_drivers_tenant_status", columnList = "tenant_id,status"),
                @Index(name = "idx_drivers_tenant_assigned_vehicle", columnList = "tenant_id,assigned_vehicle_id"),
                @Index(name = "idx_drivers_star_rating", columnList = "star_rating")
        }
)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Driver {
    @Id
    @Column(name = "driver_id", nullable = false, updatable = false)
    private UUID driverId;

    // Nullable: independent driver has null tenant
    @Column(name = "tenant_id")
    private UUID tenantId;

    // Identity/Auth
    @Column(nullable = false)
    private String email;


    private String phone;


    @Column(name = "password_hash")
    private String passwordHash; // manage via PasswordEncoder

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


    private String profilePhotoUrl;


    private String licenseNumber;


    private Instant licenseExpiry;


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


    @ElementCollection
    @CollectionTable(name = "driver_certifications", joinColumns = @JoinColumn(name = "driver_id"))
    @Column(name = "certification")
    private Set<String> certifications;


    @Column(name = "assigned_vehicle_id")
    private UUID assignedVehicleId; // FK -> vehicles.vehicle_id (optional for now)


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}