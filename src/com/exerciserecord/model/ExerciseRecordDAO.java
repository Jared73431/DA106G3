package com.exerciserecord.model;

import java.util.*;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.exerciseitem.model.ExerciseItemDAO;
import com.exerciseitem.model.ExerciseItemVO;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ExerciseRecordDAO implements ExerciseRecordDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO EXERCISE_RECORD (EXEREC_NO, MEMBER_NO, EXE_NO, EXE_DATE, EXE_TIME, EXE_TCAL)"
			+ " VALUES (('ER'||LPAD(to_char(EXE_SEQ.NEXTVAL),6,'0')),?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT EXEREC_NO, MEMBER_NO, EXE_NO, EXE_DATE, EXE_TIME, EXE_TCAL FROM EXERCISE_RECORD order by EXEREC_NO";
	private static final String GET_ONE_STMT = "SELECT EXEREC_NO, MEMBER_NO, EXE_NO, EXE_DATE, EXE_TIME, EXE_TCAL FROM EXERCISE_RECORD where EXEREC_NO = ?";
	private static final String DELETE = "DELETE FROM EXERCISE_RECORD where EXEREC_NO = ?";
	private static final String UPDATE = "UPDATE EXERCISE_RECORD set EXE_NO=?, EXE_TIME=? , EXE_TCAL=? where EXEREC_NO = ?";
	private static final String GET_DATE_STMT = "SELECT * FROM EXERCISE_RECORD where MEMBER_NO =? AND EXE_DATE = ?";
	

	public void insert(ExerciseRecordVO exerciseRecordVO) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_STMT);) {
			ps.setString(1, exerciseRecordVO.getMember_no());
			ps.setString(2, exerciseRecordVO.getExe_no());
			ps.setDate(3, exerciseRecordVO.getExe_date());
			ps.setDouble(4, exerciseRecordVO.getExe_time());
			ps.setDouble(5, exerciseRecordVO.getExe_tcal());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(ExerciseRecordVO exerciseRecordVO) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE);) {
			ps.setString(1, exerciseRecordVO.getExe_no());
			ps.setDouble(2, exerciseRecordVO.getExe_time());
			ps.setDouble(3, exerciseRecordVO.getExe_tcal());
			ps.setString(4, exerciseRecordVO.getExerec_no());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(String exerec_no) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setString(1, exerec_no);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ExerciseRecordVO findByPrimaryKey(String exerec_no) {
		ExerciseRecordVO exerciseRecordVO = null;
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_ONE_STMT);) {
			ps.setString(1, exerec_no);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					exerec_no = rs.getString(1);
					String member_no = rs.getString(2);
					String exe_no = rs.getString(3);
					java.sql.Date exe_date = rs.getDate(4);
					double exe_time = rs.getDouble(5);
					double exe_tcal = rs.getDouble(6);
					exerciseRecordVO = new ExerciseRecordVO(exerec_no, member_no, exe_no, exe_date, exe_time, exe_tcal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exerciseRecordVO;
	}

	public List<ExerciseRecordVO> getAll() {
		List<ExerciseRecordVO> list = new ArrayList<>();
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_ALL_STMT);) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					String exerec_no = rs.getString(1);
					String member_no = rs.getString(2);
					String exe_no = rs.getString(3);
					java.sql.Date exe_date = rs.getDate(4);
					double exe_time = rs.getDouble(5);
					double exe_tcal = rs.getDouble(6);
					ExerciseRecordVO exerciseRecordVO = new ExerciseRecordVO(exerec_no, member_no, exe_no, exe_date,
							exe_time, exe_tcal);
					list.add(exerciseRecordVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List findByDate(String member_no, java.sql.Date exe_date) {
		List<ExerciseRecordVO> list = new ArrayList<>();
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_DATE_STMT);) {
				ps.setString(1, member_no);
				ps.setDate(2, exe_date);
			
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					String exerec_no = rs.getString(1);
					member_no = rs.getString(2);
					String exe_no = rs.getString(3);
					exe_date = rs.getDate(4);
					double exe_time = rs.getDouble(5);
					double exe_tcal = rs.getDouble(6);
					ExerciseRecordVO exerciseRecordVO = new ExerciseRecordVO(exerec_no, member_no, exe_no, exe_date,
							exe_time, exe_tcal);
					list.add(exerciseRecordVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


//	public static void main(String[] args) {
//		ExerciseRecordDAO dao = new ExerciseRecordDAO();
////		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
//
//		// 取得所有資料
//		List<ExerciseRecordVO> list = dao.getAll();
//		for (ExerciseRecordVO exercise : list) {
//			System.out.print(exercise.getExerec_no() + ",");
//			System.out.print(exercise.getMember_no() + ",");
//			System.out.print(exercise.getExe_no() + ",");
//			System.out.print(exercise.getExe_date() + ",");
////			System.out.print(sdf4.format(exercise.getExe_date()) + ",");
//			System.out.print(exercise.getExe_time() + ",");
//			System.out.print(exercise.getExe_tcal() + ",");
//			System.out.println();
//		}
//		System.out.println("================");
//		 //取得指定資料
//		ExerciseRecordVO grVO3 = dao.findByPrimaryKey("ER000004");
//		System.out.print(grVO3.getExerec_no() + ",");
//		System.out.print(grVO3.getMember_no() + ",");
//		System.out.print(grVO3.getExe_no() + ",");
//		System.out.print(grVO3.getExe_date() + ",");
////		System.out.print(sdf4.format(grVO3.getExe_date()) + ",");
//		System.out.print(grVO3.getExe_time() + ",");
//		System.out.print(grVO3.getExe_tcal() + ",");
//		System.out.println();

		// 新增
//		ExerciseRecordVO grVO1 = new ExerciseRecordVO();
//			grVO1.setMember_no("M000003");
//			grVO1.setExe_no("EI000002");
//			grVO1.setExe_date(java.sql.Date.valueOf("2020-03-29"));
//			grVO1.setExe_time(30);
//			grVO1.setExe_tcal(300);
//			dao.insert(grVO1);

		// 修改
//		ExerciseRecordVO grVO2 = new ExerciseRecordVO();
//			grVO2.setExe_no("EI000001");
//			grVO2.setExe_time(22);
//			grVO2.setExe_tcal(222);
//			grVO2.setExerec_no("ER000049");
//			dao.update(grVO2);

		// 刪除
//			dao.delete("ER000049");
//	}
}
