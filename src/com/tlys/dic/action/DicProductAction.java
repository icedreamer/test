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
import com.tlys.dic.model.DicProduct;
import com.tlys.dic.service.DicGoodsService;
import com.tlys.dic.service.DicProdcategoryService;
import com.tlys.dic.service.DicProdsecondkindService;
import com.tlys.dic.service.DicProductService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicProductAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicProduct dicProduct;
	private List<DicProduct> dicProducts;
	private String id;

	private boolean isNew;

	private List<DicProdcategory> dicProdcategorys;
	private List<DicProdsecondkind> dicProdsecondkinds;
	private List<DicGoods> dicGoodss;
	@Autowired
	DicProductService DicProductService;

	@Autowired
	DicProdcategoryService dicProdcategoryService;
	@Autowired
	DicProdsecondkindService dicProdsecondkindService;
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
		DicProductService.expExcel(dicProducts, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
	public String list() throws Exception {
		if (null == dicProduct) {
			dicProducts = DicProductService.findAll();
		} else {
			dicProducts = DicProductService.find(dicProduct);
		}
		return "list";
	}

	public String edit() throws Exception {
		dicProdcategorys = dicProdcategoryService.findAll();
		dicProdsecondkinds  =dicProdsecondkindService.findAll();
		dicProducts = DicProductService.findAll();
		dicGoodss = dicGoodsService.findAll();
		if (null != id) {
			this.dicProduct = DicProductService.load(id);
			isNew = false;
		} else {
			dicProduct = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicProduct) {
			throw new Exception("数据未接收到！");
		} else {
			DicProductService.save(dicProduct, isNew);
			msg = new Msg(
					Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "产品字典" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			DicProductService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public DicProduct getDicProduct() {
		return dicProduct;
	}

	public void setDicProduct(DicProduct dicProduct) {
		this.dicProduct = dicProduct;
	}

	public List<DicProduct> getDicProducts() {
		return dicProducts;
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
	public List<DicProdsecondkind> getDicProdsecondkinds() {
		return dicProdsecondkinds;
	}

}
