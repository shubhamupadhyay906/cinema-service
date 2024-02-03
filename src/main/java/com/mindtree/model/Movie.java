package com.mindtree.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movies")
@NamedQuery(name = "Movie.findByMovieTitle", query = "SELECT m FROM Movie m WHERE m.title like ?1 order by m.id ")
public class Movie implements Comparable<Movie> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean showCycle;
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;


    @Override
    public int compareTo(Movie o) {
        return this.getTitle().compareTo(o.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return showCycle == movie.showCycle && Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(releaseDate, movie.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, showCycle, releaseDate);
    }
}
