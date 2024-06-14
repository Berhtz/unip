package com.Berhtz.unip.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Berhtz.unip.models.GroupName;

public interface GroupNameRepository extends JpaRepository<GroupName, Integer> {
    GroupName findByNameIgnoreCase(String name);
    List<GroupName> findByNameContainingIgnoreCase(String name);
}
