package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.PrescriptionRequest;
import com.smartclinic.entity.Prescription;
import com.smartclinic.service.PrescriptionService;
import com.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final TokenService tokenService;

    public PrescriptionController(PrescriptionService prescriptionService, TokenService tokenService) {
        this.prescriptionService = prescriptionService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createPrescription(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody PrescriptionRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Token validation failed", null));
        }

        Prescription prescription = prescriptionService.savePrescription(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Prescription saved successfully", prescription.getId()));
    }
}
