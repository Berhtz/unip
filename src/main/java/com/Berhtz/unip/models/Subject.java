package com.Berhtz.unip.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable{

    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    public List<Lesson> lesson;

    @Override
    public String toString() {
        return "Subject [id=" + id + ", name=" + name + /* ", lesson=" + lesson + */ /*  ", teachers=" + teachers + */ "]";
    }
}


