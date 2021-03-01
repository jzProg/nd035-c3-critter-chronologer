package com.udacity.jdnd.course3.critter.pet.dao;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {

    List<Pet> findByOwnerId(long ownerId);
}
