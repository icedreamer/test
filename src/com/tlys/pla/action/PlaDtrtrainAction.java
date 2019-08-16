package com.tlys.pla.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.tlys.pla.model.PlaDtrtrain;
import com.tlys.pla.service.PlaDtrtrainService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDtrtrainAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private final Logger log = Logger.getLogger(this.getClass());
	private String id;
	private PlaDtrtrain plaDtrtrain;
	private List<PlaDtrtrain> mstList;
	private List<DicAreacorp> areacorpList;
	private List<DicSinocorp> sinocorpList;
	//导出条件
	private String schobjkey;
	
	/**
	 * 产品类型(统销，非统销...)
	 */
	private String prkind;
	
	private String prkindname;
	/**
	 * 企业查询条件只做传递参数使用
	 */
	//private String corpid="";
	/**
	 * 区域公司ID
	 */
	//private String areaid="";
	/**
	 * 企业收货查询条件
	 */
	private String accept="";
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	@Autowired
	PlaDtrtrainService plaDtrtrainService;
	
	
	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaDtrtrain = (PlaDtrtrain) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaDtrtrainService.expExcel(plaDtrtrain, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}	
	public String list() throws Exception {
		String pageUrl = "/pla/pla-dtrtrain!list.action";
		if(null == prkind){
			prkind = "1";//默认为统销
		}
		
		if("1".equals(prkind)){
			prkindname = "统销";
		}else if("0".equals(prkind)){
			prkindname = "非统销";
		}else if("-1".equals(prkind)){
			prkindname = "其他产品";
		}
		
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			
			//根据当前用户及前台传来的参数对查询条件进行组装
			//出来的plaDtrtrain一定不为null
			plaDtrtrain = plaDtrtrainService.buildAuthSch(plaDtrtrain);
			if("-1".equals(prkind)){
				plaDtrtrain.setExPrkind("0,1");
			}else{
				plaDtrtrain.setProductkind(prkind);
			}
			
			//首次进入时，一定加上当前月份为查询条件
			if(null == plaDtrtrain.getMonthStr()){
				String mStr = FormatUtil.nowDateStr(9);
				plaDtrtrain.setMonthStr(mStr);
			}
			

			
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if (null != plaDtrtrain){
				String schObjKey = "plaDtrtrain_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaDtrtrain);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaDtrtrain = (PlaDtrtrain) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(14);
		plaDtrtrainService.find(plaDtrtrain, pageCtr);
		return "list";
	}


	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaDtrtrain = plaDtrtrainService.load(id);
		plaDtrtrainService.delete(plaDtrtrain);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaDtrtrain = plaDtrtrainService.load(id);
			isNew = false;
		} else {
			plaDtrtrain = new PlaDtrtrain();
			isNew = true;
		}
		PlaDtrtrain markSch = new PlaDtrtrain();
		mstList = plaDtrtrainService.find(markSch);

		return "input";
	}

	/**
	 * 更新状态（AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaDtrtrainService.update(plaDtrtrain);
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
			plaDtrtrainService.save(plaDtrtrain);
		} else {
			// 修改记录
			plaDtrtrainService.update(plaDtrtrain);
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

	public PlaDtrtrain getPlaDtrtrain() {
		return plaDtrtrain;
	}

	public void setPlaDtrtrain(PlaDtrtrain plaDtrtrain) {
		this.plaDtrtrain = plaDtrtrain;
	}

	public List<PlaDtrtrain> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaDtrtrain> mstList) {
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


	public String getPrkind() {
		return prkind;
	}


	public void setPrkind(String prkind) {
		this.prkind = prkind;
	}


	public String getPrkindname() {
		return prkindname;
	}


	public void setPrkindname(String prkindname) {
		this.prkindname = prkindname;
	}


	public String getSchobjkey() {
		return schobjkey;
	}


	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

}
