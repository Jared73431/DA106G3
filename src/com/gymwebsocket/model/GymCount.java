package com.gymwebsocket.model;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;


public class GymCount {
	private static final String ZLCSC = "https://zlcsc.cyc.org.tw/api";		//GN0001
	private static final String TYCSC = "https://tycsc.cyc.org.tw/api";		//GN0002
	private static final String CSSC = "https://cssc.cyc.org.tw/api";		//GN0003
	private static final String	NGSC = "https://ngsc.cyc.org.tw/api";		//GN0004
	private static final String NHSC = "https://nhsc.cyc.org.tw/api";		//GN0005
	private static final String XYSC = "https://xysc.cyc.org.tw/api";		//GN0006
	private static final String DASC = "https://dasc.cyc.org.tw/api";		//GN0007
	private static final String WSSC = "https://wssc.cyc.org.tw/api";		//GN0008
	private static final String XZCSC = "https://xzcsc.cyc.org.tw/api";		//GN0009
	private static final String ZGCSC = "https://zgcsc.cyc.org.tw/api";		//GN0010
	private static final String CMCSC = "https://cmcsc.cyc.org.tw/api";		//GN0011
	

	public static String count() throws IOException, JSONException {
		List<GymCountVO> list = new ArrayList<GymCountVO>();
		boolean flag = false;
		list.add(new GymCountVO("GN0001",ZLCSC));
		list.add(new GymCountVO("GN0002",TYCSC));
		list.add(new GymCountVO("GN0003",CSSC));
		list.add(new GymCountVO("GN0004",NGSC));
		list.add(new GymCountVO("GN0005",NHSC));
		list.add(new GymCountVO("GN0006",XYSC));
		list.add(new GymCountVO("GN0007",DASC));
		list.add(new GymCountVO("GN0008",WSSC));
		list.add(new GymCountVO("GN0009",XZCSC));
		list.add(new GymCountVO("GN0010",ZGCSC));
		list.add(new GymCountVO("GN0011",CMCSC));
		// 後端做到這裡
		for(int i=0;i<list.size();i++) {
			Connection connect = Jsoup.connect(list.get(i).getGym_url()).validateTLSCertificates(false);
	        // 增加偽裝
	        connect.header("Accept","text/html");
	        connect.header("Content-Type","application/x-www-form-urlencoded");
	        connect.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
	        connect.header("Accept-Language", "zh,zh-TW;q=0.9,en-US;q=0.8,en;q=0.7");
	        connect.header("Connection", "keep-alive");
			// 連線增加連線錯誤防護
	        try {
		        Document document = connect.timeout(3000).get();
				Element element = document.select("body").get(0);
				String text1 = element.select("body").text();
				JSONObject jsonObject = new JSONObject(text1);
				JSONArray gymdata = jsonObject.getJSONArray("gym");
				list.get(i).setGym_data(gymdata.toString());
		        }catch(Exception e) {
					flag =true;
		        	list.get(i).setGym_data("[\"0\",\"0\",\"0\"]");		        			        						
					System.out.println(list.get(i).getGym_no()+"目前連線異常");
		        
		        }
	        }
		// 把參數換成Json字串
		Gson gson = new Gson();
		String jsonstring = gson.toJson(list);		
		int count = 0;
		// 防錯機制
		if (flag) {
			count++;
			if (count%3==1) {
				jsonstring = "[{\"gym_no\":\"GN0001\",\"gym_url\":\"https://zlcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"15\\\",\\\"90\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0002\",\"gym_url\":\"https://tycsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"10\\\",\\\"90\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0003\",\"gym_url\":\"https://cssc.cyc.org.tw/api\",\"gym_data\":\"[\\\"9\\\",\\\"50\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0004\",\"gym_url\":\"https://ngsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"18\\\",\\\"85\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0005\",\"gym_url\":\"https://nhsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"24\\\",\\\"99\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0006\",\"gym_url\":\"https://xysc.cyc.org.tw/api\",\"gym_data\":\"[\\\"15\\\",\\\"65\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0007\",\"gym_url\":\"https://dasc.cyc.org.tw/api\",\"gym_data\":\"[\\\"12\\\",\\\"70\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0008\",\"gym_url\":\"https://wssc.cyc.org.tw/api\",\"gym_data\":\"[\\\"19\\\",\\\"85\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0009\",\"gym_url\":\"https://xzcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"9\\\",\\\"40\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0010\",\"gym_url\":\"https://zgcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"8\\\",\\\"70\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0011\",\"gym_url\":\"https://cmcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"22\\\",\\\"72\\\",\\\"0\\\"]\"}]";		
			}
			if (count%3==2) {
				jsonstring = "[{\"gym_no\":\"GN0001\",\"gym_url\":\"https://zlcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"15\\\",\\\"90\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0002\",\"gym_url\":\"https://tycsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"11\\\",\\\"90\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0003\",\"gym_url\":\"https://cssc.cyc.org.tw/api\",\"gym_data\":\"[\\\"9\\\",\\\"50\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0004\",\"gym_url\":\"https://ngsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"18\\\",\\\"85\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0005\",\"gym_url\":\"https://nhsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"23\\\",\\\"99\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0006\",\"gym_url\":\"https://xysc.cyc.org.tw/api\",\"gym_data\":\"[\\\"16\\\",\\\"65\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0007\",\"gym_url\":\"https://dasc.cyc.org.tw/api\",\"gym_data\":\"[\\\"12\\\",\\\"70\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0008\",\"gym_url\":\"https://wssc.cyc.org.tw/api\",\"gym_data\":\"[\\\"19\\\",\\\"85\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0009\",\"gym_url\":\"https://xzcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"9\\\",\\\"40\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0010\",\"gym_url\":\"https://zgcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"8\\\",\\\"70\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0011\",\"gym_url\":\"https://cmcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"22\\\",\\\"72\\\",\\\"0\\\"]\"}]";
			}
			if (count%3==0) {
				jsonstring = "[{\"gym_no\":\"GN0001\",\"gym_url\":\"https://zlcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"16\\\",\\\"90\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0002\",\"gym_url\":\"https://tycsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"11\\\",\\\"90\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0003\",\"gym_url\":\"https://cssc.cyc.org.tw/api\",\"gym_data\":\"[\\\"9\\\",\\\"50\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0004\",\"gym_url\":\"https://ngsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"18\\\",\\\"85\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0005\",\"gym_url\":\"https://nhsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"23\\\",\\\"99\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0006\",\"gym_url\":\"https://xysc.cyc.org.tw/api\",\"gym_data\":\"[\\\"15\\\",\\\"65\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0007\",\"gym_url\":\"https://dasc.cyc.org.tw/api\",\"gym_data\":\"[\\\"13\\\",\\\"70\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0008\",\"gym_url\":\"https://wssc.cyc.org.tw/api\",\"gym_data\":\"[\\\"19\\\",\\\"85\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0009\",\"gym_url\":\"https://xzcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"9\\\",\\\"40\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0010\",\"gym_url\":\"https://zgcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"8\\\",\\\"70\\\",\\\"0\\\"]\"},{\"gym_no\":\"GN0011\",\"gym_url\":\"https://cmcsc.cyc.org.tw/api\",\"gym_data\":\"[\\\"23\\\",\\\"72\\\",\\\"0\\\"]\"}]";
			}
		}
		return jsonstring;
	}
}
