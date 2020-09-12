package com.util.tool;

import java.io.*;

import javax.servlet.http.Part;

public class Upload {

	// 使用byte[]方式上傳
	public static byte[] getPictureByteArray(Part part) throws IOException {
		InputStream is = part.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = is.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		is.close();

		return baos.toByteArray();
	}
		
	// 使用String上傳
	public static String getLongString(Part part) throws IOException {
		InputStream is = part.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder(); // StringBuffer is thread-safe!
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();

		return sb.toString();
	}
	
}
