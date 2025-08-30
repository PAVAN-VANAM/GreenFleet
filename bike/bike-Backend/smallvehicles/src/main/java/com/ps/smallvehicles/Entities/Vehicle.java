package com.ps.smallvehicles.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment PK
    private Long id;

    // ✅ Many vehicles can belong to one user
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)  // FK in vehicles table
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Float mileage;  // km/l

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @Column(nullable = false)
    private Integer seats;

    @Column(nullable = false)
    private Float co2PerKm;  // kg CO2 per km

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum VehicleType {
        TWO_WHEELER, FOUR_WHEELER
    }

    public enum FuelType {
        PETROL, DIESEL, ELECTRIC
    }
}
