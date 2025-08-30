package com.ps.smallvehicles.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideDTO {
    private Long rideId;

    // User info
    private Long userId;
    private String userName;
    private String userEmail;

    // Vehicle info
    private Long vehicleId;
    private String vehicleModel;
    private String vehicleType;
    private Integer seats;

    // Ride info
    private Double sourceLat;
    private Double sourceLng;
    private String sourceAddress;

    private Double destinationLat;
    private Double destinationLng;
    private String destinationAddress;

    private Integer availableSeats;
    private Double distanceKm;
    private Double farePerKm;
    private Boolean weatherFactorApplied;
    private Boolean trafficFactorApplied;
    private String maxFactorAllowed;

    private String createdAt;
    private String updatedAt;
}
