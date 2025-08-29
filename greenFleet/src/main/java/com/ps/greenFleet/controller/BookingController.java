package com.ps.greenfleet.controller;

import com.ps.greenfleet.dto.BookingRequestDTO;
import com.ps.greenfleet.dto.BookingResponseDTO;
import com.ps.greenfleet.serviceInterface.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO req) {
        BookingResponseDTO resp = bookingService.createBooking(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/{bookingId}/start")
    public ResponseEntity<BookingResponseDTO> startBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.startBooking(bookingId));
    }

    @PostMapping("/{bookingId}/complete")
    public ResponseEntity<BookingResponseDTO> completeBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.completeBooking(bookingId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }
}

