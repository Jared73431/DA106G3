package com.authorizations.model;

import java.util.*;

public interface AuthorizationsDAO_interface {
	public void insert(AuthorizationsVO authorizationsVO);
	public void delete(String admin_no);
	public List<AuthorizationsVO> findByPrimaryKey(String admin_no);
	public List<AuthorizationsVO> getAll();
	
}
