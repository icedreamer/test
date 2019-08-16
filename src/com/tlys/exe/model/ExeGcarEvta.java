package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 车辆轨迹Model
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_GCAREVTA")
public class ExeGcarEvta implements Serializable {

	private ExeGcarEvtaId id;

	@Column(name = "ZBC_FLAG", length = 1)
	private String zbc_flag;

	@Column(name = "CAR_KIND", length = 2)
	private String car_kind;

	@Column(name = "CAR_TYPE", length = 10)
	private String car_type;

	@Column(name = "CAR_OIL_TYPE", length = 1)
	private String car_oil_type;

	@Column(name = "CAR_LIGHT_WGT", precision = 5, scale = 1)
	private Double car_light_wgt;

	@Column(name = "CAR_CAP_WGT", precision = 5, scale = 1)
	private Double car_cap_wgt;

	@Column(name = "CAR_MARK_WGT", precision = 5, scale = 1)
	private Double car_mark_wgt;

	@Column(name = "CAR_LENGTH", precision = 3, scale = 1)
	private Double car_length;

	@Column(name = "MSG_ID", length = 1)
	private String msg_id;

	@Column(name = "RPT_ID", length = 4)
	private String rpt_id;

	@Column(name = "RPT_NAME", length = 13)
	private String rpt_name;

	@Column(name = "RPT_STN_CODE", length = 3)
	private String rpt_stn_code;

	@Column(name = "RPT_STN_NAME", length = 14)
	private String rpt_stn_name;

	@Column(name = "IN_DB_TIME", length = 7)
	private Date in_db_time;

	@Column(name = "RPT_TIME", length = 7)
	private Date rpt_time;

	@Column(name = "CUR_STN_CODE", length = 3)
	private String cur_stn_code;

	@Column(name = "CUR_STN_CHAR", length = 8)
	private String cur_stn_char;

	@Column(name = "CUR_STN_NAME", length = 14)
	private String cur_stn_name;

	@Column(name = "CUR_ADM", length = 2)
	private String cur_adm;

	@Column(name = "CAR_ON_TRAIN", length = 1)
	private String car_on_train;

	@Column(name = "IN_OUT_FLAG", length = 1)
	private String in_out_flag;

	@Column(name = "CAR_ORDER", precision = 3, scale = 0)
	private Long car_order;

	@Column(name = "TRAIN_NO", length = 15)
	private String train_no;

	@Column(name = "TRAIN_DIR", length = 1)
	private String train_dir;

	@Column(name = "BNDRY_FLAG", length = 1)
	private String bndry_flag;

	@Column(name = "AEI_NO", length = 2)
	private String aei_no;

	// @Column(name = "VIR_STN_CODE", length = 3)
	// private String vir_stn_code;

	@Column(name = "LE_CODE", length = 1)
	private String le_code;

	@Column(name = "CDY_ORG_STN", length = 3)
	private String cdy_org_stn;

	@Column(name = "CDY_O_STN_NAME", length = 14)
	private String cdy_o_stn_name;

	@Column(name = "ORG_ADM", length = 2)
	private String org_adm;

	@Column(name = "WB_ID", length = 20)
	private String wb_id;

	@Column(name = "WB_NBR", length = 8)
	private String wb_nbr;

	@Temporal(TemporalType.DATE)
	@Column(name = "WB_DATE", length = 7)
	private Date wb_date;

	@Column(name = "RECORD_ID", length = 20)
	private String record_id;

	@Column(name = "CDY_CODE", length = 7)
	private String cdy_code;

	@Column(name = "CDY_TYPE", length = 2)
	private String cdy_type;

	@Column(name = "GOODS_TYPE", length = 1)
	private String goods_type;

	@Column(name = "CDY_NAME", length = 22)
	private String cdy_name;

	@Column(name = "DEST_STN", length = 3)
	private String dest_stn;

	@Column(name = "DEST_STN_NAME", length = 14)
	private String dest_stn_name;

	@Column(name = "DEST_ADM", length = 2)
	private String dest_adm;

