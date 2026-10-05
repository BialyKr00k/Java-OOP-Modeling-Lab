package repository;

import jakarta.persistence.EntityManager;
import model.Appointment;
import model.Doctor;

import java.util.List;
import java.util.Optional;

public class JpaDoctorRepository extends JpaRepository implements DoctorRepository {

    public JpaDoctorRepository(EntityManager em) {
        super(em);
    }

    @Override
    public void save(Doctor doctor) {
        try {
            em.getTransaction().begin();
            if (doctor.getId() == null) {
                em.persist(doctor);
            } else {
                em.merge(doctor);
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
    public Optional<Doctor> findById(Long id) {
        return Optional.ofNullable(em.find(Doctor.class, id));
    }

    @Override
    public List<Doctor> findAll() {
        return em.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
    }

    @Override
    public void delete(Doctor doctor) {
        try {
            em.getTransaction().begin();
            Doctor managed = em.contains(doctor) ? doctor : em.merge(doctor);
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
    public Optional<Doctor> findByIdWithAppointments(Long id) {
        return em.createQuery(
                "SELECT DISTINCT d FROM Doctor d LEFT JOIN FETCH d.appointments WHERE d.id = :id",
                Doctor.class
        ).setParameter("id", id).getResultStream().findFirst();
    }

    @Override
    public List<Appointment> findAppointmentsByDoctorId(Long id) {
        return em.createQuery(
                "SELECT a FROM Appointment a WHERE a.doctor.id = :id",
                Appointment.class
        ).setParameter("id", id).getResultList();
    }
}