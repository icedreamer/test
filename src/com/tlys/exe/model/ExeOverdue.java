package com.tlys.exe.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ³¬Ê±Ô¤¾¯±í create table TB_ZBC_EXE_OVERDUE ( ENTPR_ID VARCHAR2(8) not null,
 * MEDIUM_ID VARCHAR2(8) not null, OVER_ID VARCHAR2(8) not null, OVER_CAR_NUM
 * NUMBER, OVER_TYPE VARCHAR2(1) not null ); alter table TB_ZBC_EXE_OVERDUE add
 * constraint PK_TB_ZBC_EXE_OVERDUE primary key (ENTPR_ID, MEDIUM_ID, OVER_ID,
 * OVER_TYPE);
 * 
 * @author ¿×´¹ÔÆ
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_OVERDUE")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ExeOverdue implements Serializable {

	private ExeOverdueId id;//
	private Integer over_car_num;// ³¬Ê±Á¾Êý

	@EmbeddedId
	public ExeOverdueId getId() {
		return id;
	}

	public void setId(ExeOverdueId id) {
		this.id = id;
	}

	public Integer getOver_car_num() {
		return over_car_num;
	}

	public void setOver_car_num(Integer over_car_num) {
		this.over_car_num = over_car_num;
	}
}
