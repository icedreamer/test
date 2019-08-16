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
 * �ֵ䴦��Service
 * 
 * @author �״���
 * 
 */
@Service
public class ExeDicService {

	@Autowired
	ExeDicDao dicDao;

	/**
	 * ȡ��Ʒ���ֵ�
	 * 
	 * @return
	 */
	public List<DicGoods> listDicGoods() {
		return dicDao.listDicGoods();
	}

	/**
	 * ����ƴ������ģ����ѯƷ���ֵ�
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoods> listDicGoodsByPymAndHz(String str) {
		return dicDao.listDicGoodsByPymAndHz(str);
	}

	/**
	 * ����Ʒ���ֵ��ѯƷ��
	 * 
	 * @return
	 */
	public List<DicGoods> listDicGoodsByCdy_type(String cdy_type) {
		return dicDao.listDicGoodsByCdy_type(cdy_type);
	}

	/**
	 * ȡ��Ʒ���ֵ�
	 * 
	 * @return
	 */
	public List<DicGoodsType> listDicGoodsType() {
		return dicDao.listDicGoodsType();
	}

	/**
	 * ȡ�ó�װ�����ֵ�
	 * 
	 * @return
	 */
	public List<DicGoodscategory> listDicGoodscategory() {
		return dicDao.listDicGoodscategory();
	}

	/**
	 * ����idArrȡ�ó�װ���ʶ���,028,010,
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicGoodscategory> listDicGoodscategoryByIdArr(String goodsidArr) {
		return dicDao.listDicGoodscategoryByIdArr(goodsidArr);
	}

	/**
	 * ȡ�ó����ֵ�
	 * 
	 * @return
	 */
	public List<DicCartype> listDicCartype() {
		return dicDao.listDicCartype();
	}

	/**
	 * ���˳�վƴ���롢����
	 * 
	 * @param pycode
	 * @return
	 */
	public List<DicRwstation> filterRwstation(String pycode) {
		return dicDao.filterRwstation(pycode);
	}

	/**
	 * ���ݳ�վ��ȡ�ó�վ����
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
	 * ȡ���ڲ���ҵ�ֵ�
	 * 
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorp() {
		return dicDao.listDicSinocorp();
	}

	/**
	 * ȡ���ڲ���ҵ�ֵ�,�������ݷ���Ȩ��
	 * 
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorp(String dataAutho) {
		return dicDao.listDicSinocorp(dataAutho);
	}

	/**
	 * ���ݹ�˾id���ҹ�˾
	 * 
	 * @param corpid
	 * @return
	 */
	public DicSinocorp loadDicSinocorp(String corpid) {
		return dicDao.loadDicSinocorp(corpid);
	}

	/**
	 * ���ݹ�˾idArr���ҹ�˾,35000000, 31550000, 31800000
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorpByIdArr(String corpidArr) {
		return dicDao.listDicSinocorpByIdArr(corpidArr);
	}

	/**
	 * ȡ�ó��塢������޵�λ
	 * 
	 * @return
	 */
	public List<DicRwdepartment> listDicRwdepartment() {
		return dicDao.listDicRwdepartment();
	}

	/**
	 * ȡ��·�ִ��뼰����
	 * 
	 * @return
	 */
	public List<DicRwbureau> listAdm() {
		return dicDao.listAdm();
	}

	/**
	 * ����ƴ����ͺ���ȡ�ó�վ���룬��ѯ��ʱ����,cdy_org_stn��ʽΪBJ:����������ֵΪBJP
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
	 * ���ݳ�վ�籨�������������Ӧ��ƴ���룬����BJP�ͱ����ҵ�ƴ����ΪBJ
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
	 * ����Ԥ����ʶ��ѯԤ��ʱ���
	 * 
	 * @param over_type
	 * @return
	 */
	public List<DicOverTime> listDicOverTime(String over_type) {
		return dicDao.listDicOverTime(over_type);
	}

	/**
	 * �����Ա�����ʶ��ѯԤ��ʱ���
	 * 
	 * @param zbc_flag
	 * @return
	 */
	public List<DicOverTime> listDicOverTimeByZbc_flag(String zbc_flag) {
		return dicDao.listDicOverTimeByZbc_flag(zbc_flag);
	}

	/**
	 * ���ݳ�ʱidArr���ҳ�ʱԤ��ʱ���,1,2,
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicOverTime> listDicOverTimeByIdArr(String overidArr) {
		return dicDao.listDicOverTimeByIdArr(overidArr);
	}

	/**
	 * ȡ�ó���
	 * 
	 * @return
	 */
	public List<DicCarkind> listDicCarkind() {
		return dicDao.listDicCarkind();
	}

