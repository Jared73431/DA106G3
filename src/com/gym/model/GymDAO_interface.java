package com.gym.model;

import java.util.*;

public interface GymDAO_interface {
	public int insert(GymVO gymVO);
	public int update(GymVO gymVO);
	public void delete(String gym_no);
    public GymVO findByPrimaryKey(String gym_no);
	public List<GymVO> getAll();

}
