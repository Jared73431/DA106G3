package com.groupreport.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.groupgo.model.GroupgoVO;

public class GroupReportService {
	private GroupReportDAO_interface dao;
	private Map<Integer, List<GroupReportVO>> groupReportMap;

	public GroupReportService() {
		dao = new GroupReportDAO();
		List<GroupReportVO> groupreportList = dao.getAll();
		groupReportMap = new HashMap<>();
		groupReportMap = groupreportList.stream().collect(Collectors.groupingBy(GroupReportVO::getReport_status));
	}
	
	public List<GroupReportVO> getAll(){
		return dao.getAll();
	}
	
	public GroupReportVO addGroupReport(String groupgo_id, String member_no, String report_content) {
		GroupReportVO groupreportVO = new GroupReportVO();
		groupreportVO.setGroupgo_id(groupgo_id);
		groupreportVO.setMember_no(member_no);
		groupreportVO.setReport_content(report_content);
		
		dao.insert(groupreportVO);
		
		return groupreportVO;
	}
	public List<GroupReportVO> getStatus(Integer report_status){
		return dao.getStatus(report_status);
	}
	
	public void update(GroupReportVO groupreportVO) {
		dao.update(groupreportVO);
	}
	
	 public List<GroupReportVO> getWait() {		
		return groupReportMap.get(1);
	}
	 public List<GroupReportVO> getPass() {		
		return groupReportMap.get(2);
	}
	 public List<GroupReportVO> getFinish() {		
		return groupReportMap.get(3);
	}
	 public void updateStatus(String groupgo_id, Integer report_status) {
		 dao.updateStatus(groupgo_id, report_status);
	 }
	
}
