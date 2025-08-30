package com.ps.smallvehicles.repository;


import com.ps.smallvehicles.Entities.RideBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideBookingRepository extends JpaRepository<RideBooking, Long> {
    List<RideBooking> findByRide_RideId(Long rideId);
    List<RideBooking> findByPoolUser_Id(Long poolUserId);
    Optional<RideBooking> findById(Long bookingId);
    void  deleteById(Long bookingId);
}
