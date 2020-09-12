package com.orderlist.model;

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

public class OrderListDAO implements OrderListDAO_interface{

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
			"INSERT INTO order_list (order_no,pro_no,pro_qty,order_price) VALUES (?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT order_no,pro_no,pro_qty,order_price FROM order_list order by order_no";
	private static final String GET_ONE_STMT = 
			"SELECT order_no,pro_no,pro_qty,order_price FROM order_list where order_no = ?";
	private static final String DELETE = 
			"DELETE FROM order_list where order_no = ? and pro_no = ?";
	private static final String GET_MEM_STMT = 
			"SELECT order_no,pro_no,pro_qty,order_price FROM order_list where order_no = ? order by order_no";
	
	@Override
	public int insert(OrderListVO orderlistVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderlistVO.getOrder_no());
			pstmt.setString(2, orderlistVO.getPro_no());
			pstmt.setInt(3, orderlistVO.getPro_qty());
			pstmt.setInt(4, orderlistVO.getOrder_price());

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
	public void insertA(OrderListVO orderlistVO,Connection con) {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderlistVO.getOrder_no());
			pstmt.setString(2, orderlistVO.getPro_no());
			pstmt.setInt(3, orderlistVO.getPro_qty());
			pstmt.setInt(4, orderlistVO.getOrder_price());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if(con!=null) {
				try {
					// 設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-orderlist");
					con.rollback();
				}catch(SQLException excep) {
					throw new RuntimeException("rollbacke error occured. "
							+ excep.getMessage());
				}
			}
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
		}
	}

	
//	@Override
	public int delete(String order_no , String pro_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, order_no);
			pstmt.setString(2, pro_no);

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

//	@Override
	public OrderListVO findByPrimaryKey(String order_no) {

		OrderListVO orderlistVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, order_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				orderlistVO = new OrderListVO();
				orderlistVO.setOrder_no(rs.getString("order_no"));
				orderlistVO.setPro_no(rs.getString("pro_no"));
				orderlistVO.setPro_qty(rs.getInt("pro_qty"));
				orderlistVO.setOrder_price(rs.getInt("order_price"));
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
		return orderlistVO;
	}

	@Override
	public List<OrderListVO> getAll() {
		List<OrderListVO> list = new ArrayList<OrderListVO>();
		OrderListVO orderlistVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// orderlistVO 也稱為 Domain objects
				orderlistVO = new OrderListVO();
				orderlistVO.setOrder_no(rs.getString("order_no"));
				orderlistVO.setPro_no(rs.getString("pro_no"));
				orderlistVO.setPro_qty(rs.getInt("pro_qty"));
				orderlistVO.setOrder_price(rs.getInt("order_price"));
				list.add(orderlistVO); // Store the row in the vector
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
	public List<OrderListVO> getOrderList(String order_no) {
		List<OrderListVO> list = new ArrayList<OrderListVO>();
		OrderListVO orderlistVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEM_STMT);
			pstmt.setString(1, order_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// orderlistVO 也稱為 Domain objects
				orderlistVO = new OrderListVO();
				orderlistVO.setOrder_no(rs.getString("order_no"));
				orderlistVO.setPro_no(rs.getString("pro_no"));
				orderlistVO.setPro_qty(rs.getInt("pro_qty"));
				orderlistVO.setOrder_price(rs.getInt("order_price"));
				list.add(orderlistVO); // Store the row in the vector
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
