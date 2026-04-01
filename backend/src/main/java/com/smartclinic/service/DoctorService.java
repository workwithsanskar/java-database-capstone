package com.smartclinic.service;

import com.smartclinic.entity.Appointment;
import com.smartclinic.entity.Doctor;
import com.smartclinic.repository.AppointmentRepository;
import com.smartclinic.repository.DoctorRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * DoctorService handles business logic related to doctors
 * such as availability and login validation.
 */
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final TokenService tokenService;

    /**
     * Constructor for dependency injection
     */
    public DoctorService(DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            TokenService tokenService) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.tokenService = tokenService;
    }

    /**
     * Get available time slots for a doctor on a given date
     *
     * @param doctorId doctor ID
     * @param date     appointment date
     * @return list of available time slots
     */
    public List<LocalTime> getAvailableTimeSlots(Long doctorId, LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        // Fetch already booked time slots
        List<LocalTime> bookedTimes = appointmentRepository
                .findAppointmentsForDoctorOnDate(doctorId, date)
                .stream()
                .map(Appointment::getAppointmentTime)
                .map(dateTime -> dateTime.toLocalTime())
                .collect(Collectors.toList());

        // Filter available slots
        return doctor.getAvailableTimes()
                .stream()
                .filter(time -> !bookedTimes.contains(time))
                .collect(Collectors.toList());
    }

    /**
     * Validate doctor login credentials
     *
     * @param email    doctor's email
     * @param password doctor's password
     * @return ResponseEntity containing token or error message
     */
    public ResponseEntity<Map<String, String>> validateDoctorLogin(String email, String password) {

        Optional<Doctor> doctor = doctorRepository.findByEmailAndPassword(email, password);

        Map<String, String> response = new HashMap<>();

        // Invalid credentials
        if (doctor.isEmpty()) {
            response.put("message", "Invalid doctor credentials");
            return ResponseEntity.status(401).body(response);
        }

        // Generate token
        String token = tokenService.generateToken(email);

        response.put("message", "Doctor login successful");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}