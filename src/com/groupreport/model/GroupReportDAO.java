package com.groupreport.model;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mygroup.model.MyGroupDAO;
import com.mygroup.model.MyGroupVO;

public class GroupReportDAO implements GroupReportDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO GROUP_REPORT (GROUPGO_ID, MEMBER_NO, REPORT_CONTENT,REPORT_STATUS)"
			+ " VALUES (?,?,?,1)";
	private static final String GET_ALL = "SELECT GROUPGO_ID, MEMBER_NO, REPORT_DATE, REPORT_CONTENT, REPORT_STATUS from GROUP_REPORT order by GROUPGO_ID";
	private static final String GET_UNDO = "SELECT GROUPGO_ID, MEMBER_NO, REPORT_DATE, REPORT_CONTENT, REPORT_STATUS from GROUP_REPORT"
			+ " where REPORT_STATUS = ?";
	private static final String UPDATE = "UPDATE GROUP_REPORT set REPORT_STATUS=? where (GROUPGO_ID = ? AND MEMBER_NO = ?) ";
	private static final String UPDATE_STATUS = "UPDATE GROUP_REPORT set REPORT_STATUS=? where GROUPGO_ID = ? ";

	// 新增
	public void insert(GroupReportVO groupReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, groupReportVO.getGroupgo_id());
			pstmt.setString(2, groupReportVO.getMember_no());
//			pstmt.setTimestamp(3, groupReportVO.getReport_date());
			pstmt.setString(3, groupReportVO.getReport_content());
//			pstmt.setInt(4, groupReportVO.getReport_status());
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

	// 取全部
	public List<GroupReportVO> getAll() {
		List<GroupReportVO> list = new ArrayList<GroupReportVO>();
		GroupReportVO groupReportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupReportVO = new GroupReportVO();
				groupReportVO.setGroupgo_id(rs.getString("groupgo_id"));
				groupReportVO.setMember_no(rs.getString("member_no"));
				groupReportVO.setReport_date(rs.getTimestamp("report_date"));
				groupReportVO.setReport_content(rs.getString("report_content"));
				groupReportVO.setReport_status(rs.getInt("report_status"));
				list.add(groupReportVO);
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

	// 取還沒處理的
	public List<GroupReportVO> getStatus(Integer reportStatus) {
		List<GroupReportVO> list = new ArrayList<GroupReportVO>();
		GroupReportVO groupReportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_UNDO);

			pstmt.setInt(1, reportStatus);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupReportVO = new GroupReportVO();
				groupReportVO.setGroupgo_id(rs.getString("groupgo_id"));
				groupReportVO.setMember_no(rs.getString("member_no"));
				groupReportVO.setReport_date(rs.getTimestamp("report_date"));
				groupReportVO.setReport_content(rs.getString("report_content"));
				groupReportVO.setReport_status(rs.getInt("report_status"));
				list.add(groupReportVO);
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

	// 更新
	public void update(GroupReportVO groupReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, groupReportVO.getReport_status());
			pstmt.setString(2, groupReportVO.getGroupgo_id());
			pstmt.setString(3, groupReportVO.getMember_no());
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
	
	 public void updateStatus(String groupgo_id, Integer report_status) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_STATUS);
				pstmt.setInt(1, report_status);
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

//	public static void main(String[] args) {
//
//		GroupReportJDBCDAO dao = new GroupReportJDBCDAO();
//		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		// 新增
//		GroupReportVO grVO1 = new GroupReportVO();
//		grVO1.setGroupgo_id("G000002");
//		grVO1.setMember_no("M000002");
//		//grVO1.setReport_date(new java.sql.Timestamp(System.currentTimeMillis()));
//		grVO1.setReport_content("爛");
//		grVO1.setReport_status(2);
//		dao.insert(grVO1);
//		
//		//查全部
//		List<GroupReportVO> list = dao.getAll();
//		for (GroupReportVO aGroup : list) {
//			System.out.print(aGroup.getGroupgo_id() + ",");
//			System.out.print(aGroup.getMember_no() + ",");
//			
//			System.out.print(sdf3.format(aGroup.getReport_date())+ ",");
//			
//			System.out.print(aGroup.getReport_content() + ",");
//			System.out.print(aGroup.getReport_status() + ",");
//			System.out.println();
//		}
//		//查還沒處理的
//		List<GroupReportVO> listUN = dao.getStatus(1);
//		for (GroupReportVO aGroup : listUN) {
//			System.out.print(aGroup.getGroupgo_id() + ",");
//			System.out.print(aGroup.getMember_no() + ",");
//			System.out.print(sdf3.format(aGroup.getReport_date()) + ",");
//			System.out.print(aGroup.getReport_content() + ",");
//			System.out.print(aGroup.getReport_status() + ",");
//			System.out.println();
//		}
//		//修改狀態
//		GroupReportVO grVO2 = new GroupReportVO();
//		grVO2.setReport_status(2);
//		grVO2.setGroupgo_id("G000001");
//		grVO2.setMember_no("M000002");
//		dao.update(grVO2);
//
//	}
}
