package com.smartclinic.repository;

import com.smartclinic.entity.Doctor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByEmailAndPassword(String email, String password);

    List<Doctor> findBySpecialityIgnoreCase(String speciality);

    List<Doctor> findBySpecialityIgnoreCaseAndNameContainingIgnoreCase(String speciality, String name);
}
