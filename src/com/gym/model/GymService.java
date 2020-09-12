package com.gym.model;

import java.util.List;

public class GymService {
	
	private GymDAO_interface dao;

	public GymService() {
		dao = new GymDAO();
	}

	public GymVO addGym(String gym_name,String gym_address,Double gym_lon, Double gym_lat, String gym_site) {

		GymVO gymVO = new GymVO();

		gymVO.setGym_name(gym_name);
		gymVO.setGym_address(gym_address);
		gymVO.setGym_lon(gym_lon);
		gymVO.setGym_lat(gym_lat);
		gymVO.setGym_site(gym_site);
		dao.insert(gymVO);

		return gymVO;
	}

	public GymVO updateGym(String gym_no, String gym_name,String gym_address,Double gym_lon, Double gym_lat, String gym_site) {

		GymVO gymVO = new GymVO();

		gymVO.setGym_no(gym_no);
		gymVO.setGym_name(gym_name);
		gymVO.setGym_address(gym_address);
		gymVO.setGym_lon(gym_lon);
		gymVO.setGym_lat(gym_lat);
		gymVO.setGym_site(gym_site);
		dao.update(gymVO);

		return gymVO;
	}

	public void deleteGym(String gym_no) {
		dao.delete(gym_no);
	}

	public GymVO getOneGym(String gym_no) {
		return dao.findByPrimaryKey(gym_no);
	}

	public List<GymVO> getAll() {
		return dao.getAll();
	}
}
