package com.Berhtz.unip.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.Berhtz.unip.models.Group;
import com.Berhtz.unip.models.GroupDto;
import com.Berhtz.unip.models.GroupDto2;
import com.Berhtz.unip.models.Lesson;
import com.Berhtz.unip.repositories.GroupNameRepository;
import com.Berhtz.unip.repositories.GroupRepository;
import com.Berhtz.unip.repositories.LessonRepository;
import com.Berhtz.unip.repositories.SubjectRepository;
import com.Berhtz.unip.services.LessonService;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    LessonService lessonService;

    @Autowired
    GroupNameRepository groupNameRepository;

    @Autowired
    GroupRepository groupRepository;

    @GetMapping
    public String searchSubmitView(Model model) {
        model.addAttribute("group", new GroupDto());
        return "searchbygroup";
    }

    @PostMapping
    public String home(@ModelAttribute GroupDto groupDto, Model model) {
        Group group = lessonService.getGroup(groupDto.getName(), Integer.parseInt(groupDto.getYear()), groupDto.getStudyForm());
        List<Lesson> lessons = lessonService.getLessonsByGroup(group);
        if (lessons.isEmpty()) {
            return "notfound";
        } else {
            System.out.println(lessons);
            model.addAttribute("lessons", lessons);
            return "home";
            
        }
    }
    
    @GetMapping("/create")
    public ModelAndView createLessons() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("form");
        return modelAndView;
    }
    
    @PostMapping("/create")
    public String createGroup(@RequestBody GroupDto2 group) {
        logger.info("groupDto: " + group);
        lessonService.saveLessonsByGroup(group);
        return "success";
    }
}
