package com.mygroup.model;

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

public class MyGroupDAO implements MyGroupDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MY_GROUP (GROUPGO_ID, MEMBER_NO)"
			+ " VALUES (?,?)";
	private static final String GET_ALL_STMT = "SELECT  GROUPGO_ID, MEMBER_NO, MYGROUP_STATUS, SCORE FROM MY_GROUP order by MEMBER_NO";
	private static final String GET_GROUP_STMT = "SELECT GROUPGO_ID, MEMBER_NO, MYGROUP_STATUS, SCORE FROM MY_GROUP"
			+ " where GROUPGO_ID = ?";
	private static final String GET_MEMBER_STMT = "SELECT GROUPGO_ID, MEMBER_NO, MYGROUP_STATUS, SCORE FROM MY_GROUP"
			+ " where MEMBER_NO = ? order by MYGROUP_STATUS";
	private static final String DELETE = "DELETE FROM MY_GROUP where (MEMBER_NO = ? AND GROUPGO_ID = ?) ";
	private static final String UPDATE_STATUS = "UPDATE MY_GROUP set MYGROUP_STATUS=? where (GROUPGO_ID = ? AND MEMBER_NO = ?) ";
	private static final String UPDATE_SCORE = "UPDATE MY_GROUP set SCORE=? where (GROUPGO_ID = ? AND MEMBER_NO = ?) ";

	// 新增
	public void insert(MyGroupVO myGroupVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, myGroupVO.getGroupgo_id());
			pstmt.setString(2, myGroupVO.getMember_no());
	
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

	// 更改評分
	public void updateScore(MyGroupVO myGroupVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_SCORE);
			pstmt.setDouble(1, myGroupVO.getScore());
			pstmt.setString(2, myGroupVO.getGroupgo_id());
			pstmt.setString(3, myGroupVO.getMember_no());
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

	// 更改狀態
	public void updateStatus(MyGroupVO myGroupVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);
			pstmt.setInt(1, myGroupVO.getMygroup_status());
			pstmt.setString(2, myGroupVO.getGroupgo_id());
			pstmt.setString(3, myGroupVO.getMember_no());
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

	// 刪除
	public void delete(String member_no, String groupgo_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, member_no);
			pstmt.setString(2, groupgo_id);

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

	// 拿全部
	public List<MyGroupVO> getAll() {
		List<MyGroupVO> list = new ArrayList<MyGroupVO>();
		MyGroupVO myGroupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				myGroupVO = new MyGroupVO();
				myGroupVO.setGroupgo_id(rs.getString("groupgo_id"));
				myGroupVO.setMember_no(rs.getString("member_no"));
				myGroupVO.setMygroup_status(rs.getInt("mygroup_status"));
				myGroupVO.setScore(rs.getInt("score"));
				list.add(myGroupVO);
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

	// 拿會員參與的揪團
	public List<MyGroupVO> getMemberAttend(String member_no) {
		List<MyGroupVO> list = new ArrayList<MyGroupVO>();
		MyGroupVO myGroupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MEMBER_STMT);

			pstmt.setString(1, member_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				myGroupVO = new MyGroupVO();
				myGroupVO.setGroupgo_id(rs.getString("groupgo_id"));
				myGroupVO.setMember_no(rs.getString("member_no"));
				myGroupVO.setMygroup_status(rs.getInt("mygroup_status"));
				myGroupVO.setScore(rs.getInt("score"));
				list.add(myGroupVO);
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

	// 拿揪團裡的成員
	public List<MyGroupVO> getGroupAttend(String groupgo_id) {
		List<MyGroupVO> list = new ArrayList<MyGroupVO>();
		MyGroupVO myGroupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_GROUP_STMT);
			pstmt.setString(1, groupgo_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				myGroupVO = new MyGroupVO();
				myGroupVO.setGroupgo_id(rs.getString("groupgo_id"));
				myGroupVO.setMember_no(rs.getString("member_no"));
				myGroupVO.setMygroup_status(rs.getInt("mygroup_status"));
				myGroupVO.setScore(rs.getInt("score"));
				list.add(myGroupVO);
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
//		MyGroupJDBCDAO dao = new MyGroupJDBCDAO();
//
//		// 新增
//		MyGroupVO grVO1 = new MyGroupVO();
//		grVO1.setGroupgo_id("G000001");
//		grVO1.setMember_no("M000005");
//		grVO1.setMygroup_status(3);
//		dao.insert(grVO1);
//		//修改評分
//		MyGroupVO grVO2 = new MyGroupVO();
//		grVO2.setScore(10);
//		grVO2.setGroupgo_id("G000001");
//		grVO2.setMember_no("M000005");
//		dao.updateScore(grVO2);
//		//修改狀態
//		MyGroupVO grVO3 = new MyGroupVO();
//		grVO3.setMygroup_status(3);
//		grVO3.setGroupgo_id("G000001");
//		grVO3.setMember_no("M000005");
//		dao.updateStatus(grVO3);
//		//刪除
//		dao.delete("M000005","G000001");
//		//查詢全部
//		List<MyGroupVO> list = dao.getAll();
//		for (MyGroupVO aGroup : list) {
//			System.out.print(aGroup.getGroupgo_id() + ",");
//			System.out.print(aGroup.getMember_no() + ",");
//			System.out.print(aGroup.getMygroup_status() + ",");
//			System.out.print(aGroup.getScore() + ",");
//			System.out.println();
//		}
//		System.out.println("=======================");
//		// 查詢會員參與的揪團
//		List<MyGroupVO> list = dao.getMemberAttend("M000001");
//		for (MyGroupVO aGroup : list) {
//			System.out.print(aGroup.getGroupgo_id() + ",");
//			System.out.print(aGroup.getMember_no() + ",");
//			System.out.print(aGroup.getMygroup_status() + ",");
//			System.out.print(aGroup.getScore() + ",");
//			System.out.println();
//		}
//		System.out.println("=======================");
//		// 查詢揪團裡的會員
//		List<MyGroupVO> listG = dao.getGroupAttend("G000001");
//		for (MyGroupVO aGroupG : listG) {
//			System.out.print(aGroupG.getGroupgo_id() + ",");
//			System.out.print(aGroupG.getMember_no() + ",");
//			System.out.print(aGroupG.getMygroup_status() + ",");
//			System.out.print(aGroupG.getScore() + ",");
//			System.out.println();
//		}
//	}
}
