package repository;

import model.Appointment;
import model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {

    void save(Doctor doctor);

    Optional<Doctor> findById(Long id);

    List<Doctor> findAll();

    void delete(Doctor doctor);

    Optional<Doctor> findByIdWithAppointments(Long id);

    List<Appointment> findAppointmentsByDoctorId(Long id);
}
