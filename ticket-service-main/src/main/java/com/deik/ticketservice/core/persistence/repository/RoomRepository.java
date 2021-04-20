package com.deik.ticketservice.core.persistence.repository;

import com.deik.ticketservice.core.persistence.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {

    Optional<Room> findByName(String name);

}