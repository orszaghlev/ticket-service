package com.deik.ticketservice.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "numberOfRows")
    private int numberOfRows;
    @Column(name = "numberOfCols")
    private int numberOfCols;

    @OneToMany(mappedBy = "id.room")
    private Set<Screening> screenings;

    public Room() {

    }

    public Room(Integer id, String name, int numberOfRows, int numberOfCols) {
        this.id = id;
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfCols() {
        return numberOfCols;
    }

    public void setNumberOfCols(int numberOfCols) {
        this.numberOfCols = numberOfCols;
    }

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return getNumberOfRows() == room.getNumberOfRows() && getNumberOfCols() == room.getNumberOfCols()
                && getId().equals(room.getId()) && getName().equals(room.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getNumberOfRows(), getNumberOfCols());
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name='" + name + '\'' + ", numberOfRows=" + numberOfRows + ", numberOfCols="
                + numberOfCols + '}';
    }

}
