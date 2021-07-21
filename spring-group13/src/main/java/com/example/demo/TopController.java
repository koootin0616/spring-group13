package com.example.demo;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TopController {

	@Autowired
	HttpSession session;

	@Autowired
	private ScheduleRepository scheduleRepository;

	//http://localhost:8080/addplan
	@RequestMapping("/")
	public ModelAndView top(ModelAndView mv) {
		//Userテーブルから全ての全レコードを獲得


		mv.setViewName("top");

		return mv;
	}

	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv) {
		LocalDate today = LocalDate.now();
		Date now = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
		long datetime_now = now.getTime();

		User user = (User)session.getAttribute("userInfo");

		if(user!=null) {
			List<Schedule> schedule=scheduleRepository.findByUsercode(user.getCode());
			mv.addObject("schedule", schedule);
			mv.setViewName("main");
			return mv;
		}
		session.setAttribute("now", datetime_now);
		mv.setViewName("login");

		return mv;
	}

	@RequestMapping("/signup")
	public ModelAndView signup(ModelAndView mv) {
		LocalDate today = LocalDate.now();
		Date now = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
		long datetime_now = now.getTime();


		session.setAttribute("now", datetime_now);
		mv.setViewName("signup");

		return mv;
	}

}
