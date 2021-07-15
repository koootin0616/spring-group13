package com.example.demo;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
			@RequestParam(name = "ymd", defaultValue="") Date ymd,
			@RequestParam("achieved") int achieved,
			@RequestParam("notachieved") int notachieved,
			@RequestParam("reflection") String reflection,
			@RequestParam("improvement") String improvement,
			ModelAndView mv) {
		int per = achieved * 100 / (achieved + notachieved);
		User user = (User)session.getAttribute("userInfo");
		List<Evaluation> eva_list=evaluationRepository.findAll();
		for(Evaluation detail:eva_list) {
			if(ymd.equals(detail.getYmd())){
				mv.addObject("message","その日付の自己評価は既に登録されています");
			}else {
				Evaluation evaluation = new Evaluation(ymd, user.getCode(), achieved, notachieved, per, reflection,improvement);

				evaluationRepository.saveAndFlush(evaluation);
				mv.addObject("message","自己評価を登録しました");
			}
		}
		List<Schedule> list = scheduleRepository.findByUsercode(user.getCode());

		mv.addObject("schedule",list);
		mv.setViewName("main");


		return mv;
	}

	@RequestMapping("/evaluationmain")
	public ModelAndView evaluationmain(
			@RequestParam("ymd") Date ymd,
			ModelAndView mv) {

		User user = (User)session.getAttribute("userInfo");

		Optional<Evaluation>detail = evaluationRepository.findByUsercodeAndYmd(user.getCode(),ymd);
		List<Schedule>list=scheduleRepository.findByUsercodeAndYmd(user.getCode(), ymd);
		Evaluation evaluation = null;

		if (detail.isEmpty()) {
			mv.addObject("message", "その日付の自己評価は登録されていません");
			mv.setViewName("evaluation1st");
			return mv;
		} else {
			evaluation = detail.get();
		}

		mv.addObject("list",evaluation);
		mv.addObject("schedule",list);
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
