package com.groupgo.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Groupgo_updateBlob {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "DA106G3";
	String passwd = "123456";

	private static final String UPDATE_PIC = "UPDATE GROUPGO set PHOTO=? where GROUPGO_ID = ?";
	private static final String UPDATE_TEXT = "UPDATE GROUPGO set GROUP_CONTENT=? where GROUPGO_ID = ?";

	public void update_pic(GroupgoVO groupgoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_PIC);
			pstmt.setBytes(1, groupgoVO.getPhoto());
			pstmt.setString(2, groupgoVO.getGroupgo_id());
			System.out.println(pstmt.executeUpdate());

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	public void update_text(GroupgoVO groupgoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_TEXT);
			pstmt.setString(1, groupgoVO.getGroup_content());
			pstmt.setString(2, groupgoVO.getGroupgo_id());
			System.out.println(pstmt.executeUpdate());

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	public static void main(String[] args) {

		Groupgo_updateBlob dao = new Groupgo_updateBlob();
		// 傳照片
		GroupgoVO grVO4 = new GroupgoVO();
		for (int i = 1; i <= 8; i++) {
			try {
				byte[] pic = getPictureByteArray("images/p" + i + ".jpg");
				grVO4.setPhoto(pic);
			} catch (IOException e) {
				e.printStackTrace();
			}
			grVO4.setGroupgo_id("G00000" + i);
			dao.update_pic(grVO4);
		}

		GroupgoVO grVO5 = new GroupgoVO();
		for (int i = 1; i <= 8; i++) {
			try {
				String str = getLongString("images/t" + i + ".txt");
				grVO5.setGroup_content(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
			grVO5.setGroupgo_id("G00000" + i);
			dao.update_text(grVO5);
		}
	}

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

	public static String getLongString(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
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
