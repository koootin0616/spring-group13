package com.example.demo;

import java.sql.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EvaluationController {
	@Autowired
	HttpSession session;


	@Autowired
	private EvaluationRepository evaluationRepository;

	@RequestMapping("/fillo")
	public ModelAndView update(
			@RequestParam("code") int code,
			@RequestParam("ymd") Date ymd,
			@RequestParam("user_code") int usercode,
			@RequestParam("per") int per,
			@RequestParam("reflection") String reflection,
			@RequestParam("improvement") String improvement,
			ModelAndView mv) {

		Evaluation evaluation = new Evaluation(code, ymd, usercode, per, reflection,improvement);

		evaluationRepository.saveAndFlush(evaluation);


		mv.setViewName("main");

		return mv;
	}
}
