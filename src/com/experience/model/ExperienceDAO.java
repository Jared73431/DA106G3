package com.experience.model;

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



public class ExperienceDAO implements ExperienceDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
//	新增
	private static final String INSERT_STMT = "INSERT INTO experience(exper_no,member_no,cate_no,exper_context,exper_title,exper_picture)values(('E'||LPAD(to_char(exper_seq.nextval),6,'0')),?,?,?,?,?)";
//	茶全部
	private static final String GET_ALL_STMT = "SELECT exper_no,member_no,cate_no,exper_context,exper_title,exper_date FROM experience ";
//  查一個	
	private static final String GET_ONE_STMT = "SELECT exper_no,member_no,cate_no,exper_context,exper_title,exper_date,exper_picture FROM experience where exper_no = ?";
//  刪除	
	private static final String DELETE = "DELETE FROM experience where exper_no = ? ";
//  修改	
	private static final String UPDATE = "UPDATE experience set member_no=?,cate_no=?,exper_context=?,exper_title=?,exper_picture=? where exper_no=?";
// 隨機三個	
	private static final String GET_RANDOM_STMT = "SELECT * FROM (SELECT * FROM experience ORDER BY dbms_random.value) where rownum <= '3'";	
	
	@Override
	public void insert(ExperienceVO experienceVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, experienceVO.getMember_no());
			pstmt.setString(2, experienceVO.getCate_no());
			pstmt.setString(3, experienceVO.getExper_context());
			pstmt.setString(4, experienceVO.getExper_title());
			pstmt.setBytes(5, experienceVO.getPicture());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void update(ExperienceVO experienceVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, experienceVO.getMember_no());
			pstmt.setString(2, experienceVO.getCate_no());
			pstmt.setString(3, experienceVO.getExper_context());
			pstmt.setString(4, experienceVO.getExper_title());
			pstmt.setBytes(5, experienceVO.getPicture());
			pstmt.setString(6, experienceVO.getExper_no());
			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(String exper_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, exper_no);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public ExperienceVO findByPrimaryKey(String exper_no) {
		// TODO Auto-generated method stub
		ExperienceVO experienceVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, exper_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// answerVO 也稱為 Domain objects
				experienceVO = new ExperienceVO();
				experienceVO.setExper_no(rs.getString("exper_no"));
				experienceVO.setMember_no(rs.getString("member_no"));
				experienceVO.setCate_no(rs.getString("cate_no"));
				experienceVO.setExper_context(rs.getString("exper_context"));
				experienceVO.setExper_title(rs.getString("exper_title"));
				experienceVO.setPicture(rs.getBytes("exper_picture"));
				experienceVO.setExper_date(rs.getDate("exper_date"));
			}

			// Handle any driver errors
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
		return experienceVO;
	}

	@Override
	public List<ExperienceVO> getAll() {
		// TODO Auto-generated method stub
		List<ExperienceVO> list = new ArrayList<ExperienceVO>();
		ExperienceVO experienceVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				experienceVO = new ExperienceVO();
				experienceVO.setExper_no(rs.getString("exper_no"));
				experienceVO.setMember_no(rs.getString("member_no"));
				experienceVO.setCate_no(rs.getString("cate_no"));
				experienceVO.setExper_title(rs.getString("exper_title"));
				experienceVO.setExper_context(rs.getString("exper_context"));
				experienceVO.setExper_date(rs.getDate("exper_date"));
				list.add(experienceVO); // Store the row in the list
			}

			// Handle any driver errors
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

	@Override
	public List<ExperienceVO> getRandom() {
		// TODO Auto-generated method stub
		List<ExperienceVO> list = new ArrayList<ExperienceVO>();
		ExperienceVO experienceVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RANDOM_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				experienceVO = new ExperienceVO();
				experienceVO.setExper_no(rs.getString("exper_no"));
				experienceVO.setMember_no(rs.getString("member_no"));
				experienceVO.setCate_no(rs.getString("cate_no"));
				experienceVO.setExper_title(rs.getString("exper_title"));
				experienceVO.setExper_context(rs.getString("exper_context"));
				experienceVO.setExper_date(rs.getDate("exper_date"));
				list.add(experienceVO); // Store the row in the vector
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
