package com.ps.smallvehicles.services;


import com.ps.smallvehicles.Entities.User;
import com.ps.smallvehicles.Entities.Vehicle;
import com.ps.smallvehicles.repository.UserRepository;
import com.ps.smallvehicles.repository.VehicleRepository;
import com.ps.smallvehicles.serviceInterface.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Vehicle createVehicle(Long userId, Vehicle vehicle) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        vehicle.setUser(user);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        vehicle.setModel(vehicleDetails.getModel());
        vehicle.setMileage(vehicleDetails.getMileage());
        vehicle.setFuelType(vehicleDetails.getFuelType());
        vehicle.setSeats(vehicleDetails.getSeats());
        vehicle.setCo2PerKm(vehicleDetails.getCo2PerKm());
        vehicle.setVehicleType(vehicleDetails.getVehicleType());

        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> getVehiclesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return vehicleRepository.findByUser(user);
    }
}
