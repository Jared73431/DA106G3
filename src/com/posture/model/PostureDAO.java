package com.posture.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PostureDAO implements PostureDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO POSTURE (POSTURE_NO, MEMBER_NO, RECORD_DATE, WEIGHT, BODYFAT, BMR, BMI, REMAIN_CAL)"
			+ " VALUES (('PN'||LPAD(to_char(EXE_SEQ.NEXTVAL),6,'0')),?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT POSTURE_NO, MEMBER_NO, RECORD_DATE, WEIGHT, BODYFAT, BMR, BMI, REMAIN_CAL FROM POSTURE order by RECORD_DATE";
	private static final String GET_MEMBER_STMT = "SELECT POSTURE_NO, MEMBER_NO, RECORD_DATE, WEIGHT, BODYFAT, BMR, BMI, REMAIN_CAL FROM POSTURE where MEMBER_NO = ? order by RECORD_DATE";
	private static final String GET_ONE_STMT = "SELECT POSTURE_NO, MEMBER_NO, RECORD_DATE, WEIGHT, BODYFAT, BMR, BMI, REMAIN_CAL FROM POSTURE where POSTURE_NO = ?";
	private static final String DELETE = "DELETE FROM POSTURE where POSTURE_NO = ?";
	private static final String UPDATE = "UPDATE POSTURE set WEIGHT=?, BODYFAT=?, BMR=?, BMI=?, REMAIN_CAL=? where POSTURE_NO=?";
	private static final String GET_DATE_STMT = "SELECT POSTURE_NO, MEMBER_NO, RECORD_DATE, WEIGHT, BODYFAT, BMR, BMI, REMAIN_CAL FROM POSTURE where ( MEMBER_NO=? AND RECORD_DATE = ?)";
	private static final String UPDATE_REMAIN_CAL = "UPDATE POSTURE set REMAIN_CAL=? where POSTURE_NO=?";
	
	
	public void insert(PostureVO postureVO) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_STMT);) {
			ps.setString(1, postureVO.getMember_no());
			ps.setDate(2, postureVO.getRecord_date());
			ps.setDouble(3, postureVO.getWeight());
			ps.setDouble(4, postureVO.getBodyfat());
			ps.setInt(5, postureVO.getBmr());
			ps.setDouble(6, postureVO.getBmi());
			ps.setDouble(7, postureVO.getRemain_cal());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(PostureVO postureVO) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE);) {
			ps.setDouble(1, postureVO.getWeight());
			ps.setDouble(2, postureVO.getBodyfat());
			ps.setInt(3, postureVO.getBmr());
			ps.setDouble(4, postureVO.getBmi());
			ps.setDouble(5, postureVO.getRemain_cal());
			ps.setString(6, postureVO.getPosture_no());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateRemaincal(Double remain_cal,String posture_no){
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_REMAIN_CAL);) {
			ps.setDouble(1, remain_cal);
			ps.setString(2, posture_no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delete(String posture_no) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setString(1, posture_no);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PostureVO findOneByPrimaryKey(String posture_no) {
		PostureVO postureVO = new PostureVO();
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_ONE_STMT);) {
			ps.setString(1, posture_no);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					posture_no = rs.getString(1);
					String member_no = rs.getString(2);
					java.sql.Date record_date = rs.getDate(3);
					Double weight = rs.getDouble(4);
					Double bodyfat = rs.getDouble(5);
					Integer bmr = rs.getInt(6);
					Double bmi = rs.getDouble(7);
					Double remain_cal = rs.getDouble(8);
					postureVO = new PostureVO(posture_no, member_no, record_date, weight, bodyfat, bmr, bmi,
							remain_cal);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postureVO;
	}
	
	public PostureVO findOneByDate(String member_no,java.sql.Date record_date) {
		PostureVO postureVO = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_DATE_STMT);) {
			ps.setString(1, member_no);
			ps.setDate(2, record_date);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					postureVO = new PostureVO();
					String posture_no = rs.getString(1);
					member_no = rs.getString(2);
					record_date = rs.getDate(3);
					Double weight = rs.getDouble(4);
					Double bodyfat = rs.getDouble(5);
					Integer bmr = rs.getInt(6);
					Double bmi = rs.getDouble(7);
					Double remain_cal = rs.getDouble(8);
					postureVO = new PostureVO(posture_no, member_no, record_date, weight, bodyfat, bmr, bmi,
							remain_cal);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postureVO;
	}


	public List<PostureVO> findByPrimaryKey(String member_no) {
		List<PostureVO> list = new ArrayList<>();
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_MEMBER_STMT);) {
			ps.setString(1, member_no);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					String posture_no = rs.getString(1);
					member_no = rs.getString(2);
					java.sql.Date record_date = rs.getDate(3);
					Double weight = rs.getDouble(4);
					Double bodyfat = rs.getDouble(5);
					Integer bmr = rs.getInt(6);
					Double bmi = rs.getDouble(7);
					Double remain_cal = rs.getDouble(8);
					PostureVO postureVO = new PostureVO(posture_no, member_no, record_date, weight, bodyfat, bmr, bmi,
							remain_cal);
					list.add(postureVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PostureVO> getAll() {
		List<PostureVO> list = new ArrayList<>();
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_ALL_STMT);) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					String posture_no = rs.getString(1);
					String member_no = rs.getString(2);
					java.sql.Date record_date = rs.getDate(3);
					Double weight = rs.getDouble(4);
					Double bodyfat = rs.getDouble(5);
					Integer bmr = rs.getInt(6);
					Double bmi = rs.getDouble(7);
					Double remain_cal = rs.getDouble(8);
					PostureVO postureVO = new PostureVO(posture_no, member_no, record_date, weight, bodyfat, bmr, bmi,
							remain_cal);
					list.add(postureVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

//	public static void main(String[] args) {
//		PostureJDBCDAO dao = new PostureJDBCDAO();
////		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
//
//		// 取得所有資料
//		List<PostureVO> list = dao.getAll();
//		for(PostureVO posture : list) {
//			System.out.print(posture.getPosture_no() + ",");
//			System.out.print(posture.getMember_no() + ",");			
//			System.out.print(posture.getRecord_date()+ ",");
//			System.out.print(posture.getWeight() + ",");
//			System.out.print(posture.getBodyfat() + ",");
//			System.out.print(posture.getBmr() + ",");
//			System.out.print(posture.getBmi() + ",");
//			System.out.print(posture.getRemain_cal() + ",");
//			System.out.println();
//		}
//		System.out.println("=========================");
//	
//	// 取得指定資料
//	List<PostureVO> listMem = dao.findByPrimaryKey("M000004");
//	for(PostureVO posture : listMem) {
//		System.out.print(posture.getPosture_no() + ",");
//		System.out.print(posture.getMember_no() + ",");			
//		System.out.print(posture.getRecord_date() + ",");
//		System.out.print(posture.getWeight() + ",");
//		System.out.print(posture.getBodyfat() + ",");
//		System.out.print(posture.getBmr() + ",");
//		System.out.print(posture.getBmi() + ",");
//		System.out.print(posture.getRemain_cal() + ",");
//		System.out.println();
//	}
//	
//	PostureVO posture = dao.findOneByPrimaryKey("PN000004");
//		System.out.print(posture.getPosture_no() + ",");
//		System.out.print(posture.getMember_no() + ",");			
//		System.out.print(posture.getRecord_date() + ",");
//		System.out.print(posture.getWeight() + ",");
//		System.out.print(posture.getBodyfat() + ",");
//		System.out.print(posture.getBmr() + ",");
//		System.out.print(posture.getBmi() + ",");
//		System.out.print(posture.getRemain_cal() + ",");
//		System.out.println();
	
	// 新增
//	PostureVO grVO1 = new PostureVO();
//	grVO1.setMember_no("M000006");
//	grVO1.setRecord_date(java.sql.Date.valueOf("2020-04-10"));
//	grVO1.setWeight(54.0);
//	grVO1.setBodyfat(0.238);
//	grVO1.setBmr(1415);
//	grVO1.setBmi(22.0);
//	grVO1.setRemain_cal(100.0);
//	dao.insert(grVO1);
	//修改
//	PostureVO grVO2 = new PostureVO();
//	grVO2.setWeight(53.2);
//	grVO2.setBodyfat(0.238);
//	grVO2.setBmr(1415);
//	grVO2.setBmi(22.0);
//	grVO2.setRemain_cal(100.0);
//	grVO2.setPosture_no("PN000048");
//	dao.update(grVO2);
	
	//刪除
//	dao.delete("PN000048");
//}
}