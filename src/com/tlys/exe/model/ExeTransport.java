package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlys.dic.model.DicGoodsType;

/**
 * 运输信息Model
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_TRANSPORT")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ExeTransport implements Serializable {

	private Long rec_id;

	@Column(name = "CAR_ACTUAL_WGT", precision = 10, scale = 3)
	private Double car_actual_wgt;

	@Column(name = "CAR_CAP_WGT", precision = 5, scale = 1)
	private Double car_cap_wgt;

	@Column(name = "CAR_NO", nullable = false, length = 8)
	private String car_no;

	@Column(name = "CDY_CODE", length = 7)
	private String cdy_code;

	@Column(name = "CDY_NAME", length = 22)
	private String cdy_name;

	@Column(name = "CDY_O_STN_NAME", length = 14)
	private String cdy_o_stn_name;

	@Column(name = "CDY_ORG_STN", length = 3)
	private String cdy_org_stn;

	@Column(name = "CON_ENTPR_ID", length = 8)
	private String con_entpr_id;

	@Column(name = "CON_ENTPR_NAME", length = 40)
	private String con_entpr_name;

	@Column(name = "CON_NAME", length = 60)
	private String con_name;

	@Column(name = "DEST_ADM", length = 2)
	private String dest_adm;

	@Column(name = "DEST_STN", length = 3)
	private String dest_stn;

	@Column(name = "DEST_STN_NAME", length = 14)
	private String dest_stn_name;

	private DicGoodsType dicGoodsType;

	@Column(name = "GOODS_TYPE", length = 1)
	private String goods_type;

	@Column(name = "LE_CODE", length = 1)
	private String le_code;

	@Column(name = "MSG_STATUS", length = 1)
	private String msg_status;

	@Column(name = "MSG_TYPE", length = 1)
	private String msg_type;

	@Column(name = "ORG_ADM", length = 2)
	private String org_adm;

	@Column(name = "SHIPPER_ENTPR_ID", length = 8)
	private String shipper_entpr_id;

	@Column(name = "SHIPPER_ENTPR_NAME", length = 40)
	private String shipper_entpr_name;

	@Column(name = "SHIPPER_NAME", length = 75)
	private String shipper_name;

	@Temporal(TemporalType.DATE)
	@Column(name = "WB_DATE", nullable = false, length = 10)
	private Date wb_date;

	private String wb_id;// 装载清单

	@Column(name = "WB_NBR", length = 8)
	private String wb_nbr;

	@Column(name = "ZBC_FLAG", length = 1)
	private String zbc_flag;

	private String hp_editor;// 记录增改用户ID
	private Date hp_edit_time;// 记录增改时间
	private String cap_wgt_editor;// 计费载重增改用户ID
	private Date capw_edit_time;// 计费载重增改时间
	private String car_user_id;// 使用企业ID

	private String wb_date_str;// 制票事件字符串，索引

	public String getWb_date_str() {
		return wb_date_str;
	}

	public void setWb_date_str(String wb_date_str) {
		this.wb_date_str = wb_date_str;
	}

	public ExeTransport() {
		super();
	}

	public Double getCar_actual_wgt() {
		return car_actual_wgt;
	}

	public Double getCar_cap_wgt() {
		return car_cap_wgt;
	}

	public String getCar_no() {
		return car_no;
	}

	public String getCdy_code() {
		return cdy_code;
	}

	public String getCdy_name() {
		return cdy_name;
	}

	public String getCdy_o_stn_name() {
		return cdy_o_stn_name;
	}

	public String getCdy_org_stn() {
		return cdy_org_stn;
	}

	public String getCon_entpr_id() {
		return con_entpr_id;
	}

	public String getCon_entpr_name() {
		return con_entpr_name;
	}

	public String getCon_name() {
		return con_name;
	}

	public String getDest_adm() {
		return dest_adm;
	}

	public String getDest_stn() {
		return dest_stn;
	}

	public String getDest_stn_name() {
		return dest_stn_name;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "cdy_type")
	public DicGoodsType getDicGoodsType() {
		return dicGoodsType;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public String getLe_code() {
		return le_code;
	}

	public String getMsg_status() {
		return msg_status;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public String getOrg_adm() {
		return org_adm;
	}

	public String getShipper_entpr_id() {
		return shipper_entpr_id;
	}

	public String getShipper_entpr_name() {
		return shipper_entpr_name;
	}

	public String getShipper_name() {
		return shipper_name;
	}

	public Date getWb_date() {
		return wb_date;
	}

	@Column(name = "WB_ID", unique = true, nullable = false, length = 20)
	public String getWb_id() {
		return wb_id;
	}

	public String getWb_nbr() {
		return wb_nbr;
	}

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setCar_actual_wgt(Double car_actual_wgt) {
		this.car_actual_wgt = car_actual_wgt;
	}

	public void setCar_cap_wgt(Double car_cap_wgt) {
		this.car_cap_wgt = car_cap_wgt;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public void setCdy_code(String cdy_code) {
		this.cdy_code = cdy_code;
	}

	public void setCdy_name(String cdy_name) {
		this.cdy_name = cdy_name;
	}

	public void setCdy_o_stn_name(String cdy_o_stn_name) {
		this.cdy_o_stn_name = cdy_o_stn_name;
	}

	public void setCdy_org_stn(String cdy_org_stn) {
		this.cdy_org_stn = cdy_org_stn;
	}

	public void setCon_entpr_id(String con_entpr_id) {
		this.con_entpr_id = con_entpr_id;
	}

	public void setCon_entpr_name(String con_entpr_name) {
		this.con_entpr_name = con_entpr_name;
	}

	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

	public void setDest_adm(String dest_adm) {
		this.dest_adm = dest_adm;
	}

	public void setDest_stn(String dest_stn) {
		this.dest_stn = dest_stn;
	}

	public void setDest_stn_name(String dest_stn_name) {
		this.dest_stn_name = dest_stn_name;
	}

	public void setDicGoodsType(DicGoodsType dicGoodsType) {
		this.dicGoodsType = dicGoodsType;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public void setLe_code(String le_code) {
		this.le_code = le_code;
	}

	public void setMsg_status(String msg_status) {
		this.msg_status = msg_status;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public void setOrg_adm(String org_adm) {
		this.org_adm = org_adm;
	}

	public void setShipper_entpr_id(String shipper_entpr_id) {
		this.shipper_entpr_id = shipper_entpr_id;
	}

	public void setShipper_entpr_name(String shipper_entpr_name) {
		this.shipper_entpr_name = shipper_entpr_name;
	}

	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}

	public void setWb_date(Date wb_date) {
		this.wb_date = wb_date;
	}

	public void setWb_id(String wb_id) {
		this.wb_id = wb_id;
	}

	public void setWb_nbr(String wb_nbr) {
		this.wb_nbr = wb_nbr;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	public String getHp_editor() {
		return hp_editor;
	}

	public void setHp_editor(String hp_editor) {
		this.hp_editor = hp_editor;
	}

	public Date getHp_edit_time() {
		return hp_edit_time;
	}

	public void setHp_edit_time(Date hp_edit_time) {
		this.hp_edit_time = hp_edit_time;
	}

	public String getCap_wgt_editor() {
		return cap_wgt_editor;
	}

	public void setCap_wgt_editor(String cap_wgt_editor) {
		this.cap_wgt_editor = cap_wgt_editor;
	}

	public Date getCapw_edit_time() {
		return capw_edit_time;
	}

	public void setCapw_edit_time(Date capw_edit_time) {
		this.capw_edit_time = capw_edit_time;
	}

	public String getCar_user_id() {
		return car_user_id;
	}

	public void setCar_user_id(String car_user_id) {
		this.car_user_id = car_user_id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tb_zbc_exe_transport")
	@SequenceGenerator(name = "seq_tb_zbc_exe_transport", sequenceName = "seq_tb_zbc_exe_transport", allocationSize = 1)
	public Long getRec_id() {
		return rec_id;
	}

	public void setRec_id(Long rec_id) {
		this.rec_id = rec_id;
	}

}
