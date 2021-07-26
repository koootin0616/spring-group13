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

	//http://localhost:8080/
	//トップ画面へ遷移
	@RequestMapping("/")
	public ModelAndView top(ModelAndView mv) {



		mv.setViewName("top");

		return mv;
	}

	//http://localhost:8080/login
	//ログイン画面への遷移
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv) {
		//今日の日付の取得
		LocalDate today = LocalDate.now();
		Date now = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
		long datetime_now = now.getTime();

		User user = (User)session.getAttribute("userInfo");

		//Userのセッションオブジェクトがあるとき、メイン画面へ遷移
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

	//http://localhost:8080/signup
	//ユーザの新規登録画面への遷移
	@RequestMapping("/signup")
	public ModelAndView signup(ModelAndView mv) {
		//今日の日付の取得
		LocalDate today = LocalDate.now();
		Date now = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
		long datetime_now = now.getTime();


		session.setAttribute("now", datetime_now);
		mv.setViewName("signup");

		return mv;
	}

}
