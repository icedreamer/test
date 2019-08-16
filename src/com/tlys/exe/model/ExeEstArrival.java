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
 * 企业前方有AEI车站及预计到达时间中间表Model
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_EST_ARRIVAL")
public class ExeEstArrival implements Serializable {

	private ExeEstArrivalId id;// 联合主键
	private String entpr_id;// 企业代码

	private String train_dir;// 列车方向
	private DicRwstation front_stn_code;// 前方车站(有AEI)，多对一，对应DicRwstation
	private Integer stn_dis;// 站间距
	private Date est_arr_time;// 预计到达时间

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
