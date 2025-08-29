package com.ps.greenfleet.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vehicleType;   // Truck, Van, Car
    private Integer capacity;
    private Integer age;
    private Boolean pucCertificate;

    private Double co2PerKm;
    private Integer starRating;   // 1 - 5

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
