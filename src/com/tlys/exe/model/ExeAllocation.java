package com.tlys.exe.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicTankrepcorp;

/**
 * 调配信息model
 * 
 * @author 孔垂云
 * 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_ZBC_EXE_ALLOCATION")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class ExeAllocation implements Serializable {
	private ExeAllocationId id;
	private DicSinocorp car_lessor;
	private DicSinocorp car_lessee;
	private DicTankrepcorp tank_maint_dept;
	private DicRwdepartment car_maint_dept;
	private Date rent_start_date;
	private Date rent_end_date;
	private String create_user;
	private Date create_date;
	private String update_user;
	private Date update_date;
	private String submit_status;
	private Date submit_date;

	private DicGoodscategory goodscategory;// 充装介质

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "car_medium_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public DicGoodscategory getGoodscategory() {
		return goodscategory;
	}

	public void setGoodscategory(DicGoodscategory goodscategory) {
		this.goodscategory = goodscategory;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "TANK_MAINT_DEPT", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public DicTankrepcorp getTank_maint_dept() {
		return tank_maint_dept;
	}

	public void setTank_maint_dept(DicTankrepcorp tank_maint_dept) {
		this.tank_maint_dept = tank_maint_dept;
	}

	public ExeAllocation() {
		super();
	}

	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "car_no", column = @Column(name = "car_no", nullable = false)),
			@AttributeOverride(name = "contract_no", column = @Column(name = "contract_no", nullable = false)) })
	public ExeAllocationId getId() {
		return id;
	}

	public void setId(ExeAllocationId id) {
		this.id = id;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CAR_LESSOR")
	public DicSinocorp getCar_lessor() {
		return car_lessor;
	}

	public void setCar_lessor(DicSinocorp car_lessor) {
		this.car_lessor = car_lessor;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CAR_LESSEE")
	public DicSinocorp getCar_lessee() {
		return car_lessee;
	}

	public void setCar_lessee(DicSinocorp car_lessee) {
		this.car_lessee = car_lessee;
	}

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CAR_MAINT_DEPT", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	public DicRwdepartment getCar_maint_dept() {
		return car_maint_dept;
	}

	public void setCar_maint_dept(DicRwdepartment car_maint_dept) {
		this.car_maint_dept = car_maint_dept;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RENT_START_DATE", length = 20)
	public Date getRent_start_date() {
		return rent_start_date;
	}

	public void setRent_start_date(Date rent_start_date) {
		this.rent_start_date = rent_start_date;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RENT_END_DATE", length = 20)
	public Date getRent_end_date() {
		return rent_end_date;
	}

	public void setRent_end_date(Date rent_end_date) {
		this.rent_end_date = rent_end_date;
	}

	@Column(name = "CREATE_USER", length = 6)
	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_DATE", length = 20)
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	@Column(name = "UPDATE_USER", length = 6)
	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATE_DATE", length = 20)
	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	@Column(name = "SUBMIT_STATUS", length = 6)
	public String getSubmit_status() {
		return submit_status;
	}

	public void setSubmit_status(String submit_status) {
		this.submit_status = submit_status;
	}

	@Column(name = "SUBMIT_DATE", length = 20)
	@Temporal(TemporalType.DATE)
	public Date getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(Date submit_date) {
		this.submit_date = submit_date;
	}

}
