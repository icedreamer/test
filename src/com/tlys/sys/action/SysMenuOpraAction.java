package com.tlys.sys.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.sys.model.SysMenuopra;
import com.tlys.sys.model.SysOprationtype;
import com.tlys.sys.service.SysMenuService;

@Controller
// by struts2，定义action为原型模式
@Scope("prototype")
// by struts2，定义此pachage继承tlys-default(已在struts.xml中定义)
@ParentPackage("tlys-default")
// by struts2，定义action的namespace
@Namespace("/sys")
public class SysMenuOpraAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6931851122081753348L;
	SysMenuopra sysMenuopra;
	// private boolean isNew;
	int menuOperidCount = 0;

	@Autowired
	SysMenuService menuService;

	List<SysOprationtype> sysOprationtypeList;

	public String save() {
		if (null != sysMenuopra) {
			if (isNew) {
				sysMenuopra.setOpraid(menuService.getSeqOperaId());
			}
			sysMenuopra.setDispname(CommUtils.decode(sysMenuopra.getDispname()));
			sysMenuopra.setDescription(CommUtils.decode(sysMenuopra.getDescription()));
			menuService.saveOrUpdate(sysMenuopra);
			msg = new Msg(Msg.SUCCESS, "操作成功");
		}
		sysOprationtypeList = menuService.findSysOprationType();
		return MSG;
	}

	public String edit() {
		if (!isNew) {
			sysMenuopra = menuService.getSysMenuopra(sysMenuopra.getOpraid());
		}
		sysOprationtypeList = menuService.findSysOprationType();
		return "edit";
	}

	public int getMenuOperidCount() {
		return menuOperidCount;
	}

	public SysMenuopra getSysMenuopra() {
		return sysMenuopra;
	}

	public List<SysOprationtype> getSysOprationtypeList() {
		return sysOprationtypeList;
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("SYSMENU");
	}

	// public void setIsNew(boolean isNew) {
	// this.isNew = isNew;
	// }

	public void setMenuOperidCount(int menuOperidCount) {
		this.menuOperidCount = menuOperidCount;
	}

	public void setSysMenuopra(SysMenuopra sysMenuopra) {
		this.sysMenuopra = sysMenuopra;
	}

	public void setSysOprationtypeList(List<SysOprationtype> sysOprationtypeList) {
		this.sysOprationtypeList = sysOprationtypeList;
	}

	public String showMenuOpra() {
		int menuId = sysMenuopra.getMenuid();
		String operidReq = sysMenuopra.getOperid();
		List<SysMenuopra> menuopraList = menuService.findSysMenuopraByMenuId(menuId);
		if (null != menuopraList && !menuopraList.isEmpty()) {
			for (SysMenuopra sysMenuopra : menuopraList) {
				String operid = sysMenuopra.getOperid();
				if (null != operidReq && operidReq.equals(operid)) {
					menuOperidCount++;
				}
			}
		}
		msg = new Msg(Msg.SUCCESS, String.valueOf(menuOperidCount));
		return "msg";
	}
}
