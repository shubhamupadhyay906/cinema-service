package com.mindtree.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "CinemaIF")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "cinema_name", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("cinema")
@NamedQuery(name = "CinemaIF.findByCinemaName", query = "SELECT c FROM CinemaIF c WHERE c.cinemaName = ?1 order by id ")
public class CinemaIF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDate;
    private String branchName;
    @Column(name = "cinema_name", insertable = false, updatable = false)
    private String cinemaName;
    private BigDecimal price;
    private int bookedSeat;
    @OneToMany(targetEntity = Screen.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "screen_uk", referencedColumnName = "id")
    private List<Screen> screens;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CinemaIF cinemaIF = (CinemaIF) o;
        return bookedSeat == cinemaIF.bookedSeat && Objects.equals(id, cinemaIF.id) && Objects.equals(bookingDate, cinemaIF.bookingDate) && Objects.equals(branchName, cinemaIF.branchName) && Objects.equals(cinemaName, cinemaIF.cinemaName) && Objects.equals(price, cinemaIF.price) && Objects.equals(screens, cinemaIF.screens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookingDate, branchName, cinemaName, price, bookedSeat, screens);
    }
}
