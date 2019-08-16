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
import com.tlys.dic.model.DicRentMode;
import com.tlys.dic.service.DicRentmodeService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicRentmodeAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicRentMode dicRentmode;
	private List<DicRentMode> dicRentmodes;
	private String id;

	private boolean isNew;

	@Autowired
	DicRentmodeService dicRentmodeService;

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// 得到List
		HttpServletResponse response = ServletActionContext.getResponse();
		dicRentmodeService.expExcel(dicRentmodes, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() throws Exception {
		if (null == dicRentmode) {
			dicRentmodes = dicRentmodeService.findDICAll();
		} else {
			dicRentmodes = dicRentmodeService.find(dicRentmode);
		}

		return "list";
	}

	public String edit() {
		if (null != id) {
			this.dicRentmode = dicRentmodeService.load(id);
			isNew = false;
		} else {
			dicRentmode = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicRentmode) {
			throw new Exception("数据未接收到！");
		} else {
			// System.out.println("DicRentmodeAction.save->dicRentmode.getAddress()=="+dicRentmode.getAddress());
			dicRentmodeService.save(dicRentmode, isNew);

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
			dicRentmodeService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	// =================================
	/**
	 * @return the dicRentmode
	 */
	public DicRentMode getDicRentmode() {
		return dicRentmode;
	}

	/**
	 * @param dicRentmode
	 *            the dicRentmode to set
	 */
	public void setDicRentmode(DicRentMode dicRentmode) {
		this.dicRentmode = dicRentmode;
	}

	/**
	 * @return the dicRentmodes
	 */
	public List<DicRentMode> getDicRentmodes() {
		return dicRentmodes;
	}

	/**
	 * @param dicRentmodes
	 *            the dicRentmodes to set
	 */
	public void setDicRentmodes(List<DicRentMode> dicRentmodes) {
		this.dicRentmodes = dicRentmodes;
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
