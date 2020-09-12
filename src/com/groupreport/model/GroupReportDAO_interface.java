package com.groupreport.model;

import java.util.List;

public interface GroupReportDAO_interface {
	 public void insert(GroupReportVO groupReportVO);
	 public List<GroupReportVO> getAll();
     public List<GroupReportVO> getStatus(Integer reportStatus);
     public void update(GroupReportVO groupReportVO);
     public void updateStatus(String groupgo_id, Integer report_status);
}
