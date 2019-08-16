/**
 * 
 */
package com.tlys.dic.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.dic.model.DicCarmaker;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.service.DicCarmakerService;
import com.tlys.dic.service.DicProvinceService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicCarmakerAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicCarmaker dicCarmaker;
	private List<DicCarmaker> dicCarmakers;
	private String id;

	private List<DicProvince> dicProvinces;
	private String provids;

	private boolean isNew;

	@Autowired
	DicCarmakerService dicCarmakerService;

	@Autowired
	DicProvinceService dicProvinceService;

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// 得到List
		HttpServletResponse response = ServletActionContext.getResponse();
		dicCarmakerService.expExcel(dicCarmakers, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() throws Exception {
		if (null == dicCarmaker) {
			if (provids == null) {
				dicCarmakers = dicCarmakerService.findDICAll();
			} else {
				dicCarmakers = dicCarmakerService.findByProvids(provids);
			}
		} else {
			dicCarmakers = dicCarmakerService.find(dicCarmaker);
		}
		return "list";
	}

	public String edit() {
		dicProvinces = dicProvinceService.findAll();
		if (null != id) {
			this.dicCarmaker = dicCarmakerService.load(id);
			isNew = false;
		} else {
			dicCarmaker = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicCarmaker) {
			throw new Exception("数据未接收到！");
		} else {
			dicCarmakerService.save(dicCarmaker, isNew);

			// 以下用于msg页面显示的设置

			msg = new Msg(
					Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			dicCarmakerService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	// =================================


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
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

	/**
	 * @return the dicProvinces
	 */
	public List<DicProvince> getDicProvinces() {
		return dicProvinces;
	}

	/**
	 * @param dicProvinces
	 *            the dicProvinces to set
	 */
	public void setDicProvinces(List<DicProvince> dicProvinces) {
		this.dicProvinces = dicProvinces;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

	public String getProvids() {
		return provids;
	}

	public void setProvids(String provids) {
		this.provids = provids;
	}

	public DicCarmaker getDicCarmaker() {
		return dicCarmaker;
	}

	public void setDicCarmaker(DicCarmaker dicCarmaker) {
		this.dicCarmaker = dicCarmaker;
	}

	public List<DicCarmaker> getDicCarmakers() {
		return dicCarmakers;
	}
}
