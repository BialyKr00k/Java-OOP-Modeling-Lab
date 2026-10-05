package pja.edu.Dziedziczenie_wieloaspektowe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RemoteMedicalService extends MedicalService {
    private static final List<RemoteMedicalService> extent = new ArrayList<>();

    private String platformUrl;

    public RemoteMedicalService(String serviceId, Double price, String insurancePolicyNumber, String platformUrl) {
        super(serviceId, price, insurancePolicyNumber);
        setPlatformUrl(platformUrl);

        extent.add(this);
    }

    @Override
    public void bookAppointment() {
        System.out.println("Teleporada zarezerwowana dla usługi: " + getServiceId());
    }

    public void establishConnection() {
        System.out.println("Nawiązano połączenie z platformą: " + platformUrl + " dla usługi: " + getServiceId());
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public final void setPlatformUrl(String platformUrl) {
        if (platformUrl == null || platformUrl.isBlank()) {
            throw new IllegalArgumentException("platformUrl nie może być puste.");
        }
        this.platformUrl = platformUrl;
    }

    public static List<RemoteMedicalService> getRemoteMedicalServiceExtent() {
        return Collections.unmodifiableList(extent);
    }

    public static void showRemoteMedicalServiceExtent() {
        System.out.println("=== RemoteMedicalService extent ===");
        for (RemoteMedicalService s : extent) {
            System.out.println(s);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof RemoteMedicalService that)) return false;
        return Objects.equals(platformUrl, that.platformUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), platformUrl);
    }

    @Override
    public String toString() {
        return "RemoteMedicalService{" +
                "serviceId='" + getServiceId() + '\'' +
                ", price=" + getPrice() +
                ", insurancePolicyNumber='" + getInsurancePolicyNumber() + '\'' +
                ", platformUrl='" + platformUrl + '\'' +
                '}';
    }
}
