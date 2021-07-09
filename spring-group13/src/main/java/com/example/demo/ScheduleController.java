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

	@Autowired
	private CategoryRepository categoryRepository;

	@RequestMapping("/fill")
	public ModelAndView fill(ModelAndView mv) {

		mv.setViewName("fillout");

		return mv;
	}

	@PostMapping("/fill")
	public ModelAndView schedule(
			@RequestParam("name") String name,
			@RequestParam("jikan") Time jikan,
			@RequestParam("contents") String contents,
			@RequestParam("category_code") int category_code,
			ModelAndView mv) {



		mv.setViewName("main");

		return mv;
	}


	@RequestMapping("/ev")
	public ModelAndView evaluation(ModelAndView mv) {

		mv.setViewName("evaluation");

		return mv;
	}

	@RequestMapping("/addsche")
	public ModelAndView addschedule(ModelAndView mv) {

		List<Schedule> schedule = scheduleRepository.findAll();
		List<Category> category = categoryRepository.findAll();

		mv.setViewName("addSchedule");
		mv.addObject("list_category",category);

		mv.addObject("list_schedule",schedule);

		mv.setViewName("addschedule");

		return mv;
	}
	@PostMapping("/addsche")
	public ModelAndView update(
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

}
