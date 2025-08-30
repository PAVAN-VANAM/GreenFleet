package com.ps.smallvehicles.services;


import com.ps.smallvehicles.Entities.Ride;
import com.ps.smallvehicles.Entities.User;
import com.ps.smallvehicles.Entities.Vehicle;
import com.ps.smallvehicles.dto.RideDTO;
import com.ps.smallvehicles.repository.RideRepository;
import com.ps.smallvehicles.repository.UserRepository;
import com.ps.smallvehicles.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    // Create Ride
    public Ride createRide(Ride ride) {
        // Fetch full User
        User user = userRepository.findById(ride.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch full Vehicle
        Vehicle vehicle = vehicleRepository.findById(ride.getVehicle().getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        ride.setUser(user);
        ride.setVehicle(vehicle);
        return rideRepository.save(ride);
    }


    // Get All Rides
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    // Get Ride by ID
    public Optional<Ride> getRideById(Long id) {
        return rideRepository.findById(id);
    }

    // Update Ride
    public Ride updateRide(Long id, Ride rideDetails) {
        return rideRepository.findById(id)
                .map(ride -> {
                    ride.setUser(rideDetails.getUser());
                    ride.setVehicle(rideDetails.getVehicle());
                    ride.setSourceLat(rideDetails.getSourceLat());
                    ride.setSourceLng(rideDetails.getSourceLng());
                    ride.setSourceAddress(rideDetails.getSourceAddress());
                    ride.setDestinationLat(rideDetails.getDestinationLat());
                    ride.setDestinationLng(rideDetails.getDestinationLng());
                    ride.setDestinationAddress(rideDetails.getDestinationAddress());
                    ride.setAvailableSeats(rideDetails.getAvailableSeats());
                    ride.setDistanceKm(rideDetails.getDistanceKm());
                    ride.setFarePerKm(rideDetails.getFarePerKm());
                    ride.setWeatherFactorApplied(rideDetails.getWeatherFactorApplied());
                    ride.setTrafficFactorApplied(rideDetails.getTrafficFactorApplied());
                    ride.setMaxFactorAllowed(rideDetails.getMaxFactorAllowed());
                    return rideRepository.save(ride);
                }).orElseThrow(() -> new RuntimeException("Ride not found with id " + id));
    }

    // Delete Ride
    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }

    // ✅ Convert Ride → RideDTO
    public RideDTO toDTO(Ride ride) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return RideDTO.builder()
                .rideId(ride.getRideId())

                // User
                .userId(ride.getUser() != null ? ride.getUser().getId() : null)
                .userName(ride.getUser() != null ? ride.getUser().getName() : null)
                .userEmail(ride.getUser() != null ? ride.getUser().getEmail() : null)

                // Vehicle
                .vehicleId(ride.getVehicle() != null ? ride.getVehicle().getId() : null)
                .vehicleModel(ride.getVehicle() != null ? ride.getVehicle().getModel() : null)
                .vehicleType(ride.getVehicle() != null ? ride.getVehicle().getVehicleType().name() : null)
                .seats(ride.getVehicle() != null ? ride.getVehicle().getSeats() : null)

                // Ride info
                .sourceLat(ride.getSourceLat())
                .sourceLng(ride.getSourceLng())
                .sourceAddress(ride.getSourceAddress())

                .destinationLat(ride.getDestinationLat())
                .destinationLng(ride.getDestinationLng())
                .destinationAddress(ride.getDestinationAddress())

                .availableSeats(ride.getAvailableSeats())
                .distanceKm(ride.getDistanceKm())
                .farePerKm(ride.getFarePerKm())
                .weatherFactorApplied(ride.getWeatherFactorApplied())
                .trafficFactorApplied(ride.getTrafficFactorApplied())
                .maxFactorAllowed(ride.getMaxFactorAllowed() != null ? ride.getMaxFactorAllowed().name() : null)

                // Timestamps
                .createdAt(ride.getCreatedAt() != null ? ride.getCreatedAt().format(formatter) : null)
                .updatedAt(ride.getUpdatedAt() != null ? ride.getUpdatedAt().format(formatter) : null)
                .build();
    }

}