package repository;

import jakarta.persistence.EntityManager;
import model.Specialist;

import java.util.List;
import java.util.Optional;

public class JpaSpecialistRepository extends JpaRepository implements SpecialistRepository {

    public JpaSpecialistRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(Specialist specialist) {
        try {
            em.getTransaction().begin();
            if (specialist.getId() == null) {
                em.persist(specialist);
            } else {
                em.merge(specialist);
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
    public Optional<Specialist> findById(Long id) {
        return Optional.ofNullable(em.find(Specialist.class, id));
    }

    @Override
    public List<Specialist> findAll() {
        return em.createQuery("SELECT s FROM Specialist s", Specialist.class).getResultList();
    }

    @Override
    public void delete(Specialist specialist) {
        try {
            em.getTransaction().begin();
            Specialist managed = em.contains(specialist) ? specialist : em.merge(specialist);
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