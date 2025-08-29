package com.ps.greenfleet.repository;

import com.ps.greenfleet.model.TrackingPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingPointRepository extends JpaRepository<com.ps.greenfleet.model.TrackingPoint, Long> {
    List<TrackingPoint> findByBookingIdOrderByTimestampAsc(Long bookingId);
}
