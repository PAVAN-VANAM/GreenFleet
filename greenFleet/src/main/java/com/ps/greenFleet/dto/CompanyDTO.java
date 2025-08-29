package com.ps.greenfleet.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {
    private Long id;
    private String name;
    private String email;
    private String industry;
    private Double totalCo2Emitted;
}
