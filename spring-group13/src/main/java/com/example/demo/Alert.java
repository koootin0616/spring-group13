package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;


public class Alert {

	public static void main(String[] args) throws ParseException{

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Timer timer = new Timer(false);
				TimerTask task = new TimerTask() {

					@Override
					public void run() {

						timer.cancel();
						System.out.println("てすと");
					}
				};
				timer.schedule(task, sdf.parse("2021/07/16 11:32:00"));

				//return "/main";
			}


	}


