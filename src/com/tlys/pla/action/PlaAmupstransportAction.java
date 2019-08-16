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
import com.tlys.comm.util.FormatUtil;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.pla.model.PlaAmupstransport;
import com.tlys.pla.service.PlaAmupstransportService;
import com.tlys.sys.model.SysNavimenu;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaAmupstransportAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414809L;
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private PlaAmupstransport plaAmupstransport = new PlaAmupstransport();
	private List<PlaAmupstransport> mstList;
	private List<DicAreacorp> areacorpList;
	/**
	 * 当前用户所属企业ID
	 */
	private String corpid = "";
	/**
	 * 当前用户所属区域公司ID
	 */
	private String areaid = "";
	/**
	 * 企业收货查询条件
	 */
	private String accept = "";
	@Autowired
	DicAreacorpService dicAreacorpService;

	@Autowired
	PlaAmupstransportService plaAmupstransportService;

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
				corpid = plaAmupstransportService.getCorpid(userCorpid,
						userCorptab);
				// 根据用户单位查询所属区域公司
				areaid = plaAmupstransportService.getAreaid(userCorpid,
						userCorptab);
			}
			// 总部进入areaid为""
			if (!"".equals(areaid))
				plaAmupstransport.setAreaid(areaid);
		}
		if (null == plaAmupstransport.getStatuss()
				|| "".equals(plaAmupstransport.getStatuss())) {
			String statuss = "";
			// 根据用户权限过滤计划状态
			SysNavimenu menu = menuService
					.getSysNavimenuByMenuCode("PLA_AMUPS");
			if (null == menu) {
				throw new Exception("权限菜单数据失！");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);
			

			// 如果当前用户具有提交的权限，则他可以看到未提交和驳回的计划
			if (null != menuAuthMap.get("pla-amupstransport_amendStatus_1")) {
				statuss += "0,2,";
			}
			// 如果当前用户具有发布的权限，则他可以看到已提交的计划
			if (null != menuAuthMap.get("pla-amupstransport_amendStatus_3")) {
				statuss += "1,";
			}
			// 如果当前用户有浏览的权限，则可以看到所有已发布的计划
			if (null != menuAuthMap.get("pla-amupstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaAmupstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaAmupstransport.setStatuss("9");
		}
		// 添加区域查询条件
		DicAreacorp dicAreacorp = new DicAreacorp();
		dicAreacorp.setAreaid(areaid);
		areacorpList = dicAreacorpService.findByAreaid(dicAreacorp);
		if ("".equals(areaid)) {// 总部用户进入添加页面查询全部的条件
			DicAreacorp obj = new DicAreacorp();
			obj.setAreaid("");
			obj.setShortname("全部");
			areacorpList.add(0, obj);
		}
		mstList = plaAmupstransportService.find(plaAmupstransport);
		
		//在此调用存储过程,传入参数，当前月份(yyyyMM)
		plaAmupstransportService.syncPlaAmup(FormatUtil.nowDateStr(FormatUtil.COMPACT_MONTH));
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
		if (null == plaAmupstransport.getStatuss()
				|| "".equals(plaAmupstransport.getStatuss())) {
			String statuss = "";
			// 根据用户权限过滤计划状态
			SysNavimenu menu = menuService
					.getSysNavimenuByMenuCode("PLA_AMUPS");
			if (null == menu) {
				throw new Exception("权限菜单数据失！");
			}
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			// 如果当前用户具有提交的权限，则他可以看到未提交和驳回的计划
			if (null != menuAuthMap.get("pla-amupstransport_amendStatus_1")) {
				statuss += "0,2,";
			}
			// 如果当前用户具有发布的权限，则他可以看到已提交的计划
			if (null != menuAuthMap.get("pla-amupstransport_amendStatus_3")) {
				statuss += "1,";
			}
			// 如果当前用户有浏览的权限，则可以看到所有已发布的计划
			if (null != menuAuthMap.get("pla-amupstransport_list")) {
				statuss += "3,";
			}
			if (statuss.length() > 1)
				plaAmupstransport.setStatuss(statuss.substring(0, statuss
						.length() - 1));
			else
				plaAmupstransport.setStatuss("9");
		}
		// 企业到货查询显示全部区域公司
		areacorpList = dicAreacorpService.findAll();
		DicAreacorp obj = new DicAreacorp();
		obj.setAreaid("");
		obj.setShortname("全部");
		areacorpList.add(0, obj);
		Map map = plaAmupstransportService.findByAccept(corpid,
				plaAmupstransport);
		this.mstList = (List<PlaAmupstransport>) map.get("list");
		this.accept = (String) map.get("senders");
		corpid = "";
		return "list";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		plaAmupstransport = plaAmupstransportService.load(id);
		plaAmupstransportService.delete(plaAmupstransport);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	public String edit() throws Exception {
		if (null != id) {
			plaAmupstransport = plaAmupstransportService.load(id);
			isNew = false;
		} else {
			plaAmupstransport = new PlaAmupstransport();
			isNew = true;
		}
		PlaAmupstransport markSch = new PlaAmupstransport();
		mstList = plaAmupstransportService.find(markSch);

		return "input";
	}

	/**
	 * 更新状态（AJAX)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String amendStatus() throws Exception {
		plaAmupstransportService.update(plaAmupstransport);
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
			plaAmupstransport.setCreatedtime(new Date());
			plaAmupstransportService.save(plaAmupstransport);
		} else {
			// 修改记录
			plaAmupstransportService.update(plaAmupstransport);
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

	public PlaAmupstransport getPlaAmupstransport() {
		return plaAmupstransport;
	}

	public void setPlaAmupstransport(PlaAmupstransport plaAmupstransport) {
		this.plaAmupstransport = plaAmupstransport;
	}

	public List<PlaAmupstransport> getMstList() {
		return mstList;
	}

	public void setMstList(List<PlaAmupstransport> mstList) {
		this.mstList = mstList;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public List<DicAreacorp> getAreacorpList() {
		return areacorpList;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_AMUPS");
	}

}
