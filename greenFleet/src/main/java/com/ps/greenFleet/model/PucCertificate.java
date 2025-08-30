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
    private String issuedAt;
    private String expiryAt;
//    private String authority;
   }