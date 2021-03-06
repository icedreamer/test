package com.tlys.pla.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.pla.model.PlaAmspstransport;
import com.tlys.pla.service.PlaAmspstransportService;
import com.tlys.sys.model.SysNavimenu;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaAmspstransportAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private PlaAmspstransport plaAmspstransport = new PlaAmspstransport();
	private List<PlaAmspstransport> mstList;
	private List<DicSinocorp> dicSinocorps;
	/**
	 * 当前用户所属企业ID
	 */
	private String corpid = "";
	/**
	 * 企业收货查询条件
	 */
	private String accept = "";
	/**
	 * 企业查询条件时需要的区域ID（区域公司用户进入时使用)
	 */
	private String areaid="";
	/**
	 * 企业查询文本框显示内容
	 */
	private String corpids="";

	@Autowired
	PlaAmspstransportService plaAmspstransportService;
	@Autowired
	DicSinocorpService dicSinocorpService;

	/**
	 * 列表信息 计划状态由前台传递进来 总部查看所有计划、区域公司查看本单位计划、驻厂办或企业查看所属区域公司计划 功能权限 根据状态在页面控制显示
	 * 数据权限
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		// 到货查询当前用户所属企业ID和区域公司ID为空
		if ("".equals(accept)) {
			// 获得当前用户信息
			getCurUser();
			String userCorpid = curUser.getCorpid();
			String userCorptab = curUser.getCorptab();
			// 根据用户单位ID和单位类型查询当前用户所属企业ID和所属区域公司ID
			// 单位ID为35000000或单位类型为null默认为总部不用查询
			if (!"35000000".equals(userCorpid)) {
				// 根据用户单位查询所属企业
				corpid = plaAmspstransportService.getCorpid(userCorpid,
						userCorptab);
			}
			// 总部进入areaid为""
			if (!"".equals(corpid)){
				plaAmspstransport.setCorpid(corpid);
			} else {
				areaid = plaAmspstransportService.getAreaid(userCorpid,
						userCorptab);
				dicSinocorps = dicSinocorpService.findDicSinocorp(areaid,
						corpid);
				String cors = CommUtils.getRestrictionsIn(dicSinocorps,
						"corpid");
				if (plaAmspstransport.getCorpids() == null) {
					plaAmspstransport.setCorpids(cors);
				}
			}
		}
		if (null == plaAmspstransport.getStatuss()
				|| "".equals(plaAmspstransport.getStatuss())) {
			String statuss = "";
			// 根据用户权限过滤计划状态
			SysNavimenu menu = menuService.getSysNavimenuByMenuCode("PLA_AMSPS");
			if (null == menu) {
				throw new Exception("权限菜单数据失！");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			// 如果当前用户具有发布的权限，则他可以看到已导入的计划 
			if (null != menuAuthMap.get("pla-amspstransport_amendStatus_3")) {
				statuss += "0,3,";
			}
			// 如果当前用户有浏览的权限，则可以看到所有已发布的计划
			if (null != menuAuthMap.get("pla-amspstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaAmspstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaAmspstransport.setStatuss("9");
		}
		mstList = plaAmspstransportService.find(plaAmspstransport);
		return "list";
	}

	/**
	 * 列表信息 计划状态由前台传递进来 总部查看所有计划、区域公司查看本单位计划、驻厂办或企业查看所属区域公司计划 功能权限 根据状态在页面控制显示
	 * 数据权限
	 * 
	 * @return
	 * @throws Exception
	 */
	public String query() throws Exception {
		if (null == plaAmspstransport.getStatuss()
				|| "".equals(plaAmspstransport.getStatuss())) {
			String statuss = "";
			// 根据用户权限过滤计划状态
			SysNavimenu menu = menuService.getSysNavimenuByMenuCode("PLA_AMSPS");
			if (null == menu) {
				throw new Exception("权限菜单数据失！");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			// 如果当前用户具有发布的权限，则他可以看到已导入的计划 
			if (null != menuAuthMap.get("pla-amspstransport_amendStatus_3")) {
				statuss += "0,3,";
			}
			// 如果当前用户有浏览的权限，则可以看到所有已发布的计划
			if (null != menuAuthMap.get("pla-amspstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaAmspstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaAmspstransport.setStatuss("9");
		}
		Map map = plaAmspstransportService
				.findByAccept(corpid, plaAmspstransport);
		this.mstList = (List<PlaAmspstransport>) map.get("list");
		this.accept = (String) map.get("senders");
		corpid = "";
		return "list";
	}
	public String listpop() throws Exception {
		list();
		return "listpop";
	}
	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaAmspstransport = plaAmspstransportService.load(id);
		plaAmspstransportService.delete(plaAmspstransport);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaAmspstransport = plaAmspstransportService.load(id);
			isNew = false;
		} else {
			plaAmspstransport = new PlaAmspstransport();
			isNew = true;
		}
		PlaAmspstransport markSch = new PlaAmspstransport();
		mstList = plaAmspstransportService.find(markSch);

		return "input";
	}

	/**
	 * 更新状态（AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaAmspstransportService.update(plaAmspstransport);
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
			plaAmspstransport.setCreatedtime(new Date());
			plaAmspstransportService.save(plaAmspstransport);
		} else {
			// 修改记录
			plaAmspstransportService.update(plaAmspstransport);
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

	public PlaAmspstransport getPlaAmspstransport() {
		return plaAmspstransport;
	}

	public void setPlaAmspstransport(PlaAmspstransport plaAmspstransport) {
		this.plaAmspstransport = plaAmspstransport;
	}

	public List<PlaAmspstransport> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaAmspstransport> mstList) {
		this.mstList = mstList;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_AMSPS");
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

}
