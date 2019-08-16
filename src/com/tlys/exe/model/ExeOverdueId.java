package com.tlys.exe.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * ExeOverdue的主键
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Embeddable
public class ExeOverdueId implements Serializable {
	private String entpr_id;// 企业ID
	private String medium_id;// 充装介质ID
	private String over_id;// 统计天数间隔
	private String over_type;// 预警标识
	private String zbc_flag;// 自备车标识

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	public String getEntpr_id() {
		return entpr_id;
	}

	public void setEntpr_id(String entpr_id) {
		this.entpr_id = entpr_id;
	}

	public String getMedium_id() {
		return medium_id;
	}

	public void setMedium_id(String medium_id) {
		this.medium_id = medium_id;
	}

	public String getOver_id() {
		return over_id;
	}

	public void setOver_id(String over_id) {
		this.over_id = over_id;
	}

	public String getOver_type() {
		return over_type;
	}

	public void setOver_type(String over_type) {
		this.over_type = over_type;
	}

	public ExeOverdueId() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entpr_id == null) ? 0 : entpr_id.hashCode());
		result = prime * result + ((medium_id == null) ? 0 : medium_id.hashCode());
		result = prime * result + ((over_id == null) ? 0 : over_id.hashCode());
		result = prime * result + ((over_type == null) ? 0 : over_type.hashCode());
		result = prime * result + ((zbc_flag == null) ? 0 : zbc_flag.hashCode());
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
		final ExeOverdueId other = (ExeOverdueId) obj;
		if (entpr_id == null) {
			if (other.entpr_id != null)
				return false;
		} else if (!entpr_id.equals(other.entpr_id))
			return false;
		if (medium_id == null) {
			if (other.medium_id != null)
				return false;
		} else if (!medium_id.equals(other.medium_id))
			return false;
		if (over_id == null) {
			if (other.over_id != null)
				return false;
		} else if (!over_id.equals(other.over_id))
			return false;
		if (over_type == null) {
			if (other.over_type != null)
				return false;
		} else if (!over_type.equals(other.over_type))
			return false;
		if (zbc_flag == null) {
			if (other.zbc_flag != null)
				return false;
		} else if (!zbc_flag.equals(other.zbc_flag))
			return false;
		return true;
	}

}
