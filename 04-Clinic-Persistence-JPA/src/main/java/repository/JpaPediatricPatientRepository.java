package repository;

import jakarta.persistence.EntityManager;
import model.PediatricPatient;

import java.util.List;
import java.util.Optional;

public class JpaPediatricPatientRepository extends JpaRepository implements PediatricPatientRepository {

    public JpaPediatricPatientRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(PediatricPatient patient) {
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
    public Optional<PediatricPatient> findById(Long id) {
        return Optional.ofNullable(em.find(PediatricPatient.class, id));
    }

    @Override
    public List<PediatricPatient> findAll() {
        return em.createQuery("SELECT p FROM PediatricPatient p", PediatricPatient.class).getResultList();
    }

    @Override
    public void delete(PediatricPatient patient) {
        try {
            em.getTransaction().begin();
            PediatricPatient managed = em.contains(patient) ? patient : em.merge(patient);
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