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

	@PostMapping("/addsche")
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
		//登録するエンティティのインスタンスを生成

		Schedule schedule = new Schedule(user.getCode(), category.getCode(), name, date, time, importance,
				contents);

		//ItemエンティティをItemテーブルに登録
		scheduleRepository.saveAndFlush(schedule);

		List<Schedule> list = scheduleRepository.findByUsercode(user.getCode());

		mv.addObject("schedule", list);
		mv.setViewName("main");

		return mv;
	}

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

		List<Schedule> list = scheduleRepository.findByUsercode(user.getCode());

		mv.addObject("schedule", list);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(
			@RequestParam("code") int code,
			ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		scheduleRepository.deleteById(code);

		List<Schedule> schedule = scheduleRepository.findByUsercode(user.getCode());

		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/sortDescYmd")
	public ModelAndView sortDescYmd(ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		int categoryCounter = (Integer) session.getAttribute("categoryCounter");
		int todayCounter = (Integer) session.getAttribute("todayCounter");
		int tomorrowCounter = (Integer) session.getAttribute("tomorrowCounter");
		int weekCounter = (Integer) session.getAttribute("weekCounter");

		List<Schedule> list = scheduleRepository.findByUsercodeOrderByYmdDesc(user.getCode());
		List<Schedule> schedule = new ArrayList<>();

		if (categoryCounter == 10) {
			int categorySortCode = (Integer) session.getAttribute("categorySortCode");
			schedule = scheduleRepository.findByUsercodeAndCategorycodeOrderByYmdDesc(user.getCode(), categorySortCode);
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
			schedule = scheduleRepository.findByUsercodeOrderByYmdDesc(user.getCode());
		}

		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/sortAscYmd")
	public ModelAndView sortAscYmd(ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		int categoryCounter = (Integer) session.getAttribute("categoryCounter");
		int todayCounter = (Integer) session.getAttribute("todayCounter");
		int tomorrowCounter = (Integer) session.getAttribute("tomorrowCounter");
		int weekCounter = (Integer) session.getAttribute("weekCounter");

		List<Schedule> list = scheduleRepository.findByUsercodeOrderByYmdAsc(user.getCode());
		List<Schedule> schedule = new ArrayList<>();

		if (categoryCounter == 10) {
			int categorySortCode = (Integer) session.getAttribute("categorySortCode");
			schedule = scheduleRepository.findByUsercodeAndCategorycodeOrderByYmdAsc(user.getCode(), categorySortCode);
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
			schedule = scheduleRepository.findByUsercodeOrderByYmdAsc(user.getCode());
		}
		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/sortDescJikan")
	public ModelAndView sortDescJikan(ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		int categoryCounter = (Integer) session.getAttribute("categoryCounter");
		int todayCounter = (Integer) session.getAttribute("todayCounter");
		int tomorrowCounter = (Integer) session.getAttribute("tomorrowCounter");
		int weekCounter = (Integer) session.getAttribute("weekCounter");

		List<Schedule> list = scheduleRepository.findByUsercodeOrderByJikanDesc(user.getCode());
		List<Schedule> schedule = new ArrayList<>();

		if (categoryCounter == 10) {
			int categorySortCode = (Integer) session.getAttribute("categorySortCode");
			schedule = scheduleRepository.findByUsercodeAndCategorycodeOrderByJikanDesc(user.getCode(),
					categorySortCode);
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
			list = scheduleRepository.findByUsercodeOrderByYmdDescJikanDesc(user.getCode());
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
			schedule = scheduleRepository.findByUsercodeOrderByJikanDesc(user.getCode());
		}

		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}

	@RequestMapping("/sortAscJikan")
	public ModelAndView sortAscJikan(ModelAndView mv) {
		User user = (User) session.getAttribute("userInfo");
		int categoryCounter = (Integer) session.getAttribute("categoryCounter");
		int todayCounter = (Integer) session.getAttribute("todayCounter");
		int tomorrowCounter = (Integer) session.getAttribute("tomorrowCounter");
		int weekCounter = (Integer) session.getAttribute("weekCounter");

		List<Schedule> list = scheduleRepository.findByUsercodeOrderByJikanAsc(user.getCode());
		List<Schedule> schedule = new ArrayList<>();

		if (categoryCounter == 10) {
			int categorySortCode = (Integer) session.getAttribute("categorySortCode");
			schedule = scheduleRepository.findByUsercodeAndCategorycodeOrderByJikanAsc(user.getCode(),
					categorySortCode);
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
			schedule = scheduleRepository.findByUsercodeOrderByJikanAsc(user.getCode());
		}

		mv.addObject("schedule", schedule);
		mv.setViewName("main");

		return mv;
	}
}
