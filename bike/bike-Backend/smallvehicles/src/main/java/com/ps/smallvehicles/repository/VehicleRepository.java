package com.ps.smallvehicles.repository;


import com.ps.smallvehicles.Entities.Vehicle;
import com.ps.smallvehicles.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUser(User user);
}
