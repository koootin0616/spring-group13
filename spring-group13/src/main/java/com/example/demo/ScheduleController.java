package com.example.demo;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
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
public class ScheduleController {
	@Autowired
	HttpSession session;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	//http://localhost:8080/add
	//メイン画面へ遷移
	@PostMapping("/add")
	public ModelAndView add(
			@RequestParam("category") String categoryname,
			@RequestParam("name") String name,
			@RequestParam("ymd") String ymd,
			@RequestParam("jikan") String jikan,
			@RequestParam("importance") String importance,
			@RequestParam("contents") String contents,
			ModelAndView mv) {
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		if (categoryname.equals("") || name.equals("") || ymd.equals("") || jikan.equals("") || importance.equals("")
				|| contents.equals("")) {
			mv.addObject("message", "未記入項目があります");
			mv.setViewName("addSchedule");
			return mv;
		}
		date = Date.valueOf(ymd);
		User user = (User) session.getAttribute("userInfo");
		Time time = Time.valueOf(jikan + ":00");
		Category category = null;
		Optional<Category> categoryDetail = categoryRepository.findByName(categoryname);
		category = categoryDetail.get();

		Schedule schedule = new Schedule(user.getCode(), category.getCode(), name, date, time, importance,
				contents);


		scheduleRepository.saveAndFlush(schedule);

		List<Schedule> schedule_list = new ArrayList<>();
		List<Schedule> list = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
		long now = (Long) session.getAttribute("now");
		Date date1 = null;
		long datetime_date = 0;
		for (Schedule sche : list) {
			date1 = sche.getYmd();
			datetime_date = date1.getTime();
			if ((datetime_date - now) >= 0) {
				schedule_list.add(sche);
			}
		}
		mv.addObject("message","予定を追加しました");
		mv.addObject("schedule", schedule_list);
		mv.setViewName("main");

		return mv;
	}

	//http://localhost:8080/updateSchedule
	//メイン画面へ遷移
	@RequestMapping("/updateSchedule")
	public ModelAndView update(
			@RequestParam("code") int code,
			@RequestParam(name = "categorycode", defaultValue="0") int categorycode,
			@RequestParam("name") String name,
			@RequestParam("ymd") String ymd,
			@RequestParam("jikan") String jikan,
			@RequestParam("importance") String importance,
			@RequestParam("contents") String contents,
			ModelAndView mv) {
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		date = Date.valueOf(ymd);
		Time time = null;
		if(jikan.length()==8) {
			time = Time.valueOf(jikan);
		}else {
			time = Time.valueOf(jikan + ":00");
		}

		Optional<Schedule> detail= scheduleRepository.findById(code);
		Schedule schedule = detail.get();
		if (schedule.getCategorycode()==categorycode&&schedule.getName().equals(name)&&schedule.getYmd().equals(date)&&schedule.getJikan().equals(time)&&schedule.getImportance().equals(importance)&&schedule.getContents().equals(contents)) {
			mv.addObject("message", "変更事項がありません");
			mv.addObject("schedule", schedule);
			mv.setViewName("update");
			return mv;
		}

		User user = (User) session.getAttribute("userInfo");
		schedule = new Schedule(code, user.getCode(), categorycode, name, date, time, importance, contents);

		scheduleRepository.saveAndFlush(schedule);

		List<Schedule> schedule_list = new ArrayList<>();
		List<Schedule> list = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
		long now = (Long) session.getAttribute("now");
		Date date1 = null;
		long datetime_date = 0;
		for (Schedule sche : list) {
			date1 = sche.getYmd();
			datetime_date = date1.getTime();
			if ((datetime_date - now) >= 0) {
				schedule_list.add(sche);
			}
		}

		mv.addObject("message","予定を変更しました");
		mv.addObject("schedule", schedule_list);
		mv.setViewName("main");

		return mv;
	}

