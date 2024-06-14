package com.Berhtz.unip.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
public class Faculty {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy="faculty", fetch = FetchType.EAGER)
    private List<Group> group;
}
