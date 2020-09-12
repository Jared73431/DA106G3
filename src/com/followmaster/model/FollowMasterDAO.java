package com.followmaster.model;

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

import com.mygroup.model.MyGroupDAO;
import com.mygroup.model.MyGroupVO;

public class FollowMasterDAO implements FollowMasterDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO FOLLOW_MASTER (MEMBER_NO, MASTER_NO)" + " VALUES (?,?)";
	private static final String GET_ALL_STMT = "SELECT MEMBER_NO, MASTER_NO FROM FOLLOW_MASTER order by MEMBER_NO";
	private static final String GET_MASTER_STMT = "SELECT MEMBER_NO, MASTER_NO FROM FOLLOW_MASTER"
			+ " where MEMBER_NO = ?";
	private static final String DELETE = "DELETE FROM FOLLOW_MASTER where (MEMBER_NO = ? AND MASTER_NO = ?) ";

	public void insert(FollowMasterVO followMasterVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con =ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, followMasterVO.getMember_no());
			pstmt.setString(2, followMasterVO.getMaster_no());

			pstmt.executeUpdate();

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

	public void delete(String member_no, String master_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, member_no);
			pstmt.setString(2, master_no);
			pstmt.executeUpdate();
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

	public List<FollowMasterVO> getMaster(String member_no) {
		List<FollowMasterVO> list = new ArrayList<FollowMasterVO>();
		FollowMasterVO followMasterVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MASTER_STMT);

			pstmt.setString(1, member_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				followMasterVO = new FollowMasterVO();
				followMasterVO.setMember_no(rs.getString("member_no"));
				followMasterVO.setMaster_no(rs.getString("master_no"));
				list.add(followMasterVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public List<FollowMasterVO> getAll() {
		List<FollowMasterVO> list = new ArrayList<FollowMasterVO>();
		FollowMasterVO followMasterVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				followMasterVO = new FollowMasterVO();
				followMasterVO.setMember_no(rs.getString("member_no"));
				followMasterVO.setMaster_no(rs.getString("master_no"));
				list.add(followMasterVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

//	public static void main(String[] args) {
//
//		FollowMasterDAO dao = new FollowMasterDAO();
//		// 新增
//		FollowMasterVO grVO1 = new FollowMasterVO();
//		grVO1.setMember_no("M000005");
//		grVO1.setMaster_no("M000003");
//		dao.insert(grVO1);
//
//		// 刪除
//		dao.delete("M000005", "M000003");
//		// 查詢全部
//		List<FollowMasterVO> list = dao.getAll();
//		for (FollowMasterVO aGroup : list) {
//			System.out.print(aGroup.getMember_no() + ",");
//			System.out.print(aGroup.getMaster_no() + ",");
//			System.out.println();
//		}
//		System.out.println("=======================");
//		// 查詢會員追蹤對象
//		List<FollowMasterVO> listM = dao.getMaster("M000001");
//		for (FollowMasterVO aGroup : listM) {
//			System.out.print(aGroup.getMember_no() + ",");
//			System.out.print(aGroup.getMaster_no() + ",");
//			System.out.println();
//		}
//	}
}
