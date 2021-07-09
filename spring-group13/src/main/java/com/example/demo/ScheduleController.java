package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScheduleController {
	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping("/fillout")
	public ModelAndView items(
			@RequestParam("name") String name,
			@RequestParam("jikan") int jikan,
			@RequestParam("contents") String contents,
			@RequestParam("category_code") int category_code,
			ModelAndView mv) {

		Schedule schedule = new Schedule(name,jikan,contents,category_code);

		scheduleRepository.saveAndFlush(schedule);

		List<Schedule>list= scheduleRepository.findAll();

		mv.addObject("list",list);

		mv.setViewName("fillout");

		return mv;
	}
}
