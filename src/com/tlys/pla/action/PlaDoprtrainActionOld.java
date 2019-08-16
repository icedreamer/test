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
import com.tlys.pla.model.PlaDoprtrainOld;
import com.tlys.pla.service.PlaDoprtrainServiceOld;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDoprtrainActionOld extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;

	private String id;
	private PlaDoprtrainOld plaDoprtrainOld;
	private List<PlaDoprtrainOld> mstList;
	private List<DicAreacorp> areacorpList;
	private List<DicSinocorp> sinocorpList;
	//导出条件
	private String schobjkey;
	
	/**
	 * 企业查询条件只做传递参数使用
	 */
	//private String corpid="";
	/**
	 * 区域公司ID
	 */
	//private String areaid="";
	
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	PlaDoprtrainServiceOld plaDoprtrainServiceOld;
	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaDoprtrainOld = (PlaDoprtrainOld) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaDoprtrainServiceOld.expExcel(plaDoprtrainOld, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}
	
	public String list() throws Exception {
		String pageUrl = "/pla/pla-doprtrain!list.action";
		
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			getCurUser();
			
			//根据当前用户及前台传来的参数对查询条件进行组装
			//出来的plaDoprtrain一定不为null
			plaDoprtrainOld = plaDoprtrainServiceOld.buildAuthSch(plaDoprtrainOld,curUser);
			
			//首次进入时，一定加上当前月份为查询条件
			if(null == plaDoprtrainOld.getMonthStr()){
				String mStr = FormatUtil.nowDateStr(9);
				plaDoprtrainOld.setMonthStr(mStr);
			}

			
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if (null != plaDoprtrainOld){
				String schObjKey = "plaDoprtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaDoprtrainOld);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaDoprtrainOld = (PlaDoprtrainOld) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(14);
		plaDoprtrainServiceOld.find(plaDoprtrainOld, pageCtr);
		return "list";
	}


	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaDoprtrainOld = plaDoprtrainServiceOld.load(id);
		plaDoprtrainServiceOld.delete(plaDoprtrainOld);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaDoprtrainOld = plaDoprtrainServiceOld.load(id);
			isNew = false;
		} else {
			plaDoprtrainOld = new PlaDoprtrainOld();
			isNew = true;
		}
		PlaDoprtrainOld markSch = new PlaDoprtrainOld();
		mstList = plaDoprtrainServiceOld.find(markSch);

		return "input";
	}

	/**
	 * 更新状态（AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaDoprtrainServiceOld.update(plaDoprtrainOld);
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
			plaDoprtrainServiceOld.save(plaDoprtrainOld);
		} else {
			// 修改记录
			plaDoprtrainServiceOld.update(plaDoprtrainOld);
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

	public PlaDoprtrainOld getPlaDoprtrain() {
		return plaDoprtrainOld;
	}

	public void setPlaDoprtrain(PlaDoprtrainOld plaDoprtrainOld) {
		this.plaDoprtrainOld = plaDoprtrainOld;
	}

	public List<PlaDoprtrainOld> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaDoprtrainOld> mstList) {
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

	public String getSchobjkey() {
		return schobjkey;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

}
