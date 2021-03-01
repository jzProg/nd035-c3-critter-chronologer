package com.udacity.jdnd.course3.critter.schedule.dao;

import com.udacity.jdnd.course3.critter.schedule.model.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

}
