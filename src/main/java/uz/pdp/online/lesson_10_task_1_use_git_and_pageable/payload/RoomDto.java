package uz.pdp.online.lesson_10_task_1_use_git_and_pageable.payload;

import lombok.Data;


@Data
public class RoomDto {
    private Integer number;
    private Integer floor;
    private Integer size;
    private Integer hotelId;
}
