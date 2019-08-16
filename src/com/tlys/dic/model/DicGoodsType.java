package com.tlys.dic.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ��·����Ʒ���ֵ��
 * 
 * @author �״���
 * 
 */
@Entity
@Table(name = "TB_ZBC_DIC_GOODSTYPE")
public class DicGoodsType implements Serializable {

	@Id
	@Column(name = "DM", unique = true, nullable = false, length = 2)
	private String dm;// Ʒ�����

	@Column(name = "JC", length = 4)
	private String jc;// ���

	@Column(name = "QC", length = 20)
	private String qc;// ȫ��

	@Column(name = "BXH", length = 2)
	private String bxh;// ���

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public String getQc() {
		return qc;
	}

	public void setQc(String qc) {
		this.qc = qc;
	}

	public String getBxh() {
		return bxh;
	}

	public void setBxh(String bxh) {
		this.bxh = bxh;
	}

}
