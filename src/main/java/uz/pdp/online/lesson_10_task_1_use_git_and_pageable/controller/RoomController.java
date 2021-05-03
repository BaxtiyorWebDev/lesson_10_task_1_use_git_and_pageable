package uz.pdp.online.lesson_10_task_1_use_git_and_pageable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.entity.Hotel;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.entity.Room;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.payload.RoomDto;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.repository.HotelRepo;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.repository.RoomRepo;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    HotelRepo hotelRepo;

    @PostMapping
    public String add(@RequestBody RoomDto roomDto) {
        boolean exists = roomRepo.existsByNumberAndHotelId(roomDto.getNumber(), roomDto.getHotelId());
        if (exists)
            return "This room already exist, please entering another number or hotel id";
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        Optional<Hotel> optionalHotel = hotelRepo.findById(roomDto.getHotelId());
        if (optionalHotel.isPresent()) {
            room.setHotel(optionalHotel.get());
            roomRepo.save(room);
            return "Data added";
        }
        return "Data not found";
    }

    @GetMapping("/byHotelId/{hotelId}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer hotelId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> allByHotelId = roomRepo.findAllByHotelId(hotelId, pageable);
        return allByHotelId;
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        boolean exists = roomRepo.existsByNumberAndHotelIdAndIdNot(roomDto.getNumber(), roomDto.getHotelId(), id);
        if (exists) {
            return "This hotel already exist, please entering another data";
        }
        Optional<Room> optionalRoom = roomRepo.findById(id);
        Optional<Hotel> optionalHotel = hotelRepo.findById(roomDto.getHotelId());
        if (optionalRoom.isPresent() && optionalHotel.isPresent()) {
            Room editingRoom = optionalRoom.get();
            editingRoom.setNumber(roomDto.getNumber());
            editingRoom.setFloor(roomDto.getFloor());
            editingRoom.setSize(roomDto.getSize());
            editingRoom.setHotel(optionalHotel.get());
            roomRepo.save(editingRoom);
            return "Data edited";
        }
        return "Data not found";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomRepo.findById(id);
        if (optionalRoom.isPresent()) {
            roomRepo.delete(optionalRoom.get());
            return "Data deleted";
        }
        return "Data not found";
    }

}
