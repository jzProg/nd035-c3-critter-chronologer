package com.udacity.jdnd.course3.critter.schedule.controller;

import com.udacity.jdnd.course3.critter.pet.model.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.schedule.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.model.Employee;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.storeSchedule(convertDTOtoEntity(scheduleDTO), scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        return convertEntityToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleService.fetchAllSchedules().forEach(s-> scheduleDTOList.add(convertEntityToDTO(s)));
        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleService.fetchScheduleForPet(petId).forEach(s-> scheduleDTOList.add(convertEntityToDTO(s)));
        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleService.fetchScheduleForEmployee(employeeId).forEach(s-> scheduleDTOList.add(convertEntityToDTO(s)));
        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        scheduleService.fetchScheduleForCustomer(customerId).forEach(s-> scheduleDTOList.add(convertEntityToDTO(s)));
        return scheduleDTOList;
    }

    private Schedule convertDTOtoEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        if (scheduleDTO != null) {
            schedule.setActivities(scheduleDTO.getActivities());
            schedule.setDate(scheduleDTO.getDate());
        }
        return schedule;
    }

    private ScheduleDTO convertEntityToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        if (schedule != null) {
            scheduleDTO.setId(schedule.getId());
            scheduleDTO.setActivities(schedule.getActivities());
            scheduleDTO.setDate(schedule.getDate());
            scheduleDTO.setEmployeeIds(schedule.getEmployeeIds().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setPetIds(schedule.getPetIds().stream().map(Pet::getId).collect(Collectors.toList()));
        }
        return scheduleDTO;
    }
}
