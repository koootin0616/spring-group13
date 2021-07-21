package com.example.demo;

import java.sql.Date;
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

		List<Category> category_detail = categoryRepository.findAll();
		List<Schedule> schedule = new ArrayList<>();


		if (id.equals("") || password.equals("")) {
			mv.addObject("message", "IDとパスワードを入力してください");
			mv.setViewName("login");
			return mv;
		}

		if (detail.isEmpty()) {
			mv.addObject("message", "IDかパスワードが間違っています");
			mv.setViewName("login");
			return mv;
		} else {
			user = detail.get();
		}

		if (!(password.equals(user.getPassword()))) {
			mv.addObject("message", "IDかパスワードが間違っています");
			mv.setViewName("login");
		} else {
			List<Schedule> list = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) >= 0) {
					schedule.add(sche);
				}
			}
			session.setAttribute("userInfo", user);
			session.setAttribute("category",category_detail);
			session.setAttribute("categoryCounter", 1);
			session.setAttribute("todayCounter", 1);
			session.setAttribute("tomorrowCounter", 1);
			session.setAttribute("weekCounter", 1);
			mv.addObject("schedule",schedule);
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

		List<Category> category_detail = categoryRepository.findAll();

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
		session.setAttribute("userInfo", user);
		session.setAttribute("category",category_detail);
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 1);
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