	//http://localhost:8080/delete
	//メイン画面へ遷移
	@RequestMapping("/delete")
	public ModelAndView delete(
			@RequestParam("code") int code,
			ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		scheduleRepository.deleteById(code);
		int categoryCounter = (Integer) session.getAttribute("categoryCounter");
		int todayCounter = (Integer) session.getAttribute("todayCounter");
		int tomorrowCounter = (Integer) session.getAttribute("tomorrowCounter");
		int weekCounter = (Integer) session.getAttribute("weekCounter");

		List<Schedule> list = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
		List<Schedule> schedule = new ArrayList<>();

		if (categoryCounter == 10) {
			int categorySortCode = (Integer) session.getAttribute("categorySortCode");
			schedule = scheduleRepository.findByUsercodeAndCategorycode(user.getCode(), categorySortCode);
		} else if (todayCounter == 10) {
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) == 0) {
					schedule.add(sche);
				}
			}
		} else if (tomorrowCounter == 10) {
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) / (1000 * 60 * 60 * 24) == 1) {
					schedule.add(sche);
				}
			}
		} else if (weekCounter == 10) {
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) / (1000 * 60 * 60 * 24) < 7 && (datetime_date - now) >= 0) {
					schedule.add(sche);
				}
			}
		} else {
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
		}
		mv.addObject("message","削除しました");
		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}

	//http://localhost:8080/sortAscImportance
	//メイン画面へ遷移
	@RequestMapping("/sortAscImportance")
	public ModelAndView sortAscImportance(ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		int categoryCounter = (Integer) session.getAttribute("categoryCounter");
		int todayCounter = (Integer) session.getAttribute("todayCounter");
		int tomorrowCounter = (Integer) session.getAttribute("tomorrowCounter");
		int weekCounter = (Integer) session.getAttribute("weekCounter");

		List<Schedule> list1 = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
		List<Schedule> list2 = new ArrayList<>();
		List<Schedule> list = new ArrayList<>();
		List<Schedule> schedule = new ArrayList<>();

		//重要度のソート
		for(Schedule sche1:list1) {
			if(sche1.getImportance().equals("低")) {
				list.add(sche1);
			}
		}
		for(Schedule sche1:list1) {
			if(sche1.getImportance().equals("中")) {
				list.add(sche1);
			}
		}
		for(Schedule sche1:list1) {
			if(sche1.getImportance().equals("高")) {
				list.add(sche1);
			}
		}

		if (categoryCounter == 10) { //カテゴリー検索時のソート
			int categorySortCode = (Integer) session.getAttribute("categorySortCode");
			list1 = scheduleRepository.findByUsercodeAndCategorycodeOrderByYmdAscJikanAsc(user.getCode(),categorySortCode);
			for(Schedule sche1:list1) {
				if(sche1.getImportance().equals("低")) {
					list2.add(sche1);
				}
			}
			for(Schedule sche1:list1) {
				if(sche1.getImportance().equals("中")) {
					list2.add(sche1);
				}
			}
			for(Schedule sche1:list1) {
				if(sche1.getImportance().equals("高")) {
					list2.add(sche1);
				}
			}
			schedule=list2;
		} else if (todayCounter == 10) { //今日の予定表示時のソート
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) == 0) {
					schedule.add(sche);
				}
			}
		} else if (tomorrowCounter == 10) { //明日の予定表示時のソート
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) / (1000 * 60 * 60 * 24) == 1) {
					schedule.add(sche);
				}
			}
		} else if (weekCounter == 10) { //今週の予定表示時のソート
			list = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) / (1000 * 60 * 60 * 24) < 7 && (datetime_date - now) >= 0) {
					schedule.add(sche);
				}
			}
		} else {
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
		}

		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}

	//http://localhost:8080/sortDescImportance
	//メイン画面へ遷移
	@RequestMapping("/sortDescImportance")
	public ModelAndView sortDescImportance(ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		int categoryCounter = (Integer) session.getAttribute("categoryCounter");
		int todayCounter = (Integer) session.getAttribute("todayCounter");
		int tomorrowCounter = (Integer) session.getAttribute("tomorrowCounter");
		int weekCounter = (Integer) session.getAttribute("weekCounter");

		List<Schedule> list1 = scheduleRepository.findByUsercodeOrderByYmdAscJikanAsc(user.getCode());
		List<Schedule> list2 = new ArrayList<>();
		List<Schedule> list = new ArrayList<>();
		List<Schedule> schedule = new ArrayList<>();

		//重要度のソート
		for(Schedule sche1:list1) {
			if(sche1.getImportance().equals("高")) {
				list.add(sche1);
			}
		}
		for(Schedule sche1:list1) {
			if(sche1.getImportance().equals("中")) {
				list.add(sche1);
			}
		}
		for(Schedule sche1:list1) {
			if(sche1.getImportance().equals("低")) {
				list.add(sche1);
			}
		}

		if (categoryCounter == 10) { //カテゴリー検索時のソート
			int categorySortCode = (Integer) session.getAttribute("categorySortCode");
			list1 = scheduleRepository.findByUsercodeAndCategorycodeOrderByYmdAscJikanAsc(user.getCode(),categorySortCode);
			for(Schedule sche1:list1) {
				if(sche1.getImportance().equals("高")) {
					list2.add(sche1);
				}
			}
			for(Schedule sche1:list1) {
				if(sche1.getImportance().equals("中")) {
					list2.add(sche1);
				}
			}
			for(Schedule sche1:list1) {
				if(sche1.getImportance().equals("低")) {
					list2.add(sche1);
				}
			}
			schedule=list2;
		} else if (todayCounter == 10) { //今日の予定表示時のソート
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) == 0) {
					schedule.add(sche);
				}
			}
		} else if (tomorrowCounter == 10) { //明日の予定表示時のソート
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) / (1000 * 60 * 60 * 24) == 1) {
					schedule.add(sche);
				}
			}
		} else if (weekCounter == 10) { //今週の予定表示時のソート
			long now = (Long) session.getAttribute("now");
			Date date = null;
			long datetime_date = 0;
			for (Schedule sche : list) {
				date = sche.getYmd();
				datetime_date = date.getTime();
				if ((datetime_date - now) / (1000 * 60 * 60 * 24) < 7 && (datetime_date - now) >= 0) {
					schedule.add(sche);
				}
			}
		} else {
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
		}

		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}
}
