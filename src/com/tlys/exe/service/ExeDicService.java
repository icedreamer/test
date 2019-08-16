package com.tlys.exe.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCarmaker;
import com.tlys.dic.model.DicCartype;
import com.tlys.dic.model.DicEntprStn;
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicGoodsType;
import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicOverTime;
import com.tlys.dic.model.DicRwbureau;
import com.tlys.dic.model.DicRwdepartment;
import com.tlys.dic.model.DicRwstation;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicTankrepcorp;
import com.tlys.equ.model.EquCar;
import com.tlys.exe.dao.ExeDicDao;

/**
 * 字典处理Service
 * 
 * @author 孔垂云
 * 
 */
@Service
public class ExeDicService {

	@Autowired
	ExeDicDao dicDao;

	/**
	 * 取得品名字典
	 * 
	 * @return
	 */
	public List<DicGoods> listDicGoods() {
		return dicDao.listDicGoods();
	}

	/**
	 * 根据拼音或汉字模糊查询品类字典
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoods> listDicGoodsByPymAndHz(String str) {
		return dicDao.listDicGoodsByPymAndHz(str);
	}

	/**
	 * 根据品类字典查询品名
	 * 
	 * @return
	 */
	public List<DicGoods> listDicGoodsByCdy_type(String cdy_type) {
		return dicDao.listDicGoodsByCdy_type(cdy_type);
	}

	/**
	 * 取得品类字典
	 * 
	 * @return
	 */
	public List<DicGoodsType> listDicGoodsType() {
		return dicDao.listDicGoodsType();
	}

	/**
	 * 取得充装介质字典
	 * 
	 * @return
	 */
	public List<DicGoodscategory> listDicGoodscategory() {
		return dicDao.listDicGoodscategory();
	}

	/**
	 * 根据idArr取得充装介质对象,028,010,
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicGoodscategory> listDicGoodscategoryByIdArr(String goodsidArr) {
		return dicDao.listDicGoodscategoryByIdArr(goodsidArr);
	}

	/**
	 * 取得车型字典
	 * 
	 * @return
	 */
	public List<DicCartype> listDicCartype() {
		return dicDao.listDicCartype();
	}

	/**
	 * 过滤车站拼音码、或汉字
	 * 
	 * @param pycode
	 * @return
	 */
	public List<DicRwstation> filterRwstation(String pycode) {
		return dicDao.filterRwstation(pycode);
	}

	/**
	 * 根据车站码取得车站名称
	 * 
	 * @param stationid
	 * @return
	 */
	public String getDicRwstationName(String stationid) {
		DicRwstation station = dicDao.loadDicRwstation(stationid);
		if (station == null)
			return "";
		else
			return station.getShortname();
	}

	/**
	 * 取得内部企业字典
	 * 
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorp() {
		return dicDao.listDicSinocorp();
	}

	/**
	 * 取得内部企业字典,包含数据访问权限
	 * 
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorp(String dataAutho) {
		return dicDao.listDicSinocorp(dataAutho);
	}

	/**
	 * 根据公司id查找公司
	 * 
	 * @param corpid
	 * @return
	 */
	public DicSinocorp loadDicSinocorp(String corpid) {
		return dicDao.loadDicSinocorp(corpid);
	}

	/**
	 * 根据公司idArr查找公司,35000000, 31550000, 31800000
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorpByIdArr(String corpidArr) {
		return dicDao.listDicSinocorpByIdArr(corpidArr);
	}

	/**
	 * 取得车体、罐体检修单位
	 * 
	 * @return
	 */
	public List<DicRwdepartment> listDicRwdepartment() {
		return dicDao.listDicRwdepartment();
	}

	/**
	 * 取得路局代码及名称
	 * 
	 * @return
	 */
	public List<DicRwbureau> listAdm() {
		return dicDao.listAdm();
	}

	/**
	 * 根据拼音码和汉字取得车站代码，查询的时候用,cdy_org_stn格式为BJ:北京，返回值为BJP
	 * 
	 * @param stationpycode
	 * @param shortname
	 * @return
	 */
	public String getRwStationCode(String cdy_org_stn) {
		if (cdy_org_stn.equals(""))
			return "";
		DicRwstation station = dicDao.getRwStationCode(cdy_org_stn.substring(0, cdy_org_stn.indexOf(":")), cdy_org_stn
				.substring(cdy_org_stn.indexOf(":") + 1));
		if (station != null)
			return station.getTelegramid();
		else
			return "";
	}

	/**
	 * 根据车站电报码和名称找它对应的拼音码，比如BJP和北京找到拼音码为BJ
	 * 
	 * @param stn_code
	 * @param stn_name
	 * @return
	 */
	public String getRwStationPym(String stn_code, String stn_name) {
		DicRwstation station = dicDao.getRwStationPym(stn_code, stn_name);
		if (station != null)
			return station.getStationpycode();
		else
			return "";
	}

	/**
	 * 根据预警标识查询预警时间段
	 * 
	 * @param over_type
	 * @return
	 */
	public List<DicOverTime> listDicOverTime(String over_type) {
		return dicDao.listDicOverTime(over_type);
	}

	/**
	 * 根据自备车标识查询预警时间段
	 * 
	 * @param zbc_flag
	 * @return
	 */
	public List<DicOverTime> listDicOverTimeByZbc_flag(String zbc_flag) {
		return dicDao.listDicOverTimeByZbc_flag(zbc_flag);
	}

