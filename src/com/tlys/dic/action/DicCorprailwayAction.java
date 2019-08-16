/**
 * @author 郭建军
 * 
 */
package com.tlys.dic.action;

import java.util.Date;
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
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicCorprailway;
import com.tlys.dic.model.DicRwbureau;
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicCorprailwayService;
import com.tlys.dic.service.DicRwbureauService;
import com.tlys.dic.service.DicRwdepartmentService;
import com.tlys.dic.service.DicSinocorpService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicCorprailwayAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicCorprailway dicCorprailway = new DicCorprailway();
	private List<DicCorprailway> dicCorprailways;
	private String id;
	private String flag;
	private boolean isNew;
	private List<DicSinocorp> dicSinocorps;
	private List<DicRwdepartment> dicRwdepartments;
	private List<DicRwbureau> dicRwbureaus;

	@Autowired
	DicCorprailwayService dicCorprailwayService;

	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicRwdepartmentService dicRwdepartmentService;

	@Autowired
	DicRwbureauService dicRwbureauService;

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		dicCorprailways = dicCorprailwayService.findDICAll();
		HttpServletResponse response = ServletActionContext.getResponse();
		dicCorprailwayService.expExcel(dicCorprailways, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String list_old() throws Exception {
		if (null == dicCorprailway) {
			dicCorprailways = dicCorprailwayService.findDICAll();
		} else {
			dicCorprailways = dicCorprailwayService.find(dicCorprailway);
		}
		return "list";
	}

	public String list() throws Exception {
		String pageUrl = "/dic/dic-corprailway!list.action";
		if (null == flag) {
			flag = "ALL";
		}
		dicCorprailway.setFlag(flag);
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageViewNew中
			// 在生成页码跳转html时，应带上此参数
			if (null != dicCorprailway) {
				String schObjKey = "dicCorprailway_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, dicCorprailway);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				dicCorprailway = (DicCorprailway) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(16);
		dicCorprailwayService.findPageCtr(dicCorprailway, pageCtr);

		return "list";
	}

	public String edit() throws Exception {
		dicSinocorps = dicSinocorpService.findAll();
		dicRwdepartments = dicRwdepartmentService.findAll();
		dicRwbureaus = dicRwbureauService.findAll();
		if (null != id) {
			this.dicCorprailway = dicCorprailwayService.load(id);
			isNew = false;
		} else {
			dicCorprailway.setIsactive("1");
			dicCorprailway.setIspublic("1");
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicCorprailway) {
			throw new Exception("数据未接收到！");
		} else {
			String crwid = dicCorprailwayService.getMaxCrwid();
			String crwids = crwid.substring(0, 1);
			String newCrwid = null;
			int i = 0;
			i = Integer.parseInt(crwid);
			i= i+1;
			if(i<99){
				newCrwid = "0"+String.valueOf(i);
			}else{
				newCrwid = String.valueOf(i);
			}
			dicCorprailway.setCrwid(newCrwid);
			dicCorprailwayService.save(dicCorprailway, isNew);
			// 以下用于msg页面显示的设置
			msg = new Msg(
					Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "企业专用线" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			String[] ids = id.split(",");
			for (String lid : ids) {
				dicCorprailwayService.delete(lid.trim());
			}
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public DicCorprailway getDicCorprailway() {
		return dicCorprailway;
	}

	public void setDicCorprailway(DicCorprailway dicCorprailway) {
		this.dicCorprailway = dicCorprailway;
	}

	public List<DicCorprailway> getDicCorprailways() {
		return dicCorprailways;
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

	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("SPE_CRW");
	}

	public List<DicRwdepartment> getDicRwdepartments() {
		return dicRwdepartments;
	}

	public List<DicRwbureau> getDicRwbureaus() {
		return dicRwbureaus;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
