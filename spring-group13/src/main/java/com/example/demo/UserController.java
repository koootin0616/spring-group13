package com.example.demo;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	@Autowired
	HttpSession session;

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/loggedin")
	public ModelAndView login(
			@RequestParam(name = "id", defaultValue = "") String id,
			@RequestParam(name = "password", defaultValue = "") String password,
			ModelAndView mv) {
		User user = null;
		Optional<User> detail = userRepository.findById(id);

		if (id.equals("") || password.equals("")) {
			mv.addObject("message", "IDとパスワードを入力してください");
			mv.setViewName("login");
			return mv;
		}

		if (detail.isEmpty()) {
			mv.addObject("message", "入力されたIDは登録されていません");
			mv.setViewName("login");
			return mv;
		}else {
			user=detail.get();
		}

		if(!(password.equals(user.getPassword()))) {
			mv.addObject("message", "入力されたパスワードは間違えています");
			mv.setViewName("login");
		}else {
			session.setAttribute("userInfo", user);

			mv.setViewName("top");
		}

		return mv;
	}
}
