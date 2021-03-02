package com.udacity.jdnd.course3.critter.user.model;

import com.udacity.jdnd.course3.critter.schedule.model.Schedule;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "Employee")
@DiscriminatorValue("employee")
public class Employee extends User {

    @ElementCollection(targetClass=EmployeeSkill.class)
    @CollectionTable(name="user_skill", joinColumns=@JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name="skill")
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass=DayOfWeek.class)
    @JoinTable(name="Days")
    @Enumerated(EnumType.STRING)
    @Column(name="day")
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "employee_schedule",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "schedule_id") }
    )
    private List<Schedule> schedules = new ArrayList<>();

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
