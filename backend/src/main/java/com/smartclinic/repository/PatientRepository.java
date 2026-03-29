package com.smartclinic.repository;

import com.smartclinic.entity.Patient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
