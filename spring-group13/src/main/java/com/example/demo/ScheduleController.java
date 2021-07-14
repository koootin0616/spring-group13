package com.example.demo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ScheduleController {
	@Autowired
	HttpSession session;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@PostMapping("/addsche")
	public ModelAndView add(
			@RequestParam("category") String categoryname,
			@RequestParam("name") String name,
			@RequestParam("ymd") Date ymd,
			@RequestParam("jikan") String jikan,
			@RequestParam("importance") String importance,
			@RequestParam("contents") String contents,
			ModelAndView mv) {
		User user= (User)session.getAttribute("userInfo");
		Time time = Time.valueOf(jikan+":00");
		Category category = null;
		Optional<Category> categoryDetail=categoryRepository.findByName(categoryname);
		category=categoryDetail.get();
		//登録するエンティティのインスタンスを生成

		Schedule schedule = new Schedule(user.getCode(), category.getCode(), name, ymd, time, importance, contents);


		//ItemエンティティをItemテーブルに登録
		scheduleRepository.saveAndFlush(schedule);

		List<Schedule>list= scheduleRepository.findByUsercode(user.getCode());

		mv.addObject("schedule",list);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/updateSchedule")
	public ModelAndView update(
			@RequestParam("code") int code,
			@RequestParam("categorycode") int categorycode,
			@RequestParam("name") String name,
			@RequestParam("ymd") Date ymd,
			@RequestParam("jikan") String jikan,
			@RequestParam("importance") String importance,
			@RequestParam("contents") String contents,
			ModelAndView mv) {
		User user= (User)session.getAttribute("userInfo");
		Time time = Time.valueOf(jikan+":00");
		Schedule schedule = new Schedule(code, user.getCode(), categorycode, name, ymd, time, importance, contents);

		scheduleRepository.saveAndFlush(schedule);

		List<Schedule>list= scheduleRepository.findByUsercode(user.getCode());

		mv.addObject("schedule",list);
		 mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(
			@RequestParam("code")int code,
			ModelAndView mv) {
		User user= (User)session.getAttribute("userInfo");
		scheduleRepository.deleteById(code);

		List<Schedule> schedule = scheduleRepository.findByUsercode(user.getCode());

		mv.addObject("schedule",schedule);
		mv.setViewName("main");

		return mv;
	}

}
