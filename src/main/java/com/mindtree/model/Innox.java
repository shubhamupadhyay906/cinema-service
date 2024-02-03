package com.mindtree.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("innox")
@Table(name = "INNOX")
public class Innox extends CinemaIF implements Comparable<Innox> {
    private BigDecimal foodCharge;

    @Override
    public int compareTo(Innox o) {
        return this.getBranchName().compareTo(o.getBranchName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Innox innox = (Innox) o;
        return Objects.equals(foodCharge, innox.foodCharge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), foodCharge);
    }
}
