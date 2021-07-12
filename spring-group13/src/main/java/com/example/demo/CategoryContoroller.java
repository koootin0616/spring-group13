package com.example.demo;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryContoroller {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;


	@RequestMapping("/updateCategory")
	public ModelAndView update(ModelAndView mv) {

		List<Category> category=categoryRepository.findAll();


		mv.addObject("list", category);
		mv.setViewName("update");

		return mv;
	}
	@RequestMapping("/addCategory")
	public ModelAndView addCategory(
			@RequestParam(name="addCategory") String name,
			ModelAndView mv) {

		if(name.equals("")) {

		}else {
			Category category = new Category(name);
			categoryRepository.saveAndFlush(category);
		}

		List<Schedule> schedule=scheduleRepository.findAll();
		List<Category> list = categoryRepository.findAll();

		mv.addObject("schedule",schedule);
		mv.addObject("category", list);
		mv.setViewName("main");

		return mv;
	}
	@RequestMapping("/deleteSchedule")
	public ModelAndView delete(
			@RequestParam("name") String name,
			@RequestParam("jikan") Time jikan,
			@RequestParam("importance") String importance,
			@RequestParam("contents") String contents,
			ModelAndView mv) {

		Schedule deleteSchedule = new Schedule(name,jikan,importance,contents);

		scheduleRepository.saveAndFlush(deleteSchedule);

		List<Schedule>list= scheduleRepository.findAll();

			//List<Category> category=categoryRepository.findAll();


		mv.addObject("list", list);
		mv.setViewName("delete");

		return mv;
	}
}
