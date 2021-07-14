package com.example.demo;

import java.sql.Date;
import java.util.List;

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
	private ScheduleRepository scheduleRepository;

	@Autowired
	private EvaluationRepository evaluationRepository;

	@RequestMapping("/fillo")
	public ModelAndView update(
			@RequestParam("ymd") Date ymd,
			@RequestParam("per") int per,
			@RequestParam("reflection") String reflection,
			@RequestParam("improvement") String improvement,
			ModelAndView mv) {
		User user = (User)session.getAttribute("userInfo");

		Evaluation evaluation = new Evaluation( ymd,user.getCode(),per, reflection,improvement);

		evaluationRepository.saveAndFlush(evaluation);


		mv.setViewName("main");


		return mv;
	}

	@RequestMapping("/evaluationmain")
	public ModelAndView evaluationmain(
			@RequestParam("ymd") Date ymd,
			ModelAndView mv) {

//		User user = (User)session.getAttribute("userInfo");
//
//		List<Evaluation> list = evaluationRepository.findByUsercodeAndYmd(user.getCode(),ymd);




		Evaluation evaluation = null;
//		Optional<Evaluation> detail = evaluationRepository.findByYmd(ymd);
//
//		if (detail.isEmpty()) {
//			mv.setViewName("main");
//			return mv;
//		} else {
//			evaluation = detail.get();
//		}
//
//		mv.addObject("list",evaluation);

		mv.addObject("ymd",ymd);
		mv.setViewName("evaluation");


		return mv;
	}
	@RequestMapping("/fillmain")
	public ModelAndView fillmain(
			@RequestParam("ymd") Date ymd,
			ModelAndView mv) {

		User user = (User)session.getAttribute("userInfo");

		List<Schedule> list = scheduleRepository.findByUsercodeAndYmd(user.getCode(),ymd);



		mv.addObject("schedule",list);

		mv.addObject("ymd",ymd);
		mv.setViewName("fillout");

		return mv;
	}

}
