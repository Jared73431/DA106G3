package com.transactions.model;


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

import com.transactions.model.*;


public class TransactionsJNDIDAO implements TransactionsDAO_interface {

	private static final String INSERT_STMT = 
			"INSERT INTO TRANSACTIONS (TRANS_NO, MEMBER_NO, DEPOSIT, WITHDRAW, REMARK) VALUES (('T'||LPAD(to_char(member_seq.NEXTVAL),6,'0')),?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT TRANS_NO, MEMBER_NO, DEPOSIT, WITHDRAW, TRAN_DATE, REMARK FROM TRANSACTIONS order by TRANS_NO";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM TRANSACTIONS where TRANS_NO = ?";
	private static final String DELETE = 
			"DELETE FROM TRANSACTIONS where TRANS_NO = ? ";
	private static final String UPDATE = 
			"UPDATE TRANSACTIONS set MEMBER_NO=?, DEPOSIT=?, WITHDRAW=?, TRAN_DATE=?, REMARK=? where TRANS_NO = ?";
	private static final String GET_ONE_MEM =
			"SELECT * FROM TRANSACTIONS WHERE MEMBER_NO = ?";
	
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
	public int insert(TransactionsVO transactionsVO) {
		
		int updateCount = 0;
		Connection con = null;

		try (
			Connection connection = ds.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(INSERT_STMT);){
			
			pstmt.setString(1, transactionsVO.getMember_no());
			pstmt.setInt(2,transactionsVO.getDeposit());
			pstmt.setInt(3, transactionsVO.getWithdraw());
			pstmt.setString(4,transactionsVO.getRemark());
		

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} 
		return updateCount;			
	}

	@Override
	public int updateBookingData(TransactionsVO transactionsVO) {
		int rowCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			
			pstmt.setString(1, transactionsVO.getMember_no());
			pstmt.setInt(2,transactionsVO.getDeposit());
			pstmt.setInt(3, transactionsVO.getWithdraw());
			pstmt.setTimestamp(4,transactionsVO.getTran_date());
			pstmt.setString(5,transactionsVO.getRemark());
			pstmt.setString(6, transactionsVO.getTrans_no());
			
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
	public boolean delete(String trans_no) {
		int rowCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
				pstmt.setString(1, trans_no);
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
	public TransactionsVO findByPrimaryKey(String trans_no) {
		TransactionsVO transactionsVO= null;
		ResultSet rs = null;
		try(Connection connection = ds.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(GET_ONE_STMT);)
		{
			ps.setString(1, trans_no);
			rs = ps.executeQuery();
			while (rs.next()) {
				// empVo �]�٬� Domain objects
				transactionsVO = new TransactionsVO();
				transactionsVO.setTrans_no(rs.getString("TRANS_NO"));
				transactionsVO.setMember_no(rs.getString("MEMBER_NO"));
				transactionsVO.setDeposit(rs.getInt("DEPOSIT"));
				transactionsVO.setWithdraw(rs.getInt("WITHDRAW"));
				transactionsVO.setTran_date(rs.getTimestamp("TRAN_DATE"));
				transactionsVO.setRemark(rs.getString("REMARK"));
				
			}

		}
		
		catch(SQLException e) 
		{e.printStackTrace();}
		
		
		return transactionsVO;
	}

//	@Override
	public List<TransactionsVO> getAll() {
		// TODO Auto-generated method stub
		List<TransactionsVO> list = new ArrayList<TransactionsVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				TransactionsVO transactionsVO = new TransactionsVO();
				transactionsVO = new TransactionsVO();
				transactionsVO.setTrans_no(rs.getString("TRANS_NO"));
				transactionsVO.setMember_no(rs.getString("MEMBER_NO"));
				transactionsVO.setDeposit(rs.getInt("DEPOSIT"));
				transactionsVO.setWithdraw(rs.getInt("WITHDRAW"));
				transactionsVO.setTran_date(rs.getTimestamp("TRAN_DATE"));
				transactionsVO.setRemark(rs.getString("REMARK"));
				list.add(transactionsVO);
			}
		} catch (SQLException e) {
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
	
	public List<TransactionsVO> getOneByMem(String member_no){
		List<TransactionsVO> list = new ArrayList<TransactionsVO>();
		ResultSet rs = null;
		try(Connection connection = ds.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(GET_ONE_MEM);)
		{
			ps.setString(1, member_no);
			rs = ps.executeQuery();
			while (rs.next()) {
				TransactionsVO transactionsVO = new TransactionsVO();
				transactionsVO.setTrans_no(rs.getString("TRANS_NO"));
				transactionsVO.setMember_no(rs.getString("MEMBER_NO"));
				transactionsVO.setDeposit(rs.getInt("DEPOSIT"));
				transactionsVO.setWithdraw(rs.getInt("WITHDRAW"));
				transactionsVO.setTran_date(rs.getTimestamp("TRAN_DATE"));
				transactionsVO.setRemark(rs.getString("REMARK"));
				list.add(transactionsVO);
			}
		}
		catch(SQLException e) 
		{e.printStackTrace();}
		
		return list;
	}

}
