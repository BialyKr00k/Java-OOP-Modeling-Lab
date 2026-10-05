package repository;

import model.MedicalRecord;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordRepository {

    void save(MedicalRecord record);

    Optional<MedicalRecord> findById(Long id);

    List<MedicalRecord> findAll();

    void delete(MedicalRecord record);
}
