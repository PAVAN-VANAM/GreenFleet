package com.ps.greenfleet.mapper;

import com.ps.greenfleet.dto.DriverCreateRequest;
import com.ps.greenfleet.dto.DriverResponse;
import com.ps.greenfleet.dto.DriverUpdateRequest;
import com.ps.greenfleet.model.Driver;

public class DriverMapper {

    public static Driver toEntity(DriverCreateRequest dto) {
        return Driver.builder()
                .tenantId(dto.getTenantId())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .name(dto.getName())
                .licenseNumber(dto.getLicenseNumber())
                .licenseExpiry(dto.getLicenseExpiry())
                .vehicleType(dto.getVehicleType())
                .vehicleAgeYears(dto.getVehicleAgeYears())
                .capacityKg(dto.getCapacityKg())
                .status(dto.getStatus())
                .role(dto.getRole())
                .build();
    }

    public static void updateEntity(Driver driver, DriverUpdateRequest dto) {
        if (dto.getName() != null) driver.setName(dto.getName());
        if (dto.getPhone() != null) driver.setPhone(dto.getPhone());
        if (dto.getStatus() != null) driver.setStatus(dto.getStatus());
        if (dto.getVehicleType() != null) driver.setVehicleType(dto.getVehicleType());
        if (dto.getCapacityKg() != null) driver.setCapacityKg(dto.getCapacityKg());
    }

    public static DriverResponse toResponse(Driver driver) {
        return DriverResponse.builder()
                .driverId(driver.getDriverId())
                .tenantId(driver.getTenantId())
                .email(driver.getEmail())
                .phone(driver.getPhone())
                .name(driver.getName())
                .licenseNumber(driver.getLicenseNumber())
                .vehicleType(driver.getVehicleType())
                .capacityKg(driver.getCapacityKg())
                .status(driver.getStatus())
                .role(driver.getRole())
                .assignedVehicleId(driver.getAssignedVehicleId())
                .createdAt(driver.getCreatedAt())
                .updatedAt(driver.getUpdatedAt())
                .build();
    }
}
