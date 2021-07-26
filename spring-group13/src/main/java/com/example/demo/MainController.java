package com.example.demo;

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

	@Autowired
	private EvaluationRepository evaluationRepository;

	//http://localhost:8080/mainreturn
	//メイン画面へ遷移
	@RequestMapping("/mainreturn")
	public ModelAndView mainreturn(
			ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");

		List<Schedule> schedule = new ArrayList<>();
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
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 1);
		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}

	//http://localhost:8080/fillout
	//自己評価記入画面へ遷移
	@RequestMapping("/fillout")
	public ModelAndView fill(ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		List<Schedule> schedule = scheduleRepository.findByUsercode(user.getCode());
		long now = (Long) session.getAttribute("now");
		Date date = null;
		long datetime_date = 0;
		List<Schedule> list = new ArrayList<>();
		int count = 0;

		for (Schedule sche : schedule) {
			date = sche.getYmd();
			datetime_date = date.getTime();
			if ((datetime_date - now) == 0) {
				list.add(sche);
				mv.addObject("ymd", sche.getYmd());
				count++;
			}
		}
		if (list.isEmpty()) {
			mv.addObject("message", "日付を選択してください");
			mv.setViewName("fillout1st");
		} else {
			mv.addObject("count", count);
			mv.addObject("schedule", list);
			mv.setViewName("fillout");
		}

		return mv;
	}

	//http://localhost:8080/addSchedule
	//予定追加画面へ遷移
	@RequestMapping("/addSchedule")
	public ModelAndView addschedule(
			ModelAndView mv) {

		mv.setViewName("addSchedule");

		return mv;
	}

	//http://localhost:8080/evaluation
	//自己評価確認画面へ遷移
	@RequestMapping("/evaluation")
	public ModelAndView evaluation(ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		List<Evaluation> evaluation = evaluationRepository.findByUsercode(user.getCode());
		long now = (Long) session.getAttribute("now");
		Date date = null;
		Date date1 = null;
		long datetime_date = 0;
		Evaluation detail = null;

		for (Evaluation eva : evaluation) {
			date = eva.getYmd();
			datetime_date = date.getTime();
			if ((datetime_date - now) == 0) {
				detail = eva;
				mv.addObject("ymd", eva.getYmd());
				date1 = eva.getYmd();
			}
		}
		List<Schedule> list1 = scheduleRepository.findByUsercodeAndYmd(user.getCode(), (java.sql.Date) date1);
		if (detail == null) {
			mv.addObject("message", "日付を選択してください");
			mv.setViewName("evaluation1st");
		} else {
			mv.addObject("schedule", list1);
			mv.addObject("list", detail);
			mv.setViewName("evaluation");
		}

		return mv;
	}

	//http://localhost:8080/update
	//予定変更画面へ遷移
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

	//http://localhost:8080/search
	//メイン画面へ遷移
	@RequestMapping("/search")
	public ModelAndView search(
			@RequestParam(name = "categoryname") String name,
			ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		Category category = null;
		Optional<Category> detail = categoryRepository.findByName(name);
		List<Schedule> schedule_detail = new ArrayList<>();

		if (detail.isEmpty() == false) { //レコードがあれば
			category = detail.get(); //レコードを取得する
		}
		List<Schedule> schedule = scheduleRepository.findByUsercodeAndCategorycodeOrderByYmdAscJikanAsc(user.getCode(),category.getCode());

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

		mv.addObject("schedule", schedule_detail);
		session.setAttribute("categoryCounter", 10);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 1);
		session.setAttribute("categorySortCode", category.getCode());
		mv.setViewName("main");

		return mv;
	}

	//http://localhost:8080/today
	//メイン画面へ遷移
	@RequestMapping("/today")
	public ModelAndView today(ModelAndView mv) { //今日の予定のみ表示
		User user = (User) session.getAttribute("userInfo");
		List<Schedule> schedule = scheduleRepository.findByUsercodeOrderByJikanAsc(user.getCode());
		long now = (Long) session.getAttribute("now");
		Date date = null;
		long datetime_date = 0;
		List<Schedule> schedule_detail = new ArrayList<>();

		for (Schedule sche : schedule) {
			date = sche.getYmd();
			datetime_date = date.getTime();
			if ((datetime_date - now) == 0) {
				schedule_detail.add(sche);
			}
		}
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 10);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 1);
		mv.addObject("schedule", schedule_detail);
		mv.setViewName("main");

		return mv;
	}

	//http://localhost:8080/tomorrow
	//メイン画面へ遷移
	@RequestMapping("/tomorrow")
	public ModelAndView tomorrow(ModelAndView mv) { //明日の予定のみ表示
		User user = (User) session.getAttribute("userInfo");
		List<Schedule> schedule = scheduleRepository.findByUsercodeOrderByJikanAsc(user.getCode());
		long now = (Long) session.getAttribute("now");
		long datetime_date = 0;
		long one_date_time = 1000 * 60 * 60 * 24;

		List<Schedule> schedule_detail = new ArrayList<>();

		for (Schedule sche : schedule) {
			Date date = sche.getYmd();
			datetime_date = date.getTime();
			if ((datetime_date - now) / one_date_time == 1) {
				schedule_detail.add(sche);
			}
		}
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 10);
		session.setAttribute("weekCounter", 1);
		mv.addObject("schedule", schedule_detail);
		mv.setViewName("main");

		return mv;
	}

	//http://localhost:8080/week
	//メイン画面へ遷移
	@RequestMapping("/week")
	public ModelAndView week(ModelAndView mv) { //今週の予定のみ表示
		User user = (User) session.getAttribute("userInfo");
		List<Schedule> schedule = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
		Date date = null;
		long datetime_date = 0;
		long one_date_time = 1000 * 60 * 60 * 24;
		long now = (Long) session.getAttribute("now");
		List<Schedule> schedule_detail = new ArrayList<>();

		for (Schedule sche : schedule) {
			date = sche.getYmd();
			datetime_date = date.getTime();
			if ((datetime_date - now) / one_date_time < 7
					&& (datetime_date - now) >= 0) {
				schedule_detail.add(sche);
			}
		}
		session.setAttribute("categoryCounter", 1);
		session.setAttribute("todayCounter", 1);
		session.setAttribute("tomorrowCounter", 1);
		session.setAttribute("weekCounter", 10);
		mv.addObject("schedule", schedule_detail);
		mv.setViewName("main");

		return mv;
	}
}
