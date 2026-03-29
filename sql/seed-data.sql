USE smart_clinic;

INSERT INTO doctors (id, name, speciality, email, password) VALUES
(1, 'Dr. Anika Sharma', 'Cardiology', 'anika.sharma@smartclinic.com', 'doctor123'),
(2, 'Dr. Rohan Mehta', 'Dermatology', 'rohan.mehta@smartclinic.com', 'doctor123'),
(3, 'Dr. Priya Nair', 'Pediatrics', 'priya.nair@smartclinic.com', 'doctor123');

INSERT INTO doctor_available_times (doctor_id, available_time) VALUES
(1, '09:00:00'),
(1, '10:00:00'),
(1, '11:00:00'),
(2, '14:00:00'),
(2, '15:00:00'),
(3, '16:00:00');

INSERT INTO patients (id, name, email, phone_number, password) VALUES
(1, 'Aarav Patel', 'aarav.patel@email.com', '9000000001', 'patient123'),
(2, 'Isha Verma', 'isha.verma@email.com', '9000000002', 'patient123'),
(3, 'Rahul Singh', 'rahul.singh@email.com', '9000000003', 'patient123'),
(4, 'Neha Joshi', 'neha.joshi@email.com', '9000000004', 'patient123'),
(5, 'Kabir Khan', 'kabir.khan@email.com', '9000000005', 'patient123'),
(6, 'Mira Das', 'mira.das@email.com', '9000000006', 'patient123');

INSERT INTO appointments (id, doctor_id, patient_id, appointment_time, status) VALUES
(1, 1, 1, '2026-03-30 09:00:00', 'BOOKED'),
(2, 1, 2, '2026-03-30 10:00:00', 'BOOKED'),
(3, 2, 3, '2026-03-30 14:00:00', 'BOOKED'),
(4, 1, 4, '2026-04-05 11:00:00', 'BOOKED'),
(5, 3, 5, '2026-04-10 16:00:00', 'BOOKED');

INSERT INTO prescriptions (id, doctor_id, patient_id, medication_details, notes) VALUES
(1, 1, 1, 'Atorvastatin 10mg once daily', 'Continue for 30 days and review cholesterol levels.'),
(2, 2, 3, 'Topical cream twice daily', 'Avoid direct sun exposure and follow up in one week.');
