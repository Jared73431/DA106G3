package com.dietdetail.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class DietDetailDAO implements DietDetailDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	private static final String INSERT_STMT = "INSERT INTO DIET_DETAIL (DIET_NO, FOOD_NO, AMOUNT, FOOD_CAL) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM DIET_DETAIL order by diet_no";
	private static final String GET_DIET_STMT = "SELECT * FROM DIET_DETAIL where diet_no = ?";
	private static final String GET_ONE_STMT = "SELECT * FROM DIET_DETAIL where (diet_no =? AND food_no =?)";
	private static final String DELETE = "DELETE FROM DIET_DETAIL where (diet_no =? AND food_no =?)";
	private static final String UPDATE = "UPDATE DIET_DETAIL set amount=?, food_cal=? where diet_no = ? AND food_no=?";

	@Override
	public int insert(DietDetailVO dietdetailVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dietdetailVO.getDiet_no());
			pstmt.setString(2, dietdetailVO.getFood_no());
			pstmt.setDouble(3, dietdetailVO.getAmount());
			pstmt.setDouble(4, dietdetailVO.getFood_cal());

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	 public void insert2(DietDetailVO dietdetailVO, Connection con) {
		
		PreparedStatement pstmt = null;

		try {
			
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dietdetailVO.getDiet_no());
			pstmt.setString(2, dietdetailVO.getFood_no());
			pstmt.setDouble(3, dietdetailVO.getAmount());
			pstmt.setDouble(4, dietdetailVO.getFood_cal());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-emp");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
		}

	}
	
	
	@Override
	public int update(DietDetailVO dietdetailVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setDouble(1, dietdetailVO.getAmount());
			pstmt.setDouble(2, dietdetailVO.getFood_cal());
			pstmt.setString(3, dietdetailVO.getDiet_no());
			pstmt.setString(4, dietdetailVO.getFood_no());

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
	public void delete(String diet_no ,String food_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, diet_no);
			pstmt.setString(2, food_no);

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
	public List<DietDetailVO> findByPrimaryKey(String diet_no) {

		List<DietDetailVO> list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DIET_STMT);

			pstmt.setString(1, diet_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				DietDetailVO dietdetailVO = new DietDetailVO();
				dietdetailVO.setDiet_no(rs.getString("diet_no"));
				dietdetailVO.setFood_no(rs.getString("food_no"));
				dietdetailVO.setAmount(rs.getDouble("amount"));
				dietdetailVO.setFood_cal(rs.getDouble("food_cal"));
				list.add(dietdetailVO);
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

	public DietDetailVO findOneByPrimaryKey(String diet_no,String food_no) {
		DietDetailVO dietdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con =ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, diet_no);
			pstmt.setString(2, food_no);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dietdetailVO = new DietDetailVO();
				dietdetailVO.setDiet_no(rs.getString("diet_no"));
				dietdetailVO.setFood_no(rs.getString("food_no"));
				dietdetailVO.setAmount(rs.getDouble("amount"));
				dietdetailVO.setFood_cal(rs.getDouble("food_cal"));
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
		return dietdetailVO;
	}
	@Override
	public List<DietDetailVO> getAll() {
		List<DietDetailVO> list = new ArrayList<DietDetailVO>();
		DietDetailVO dietdetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dietdetailVO = new DietDetailVO();
				dietdetailVO.setDiet_no(rs.getString("diet_no"));
				dietdetailVO.setFood_no(rs.getString("food_no"));
				dietdetailVO.setAmount(rs.getDouble("amount"));
				dietdetailVO.setFood_cal(rs.getDouble("food_cal"));
				list.add(dietdetailVO);
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

//	public static void main(String[] args) throws IOException {
//
//		DietDetailDAO dao = new DietDetailDAO();

		/********** 增加 **********/
//		DietDetailVO dietDetailVO1 = new DietDetailVO();
//		dietDetailVO1.setDiet_no("DR000001"); 
//		dietDetailVO1.setFood_no("FN000050");
//		dietDetailVO1.setAmount(20);
//		dietDetailVO1.setFood_cal(50);
//		
//		int updateCount_insert = dao.insert(dietDetailVO1);
//		System.out.println(updateCount_insert);

		/********** 修改 **********/
//		 DietDetailVO dietDetailVO2 = new DietDetailVO();
//		 dietDetailVO2.setAmount(0);
//		 dietDetailVO2.setFood_cal(1);
//		 dietDetailVO2.setDiet_no("DR000001");
//		 dietDetailVO2.setFood_no("FN000050");
//		 int updateCount_update = dao.update(dietDetailVO2);
//		 System.out.println(updateCount_update);

//		/********** 刪除 **********/
//		int updateCount_delete = dao.delete("DR000001","FN000001");
//		System.out.println(updateCount_delete);
//
//		/********** 查詢 **********/
//       List<DietDetailVO> list1 = dao.findByPrimaryKey("DR000001");
//		for (DietDetailVO dietDetailVO3 : list1) {
//        System.out.print(dietDetailVO3.getDiet_no() + ",");
//        System.out.print(dietDetailVO3.getFood_no() + ",");
//        System.out.print(dietDetailVO3.getAmount() + ",");
//        System.out.print(dietDetailVO3.getFood_cal() + ",");
//        
//        System.out.println("\n---------------------");
//		}
//		/********** 查詢 **********/
//		List<DietDetailVO> list = dao.getAll();
//		for (DietDetailVO dietDetailVO4 : list) {
//			System.out.print(dietDetailVO4.getDiet_no() + ",");
//			System.out.print(dietDetailVO4.getFood_no() + ",");
//			System.out.print(dietDetailVO4.getAmount() + ",");
//			System.out.print(dietDetailVO4.getFood_cal() + ",");
//			System.out.println();
//		}
//	}
}