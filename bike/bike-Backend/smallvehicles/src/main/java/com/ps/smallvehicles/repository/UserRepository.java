package com.ps.smallvehicles.repository;

import com.ps.smallvehicles.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByBikeNumber(String bikeNumber);

    Optional<User> findByBikeLicense(String bikeLicense);
}
