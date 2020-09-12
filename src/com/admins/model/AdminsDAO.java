package com.admins.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdminsDAO implements AdminsDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "insert into Admins (admin_no,admin_account,admin_password,admin_name,admin_photo,admin_status) values (('A'||LPAD(to_char(admins_seq.NEXTVAL), 6, '0')),?,?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT admin_no,admin_account,admin_password,admin_name,admin_photo,admin_status FROM Admins where admin_no = ?";

	private static final String GET_ALL_STMT = "SELECT admin_no,admin_account,admin_password,admin_name,admin_photo,admin_status FROM Admins order by admin_no ";
	private static final String DELETE = "DELETE FROM admins where admin_no = ?";
	private static final String UPDATE = "UPDATE admins set  admin_account=?, admin_password=?, admin_name=?, admin_photo=?, admin_status=? where admin_no = ?";

	private static final String FINDPIC = "SELECT admin_photo FROM admins WHERE admin_no= ?";
	private static final String CHECKACCOUNT = "SELECT admin_account From admins where admin_account = ?";


	@Override

	public void insert(AdminsVO adminsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminsVO.getAdmin_account());
			pstmt.setString(2, adminsVO.getAdmin_password());
			pstmt.setString(3, adminsVO.getAdmin_name());
			pstmt.setBytes(4, adminsVO.getAdmin_photo());
			pstmt.setInt(5, adminsVO.getAdmin_status());
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

	public void update(AdminsVO adminsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, adminsVO.getAdmin_account());
			pstmt.setString(2, adminsVO.getAdmin_password());
			pstmt.setString(3, adminsVO.getAdmin_name());
			pstmt.setBytes(4, adminsVO.getAdmin_photo());
			pstmt.setInt(5, adminsVO.getAdmin_status());
			pstmt.setString(6, adminsVO.getAdmin_no());
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

	public void delete(String admin_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, admin_no);

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
	public AdminsVO findByPrimaryKey(String admin_no) {
		AdminsVO adminsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, admin_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminsVO = new AdminsVO();
				adminsVO.setAdmin_no(rs.getString("admin_no"));
				adminsVO.setAdmin_account(rs.getString("admin_account"));
				adminsVO.setAdmin_password(rs.getString("admin_password"));
				adminsVO.setAdmin_name(rs.getString("admin_name"));
				adminsVO.setAdmin_photo(rs.getBytes("admin_photo"));
				adminsVO.setAdmin_status(rs.getInt("admin_status"));
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
		return adminsVO;
	}

	@Override
	public List<AdminsVO> getAll() {
		List<AdminsVO> list = new ArrayList<AdminsVO>();
		AdminsVO adminsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminsVO = new AdminsVO();
				adminsVO.setAdmin_no(rs.getString("admin_no"));
				adminsVO.setAdmin_account(rs.getString("admin_account"));
				adminsVO.setAdmin_password(rs.getString("admin_password"));
				adminsVO.setAdmin_name(rs.getString("admin_name"));
				adminsVO.setAdmin_photo(rs.getBytes("admin_photo"));
				adminsVO.setAdmin_status(rs.getInt("admin_status"));
				list.add(adminsVO);
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

	public byte[] getPicture(String admin_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] pic = null;
//		ByteArrayInputStream bis = null;
//		ByteArrayOutputStream bos = null;
//		InputStream is = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FINDPIC);
			pstmt.setString(1, admin_no);
			rs = pstmt.executeQuery();
			
			rs.next();
			pic = rs.getBytes(1);
//			while (rs.next()) {
//
//				bis = new ByteArrayInputStream(rs.getBytes(1));
//
//				bos = new ByteArrayOutputStream();
//				byte[] b = new byte[4 * 1024];
//				int len = 0;
//
//				while ((len = bis.read(b)) != -1) {
//					bos.write(b, 0, len);
//
//					pic = bos.toByteArray();
//				}
//			}

		} catch (Exception e) {

			throw new RuntimeException("A database error occured. " + e.getMessage());
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
		return pic;
	}

	

	@Override
	public int checkAccount(String admin_account) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECKACCOUNT);
			pstmt.setString(1, admin_account);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				++count;
			}

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
		return count;
	}
}
