package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TopController {
	//http://localhost:8080/
	@RequestMapping("/")
	public ModelAndView top(ModelAndView mv) {
		//Userテーブルから全ての全レコードを獲得


		mv.setViewName("top");

		return mv;
	}

	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv) {

		mv.setViewName("login");

		return mv;
	}

	@RequestMapping("/signup")
	public ModelAndView signup(ModelAndView mv) {

		mv.setViewName("signup");

		return mv;
	}


}
