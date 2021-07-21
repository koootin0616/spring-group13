package com.example.demo;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
	List<Schedule> findByUsercode(int usercode);
	List<Schedule> findByUsercodeAndYmd(int usercode,Date ymd);
	List<Schedule> findByUsercodeAndCategorycode(int usercode, int categorycode);
	List<Schedule> findByUsercodeOrderByJikanAsc(int usercode);
	List<Schedule> findByUsercodeOrderByYmdAscJikanAsc(int usercode);
	List<Schedule> findByUsercodeAndCategorycodeOrderByYmdAscJikanAsc(int usercode, int cotegorycode);
}