	@Column(name = "CON_NAME", length = 75)
	private String con_name;

	@Column(name = "CON_ENTPR_ID", length = 8)
	private String con_entpr_id;

	@Column(name = "CON_ENTPR_NAME", length = 75)
	private String con_entpr_name;

	@Column(name = "SHIPPER_NAME", length = 75)
	private String shipper_name;

	@Column(name = "SHIPPER_ENTPR_ID", length = 8)
	private String shipper_entpr_id;

	@Column(name = "SHIPPER_ENTPR_NAME", length = 75)
	private String shipper_entpr_name;

	@Column(name = "CAR_RENT_FLAG", length = 1)
	private String car_rent_flag;

	@Column(name = "CAR_OWNER_NAME", length = 40)
	private String car_owner_name;

	@Column(name = "CAR_OWNER_ID", length = 8)
	private String car_owner_id;

	@Column(name = "CAR_OWNER_FLAG", length = 1)
	private String car_owner_flag;

	@Column(name = "CAR_USER_NAME", length = 40)
	private String car_user_name;

	@Column(name = "CAR_USER_ID", length = 8)
	private String car_user_id;

	@Column(name = "CAR_USER_FLAG", length = 1)
	private String car_user_flag;

	@Column(name = "CAR_FILL_MEDIUM", length = 30)
	private String car_fill_medium;

	@Column(name = "CAR_MEDIUM_ID", length = 8)
	private String car_medium_id;

	@Column(name = "MSG_TYPE", length = 1)
	private String msg_type;

	@Column(name = "A_D_FLAG", length = 1)
	private String a_d_flag;

	private Date a_d_time;

	@Column(name = "A_D_STN", length = 3)
	private String a_d_stn;

	@Column(name = "A_D_STN_NAME", length = 14)
	private String a_d_stn_name;

	@Column(name = "A_D_ADM", length = 2)
	private String a_d_adm;

	@Column(name = "IN_BND_STN", length = 3)
	private String in_bnd_stn;

	@Column(name = "IN_BND_NAME", length = 14)
	private String in_bnd_name;

	@Column(name = "IN_ADM_TIME")
	private Date in_adm_time;

	@Column(name = "IN_ADM_TRAIN_NO", length = 15)
	private String in_adm_train_no;

	@Column(name = "OUT_BND_STN", length = 3)
	private String out_bnd_stn;

	@Column(name = "OUT_BND_NAME", length = 14)
	private String out_bnd_name;

	@Column(name = "OUT_ADM_TIME")
	private Date out_adm_time;

	@Column(name = "OUT_ADM_TRAIN_NO", length = 15)
	private String out_adm_train_no;

	@Column(name = "CON_AREA_ID", length = 8)
	private String con_area_id;

	@Column(name = "SHIPPER_AREA_ID", length = 8)
	private String shipper_area_id;

	@Column(name = "OWNER_AREA_ID", length = 8)
	private String owner_area_id;

	@Column(name = "USER_AREA_ID", length = 8)
	private String user_area_id;

	private String evt_date_str;// 事件事件，索引字段

	public String getEvt_date_str() {
		return evt_date_str;
	}

	public void setEvt_date_str(String evt_date_str) {
		this.evt_date_str = evt_date_str;
	}

	public ExeGcarEvta() {
		super();
	}

	public Date getOut_adm_time() {
		return out_adm_time;
	}

	public void setOut_adm_time(Date out_adm_time) {
		this.out_adm_time = out_adm_time;
	}

