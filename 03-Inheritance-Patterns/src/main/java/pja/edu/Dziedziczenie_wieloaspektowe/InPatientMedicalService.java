package pja.edu.Dziedziczenie_wieloaspektowe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InPatientMedicalService extends MedicalService {
    private static final List<InPatientMedicalService> extent = new ArrayList<>();

    private String roomNumber;

    public InPatientMedicalService(String serviceId, Double price, String insurancePolicyNumber, String roomNumber) {
        super(serviceId, price, insurancePolicyNumber);
        setRoomNumber(roomNumber);

        extent.add(this);
    }

    @Override
    public void bookAppointment() {
        System.out.println("Wizyta stacjonarna zarezerwowana dla usługi: " + getServiceId());
    }

    public void assignBed() {
        System.out.println("Przydzielono łóżko w sali: " + roomNumber + " dla usługi: " + getServiceId());
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public final void setRoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.isBlank()) {
            throw new IllegalArgumentException("roomNumber nie może być puste.");
        }
        this.roomNumber = roomNumber;
    }

    public static List<InPatientMedicalService> getInPatientMedicalServiceExtent() {
        return Collections.unmodifiableList(extent);
    }

    public static void showInPatientMedicalServiceExtent() {
        System.out.println("=== InPatientMedicalService extent ===");
        for (InPatientMedicalService s : extent) {
            System.out.println(s);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof InPatientMedicalService that)) return false;
        return Objects.equals(roomNumber, that.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roomNumber);
    }

    @Override
    public String toString() {
        return "InPatientMedicalService{" +
                "serviceId='" + getServiceId() + '\'' +
                ", price=" + getPrice() +
                ", insurancePolicyNumber='" + getInsurancePolicyNumber() + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
