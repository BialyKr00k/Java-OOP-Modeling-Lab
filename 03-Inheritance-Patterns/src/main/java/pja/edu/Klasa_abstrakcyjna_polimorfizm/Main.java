package pja.edu.Klasa_abstrakcyjna_polimorfizm;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Animal a1 = new Pet("Dog", 13, "Jan Kowalski", "Burek", 70, 1.2);
        Animal a2 = new FarmAnimal("Cow", 20, 30, LocalDate.of(2026, 4, 10), 50, 120);
        Animal a3 = new WildAnimal("Wolf", 8, 200, 7, 10);

        System.out.println("=== Polimorfizm ===");
        for (Animal a : Animal.getExtent()) {
            System.out.println(a + " | impact=" + a.estimateEnvironmentalImpact());
        }

        System.out.println("\n=== Ekstensja Animal (wszystkie) ===");
        Animal.showExtent();

        System.out.println("\n=== Ekstensje klas pochodnych ===");
        Pet.showPetExtent();
        FarmAnimal.showFarmAnimalExtent();
        WildAnimal.showWildAnimalExtent();

        System.out.println("\n=== Rozmiary ekstensji ===");
        System.out.println("Animal: " + Animal.getExtent().size());
        System.out.println("Pet: " + Pet.getPetExtent().size());
        System.out.println("FarmAnimal: " + FarmAnimal.getFarmAnimalExtent().size());
        System.out.println("WildAnimal: " + WildAnimal.getWildAnimalExtent().size());
    }
}