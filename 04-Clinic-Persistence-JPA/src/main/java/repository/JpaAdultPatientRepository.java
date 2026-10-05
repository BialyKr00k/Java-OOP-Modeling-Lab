package repository;

import jakarta.persistence.EntityManager;
import model.AdultPatient;

import java.util.List;
import java.util.Optional;

public class JpaAdultPatientRepository extends JpaRepository implements AdultPatientRepository {

    public JpaAdultPatientRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(AdultPatient patient) {
        try {
            em.getTransaction().begin();
            if (patient.getId() == null) {
                em.persist(patient);
            } else {
                em.merge(patient);
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
    public Optional<AdultPatient> findById(Long id) {
        return Optional.ofNullable(em.find(AdultPatient.class, id));
    }

    @Override
    public List<AdultPatient> findAll() {
        return em.createQuery("SELECT p FROM AdultPatient p", AdultPatient.class).getResultList();
    }

    @Override
    public void delete(AdultPatient patient) {
        try {
            em.getTransaction().begin();
            AdultPatient managed = em.contains(patient) ? patient : em.merge(patient);
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