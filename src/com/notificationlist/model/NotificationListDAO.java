package com.notificationlist.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class NotificationListDAO implements NotificationListDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO notification_list(note_no,member_no,category_no,note_content,note_status)values(('NL'||LPAD(to_char(NOTIFICATION_SEQ.nextval),4,'0')),?,?,?,0)";
	private static final String GET_ALL_STMT = "SELECT note_no,member_no,category_no,note_content,note_status,note_date FROM notification_list order by note_date desc ";
	private static final String GET_ONE_STMT = "SELECT note_no,member_no,category_no,note_content,note_status,note_date FROM notification_list where note_no = ?";
	private static final String DELETE = "DELETE FROM notification_list where note_no = ? ";
	private static final String UPDATE = "UPDATE notification_list set member_no=?,category_no=?,note_content=?,note_status=1 where note_no=?";
	private static final String READED = "UPDATE notification_list set note_status=1 where note_no = ?";
	
	@Override
	public void insert(NotificationListVO notificationListVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, notificationListVO.getMember_no());
			pstmt.setString(2, notificationListVO.getCategory_no());
			pstmt.setString(3, notificationListVO.getNote_content());

			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
	public void update(NotificationListVO notificationListVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, notificationListVO.getMember_no());
			pstmt.setString(2, notificationListVO.getCategory_no());
			pstmt.setString(3, notificationListVO.getNote_content());
			pstmt.setString(4, notificationListVO.getNote_no());
			pstmt.executeUpdate();

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
	}

	@Override
	public void delete(String note_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, note_no);

			pstmt.executeUpdate();

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

	}

	@Override
	public NotificationListVO findByPrimaryKey(String note_no) {
		// TODO Auto-generated method stub
		NotificationListVO notificationListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, note_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// answerVO 也稱為 Domain objects
				notificationListVO = new NotificationListVO();
				notificationListVO.setMember_no(rs.getString("member_no"));
				notificationListVO.setNote_no(rs.getString("note_no"));
				notificationListVO.setCategory_no(rs.getString("category_no"));
				notificationListVO.setNote_content(rs.getString("note_content"));
				notificationListVO.setNote_status(rs.getInt("note_status"));
				notificationListVO.setNote_date(rs.getDate("note_date"));
			}

			// Handle any driver errors
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
		return notificationListVO;
	}

	@Override
	public List<NotificationListVO> getAll() {
		// TODO Auto-generated method stub
		List<NotificationListVO> list = new ArrayList<NotificationListVO>();
		NotificationListVO notificationListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				notificationListVO = new NotificationListVO();
				notificationListVO.setMember_no(rs.getString("member_no"));
				notificationListVO.setNote_no(rs.getString("note_no"));
				notificationListVO.setCategory_no(rs.getString("category_no"));
				notificationListVO.setNote_content(rs.getString("note_content"));
				notificationListVO.setNote_status(rs.getInt("note_status"));
				notificationListVO.setNote_date(rs.getDate("note_date"));
				list.add(notificationListVO); // Store the row in the list
			}

			// Handle any driver errors
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
	public List<NotificationListVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		List<NotificationListVO> list = new ArrayList<NotificationListVO>();
		NotificationListVO noteVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			
			con = ds.getConnection();
			String finalSQL = "select * FROM notification_list order by note_date desc";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = "+finalSQL);
			
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				noteVO = new NotificationListVO();
				noteVO.setNote_no(rs.getString("note_no"));
				noteVO.setMember_no(rs.getString("member_no"));
				noteVO.setCategory_no(rs.getString("category_no"));
				noteVO.setNote_content(rs.getString("note_content"));
				noteVO.setNote_status(rs.getInt("note_status"));
				noteVO.setNote_date(rs.getDate("note_date"));
				list.add(noteVO); // Store the row in the List
			}
	
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void read(String note_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(READED);

			pstmt.setString(1, note_no);

			pstmt.executeUpdate();

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
		
	}

}
