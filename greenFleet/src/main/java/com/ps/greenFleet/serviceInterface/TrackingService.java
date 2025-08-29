package com.ps.greenfleet.serviceInterface;


import com.ps.greenfleet.dto.TrackingLocationDTO;
import com.ps.greenfleet.dto.TrackingUpdateDTO;

import java.util.List;

public interface TrackingService {
    void pushLocationUpdate(TrackingUpdateDTO update);
    List<TrackingLocationDTO> getTrackingForBooking(Long bookingId);
}
