package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

/**
 * AreaDist联合主键
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class ExeAreaDistId implements Serializable {

	private Date stat_date;// 统计日期
	private Integer stat_point;// /统计时间点
	private String user_area_id;// 使用企业所属区域ID
	private String car_user_id;// 使用企业ID
	private String cur_adm;// 当前路局
	private String le_code;// 重空
	private String car_medium_id; //充装介质，与TB_ZBC_DIC_GOODSCATEGORY关联

	public ExeAreaDistId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExeAreaDistId(Date stat_date, Integer stat_point, String user_area_id, String car_user_id, String cur_adm,
			String le_code) {
		super();
		this.stat_date = stat_date;
		this.stat_point = stat_point;
		this.user_area_id = user_area_id;
		this.car_user_id = car_user_id;
		this.cur_adm = cur_adm;
		this.le_code = le_code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((car_user_id == null) ? 0 : car_user_id.hashCode());
		result = prime * result + ((cur_adm == null) ? 0 : cur_adm.hashCode());
		result = prime * result + ((le_code == null) ? 0 : le_code.hashCode());
		result = prime * result + ((stat_date == null) ? 0 : stat_date.hashCode());
		result = prime * result + ((stat_point == null) ? 0 : stat_point.hashCode());
		result = prime * result + ((user_area_id == null) ? 0 : user_area_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ExeAreaDistId other = (ExeAreaDistId) obj;
		if (car_user_id == null) {
			if (other.car_user_id != null)
				return false;
		} else if (!car_user_id.equals(other.car_user_id))
			return false;
		if (cur_adm == null) {
			if (other.cur_adm != null)
				return false;
		} else if (!cur_adm.equals(other.cur_adm))
			return false;
		if (le_code == null) {
			if (other.le_code != null)
				return false;
		} else if (!le_code.equals(other.le_code))
			return false;
		if (stat_date == null) {
			if (other.stat_date != null)
				return false;
		} else if (!stat_date.equals(other.stat_date))
			return false;
		if (stat_point == null) {
			if (other.stat_point != null)
				return false;
		} else if (!stat_point.equals(other.stat_point))
			return false;
		if (user_area_id == null) {
			if (other.user_area_id != null)
				return false;
		} else if (!user_area_id.equals(other.user_area_id))
			return false;
		return true;
	}

	public Date getStat_date() {
		return stat_date;
	}

	public void setStat_date(Date stat_date) {
		this.stat_date = stat_date;
	}

	public Integer getStat_point() {
		return stat_point;
	}

	public void setStat_point(Integer stat_point) {
		this.stat_point = stat_point;
	}

	public String getUser_area_id() {
		return user_area_id;
	}

	public void setUser_area_id(String user_area_id) {
		this.user_area_id = user_area_id;
	}

	public String getCar_user_id() {
		return car_user_id;
	}

	public void setCar_user_id(String car_user_id) {
		this.car_user_id = car_user_id;
	}

	public String getCur_adm() {
		return cur_adm;
	}

	public void setCur_adm(String cur_adm) {
		this.cur_adm = cur_adm;
	}

	public String getLe_code() {
		return le_code;
	}

	public void setLe_code(String le_code) {
		this.le_code = le_code;
	}

	public String getCar_medium_id() {
		return car_medium_id;
	}

	public void setCar_medium_id(String car_medium_id) {
		this.car_medium_id = car_medium_id;
	}

}
