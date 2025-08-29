package com.ps.greenfleet.repository;


import com.ps.greenfleet.model.VehicleBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleBookingRepository extends JpaRepository<VehicleBooking, Long> {
    List<VehicleBooking> findByCompanyId(Long companyId);
}
