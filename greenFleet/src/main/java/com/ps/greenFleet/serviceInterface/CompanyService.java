package com.ps.greenfleet.serviceInterface;

import com.ps.greenfleet.dto.CompanyDTO;
import com.ps.greenfleet.dto.CompanyVehicleDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO registerCompany(CompanyDTO companyDTO);
    List<CompanyVehicleDTO> addCompanyVehicles(Long companyId, List<CompanyVehicleDTO> vehicles);
    List<CompanyVehicleDTO> getCompanyVehicles(Long companyId);
    CompanyDTO getCompanyProfile(Long companyId);
}

