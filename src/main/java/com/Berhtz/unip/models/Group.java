package com.Berhtz.unip.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="groups")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Group implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name="groupName_id", referencedColumnName="id")
    private GroupName groupName;

    private Integer year;

    private String studyForm;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private List<Lesson> lesson;

    @ManyToOne
    @JoinColumn(name="group_id_faculty", referencedColumnName = "id")
    private Faculty faculty;

    @Override
    public String toString() {
        return "Group [name=" + groupName + "]";
    }    
}
