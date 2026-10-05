package repository;

import model.MedicalRecord;
import model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    void save(Patient patient);

    Optional<Patient> findById(Long id);

    List<Patient> findAll();

    void delete(Patient patient);

    Optional<Patient> findByIdWithMedicalRecords(Long id);

    List<MedicalRecord> findMedicalRecordsByPatientId(Long id);
}
