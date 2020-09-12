package com.transactions.model;

import java.util.List;
import java.util.Map;

import com.transactions.*;

public interface TransactionsDAO_interface {
	
	public int insert(TransactionsVO  transactionsVO);
	public int updateBookingData(TransactionsVO  transactionsVO);
	public boolean delete(String transactionsVO);
	public TransactionsVO findByPrimaryKey(String transactionsVO);
	public List<TransactionsVO> getAll();
	public List<TransactionsVO> getOneByMem(String member_no);
//	public List<TransactionsVO> getAll(Map<String, String[]> map);
}