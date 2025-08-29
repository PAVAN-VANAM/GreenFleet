package com.ps.greenfleet.model;

import com.ps.greenfleet.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String vehicleId;

    @Column(nullable = false)
    private String registrationNumber;  // unique plate number

    @Column(nullable = false)
    private String make;

    private String model;

    private Integer year;

    @Enumerated(EnumType.STRING)
    private VehicleType type;  // EV_VAN, EV_TRUCK, etc.

    private Integer capacityKg;

    private Double co2PerKm;

    private Boolean isAvailable;

    // Tenant ownership
    private String tenantId; // nullable for independent vehicles

    // Assigned driver (nullable)
    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver assignedDriver;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
