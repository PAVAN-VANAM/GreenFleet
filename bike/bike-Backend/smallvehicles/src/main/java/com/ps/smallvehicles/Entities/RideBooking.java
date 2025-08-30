package com.ps.smallvehicles.Entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ride_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideBooking {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ride_id", nullable = false)
    private Ride ride;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pool_user_id", nullable = false)
    private User poolUser;

    private Integer seatsBooked;

    private Double fare;

    private Double co2Saved;

    private LocalDateTime bookingTime;
}
