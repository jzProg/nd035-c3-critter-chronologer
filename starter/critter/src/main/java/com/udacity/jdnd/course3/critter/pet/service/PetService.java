package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.UserNotFoundException;
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
    public Pet storePet(Pet pet) throws UserNotFoundException {
        Customer customer = userService.fetchCustomer(pet.getOwnerId());
        if (customer == null) throw new UserNotFoundException("Customer with id: " + pet.getOwnerId() + " not found!");
        petRepository.save(pet);
        List<Long> petIds = customer.getPetIds();
        petIds.add(pet.getId());
        customer.setPetIds(petIds);
        userService.storeCustomer(customer);
        return pet;
    }

    @Transactional
    public Pet fetchPet(long petId) throws PetNotFoundException {
        return petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException("Pet with id: " + petId + " not Found!"));
    }

    @Transactional
    public List<Pet> fetchAllPets() {
        return (List<Pet>) petRepository.findAll();
    }

    @Transactional
    public List<Pet> fetchPetByOwner(long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }
}
