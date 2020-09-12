package com.groupchat.model;

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

public class GroupChatDAO implements GroupChatDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO group_chat(grchat_no,groupgo_id,chatmen_id,chat_con)Values(('GC'||LPAD(to_char(GROUPCHAT_SEQ.nextval),6,'0')),?,?,?)";
	private static final String GET_ALL_STMT = "SELECT groupgo_id,chatmen_id,chat_con,chat_time FROM group_chat order by chat_time where groupgo_id?";
	private static final String GET_ONE_STMT = "SELECT groupgo_id,chatmen_id,chat_con,chat_time FROM group_chat order by chat_time where chatmen_id = ?";
	private static final String DELETE = "DELETE FROM group_chat where grchat_no = ? ";
	private static final String UPDATE = "UPDATE group_chat set groupgo_id=?,chatmen_id=?,chat_con=? where grchat_no=?";

	@Override
	public void insert(GroupChatVO groupChatVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, groupChatVO.getGroupgo_id());
			pstmt.setString(2, groupChatVO.getChatmen_id());
			pstmt.setString(3, groupChatVO.getChat_con());
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
	public void update(GroupChatVO groupChatVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, groupChatVO.getGroupgo_id());
			pstmt.setString(2, groupChatVO.getChatmen_id());
			pstmt.setString(3, groupChatVO.getChat_con());
//			pstmt.setString(4, groupChatVO.getGrchat_no());
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
	public void delete(String grchat_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, grchat_no);

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
	public GroupChatVO findByPrimaryKey(String grchat_no) {
		// TODO Auto-generated method stub
		GroupChatVO groupChatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, grchat_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// answerVO 也稱為 Domain objects
				groupChatVO = new GroupChatVO();
				groupChatVO.setChatmen_id(rs.getString("chatmen_id"));
//				groupChatVO.setGrchat_no(rs.getString("grchat_no"));
				groupChatVO.setGroupgo_id(rs.getString("groupgo_id"));
				groupChatVO.setChat_con(rs.getString("chat_con"));
				
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
		return groupChatVO;
	}

	@Override
	public List<GroupChatVO> getAll() {
		// TODO Auto-generated method stub
		List<GroupChatVO> list = new ArrayList<GroupChatVO>();
		GroupChatVO groupChatVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				groupChatVO = new GroupChatVO();
//				groupChatVO.setGrchat_no(rs.getString("grchat_no"));
				groupChatVO.setChatmen_id(rs.getString("chatmen_id"));
				groupChatVO.setGroupgo_id(rs.getString("groupgo_id"));
				groupChatVO.setChat_con(rs.getString("chat_con"));
				
				list.add(groupChatVO); // Store the row in the list
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
}
