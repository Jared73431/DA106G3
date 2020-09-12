package com.course.model;

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

public class CourseDAO implements CourseDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "insert into course (cour_no,cour_type_no,coa_no,cour_name,cour_addr,cour_mode,cour_date,cour_update,cour_deadline) values (('C'||LPAD(to_char(COURSE_seq.NEXTVAL), 6, '0')),?,?,?,?,?,?,?,?)";
	private static final String GET_ONE_STMT_COA = "SELECT cour_no,cour_type_no,coa_no,cour_name,cour_addr,cour_mode,cour_date,cour_status,cour_update,cour_deadline From course where coa_no= ? ";
	private static final String GET_ONE_STMT_COUR = "SELECT cour_no,cour_type_no,coa_no,cour_name,cour_addr,cour_mode,cour_date,cour_status,cour_update,cour_deadline From course where cour_no= ? ";

	private static final String GET_ALL_STMT = "SELECT cour_no,cour_type_no,coa_no,cour_name,cour_addr,cour_mode,cour_date,cour_status,cour_update,cour_deadline From course order by cour_no";
	private static final String DELETE = "DELETE FROM course where cour_no = ?";
	private static final String UPDATE = "UPDATE course set cour_type_no = ?,coa_no = ?,cour_name = ?,cour_addr = ?,cour_mode = ?,cour_date = ?,cour_status = ?,cour_update = ?,cour_deadline = ? where cour_no = ?";

	private static final String UPDATE_COUR_STATUS = "update course set cour_status = ? where cour_no = ? ";

	private static final String UPDATEFORCOMF = "select coa_comf,trainee_comf,booking_no from cour_booking where cour_no = ? and coa_comf!='3' and trainee_comf != '3' order by booking_no DESC ";

	@Override

	public void insert(CourseVO courseVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, courseVO.getCour_type_no());
			pstmt.setString(2, courseVO.getCoa_no());
			pstmt.setString(3, courseVO.getCour_name());
			pstmt.setString(4, courseVO.getCour_addr());
			pstmt.setInt(5, courseVO.getCour_mode());
			pstmt.setTimestamp(6, courseVO.getCour_date());
			pstmt.setTimestamp(7, courseVO.getCour_update());
			pstmt.setTimestamp(8, courseVO.getCour_deadline());

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

	public void update(CourseVO courseVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, courseVO.getCour_type_no());
			pstmt.setString(2, courseVO.getCoa_no());
			pstmt.setString(3, courseVO.getCour_name());
			pstmt.setString(4, courseVO.getCour_addr());
			pstmt.setInt(5, courseVO.getCour_mode());
			pstmt.setTimestamp(6, courseVO.getCour_date());
			pstmt.setInt(7, courseVO.getCour_status());
			pstmt.setTimestamp(8, courseVO.getCour_update());
			pstmt.setTimestamp(9, courseVO.getCour_deadline());
			pstmt.setString(10, courseVO.getCour_no());

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

	public String updateForComf(String cour_no,Integer coa_comf,Integer trainee_comf) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String booking_no = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATEFORCOMF, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			pstmt.setString(1, cour_no);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			rs.updateInt("coa_comf", coa_comf);
			rs.updateInt("trainee_comf", trainee_comf);

			booking_no = rs.getString("booking_no");

			rs.updateRow();
			
		} catch (SQLException se) {
			se.printStackTrace();
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
		return booking_no;
	}

	@Override
	public void delete(String cour_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, cour_no);

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
	public List<CourseVO> findCoachCourse(String coa_no) {
		List<CourseVO> list = new ArrayList<CourseVO>();
		CourseVO courseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_COA);

			pstmt.setString(1, coa_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				courseVO = new CourseVO();
				courseVO.setCour_no(rs.getString("cour_no"));
				courseVO.setCour_type_no(rs.getString("cour_type_no"));
				courseVO.setCoa_no(rs.getString("coa_no"));
				courseVO.setCour_name(rs.getString("cour_name"));
				courseVO.setCour_addr(rs.getString("cour_addr"));
				courseVO.setCour_mode(rs.getInt("cour_mode"));
				courseVO.setCour_date(rs.getTimestamp("cour_date"));
				courseVO.setCour_status(rs.getInt("cour_status"));
				courseVO.setCour_update(rs.getTimestamp("cour_update"));
				courseVO.setCour_deadline(rs.getTimestamp("cour_deadline"));
				list.add(courseVO);
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
	public CourseVO findByPrimaryKey(String cour_no) {

		CourseVO courseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_COUR);

			pstmt.setString(1, cour_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				courseVO = new CourseVO();
				courseVO.setCour_no(rs.getString("cour_no"));
				courseVO.setCour_type_no(rs.getString("cour_type_no"));
				courseVO.setCoa_no(rs.getString("coa_no"));
				courseVO.setCour_name(rs.getString("cour_name"));
				courseVO.setCour_addr(rs.getString("cour_addr"));
				courseVO.setCour_mode(rs.getInt("cour_mode"));
				courseVO.setCour_date(rs.getTimestamp("cour_date"));
				courseVO.setCour_status(rs.getInt("cour_status"));
				courseVO.setCour_update(rs.getTimestamp("cour_update"));
				courseVO.setCour_deadline(rs.getTimestamp("cour_deadline"));

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
		return courseVO;
	}

	@Override
	public List<CourseVO> getAll() {
		List<CourseVO> list = new ArrayList<CourseVO>();
		CourseVO courseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				courseVO = new CourseVO();
				courseVO.setCour_no(rs.getString("cour_no"));
				courseVO.setCour_type_no(rs.getString("cour_type_no"));
				courseVO.setCoa_no(rs.getString("coa_no"));
				courseVO.setCour_name(rs.getString("cour_name"));
				courseVO.setCour_addr(rs.getString("cour_addr"));
				courseVO.setCour_mode(rs.getInt("cour_mode"));
				courseVO.setCour_date(rs.getTimestamp("cour_date"));
				courseVO.setCour_status(rs.getInt("cour_status"));
				courseVO.setCour_update(rs.getTimestamp("cour_update"));
				courseVO.setCour_deadline(rs.getTimestamp("cour_deadline"));
				list.add(courseVO);
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

	public void updateForStatus(String cour_no, Integer cour_status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_COUR_STATUS);

			pstmt.setInt(1, cour_status);
			pstmt.setString(2, cour_no);

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
}
