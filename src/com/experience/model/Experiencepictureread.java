package com.experience.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Experiencepictureread {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "DA106G3";
	String password = "123456";

	private static final String UPLOAD = "UPDATE experience set exper_picture=? where exper_no = ?";

	public void upload(ExperienceVO expVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, password);
			pstmt = con.prepareStatement(UPLOAD);

			// 1. setBytes
			pstmt.setBytes(1, expVO.getPicture());
			pstmt.setString(2, expVO.getExper_no());
			pstmt.executeUpdate();

			// 清空裡面參數，重覆使用已取得的PreparedStatement物件
			pstmt.clearParameters();

			System.out.println("新增成功");

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {
			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
		}
	}

	// 使用byte[]方式
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	public static void main(String[] args) {

		Experiencepictureread expread = new Experiencepictureread();
		// 上傳
		ExperienceVO expVO = new ExperienceVO();
		File file = new File("experpic/");
		String[] filelist = file.list();
		// 列出資料夾內的檔案（此為陣列位置，要輸入索引值呈現檔案位置)
		for (int i = 0; i < filelist.length; i++) {
			File readfile = new File("experpic/" + filelist[i]);
			int dotpos = readfile.getName().lastIndexOf(".");
			String filename = readfile.getName().substring(0, dotpos);
			// 觀察路徑與檔名切割狀況
			System.out.println(filename);
			System.out.println(readfile.getPath());
			// 設定資料庫的產品編號，因為圖片名稱與欄位主檔相同
			expVO.setExper_no(filename);
			try {
				// 設定資料庫想要存取的BLOB路徑
				expVO.setPicture(getPictureByteArray(readfile.getPath()));
			} catch (IOException ie) {
				System.out.println(ie);
			}
			expread.upload(expVO);
		}
	}
}
