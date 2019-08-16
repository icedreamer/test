/**
 * 
 */
package com.tlys.comm.util;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCarmaker;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicCorprailway;
import com.tlys.dic.model.DicCorprepdepot;
import com.tlys.dic.model.DicCustomer;
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicProdcategory;
import com.tlys.dic.model.DicProdsecondkind;
import com.tlys.dic.model.DicProduct;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.model.DicRwbureau;
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicRwdepartype;
import com.tlys.dic.model.DicRwstation;
import com.tlys.dic.model.DicSalecorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.model.DicTrankMaterial;
import com.tlys.dic.model.DicTransporter;
import com.tlys.dic.model.ctl.CtlCorpreceiver;
import com.tlys.dic.model.ctl.CtlCorpsender;
import com.tlys.dic.model.ctl.CtlCustomerreceiver;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicCarkindService;
import com.tlys.dic.service.DicCarmakerService;
import com.tlys.dic.service.DicCartypeService;
import com.tlys.dic.service.DicCorprailwayService;
import com.tlys.dic.service.DicCustomerService;
import com.tlys.dic.service.DicGoodsService;
import com.tlys.dic.service.DicGoodscategoryService;
import com.tlys.dic.service.DicProdcategoryService;
import com.tlys.dic.service.DicProdsecondkindService;
import com.tlys.dic.service.DicProductService;
import com.tlys.dic.service.DicProvinceService;
import com.tlys.dic.service.DicRwbureauService;
import com.tlys.dic.service.DicRwdepartmentService;
import com.tlys.dic.service.DicRwdepartypeService;
import com.tlys.dic.service.DicRwstationService;
import com.tlys.dic.service.DicSalecorpService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.dic.service.DicTrankMaterialService;
import com.tlys.dic.service.DicTransporterService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlCorpsenderService;
import com.tlys.dic.service.ctl.CtlCustomerreceiverService;
import com.tlys.equ.model.EquCar;
import com.tlys.spe.model.SpeLaw;
import com.tlys.spe.model.SpeLoadMeastype;
import com.tlys.spe.model.SpePackmark;
import com.tlys.spe.model.SpePackmarkPic;
import com.tlys.spe.model.SpePaperstype;
import com.tlys.spe.service.SpeLoadMeastypeService;
import com.tlys.spe.service.SpePackmarkService;
import com.tlys.spe.service.SpePapersService;
import com.tlys.sys.model.SysNavimenu;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysMenuService;
import com.tlys.sys.service.SysUserService;

/**
 * @author fengym 保存系统中用到的系统字典（键－值对) 静态，长驻内存 2012-02-01更新： 加入字典源数据变动标志位处理，
 *         对象如果在数据库中有变动，则在内存全局变量中设置一个标志位， 当有此标志时，取数据字典数据的时候就要更新先前读进内存的字典数据。
 *         具体用法请参考getPackmarkMap()及相关方法。
 */
@Component
public class DicMap {
	private Log log = LogFactory.getLog(this.getClass());
	@Autowired
	DicProvinceService dicProvinceService;
	@Autowired
	DicGoodscategoryService dicGoodscategoryService;
	@Autowired
	DicTrankMaterialService dicTrankmaterialService;
	@Autowired
	DicCarmakerService dicCarmakerService;
	@Autowired
	DicCarkindService dicCarkindService;

	@Autowired
	DicCartypeService dicCartypeService;

	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinodepartmentService dicSinodepartmentService;

	@Autowired
	DicRwdepartmentService dicRwdepartmentService;
	@Autowired
	DicRwbureauService dicRwbureauService;
	@Autowired
	DicRwdepartypeService dicRwdepartypeService;

	@Autowired
	DicCustomerService dicCustomerService;

	@Autowired
	SpePackmarkService spePackmarkService;

	@Autowired
	SpePapersService spePapersService;

	@Autowired
	DicProdcategoryService dicProdcategoryService;

	@Autowired
	DicProdsecondkindService dicProdsecondkindService;

	@Autowired
	DicProductService dicProductService;

	@Autowired
	DicCorprailwayService dicCorprailwayService;

	@Autowired
	DicTransporterService dicTransporterService;

	@Autowired
	DicRwstationService dicRwstationService;

	@Autowired
	DicGoodsService dicGoodsService;

	@Autowired
	DicSalecorpService dicSalecorpService;

	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;

	@Autowired
	CtlCorpsenderService ctlCorpsenderService;

	@Autowired
	CtlCustomerreceiverService ctlCustomerreceiverService;

	@Autowired
	SpeLoadMeastypeService speLoadMeastypeService;

	// @Resource
	@Autowired
	ServletContext servletContext;
	@Autowired
	SysUserService sysUserService;

	@Autowired
	SysMenuService menuService;

	// 企业（已加更新标志处理)
	static Map corpMap;

	// 区域公司
	static Map areaMap;

	// 所辖单位
	static Map deptMap;

	// 省份
	static Map provinceMap;

	// 自备车充装介质
	static Map goodscategoryMap;

	// 罐体材质
	static Map tankmaterialMap;

	// 制造厂商
	static Map carmakerMap;
	// 车种
	static Map carkindMap;

	// 车型
	static Map cartypeMap;

	// 过轨站
	static Map rwdepartmentMap;
	// 路局
	static Map rwbureauMap;
	// 铁路单位类型
	static Map rwdepartypeMap;

	// 标志标识（已加更新标志处理)
	static Map packmarkMap;

	// 化工销售客户
	static Map customerMap;

	// 产品大类
	static Map productcategoryMap;

	// 产品小类
	static Map productsecondMap;

	// 产品
	static Map productMap;

	// 专用线(dicCorprailway)
	static Map crwMap;

	// 承运商
	static Map transporterMap;

	// 车站视图
	static Map rwstationMap;

	// 铁路品名
	static Map goodsMap;

	// 销售机构
	static Map salecorpMap;

	// 收货人
	static Map corpreceiverMap;

	// 发货人
	static Map corpsenderMap;

	// 客户对应的收货人
	static Map customerreceiverMap;

	// 是否
	static Map ifMap;

	// 计量方式
	static Map speLoadMeastypeMap;

	// 状态
	static Map statusMap;
	static Map spePaperstypeMap;
	static Map userMap;

	/**
	 * 根据PID递归生成空格
	 * 
	 * @param pid
	 * @param map
	 * @return
	 */
	public String getTreeName(String pid, Map map) {
		String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;";
		if (!"0".equals(pid)) {
			DicProduct dic = (DicProduct) map.get(pid);
			if (dic != null) {
				pid = dic.getParentid();
				nbsp = getTreeName(pid, map) + nbsp;
			}
		}
		return nbsp;
	}

	// --------------------------------------------------------------------------------
	/**
	 * 以下是得到各字典Map的方法，一次调用，则长驻内存
	 */
	// --------------------------------------------------------------------------------
	public Map getAllUserMap() {
		if (userMap == null || isAlter("SysUser")) {
			// System.out.println("DicMap.getUserMap->==从数据库中读取sysUser!!!!!!!!!");
			List<SysUser> sysUsers = sysUserService.findAll();
			userMap = new LinkedHashMap();
			for (Iterator iter = sysUsers.iterator(); iter.hasNext();) {
				SysUser sysUser = (SysUser) iter.next();
				userMap.put(sysUser.getUserid(), sysUser);
			}
			removeAlterFlag("SysUser");
		}
		return userMap;
	}

