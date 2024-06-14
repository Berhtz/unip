package com.Berhtz.unip.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
  private Map<String, List<Lesson>> schedule;

  public void addLesson(String day, Lesson lesson) {
    if (!schedule.containsKey(day)) {
        schedule.put(day, new ArrayList<>());
    }
    schedule.get(day).add(lesson);
}
}


