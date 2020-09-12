package com.race.model;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.*;

public class JsoupRace {
    public static String raceinform(String year) throws IOException {
    	List<RaceVO> list = new ArrayList<RaceVO>();
        String url = "http://www.taipeimarathon.org.tw/contest.aspx";
        String url2 = "?__EVENTTARGET=Year&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=%2FwEPDwUKLTEzOTA2NDAyOGQYAQUJR3JpZFZpZXcxDzwrAAwBCAIBZNZIYVszZyt5LAvCDtRngbNYH8qkNDRxzlIiSB2flLvu&__VIEWSTATEGENERATOR=9A03162A&__EVENTVALIDATION=%2FwEdACWRm09k4lW9xFsj2bR05ZY58BDSks8yYLW5qDynT%2FlaXJoskkBYVV2mozz%2FRpX4YeRd49yorbZLt1F85MeifL0SJTvO5XRavkzkBMVRKMutrJYSQCpvpY1ehH%2BsqVJISYowEM9AJIFFxTF6D%2BVNEJqszbd1XTv6eWlnI4cH0uCalJUOLcDcK%2FPzz08Sk7SgH7slEyvLeXdyVwyJsy7%2B9W7Zx%2F3qItWWE5Pp6LKOLC3tCEuGy0uBf%2BlE3ffgTbdEi1W6OipSpQKg6WEBpJC8GxhbfVpi53ne%2BZ%2FQaZNjciUu1jZi8iQqlsIxG%2FzaAaVL%2FBecd%2BhQa1%2F6MLZpw8ZBuNkM8Vbv0pRFxa0JteZVJK6qfDZe9dA02n4d3Aqr%2Bg6pf3raZqY%2Buy%2Fd5Tg9zvoJmSBM%2FFAZBsbyKZGM2FMYFhA3ixiRShq4sYTUSAZCh4k3m5gzO4X5aaMFZEmAfXr%2B8dlSn6QKdph%2BHW7Tum3QylENLWyErlMeT1PA17aVbK9XtfS6%2FL4P9HreF%2FFz2HTbnQFaM9Yq%2Fa%2FUhDoYYWIN7ETQqCgNDsoIhG%2BimkrkZp34mfN4cfSghQQyILPA5pA0G174j19yVDElG2H25wrkZp%2FPMrkLvQyuTmE%2FThG0YIK6hxC%2FXCtSblB1r32Zemn4USbxUNCGdLJwXpZYVR%2BMY1oyQid5Epc007LVg1gMlKx4lEes7EEVLEFmzaBNN7kdl8kIPNnJDhVxv%2F1jYLyCfbNcdLRU%2FoaIAi3u4L4Nha5bPW2BD1tBhzPUOeNV5rIGuI6kFRYhPArPUWXkJedWlCieDnUlkqo4PiEdlKaT1nItOTo%3D&DropDownList1=all&"
        		+ "type=all&Year=";
        String url3 = url2 + year;
        Connection connect =Jsoup.connect(url);
        // 增加偽裝
        connect.header("Accept","text/html");
        connect.header("Content-Type","application/x-www-form-urlencoded");
        connect.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
        connect.header("Accept-Language", "zh,zh-TW;q=0.9,en-US;q=0.8,en;q=0.7");
        connect.header("Connection", "keep-alive");
        connect.timeout(5000);
        Document doc = connect.requestBody(url).post();             
        System.out.printf("Fetching %s...", url);
        System.out.println("資料取得");
        // 選擇class是gridview的table標籤
        Elements table = doc.select(".gridview");
        // 標籤中的tbody
        Elements tbody = table.select("tbody");
        // tbody底下的tr
        Elements trs = tbody.select("tr");

        for (Element tr:trs) {
        	if(!tr.select("td").isEmpty()) {
        		// 建構子的方式改變
        		RaceVO race = new RaceVO();
        		race.setRace_name(tr.select("td:eq(1)").outerHtml());	// 比賽名稱
        		race.setRace_time(tr.select("td:eq(3)").html());		// 比賽日期
        		race.setRace_location(tr.select("td:eq(4)").html());	// 比賽地點	
        		race.setRace_content(tr.select("td:eq(5)").html());		// 比賽內容
        		race.setRace_organizer(tr.select("td:eq(6)").html());	// 主辦單位	
        		race.setRace_deadline(tr.select("td:eq(7)").html());	// 報名狀況
        		list.add(race);
        	}
        }
        Gson gson = new Gson();
        String js = gson.toJson(list);
        return js;
    }
//    public static void main(String[] args) throws IOException {
//    	
//    	String raceinform = raceinform("");
//    	RaceService raceSvc = new RaceService();
//    	raceSvc.updateRace("R001", "IC02", "NOW", raceinform);
    	//    	Gson gson = new Gson();
    	
    	// 這段可以拿來抓資料庫的值，反轉出List
//    	List<RaceVO> race = gson.fromJson(raceinform,new TypeToken<List<RaceVO>>() {}.getType());
////    	System.out.println(race);
//    	for (int i=0;i<race.size();i++) {
//    	RaceVO raceVO = race.get(i);
//    	String race_name = raceVO.getRace_name();
//    	String race_time = raceVO.getRace_time();
//    	String race_location = raceVO.getRace_location();
//    	String race_content = raceVO.getRace_content();
//    	String race_organizer = raceVO.getRace_organizer();
//    	String race_deadline = raceVO.getRace_deadline();
//    	System.out.print("比賽名稱: "+race_name+ "\n比賽時間: " + race_time +"\n比賽地點: " +race_location 
//    					+"\n比賽內容: " +race_content +"\n主辦單位: " + race_organizer +"\n報名狀況: "+ race_deadline+"\n");
//    	System.out.println("----------分隔線----------");
//    	}
//    }
}

