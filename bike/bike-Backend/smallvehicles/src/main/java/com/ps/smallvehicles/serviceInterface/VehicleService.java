package com.ps.smallvehicles.serviceInterface;


import com.ps.smallvehicles.Entities.Vehicle;
import java.util.List;

public interface VehicleService {
    Vehicle createVehicle(Long userId, Vehicle vehicle);
    Vehicle updateVehicle(Long id, Vehicle vehicle);
    void deleteVehicle(Long id);
    Vehicle getVehicleById(Long id);
    List<Vehicle> getAllVehicles();
    List<Vehicle> getVehiclesByUserId(Long userId);
}
