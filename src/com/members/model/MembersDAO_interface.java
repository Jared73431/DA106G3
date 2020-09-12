package com.members.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.members.model.MembersVO;
import com.notificationlist.model.NotificationListVO;
import com.experience.model.ExperienceVO;
import com.members.*;

public interface MembersDAO_interface {
	
	public int insert(MembersVO  membersVO);
	public int update(MembersVO  membersVO);
	public boolean delete(String member_no);
	public MembersVO findByPrimaryKey(String member_no);
	public List<MembersVO> getAll();
	public List<MembersVO> getNoStatus(Integer mem_status);
	public MembersVO getOneMemberByAccount(String mem_account);
	public void updateMembersRealBlance(Integer real_blance, String member_no);
	public void updateCoa(Integer coa_qualifications, byte[] license, String member_no);
	public void updateStatus(Integer mem_status, String member_no);
//	public List<MembersVO> getAll(Map<String, String[]> map);
	// 勝瑜加入為了抓心得與通知
	public Set<NotificationListVO> getNotesByMember(String member_no);
	public Set<ExperienceVO> getExoerByMember(String member_no);
}