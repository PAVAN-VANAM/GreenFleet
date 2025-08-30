package com.ps.greenfleet.dto;

// Login Request DTO
import lombok.Data;

@Data
public class DriverLogin {
    private String email;
    private String password;
}
