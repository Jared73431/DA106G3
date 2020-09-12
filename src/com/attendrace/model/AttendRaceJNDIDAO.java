package com.attendrace.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.attendrace.*;
import com.members.model.MembersVO;



public class AttendRaceJNDIDAO implements AttendRaceDAO_interface {

	private static final String INSERT_STMT = 
			"INSERT INTO ATTEND_RACE (ATTEND_NO, MEMBER_NO, RACE_TYPE, RACE_NAME, FIN_DATE, FIN_TIME, FIN_RANK, FIN_RECORD, FIN_REMARK) VALUES (('AR'||LPAD(to_char(member_seq.NEXTVAL),6,'0')),?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT ATTEND_NO, MEMBER_NO, RACE_TYPE, RACE_NAME, FIN_DATE, FIN_TIME, FIN_RANK, FIN_REMARK FROM ATTEND_RACE order by ATTEND_NO";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM ATTEND_RACE where ATTEND_NO = ?";
	private static final String DELETE = 
			"DELETE FROM ATTEND_RACE where ATTEND_NO = ? ";
	private static final String UPDATE = 
			"UPDATE ATTEND_RACE set MEMBER_NO=?, RACE_TYPE=?, RACE_NAME=?, FIN_TIME=?, FIN_RANK=?, FIN_REMARK=? where ATTEND_NO = ?";
	private static final String GET_ONE_MEM =
			"SELECT * FROM ATTEND_RACE WHERE MEMBER_NO = ?";
	
