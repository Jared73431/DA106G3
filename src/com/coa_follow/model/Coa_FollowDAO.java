package com.coa_follow.model;

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

public class Coa_FollowDAO implements Coa_FollowDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "insert into coa_follow (coa_no,member_no) values (?,?)";
	private static final String GET_ONE_STMT = "SELECT member_no,coa_no FROM coa_follow where member_no = ? or coa_no = ? ";

	private static final String GET_ALL_STMT = "SELECT member_no,coa_no FROM coa_follow order by member_no ";
	private static final String DELETE = "DELETE FROM coa_follow where member_no = ? and coa_no = ? ";
	
	private static final String CHECK = "SELECT member_no,coa_no FROM coa_follow where member_no = ? and coa_no = ? ";
	@Override

	public void insert(Coa_FollowVO coa_FollowVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, coa_FollowVO.getCoa_no());
			pstmt.setString(2, coa_FollowVO.getMember_no());

			pstmt.execute();
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

	public void delete(String member_no, String coa_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, member_no);
			pstmt.setString(2, coa_no);

			pstmt.executeUpdate();
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
	public List<Coa_FollowVO> findByPrimaryKey(String member_no,String coa_no) {
		List<Coa_FollowVO> list = new ArrayList<Coa_FollowVO>();
		Coa_FollowVO coa_FollowVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, member_no);
			pstmt.setString(2, coa_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				coa_FollowVO = new Coa_FollowVO();
				coa_FollowVO.setMember_no(rs.getString("member_no"));
				coa_FollowVO.setCoa_no(rs.getString("coa_no"));

				list.add(coa_FollowVO);
			}
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
	public List<Coa_FollowVO> getAll() {
		List<Coa_FollowVO> list = new ArrayList<Coa_FollowVO>();
		Coa_FollowVO coa_FollowVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				coa_FollowVO = new Coa_FollowVO();
				coa_FollowVO.setMember_no(rs.getString("member_no"));
				coa_FollowVO.setCoa_no(rs.getString("coa_no"));

				list.add(coa_FollowVO);
			}

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
	
	public int check(String member_no,String coa_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0 ;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECK);
			pstmt.setString(1, member_no);
			pstmt.setString(2, coa_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				++count;
			}

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
		
		return count;
	}
}
