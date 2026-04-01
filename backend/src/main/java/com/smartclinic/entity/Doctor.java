package com.smartclinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Doctor Entity represents a doctor in the Smart Clinic system.
 * This class is mapped to the "doctors" table in the database.
 */
@Entity
@Table(name = "doctors")
public class Doctor {

    /**
     * Unique identifier for each doctor (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the doctor
     */
    @NotBlank
    @Column(nullable = false)
    private String name;

    /**
     * Specialization of the doctor (e.g., Cardiology, Neurology)
     */
    @NotBlank
    @Column(nullable = false)
    private String speciality;

    /**
     * Email of the doctor (must be unique)
     */
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Password for authentication
     */
    @NotBlank
    @Column(nullable = false)
    private String password;

    /**
     * List of available time slots for the doctor
     * Stored in a separate table "doctor_available_times"
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "doctor_available_times", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "available_time", nullable = false)
    private List<LocalTime> availableTimes = new ArrayList<>();

    // ================= GETTERS & SETTERS =================

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<LocalTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
    }
}