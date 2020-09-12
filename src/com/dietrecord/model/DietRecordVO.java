package com.dietrecord.model;

import java.sql.*;
import java.util.Arrays;

public class DietRecordVO implements java.io.Serializable {
    /**
     * 
     */


    private String diet_no;
    private String member_no;
    private Date rec_date;
    private Integer eat_period;
    private byte[] photo;

    public DietRecordVO() {
        super();
    }

    public DietRecordVO(String diet_no, String member_no, Date rec_date,
            Integer eat_period, byte[] photo) {
        super();
        this.diet_no = diet_no;
        this.member_no = member_no;
        this.rec_date = rec_date;
        this.eat_period = eat_period;
        this.photo = photo;
    }

    public String getDiet_no() {
        return diet_no;
    }

    public void setDiet_no(String diet_no) {
        this.diet_no = diet_no;
    }

    public String getMember_no() {
        return member_no;
    }

    public void setMember_no(String member_no) {
        this.member_no = member_no;
    }

    public Date getRec_date() {
        return rec_date;
    }

    public void setRec_date(Date rec_date) {
        this.rec_date = rec_date;
    }

    public Integer getEat_period() {
        return eat_period;
    }

    public void setEat_period(Integer eat_period) {
        this.eat_period = eat_period;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

	@Override
	public String toString() {
		return "DietRecordVO [diet_no=" + diet_no + ", member_no=" + member_no + ", rec_date=" + rec_date
				+ ", eat_period=" + eat_period  + "]";
	}

 

}
