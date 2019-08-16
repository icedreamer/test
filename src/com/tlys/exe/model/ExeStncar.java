package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * AEI����Model
 * 
 * @author �״���
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_STNCAR")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ExeStncar implements Serializable {
	private ExeStncarId id;// ����
	private String train_nbr;// �г�����
	private String stn_name;// ����վ
	private Date arr_time;// ����ʱ��
	private String part_id;// ������ʶ
	private String train_dir;// �г�����
	private String car_flag;// ������־
	private String car_kind;// ����,���һ
	private String car_type;// ���ͣ����һ
	private String car_no;// ����
	private Integer car_length;// ����
	private String made_factory;// ���쳧,���һ
	private String made_date;// ��������
	private String uncar_ident;// ����ʶ�𳵱�ʶ
	private String wb_id;// ��Ʊ��װ���嵥)��ʶ
	private String msg_flag;// ��Ϣ��Դ��ʶ
	private String match_flag;// �Ƿ�ƥ��
	private String record_id;// �붯̬�켣������ֶ�
	private ExeTransport transport;// һ��һ��������������Ϣ��

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wb_id ", referencedColumnName = "wb_id")
	public ExeTransport getTransport() {
		return transport;
	}

	public void setTransport(ExeTransport transport) {
		this.transport = transport;
	}

	public String getMatch_flag() {
		return match_flag;
	}

	public void setMatch_flag(String match_flag) {
		this.match_flag = match_flag;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	@Column(columnDefinition = "default sysdate")
	public Date getArr_time() {
		return arr_time;
	}

	public String getCar_flag() {
		return car_flag;
	}

	public Integer getCar_length() {
		return car_length;
	}

	public String getCar_no() {
		return car_no;
	}

	@EmbeddedId
	public ExeStncarId getId() {
		return id;
	}

	public String getMade_date() {
		return made_date;
	}

	public String getMsg_flag() {
		return msg_flag;
	}

	public String getPart_id() {
		return part_id;
	}

	public String getStn_name() {
		return stn_name;
	}

	public String getTrain_dir() {
		return train_dir;
	}

	public String getTrain_nbr() {
		return train_nbr;
	}

	public String getUncar_ident() {
		return uncar_ident;
	}

	public String getWb_id() {
		return wb_id;
	}

	public void setArr_time(Date arr_time) {
		this.arr_time = arr_time;
	}

	public void setCar_flag(String car_flag) {
		this.car_flag = car_flag;
	}

	public void setCar_length(Integer car_length) {
		this.car_length = car_length;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public void setId(ExeStncarId id) {
		this.id = id;
	}

	public String getCar_kind() {
		return car_kind;
	}

	public void setCar_kind(String car_kind) {
		this.car_kind = car_kind;
	}

	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public void setMade_date(String made_date) {
		this.made_date = made_date;
	}

	public String getMade_factory() {
		return made_factory;
	}

	public void setMade_factory(String made_factory) {
		this.made_factory = made_factory;
	}

	public void setMsg_flag(String msg_flag) {
		this.msg_flag = msg_flag;
	}

	public void setPart_id(String part_id) {
		this.part_id = part_id;
	}

	public void setStn_name(String stn_name) {
		this.stn_name = stn_name;
	}

	public void setTrain_dir(String train_dir) {
		this.train_dir = train_dir;
	}

	public void setTrain_nbr(String train_nbr) {
		this.train_nbr = train_nbr;
	}

	public void setUncar_ident(String uncar_ident) {
		this.uncar_ident = uncar_ident;
	}

	public void setWb_id(String wb_id) {
		this.wb_id = wb_id;
	}
}
