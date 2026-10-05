package repository;

import model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {

    void save(Appointment appointment);

    Optional<Appointment> findById(Long id);

    List<Appointment> findAll();

    void delete(Appointment appointment);

    Optional<Appointment> findByIdWithDoctorAndPatient(Long id);
}