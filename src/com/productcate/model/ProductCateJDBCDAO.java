package com.productcate.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCateJDBCDAO implements ProductCateDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:49161:XE";
	String userid = "DA106G3";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO product_cate (prc_no,prc_name) VALUES ('PC'||LPAD(to_char(prc_seq.NEXTVAL),2,'0'),?)";
	private static final String GET_ALL_STMT = 
			"SELECT prc_no,prc_name FROM product_cate order by prc_no";
	private static final String GET_ONE_STMT = 
			"SELECT prc_no,prc_name FROM product_cate where prc_no = ?";
	private static final String DELETE = 
			"DELETE FROM product_cate where prc_no = ?";
	private static final String UPDATE = 
			"UPDATE product_cate set prc_name=? where prc_no = ?";

	@Override
	public int insert(ProductCateVO productcateVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, productcateVO.getPrc_name());
			
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
	public int update(ProductCateVO productcateVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, productcateVO.getPrc_name());
			pstmt.setString(2, productcateVO.getPrc_no());	
			
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
	public int delete(String prc_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, prc_no);

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
	public ProductCateVO findByPrimaryKey(String prc_no) {

		ProductCateVO productcateVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, prc_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				productcateVO = new ProductCateVO();
				productcateVO.setPrc_no(rs.getString("prc_no"));
				productcateVO.setPrc_name(rs.getString("prc_name"));
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
		return productcateVO;
	}

	@Override
	public List<ProductCateVO> getAll() {
		List<ProductCateVO> list = new ArrayList<ProductCateVO>();
		ProductCateVO productcateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// productcateVO 也稱為 Domain objects
				productcateVO = new ProductCateVO();
				productcateVO.setPrc_no(rs.getString("prc_no"));
				productcateVO.setPrc_name(rs.getString("prc_name"));
				list.add(productcateVO); // Store the row in the vector
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

		ProductCateJDBCDAO dao = new ProductCateJDBCDAO();

		// 新增 0328確認
//			 ProductCateVO pcate4 = new ProductCateVO();
//			 pcate4.setPrc_no("PC07");
//			 pcate4.setPrc_name("健身-動吃動吃");
//			 int updateCount_insert = dao.insert(pcate4);
//			 System.out.println(updateCount_insert);

			 
		// 修改 0328確認
//			 ProductCateVO pcate3 = new ProductCateVO();
//			 pcate3.setPrc_no("PC07");
//		 	 pcate3.setPrc_name("健身-動茲動茲");
//			 int updateCount_update = dao.update(pcate3);
//			 System.out.println(updateCount_update);

		// 刪除 確認
			 int updateCount_delete = dao.delete("PC07");
			 System.out.println(updateCount_delete);

		// 查詢 0328確認
//			ProductCateVO pcate2 = dao.findByPrimaryKey("PC04");
//			System.out.print(pcate2.getPrc_no() + ",");
//			System.out.print(pcate2.getPrc_name() + ",");
//			System.out.println("---------------------");

		// 查詢 0328確認
//		List<ProductCateVO> list = dao.getAll();
//		for (ProductCateVO pcate : list) {
//			System.out.print(pcate.getPrc_no() + ",");
//			System.out.print(pcate.getPrc_name() + ",");
//			System.out.println();
//		}
	}

}
