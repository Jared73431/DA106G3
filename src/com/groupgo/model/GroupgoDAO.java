package com.groupgo.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import java.io.*;
import java.sql.*;

public class GroupgoDAO implements GroupgoDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDBG3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO GROUPGO (GROUPGO_ID, MASTER_ID, GROUP_NAME, GROUP_DATE, PLACE, "
			+ "LON, LAT, PEOPLE_NUM, BUDGET, GROUP_CONTENT, PHOTO, DEADLINE) "
			+ "VALUES (('G'||LPAD(to_char(GROUPGO_SEQ.NEXTVAL),6,'0')),?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT GROUPGO_ID, MASTER_ID, GROUP_NAME, to_char(GROUP_DATE,'yyyy-mm-dd hh24:mi:ss') GROUP_DATE, "
			+ "PLACE,LON,LAT, GROUP_STATUS, PEOPLE_NUM, CURRENT_PEO, BUDGET, GROUP_CONTENT, "
			+ "PHOTO, to_char(DEADLINE,'yyyy-mm-dd hh24:mi:ss') DEADLINE, SCORE_SUM FROM GROUPGO order by GROUPGO_ID";
	private static final String GET_ONE_STMT = "SELECT GROUPGO_ID, MASTER_ID, GROUP_NAME, to_char(GROUP_DATE,'yyyy-mm-dd hh24:mi:ss') GROUP_DATE, "
			+ "PLACE,LON,LAT, GROUP_STATUS, PEOPLE_NUM, CURRENT_PEO, BUDGET, GROUP_CONTENT, "
			+ "PHOTO, to_char(DEADLINE,'yyyy-mm-dd hh24:mi:ss') DEADLINE, SCORE_SUM FROM GROUPGO where GROUPGO_ID = ?";
	private static final String GET_MASTER_STMT = "SELECT GROUPGO_ID, MASTER_ID, GROUP_NAME, to_char(GROUP_DATE,'yyyy-mm-dd hh24:mi:ss') GROUP_DATE, "
			+ "PLACE,LON,LAT, GROUP_STATUS, PEOPLE_NUM, CURRENT_PEO, BUDGET, GROUP_CONTENT, "
			+ "PHOTO, to_char(DEADLINE,'yyyy-mm-dd hh24:mi:ss') DEADLINE, SCORE_SUM FROM GROUPGO where MASTER_ID = ? order by group_status";
	private static final String DELETE = "DELETE FROM GROUPGO where GROUPGO_ID = ?";
	private static final String UPDATE = "UPDATE GROUPGO set  MASTER_ID=?,GROUP_NAME=?, GROUP_DATE=?, PLACE=?, LON=?, LAT=?, GROUP_STATUS=?, "
			+ "PEOPLE_NUM=?, BUDGET=?, GROUP_CONTENT=?, PHOTO=?, DEADLINE=? where GROUPGO_ID = ?";
	
	private static final String TOTAL_SCORE = "SELECT SUM(SCORE) FROM MY_GROUP WHERE GROUPGO_ID =?";
	private static final String UPDATE_SCORE_SUM="UPDATE GROUPGO set SCORE_SUM=? WHERE GROUPGO_ID=?";
	private static final String UPDATE_CURRENT_PEO="UPDATE GROUPGO set CURRENT_PEO=? WHERE GROUPGO_ID=?";
	private static final String UPDATE_STATUS="UPDATE GROUPGO set GROUP_STATUS=? WHERE GROUPGO_ID=?";
	private static final String GET_NEAR_STMT = "SELECT * FROM GROUPGO WHERE (LON BETWEEN ?-0.1 AND ?+0.1) AND (LAT BETWEEN ?-0.1 AND ?+0.1) AND GROUP_STATUS='1'";

	
	public void insert(GroupgoVO groupgoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, groupgoVO.getMaster_id());
			pstmt.setString(2, groupgoVO.getGroup_name());
			pstmt.setTimestamp(3, groupgoVO.getGroup_date());
			pstmt.setString(4, groupgoVO.getPlace());
			pstmt.setDouble(5, groupgoVO.getLon());
			pstmt.setDouble(6, groupgoVO.getLat());
			pstmt.setInt(7, groupgoVO.getPeople_num());
			pstmt.setString(8, groupgoVO.getBudget());
			pstmt.setString(9, groupgoVO.getGroup_content());
			pstmt.setBytes(10, groupgoVO.getPhoto());
			pstmt.setTimestamp(11, groupgoVO.getDeadline());

			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public void update(GroupgoVO groupgoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, groupgoVO.getMaster_id());
			pstmt.setString(2, groupgoVO.getGroup_name());
			pstmt.setTimestamp(3, groupgoVO.getGroup_date());
			pstmt.setString(4, groupgoVO.getPlace());
			pstmt.setDouble(5, groupgoVO.getLon());
			pstmt.setDouble(6, groupgoVO.getLat());
			pstmt.setInt(7, groupgoVO.getGroup_status());
			pstmt.setInt(8, groupgoVO.getPeople_num());
			pstmt.setString(9, groupgoVO.getBudget());
			pstmt.setString(10, groupgoVO.getGroup_content());
			if (groupgoVO.getPhoto().length == 0) {
				GroupgoDAO dao = new GroupgoDAO();
				pstmt.setBytes(11, dao.findByPrimaryKey(groupgoVO.getGroupgo_id()).getPhoto());
			} else {
				pstmt.setBytes(11, groupgoVO.getPhoto());
			}
			pstmt.setTimestamp(12, groupgoVO.getDeadline());
			pstmt.setString(13, groupgoVO.getGroupgo_id());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public void delete(String groupgo_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, groupgo_id);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public GroupgoVO findByPrimaryKey(String groupgo_id) {
		GroupgoVO groupgoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, groupgo_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupgoVO = new GroupgoVO();
				groupgoVO.setGroupgo_id(rs.getString("groupgo_id"));
				groupgoVO.setMaster_id(rs.getString("master_id"));
				groupgoVO.setGroup_name(rs.getString("group_name"));
				groupgoVO.setGroup_date(rs.getTimestamp("group_date"));
				groupgoVO.setPlace(rs.getString("place"));
				groupgoVO.setLon(rs.getDouble("lon"));
				groupgoVO.setLat(rs.getDouble("lat"));
				groupgoVO.setGroup_status(rs.getInt("group_status"));
				groupgoVO.setPeople_num(rs.getInt("people_num"));
				groupgoVO.setCurrent_peo(rs.getInt("current_peo"));
				groupgoVO.setBudget(rs.getString("budget"));
				groupgoVO.setGroup_content(rs.getString("group_content"));
				groupgoVO.setPhoto(rs.getBytes("photo"));
				groupgoVO.setDeadline(rs.getTimestamp("deadline"));
				groupgoVO.setScore_sum(rs.getInt("score_sum"));

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return groupgoVO;
	}

	public List<GroupgoVO> findByMaster(String master_id) {
		List<GroupgoVO> list = new ArrayList<GroupgoVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MASTER_STMT);

			pstmt.setString(1, master_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				GroupgoVO groupgoVO = new GroupgoVO();
				groupgoVO.setGroupgo_id(rs.getString("groupgo_id"));
				groupgoVO.setMaster_id(rs.getString("master_id"));
				groupgoVO.setGroup_name(rs.getString("group_name"));
				groupgoVO.setGroup_date(rs.getTimestamp("group_date"));
				groupgoVO.setPlace(rs.getString("place"));
				groupgoVO.setLon(rs.getDouble("lon"));
				groupgoVO.setLat(rs.getDouble("lat"));
				groupgoVO.setGroup_status(rs.getInt("group_status"));
				groupgoVO.setPeople_num(rs.getInt("people_num"));
				groupgoVO.setCurrent_peo(rs.getInt("current_peo"));
				groupgoVO.setBudget(rs.getString("budget"));
				groupgoVO.setGroup_content(rs.getString("group_content"));
		//		groupgoVO.setPhoto(rs.getBytes("photo"));
				groupgoVO.setDeadline(rs.getTimestamp("deadline"));
				groupgoVO.setScore_sum(rs.getInt("score_sum"));
				list.add(groupgoVO);

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public List<GroupgoVO> getAll() {

		List<GroupgoVO> list = new ArrayList<GroupgoVO>();
		GroupgoVO groupgoVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupgoVO = new GroupgoVO();
				groupgoVO.setGroupgo_id(rs.getString("groupgo_id"));
				groupgoVO.setMaster_id(rs.getString("master_id"));
				groupgoVO.setGroup_name(rs.getString("group_name"));
				groupgoVO.setGroup_date(rs.getTimestamp("group_date"));
				groupgoVO.setPlace(rs.getString("place"));
				groupgoVO.setLon(rs.getDouble("lon"));
				groupgoVO.setLat(rs.getDouble("lat"));
				groupgoVO.setGroup_status(rs.getInt("group_status"));
				groupgoVO.setPeople_num(rs.getInt("people_num"));
				groupgoVO.setCurrent_peo(rs.getInt("current_peo"));
				groupgoVO.setBudget(rs.getString("budget"));
				groupgoVO.setGroup_content(rs.getString("group_content"));
			//	groupgoVO.setPhoto(rs.getBytes("photo"));
				groupgoVO.setDeadline(rs.getTimestamp("deadline"));
				groupgoVO.setScore_sum(rs.getInt("score_sum"));
				list.add(groupgoVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
	
	public List<GroupgoVO> findNear(Double lon, Double lat) {
		List<GroupgoVO> list = new ArrayList<GroupgoVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_NEAR_STMT);

			pstmt.setDouble(1, lon);
			pstmt.setDouble(2, lon);
			pstmt.setDouble(3, lat);
			pstmt.setDouble(4, lat);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				GroupgoVO groupgoVO = new GroupgoVO();
				groupgoVO.setGroupgo_id(rs.getString("groupgo_id"));
				groupgoVO.setMaster_id(rs.getString("master_id"));
				groupgoVO.setGroup_name(rs.getString("group_name"));
				groupgoVO.setGroup_date(rs.getTimestamp("group_date"));
				groupgoVO.setPlace(rs.getString("place"));
				groupgoVO.setLon(rs.getDouble("lon"));
				groupgoVO.setLat(rs.getDouble("lat"));
				groupgoVO.setGroup_status(rs.getInt("group_status"));
				groupgoVO.setPeople_num(rs.getInt("people_num"));
				groupgoVO.setCurrent_peo(rs.getInt("current_peo"));
				groupgoVO.setBudget(rs.getString("budget"));
				groupgoVO.setGroup_content(rs.getString("group_content"));
	//			groupgoVO.setPhoto(rs.getBytes("photo"));
				groupgoVO.setDeadline(rs.getTimestamp("deadline"));
				groupgoVO.setScore_sum(rs.getInt("score_sum"));
				list.add(groupgoVO);

			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	

	public List<GroupgoVO> magic_getAll(Map<String, String[]> map) {
		GroupgoVO grVO5 = null;
		List<GroupgoVO> list = new ArrayList<>();

		StringBuffer Magic_Select_Stmt = new StringBuffer("SELECT * FROM GROUPGO WHERE ");
		String[] valueArray = null;
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Magic_Select_Stmt.append(key + " in (");
			valueArray = map.get(key);
			for (int i = 0; i < valueArray.length; i++) {
				Magic_Select_Stmt.append("\'" + valueArray[i] + "\'" + ((i == valueArray.length - 1) ? "" : ","));
			}
			Magic_Select_Stmt.append(")" + (iterator.hasNext() ? " and " : ""));
		} // System.out.println(Magic_Select_Stmt.toString());
		try (Connection connection = ds.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(Magic_Select_Stmt.toString());) {
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					grVO5 = new GroupgoVO();
					grVO5.setGroupgo_id(rs.getString("groupgo_id"));
					grVO5.setMaster_id(rs.getString("master_id"));
					grVO5.setGroup_name(rs.getString("group_name"));
					grVO5.setGroup_date(rs.getTimestamp("group_date"));
					grVO5.setPlace(rs.getString("place"));
					grVO5.setGroup_status(rs.getInt("group_status"));
					grVO5.setPeople_num(rs.getInt("people_num"));
					grVO5.setCurrent_peo(rs.getInt("current_peo"));
					grVO5.setBudget(rs.getString("budget"));

					Clob clob = rs.getClob("group_content");// java.sql.Clob
					String detailinfo = "";
					if (clob != null) {
						detailinfo = clob.getSubString((long) 1, (int) clob.length());
					}
					grVO5.setGroup_content(detailinfo);
		//			grVO5.setPhoto(rs.getBytes("photo"));
					grVO5.setDeadline(rs.getTimestamp("deadline"));
					grVO5.setScore_sum(rs.getInt("score_sum"));
					list.add(grVO5);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Integer getScoreSum(String groupgo_id) {
		Integer score_sum = 0;
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(TOTAL_SCORE)) {
			ps.setString(1, groupgo_id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					score_sum = rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return score_sum;
	}
	
	
	
	public void updateScoreSum(Integer score_sum,String groupgo_id) {
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_SCORE_SUM)) {
			ps.setInt(1, score_sum);
			ps.setString(2, groupgo_id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
	}
	}
	 public void updateCurrentPeo(Integer current_peo,String groupgo_id) {
		 try (Connection connection = ds.getConnection();
					PreparedStatement ps = connection.prepareStatement(UPDATE_CURRENT_PEO)) {
				ps.setInt(1,current_peo);
				ps.setString(2, groupgo_id);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
		}
	 }
	 public void updateStatus(Integer group_status,String groupgo_id) {
		 try (Connection connection = ds.getConnection();
					PreparedStatement ps = connection.prepareStatement(UPDATE_STATUS)) {
				ps.setInt(1,group_status);
				ps.setString(2, groupgo_id);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
		}
	 }
//	public static void main(String[] args) {
//		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
//		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
//		GroupgoJDBCDAO dao = new GroupgoJDBCDAO();
//
//		// 新增
//		GroupgoVO grVO1 = new GroupgoVO();
//		grVO1.setMaster_id("M000005");
//		grVO1.setGroup_name("運動");
//		grVO1.setGroup_date(java.sql.Timestamp.valueOf("2020-05-29 20:25:58"));
//		grVO1.setPlace("台北");
//		grVO1.setLon(121.222222);
//		grVO1.setLat(22.333333);
//		grVO1.setPeople_num(20);
//		grVO1.setBudget("500");
//				
//		try {
//			String str = getLongString("images/miew.txt");
//			grVO1.setGroup_content(str);
//			byte[] pic = getPictureByteArray("images/p1.jpg");
//			grVO1.setPhoto(pic);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		grVO1.setDeadline( java.sql.Timestamp.valueOf("2020-05-20 20:25:58"));
//		dao.insert(grVO1);

	// 修改
//		GroupgoVO grVO2 = new GroupgoVO();
//		
//		grVO2.setGroup_name("修改了沒");
//		grVO2.setGroup_date(java.sql.Timestamp.valueOf("2020-05-20 20:25:58"));
//		grVO2.setPlace("台北");
//		grVO2.setLon(new Double(121.222222));
//		grVO2.setLat(new Double(22.333333));
//		grVO2.setGroup_status(new Integer(0));
//		grVO2.setPeople_num(new Integer(20));
//		grVO2.setBudget("5000");
//		byte[] pic2;
//		try {
//			String str = getLongString("images/t1.txt");
//			grVO2.setGroup_content(str);
//			pic2 = getPictureByteArray("images/p1.jpg");
//			grVO2.setPhoto(pic2);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
//		grVO2.setDeadline(java.sql.Timestamp.valueOf("2020-05-20 20:25:58"));
//		grVO2.setGroupgo_id("G000007");
//		dao.update(grVO2);

//		 //刪除
//		dao.delete("G000027");
//		// 查詢
//		GroupgoVO grVO3 = dao.findByPrimaryKey("G000004");
//		System.out.print(grVO3.getGroupgo_id() + ",");
//		System.out.print(grVO3.getMaster_id() + ",");
//		System.out.print(grVO3.getGroup_name() + ",");
//		System.out.print(sdf3.format(grVO3.getGroup_date()) + ",");
//		System.out.print(grVO3.getPlace() + ",");
//		System.out.print(grVO3.getGroup_status() + ",");
//		System.out.print(grVO3.getPeople_num() + ",");
//		System.out.print(grVO3.getCurrent_peo() + ",");
//		System.out.print(grVO3.getBudget() + ",");
//		System.out.print(grVO3.getGroup_content() + ",");
//		System.out.print(grVO3.getPhoto() + ",");
//		System.out.print(sdf4.format(grVO3.getDeadline()) + ",");
//		System.out.println(grVO3.getScore_sum() + ",");
//		System.out.println("---------------------");

//		// 查詢
//		List<GroupgoVO> list = dao.getAll();
//		for (GroupgoVO aGroup : list) {
//			System.out.print(aGroup.getGroupgo_id() + ",");
//			System.out.print(aGroup.getMaster_id() + ",");
//			System.out.print(aGroup.getGroup_name() + ",");
//			System.out.print(sdf3.format(aGroup.getGroup_date()) + ",");
//			System.out.print(aGroup.getPlace() + ",");
//			System.out.print(aGroup.getGroup_status() + ",");
//			System.out.print(aGroup.getPeople_num() + ",");			
//			System.out.print(aGroup.getCurrent_peo() + ",");
//			System.out.print(aGroup.getBudget() + ",");
//			System.out.print(aGroup.getGroup_content() + ",");
//			System.out.print(aGroup.getPhoto() + ",");
//			System.out.print(sdf4.format(aGroup.getDeadline()) + ",");
//			System.out.print(aGroup.getScore_sum() + ",");
//			System.out.println();
//		}
// 萬用select		
//		Map<String,String[]> all = new HashMap<>();
//		String[] master = {"M000005"};
//		String[] gs = {"1"};
//		all.put("MASTER_ID", master);
//		all.put("GROUP_STATUS", gs);
//		List<GroupgoVO> listM = dao.getAll(all);
//		for (GroupgoVO aGroup : listM) {
//			System.out.print(aGroup.getGroupgo_id() + ",");
//			System.out.print(aGroup.getMaster_id() + ",");
//			System.out.print(aGroup.getGroup_name() + ",");
//			System.out.print(sdf3.format(aGroup.getGroup_date()) + ",");
//			System.out.print(aGroup.getPlace() + ",");
//			System.out.print(aGroup.getGroup_status() + ",");
//			System.out.print(aGroup.getPeople_num() + ",");		
//			System.out.print(aGroup.getCurrent_peo() + ",");
//			System.out.print(aGroup.getBudget() + ",");
//			System.out.print(aGroup.getGroup_content() + ",");
//			System.out.print(aGroup.getPhoto() + ",");
//			System.out.print(sdf4.format(aGroup.getDeadline()) + ",");
//			System.out.print(aGroup.getScore_sum() + ",");
//			System.out.println();
//		}

//	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	public static String getLongString(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder(); // StringBuffer is thread-safe!
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();

		return sb.toString();
	}
}
