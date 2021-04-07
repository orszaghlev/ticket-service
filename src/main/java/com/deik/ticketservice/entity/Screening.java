package com.deik.ticketservice.entity;

import com.deik.ticketservice.entity.id.ScreeningId;

import javax.persistence.*;

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
    public String toString() {
        return "Screening{" +
                "id=" + id +
                '}';
    }

}