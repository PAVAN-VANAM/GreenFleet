package com.ps.greenfleet.model;



import jakarta.persistence.Embeddable;
import lombok.*;


import java.time.Instant;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PucCertificate {
    private String certificateId;
    private Instant issuedAt;
    private Instant expiryAt;
    private String authority;
    private String fileUrl; // optional storage link
}