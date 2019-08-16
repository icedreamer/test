package com.tlys.dic.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DicEntprstnIOID implements Serializable {

	private String entpr_id;// 企业编号
	private String entpr_stn_code;// 企业站区代码
	private Integer aei_no;// AEI号
	private String train_dir;// 列车方向
	private String in_out_flag;// 进出标识

	public DicEntprstnIOID() {
		super();
	}

	public String getEntpr_id() {
		return entpr_id;
	}

	public DicEntprstnIOID(String entpr_id, String entpr_stn_code, Integer aei_no, String train_dir, String in_out_flag) {
		super();
		this.entpr_id = entpr_id;
		this.entpr_stn_code = entpr_stn_code;
		this.aei_no = aei_no;
		this.train_dir = train_dir;
		this.in_out_flag = in_out_flag;
	}

	public void setEntpr_id(String entpr_id) {
		this.entpr_id = entpr_id;
	}

	public String getEntpr_stn_code() {
		return entpr_stn_code;
	}

	public void setEntpr_stn_code(String entpr_stn_code) {
		this.entpr_stn_code = entpr_stn_code;
	}

	public Integer getAei_no() {
		return aei_no;
	}

	public void setAei_no(Integer aei_no) {
		this.aei_no = aei_no;
	}

	public String getTrain_dir() {
		return train_dir;
	}

	public void setTrain_dir(String train_dir) {
		this.train_dir = train_dir;
	}

	public String getIn_out_flag() {
		return in_out_flag;
	}

	public void setIn_out_flag(String in_out_flag) {
		this.in_out_flag = in_out_flag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aei_no == null) ? 0 : aei_no.hashCode());
		result = prime * result + ((entpr_id == null) ? 0 : entpr_id.hashCode());
		result = prime * result + ((entpr_stn_code == null) ? 0 : entpr_stn_code.hashCode());
		result = prime * result + ((in_out_flag == null) ? 0 : in_out_flag.hashCode());
		result = prime * result + ((train_dir == null) ? 0 : train_dir.hashCode());
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
		final DicEntprstnIOID other = (DicEntprstnIOID) obj;
		if (aei_no == null) {
			if (other.aei_no != null)
				return false;
		} else if (!aei_no.equals(other.aei_no))
			return false;
		if (entpr_id == null) {
			if (other.entpr_id != null)
				return false;
		} else if (!entpr_id.equals(other.entpr_id))
			return false;
		if (entpr_stn_code == null) {
			if (other.entpr_stn_code != null)
				return false;
		} else if (!entpr_stn_code.equals(other.entpr_stn_code))
			return false;
		if (in_out_flag == null) {
			if (other.in_out_flag != null)
				return false;
		} else if (!in_out_flag.equals(other.in_out_flag))
			return false;
		if (train_dir == null) {
			if (other.train_dir != null)
				return false;
		} else if (!train_dir.equals(other.train_dir))
			return false;
		return true;
	}
}
