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
	List<Schedule> findByUsercodeOrderByJikanDesc(int usercode);
	List<Schedule> findByUsercodeOrderByYmdAscJikanAsc(int usercode);
	List<Schedule> findByUsercodeOrderByYmdDescJikanDesc(int usercode);
	List<Schedule> findByUsercodeOrderByYmdAsc(int usercode);
	List<Schedule> findByUsercodeOrderByYmdDesc(int usercode);
	List<Schedule> findByUsercodeAndYmdOrderByJikanAsc(int usercode,Date ymd);
	List<Schedule> findByUsercodeAndYmdOrderByJikanDesc(int usercode,Date ymd);
	List<Schedule> findByUsercodeAndCategorycodeOrderByJikanAsc(int usercode, int categorycode);
	List<Schedule> findByUsercodeAndCategorycodeOrderByJikanDesc(int usercode, int categorycode);
	List<Schedule> findByUsercodeAndCategorycodeOrderByYmdAsc(int usercode, int categorycode);
	List<Schedule> findByUsercodeAndCategorycodeOrderByYmdDesc(int usercode, int categorycode);

}
