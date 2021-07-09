package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryContoroller {
	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/addschedule")
	public ModelAndView addplan(
			//@RequestParam("name") String name,
			ModelAndView mv) {

		//		Category category = new Category(name);
		//
		//
		//		categoryRepository.saveAndFlush(category);
		//
		//		List<Category>list= categoryRepository.findAll();
		//
		//		mv.addObject("list",list);
		mv.setViewName("addSchedule");

		return mv;
	}

	@PostMapping("/update")
	public ModelAndView update(
			//@RequestParam("name") String name,
			ModelAndView mv) {

//		Category category = new Category(name);
//
//		categoryRepository.saveAndFlush(category);
//
//		List<Category> list = categoryRepository.findAll();
//
//		mv.addObject("list", list);
		mv.setViewName("update");

		return mv;
	}
}
