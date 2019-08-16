/**
 * @author 郭建军
 * 
 */
package com.tlys.dic.action;

import java.util.List;
import java.util.Map;

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
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.model.DicRwbureau;
import com.tlys.dic.service.DicProvinceService;
import com.tlys.dic.service.DicRwbureauService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicRwbureauAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicRwbureau dicRwbureau;
	private List<DicRwbureau> dicRwbureaus;
	private String id;

	private boolean isNew;
	private Map<String, List> suborgMap;

	private List<DicProvince> dicProvinces;

	@Autowired
	DicRwbureauService dicRwbureauService;

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
		dicRwbureauService.expExcel(dicRwbureaus, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() {
		if (null == dicRwbureau) {
			dicRwbureaus = dicRwbureauService.findAll();
		} else {
			dicRwbureaus = dicRwbureauService.find(dicRwbureau);
		}
		// System.out.println(dicRwbureaus);

		return "list";
	}

	public String edit() {
		dicProvinces = dicProvinceService.findAll();
		if (null != id) {
			detail();
			isNew = false;
		} else {
			dicRwbureau = null;
			isNew = true;
		}
		return "input";
	}

	public String detail() {
		this.dicRwbureau = dicRwbureauService.load(id);
		// System.out.println("dicRwbureauAction.detail->rmap=="+rmap);
		return "detail";
	}

	public String save() throws Exception {
		if (null == dicRwbureau) {
			throw new Exception("数据未接收到！");
		} else {
			// System.out.println("dicRwbureauAction.save->dicRwbureau.getAddress()=="+dicRwbureau.getAddress());
			dicRwbureauService.save(dicRwbureau, isNew);

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

	public String suborgs() {
		suborgMap = dicRwbureauService.findSuborgsMap(id);

		return "suborgs";
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			dicRwbureauService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public String listpop() {
		list();
		return "listpop";
	}

	public DicRwbureau getDicRwbureau() {
		return dicRwbureau;
	}

	public void setDicRwbureau(DicRwbureau dicRwbureau) {
		this.dicRwbureau = dicRwbureau;
	}

	public List<DicRwbureau> getDicRwbureaus() {
		return dicRwbureaus;
	}

	public void setDicRwbureaus(List<DicRwbureau> dicRwbureaus) {
		this.dicRwbureaus = dicRwbureaus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public Map<String, List> getSuborgMap() {
		return suborgMap;
	}

	public void setSuborgMap(Map<String, List> suborgMap) {
		this.suborgMap = suborgMap;
	}

	public List<DicProvince> getDicProvinces() {
		return dicProvinces;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

}
