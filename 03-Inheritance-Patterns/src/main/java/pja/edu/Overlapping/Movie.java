package pja.edu.Overlapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class Movie {
    private String title;
    private EnumSet<MovieGenre> genre = EnumSet.noneOf(MovieGenre.class);

    private Integer jokesPerMinute;
    private Integer scienceFactsCount;
    private Integer jumpScaresCount;

    private static final List<Movie> movieExtent = new ArrayList<>();

    public static List<Movie> getMovieExtent() {
        return Collections.unmodifiableList(movieExtent);
    }

    public Movie(String title, EnumSet<MovieGenre> genre) {
        setTitle(title);
        setGenre(genre);
        movieExtent.add(this);
    }

    public String getTitle() {
        return title;
    }
    public final void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank.");
        }
        this.title = title;
    }
    public EnumSet<MovieGenre> getGenre() {
        return EnumSet.copyOf(genre);
    }
    public final void setGenre(EnumSet<MovieGenre> genre) {
        if (genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Movie must have at least one genre.");
        }
        this.genre = EnumSet.copyOf(genre);
        if (!this.genre.contains(MovieGenre.COMEDY)) {
            jokesPerMinute = null;
        }
        if (!this.genre.contains(MovieGenre.SCIFI)) {
            scienceFactsCount = null;
        }
        if (!this.genre.contains(MovieGenre.HORROR)) {
            jumpScaresCount = null;
        }
    }

    public Integer getJokesPerMinute() {
        requireGenre(MovieGenre.COMEDY, "getJokesPerMinute");
        return jokesPerMinute;
    }
    public Integer getScienceFactsCount() {
        requireGenre(MovieGenre.SCIFI, "getScienceFactsCount");
        return scienceFactsCount;
    }
    public Integer getJumpScaresCount() {
        requireGenre(MovieGenre.HORROR, "getJumpScaresCount");
        return jumpScaresCount;
    }

    public void addJoke() {
        requireGenre(MovieGenre.COMEDY, "addJoke");
        if (jokesPerMinute == null) {
            jokesPerMinute = 0;
        }
        jokesPerMinute++;
    }

    public void addScienceFact() {
        requireGenre(MovieGenre.SCIFI, "addScienceFact");
        if (scienceFactsCount == null) {
            scienceFactsCount = 0;
        }
        scienceFactsCount++;
    }

    public void addJumpScare() {
        requireGenre(MovieGenre.HORROR, "addJumpScare");
        if (jumpScaresCount == null) {
            jumpScaresCount = 0;
        }
        jumpScaresCount++;
    }

    public double calculateMarketingBudget() {
        double budget = 10000.0;
        if (genre.contains(MovieGenre.COMEDY)) {
            budget += 800.0 * Objects.requireNonNullElse(jokesPerMinute, 0);
        }
        if (genre.contains(MovieGenre.SCIFI)) {
            budget += 1_200.0 * Objects.requireNonNullElse(scienceFactsCount, 0);
        }
        if (genre.contains(MovieGenre.HORROR)) {
            budget += 1_000.0 * Objects.requireNonNullElse(jumpScaresCount, 0);
        }
        return budget;
    }

    private void requireGenre(MovieGenre required, String operation) {
        if (!genre.contains(required)) {
            throw new IllegalStateException(
                    "Operation '" + operation + "' not allowed. Missing genre: " + required
            );
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", genre=" + genre +
                ", jokesPerMinute=" + jokesPerMinute +
                ", scienceFactsCount=" + scienceFactsCount +
                ", jumpScaresCount=" + jumpScaresCount +
                ", marketingBudget=" + calculateMarketingBudget() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(title, movie.title)
                && Objects.equals(genre, movie.genre)
                && Objects.equals(jokesPerMinute, movie.jokesPerMinute)
                && Objects.equals(scienceFactsCount, movie.scienceFactsCount)
                && Objects.equals(jumpScaresCount, movie.jumpScaresCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, jokesPerMinute, scienceFactsCount, jumpScaresCount);
    }
}
