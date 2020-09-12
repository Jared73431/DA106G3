package com.gym.model;

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

public class GymDAO implements GymDAO_interface{

	private static DataSource ds =null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO gym (gym_no,gym_name,gym_address,gym_lon,gym_lat,gym_site) VALUES ('GN'||LPAD(to_char(gym_seq.NEXTVAL),4,'0'),?,?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT gym_no,cate_no,gym_name,gym_address,gym_lon,gym_lat,gym_site FROM gym order by gym_no";
	private static final String GET_ONE_STMT = 
			"SELECT gym_no,cate_no,gym_name,gym_address,gym_lon,gym_lat,gym_site FROM gym where gym_no = ?";
	private static final String DELETE = 
			"DELETE FROM gym where gym_no = ?";
	private static final String UPDATE = 
			"UPDATE gym set gym_name=?, gym_address=?, gym_lon=?, gym_lat=?, gym_site=? where gym_no = ?";

	@Override
	public int insert(GymVO gymVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, gymVO.getGym_name());
			pstmt.setString(2, gymVO.getGym_address());
			pstmt.setDouble(3, gymVO.getGym_lon());
			pstmt.setDouble(4, gymVO.getGym_lat());
			pstmt.setString(5, gymVO.getGym_site());
			
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
	public int update(GymVO gymVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, gymVO.getGym_name());
			pstmt.setString(2, gymVO.getGym_address());
			pstmt.setDouble(3, gymVO.getGym_lon());
			pstmt.setDouble(4, gymVO.getGym_lat());
			pstmt.setString(5, gymVO.getGym_site());
			pstmt.setString(6, gymVO.getGym_no());
			
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
	public void delete(String gym_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, gym_no);

			pstmt.executeUpdate();

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
	public GymVO findByPrimaryKey(String fi_no) {

		GymVO gymVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, fi_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				gymVO = new GymVO();
				gymVO.setGym_no(rs.getString("gym_no"));
				gymVO.setCate_no(rs.getString("cate_no"));
				gymVO.setGym_name(rs.getString("gym_name"));
				gymVO.setGym_address(rs.getString("gym_address"));
				gymVO.setGym_lon(rs.getDouble("gym_lon"));
				gymVO.setGym_lat(rs.getDouble("gym_lat"));
				gymVO.setGym_site(rs.getNString("gym_site"));
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
		return gymVO;
	}

	@Override
	public List<GymVO> getAll() {
		List<GymVO> list = new ArrayList<GymVO>();
		GymVO gymVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// gymVO 也稱為 Domain objects
				gymVO = new GymVO();
				gymVO.setGym_no(rs.getString("gym_no"));
				gymVO.setCate_no(rs.getString("cate_no"));
				gymVO.setGym_name(rs.getString("gym_name"));
				gymVO.setGym_address(rs.getString("gym_address"));
				gymVO.setGym_lon(rs.getDouble("gym_lon"));
				gymVO.setGym_lat(rs.getDouble("gym_lat"));
				gymVO.setGym_site(rs.getString("gym_site"));
				list.add(gymVO); // Store the row in the vector
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
