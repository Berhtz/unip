package com.Berhtz.unip.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Lesson implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer number;

    @ManyToOne
    @ToString.Exclude
    @NotNull
    @JoinColumn(name="subject_id", referencedColumnName = "id")
    private Subject subject; 
    
    @ManyToOne
    @JoinColumn(name="teacher_id", referencedColumnName="id")
    private Teacher teacher;

    @ManyToOne
    @NotNull
    @JoinColumn(name="group_id", referencedColumnName = "id")
    private Group group;

    @ManyToOne
    @NotNull
    @JoinColumn(name="day_id", referencedColumnName="id")
    private Day day;
}