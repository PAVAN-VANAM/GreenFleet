package com.ps.greenfleet.controller;

import com.ps.greenfleet.dto.DriverCreateRequest;
import com.ps.greenfleet.dto.DriverResponse;
import com.ps.greenfleet.dto.DriverUpdateRequest;
import com.ps.greenfleet.serviceInterface.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService service;

    @PostMapping
    public DriverResponse createDriver(@Valid @RequestBody DriverCreateRequest dto) {
        return service.createDriver(dto);
    }

    @PutMapping("/{driverId}")
    public DriverResponse updateDriver(@PathVariable String driverId,
                                          @RequestBody DriverUpdateRequest dto) {
        return service.updateDriver(driverId, dto);
    }

    @GetMapping("/{driverId}")
    public DriverResponse getDriver(@PathVariable String driverId) {
        return service.getDriver(driverId);
    }

    @GetMapping
    public List<DriverResponse> listDrivers(@RequestParam(required = false) String tenantId,
                                               @RequestParam(required = false) String status,
                                               @RequestParam(required = false) Boolean independentOnly) {
        return service.listDrivers(tenantId, status, independentOnly);
    }

    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable String driverId) {
        service.deleteDriver(driverId);
    }

    @PostMapping("/{driverId}/assign/{vehicleId}")
    public DriverResponse assignVehicle(@PathVariable String driverId, @PathVariable String vehicleId) {
        return service.assignVehicle(driverId, vehicleId);
    }

    @PostMapping("/{driverId}/unassign")
    public DriverResponse unassignVehicle(@PathVariable String driverId) {
        return service.unassignVehicle(driverId);
    }
}
