package com.tlys.dic.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 企业站区信息表
 * 
 * @author 孔垂云
 * 
 */
@Entity
@Table(name = "TB_ZBC_DIC_ENTPR_STN")
public class DicEntprStn implements Serializable {

	private String entpr_stn_code;// 企业站区代码
	private String entpr_stn_name;// 企业站区汉字名称
	private String entpr_id;// 所属企业代码

	@Id
	public String getEntpr_stn_code() {
		return entpr_stn_code;
	}

	public void setEntpr_stn_code(String entpr_stn_code) {
		this.entpr_stn_code = entpr_stn_code;
	}

	public String getEntpr_stn_name() {
		return entpr_stn_name;
	}

	public void setEntpr_stn_name(String entpr_stn_name) {
		this.entpr_stn_name = entpr_stn_name;
	}

	public String getEntpr_id() {
		return entpr_id;
	}

	public void setEntpr_id(String entpr_id) {
		this.entpr_id = entpr_id;
	}

}
