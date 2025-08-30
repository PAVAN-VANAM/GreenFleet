package com.ps.smallvehicles.controller;

import com.ps.smallvehicles.Entities.Ride;
import com.ps.smallvehicles.dto.RideDTO;
import com.ps.smallvehicles.services.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // Create Ride
    @PostMapping
    public ResponseEntity<RideDTO> createRide(@RequestBody Ride ride) {
        Ride savedRide = rideService.createRide(ride);
        return ResponseEntity.ok(rideService.toDTO(savedRide));
    }

    // Get All Rides
    @GetMapping
    public ResponseEntity<List<Ride>> getAllRides() {
        return ResponseEntity.ok(rideService.getAllRides());
    }

    // Get Ride by ID
    @GetMapping("/{id}")
    public ResponseEntity<Ride> getRideById(@PathVariable Long id) {
        return rideService.getRideById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Ride
    @PutMapping("/{id}")
    public ResponseEntity<Ride> updateRide(@PathVariable Long id, @RequestBody Ride rideDetails) {
        return ResponseEntity.ok(rideService.updateRide(id, rideDetails));
    }

    // Delete Ride
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }
}
