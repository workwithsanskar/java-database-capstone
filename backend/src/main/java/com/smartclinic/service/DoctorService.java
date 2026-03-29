package com.smartclinic.service;

import com.smartclinic.dto.ApiResponse;
import com.smartclinic.entity.Appointment;
import com.smartclinic.entity.Doctor;
import com.smartclinic.repository.AppointmentRepository;
import com.smartclinic.repository.DoctorRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final TokenService tokenService;

    public DoctorService(DoctorRepository doctorRepository,
                         AppointmentRepository appointmentRepository,
                         TokenService tokenService) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.tokenService = tokenService;
    }

    public List<LocalTime> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        List<LocalTime> bookedTimes = appointmentRepository.findAppointmentsForDoctorOnDate(doctorId, date)
                .stream()
                .map(Appointment::getAppointmentTime)
                .map(dateTime -> dateTime.toLocalTime())
                .collect(Collectors.toList());

        return doctor.getAvailableTimes()
                .stream()
                .filter(time -> !bookedTimes.contains(time))
                .collect(Collectors.toList());
    }

    public ApiResponse validateDoctorLogin(String email, String password) {
        Optional<Doctor> doctor = doctorRepository.findByEmailAndPassword(email, password);
        if (doctor.isEmpty()) {
            return new ApiResponse(false, "Invalid doctor credentials", null);
        }

        String token = tokenService.generateToken(email);
        return new ApiResponse(true, "Doctor login successful", token);
    }
}
