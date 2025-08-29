package com.ps.greenfleet.repository;


import com.ps.greenfleet.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {
    List<Driver> findByTenantId(UUID tenantId);
    List<Driver> findByTenantIdIsNull(); // independent drivers
    List<Driver> findByStatus(String status);
}
