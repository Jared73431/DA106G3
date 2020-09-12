package com.dietdetail.model;

import java.sql.Connection;
import java.util.List;



public interface DietDetailDAO_interface {

    public int insert(DietDetailVO dietdetailVO);
    
    public void insert2(DietDetailVO dietdetailVO, Connection con);

    public int update(DietDetailVO dietdetailVO);

    public void delete(String diet_no,String food_no);

    public List<DietDetailVO> findByPrimaryKey(String diet_no);
    
    public DietDetailVO findOneByPrimaryKey(String diet_no,String food_no);

    public List<DietDetailVO> getAll();
}
