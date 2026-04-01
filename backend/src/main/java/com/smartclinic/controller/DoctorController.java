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
import org.springframework.web.bind.annotation.*;

/**
 * DoctorController handles all doctor-related API requests
 * such as login and fetching doctor availability.
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final TokenService tokenService;

    /**
     * Constructor-based dependency injection
     */
    public DoctorController(DoctorService doctorService, TokenService tokenService) {
        this.doctorService = doctorService;
        this.tokenService = tokenService;
    }

    /**
     * API to fetch doctor availability based on doctorId and date
     * 
     * URL Format:
     * /api/doctors/availability/{user}/{doctorId}/{date}/{token}
     * 
     * @param user     - user role (e.g., doctor/admin)
     * @param doctorId - ID of the doctor
     * @param date     - date for checking availability
     * @param token    - authentication token
     * 
     * @return ApiResponse containing available time slots
     */
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<ApiResponse> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable String token) {

        // Step 1: Validate authentication token
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid or expired token", null));
        }

        // Step 2: Fetch available time slots from service layer
        List<?> availableSlots = doctorService.getAvailableTimeSlots(doctorId, date);

        // Step 3: Return structured response
        return ResponseEntity.ok(
                new ApiResponse(true, "Doctor availability fetched successfully", availableSlots));
    }

    /**
     * API for doctor login
     * 
     * @param request - contains email and password
     * @return ApiResponse with login status
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginDoctor(@Valid @RequestBody DoctorLoginRequest request) {

        // Validate login credentials via service layer
        ApiResponse response = doctorService.validateDoctorLogin(
                request.getEmail(),
                request.getPassword());

        // Return appropriate HTTP status
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(response);
    }
}