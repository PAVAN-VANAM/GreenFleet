package com.ps.greenfleet.serviceInterface;


import com.ps.greenfleet.dto.BookingRequestDTO;
import com.ps.greenfleet.dto.BookingResponseDTO;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO request);
    BookingResponseDTO startBooking(Long bookingId);
    BookingResponseDTO completeBooking(Long bookingId);
    BookingResponseDTO getBooking(Long bookingId);
}
