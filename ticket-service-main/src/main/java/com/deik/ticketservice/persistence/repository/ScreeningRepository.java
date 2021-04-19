package com.deik.ticketservice.persistence.repository;

import com.deik.ticketservice.persistence.entity.Movie;
import com.deik.ticketservice.persistence.entity.Room;
import com.deik.ticketservice.persistence.entity.Screening;
import com.deik.ticketservice.persistence.entity.id.ScreeningId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends CrudRepository<Screening, ScreeningId> {

    Optional<Screening> findById_MovieAndId_RoomAndId_Date(Movie movie, Room room, Date date);

}
