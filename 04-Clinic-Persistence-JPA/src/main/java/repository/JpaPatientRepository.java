package repository;

import jakarta.persistence.EntityManager;
import model.MedicalRecord;
import model.Patient;

import java.util.List;
import java.util.Optional;

public class JpaPatientRepository extends JpaRepository implements PatientRepository {

    public JpaPatientRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(Patient patient) {
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
    public Optional<Patient> findById(Long id) {
        return Optional.ofNullable(em.find(Patient.class, id));
    }

    @Override
    public List<Patient> findAll() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }

    @Override
    public void delete(Patient patient) {
        try {
            em.getTransaction().begin();
            Patient managed = em.contains(patient) ? patient : em.merge(patient);
            em.remove(managed);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<Patient> findByIdWithMedicalRecords(Long id) {
        return em.createQuery(
                "SELECT DISTINCT p FROM Patient p LEFT JOIN FETCH p.medicalRecords WHERE p.id = :id",
                Patient.class
        ).setParameter("id", id).getResultStream().findFirst();
    }

    @Override
    public List<MedicalRecord> findMedicalRecordsByPatientId(Long id) {
        return em.createQuery(
                "SELECT mr FROM MedicalRecord mr WHERE mr.patient.id = :id",
                MedicalRecord.class
        ).setParameter("id", id).getResultList();
    }
}