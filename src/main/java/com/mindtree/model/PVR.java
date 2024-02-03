package com.mindtree.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("pvr")
@Table(name = "PVR")
@ToString(callSuper = true)
public class PVR extends CinemaIF implements Comparable<PVR> {
    private BigDecimal parkingCharge;

    @Override
    public int compareTo(PVR o) {
        return this.getBranchName().compareTo(o.getBranchName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PVR pvr = (PVR) o;
        return Objects.equals(parkingCharge, pvr.parkingCharge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parkingCharge);
    }
}
