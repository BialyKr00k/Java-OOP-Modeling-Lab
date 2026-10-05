package pja.edu.Dziedziczenie_wieloaspektowe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class MedicalService {
    private static final List<MedicalService> extent = new ArrayList<>();
    private String serviceId;
    private Double price;
    private String insurancePolicyNumber;

    public MedicalService(String serviceId, Double price, String insurancePolicyNumber) {
        setServiceId(serviceId);
        this.price = price;
        this.insurancePolicyNumber = insurancePolicyNumber;

        extent.add(this);
    }

    public abstract void bookAppointment();

    public String issueInvoice() {
        if (price == null) {
            throw new IllegalStateException("Nie można wystawić faktury - cena nie została ustawiona.");
        }
        return "Faktura wystawiona na kwotę: " + price + " dla usługi " + serviceId;
    }

    public boolean verifyInsuranceStatus() {
        return insurancePolicyNumber != null && !insurancePolicyNumber.isBlank();
    }

    public String getServiceId() {
        return serviceId;
    }

    public final void setServiceId(String serviceId) {
        if (serviceId == null || serviceId.isBlank()) {
            throw new IllegalArgumentException("serviceId nie może być puste.");
        }
        this.serviceId = serviceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price != null && price < 0) {
            throw new IllegalArgumentException("price nie może być ujemne.");
        }
        this.price = price;
    }

    public String getInsurancePolicyNumber() {
        return insurancePolicyNumber;
    }

    public void setInsurancePolicyNumber(String insurancePolicyNumber) {
        if (insurancePolicyNumber != null && insurancePolicyNumber.isBlank()) {
            throw new IllegalArgumentException("insurancePolicyNumber nie może być blank.");
        }
        this.insurancePolicyNumber = insurancePolicyNumber;
    }

    public static List<MedicalService> getMedicalServiceExtent() {
        return Collections.unmodifiableList(extent);
    }

    public static void showMedicalServiceExtent() {
        System.out.println("=== MedicalService extent ===");
        for (MedicalService ms : extent) {
            System.out.println(ms);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalService that)) return false;
        return Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId);
    }

    @Override
    public String toString() {
        return "MedicalService{" +
                "serviceId='" + serviceId + '\'' +
                ", price=" + price +
                ", insurancePolicyNumber='" + insurancePolicyNumber + '\'' +
                '}';
    }
}
