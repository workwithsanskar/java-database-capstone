package com.smartclinic.repository;

import com.smartclinic.entity.Appointment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.doctor.id = :doctorId and a.appointmentTime between :start and :end")
    List<Appointment> findAppointmentsForDoctorOnDate(
            @Param("doctorId") Long doctorId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("select a from Appointment a where a.patient.id = :patientId order by a.appointmentTime asc")
    List<Appointment> findAllByPatientId(@Param("patientId") Long patientId);

    default List<Appointment> findAppointmentsForDoctorOnDate(Long doctorId, LocalDate date) {
        return findAppointmentsForDoctorOnDate(
                doctorId,
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay().minusNanos(1)
        );
    }
}
