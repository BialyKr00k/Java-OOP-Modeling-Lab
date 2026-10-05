package repository;

import jakarta.persistence.EntityManager;
import model.Appointment;

import java.util.List;
import java.util.Optional;

public class JpaAppointmentRepository extends JpaRepository implements AppointmentRepository {

    public JpaAppointmentRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(Appointment appointment) {
        try {
            em.getTransaction().begin();
            if (appointment.getId() == null) {
                em.persist(appointment);
            } else {
                em.merge(appointment);
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
    public Optional<Appointment> findById(Long id) {
        return Optional.ofNullable(em.find(Appointment.class, id));
    }

    @Override
    public List<Appointment> findAll() {
        return em.createQuery("SELECT a FROM Appointment a", Appointment.class).getResultList();
    }

    @Override
    public void delete(Appointment appointment) {
        try {
            em.getTransaction().begin();
            Appointment managed = em.contains(appointment) ? appointment : em.merge(appointment);
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
    public Optional<Appointment> findByIdWithDoctorAndPatient(Long id) {
        return em.createQuery(
                "SELECT a FROM Appointment a JOIN FETCH a.doctor JOIN FETCH a.patient WHERE a.id = :id",
                Appointment.class
        ).setParameter("id", id).getResultStream().findFirst();
    }
}