package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

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

	@Autowired
	HttpSession session;

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

		session.setAttribute("category", list);

		mv.addObject("schedule",schedule);
		mv.setViewName("main");

		return mv;
	}
	//削除一覧
	@RequestMapping("/deleteCate")
	public ModelAndView delete(
			@RequestParam(name="categorycode") int categorycode,
						ModelAndView mv) {

			List<Category> category = categoryRepository.findAll();


			mv.addObject("list",category);
		    mv.setViewName("deleteCategory");

		return mv;
	}

}
