package com.ps.greenfleet.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who booked (company)
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // vehicle used (could be company vehicle or driver-provided vehicle stored in CompanyVehicle)
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private CompanyVehicle vehicle;

    private String sourceAddress;
    private Double sourceLat;
    private Double sourceLng;

    private String destinationAddress;
    private Double destinationLat;
    private Double destinationLng;

    private Double distanceKm;
    private Long estimatedTimeSeconds;

    private Double co2EmittedKg;   // Total CO2 for this trip in kilograms
    private Integer treesToPlant;  // computed number of trees to neutralize

    private Instant createdAt;
    private Instant startedAt;
    private Instant completedAt;

    private String status; // CREATED, IN_PROGRESS, COMPLETED, CANCELLED
}
