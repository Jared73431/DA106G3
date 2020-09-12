package com.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.groupgo.model.*;
import com.mygroup.model.*;
import com.notificationlist.model.NotificationListService;
import com.notificationlist.model.NotificationListVO;

public class MyTimerTask extends TimerTask {
	List<Date> schedule = new ArrayList();

	@Override
	public void run() {

		schedule.add(new Date(scheduledExecutionTime()));

		System.out.println("schedule=" + schedule);

		GroupgoService groupgoSvc = new GroupgoService();
		NotificationListService noteSvc = new NotificationListService();
		List list = groupgoSvc.getAll();
		for (int i = 0; i < list.size(); i++) {
			GroupgoVO groupgoVO = (GroupgoVO) list.get(i);
			if (groupgoVO.getDeadline().before(schedule.get(schedule.size() - 1))) {
				if (groupgoVO.getGroup_status() == 1 && groupgoVO.getCurrent_peo() >= groupgoVO.getPeople_num()) {
					MygroupService mygroupsvc = new MygroupService();
					groupgoSvc.updateStatus(2, groupgoVO.getGroupgo_id());
					List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgoVO.getGroupgo_id());
					for (MyGroupVO groupMember : groupmember) {
						MyGroupVO mygroupVO = new MyGroupVO();
						mygroupVO.setGroupgo_id(groupgoVO.getGroupgo_id());
						mygroupVO.setMember_no(groupMember.getMember_no());
						mygroupVO.setMygroup_status(2);
						mygroupsvc.updateStatus(mygroupVO);
					noteSvc.addNotification(groupMember.getMember_no(), "CA0002",  groupgoSvc.getOneGroup(groupMember.getGroupgo_id()).getGroup_name()+"準備開始囉");
							
					}
				} else if (groupgoVO.getGroup_status() == 1 && groupgoVO.getCurrent_peo() < groupgoVO.getPeople_num()) {
					MygroupService mygroupsvc = new MygroupService();
					groupgoSvc.updateStatus(4, groupgoVO.getGroupgo_id());
					List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgoVO.getGroupgo_id());
					for (MyGroupVO groupMember : groupmember) {
						MyGroupVO mygroupVO = new MyGroupVO();
						mygroupVO.setGroupgo_id(groupgoVO.getGroupgo_id());
						mygroupVO.setMember_no(groupMember.getMember_no());
						mygroupVO.setMygroup_status(4);
						mygroupsvc.updateStatus(mygroupVO);
						noteSvc.addNotification(groupMember.getMember_no(), "CA0002", groupgoSvc.getOneGroup(groupMember.getGroupgo_id()).getGroup_name()+"人數不夠,取消了QQ");
						noteSvc.addNotification(groupgoSvc.getOneGroup(groupMember.getGroupgo_id()).getMaster_id(), "CA0002",  groupgoSvc.getOneGroup(groupMember.getGroupgo_id()).getGroup_name()+"準備開始囉");
					}
				} else if (groupgoVO.getGroup_date().before(schedule.get(schedule.size() - 1))) {
				
					if (groupgoVO.getGroup_status() == 2) {
						
						MygroupService mygroupsvc = new MygroupService();
						groupgoSvc.updateStatus(3, groupgoVO.getGroupgo_id());
						List<MyGroupVO> groupmember = mygroupsvc.getGroupAttend(groupgoVO.getGroupgo_id());
						for (MyGroupVO groupMember : groupmember) {
							MyGroupVO mygroupVO = new MyGroupVO();
							mygroupVO.setGroupgo_id(groupgoVO.getGroupgo_id());
							mygroupVO.setMember_no(groupMember.getMember_no());
							mygroupVO.setMygroup_status(3);
							mygroupsvc.updateStatus(mygroupVO);
							mygroupVO.setScore(10);
							mygroupsvc.updateScore(mygroupVO);
							noteSvc.addNotification(groupMember.getMember_no(), "CA0002",  groupgoSvc.getOneGroup(groupMember.getGroupgo_id()).getGroup_name()+"結束了,記得去評分喔!");
							GroupgoService groupgosvc = new GroupgoService();
//							Integer current_peo = groupgosvc.getOneGroup(groupgoVO.getGroupgo_id()).getCurrent_peo();
							Integer score_sum = groupgosvc.getScoreSum(groupgoVO.getGroupgo_id());
							groupgosvc.updateScoreSum(score_sum, groupgoVO.getGroupgo_id());
						}
					}
				}

			}
		}
	}
}
