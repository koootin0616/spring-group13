package com.example.demo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class CategoryContoroller {
	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/addplan")
	public ModelAndView addUser(
			@RequestParam("name") String name,
			ModelAndView mv) {

		Category category = new Category(name);


		categoryRepository.saveAndFlush(category);

		List<Category>list= categoryRepository.findAll();

		mv.addObject("list",list);
		 mv.setViewName("");

		   return mv;
	}
}
