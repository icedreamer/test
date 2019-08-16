package com.tlys.pla.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.pla.model.PlaMoprtrain;
import com.tlys.pla.service.PlaMoprtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMoprtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;

	private String id;
	private PlaMoprtrain plaMoprtrain;
	private List<PlaMoprtrain> mstList;
	private List<DicAreacorp> areacorpList;
	private List<DicSinocorp> sinocorpList;
	/**
	 * 企业收货查询条件
	 */
	private String accept="";
	//导出条件
	private String schobjkey;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	PlaMoprtrainService plaMoprtrainService;
	
	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaMoprtrain = (PlaMoprtrain) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaMoprtrainService.expExcel(plaMoprtrain, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
	
	public String list() throws Exception {
		String pageUrl = "/pla/pla-moprtrain!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			getCurUser();
			
			//根据当前用户及前台传来的参数对查询条件进行组装
			//出来的plaMoprtrain一定不为null
			plaMoprtrain = plaMoprtrainService.buildAuthSch(plaMoprtrain);

			if(null == plaMoprtrain.getMonthStr()){
				String mStr = FormatUtil.nowDateStr(9);
				plaMoprtrain.setMonthStr(mStr);
			}
			
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if (null != plaMoprtrain){
				String schObjKey = "plaMoprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaMoprtrain);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaMoprtrain = (PlaMoprtrain) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(5);
		plaMoprtrainService.find(plaMoprtrain, pageCtr);
		return "list";
	}


	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaMoprtrain = plaMoprtrainService.load(id);
		plaMoprtrainService.delete(plaMoprtrain);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaMoprtrain = plaMoprtrainService.load(id);
			isNew = false;
		} else {
			plaMoprtrain = new PlaMoprtrain();
			isNew = true;
		}
		return "input";
	}

	/**
	 * 更新状态（AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaMoprtrainService.update(plaMoprtrain);
		msg = new Msg(Msg.SUCCESS, "操作成功!");
		return MSG;
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (isNew) {
			// 新增记录
			plaMoprtrainService.save(plaMoprtrain);
		} else {
			// 修改记录
			plaMoprtrainService.update(plaMoprtrain);
		}

		msg = new Msg(Msg.SUCCESS, "操作成功!");
		return edit();
	}

	// -------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlaMoprtrain getPlaMoprtrain() {
		return plaMoprtrain;
	}

	public void setPlaMoprtrain(PlaMoprtrain plaMoprtrain) {
		this.plaMoprtrain = plaMoprtrain;
	}

	public List<PlaMoprtrain> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaMoprtrain> mstList) {
		this.mstList = mstList;
	}

	public List<DicAreacorp> getAreacorpList() {
		return areacorpList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		initOpraMap("PLA_MUPR");
	}

	
	public List<DicSinocorp> getSinocorpList() {
		return sinocorpList;
	}

	
	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

}
