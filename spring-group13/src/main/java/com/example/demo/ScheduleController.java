package com.example.demo;

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
			@RequestParam("jikan") Time jikan,
			@RequestParam("importance") String importance,
			@RequestParam("contents") String contents,
			ModelAndView mv) {
		//登録するエンティティのインスタンスを生成
		Schedule addschedule = new Schedule(name,jikan,importance,contents);

		//ItemエンティティをItemテーブルに登録
		scheduleRepository.saveAndFlush(addschedule);

		List<Schedule>list= scheduleRepository.findAll();

		mv.addObject("list",list);
		 mv.setViewName("main");

		   return mv;
	}

	@RequestMapping("/updateSchedule")
	public ModelAndView update(
			ModelAndView mv) {
		return mv;
	}



}
