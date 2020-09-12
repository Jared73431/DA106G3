package com.coach.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CoachDAO implements CoachDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "insert into coach (coa_no,member_no,coa_info,service_area,coa_skill1,coa_licence1,coa_skill2,coa_skill3) values (('CO'||LPAD(to_char(coach_seq.NEXTVAL), 6, '0')),?,?,?,?,?,?,?)";
	private static final String GET_ONE_STMT = "SELECT coa_no,member_no,coa_info,service_area,coa_skill1,coa_licence1,coa_skill2,coa_licence2,coa_skill3,coa_licence3,coa_status From coach where coa_no= ?";

	private static final String GET_ALL_STMT = "SELECT coa_no,member_no,coa_info,service_area,coa_skill1,coa_licence1,coa_skill2,coa_licence2,coa_skill3,coa_licence3,coa_status From coach order by coa_no";
	private static final String DELETE = "DELETE FROM coach where coa_no = ?";
	private static final String UPDATE = "UPDATE coach set member_no = ?,coa_info = ?,service_area = ?,coa_skill1 = ?,coa_licence1 = ?,coa_skill2 = ?,coa_licence2 = ?,coa_skill3 = ?,coa_licence3 = ?,coa_status = ? where coa_no = ?";
	private static final String GET_ONE_MEMBER = "SELECT coa_no,member_no,coa_info,service_area,coa_skill1,coa_licence1,coa_skill2,coa_licence2,coa_skill3,coa_licence3,coa_status From coach where member_no= ?";
	private static final String CHECKSTATUS = "select coa_status from coach where member_no= ?";

	private static final String PUNISHUPDATE = "UPDATE coach set coa_status = 2 where coa_no = ?";
	private static final String UPDATECOAQUA = "UPDATE members set COA_QUALIFICATIONS = 1 where member_no = ?";
	private static final String CLEARLICENSE = "UPDATE members set COA_QUALIFICATIONS = 0,LICENSE=null where member_no = ?";
	
	@Override

	public void insert(CoachVO coachVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, coachVO.getMember_no());
			pstmt.setString(2, coachVO.getCoa_info());
			pstmt.setString(3, coachVO.getService_area());
			pstmt.setString(4, coachVO.getCoa_skill1());
			pstmt.setBytes(5, coachVO.getCoa_licence1());
			pstmt.setString(6, coachVO.getCoa_skill2());
			pstmt.setString(7, coachVO.getCoa_skill3());

			pstmt.executeUpdate();
			
			
			pstmt = con.prepareStatement(UPDATECOAQUA);
			pstmt.setString(1, coachVO.getMember_no());
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

	public void update(CoachVO coachVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, coachVO.getMember_no());
			pstmt.setString(2, coachVO.getCoa_info());
			pstmt.setString(3, coachVO.getService_area());
			pstmt.setString(4, coachVO.getCoa_skill1());
			pstmt.setBytes(5, coachVO.getCoa_licence1());
			pstmt.setString(6, coachVO.getCoa_skill2());
			pstmt.setBytes(7, coachVO.getCoa_licence2());
			pstmt.setString(8, coachVO.getCoa_skill3());
			pstmt.setBytes(9, coachVO.getCoa_licence3());
			pstmt.setInt(10, coachVO.getCoa_status());
			pstmt.setString(11, coachVO.getCoa_no());
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
	public void delete(String coa_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, coa_no);

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
	public CoachVO findByPrimaryKey(String coa_no) {
		CoachVO coachVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, coa_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				coachVO = new CoachVO();
				coachVO.setCoa_no(rs.getString("coa_no"));
				coachVO.setMember_no(rs.getString("member_no"));
				coachVO.setCoa_info(rs.getString("coa_info"));
				coachVO.setService_area(rs.getString("service_area"));
				coachVO.setCoa_skill1(rs.getString("coa_skill1"));
				coachVO.setCoa_licence1(rs.getBytes("coa_licence1"));
				coachVO.setCoa_skill2(rs.getString("coa_skill2"));
				coachVO.setCoa_licence2(rs.getBytes("coa_licence2"));
				coachVO.setCoa_skill3(rs.getString("coa_skill3"));
				coachVO.setCoa_licence3(rs.getBytes("coa_licence3"));
				coachVO.setCoa_status(rs.getInt("coa_status"));
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
		return coachVO;
	}

	@Override
	public String checkStatus(String member_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String check = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CHECKSTATUS);

			pstmt.setString(1, member_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				check = rs.getString(1);
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
		return check;
	}

	@Override
	public CoachVO findByMember(String member_no) {
		CoachVO coachVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEMBER);

			pstmt.setString(1, member_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				coachVO = new CoachVO();
				coachVO.setCoa_no(rs.getString("coa_no"));
				coachVO.setMember_no(rs.getString("member_no"));
				coachVO.setCoa_info(rs.getString("coa_info"));
				coachVO.setService_area(rs.getString("service_area"));
				coachVO.setCoa_skill1(rs.getString("coa_skill1"));
				coachVO.setCoa_licence1(rs.getBytes("coa_licence1"));
				coachVO.setCoa_skill2(rs.getString("coa_skill2"));
				coachVO.setCoa_licence2(rs.getBytes("coa_licence2"));
				coachVO.setCoa_skill3(rs.getString("coa_skill3"));
				coachVO.setCoa_licence3(rs.getBytes("coa_licence3"));
				coachVO.setCoa_status(rs.getInt("coa_status"));
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
		return coachVO;
	}

	@Override
	public List<CoachVO> getAll() {
		List<CoachVO> list = new ArrayList<CoachVO>();
		CoachVO coachVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				coachVO = new CoachVO();
				coachVO.setCoa_no(rs.getString("coa_no"));
				coachVO.setMember_no(rs.getString("member_no"));
				coachVO.setCoa_info(rs.getString("coa_info"));
				coachVO.setService_area(rs.getString("service_area"));
				coachVO.setCoa_skill1(rs.getString("coa_skill1"));
				coachVO.setCoa_licence1(rs.getBytes("coa_licence1"));
				coachVO.setCoa_skill2(rs.getString("coa_skill2"));
				coachVO.setCoa_licence2(rs.getBytes("coa_licence2"));
				coachVO.setCoa_skill3(rs.getString("coa_skill3"));
				coachVO.setCoa_licence3(rs.getBytes("coa_licence3"));
				coachVO.setCoa_status(rs.getInt("coa_status"));
				list.add(coachVO);
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

	public byte[] getPicture(String member_no, String licence) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		byte[] pic = null;

		try {
			con = ds.getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT " + licence + " FROM coach WHERE member_no='" + member_no + "'");

			if (rs.next())
				pic = rs.getBytes(1);

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
			if (stmt != null) {
				try {
					stmt.close();
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
	public void punishUpdate(String coa_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(PUNISHUPDATE);
			pstmt.setString(1, coa_no);
			rs = pstmt.executeQuery();
		}catch (Exception e) {

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
	}
	@Override
	public void clearLicense(String member_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CLEARLICENSE);
			pstmt.setString(1, member_no);
			rs = pstmt.executeQuery();
		}catch (Exception e) {

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
	}
	
}
