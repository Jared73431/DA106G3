package com.orders.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.orderlist.model.*;

public class OrdersDAO implements OrdersDAO_interface{

	private static DataSource ds =null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 改用自動建立流水號，修正實際情況，ETB自動產生，ETA由員工修改
	private static final String INSERT_STMT = 
			"INSERT INTO orders (order_no,member_no,order_zip,order_address,total_price) "
			+ "VALUES (to_char(sysdate,'yyyymmdd')||'-'||LPAD(to_char(order_seq.NEXTVAL), 6, '0'),?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT order_no,member_no,order_zip,order_address,etb,eta,total_price,order_status FROM orders order by order_no";
	// 會員查詢
	private static final String GET_MEM_STMT = 
			"SELECT order_no,member_no,order_zip,order_address,etb,eta,total_price,order_status FROM orders where member_no= ? order by order_no";
	// 出貨管理
	private static final String GET_ETA_STMT = 
			"SELECT order_no,member_no,order_zip,order_address,etb,eta,total_price,order_status FROM orders where order_status='1' order by order_no";	
	
	private static final String GET_ONE_STMT = 
			"SELECT order_no,member_no,order_zip,order_address,etb,eta,total_price,order_status FROM orders where order_no = ?";
	private static final String DELETE = 
			"DELETE FROM orders where order_no = ?";
	private static final String UPDATE = // 實際狀況訂單，員工只能修改狀態與預計到貨時間
			"UPDATE orders set member_no=?, order_zip=?, order_address=?, etb=? , eta=? , total_price=? , order_status=? where order_no = ?";

	private static final String UPDATE_REAL_BLANCE =
			"UPDATE MEMBERS SET REAL_BLANCE=? WHERE MEMBER_NO=?";
	
