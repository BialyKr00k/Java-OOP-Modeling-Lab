package pja.edu.Dziedziczenie_wieloaspektowe;

public class Main {
    public static void main(String[] args) {
        InPatientMedicalService stacjonarna = new InPatientMedicalService(
                "MS-001",
                350.0,
                null,
                "A-12"
        );

        RemoteMedicalService zdalna = new RemoteMedicalService(
                "MS-002",
                null,
                "POL-2026-XYZ",
                "https://telemed.example.com/visit/15"
        );

        stacjonarna.bookAppointment();
        stacjonarna.assignBed();
        System.out.println(stacjonarna.issueInvoice());

        zdalna.bookAppointment();
        zdalna.establishConnection();
        System.out.println("Ubezpieczenie zdalnej usługi aktywne? " + zdalna.verifyInsuranceStatus());

        System.out.println("\n--- toString() ---");
        System.out.println(stacjonarna);
        System.out.println(zdalna);

        System.out.println("\n--- Ekstensje ---");
        MedicalService.showMedicalServiceExtent();
        InPatientMedicalService.showInPatientMedicalServiceExtent();
        RemoteMedicalService.showRemoteMedicalServiceExtent();
    }
}
