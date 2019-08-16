/**
 * 
 */
package com.tlys.equ.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.model.EquCarHpinfo;
import com.tlys.equ.model.EquCarHpreginfo;
import com.tlys.equ.model.EquCarNpinfo;
import com.tlys.equ.service.EquCarCertificateService;
import com.tlys.equ.service.EquCarHpinfoService;
import com.tlys.equ.service.EquCarHpreginfoService;
import com.tlys.equ.service.EquCarNpinfoService;
import com.tlys.equ.service.EquCarRentService;
import com.tlys.equ.service.EquCarService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquCarAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private EquCar equCar = new EquCar();
	// 常压技术性能表
	private EquCarNpinfo equCarNpinfo = new EquCarNpinfo();
	// 高压技术性能表
	private EquCarHpinfo equCarHpinfo = new EquCarHpinfo();
	// 高压证书表
	private EquCarHpreginfo equCarHpreginfo = new EquCarHpreginfo();
	private List<EquCar> equCars;
	private Map<String, List> corptrains;
	private String id;
	private String carmakerid;
	private String goodsid;
	private String tankmateid;
	private String rentid;
	private String className;
	private String code;
	private String define;
	private String carno;
	private String isexpire;
	private String cartypeid;
	private String carkindid;
	private String corpid;
	private String carnos;
	private List<Object> checks;
	// 导出条件
	private String schobjkey;

	// 所有子集
	private Map<String, List<Object>> submap;

	private String expiredate;
	private boolean isNew;

	@Autowired
	EquCarService equCarService;
	@Autowired
	EquCarHpinfoService equCarHpinfoService;
	@Autowired
	EquCarHpreginfoService equCarHpreginfoService;
	@Autowired
	EquCarNpinfoService equCarNpinfoService;
	@Autowired
	EquCarCertificateService equCarCertificateService;
	@Autowired
	EquCarRentService equCarRentService;

	@Autowired
	DicMap dicMap;

	List carList;

	/**
	 * 统计数据
	 */
	private Map statMap;

	/**
	 * 数据字典车辆相关-自备车号查询
	 */
	public String suborgs() {
		equCar.setCarmakerid(carmakerid);
		equCar.setGoodsid(goodsid);
		equCar.setTankmateid(tankmateid);
		equCar.setRentid(rentid);
		equCar.setCartypeid(cartypeid);
		corptrains = equCarService.findByColumn(equCar);
		return "suborgs";
	}

	public String list() throws Exception {
		String pageUrl = "/equ/equ-car!list.action";
		if (null == equCar) {
			equCar = new EquCar();
		}
		String isexpire = equCar.getIsexpire();
		if (isexpire == null || "".equals(isexpire)) {
			equCar.setIsexpire("0");
		}
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			String schObjKey = "equCar_" + new Date().getTime();
			pageCtr.setSchObjKey(schObjKey);
			setSessionAttr(schObjKey, equCar);
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				equCar = (EquCar) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(16);
		equCarService.findPageCtr(equCar, pageCtr);
		return "list";
	}

	public String edit() throws Exception {
		this.submap = equCarService.findSubs();
		if (null != id) {
			this.equCar = equCarService.load(id);
			if (equCar != null)
				// this.expiredate =
				// equCar.getExpiredate().toString().substring(0, 10);
				this.expiredate = equCar.getExpiredateStr();
			this.equCarNpinfo = equCarNpinfoService.load(id);

			this.equCarHpinfo = equCarHpinfoService.load(id);
			this.equCarHpreginfo = equCarHpreginfoService.load(id);
			submap.put("carno", equCarService.find("from EquCarRent as t where t.carno='" + id + "'"));
			submap.put("trainno", equCarService.find("from EquCarCertificate as t where t.trainno='" + id + "'"));
			isNew = false;
		} else {
			equCar.setIsstandartrain("1");
			equCar.setIsdangerous("0");
			equCar.setIsrent("0");
			equCar.setIsexpire("0");
			isNew = true;
		}
		return "input";
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		equCar = (EquCar) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		equCarService.expExcel(equCar, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public String save() throws Exception {
		if (null == equCar) {
			throw new Exception("数据未接收到！");
		} else {
			if (null != expiredate && !"".equals(expiredate)) {
				equCar.setExpiredate(EquCar.parseFormat.parse(expiredate));
			} else {
				equCar.setExpiredate(null);
			}
			String madedate = equCar.getMadedate();
			String inroaddate = equCar.getInroaddate();
			if (null != madedate && !"".equals(madedate)) {
				equCar.setMadedate(EquCar.shortFormat.format(EquCar.parseFormat.parse(madedate)));
			}
			if (null != inroaddate && !"".equals(inroaddate)) {
				equCar.setInroaddate(EquCar.shortFormat.format(EquCar.parseFormat.parse(inroaddate)));
			}

			equCarService.save(equCar, isNew);

			carno = equCar.getCarno();
			if ("HP".equals(equCar.getPressuretype())) {
				EquCarHpinfo Hpinfo = equCarHpinfoService.load(carno);
				equCarHpinfo.setCarno(carno);
				equCarHpinfo.setTankmateid(equCar.getTankmateid());
				if (null == Hpinfo) {
					equCarHpinfoService.save(equCarHpinfo, true);
				} else {
					Hpinfo.setBackpressure(equCarHpinfo.getBackpressure());
					Hpinfo.setBrakeequipmentno(equCarHpinfo.getBrakeequipmentno());
					Hpinfo.setDesignpressure(equCarHpinfo.getDesignpressure());
					Hpinfo.setDesigntemperature(equCarHpinfo.getDesigntemperature());
					Hpinfo.setFopenpressure(equCarHpinfo.getFopenpressure());
					Hpinfo.setFullloadweight(equCarHpinfo.getFullloadweight());
					Hpinfo.setGasvalveno(equCarHpinfo.getGasvalveno());
					Hpinfo.setHydrostatictest(equCarHpinfo.getHydrostatictest());
					Hpinfo.setLiquidno(equCarHpinfo.getLiquidno());
					Hpinfo.setLiquidvalveno(equCarHpinfo.getLiquidvalveno());
					Hpinfo.setLiquidtype(equCarHpinfo.getLiquidtype());
					Hpinfo.setMagnetType(equCarHpinfo.getMagnetType());
					Hpinfo.setNominaldiameter(equCarHpinfo.getNominaldiameter());
					Hpinfo.setNominalpressure(equCarHpinfo.getNominalpressure());
					Hpinfo.setOpenpressure(equCarHpinfo.getOpenpressure());
					Hpinfo.setRedirectno(equCarHpinfo.getRedirectno());
					Hpinfo.setRustydegree(equCarHpinfo.getRustydegree());
					Hpinfo.setSafetyvalveno(equCarHpinfo.getSafetyvalveno());
					Hpinfo.setSafetyvalvetype(equCarHpinfo.getSafetyvalvetype());
					Hpinfo.setTankmateid(equCarHpinfo.getTankmateid());
					Hpinfo.setTanksize(equCarHpinfo.getTanksize());
					Hpinfo.setTankweight(equCarHpinfo.getTankweight());
					Hpinfo.setThermotype(equCarHpinfo.getThermotype());
					Hpinfo.setTightnesstest(equCarHpinfo.getTightnesstest());
					Hpinfo.setValidHeight(equCarHpinfo.getValidHeight());
					equCarHpinfoService.save(Hpinfo, false);
				}
				EquCarHpreginfo Hpreginfo = equCarHpreginfoService.load(carno);
				equCarHpreginfo.setCarno(carno);
				if (null == Hpreginfo) {
					String usedate = equCarHpreginfo.getUsedate();
					equCarHpreginfo.setUsedate(null != usedate && !"".equals(usedate) ? EquCar.shortFormat.format(EquCar.parseFormat
							.parse(usedate)) : "");
					equCarHpreginfoService.save(equCarHpreginfo, true);
				} else {
					Hpreginfo.setCarchassisno(equCarHpreginfo.getCarchassisno());
					Hpreginfo.setCartankno(equCarHpreginfo.getCartankno());
					Hpreginfo.setContainername(equCarHpreginfo.getContainername());
					Hpreginfo.setInternalno(equCarHpreginfo.getInternalno());
					Hpreginfo.setMakecorp(equCarHpreginfo.getMakecorp());
					Hpreginfo.setMakelicense(equCarHpreginfo.getMakelicense());
					Hpreginfo.setRegistercode(equCarHpreginfo.getRegistercode());
					Hpreginfo.setRegisterdepartment(equCarHpreginfo.getRegisterdepartment());
					Hpreginfo.setRemarks(equCarHpreginfo.getRemarks());
					Hpreginfo.setUsedate(equCarHpreginfo.getUsedate().replaceAll("-", ""));
					Hpreginfo.setUseregisterno(equCarHpreginfo.getUseregisterno());
					equCarHpreginfoService.save(Hpreginfo, false);
				}
			} else if ("NP".equals(equCar.getPressuretype())) {
				if (logger.isDebugEnabled()) {
					logger.debug("Npinfo.carno : " + carno);
					logger.debug("equCarNpinfo.carno : " + equCarNpinfo.getCarno());
				}
				EquCarNpinfo Npinfo = equCarNpinfoService.load(carno);
				equCarNpinfo.setCarno(carno);
				equCarNpinfo.setTankmateid(equCar.getTankmateid());

				if (null == Npinfo) {
					equCarNpinfoService.save(equCarNpinfo, true);
				} else {
					Npinfo.setAixweight(equCarNpinfo.getAixweight());
					Npinfo.setAxisdist(equCarNpinfo.getAxisdist());
					Npinfo.setBrakeequipmentno(equCarNpinfo.getBrakeequipmentno());
					Npinfo.setCenterlinehight(equCarNpinfo.getCenterlinehight());
					Npinfo.setEcoghigh(equCarNpinfo.getEcoghigh());
					Npinfo.setEcoghight(equCarNpinfo.getEcoghight());
					Npinfo.setFixeddist(equCarNpinfo.getFixeddist());
					Npinfo.setMaxhight(equCarNpinfo.getMaxhight());
					Npinfo.setMaxloadweight(equCarNpinfo.getMaxloadweight());
					Npinfo.setMaxwidth(equCarNpinfo.getMaxwidth());
					Npinfo.setMinloadweight(equCarNpinfo.getMinloadweight());
					Npinfo.setRedirectno(equCarNpinfo.getRedirectno());
					Npinfo.setSemidiameter(equCarNpinfo.getSemidiameter());
					Npinfo.setTankmateid(equCarNpinfo.getTankmateid());
					Npinfo.setThickness(equCarNpinfo.getThickness());
					Npinfo.setTotalcapacity(equCarNpinfo.getTotalcapacity());
					Npinfo.setValidcapacity(equCarNpinfo.getValidcapacity());
					Npinfo.setWheelidameter(equCarNpinfo.getWheelidameter());
					Npinfo.setWorkpressure(equCarNpinfo.getWorkpressure());
					Npinfo.setWorktemperature(equCarNpinfo.getWorktemperature());
					equCarNpinfoService.update(Npinfo);// .save(equCarNpinfo,
					// false);
				}
			}
			// 以下用于msg页面显示的设置

			msg = new Msg(Msg.SUCCESS, "添加/编辑成功！");
		}
		return MSG;
	}

	/**
	 * 查询面板
	 * 
	 * @return
	 * @throws Exception
	 */
	public String left() throws Exception {
		this.submap = equCarService.findSubs();
		List list = equCarService.query("select a.carno,b.shortname from EquCarRent a,DicSinocorp b where a.rentcorpid = b.corpid");
		submap.put("carno", list);
		return "left";
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("不能确定要删除的对象！");
		} else {
			String[] ids = id.split(",");
			for (String lid : ids) {
				equCarService.delete(lid.trim());
				equCarHpinfoService.delete(lid.trim());
				equCarHpreginfoService.delete(lid.trim());
				equCarNpinfoService.delete(lid.trim());
				equCarCertificateService.deleteByCarno(lid.trim());
				equCarRentService.deleteByCarno(lid.trim());
			}
			msg = new Msg(Msg.SUCCESS, "删除对象成功！");
		}
		return MSG;
	}

	/**
	 * 新增编辑页面过滤查询过轨站
	 * 
	 * @return
	 * @throws Exception
	 */
	public String check() throws Exception {
		List<String[]> list = equCarService.findShrink(corpid);
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			Object[] ss = list.get(i);
			str += ss[0] + ":" + ss[1] + ",";
		}
		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		msg = new Msg(Msg.SUCCESS, str);
		return MSG;
	}

	/**
	 * 车辆检修模块更新车辆是否报废
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		equCar = equCarService.load(equCar.getCarno());
		if (null != equCar) {
			String isexpirepire = equCar.getIsexpire();
			if (logger.isDebugEnabled()) {
				logger.debug("isexpirepire : " + isexpire);
			}
			equCar.setIsexpire(isexpire);
			equCarService.update(equCar);
			msg = new Msg(Msg.SUCCESS, "操作成功");
		} else {
			msg = new Msg(Msg.FAILURE, "数据缺失!");
		}
		return MSG;
	}

	// =================================

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getCarmakerid() {
		return carmakerid;
	}

	public void setCarmakerid(String carmakerid) {
		this.carmakerid = carmakerid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getTankmateid() {
		return tankmateid;
	}

	public void setTankmateid(String tankmateid) {
		this.tankmateid = tankmateid;
	}

	public String getRentid() {
		return rentid;
	}

	public void setRentid(String rentid) {
		this.rentid = rentid;
	}

	public Map<String, List> getCorptrains() {
		return corptrains;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDefine() {
		return define;
	}

	public void setDefine(String define) {
		this.define = define;
	}

	public List<Object> getChecks() {
		return checks;
	}

	public String getCartypeid() {
		return cartypeid;
	}

	public void setCartypeid(String cartypeid) {
		this.cartypeid = cartypeid;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public String getCarkindid() {
		return carkindid;
	}

	public void setCarkindid(String carkindid) {
		this.carkindid = carkindid;
	}

	public String getIsexpire() {
		return isexpire;
	}

	public void setIsexpire(String isexpire) {
		this.isexpire = isexpire;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCarnos() {
		return carnos;
	}

	public void setCarnos(String carnos) {
		this.carnos = carnos;
	}

	public Map<String, List<Object>> getSubmap() {
		return submap;
	}

	public void setSubmap(Map<String, List<Object>> submap) {
		this.submap = submap;
	}

	public EquCarNpinfo getEquCarNpinfo() {
		return equCarNpinfo;
	}

	public void setEquCarNpinfo(EquCarNpinfo equCarNpinfo) {
		this.equCarNpinfo = equCarNpinfo;
	}

	public EquCarHpinfo getEquCarHpinfo() {
		return equCarHpinfo;
	}

	public void setEquCarHpinfo(EquCarHpinfo equCarHpinfo) {
		this.equCarHpinfo = equCarHpinfo;
	}

	public EquCarHpreginfo getEquCarHpreginfo() {
		return equCarHpreginfo;
	}

	public void setEquCarHpreginfo(EquCarHpreginfo equCarHpreginfo) {
		this.equCarHpreginfo = equCarHpreginfo;
	}

	public void prepare() throws Exception {
		initOpraMap("EQU_CAR");
	}

	/**
	 * @return the equCars
	 */
	public List<EquCar> getEquCars() {
		return equCars;
	}

	/**
	 * @param equCars
	 *            the equCars to set
	 */
	public void setEquCars(List<EquCar> equCars) {
		this.equCars = equCars;
	}

	/**
	 * @return the equCar
	 */
	public EquCar getEquCar() {
		return equCar;
	}

	/**
	 * @param equCar
	 *            the equCar to set
	 */
	public void setEquCar(EquCar equCar) {
		this.equCar = equCar;
	}

	public String getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}

	/**
	 * @return the carList
	 */
	public List getCarList() {
		return carList;
	}

	/**
	 * @param carList
	 *            the carList to set
	 */
	public void setCarList(List carList) {
		this.carList = carList;
	}

	public Map getStatMap() {
		return statMap;
	}

	public void setStatMap(Map statMap) {
		this.statMap = statMap;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

}
