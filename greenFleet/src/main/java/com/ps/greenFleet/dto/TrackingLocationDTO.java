package com.ps.greenfleet.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingLocationDTO {
    private Long bookingId;
    private Double lat;
    private Double lng;
    private Double bearing;
    private Long timestampEpochMillis;
}
