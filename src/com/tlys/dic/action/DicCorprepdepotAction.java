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
import com.tlys.dic.model.DicCorprepdepot;
import com.tlys.dic.model.DicRwbureau;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicCorprepdepotService;
import com.tlys.dic.service.DicRwbureauService;
import com.tlys.dic.service.DicSinocorpService;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/dic")
public class DicCorprepdepotAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private DicCorprepdepot dicCorprepdepot;
	private List<DicCorprepdepot> dicCorprepdepots;
	private List<DicRwbureau> dicrwbureaus;
	private Long id;

	private boolean isNew;

	private List<DicSinocorp> dicSinocorps;

	@Autowired
	DicCorprepdepotService DicCorprepdepotService;

	@Autowired
	DicSinocorpService dicSinocorpService;

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
		DicCorprepdepotService.expExcel(dicCorprepdepots, response);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
	public String list() throws Exception {
		if (null == dicCorprepdepot) {
			dicCorprepdepots = DicCorprepdepotService.findDICAll();
		} else {
			dicCorprepdepots = DicCorprepdepotService.find(dicCorprepdepot);
		}
		return "list";
	}

	public String edit() throws Exception {
		dicSinocorps = dicSinocorpService.findAll();
		dicrwbureaus = dicRwbureauService.findAll();
		if (null != id) {
			this.dicCorprepdepot = DicCorprepdepotService.load(id);
			isNew = false;
		} else {
			dicCorprepdepot = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == dicCorprepdepot) {
			throw new Exception("数据未接收到！");
		} else {
			DicCorprepdepotService.save(dicCorprepdepot, isNew);
			msg = new Msg(
					Msg.SUCCESS,
					"添加/编辑成功！",
					false,
					"alert('添加/编辑成功！');frameElement.lhgDG.curWin.location.reload();",
					new String[] { "curTitle", "检修车辆段" });
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			DicCorprepdepotService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	public DicCorprepdepot getDicCorprepdepot() {
		return dicCorprepdepot;
	}

	public void setDicCorprepdepot(DicCorprepdepot dicCorprepdepot) {
		this.dicCorprepdepot = dicCorprepdepot;
	}

	public List<DicCorprepdepot> getDicCorprepdepots() {
		return dicCorprepdepots;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		initOpraMap("DIC");
	}

	public List<DicRwbureau> getDicrwbureaus() {
		return dicrwbureaus;
	}

}
