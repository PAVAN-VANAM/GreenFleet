package com.ps.greenfleet.service;
import com.ps.greenfleet.dto.CompanyDTO;
import com.ps.greenfleet.dto.CompanyVehicleDTO;
import com.ps.greenfleet.model.Company;
import com.ps.greenfleet.model.CompanyVehicle;
import com.ps.greenfleet.repository.CompanyRepository;
import com.ps.greenfleet.repository.CompanyVehicleRepository;
import com.ps.greenfleet.serviceInterface.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyVehicleRepository vehicleRepository;

    @Override
    public CompanyDTO registerCompany(CompanyDTO dto) {
        Company company = Company.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .industry(dto.getIndustry())
                .password("hashed_password") // TODO: hash real password later
                .build();
        Company saved = companyRepository.save(company);
        return mapToDTO(saved);
    }

    @Override
    public List<CompanyVehicleDTO> addCompanyVehicles(Long companyId, List<CompanyVehicleDTO> vehicles) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        List<CompanyVehicle> entityList = vehicles.stream().map(v ->
                CompanyVehicle.builder()
                        .vehicleType(v.getVehicleType())
                        .capacity(v.getCapacity())
                        .age(v.getAge())
                        .pucCertificate(v.getPucCertificate())
                        .co2PerKm(v.getCo2PerKm())
                        .starRating(v.getStarRating())
                        .company(company)
                        .build()
        ).collect(Collectors.toList());

        return vehicleRepository.saveAll(entityList)
                .stream().map(this::mapVehicleToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyVehicleDTO> getCompanyVehicles(Long companyId) {
        return vehicleRepository.findByCompanyId(companyId)
                .stream().map(this::mapVehicleToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO getCompanyProfile(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return mapToDTO(company);
    }

    private CompanyDTO mapToDTO(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .email(company.getEmail())
                .industry(company.getIndustry())
                .totalCo2Emitted(company.getTotalCo2Emitted())
                .build();
    }

    private CompanyVehicleDTO mapVehicleToDTO(CompanyVehicle vehicle) {
        return CompanyVehicleDTO.builder()
                .id(vehicle.getId())
                .vehicleType(vehicle.getVehicleType())
                .capacity(vehicle.getCapacity())
                .age(vehicle.getAge())
                .pucCertificate(vehicle.getPucCertificate())
                .co2PerKm(vehicle.getCo2PerKm())
                .starRating(vehicle.getStarRating())
                .build();
    }
}
