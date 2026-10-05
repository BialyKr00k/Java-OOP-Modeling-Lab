package repository;

import model.PediatricPatient;

import java.util.List;
import java.util.Optional;

public interface PediatricPatientRepository {

    void save(PediatricPatient patient);

    Optional<PediatricPatient> findById(Long id);

    List<PediatricPatient> findAll();

    void delete(PediatricPatient patient);
}
