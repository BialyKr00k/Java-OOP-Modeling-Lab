package repository;

import model.Specialist;

import java.util.List;
import java.util.Optional;

public interface SpecialistRepository {

    void save(Specialist specialist);

    Optional<Specialist> findById(Long id);

    List<Specialist> findAll();

    void delete(Specialist specialist);
}
