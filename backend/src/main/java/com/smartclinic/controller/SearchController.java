package com.smartclinic.controller;

import com.smartclinic.dto.DoctorResponse;
import com.smartclinic.repository.DoctorRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final DoctorRepository doctorRepository;

    public SearchController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/doctors")
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorResponse::fromEntity)
                .toList();
    }

    @GetMapping("/search/doctors")
    public List<DoctorResponse> searchDoctors(
            @RequestParam String speciality,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam LocalTime time) {
        return doctorRepository.findBySpecialityIgnoreCase(speciality)
                .stream()
                .filter(doctor -> doctor.getAvailableTimes().contains(time))
                .map(DoctorResponse::fromEntity)
                .toList();
    }
}
