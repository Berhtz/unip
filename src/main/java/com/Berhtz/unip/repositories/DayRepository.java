package com.Berhtz.unip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Berhtz.unip.models.Day;

public interface DayRepository extends JpaRepository<Day, Integer> {
    Day findByName(String name);
}
