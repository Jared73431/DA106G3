package com.feature.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeatureJDBCDAO implements FeatureDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "FAKER";
	String passwd = "123456";

	private static final String INSERT_STMT = "insert into feature (feature_no,feature_name,feature_spec) values (('F'||LPAD(to_char(feature_seq.NEXTVAL), 4, '0')),?,?)";
	private static final String GET_ONE_STMT = "SELECT feature_no,feature_name,feature_spec FROM feature where feature_no = ?";

	private static final String GET_ALL_STMT = "SELECT feature_no,feature_name,feature_spec FROM feature order by feature_no ";
	private static final String DELETE = "DELETE FROM feature where feature_no = ?";
	private static final String UPDATE = "UPDATE feature set feature_name = ?,feature_spec = ? where feature_no = ?";

	@Override

	public void insert(FeatureVO featureVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, featureVO.getFeature_name());
			pstmt.setString(2, featureVO.getFeature_spec());
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace(System.err);

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

	public void update(FeatureVO featureVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, featureVO.getFeature_name());
			pstmt.setString(2, featureVO.getFeature_spec());
			pstmt.setString(3, featureVO.getFeature_no());
			pstmt.execute();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace(System.err);

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

	@Override
	public void delete(String feature_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, feature_no);

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public FeatureVO findByPrimaryKey(String feature_no) {
		FeatureVO featureVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, feature_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				featureVO = new FeatureVO();
				featureVO.setFeature_no(rs.getString("feature_no"));
				featureVO.setFeature_name(rs.getString("feature_name"));
				featureVO.setFeature_spec(rs.getString("feature_spec"));

			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return featureVO;
	}

	@Override
	public List<FeatureVO> getAll() {
		List<FeatureVO> list = new ArrayList<FeatureVO>();
		FeatureVO featureVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				featureVO = new FeatureVO();
				featureVO.setFeature_no(rs.getString("feature_no"));
				featureVO.setFeature_name(rs.getString("feature_name"));
				featureVO.setFeature_spec(rs.getString("feature_spec"));
				list.add(featureVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	public static void main(String[] args) {
		FeatureJDBCDAO dao = new FeatureJDBCDAO();
		// 新增
//		FeatureVO feature01 = new FeatureVO();
//		FeatureVO feature02 = new FeatureVO();
//		FeatureVO feature03 = new FeatureVO();
//		
//		feature01.setFeature_name("員工管理");
//		feature01.setFeature_spec("就是員工管理");
//		feature02.setFeature_name("教練管理");
//		feature02.setFeature_spec("就是教練管理");
//		feature03.setFeature_name("檢舉管理");
//		feature03.setFeature_spec("就是檢舉管理");
//		
//		
//		dao.insert(feature01);
//		dao.insert(feature02);
//		dao.insert(feature03);
		// 查單筆
//		FeatureVO s1 = dao.findByPrimaryKey("F0007");
//		System.out.print(s1.getFeature_no() + ",");
//		System.out.print(s1.getFeature_name() + ",");
//		System.out.print(s1.getFeature_spec());
//		System.out.println("----------(findByPrimaryKey)-----------");
		// 查全部
		List<FeatureVO> list = dao.getAll();
		for (FeatureVO result : list) {
			System.out.print(result.getFeature_no() + ",");
			System.out.print(result.getFeature_name() + ",");
			System.out.print(result.getFeature_spec());
			System.out.println("---------(getALL)------------");
		}
		// 修改
//		FeatureVO upFeature = new FeatureVO();
//		upFeature.setFeature_no("F0010");
//		upFeature.setFeature_name("珊珊珊");
//		upFeature.setFeature_spec("GGGGG");
//		dao.update(upFeature);

//		dao.delete("F0010");
	}
}
