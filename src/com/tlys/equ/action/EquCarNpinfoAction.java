/**
 * 
 */
package com.tlys.equ.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.equ.model.EquCarNpinfo;
import com.tlys.equ.service.EquCarNpinfoService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquCarNpinfoAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private EquCarNpinfo equCarNpinfo = new EquCarNpinfo();
	private String id;
	private boolean isNew;

	@Autowired
	EquCarNpinfoService equCarNpinfoService;

	public String edit() {
		if (null != id) {
			this.equCarNpinfo = equCarNpinfoService.load(id);
			isNew = false;
		} else {
			equCarNpinfo = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == equCarNpinfo) {
			throw new Exception("数据未接收到！");
		} else {
			equCarNpinfoService.save(equCarNpinfo, isNew);

			// 以下用于msg页面显示的设置

			msg = new Msg(
					Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "铁路单位类" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			equCarNpinfoService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public EquCarNpinfo getEquCarNpinfo() {
		return equCarNpinfo;
	}

	public void setEquCarNpinfo(EquCarNpinfo equCarNpinfo) {
		this.equCarNpinfo = equCarNpinfo;
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

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

}
