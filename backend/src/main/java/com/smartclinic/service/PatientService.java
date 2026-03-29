package com.smartclinic.service;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.entity.Patient;
import com.smartclinic.repository.PatientRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final TokenService tokenService;

    public PatientService(PatientRepository patientRepository, TokenService tokenService) {
        this.patientRepository = patientRepository;
        this.tokenService = tokenService;
    }

    public ApiResponse validatePatientLogin(String email, String password) {
        Optional<Patient> patient = patientRepository.findByEmail(email)
                .filter(existing -> existing.getPassword().equals(password));

        if (patient.isEmpty()) {
            return new ApiResponse(false, "Invalid patient credentials", null);
        }

        return new ApiResponse(true, "Patient login successful", tokenService.generateToken(email));
    }
}
