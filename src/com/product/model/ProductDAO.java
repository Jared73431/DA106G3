package com.product.model;

import java.io.IOException;
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

import com.util.tool.Upload;

public class ProductDAO implements ProductDAO_interface{
	
	private static DataSource ds =null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	// 0328 修改自動產生序號
	private static final String INSERT_STMT = 
			"INSERT INTO product (pro_no,prc_no,pro_name,pro_price,pro_dis,pro_pic) VALUES ('P'||LPAD(to_char(pro_seq.NEXTVAL),6,'0'),?,?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT pro_no,prc_no,pro_name,pro_price,pro_status,pro_dis FROM product order by pro_no";
	private static final String GET_ONE_STMT = 
			"SELECT pro_no,prc_no,pro_name,pro_price,pro_status,pro_dis FROM product where pro_no = ?";
	// 0506 更改為狀態變更
	private static final String CHANGE_STATUS = 
			"UPDATE product set pro_status=? where pro_no = ?";
	private static final String UPDATE = 
			"UPDATE product set prc_no = ?, pro_name=?, pro_price=?, pro_dis=?, pro_pic=?, pro_status=? where pro_no = ?";
	// 新增讀取圖片測試
	private static final String READ_PIC = 
			"SELECT pro_pic FROM product where pro_no = ?";
	// 新增商店使用的語法(避免呈現下架商品) => 確保驗證還沒拿掉status
	private static final String GET_SHOP_STMT = 
			"SELECT pro_no,prc_no,pro_name,pro_price,pro_status,pro_dis FROM product where pro_status =1 order by pro_no";
	// 新增商店使用的語法(避免呈現下架商品) => 提供分類使用
	private static final String GET_CATE_STMT = 
			"SELECT pro_no,prc_no,pro_name,pro_price,pro_status,pro_dis FROM product where pro_status =1 and prc_no=? order by pro_no";

	
	@Override
	public int insert(ProductVO productVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, productVO.getPrc_no());
			pstmt.setString(2, productVO.getPro_name());
			pstmt.setInt(3, productVO.getPro_price());
			pstmt.setString(4, productVO.getPro_dis());
			pstmt.setBytes(5, productVO.getPro_pic());

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
	public int update(ProductVO productVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, productVO.getPrc_no());
			pstmt.setString(2, productVO.getPro_name());
			pstmt.setInt(3, productVO.getPro_price());
			pstmt.setString(4, productVO.getPro_dis());
			pstmt.setBytes(5, productVO.getPro_pic());			
			pstmt.setInt(6, productVO.getPro_status());
			pstmt.setString(7, productVO.getPro_no());
			
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
	public void changeStatus(String pro_no,Integer pro_status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGE_STATUS);

			pstmt.setInt(1, pro_status);
			pstmt.setString(2, pro_no);
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
	public byte[] readPic(String pro_no) {
		byte[] pic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(READ_PIC);

			pstmt.setString(1, pro_no);
			rs = pstmt.executeQuery();
			rs.next();
			// 抓照片
			pic = rs.getBytes(1);
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
	public List<ProductVO> getAll() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// productVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setPrc_no(rs.getString("prc_no"));
				productVO.setPro_name(rs.getString("pro_name"));
				productVO.setPro_price(rs.getInt("pro_price"));
				productVO.setPro_dis(rs.getString("pro_dis"));
				productVO.setPro_status(rs.getInt("pro_status"));
				list.add(productVO); // Store the row in the vector
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
	public ProductVO findByPrimaryKey(String pro_no) {

		ProductVO productVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, pro_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setPrc_no(rs.getString("prc_no"));
				productVO.setPro_name(rs.getString("pro_name"));
				productVO.setPro_price(rs.getInt("pro_price"));
				productVO.setPro_dis(rs.getString("pro_dis"));
				productVO.setPro_status(rs.getInt("pro_status"));				
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
		return productVO;
	}
	
	@Override
	public List<ProductVO> getShop() {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SHOP_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// productVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setPrc_no(rs.getString("prc_no"));
				productVO.setPro_name(rs.getString("pro_name"));
				productVO.setPro_price(rs.getInt("pro_price"));
				productVO.setPro_dis(rs.getString("pro_dis"));
				productVO.setPro_status(rs.getInt("pro_status"));
				list.add(productVO); // Store the row in the vector
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
	public List<ProductVO> getShop(String prc_no) {
		List<ProductVO> list = new ArrayList<ProductVO>();
		ProductVO productVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_CATE_STMT);
			pstmt.setString(1, prc_no);			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// productVO 也稱為 Domain objects
				productVO = new ProductVO();
				productVO.setPro_no(rs.getString("pro_no"));
				productVO.setPrc_no(rs.getString("prc_no"));
				productVO.setPro_name(rs.getString("pro_name"));
				productVO.setPro_price(rs.getInt("pro_price"));
				productVO.setPro_dis(rs.getString("pro_dis"));
				productVO.setPro_status(rs.getInt("pro_status"));
				list.add(productVO); // Store the row in the vector
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
