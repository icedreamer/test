package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tlys.dic.model.DicSinocorp;

/**
 * 车站（企业站区)列车出入信息表
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_STNTRN")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ExeStntrn implements Serializable {
	
	private String rptname;// 报文名
	private String train_nbr;// 列车车次
	private String stn_name;// 报告站
	private DicSinocorp stn_entpr;// 报告站所属企业ID
	private String stn_type;// 站属性
	private String part_id;// 分区ID
	private String train_dir;// 列车方向
	private String in_out_flag;// 进出企业站区标识
	private Date arr_time;// 到达时间
	private Date rpt_time;// 报告时间
	private Integer rpt_car_number;// 报文总车辆数
	private Integer car_number;// 可识别车辆数
	private Integer t_number;// 其中部属车辆数
	private Integer q_number;// 企业自备车辆数
	private Integer uncar_number;// 不可识别货车数
	private Integer aei_no;// AEI编号
	private Integer antenna_no;// 天线编号
	private Date in_db_Time;// 报文入库时间
	private String aei_flag;// AEI主备标志
	private String speed_flag;// 低速标志
	private String fact_flag;// 厂家编号
	private String ver_no;// 版本号
	private String msg_flag;// 信息来源标识
	private String msg_status;// 信息状态标识

	@Column(name = "IN_DB_TIME", columnDefinition = "default sysdate")
	public Date getIn_db_Time() {
		return in_db_Time;
	}

	public void setIn_db_Time(Date in_db_Time) {
		this.in_db_Time = in_db_Time;
	}

	// 站区企业，多对一
	@ManyToOne
	@JoinColumn(name = "stn_entpr_id")
	public DicSinocorp getStn_entpr() {
		return stn_entpr;
	}

	public void setStn_entpr(DicSinocorp stn_entpr) {
		this.stn_entpr = stn_entpr;
	}

	public String getTrain_nbr() {
		return train_nbr;
	}

	public void setTrain_nbr(String train_nbr) {
		this.train_nbr = train_nbr;
	}

	public String getStn_name() {
		return stn_name;
	}

	public void setStn_name(String stn_name) {
		this.stn_name = stn_name;
	}

	public String getStn_type() {
		return stn_type;
	}

	public void setStn_type(String stn_type) {
		this.stn_type = stn_type;
	}

	public String getPart_id() {
		return part_id;
	}

	public void setPart_id(String part_id) {
		this.part_id = part_id;
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

	public Date getArr_time() {
		return arr_time;
	}

	public void setArr_time(Date arr_time) {
		this.arr_time = arr_time;
	}

	@Column(name = "RPT_TIME", columnDefinition = "default sysdate")
	public Date getRpt_time() {
		return rpt_time;
	}

	public void setRpt_time(Date rpt_time) {
		this.rpt_time = rpt_time;
	}

	public Integer getRpt_car_number() {
		return rpt_car_number;
	}

	public void setRpt_car_number(Integer rpt_car_number) {
		this.rpt_car_number = rpt_car_number;
	}

	public Integer getCar_number() {
		return car_number;
	}

	public void setCar_number(Integer car_number) {
		this.car_number = car_number;
	}

	public Integer getT_number() {
		return t_number;
	}

	public void setT_number(Integer t_number) {
		this.t_number = t_number;
	}

	public Integer getQ_number() {
		return q_number;
	}

	public void setQ_number(Integer q_number) {
		this.q_number = q_number;
	}

	public Integer getUncar_number() {
		return uncar_number;
	}

	public void setUncar_number(Integer uncar_number) {
		this.uncar_number = uncar_number;
	}

	public Integer getAei_no() {
		return aei_no;
	}

	public void setAei_no(Integer aei_no) {
		this.aei_no = aei_no;
	}

	public Integer getAntenna_no() {
		return antenna_no;
	}

	public void setAntenna_no(Integer antenna_no) {
		this.antenna_no = antenna_no;
	}

	@Id
	public String getRptname() {
		return rptname;
	}

	public void setRptname(String rptname) {
		this.rptname = rptname;
	}

	public String getAei_flag() {
		return aei_flag;
	}

	public void setAei_flag(String aei_flag) {
		this.aei_flag = aei_flag;
	}

	public String getSpeed_flag() {
		return speed_flag;
	}

	public void setSpeed_flag(String speed_flag) {
		this.speed_flag = speed_flag;
	}

	public String getFact_flag() {
		return fact_flag;
	}

	public void setFact_flag(String fact_flag) {
		this.fact_flag = fact_flag;
	}

	public String getVer_no() {
		return ver_no;
	}

	public void setVer_no(String ver_no) {
		this.ver_no = ver_no;
	}

	public String getMsg_flag() {
		return msg_flag;
	}

	public void setMsg_flag(String msg_flag) {
		this.msg_flag = msg_flag;
	}

	public String getMsg_status() {
		return msg_status;
	}

	public void setMsg_status(String msg_status) {
		this.msg_status = msg_status;
	}

}
