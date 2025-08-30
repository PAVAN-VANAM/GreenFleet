package com.ps.smallvehicles.services;


import com.ps.smallvehicles.Entities.Ride;
import com.ps.smallvehicles.Entities.RideBooking;
import com.ps.smallvehicles.Entities.User;
import com.ps.smallvehicles.repository.RideBookingRepository;
import com.ps.smallvehicles.repository.RideRepository;
import com.ps.smallvehicles.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RideBookingService {

    private final RideBookingRepository rideBookingRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public RideBookingService(RideBookingRepository rideBookingRepository,
                              RideRepository rideRepository,
                              UserRepository userRepository) {
        this.rideBookingRepository = rideBookingRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    public RideBooking createBooking(Long rideId, Long poolUserId, int seatsBooked) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        User poolUser = userRepository.findById(poolUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (ride.getAvailableSeats() < seatsBooked) {
            throw new RuntimeException("Not enough seats available");
        }

        // Calculate fare
        double totalFare = ride.getFarePerKm() * ride.getDistanceKm() * seatsBooked;

        // Example CO2 saved = distance * seatsBooked * vehicle.co2PerKm
        double co2Saved = ride.getVehicle().getCo2PerKm() * ride.getDistanceKm() * seatsBooked;

        RideBooking booking = RideBooking.builder()
                .ride(ride)
                .poolUser(poolUser)
                .seatsBooked(seatsBooked)
                .fare(totalFare)
                .co2Saved(co2Saved)
                .bookingTime(LocalDateTime.now())
                .build();

        // update available seats
        ride.setAvailableSeats(ride.getAvailableSeats() - seatsBooked);
        rideRepository.save(ride);

        return rideBookingRepository.save(booking);
    }

    public List<RideBooking> getAllBookings() {
        return rideBookingRepository.findAll();
    }

    public Optional<RideBooking> getBookingById(Long bookingId) {
        return rideBookingRepository.findById(bookingId);
    }

    public List<RideBooking> getBookingsByRide(Long rideId) {
        return rideBookingRepository.findByRide_RideId(rideId);
    }

    public List<RideBooking> getBookingsByUser(Long poolUserId) {
        return rideBookingRepository.findByPoolUser_Id(poolUserId);
    }

    public void deleteBooking(Long bookingId) {
        rideBookingRepository.deleteById(bookingId);
    }
}
