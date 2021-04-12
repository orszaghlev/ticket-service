package com.deik.ticketservice.repository;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.entity.Screening;
import com.deik.ticketservice.entity.id.ScreeningId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends CrudRepository<Screening, ScreeningId> {

    Optional<Screening> findById_MovieAndId_RoomAndId_Date(Movie movie, Room room, Date date);

}
