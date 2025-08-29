package com.ps.greenfleet.repository;


import com.ps.greenfleet.model.CompanyVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompanyVehicleRepository extends JpaRepository<CompanyVehicle, Long> {
    List<CompanyVehicle> findByCompanyId(Long companyId);
}
