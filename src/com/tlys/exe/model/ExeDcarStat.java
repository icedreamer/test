package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tlys.dic.model.DicGoods;

/**
 * 动态表Model
 * 
 * @author 孔垂云
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_DCARSTAT")
public class ExeDcarStat implements Serializable {

	private String car_no;

	public String getEstarr_flag() {
		return estarr_flag;
	}

	public void setEstarr_flag(String estarr_flag) {
		this.estarr_flag = estarr_flag;
	}

	public ExeDcarStat() {
		super();
	}

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

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_TIME", length = 7)
	private Date update_time;

	@Temporal(TemporalType.DATE)
	@Column(name = "RPT_TIME", length = 7)
	private Date rpt_time;

	@Temporal(TemporalType.DATE)
	@Column(name = "EVT_TIME", length = 7)
	private Date evt_time;

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

	@Column(name = "A_D_FLAG", length = 1)
	private String a_d_flag;

	@Temporal(TemporalType.DATE)
	@Column(name = "A_D_TIME", length = 7)
	private Date a_d_time;

	@Column(name = "A_D_STN", length = 3)
	private String a_d_stn;

	@Column(name = "A_D_STN_NAME", length = 14)
	private String a_d_stn_name;

	@Column(name = "A_D_ADM", length = 2)
	private String a_d_adm;

	// @Column(name = "A_D_SADM", length = 3)
	// private String a_d_sadm;

	@Column(name = "IN_BND_STN", length = 3)
	private String in_bnd_stn;

	@Column(name = "IN_BND_NAME", length = 14)
	private String in_bnd_name;

	@Temporal(TemporalType.DATE)
	@Column(name = "IN_ADM_TIME", length = 7)
	private Date in_adm_time;

	@Column(name = "IN_ADM_TRAIN_NO", length = 15)
	private String in_adm_train_no;

	@Column(name = "OUT_BND_STN", length = 3)
	private String out_bnd_stn;

	@Column(name = "OUT_BND_NAME", length = 14)
	private String out_bnd_name;

	@Temporal(TemporalType.DATE)
	@Column(name = "OUT_ADM_TIME", length = 7)
	private Date out_adm_time;

	@Column(name = "OUT_ADM_TRAIN_NO", length = 15)
	private String out_adm_train_no;

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

	// @Column(name = "CDY_CODE", length = 7)
	// private String cdy_code;

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

	// @Column(name = "LOAD_ID", length = 23)
	// private String load_id;

	@Column(name = "MSG_TYPE", length = 1)
	private String msg_type;

	@Column(name = "CON_AREA_ID", length = 8)
	private String con_area_id;

	@Column(name = "SHIPPER_AREA_ID", length = 8)
	private String shipper_area_id;

	@Column(name = "OWNER_AREA_ID", length = 8)
	private String owner_area_id;

	@Column(name = "USER_AREA_ID", length = 8)
	private String user_area_id;

	private DicGoods dicGoods;// 模块
	private String estarr_flag;// 有预计到达信息

	public Double getCar_cap_wgt() {
		return car_cap_wgt;
	}

	public void setCar_cap_wgt(Double car_cap_wgt) {
		this.car_cap_wgt = car_cap_wgt;
	}

	// 多对一,品名
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cdy_code")
	public DicGoods getDicGoods() {
		return dicGoods;
	}

	public void setDicGoods(DicGoods dicGoods) {
		this.dicGoods = dicGoods;
	}

	public Date getOut_adm_time() {
		return out_adm_time;
	}

	public void setOut_adm_time(Date out_adm_time) {
		this.out_adm_time = out_adm_time;
	}

	@Id
	@Column(name = "CAR_NO", unique = true, nullable = false, length = 8)
	public String getCar_no() {
		return car_no;
	}

	/**
	 * @param car_no
	 *            the car_no to set
	 */
	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	/**
	 * @return the zbc_flag
	 */
	public String getZbc_flag() {
		return zbc_flag;
	}

	/**
	 * @param zbc_flag
	 *            the zbc_flag to set
	 */
	public void setZbc_flag(String zbc_flag) {
		this.zbc_flag = zbc_flag;
	}

	/**
	 * @return the car_kind
	 */
	public String getCar_kind() {
		return car_kind;
	}

	/**
	 * @param car_kind
	 *            the car_kind to set
	 */
	public void setCar_kind(String car_kind) {
		this.car_kind = car_kind;
	}

	/**
	 * @return the car_type
	 */
	public String getCar_type() {
		return car_type;
	}

	/**
	 * @param car_type
	 *            the car_type to set
	 */
	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	/**
	 * @return the car_oil_type
	 */
	public String getCar_oil_type() {
		return car_oil_type;
	}

	/**
	 * @param car_oil_type
	 *            the car_oil_type to set
	 */
	public void setCar_oil_type(String car_oil_type) {
		this.car_oil_type = car_oil_type;
	}

	/**
	 * @return the car_mark_wgt
	 */
	public Double getCar_mark_wgt() {
		return car_mark_wgt;
	}

	/**
	 * @param car_mark_wgt
	 *            the car_mark_wgt to set
	 */
	public void setCar_mark_wgt(Double car_mark_wgt) {
		this.car_mark_wgt = car_mark_wgt;
	}

	/**
	 * @return the car_length
	 */
	public Double getCar_length() {
		return car_length;
	}

	/**
	 * @param car_length
	 *            the car_length to set
	 */
	public void setCar_length(Double car_length) {
		this.car_length = car_length;
	}

	/**
	 * @return the msg_id
	 */
	public String getMsg_id() {
		return msg_id;
	}

	/**
	 * @param msg_id
	 *            the msg_id to set
	 */
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}

	/**
	 * @return the rpt_id
	 */
	public String getRpt_id() {
		return rpt_id;
	}

	/**
	 * @param rpt_id
	 *            the rpt_id to set
	 */
	public void setRpt_id(String rpt_id) {
		this.rpt_id = rpt_id;
	}

	/**
	 * @return the rpt_name
	 */
	public String getRpt_name() {
		return rpt_name;
	}

	/**
	 * @param rpt_name
	 *            the rpt_name to set
	 */
	public void setRpt_name(String rpt_name) {
		this.rpt_name = rpt_name;
	}

	/**
	 * @return the rpt_stn_code
	 */
	public String getRpt_stn_code() {
		return rpt_stn_code;
	}

	/**
	 * @param rpt_stn_code
	 *            the rpt_stn_code to set
	 */
	public void setRpt_stn_code(String rpt_stn_code) {
		this.rpt_stn_code = rpt_stn_code;
	}

	/**
	 * @return the rpt_stn_name
	 */
	public String getRpt_stn_name() {
		return rpt_stn_name;
	}

	/**
	 * @param rpt_stn_name
	 *            the rpt_stn_name to set
	 */
	public void setRpt_stn_name(String rpt_stn_name) {
		this.rpt_stn_name = rpt_stn_name;
	}

	/**
	 * @return the update_time
	 */
	public Date getUpdate_time() {
		return update_time;
	}

	/**
	 * @param update_time
	 *            the update_time to set
	 */
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	/**
	 * @return the rpt_time
	 */
	public Date getRpt_time() {
		return rpt_time;
	}

	/**
	 * @param rpt_time
	 *            the rpt_time to set
	 */
	public void setRpt_time(Date rpt_time) {
		this.rpt_time = rpt_time;
	}

	/**
	 * @return the evt_time
	 */
	public Date getEvt_time() {
		return evt_time;
	}

	/**
	 * @param evt_time
	 *            the evt_time to set
	 */
	public void setEvt_time(Date evt_time) {
		this.evt_time = evt_time;
	}

	/**
	 * @return the cur_stn_code
	 */
	public String getCur_stn_code() {
		return cur_stn_code;
	}

	/**
	 * @param cur_stn_code
	 *            the cur_stn_code to set
	 */
	public void setCur_stn_code(String cur_stn_code) {
		this.cur_stn_code = cur_stn_code;
	}

	/**
	 * @return the cur_stn_char
	 */
	public String getCur_stn_char() {
		return cur_stn_char;
	}

	/**
	 * @param cur_stn_char
	 *            the cur_stn_char to set
	 */
	public void setCur_stn_char(String cur_stn_char) {
		this.cur_stn_char = cur_stn_char;
	}

	/**
	 * @return the cur_stn_name
	 */
	public String getCur_stn_name() {
		return cur_stn_name;
	}

	/**
	 * @param cur_stn_name
	 *            the cur_stn_name to set
	 */
	public void setCur_stn_name(String cur_stn_name) {
		this.cur_stn_name = cur_stn_name;
	}

	/**
	 * @return the cur_adm
	 */
	public String getCur_adm() {
		return cur_adm;
	}

	/**
	 * @param cur_adm
	 *            the cur_adm to set
	 */
	public void setCur_adm(String cur_adm) {
		this.cur_adm = cur_adm;
	}

	/**
	 * @return the car_on_train
	 */
	public String getCar_on_train() {
		return car_on_train;
	}

	/**
	 * @param car_on_train
	 *            the car_on_train to set
	 */
	public void setCar_on_train(String car_on_train) {
		this.car_on_train = car_on_train;
	}

	/**
	 * @return the in_out_flag
	 */
	public String getIn_out_flag() {
		return in_out_flag;
	}

	/**
	 * @param in_out_flag
	 *            the in_out_flag to set
	 */
	public void setIn_out_flag(String in_out_flag) {
		this.in_out_flag = in_out_flag;
	}

	/**
	 * @return the car_order
	 */
	public Long getCar_order() {
		return car_order;
	}

	/**
	 * @param car_order
	 *            the car_order to set
	 */
	public void setCar_order(Long car_order) {
		this.car_order = car_order;
	}

	/**
	 * @return the train_no
	 */
	public String getTrain_no() {
		return train_no;
	}

	/**
	 * @param train_no
	 *            the train_no to set
	 */
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}

	/**
	 * @return the train_dir
	 */
	public String getTrain_dir() {
		return train_dir;
	}

	/**
	 * @param train_dir
	 *            the train_dir to set
	 */
	public void setTrain_dir(String train_dir) {
		this.train_dir = train_dir;
	}

	/**
	 * @return the bndry_flag
	 */
	public String getBndry_flag() {
		return bndry_flag;
	}

	/**
	 * @param bndry_flag
	 *            the bndry_flag to set
	 */
	public void setBndry_flag(String bndry_flag) {
		this.bndry_flag = bndry_flag;
	}

	/**
	 * @return the aei_no
	 */
	public String getAei_no() {
		return aei_no;
	}

	/**
	 * @param aei_no
	 *            the aei_no to set
	 */
	public void setAei_no(String aei_no) {
		this.aei_no = aei_no;
	}

	/**
	 * @return the a_d_flag
	 */
	public String getA_d_flag() {
		return a_d_flag;
	}

	/**
	 * @param a_d_flag
	 *            the a_d_flag to set
	 */
	public void setA_d_flag(String a_d_flag) {
		this.a_d_flag = a_d_flag;
	}

	/**
	 * @return the a_d_time
	 */
	public Date getA_d_time() {
		return a_d_time;
	}

	/**
	 * @param a_d_time
	 *            the a_d_time to set
	 */
	public void setA_d_time(Date a_d_time) {
		this.a_d_time = a_d_time;
	}

	/**
	 * @return the a_d_stn
	 */
	public String getA_d_stn() {
		return a_d_stn;
	}

	/**
	 * @param a_d_stn
	 *            the a_d_stn to set
	 */
	public void setA_d_stn(String a_d_stn) {
		this.a_d_stn = a_d_stn;
	}

	/**
	 * @return the a_d_stn_name
	 */
	public String getA_d_stn_name() {
		return a_d_stn_name;
	}

	/**
	 * @param a_d_stn_name
	 *            the a_d_stn_name to set
	 */
	public void setA_d_stn_name(String a_d_stn_name) {
		this.a_d_stn_name = a_d_stn_name;
	}

	/**
	 * @return the a_d_adm
	 */
	public String getA_d_adm() {
		return a_d_adm;
	}

	/**
	 * @param a_d_adm
	 *            the a_d_adm to set
	 */
	public void setA_d_adm(String a_d_adm) {
		this.a_d_adm = a_d_adm;
	}

	/**
	 * @return the in_bnd_stn
	 */
	public String getIn_bnd_stn() {
		return in_bnd_stn;
	}

	/**
	 * @param in_bnd_stn
	 *            the in_bnd_stn to set
	 */
	public void setIn_bnd_stn(String in_bnd_stn) {
		this.in_bnd_stn = in_bnd_stn;
	}

	/**
	 * @return the in_bnd_name
	 */
	public String getIn_bnd_name() {
		return in_bnd_name;
	}

	/**
	 * @param in_bnd_name
	 *            the in_bnd_name to set
	 */
	public void setIn_bnd_name(String in_bnd_name) {
		this.in_bnd_name = in_bnd_name;
	}

	/**
	 * @return the in_adm_time
	 */
	public Date getIn_adm_time() {
		return in_adm_time;
	}

	/**
	 * @param in_adm_time
	 *            the in_adm_time to set
	 */
	public void setIn_adm_time(Date in_adm_time) {
		this.in_adm_time = in_adm_time;
	}

	/**
	 * @return the in_adm_train_no
	 */
	public String getIn_adm_train_no() {
		return in_adm_train_no;
	}

	/**
	 * @param in_adm_train_no
	 *            the in_adm_train_no to set
	 */
	public void setIn_adm_train_no(String in_adm_train_no) {
		this.in_adm_train_no = in_adm_train_no;
	}

	/**
	 * @return the out_bnd_stn
	 */
	public String getOut_bnd_stn() {
		return out_bnd_stn;
	}

	/**
	 * @param out_bnd_stn
	 *            the out_bnd_stn to set
	 */
	public void setOut_bnd_stn(String out_bnd_stn) {
		this.out_bnd_stn = out_bnd_stn;
	}

	/**
	 * @return the out_bnd_name
	 */
	public String getOut_bnd_name() {
		return out_bnd_name;
	}

	/**
	 * @param out_bnd_name
	 *            the out_bnd_name to set
	 */
	public void setOut_bnd_name(String out_bnd_name) {
		this.out_bnd_name = out_bnd_name;
	}

	/**
	 * @return the out_adm_train_no
	 */
	public String getOut_adm_train_no() {
		return out_adm_train_no;
	}

	/**
	 * @param out_adm_train_no
	 *            the out_adm_train_no to set
	 */
	public void setOut_adm_train_no(String out_adm_train_no) {
		this.out_adm_train_no = out_adm_train_no;
	}

	/**
	 * @return the le_code
	 */
	public String getLe_code() {
		return le_code;
	}

	/**
	 * @param le_code
	 *            the le_code to set
	 */
	public void setLe_code(String le_code) {
		this.le_code = le_code;
	}

	/**
	 * @return the cdy_org_stn
	 */
	public String getCdy_org_stn() {
		return cdy_org_stn;
	}

	/**
	 * @param cdy_org_stn
	 *            the cdy_org_stn to set
	 */
	public void setCdy_org_stn(String cdy_org_stn) {
		this.cdy_org_stn = cdy_org_stn;
	}

	/**
	 * @return the cdy_o_stn_name
	 */
	public String getCdy_o_stn_name() {
		return cdy_o_stn_name;
	}

	/**
	 * @param cdy_o_stn_name
	 *            the cdy_o_stn_name to set
	 */
	public void setCdy_o_stn_name(String cdy_o_stn_name) {
		this.cdy_o_stn_name = cdy_o_stn_name;
	}

	/**
	 * @return the org_adm
	 */
	public String getOrg_adm() {
		return org_adm;
	}

	/**
	 * @param org_adm
	 *            the org_adm to set
	 */
	public void setOrg_adm(String org_adm) {
		this.org_adm = org_adm;
	}

	/**
	 * @return the wb_id
	 */
	public String getWb_id() {
		return wb_id;
	}

	/**
	 * @param wb_id
	 *            the wb_id to set
	 */
	public void setWb_id(String wb_id) {
		this.wb_id = wb_id;
	}

	/**
	 * @return the wb_nbr
	 */
	public String getWb_nbr() {
		return wb_nbr;
	}

	/**
	 * @param wb_nbr
	 *            the wb_nbr to set
	 */
	public void setWb_nbr(String wb_nbr) {
		this.wb_nbr = wb_nbr;
	}

	/**
	 * @return the wb_date
	 */
	public Date getWb_date() {
		return wb_date;
	}

	/**
	 * @param wb_date
	 *            the wb_date to set
	 */
	public void setWb_date(Date wb_date) {
		this.wb_date = wb_date;
	}

	/**
	 * @return the record_id
	 */
	public String getRecord_id() {
		return record_id;
	}

	/**
	 * @param record_id
	 *            the record_id to set
	 */
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	/**
	 * @return the cdy_type
	 */
	public String getCdy_type() {
		return cdy_type;
	}

	/**
	 * @param cdy_type
	 *            the cdy_type to set
	 */
	public void setCdy_type(String cdy_type) {
		this.cdy_type = cdy_type;
	}

	/**
	 * @return the goods_type
	 */
	public String getGoods_type() {
		return goods_type;
	}

	/**
	 * @param goods_type
	 *            the goods_type to set
	 */
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	/**
	 * @return the cdy_name
	 */
	public String getCdy_name() {
		return cdy_name;
	}

	/**
	 * @param cdy_name
	 *            the cdy_name to set
	 */
	public void setCdy_name(String cdy_name) {
		this.cdy_name = cdy_name;
	}

	/**
	 * @return the dest_stn
	 */
	public String getDest_stn() {
		return dest_stn;
	}

	/**
	 * @param dest_stn
	 *            the dest_stn to set
	 */
	public void setDest_stn(String dest_stn) {
		this.dest_stn = dest_stn;
	}

	/**
	 * @return the dest_stn_name
	 */
	public String getDest_stn_name() {
		return dest_stn_name;
	}

	/**
	 * @param dest_stn_name
	 *            the dest_stn_name to set
	 */
	public void setDest_stn_name(String dest_stn_name) {
		this.dest_stn_name = dest_stn_name;
	}

	/**
	 * @return the dest_adm
	 */
	public String getDest_adm() {
		return dest_adm;
	}

	/**
	 * @param dest_adm
	 *            the dest_adm to set
	 */
	public void setDest_adm(String dest_adm) {
		this.dest_adm = dest_adm;
	}

	/**
	 * @return the con_name
	 */
	public String getCon_name() {
		return con_name;
	}

	/**
	 * @param con_name
	 *            the con_name to set
	 */
	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}

	/**
	 * @return the con_entpr_id
	 */
	public String getCon_entpr_id() {
		return con_entpr_id;
	}

	/**
	 * @param con_entpr_id
	 *            the con_entpr_id to set
	 */
	public void setCon_entpr_id(String con_entpr_id) {
		this.con_entpr_id = con_entpr_id;
	}

	/**
	 * @return the con_entpr_name
	 */
	public String getCon_entpr_name() {
		return con_entpr_name;
	}

	/**
	 * @param con_entpr_name
	 *            the con_entpr_name to set
	 */
	public void setCon_entpr_name(String con_entpr_name) {
		this.con_entpr_name = con_entpr_name;
	}

	/**
	 * @return the shipper_name
	 */
	public String getShipper_name() {
		return shipper_name;
	}

	/**
	 * @param shipper_name
	 *            the shipper_name to set
	 */
	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}

	/**
	 * @return the shipper_entpr_id
	 */
	public String getShipper_entpr_id() {
		return shipper_entpr_id;
	}

	/**
	 * @param shipper_entpr_id
	 *            the shipper_entpr_id to set
	 */
	public void setShipper_entpr_id(String shipper_entpr_id) {
		this.shipper_entpr_id = shipper_entpr_id;
	}

	/**
	 * @return the shipper_entpr_name
	 */
	public String getShipper_entpr_name() {
		return shipper_entpr_name;
	}

	/**
	 * @param shipper_entpr_name
	 *            the shipper_entpr_name to set
	 */
	public void setShipper_entpr_name(String shipper_entpr_name) {
		this.shipper_entpr_name = shipper_entpr_name;
	}

	/**
	 * @return the car_rent_flag
	 */
	public String getCar_rent_flag() {
		return car_rent_flag;
	}

	/**
	 * @param car_rent_flag
	 *            the car_rent_flag to set
	 */
	public void setCar_rent_flag(String car_rent_flag) {
		this.car_rent_flag = car_rent_flag;
	}

	/**
	 * @return the car_owner_name
	 */
	public String getCar_owner_name() {
		return car_owner_name;
	}

	/**
	 * @param car_owner_name
	 *            the car_owner_name to set
	 */
	public void setCar_owner_name(String car_owner_name) {
		this.car_owner_name = car_owner_name;
	}

	/**
	 * @return the car_owner_id
	 */
	public String getCar_owner_id() {
		return car_owner_id;
	}

	/**
	 * @param car_owner_id
	 *            the car_owner_id to set
	 */
	public void setCar_owner_id(String car_owner_id) {
		this.car_owner_id = car_owner_id;
	}

	/**
	 * @return the car_owner_flag
	 */
	public String getCar_owner_flag() {
		return car_owner_flag;
	}

	/**
	 * @param car_owner_flag
	 *            the car_owner_flag to set
	 */
	public void setCar_owner_flag(String car_owner_flag) {
		this.car_owner_flag = car_owner_flag;
	}

	/**
	 * @return the car_user_name
	 */
	public String getCar_user_name() {
		return car_user_name;
	}

	/**
	 * @param car_user_name
	 *            the car_user_name to set
	 */
	public void setCar_user_name(String car_user_name) {
		this.car_user_name = car_user_name;
	}

	/**
	 * @return the car_user_id
	 */
	public String getCar_user_id() {
		return car_user_id;
	}

	/**
	 * @param car_user_id
	 *            the car_user_id to set
	 */
	public void setCar_user_id(String car_user_id) {
		this.car_user_id = car_user_id;
	}

	/**
	 * @return the car_user_flag
	 */
	public String getCar_user_flag() {
		return car_user_flag;
	}

	/**
	 * @param car_user_flag
	 *            the car_user_flag to set
	 */
	public void setCar_user_flag(String car_user_flag) {
		this.car_user_flag = car_user_flag;
	}

	/**
	 * @return the car_fill_medium
	 */
	public String getCar_fill_medium() {
		return car_fill_medium;
	}

	/**
	 * @param car_fill_medium
	 *            the car_fill_medium to set
	 */
	public void setCar_fill_medium(String car_fill_medium) {
		this.car_fill_medium = car_fill_medium;
	}

	/**
	 * @return the car_medium_id
	 */
	public String getCar_medium_id() {
		return car_medium_id;
	}

	/**
	 * @param car_medium_id
	 *            the car_medium_id to set
	 */
	public void setCar_medium_id(String car_medium_id) {
		this.car_medium_id = car_medium_id;
	}

	/**
	 * @return the msg_type
	 */
	public String getMsg_type() {
		return msg_type;
	}

	/**
	 * @param msg_type
	 *            the msg_type to set
	 */
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	/**
	 * @return the con_area_id
	 */
	public String getCon_area_id() {
		return con_area_id;
	}

	/**
	 * @param con_area_id
	 *            the con_area_id to set
	 */
	public void setCon_area_id(String con_area_id) {
		this.con_area_id = con_area_id;
	}

	/**
	 * @return the shipper_area_id
	 */
	public String getShipper_area_id() {
		return shipper_area_id;
	}

	/**
	 * @param shipper_area_id
	 *            the shipper_area_id to set
	 */
	public void setShipper_area_id(String shipper_area_id) {
		this.shipper_area_id = shipper_area_id;
	}

	/**
	 * @return the owner_area_id
	 */
	public String getOwner_area_id() {
		return owner_area_id;
	}

	/**
	 * @param owner_area_id
	 *            the owner_area_id to set
	 */
	public void setOwner_area_id(String owner_area_id) {
		this.owner_area_id = owner_area_id;
	}

	/**
	 * @return the user_area_id
	 */
	public String getUser_area_id() {
		return user_area_id;
	}

	/**
	 * @param user_area_id
	 *            the user_area_id to set
	 */
	public void setUser_area_id(String user_area_id) {
		this.user_area_id = user_area_id;
	}

	public Double getCar_light_wgt() {
		return car_light_wgt;
	}

	public void setCar_light_wgt(Double car_light_wgt) {
		this.car_light_wgt = car_light_wgt;
	}

}