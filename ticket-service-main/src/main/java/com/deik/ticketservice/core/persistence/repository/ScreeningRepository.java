package com.deik.ticketservice.core.persistence.repository;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.persistence.entity.id.ScreeningId;
import com.deik.ticketservice.core.persistence.entity.Screening;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends CrudRepository<Screening, ScreeningId> {

    Optional<Screening> findById_MovieAndId_RoomAndId_Date(Movie movie, Room room, Date date);

}
