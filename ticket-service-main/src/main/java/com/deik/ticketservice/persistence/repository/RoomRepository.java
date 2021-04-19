package com.deik.ticketservice.persistence.repository;

import com.deik.ticketservice.persistence.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {

    Optional<Room> findByName(String name);

}