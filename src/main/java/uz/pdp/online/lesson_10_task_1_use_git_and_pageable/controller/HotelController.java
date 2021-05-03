package uz.pdp.online.lesson_10_task_1_use_git_and_pageable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.entity.Hotel;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.entity.Room;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.repository.HotelRepo;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.repository.RoomRepo;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    RoomRepo roomRepo;

    @PostMapping
    private String add(@RequestBody Hotel hotel) {
        boolean existsByName = hotelRepo.existsByName(hotel.getName());
        if (existsByName||hotel.getName().isEmpty())
            return "This hotel already exist, or name is empty";
        hotelRepo.save(hotel);
        return "Data added";
    }

    @GetMapping
    public Page<Hotel> getList(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Hotel> hotels = hotelRepo.findAll(pageable);
        return hotels;
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody Hotel hotel) {
        boolean exists = hotelRepo.existsByNameAndIdNot(hotel.getName(), id);
        if (exists)
            return "This hotel already exist, entering another name";
        Optional<Hotel> optionalHotel = hotelRepo.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel editingHotel = optionalHotel.get();
            editingHotel.setName(hotel.getName());
            hotelRepo.save(editingHotel);
            return "Data saved";
        }
        return "Data not found";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Hotel> optionalHotel = hotelRepo.findById(id);
        List<Room> allByHotelId = roomRepo.findAllByHotelId(id);
        if (optionalHotel.isPresent()) {
            roomRepo.deleteAll(allByHotelId);
            hotelRepo.delete(optionalHotel.get());
            return "Data deleted";
        }
        return "Data not found";
    }
}
