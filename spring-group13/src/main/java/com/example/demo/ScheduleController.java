package com.example.demo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScheduleController {
	@Autowired
	private ScheduleRepository scheduleRepository;

	@PostMapping("/addsche")
	public ModelAndView add(
			@RequestParam("name") String name,
			@RequestParam("ymd") Date ymd,
			@RequestParam("jikan") Time jikan,
			@RequestParam("importance") String importance,
			@RequestParam("contents") String contents,
			ModelAndView mv) {
		//登録するエンティティのインスタンスを生成
		Schedule schedule = new Schedule(name,jikan,importance,contents);

		//ItemエンティティをItemテーブルに登録
		scheduleRepository.saveAndFlush(schedule);

		List<Schedule>list= scheduleRepository.findAll();

		mv.addObject("list",list);
		 mv.setViewName("main");

		   return mv;
	}

	@RequestMapping("/updateSchedule")
	public ModelAndView update(
			@RequestParam("code") int code,
			@RequestParam("user_code") int user_code,
			@RequestParam("category_code") int category_code,
			@RequestParam("name") String name,
			@RequestParam("ymd") Date ymd,
			@RequestParam("jikan") Time jikan,
			@RequestParam("importance") String importance,
			@RequestParam("contents") String contents,
			ModelAndView mv) {
		Schedule schedule = new Schedule(code, user_code, category_code, name, ymd, jikan, importance, contents);

		scheduleRepository.saveAndFlush(schedule);

		List<Schedule>list= scheduleRepository.findAll();

		mv.addObject("schedule",list);
		 mv.setViewName("main");

		return mv;
	}



}
