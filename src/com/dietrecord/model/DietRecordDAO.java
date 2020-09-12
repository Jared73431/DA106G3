package com.dietrecord.model;

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

import com.dietdetail.model.DietDetailDAO;
import com.dietdetail.model.DietDetailVO;

public class DietRecordDAO implements DietRecordDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO Diet_Record (diet_no,member_no,rec_date,eat_period,photo) VALUES ('DR'||LPAD(to_char(DIET_NO_SEQ.NEXTVAL), 6, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM Diet_Record order by diet_no";
	private static final String GET_ONE_STMT = "SELECT * FROM Diet_Record where diet_no = ?";
	private static final String DELETE = "DELETE FROM Diet_Record where diet_no = ?";
	private static final String UPDATE = "UPDATE Diet_Record set member_no=?, rec_date=?, eat_period=?, photo=? where diet_no = ?";
	private static final String GET_DATE_STMT = "SELECT * FROM Diet_Record where MEMBER_NO =? AND REC_DATE = ? order by eat_period";
	private static final String DELETE_PIC = "Update Diet_Record set PHOTO = null where diet_no = ?";

	@Override
	public int insert(DietRecordVO dietRecordVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			// pstmt.setString(1, dietRecordVO.getDiet_no());
			pstmt.setString(1, dietRecordVO.getMember_no());
			pstmt.setDate(2, dietRecordVO.getRec_date());
			pstmt.setInt(3, dietRecordVO.getEat_period());
			pstmt.setBytes(4, dietRecordVO.getPhoto());

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
	public int update(DietRecordVO dietRecordVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, dietRecordVO.getMember_no());
			pstmt.setDate(2, dietRecordVO.getRec_date());
			pstmt.setInt(3, dietRecordVO.getEat_period());
			if(dietRecordVO.getPhoto().length == 0) {
				DietRecordDAO dao = new DietRecordDAO();
				pstmt.setBytes(4,dao.findByPrimaryKey(dietRecordVO.getDiet_no()).getPhoto());
			}else {
				pstmt.setBytes(4, dietRecordVO.getPhoto());
			}
		
			pstmt.setString(5, dietRecordVO.getDiet_no());

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
	
	public void delete_pic (String diet_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_PIC);
			pstmt.setString(1, diet_no);			
			updateCount = pstmt.executeUpdate();
		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public int delete(String diet_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, diet_no);

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
	public DietRecordVO findByPrimaryKey(String diet_no) {

		DietRecordVO dietRecordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, diet_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				dietRecordVO = new DietRecordVO();
				dietRecordVO.setDiet_no(rs.getString("diet_no"));
				dietRecordVO.setMember_no(rs.getString("member_no"));
				dietRecordVO.setRec_date(rs.getDate("rec_date"));
				dietRecordVO.setEat_period(rs.getInt("eat_period"));
				dietRecordVO.setPhoto(rs.getBytes("photo"));
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
		return dietRecordVO;
	}

	@Override
	public List<DietRecordVO> getAll() {
		List<DietRecordVO> list = new ArrayList<DietRecordVO>();
		DietRecordVO dietRecordVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dietRecordVO = new DietRecordVO();
				dietRecordVO.setDiet_no(rs.getString("diet_no"));
				dietRecordVO.setMember_no(rs.getString("member_no"));
				dietRecordVO.setRec_date(rs.getDate("rec_date"));
				dietRecordVO.setEat_period(rs.getInt("eat_period"));
				dietRecordVO.setPhoto(rs.getBytes("photo"));
				list.add(dietRecordVO); // Store the row in the vector
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
	
	public List findByDate(String member_no, java.sql.Date rec_date) {
		List<DietRecordVO> list = new ArrayList<DietRecordVO>();
		DietRecordVO dietRecordVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DATE_STMT);

			pstmt.setString(1, member_no);
			pstmt.setDate(2, rec_date);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dietRecordVO = new DietRecordVO();
				dietRecordVO.setDiet_no(rs.getString("diet_no"));
				dietRecordVO.setMember_no(rs.getString("member_no"));
				dietRecordVO.setRec_date(rs.getDate("rec_date"));
				dietRecordVO.setEat_period(rs.getInt("eat_period"));
				dietRecordVO.setPhoto(rs.getBytes("photo"));
				list.add(dietRecordVO);
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
	
	public void insertWithDetail(DietRecordVO dietRecordVO , List<DietDetailVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			String cols[] = {"diet_no"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, dietRecordVO.getMember_no());
			pstmt.setDate(2, dietRecordVO.getRec_date());
			pstmt.setInt(3, dietRecordVO.getEat_period());
			pstmt.setBytes(4, dietRecordVO.getPhoto());
			pstmt.executeQuery();
			
			String next_diet_no = null;
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				next_diet_no = rs.getString(1);
				System.out.println("自增主鍵值= " + next_diet_no);
			}else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			DietDetailDAO dao = new DietDetailDAO();
			for(DietDetailVO dietdetail : list) {
				dietdetail.setDiet_no(next_diet_no);
				dao.insert2(dietdetail,con);
			}
			con.commit();
			con.setAutoCommit(true);
			// Handle any driver errors

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dietRecord");
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	}

//	public static byte[] getPictureByteArray(String path) throws IOException {
//		File file = new File(path);
//		FileInputStream fis = new FileInputStream(file);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] buffer = new byte[8192];
//		int i;
//		while ((i = fis.read(buffer)) != -1) {
//			baos.write(buffer, 0, i);
//		}
//		baos.close();
//		fis.close();
//
//		return baos.toByteArray();
//	}

//	public static void main(String[] args) throws IOException {
//
//		DietRecordDAO dao = new DietRecordDAO();

		/********** 增加 **********/
//		DietRecordVO dietRecordVO1 = new DietRecordVO();
//		dietRecordVO1.setMember_no("M000001"); // FK
//		dietRecordVO1.setEat_period(0);
//		dietRecordVO1.setRec_date(java.sql.Date.valueOf("2020-03-29"));
//		dietRecordVO1.setPhoto(getPictureByteArray("images/DietRecord_photo01.jpg"));
//
//		int updateCount_insert = dao.insert(dietRecordVO1);
//		System.out.println(updateCount_insert);
//
////		/********** 修改 **********/
//		 DietRecordVO dietRecordVO2 = new DietRecordVO();
//		 dietRecordVO2.setMember_no("M000001");
//		 dietRecordVO2.setRec_date(java.sql.Date.valueOf("2020-03-25"));
//		 dietRecordVO2.setEat_period(0);
//		 dietRecordVO2.setPhoto(null);
//		 dietRecordVO2.setDiet_no("4");
//		 int updateCount_update = dao.update(dietRecordVO2);
//		 System.out.println(updateCount_update);

		/********** 刪除 **********/
//		int updateCount_delete = dao.delete("3");
//		System.out.println(updateCount_delete);
//
//		/********** 查詢 **********/
//        DietRecordVO dietRecordVO3 = dao.findByPrimaryKey("DR000011");
//        System.out.print(dietRecordVO3.getDiet_no() + ",");
//        System.out.print(dietRecordVO3.getMember_no() + ",");
//        System.out.print(dietRecordVO3.getRec_date() + ",");
//        System.out.print(dietRecordVO3.getEat_period() + ",");
//        
//        FileOutputStream fos = new FileOutputStream("output/DietRecord02.jpg");
//        fos.write(dietRecordVO3.getPhoto());
//        fos.close();
//        fos.close();
//        System.out.print(dietRecordVO3.getPhoto() + ",");
//        System.out.println("\n---------------------");
//
//		/********** 查詢 **********/
//		List<DietRecordVO> list = dao.getAll();
//		for (DietRecordVO dietRecord : list) {
//			System.out.print(dietRecord.getDiet_no() + ",");
//			System.out.print(dietRecord.getMember_no() + ",");
//			System.out.print(dietRecord.getRec_date() + ",");
//			System.out.print(dietRecord.getEat_period() + ",");
//			System.out.print(dietRecord.getPhoto() + ",");
//			System.out.println();
//		}
//
//	}
//}