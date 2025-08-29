package com.ps.greenfleet.controller;

import com.ps.greenfleet.dto.ApiResponse;
import com.ps.greenfleet.dto.CompanyDTO;
import com.ps.greenfleet.dto.CompanyVehicleDTO;
import com.ps.greenfleet.serviceInterface.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CompanyDTO>> registerCompany(@RequestBody CompanyDTO dto) {
        return ResponseEntity.ok(ApiResponse.<CompanyDTO>builder()
                .message("Company registered successfully")
                .data(companyService.registerCompany(dto))
                .build());
    }

    @PostMapping("/{companyId}/vehicles")
    public ResponseEntity<ApiResponse<List<CompanyVehicleDTO>>> addVehicles(
            @PathVariable Long companyId,
            @RequestBody List<CompanyVehicleDTO> vehicles) {
        return ResponseEntity.ok(ApiResponse.<List<CompanyVehicleDTO>>builder()
                .message("Vehicles added successfully")
                .data(companyService.addCompanyVehicles(companyId, vehicles))
                .build());
    }

    @GetMapping("/{companyId}/vehicles")
    public ResponseEntity<ApiResponse<List<CompanyVehicleDTO>>> getVehicles(@PathVariable Long companyId) {
        return ResponseEntity.ok(ApiResponse.<List<CompanyVehicleDTO>>builder()
                .message("Company vehicles fetched successfully")
                .data(companyService.getCompanyVehicles(companyId))
                .build());
    }

    @GetMapping("/{companyId}/profile")
    public ResponseEntity<ApiResponse<CompanyDTO>> getProfile(@PathVariable Long companyId) {
        return ResponseEntity.ok(ApiResponse.<CompanyDTO>builder()
                .message("Company profile fetched successfully")
                .data(companyService.getCompanyProfile(companyId))
                .build());
    }
}
