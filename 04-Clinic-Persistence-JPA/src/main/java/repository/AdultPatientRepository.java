package repository;

import model.AdultPatient;

import java.util.List;
import java.util.Optional;

public interface AdultPatientRepository {

    void save(AdultPatient patient);

    Optional<AdultPatient> findById(Long id);

    List<AdultPatient> findAll();

    void delete(AdultPatient patient);
}
