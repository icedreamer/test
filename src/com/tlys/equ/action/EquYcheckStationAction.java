package com.tlys.equ.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.service.DicRwdepartmentService;

/**
 * ¹ý¹ìÕ¾
 * 
 * @author ccsong
 * 
 */
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquYcheckStationAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519433858124252679L;
	private List<DicRwdepartment> rwdepartmentList;
	private DicRwdepartment dicRwdepartment;
	private String goodsid;
	// Æ´ÒôÂë
	private String term;

	@Autowired
	DicRwdepartmentService dicRwdepartmentService;

	public String list() throws Exception {
		rwdepartmentList = dicRwdepartmentService.find(dicRwdepartment);
		return "list";
	}

	public String getrw() throws Exception {
		// ActionContext context = ServletActionContext.getContext();
		// context.getActionInvocation().getProxy().setExecuteResult(false);
		HttpServletResponse response = ServletActionContext.getResponse();
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		rwdepartmentList = dicRwdepartmentService.findByPycode(term);
		if (null != rwdepartmentList && !rwdepartmentList.isEmpty()) {
			for (int i = 0; i < rwdepartmentList.size(); i++) {
				DicRwdepartment dicRwdepartment = rwdepartmentList.get(i);
				// buffer.append("\"").append(dicRwdepartment.getShortname()).append("\",");
				buffer.append("{");
				buffer.append("\"label\" : ").append("\"").append(dicRwdepartment.getShortname()).append("\"")
						.append(",");
				buffer.append("\"value\" : ").append("\"").append(dicRwdepartment.getPycode()).append("\"").append(",");
				buffer.append("\"id\" : ").append("\"").append(dicRwdepartment.getRwdepaid()).append("\"");
				buffer.append("}");
				if (i + 1 != rwdepartmentList.size()) {
					buffer.append(",");
				}
			}
		}
		buffer.append("]");
		String sb = buffer.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("rw : " + sb);
		}
		// if (sb.length() > 1) {
		// sb = sb.substring(0, sb.length() - 1);
		// }
		// sb += "]";
		CommUtils.flushData(sb, response);
		return null;
	}

	public List<DicRwdepartment> getRwdepartmentList() {
		return rwdepartmentList;
	}

	public void setRwdepartmentList(List<DicRwdepartment> rwdepartmentList) {
		this.rwdepartmentList = rwdepartmentList;
	}

	public DicRwdepartment getDicRwdepartment() {
		return dicRwdepartment;
	}

	public void setDicRwdepartment(DicRwdepartment dicRwdepartment) {
		this.dicRwdepartment = dicRwdepartment;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

}