	/**
	 * 根据超时idArr查找超时预警时间段,1,2,
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicOverTime> listDicOverTimeByIdArr(String overidArr) {
		return dicDao.listDicOverTimeByIdArr(overidArr);
	}

	/**
	 * 取得车种
	 * 
	 * @return
	 */
	public List<DicCarkind> listDicCarkind() {
		return dicDao.listDicCarkind();
	}

	/**
	 * 取得车辆制造企业
	 * 
	 * @return
	 */
	public List<DicCarmaker> listDicCarmaker() {
		return dicDao.listDicCarmaker();
	}

	/**
	 * 根据公司id取得企业站区列表
	 * 
	 * @param entpr_id
	 * @return
	 */
	public List<DicEntprStn> listDicEntprStn(String entpr_id) {
		return dicDao.listDicEntprStn(entpr_id);
	}

	/**
	 * 根据企业id和企业站区查找AEI编号
	 * 
	 * @param entpr_id
	 * @param entpr_stn_code
	 * @return
	 */
	public List<Integer> getAei_noByEntpr(String entpr_id, String entpr_stn_code) {
		return dicDao.getAei_noByEntpr(entpr_id, entpr_stn_code);
	}

	/**
	 * 根据企业id，站区代码，进出标志，AEI 编号查询上下行
	 * 
	 * @return
	 */
	public String getTrain_dirByEntprstn(String entpr_id, String entpr_stn_code, int aei_no, String in_out_flag) {
		return dicDao.getTrain_dirByEntprstn(entpr_id, entpr_stn_code, aei_no, in_out_flag);
	}

	/**
	 * 取得罐体检修单位
	 * 
	 * @return
	 */
	public List<DicTankrepcorp> listDicTankrepcord() {
		return dicDao.listDicTankrepcord();
	}

	/**
	 * 根据区域名称查找该区域
	 * 
	 * @param areaname
	 * @return
	 */
	public DicAreacorp loadDicAreacorpByShrinkname(String areaname) {
		return dicDao.loadDicAreacorpByShrinkname(areaname);
	}

	/**
	 * 根据企业名称查该企业
	 * 
	 * @param shortname
	 * @return
	 */
	public DicSinocorp loadDicSinocorpByShortname(String shortname) {
		return dicDao.loadDicSinocorpByShortname(shortname);
	}

	/**
	 * 根据电报码取得铁路车站字典
	 * 
	 * @param shortname
	 * @return
	 */
	public DicRwstation getRwStationByTelegramid(String telegramid) {
		return dicDao.getRwStationByTelegramid(telegramid);
	}

	/**
	 * 根据车号取得自备车信息
	 * 
	 * @param car_no
	 * @return
	 */
	public EquCar getEquCarByCarno(String car_no) {
		return dicDao.getEquCarByCarno(car_no);
	}

	/**
	 * 根据车号取得企业名称，从自备车台账里面查找,用于从前台页面直接获取自备车里面的使用企业
	 * 
	 * @param stationid
	 * @return
	 */
	public HashMap<String, String> getCorpNameByCar_noArr(String car_noArr) {
		List<EquCar> list = dicDao.getEquCarByCarnoArr(car_noArr);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (EquCar car : list) {
			hashMap.put(car.getCarno(), car.getCorpshrinkname());
		}
		return hashMap;
	}

	/**
	 * 根据用户权限取得所有区域
	 * 
	 * @param dataAutho
	 * @return
	 */
	public List<DicAreacorp> listDicAreacorp(String dataAutho) {
		return dicDao.listDicAreacorp("dataAutho");
	}

	/**
	 * 根据站区id从站区字典里面查站区名称，用于从前台页面直接获取站区名称
	 * 
	 * @param stnIdArr
	 * @return
	 */
	public HashMap<String, String> getStnNameByStnIdArr(String stnIdArr) {
		List<DicEntprStn> list = dicDao.getEntprStnByStnIdArr(stnIdArr);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (DicEntprStn stn : list) {
			hashMap.put(stn.getEntpr_stn_code(), stn.getEntpr_stn_name());
		}
		return hashMap;
	}

	/**
	 * 根据车号Arr查所属企业
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getCorpNameByCar_noArr2(String car_noArr) {
		return dicDao.getCorpNameByCar_noArr2(car_noArr);
	}

	/**
	 * 根据站区代码查找站区名称
	 * 
	 * @param stn_codeArr
	 * @return
	 */
	public HashMap<String, String> getEntprStnByStnCode(String stn_codeArr) {
		return dicDao.getCorpNameByCar_noArr2(stn_codeArr);
	}

	/**
	 * 根据车号Arr查充装介质
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getMediumIdByCar_noArr(String car_noArr) {
		return dicDao.getMediumIdByCar_noArr(car_noArr);
	}

	/**
	 * 根据车号Arr查进出标识
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getInOutFlagByRptNameArr(String rptNameArr) {
		return dicDao.getInOutFlagByRptNameArr(rptNameArr);
	}

	/**
	 * 取得所有区域的公司数
	 * 
	 * @return
	 */
	public int[] getListRowspan() {
		List<DicAreacorp> listAreacorp = dicDao.listDicAreacorp("dataAutho");
		List<DicSinocorp> listSinocorp = dicDao.listDicSinocorp("dataAutho");
		int[] rowspan = new int[listAreacorp.size()];// 区域公司
		int i = 0;
		for (DicAreacorp area : listAreacorp) {
			int k = 0;
			for (DicSinocorp corp : listSinocorp) {
				if (area.getAreaid().equals(corp.getAreaid())) {
					k++;
				}
			}
			rowspan[i] = k;
			i++;
		}
		return rowspan;
	}

	public List<DicSinocorp> listZqqy(int type) {
		return dicDao.listZqqy(type);
	}
}
