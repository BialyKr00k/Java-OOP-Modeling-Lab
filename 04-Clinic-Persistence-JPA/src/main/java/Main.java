import jakarta.persistence.EntityManager;
import model.*;
import org.h2.tools.Server;
import repository.*;
import utility.JpaUtility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Server h2server = null;

        try (EntityManager em = JpaUtility.getEntityManager()) {
            JpaDoctorRepository doctorRepo = new JpaDoctorRepository(em);
            JpaSpecialistRepository specialistRepo = new JpaSpecialistRepository(em);
            JpaGeneralPractitionerRepository gpRepo = new JpaGeneralPractitionerRepository(em);
            JpaPatientRepository patientRepo = new JpaPatientRepository(em);
            JpaAdultPatientRepository adultRepo = new JpaAdultPatientRepository(em);
            JpaPediatricPatientRepository pediatricRepo = new JpaPediatricPatientRepository(em);
            JpaAppointmentRepository appointmentRepo = new JpaAppointmentRepository(em);
            JpaMedicalRecordRepository recordRepo = new JpaMedicalRecordRepository(em);
            JpaAppointmentNoteRepository noteRepo = new JpaAppointmentNoteRepository(em);

            Specialist specialist = Specialist.builder()
                    .firstName("Jan").lastName("Kowalski")
                    .email("jan.kowalski@klinika.pl")
                    .address(new Address("ul. Medyczna 5", "Warszawa", "00-001"))
                    .specializations(Set.of("Kardiologia", "Interna"))
                    .licenseNumber("LIC-2024-001")
                    .build();
            specialistRepo.save(specialist);

            GeneralPractitioner gp = GeneralPractitioner.builder()
                    .firstName("Maria").lastName("Wisniewska")
                    .email("m.wisniewska@klinika.pl")
                    .address(new Address("ul. Zdrowa 10", "Gdansk", "80-001"))
                    .specializations(Set.of("Medycyna ogolna"))
                    .clinicName("Przychodnia Centrum")
                    .build();
            gpRepo.save(gp);

            AdultPatient adult = AdultPatient.builder()
                    .firstName("Anna").lastName("Nowak")
                    .email("anna.nowak@email.com").age(35)
                    .address(new Address("ul. Rozana 12", "Krakow", "30-001"))
                    .occupation("Inzynier")
                    .build();
            adultRepo.save(adult);

            PediatricPatient child = PediatricPatient.builder()
                    .firstName("Piotr").lastName("Nowak")
                    .email("piotr.nowak@email.com").age(8)
                    .address(new Address("ul. Rozana 12", "Krakow", "30-001"))
                    .guardianName("Jan Nowak")
                    .build();
            pediatricRepo.save(child);

            Appointment appointment = Appointment.builder()
                    .appointmentDate(LocalDateTime.now().plusDays(3))
                    .description("Kontrolna wizyta kardiologiczna")
                    .status(AppointmentStatus.SCHEDULED)
                    .doctor(gp).patient(adult)
                    .build();
            appointmentRepo.save(appointment);

            MedicalRecord record1 = new MedicalRecord(null, "Wywiad kardiologiczny - cisnienie 120/80", adult);
            MedicalRecord record2 = new MedicalRecord(null, "Badanie EKG - wynik prawidlowy", adult);
            recordRepo.save(record1);
            recordRepo.save(record2);

            AppointmentNote note = new AppointmentNote(
                    null, "Pacjent wymaga dalszej obserwacji", LocalDateTime.now(), gp, adult);
            noteRepo.save(note);

            em.clear();

            System.out.println("\n=== DZIEDZICZENIE JOINED (Doctor) ===");
            System.out.println("Wszyscy lekarze (polimorficznie): " + doctorRepo.findAll().size());
            System.out.println("Tylko Specialist: " + specialistRepo.findAll().size());
            System.out.println("Tylko GeneralPractitioner: " + gpRepo.findAll().size());
            specialistRepo.findById(specialist.getId()).ifPresent(s ->
                    System.out.println("findById Specialist: " + s.getFirstName() + ", licencja: " + s.getLicenseNumber()));
            specialistRepo.delete(specialist);
            em.clear();
            System.out.println("Specialist po usunieciu: " + specialistRepo.findAll().size());

            System.out.println("\n=== DZIEDZICZENIE SINGLE_TABLE (Patient) ===");
            System.out.println("Wszyscy pacjenci (polimorficznie): " + patientRepo.findAll().size());
            System.out.println("Tylko AdultPatient: " + adultRepo.findAll().size());
            System.out.println("Tylko PediatricPatient: " + pediatricRepo.findAll().size());
            pediatricRepo.findById(child.getId()).ifPresent(p ->
                    System.out.println("findById PediatricPatient: " + p.getFirstName() + ", opiekun: " + p.getGuardianName()));

            pediatricRepo.delete(child);
            em.clear();
            System.out.println("PediatricPatient po usunieciu: " + pediatricRepo.findAll().size());

            System.out.println("\n=== ASOCJACJA ZWYKLA (Doctor <-> Appointment) ===");

            doctorRepo.findByIdWithAppointments(gp.getId()).ifPresent(d ->
                    System.out.println("Lekarz z wizytami (JOIN FETCH): " + d.getFirstName()
                            + ", liczba wizyt: " + d.getAppointments().size()));

            List<Appointment> doctorAppointments = doctorRepo.findAppointmentsByDoctorId(gp.getId());
            System.out.println("Wizyty lekarza (tylko powiazane obiekty): " + doctorAppointments.size());

            appointmentRepo.delete(appointment);
            em.clear();
            System.out.println("Wizyty po usunieciu: " + appointmentRepo.findAll().size());

            System.out.println("\n=== KOMPOZYCJA (Patient -> MedicalRecord) ===");
            patientRepo.findByIdWithMedicalRecords(adult.getId()).ifPresent(p ->
                    System.out.println("Pacjent z rekordami (JOIN FETCH): " + p.getFirstName()
                            + ", liczba rekordow: " + p.getMedicalRecords().size()));
            List<MedicalRecord> records = patientRepo.findMedicalRecordsByPatientId(adult.getId());
            System.out.println("Rekordy pacjenta (tylko powiazane obiekty): " + records.size());
            recordRepo.delete(record1);
            em.clear();
            System.out.println("Rekordy po usunieciu record1: " + recordRepo.findAll().size());

            System.out.println("\n=== ASOCJACJA Z ATRYBUTEM - BAG (AppointmentNote) ===");
            List<AppointmentNote> doctorNotes = noteRepo.findByDoctorId(gp.getId());
            System.out.println("Notatki lekarza (tylko powiazane obiekty): " + doctorNotes.size());

            Appointment appointment2 = Appointment.builder()
                    .appointmentDate(LocalDateTime.now().plusDays(7))
                    .description("Wizyta kontrolna")
                    .status(AppointmentStatus.SCHEDULED)
                    .doctor(em.find(GeneralPractitioner.class, gp.getId()))
                    .patient(em.find(AdultPatient.class, adult.getId()))
                    .build();
            appointmentRepo.save(appointment2);
            em.clear();

            appointmentRepo.findByIdWithDoctorAndPatient(appointment2.getId()).ifPresent(a ->
                    System.out.println("Wizyta (JOIN FETCH z doktorem i pacjentem): "
                            + a.getDescription() + " | dr " + a.getDoctor().getLastName()
                            + " | pacjent: " + a.getPatient().getLastName()));

            noteRepo.delete(note);
            em.clear();
            System.out.println("Notatki po usunieciu: " + noteRepo.findAll().size());

            h2server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("H2 Console is running!");
            System.out.println("1. Open your browser and go to: http://localhost:8082");
            System.out.println("2. Make sure the settings match the following:");
            System.out.println("    - Driver Class: org.h2.Driver");
            System.out.println("    - JDBC URL: jdbc:h2:./data/mp4");
            System.out.println("    - User name: mp4");
            System.out.println("    - Password: [blank]");

            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (h2server != null) {
                h2server.stop();
            }
            JpaUtility.close();
        }
    }
}