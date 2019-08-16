package com.tlys.spe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.spe.model.SpePaperstype;
import com.tlys.spe.service.SpePapersService;
import com.tlys.sys.model.SysUser;

@Controller
@ParentPackage("tlys-default")
@Namespace("/spe")
public class SpePaperstypeAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7768602733375725749L;

	@Autowired
	SpePapersService spePapersService;

	private SpePaperstype spePaperstype;

	public String delete() {
		spePaperstype = spePapersService.getSpePaperstype(spePaperstype.getPtypeid());
		spePapersService.delete(spePaperstype);
		return MSG;
	}

	public String edit() {
		if (!isNew) {
			spePaperstype = spePapersService.getSpePaperstype(spePaperstype.getPtypeid());
		}
		return "edit";
	}

	public SpePaperstype getSpePaperstype() {
		return spePaperstype;
	}

	public String save() {
		String nextPtypeId = spePapersService.getNextPtypeId();
		if (logger.isDebugEnabled()) {
			logger.debug("nextPtypeId : " + nextPtypeId);
		}
		spePaperstype.setPtypeid(nextPtypeId);
		spePaperstype.setCreatedtime(new Date());
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("sysUserSess");
		spePaperstype.setCreator(sysUser.getUserid());
		spePapersService.saveOrUpdate(spePaperstype);
		msg = new Msg(Msg.SUCCESS, "²Ù×÷³É¹¦£¡");
		return MSG;
	}

	public void setSpePaperstype(SpePaperstype spePaperstype) {
		this.spePaperstype = spePaperstype;
	}

	@Override
	public void prepare() throws Exception {
		initOpraMap("SPE_MANAGE");
	}
}
