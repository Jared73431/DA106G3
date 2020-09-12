package com.race.model;

import java.util.List;

public class RaceService {
	
	private RaceDAO_interface dao;

	public RaceService() {
		dao = new RaceDAO();
	}

	public RaceinformVO addRace(String race_year,String race_inform) {

		RaceinformVO raceinformVO = new RaceinformVO();

		raceinformVO.setRace_year(race_year);
		raceinformVO.setRace_inform(race_inform);
		dao.insert(raceinformVO);

		return raceinformVO;
	}

	public RaceinformVO updateRace(String race_no, String cate_no, String race_year,String race_inform) {

		RaceinformVO raceinformVO = new RaceinformVO();

		raceinformVO.setRace_no(race_no);
		raceinformVO.setCate_no(cate_no);		
		raceinformVO.setRace_year(race_year);
		raceinformVO.setRace_inform(race_inform);
		dao.update(raceinformVO);

		return raceinformVO;
	}
	
	public RaceinformVO getOneRace(String race_no) {
		return dao.findByPrimaryKey(race_no);
	}

	public List<RaceVO> getRace(String race_no) {
		return dao.raceList(race_no);
	}

	public List<RaceinformVO> getAll() {
		return dao.getAll();
	}
}
