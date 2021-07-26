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

	//http://localhost:8080/loggedin
	//メイン画面への遷移
	@RequestMapping("/loggedin")
	public ModelAndView login(
			@RequestParam(name = "id", defaultValue = "") String id,
			@RequestParam(name = "password", defaultValue = "") String password,
			ModelAndView mv) {
		User user = null;
		Optional<User> user_detail = userRepository.findById(id);

		List<Category> category1 = categoryRepository.findByUsercode(0);
		List<Category> category2 = new ArrayList<>();
		List<Category> category_detail = new ArrayList<>();
		List<Schedule> schedule_detail = new ArrayList<>();

		if (id.equals("") || password.equals("")) { //ID、パスワードの項目が空の場合の処理
			mv.addObject("message", "IDとパスワードを入力してください");
			mv.setViewName("login");
			return mv;
		}

		if (user_detail.isEmpty()) { //入力されたIDがデータベースにない場合の処理
			mv.addObject("message", "IDかパスワードが間違っています");
			mv.setViewName("login");
			return mv;
		} else {
			user = user_detail.get();
		}

		if (!(password.equals(user.getPassword()))) { //入力されたIDに対して、パスワードが間違っている場合の処理
			mv.addObject("message", "IDかパスワードが間違っています");
			mv.setViewName("login");
		} else { //ID、パスワードが正しく入力された場合の処理
			List<Schedule> schedule = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : schedule) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) >= 0) {
					schedule_detail.add(sche);
				}
			}
			category2 = categoryRepository.findByUsercode(user.getCode());

			//全ユーザ共通のカテゴリー情報と特定のユーザのカテゴリー情報をまとめる
			for (Category list1 : category1) {
				category_detail.add(list1);
			}
			for (Category list2 : category2) {
				category_detail.add(list2);
			}

			session.setAttribute("userInfo", user);
			session.setAttribute("category", category_detail);
			session.setAttribute("categoryCounter", 1);
			session.setAttribute("todayCounter", 1);
			session.setAttribute("tomorrowCounter", 1);
			session.setAttribute("weekCounter", 1);
			mv.addObject("schedule", schedule);
			mv.setViewName("main");
		}

		return mv;
	}

	//http://localhost:8080/signedup
	//メイン画面への遷移
	@RequestMapping("/signedup")
	public ModelAndView signup(
			@RequestParam(name = "id", defaultValue = "") String id,
			@RequestParam(name = "password", defaultValue = "") String password,
			ModelAndView mv) {
		Optional<User> user_detail = userRepository.findById(id);

		List<Category> category_detail = categoryRepository.findAll();

		if (id.equals("") || password.equals("")) { //ID、パスワードの項目が空の場合の処理
			mv.addObject("message", "IDとパスワードを入力してください");
			mv.setViewName("signup");
			return mv;
		}

		if (!(user_detail.isEmpty())) { //既にデータベースにあるIDを入力した場合の処理
			mv.addObject("message", "入力されたIDは登録されています");
			mv.setViewName("signup");
			return mv;
		}

		User user = new User(id, password);

		userRepository.saveAndFlush(user);
		session.setAttribute("userInfo", user);
		session.setAttribute("category", category_detail);
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 1);
		mv.addObject("message", "登録が完了しました");
		mv.setViewName("main");
		return mv;
	}

	//http://localhost:8080/logout
	//トップ画面への遷移
	@RequestMapping("/logout")
	public ModelAndView logout(ModelAndView mv) {
		session.invalidate();

		mv.setViewName("top");
		return mv;
	}
}
