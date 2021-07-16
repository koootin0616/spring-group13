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
			@RequestParam(name = "addCategory") String name,
			ModelAndView mv) {
		List<Category> list = categoryRepository.findAll();
		User user = (User)session.getAttribute("userInfo");
		int count = 0;

		if (name.equals("")) {
			mv.addObject("message","カテゴリー名を入力してください");
		} else {
			for(Category cate : list) {
				if(name.equals(cate.getName())) {
					count = 1;
				}else {

				}
			}
			if(count==0) {
				Category category = new Category(name);
				categoryRepository.saveAndFlush(category);
				mv.addObject("message","カテゴリーを追加しました");
			}
		}

		list = categoryRepository.findAll();

		List<Schedule> schedule = scheduleRepository.findByUsercode(user.getCode());

		session.setAttribute("category", list);

		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}

	//削除一覧
	@RequestMapping("/deleteCate")
	public ModelAndView delete(	ModelAndView mv) {

		List<Category> category = categoryRepository.findAll();

		mv.addObject("list", category);
		mv.setViewName("deleteCategory");

		return mv;
	}
	//削除一覧から削除
	@RequestMapping("/deleteCatego")
	public ModelAndView deleteCategory(
			@RequestParam("code")int code,
			ModelAndView mv) {

		categoryRepository.deleteById(code);

		List<Category> list = categoryRepository.findAll();

		mv.addObject("list",list);
		mv.setViewName("deleteCategory");

		return mv;
	}
	//削除一覧からメインページに戻る
	@RequestMapping("/mainreturn")
	public ModelAndView mainreturn(
			ModelAndView mv) {
		User user = (User)session.getAttribute("userInfo");

		List<Category> category = categoryRepository.findAll();
		List<Schedule> list = scheduleRepository.findByUsercode(user.getCode());

		session.setAttribute("category", category);

		mv.addObject("schedule",list);
		mv.setViewName("main");

		return mv;
	}

}
