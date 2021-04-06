package com.deik.ticketservice.entity;

import javax.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "numberOfRows")
    private int numberOfRows;
    @Column(name = "numberOfCols")
    private int numberOfCols;

    public Room() {

    }

    public Room(String name, int numberOfRows, int numberOfCols) {
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
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

    @Override
    public String toString() {
        return "Room{" +
                ", name='" + name + '\'' +
                ", numberOfRows=" + numberOfRows +
                ", numberOfCols=" + numberOfCols +
                '}';
    }

}
