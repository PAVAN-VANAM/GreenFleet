package com.ps.smallvehicles.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;   // Auto-increment PK instead of UUID

    // Relationships
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    // Source details
    private Double sourceLat;
    private Double sourceLng;
    private String sourceAddress;

    // Destination details
    private Double destinationLat;
    private Double destinationLng;
    private String destinationAddress;

    // Ride details
    private Integer availableSeats;
    private Double distanceKm;
    private Double farePerKm;

    private Boolean weatherFactorApplied;
    private Boolean trafficFactorApplied;

    @Enumerated(EnumType.STRING)
    private MaxFactorAllowed maxFactorAllowed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Enum for maxFactorAllowed
    public enum MaxFactorAllowed {
        WEATHER, TRAFFIC, BOTH
    }
}
