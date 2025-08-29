package com.ps.greenfleet.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {
    private Long companyId;
    private Long vehicleId; // selected vehicle id
    private String sourceAddress;
    private Double sourceLat;
    private Double sourceLng;
    private String destinationAddress;
    private Double destinationLat;
    private Double destinationLng;
    private Double distanceKm; // optional: if client knows; backend may compute/overwrite
    private Long estimatedTimeSeconds; // optional
}
