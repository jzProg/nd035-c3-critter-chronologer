package com.udacity.jdnd.course3.critter.schedule.service;

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
    public Schedule storeSchedule(Schedule schedule, List<Long> employees, List<Long> pets) {
        scheduleRepository.save(schedule);
        employees.forEach(e -> {
            Employee ee = userService.fetchEmployee(e);
            ee.getSchedules().add(schedule);
            userService.storeEmployee(ee);
        });
        pets.forEach(p -> {
            Pet pp = petService.fetchPet(p);
            pp.getSchedules().add(schedule);
            petService.storePet(pp);
        });
        schedule.setEmployeeIds(employees.stream().map(e -> userService.fetchEmployee(e)).collect(Collectors.toList()));
        schedule.setPetIds(pets.stream().map(p -> petService.fetchPet(p)).collect(Collectors.toList()));
        return schedule;
    }

    @Transactional
    public List<Schedule> fetchAllSchedules() {
        return (List<Schedule>) scheduleRepository.findAll();
    }

    @Transactional
    public List<Schedule> fetchScheduleForPet(long petId) {
        Pet pet = petService.fetchPet(petId);
        return pet.getSchedules();
    }

    @Transactional
    public List<Schedule> fetchScheduleForEmployee(long employeeId) {
        Employee e = userService.fetchEmployee(employeeId);
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
