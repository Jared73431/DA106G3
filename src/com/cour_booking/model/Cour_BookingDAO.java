package com.cour_booking.model;

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

public class Cour_BookingDAO implements Cour_BookingDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "insert into cour_booking (booking_no,cour_no,coa_no,member_no,choose_mode) values (('CB'||LPAD(to_char(COURSE_BOOKING_SEQ.NEXTVAL), 6, '0')),?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT booking_no,cour_no,member_no,coa_no,coa_comf,trainee_comf,coa_ci,trainee_ci,cour_score,cour_option,choose_mode FROM Cour_Booking where booking_no = ? or cour_no = ? or coa_no = ? or member_no = ?";
	private static final String GET_ONE_STMTBYBOOKIN_NO = "SELECT booking_no,cour_no,member_no,coa_no,coa_comf,trainee_comf,coa_ci,trainee_ci,cour_score,cour_option,choose_mode FROM Cour_Booking where booking_no = ? ";

	private static final String GET_ALL_STMT = "SELECT booking_no,cour_no,member_no,coa_no,coa_comf,trainee_comf,coa_ci,trainee_ci,cour_score,cour_option,choose_mode FROM Cour_Booking order by booking_no";
	private static final String UPDATE = "UPDATE cour_booking set cour_no = ?,coa_no = ?,member_no = ?,coa_comf = ?,trainee_comf = ?,coa_ci = ?,trainee_ci = ?,cour_score = ?,cour_option = ?,choose_mode = ? where booking_no = ?";
	
	private static final String COACHCHECKIN="UPDATE cour_booking set coa_ci = 1 where booking_no = ?";
	private static final String TRAINEECHECKIN="UPDATE cour_booking set trainee_ci = 1 where booking_no = ?";
	
	private static final String CHANGESTATUS="UPDATE course set cour_status = ? where cour_no = ?";
	private static final String COMFCHANGE="UPDATE cour_booking set coa_comf = ? , trainee_comf = ? where booking_no = ?";
	
	private static final String UPDATE_REAL_BLANCE =
			"UPDATE MEMBERS SET REAL_BLANCE=? WHERE MEMBER_NO=?";
	private static final String INSERT_TRANS = 
			"INSERT INTO TRANSACTIONS (TRANS_NO, MEMBER_NO, DEPOSIT, WITHDRAW, REMARK) VALUES (('T'||LPAD(to_char(member_seq.NEXTVAL),6,'0')),?,?,?,?)";
	@Override
	public void insert(Cour_BookingVO cour_BookingVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,cour_BookingVO.getCour_no());
			pstmt.setString(2,cour_BookingVO.getCoa_no());
			pstmt.setString(3,cour_BookingVO.getMember_no());
			pstmt.setInt(4, cour_BookingVO.getChoose_mode());
			
			pstmt.execute();
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	}
	@Override
	public void update(Cour_BookingVO cour_BookingVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,cour_BookingVO.getCour_no());
			pstmt.setString(2,cour_BookingVO.getCoa_no());
			pstmt.setString(3,cour_BookingVO.getMember_no());
			pstmt.setInt(4,cour_BookingVO.getCoa_comf());
			pstmt.setInt(5,cour_BookingVO.getTrainee_comf());
			pstmt.setInt(6,cour_BookingVO.getCoa_ci());
			pstmt.setInt(7,cour_BookingVO.getTrainee_ci());
			pstmt.setInt(8,cour_BookingVO.getCour_score());
			pstmt.setString(9,cour_BookingVO.getCour_option());
			pstmt.setInt(10, cour_BookingVO.getChoose_mode());
			pstmt.setString(11,cour_BookingVO.getBooking_no());
			pstmt.execute();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	}
	@Override
	public void coachCheckin(String booking_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(COACHCHECKIN);
			
			pstmt.setString(1,booking_no);

			pstmt.execute();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	}
	@Override
	public void passForCourseStatus(String cour_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGESTATUS);
			
			pstmt.setInt(1, 4);
			pstmt.setString(2,cour_no);

			pstmt.execute();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	}
	@Override
	public void traineeCheckin(String booking_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(TRAINEECHECKIN);
			
			pstmt.setString(1,booking_no);

			pstmt.execute();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	}
	@Override
	public void comfOnchange(String booking_no,Integer coa_comf,Integer trainee_comf,String cour_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(COMFCHANGE);
		
			pstmt.setInt(1, coa_comf);
			pstmt.setInt(2, trainee_comf);
			pstmt.setString(3,booking_no);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(CHANGESTATUS);
			
			pstmt.setInt(1, 1);
			pstmt.setString(2, cour_no);
			pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
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
	}
	@Override
	public List<Cour_BookingVO> findByPrimaryKey(String booking_no,String cour_no ,String coa_no ,String member_no) {
		List<Cour_BookingVO> list = new ArrayList<Cour_BookingVO>();
		Cour_BookingVO cour_BookingVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, booking_no);
			pstmt.setString(2, cour_no);
			pstmt.setString(3, coa_no);
			pstmt.setString(4, member_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cour_BookingVO = new Cour_BookingVO();
				cour_BookingVO.setBooking_no(rs.getString("booking_no"));
				cour_BookingVO.setCour_no(rs.getString("cour_no"));
				cour_BookingVO.setMember_no(rs.getString("member_no"));
				cour_BookingVO.setCoa_no(rs.getString("coa_no"));
				cour_BookingVO.setCoa_comf(rs.getInt("coa_comf"));
				cour_BookingVO.setTrainee_comf(rs.getInt("trainee_comf"));
				cour_BookingVO.setCoa_ci(rs.getInt("coa_ci"));
				cour_BookingVO.setTrainee_ci(rs.getInt("trainee_ci"));
				cour_BookingVO.setCour_score(rs.getInt("cour_score"));
				cour_BookingVO.setCour_option(rs.getString("cour_option"));
				cour_BookingVO.setChoose_mode(rs.getInt("choose_mode"));
				list.add(cour_BookingVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public Cour_BookingVO findByPrimaryKey(String booking_no) {
		
		Cour_BookingVO cour_BookingVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMTBYBOOKIN_NO);
			pstmt.setString(1, booking_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				cour_BookingVO = new Cour_BookingVO();
				cour_BookingVO.setBooking_no(rs.getString("booking_no"));
				cour_BookingVO.setCour_no(rs.getString("cour_no"));
				cour_BookingVO.setMember_no(rs.getString("member_no"));
				cour_BookingVO.setCoa_no(rs.getString("coa_no"));
				cour_BookingVO.setCoa_comf(rs.getInt("coa_comf"));
				cour_BookingVO.setTrainee_comf(rs.getInt("trainee_comf"));
				cour_BookingVO.setCoa_ci(rs.getInt("coa_ci"));
				cour_BookingVO.setTrainee_ci(rs.getInt("trainee_ci"));
				cour_BookingVO.setCour_score(rs.getInt("cour_score"));
				cour_BookingVO.setCour_option(rs.getString("cour_option"));
				cour_BookingVO.setChoose_mode(rs.getInt("choose_mode"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return cour_BookingVO;
	}
	@Override
	public List<Cour_BookingVO> getAll() {
		List<Cour_BookingVO> list = new ArrayList<Cour_BookingVO>();
		Cour_BookingVO cour_BookingVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cour_BookingVO = new Cour_BookingVO();
				cour_BookingVO.setBooking_no(rs.getString("booking_no"));
				cour_BookingVO.setCour_no(rs.getString("cour_no"));
				cour_BookingVO.setMember_no(rs.getString("member_no"));
				cour_BookingVO.setCoa_no(rs.getString("coa_no"));
				cour_BookingVO.setCoa_comf(rs.getInt("coa_comf"));
				cour_BookingVO.setTrainee_comf(rs.getInt("trainee_comf"));
				cour_BookingVO.setCoa_ci(rs.getInt("coa_ci"));
				cour_BookingVO.setTrainee_ci(rs.getInt("trainee_ci"));
				cour_BookingVO.setCour_score(rs.getInt("cour_score"));
				cour_BookingVO.setCour_option(rs.getString("cour_option"));
				cour_BookingVO.setChoose_mode(rs.getInt("choose_mode"));
				list.add(cour_BookingVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
	public boolean refund(String member_no,Integer real_blance,Integer deposit,Integer withdraw,String remark) {
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