	public Map getUserMap() {
		if (userMap == null || isAlter("SysUser")) {
			// System.out.println("DicMap.getUserMap->==从数据库中读取sysUser!!!!!!!!!");
			List<SysUser> sysUsers = sysUserService.findUsers();
			userMap = new LinkedHashMap();
			for (Iterator iter = sysUsers.iterator(); iter.hasNext();) {
				SysUser sysUser = (SysUser) iter.next();
				userMap.put(sysUser.getUserid(), sysUser);
			}
			removeAlterFlag("SysUser");
		}
		return userMap;
	}

	/**
	 * 省份字典
	 * 
	 * @return the provinceMap
	 */
	public Map getProvinceMap() {
		if (provinceMap == null || isAlter("dicProvince")) {
			List<DicProvince> provinces = dicProvinceService.findAll();
			provinceMap = new LinkedHashMap();
			for (Iterator iter = provinces.iterator(); iter.hasNext();) {
				DicProvince dicProvince = (DicProvince) iter.next();
				provinceMap.put(dicProvince.getProvinceid(), dicProvince.getName());

			}
			removeAlterFlag("dicProvince");
		}
		return provinceMap;
	}

	/**
	 * 专为计划导入设计的自动匹配接口
	 * 
	 * @return
	 */
	public Map getGoalprovinceMap() {
		return getProvinceMap();
	}

	/**
	 * 罐体材质
	 * 
	 * @return the tankmaterialMap
	 */
	public Map getTankmaterialMap() {
		if (null == tankmaterialMap || isAlter("dicTrankMaterial")) {
			List<DicTrankMaterial> tankmList = dicTrankmaterialService.findAll();
			tankmaterialMap = new LinkedHashMap();
			for (Iterator iterator = tankmList.iterator(); iterator.hasNext();) {
				DicTrankMaterial dicTrankMaterial = (DicTrankMaterial) iterator.next();
				tankmaterialMap.put(dicTrankMaterial.getTankmateid(), dicTrankMaterial.getMname());

			}
			removeAlterFlag("dicTrankMaterial");
		}
		return tankmaterialMap;
	}

	/**
	 * 制造商字典
	 * 
	 * @return the carmakerMap
	 */
	public Map getCarmakerMap() {
		if (null == carmakerMap || isAlter("dicCarmaker")) {
			List<DicCarmaker> carmakerList = dicCarmakerService.findAll();
			carmakerMap = new LinkedHashMap();
			for (Iterator iterator = carmakerList.iterator(); iterator.hasNext();) {
				DicCarmaker dicCarmaker = (DicCarmaker) iterator.next();
				carmakerMap.put(dicCarmaker.getCarmakerid(), dicCarmaker.getShortname());
			}
			removeAlterFlag("dicCarmaker");
		}
		return carmakerMap;
	}

	/**
	 * 充装介质字典
	 * 
	 * @return the goodscategoryMap
	 */
	public Map getGoodscategoryMap() {
		if (null == goodscategoryMap || isAlter("dicGoodscategory")) {
			List<DicGoodscategory> goodscategoryList = dicGoodscategoryService.findAll();
			goodscategoryMap = new LinkedHashMap();
			for (Iterator iter = goodscategoryList.iterator(); iter.hasNext();) {
				DicGoodscategory dicGoodsCate = (DicGoodscategory) iter.next();
				goodscategoryMap.put(dicGoodsCate.getGoodsid(), dicGoodsCate.getGoodsname());

			}
			removeAlterFlag("dicGoodscategory");
		}
		return goodscategoryMap;
	}

	/**
	 * 车种字典
	 * 
	 * @return the carkindMap
	 * @throws Exception
	 */
	public Map getCarkindMap() {
		if (null == carkindMap || isAlter("dicCarkind")) {
			List<DicCarkind> ckList = dicCarkindService.findAll();
			carkindMap = new LinkedHashMap();
			for (Iterator iter = ckList.iterator(); iter.hasNext();) {
				DicCarkind dicCarkind = (DicCarkind) iter.next();
				carkindMap.put(dicCarkind.getCarkindid(), dicCarkind);
			}
			removeAlterFlag("dicCarkind");
		}

		return carkindMap;
	}

	public Map getCartypeMap() {
		if (cartypeMap == null || isAlter("dicCartype")) {
			List<DicCartype> corps = dicCartypeService.findAll();
			cartypeMap = new LinkedHashMap();
			for (Iterator iter = corps.iterator(); iter.hasNext();) {
				DicCartype cartype = (DicCartype) iter.next();
				cartypeMap.put(cartype.getCartypeid(), cartype);
			}
			removeAlterFlag("dicCartype");
		}
		return cartypeMap;
	}

	/**
	 * 企业字典
	 * 
	 * @return corpMap
	 */
	public Map getCorpAllMap() {
		if (corpMap == null || isAlter("dicSinocorp")) {
			List<DicSinocorp> corps = dicSinocorpService.findAll4Map();
			corpMap = new LinkedHashMap();
			for (Iterator iter = corps.iterator(); iter.hasNext();) {
				DicSinocorp corp = (DicSinocorp) iter.next();
				corpMap.put(corp.getCorpid(), corp);

			}
			removeAlterFlag("dicSinocorp");
		}
		return corpMap;
	}

	/**
	 * 区域公司
	 * 
	 * @return areaMap
	 * @throws Exception
	 */
	public Map getAreaMap() throws Exception {
		if (areaMap == null || isAlter("dicAreacorp")) {
			List<DicAreacorp> corps = dicAreacorpService.findAll();
			areaMap = new LinkedHashMap();
			for (Iterator iter = corps.iterator(); iter.hasNext();) {
				DicAreacorp area = (DicAreacorp) iter.next();
				areaMap.put(area.getAreaid(), area);
			}
			removeAlterFlag("dicAreacorp");
		}
		return areaMap;
	}