	//一個應用程式中,針對一個資料庫,共用一個DataSource即可
			private static DataSource ds = null;
			static {
				try {
					Context ctx = new InitialContext();
					ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
	
	
	

	@Override
	public int insert(AttendRaceVO attendRaceVO) {
		// TODO Auto-generated method stub
		
		int updateCount = 0;
		Connection con = null;

		try (
			Connection connection = ds.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(INSERT_STMT);){
			
			pstmt.setString(1, attendRaceVO.getMember_no());
			pstmt.setInt(2,attendRaceVO.getRace_type());
			pstmt.setString(3, attendRaceVO.getRace_name());
			pstmt.setDate(4,attendRaceVO.getFin_date());
			pstmt.setString(5,attendRaceVO.getFin_time());
			pstmt.setInt(6,attendRaceVO.getFin_rank());
			pstmt.setBytes(7,attendRaceVO.getFin_record());
			pstmt.setString(8,attendRaceVO.getFin_remark());
			
		

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		} 
		return updateCount;			
	}

	@Override
	public int updateBookingData(AttendRaceVO attendRaceVO) {
		int rowCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, attendRaceVO.getMember_no());
			pstmt.setInt(2, attendRaceVO.getRace_type());
			pstmt.setString(3, attendRaceVO.getRace_name());
//			pstmt.setTimestamp(4, attendRaceVO.getFin_date());
			pstmt.setString(4, attendRaceVO.getFin_time());
			pstmt.setInt(5, attendRaceVO.getFin_rank());
			pstmt.setString(6, attendRaceVO.getFin_remark());
			pstmt.setString(7, attendRaceVO.getAttend_no());

			rowCount = pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
		return rowCount;

	}

	@Override
	public boolean delete(String attend_no) {
		int rowCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
				pstmt.setString(1, attend_no);
			rowCount = pstmt.executeUpdate();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
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
		return rowCount != 0;
	}	
	
	@Override
	public AttendRaceVO findByPrimaryKey(String trans_no) {
		AttendRaceVO attendRaceVO= null;
		ResultSet rs = null;
		try(Connection connection = ds.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(GET_ONE_STMT);)
		{
			ps.setString(1, trans_no);
			rs = ps.executeQuery();
			while (rs.next()) {
				// empVo �]�٬� Domain objects
				attendRaceVO = new AttendRaceVO();
				attendRaceVO.setAttend_no(rs.getString("ATTEND_NO"));
				attendRaceVO.setMember_no(rs.getString("MEMBER_NO"));
				attendRaceVO.setRace_type(rs.getInt("RACE_TYPE"));
				attendRaceVO.setRace_name(rs.getString("RACE_NAME"));
				attendRaceVO.setFin_date(rs.getDate("FIN_DATE"));
				attendRaceVO.setFin_time(rs.getString("FIN_TIME"));
				attendRaceVO.setFin_rank(rs.getInt("FIN_RANK"));
				attendRaceVO.setFin_remark(rs.getString("FIN_REMARK"));
				
			}

		}
		
		catch(SQLException e) 
		{e.printStackTrace();}
		
		
		return attendRaceVO;
	}

	@Override
	public List<AttendRaceVO> getAll() {
		// TODO Auto-generated method stub
		List<AttendRaceVO> list = new ArrayList<AttendRaceVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AttendRaceVO attendRaceVO = new AttendRaceVO();
				attendRaceVO.setAttend_no(rs.getString("attend_no"));
				attendRaceVO.setMember_no(rs.getString("member_no"));
				attendRaceVO.setRace_type(rs.getInt("race_type"));
				attendRaceVO.setRace_name(rs.getString("race_name"));
				attendRaceVO.setFin_date(rs.getDate("fin_date"));
				attendRaceVO.setFin_time(rs.getString("fin_time"));
				attendRaceVO.setFin_rank(rs.getInt("fin_rank"));
//				attendRaceVO.setFin_record(rs.getInt("price"));
				attendRaceVO.setFin_remark(rs.getString("fin_remark"));
				list.add(attendRaceVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
		return list;
	}
	
	public List<AttendRaceVO> getByMem(String member_no){
		List<AttendRaceVO> list = new ArrayList<AttendRaceVO>();
		ResultSet rs = null;
		try(Connection connection = ds.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(GET_ONE_MEM);)
		{
			ps.setString(1, member_no);
			rs = ps.executeQuery();
			while (rs.next()) {
				AttendRaceVO attendRaceVO = new AttendRaceVO();
				attendRaceVO.setAttend_no(rs.getString("ATTEND_NO"));
				attendRaceVO.setMember_no(rs.getString("MEMBER_NO"));
				attendRaceVO.setRace_type(rs.getInt("RACE_TYPE"));
				attendRaceVO.setRace_name(rs.getString("RACE_NAME"));
				attendRaceVO.setFin_date(rs.getDate("FIN_DATE"));
				attendRaceVO.setFin_time(rs.getString("FIN_TIME"));
				attendRaceVO.setFin_rank(rs.getInt("FIN_RANK"));
				attendRaceVO.setFin_record(rs.getBytes("FIN_RECORD"));
				attendRaceVO.setFin_remark(rs.getString("FIN_REMARK"));
				list.add(attendRaceVO);
			}
		}
		
		catch(SQLException e) 
		{e.printStackTrace();}
		
		return list;
	}
//	private static StringBuffer Magic_Select_Stmt = null;

//	@Override
//	public List<AttendRaceVO> getAll(Map<String, String[]> map) {
//		ResultSet rs = null;
//		BookingOrderDetailVO rtVO = null;
//		List<AttendRaceVO> list = new ArrayList<>();
//		
//		Magic_Select_Stmt = new StringBuffer("SELECT * FROM ATTEND_RACE WHERE ");
//		String[] valueArray = null;
//		Iterator<String> iterator = map.keySet().iterator();
//		while(iterator.hasNext()) {
//			String key = (String)iterator.next();
//			Magic_Select_Stmt.append(key + " in ( ");
//			valueArray = map.get(key);
//			for(int i = 0; i < valueArray.length; i++) {
//				Magic_Select_Stmt.append("\'" + valueArray[i] + "\'" + ((i == valueArray.length-1)?"":","));
//			}
//			Magic_Select_Stmt.append(") " + (iterator.hasNext()? " and " : ""));
//		}
//		System.out.println(Magic_Select_Stmt);
//	
//	try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD); 
//		PreparedStatement pstmt = con.prepareStatement(Magic_Select_Stmt.toString());){
//		
//		rs = pstmt.executeQuery();
//		while(rs.next()) {
//			AttendRaceVO dao=new AttendRaceVO();
//			dao.setAttend_no(rs.getString("ATTEND_NO"));
//			dao.setMember_no(rs.getString("MEMBER_NO"));
//			dao.setRace_type(rs.getInt("RACE_TYPE"));
//			dao.setRace_name(rs.getString("RACE_NAME"));
//			dao.setFin_date(rs.getTimestamp("FIN_DATE"));
//			dao.setFin_time(rs.getString("FIN_TIME"));
//			dao.setFin_rank(rs.getInt("FIN_REMARK"));
//			dao.setFin_remark(rs.getString("FIN_REMARK"));
//			list.add(dao);
//		}
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	return list;
//}	

	

	}

