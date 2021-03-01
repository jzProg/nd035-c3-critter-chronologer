package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.dao.PetRepository;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.user.model.Customer;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Pet storePet(Pet pet) {
        long ownerId = pet.getOwnerId();
        petRepository.save(pet);
        Customer customer = userService.fetchCustomer(ownerId);
        List<Long> petIds = customer.getPetIds();
        petIds.add(pet.getId());
        customer.setPetIds(petIds);
        userService.storeCustomer(customer);
        return pet;
    }

    public Pet fetchPet(long petId) {
        return petRepository.findById(petId).orElse(null);
    }

    public List<Pet> fetchAllPets() {
        return (List<Pet>) petRepository.findAll();
    }

    public List<Pet> fetchPetByOwner(long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}
