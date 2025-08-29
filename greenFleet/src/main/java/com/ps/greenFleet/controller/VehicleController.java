package com.ps.greenfleet.controller;


import com.ps.greenfleet.dto.VehicleCreateRequest;
import com.ps.greenfleet.dto.VehicleResponse;
import com.ps.greenfleet.dto.VehicleUpdateRequest;
import com.ps.greenfleet.serviceInterface.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public VehicleResponse create(@RequestBody VehicleCreateRequest request) {
        return vehicleService.create(request);
    }

    @PutMapping("/{id}")
    public VehicleResponse update(@PathVariable String id, @RequestBody VehicleUpdateRequest request) {
        return vehicleService.update(id, request);
    }

    @GetMapping("/{id}")
    public VehicleResponse getById(@PathVariable String id) {
        return vehicleService.getById(id);
    }

    @GetMapping
    public List<VehicleResponse> listAll(@RequestParam(required = false) String tenantId) {
        return vehicleService.listAll(tenantId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        vehicleService.delete(id);
    }

    @PostMapping("/{id}/assign-driver/{driverId}")
    public VehicleResponse assignDriver(@PathVariable String id, @PathVariable String driverId) {
        return vehicleService.assignDriver(id, driverId);
    }

    @PostMapping("/{id}/unassign-driver")
    public VehicleResponse unassignDriver(@PathVariable String id) {
        return vehicleService.unassignDriver(id);
    }
}

