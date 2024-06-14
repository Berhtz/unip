package com.Berhtz.unip.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Berhtz.unip.models.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    Optional<Subject> findById(Integer id);
    Subject findByName(String name);
    List<Subject> findByNameContainingIgnoreCase(String name);
}