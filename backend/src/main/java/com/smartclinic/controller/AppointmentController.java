package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.AppointmentBookingRequest;
import com.smartclinic.dto.AppointmentResponse;
import com.smartclinic.entity.Appointment;
import com.smartclinic.service.AppointmentService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final TokenService tokenService;

    public AppointmentController(AppointmentService appointmentService, TokenService tokenService) {
        this.appointmentService = appointmentService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> bookAppointment(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody AppointmentBookingRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Token validation failed", null));
        }

        Appointment appointment = appointmentService.bookAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Appointment booked successfully", appointment.getId()));
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<ApiResponse> getAppointmentsForPatient(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long patientId) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid or expired token", null));
        }

        List<AppointmentResponse> appointments = appointmentService.getAppointmentsForPatient(patientId)
                .stream()
                .map(AppointmentResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(new ApiResponse(true, "Patient appointments fetched successfully", appointments));
    }
}
