# Database Schema Design

## Tables

### Patients
- id (INT, PRIMARY KEY)
- name (VARCHAR)
- age (INT)

### Doctors
- id (INT, PRIMARY KEY)
- name (VARCHAR)
- specialization (VARCHAR)

### Appointments
- id (INT, PRIMARY KEY)
- patient_id (INT)
- doctor_id (INT)
- date (DATE)