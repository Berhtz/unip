package com.Berhtz.unip.models;

import java.util.List;

import lombok.Data;

@Data
public class GroupDto2 {
    private String groupName;
    private String groupYear;
    private String formOfStudy;
    private List<LessonDto> lessons;

}
