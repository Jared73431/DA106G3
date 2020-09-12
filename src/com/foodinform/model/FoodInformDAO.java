package com.foodinform.model;

import java.util.*;
import java.io.IOException;
import java.sql.*;
import javax.naming.*;
import javax.sql.*;

import com.util.tool.Upload;

public class FoodInformDAO implements FoodInformDAO_interface {
	
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
			"INSERT INTO food_inform (fi_no,fi_title,fi_author,fi_cover,fi_content) VALUES ('FI'||LPAD(to_char(fi_seq.NEXTVAL),6,'0'),?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT fi_no,cate_no,fi_title,fi_author,fi_cover,fi_content,to_char(fi_date,'yyyy-mm-dd hh:mm:ss') fi_date,fi_status FROM food_inform order by fi_no";
	private static final String GET_ONE_STMT = 
			"SELECT fi_no,cate_no,fi_title,fi_author,fi_cover,fi_content,to_char(fi_date,'yyyy-mm-dd hh:mm:ss') fi_date,fi_status FROM food_inform where fi_no = ?";
	// 取得隨機3筆資料
	private static final String GET_RANDOM_STMT = 
			"SELECT * FROM (SELECT * FROM food_inform ORDER BY dbms_random.value) where fi_status='1' and rownum <= '3'";	
	private static final String CHANGE_STATUS = 
			"UPDATE food_inform set fi_status=? where fi_no = ?";
	private static final String UPDATE = 
			"UPDATE food_inform set fi_title=?, fi_author=?, fi_cover=?, fi_content=?, fi_date=?, fi_status=? where fi_no = ?";
	// 新增讀取圖片測試
	private static final String READ_PIC = 
			"SELECT fi_cover FROM food_inform where fi_no = ?";
	// 新增讀取上架文章
	private static final String GET_ALL_AUTH = 
			"SELECT fi_no,cate_no,fi_title,fi_author,fi_cover,fi_content,to_char(fi_date,'yyyy-mm-dd hh:mm:ss') fi_date,fi_status FROM food_inform where fi_status='1' order by fi_date";
	
	@Override
	public int insert(FoodInformVO foodinformVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, foodinformVO.getFi_title());
			pstmt.setString(2, foodinformVO.getFi_author());
			pstmt.setBytes(3, foodinformVO.getFi_cover());
			pstmt.setString(4, foodinformVO.getFi_content());

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
	public int update(FoodInformVO foodinformVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, foodinformVO.getFi_title());
			pstmt.setString(2, foodinformVO.getFi_author());
			pstmt.setBytes(3, foodinformVO.getFi_cover());
			pstmt.setString(4, foodinformVO.getFi_content());
			pstmt.setTimestamp(5, foodinformVO.getFi_date());
			pstmt.setInt(6, foodinformVO.getFi_status());
			pstmt.setString(7, foodinformVO.getFi_no());

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
	
	// 0501刪除改成狀態變更
	@Override
	public void changeStatus(String fi_no, Integer fi_status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGE_STATUS);
			
			pstmt.setInt(1, fi_status);
			pstmt.setString(2, fi_no);
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
		System.out.println("AJAX操作成功");
	}

	@Override
	public FoodInformVO findByPrimaryKey(String fi_no) {

		FoodInformVO foodinformVO = null;
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
				foodinformVO = new FoodInformVO();
				foodinformVO.setFi_no(rs.getString("fi_no"));
				foodinformVO.setCate_no(rs.getString("cate_no"));
				foodinformVO.setFi_title(rs.getString("fi_title"));
				foodinformVO.setFi_author(rs.getString("fi_author"));
				foodinformVO.setFi_cover(rs.getBytes("fi_cover"));
				foodinformVO.setFi_content(rs.getString("fi_content"));
				foodinformVO.setFi_date(rs.getTimestamp("fi_date"));
				foodinformVO.setFi_status(rs.getInt("fi_status"));
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
		return foodinformVO;
	}

	@Override
	public byte[] readPic(String fi_no) {
		byte[] pic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(READ_PIC);

			pstmt.setString(1, fi_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 抓照片
				pic = rs.getBytes(1);
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
		return pic;
	}
	
	@Override
	public List<FoodInformVO> getAll() {
		List<FoodInformVO> list = new ArrayList<FoodInformVO>();
		FoodInformVO foodinformVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// foodinformVO 也稱為 Domain objects
				foodinformVO = new FoodInformVO();
				foodinformVO.setFi_no(rs.getString("fi_no"));
				foodinformVO.setCate_no(rs.getString("cate_no"));
				foodinformVO.setFi_title(rs.getString("fi_title"));
				foodinformVO.setFi_author(rs.getString("fi_author"));
				foodinformVO.setFi_content(rs.getString("fi_content"));
				foodinformVO.setFi_date(rs.getTimestamp("fi_date"));
				foodinformVO.setFi_status(rs.getInt("fi_status"));
				list.add(foodinformVO); // Store the row in the vector
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
	
	@Override
	public List<FoodInformVO> getRandom() {
		List<FoodInformVO> list = new ArrayList<FoodInformVO>();
		FoodInformVO foodinformVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RANDOM_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				foodinformVO = new FoodInformVO();
				foodinformVO.setFi_no(rs.getString("fi_no"));
				foodinformVO.setCate_no(rs.getString("cate_no"));
				foodinformVO.setFi_title(rs.getString("fi_title"));
				foodinformVO.setFi_author(rs.getString("fi_author"));
				foodinformVO.setFi_cover(rs.getBytes("fi_cover"));
				foodinformVO.setFi_content(rs.getString("fi_content"));
				foodinformVO.setFi_date(rs.getTimestamp("fi_date"));
				foodinformVO.setFi_status(rs.getInt("fi_status"));
				list.add(foodinformVO); // Store the row in the vector
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
	
	// 取得上架文章
	@Override
	public List<FoodInformVO> getAuth() {
		List<FoodInformVO> list = new ArrayList<FoodInformVO>();
		FoodInformVO foodinformVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_AUTH);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// foodinformVO 也稱為 Domain objects
				foodinformVO = new FoodInformVO();
				foodinformVO.setFi_no(rs.getString("fi_no"));
				foodinformVO.setCate_no(rs.getString("cate_no"));
				foodinformVO.setFi_title(rs.getString("fi_title"));
				foodinformVO.setFi_author(rs.getString("fi_author"));
				foodinformVO.setFi_content(rs.getString("fi_content"));
				foodinformVO.setFi_date(rs.getTimestamp("fi_date"));
				foodinformVO.setFi_status(rs.getInt("fi_status"));
				list.add(foodinformVO); // Store the row in the vector
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
