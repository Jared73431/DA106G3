package com.foodnutrition.model;

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

public class FoodNutritionDAO implements FoodNutritionDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

	private static final String INSERT_STMT = "INSERT INTO FOOD_NUTRITION (FOOD_NO, FOOD_NAME, REF_BRAND, REF_AMOUNT, REF_CAL, REF_PROTEIN, REF_FAT, REF_CHO) VALUES ('FN'||LPAD(to_char(FOOD_NO_SEQ.NEXTVAL), 6, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM FOOD_NUTRITION order by food_no";
	private static final String GET_ONE_STMT = "SELECT * FROM FOOD_NUTRITION where food_no = ?";
	private static final String DELETE = "DELETE FROM FOOD_NUTRITION where food_no = ?";
	private static final String UPDATE = "UPDATE FOOD_NUTRITION set food_name=?, ref_brand=?, ref_amount=?, ref_cal=?, ref_protein=?, ref_fat=?, ref_cho=? where food_no = ?";

	@Override
	public int insert(FoodNutritionVO foodNutritionVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, foodNutritionVO.getFood_name());
			pstmt.setString(2, foodNutritionVO.getRef_brand());
			pstmt.setString(3, foodNutritionVO.getRef_amount());
			pstmt.setDouble(4, foodNutritionVO.getRef_cal());
			pstmt.setDouble(5, foodNutritionVO.getRef_protein());
			pstmt.setDouble(6, foodNutritionVO.getRef_fat());
			pstmt.setDouble(7, foodNutritionVO.getRef_cho());

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
	public int update(FoodNutritionVO foodNutritionVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, foodNutritionVO.getFood_name());
			pstmt.setString(2, foodNutritionVO.getRef_brand());
			pstmt.setString(3, foodNutritionVO.getRef_amount());
			pstmt.setDouble(4, foodNutritionVO.getRef_cal());
			pstmt.setDouble(5, foodNutritionVO.getRef_protein());
			pstmt.setDouble(6, foodNutritionVO.getRef_fat());
			pstmt.setDouble(7, foodNutritionVO.getRef_cho());
			pstmt.setString(8, foodNutritionVO.getFood_no());

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
	public int delete(String food_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, food_no);

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
	public FoodNutritionVO findByPrimaryKey(String food_no) {

		FoodNutritionVO foodNutritionVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, food_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				foodNutritionVO = new FoodNutritionVO();
				foodNutritionVO.setFood_no(rs.getString("food_no"));
				foodNutritionVO.setFood_name(rs.getString("food_name"));
				foodNutritionVO.setRef_brand(rs.getString("ref_brand"));
				foodNutritionVO.setRef_amount(rs.getString("ref_amount"));
				foodNutritionVO.setRef_cal(rs.getDouble("ref_cal"));
				foodNutritionVO.setRef_protein(rs.getDouble("ref_protein"));
				foodNutritionVO.setRef_fat(rs.getDouble("ref_fat"));
				foodNutritionVO.setRef_cho(rs.getDouble("ref_cho"));
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
		return foodNutritionVO;
	}

	@Override
	public List<FoodNutritionVO> getAll() {
		List<FoodNutritionVO> list = new ArrayList<FoodNutritionVO>();
		FoodNutritionVO foodNutritionVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				foodNutritionVO = new FoodNutritionVO();
				foodNutritionVO.setFood_no(rs.getString("food_no"));
				foodNutritionVO.setFood_name(rs.getString("food_name"));
				foodNutritionVO.setRef_brand(rs.getString("ref_brand"));
				foodNutritionVO.setRef_amount(rs.getString("ref_amount"));
				foodNutritionVO.setRef_cal(rs.getDouble("ref_cal"));
				foodNutritionVO.setRef_protein(rs.getDouble("ref_protein"));
				foodNutritionVO.setRef_fat(rs.getDouble("ref_fat"));
				foodNutritionVO.setRef_cho(rs.getDouble("ref_cho"));
				list.add(foodNutritionVO);
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
//		FoodNutritionJDBCDAO dao = new FoodNutritionJDBCDAO();

		/********** 增加 **********/
//		FoodNutritionVO foodNutritionVO1 = new FoodNutritionVO();
//		foodNutritionVO1.setFood_name("雞塊"); 
//		foodNutritionVO1.setRef_brand("肯德基");
//		foodNutritionVO1.setRef_amount("200g");
//		foodNutritionVO1.setRef_cal(50.55);
//		foodNutritionVO1.setRef_protein(50.55);
//		foodNutritionVO1.setRef_fat(50.55);
//		foodNutritionVO1.setRef_cho(50.33);
//		
//		int updateCount_insert = dao.insert(foodNutritionVO1);
//		System.out.println(updateCount_insert);

		/********** 修改 **********/
//		FoodNutritionVO foodNutritionVO2 = new FoodNutritionVO();
//		foodNutritionVO2.setFood_name("雞塊");
//		foodNutritionVO2.setRef_brand("肯德基");
//		foodNutritionVO2.setRef_amount("200g");
//		foodNutritionVO2.setRef_cal(50.55);
//		foodNutritionVO2.setRef_protein(50.55);
//		foodNutritionVO2.setRef_fat(50.55);
//		foodNutritionVO2.setRef_cho(50.33);
//		foodNutritionVO2.setFood_no("FN000001");
//		int updateCount_update = dao.update(foodNutritionVO2);
//		System.out.println(updateCount_update);

//		/********** 刪除 **********/
//		int updateCount_delete = dao.delete("FN000004");
//		System.out.println(updateCount_delete);
//
//		/********** 查詢 **********/
//        FoodNutritionVO foodNutritionVO3 = dao.findByPrimaryKey("FN000002");
//        System.out.print(foodNutritionVO3.getFood_no() + ",");
//        System.out.print(foodNutritionVO3.getFood_name() + ",");
//        System.out.print(foodNutritionVO3.getRef_brand() + ",");
//        System.out.print(foodNutritionVO3.getRef_amount() + ",");
//        System.out.print(foodNutritionVO3.getRef_cal() + ",");
//        System.out.print(foodNutritionVO3.getRef_protein() + ",");
//        System.out.print(foodNutritionVO3.getRef_fat() + ",");
//        System.out.print(foodNutritionVO3.getRef_cho());
//        
//        System.out.println("\n---------------------");

//		/********** 查詢 **********/
//		List<FoodNutritionVO> list = dao.getAll();
//		for (FoodNutritionVO foodNutritionVO4 : list) {
//			System.out.print(foodNutritionVO4.getFood_no() + ",");
//	        System.out.print(foodNutritionVO4.getFood_name() + ",");
//	        System.out.print(foodNutritionVO4.getRef_brand() + ",");
//	        System.out.print(foodNutritionVO4.getRef_amount() + ",");
//	        System.out.print(foodNutritionVO4.getRef_cal() + ",");
//	        System.out.print(foodNutritionVO4.getRef_protein() + ",");
//	        System.out.print(foodNutritionVO4.getRef_fat() + ",");
//	        System.out.print(foodNutritionVO4.getRef_cho());
//	        System.out.println();
//		}
//
//	}
}