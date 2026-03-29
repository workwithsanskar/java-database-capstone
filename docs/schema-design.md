# MySQL Schema Design

## Database Name

`smart_clinic`

## Tables

### `doctors`

| Field | Type | Constraints |
| --- | --- | --- |
| `id` | `BIGINT` | Primary key, auto increment |
| `name` | `VARCHAR(120)` | Not null |
| `speciality` | `VARCHAR(100)` | Not null |
| `email` | `VARCHAR(150)` | Unique, not null |
| `password` | `VARCHAR(255)` | Not null |

### `doctor_available_times`

| Field | Type | Constraints |
| --- | --- | --- |
| `doctor_id` | `BIGINT` | Foreign key to `doctors.id` |
| `available_time` | `TIME` | Not null |

### `patients`

| Field | Type | Constraints |
| --- | --- | --- |
| `id` | `BIGINT` | Primary key, auto increment |
| `name` | `VARCHAR(120)` | Not null |
| `email` | `VARCHAR(150)` | Unique, not null |
| `phone_number` | `VARCHAR(20)` | Unique, not null |
| `password` | `VARCHAR(255)` | Not null |

### `appointments`

| Field | Type | Constraints |
| --- | --- | --- |
| `id` | `BIGINT` | Primary key, auto increment |
| `doctor_id` | `BIGINT` | Foreign key to `doctors.id`, not null |
| `patient_id` | `BIGINT` | Foreign key to `patients.id`, not null |
| `appointment_time` | `DATETIME` | Not null |
| `status` | `VARCHAR(50)` | Default `BOOKED` |

### `prescriptions`

| Field | Type | Constraints |
| --- | --- | --- |
| `id` | `BIGINT` | Primary key, auto increment |
| `doctor_id` | `BIGINT` | Foreign key to `doctors.id`, not null |
| `patient_id` | `BIGINT` | Foreign key to `patients.id`, not null |
| `medication_details` | `TEXT` | Not null |
| `notes` | `TEXT` | Not null |

## Relationships

- One doctor can have many available time slots.
- One doctor can have many appointments.
- One patient can have many appointments.
- One doctor can create many prescriptions.
- One patient can receive many prescriptions.
