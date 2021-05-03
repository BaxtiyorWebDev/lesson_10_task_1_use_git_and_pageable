package uz.pdp.online.lesson_10_task_1_use_git_and_pageable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_10_task_1_use_git_and_pageable.entity.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, Integer> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
