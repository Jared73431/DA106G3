package com.race.model;

import java.util.*;

import com.product.model.ProductVO;

public interface RaceDAO_interface {
	public int insert(RaceinformVO raceinformVO);
	public void update(RaceinformVO raceinformVO);
	public RaceinformVO findByPrimaryKey(String race_no);
	public List<RaceVO> raceList(String race_no);
	public List<RaceinformVO> getAll();
}
