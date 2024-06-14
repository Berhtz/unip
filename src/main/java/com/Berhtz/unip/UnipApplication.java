package com.Berhtz.unip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.Berhtz.unip.models.Day;
import com.Berhtz.unip.models.Group;
import com.Berhtz.unip.models.GroupName;
import com.Berhtz.unip.models.Lesson;
import com.Berhtz.unip.models.Subject;
import com.Berhtz.unip.models.Teacher;
import com.Berhtz.unip.repositories.DayRepository;
import com.Berhtz.unip.repositories.FacultyRepository;
import com.Berhtz.unip.repositories.GroupNameRepository;
import com.Berhtz.unip.repositories.GroupRepository;
import com.Berhtz.unip.repositories.LessonRepository;
import com.Berhtz.unip.repositories.SubjectRepository;
import com.Berhtz.unip.repositories.TeacherRepository;
import com.Berhtz.unip.services.LessonService;

@SpringBootApplication
public class UnipApplication {

	@Autowired
	LessonService lessonService;

	public static void main(String[] args) {
		SpringApplication.run(UnipApplication.class, args);
	}

	@Bean
	public CommandLineRunner subjectDataLoad(
			SubjectRepository subjectRepository,
			TeacherRepository teacherRepository,
			GroupRepository groupRepository,
			LessonRepository lessonRepository,
			FacultyRepository facultyRepository,
			DayRepository dayRepository,
			GroupNameRepository groupNameRepository) {
		return args -> {
			List<String> dayNames = Arrays.asList("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота");
			List<Day> days = saveDays(dayRepository, dayNames);

			List<String> teacherNames = Arrays.asList("Иванов И. И", "Смирнов И. И", "Петров П. П", "Сидоров С. С");
			List<Teacher> teachers = saveTeachers(teacherRepository, teacherNames);

			List<String> mathSubjects = Arrays.asList("Математика", "Информатика", "Алгебра",
					"Практика программирования");
			List<Subject> mathSubjectEntities = saveSubjects(subjectRepository, mathSubjects);

			List<String> langSubjects = Arrays.asList("Английский язык", "Русский язык", "Литература", "История");
			List<Subject> langSubjectEntities = saveSubjects(subjectRepository, langSubjects);

			createGroupWithLessons(
					groupRepository,
					groupNameRepository,
					lessonRepository,
					"Прикладная математика и информатика",
					"Очная",
					1,
					mathSubjectEntities,
					teachers,
					days);

			createGroupWithLessons(
					groupRepository,
					groupNameRepository,
					lessonRepository,
					"Педагогика: английский язык",
					"Очная",
					1,
					langSubjectEntities,
					teachers,
					days);
		};
	}

	private List<Day> saveDays(DayRepository dayRepository, List<String> dayNames) {
		List<Day> days = new ArrayList<>();
		for (String dayName : dayNames) {
			Day day = Day.builder().name(dayName).build();
			dayRepository.save(day);
			days.add(day);
		}
		return days;
	}

	private List<Teacher> saveTeachers(TeacherRepository teacherRepository, List<String> teacherNames) {
		List<Teacher> teachers = new ArrayList<>();
		for (String teacherName : teacherNames) {
			Teacher teacher = Teacher.builder().name(teacherName).build();
			teacherRepository.save(teacher);
			teachers.add(teacher);
		}
		return teachers;
	}

	private List<Subject> saveSubjects(SubjectRepository subjectRepository, List<String> subjectNames) {
		List<Subject> subjects = new ArrayList<>();
		for (String subjectName : subjectNames) {
			Subject subject = Subject.builder().name(subjectName).build();
			subjectRepository.save(subject);
			subjects.add(subject);
		}
		return subjects;
	}

	private void createGroupWithLessons(
			GroupRepository groupRepository,
			GroupNameRepository groupNameRepository,
			LessonRepository lessonRepository,
			String groupNameStr,
			String studyForm,
			int year,
			List<Subject> subjects,
			List<Teacher> teachers,
			List<Day> days) {
		GroupName groupName = GroupName.builder().name(groupNameStr).build();
		groupNameRepository.save(groupName);

		Group group = Group.builder().groupName(groupName).studyForm(studyForm).year(year).build();
		groupRepository.save(group);

		Random random = new Random();
		int minNumber = 3;
		int maxNumber = 4;

		for (Day day : days) {
			int rngNumber = random.nextInt(maxNumber - minNumber + 1) + minNumber;
			for (int i = 0; i < rngNumber; i++) {
				Lesson lesson = Lesson.builder()
						.number(i + 1)
						.group(group)
						.subject(subjects.get(random.nextInt(subjects.size())))
						.teacher(teachers.get(random.nextInt(teachers.size())))
						.day(day)
						.build();
				lessonRepository.save(lesson);
			}
		}
	}
}
