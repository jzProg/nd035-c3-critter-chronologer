package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.dao.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
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
    public Schedule storeSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> fetchAllSchedules() {
        return (List<Schedule>) scheduleRepository.findAll();
    }

    public List<Schedule> fetchScheduleForPet(long petId) {
        List<Schedule> schedules = fetchAllSchedules();
        return schedules.stream()
                .filter(s -> s.getPetIds().contains(petId))
                .collect(Collectors.toList());
    }

    public List<Schedule> fetchScheduleForEmployee(long employeeId) {
        List<Schedule> schedules = fetchAllSchedules();
        return schedules.stream()
                .filter(s -> s.getEmployeeIds().contains(employeeId))
                .collect(Collectors.toList());
    }

    public List<Schedule> fetchScheduleForCustomer(long customerId) {
        List<Schedule> schedules = fetchAllSchedules();
        return schedules.stream()
                .filter(s -> s.getPetIds().stream()
                        .anyMatch(p -> userService.fetchCustomerByPet(p).getId().equals(customerId)))
                .collect(Collectors.toList());
    }
}
