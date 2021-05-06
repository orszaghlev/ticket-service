package com.deik.ticketservice.core.screening.persistence.repository;

import com.deik.ticketservice.core.movie.persistence.entity.Movie;
import com.deik.ticketservice.core.room.persistence.entity.Room;
import com.deik.ticketservice.core.screening.persistence.entity.id.ScreeningId;
import com.deik.ticketservice.core.screening.persistence.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, ScreeningId> {

    Optional<Screening> findById_MovieAndId_RoomAndId_Date(Movie movie, Room room, Date date);

}
