package com.ps.greenfleet.service;


import com.ps.greenfleet.dto.DriverCreateRequest;
import com.ps.greenfleet.dto.DriverLogin;
import com.ps.greenfleet.dto.DriverResponse;
import com.ps.greenfleet.dto.DriverUpdateRequest;
import com.ps.greenfleet.mapper.DriverMapper;
import com.ps.greenfleet.model.Driver;
import com.ps.greenfleet.repository.DriverRepository;
import com.ps.greenfleet.serviceInterface.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverServiceImpl implements DriverService {

    private final DriverRepository repository;

    @Override
    public DriverResponse login(DriverLogin request) {
        Driver driver = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (!request.getPassword().equals(driver.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return repository.findByEmail(request.getEmail())
                 .map(DriverMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    @Override
    public DriverResponse createDriver(DriverCreateRequest dto) {
        Driver driver = DriverMapper.toEntity(dto);
        return DriverMapper.toResponse(repository.save(driver));
    }

    @Override
    public DriverResponse updateDriver(String driverId, DriverUpdateRequest dto) {
        Driver driver = repository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        DriverMapper.updateEntity(driver, dto);
        return DriverMapper.toResponse(repository.save(driver));
    }

    @Override
    public DriverResponse getDriver(String driverId) {
        return repository.findById(driverId)
                .map(DriverMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

//    @Override
//    public List<DriverResponse> listDrivers(String tenantId, String status, Boolean independentOnly) {
//        List<Driver> drivers;
//        if (independentOnly != null && independentOnly) {
//            drivers = repository.findByTenantIdIsNull();
//        } else if (tenantId != null) {
//            drivers = repository.findByTenantId(tenantId);
//        } else if (status != null) {
//            drivers = repository.findByStatus(status);
//        } else {
//            drivers = repository.findAll();
//        }
//        return drivers.stream().map(DriverMapper::toResponse).collect(Collectors.toList());
//    }

    @Override
    public void deleteDriver(String driverId) {
        repository.deleteById(driverId);
    }

//    @Override
//    public DriverResponse assignVehicle(String driverId, String vehicleId) {
//        Driver driver = repository.findById(driverId)
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//        driver.setAssignedVehicleId(vehicleId);
//        return DriverMapper.toResponse(repository.save(driver));
//    }
//
//    @Override
//    public DriverResponse unassignVehicle(String driverId) {
//        Driver driver = repository.findById(driverId)
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//        driver.setAssignedVehicleId(null);
//        return DriverMapper.toResponse(repository.save(driver));
//    }
}
