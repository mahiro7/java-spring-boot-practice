package com.gft.plannercrud.entities;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Código da disciplina não pode estar em branco!")
    @Column(unique = true)
    private String code;

    @NotBlank(message = "Nome da disciplina não pode estar em branco!")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
