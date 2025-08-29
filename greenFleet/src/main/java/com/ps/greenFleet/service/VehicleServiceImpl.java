package com.ps.greenfleet.service;


import com.ps.greenfleet.dto.VehicleCreateRequest;
import com.ps.greenfleet.dto.VehicleResponse;
import com.ps.greenfleet.dto.VehicleUpdateRequest;
import com.ps.greenfleet.mapper.VehicleMapper;
import com.ps.greenfleet.model.Driver;
import com.ps.greenfleet.model.Vehicle;
import com.ps.greenfleet.repository.DriverRepository;
import com.ps.greenfleet.repository.VehicleRepository;
import com.ps.greenfleet.serviceInterface.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    @Override
    public VehicleResponse create(VehicleCreateRequest request) {
        Vehicle vehicle = VehicleMapper.toEntity(request);
        return VehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleResponse update(String vehicleId, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setType(request.getType());
        vehicle.setCapacityKg(request.getCapacityKg());
        vehicle.setCo2PerKm(request.getCo2PerKm());
        vehicle.setIsAvailable(request.getIsAvailable());
        return VehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public void delete(String vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponse getById(String vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .map(VehicleMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponse> listAll(String tenantId) {
        List<Vehicle> vehicles = tenantId != null
                ? vehicleRepository.findByTenantId(tenantId)
                : vehicleRepository.findAll();
        return vehicles.stream().map(VehicleMapper::toResponse).toList();
    }

    @Override
    public VehicleResponse assignDriver(String vehicleId, String driverId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        vehicle.setAssignedDriver(driver);
        vehicle.setIsAvailable(false);
        return VehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleResponse unassignDriver(String vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setAssignedDriver(null);
        vehicle.setIsAvailable(true);
        return VehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }
}
