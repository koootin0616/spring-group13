package com.example.demo;

import java.util.List;
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
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@RequestMapping("/loggedin")
	public ModelAndView login(
			@RequestParam(name = "id", defaultValue = "") String id,
			@RequestParam(name = "password", defaultValue = "") String password,
			ModelAndView mv) {
		User user = null;
		Optional<User> detail = userRepository.findById(id);

		List<Category> category_detail=categoryRepository.findAll();
		List<Schedule> schedule_detail=scheduleRepository.findAll();

		if (id.equals("") || password.equals("")) {
			mv.addObject("message", "IDとパスワードを入力してください");
			mv.setViewName("login");
			return mv;
		}

		if (detail.isEmpty()) {
			mv.addObject("message", "入力されたIDは登録されていません");
			mv.setViewName("login");
			return mv;
		} else {
			user = detail.get();
		}

		if (!(password.equals(user.getPassword()))) {
			mv.addObject("message", "入力されたパスワードは間違えています");
			mv.setViewName("login");
		} else {
			session.setAttribute("userInfo", user);
			mv.addObject("category",category_detail);
			mv.addObject("schedule",schedule_detail);
			mv.setViewName("main");
		}

		return mv;
	}

	@RequestMapping("/signedup")
	public ModelAndView signup(
			@RequestParam(name = "id", defaultValue = "") String id,
			@RequestParam(name = "password", defaultValue = "") String password,
			ModelAndView mv) {
		Optional<User> detail = userRepository.findById(id);

		if (id.equals("") || password.equals("")) {
			mv.addObject("message", "IDとパスワードを入力してください");
			mv.setViewName("signup");
			return mv;
		}

		if (!(detail.isEmpty())) {
			mv.addObject("message", "入力されたIDは登録されています");
			mv.setViewName("signup");
			return mv;
		}

		User user = new User(id, password);
		userRepository.saveAndFlush(user);

		mv.addObject("message","登録が完了しました");
		mv.setViewName("main");
		return mv;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(ModelAndView mv) {
		session.invalidate();

		mv.setViewName("top");
		return mv;
	}
}
