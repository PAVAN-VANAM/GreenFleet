package com.ps.greenfleet.service;

import com.ps.greenfleet.dto.TrackingLocationDTO;
import com.ps.greenfleet.dto.TrackingUpdateDTO;
import com.ps.greenfleet.model.TrackingPoint;
import com.ps.greenfleet.repository.TrackingPointRepository;
import com.ps.greenfleet.serviceInterface.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

    private final TrackingPointRepository trackingRepository;
    private final SimpMessagingTemplate messagingTemplate; // to publish to websocket topics

    private static final String TRACKING_TOPIC_PREFIX = "/topic/tracking/"; // client subscribes to /topic/tracking/{bookingId}

    @Override
    public void pushLocationUpdate(TrackingUpdateDTO update) {
        TrackingPoint p = TrackingPoint.builder()
                .bookingId(update.getBookingId())
                .lat(update.getLat())
                .lng(update.getLng())
                .bearing(update.getBearing())
                .timestamp(Instant.ofEpochMilli(update.getTimestampEpochMillis()))
                .build();

        trackingRepository.save(p);

        // push to subscribers
        TrackingLocationDTO loc = TrackingLocationDTO.builder()
                .bookingId(update.getBookingId())
                .lat(update.getLat())
                .lng(update.getLng())
                .bearing(update.getBearing())
                .timestampEpochMillis(update.getTimestampEpochMillis())
                .build();

        messagingTemplate.convertAndSend(TRACKING_TOPIC_PREFIX + update.getBookingId(), loc);
    }

    @Override
    public List<TrackingLocationDTO> getTrackingForBooking(Long bookingId) {
        return trackingRepository.findByBookingIdOrderByTimestampAsc(bookingId)
                .stream()
                .map(p -> TrackingLocationDTO.builder()
                        .bookingId(p.getBookingId())
                        .lat(p.getLat())
                        .lng(p.getLng())
                        .bearing(p.getBearing())
                        .timestampEpochMillis(p.getTimestamp().toEpochMilli())
                        .build())
                .collect(Collectors.toList());
    }
}
