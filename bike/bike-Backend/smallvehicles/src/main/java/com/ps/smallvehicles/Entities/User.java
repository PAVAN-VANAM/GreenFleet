package com.ps.smallvehicles.Entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")   // "user" is reserved keyword in PostgreSQL, so use "users"
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-generate ID
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true) // unique email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private Double rating;   // user’s rating (average rating from rides/pools)

    @Column(name = "ride_state")
    private String stateOfRidePool;  // state of ride/pool (ex: ACTIVE, INACTIVE)

    @Column(name = "bike_number", unique = true)
    private String bikeNumber;

    @Column(name = "bike_license", unique = true)
    private String bikeLicense;

    @Column(name = "co2_rate")
    private Double co2Rate;   // emission rate

    @Column(name = "puc_certificate")
    private String pucCertificate;  // path/number of certificate
}