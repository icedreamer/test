package com.tlys.exe.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 区域分布Model
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_AREADIST")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ExeAreaDist implements Serializable {

	private ExeAreaDistId id;
	private Integer zbc_car_num;// 自备车数量

	@EmbeddedId
	public ExeAreaDistId getId() {
		return id;
	}

	public void setId(ExeAreaDistId id) {
		this.id = id;
	}

	@Column(name = "ZBC_CAR_NUM", length = 5)
	public Integer getZbc_car_num() {
		return zbc_car_num;
	}

	public void setZbc_car_num(Integer zbc_car_num) {
		this.zbc_car_num = zbc_car_num;
	}

}
