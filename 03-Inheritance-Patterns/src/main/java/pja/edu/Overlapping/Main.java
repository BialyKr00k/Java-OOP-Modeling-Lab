package pja.edu.Overlapping;

import java.util.EnumSet;

public class Main {
    public static void main(String[] args) {
        Movie m1 = new Movie("Scary Space", EnumSet.of(MovieGenre.SCIFI, MovieGenre.HORROR));
        m1.addScienceFact();
        m1.addScienceFact();
        m1.addJumpScare();

        Movie m2 = new Movie("Funny Night", EnumSet.of(MovieGenre.COMEDY, MovieGenre.HORROR));
        m2.addJoke();
        m2.addJoke();
        m2.addJumpScare();
        m2.addJumpScare();

        Movie m3 = new Movie("Stand-up Only", EnumSet.of(MovieGenre.COMEDY));
        m3.addJoke();

        System.out.println("=== FILMY ===");
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);

        System.out.println("\n=== EKSTENSJA ===");
        Movie.getMovieExtent().forEach(System.out::println);

        System.out.println("\n=== PRÓBA NIEDOZWOLONEJ OPERACJI ===");
        try {
            m3.addScienceFact();
        } catch (IllegalStateException e) {
            System.out.println("Oczekiwany wyjątek: " + e.getMessage());
        }
    }
}
