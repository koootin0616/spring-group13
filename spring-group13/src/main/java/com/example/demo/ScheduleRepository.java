package com.example.demo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
	List<Schedule> findByUsercode(int usercode);
	List<Schedule> findByCategorycode(int categorycode);
	List<Schedule> findByUsercodeAndYmd(int usercode,Date ymd);
}
