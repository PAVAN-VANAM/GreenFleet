package com.ps.greenfleet.mapper;


import com.ps.greenfleet.dto.VehicleCreateRequest;
import com.ps.greenfleet.dto.VehicleResponse;
import com.ps.greenfleet.model.Vehicle;

public class VehicleMapper {
    public static Vehicle toEntity(VehicleCreateRequest request) {
        return Vehicle.builder()
                .registrationNumber(request.getRegistrationNumber())
                .make(request.getMake())
                .model(request.getModel())
                .year(request.getYear())
                .type(request.getType())
                .capacityKg(request.getCapacityKg())
                .co2PerKm(request.getCo2PerKm())
                .isAvailable(true)
                .tenantId(request.getTenantId())
                .build();
    }

    public static VehicleResponse toResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .vehicleId(vehicle.getVehicleId())
                .registrationNumber(vehicle.getRegistrationNumber())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .type(vehicle.getType())
                .capacityKg(vehicle.getCapacityKg())
                .co2PerKm(vehicle.getCo2PerKm())
                .isAvailable(vehicle.getIsAvailable())
                .tenantId(vehicle.getTenantId())
                .assignedDriverId(
                        vehicle.getAssignedDriver() != null
                                ? vehicle.getAssignedDriver().getId().toString()
                                : null
                )
                .build();
    }
}
