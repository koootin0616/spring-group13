package com.example.demo;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation,Integer>{
	Optional<Evaluation> findByUsercodeAndYmd(int usercode,Date ymd);
	List<Evaluation> findByUsercode(int usercode);
}
