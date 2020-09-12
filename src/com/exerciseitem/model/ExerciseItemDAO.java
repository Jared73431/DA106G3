package com.exerciseitem.model;


import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mygroup.model.MyGroupVO;

import java.io.*;
import java.sql.*;

public class ExerciseItemDAO implements ExerciseItemDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO EXERCISE_ITEM (EXE_NO, EXE_ITEM, EXE_CAL)"
			+ " VALUES (('EI'||LPAD(to_char(EXE_SEQ.NEXTVAL),6,'0')),?,?)";
	private static final String GET_ALL_STMT = "SELECT EXE_NO, EXE_ITEM, EXE_CAL FROM EXERCISE_ITEM order by EXE_NO";
	private static final String GET_ONE_STMT = "SELECT EXE_ITEM, EXE_CAL FROM EXERCISE_ITEM where EXE_NO = ?";
	private static final String DELETE = "DELETE FROM EXERCISE_ITEM where EXE_NO = ?";
	private static final String UPDATE = "UPDATE EXERCISE_ITEM set EXE_ITEM=?, EXE_CAL=? where  EXE_NO=?";


	public void insert(ExerciseItemVO exerciseItemVO) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_STMT);) {
			ps.setString(1, exerciseItemVO.getExe_item());
			ps.setDouble(2, exerciseItemVO.getExe_cal());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public void update(ExerciseItemVO exerciseItemVO) {
    	try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE);) {
    		
			ps.setString(1, exerciseItemVO.getExe_item());
			ps.setDouble(2, exerciseItemVO.getExe_cal());
			ps.setString(3, exerciseItemVO.getExe_no());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		
	}
    }
    public void delete(String exe_no) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setString(1,exe_no);
			 ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public ExerciseItemVO findByPrimaryKey(String exe_no) {
    	ExerciseItemVO exerciseItemVO = null;
    	try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_ONE_STMT);) {
					ps.setString(1, exe_no);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					
					String exe_item = rs.getString(1);
					Double exe_cal = rs.getDouble(2);
					exerciseItemVO = new ExerciseItemVO(exe_no, exe_item, exe_cal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exerciseItemVO;
	}
    	
    
    public List<ExerciseItemVO> getAll(){
    	List<ExerciseItemVO> list = new ArrayList<>();
    	ExerciseItemVO exerciseItemVO =null;
    	try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(GET_ALL_STMT);) {
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {

					exerciseItemVO = new ExerciseItemVO();
					exerciseItemVO.setExe_no(rs.getString("exe_no"));
					exerciseItemVO.setExe_item(rs.getString("exe_item"));
					exerciseItemVO.setExe_cal(rs.getDouble("exe_cal"));
					list.add(exerciseItemVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
//    public static void main(String[] args) {
//    	ExerciseItemDAO dao = new ExerciseItemDAO();
//
//		// 取得所有資料
//		List<ExerciseItemVO> list = dao.getAll();
//		for (ExerciseItemVO exercise : list) {
//			System.out.print(exercise.getExe_no() + ",");
//			System.out.print(exercise.getExe_item() + ",");
//			System.out.print(exercise.getExe_cal() + ",");
//			System.out.println();
//		}

		// 取得指定資料
//		ExerciseItemVO grVO3 = dao.findByPrimaryKey("EI000004");
//		System.out.print(grVO3.getExe_no() + ",");
//		System.out.print(grVO3.getExe_item() + ",");
//		System.out.print(grVO3.getExe_cal() + ",");
//		System.out.println();

		// 新增
//		ExerciseItemVO grVO1 = new ExerciseItemVO();
//		grVO1.setExe_item("test");
//		grVO1.setExe_cal(2.2);
//		dao.insert(grVO1);
		
		
		// 修改
//		ExerciseItemVO grVO2 = new ExerciseItemVO();
//		grVO2.setExe_no("EI000046");
//		grVO2.setExe_item("test22");
//		grVO2.setExe_cal(2.22);
//		dao.update(grVO2);
		
		// 刪除
//		dao.delete("EI000047");
//	}
    }

