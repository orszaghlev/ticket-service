package com.deik.ticketservice.core.room.model;

import java.util.Objects;

public class RoomDto {

    private final String name;
    private final int numberOfRows;
    private final int numberOfCols;

    private RoomDto(String name, int numberOfRows, int numberOfCols) {
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.numberOfCols = numberOfCols;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfCols() {
        return numberOfCols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomDto)) {
            return false;
        }
        RoomDto roomDto = (RoomDto) o;
        return getNumberOfRows() == roomDto.getNumberOfRows() && getNumberOfCols() == roomDto.getNumberOfCols()
                && getName().equals(roomDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumberOfRows(), getNumberOfCols());
    }

    @Override
    public String toString() {
        return "RoomDto{" + "name='" + name + '\'' + ", numberOfRows=" + numberOfRows + ", numberOfCols=" + numberOfCols
                + '}';
    }

    public static class Builder {

        private String name;
        private int numberOfRows;
        private int numberOfCols;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withNumberOfRows(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            return this;
        }

        public Builder withNumberOfCols(int numberOfCols) {
            this.numberOfCols = numberOfCols;
            return this;
        }

        public RoomDto build() {
            return new RoomDto(name, numberOfRows, numberOfCols);
        }

    }

}
