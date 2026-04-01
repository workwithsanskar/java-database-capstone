package com.smartclinic.controller;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.dto.PrescriptionRequest;
import com.smartclinic.entity.Prescription;
import com.smartclinic.service.PrescriptionService;
import com.smartclinic.service.TokenService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * PrescriptionController handles all API requests related to prescriptions
 * such as creating and managing prescriptions.
 */
@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final TokenService tokenService;

    /**
     * Constructor for dependency injection
     */
    public PrescriptionController(PrescriptionService prescriptionService, TokenService tokenService) {
        this.prescriptionService = prescriptionService;
        this.tokenService = tokenService;
    }

    /**
     * API to create a new prescription
     *
     * URL:
     * /api/prescriptions/create/{token}
     *
     * @param token   authentication token
     * @param request prescription details
     * @return ApiResponse with created prescription ID
     */
    @PostMapping("/create/{token}")
    public ResponseEntity<ApiResponse> createPrescription(
            @PathVariable String token,
            @Valid @RequestBody PrescriptionRequest request) {

        // Step 1: Validate token
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid or expired token", null));
        }

        // Step 2: Save prescription using service layer
        Prescription prescription = prescriptionService.savePrescription(request);

        // Step 3: Return structured response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        true,
                        "Prescription saved successfully",
                        prescription.getId()));
    }
}