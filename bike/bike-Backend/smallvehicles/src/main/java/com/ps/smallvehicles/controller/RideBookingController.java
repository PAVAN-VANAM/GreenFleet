package com.ps.smallvehicles.controller;


import com.ps.smallvehicles.Entities.RideBooking;
import com.ps.smallvehicles.services.RideBookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class RideBookingController {

    private final RideBookingService rideBookingService;

    public RideBookingController(RideBookingService rideBookingService) {
        this.rideBookingService = rideBookingService;
    }

    @PostMapping
    public RideBooking createBooking(@RequestParam Long rideId,
                                     @RequestParam Long poolUserId,
                                     @RequestParam int seatsBooked) {
        return rideBookingService.createBooking(rideId, poolUserId, seatsBooked);
    }

    @GetMapping
    public List<RideBooking> getAllBookings() {
        return rideBookingService.getAllBookings();
    }

    @GetMapping("/{bookingId}")
    public Optional<RideBooking> getBookingById(@PathVariable Long bookingId) {
        return rideBookingService.getBookingById(bookingId);
    }

    @GetMapping("/ride/{rideId}")
    public List<RideBooking> getBookingsByRide(@PathVariable Long rideId) {
        return rideBookingService.getBookingsByRide(rideId);
    }

    @GetMapping("/user/{poolUserId}")
    public List<RideBooking> getBookingsByUser(@PathVariable Long poolUserId) {
        return rideBookingService.getBookingsByUser(poolUserId);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable Long bookingId) {
        rideBookingService.deleteBooking(bookingId);
    }
}
