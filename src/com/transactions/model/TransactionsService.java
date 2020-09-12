package com.transactions.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsService {

	private TransactionsDAO_interface dao;

	public TransactionsService() {
		dao = new TransactionsJNDIDAO();
	}

	public TransactionsVO addTransactions(String member_no, Integer deposit, Integer withdraw,
			String remark) {
		
		TransactionsVO transactionsVO = new TransactionsVO();

		transactionsVO.setMember_no(member_no);
		transactionsVO.setDeposit(deposit);
		transactionsVO.setWithdraw(withdraw);
//		transactionsVO.setTran_date(tran_date);
		transactionsVO.setRemark(remark);
		dao.insert(transactionsVO);

		return transactionsVO;
	}

	public TransactionsVO updateTransactions(String trans_no, String member_no, Integer deposit, Integer withdraw,
			String remark) {
		TransactionsVO transactionsVO = new TransactionsVO();

		transactionsVO.setTrans_no(trans_no);
		transactionsVO.setMember_no(member_no);
		transactionsVO.setDeposit(deposit);
		transactionsVO.setWithdraw(withdraw);
//		transactionsVO.setTran_date(tran_date);
		transactionsVO.setRemark(remark);
		dao.updateBookingData(transactionsVO);
		return transactionsVO;
	}

	public boolean deleteTransactions(String trans_no) {
		return dao.delete(trans_no);
	}

	public TransactionsVO getOneTransactions(String trans_no) {
		return dao.findByPrimaryKey(trans_no);
	}

	public List<TransactionsVO> getAll() {
		return dao.getAll();
	}
	
	public List<TransactionsVO> getOneByMem(String member_no) {
		return dao.getOneByMem(member_no);
	}
}
