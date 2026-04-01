package com.smartclinic.repository;

import com.smartclinic.entity.Patient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PatientRepository handles database operations for Patient entity.
 * It provides methods to retrieve patient details based on email or phone
 * number.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Find a patient by email address
     *
     * @param email patient's email
     * @return Optional containing Patient if found
     */
    Optional<Patient> findByEmail(String email);

    /**
     * Find a patient by email OR phone number
     *
     * @param email       patient's email
     * @param phoneNumber patient's phone number
     * @return Optional containing Patient if found
     */
    Optional<Patient> findByEmailOrPhoneNumber(String email, String phoneNumber);
}