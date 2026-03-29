package com.smartclinic.dto;

import com.smartclinic.entity.Doctor;
import java.time.LocalTime;
import java.util.List;

public class DoctorResponse {

    private Long id;
    private String name;
    private String speciality;
    private String email;
    private List<LocalTime> availableTimes;

    public static DoctorResponse fromEntity(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setName(doctor.getName());
        response.setSpeciality(doctor.getSpeciality());
        response.setEmail(doctor.getEmail());
        response.setAvailableTimes(doctor.getAvailableTimes());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LocalTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
