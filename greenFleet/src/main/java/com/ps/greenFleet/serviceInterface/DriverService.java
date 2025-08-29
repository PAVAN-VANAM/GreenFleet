package com.ps.greenfleet.serviceInterface;

import com.ps.greenfleet.dto.DriverCreateRequest;
import com.ps.greenfleet.dto.DriverResponse;
import com.ps.greenfleet.dto.DriverUpdateRequest;

import java.util.List;

public interface DriverService {
    DriverResponse createDriver(DriverCreateRequest dto);
    DriverResponse updateDriver(String driverId, DriverUpdateRequest dto);
    DriverResponse getDriver(String driverId);
    List<DriverResponse> listDrivers(String tenantId, String status, Boolean independentOnly);
    void deleteDriver(String driverId);
    DriverResponse assignVehicle(String driverId, String vehicleId);
    DriverResponse unassignVehicle(String driverId);
}
