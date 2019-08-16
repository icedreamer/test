package com.tlys.dic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 
 * create table TB_ZBC_DIC_OVERTIME
 (
 over_id   varchar2(20) not null,
 over_type varchar2(1),
 min_days  number,
 max_days  number,
 over_time varchar2(20)
 )
 ;alter table TB_ZBC_DIC_OVERTIME
 add constraint pk_tb_zbc_dic_overtime primary key (OVER_ID);
 */
/**
 * 超时预警字典
 * 
 * @author kcy
 * 
 */

@Entity
@Table(name = "TB_ZBC_DIC_OVERTIME")
public class DicOverTime {

	private String over_id;
	private String over_type;
	private int min_days;// 最小天
	private int max_days;// 最大天
	private String over_time;// 超时时间段名称
	private String zbc_flag;// 自备车标识

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	@Id
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

	public int getMin_days() {
		return min_days;
	}

	public void setMin_days(int min_days) {
		this.min_days = min_days;
	}

	public int getMax_days() {
		return max_days;
	}

	public void setMax_days(int max_days) {
		this.max_days = max_days;
	}

	public String getOver_time() {
		return over_time;
	}

	public void setOver_time(String over_time) {
		this.over_time = over_time;
	}
}
