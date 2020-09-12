package com.admins.model;

import java.io.File;
import java.util.*;

public interface AdminsDAO_interface {
	public void insert(AdminsVO adminsVO);
	public void update(AdminsVO adminsVO);
	public void delete(String admin_no);
	public AdminsVO findByPrimaryKey(String admin_no);
	public List<AdminsVO> getAll();
	public byte[] getPicture(String admin_no);
	public int checkAccount(String admin_account); 

}
