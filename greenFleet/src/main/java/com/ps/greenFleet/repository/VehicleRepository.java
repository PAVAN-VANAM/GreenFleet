package com.ps.greenfleet.repository;


import com.ps.greenfleet.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findByTenantId(String tenantId);
    List<Vehicle> findByIsAvailableTrue();
}
