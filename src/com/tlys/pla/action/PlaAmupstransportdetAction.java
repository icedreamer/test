package com.tlys.pla.action;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicCorprailway;
import com.tlys.dic.model.DicCustomer;
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicProdcategory;
import com.tlys.dic.model.DicProdsecondkind;
import com.tlys.dic.model.DicProduct;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.model.DicRwstation;
import com.tlys.dic.model.DicSalecorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicTransporter;
import com.tlys.dic.model.ctl.CtlCorpreceiver;
import com.tlys.dic.model.ctl.CtlCorpsender;
import com.tlys.dic.model.ctl.CtlCorpsendwarehouse;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicCarkindService;
import com.tlys.dic.service.DicCustomerService;
import com.tlys.dic.service.DicGoodsService;
import com.tlys.dic.service.DicProductService;
import com.tlys.dic.service.DicProvinceService;
import com.tlys.dic.service.DicRwstationService;
import com.tlys.dic.service.DicSalecorpService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicTransporterService;
import com.tlys.dic.service.ctl.CtlCorpreceiverService;
import com.tlys.dic.service.ctl.CtlCorpsenderService;
import com.tlys.pla.model.PlaAmupstransport;
import com.tlys.pla.model.PlaAmupstransportdet;
import com.tlys.pla.model.PlaMupstransportdet;
import com.tlys.pla.service.PlaAmupstransportService;
import com.tlys.pla.service.PlaAmupstransportdetService;
import com.tlys.pla.service.PlaMupstransportdetService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaAmupstransportdetAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414810L;

	private PlaAmupstransportdet plaAmupstransportdet;
	private List<PlaAmupstransportdet> mstdList;
	// 计划Map，键是Id，值PlaMupstransportdet
	private Map<Long, PlaMupstransportdet> plaMupstransportdetByIdMap;
	private Long id;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private String status;
	// 是否可編輯
	private String canEdit;
	private String planno;
	// 生产企业
	private List<DicSinocorp> dicSinocorps;

	// 销售客户
	private List dicCustomers;
	// 产品大类
	private List<DicProdcategory> dicProdcategories;
	// 产品小类
	private List<DicProdsecondkind> dicProdsecondkinds;
	// 车站
	private List<DicRwstation> dicRwstations;
	// 收货人
	private List<DicProvince> dicProvinces;
	// 承运人
	private List<DicTransporter> dicTransporters;
	// 发货仓库
	private List<CtlCorpsendwarehouse> corpsendwarehouses;
	// 产品
	private List<DicProduct> dicProducts;
	// 是否石化内部企业
	private int isInnerCorp;
	private String fieldName;
	private String fieldValue;
	// 是否可編輯
	private String editIf;
	private String corpids;
	private String secondkinds;
	private String categories;
	// 收貨人
	private List<CtlCorpreceiver> receivers;
	// 發貨人
	private List<CtlCorpsender> senders;
	private Map<String, DicProduct> dicProductByIdMap = new HashMap<String, DicProduct>();
	private Map<String, DicProdsecondkind> dicProdsecondkindByIdMap = new HashMap<String, DicProdsecondkind>();
	private Map<String, DicProdcategory> dicProdcategoryByIdMap = new HashMap<String, DicProdcategory>();
	// 客户Map
	private Map<String, DicCustomer> dicCustomerByIdMap = new HashMap<String, DicCustomer>();
	// 专用线Map
	private Map<String, DicCorprailway> dicCorprailwayByIdMap;
	// 车型Map
	private Map<String, DicCartype> dicCartypeByIdMap;
	// 车站
	private Map<String, DicRwstation> dicRwstationByIdMap = new HashMap<String, DicRwstation>();
	private Map<String, DicProvince> dicProvinceMap;
	// 承运商
	private Map<String, DicTransporter> dicTransporterMap;
	// 车种
	private Map<String, DicCarkind> dicCarkindMap;
	// 发货人
	private Map<String, CtlCorpsender> ctlCorpsenderMap;
	// 铁路品名
	private Map<String, DicGoods> dicGoodsMap;
	// 销售机构
	private Map<String, DicSalecorp> dicSalecorpMap;
	// 导出条件
	private String schobjkey;
	//计划统称(列表抬头说明)
	private String plannoStr;

	@Autowired
	PlaAmupstransportdetService plaAmupstransportdetService;
	@Autowired
	PlaMupstransportdetService plaMupstransportdetService;
	@Autowired
	DicProductService dicProductService;

	@Autowired
	DicCustomerService dicCustomerService;

	@Autowired
	DicMap dicMap;
	@Autowired
	DicRwstationService dicRwstationService;
	@Autowired
	DicProvinceService dicProvinceService;
	@Autowired
	DicTransporterService dicTransporterService;
	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicGoodsService dicGoodsService;
	@Autowired
	DicCarkindService dicCarkindService;
	@Autowired
	CtlCorpreceiverService ctlCorpreceiverService;
	@Autowired
	CtlCorpsenderService ctlCorpsenderService;
	@Autowired
	DicSalecorpService dicSalecorpService;
	@Autowired
	PlaAmupstransportService plaAmupstransportService;

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaAmupstransportdet = (PlaAmupstransportdet) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaAmupstransportdetService.expExcel(plaAmupstransportdet, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	/**
	 * 车型
	 * 
	 * @return
	 */
	public String cartypes() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);// plaAmupstransportdetService.load(id);
		dicCartypeByIdMap = dicMap.getCartypeMap();
		return "cartypes";
	}

	/**
	 * 专用线
	 * 
	 * @return
	 * @throws Exception
	 */
	public String crws() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		dicCorprailwayByIdMap = dicMap.getCrwMap();
		return "crws";
	}

	/**
	 * 发站
	 * 
	 * @return
	 * @throws Exception
	 */
	public String startstations() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		String corpid = "";
		if (null != plaAmupstransportdet) {
			corpid = plaAmupstransportdet.getCorpid();
		}
		dicRwstations = dicRwstationService.findDicRwstation(corpid);
		return "startstations";
	}

	/**
	 * 到站
	 * 
	 * @return
	 */
	public String customerstations() throws Exception {
		plaAmupstransportdet = plaAmupstransportdetService.load(plaAmupstransportdet.getId());
		String customerid = "";
		if (null != plaAmupstransportdet) {
			customerid = plaAmupstransportdet.getCustomerid();
		}
		dicRwstations = dicRwstationService.findCustomerDicRwstation(customerid);
		return "customerstations";
	}

	/**
	 * 客户列表（页面显示为下拉列表)
	 * 
	 * @return
	 */
	public String customers() throws Exception {

		Long id = plaAmupstransportdet.getId();
		if (logger.isDebugEnabled()) {
			logger.debug("id : " + id);
		}
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		String corpid = "";
		if (null != plaAmupstransportdet) {
			corpid = plaAmupstransportdet.getCorpid().trim();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("corpid : " + corpid);
		}
		if (1 == isInnerCorp) {
			dicCustomers = dicSinocorpService.find(new DicSinocorp());
		} else {
			dicCustomers = dicCustomerService.findDicCustomer(corpid);
		}
		return "customers";
	}

	/**
	 * 送达省份
	 * 
	 * @return
	 */
	public String provinces() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		String customerid = "";
		if (null != plaAmupstransportdet) {
			customerid = plaAmupstransportdet.getCustomerid();
		}
		dicProvinces = dicProvinceService.findDicProvince(customerid);

		return "provinces";
	}

	/**
	 * 承运人
	 * 
	 * @return
	 */
	public String transporters() {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		String customerid = "";
		String corpid = "";
		if (null != plaAmupstransportdet) {
			customerid = plaAmupstransportdet.getCustomerid().trim();
			corpid = plaAmupstransportdet.getCorpid().trim();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("customerid : " + customerid);
			logger.debug("corpid : " + corpid);
		}
		dicTransporters = dicTransporterService.findTransporter(customerid, corpid);
		if (logger.isDebugEnabled()) {
			logger.debug("dicTransporters : " + dicTransporters);
		}
		return "transporters";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		plaAmupstransportdetService.delete(plaAmupstransportdet);
		msg = new Msg(Msg.SUCCESS, "删除成功");
		return MSG;
	}

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/pla/pla-amupstransportdet!list.action?plannoStr="+plannoStr;
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if (null != plaAmupstransportdet) {
				plaAmupstransportdet.setCorpids(null != corpids && !"".equals(corpids) ? corpids.split(",") : null);
				plaAmupstransportdet.setSecondkinds(null != secondkinds && !"".equals(secondkinds) ? secondkinds
						.split(",") : null);
				plaAmupstransportdet.setCategories(null != categories && !"".equals(categories) ? categories.split(",")
						: null);
				String schObjKey = "plaAmupstransportdet_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, plaAmupstransportdet);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaAmupstransportdet = (PlaAmupstransportdet) getSessionAttr(pageCtr.getSchObjKey());
			}
		}

		pageCtr.setPageUrl(pageUrl);
		plaAmupstransportdetService.find(plaAmupstransportdet, pageCtr);

		// 调整计划的记录
		mstdList = pageCtr.getRecords();
		// 原计划Id的列表
		List<Long> planIds = new ArrayList<Long>();
		if (null != mstdList && !mstdList.isEmpty()) {
			for (PlaAmupstransportdet plaAmupstransportdet : mstdList) {
				planIds.add(plaAmupstransportdet.getOldplanid());
			}
		}
		// 原计划
		List<PlaMupstransportdet> plaMupstransportdets = null;
		if (null != planIds && !planIds.isEmpty()) {
			plaMupstransportdets = plaMupstransportdetService.findPlaMupstransportdetByIds(planIds);
		}
		if (null != plaMupstransportdets && !plaMupstransportdets.isEmpty()) {
			plaMupstransportdetByIdMap = new HashMap<Long, PlaMupstransportdet>();
			for (PlaMupstransportdet plaMupstransportdet : plaMupstransportdets) {
				plaMupstransportdetByIdMap.put(plaMupstransportdet.getId(), plaMupstransportdet);
			}
		}
		String planno = plaAmupstransportdet.getPlanno();
		PlaAmupstransport plaAmupstransport = plaAmupstransportService.load(planno);
		if (null != plaAmupstransport) {
			String status = plaAmupstransport.getStatus();
			if (null != status && ("0".equals(status) || "2".equals(status))) {
				canEdit = "1";
			} else {
				canEdit = "0";
			}
		}

		dicProductByIdMap = dicMap.getProductMap();
		dicProdsecondkindByIdMap = dicMap.getProductsecondMap();
		dicCustomerByIdMap = dicMap.getCustomerMap();
		dicProdcategoryByIdMap = dicMap.getProductcategoryMap();
		dicCorprailwayByIdMap = dicMap.getCrwMap();
		dicRwstationByIdMap = dicMap.getRwstationMap();
		dicProvinceMap = dicMap.getProvinceMap();
		dicTransporterMap = dicMap.getTransporterMap();
		dicGoodsMap = dicMap.getDicGoodsMap();
		dicCarkindMap = dicMap.getCarkindMap();
		ctlCorpsenderMap = dicMap.getSenderMap();
		dicSalecorpMap = dicMap.getSalecorpMap();
		return "list";
	}

	/**
	 * 产品名称
	 * 
	 * @return
	 */
	public String products() {
		dicProductByIdMap = dicMap.getProductMap();
		Set<String> idSet = dicProductByIdMap.keySet();
		dicProducts = new ArrayList<DicProduct>();
		for (Iterator iterator = idSet.iterator(); iterator.hasNext();) {
			String productId = (String) iterator.next();
			DicProduct dicProduct = dicProductByIdMap.get(productId);
			if (null != dicProduct.getSecondkindid()
					&& dicProduct.getSecondkindid().equals(plaAmupstransportdet.getProductsecondid())) {
				dicProducts.add(dicProduct);
			}
		}
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		if (logger.isDebugEnabled()) {
			logger.debug("dicProducts : " + dicProducts);
		}
		return "products";
	}

	/**
	 * 产品大类
	 * 
	 * @return
	 */
	public String productcategories() {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		dicProdcategoryByIdMap = dicMap.getProductcategoryMap();
		return "productcategories";
	}

	/**
	 * 产品小类(页面显示为下拉列表)
	 * 
	 * @return
	 */
	public String productseconds() {
		dicProdsecondkindByIdMap = dicMap.getProductsecondMap();
		dicProdsecondkinds = new ArrayList<DicProdsecondkind>();
		Set<String> idSet = dicProdsecondkindByIdMap.keySet();
		for (Iterator<String> iterator = idSet.iterator(); iterator.hasNext();) {
			String secondkindid = iterator.next();
			DicProdsecondkind dicProdsecondkind = dicProdsecondkindByIdMap.get(secondkindid);
			String custorCategoryId = dicProdsecondkind.getCategoryid();
			if (null != custorCategoryId && custorCategoryId.equals(plaAmupstransportdet.getProductcategoryid())) {
				dicProdsecondkinds.add(dicProdsecondkind);
			}
		}
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		return "productseconds";
	}

	// -------------------------------------------------

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		if (logger.isDebugEnabled()) {
			logger.debug("fieldName : " + fieldName);
		}
		Class<?> clazz = plaAmupstransportdet.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldType = field.getType().getSimpleName();
			if (field.getName().equals(fieldName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fieldName custor : " + fieldName);
					logger.debug("fieldType custor : " + fieldType);
				}
				if (fieldType.equals("Double")) {
					fields[i].set(plaAmupstransportdet, Double.valueOf(fieldValue));
				} else if (fieldType.equals("Long")) {
					fields[i].set(plaAmupstransportdet, Long.valueOf(fieldValue));
				} else {
					fields[i].set(plaAmupstransportdet, fieldValue);
				}
				if (fieldName.equals("customerid")) {
					if (isInnerCorp == 1) {
						DicSinocorp dicSinocorp = dicSinocorpService.load(fieldValue);
						if (null != dicSinocorp) {
							if (logger.isDebugEnabled()) {
								logger.debug("corpid : " + dicSinocorp.getCorpid());
								logger.debug("corp.name : " + dicSinocorp.getShortname());
							}
							plaAmupstransportdet.setCustomerid(dicSinocorp.getCorpid());
							plaAmupstransportdet.setCoustomershortname(dicSinocorp.getShortname());
						}
					} else if (isInnerCorp == 0) {
						DicCustomer dicCustomer = dicCustomerService.load(fieldValue);
						if (null != dicCustomer) {
							plaAmupstransportdet.setCustomerid(dicCustomer.getCustomerid());
							plaAmupstransportdet.setCoustomershortname(dicCustomer.getShortname());
						}
					}
				} else if (fieldName.equals("coustomershortname")) {
					DicSinocorp dicSinocorp = dicSinocorpService.getDicSinocorpByName(fieldValue);
					DicCustomer dicCustomer = null;
					if (null == dicSinocorp) {
						dicCustomer = dicCustomerService.getDicCustomerByName(fieldValue);
						if (null == dicCustomer) {
							dicCustomer = new DicCustomer();
							dicCustomer.setShortname(fieldValue);
							dicCustomer.setFullname(fieldValue);
							dicCustomer.setShrinkname(fieldValue);
							dicCustomer.setProvinceid("0");
							dicCustomer.setIsreceiver("0");
							dicCustomer.setIssender("0");
							dicCustomer.setIsactive("1");
							dicCustomer.setIsbelongsino("0");
							dicCustomer.setCreatedtime(new Date());
							dicCustomerService.save(dicCustomer, true);
						}
						plaAmupstransportdet.setCustomerid(dicCustomer.getCustomerid());
						plaAmupstransportdet.setCoustomershortname(dicCustomer.getShortname());
					} else {
						plaAmupstransportdet.setCustomerid(dicSinocorp.getCorpid());
						plaAmupstransportdet.setCoustomershortname(dicSinocorp.getShortname());

					}
					if (null != dicCustomer) {
						plaAmupstransportdet.setCoustomershortname(dicCustomer.getShortname());
					}
				} else if (fieldName.equals("corpid")) {
					DicSinocorp dicSinocorp = dicSinocorpService.load(fieldValue);
					if (null != dicSinocorp) {
						plaAmupstransportdet.setCorpshortname(dicSinocorp.getShortname());
						String areaid = dicSinocorp.getAreaid();
						plaAmupstransportdet.setAreaid(areaid);
						DicAreacorp dicAreacorp = dicAreacorpService.load(areaid);
						if (null != dicAreacorp) {
							// plaAmupstransportdet.setAreashortname(dicAreacorp.getShortname());
						}
					}
				} else if (fieldName.equals("prodductid")) {
					DicProduct dicProduct = dicProductService.load(fieldValue);
					if (null != dicProduct) {
						plaAmupstransportdet.setProdductid(fieldValue);
						plaAmupstransportdet.setProductcategoryid(dicProduct.getCategoryid());
						plaAmupstransportdet.setProductsecondid(dicProduct.getSecondkindid());
					}
				} else if (fieldName.equals("rwkindid")) {
					DicGoods dicGoods = dicGoodsService.getDicGoods(fieldValue);
					if (null != dicGoods) {
						plaAmupstransportdet.setRkindname(dicGoods.getPmhz());
					}
				} else if (fieldName.equals("receiverid")) {
					CtlCorpreceiver ctlCorpreceiver = ctlCorpreceiverService.getCtlCorpreceiver(fieldValue);
					if (null != ctlCorpreceiver) {
						plaAmupstransportdet.setReceivername(ctlCorpreceiver.getReceivershortname());
					}
				} else if (fieldName.equals("receivername")) {
					CtlCorpreceiver ctlCorpreceiver = new CtlCorpreceiver();
					ctlCorpreceiver.setCorpid(plaAmupstransportdet.getCustomerid());
					ctlCorpreceiver.setCorpshortname(plaAmupstransportdet.getCoustomershortname());
					ctlCorpreceiver.setCreatedtime(new Date());
					ctlCorpreceiver.setIsactive("1");
					String receiverNextReq = ctlCorpreceiverService.getReceiverNextReq();
					if (logger.isDebugEnabled()) {
						logger.debug("receiverNextReq : " + receiverNextReq);
					}
					ctlCorpreceiver.setReceiverid(receiverNextReq);
					ctlCorpreceiver.setReceivername(fieldValue);
					ctlCorpreceiver.setReceivershortname(fieldValue);
					ctlCorpreceiverService.save(ctlCorpreceiver);
				} else if (fieldName.equals("senderid")) {

				} else if (fieldName.equals("sendername")) {
					CtlCorpsender ctlCorpsender = new CtlCorpsender();
					ctlCorpsender.setCorpid(plaAmupstransportdet.getCorpid());
					ctlCorpsender.setCorpshortname(plaAmupstransportdet.getCorpshortname());
					ctlCorpsender.setCreatedtime(new Date());
					ctlCorpsender.setIsactive("1");
					String senderNextReq = ctlCorpsenderService.getSenderNextReq();
					if (logger.isDebugEnabled()) {
						logger.debug("senderNextReq : " + senderNextReq);
					}
					ctlCorpsender.setSenderid(senderNextReq);
					ctlCorpsender.setSendername(fieldValue);
					ctlCorpsender.setSendershortname(fieldValue);
					ctlCorpsenderService.save(ctlCorpsender);
					dicMap.dicAlter("ctlCorpsender");
					plaAmupstransportdet.setSenderid(senderNextReq);
				}
				break;
			}
		}
		// 修改记录
		plaAmupstransportdetService.update(plaAmupstransportdet);
		msg = new Msg(Msg.SUCCESS, "操作成功!");
		if (logger.isDebugEnabled()) {
			logger.debug("save complete.");
		}
		return MSG;
	}

	/**
	 * 生成企业
	 * 
	 * @return
	 * @throws Exception
	 */
	public String corps() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		dicSinocorps = dicSinocorpService.findDicSinocorp();
		return "corps";
	}

	/**
	 * 发货仓库
	 * 
	 * @return
	 */
	public String sendwarehouses() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		String corpid = "";
		if (null != plaAmupstransportdet) {
			corpid = plaAmupstransportdet.getCorpid().trim();
		}
		corpsendwarehouses = plaAmupstransportdetService.findCtlCorpsendwarehouse(corpid);
		return "sendwarehouses";
	}

	/**
	 * 车种
	 * 
	 * @return
	 * @throws Exception
	 */
	public String carkinds() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		// dicCarkinds = dicCarkindService.findAll();
		dicCarkindMap = dicMap.getCarkindMap();
		return "carkinds";
	}

	/**
	 * 铁路品名
	 * 
	 * @return
	 */
	public String rwkinds() {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		dicGoodsMap = dicMap.getDicGoodsMap();
		return "rwkinds";
	}

	/**
	 * 收貨人
	 * 
	 * @return
	 */
	public String receivers() {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		String corpid = "";
		if (null != plaAmupstransportdet) {
			corpid = plaAmupstransportdet.getCustomerid();
		}
		receivers = ctlCorpreceiverService.findCtlCorpreceiverByCorpid(corpid);

		return "receivers";
	}

	/**
	 * 發貨人
	 * 
	 * @return
	 */
	public String senders() {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		String corpid = "";
		if (null != plaAmupstransportdet) {
			corpid = plaAmupstransportdet.getCorpid();
		}
		senders = ctlCorpsenderService.findCtlCorpsender(corpid);
		return "senders";
	}

	/**
	 * 销售机构
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sales() throws Exception {
		Long id = plaAmupstransportdet.getId();
		plaAmupstransportdet = plaAmupstransportdetService.load(id);
		dicSalecorpMap = dicMap.getSalecorpMap();
		return "sales";
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_AMUPS");
	}

	public PlaAmupstransportdet getPlaAmupstransportdet() {
		return plaAmupstransportdet;
	}

	public List<PlaAmupstransportdet> getMstdList() {
		return mstdList;
	}

	public File getUpload() {
		return upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public String getPlanno() {
		return planno;
	}

	public List<DicSinocorp> getDicSinocorps() {
		return dicSinocorps;
	}

	public List getDicCustomers() {
		return dicCustomers;
	}

	public List<DicProdcategory> getDicProdcategories() {
		return dicProdcategories;
	}

	public List<DicProdsecondkind> getDicProdsecondkinds() {
		return dicProdsecondkinds;
	}

	public List<DicRwstation> getDicRwstations() {
		return dicRwstations;
	}

	public List<DicProvince> getDicProvinces() {
		return dicProvinces;
	}

	public List<DicTransporter> getDicTransporters() {
		return dicTransporters;
	}

	public List<CtlCorpsendwarehouse> getCorpsendwarehouses() {
		return corpsendwarehouses;
	}

	public List<DicProduct> getDicProducts() {
		return dicProducts;
	}

	public int getIsInnerCorp() {
		return isInnerCorp;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public String getEditIf() {
		return editIf;
	}

	public List<CtlCorpreceiver> getReceivers() {
		return receivers;
	}

	public List<CtlCorpsender> getSenders() {
		return senders;
	}

	public Map<String, DicProduct> getDicProductByIdMap() {
		return dicProductByIdMap;
	}

	public Map<String, DicProdsecondkind> getDicProdsecondkindByIdMap() {
		return dicProdsecondkindByIdMap;
	}

	public Map<String, DicProdcategory> getDicProdcategoryByIdMap() {
		return dicProdcategoryByIdMap;
	}

	public Map<String, DicCustomer> getDicCustomerByIdMap() {
		return dicCustomerByIdMap;
	}

	public Map<String, DicCorprailway> getDicCorprailwayByIdMap() {
		return dicCorprailwayByIdMap;
	}

	public Map<String, DicCartype> getDicCartypeByIdMap() {
		return dicCartypeByIdMap;
	}

	public Map<String, DicRwstation> getDicRwstationByIdMap() {
		return dicRwstationByIdMap;
	}

	public Map<String, DicProvince> getDicProvinceMap() {
		return dicProvinceMap;
	}

	public Map<String, DicTransporter> getDicTransporterMap() {
		return dicTransporterMap;
	}

	public Map<String, DicCarkind> getDicCarkindMap() {
		return dicCarkindMap;
	}

	public Map<String, CtlCorpsender> getCtlCorpsenderMap() {
		return ctlCorpsenderMap;
	}

	public Map<String, DicGoods> getDicGoodsMap() {
		return dicGoodsMap;
	}

	public Map<String, DicSalecorp> getDicSalecorpMap() {
		return dicSalecorpMap;
	}

	public void setPlaAmupstransportdet(PlaAmupstransportdet plaAmupstransportdet) {
		this.plaAmupstransportdet = plaAmupstransportdet;
	}

	public void setMstdList(List<PlaAmupstransportdet> mstdList) {
		this.mstdList = mstdList;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setPlanno(String planno) {
		this.planno = planno;
	}

	public void setDicSinocorps(List<DicSinocorp> dicSinocorps) {
		this.dicSinocorps = dicSinocorps;
	}

	public void setDicCustomers(List dicCustomers) {
		this.dicCustomers = dicCustomers;
	}

	public void setDicProdcategories(List<DicProdcategory> dicProdcategories) {
		this.dicProdcategories = dicProdcategories;
	}

	public void setDicProdsecondkinds(List<DicProdsecondkind> dicProdsecondkinds) {
		this.dicProdsecondkinds = dicProdsecondkinds;
	}

	public void setDicRwstations(List<DicRwstation> dicRwstations) {
		this.dicRwstations = dicRwstations;
	}

	public void setDicProvinces(List<DicProvince> dicProvinces) {
		this.dicProvinces = dicProvinces;
	}

	public void setDicTransporters(List<DicTransporter> dicTransporters) {
		this.dicTransporters = dicTransporters;
	}

	public void setCorpsendwarehouses(List<CtlCorpsendwarehouse> corpsendwarehouses) {
		this.corpsendwarehouses = corpsendwarehouses;
	}

	public void setDicProducts(List<DicProduct> dicProducts) {
		this.dicProducts = dicProducts;
	}

	public void setIsInnerCorp(int isInnerCorp) {
		this.isInnerCorp = isInnerCorp;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void setEditIf(String editIf) {
		this.editIf = editIf;
	}

	public void setReceivers(List<CtlCorpreceiver> receivers) {
		this.receivers = receivers;
	}

	public void setSenders(List<CtlCorpsender> senders) {
		this.senders = senders;
	}

	public void setDicProductByIdMap(Map<String, DicProduct> dicProductByIdMap) {
		this.dicProductByIdMap = dicProductByIdMap;
	}

	public void setDicProdsecondkindByIdMap(Map<String, DicProdsecondkind> dicProdsecondkindByIdMap) {
		this.dicProdsecondkindByIdMap = dicProdsecondkindByIdMap;
	}

	public void setDicProdcategoryByIdMap(Map<String, DicProdcategory> dicProdcategoryByIdMap) {
		this.dicProdcategoryByIdMap = dicProdcategoryByIdMap;
	}

	public void setDicCustomerByIdMap(Map<String, DicCustomer> dicCustomerByIdMap) {
		this.dicCustomerByIdMap = dicCustomerByIdMap;
	}

	public void setDicCorprailwayByIdMap(Map<String, DicCorprailway> dicCorprailwayByIdMap) {
		this.dicCorprailwayByIdMap = dicCorprailwayByIdMap;
	}

	public void setDicCartypeByIdMap(Map<String, DicCartype> dicCartypeByIdMap) {
		this.dicCartypeByIdMap = dicCartypeByIdMap;
	}

	public void setDicRwstationByIdMap(Map<String, DicRwstation> dicRwstationByIdMap) {
		this.dicRwstationByIdMap = dicRwstationByIdMap;
	}

	public void setDicProvinceMap(Map<String, DicProvince> dicProvinceMap) {
		this.dicProvinceMap = dicProvinceMap;
	}

	public void setDicTransporterMap(Map<String, DicTransporter> dicTransporterMap) {
		this.dicTransporterMap = dicTransporterMap;
	}

	public void setDicCarkindMap(Map<String, DicCarkind> dicCarkindMap) {
		this.dicCarkindMap = dicCarkindMap;
	}

	public void setCtlCorpsenderMap(Map<String, CtlCorpsender> ctlCorpsenderMap) {
		this.ctlCorpsenderMap = ctlCorpsenderMap;
	}

	public void setDicGoodsMap(Map<String, DicGoods> dicGoodsMap) {
		this.dicGoodsMap = dicGoodsMap;
	}

	public void setDicSalecorpMap(Map<String, DicSalecorp> dicSalecorpMap) {
		this.dicSalecorpMap = dicSalecorpMap;
	}

	public Map<Long, PlaMupstransportdet> getPlaMupstransportdetByIdMap() {
		return plaMupstransportdetByIdMap;
	}

	public void setPlaMupstransportdetByIdMap(Map<Long, PlaMupstransportdet> plaMupstransportdetByIdMap) {
		this.plaMupstransportdetByIdMap = plaMupstransportdetByIdMap;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCorpids() {
		return corpids;
	}

	public String getSecondkinds() {
		return secondkinds;
	}

	public String getCategories() {
		return categories;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	public void setSecondkinds(String secondkinds) {
		this.secondkinds = secondkinds;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

	public String getPlannoStr() {
		return plannoStr;
	}

	public void setPlannoStr(String plannoStr) {
		this.plannoStr = plannoStr;
	}

}
