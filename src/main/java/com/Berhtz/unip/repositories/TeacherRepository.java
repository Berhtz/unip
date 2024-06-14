package com.Berhtz.unip.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Berhtz.unip.models.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer>{
    Optional<Teacher> findById(Integer id);
    Teacher findByName(String name);
    List<Teacher> findByNameContainingIgnoreCase(String name);
}
