/**
 * @author 郭建军
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
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicProdcategory;
import com.tlys.dic.model.DicProdsecondkind;
import com.tlys.dic.service.DicGoodsService;
import com.tlys.dic.service.DicProdcategoryService;
import com.tlys.dic.service.DicProdsecondkindService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicProdsecondkindAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicProdsecondkind dicProdsecondkind;
	private List<DicProdsecondkind> dicProdsecondkinds;
	private String id;

	private boolean isNew;

	private List<DicProdcategory> dicProdcategorys;
	private List<DicGoods> dicGoodss;
	@Autowired
	DicProdsecondkindService DicProdsecondkindService;

	@Autowired
	DicProdcategoryService dicProdcategoryService;
	@Autowired
	DicGoodsService dicGoodsService;

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// 得到List
		HttpServletResponse response = ServletActionContext.getResponse();
		DicProdsecondkindService.expExcel(dicProdsecondkinds, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() {
		if (null == dicProdsecondkind) {
			dicProdsecondkinds = DicProdsecondkindService.findDICAll();
		} else {
			dicProdsecondkinds = DicProdsecondkindService
					.find(dicProdsecondkind);
		}
		return "list";
	}

	public String edit() {
		dicProdcategorys = dicProdcategoryService.findAll();
		dicGoodss = dicGoodsService.findAll();
		if (null != id) {
			this.dicProdsecondkind = DicProdsecondkindService.load(id);
			isNew = false;
		} else {
			dicProdsecondkind = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicProdsecondkind) {
			throw new Exception("数据未接收到！");
		} else {
			DicProdsecondkindService.save(dicProdsecondkind, isNew);
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
			DicProdsecondkindService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public DicProdsecondkind getDicProdsecondkind() {
		return dicProdsecondkind;
	}

	public void setDicProdsecondkind(DicProdsecondkind dicProdsecondkind) {
		this.dicProdsecondkind = dicProdsecondkind;
	}

	public List<DicProdsecondkind> getDicProdsecondkinds() {
		return dicProdsecondkinds;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}

	public List<DicProdcategory> getDicProdcategorys() {
		return dicProdcategorys;
	}

	public List<DicGoods> getDicGoodss() {
		return dicGoodss;
	}

}
