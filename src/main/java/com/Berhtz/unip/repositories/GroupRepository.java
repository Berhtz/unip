package com.Berhtz.unip.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Berhtz.unip.models.Group;
import com.Berhtz.unip.models.GroupName;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findByGroupNameAndStudyFormAndYear(GroupName groupName, String studyForm, Integer year);
    Group findByGroupName(GroupName groupName);
}
