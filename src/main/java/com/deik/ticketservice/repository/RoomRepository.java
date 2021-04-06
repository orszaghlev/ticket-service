package com.deik.ticketservice.repository;

import com.deik.ticketservice.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, String> {

    Optional<Room> findByName(String name);

}