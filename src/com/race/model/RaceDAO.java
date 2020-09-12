package com.race.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.product.model.ProductVO;

public class RaceDAO implements RaceDAO_interface {

	private static DataSource ds =null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	// 改成流水號
	private static final String INSERT_STMT = 
			"INSERT INTO race (race_no,race_year,race_inform) VALUES ('R'||LPAD(to_char(race_seq.NEXTVAL),3,'0'),?,?)";
	private static final String GET_ONE_STMT = 
			"SELECT race_no,cate_no,race_year,race_inform FROM race where race_no = ?";
	private static final String GET_ALL_STMT = 
			"SELECT race_no,cate_no,race_year,race_inform FROM race";
	// 專門爬洗資料用
	private static final String UPDATE = 
			"UPDATE race set race_inform=? where race_no = ?";

	@Override
	public int insert(RaceinformVO raceinformVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, raceinformVO.getCate_no());
			pstmt.setString(2, raceinformVO.getRace_year());
			pstmt.setString(3, raceinformVO.getRace_inform());
			
			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public void update(RaceinformVO raceinformVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, raceinformVO.getRace_inform());
			pstmt.setString(2, raceinformVO.getRace_no());
			
			pstmt.executeUpdate();
			System.out.println("資料更新");

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
	}

	@Override
	public RaceinformVO findByPrimaryKey(String race_no) {

		RaceinformVO raceinformVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, race_no);

			rs = pstmt.executeQuery();

			rs.next();
			// raceinformVo 也稱為 Domain objects
			raceinformVO = new RaceinformVO();
			raceinformVO.setRace_no(rs.getString("race_no"));
			raceinformVO.setCate_no(rs.getString("cate_no"));
			raceinformVO.setRace_year(rs.getString("race_year"));
			raceinformVO.setRace_inform(rs.getString("race_inform"));
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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
		return raceinformVO;
	}
	
	@Override
	public List<RaceVO> raceList(String race_no) {

		List<RaceVO> race = new ArrayList<RaceVO>();;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, race_no);

			rs = pstmt.executeQuery();

			rs.next();
			String race_inform = rs.getString("race_inform");	
			Gson gson = new Gson();
			// 轉換成RaceVO List回傳
			race = gson.fromJson(race_inform,new TypeToken<List<RaceVO>>() {}.getType());

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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
		return race;
	}
	
	@Override
	public List<RaceinformVO> getAll() {
		List<RaceinformVO> list = new ArrayList<RaceinformVO>();
		RaceinformVO raceinformVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// productVO 也稱為 Domain objects
				raceinformVO = new RaceinformVO();
				raceinformVO.setRace_no(rs.getString("race_no"));
				raceinformVO.setRace_year(rs.getString("race_year"));
				list.add(raceinformVO); // Store the row in the vector
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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
}
