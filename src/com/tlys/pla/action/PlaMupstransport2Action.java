package com.tlys.pla.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.model.PlaMupstransport2;
import com.tlys.pla.service.PlaMupstransport2Service;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMupstransport2Action extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private PlaMupstransport2 plaMupstransport2;

	@Autowired
	PlaMupstransport2Service plaMupstransport2Service;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/pla/pla-mupstransport2!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if(null==plaMupstransport2){
			String schObjKey = "plaMupstransport2_" + new Date().getTime();
			pageCtr.setSchObjKey(schObjKey);
			setSessionAttr(schObjKey, plaMupstransport2);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaMupstransport2 = (PlaMupstransport2) getSessionAttr(pageCtr
						.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(15);
		plaMupstransport2Service.find(plaMupstransport2, pageCtr);
		return "list";
	}

	/**
	 * 列表信息 计划状态由前台传递进来 总部查看所有计划、区域公司查看本单位计划、驻厂办或企业查看所属区域公司计划 功能权限 根据状态在页面控制显示
	 * 数据权限
	 * 
	 * @return
	 * @throws Exception
	 */
//	public String query() throws Exception {
//		Map map = plaMupstransport2Service.findByAccept(corpid,
//				plaMupstransport2);
//		this.mstList = (List<PlaMupstransport2>) map.get("list");
//		this.accept = (String) map.get("senders");
//		corpid = "";
//		return "list";
//	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaMupstransport2 = plaMupstransport2Service.load(id);
		String delRs = plaMupstransport2Service.delete(plaMupstransport2);
		msg = new Msg(Msg.SUCCESS, delRs);
		return MSG;
	}

	/**
	 * 更新状态（AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		String result = plaMupstransport2Service.update(plaMupstransport2);
		msg = new Msg(Msg.SUCCESS, result);
		return MSG;
	}

	// -------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PlaMupstransport2 getPlaMupstransport2() {
		return plaMupstransport2;
	}

	public void setPlaMupstransport2(PlaMupstransport2 plaMupstransport2) {
		this.plaMupstransport2 = plaMupstransport2;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_MUPS2");
	}

}
