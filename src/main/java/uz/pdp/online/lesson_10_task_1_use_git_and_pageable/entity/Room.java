package uz.pdp.online.lesson_10_task_1_use_git_and_pageable.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)

    private Integer floor;

    @Column(nullable = false)

    private Integer size;

    @ManyToOne
    private Hotel hotel;
}
