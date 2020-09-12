package com.dietrecord.model;

import java.util.List;

import com.dietdetail.model.DietDetailVO;


public interface DietRecordDAO_interface {

    public int insert(DietRecordVO dietRecordVO);

    public int update(DietRecordVO dietRecordVO);

    public int delete(String diet_no);

    public DietRecordVO findByPrimaryKey(String diet_no);

    public List<DietRecordVO> getAll();
    
    public void insertWithDetail(DietRecordVO dietRecordVO , List<DietDetailVO> list);
    
    public List findByDate(String member_no, java.sql.Date rec_date);
    
    public void delete_pic (String diet_no);
}
