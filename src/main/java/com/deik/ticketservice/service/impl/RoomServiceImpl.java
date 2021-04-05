package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.repository.RoomRepository;
import com.deik.ticketservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> listRooms() {
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(room -> rooms.add(room));
        return rooms;
    }

}
