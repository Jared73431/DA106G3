package com.members.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.experience.model.ExperienceVO;
import com.notificationlist.model.NotificationListVO;


public class MembersDAO implements MembersDAO_interface {

	private static final String INSERT_STMT = 
			"INSERT INTO MEMBERS (MEMBER_NO, KNOWN, SEXUAL, MEM_NAME, MEM_ACCOUNT, EMAIL, MEM_PASSWORD, BIRTHDAY, PHONE, MEM_ZIP, ADDRESS, HEIGHT, PICTURE) VALUES (('M'||LPAD(to_char(member_seq.NEXTVAL),6,'0')),?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT MEMBER_NO, COA_QUALIFICATIONS, KNOWN, SEXUAL, MEM_NAME, MEM_ACCOUNT, MEM_STATUS, EMAIL, MEM_PASSWORD, BIRTHDAY, PHONE, MEM_ZIP, ADDRESS, REG_DATE, REAL_BLANCE, HEIGHT, PICTURE, LICENSE FROM MEMBERS order by MEMBER_NO";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM MEMBERS where MEMBER_NO = ?";
	private static final String DELETE = 
			"DELETE FROM MEMBERS where MEMBER_NO = ? ";
	private static final String UPDATE = 
			"UPDATE MEMBERS set COA_QUALIFICATIONS=?, KNOWN=?, SEXUAL=?, MEM_NAME=?, MEM_ACCOUNT=?, MEM_STATUS=?, EMAIL=?, MEM_PASSWORD=?, BIRTHDAY=?, PHONE=?, MEM_ZIP=?, ADDRESS=?, REAL_BLANCE=?, HEIGHT=?, PICTURE=? where MEMBER_NO = ?";
	private static final String GET_NOSTATUS_STMT =
			"SELECT * FROM MEMBERS WHERE MEM_STATUS= ?";
	private static final String GET_ONE_BY_ACCOUNT =
			"SELECT * FROM MEMBERS WHERE MEM_ACCOUNT = ?";
	private static final String UPDATE_REAL_BLANCE =
			"UPDATE MEMBERS SET REAL_BLANCE=? WHERE MEMBER_NO=?";
	private static final String UPDATE_COA = 
			"UPDATE MEMBERS set COA_QUALIFICATIONS=?, LICENSE=? where MEMBER_NO = ?";
	private static final String UPDATE_STATUS =
			"UPDATE MEMBERS set MEM_STATUS=? where MEMBER_NO = ?";
	
	//notification查詢
	private static final String GET_Notes_ByMemberno_STMT = 
			"SELECT note_no,member_no,category_no,note_content,note_status,note_date FROM notification_list where member_no = ? order by note_no desc";
	
	//experience查詢
		private static final String GET_EXPERIENCE_ByMemberno_STMT = 
				"SELECT * FROM experience where member_no = ? order by exper_no desc";

	
	//一個應用程式中,針對一個資料庫,共用一個DataSource即可
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	
	
	

