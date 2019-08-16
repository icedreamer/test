/**
 * 
 */
package com.tlys.equ.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.equ.model.EquCarRent;
import com.tlys.equ.service.EquCarRentService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquCarrentAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private EquCarRent equCarRent = new EquCarRent();
	private Long id;
	private boolean isNew;
	private String carno;

	@Autowired
	EquCarRentService equCarRentService;
	@Autowired
	DicSinocorpService dicSinocorpService;
	private List<DicSinocorp> dicSinocorps;

	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

	public String edit() throws Exception {
		this.dicSinocorps = dicSinocorpService.findAll();
		if (null != id) {
			this.equCarRent = equCarRentService.load(id);
			isNew = false;
		} else {
			equCarRent = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == equCarRent) {
			throw new Exception("数据未接收到！");
		} else {
			equCarRentService.save(equCarRent, isNew);
			// 以下用于msg页面显示的设置
			msg = new Msg(Msg.SUCCESS, "SUCCESS,"+equCarRent.getId());
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			equCarRentService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public EquCarRent getEquCarRent() {
		return equCarRent;
	}

	public void setEquCarRent(EquCarRent equCarRent) {
		this.equCarRent = equCarRent;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

}