	/**
	 * 所辖部门
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getDeptMap() throws Exception {
		if (deptMap == null || isAlter("dicSinodepartment")) {
			List<DicSinodepartment> depts = dicSinodepartmentService.findAll();
			deptMap = new LinkedHashMap();
			for (Iterator iter = depts.iterator(); iter.hasNext();) {
				DicSinodepartment dept = (DicSinodepartment) iter.next();
				deptMap.put(dept.getSinodepaid(), dept);
			}
			removeAlterFlag("dicSinodepartment");
		}
		return deptMap;
	}

	/**
	 * 过轨站字典
	 * 
	 * @return rwdepartmentMap
	 */
	public Map getRwdepartmentMap() {
		if (rwdepartmentMap == null || isAlter("dicRwdepartment")) {
			List<DicRwdepartment> list = dicRwdepartmentService.findAll();
			rwdepartmentMap = new LinkedHashMap();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				DicRwdepartment rwdepartment = (DicRwdepartment) iter.next();
				rwdepartmentMap.put(rwdepartment.getRwdepaid(), rwdepartment.getShortname());

			}
			removeAlterFlag("dicRwdepartment");
		}
		return rwdepartmentMap;
	}

	/**
	 * 路局
	 * 
	 * @return rwdepartmentMap
	 */
	public Map getRwbureauMap() {
		if (rwbureauMap == null || isAlter("dicRwbureau")) {
			List<DicRwbureau> list = dicRwbureauService.findAll();
			rwbureauMap = new LinkedHashMap();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				DicRwbureau rwbureau = (DicRwbureau) iter.next();
				rwbureauMap.put(rwbureau.getBureauid(), rwbureau.getShortname());

			}
			removeAlterFlag("dicRwbureau");
		}
		return rwbureauMap;
	}

	/**
	 * 铁路单位类型
	 * 
	 * @return rwdepartmentMap
	 */
	public Map getRwdepartypeMap() {
		if (rwdepartypeMap == null || isAlter("dicRwdepartype")) {
			List<DicRwdepartype> list = dicRwdepartypeService.findAll();
			rwdepartypeMap = new LinkedHashMap();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				DicRwdepartype rwdepartype = (DicRwdepartype) iter.next();
				rwdepartypeMap.put(rwdepartype.getTpyeid(), rwdepartype.getName());

			}
			removeAlterFlag("dicRwdepartype");
		}
		return rwdepartypeMap;
	}

	/**
	 * 计量方式
	 * 
	 * @return speLoadMeastypeMap
	 */
	public Map getLoadMeastypeMap() {
		if (speLoadMeastypeMap == null || isAlter("speLoadMeastype")) {
			List<SpeLoadMeastype> list = speLoadMeastypeService.findAll();
			speLoadMeastypeMap = new LinkedHashMap();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				SpeLoadMeastype speObj = (SpeLoadMeastype) iter.next();
				speLoadMeastypeMap.put(speObj.getMeastypeid(), speObj);
			}
			removeAlterFlag("speLoadMeastype");
		}
		return speLoadMeastypeMap;
	}

	/**
	 * 是否
	 * 
	 * @return ifMap
	 */
	public Map getIfMap() {
		if (ifMap == null) {
			ifMap = new LinkedHashMap();
			ifMap.put("0", "否");
			ifMap.put("1", "是");
			ifMap.put(null, "否");
		}
		return ifMap;
	}

	/**
	 * 状态
	 * 
	 * @return statusMap
	 */
	public Map getStatusMap() {
		if (statusMap == null) {
			statusMap = new LinkedHashMap();
			statusMap.put("0", "未提交");
			statusMap.put("1", "待发布");
			statusMap.put("2", "已驳回");
			statusMap.put("3", "已发布");
		}
		return statusMap;
	}

	public Map getPackmarkMap() {
		if (null == packmarkMap || isAlter("spePackmark")) {
			packmarkMap = new LinkedHashMap();
			List<SpePackmark> pmList = spePackmarkService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				SpePackmark spePackmark = (SpePackmark) iter.next();
				packmarkMap.put(spePackmark.getMarkid(), spePackmark);
			}
			removeAlterFlag("spePackmark");
		}
		return packmarkMap;
	}

	public Map getSpePaperstypeMap() {
		if (null == spePaperstypeMap || isAlter("spePaperstype")) {
			spePaperstypeMap = new LinkedHashMap();
			List<SpePaperstype> sptpList = spePapersService.findSpePaperstype();
			for (Iterator iter = sptpList.iterator(); iter.hasNext();) {
				SpePaperstype spePaperstype = (SpePaperstype) iter.next();
				spePaperstypeMap.put(spePaperstype.getPtypeid(), spePaperstype);
			}
			removeAlterFlag("spePaperstype");
		}
		return spePaperstypeMap;
	}

	public Map getCustomerMap() {
		if (null == customerMap || isAlter("dicCustomer")) {
			customerMap = new LinkedHashMap();

			List<DicCustomer> pmList = dicCustomerService.findAll4Dic();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicCustomer dicCustomer = (DicCustomer) iter.next();
				customerMap.put(dicCustomer.getCustomerid(), dicCustomer);
			}
			removeAlterFlag("dicCustomer");
		}
		return customerMap;
	}

	/**
	 * 把dicCustomer与dicSinocorp的数据进行合并 key为ID，value为字符串
	 * （暂时不用了：由于Customer中已经包含了内部企业corp，故不需要进行合并，目前保留！)
	 * 
	 * @return
	 */
	public Map getCustomercorpMap() {
		Map ccMap = new LinkedHashMap();
		Map coMap = this.getCorpMap();
		Map cuMap = this.getCustomerMap();
		DicCustomer dicCustomerTmp = null;
		for (Iterator iter = coMap.keySet().iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			DicSinocorp corp = (DicSinocorp) coMap.get(id);
			dicCustomerTmp = new DicCustomer();
			dicCustomerTmp.setCustomerid(corp.getCorpid());
			dicCustomerTmp.setFullname(corp.getFullname());
			dicCustomerTmp.setShortname(corp.getShortname());
			ccMap.put(id, dicCustomerTmp);
		}
		for (Iterator iter = cuMap.keySet().iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			DicCustomer dicCustomer = (DicCustomer) cuMap.get(id);
			ccMap.put(id, dicCustomer);
		}
		return ccMap;
	}

	public Map getProductcategoryMap() {
		if (null == productcategoryMap || isAlter("dicProdcategory")) {
			productcategoryMap = new LinkedHashMap();

			List<DicProdcategory> pmList = dicProdcategoryService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicProdcategory dicProdcategory = (DicProdcategory) iter.next();
				productcategoryMap.put(dicProdcategory.getCategoryid(), dicProdcategory);
			}
			removeAlterFlag("dicProdcategory");
		}
		return productcategoryMap;
	}

	public Map getProductsecondMap() {
		if (null == productsecondMap || isAlter("dicProdsecondkind")) {
			productsecondMap = new LinkedHashMap();

			List<DicProdsecondkind> pmList = dicProdsecondkindService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicProdsecondkind dicProdsecondkind = (DicProdsecondkind) iter.next();
				productsecondMap.put(dicProdsecondkind.getSecondkindid(), dicProdsecondkind);
			}
			removeAlterFlag("dicProdsecondkind");
		}
		return productsecondMap;
	}

	public Map getProductMap() {
		if (null == productMap || isAlter("dicProduct")) {
			productMap = new LinkedHashMap();

			List<DicProduct> pmList = dicProductService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicProduct dicProduct = (DicProduct) iter.next();
				productMap.put(dicProduct.getProdid(), dicProduct);
			}
			removeAlterFlag("dicProduct");
		}
		return productMap;
	}

	public Map getCrwMap() {
		if (null == crwMap || isAlter("dicCorprailway")) {
			crwMap = new LinkedHashMap();

			List<DicCorprailway> pmList = dicCorprailwayService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicCorprailway dicCorprailway = (DicCorprailway) iter.next();
				crwMap.put(dicCorprailway.getCrwid(), dicCorprailway);
			}
			removeAlterFlag("dicCorprailway");
		}
		return crwMap;
	}

	public Map getTransporterMap() {
		if (null == transporterMap || isAlter("dicTransporter")) {
			transporterMap = new LinkedHashMap();

			List<DicTransporter> pmList = dicTransporterService.findAll4Dic();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicTransporter dicTransporter = (DicTransporter) iter.next();
				transporterMap.put(dicTransporter.getTransportid(), dicTransporter);
			}
			removeAlterFlag("dicTransporter");
		}
		return transporterMap;
	}

	public Map getRwstationMap() {
		// 这里采用的是常驻内存，不更新机制，因为所操作对象是视图，程序中没有更新等操作
		// 后增：如果全局刷新时，也要进行更新，故仍增加判断语句
		if (null == rwstationMap || isAlter("rwstation")) {
			rwstationMap = new LinkedHashMap();

			List<DicRwstation> pmList = dicRwstationService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicRwstation dicRwstation = (DicRwstation) iter.next();
				rwstationMap.put(dicRwstation.getStationid(), dicRwstation);
			}
			removeAlterFlag("rwstation");
		}
		return rwstationMap;
	}

	/**
	 * 发站 为计划模块提供数据字典视图
	 * 
	 * @return
	 */
	public Map getStartstationMap() {
		return getRwstationMap();
	}

	/**
	 * 到站 为计划模块提供数据字典视图
	 * 
	 * @return
	 */
	public Map getEndstationMap() {
		return getRwstationMap();
	}

	public Map getDicGoodsMap() {
		// 这里采用的是常驻内存，不更新机制，因为更新操作由铁道部所掌握,后面再协调
		if (null == goodsMap || isAlter("dicGoods")) {
			goodsMap = new LinkedHashMap();

			List<DicGoods> pmList = dicGoodsService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicGoods dicGoods = (DicGoods) iter.next();
				goodsMap.put(dicGoods.getDm(), dicGoods);
			}
			removeAlterFlag("dicGoods");
		}
		return goodsMap;
	}

	public Map getSalecorpMap() {
		// 这里采用的是常驻内存，不更新机制或随时更新机制
		// 改为随时更新
		if (null == salecorpMap || isAlter("dicSalecorp")) {
			salecorpMap = new LinkedHashMap();

			List<DicSalecorp> pmList = dicSalecorpService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				DicSalecorp dicSalecorp = (DicSalecorp) iter.next();
				salecorpMap.put(dicSalecorp.getId(), dicSalecorp);
			}

			removeAlterFlag("dicSalecorp");
		}
		return salecorpMap;
	}

	public Map getSaledepartmentMap() {
		return getSalecorpMap();
	}

	/**
	 * 此处Map结构有所不同 Map: corpid=Map{receiverid=ctlCorpreceiver}
	 * 
	 * @return
	 */
	public Map getReceiverMap() {
		if (null == corpreceiverMap || isAlter("ctlCorpreceiver")) {
			corpreceiverMap = new LinkedHashMap();

			List<CtlCorpreceiver> pmList = ctlCorpreceiverService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				CtlCorpreceiver ctlCorpreceiver = (CtlCorpreceiver) iter.next();
				String corpid = ctlCorpreceiver.getCorpid();
				Map receiverMap = (Map) corpreceiverMap.get(corpid);
				if (null == receiverMap) {
					receiverMap = new LinkedHashMap();
					corpreceiverMap.put(corpid, receiverMap);
				}
				receiverMap.put(ctlCorpreceiver.getReceiverid(), ctlCorpreceiver);
			}
			removeAlterFlag("ctlCorpreceiver");
		}
		return corpreceiverMap;
	}

	/**
	 * 此处Map结构有所不同
	 * 
	 * @return
	 */
	public Map getSenderMap() {
		if (null == corpsenderMap || isAlter("ctlCorpsender")) {
			corpsenderMap = new LinkedHashMap();

			List<CtlCorpsender> pmList = ctlCorpsenderService.findAll();
			for (Iterator iter = pmList.iterator(); iter.hasNext();) {
				CtlCorpsender ctlCorpsender = (CtlCorpsender) iter.next();
				String corpid = ctlCorpsender.getCorpid();
				Map senderMap = (Map) corpsenderMap.get(corpid);
				if (null == senderMap) {
					senderMap = new LinkedHashMap();
					corpsenderMap.put(corpid, senderMap);
				}
				senderMap.put(ctlCorpsender.getSenderid(), ctlCorpsender);
			}
			removeAlterFlag("ctlCorpsender");
		}
		return corpsenderMap;
	}

	/**
	 * 调用此方法前，如果数据库有更新，请先执行 dicMap.dicAlter("ctlCustomerreceiver");
	 * Map结构：customerid=Map{receiverid=ctlCustomerrceiver}
	 * 
	 * @return
	 */
	public Map getCustomerreceiverMap() {
		//
		if (null == customerreceiverMap || isAlter("ctlCustomerreceiver")) {
			customerreceiverMap = new LinkedHashMap();
			List<CtlCustomerreceiver> crList = ctlCustomerreceiverService.findAll();
			for (Iterator iter = crList.iterator(); iter.hasNext();) {
				CtlCustomerreceiver cr = (CtlCustomerreceiver) iter.next();
				if (null != cr) {
					String crid = cr.getCustomerid();
					Map recMap = (Map) customerreceiverMap.get(crid);
					if (null == recMap) {
						recMap = new LinkedHashMap();
						customerreceiverMap.put(crid, recMap);
					}
					recMap.put(cr.getReceiverid(), cr);
				}
			}
			removeAlterFlag("ctlCustomerreceiver");
		}

		return customerreceiverMap;
	}

	/**
	 * 传入企业ID，返回企业对应的发货人Map
	 * 
	 * @param corpid
	 * @return
	 */
	public Map getSenderMap(String corpid) {
		Map sendMap = getSenderMap();
		return (Map) sendMap.get(corpid);
	}

	/**
	 * 传入企业ID，返回企业对应的收货人Map
	 * 
	 * @param corpid
	 * @return
	 */
	public Map getReceiverMap_bak(String corpid) {
		Map recMap = getReceiverMap();
		return (Map) recMap.get(corpid);
	}

	/**
	 * 封装后给计划模块调用
	 * 
	 * @return
	 */
	public Map getRwkindMap() {
		return getDicGoodsMap();
	}

	/**
	 * 调用此方法前，如果数据库有更新，请先执行 dicMap.dicAlter("ctlCustomerreceiver");
	 * dicMap.dicAlter("ctlCorpreceiver");
	 * 
	 * @param cid
	 * @return
	 */
	public Map getReceiverMap(String cid) {
		getCorpMap();
		Map receiverMap = null;
		DicSinocorp corp = (DicSinocorp) corpMap.get(cid);
		if (null != corp) {
			Map recMap = getReceiverMap();
			if (null != recMap) {
				receiverMap = (Map) recMap.get(cid);
			}
		} else {
			Map crMap = this.getCustomerreceiverMap();
			if (null != crMap) {
				receiverMap = (Map) crMap.get(cid);
			}
		}
		return receiverMap;
	}

	/**
	 * 当保存，更新，删除某个对象后，在内存在保存一个标志， 以通知字典管理程序（dicMap)，更新静态变量 此方法一般被各service所调用
	 */
	public void dicAlter(String obj) {
		String flag = (String) servletContext.getAttribute("dicAlterFlag");
		String objWrap = ":" + obj + ":";
		if (null == flag) {
			flag = objWrap;
		} else if (flag.indexOf(objWrap) < 0) {
			flag += objWrap;
		}
		servletContext.setAttribute("dicAlterFlag", flag);
	}

	/**
	 * 当探测到内存变量中有当前对象关键字，则返回true
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isAlter(String obj) {
		String flag = (String) servletContext.getAttribute("dicAlterFlag");
		String globalFlag = (String) servletContext.getAttribute("globalRefreshFlag");
		String objWrap = ":" + obj + ":";
		boolean alt = false;
		if ((null != flag && flag.indexOf(objWrap) > -1)
				|| (null == globalFlag || globalFlag.indexOf(objWrap) < 0)) {
			alt = true;
		}
		return alt;
	}

	/**
	 * 当读取最新数据后，清除内存中的标记 同时增加全局刷新标志
	 * 
	 * @param string
	 */
	private void removeAlterFlag(String obj) {
		String flag = (String) servletContext.getAttribute("dicAlterFlag");
		String globalFlag = (String) servletContext.getAttribute("globalRefreshFlag");
		String objWrap = ":" + obj + ":";
		if (null != flag && flag.indexOf(objWrap) > -1) {
			flag = flag.replaceAll(objWrap, "");
			servletContext.setAttribute("dicAlterFlag", flag);
		}
		if (null == globalFlag) {
			globalFlag = objWrap;
		} else if (globalFlag.indexOf(objWrap) < 0) {
			globalFlag += objWrap;
		}
		servletContext.setAttribute("globalRefreshFlag", globalFlag);
	}

	/**
	 * 通过VALUE值反向取得KEY值
	 */
	public Object getKey(String value, Map map) {
		Object o = null;
		ArrayList all = new ArrayList(); // 建一个数组用来存放符合条件的KEY值
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			// 字符串匹配不区分大小写
			if (value.equalsIgnoreCase((String) entry.getValue())) {
				// 对象匹配区分大小写
				// if (entry.getValue().equals(value)) {
				o = entry.getKey();
				all.add(o); // 把符合条件的项放到容器中
			}
		}
		return all;
	}

	// ==============================================================
	/**
	 * 导出字典翻译(发货人\产品大类\产品小类\产品名称\车种\车型\专用线\发站\到站\销售机构) TODO 计划导出翻译
	 * 
	 * @param list
	 * @throws Exception
	 */
	public void bdPlaMupsDic(List list) throws Exception {
		getSenderMap();// corpsenderMap
		getProductcategoryMap();// productcategoryMap
		getProductsecondMap();// productsecondMap
		getProductMap();// productMap
		getCarkindMap();// carkindMap
		getCartypeMap();// cartypeMap
		getCrwMap();// crwMap
		getRwstationMap();// rwstationMap
		getSalecorpMap();// salecorpMap
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			// 发货人
			String corpid = BeanUtils.getProperty(obj, "corpid");
			if (null != corpid && corpid.length() > 0) {
				Map map = (Map) corpsenderMap.get(corpid);
				if (null != map && map.size() > 0) {
					String senderid = BeanUtils.getProperty(obj, "senderid");
					CtlCorpsender ctlCorpsender = (CtlCorpsender) map.get(senderid);
					String senderidDIC = ctlCorpsender != null ? ctlCorpsender.getCorpshortname()
							: "";
					BeanUtils.setProperty(obj, "senderidDIC", senderidDIC);
				}
			}
			// 产品大类
			String productcategoryid = BeanUtils.getProperty(obj, "productcategoryid");
			DicProdcategory dicProdcategory = (DicProdcategory) productcategoryMap
					.get(productcategoryid);
			String productcategoryidDIC = dicProdcategory != null ? dicProdcategory.getShortname()
					: productcategoryid;
			BeanUtils.setProperty(obj, "productcategoryidDIC", productcategoryidDIC);
			// 产品小类
			String productsecondid = BeanUtils.getProperty(obj, "productsecondid");
			DicProdsecondkind dicProdsecondkind = (DicProdsecondkind) productsecondMap
					.get(productsecondid);
			String productsecondidDIC = dicProdsecondkind != null ? dicProdsecondkind
					.getShortname() : productsecondid;
			BeanUtils.setProperty(obj, "productsecondidDIC", productsecondidDIC);
			// 产品名称
			String prodductid = BeanUtils.getProperty(obj, "prodductid");
			DicProduct dicProduct = (DicProduct) productMap.get(prodductid);
			String prodductidDIC = dicProduct != null ? dicProduct.getShortname() : prodductid;
			BeanUtils.setProperty(obj, "prodductidDIC", prodductidDIC);
			// 车种
			String carkindid = BeanUtils.getProperty(obj, "carkindid");
			DicCarkind dicCarkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindidDIC = dicCarkind != null ? dicCarkind.getShortname() : carkindid;
			BeanUtils.setProperty(obj, "carkindidDIC", carkindidDIC);
			// 车型
			String cartypeid = BeanUtils.getProperty(obj, "cartypeid");
			DicCartype dicCartype = (DicCartype) cartypeMap.get(cartypeid);
			String cartypeidDIC = dicCartype != null ? dicCartype.getCartypename() : cartypeid;
			BeanUtils.setProperty(obj, "cartypeidDIC", cartypeidDIC);
			// 专用线
			String crwid = BeanUtils.getProperty(obj, "crwid");
			DicCorprailway dicCorprailway = (DicCorprailway) crwMap.get(crwid);
			String crwidDIC = dicCorprailway != null ? dicCorprailway.getShortname() : crwid;
			BeanUtils.setProperty(obj, "crwidDIC", crwidDIC);
			// 发站
			String startstationid = BeanUtils.getProperty(obj, "startstationid");
			DicRwstation dicRwstation = (DicRwstation) rwstationMap.get(startstationid);
			String startstationidDIC = dicRwstation != null ? dicRwstation.getShortname()
					: startstationid;
			BeanUtils.setProperty(obj, "startstationidDIC", startstationidDIC);
			// 到站
			String endstationid = BeanUtils.getProperty(obj, "endstationid");
			DicRwstation dicRwstation1 = (DicRwstation) rwstationMap.get(endstationid);
			String endstationidDIC = dicRwstation1 != null ? dicRwstation1.getShortname()
					: endstationid;
			BeanUtils.setProperty(obj, "endstationidDIC", endstationidDIC);
			// 销售机构
			String saledepartment = BeanUtils.getProperty(obj, "saledepartment");
			DicSalecorp dicSalecorp = (DicSalecorp) salecorpMap.get(saledepartment);
			String saledepartmentDIC = dicSalecorp != null ? dicSalecorp.getShortname()
					: saledepartment;
			BeanUtils.setProperty(obj, "saledepartmentDIC", saledepartmentDIC);
		}
	}

	/**
	 * 导出字典翻译(发货人\产品大类\产品小类\产品名称\车种\车型\专用线\发站\到站\销售机构) TODO 计划导出翻译
	 * 
	 * @param list
	 * @throws Exception
	 */
	public void bdPlaMspsDic(List list) throws Exception {
		getSenderMap();// corpsenderMap
		getProductcategoryMap();// productcategoryMap
		getProductsecondMap();// productsecondMap
		getProductMap();// productMap
		getCarkindMap();// carkindMap
		getCartypeMap();// cartypeMap
		getCrwMap();// crwMap
		getRwstationMap();// rwstationMap
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			// 发货人
			String corpid = BeanUtils.getProperty(obj, "corpid");
			if (null != corpid && corpid.length() > 0) {
				Map map = (Map) corpsenderMap.get(corpid);
				if (null != map && map.size() > 0) {
					String senderid = BeanUtils.getProperty(obj, "senderid");
					CtlCorpsender ctlCorpsender = (CtlCorpsender) map.get(senderid);
					String senderidDIC = ctlCorpsender != null ? ctlCorpsender.getCorpshortname()
							: "";
					BeanUtils.setProperty(obj, "senderidDIC", senderidDIC);
				}
			}
			// 产品大类
			String productcategoryid = BeanUtils.getProperty(obj, "productcategoryid");
			DicProdcategory dicProdcategory = (DicProdcategory) productcategoryMap
					.get(productcategoryid);
			String productcategoryidDIC = dicProdcategory != null ? dicProdcategory.getShortname()
					: productcategoryid;
			BeanUtils.setProperty(obj, "productcategoryidDIC", productcategoryidDIC);
			// 产品小类
			String productsecondid = BeanUtils.getProperty(obj, "productsecondid");
			DicProdsecondkind dicProdsecondkind = (DicProdsecondkind) productsecondMap
					.get(productsecondid);
			String productsecondidDIC = dicProdsecondkind != null ? dicProdsecondkind
					.getShortname() : productsecondid;
			BeanUtils.setProperty(obj, "productsecondidDIC", productsecondidDIC);
			// 产品名称
			String prodductid = BeanUtils.getProperty(obj, "prodductid");
			DicProduct dicProduct = (DicProduct) productMap.get(prodductid);
			String prodductidDIC = dicProduct != null ? dicProduct.getShortname() : prodductid;
			BeanUtils.setProperty(obj, "prodductidDIC", prodductidDIC);
			// 车种
			String carkindid = BeanUtils.getProperty(obj, "carkindid");
			DicCarkind dicCarkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindidDIC = dicCarkind != null ? dicCarkind.getShortname() : carkindid;
			BeanUtils.setProperty(obj, "carkindidDIC", carkindidDIC);
			// 车型
			String cartypeid = BeanUtils.getProperty(obj, "cartypeid");
			DicCartype dicCartype = (DicCartype) cartypeMap.get(cartypeid);
			String cartypeidDIC = dicCartype != null ? dicCartype.getCartypename() : cartypeid;
			BeanUtils.setProperty(obj, "cartypeidDIC", cartypeidDIC);
			// 专用线
			String crwid = BeanUtils.getProperty(obj, "crwid");
			DicCorprailway dicCorprailway = (DicCorprailway) crwMap.get(crwid);
			String crwidDIC = dicCorprailway != null ? dicCorprailway.getShortname() : crwid;
			BeanUtils.setProperty(obj, "crwidDIC", crwidDIC);
			// 发站
			String startstationid = BeanUtils.getProperty(obj, "startstationid");
			DicRwstation dicRwstation = (DicRwstation) rwstationMap.get(startstationid);
			String startstationidDIC = dicRwstation != null ? dicRwstation.getShortname()
					: startstationid;
			BeanUtils.setProperty(obj, "startstationidDIC", startstationidDIC);
			// 到站
			String endstationid = BeanUtils.getProperty(obj, "endstationid");
			DicRwstation dicRwstation1 = (DicRwstation) rwstationMap.get(endstationid);
			String endstationidDIC = dicRwstation1 != null ? dicRwstation1.getShortname()
					: endstationid;
			BeanUtils.setProperty(obj, "endstationidDIC", endstationidDIC);
		}
	}

	/**
	 * 为对象的省份ID换成name 此方法为通用方法，可被任何列表所用 但有条件： 1、此列表中的对象中含有provinceid这个字段；
	 * 2、此列表的对象中含有provinceidDIC这个transient字段。
	 * 
	 * @param acList
	 */
	public void bdProv(List acList) throws Exception {
		getProvinceMap();
		for (Iterator iter = acList.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String provId = BeanUtils.getProperty(obj, "provinceid");
			String provinceidDIC = (String) provinceMap.get(provId);
			BeanUtils.setProperty(obj, "provinceidDIC", provinceidDIC);
		}
	}

	/**
	 * 车型：是否启用(等同于bdIsactive方法)
	 * 
	 * @param list
	 */
	public void bdCartypeDic(List<DicCartype> list) {
		getCarkindMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DicCartype dic = (DicCartype) iter.next();
			DicCarkind obj = (DicCarkind) carkindMap.get(dic.getCarkindid());
			String carkindidDIC = obj != null ? obj.getShortname() : dic.getCarkindid();
			dic.setCarkindidDIC(carkindidDIC);
		}
	}

	/**
	 * 企业装卸设施表字典翻译:专用线ID、 计量方式ID
	 * 
	 * @param list
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void bdSpeLoadeqiupment(List list) throws Exception {
		getCrwMap();
		getLoadMeastypeMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String crwid = BeanUtils.getProperty(obj, "crwid");
			DicCorprailway dicCorprailway = (DicCorprailway) crwMap.get(crwid);
			String crwidDIC = dicCorprailway != null ? dicCorprailway.getShortname() : crwid;
			BeanUtils.setProperty(obj, "crwidDIC", crwidDIC);

			String meastypeid = BeanUtils.getProperty(obj, "meastypeid");
			SpeLoadMeastype speLoadMeastype = (SpeLoadMeastype) speLoadMeastypeMap.get(meastypeid);
			String meastypeidDIC = speLoadMeastype != null ? speLoadMeastype.getName() : meastypeid;

			BeanUtils.setProperty(obj, "meastypeidDIC", meastypeidDIC);
		}
	}

	/**
	 * 充装介质：父层
	 * 
	 * @param list
	 */
	public void bdGoodscategoryDic(List<DicGoodscategory> list) {
		getGoodscategoryMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DicGoodscategory dic = (DicGoodscategory) iter.next();
			String parentidDIC = (String) goodscategoryMap.get(dic.getParentid());
			dic.setParentidDIC(parentidDIC != null ? parentidDIC : dic.getParentid());
		}
	}

	/**
	 * 产品小类：大类、铁路品名
	 * 
	 * @param list
	 */
	public void bdProdsecondkindDic(List<DicProdsecondkind> list) {
		getProductcategoryMap();
		getDicGoodsMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DicProdsecondkind dic = (DicProdsecondkind) iter.next();
			DicProdcategory dicProdcategory = (DicProdcategory) productcategoryMap.get(dic
					.getCategoryid());
			String categoryidDIC = dicProdcategory != null ? dicProdcategory.getShortname() : dic
					.getCategoryid();
			dic.setCategoryidDIC(categoryidDIC);
			DicGoods dicGoods = (DicGoods) goodsMap.get(dic.getRwkindid());
			String rwkindidDIC = dicGoods != null ? dicGoods.getPmhz() : dic.getRwkindid();
			dic.setRwkindidDIC(rwkindidDIC);
		}
	}

	/**
	 * 用于产品字典翻译(大类、小类、品名)及产品名称的树开显示
	 * 
	 * @param list
	 */
	public void bdDicProductTree(List<DicProduct> list) {
		getProductcategoryMap();
		getProductsecondMap();
		getDicGoodsMap();
		Map map = new TreeMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DicProduct obj = (DicProduct) iter.next();
			map.put(obj.getProdid(), obj);
		}
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DicProduct obj = (DicProduct) iter.next();
			// 大类
			DicProdcategory dicProdcategory = (DicProdcategory) productcategoryMap.get(obj
					.getCategoryid());
			String categoryidDIC = dicProdcategory != null ? dicProdcategory.getShortname() : obj
					.getCategoryid();
			obj.setCategoryidDIC(categoryidDIC);
			// 小类
			DicProdsecondkind dicProdsecondkind = (DicProdsecondkind) productsecondMap.get(obj
					.getSecondkindid());
			String secondkindidDIC = dicProdsecondkind != null ? dicProdsecondkind.getShortname()
					: obj.getSecondkindid();
			obj.setSecondkindidDIC(secondkindidDIC);
			// 品名
			DicGoods dicGoods = (DicGoods) goodsMap.get(obj.getRwkindid());
			String rwkindidDIC = dicGoods != null ? dicGoods.getPmhz() : obj.getRwkindid();
			obj.setRwkindidDIC(rwkindidDIC);
			// 名称树形
			String pid = obj.getParentid();

			String fullName = obj.getFullname();
			fullName = getTreeName(pid, map) + fullName;
			obj.setFullnameTree(fullName);
		}
	}

	/**
	 * 铁路生产单位字典翻译:路局、单位类型
	 * 
	 * @param list
	 */
	public void bdDicRwdepartmentDic(List list) {
		getRwdepartypeMap();
		getRwbureauMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DicRwdepartment dic = (DicRwdepartment) iter.next();
			String tpyeidDIC = (String) rwdepartypeMap.get(dic.getTpyeid());
			String bureauidDIC = (String) rwbureauMap.get(dic.getBureauid());
			dic.setTpyeidDIC(tpyeidDIC != null ? tpyeidDIC : dic.getTpyeid());
			dic.setBureauidDIC(bureauidDIC != null ? bureauidDIC : dic.getBureauid());
		}
	}

	/**
	 * 检修车辆段字典翻译:企业、是否启用
	 * 
	 * @param list
	 */
	public void bdCorprepdepotDic(List list) {
		getCorpMap();
		getRwbureauMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DicCorprepdepot dic = (DicCorprepdepot) iter.next();
			DicSinocorp corp = (DicSinocorp) corpMap.get(dic.getCorpid());
			String corpidDIC = corp != null ? corp.getShortname() : dic.getCorpid();
			String bureauidDIC = (String) rwbureauMap.get(dic.getBureauid());
			dic.setCorpidDIC(corpidDIC);
			dic.setBureauidDIC(bureauidDIC != null ? bureauidDIC : dic.getBureauid());
		}
	}

	/**
	 * 企业专用线字典翻译:企业、过轨站、是否共用、是否启用
	 * 
	 * @param list
	 */
	public void bdCorprailwayDic(List list) {
		getCorpMap();
		getRwdepartmentMap();
		getIfMap();
		getRwbureauMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DicCorprailway dic = (DicCorprailway) iter.next();
			DicSinocorp corp = (DicSinocorp) corpMap.get(dic.getCorpid());
			String corpidDIC = corp != null ? corp.getShortname() : dic.getCorpid();
			String neareststationDIC = (String) rwdepartmentMap.get(dic.getNeareststation());
			String ispublicDIC = (String) ifMap.get(dic.getIspublic());
			String bureauidDIC = (String) rwbureauMap.get(dic.getBureauid());
			dic.setCorpidDIC(corpidDIC);
			dic.setNeareststationDIC(neareststationDIC != null ? neareststationDIC : dic
					.getNeareststation());
			dic.setIspublicDIC(ispublicDIC);
			dic.setBureauidDIC(bureauidDIC != null ? bureauidDIC : dic.getBureauid());
			dic.setPubliccorp("化工销售有限公司");
		}
	}

	/**
	 * 此方法适用于所有的EquCar列表, 可将列表中的car对象中ID值翻译成名字 调用方法：首先在service中注入DicMap，
	 * 然后就将从任何渠道得到的List<EquCar>传入， 则程序会自动进行翻译
	 * 
	 * @param carList
	 * @throws Exception
	 */
	public void bdEquCarDic(List carList) throws Exception {
		getCorpMap();
		getCarmakerMap();
		getGoodscategoryMap();
		getTankmaterialMap();
		getCarkindMap();
		for (Iterator iter = carList.iterator(); iter.hasNext();) {
			EquCar car = (EquCar) iter.next();

			DicSinocorp corp = (DicSinocorp) corpMap.get(car.getCorpid());
			String corpidDIC = corp != null ? corp.getShrinkname() : car.getCorpid();

			String makerid = car.getCarmakerid();
			String makername = (String) carmakerMap.get(makerid);

			String goodsid = car.getGoodsid();
			String goodsname = (String) goodscategoryMap.get(goodsid);

			String tankmateid = car.getTankmateid();
			String tankname = (String) tankmaterialMap.get(tankmateid);

			String carkindid = car.getCarkindid();
			DicCarkind carkind = (DicCarkind) carkindMap.get(carkindid);
			String carkindname = carkind.getShortname();

			car.setCorpidDIC(corpidDIC);
			car.setCarmakername(makername);
			car.setGoodsname(goodsname);
			car.setTankmatename(tankname);
			car.setCarkindname(carkindname);
		}
	}

	/**
	 * 此方法用于法律法规列表, 可将列表中的法律法规对象中字符串类型日期转换为相应格式
	 * 
	 * @param list
	 * @throws ParseException
	 */
	public void bdSpeLaw(List list) throws ParseException {
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			SpeLaw speLaw = (SpeLaw) iter.next();
			String issuedate = FormatUtil.rectDate(speLaw.getIssuedate());
			speLaw.setIssuedateStr(issuedate);
		}
	}

	/**
	 * 把对象的单位ID换成shortname
	 * 
	 * @param acList
	 * @throws Exception
	 */
	public void bdCorp(List acList) throws Exception {
		getCorpMap();
		getAreaMap();
		for (Iterator iter = acList.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String corpid = BeanUtils.getProperty(obj, "corpid");
			DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
			if (null != corp) {
				String corpname = corp.getShortname();
				BeanUtils.setProperty(obj, "corpname", corpname);
			} else {
				DicAreacorp areacorp = (DicAreacorp) areaMap.get(corpid);
				if (null != areacorp) {
					String corpname = areacorp.getShortname();
					BeanUtils.setProperty(obj, "corpname", corpname);
				}
			}
		}
	}

	/**
	 * 用于spePackmarkPic类的列表中注入spePackmark 注意不是简单的翻译，而是将整个对象挂在目标对象下面
	 */
	public void bdPackmark(List<SpePackmarkPic> picList) {
		getPackmarkMap();
		for (Iterator iter = picList.iterator(); iter.hasNext();) {
			SpePackmarkPic spePackmarkPic = (SpePackmarkPic) iter.next();
			spePackmarkPic
					.setSpePackmark((SpePackmark) packmarkMap.get(spePackmarkPic.getMarkid()));
		}
	}

	/**
	 * 统销计划页面显示翻译
	 * 
	 * @throws Exception
	 */
	public void bdPlaShowDic(List list) throws Exception {
		getAreaMap();
		getStatusMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String areaid = BeanUtils.getProperty(obj, "areaid");
			DicAreacorp area = (DicAreacorp) areaMap.get(areaid);
			String areaidDIC = area != null ? area.getShortname() : areaid;
			BeanUtils.setProperty(obj, "areaidDIC", areaidDIC);
			String month = BeanUtils.getProperty(obj, "month");
			String monthEnd = month.substring(0, 4) + "年" + month.substring(4) + "月";
			BeanUtils.setProperty(obj, "monthEnd", monthEnd);
			String status = BeanUtils.getProperty(obj, "status");
			String statusDIC = (String) statusMap.get(status);
			BeanUtils.setProperty(obj, "statusDIC", statusDIC);
		}
	}

	/**
	 * 非统销计划页面显示翻译
	 * 
	 * @throws Exception
	 */
	public void bdPlaMspDic(List list) throws Exception {
		getAreaMap();
		getStatusMap();
		getCorpMap();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			String corpid = BeanUtils.getProperty(obj, "corpid");
			DicSinocorp corp = (DicSinocorp) corpMap.get(corpid);
			String corpidDIC = corp != null ? corp.getShrinkname() : corpid;
			BeanUtils.setProperty(obj, "corpidDIC", corpidDIC);
			String month = BeanUtils.getProperty(obj, "month");
			String monthEnd = month.substring(0, 4) + "年" + month.substring(4) + "月";
			BeanUtils.setProperty(obj, "monthEnd", monthEnd);
			String status = BeanUtils.getProperty(obj, "status");
			String statusDIC = (String) statusMap.get(status);
			BeanUtils.setProperty(obj, "statusDIC", statusDIC);
		}
	}

	/**
	 * 得到总部单位
	 * 
	 * @return
	 */
	public Map getHeadMap() {
		Map cpMap = getCorpAllMap();
		Map hdMap = new LinkedHashMap();
		for (Iterator iter = cpMap.keySet().iterator(); iter.hasNext();) {
			String cid = (String) iter.next();
			DicSinocorp corp = (DicSinocorp) cpMap.get(cid);
			String cptp = corp.getCorptype();
			if (null != cptp && cptp.equals("0")) {
				hdMap.put(cid, corp);
			}
		}
		return hdMap;
	}

	/**
	 * 得到一般石化企业
	 * 
	 * @return
	 */
	public Map getCorpMap() {
		Map cpMap = getCorpAllMap();
		Map cMap = new LinkedHashMap();
		for (Iterator iter = cpMap.keySet().iterator(); iter.hasNext();) {
			String cid = (String) iter.next();
			DicSinocorp corp = (DicSinocorp) cpMap.get(cid);
			String cptp = corp.getCorptype();
			if (null == cptp || !cptp.equals("0")) {
				cMap.put(cid, corp);
			}
		}
		return cMap;
	}

	/**
	 * 根据区域公司id获取企业字典 如果areaid为null,则取全部企业
	 * 
	 * @param areaid
	 * @return
	 */
	public Map getAreaCorpMap(String areaid) {
		if (null == areaid) {
			return getCorpMap();
		}
		Map cpMap = getCorpAllMap();
		Map cMap = new LinkedHashMap();
		for (Iterator iter = cpMap.keySet().iterator(); iter.hasNext();) {
			String cid = (String) iter.next();
			DicSinocorp corp = (DicSinocorp) cpMap.get(cid);
			String cptp = corp.getCorptype();
			String caid = corp.getAreaid();
			if (caid.equals(areaid)) {
				cMap.put(cid, corp);
			}
		}
		return cMap;
	}

	/**
	 * 
	 * @param areaids
	 * @return
	 */
	public Map getAreaCorpByAreaIdsMap(String[] areaids) {
		if (null == areaids) {
			return getCorpMap();
		}
		Map cpMap = getCorpAllMap();
		Map cMap = new LinkedHashMap();
		List<String> areaidList = Arrays.asList(areaids);
		for (Iterator iter = cpMap.keySet().iterator(); iter.hasNext();) {
			String cid = (String) iter.next();
			DicSinocorp corp = (DicSinocorp) cpMap.get(cid);
			String cptp = corp.getCorptype();
			String caid = corp.getAreaid();
			if (areaidList.contains(caid)) {
				cMap.put(cid, corp);
			}
		}
		return cMap;
	}

	/**
	 * 用户计划状态权限
	 * 
	 * @param status
	 *            传入的状态列表，一般为查询参数
	 * @param plaMenuCode
	 *            计划的菜单代码
	 * @return statuss
	 * @throws Exception
	 * 
	 */

	public String getStatuss(String status, String plaMenuCode) throws Exception {
		String str = "";

		if (null != status && !"".equals(status)) {
			return status;
		} else {
			// 根据用户权限过滤计划状态
			SysNavimenu menu = menuService.getSysNavimenuByMenuCode(plaMenuCode);
			if (null == menu) {
				throw new Exception("权限菜单数据失！");
			}

			// 得到当前用户在某菜单下所有有权限的按钮
			Map menuAuthMap = CommUtils.initOpraMap(menu.getMenuid(), null);

			String mps = "mspstransport2";
			if (plaMenuCode.indexOf("MUPS") > -1) {
				mps = "mupstransport2";
			}

			// 如果当前用户具有提交的权限，则他可以看到未提交和驳回的计划
			// 统销
			if ("PLA_MUPS2".equals(plaMenuCode)) {
				if (null != menuAuthMap.get("pla-" + mps + "_amendStatus_1")) {
					str += "0,2,";
				}
				// 如果当前用户具有发布的权限，则他可以看到已提交的计划
				if (null != menuAuthMap.get("pla-" + mps + "_amendStatus_3")) {
					str += "1,";
				}
			}

			// 非统销，计划只有如下两个状态0,3
			if ("PLA_MSPS2".equals(plaMenuCode)) {
				if (null != menuAuthMap.get("pla-" + mps + "_amendStatus_3")) {
					str += "0,3,";
				}
			}

			// 对统销和非统销均如此：如果当前用户有浏览的权限，则可以看到所有已发布的计划
			if (null != menuAuthMap.get("pla-" + mps + "_list")) {
				str += "3,";
			}

			if (str.length() > 1) {
				str = str.substring(0, str.length() - 1);
			} else {
				str = "9";
			}
		}

		// System.out.println("DicMap.getStatuss->str=="+str);
		return str;
	}

	/**
	 * 设置全局刷新标志 应在系统管理的前端调用后，所有dicMap的get方法均要重新从数据库中加载
	 */
	public void setGlobalRefreshFlag() {
		// String flag = (String)
		// servletContext.getAttribute("globalRefreshFlag");
		servletContext.setAttribute("globalRefreshFlag", ":refresh:");

	}
}
