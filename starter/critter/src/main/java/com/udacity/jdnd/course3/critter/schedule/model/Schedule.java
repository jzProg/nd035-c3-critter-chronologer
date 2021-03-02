package com.udacity.jdnd.course3.critter.schedule.model;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.model.EmployeeSkill;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy="pet_schedules", cascade = CascadeType.ALL)
    private List<Pet> petIds;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="schedules", cascade = CascadeType.ALL)
    private List<Employee> employeeIds;

    private LocalDate date;

    @ElementCollection
    @CollectionTable(name="EmployeeSkill", joinColumns=@JoinColumn(name="id"))
    @Column(name="skills")
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Employee> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Pet> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pet> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
