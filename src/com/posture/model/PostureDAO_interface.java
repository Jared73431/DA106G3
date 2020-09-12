package com.posture.model;

import java.util.List;


public interface PostureDAO_interface {
	public void insert(PostureVO postureVO);
    public void update(PostureVO postureVO);
    public void delete(String posture_no);
    public PostureVO findOneByPrimaryKey(String posture_no);
    public List<PostureVO> findByPrimaryKey(String member_no);
    public List<PostureVO> getAll();
    public PostureVO findOneByDate(String member_no,java.sql.Date record_date);
    public void updateRemaincal(Double remain_cal,String posture_no);
}
