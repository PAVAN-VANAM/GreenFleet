package com.ps.greenfleet.controller;

import com.ps.greenfleet.dto.TrackingLocationDTO;
import com.ps.greenfleet.dto.TrackingUpdateDTO;
import com.ps.greenfleet.serviceInterface.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    /**
     * Drivers call this endpoint to update their location.
     * For reliability and low latency you can also allow WebSocket-based updates.
     */
    @PostMapping("/update")
    public ResponseEntity<Void> updateLocation(@RequestBody TrackingUpdateDTO dto) {
        trackingService.pushLocationUpdate(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * Companies or UI clients can fetch historical/buffered tracking points for a booking.
     */
    @GetMapping("/{bookingId}/points")
    public ResponseEntity<List<TrackingLocationDTO>> getTrackingPoints(@PathVariable Long bookingId) {
        return ResponseEntity.ok(trackingService.getTrackingForBooking(bookingId));
    }
}
