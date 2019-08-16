/**
 * @author 郭建军
 * 
 */
package com.tlys.dic.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
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
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicRwdepartype;
import com.tlys.dic.service.DicProvinceService;
import com.tlys.dic.service.DicRwbureauService;
import com.tlys.dic.service.DicRwdepartmentService;
import com.tlys.dic.service.DicRwdepartypeService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicRwdepartmentAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicRwdepartment dicRwdepartment;
	private List<DicRwdepartment> dicRwdepartments;
	private String bureauid;
	private String typeid;
	private String id;
	private String parentid;
	private String parentName;
	private boolean isNew;

	private List<DicProvince> dicProvinces;
	private List<DicRwdepartype> dicrwdepartypes;
	private List<DicRwbureau> dicrwbureaus;
	private List parents = new ArrayList();
	@Autowired
	DicRwdepartmentService DicRwdepartmentService;

	@Autowired
	DicProvinceService dicProvinceService;

	@Autowired
	DicRwdepartypeService dicRwdepartypeService;

	@Autowired
	DicRwbureauService dicRwbureauService;

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		list();// 得到List
		HttpServletResponse response = ServletActionContext.getResponse();
		DicRwdepartmentService.expExcel(dicRwdepartments, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list() throws Exception {
		if (null == dicRwdepartment) {
			if (bureauid == null && typeid == null) {
				dicRwdepartments = DicRwdepartmentService.findDICAll();
			} else {
				if (bureauid != null)
					dicRwdepartments = DicRwdepartmentService.findByType(
							bureauid, "bureauid");
				else
					dicRwdepartments = DicRwdepartmentService.findByType(
							typeid, "tpyeid");

			}
		} else {
			dicRwdepartments = DicRwdepartmentService.find(dicRwdepartment);
		}

		return "list";
	}

	public String edit() {
		dicProvinces = dicProvinceService.findAll();
		dicrwdepartypes = dicRwdepartypeService.findAll();
		dicrwbureaus = dicRwbureauService.findAll();
		dicRwdepartments = DicRwdepartmentService.findAll();
		if (null != id) {
			detail();
			isNew = false;
		} else {
			dicRwdepartment = new DicRwdepartment();
			if (parentid != null && parentid.length() > 0) {
				dicRwdepartment.setParentid(parentid);
				if (bureauid != "") {
					DicRwdepartment dic = DicRwdepartmentService.load(parentid);
					bureauid = dic.getBureauid();
					typeid = dic.getTpyeid();
				}
			} else {
				dicRwdepartment.setParentid(bureauid);
			}
			//路局、类型页面新单位时使用
			dicRwdepartment.setBureauid(bureauid);
			dicRwdepartment.setTpyeid(typeid);
			//默认值 
			dicRwdepartment.setIsactive("1");
			dicRwdepartment.setHasaei("0");
			dicRwdepartment.setIsrailstn("0");
			isNew = true;
		}
		return "input";
	}

	public String detail() {
		this.dicRwdepartment = DicRwdepartmentService.load(id);
		// System.out.println("DicRwdepartmentAction.detail->rmap=="+rmap);
		return "detail";
	}

	public String save() throws Exception {
		if (null == dicRwdepartment) {
			throw new Exception("数据未接收到！");
		} else {
			String str[] = dicRwdepartment.getParentid().split(",");
			if (str[1].trim().length() > 0) {
				dicRwdepartment.setParentid(str[1].trim());
			} else {
				dicRwdepartment.setParentid(str[0].trim());
			}
			DicRwdepartmentService.save(dicRwdepartment, isNew);

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
			DicRwdepartmentService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public String check() throws Exception {
		DicRwdepartment dicRwdepartment = new DicRwdepartment();
		dicRwdepartment.setBureauid(bureauid);
		dicRwdepartment.setTpyeid(typeid);
		this.dicRwdepartments = DicRwdepartmentService.find(dicRwdepartment);
		String str = bureauid + "_" + typeid + "||";
		for (DicRwdepartment dic : dicRwdepartments) {
			str += dic.getRwdepaid() + "," + dic.getShortname() + "#";
			// str+="<a
			// href=\"javascript:check1(3,'"+dic.getRwdepaid()+"')\">"+dic.getShortname()+"</a>";
		}
		str = str.substring(0, str.length() - 1);
		this.responseText(str);
		return null;
	}

	public void responseText(String str) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream ouputStream = response.getOutputStream();
		response.setContentType("text/html;charset=GB2312");
		byte[] retureValue = str.getBytes();
		ouputStream.write(retureValue);
	}

	// -----------------
	public DicRwdepartment getDicRwdepartment() {
		return dicRwdepartment;
	}

	public void setDicRwdepartment(DicRwdepartment dicRwdepartment) {
		this.dicRwdepartment = dicRwdepartment;
	}

	public List<DicRwdepartment> getDicRwdepartments() {
		return dicRwdepartments;
	}

	public void setDicRwdepartments(List<DicRwdepartment> dicRwdepartments) {
		this.dicRwdepartments = dicRwdepartments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getBureauid() {
		return bureauid;
	}

	public void setBureauid(String bureauid) {
		this.bureauid = bureauid;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public List<DicProvince> getDicProvinces() {
		return dicProvinces;
	}

	public List<DicRwdepartype> getDicrwdepartypes() {
		return dicrwdepartypes;
	}

	public List<DicRwbureau> getDicrwbureaus() {
		return dicrwbureaus;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List getParents() {
		return parents;
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
