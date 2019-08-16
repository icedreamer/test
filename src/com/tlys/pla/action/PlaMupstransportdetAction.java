package com.tlys.pla.action;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tlys.dic.model.ctl.CtlCustomerreceiver;
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
import com.tlys.pla.model.PlaMupstransport;
import com.tlys.pla.model.PlaMupstransportdet;
import com.tlys.pla.service.PlaMupstransportService;
import com.tlys.pla.service.PlaMupstransportdetService;

@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaMupstransportdetAction extends _BaseAction {

	private static final long serialVersionUID = 431299588520414810L;
	private Long id;
	private PlaMupstransportdet plaMupstransportdet;
	private List<PlaMupstransportdet> mstdList;

	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	// 是否石化内部企业
	private int isInnerCorp;
	private String fieldName;
	private String fieldValue;
	// 是否可編輯
	private String canEdit;
	private String status;
	private String corpids;
	private String secondkinds;
	private String categories;
	// 导出条件
	private String schobjkey;

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
	//计划统称(列表抬头说明)
	private String plannoStr;

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
	CtlCustomerreceiverService ctlCustomerreceiverService;
	@Autowired
	CtlCorpsenderService ctlCorpsenderService;
	@Autowired
	DicSalecorpService dicSalecorpService;
	@Autowired
	PlaMupstransportService plaMupstransportService;

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() throws Exception {
		Long id = plaMupstransportdet.getId();
		plaMupstransportdet = plaMupstransportdetService.load(id);
		plaMupstransportdetService.delete(plaMupstransportdet);
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
		String pageUrl = "/pla/pla-mupstransportdet!list.action?plannoStr="+plannoStr;
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if (null != plaMupstransportdet) {
				String schObjKey = "plaMupstransportdet_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				plaMupstransportdet.setCorpids(null == corpids || "".equals(corpids) ? null : corpids.split(","));
				plaMupstransportdet.setSecondkinds(null == secondkinds || "".equals(secondkinds) ? null : secondkinds
						.split(","));
				plaMupstransportdet.setCategories(null == categories || "".equals(categories) ? null : categories
						.split(","));
				setSessionAttr(schObjKey, plaMupstransportdet);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
			if (null != pageCtr.getSchObjKey()) {
				plaMupstransportdet = (PlaMupstransportdet) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);

		plaMupstransportdetService.find(plaMupstransportdet, pageCtr);
		String planno = plaMupstransportdet.getPlanno();
		PlaMupstransport plaMupstransport = plaMupstransportService.load(planno);
		if (null != plaMupstransport) {
			String status = plaMupstransport.getStatus();
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

	// -------------------------------------------------

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		plaMupstransportdet = plaMupstransportdetService.load(id);
		if (logger.isDebugEnabled()) {
			logger.debug("fieldName : " + fieldName);
		}
		Class<?> clazz = plaMupstransportdet.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldType = field.getType().getSimpleName();
			if (field.getName().equals(fieldName)) {
				if (logger.isDebugEnabled()) {
					logger.debug("fieldName custor : " + fieldName);
					logger.debug("fieldType custor : " + fieldType);
					logger.debug("fieldValue : " + fieldValue);
				}
				if (fieldType.equals("Double")) {
					fields[i].set(plaMupstransportdet, Double.valueOf(fieldValue));
				} else if (fieldType.equals("Long")) {
					fields[i].set(plaMupstransportdet, Long.valueOf(fieldValue));
				} else {
					fields[i].set(plaMupstransportdet, fieldValue);
				}
				if (fieldName.equals("customerid")) {
					if (isInnerCorp == 1) {
						DicSinocorp dicSinocorp = dicSinocorpService.load(fieldValue);
						if (null != dicSinocorp) {
							if (logger.isDebugEnabled()) {
								logger.debug("corpid : " + dicSinocorp.getCorpid());
								logger.debug("corp.name : " + dicSinocorp.getShortname());
							}
							plaMupstransportdet.setCustomerid(dicSinocorp.getCorpid());
							plaMupstransportdet.setCoustomershortname(dicSinocorp.getShortname());
						}
					} else if (isInnerCorp == 0) {
						DicCustomer dicCustomer = dicCustomerService.load(fieldValue);
						if (null != dicCustomer) {
							plaMupstransportdet.setCustomerid(dicCustomer.getCustomerid());
							plaMupstransportdet.setCoustomershortname(dicCustomer.getShortname());
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
						plaMupstransportdet.setCustomerid(dicCustomer.getCustomerid());
						plaMupstransportdet.setCoustomershortname(dicCustomer.getShortname());
					} else {
						plaMupstransportdet.setCustomerid(dicSinocorp.getCorpid());
						plaMupstransportdet.setCoustomershortname(dicSinocorp.getShortname());

					}
					if (null != dicCustomer) {
						plaMupstransportdet.setCoustomershortname(dicCustomer.getShortname());
					}
				} else if (fieldName.equals("corpid")) {
					DicSinocorp dicSinocorp = dicSinocorpService.load(fieldValue);
					if (null != dicSinocorp) {
						plaMupstransportdet.setCorpshortname(dicSinocorp.getShortname());
						String areaid = dicSinocorp.getAreaid();
						plaMupstransportdet.setAreaid(areaid);
						DicAreacorp dicAreacorp = dicAreacorpService.load(areaid);
						if (null != dicAreacorp) {
							plaMupstransportdet.setAreashortname(dicAreacorp.getShortname());
						}
					}
				} else if (fieldName.equals("prodductid")) {
					DicProduct dicProduct = dicProductService.load(fieldValue);
					if (null != dicProduct) {
						plaMupstransportdet.setProdductid(fieldValue);
						plaMupstransportdet.setProductcategoryid(dicProduct.getCategoryid());
						plaMupstransportdet.setProductsecondid(dicProduct.getSecondkindid());
					}
				} else if (fieldName.equals("rwkindid")) {
					DicGoods dicGoods = dicGoodsService.getDicGoods(fieldValue);
					if (null != dicGoods) {
						plaMupstransportdet.setRkindname(dicGoods.getPmhz());
					}
				} else if (fieldName.equals("receiverid")) {
					if (isInnerCorp == 0) {
						CtlCustomerreceiver ctlCustomerreceiver = ctlCustomerreceiverService
								.getCtlCustomerreceiver(fieldValue);
						if (null != ctlCustomerreceiver) {
							plaMupstransportdet.setReceivername(ctlCustomerreceiver.getReceivername());
						}
					} else if (isInnerCorp == 1) {
						CtlCorpreceiver ctlCorpreceiver = ctlCorpreceiverService.getCtlCorpreceiver(fieldValue);
						if (null != ctlCorpreceiver) {
							plaMupstransportdet.setReceivername(ctlCorpreceiver.getReceivername());
						}
					}
				} else if (fieldName.equals("receivername")) {
					// CtlCorpreceiver ctlCorpreceiver = new CtlCorpreceiver();
					// ctlCorpreceiver.setCorpid(plaMupstransportdet.getCustomerid());
					String customershortname = plaMupstransportdet.getCoustomershortname();
					// ctlCorpreceiver.setCorpshortname(customershortname);
					// ctlCorpreceiver.setCreatedtime(new Date());
					// ctlCorpreceiver.setIsactive("1");
					// String receiverNextReq =
					// ctlCorpreceiverService.getReceiverNextReq();
					// if (logger.isDebugEnabled()) {
					// logger.debug("receiverNextReq : " + receiverNextReq);
					// }
					// ctlCorpreceiver.setReceiverid(receiverNextReq);
					// ctlCorpreceiver.setReceivername(fieldValue);
					// ctlCorpreceiver.setReceivershortname(fieldValue);

					String[] datas = { customershortname, fieldValue };
					Object[] returnValues = ctlCorpreceiverService.getCtlCorpReceiver(datas);
					dicMap.dicAlter("ctlCorpsender");
					String customerid = null == returnValues[0] ? "" : returnValues[0].toString();
					String receiverid = null == returnValues[1] ? "" : returnValues[1].toString();
					if (logger.isDebugEnabled()) {
						logger.debug("customerid : " + customerid);
						logger.debug("receiverid : " + receiverid);
					}
					plaMupstransportdet.setReceivername(fieldValue);
					plaMupstransportdet.setCoustomershortname(customershortname);
					plaMupstransportdet.setCustomerid(customerid);
					plaMupstransportdet.setReceiverid(receiverid);
					// ctlCorpreceiverService.save(ctlCorpreceiver);
				} else if (fieldName.equals("senderid")) {

				} else if (fieldName.equals("sendername")) {
					CtlCorpsender ctlCorpsender = new CtlCorpsender();
					ctlCorpsender.setCorpid(plaMupstransportdet.getCorpid());
					ctlCorpsender.setCorpshortname(plaMupstransportdet.getCorpshortname());
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
					plaMupstransportdet.setSenderid(senderNextReq);
				}
				break;
			}
		}
		// 修改记录
		plaMupstransportdetService.update(plaMupstransportdet);
		msg = new Msg(Msg.SUCCESS, "操作成功!");
		if (logger.isDebugEnabled()) {
			logger.debug("save complete.");
		}
		return MSG;
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	public String expExcel() throws Exception {
		plaMupstransportdet = (PlaMupstransportdet) getSessionAttr(schobjkey);
		HttpServletResponse response = ServletActionContext.getResponse();
		plaMupstransportdetService.expExcel(plaMupstransportdet, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	public void prepare() throws Exception {
		initOpraMap("PLA_MUPS");
	}

	public PlaMupstransportdet getPlaMupstransportdet() {
		return plaMupstransportdet;
	}

	public List<PlaMupstransportdet> getMstdList() {
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

	public int getIsInnerCorp() {
		return isInnerCorp;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public String getCanEdit() {
		return canEdit;
	}

	public String getStatus() {
		return status;
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

	public void setPlaMupstransportdet(PlaMupstransportdet plaMupstransportdet) {
		this.plaMupstransportdet = plaMupstransportdet;
	}

	public void setMstdList(List<PlaMupstransportdet> mstdList) {
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

	public void setIsInnerCorp(int isInnerCorp) {
		this.isInnerCorp = isInnerCorp;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

	public String getPlannoStr() {
		return plannoStr;
	}

	public void setPlannoStr(String plannoStr) {
		this.plannoStr = plannoStr;
	}

}
