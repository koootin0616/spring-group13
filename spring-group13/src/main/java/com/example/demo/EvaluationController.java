package com.example.demo;



import java.sql.Date;
import java.util.ArrayList;
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

	@RequestMapping("/filledOut")
	public ModelAndView update(
			@RequestParam(name ="ymd") String ymd,
			@RequestParam(name="achieved") int achieved,
			@RequestParam(name="notachieved") int notachieved,
			@RequestParam("reflection") String reflection,
			@RequestParam("improvement") String improvement,
			ModelAndView mv) {
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		date = Date.valueOf(ymd);
		int per = 0;
		User user = (User)session.getAttribute("userInfo");
		List<Evaluation> eva_list=evaluationRepository.findAll();
		List<Schedule> list = new ArrayList<>();
		for(Evaluation detail:eva_list) {

			if(date.equals(detail.getYmd())){
				mv.addObject("message","その日付の自己評価は既に登録されています");
				mv.setViewName("fillout1st");
				return mv;
			}
		}

		if(achieved==0&&notachieved==00) {
			list = scheduleRepository.findByUsercodeAndYmd(user.getCode(),date);
			mv.addObject("schedule",list);
			mv.addObject("ymd",ymd);
			mv.addObject("message","未記入項目があります");
			mv.setViewName("fillout");
			return mv;
		}else {
			per = achieved * 100 / (achieved + notachieved);
		}


		if(reflection.equals("")||improvement.equals("")){
			list = scheduleRepository.findByUsercodeAndYmd(user.getCode(),date);
			mv.addObject("schedule",list);
			mv.addObject("ymd",ymd);
			mv.addObject("message","未記入項目があります");
			mv.setViewName("fillout");
			return mv;
		}else {
			Evaluation evaluation = new Evaluation(date, user.getCode(), achieved, notachieved, per, reflection,improvement);

			evaluationRepository.saveAndFlush(evaluation);
			mv.addObject("message","自己評価を登録しました");
		}
		List<Schedule> schedule = new ArrayList<>();
		list = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
		long now = (Long) session.getAttribute("now");
		long datetime_date = 0;
		for (Schedule sche : list) {
			date = sche.getYmd();
			datetime_date = date.getTime();
			if ((datetime_date - now) >= 0) {
				schedule.add(sche);
			}
		}

		mv.addObject("schedule",schedule);
		mv.setViewName("main");


		return mv;
	}

	@RequestMapping("/evaluationmain")
	public ModelAndView evaluationmain(
			@RequestParam("ymd") String ymd,
			ModelAndView mv) {
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		if (ymd.equals("")) {
			mv.addObject("message", "日付を選択してください");
			mv.setViewName("evaluation1st");
			return mv;
		}
		date = Date.valueOf(ymd);

		User user = (User)session.getAttribute("userInfo");

		Optional<Evaluation>detail = evaluationRepository.findByUsercodeAndYmd(user.getCode(),date);
		List<Schedule>list=scheduleRepository.findByUsercodeAndYmd(user.getCode(), date);
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
			@RequestParam("ymd") String ymd,
			ModelAndView mv) {
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		int count = 0;
		if (ymd.equals("")) {
			mv.addObject("message", "日付を選択してください");
			mv.setViewName("fillout1st");
			return mv;
		}
		date = Date.valueOf(ymd);

		User user = (User)session.getAttribute("userInfo");

		List<Schedule> list = scheduleRepository.findByUsercodeAndYmd(user.getCode(),date);

		for(Schedule sche:list) {
			count++;
		}

		mv.addObject("schedule",list);
		mv.addObject("count",count);

		mv.addObject("ymd",ymd);
		mv.setViewName("fillout");

		return mv;
	}

}
