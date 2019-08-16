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
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.service.DicGoodscategoryService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicGoodscategoryAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicGoodscategory dicGoodscategory;
	private List<DicGoodscategory> dicGoodscategorys;
	private String id;

	private boolean isNew;

	@Autowired
	DicGoodscategoryService dicGoodscategoryService;

	/**
	 * ����excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// �õ�List
		HttpServletResponse response = ServletActionContext.getResponse();
		dicGoodscategoryService.expExcel(dicGoodscategorys, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() throws Exception {
		if (null == dicGoodscategory) {
			dicGoodscategorys = dicGoodscategoryService.findDICAll();
		} else {
			dicGoodscategorys = dicGoodscategoryService.find(dicGoodscategory);
		}

		return "list";
	}

	public String edit() {
		if (null != id) {
			this.dicGoodscategory = dicGoodscategoryService.load(id);
			isNew = false;
		} else {
			dicGoodscategory = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicGoodscategory) {
			throw new Exception("����δ���յ���");
		} else {
			// System.out.println("DicGoodscategoryAction.save->dicGoodscategory.getAddress()=="+dicGoodscategory.getAddress());
			dicGoodscategoryService.save(dicGoodscategory, isNew);

			// ��������msgҳ����ʾ������

			msg = new Msg(
					Msg.SUCCESS,
					"���/�༭�ɹ���",
					false,
					"alert('���/�༭�ɹ���');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("����ȷ��Ҫɾ���Ķ���");
		} else {
			dicGoodscategoryService.delete(id);
			msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���");
		}
		return MSG;
	}

	// =================================
	/**
	 * @return the dicGoodscategory
	 */
	public DicGoodscategory getDicGoodscategory() {
		return dicGoodscategory;
	}

	/**
	 * @param dicGoodscategory
	 *            the dicGoodscategory to set
	 */
	public void setDicGoodscategory(DicGoodscategory dicGoodscategory) {
		this.dicGoodscategory = dicGoodscategory;
	}

	/**
	 * @return the dicGoodscategorys
	 */
	public List<DicGoodscategory> getDicGoodscategorys() {
		return dicGoodscategorys;
	}

	/**
	 * @param dicGoodscategorys
	 *            the dicGoodscategorys to set
	 */
	public void setDicGoodscategorys(List<DicGoodscategory> dicGoodscategorys) {
		this.dicGoodscategorys = dicGoodscategorys;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("DIC");
	}
}
