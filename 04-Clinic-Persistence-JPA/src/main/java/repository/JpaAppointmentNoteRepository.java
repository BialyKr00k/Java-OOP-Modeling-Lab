package repository;

import jakarta.persistence.EntityManager;
import model.AppointmentNote;

import java.util.List;
import java.util.Optional;

public class JpaAppointmentNoteRepository extends JpaRepository implements AppointmentNoteRepository {

    public JpaAppointmentNoteRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(AppointmentNote note) {
        try {
            em.getTransaction().begin();
            if (note.getId() == null) {
                em.persist(note);
            } else {
                em.merge(note);
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
    public Optional<AppointmentNote> findById(Long id) {
        return Optional.ofNullable(em.find(AppointmentNote.class, id));
    }

    @Override
    public List<AppointmentNote> findAll() {
        return em.createQuery("SELECT n FROM AppointmentNote n", AppointmentNote.class).getResultList();
    }

    @Override
    public void delete(AppointmentNote note) {
        try {
            em.getTransaction().begin();
            AppointmentNote managed = em.contains(note) ? note : em.merge(note);
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
    public List<AppointmentNote> findByDoctorId(Long doctorId) {
        return em.createQuery(
                "SELECT n FROM AppointmentNote n WHERE n.doctor.id = :doctorId",
                AppointmentNote.class
        ).setParameter("doctorId", doctorId).getResultList();
    }
}