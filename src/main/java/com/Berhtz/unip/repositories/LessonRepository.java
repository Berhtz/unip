package com.Berhtz.unip.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Berhtz.unip.models.Day;
import com.Berhtz.unip.models.Group;
import com.Berhtz.unip.models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    Optional<Lesson> findById(Integer id);
    List<Lesson> findByGroup(Group group);
    Lesson findByGroupAndDayAndNumber(Group group, Day day, Integer number);
}
