package com.tlys.pla.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.DicMap;
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
import com.tlys.dic.service.ctl.CtlCustomerreceiverService;
import com.tlys.pla.model.PlaAmupstransportdet;
import com.tlys.pla.model.PlaMspstransportdet2;
import com.tlys.pla.model.PlaMupstransportdet2;
import com.tlys.pla.service.PlaAmupstransportdetService;
import com.tlys.pla.service.PlaMspstransportdet2Service;
import com.tlys.pla.service.PlaMupstransportdet2Service;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaDictionary2Action extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8975289571719604641L;
	private Long id;
	private String diccorpid;
	private String corpid;
	private String areaid;
	private String customerid;
	private int isInnerCorp;
	private String productsecondid = "";
	private String productCategoryid = "";
	private String productid;
	private String detname;
	private String planid;
	private String detMup = "mupstransportdet2";
	private String detMsp = "mspstransportdet2";
	private String detAmup = "amupstransportdet2";
	private PlaMupstransportdet2 plaMupstransportdet2;
	private PlaMspstransportdet2 plaMspstransportdet2;
	private PlaAmupstransportdet plaAmupstransportdet;
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

	// 收貨人
	private List receivers;
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

	@Autowired
	DicMap dicMap;

	@Autowired
	DicProductService dicProductService;

	@Autowired
	DicCustomerService dicCustomerService;

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
	CtlCustomerreceiverService ctlCustomerreceiverService;
	@Autowired
	CtlCorpsenderService ctlCorpsenderService;
	@Autowired
	DicSalecorpService dicSalecorpService;
	@Autowired
	PlaMupstransportdet2Service plaMupstransportdet2Service;
	@Autowired
	PlaMspstransportdet2Service plaMspstransportdet2Service;
	@Autowired
	PlaAmupstransportdetService plaAmupstransportdetService;

	/**
	 * 发站
	 * 
	 * @return
	 * @throws Exception
	 */
	public String startstations() throws Exception {
		String corpid = "";
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				planid = plaMupstransportdet2.getStartstationid();
				corpid = plaMupstransportdet2.getCorpid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				planid = plaMspstransportdet2.getStartstationid();
				corpid = plaMspstransportdet2.getCorpid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				planid = plaAmupstransportdet.getStartstationid();
				corpid = plaAmupstransportdet.getCorpid();
			}
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
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				planid = plaMupstransportdet2.getEndstationid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				planid = plaMspstransportdet2.getEndstationid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				planid = plaAmupstransportdet.getEndstationid();
			}
		}
		dicRwstations = dicRwstationService
				.findCustomerDicRwstation(customerid);
		return "customerstations";
	}

	/**
	 * 客户列表（页面显示为下拉列表)
	 * 
	 * @return
	 */
	public String customers() throws Exception {
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				diccorpid = plaMupstransportdet2.getCorpid();
				planid = plaMupstransportdet2.getCustomerid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				diccorpid = plaMspstransportdet2.getCorpid();
				planid = plaMspstransportdet2.getCustomerid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				diccorpid = plaAmupstransportdet.getCorpid();
				planid = plaAmupstransportdet.getCustomerid();
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("diccorpid : " + diccorpid);
		}
		if (1 == isInnerCorp) {
			dicCustomers = dicSinocorpService.find(new DicSinocorp());
		} else {

			dicCustomers = dicCustomerService.findDicCustomer(diccorpid);
		}
		return "customers";
	}

	/**
	 * 产品名称
	 * 
	 * @return
	 */
	public String products() {
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				productid = plaMupstransportdet2.getProdductid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				productid = plaMspstransportdet2.getProdductid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				productid = plaAmupstransportdet.getProdductid();
			}
		}
		dicProductByIdMap = dicMap.getProductMap();
		Set<String> idSet = dicProductByIdMap.keySet();
		dicProducts = new ArrayList<DicProduct>();
		for (Iterator iterator = idSet.iterator(); iterator.hasNext();) {
			String productId = (String) iterator.next();
			DicProduct dicProduct = dicProductByIdMap.get(productId);
			if (null != dicProduct.getSecondkindid()
					&& dicProduct.getSecondkindid().equals(productsecondid)) {
				dicProducts.add(dicProduct);
			}
		}
		return "products";
	}

	/**
	 * 产品大类
	 * 
	 * @return
	 */
	public String productcategories() {
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				productCategoryid = plaMupstransportdet2.getProductcategoryid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				productCategoryid = plaMspstransportdet2.getProductcategoryid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				productCategoryid = plaAmupstransportdet.getProductcategoryid();
			}
		}
		dicProdcategoryByIdMap = dicMap.getProductcategoryMap();
		return "productcategories";
	}

	/**
	 * 产品小类(页面显示为下拉列表)
	 * 
	 * @return
	 */
	public String productseconds() {
		if (logger.isDebugEnabled()) {
			logger.debug("detname : " + detname);
		}
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				productsecondid = plaMupstransportdet2.getProductsecondid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				productsecondid = plaMspstransportdet2.getProductsecondid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				productsecondid = plaAmupstransportdet.getProductsecondid();
			}
		}
		dicProdsecondkindByIdMap = dicMap.getProductsecondMap();
		dicProdsecondkinds = new ArrayList<DicProdsecondkind>();
		Set<String> idSet = dicProdsecondkindByIdMap.keySet();
		for (Iterator iterator = idSet.iterator(); iterator.hasNext();) {
			String secondkindid = (String) iterator.next();
			DicProdsecondkind dicProdsecondkind = dicProdsecondkindByIdMap
					.get(secondkindid);
			String custorCategoryId = dicProdsecondkind.getCategoryid();
			if (null != custorCategoryId
					&& custorCategoryId.equals(productCategoryid)) {
				dicProdsecondkinds.add(dicProdsecondkind);
			}
		}
		return "productseconds";
	}

	/**
	 * 生成企业
	 * 
	 * @return
	 * @throws Exception
	 */
	public String corps() throws Exception {
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				diccorpid = plaMupstransportdet2.getCorpid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				diccorpid = plaMspstransportdet2.getCorpid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				diccorpid = plaAmupstransportdet.getCorpid();
			}
		}
		dicSinocorps = dicSinocorpService.findDicSinocorp(areaid, corpid);
		return "corps";
	}

	/**
	 * 发货仓库
	 * 
	 * @return
	 */
	public String sendwarehouses() throws Exception {
		corpsendwarehouses = plaMupstransportdet2Service
				.findCtlCorpsendwarehouse(diccorpid);
		return "sendwarehouses";
	}

	/**
	 * 车种
	 * 
	 * @return
	 * @throws Exception
	 */
	public String carkinds() throws Exception {
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				planid = plaMupstransportdet2.getCarkindid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				planid = plaMspstransportdet2.getCarkindid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				planid = plaAmupstransportdet.getCarkindid();
			}
		}
		dicCarkindMap = dicMap.getCarkindMap();
		return "carkinds";
	}

	/**
	 * 铁路品名
	 * 
	 * @return
	 */
	public String rwkinds() {
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				planid = plaMupstransportdet2.getRwkindid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				planid = plaMspstransportdet2.getRwkindid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				planid = plaAmupstransportdet.getRwkindid();
			}
		}
		dicGoodsMap = dicMap.getDicGoodsMap();
		return "rwkinds";
	}

	/**
	 * 收貨人
	 * 
	 * @return
	 */
	public String receivers() {
		String customerid = "";
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				planid = plaMupstransportdet2.getReceiverid();
				customerid = plaMupstransportdet2.getCustomerid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				planid = plaMspstransportdet2.getReceiverid();
				customerid = plaMspstransportdet2.getCustomerid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				planid = plaAmupstransportdet.getReceiverid();
				customerid = plaAmupstransportdet.getCustomerid();
			}
		}
		if (isInnerCorp == 1) {
			receivers = ctlCorpreceiverService.findCtlCorpreceiverByCorpid("");
		} else {
			receivers = ctlCustomerreceiverService
					.findCtlCustomerreceiver(customerid);
		}
		return "receivers";
	}

	/**
	 * 發貨人
	 * 
	 * @return
	 */
	public String senders() {
		String corpid = "";
		if (detname.equals(detMup)) {
			plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
			if (null != plaMupstransportdet2) {
				planid = plaMupstransportdet2.getSenderid();
				corpid = plaMupstransportdet2.getCorpid();
			}
		} else if (detname.equals(detMsp)) {
			plaMspstransportdet2 = plaMspstransportdet2Service.load(id);
			if (null != plaMspstransportdet2) {
				planid = plaMspstransportdet2.getSenderid();
				corpid = plaMspstransportdet2.getCorpid();
			}
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			if (null != plaAmupstransportdet) {
				planid = plaAmupstransportdet.getSenderid();
				corpid = plaAmupstransportdet.getCorpid();
			}
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
		plaMupstransportdet2 = plaMupstransportdet2Service.load(id);
		if (null != plaMupstransportdet2) {
			planid = plaMupstransportdet2.getSaledepartment();
		} else if (detname.equals(detAmup)) {
			plaAmupstransportdet = plaAmupstransportdetService.load(id);
			planid = plaAmupstransportdet.getSaledepartment();
		}
		dicSalecorpMap = dicMap.getSalecorpMap();
		return "sales";
	}

	public Long getId() {
		return id;
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

	public List getReceivers() {
		return receivers;
	}

	public List<CtlCorpsender> getSenders() {
		return senders;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setCorpsendwarehouses(
			List<CtlCorpsendwarehouse> corpsendwarehouses) {
		this.corpsendwarehouses = corpsendwarehouses;
	}

	public void setDicProducts(List<DicProduct> dicProducts) {
		this.dicProducts = dicProducts;
	}

	public void setReceivers(List receivers) {
		this.receivers = receivers;
	}

	public void setSenders(List<CtlCorpsender> senders) {
		this.senders = senders;
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

	public void setDicProductByIdMap(Map<String, DicProduct> dicProductByIdMap) {
		this.dicProductByIdMap = dicProductByIdMap;
	}

	public void setDicProdsecondkindByIdMap(
			Map<String, DicProdsecondkind> dicProdsecondkindByIdMap) {
		this.dicProdsecondkindByIdMap = dicProdsecondkindByIdMap;
	}

	public void setDicProdcategoryByIdMap(
			Map<String, DicProdcategory> dicProdcategoryByIdMap) {
		this.dicProdcategoryByIdMap = dicProdcategoryByIdMap;
	}

	public void setDicCustomerByIdMap(
			Map<String, DicCustomer> dicCustomerByIdMap) {
		this.dicCustomerByIdMap = dicCustomerByIdMap;
	}

	public void setDicCorprailwayByIdMap(
			Map<String, DicCorprailway> dicCorprailwayByIdMap) {
		this.dicCorprailwayByIdMap = dicCorprailwayByIdMap;
	}

	public void setDicCartypeByIdMap(Map<String, DicCartype> dicCartypeByIdMap) {
		this.dicCartypeByIdMap = dicCartypeByIdMap;
	}

	public void setDicRwstationByIdMap(
			Map<String, DicRwstation> dicRwstationByIdMap) {
		this.dicRwstationByIdMap = dicRwstationByIdMap;
	}

	public void setDicProvinceMap(Map<String, DicProvince> dicProvinceMap) {
		this.dicProvinceMap = dicProvinceMap;
	}

	public void setDicTransporterMap(
			Map<String, DicTransporter> dicTransporterMap) {
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

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public int getIsInnerCorp() {
		return isInnerCorp;
	}

	public void setIsInnerCorp(int isInnerCorp) {
		this.isInnerCorp = isInnerCorp;
	}

	public String getProductsecondid() {
		return productsecondid;
	}

	public void setProductsecondid(String productsecondid) {
		this.productsecondid = productsecondid;
	}

	public String getProductCategoryid() {
		return productCategoryid;
	}

	public void setProductCategoryid(String productCategoryid) {
		this.productCategoryid = productCategoryid;
	}

	public String getDetname() {
		return detname;
	}

	public void setDetname(String detname) {
		this.detname = detname;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getDiccorpid() {
		return diccorpid;
	}

	public void setDiccorpid(String diccorpid) {
		this.diccorpid = diccorpid;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public PlaMupstransportdet2 getPlaMupstransportdet2() {
		return plaMupstransportdet2;
	}

	public PlaMspstransportdet2 getPlaMspstransportdet2() {
		return plaMspstransportdet2;
	}

	public PlaAmupstransportdet getPlaAmupstransportdet() {
		return plaAmupstransportdet;
	}

	public void setPlaMupstransportdet2(
			PlaMupstransportdet2 plaMupstransportdet2) {
		this.plaMupstransportdet2 = plaMupstransportdet2;
	}

	public void setPlaMspstransportdet2(
			PlaMspstransportdet2 plaMspstransportdet2) {
		this.plaMspstransportdet2 = plaMspstransportdet2;
	}

	public void setPlaAmupstransportdet(
			PlaAmupstransportdet plaAmupstransportdet) {
		this.plaAmupstransportdet = plaAmupstransportdet;
	}

}
