package com.udacity.jdnd.course3.critter.user.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Customer")
@DiscriminatorValue("customer")
public class Customer extends User {

    @Column(name="phone_number")
    private String phoneNumber;

    private String notes;

    @ElementCollection
    @CollectionTable(name="customer_pet", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="pet")
    private List<Long> petIds = new ArrayList<>();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }
}
