package com.Berhtz.unip.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Berhtz.unip.models.GroupName;
import com.Berhtz.unip.models.Subject;
import com.Berhtz.unip.models.Teacher;
import com.Berhtz.unip.services.LessonService;

@RestController
@RequestMapping("/api")
public class HomeRestController {

    @Autowired
    private LessonService lessonService;

    @GetMapping("/groupnames")
    public List<String> getGroupNames(@RequestParam("query") String query) {
        List<GroupName> groupNames = lessonService.getGroupNameSuggestions(query);
        return groupNames.stream().map(GroupName::getName).collect(Collectors.toList());
    }

    @GetMapping("/subjectnames")
    public List<String> getSubjectNames(@RequestParam("query") String query) {
        List<Subject> subjects = lessonService.getSubjectSuggestions(query);
        return subjects.stream().map(Subject::getName).collect(Collectors.toList());
    }

    @GetMapping("/teachernames")
    public List<String> getTeacherNames(@RequestParam("query") String query) {
        List<Teacher> teachers = lessonService.getTeacherSuggestions(query);
        return teachers.stream().map(Teacher::getName).collect(Collectors.toList());
    }
}
