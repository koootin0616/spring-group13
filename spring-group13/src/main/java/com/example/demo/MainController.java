package com.example.demo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
public class MainController {
	@Autowired
	HttpSession session;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@RequestMapping("/fill")
	public ModelAndView fill(ModelAndView mv) {

		mv.setViewName("fillout1st");

		return mv;
	}

	@RequestMapping("/addsche")
	public ModelAndView addschedule(
			ModelAndView mv) {

		mv.setViewName("addSchedule");

		return mv;
	}

	@RequestMapping("/ev")
	public ModelAndView evaluation(ModelAndView mv) {

		mv.setViewName("evaluation1st");

		return mv;
	}

	@PostMapping("/update")
	public ModelAndView update(
			@RequestParam(name = "code") int code,
			ModelAndView mv) {
		Schedule schedule = null;

		Optional<Schedule> detail = scheduleRepository.findById(code);

		if (detail.isEmpty() == false) { //レコードがあれば
			schedule = detail.get(); //レコードを取得する
		}

		mv.addObject("schedule", schedule);

		mv.setViewName("update");

		return mv;
	}


	@RequestMapping("/search")
	public ModelAndView search(
			@RequestParam(name = "categoryname") String name,
			ModelAndView mv) {
		User user= (User)session.getAttribute("userInfo");
		Category category = null;
		Optional<Category> detail = categoryRepository.findByName(name);
		if (detail.isEmpty() == false) { //レコードがあれば
			category = detail.get(); //レコードを取得する
		}
		List<Schedule> schedule = scheduleRepository.findByUsercodeAndCategorycode(user.getCode(), category.getCode());

		mv.addObject("schedule", schedule);
		session.setAttribute("categoryCounter", 10);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 1);
		session.setAttribute("categorySortCode",category.getCode());
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/week")
	public ModelAndView week(ModelAndView mv) {
		User user= (User)session.getAttribute("userInfo");
		List<Schedule> schedule = scheduleRepository.findByUsercode(user.getCode());
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
					&& (datetime_date - datetime_now) >= 0) {
				list.add(sche);
			}
		}
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 10);
		session.setAttribute("now", datetime_now);
		mv.addObject("schedule", list);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/today")
	public ModelAndView today(ModelAndView mv) {
		User user= (User)session.getAttribute("userInfo");
		List<Schedule> schedule = scheduleRepository.findByUsercode(user.getCode());
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
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 10);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 1);
		session.setAttribute("now", datetime_now);
		mv.addObject("schedule",list);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/tomorrow")
	public ModelAndView tomorrow(ModelAndView mv) {
		User user= (User)session.getAttribute("userInfo");
		List<Schedule> schedule = scheduleRepository.findByUsercode(user.getCode());
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
			if ((datetime_date - datetime_now) / one_date_time == 1) {
				list.add(sche);
			}
		}
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 10);
		session.setAttribute("weekCounter", 1);
		session.setAttribute("now", datetime_now);
		mv.addObject("schedule", list);
		mv.setViewName("main");

		return mv;
	}

}
