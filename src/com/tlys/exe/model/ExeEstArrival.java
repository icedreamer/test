package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tlys.dic.model.DicRwstation;

/**
 * ��ҵǰ����AEI��վ��Ԥ�Ƶ���ʱ���м��Model
 * 
 * @author �״���
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_EST_ARRIVAL")
public class ExeEstArrival implements Serializable {

	private ExeEstArrivalId id;// ��������
	private String entpr_id;// ��ҵ����

	private String train_dir;// �г�����
	private DicRwstation front_stn_code;// ǰ����վ(��AEI)�����һ����ӦDicRwstation
	private Integer stn_dis;// վ���
	private Date est_arr_time;// Ԥ�Ƶ���ʱ��

	public Integer getStn_dis() {
		return stn_dis;
	}

	public void setStn_dis(Integer stn_dis) {
		this.stn_dis = stn_dis;
	}

	@EmbeddedId
	public ExeEstArrivalId getId() {
		return id;
	}

	public void setId(ExeEstArrivalId id) {
		this.id = id;
	}

	public String getEntpr_id() {
		return entpr_id;
	}

	public void setEntpr_id(String entpr_id) {
		this.entpr_id = entpr_id;
	}

	public String getTrain_dir() {
		return train_dir;
	}

	public void setTrain_dir(String train_dir) {
		this.train_dir = train_dir;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "front_stn_code", referencedColumnName = "telegramid")
	public DicRwstation getFront_stn_code() {
		return front_stn_code;
	}

	public void setFront_stn_code(DicRwstation front_stn_code) {
		this.front_stn_code = front_stn_code;
	}

	public Date getEst_arr_time() {
		return est_arr_time;
	}

	public void setEst_arr_time(Date est_arr_time) {
		this.est_arr_time = est_arr_time;
	}
}
