# 04 - Clinic Persistence with JPA

A Java clinic model extended with relational persistence using JPA and Hibernate ORM.

The project separates domain classes from repository interfaces and JPA repository implementations.

## Main Concepts

- object-relational mapping
- persistent domain entities
- repository pattern
- JPA repositories
- Hibernate ORM
- H2 database
- inheritance mapping
- entity relationships
- validation

## Project Structure

```text
src/main/java/
├── model/
├── repository/
└── utility/
```

### Model

The domain model contains classes such as:

```text
Patient
AdultPatient
PediatricPatient
Doctor
GeneralPractitioner
Specialist
Appointment
AppointmentNote
MedicalRecord
Address
```

### Repository Layer

The module contains repository interfaces and corresponding JPA implementations, for example:

```text
PatientRepository
JpaPatientRepository

DoctorRepository
JpaDoctorRepository

AppointmentRepository
JpaAppointmentRepository
```

A shared `JpaRepository` abstraction and `JpaUtility` are also included.

## Persistence Configuration

JPA configuration is stored in:

```text
src/main/resources/META-INF/persistence.xml
```

## Tech Stack

- Java 21
- Maven
- JPA
- Hibernate ORM 6.6
- H2 Database
- Jakarta Validation
- Hibernate Validator
- Lombok

## Running

Open this directory as a Maven project in your IDE and run `Main.java`.
