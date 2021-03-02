package com.udacity.jdnd.course3.critter.pet.model;

import com.udacity.jdnd.course3.critter.schedule.model.Schedule;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pet {

    @Id
    @Column(name="pet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    @JoinColumn(name = "user_id")
    private Long ownerId;

    private LocalDate birthDate;

    private String notes;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "pet_schedule",
            joinColumns = { @JoinColumn(name = "pet_id") },
            inverseJoinColumns = { @JoinColumn(name = "schedule_id") }
    )
    private List<Schedule> pet_schedules = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Schedule> getSchedules() {
        return pet_schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.pet_schedules = schedules;
    }
}
