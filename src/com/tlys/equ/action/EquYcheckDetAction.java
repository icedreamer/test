package com.tlys.equ.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicRwbureau;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicGoodscategoryService;
import com.tlys.dic.service.DicRwbureauService;
import com.tlys.dic.service.DicRwdepartmentService;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.model.EquRepCars;
import com.tlys.equ.model.EquYcheck;
import com.tlys.equ.model.EquYcheckBureau;
import com.tlys.equ.model.EquYcheckDet;
import com.tlys.equ.model.EquYcheckStation;
import com.tlys.equ.service.EquCarCertificateService;
import com.tlys.equ.service.EquCarService;
import com.tlys.equ.service.EquYcheckService;
import com.tlys.spe.model.SpePapers;
import com.tlys.spe.model.SpePaperstype;
import com.tlys.spe.service.SpePapersService;

/**
 * 年检明细
 * 
 * @author ccsong
 * 
 */
@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquYcheckDetAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9065649339802874339L;

	private EquYcheckDet equYcheckDet;
	private EquYcheck equYcheck;
	// 根据年检编号查询路局列表
	private Map<String, List<EquYcheckBureau>> equYcheckBureauListByGoodsidMap;
	private Map<String, List<String>> loadstationsByGoodsIdAndBureauidMap;
	private Map<String, List<String>> unloadstationsByGoodsIdAndBureauidMap;

	private String[] carnos;

	private List<EquYcheckDet> equYcheckDetList;
	// 铁路品类(充装介质)
	private Map<String, String> goodsNameMap;
	private Map<String, DicCarkind> dicCarkindMap;

	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicCartype> dicCartypeMap;
	private Map<String, List<String>> cartypesByGoodsIdMap;
	private Map<String, List<String>> carsByCartypeIdMap;
	private Map<String, List<String>> carsByCarkindIdMap;
	// 铁路品名 (充装介质)
	private List<DicGoodscategory> goodscategories;
	private Map<String, EquYcheckDet> equYcheckDetMap;

	private List<String> goodsIds;
	private String goodsid;
	// 根据品名查询支持的车号列表
	private Map<String, List<String>> carnosByGoodsIdMap;

	private List<EquCar> equCarList;

	private List<DicRwbureau> dicRwbureauList;

	private String[] bureauids;

	private Map<String, String> rwbureauMap;

	private Map<String, String> dicRwdepartmentMap;

	private String[] loadstationids;
	private String[] unloadstationids;
	private Long[] ids;

	@Autowired
	EquYcheckService equYcheckService;
	@Autowired
	EquCarService equCarService;
	@Autowired
	DicMap dicMap;
	@Autowired
	EquCarCertificateService equCarCertificateService;
	@Autowired
	DicRwbureauService dicRwbureauService;
	@Autowired
	DicGoodscategoryService dicGoodscategoryService;
	@Autowired
	DicRwdepartmentService dicRwdepartmentService;
	@Autowired
	SpePapersService spePapersService;

	/**
	 * 明细表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/equ/equ-ycheck-det!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr<EquRepCars>();
			if (null != equYcheckDet) {
				String schObjKey = "equ_ycheck_det_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equYcheckDet);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				equYcheckDet = (EquYcheckDet) getSessionAttr(pageCtr.getSchObjKey());
			}
		}

		pageCtr.setPageUrl(pageUrl);
		equYcheckService.findEquYcheckDet(equYcheckDet, pageCtr);

		List<EquYcheckDet> detList = pageCtr.getRecords();
		List<String> goodsIdList = null;
		if (null != detList && !detList.isEmpty()) {
			goodsIdList = new ArrayList<String>();
			for (EquYcheckDet equYcheckDet : detList) {
				if (!goodsIdList.contains(equYcheckDet.getGoodsid())) {
					goodsIdList.add(equYcheckDet.getGoodsid());
				}
			}
		}
		String yinspectionno = equYcheckDet.getYinspectionno();
		List<EquYcheckBureau> equYcheckBureauList = equYcheckService.findEquYcheckBureau(yinspectionno, goodsIdList);
		Object[] arry = equYcheckService.buildBureau(equYcheckBureauList);
		equYcheckBureauListByGoodsidMap = (Map<String, List<EquYcheckBureau>>) arry[0];
		loadstationsByGoodsIdAndBureauidMap = (Map<String, List<String>>) arry[1];
		unloadstationsByGoodsIdAndBureauidMap = (Map<String, List<String>>) arry[2];
		if (null != yinspectionno && !"".equals(yinspectionno)) {
			equYcheck = equYcheckService.loadYcheck(yinspectionno);
		}
		goodsNameMap = dicMap.getGoodscategoryMap();
		dicCarkindMap = dicMap.getCarkindMap();
		dicRwdepartmentMap = dicMap.getRwdepartmentMap();
		return "list";
	}

	/**
	 * 将选择好的车号和充装介质，以及车型车种数据保存(或更新)到明细表中，同时将这些记录查询出来，显示在表格中
	 * 
	 * @return
	 * @throws Exception
	 */

	public String storage() throws Exception {
		String corpid = equYcheck.getCorpid();
		String year = equYcheck.getYear();
		String inspectiontype = equYcheck.getInspectiontype();
		String yinspectionno = CommUtils.getString(corpid, "-", year, "-", inspectiontype);
		EquYcheck localEquYcheck = equYcheckService.loadYcheck(yinspectionno);
		localEquYcheck.setCarnumber(equYcheck.getCarnumber());
		equYcheckService.update(localEquYcheck);
		// 企业自备车列表
		List<EquCar> equCarList = equCarService.findEquCarByCorpid(corpid);
		// 页面carnos 转换为carnoList队列
		List<String> carnoList = null;
		if (null != carnos && carnos.length > 0) {
			carnoList = Arrays.asList(carnos);
		}
		List<EquCar> list = null;
		if (null != equCarList && !equCarList.isEmpty()) {
			list = new ArrayList<EquCar>();
			for (EquCar equCar : equCarList) {
				if (null == carnoList || carnoList.isEmpty()) {
					list.add(equCar);
				} else {
					if (!carnoList.contains(equCar.getCarno())) {
						list.add(equCar);
					}
				}
			}
		}
		if (null == equYcheckDet) {
			equYcheckDet = new EquYcheckDet();
		}
		equYcheckDet.setCorpid(corpid);
		equYcheckDet.setYear(year);
		equYcheckDet.setInspectiontype(inspectiontype);
		List<EquYcheckDet> equYcheckDetList = equYcheckService.findEquYcheckDet(equYcheckDet);

		equYcheckDet.setYear(String.valueOf(Integer.parseInt(year) - 1));
		List<EquYcheckDet> lastYearEquYcheckDets = equYcheckService.findEquYcheckDet(equYcheckDet);
		Map<String, String> lastYearStationDepotMap = new HashMap<String, String>();
		if (null != lastYearEquYcheckDets && !lastYearEquYcheckDets.isEmpty()) {
			for (EquYcheckDet equYcheckDet : lastYearEquYcheckDets) {
				lastYearStationDepotMap.put(equYcheckDet.getCarno(), equYcheckDet.getStationdepot());
			}
		}
		Map<String, EquYcheckDet> equYcheckDetMap = new HashMap<String, EquYcheckDet>();
		List<String> needDetList = new ArrayList<String>();
		if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
			for (EquYcheckDet equYcheckDet : equYcheckDetList) {
				if (null != carnoList && carnoList.contains(equYcheckDet.getCarno())) {
					needDetList.add(equYcheckDet.getCarno());
				}
				equYcheckDetMap.put(equYcheckDet.getCarno(), equYcheckDet);
			}
		}
		// List<EquCarCertificate> equCarCertificates =
		// equCarCertificateService.findEquCarCertificate(corpid);
		// Map<String, EquCarCertificate> equCarCertificateMap = new
		// HashMap<String, EquCarCertificate>();
		// if (null != equCarCertificates && !equCarCertificates.isEmpty()) {
		// for (EquCarCertificate equCarCertificate : equCarCertificates) {
		// String carno = equCarCertificate.getTrainno().trim();
		// equCarCertificateMap.put(carno, equCarCertificate);
		// }
		// }
		SpePapers spePapers = new SpePapers();
		SpePaperstype spePaperstype = new SpePaperstype();
		// 安全技术合格证
		spePaperstype.setPtypeid("02");
		spePapers.setSpePaperstype(spePaperstype);
		spePapers.setCorpid(corpid);
		List<SpePapers> safeSpePapers = spePapersService.findSpePapers(spePapers);
		Map<String, String> safeNoMap = new HashMap<String, String>();
		if (null != safeSpePapers && !safeSpePapers.isEmpty()) {
			for (SpePapers papers : safeSpePapers) {
				safeNoMap.put(papers.getCarno(), papers.getPapersname());
			}
		}
		// 过轨运输许可证
		spePaperstype.setPtypeid("04");
		spePapers.setSpePaperstype(spePaperstype);
		List<SpePapers> permitSpePaperses = spePapersService.findSpePapers(spePapers);
		Map<String, String> permitNoMap = new HashMap<String, String>();
		if (null != permitSpePaperses && !permitSpePaperses.isEmpty()) {
			for (SpePapers papers : permitSpePaperses) {
				permitNoMap.put(papers.getCarno(), papers.getPapersname());
			}
		}
		if (null != list && !list.isEmpty()) {
			for (EquCar equCar : list) {
				String carno = equCar.getCarno();
				EquYcheckDet equYcheckDet = equYcheckDetMap.get(carno);
				if (null == equYcheckDet) {
					equYcheckDet = new EquYcheckDet();
					equYcheckDet.setCarkindid(equCar.getCarkindid());
					equYcheckDet.setCarno(carno);
					equYcheckDet.setCartypeid(equCar.getCartypeid());
					equYcheckDet.setCorpid(corpid);
					equYcheckDet.setGoodsid(equCar.getGoodsid());
					equYcheckDet.setYear(year);
					equYcheckDet.setYinspectionno(yinspectionno);
					equYcheckDet.setInspectiontype(inspectiontype);
					// EquCarCertificate equCarCertificate =
					// equCarCertificateMap.get(carno);
					// if (null != equCarCertificate) {
					equYcheckDet.setSaftcertcode(safeNoMap.get(carno));
					equYcheckDet.setTrancertcode(permitNoMap.get(carno));
					equYcheckDet.setStationdepot(lastYearStationDepotMap.get(carno));
					// }
					equYcheckService.save(equYcheckDet);
				}
			}
		}
		equYcheckService.deleteEquYcheckDet(needDetList, year, corpid);
		msg = new Msg(Msg.SUCCESS, "1");
		return MSG;
	}

	public String bureaus() throws Exception {
		String yinspectionno = equYcheck.getYinspectionno();
		equYcheck = equYcheckService.loadYcheck(equYcheck.getYinspectionno());
		if (null == equYcheckDet) {
			equYcheckDet = new EquYcheckDet();
		}
		equYcheckDet.setYinspectionno(yinspectionno);
		equYcheckDetList = equYcheckService.findEquYcheckDet(equYcheckDet);
		equCarList = equCarService.findEquCarByCorpid(equYcheck.getCorpid());
		List<String> goodsIdList = null;
		if (null != equCarList && !equCarList.isEmpty()) {
			goodsIdList = new ArrayList<String>();
			for (EquCar equCar : equCarList) {
				String goodsid = equCar.getGoodsid();
				if (!goodsIdList.contains(goodsid)) {
					goodsIdList.add(goodsid);
				}
			}
		}
		goodscategories = dicGoodscategoryService.findDicGoodscategory(goodsIdList);
		if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
			carsByCarkindIdMap = new HashMap<String, List<String>>();
			for (EquYcheckDet equYcheckDet : equYcheckDetList) {
				String carno = equYcheckDet.getCarno();
				String carkindid = equYcheckDet.getCarkindid();
				List<String> carList = carsByCarkindIdMap.get(carkindid);
				if (null == carList) {
					carList = new ArrayList<String>();
				}
				carList.add(carno);
				carsByCarkindIdMap.put(carkindid, carList);
			}
		}

		rwbureauMap = dicMap.getRwbureauMap();
		dicRwdepartmentMap = dicMap.getRwdepartmentMap();
		dicCarkindMap = dicMap.getCarkindMap();
		List<EquYcheckBureau> equYcheckBureauList = equYcheckService.findEquYcheckBureau(yinspectionno);
		if (null != equYcheckBureauList && !equYcheckBureauList.isEmpty()) {
			equYcheckBureauListByGoodsidMap = new HashMap<String, List<EquYcheckBureau>>();
			for (EquYcheckBureau equYcheckBureau : equYcheckBureauList) {
				String goodsid = equYcheckBureau.getGoodsid();
				List<EquYcheckBureau> list = equYcheckBureauListByGoodsidMap.get(goodsid);
				if (null == list) {
					list = new ArrayList<EquYcheckBureau>();
				}
				list.add(equYcheckBureau);
				equYcheckBureauListByGoodsidMap.put(goodsid, list);
			}
		}

		List<EquYcheckStation> stationList = equYcheckService.findEquYcheckStation(yinspectionno);
		if (null != stationList && !stationList.isEmpty()) {
			loadstationsByGoodsIdAndBureauidMap = new HashMap<String, List<String>>();
			unloadstationsByGoodsIdAndBureauidMap = new HashMap<String, List<String>>();
			for (EquYcheckStation equYcheckStation : stationList) {
				String datatype = equYcheckStation.getDatatype();
				String yinspectionbureauid = equYcheckStation.getYinspectionbureauid();
				String[] array = yinspectionbureauid.split("-");
				String goodsidAndbureauid = CommUtils.getString(array[3], "-", array[4]);
				List<String> loadstations = loadstationsByGoodsIdAndBureauidMap.get(goodsidAndbureauid);
				List<String> unloadstations = unloadstationsByGoodsIdAndBureauidMap.get(goodsidAndbureauid);
				if (null == loadstations) {
					loadstations = new ArrayList<String>();
				}
				if (null == unloadstations) {
					unloadstations = new ArrayList<String>();
				}
				if (datatype.equals("L")) {
					loadstations.add(equYcheckStation.getStationid());
				} else if (datatype.equals("U")) {
					unloadstations.add(equYcheckStation.getStationid());
				}
				loadstationsByGoodsIdAndBureauidMap.put(goodsidAndbureauid, loadstations);
				unloadstationsByGoodsIdAndBureauidMap.put(goodsidAndbureauid, unloadstations);
			}
		}

		return "bureaus";
	}

	/**
	 * 将storage()方法中刚刚保存的数据显示出来 和打印页面格式相同
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detaillist() throws Exception {
		String yinspectionno = equYcheck.getYinspectionno();
		String corpid = yinspectionno.split("-")[0];
		// 显示车号信息
		equCarList = equCarService.findEquCarByCorpid(corpid);
		List<String> goodsIdList = null;
		if (null != equCarList && !equCarList.isEmpty()) {
			goodsIdList = new ArrayList<String>();
			cartypesByGoodsIdMap = new HashMap<String, List<String>>();
			carsByCartypeIdMap = new HashMap<String, List<String>>();
			carsByCarkindIdMap = new HashMap<String, List<String>>();
			for (EquCar equCar : equCarList) {
				String goodsid = equCar.getGoodsid();
				String cartypeid = equCar.getCartypeid();
				String carno = equCar.getCarno();
				if (!goodsIdList.contains(goodsid)) {
					goodsIdList.add(goodsid);
				}
				List<String> cartypeList = cartypesByGoodsIdMap.get(goodsid);
				if (null == cartypeList) {
					cartypeList = new ArrayList<String>();
				}
				if (!cartypeList.contains(cartypeid)) {
					cartypeList.add(cartypeid);
				}
				cartypesByGoodsIdMap.put(goodsid, cartypeList);
				String key = CommUtils.getString(goodsid, "_", cartypeid);
				List<String> carnoList = carsByCartypeIdMap.get(key);
				if (null == carnoList) {
					carnoList = new ArrayList<String>();
				}
				if (!carnoList.contains(carno)) {
					carnoList.add(carno);
				}
				carsByCartypeIdMap.put(key, carnoList);

			}
			goodscategories = dicGoodscategoryService.findDicGoodscategory(goodsIdList);
			List<EquYcheckDet> equYcheckDetList = equYcheckService.findEquYcheckDet(equYcheckDet);
			equYcheckDetMap = new HashMap<String, EquYcheckDet>();
			if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
				for (EquYcheckDet equYcheckDet : equYcheckDetList) {
					equYcheckDetMap.put(equYcheckDet.getCarno(), equYcheckDet);
				}
			}
			for (EquCar equCar : equCarList) {
				if (equYcheckDetMap != null && equYcheckDetMap.containsKey(equCar.getCarno())) {
					String carkindid = equCar.getCarkindid();
					List<String> carlist = carsByCarkindIdMap.get(carkindid);
					if (null == carlist) {
						carlist = new ArrayList<String>();
					}
					carlist.add(equCar.getCarno());
					carsByCarkindIdMap.put(carkindid, carlist);
				}
			}
		}
		dicCarkindMap = dicMap.getCarkindMap();
		equYcheck = equYcheckService.loadYcheck(yinspectionno);
		dicRwbureauList = dicRwbureauService.find(new DicRwbureau());
		return "detaillist";
	}

	/**
	 * 导出前 预览正页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String positive() throws Exception {

		return "positive";
	}

	/**
	 * 导出前 预览附页
	 * 
	 * @return
	 * @throws Exception
	 */
	public String vice() throws Exception {

		return "vice";
	}

	/**
	 * 导出前预览
	 * 
	 * @return
	 * @throws Exception
	 */
	public String preview() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("equYcheckDet : " + equYcheckDet);
		}
		String corpid = equYcheckDet.getCorpid();
		String year = equYcheckDet.getYear();
		String inspectiontype = equYcheckDet.getInspectiontype();
		// equYcheckDet.setPageSize(14);
		equYcheckDetList = equYcheckService.findEquYcheckDet(equYcheckDet);
		String yinspectionno = CommUtils.getString(corpid, "-", year, "-", inspectiontype);
		// 处理运行范围
		List<EquYcheckBureau> equYcheckBureaus = equYcheckService.findEquYcheckBureau(yinspectionno);
		Object[] arry = equYcheckService.buildBureau(equYcheckBureaus);
		equYcheckBureauListByGoodsidMap = (Map<String, List<EquYcheckBureau>>) arry[0];
		loadstationsByGoodsIdAndBureauidMap = (Map<String, List<String>>) arry[1];
		unloadstationsByGoodsIdAndBureauidMap = (Map<String, List<String>>) arry[2];
		// 铁路品名（充装介质)
		goodsNameMap = dicMap.getGoodscategoryMap();
		// 主表记录
		equYcheck = equYcheckService.loadYcheck(yinspectionno);
		if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
			goodsIds = new ArrayList<String>();
			carnosByGoodsIdMap = new HashMap<String, List<String>>();
			carsByCarkindIdMap = new HashMap<String, List<String>>();
			for (int i = 0; i < equYcheckDetList.size(); i++) {
				EquYcheckDet equYcheckDet = equYcheckDetList.get(i);
				String goodsid = equYcheckDet.getGoodsid();
				String carno = equYcheckDet.getCarno().trim();
				List<String> carnos = carnosByGoodsIdMap.get(goodsid);
				if (null == carnos) {
					carnos = new ArrayList<String>();
				}
				if (!carnos.contains(carno)) {
					carnos.add(carno);
				}
				carnosByGoodsIdMap.put(goodsid, carnos);
				if (!goodsIds.contains(goodsid)) {
					goodsIds.add(goodsid);
				}
				String carkindid = equYcheckDet.getCarkindid();
				List<String> carnoList = carsByCarkindIdMap.get(carkindid);
				if (null == carnoList) {
					carnoList = new ArrayList<String>();
				}
				carnoList.add(carno);
				carsByCarkindIdMap.put(carkindid, carnoList);
			}
		}
		dicCarkindMap = dicMap.getCarkindMap();
		dicRwbureauList = dicRwbureauService.find(new DicRwbureau());
		dicRwdepartmentMap = dicMap.getRwdepartmentMap();
		return "preview";
	}

	/**
	 * 删除明细表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		equYcheckService.delete(ids);
		String yinspectionno = equYcheck.getYinspectionno();
		equYcheck = equYcheckService.loadYcheck(yinspectionno);
		if (null == equYcheckDet) {
			equYcheckDet = new EquYcheckDet();
		}
		equYcheckDet.setYinspectionno(yinspectionno);
		int count = equYcheckService.getEquYcheckDetCount(equYcheckDet);
		equYcheck.setCarnumber(count);
		equYcheckService.update(equYcheck);
		msg = new Msg(Msg.SUCCESS, "操作成功");
		return MSG;
	}

	public String deleteBureau() throws Exception {
		// List<String> yinspectionrelatednos = new ArrayList<String>();
		// yinspectionrelatednos.add(equYcheckDet.getYinspectionrelatedno() + "
		// ");
		// equYcheckService.deleteEquYcheckBureau(yinspectionrelatednos);

		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	/**
	 * 点击车号时显示的页面，品名，车数，证号类型 ，证号(弃用)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String shownos() throws Exception {
		equYcheckDetList = equYcheckService.findEquYcheckDet(equYcheckDet);
		if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
			goodsIds = new ArrayList<String>();
			carnosByGoodsIdMap = new HashMap<String, List<String>>();
			for (EquYcheckDet equYcheckDet : this.equYcheckDetList) {
				String goodsid = equYcheckDet.getGoodsid();
				String carno = equYcheckDet.getCarno().trim();
				List<String> carnos = carnosByGoodsIdMap.get(goodsid);
				if (null == carnos) {
					carnos = new ArrayList<String>();
				}
				carnos.add(carno);
				carnosByGoodsIdMap.put(goodsid, carnos);
				if (!goodsIds.contains(goodsid)) {
					goodsIds.add(goodsid);
				}
			}
		}
		// 铁路品名（充装介质)
		goodsNameMap = dicMap.getGoodscategoryMap();
		return "shownos";
	}

	/**
	 * 更新明细数据对应的证号(批量更新页面)
	 * 
	 * @return
	 * @throws Exception
	 */
	// public String updatecardnum() throws Exception {
	// String type = equYcheckDet.getType();
	// String cardnum = equYcheckDet.getCardnum();
	// if (logger.isDebugEnabled()) {
	// logger.debug("yinspectionno : " + equYcheckDet.getYinspectionno());
	// logger.debug("goodsid : " + equYcheckDet.getGoodsid());
	// logger.debug("type : " + type);
	// logger.debug("cardnum : " + cardnum);
	// }
	// List<EquYcheckDet> equYcheckDetList =
	// equYcheckService.findEquYcheckDet(equYcheckDet);
	// String[] carnos = null;
	// if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
	// carnos = new String[equYcheckDetList.size()];
	// for (int i = 0; i < equYcheckDetList.size(); i++) {
	// EquYcheckDet equYcheckDet = equYcheckDetList.get(i);
	// carnos[i] = equYcheckDet.getCarno().trim();
	// }
	// EquCarCertificate equCarCertificate = new EquCarCertificate();
	// equCarCertificate.setCarnos(CommUtils.getSqlIn(carnos));
	// equCarCertificate.setIsvalid("1");
	// List<EquCarCertificate> equCarCertificateList = equCarCertificateService
	// .find(equCarCertificate);
	// Map<String, EquCarCertificate> equCarCertificateMap = new HashMap<String,
	// EquCarCertificate>();
	// if (equCarCertificateList != null && !equCarCertificateList.isEmpty()) {
	// for (EquCarCertificate carCertificate : equCarCertificateList) {
	// equCarCertificateMap.put(carCertificate.getTrainno().trim(),
	// carCertificate);
	// }
	// }
	// for (EquYcheckDet equYcheckDet : equYcheckDetList) {
	// if (null != type) {
	// if (type.equals("1")) {
	// equYcheckDet.setSaftcertcode(cardnum);
	// } else if (type.equals("2")) {
	// equYcheckDet.setTrancertcode(cardnum);
	// }
	// equYcheckService.update(equYcheckDet);
	// EquCarCertificate carCertificate = equCarCertificateMap.get(equYcheckDet
	// .getCarno().trim());
	// if (carCertificate == null) {
	// carCertificate = new EquCarCertificate();
	// isNew = true;
	// }
	//
	// carCertificate.setTrainno(equYcheckDet.getCarno());
	// carCertificate.setCertdate(new Date());
	// carCertificate.setIsvalid("1");
	// if (type.equals("1")) {
	// carCertificate.setSaftcertcode(cardnum);
	// } else if (type.equals("2")) {
	// carCertificate.setTrancertcode(cardnum);
	// }
	// if (isNew) {
	// equCarCertificateService.save(carCertificate);
	// } else {
	// equCarCertificateService.update(carCertificate);
	// }
	// cardnum = CommUtils.getNext(cardnum);
	// }
	// }
	// }
	//
	// msg = new Msg(Msg.SUCCESS, "1");
	// return MSG;
	// }
	/**
	 * 更新明细数据对应的路局信息
	 * 
	 * @return
	 * @throws Exception
	 */
	// public String modifybureau() throws Exception {
	// String yinspectionno = equYcheckDet.getYinspectionno();
	//
	// if (null != carnos && carnos.length > 0) {
	// for (int i = 0; i < carnos.length; i++) {
	// String carno = carnos[i];
	// for (int j = 0; j < bureaus.length; j++) {
	// String bureauid = bureaus[j].split("_")[1];
	// String bureauname = bureaus[j].split("_")[2];
	// // String yinspectionrelatedno =
	// // CommUtils.getString(yinspectionno, "-", carno);
	// EquYcheckBureau equYcheckBureau = equYcheckService.getEquYcheckBureau(
	// yinspectionno, bureauid);
	// if (null == equYcheckBureau) {
	// equYcheckBureau = new EquYcheckBureau();
	// equYcheckBureau.setBureauid(bureauid);
	// equYcheckBureau.setBureaushortname(URLDecoder.decode(bureauname,
	// "UTF-8"));
	// equYcheckBureau.setYinspectionno(yinspectionno);
	// // .setYinspectionrelatedno(yinspectionrelatedno);
	// equYcheckService.save(equYcheckBureau);
	// }
	// }
	// }
	// }
	// return detaillist();
	// }
	/**
	 * 进入编辑页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		String yinspectionno = equYcheck.getYinspectionno();
		String corpid = yinspectionno.split("-")[0];
		if (null == equYcheckDet) {
			equYcheckDet = new EquYcheckDet();
		}
		equYcheckDet.setYinspectionno(yinspectionno);
		List<EquYcheckDet> equYcheckDets = equYcheckService.findEquYcheckDet(equYcheckDet);
		List<String> carnoList = null;
		if (null != equYcheckDets && !equYcheckDets.isEmpty()) {
			carnoList = new ArrayList<String>();
			for (int i = 0; i < equYcheckDets.size(); i++) {
				EquYcheckDet equYcheckDet = equYcheckDets.get(i);
				if (!carnoList.contains(equYcheckDet.getCarno().trim())) {
					carnoList.add(equYcheckDet.getCarno().trim());
				}
			}
		}
		equCarList = equCarService.findEquCar(corpid, carnoList);
		// equCarList = equCarService.findEquCarByCorpid(corpid);
		// equCarList = equCarService.findEquCarByCorpid(corpid);
		equYcheck = equYcheckService.loadYcheck(yinspectionno);
		if (!isNew) {
			long id = equYcheckDet.getId();
			equYcheckDet = equYcheckService.loadYcheckDet(id);
		}
		return "edit";
	}

	public String save() throws Exception {
		String carno = equYcheckDet.getCarno();
		EquCar equCar = equCarService.load(carno);
		equYcheckDet.setCartypeid(equCar.getCartypeid());
		equYcheckDet.setCarkindid(equCar.getCarkindid());
		equYcheckDet.setGoodsid(equCar.getGoodsid());
		if (isNew) {
			equYcheckService.save(equYcheckDet);
		} else {
			equYcheckService.updateEntity(equYcheckDet);
		}
		msg = new Msg(Msg.SUCCESS, "操作成功");
		return MSG;
	}

	public String savebureau() throws Exception {
		String yinspectionno = equYcheck.getYinspectionno();
		equYcheckService.deleteEquYcheckBureau(yinspectionno);
		// 保存路局
		if (null != bureauids && bureauids.length > 0) {
			for (int i = 0; i < bureauids.length; i++) {
				String goodsid = bureauids[i].split("_")[0];
				String bureauid = bureauids[i].split("_")[1];
				String bureauname = bureauids[i].split("_")[2];
				EquYcheckBureau equYcheckBureau = new EquYcheckBureau();
				equYcheckBureau.setBureauid(bureauid);
				equYcheckBureau.setBureaushortname(URLDecoder.decode(bureauname, "UTF-8"));
				equYcheckBureau.setCorpid(yinspectionno.split("-")[0]);
				equYcheckBureau.setGoodsid(goodsid);
				equYcheckBureau.setInspectiontype(yinspectionno.split("-")[2]);
				equYcheckBureau.setYear(yinspectionno.split("-")[1]);
				equYcheckBureau.setYinspectionno(yinspectionno);
				equYcheckService.save(equYcheckBureau);
			}
		}
		equYcheckService.deleteEquYcheckStation(yinspectionno);
		// 保存车站
		if (null != loadstationids && loadstationids.length > 0) {
			// 装车站
			for (int i = 0; i < loadstationids.length; i++) {
				String stationid = loadstationids[i].split("_")[0];
				String goodsid = loadstationids[i].split("_")[1];
				String bureauid = loadstationids[i].split("_")[2];
				EquYcheckStation equYcheckStation = new EquYcheckStation();
				equYcheckStation.setStationid(stationid);
				equYcheckStation.setDatatype("L");
				equYcheckStation.setBureauid(bureauid);
				String yinspectionbureauid = CommUtils.getString(yinspectionno, "-", goodsid, "-", bureauid);
				equYcheckStation.setYinspectionbureauid(yinspectionbureauid);
				equYcheckStation.setYinspectionno(yinspectionno);
				equYcheckService.save(equYcheckStation);
			}
		}
		// unloadstationids
		if (null != unloadstationids && unloadstationids.length > 0) {
			// 卸车站
			for (int i = 0; i < unloadstationids.length; i++) {
				String stationid = unloadstationids[i].split("_")[0];
				String goodsid = unloadstationids[i].split("_")[1];
				String bureauid = unloadstationids[i].split("_")[2];
				EquYcheckStation equYcheckStation = new EquYcheckStation();
				equYcheckStation.setStationid(stationid);
				equYcheckStation.setDatatype("U");
				equYcheckStation.setBureauid(bureauid);
				String yinspectionbureauid = CommUtils.getString(yinspectionno, "-", goodsid, "-", bureauid);
				equYcheckStation.setYinspectionbureauid(yinspectionbureauid);
				equYcheckStation.setYinspectionno(yinspectionno);
				equYcheckService.save(equYcheckStation);
			}
		}

		// msg = new Msg(Msg.SUCCESS, "",
		// "frameElement.lhgDG.curWin.location.reload();");
		msg = new Msg(Msg.SUCCESS, "1");
		return MSG;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		initOpraMap("EQU_YCHECK");
	}

	public EquYcheckDet getEquYcheckDet() {
		return equYcheckDet;
	}

	public void setEquYcheckDet(EquYcheckDet equYcheckDet) {
		this.equYcheckDet = equYcheckDet;
	}

	public EquYcheck getEquYcheck() {
		return equYcheck;
	}

	public void setEquYcheck(EquYcheck equYcheck) {
		this.equYcheck = equYcheck;
	}

	public String[] getCarnos() {
		return carnos;
	}

	public void setCarnos(String[] carnos) {
		this.carnos = carnos;
	}

	public List<EquYcheckDet> getEquYcheckDetList() {
		return equYcheckDetList;
	}

	public void setEquYcheckDetList(List<EquYcheckDet> equYcheckDetList) {
		this.equYcheckDetList = equYcheckDetList;
	}

	public Map<String, String> getGoodsNameMap() {
		return goodsNameMap;
	}

	public void setGoodsNameMap(Map<String, String> goodsNameMap) {
		this.goodsNameMap = goodsNameMap;
	}

	public Map<String, DicCarkind> getDicCarkindMap() {
		return dicCarkindMap;
	}

	public void setDicCarkindMap(Map<String, DicCarkind> dicCarkindMap) {
		this.dicCarkindMap = dicCarkindMap;
	}

	public List<String> getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(List<String> goodsIds) {
		this.goodsIds = goodsIds;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public List<EquCar> getEquCarList() {
		return equCarList;
	}

	public void setEquCarList(List<EquCar> equCarList) {
		this.equCarList = equCarList;
	}

	public List<DicRwbureau> getDicRwbureauList() {
		return dicRwbureauList;
	}

	public void setDicRwbureauList(List<DicRwbureau> dicRwbureauList) {
		this.dicRwbureauList = dicRwbureauList;
	}

	public String[] getBureauids() {
		return bureauids;
	}

	public void setBureauids(String[] bureauids) {
		this.bureauids = bureauids;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public Map<String, DicCartype> getDicCartypeMap() {
		return dicCartypeMap;
	}

	public void setDicCartypeMap(Map<String, DicCartype> dicCartypeMap) {
		this.dicCartypeMap = dicCartypeMap;
	}

	public Map<String, List<String>> getCarnosByGoodsIdMap() {
		return carnosByGoodsIdMap;
	}

	public void setCarnosByGoodsIdMap(Map<String, List<String>> carnosByGoodsIdMap) {
		this.carnosByGoodsIdMap = carnosByGoodsIdMap;
	}

	public Map<String, List<String>> getCartypesByGoodsIdMap() {
		return cartypesByGoodsIdMap;
	}

	public void setCartypesByGoodsIdMap(Map<String, List<String>> cartypesByGoodsIdMap) {
		this.cartypesByGoodsIdMap = cartypesByGoodsIdMap;
	}

	public Map<String, List<String>> getCarsByCartypeIdMap() {
		return carsByCartypeIdMap;
	}

	public void setCarsByCartypeIdMap(Map<String, List<String>> carsByCartypeIdMap) {
		this.carsByCartypeIdMap = carsByCartypeIdMap;
	}

	public List<DicGoodscategory> getGoodscategories() {
		return goodscategories;
	}

	public void setGoodscategories(List<DicGoodscategory> goodscategories) {
		this.goodscategories = goodscategories;
	}

	public Map<String, EquYcheckDet> getEquYcheckDetMap() {
		return equYcheckDetMap;
	}

	public void setEquYcheckDetMap(Map<String, EquYcheckDet> equYcheckDetMap) {
		this.equYcheckDetMap = equYcheckDetMap;
	}

	public Map<String, String> getRwbureauMap() {
		return rwbureauMap;
	}

	public void setRwbureauMap(Map<String, String> rwbureauMap) {
		this.rwbureauMap = rwbureauMap;
	}

	public String[] getLoadstationids() {
		return loadstationids;
	}

	public void setLoadstationids(String[] loadstationids) {
		this.loadstationids = loadstationids;
	}

	public String[] getUnloadstationids() {
		return unloadstationids;
	}

	public void setUnloadstationids(String[] unloadstationids) {
		this.unloadstationids = unloadstationids;
	}

	public Map<String, List<EquYcheckBureau>> getEquYcheckBureauListByGoodsidMap() {
		return equYcheckBureauListByGoodsidMap;
	}

	public void setEquYcheckBureauListByGoodsidMap(Map<String, List<EquYcheckBureau>> equYcheckBureauListByGoodsidMap) {
		this.equYcheckBureauListByGoodsidMap = equYcheckBureauListByGoodsidMap;
	}

	public Map<String, List<String>> getLoadstationsByGoodsIdAndBureauidMap() {
		return loadstationsByGoodsIdAndBureauidMap;
	}

	public void setLoadstationsByGoodsIdAndBureauidMap(Map<String, List<String>> loadstationsByGoodsIdAndBureauidMap) {
		this.loadstationsByGoodsIdAndBureauidMap = loadstationsByGoodsIdAndBureauidMap;
	}

	public Map<String, List<String>> getUnloadstationsByGoodsIdAndBureauidMap() {
		return unloadstationsByGoodsIdAndBureauidMap;
	}

	public void setUnloadstationsByGoodsIdAndBureauidMap(Map<String, List<String>> unloadstationsByGoodsIdAndBureauidMap) {
		this.unloadstationsByGoodsIdAndBureauidMap = unloadstationsByGoodsIdAndBureauidMap;
	}

	public Map<String, String> getDicRwdepartmentMap() {
		return dicRwdepartmentMap;
	}

	public void setDicRwdepartmentMap(Map<String, String> dicRwdepartmentMap) {
		this.dicRwdepartmentMap = dicRwdepartmentMap;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public Map<String, List<String>> getCarsByCarkindIdMap() {
		return carsByCarkindIdMap;
	}

	public void setCarsByCarkindIdMap(Map<String, List<String>> carsByCarkindIdMap) {
		this.carsByCarkindIdMap = carsByCarkindIdMap;
	}

}
