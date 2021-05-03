package uz.pdp.online.lesson_10_task_1_use_git_and_pageable.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.entity.Room;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Integer> {

    boolean existsByNumberAndHotelId(Integer number, Integer hotel_id);

    Page<Room> findAllByHotelId(Integer hotel_id, Pageable pageable);

    List<Room> findAllByHotelId(Integer hotel_id);

    boolean existsByNumberAndHotelIdAndIdNot(Integer number, Integer hotel_id, Integer id);
}
