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
import com.tlys.dic.model.DicProdcategory;
import com.tlys.dic.service.DicProdcategoryService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicProdcategoryAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicProdcategory dicProdcategory;
	private List<DicProdcategory> dicProdcategorys;
	private String id;

	private boolean isNew;

	@Autowired
	DicProdcategoryService DicProdcategoryService;

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// 得到List
		HttpServletResponse response = ServletActionContext.getResponse();
		DicProdcategoryService.expExcel(dicProdcategorys, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() throws Exception {
		if (null == dicProdcategory) {
			dicProdcategorys = DicProdcategoryService.findAll();
		} else {
			dicProdcategorys = DicProdcategoryService.find(dicProdcategory);
		}
		return "list";
	}

	public String edit() {
		if (null != id) {
			this.dicProdcategory = DicProdcategoryService.load(id);
			isNew = false;
		} else {
			dicProdcategory = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicProdcategory) {
			throw new Exception("数据未接收到！");
		} else {
			DicProdcategoryService.save(dicProdcategory, isNew);
			msg = new Msg(
					Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "产品大类" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			DicProdcategoryService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public DicProdcategory getDicProdcategory() {
		return dicProdcategory;
	}

	public void setDicProdcategory(DicProdcategory dicProdcategory) {
		this.dicProdcategory = dicProdcategory;
	}

	public List<DicProdcategory> getDicProdcategorys() {
		return dicProdcategorys;
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

}
