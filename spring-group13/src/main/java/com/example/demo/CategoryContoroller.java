package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryContoroller {
	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/addschedule")
	public ModelAndView addplan(ModelAndView mv) {

		List<Category> category=categoryRepository.findAll();

		mv.setViewName("addSchedule");
		mv.addObject("list",category);
		return mv;
	}


	@PostMapping("/update")
	public ModelAndView update(

			ModelAndView mv) {

		List<Category> category=categoryRepository.findAll();


		mv.addObject("list", category);
		mv.setViewName("update");

		return mv;
	}
}
