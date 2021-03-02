package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.exceptions.MissingIdException;
import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.UserNotFoundException;
import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.dao.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Transactional
    public Schedule storeSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        if (employeeIds.isEmpty() || petIds.isEmpty()) throw new MissingIdException("No employee ID or pet ID...");
        scheduleRepository.save(schedule);
        employeeIds.forEach(eId -> {
            Employee e = userService.fetchEmployee(eId);
            e.getSchedules().add(schedule);
            userService.storeEmployee(e);
        });
        petIds.forEach(pId -> {
            Pet pet = petService.fetchPet(pId);
            pet.getSchedules().add(schedule);
            petService.storePet(pet);
        });
        schedule.setEmployeeIds(employeeIds.stream().map(e -> userService.fetchEmployee(e)).collect(Collectors.toList()));
        schedule.setPetIds(petIds.stream().map(p -> petService.fetchPet(p)).collect(Collectors.toList()));
        return schedule;
    }

    @Transactional
    public List<Schedule> fetchAllSchedules() {
        return (List<Schedule>) scheduleRepository.findAll();
    }

    @Transactional
    public List<Schedule> fetchScheduleForPet(long petId) {
        Pet pet = petService.fetchPet(petId);
        if (pet == null) throw new PetNotFoundException("No Pet found for id: " + petId);
        return pet.getSchedules();
    }

    @Transactional
    public List<Schedule> fetchScheduleForEmployee(long employeeId) {
        Employee e = userService.fetchEmployee(employeeId);
        if (e == null) throw new UserNotFoundException("No Employee found for id: " + employeeId);
        return e.getSchedules();
    }

    @Transactional
    public List<Schedule> fetchScheduleForCustomer(long customerId) {
        List<Schedule> schedules = fetchAllSchedules();
        return schedules.stream()
                .filter(s -> s.getPetIds().stream()
                        .anyMatch(p -> p.getOwnerId() == customerId))
                .collect(Collectors.toList());
    }
}
