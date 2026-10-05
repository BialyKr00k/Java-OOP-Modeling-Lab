package repository;

import model.GeneralPractitioner;

import java.util.List;
import java.util.Optional;

public interface GeneralPractitionerRepository {

    void save(GeneralPractitioner gp);

    Optional<GeneralPractitioner> findById(Long id);

    List<GeneralPractitioner> findAll();

    void delete(GeneralPractitioner gp);
}
