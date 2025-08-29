package com.ps.greenfleet.service;

import com.ps.greenfleet.dto.BookingRequestDTO;
import com.ps.greenfleet.dto.BookingResponseDTO;
import com.ps.greenfleet.model.Company;
import com.ps.greenfleet.model.CompanyVehicle;
import com.ps.greenfleet.model.VehicleBooking;
import com.ps.greenfleet.repository.CompanyRepository;
import com.ps.greenfleet.repository.CompanyVehicleRepository;
import com.ps.greenfleet.repository.VehicleBookingRepository;
import com.ps.greenfleet.serviceInterface.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final VehicleBookingRepository bookingRepository;
    private final CompanyRepository companyRepository;
    private final CompanyVehicleRepository vehicleRepository;

    private static final double CO2_ABSORPTION_PER_TREE_KG = 21.77; // kg per tree per year

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO req) {
        Company company = companyRepository.findById(req.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        CompanyVehicle vehicle = vehicleRepository.findById(req.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Use provided distance if present; otherwise it must be computed by frontend or route service
        Double distanceKm = req.getDistanceKm() != null ? req.getDistanceKm() : 0.0;
        Long estimatedTimeSeconds = req.getEstimatedTimeSeconds();

        // compute CO2: assuming vehicle.co2PerKm is stored in kg/km
        double co2EmittedKg = distanceKm * (vehicle.getCo2PerKm() == null ? 0.0 : vehicle.getCo2PerKm());
        int trees = (int) Math.ceil(co2EmittedKg / CO2_ABSORPTION_PER_TREE_KG);

        VehicleBooking booking = VehicleBooking.builder()
                .company(company)
                .vehicle(vehicle)
                .sourceAddress(req.getSourceAddress())
                .sourceLat(req.getSourceLat())
                .sourceLng(req.getSourceLng())
                .destinationAddress(req.getDestinationAddress())
                .destinationLat(req.getDestinationLat())
                .destinationLng(req.getDestinationLng())
                .distanceKm(distanceKm)
                .estimatedTimeSeconds(estimatedTimeSeconds)
                .co2EmittedKg(co2EmittedKg)
                .treesToPlant(trees)
                .createdAt(Instant.now())
                .status("CREATED")
                .build();

        VehicleBooking saved = bookingRepository.save(booking);

        // Optionally update company's totalCo2Emitted (aggregate) - here we don't commit to running total until trip completes,
        // but if you want to count scheduled bookings immediately you can add co2 now.
        BookingResponseDTO resp = BookingResponseDTO.builder()
                .bookingId(saved.getId())
                .status(saved.getStatus())
                .distanceKm(saved.getDistanceKm())
                .estimatedTimeSeconds(saved.getEstimatedTimeSeconds())
                .co2EmittedKg(saved.getCo2EmittedKg())
                .treesToPlant(saved.getTreesToPlant())
                .build();

        return resp;
    }

    @Override
    @Transactional
    public BookingResponseDTO startBooking(Long bookingId) {
        VehicleBooking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        b.setStatus("IN_PROGRESS");
        b.setStartedAt(Instant.now());
        bookingRepository.save(b);
        return toDto(b);
    }

    @Override
    @Transactional
    public BookingResponseDTO completeBooking(Long bookingId) {
        VehicleBooking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        b.setStatus("COMPLETED");
        b.setCompletedAt(Instant.now());
        bookingRepository.save(b);

        // update company total CO2 emitted aggregate
        Company company = b.getCompany();
        if (company != null) {
            Double prev = company.getTotalCo2Emitted() == null ? 0.0 : company.getTotalCo2Emitted();
            company.setTotalCo2Emitted(prev + (b.getCo2EmittedKg() == null ? 0.0 : b.getCo2EmittedKg()));
            companyRepository.save(company);
        }

        return toDto(b);
    }

    @Override
    public BookingResponseDTO getBooking(Long bookingId) {
        VehicleBooking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return toDto(b);
    }

    private BookingResponseDTO toDto(VehicleBooking b) {
        return BookingResponseDTO.builder()
                .bookingId(b.getId())
                .status(b.getStatus())
                .distanceKm(b.getDistanceKm())
                .estimatedTimeSeconds(b.getEstimatedTimeSeconds())
                .co2EmittedKg(b.getCo2EmittedKg())
                .treesToPlant(b.getTreesToPlant())
                .build();
    }
}
