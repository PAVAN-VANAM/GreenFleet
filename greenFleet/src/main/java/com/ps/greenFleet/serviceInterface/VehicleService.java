package com.ps.greenfleet.serviceInterface;

import com.ps.greenfleet.dto.VehicleCreateRequest;
import com.ps.greenfleet.dto.VehicleResponse;
import com.ps.greenfleet.dto.VehicleUpdateRequest;

import java.util.List;

public interface VehicleService {
    VehicleResponse create(VehicleCreateRequest request);
    VehicleResponse update(String vehicleId, VehicleUpdateRequest request);
    void delete(String vehicleId);
    VehicleResponse getById(String vehicleId);
    List<VehicleResponse> listAll(String tenantId);
    VehicleResponse assignDriver(String vehicleId, String driverId);
    VehicleResponse unassignDriver(String vehicleId);
}
