package repository;

import jakarta.persistence.EntityManager;
import model.GeneralPractitioner;

import java.util.List;
import java.util.Optional;

public class JpaGeneralPractitionerRepository extends JpaRepository implements GeneralPractitionerRepository {

    public JpaGeneralPractitionerRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(GeneralPractitioner gp) {
        try {
            em.getTransaction().begin();
            if (gp.getId() == null) {
                em.persist(gp);
            } else {
                em.merge(gp);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<GeneralPractitioner> findById(Long id) {
        return Optional.ofNullable(em.find(GeneralPractitioner.class, id));
    }

    @Override
    public List<GeneralPractitioner> findAll() {
        return em.createQuery("SELECT gp FROM GeneralPractitioner gp", GeneralPractitioner.class).getResultList();
    }

    @Override
    public void delete(GeneralPractitioner gp) {
        try {
            em.getTransaction().begin();
            GeneralPractitioner managed = em.contains(gp) ? gp : em.merge(gp);
            em.remove(managed);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}