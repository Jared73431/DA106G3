package com.transactions.model;
import java.sql.*;

public class TransactionsVO implements java.io.Serializable{
	private String trans_no;
	private String member_no;
	private Integer deposit;
	private Integer withdraw;
	private Timestamp tran_date;
	private String remark;
	public TransactionsVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransactionsVO(String trans_no, String member_no, Integer deposit, Integer withdraw, Timestamp tran_date,
			String remark) {
		super();
		this.trans_no = trans_no;
		this.member_no = member_no;
		this.deposit = deposit;
		this.withdraw = withdraw;
		this.tran_date = tran_date;
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "TransactionsVO [trans_no=" + trans_no + ", member_no=" + member_no + ", deposit=" + deposit
				+ ", withdraw=" + withdraw + ", tran_date=" + tran_date + ", remark=" + remark + "]";
	}
	public String getTrans_no() {
		return trans_no;
	}
	public void setTrans_no(String trans_no) {
		this.trans_no = trans_no;
	}
	public String getMember_no() {
		return member_no;
	}
	public void setMember_no(String member_no) {
		this.member_no = member_no;
	}
	public Integer getDeposit() {
		return deposit;
	}
	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}
	public Integer getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(Integer withdraw) {
		this.withdraw = withdraw;
	}
	public Timestamp getTran_date() {
		return tran_date;
	}
	public void setTran_date(Timestamp tran_date) {
		this.tran_date = tran_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}