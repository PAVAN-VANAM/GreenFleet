package com.ps.smallvehicles.repository;


import com.ps.smallvehicles.Entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    // You can add custom queries if needed later
}
