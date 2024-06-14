package com.Berhtz.unip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Berhtz.unip.models.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
    
}