	@Override
	public int insert(MembersVO membersVO) {
		
		int updateCount = 0;
		Connection con = null;

		try (
			Connection connection = ds.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(INSERT_STMT);){
			
//			pstmt.setString(1, membersVO.getCoa_qualifications());
			pstmt.setString(1,membersVO.getKnown());
			pstmt.setInt(2, membersVO.getSexual());
			pstmt.setString(3,membersVO.getMem_name());
			pstmt.setString(4,membersVO.getMem_account());
//			pstmt.setInt(5,membersVO.getMem_status());
			pstmt.setString(5,membersVO.getEmail());
			pstmt.setString(6,membersVO.getMem_password());
			pstmt.setDate(7,membersVO.getBirthday());
			pstmt.setString(8,membersVO.getPhone());
			pstmt.setString(9,membersVO.getMem_zip());
			pstmt.setString(10,membersVO.getAddress());
//			pstmt.setInt(11,membersVO.getReal_blance());
			pstmt.setDouble(11,membersVO.getHeight());
			pstmt.setBytes(12,membersVO.getPicture());
//			pstmt.setBytes(13,membersVO.getLicense());
			
		

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	public int update(MembersVO membersVO) {
		int rowCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE); 
			
	
			pstmt.setInt(1, membersVO.getCoa_qualifications());
			pstmt.setString(2,membersVO.getKnown());
			pstmt.setInt(3, membersVO.getSexual());
			pstmt.setString(4,membersVO.getMem_name());
			pstmt.setString(5,membersVO.getMem_account());
			pstmt.setInt(6,membersVO.getMem_status());
			pstmt.setString(7,membersVO.getEmail());
			pstmt.setString(8,membersVO.getMem_password());
			pstmt.setDate(9,membersVO.getBirthday());
			pstmt.setString(10,membersVO.getPhone());
			pstmt.setString(11,membersVO.getMem_zip());
			pstmt.setString(12,membersVO.getAddress());
			pstmt.setInt(13,membersVO.getReal_blance());
			pstmt.setDouble(14,membersVO.getHeight());
			pstmt.setBytes(15,membersVO.getPicture());
//			pstmt.setBytes(16,membersVO.getLicense());
			pstmt.setString(16, membersVO.getMember_no());
			
			rowCount = pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return rowCount;

		
	}

	@Override
	public boolean delete(String member_no) {
		int rowCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
				pstmt.setString(1, member_no);
			rowCount = pstmt.executeUpdate();
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		return rowCount != 0;
	}	
	
	@Override
	public MembersVO findByPrimaryKey(String member_no) {
		MembersVO membersVO= null;
		ResultSet rs = null;
		try(Connection connection = ds.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(GET_ONE_STMT);)
		{
			ps.setString(1, member_no);
			rs = ps.executeQuery();
			while (rs.next()) {
				// empVo �]�٬� Domain objects
				membersVO = new MembersVO();
				membersVO.setMember_no(rs.getString("MEMBER_NO"));
				membersVO.setCoa_qualifications(rs.getInt("COA_QUALIFICATIONS"));
				membersVO.setKnown(rs.getString("KNOWN"));
				membersVO.setSexual(rs.getInt("SEXUAL"));
				membersVO.setMem_name(rs.getString("MEM_NAME"));
				membersVO.setMem_account(rs.getString("MEM_ACCOUNT"));
				membersVO.setMem_status(rs.getInt("MEM_STATUS"));
				membersVO.setEmail(rs.getString("EMAIL"));
				membersVO.setMem_password(rs.getString("MEM_PASSWORD"));
				membersVO.setBirthday(rs.getDate("BIRTHDAY"));
				membersVO.setPhone(rs.getString("PHONE"));
				membersVO.setMem_zip(rs.getString("MEM_ZIP"));
				membersVO.setAddress(rs.getString("ADDRESS"));
				membersVO.setReal_blance(rs.getInt("REAL_BLANCE"));
				membersVO.setReg_date(rs.getTimestamp("REG_DATE"));
				membersVO.setHeight(rs.getDouble("HEIGHT"));
				membersVO.setPicture(rs.getBytes("PICTURE"));
				membersVO.setLicense(rs.getBytes("LICENSE"));
				
				
			}

		}
		
		catch(SQLException e) 
		{e.printStackTrace();}
		
		
		return membersVO;
	}

	@Override
	public List<MembersVO> getAll() {
		// TODO Auto-generated method stub
		List<MembersVO> list = new ArrayList<MembersVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MembersVO membersVO = new MembersVO();
				membersVO = new MembersVO();
				membersVO.setMember_no(rs.getString("MEMBER_NO"));
				membersVO.setCoa_qualifications(rs.getInt("COA_QUALIFICATIONS"));
				membersVO.setKnown(rs.getString("KNOWN"));
				membersVO.setSexual(rs.getInt("SEXUAL"));
				membersVO.setMem_name(rs.getString("MEM_NAME"));
				membersVO.setMem_account(rs.getString("MEM_ACCOUNT"));
				membersVO.setMem_status(rs.getInt("MEM_STATUS"));
				membersVO.setEmail(rs.getString("EMAIL"));
				membersVO.setMem_password(rs.getString("MEM_PASSWORD"));
				membersVO.setBirthday(rs.getDate("BIRTHDAY"));
				membersVO.setPhone(rs.getString("PHONE"));
				membersVO.setMem_zip(rs.getString("MEM_ZIP"));
				membersVO.setAddress(rs.getString("ADDRESS"));
				membersVO.setReal_blance(rs.getInt("REAL_BLANCE"));
				membersVO.setReg_date(rs.getTimestamp("REG_DATE"));
				membersVO.setHeight(rs.getDouble("HEIGHT"));
				membersVO.setPicture(rs.getBytes("PICTURE"));
				membersVO.setLicense(rs.getBytes("LICENSE"));
				list.add(membersVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	

	public List<MembersVO> getNoStatus(Integer mem_status){
		List<MembersVO> list = new ArrayList<MembersVO>();
		ResultSet rs = null;
		try(Connection connection = ds.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(GET_NOSTATUS_STMT);)
		{
			ps.setInt(1, mem_status);
			rs = ps.executeQuery();
			while (rs.next()) {
				// empVo �]�٬� Domain objects
				MembersVO membersVO = new MembersVO();
				membersVO.setMember_no(rs.getString("MEMBER_NO"));
				membersVO.setCoa_qualifications(rs.getInt("COA_QUALIFICATIONS"));
				membersVO.setKnown(rs.getString("KNOWN"));
				membersVO.setSexual(rs.getInt("SEXUAL"));
				membersVO.setMem_name(rs.getString("MEM_NAME"));
				membersVO.setMem_account(rs.getString("MEM_ACCOUNT"));
				membersVO.setMem_status(rs.getInt("MEM_STATUS"));
				membersVO.setEmail(rs.getString("EMAIL"));
				membersVO.setMem_password(rs.getString("MEM_PASSWORD"));
				membersVO.setBirthday(rs.getDate("BIRTHDAY"));
				membersVO.setPhone(rs.getString("PHONE"));
				membersVO.setMem_zip(rs.getString("MEM_ZIP"));
				membersVO.setAddress(rs.getString("ADDRESS"));
				membersVO.setReal_blance(rs.getInt("REAL_BLANCE"));
				membersVO.setReg_date(rs.getTimestamp("REG_DATE"));
				membersVO.setHeight(rs.getDouble("HEIGHT"));
				membersVO.setPicture(rs.getBytes("PICTURE"));
				membersVO.setLicense(rs.getBytes("LICENSE"));
				list.add(membersVO);
				
			}

		}
		
		catch(SQLException e) 
		{e.printStackTrace();}
		
		
		return list;
	}
	public MembersVO getOneMemberByAccount(String mem_account) {
		MembersVO membersVO= null;
		ResultSet rs = null;
		try(Connection connection = ds.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(GET_ONE_BY_ACCOUNT);)
		{
			ps.setString(1, mem_account);
			rs = ps.executeQuery();
			while (rs.next()) {
				// empVo �]�٬� Domain objects
				membersVO = new MembersVO();
				membersVO.setMember_no(rs.getString("MEMBER_NO"));
				membersVO.setCoa_qualifications(rs.getInt("COA_QUALIFICATIONS"));
				membersVO.setKnown(rs.getString("KNOWN"));
				membersVO.setSexual(rs.getInt("SEXUAL"));
				membersVO.setMem_name(rs.getString("MEM_NAME"));
				membersVO.setMem_account(rs.getString("MEM_ACCOUNT"));
				membersVO.setMem_status(rs.getInt("MEM_STATUS"));
				membersVO.setEmail(rs.getString("EMAIL"));
				membersVO.setMem_password(rs.getString("MEM_PASSWORD"));
				membersVO.setBirthday(rs.getDate("BIRTHDAY"));
				membersVO.setPhone(rs.getString("PHONE"));
				membersVO.setMem_zip(rs.getString("MEM_ZIP"));
				membersVO.setAddress(rs.getString("ADDRESS"));
				membersVO.setReal_blance(rs.getInt("REAL_BLANCE"));
				membersVO.setReg_date(rs.getTimestamp("REG_DATE"));
				membersVO.setHeight(rs.getDouble("HEIGHT"));
				membersVO.setPicture(rs.getBytes("PICTURE"));
				membersVO.setLicense(rs.getBytes("LICENSE"));
				
				
			}

		}
		
		catch(SQLException e) 
		{e.printStackTrace();}
		
		
		return membersVO;
	}

	public void updateMembersRealBlance(Integer real_blance, String member_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_REAL_BLANCE);
			
			pstmt.setInt(1, real_blance);
			pstmt.setString(2,member_no);
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
	
	public void updateCoa(Integer coa_qualifications, byte[] license, String member_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_COA); 
			
	
			pstmt.setInt(1, coa_qualifications);
			pstmt.setBytes(2, license);
			pstmt.setString(3, member_no);
			pstmt.executeUpdate();
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	
	public void updateStatus(Integer mem_status, String member_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS); 
			
	
			pstmt.setInt(1, mem_status);
			pstmt.setString(2, member_no);
			pstmt.executeUpdate();
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public Set<NotificationListVO> getNotesByMember(String member_no) {
		// TODO Auto-generated method stub
		Set<NotificationListVO> list = new LinkedHashSet<NotificationListVO>();
		NotificationListVO noteVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Notes_ByMemberno_STMT);
			pstmt.setString(1, member_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				noteVO = new NotificationListVO();
				noteVO.setNote_no(rs.getString("note_no"));
				noteVO.setMember_no(rs.getString("MEMBER_NO"));
				noteVO.setCategory_no(rs.getString("category_no"));
				noteVO.setNote_content(rs.getString("note_content"));
				noteVO.setNote_status(rs.getInt("note_status"));
				noteVO.setNote_date(rs.getDate("note_date"));
				list.add(noteVO); // Store the row in the vector
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
	public Set<ExperienceVO> getExoerByMember(String member_no) {
		// TODO Auto-generated method stub
		Set<ExperienceVO> list = new LinkedHashSet<ExperienceVO>();
		ExperienceVO experVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EXPERIENCE_ByMemberno_STMT);
			pstmt.setString(1, member_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				experVO = new ExperienceVO();
				experVO.setExper_no(rs.getString("exper_no"));
				experVO.setMember_no(rs.getString("member_no"));
				experVO.setCate_no(rs.getString("cate_no"));
				experVO.setExper_title(rs.getString("exper_title"));
				experVO.setExper_context(rs.getString("exper_context"));
				experVO.setExper_date(rs.getDate("exper_date"));
				list.add(experVO); // Store the row in the vector
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
	
}