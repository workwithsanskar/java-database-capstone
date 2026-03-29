package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.DoctorLoginRequest;
import com.smartclinic.service.DoctorService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final TokenService tokenService;

    public DoctorController(DoctorService doctorService, TokenService tokenService) {
        this.doctorService = doctorService;
        this.tokenService = tokenService;
    }

    @GetMapping("/availability")
    public ResponseEntity<ApiResponse> getDoctorAvailability(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid or expired token", null));
        }

        List<?> availableSlots = doctorService.getAvailableTimeSlots(doctorId, date);
        return ResponseEntity.ok(new ApiResponse(true, "Doctor availability fetched successfully", availableSlots));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginDoctor(@Valid @RequestBody DoctorLoginRequest request) {
        ApiResponse response = doctorService.validateDoctorLogin(request.getEmail(), request.getPassword());
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(response);
    }
}
