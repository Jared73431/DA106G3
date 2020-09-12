package com.main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class MyTimerTaskListener implements ServletContextListener {
	private Timer timer = null;
	List<Date> schedule = new ArrayList();

	
	public void contextInitialized(ServletContextEvent event) {	
		timer = new Timer(true);
		Calendar calendar = Calendar.getInstance();     
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 0);   
		Date date = calendar.getTime(); 
	
		timer.schedule(new MyTimerTask(),date, 30 * 60 * 1000);		
		
	}

	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
	}

}
