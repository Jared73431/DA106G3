package com.newsknowledge.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.util.tool.Upload;

public class NewsKnowledgeDAO implements NewsKnowledgeDAO_interface{

	private static DataSource ds =null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO news_knowledge (news_no,news_title,news_author,news_cover,news_content) VALUES ('NK'||LPAD(to_char(news_seq.NEXTVAL),6,'0'),?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT news_no,cate_no,news_title,news_author,to_char(news_date,'yyyy-mm-dd hh:mm:ss') news_date,news_status FROM news_knowledge order by news_no";
	private static final String GET_ONE_STMT = 
			"SELECT news_no,cate_no,news_title,news_author,to_char(news_date,'yyyy-mm-dd hh:mm:ss') news_date,news_status,news_content FROM news_knowledge where news_no = ?";
	// 取得一筆隨機資料
	private static final String GET_RANDOM_STMT = 
			"SELECT * FROM (SELECT * FROM news_knowledge ORDER BY dbms_random.value) where news_status='1' and rownum <= '3'";	
	// 0501刪除改成下架
	private static final String CHANGE_STATUS = 
			"UPDATE news_knowledge set news_status=? where news_no = ?";
	private static final String UPDATE = 
			"UPDATE news_knowledge set news_title=?, news_author=?, news_cover=?, news_content=?, news_date=?, news_status=? where news_no = ?";
	// 新增讀取圖片測試
	private static final String READ_PIC = 
			"SELECT news_cover FROM news_knowledge where news_no = ?";
	// 讀取上架文章
	private static final String GET_ALL_AUTH = 
			"SELECT news_no,cate_no,news_title,news_author,to_char(news_date,'yyyy-mm-dd hh:mm:ss') news_date,news_status FROM news_knowledge where news_status='1' order by news_date";

	
	@Override
	public int insert(NewsKnowledgeVO newsknowledgeVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, newsknowledgeVO.getNews_title());
			pstmt.setString(2, newsknowledgeVO.getNews_author());
			pstmt.setBytes(3, newsknowledgeVO.getNews_cover());
			pstmt.setString(4, newsknowledgeVO.getNews_content());			

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public int update(NewsKnowledgeVO newsknowledgeVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, newsknowledgeVO.getNews_title());
			pstmt.setString(2, newsknowledgeVO.getNews_author());
			pstmt.setBytes(3, newsknowledgeVO.getNews_cover());
			pstmt.setString(4, newsknowledgeVO.getNews_content());
			pstmt.setTimestamp(5, newsknowledgeVO.getNews_date());
			pstmt.setInt(6, newsknowledgeVO.getNews_status());			
			pstmt.setString(7, newsknowledgeVO.getNews_no());

			updateCount = pstmt.executeUpdate();

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
		return updateCount;
	}

	@Override
	public void changeStatus(String news_no, Integer news_status) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CHANGE_STATUS);
			pstmt.setInt(1, news_status);
			pstmt.setString(2, news_no);
			pstmt.executeUpdate();

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
		System.out.println("AJAX操作成功");
	}

	@Override
	public NewsKnowledgeVO findByPrimaryKey(String news_no) {

		NewsKnowledgeVO newsknowledgeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, news_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// newsknowledgeVO 也稱為 Domain objects
				newsknowledgeVO = new NewsKnowledgeVO();
				newsknowledgeVO.setNews_no(rs.getString("news_no"));
				newsknowledgeVO.setCate_no(rs.getString("cate_no"));
				newsknowledgeVO.setNews_title(rs.getString("news_title"));
				newsknowledgeVO.setNews_author(rs.getString("news_author"));
				newsknowledgeVO.setNews_date(rs.getTimestamp("news_date"));
				newsknowledgeVO.setNews_status(rs.getInt("news_status"));
				newsknowledgeVO.setNews_content(rs.getString("news_content"));				
			}

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
		return newsknowledgeVO;
	}

	@Override
	public byte[] readPic(String news_no) {
		byte[] pic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(READ_PIC);

			pstmt.setString(1, news_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
			// 抓照片
			pic = rs.getBytes(1);
			}
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
		return pic;
	}
	
	@Override
	public List<NewsKnowledgeVO> getAll() {
		List<NewsKnowledgeVO> list = new ArrayList<NewsKnowledgeVO>();
		NewsKnowledgeVO newsknowledgeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// newsknowledgeVO 也稱為 Domain objects
				newsknowledgeVO = new NewsKnowledgeVO();
				newsknowledgeVO.setNews_no(rs.getString("news_no"));
				newsknowledgeVO.setCate_no(rs.getString("cate_no"));
				newsknowledgeVO.setNews_title(rs.getString("news_title"));
				newsknowledgeVO.setNews_author(rs.getString("news_author"));
				newsknowledgeVO.setNews_date(rs.getTimestamp("news_date"));
				newsknowledgeVO.setNews_status(rs.getInt("news_status"));
				list.add(newsknowledgeVO); // Store the row in the vector
			}

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
	
	@Override
	public List<NewsKnowledgeVO> getAuth() {
		List<NewsKnowledgeVO> list = new ArrayList<NewsKnowledgeVO>();
		NewsKnowledgeVO newsknowledgeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_AUTH);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// newsknowledgeVO 也稱為 Domain objects
				newsknowledgeVO = new NewsKnowledgeVO();
				newsknowledgeVO.setNews_no(rs.getString("news_no"));
				newsknowledgeVO.setCate_no(rs.getString("cate_no"));
				newsknowledgeVO.setNews_title(rs.getString("news_title"));
				newsknowledgeVO.setNews_author(rs.getString("news_author"));
				newsknowledgeVO.setNews_date(rs.getTimestamp("news_date"));
				newsknowledgeVO.setNews_status(rs.getInt("news_status"));
				list.add(newsknowledgeVO); // Store the row in the vector
			}

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
	
	public List<NewsKnowledgeVO> getRandom() {
		List<NewsKnowledgeVO> list = new ArrayList<NewsKnowledgeVO>();
		NewsKnowledgeVO newsknowledgeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RANDOM_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// newsknowledgeVO 也稱為 Domain objects
				newsknowledgeVO = new NewsKnowledgeVO();
				newsknowledgeVO.setNews_no(rs.getString("news_no"));
				newsknowledgeVO.setCate_no(rs.getString("cate_no"));
				newsknowledgeVO.setNews_title(rs.getString("news_title"));
				newsknowledgeVO.setNews_author(rs.getString("news_author"));
				newsknowledgeVO.setNews_date(rs.getTimestamp("news_date"));
				newsknowledgeVO.setNews_status(rs.getInt("news_status"));
				newsknowledgeVO.setNews_content(rs.getString("news_content"));				
				list.add(newsknowledgeVO);
			}

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

}
