package com.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class GetGooglePlace {

	public double[] GetGooglePlace(String place) {
		double[] latlng = new double[2];
		try{
			
			
			String GOOGLE_URL = "https://maps.googleapis.com/maps/api/geocode/json?" + "address=" + URLEncoder.encode(place,"utf-8") +
				"&language=zh-TW&" + "key=googlekey";
			
			URL url = new URL(GOOGLE_URL);
		
			
		
		StringBuilder sb = new StringBuilder();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoInput(true);
		
		InputStream is = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		String data;
		
		while ((data = br.readLine()) != null) 			
		sb.append(data);
		
		br.close();
		isr.close();
		is.close();
		
		con.disconnect();
		String jsonStr = sb.toString();
//System.out.println(jsonStr);
		JSONObject object = new JSONObject(jsonStr);

		JSONArray result = object.getJSONArray("results");
		JSONObject jSONObject2 = result.getJSONObject(0);
		JSONObject geometry = jSONObject2.getJSONObject("geometry");
		JSONObject location = geometry.getJSONObject("location");
		double lat = location.getDouble("lat");
		double lng = location.getDouble("lng");

		System.out.println("緯度:" + lat + "經度" + lng);
		latlng[0] = lat;
		latlng[1] = lng;
		
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return latlng;
	}

}
