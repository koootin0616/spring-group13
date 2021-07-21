package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		User user = (User)session.getAttribute("userInfo");
		List<Category> list0 = categoryRepository.findByUsercode(0);
		List<Category> list1 = categoryRepository.findByUsercode(user.getCode());
		List<Category> category = new ArrayList<>();
		int count = 0;

		if (name.equals("")) {
			mv.addObject("message","カテゴリー名を入力してください");
		} else {
			for(Category cate0 : list0) {
				if(name.equals(cate0.getName())) {
					count = 1;
				}
			}
			for(Category cate1 : list1) {
				if(name.equals(cate1.getName())) {
					count = 1;
				}
			}
			if(count==0) {
				Category category_detail = new Category(user.getCode(),name);
				categoryRepository.saveAndFlush(category_detail);
				list1 = categoryRepository.findByUsercode(user.getCode());
				mv.addObject("message","カテゴリーを追加しました");
			}else {
				mv.addObject("message","登録済みのカテゴリーです");
			}
		}

		for(Category category0 : list0) {
			category.add(category0);
		}
		for(Category category1 : list1) {
			category.add(category1);
		}

		session.setAttribute("category", category);

		mv.setViewName("deleteCategory");

		return mv;
	}

	//削除一覧
	@RequestMapping("/deleteCate")
	public ModelAndView delete(ModelAndView mv) {

		List<Category> category = categoryRepository.findAll();

		mv.addObject("list", category);
		mv.setViewName("deleteCategory");

		return mv;
	}

	//削除一覧から削除
	@RequestMapping("/deleteCatego")
	public ModelAndView deleteCategory(
			@RequestParam("code") int code,
			ModelAndView mv) {
		List<Schedule> schedule = scheduleRepository.findAll();
		User user = (User)session.getAttribute("userInfo");
		List<Category> list0 = categoryRepository.findByUsercode(0);
		List<Category> list1 = new ArrayList<>();
		List<Category> category = new ArrayList<>();
		int flag = 0;
		Optional<Category> cate = categoryRepository.findById(code);
		Category category_detail=cate.get();
		for (Schedule sche : schedule) {
			if (sche.getCategorycode() == code) {
				flag = 1;
			}
		}

		if (flag == 0) {
			categoryRepository.deleteById(code);
			list1 = categoryRepository.findByUsercode(user.getCode());
			mv.addObject("message", "削除しました");
		} else {
			mv.addObject("message", "予定が登録されているため、削除できません");
			if(category_detail.getUsercode()==0) {
				mv.addObject("message","削除できないカテゴリーです");
			}
		}

		for(Category category0 : list0) {
			category.add(category0);
		}
		for(Category category1 : list1) {
			category.add(category1);
		}

		session.setAttribute("category", category);
		mv.setViewName("deleteCategory");

		return mv;
	}

}
