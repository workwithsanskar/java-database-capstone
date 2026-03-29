# SQL And API Outputs

## Question 19: `SHOW TABLES;`

```sql
+------------------------+
| Tables_in_smart_clinic |
+------------------------+
| appointments           |
| doctor_available_times |
| doctors                |
| patients               |
| prescriptions          |
+------------------------+
```

## Question 20: `SELECT * FROM patients LIMIT 5;`

```sql
+----+-------------+------------------------+--------------+------------+
| id | name        | email                  | phone_number | password   |
+----+-------------+------------------------+--------------+------------+
|  1 | Aarav Patel | aarav.patel@email.com  | 9000000001   | patient123 |
|  2 | Isha Verma  | isha.verma@email.com   | 9000000002   | patient123 |
|  3 | Rahul Singh | rahul.singh@email.com  | 9000000003   | patient123 |
|  4 | Neha Joshi  | neha.joshi@email.com   | 9000000004   | patient123 |
|  5 | Kabir Khan  | kabir.khan@email.com   | 9000000005   | patient123 |
+----+-------------+------------------------+--------------+------------+
```

## Question 21: `CALL GetDailyAppointmentReportByDoctor('2026-03-30');`

```sql
+------------------+-------------+--------------------+
| doctor_name      | speciality  | total_appointments |
+------------------+-------------+--------------------+
| Dr. Anika Sharma | Cardiology  | 2                  |
| Dr. Rohan Mehta  | Dermatology | 1                  |
| Dr. Priya Nair   | Pediatrics  | 0                  |
+------------------+-------------+--------------------+
```

## Question 22: `CALL GetDoctorWithMostPatientsByMonth(2026, 3);`

```sql
+------------------+------------+-----------------+
| doctor_name      | speciality | unique_patients |
+------------------+------------+-----------------+
| Dr. Anika Sharma | Cardiology | 2               |
+------------------+------------+-----------------+
```

## Question 23: `CALL GetDoctorWithMostPatientsByYear(2026);`

```sql
+------------------+------------+-----------------+
| doctor_name      | speciality | unique_patients |
+------------------+------------+-----------------+
| Dr. Anika Sharma | Cardiology | 3               |
+------------------+------------+-----------------+
```

## Question 24: `curl http://localhost:8080/api/doctors`

```json
[
  {
    "id": 1,
    "name": "Dr. Anika Sharma",
    "speciality": "Cardiology",
    "email": "anika.sharma@smartclinic.com"
  },
  {
    "id": 2,
    "name": "Dr. Rohan Mehta",
    "speciality": "Dermatology",
    "email": "rohan.mehta@smartclinic.com"
  },
  {
    "id": 3,
    "name": "Dr. Priya Nair",
    "speciality": "Pediatrics",
    "email": "priya.nair@smartclinic.com"
  }
]
```

## Question 25: Patient appointments with login credentials

```bash
curl -X POST http://localhost:8080/api/patients/login -H "Content-Type: application/json" -d "{\"email\":\"aarav.patel@email.com\",\"password\":\"patient123\"}"
curl http://localhost:8080/api/appointments/patients/1 -H "Authorization: Bearer <token>"
```

```json
[
  {
    "id": 1,
    "doctorId": 1,
    "patientId": 1,
    "appointmentTime": "2026-03-30T09:00:00",
    "status": "BOOKED"
  }
]
```

## Question 26: Doctor details for any speciality and time

```bash
curl "http://localhost:8080/api/search/doctors?speciality=Cardiology&date=2026-03-30&time=11:00:00"
```

```json
[
  {
    "id": 1,
    "name": "Dr. Anika Sharma",
    "speciality": "Cardiology",
    "availableTimes": [
      "11:00:00"
    ]
  }
]
```
