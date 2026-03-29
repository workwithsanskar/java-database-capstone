USE smart_clinic;

DROP PROCEDURE IF EXISTS GetDailyAppointmentReportByDoctor;
DELIMITER //
CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN report_date DATE)
BEGIN
    SELECT d.name AS doctor_name,
           d.speciality,
           COUNT(a.id) AS total_appointments
    FROM doctors d
    LEFT JOIN appointments a
        ON d.id = a.doctor_id
       AND DATE(a.appointment_time) = report_date
    GROUP BY d.id, d.name, d.speciality
    ORDER BY total_appointments DESC, d.name ASC;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByMonth;
DELIMITER //
CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN report_year INT, IN report_month INT)
BEGIN
    SELECT d.name AS doctor_name,
           d.speciality,
           COUNT(DISTINCT a.patient_id) AS unique_patients
    FROM doctors d
    JOIN appointments a ON d.id = a.doctor_id
    WHERE YEAR(a.appointment_time) = report_year
      AND MONTH(a.appointment_time) = report_month
    GROUP BY d.id, d.name, d.speciality
    ORDER BY unique_patients DESC
    LIMIT 1;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS GetDoctorWithMostPatientsByYear;
DELIMITER //
CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN report_year INT)
BEGIN
    SELECT d.name AS doctor_name,
           d.speciality,
           COUNT(DISTINCT a.patient_id) AS unique_patients
    FROM doctors d
    JOIN appointments a ON d.id = a.doctor_id
    WHERE YEAR(a.appointment_time) = report_year
    GROUP BY d.id, d.name, d.speciality
    ORDER BY unique_patients DESC
    LIMIT 1;
END //
DELIMITER ;
