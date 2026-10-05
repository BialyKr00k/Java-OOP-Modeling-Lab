package repository;

import model.AppointmentNote;

import java.util.List;
import java.util.Optional;

public interface AppointmentNoteRepository {

    void save(AppointmentNote note);

    Optional<AppointmentNote> findById(Long id);

    List<AppointmentNote> findAll();

    void delete(AppointmentNote note);

    List<AppointmentNote> findByDoctorId(Long doctorId);
}