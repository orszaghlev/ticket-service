package com.deik.ticketservice.entity;

import com.deik.ticketservice.entity.id.ScreeningId;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import java.util.Objects;

@Entity
@Table(name = "screening")
public class Screening {

    @EmbeddedId
    private ScreeningId id;

    public Screening() {

    }

    public Screening(ScreeningId id) {
        this.id = id;
    }

    public ScreeningId getId() {
        return id;
    }

    public void setId(ScreeningId id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Screening)) {
            return false;
        }
        Screening screening = (Screening) o;
        return getId().equals(screening.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Screening{" + "id=" + id + '}';
    }

}