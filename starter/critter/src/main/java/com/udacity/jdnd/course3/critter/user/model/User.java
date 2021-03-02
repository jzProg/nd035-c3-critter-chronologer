package com.udacity.jdnd.course3.critter.user.model;

import javax.persistence.*;

/*
The default Single Table Inheritance was selected due to polymorphic query performance
and because NOT NULL constraints wasn't necessary for this project.
*/

@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

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
}
