package com.example.demo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
			@RequestParam("usercode") int usercode,
			ModelAndView mv) {

		List<Category> category = categoryRepository.findAll();

		mv.addObject("usercode", usercode);
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
			@RequestParam(name = "code") int code,
			ModelAndView mv) {
		Schedule schedule = null;

		Optional<Schedule> detail = scheduleRepository.findById(code);
		List<Category> category = categoryRepository.findAll();

		if (detail.isEmpty() == false) { //レコードがあれば
			schedule = detail.get(); //レコードを取得する
		}

		mv.addObject("schedule", schedule);
		mv.addObject("category", category);

		mv.setViewName("update");

		return mv;
	}
	@RequestMapping("/reset")
	public ModelAndView reset(
			ModelAndView mv) {

		List<Schedule> schedule=scheduleRepository.findAll();


		mv.addObject("schedule",schedule);

		mv.setViewName("main");



		return mv;
	}

	@RequestMapping("/search")
	public ModelAndView search(
			@RequestParam(name = "categoryname") String name,
			ModelAndView mv) {
		Category category = null;
		Optional<Category> detail = categoryRepository.findByName(name);
		if (detail.isEmpty() == false) { //レコードがあれば
			category = detail.get(); //レコードを取得する
		}
		List<Schedule> schedule = scheduleRepository.findByCategorycode(category.getCode());

		mv.addObject("schedule", schedule);

		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/weekschedule")
	public ModelAndView week(ModelAndView mv) {
		List<Schedule> schedule = scheduleRepository.findAll();
		LocalDate today = LocalDate.now();
		Date now = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date date = null;
		long datetime_now = now.getTime();
		long datetime_date = 0;
		long one_date_time = 1000 * 60 * 60 * 24;

		List<Schedule> list = new ArrayList<>();

		for (Schedule sche : schedule) {
			date = sche.getYmd();
			datetime_date = date.getTime();
			if ((datetime_date - datetime_now) / one_date_time < 7
					&& (datetime_date - datetime_now) / one_date_time >= 0) {
				list.add(sche);
			}
		}
		mv.addObject("schedule", list);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/todayschedule")
	public ModelAndView today(ModelAndView mv) {

		List<Schedule> schedule = scheduleRepository.findAll();
		LocalDate today = LocalDate.now();
		Date now = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date date = null;
		long datetime_now = now.getTime();
		long datetime_date = 0;
		List<Schedule> list = new ArrayList<>();


		for (Schedule sche : schedule) {
			date = sche.getYmd();
			datetime_date = date.getTime();
			if ((datetime_date - datetime_now) == 0) {
				list.add(sche);
			}
		}

		mv.addObject("schedule",list);
		mv.setViewName("main");

		return mv;
	}



}
