package com.coa_follow.model;

import java.util.*;

public interface Coa_FollowDAO_interface {
	public void insert(Coa_FollowVO coa_FollowVO);
	public void delete(String member_no,String coa_no);
	public List<Coa_FollowVO> findByPrimaryKey(String member_no,String coa_no);
	public List<Coa_FollowVO> getAll();
	public int check(String member_no ,String coa_no);
}
