package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@RequestMapping("/fill")
	public ModelAndView fill(ModelAndView mv) {

		mv.setViewName("fillout");

		return mv;
	}

	@RequestMapping("/addsche")
	public ModelAndView addschedule(
			@RequestParam("user_code")int user_code,
			ModelAndView mv) {

		List<Category> category=categoryRepository.findAll();

		mv.addObject("user_code", user_code);
		mv.addObject("category", category);

		mv.setViewName("addSchedule");



		return mv;
	}

	@RequestMapping("/ev")
	public ModelAndView evaluation(ModelAndView mv) {

		mv.setViewName("evaluation");

		return mv;
	}

	@PostMapping("/update")
	public ModelAndView update(
			@RequestParam(name="code") int code,
			ModelAndView mv) {
		Schedule schedule = null;

		Optional<Schedule> detail = scheduleRepository.findById(code);
		List<Category> category=categoryRepository.findAll();

		if (detail.isEmpty() == false) { //レコードがあれば
			schedule = detail.get(); //レコードを取得する
		}

		mv.addObject("schedule", schedule);
		mv.addObject("category", category);

		mv.setViewName("update");

		return mv;
	}



}
