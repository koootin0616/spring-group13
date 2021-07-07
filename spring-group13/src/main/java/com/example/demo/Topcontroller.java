package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Topcontroller {
	//http://localhost:8080/top
	@RequestMapping("/top")
	public ModelAndView items(ModelAndView mv) {
		//Userテーブルから全ての全レコードを獲得


		mv.setViewName("top");

		return mv;
	}
}
