package repository;

import jakarta.persistence.EntityManager;
import model.MedicalRecord;

import java.util.List;
import java.util.Optional;

public class JpaMedicalRecordRepository extends JpaRepository implements MedicalRecordRepository {

    public JpaMedicalRecordRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(MedicalRecord record) {
        try {
            em.getTransaction().begin();
            if (record.getId() == null) {
                em.persist(record);
            } else {
                em.merge(record);
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
    public Optional<MedicalRecord> findById(Long id) {
        return Optional.ofNullable(em.find(MedicalRecord.class, id));
    }

    @Override
    public List<MedicalRecord> findAll() {
        return em.createQuery("SELECT mr FROM MedicalRecord mr", MedicalRecord.class).getResultList();
    }

    @Override
    public void delete(MedicalRecord record) {
        try {
            em.getTransaction().begin();
            MedicalRecord managed = em.contains(record) ? record : em.merge(record);
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