package com.example.demo;

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

		User user = (User)session.getAttribute("userInfo");

		if(user!=null) {
			List<Schedule> schedule=scheduleRepository.findByUsercode(user.getCode());
			mv.addObject("schedule", schedule);
			mv.setViewName("main");
			return mv;
		}

		mv.setViewName("login");

		return mv;
	}

	@RequestMapping("/signup")
	public ModelAndView signup(ModelAndView mv) {

		mv.setViewName("signup");

		return mv;
	}
	@RequestMapping("/addplan")
	public ModelAndView addplan(ModelAndView mv) {

		mv.setViewName("addplan");

		return mv;
	}
	@RequestMapping("/update")
	public ModelAndView update(ModelAndView mv) {

		mv.setViewName("update");

		return mv;
	}

	@RequestMapping("/evaluation")
	public ModelAndView evaluation(ModelAndView mv) {

		mv.setViewName("evaluation");

		return mv;
	}



}