	public String getCon_area_id() {
		return con_area_id;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "car_no", column = @Column(name = "CAR_NO", nullable = false, length = 8)),
			@AttributeOverride(name = "evt_time", column = @Column(name = "EVT_TIME", nullable = false, length = 7)) })
	public ExeGcarEvtaId getId() {
		return id;
	}

	public void setId(ExeGcarEvtaId id) {
		this.id = id;
	}

	public String getZbc_flag() {
		return zbc_flag;
	}

	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
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

	public String getCar_oil_type() {
		return car_oil_type;
	}

	public void setCar_oil_type(String car_oil_type) {
		this.car_oil_type = car_oil_type;
	}

	public Double getCar_light_wgt() {
		return car_light_wgt;
	}

	public void setCar_light_wgt(Double car_light_wgt) {
		this.car_light_wgt = car_light_wgt;
	}

	public Double getCar_cap_wgt() {
		return car_cap_wgt;
	}

	public void setCar_cap_wgt(Double car_cap_wgt) {
		this.car_cap_wgt = car_cap_wgt;
	}

	public Double getCar_mark_wgt() {
		return car_mark_wgt;
	}

	public void setCar_mark_wgt(Double car_mark_wgt) {
		this.car_mark_wgt = car_mark_wgt;
	}

	public Double getCar_length() {
		return car_length;
	}

	public void setCar_length(Double car_length) {
		this.car_length = car_length;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	public String getRpt_id() {
		return rpt_id;
	}

	public void setRpt_id(String rpt_id) {
		this.rpt_id = rpt_id;
	}

	public String getRpt_name() {
		return rpt_name;
	}

	public void setRpt_name(String rpt_name) {
		this.rpt_name = rpt_name;
	}

	public String getRpt_stn_code() {
		return rpt_stn_code;
	}

	public void setRpt_stn_code(String rpt_stn_code) {
		this.rpt_stn_code = rpt_stn_code;
	}

	public String getRpt_stn_name() {
		return rpt_stn_name;
	}

	public void setRpt_stn_name(String rpt_stn_name) {
		this.rpt_stn_name = rpt_stn_name;
	}

	public Date getIn_db_time() {
		return in_db_time;
	}

	public void setIn_db_time(Date in_db_time) {
		this.in_db_time = in_db_time;
	}

	public Date getRpt_time() {
		return rpt_time;
	}

	public void setRpt_time(Date rpt_time) {
		this.rpt_time = rpt_time;
	}

	public String getCur_stn_code() {
		return cur_stn_code;
	}

	public void setCur_stn_code(String cur_stn_code) {
		this.cur_stn_code = cur_stn_code;
	}

	public String getCur_stn_char() {
		return cur_stn_char;
	}

	public void setCur_stn_char(String cur_stn_char) {
		this.cur_stn_char = cur_stn_char;
	}

	public String getCur_stn_name() {
		return cur_stn_name;
	}

	public void setCur_stn_name(String cur_stn_name) {
		this.cur_stn_name = cur_stn_name;
	}

	public String getCur_adm() {
		return cur_adm;
	}

	public void setCur_adm(String cur_adm) {
		this.cur_adm = cur_adm;
	}

	public String getCar_on_train() {
		return car_on_train;
	}

	public void setCar_on_train(String car_on_train) {
		this.car_on_train = car_on_train;
	}

	public String getIn_out_flag() {
		return in_out_flag;
	}

	public void setIn_out_flag(String in_out_flag) {
		this.in_out_flag = in_out_flag;
	}

	public Long getCar_order() {
		return car_order;
	}

	public void setCar_order(Long car_order) {
		this.car_order = car_order;
	}

	public String getTrain_no() {
		return train_no;
	}

	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}

	public String getTrain_dir() {
		return train_dir;
	}

	public void setTrain_dir(String train_dir) {
		this.train_dir = train_dir;
	}

	public String getBndry_flag() {
		return bndry_flag;
	}

	public void setBndry_flag(String bndry_flag) {
		this.bndry_flag = bndry_flag;
	}

	public String getAei_no() {
		return aei_no;
	}

	public void setAei_no(String aei_no) {
		this.aei_no = aei_no;
	}

	public String getLe_code() {
		return le_code;
	}

	public void setLe_code(String le_code) {
		this.le_code = le_code;
	}

	public String getCdy_org_stn() {
		return cdy_org_stn;
	}

	public void setCdy_org_stn(String cdy_org_stn) {
		this.cdy_org_stn = cdy_org_stn;
	}

	public String getCdy_o_stn_name() {
		return cdy_o_stn_name;
	}

	public void setCdy_o_stn_name(String cdy_o_stn_name) {
		this.cdy_o_stn_name = cdy_o_stn_name;
	}

	public String getOrg_adm() {
		return org_adm;
	}

	public void setOrg_adm(String org_adm) {
		this.org_adm = org_adm;
	}

	public String getWb_id() {
		return wb_id;
	}

	public void setWb_id(String wb_id) {
		this.wb_id = wb_id;
	}

	public String getWb_nbr() {
		return wb_nbr;
	}

	public void setWb_nbr(String wb_nbr) {
		this.wb_nbr = wb_nbr;
	}

	public Date getWb_date() {
		return wb_date;
	}

	public void setWb_date(Date wb_date) {
		this.wb_date = wb_date;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getCdy_code() {
		return cdy_code;
	}

	public void setCdy_code(String cdy_code) {
		this.cdy_code = cdy_code;
	}

	public String getCdy_type() {
		return cdy_type;
	}

	public void setCdy_type(String cdy_type) {
		this.cdy_type = cdy_type;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getCdy_name() {
		return cdy_name;
	}

	public void setCdy_name(String cdy_name) {
		this.cdy_name = cdy_name;
	}

	public String getDest_stn() {
		return dest_stn;
	}

	public void setDest_stn(String dest_stn) {
		this.dest_stn = dest_stn;
	}

	public String getDest_stn_name() {
		return dest_stn_name;
	}

	public void setDest_stn_name(String dest_stn_name) {
		this.dest_stn_name = dest_stn_name;
	}

	public String getDest_adm() {
		return dest_adm;
	}

	public void setDest_adm(String dest_adm) {
		this.dest_adm = dest_adm;
	}

	public String getCon_name() {
		return con_name;
	}

	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

	public String getCon_entpr_id() {
		return con_entpr_id;
	}

	public void setCon_entpr_id(String con_entpr_id) {
		this.con_entpr_id = con_entpr_id;
	}

	public String getCon_entpr_name() {
		return con_entpr_name;
	}

	public void setCon_entpr_name(String con_entpr_name) {
		this.con_entpr_name = con_entpr_name;
	}

	public String getShipper_name() {
		return shipper_name;
	}

	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}

	public String getShipper_entpr_id() {
		return shipper_entpr_id;
	}

	public void setShipper_entpr_id(String shipper_entpr_id) {
		this.shipper_entpr_id = shipper_entpr_id;
	}

	public String getShipper_entpr_name() {
		return shipper_entpr_name;
	}

	public void setShipper_entpr_name(String shipper_entpr_name) {
		this.shipper_entpr_name = shipper_entpr_name;
	}

	public String getCar_rent_flag() {
		return car_rent_flag;
	}

	public void setCar_rent_flag(String car_rent_flag) {
		this.car_rent_flag = car_rent_flag;
	}

	public String getCar_owner_name() {
		return car_owner_name;
	}

	public void setCar_owner_name(String car_owner_name) {
		this.car_owner_name = car_owner_name;
	}

	public String getCar_owner_id() {
		return car_owner_id;
	}

	public void setCar_owner_id(String car_owner_id) {
		this.car_owner_id = car_owner_id;
	}

	public String getCar_owner_flag() {
		return car_owner_flag;
	}

	public void setCar_owner_flag(String car_owner_flag) {
		this.car_owner_flag = car_owner_flag;
	}

	public String getCar_user_name() {
		return car_user_name;
	}

	public void setCar_user_name(String car_user_name) {
		this.car_user_name = car_user_name;
	}

	public String getCar_user_id() {
		return car_user_id;
	}

	public void setCar_user_id(String car_user_id) {
		this.car_user_id = car_user_id;
	}

	public String getCar_user_flag() {
		return car_user_flag;
	}

	public void setCar_user_flag(String car_user_flag) {
		this.car_user_flag = car_user_flag;
	}

	public String getCar_fill_medium() {
		return car_fill_medium;
	}

	public void setCar_fill_medium(String car_fill_medium) {
		this.car_fill_medium = car_fill_medium;
	}

	public String getCar_medium_id() {
		return car_medium_id;
	}

	public void setCar_medium_id(String car_medium_id) {
		this.car_medium_id = car_medium_id;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public String getA_d_flag() {
		return a_d_flag;
	}

	public void setA_d_flag(String a_d_flag) {
		this.a_d_flag = a_d_flag;
	}

	@Column(name = "A_D_TIME", length = 22)
	public Date getA_d_time() {
		return a_d_time;
	}

	public void setA_d_time(Date a_d_time) {
		this.a_d_time = a_d_time;
	}

	public String getA_d_stn() {
		return a_d_stn;
	}

	public void setA_d_stn(String a_d_stn) {
		this.a_d_stn = a_d_stn;
	}

	public String getA_d_stn_name() {
		return a_d_stn_name;
	}

	public void setA_d_stn_name(String a_d_stn_name) {
		this.a_d_stn_name = a_d_stn_name;
	}

	public String getA_d_adm() {
		return a_d_adm;
	}

	public void setA_d_adm(String a_d_adm) {
		this.a_d_adm = a_d_adm;
	}

	public String getIn_bnd_stn() {
		return in_bnd_stn;
	}

	public void setIn_bnd_stn(String in_bnd_stn) {
		this.in_bnd_stn = in_bnd_stn;
	}

	public String getIn_bnd_name() {
		return in_bnd_name;
	}

	public void setIn_bnd_name(String in_bnd_name) {
		this.in_bnd_name = in_bnd_name;
	}

	public Date getIn_adm_time() {
		return in_adm_time;
	}

	public void setIn_adm_time(Date in_adm_time) {
		this.in_adm_time = in_adm_time;
	}

	public String getIn_adm_train_no() {
		return in_adm_train_no;
	}

	public void setIn_adm_train_no(String in_adm_train_no) {
		this.in_adm_train_no = in_adm_train_no;
	}

	public String getOut_bnd_stn() {
		return out_bnd_stn;
	}

	public void setOut_bnd_stn(String out_bnd_stn) {
		this.out_bnd_stn = out_bnd_stn;
	}

	public String getOut_bnd_name() {
		return out_bnd_name;
	}

	public void setOut_bnd_name(String out_bnd_name) {
		this.out_bnd_name = out_bnd_name;
	}

	public String getOut_adm_train_no() {
		return out_adm_train_no;
	}

	public void setOut_adm_train_no(String out_adm_train_no) {
		this.out_adm_train_no = out_adm_train_no;
	}

	public void setCon_area_id(String con_area_id) {
		this.con_area_id = con_area_id;
	}

	public String getShipper_area_id() {
		return shipper_area_id;
	}

	public void setShipper_area_id(String shipper_area_id) {
		this.shipper_area_id = shipper_area_id;
	}

	public String getOwner_area_id() {
		return owner_area_id;
	}

	public void setOwner_area_id(String owner_area_id) {
		this.owner_area_id = owner_area_id;
	}

	public String getUser_area_id() {
		return user_area_id;
	}

	public void setUser_area_id(String user_area_id) {
		this.user_area_id = user_area_id;
	}

	public ExeGcarEvta(ExeGcarEvtaId id, String zbc_flag, String car_kind, String car_type, String car_oil_type,
			Double car_light_wgt, Double car_cap_wgt, Double car_mark_wgt, Double car_length, String msg_id,
			String rpt_id, String rpt_name, String rpt_stn_code, String rpt_stn_name, Date in_db_time, Date rpt_time,
			String cur_stn_code, String cur_stn_char, String cur_stn_name, String cur_adm, String car_on_train,
			String in_out_flag, Long car_order, String train_no, String train_dir, String bndry_flag, String aei_no,
			String le_code, String cdy_org_stn, String cdy_o_stn_name, String org_adm, String wb_id, String wb_nbr,
			Date wb_date, String record_id, String cdy_code, String cdy_type, String goods_type, String cdy_name,
			String dest_stn, String dest_stn_name, String dest_adm, String con_name, String con_entpr_id,
			String con_entpr_name, String shipper_name, String shipper_entpr_id, String shipper_entpr_name,
			String car_rent_flag, String car_owner_name, String car_owner_id, String car_owner_flag,
			String car_user_name, String car_user_id, String car_user_flag, String car_fill_medium,
			String car_medium_id, String msg_type, String a_d_flag, Date a_d_time, String a_d_stn, String a_d_stn_name,
			String a_d_adm, String in_bnd_stn, String in_bnd_name, Date in_adm_time, String in_adm_train_no,
			String out_bnd_stn, String out_bnd_name, Date out_adm_time, String out_adm_train_no, String con_area_id,
			String shipper_area_id, String owner_area_id, String user_area_id) {
		super();
		this.id = id;
		this.zbc_flag = zbc_flag;
		this.car_kind = car_kind;
		this.car_type = car_type;
		this.car_oil_type = car_oil_type;
		this.car_light_wgt = car_light_wgt;
		this.car_cap_wgt = car_cap_wgt;
		this.car_mark_wgt = car_mark_wgt;
		this.car_length = car_length;
		this.msg_id = msg_id;
		this.rpt_id = rpt_id;
		this.rpt_name = rpt_name;
		this.rpt_stn_code = rpt_stn_code;
		this.rpt_stn_name = rpt_stn_name;
		this.in_db_time = in_db_time;
		this.rpt_time = rpt_time;
		this.cur_stn_code = cur_stn_code;
		this.cur_stn_char = cur_stn_char;
		this.cur_stn_name = cur_stn_name;
		this.cur_adm = cur_adm;
		this.car_on_train = car_on_train;
		this.in_out_flag = in_out_flag;
		this.car_order = car_order;
		this.train_no = train_no;
		this.train_dir = train_dir;
		this.bndry_flag = bndry_flag;
		this.aei_no = aei_no;
		this.le_code = le_code;
		this.cdy_org_stn = cdy_org_stn;
		this.cdy_o_stn_name = cdy_o_stn_name;
		this.org_adm = org_adm;
		this.wb_id = wb_id;
		this.wb_nbr = wb_nbr;
		this.wb_date = wb_date;
		this.record_id = record_id;
		this.cdy_code = cdy_code;
		this.cdy_type = cdy_type;
		this.goods_type = goods_type;
		this.cdy_name = cdy_name;
		this.dest_stn = dest_stn;
		this.dest_stn_name = dest_stn_name;
		this.dest_adm = dest_adm;
		this.con_name = con_name;
		this.con_entpr_id = con_entpr_id;
		this.con_entpr_name = con_entpr_name;
		this.shipper_name = shipper_name;
		this.shipper_entpr_id = shipper_entpr_id;
		this.shipper_entpr_name = shipper_entpr_name;
		this.car_rent_flag = car_rent_flag;
		this.car_owner_name = car_owner_name;
		this.car_owner_id = car_owner_id;
		this.car_owner_flag = car_owner_flag;
		this.car_user_name = car_user_name;
		this.car_user_id = car_user_id;
		this.car_user_flag = car_user_flag;
		this.car_fill_medium = car_fill_medium;
		this.car_medium_id = car_medium_id;
		this.msg_type = msg_type;
		this.a_d_flag = a_d_flag;
		this.a_d_time = a_d_time;
		this.a_d_stn = a_d_stn;
		this.a_d_stn_name = a_d_stn_name;
		this.a_d_adm = a_d_adm;
		this.in_bnd_stn = in_bnd_stn;
		this.in_bnd_name = in_bnd_name;
		this.in_adm_time = in_adm_time;
		this.in_adm_train_no = in_adm_train_no;
		this.out_bnd_stn = out_bnd_stn;
		this.out_bnd_name = out_bnd_name;
		this.out_adm_time = out_adm_time;
		this.out_adm_train_no = out_adm_train_no;
		this.con_area_id = con_area_id;
		this.shipper_area_id = shipper_area_id;
		this.owner_area_id = owner_area_id;
		this.user_area_id = user_area_id;
	}
}
