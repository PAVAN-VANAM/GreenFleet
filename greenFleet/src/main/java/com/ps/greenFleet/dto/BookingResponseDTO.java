package com.ps.greenfleet.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long bookingId;
    private String status;
    private Double distanceKm;
    private Long estimatedTimeSeconds;
    private Double co2EmittedKg;
    private Integer treesToPlant;
}