	/**
	 * ȡ�ó���������ҵ
	 * 
	 * @return
	 */
	public List<DicCarmaker> listDicCarmaker() {
		return dicDao.listDicCarmaker();
	}

	/**
	 * ���ݹ�˾idȡ����ҵվ���б�
	 * 
	 * @param entpr_id
	 * @return
	 */
	public List<DicEntprStn> listDicEntprStn(String entpr_id) {
		return dicDao.listDicEntprStn(entpr_id);
	}

	/**
	 * ������ҵid����ҵվ������AEI���
	 * 
	 * @param entpr_id
	 * @param entpr_stn_code
	 * @return
	 */
	public List<Integer> getAei_noByEntpr(String entpr_id, String entpr_stn_code) {
		return dicDao.getAei_noByEntpr(entpr_id, entpr_stn_code);
	}

	/**
	 * ������ҵid��վ�����룬������־��AEI ��Ų�ѯ������
	 * 
	 * @return
	 */
	public String getTrain_dirByEntprstn(String entpr_id, String entpr_stn_code, int aei_no, String in_out_flag) {
		return dicDao.getTrain_dirByEntprstn(entpr_id, entpr_stn_code, aei_no, in_out_flag);
	}

	/**
	 * ȡ�ù�����޵�λ
	 * 
	 * @return
	 */
	public List<DicTankrepcorp> listDicTankrepcord() {
		return dicDao.listDicTankrepcord();
	}

	/**
	 * �����������Ʋ��Ҹ�����
	 * 
	 * @param areaname
	 * @return
	 */
	public DicAreacorp loadDicAreacorpByShrinkname(String areaname) {
		return dicDao.loadDicAreacorpByShrinkname(areaname);
	}

	/**
	 * ������ҵ���Ʋ����ҵ
	 * 
	 * @param shortname
	 * @return
	 */
	public DicSinocorp loadDicSinocorpByShortname(String shortname) {
		return dicDao.loadDicSinocorpByShortname(shortname);
	}

	/**
	 * ���ݵ籨��ȡ����·��վ�ֵ�
	 * 
	 * @param shortname
	 * @return
	 */
	public DicRwstation getRwStationByTelegramid(String telegramid) {
		return dicDao.getRwStationByTelegramid(telegramid);
	}

	/**
	 * ���ݳ���ȡ���Ա�����Ϣ
	 * 
	 * @param car_no
	 * @return
	 */
	public EquCar getEquCarByCarno(String car_no) {
		return dicDao.getEquCarByCarno(car_no);
	}

	/**
	 * ���ݳ���ȡ����ҵ���ƣ����Ա���̨���������,���ڴ�ǰ̨ҳ��ֱ�ӻ�ȡ�Ա��������ʹ����ҵ
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
	 * �����û�Ȩ��ȡ����������
	 * 
	 * @param dataAutho
	 * @return
	 */
	public List<DicAreacorp> listDicAreacorp(String dataAutho) {
		return dicDao.listDicAreacorp("dataAutho");
	}

	/**
	 * ����վ��id��վ���ֵ������վ�����ƣ����ڴ�ǰ̨ҳ��ֱ�ӻ�ȡվ������
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
	 * ���ݳ���Arr��������ҵ
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getCorpNameByCar_noArr2(String car_noArr) {
		return dicDao.getCorpNameByCar_noArr2(car_noArr);
	}

	/**
	 * ����վ���������վ������
	 * 
	 * @param stn_codeArr
	 * @return
	 */
	public HashMap<String, String> getEntprStnByStnCode(String stn_codeArr) {
		return dicDao.getCorpNameByCar_noArr2(stn_codeArr);
	}

	/**
	 * ���ݳ���Arr���װ����
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getMediumIdByCar_noArr(String car_noArr) {
		return dicDao.getMediumIdByCar_noArr(car_noArr);
	}

	/**
	 * ���ݳ���Arr�������ʶ
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getInOutFlagByRptNameArr(String rptNameArr) {
		return dicDao.getInOutFlagByRptNameArr(rptNameArr);
	}

	/**
	 * ȡ����������Ĺ�˾��
	 * 
	 * @return
	 */
	public int[] getListRowspan() {
		List<DicAreacorp> listAreacorp = dicDao.listDicAreacorp("dataAutho");
		List<DicSinocorp> listSinocorp = dicDao.listDicSinocorp("dataAutho");
		int[] rowspan = new int[listAreacorp.size()];// ����˾
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
