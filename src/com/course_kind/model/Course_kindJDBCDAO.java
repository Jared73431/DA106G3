package com.course_kind.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;







public class Course_kindJDBCDAO implements Course_kindDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "FAKER";
	String passwd = "123456";
	private static final String INSERT_STMT = "insert into course_kind (cour_type_no,cour_type) values (('CK'||LPAD(to_char(course_kind_seq.NEXTVAL),4,'0')),?)";
	private static final String GET_ONE_STMT = 
			"SELECT cour_type_no,cour_type FROM course_kind where cour_type_no = ?";
	
	private static final String GET_ALL_STMT = 
			"SELECT cour_type_no,cour_type FROM course_kind order by cour_type_no";
	private static final String DELETE = "DELETE FROM course_kind where cour_type_no = ?";
	private static final String UPDATE = "UPDATE course_kind set cour_type = ? where cour_type_no = ?";

	@Override
	
	public void insert(Course_kindVO course_kindVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1,course_kindVO.getCour_type());
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace(System.err);
					System.out.println("sqle2 GG");
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
					System.out.println("e GG");
				}
			}

		}
	}
@Override
	
	public void update(Course_kindVO course_kindVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1,course_kindVO.getCour_type());
			pstmt.setString(2,course_kindVO.getCour_type_no());
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace(System.err);
					System.out.println("sqle2 GG");
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
					System.out.println("e GG");
				}
			}

		}
	}
@Override
public void delete(String cour_type_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, cour_type_no);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public Course_kindVO findByPrimaryKey(String cour_type_no) {
		Course_kindVO course_kindVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, cour_type_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				course_kindVO = new Course_kindVO();
				course_kindVO.setCour_type_no(rs.getString("cour_type_no"));
				course_kindVO.setCour_type(rs.getString("cour_type"));

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
		return course_kindVO;
	}
	@Override
	public List<Course_kindVO> getAll(){
		List<Course_kindVO> list = new ArrayList<Course_kindVO>();
		Course_kindVO course_kindVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				course_kindVO = new Course_kindVO();
				course_kindVO.setCour_type_no(rs.getString("cour_type_no"));
				course_kindVO.setCour_type(rs.getString("cour_type"));
				list.add(course_kindVO);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
	public static void main(String[] args) {
		Course_kindJDBCDAO dao = new Course_kindJDBCDAO();
		//新增
//		Course_kindVO kind1 = new Course_kindVO();
//		Course_kindVO kind2 = new Course_kindVO();
//		Course_kindVO kind3 = new Course_kindVO();
//		
//		kind1.setCour_type("戰鬥有氧");
//		kind2.setCour_type("瑜珈");
//		kind3.setCour_type("皮拉提斯");
//		
//		dao.insert(kind1);
//		dao.insert(kind2);
//		dao.insert(kind3);
		//查單筆
//		Course_kindVO s1 = dao.findByPrimaryKey("CK0007");
//		System.out.print(s1.getCour_type_no() + ",");
//		System.out.print(s1.getCour_type());
//		System.out.println("----------(findByPrimaryKey)-----------");
		
		//查全部
//		List<Course_kindVO> list = dao.getAll();
//		for(Course_kindVO result :list) {
//			System.out.print(result.getCour_type_no() + ",");
//			System.out.print(result.getCour_type() );
//			System.out.println("---------(getALL)------------");
//		}
		//新增
//		Course_kindVO upType = new Course_kindVO();
//		upType.setCour_type_no("CK0009");
//		upType.setCour_type("太極拳");
//		dao.update(upType);
		//刪除
		dao.delete("CK0010");
		
	}

}