	private static final String INSERT_TRANS = 
			"INSERT INTO TRANSACTIONS (TRANS_NO, MEMBER_NO, DEPOSIT, WITHDRAW, REMARK) VALUES (('T'||LPAD(to_char(member_seq.NEXTVAL),6,'0')),?,?,?,?)";

	
	@Override
	public int insert(OrdersVO ordersVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, ordersVO.getMember_no());
			pstmt.setString(2, ordersVO.getOrder_zip());
			pstmt.setString(3, ordersVO.getOrder_address());
			pstmt.setInt(4, ordersVO.getTotal_price());

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
	public boolean insert(OrdersVO ordersVO, List<OrderListVO> buylist) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		try {
			con = ds.getConnection();
			// 1.設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 2.先新增訂單
    		String cols[] = {"ORDER_NO"};
    		pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, ordersVO.getMember_no());
			pstmt.setString(2, ordersVO.getOrder_zip());
			pstmt.setString(3, ordersVO.getOrder_address());
			pstmt.setInt(4, ordersVO.getTotal_price());
			pstmt.executeUpdate();
			// 3.取得對應的自增主鍵值
			String next_orderno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				next_orderno = rs.getString(1);
				System.out.println("自增主鍵值= " + next_orderno + "(新增的一筆訂單編號)");
			}else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 4.同時新增訂單明細
			OrderListService orderlistSvc = new OrderListService();
			System.out.println("buylist.size()-A="+buylist.size());
			for (OrderListVO list : buylist) {
				list.setOrder_no(next_orderno);
				System.out.println(list.getOrder_no());
				System.out.println(list.getPro_no());
				System.out.println(list.getOrder_price());
				System.out.println(list.getPro_qty());
				orderlistSvc.addOrderListA(list, con);
			}
			
			// 5.設定於pstm.excuteUpdate()之後
			con.commit();
			con.setAutoCommit(true); // 養成關閉要打開的習慣
			System.out.println("buylist.size()-B=\"+buylist.size()");
			System.out.println("新增訂單編號" + next_orderno + 
					"時，共有商品明細"+buylist.size()+"份同時被新增");
			flag = true;
			// Handle any SQL errors
		} catch (SQLException se) {
			if(con != null) {
				try {
				// 6.設定當有exception發生時之catch區塊內
					System.err.print("Transaction is being");
					System.err.println("rolled back-由-orders");
					con.rollback();
				}catch(SQLException excep){
					throw new RuntimeException("rollback error occured."
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return flag;
	}
	
	
	@Override
	public int update(OrdersVO ordersVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, ordersVO.getMember_no());
			pstmt.setString(2, ordersVO.getOrder_zip());
			pstmt.setString(3, ordersVO.getOrder_address());
			pstmt.setTimestamp(4, ordersVO.getEtb());
			pstmt.setTimestamp(5, ordersVO.getEta());
			pstmt.setInt(6, ordersVO.getTotal_price());
			pstmt.setInt(7, ordersVO.getOrder_status());
			pstmt.setString(8, ordersVO.getOrder_no());

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
	public int delete(String order_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, order_no);

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
	public OrdersVO findByPrimaryKey(String order_no) {

		OrdersVO ordersVO = null;
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
				ordersVO = new OrdersVO();
				ordersVO.setOrder_no(rs.getString("order_no"));
				ordersVO.setMember_no(rs.getString("member_no"));
				ordersVO.setOrder_zip(rs.getString("order_zip"));
				ordersVO.setOrder_address(rs.getString("order_address"));
				ordersVO.setEtb(rs.getTimestamp("etb"));
				ordersVO.setEta(rs.getTimestamp("eta"));
				ordersVO.setTotal_price(rs.getInt("total_price"));
				ordersVO.setOrder_status(rs.getInt("order_status"));
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
		return ordersVO;
	}

	@Override
	public List<OrdersVO> getAll() {
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ordersVO 也稱為 Domain objects
				ordersVO = new OrdersVO();
				ordersVO.setOrder_no(rs.getString("order_no"));
				ordersVO.setMember_no(rs.getString("member_no"));
				ordersVO.setOrder_zip(rs.getString("order_zip"));
				ordersVO.setOrder_address(rs.getString("order_address"));
				ordersVO.setEtb(rs.getTimestamp("etb"));
				ordersVO.setEta(rs.getTimestamp("eta"));
				ordersVO.setTotal_price(rs.getInt("total_price"));
				ordersVO.setOrder_status(rs.getInt("order_status"));
				list.add(ordersVO); // Store the row in the vector
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
	public List<OrdersVO> getMem(String member_no) {
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEM_STMT);
			pstmt.setString(1, member_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ordersVO 也稱為 Domain objects
				ordersVO = new OrdersVO();
				ordersVO.setOrder_no(rs.getString("order_no"));
				ordersVO.setMember_no(rs.getString("member_no"));
				ordersVO.setOrder_zip(rs.getString("order_zip"));
				ordersVO.setOrder_address(rs.getString("order_address"));
				ordersVO.setEtb(rs.getTimestamp("etb"));
				ordersVO.setEta(rs.getTimestamp("eta"));
				ordersVO.setTotal_price(rs.getInt("total_price"));
				ordersVO.setOrder_status(rs.getInt("order_status"));
				list.add(ordersVO); // Store the row in the vector
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
	public List<OrdersVO> getEta() {
		List<OrdersVO> list = new ArrayList<OrdersVO>();
		OrdersVO ordersVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ETA_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// ordersVO 也稱為 Domain objects
				ordersVO = new OrdersVO();
				ordersVO.setOrder_no(rs.getString("order_no"));
				ordersVO.setMember_no(rs.getString("member_no"));
				ordersVO.setOrder_zip(rs.getString("order_zip"));
				ordersVO.setOrder_address(rs.getString("order_address"));
				ordersVO.setEtb(rs.getTimestamp("etb"));
				ordersVO.setEta(rs.getTimestamp("eta"));
				ordersVO.setTotal_price(rs.getInt("total_price"));
				ordersVO.setOrder_status(rs.getInt("order_status"));
				list.add(ordersVO); // Store the row in the vector
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
	public boolean paybill(String member_no,Integer real_blance,Integer deposit,Integer withdraw,String remark) {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = true;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(UPDATE_REAL_BLANCE);
			pstmt.setInt(1, real_blance);
			pstmt.setString(2,member_no);
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(INSERT_TRANS);
			
			pstmt.setString(1, member_no);
			pstmt.setInt(2, deposit);
			pstmt.setInt(3, withdraw);
			pstmt.setString(4, remark);
			
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
					result = false;
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {

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
		return result;
	}
}
