package com.Berhtz.unip.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Berhtz.unip.models.Day;
import com.Berhtz.unip.models.Group;
import com.Berhtz.unip.models.GroupDto2;
import com.Berhtz.unip.models.GroupName;
import com.Berhtz.unip.models.Lesson;
import com.Berhtz.unip.models.LessonDto;
import com.Berhtz.unip.models.Subject;
import com.Berhtz.unip.models.Teacher;
import com.Berhtz.unip.repositories.DayRepository;
import com.Berhtz.unip.repositories.GroupNameRepository;
import com.Berhtz.unip.repositories.GroupRepository;
import com.Berhtz.unip.repositories.LessonRepository;
import com.Berhtz.unip.repositories.SubjectRepository;
import com.Berhtz.unip.repositories.TeacherRepository;

@Service
public class LessonService {
    private static final Logger logger = LoggerFactory.getLogger(LessonService.class);
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupNameRepository groupNameRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson getLessonById(Integer id) {
        return lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public List<Lesson> getLessonsByGroup(Group group) {
        return lessonRepository.findByGroup(group);
    }

    public Group getGroup(String groupName, Integer groupYear, String studyForm) {
        GroupName name = groupNameRepository.findByNameIgnoreCase(groupName);
        return groupRepository.findByGroupNameAndStudyFormAndYear(name, studyForm, groupYear);
    }

    public List<Teacher> getTeacherSuggestions(String name) {
        return teacherRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Subject> getSubjectSuggestions(String name) {
        return subjectRepository.findByNameContainingIgnoreCase(name);
    }

    public List<GroupName> getGroupNameSuggestions(String name) {
        return groupNameRepository.findByNameContainingIgnoreCase(name);
    }

    public Group createGroup(GroupDto2 groupDto) {
        GroupName groupName = saveAndGetGroupName(groupDto);
        Group group = Group.builder()
                .groupName(groupName)
                .year(Integer.parseInt(groupDto.getGroupYear()))
                .studyForm(groupDto.getFormOfStudy())
                .build();
        return groupRepository.save(group);
    }

    public GroupName saveAndGetGroupName(GroupDto2 groupDto) {
        GroupName groupName = groupNameRepository.findByNameIgnoreCase(groupDto.getGroupName());
        if (groupName == null) {
            groupName = GroupName.builder().name(groupDto.getGroupName()).build();
            return groupNameRepository.save(groupName);
        } else {
            return groupName;
        }
    }

    public Group saveAndGetGroup(GroupDto2 groupDto) {
        GroupName groupName = saveAndGetGroupName(groupDto);
        Group group = groupRepository.findByGroupNameAndStudyFormAndYear(groupName, groupDto.getFormOfStudy(),
                Integer.parseInt(groupDto.getGroupYear()));
        if (group == null) {
            group = Group.builder()
                    .groupName(groupName)
                    .year(Integer.parseInt(groupDto.getGroupYear()))
                    .studyForm(groupDto.getFormOfStudy())
                    .build();
            return groupRepository.save(group);
        } else {
            return group;
        }
    }

    public void saveDay(Day day) {
        dayRepository.save(day);
    }

    public void saveLessonsByGroup(GroupDto2 groupDto) {
        Group group = saveAndGetGroup(groupDto);
        for (LessonDto ls : groupDto.getLessons()) {
            Day day = dayRepository.findByName(ls.getDays());
            if (day == null) {
                logger.error("Day not found: " + ls.getDays());
                continue;
            }
            Integer number = Integer.parseInt(ls.getNumber());
            Subject subject = subjectRepository.findByName(ls.getSubject());
            Teacher teacher = teacherRepository.findByName(ls.getTeacher());
            if (subject == null) {
                subject = Subject.builder().name(ls.getSubject()).build();
                subjectRepository.save(subject);
            }
            if (teacher == null) {
                teacher = Teacher.builder().name(ls.getTeacher()).build();
                teacherRepository.save(teacher);
            }
            Lesson lesson = lessonRepository.findByGroupAndDayAndNumber(group, day, number);
            logger.info("lesson: " + lesson);
            if (lesson == null) {
                lesson = Lesson.builder()
                        .group(group)
                        .subject(subject)
                        .number(number)
                        .teacher(teacher)
                        .day(day)
                        .build();
                lessonRepository.save(lesson);
                logger.info("Урок сохранен в БД (id + subject): " + lesson.getId() + " " + lesson.getSubject());
            } else {
                logger.info("Урок уже существует в БД (id + subject): " + lesson.getId() + " " + lesson.getSubject());
            }
        }
    }
}
