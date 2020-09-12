package com.feature.model;

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

public class FeatureDAO implements FeatureDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "insert into feature (feature_no,feature_name,feature_spec) values (('F'||LPAD(to_char(feature_seq.NEXTVAL), 4, '0')),?,?)";
	private static final String GET_ONE_STMT = "SELECT feature_no,feature_name,feature_spec FROM feature where feature_no = ?";

	private static final String GET_ALL_STMT = "SELECT feature_no,feature_name,feature_spec FROM feature order by feature_no ";
	private static final String DELETE = "DELETE FROM feature where feature_no = ?";
	private static final String UPDATE = "UPDATE feature set feature_name = ?,feature_spec = ? where feature_no = ?";
	private static final String DELETE_AUTHOR ="DELETE FROM authorizations where feature_no = ?";
	@Override

	public void insert(FeatureVO featureVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, featureVO.getFeature_name());
			pstmt.setString(2, featureVO.getFeature_spec());
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

	public void update(FeatureVO featureVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, featureVO.getFeature_name());
			pstmt.setString(2, featureVO.getFeature_spec());
			pstmt.setString(3, featureVO.getFeature_no());
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
		int updateAuthor= 0 ;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			
			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			//先刪除賦予的權限
			pstmt = con.prepareStatement(DELETE_AUTHOR);
			pstmt.setString(1, feature_no);
			updateAuthor = pstmt.executeUpdate();
			//再刪除功能
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, feature_no);
			pstmt.executeUpdate();
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除功能編號" + feature_no + "時,共有賦予的權限" + updateAuthor
					+ "項同時被刪除");
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
	public FeatureVO findByPrimaryKey(String feature_no) {
		FeatureVO featureVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, feature_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				featureVO = new FeatureVO();
				featureVO.setFeature_no(rs.getString("feature_no"));
				featureVO.setFeature_name(rs.getString("feature_name"));
				featureVO.setFeature_spec(rs.getString("feature_spec"));

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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				featureVO = new FeatureVO();
				featureVO.setFeature_no(rs.getString("feature_no"));
				featureVO.setFeature_name(rs.getString("feature_name"));
				featureVO.setFeature_spec(rs.getString("feature_spec"));
				list.add(featureVO);
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
}
