package com.tlys.equ.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtil;
import com.tlys.comm.util.WordUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.service.DicGoodscategoryService;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.model.EquRepCars;
import com.tlys.equ.model.EquYcheck;
import com.tlys.equ.model.EquYcheckDet;
import com.tlys.equ.service.EquCarService;
import com.tlys.equ.service.EquYcheckService;
import com.tlys.sys.model.SysUser;

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquYcheckAction extends _BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9065649339802874339L;

	private EquYcheck equYcheck;
	private EquYcheckDet equYcheckDet;
	private Map<String, DicSinocorp> dicSinocorpMap;
	private Map<String, DicAreacorp> dicAreacorpMap;
	private Map<String, DicCartype> dicCartypeMap;
	private Map<String, DicCarkind> dicCarkindMap;
	private Map<String, String> dicGoodsMap;
	private Map<String, SysUser> sysUserMap;
	// ����б�
	private List<String> yearList;
	// ��·Ʒ�� (��װ����)
	private List<DicGoodscategory> goodscategories;
	// ����Ʒ��(��װ����)��ȡ����
	private Map<String, List<String>> cartypesByGoodsIdMap;

	private Map<String, List<String>> carsByCartypeIdMap;

	private Map<String, EquYcheckDet> equYcheckDetMap;

	private int doctype;

	@Autowired
	EquYcheckService equYcheckService;
	@Autowired
	EquCarService equCarService;
	@Autowired
	DicGoodscategoryService dicGoodscategoryService;
	@Autowired
	DicMap dicMap;

	/**
	 * ����ѯ���
	 * 
	 * @return
	 * @throws Exception
	 */
	public String left() throws Exception {
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		dicCartypeMap = dicMap.getCartypeMap();
		dicCarkindMap = dicMap.getCarkindMap();
		dicGoodsMap = dicMap.getDicGoodsMap();
		dicAreacorpMap = CommUtils.getUserAreaMap(getCurUser(), dicMap);
		return "left";
	}

	/**
	 * �����б�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		String pageUrl = "/equ/equ-ycheck!list.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr<EquRepCars>();
			if (null != equYcheck) {
				String schObjKey = "equ_ycheck_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, equYcheck);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				equYcheck = (EquYcheck) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageSize(15);
		pageCtr.setPageUrl(pageUrl);
		equYcheckService.findEquYcheck(equYcheck, pageCtr);
		sysUserMap = dicMap.getUserMap();
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		yearList = equYcheckService.getYears();
		return "list";
	}

	/**
	 * �ڵ�� �������߱༭ʱ �����Ի�������һ�������ҳ�棬 ���ȱ��������¼��ͬʱ��ѯ������ҵ�Ķ��г����� ������·Ʒ��(��װ����) ���� ������ʾ
	 * ���Ұ���ϸ���и���ҵ�Ѿ����ڵ���ϸ���ݶ�ȡ��������ҳ����Ĭ��ѡ��
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		ActionContext context = ServletActionContext.getContext();
		context.getActionInvocation().getProxy().setExecuteResult(false);
		String corpid = equYcheck.getCorpid();
		String year = equYcheck.getYear();
		String inspectiontype = equYcheck.getInspectiontype();
		String yinspectionno = CommUtils.getString(corpid, "-", year, "-", inspectiontype);
		equYcheck.setYinspectionno(yinspectionno);
		Map<String, DicSinocorp> dicSinocorpMap = dicMap.getCorpAllMap();
		DicSinocorp dicSinocorp = dicSinocorpMap.get(equYcheck.getCorpid());
		equYcheck.setAreaid(dicSinocorp.getAreaid());
		equYcheck.setCorpfullname(dicSinocorp.getFullname());
		equYcheck.setCreator(getCurUser().getUserid());
		equYcheck.setCreatedtime(new Date());
		// ����������Ϣ
		if (isNew) {
			equYcheck.setStatus("0");
			equYcheckService.save(equYcheck);
		} else {
			equYcheckService.update(equYcheck);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String url = CommUtils.getString("/equ/equ-ycheck-det!detaillist.action?equYcheck.yinspectionno=",
				yinspectionno);
		response.sendRedirect(url);
		return null;
	}

	public String cars() throws Exception {
		String corpid = equYcheck.getCorpid();
		String year = equYcheck.getYear();
		// ��ʾ������Ϣ
		List<EquCar> equCarList = equCarService.findEquCarByCorpid(corpid);
		List<String> goodsIdList = null;
		if (null != equCarList && !equCarList.isEmpty()) {
			goodsIdList = new ArrayList<String>();
			cartypesByGoodsIdMap = new HashMap<String, List<String>>();
			carsByCartypeIdMap = new HashMap<String, List<String>>();
			for (EquCar equCar : equCarList) {
				String goodsid = equCar.getGoodsid();
				String cartypeid = equCar.getCartypeid();
				String carno = equCar.getCarno();
				if (!goodsIdList.contains(goodsid)) {
					goodsIdList.add(equCar.getGoodsid());
				}
				List<String> cartypeList = cartypesByGoodsIdMap.get(equCar.getGoodsid());
				if (null == cartypeList) {
					cartypeList = new ArrayList<String>();
				}
				if (!cartypeList.contains(cartypeid)) {
					cartypeList.add(cartypeid);
				}
				cartypesByGoodsIdMap.put(equCar.getGoodsid(), cartypeList);
				List<String> carnoList = carsByCartypeIdMap.get(cartypeid);
				if (null == carnoList) {
					carnoList = new ArrayList<String>();
				}
				if (!carnoList.contains(carno)) {
					carnoList.add(carno);
				}
				carsByCartypeIdMap.put(cartypeid, carnoList);
			}
		}
		goodscategories = dicGoodscategoryService.findDicGoodscategory(goodsIdList);
		if (null == equYcheckDet) {
			equYcheckDet = new EquYcheckDet();
		}
		equYcheckDet.setCorpid(corpid);
		equYcheckDet.setYear(year);
		List<EquYcheckDet> equYcheckDetList = equYcheckService.findEquYcheckDet(equYcheckDet);
		if (null != equYcheckDetList && !equYcheckDetList.isEmpty()) {
			equYcheckDetMap = new HashMap<String, EquYcheckDet>();
			for (EquYcheckDet equYcheckDet : equYcheckDetList) {
				equYcheckDetMap.put(equYcheckDet.getCarno().trim(), equYcheckDet);
			}
		}
		return "cars";

	}

	/**
	 * ����༭ҳ��
	 * 
	 * @return
	 */
	public String edit() {
		dicSinocorpMap = CommUtils.getUserCorpMap(getCurUser(), dicMap);
		if (!isNew) {
			equYcheck = equYcheckService.loadYcheck(equYcheck.getYinspectionno());
		}
		return "edit";
	}

	/**
	 * ɾ�������¼��ͬʱɾ��4�����е���Ӧ��¼
	 * 
	 * @return
	 * @throws Exception
	 */

	public String delete() throws Exception {
		equYcheckService.deleteEquYcheck(equYcheck.getYinspectionnos());
		msg = new Msg(Msg.SUCCESS, "�����ɹ�");
		return MSG;
	}

	/**
	 * �޸� �����¼ ��״̬ (����ύ����˲��أ����ͨ��)
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		equYcheckService.update(equYcheck.getYinspectionnos(), equYcheck.getStatus());
		msg = new Msg(Msg.SUCCESS, "�ύ�ɹ�");
		return MSG;
	}

	public String export() throws Exception {
		String yinspectionno = equYcheck.getYinspectionno();
		EquYcheck localEquYcheck = equYcheckService.loadYcheck(yinspectionno);
		EquYcheckDet equYcheckDet = new EquYcheckDet();
		equYcheckDet.setCorpid(localEquYcheck.getCorpid());
		equYcheckDet.setYear(localEquYcheck.getYear());
		List<EquYcheckDet> equYcheckDetList = equYcheckService.findEquYcheckDet(equYcheckDet);
		HSSFWorkbook workbook = new HSSFWorkbook();
		localEquYcheck.setFooter(equYcheck.getFooter());
		equYcheckService.exportToExcel(localEquYcheck, equYcheckDetList, workbook, doctype);
		HttpServletResponse response = ServletActionContext.getResponse();
		dicSinocorpMap = dicMap.getCorpMap();
		DicSinocorp dicSinocorp = dicSinocorpMap.get(localEquYcheck.getCorpid());
		String type = "";
		String inspectiontype = localEquYcheck.getInspectiontype();
		if (null != inspectiontype) {
			if ("1".equals(inspectiontype)) {
				type = "��������";
			} else if ("2".equals(inspectiontype)) {
				type = "Σ�������";
			} else if ("3".equals(inspectiontype)) {
				type = "��������Э��";
			}
		}
		String filename = CommUtils.getString((null == dicSinocorp ? "" : dicSinocorp.getFullname()),
				localEquYcheck.getYear() + "���" + type);
		ExcelUtil.writeWorkbook(response, workbook, filename);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	/**
	 * ��������Э�����ĵ���Ϊword�ĵ�����ͨ��ģ�棬��̬��������
	 * 
	 * @return
	 * @throws Exception
	 */
	public String down() throws Exception {
		String yinspectionno = equYcheck.getYinspectionno();
		EquYcheck localEquYcheck = equYcheckService.loadYcheck(yinspectionno);
		HWPFDocument document = equYcheckService.down(localEquYcheck);
		HttpServletResponse response = ServletActionContext.getResponse();
		WordUtil.writeWordDocument(response, document, localEquYcheck.getCorpfullname());
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	/**
	 * ����Σ�������
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportHazardous() throws Exception {
		String yinspectionno = equYcheck.getYinspectionno();
		EquYcheck localEquYcheck = equYcheckService.loadYcheck(yinspectionno);

		EquYcheckDet equYcheckDet = new EquYcheckDet();
		equYcheckDet.setCorpid(localEquYcheck.getCorpid());
		equYcheckDet.setYear(localEquYcheck.getYear());
		List<EquYcheckDet> equYcheckDetList = equYcheckService.findEquYcheckDet(equYcheckDet);
		HSSFWorkbook workbook = new HSSFWorkbook();

		equYcheckService.exportHazardous(workbook, localEquYcheck, equYcheckDetList);

		HttpServletResponse response = ServletActionContext.getResponse();
		dicSinocorpMap = dicMap.getCorpMap();
		DicSinocorp dicSinocorp = dicSinocorpMap.get(localEquYcheck.getCorpid());
		String filename = CommUtils.getString((null == dicSinocorp ? "" : dicSinocorp.getFullname()),
				localEquYcheck.getYear() + "��� Σ�������");
		ExcelUtil.writeWorkbook(response, workbook, filename);

		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		initOpraMap("EQU_YCHECK");
	}

	public EquYcheck getEquYcheck() {
		return equYcheck;
	}

	public void setEquYcheck(EquYcheck equYcheck) {
		this.equYcheck = equYcheck;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public List<String> getYearList() {
		return yearList;
	}

	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}

	public List<DicGoodscategory> getGoodscategories() {
		return goodscategories;
	}

	public void setGoodscategories(List<DicGoodscategory> goodscategories) {
		this.goodscategories = goodscategories;
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

	public Map<String, EquYcheckDet> getEquYcheckDetMap() {
		return equYcheckDetMap;
	}

	public void setEquYcheckDetMap(Map<String, EquYcheckDet> equYcheckDetMap) {
		this.equYcheckDetMap = equYcheckDetMap;
	}

	public EquYcheckDet getEquYcheckDet() {
		return equYcheckDet;
	}

	public void setEquYcheckDet(EquYcheckDet equYcheckDet) {
		this.equYcheckDet = equYcheckDet;
	}

	public Map<String, DicCartype> getDicCartypeMap() {
		return dicCartypeMap;
	}

	public void setDicCartypeMap(Map<String, DicCartype> dicCartypeMap) {
		this.dicCartypeMap = dicCartypeMap;
	}

	public Map<String, DicCarkind> getDicCarkindMap() {
		return dicCarkindMap;
	}

	public void setDicCarkindMap(Map<String, DicCarkind> dicCarkindMap) {
		this.dicCarkindMap = dicCarkindMap;
	}

	public Map<String, String> getDicGoodsMap() {
		return dicGoodsMap;
	}

	public void setDicGoodsMap(Map<String, String> dicGoodsMap) {
		this.dicGoodsMap = dicGoodsMap;
	}

	public Map<String, SysUser> getSysUserMap() {
		return sysUserMap;
	}

	public void setSysUserMap(Map<String, SysUser> sysUserMap) {
		this.sysUserMap = sysUserMap;
	}

	public Map<String, DicAreacorp> getDicAreacorpMap() {
		return dicAreacorpMap;
	}

	public void setDicAreacorpMap(Map<String, DicAreacorp> dicAreacorpMap) {
		this.dicAreacorpMap = dicAreacorpMap;
	}

	public int getDoctype() {
		return doctype;
	}

	public void setDoctype(int doctype) {
		this.doctype = doctype;
	}

}
