package com.authorizations.model;

import java.util.List;

public class AuthorizationsService {
	private AuthorizationsDAO_interface dao;
	
	public AuthorizationsService() {
		dao = new AuthorizationsDAO();
	}
	
	public AuthorizationsVO addAuthorization(String admin_no,String feature_no) {
		AuthorizationsVO authorizationsVO = new AuthorizationsVO();
		
		authorizationsVO.setAdmin_no(admin_no);
		authorizationsVO.setFeature_no(feature_no);
		
		dao.insert(authorizationsVO);
		
		return authorizationsVO;
	}
	
	public void delete(String admin_no) {
		dao.delete(admin_no);
	}
	
	public List<AuthorizationsVO> getOneAdmin(String admin_no){
		return dao.findByPrimaryKey(admin_no);
	}
	
	public  List<AuthorizationsVO> getAll(){
		return dao.getAll();
	}
}
