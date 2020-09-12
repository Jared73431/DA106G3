package com.informcate.model;

import java.util.*;

public interface InformCateDAO_interface {
	public int insert(InformCateVO informcateVO);
	public int update(InformCateVO informcateVO);
	public int delete(String cate_no);
    public InformCateVO findByPrimaryKey(String cate_no);
	public List<InformCateVO> getAll();

}
