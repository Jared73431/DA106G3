package com.informcate.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InformCateJDBCDAO implements InformCateDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:49161:XE";
	String userid = "DA106G3";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO inform_cate (cate_no,cate_name) VALUES ('IC'||LPAD(to_char(inform_seq.NEXTVAL),2,'0'),?)";
	private static final String GET_ALL_STMT = 
			"SELECT cate_no,cate_name FROM inform_cate order by cate_no";
	private static final String GET_ONE_STMT = 
			"SELECT cate_no,cate_name FROM inform_cate where cate_no = ?";
	private static final String DELETE = 
			"DELETE FROM inform_cate where cate_no = ?";
	private static final String UPDATE = 
			"UPDATE inform_cate set cate_name=? where cate_no = ?";

	@Override
	public int insert(InformCateVO informcateVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, informcateVO.getCate_name());
			
			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public int update(InformCateVO informcateVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, informcateVO.getCate_name());
			pstmt.setString(2, informcateVO.getCate_no());	
			
			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public int delete(String cate_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, cate_no);

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public InformCateVO findByPrimaryKey(String cate_no) {

		InformCateVO informcateVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, cate_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				informcateVO = new InformCateVO();
				informcateVO.setCate_no(rs.getString("cate_no"));
				informcateVO.setCate_name(rs.getString("cate_name"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
		return informcateVO;
	}

	@Override
	public List<InformCateVO> getAll() {
		List<InformCateVO> list = new ArrayList<InformCateVO>();
		InformCateVO informcateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// informcateVO 也稱為 Domain objects
				informcateVO = new InformCateVO();
				informcateVO.setCate_no(rs.getString("cate_no"));
				informcateVO.setCate_name(rs.getString("cate_name"));
				list.add(informcateVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public static void main(String[] args) {

		InformCateJDBCDAO dao = new InformCateJDBCDAO();

		// 新增 0326確認
//			 InformCateVO icate4 = new InformCateVO();
//			 icate4.setCate_no("IC05");
//			 icate4.setCate_name("動吃動吃");
//			 int updateCount_insert = dao.insert(icate4);
//			 System.out.println(updateCount_insert);

			 
		// 修改 0326確認
//			 InformCateVO icate3 = new InformCateVO();
//			 icate3.setCate_no("IC05");
//		 	 icate3.setCate_name("動茲動茲");
//			 int updateCount_update = dao.update(icate3);
//			 System.out.println(updateCount_update);

		// 刪除 0326確認
//			 int updateCount_delete = dao.delete("IC05");
//			 System.out.println(updateCount_delete);

		// 查詢 0326確認
//			InformCateVO icate2 = dao.findByPrimaryKey("IC04");
//			System.out.print(icate2.getCate_no() + ",");
//			System.out.print(icate2.getCate_name() + ",");
//			System.out.println("---------------------");

		// 查詢 0326確認
//		List<InformCateVO> list = dao.getAll();
//		for (InformCateVO icate : list) {
//			System.out.print(icate.getCate_no() + ",");
//			System.out.print(icate.getCate_name() + ",");
//			System.out.println();
//		}
	}

}
