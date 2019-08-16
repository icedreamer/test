/**
 * 
 */
package com.tlys.equ.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.equ.dao.EquCarCertificateDao;
import com.tlys.equ.dao.EquCarDao;
import com.tlys.equ.dao.EquCarHpinfoDao;
import com.tlys.equ.dao.EquCarHpreginfoDao;
import com.tlys.equ.dao.EquCarNpinfoDao;
import com.tlys.equ.dao.EquCarRentDao;
import com.tlys.equ.model.EquCar;
import com.tlys.equ.model.EquCarCertificate;
import com.tlys.equ.model.EquCarHpinfo;
import com.tlys.equ.model.EquCarHpreginfo;
import com.tlys.equ.model.EquCarNpinfo;
import com.tlys.equ.model.EquCarRent;
import com.tlys.exe.dao.ExeDcarStatDao;
import com.tlys.sys.model.SysUser;

/**
 * @author ������
 * 
 */
@Service
public class EquCarService {
	protected final Log log = LogFactory.getLog(this.getClass());
	@Autowired
	EquCarDao equCarDao;

	@Autowired
	EquCarHpinfoDao equCarHpinfoDao;

	@Autowired
	EquCarHpreginfoDao equCarHpreginfoDao;

	@Autowired
	EquCarRentDao equCarRentDao;

	@Autowired
	EquCarCertificateDao equCarCertificateDao;

	@Autowired
	EquCarNpinfoDao equCarNpinfoDao;

	@Autowired
	DicMap dicMap;
	// ����
	@Autowired
	ExeDcarStatDao exeDcarStatDao;

	public void findPageCtr(EquCar obj, PageCtr pageCtr) throws Exception {
		int totalRecord = getObjCount(obj);
		pageCtr.setTotalRecord(totalRecord);
		equCarDao.findPageCtr(obj, pageCtr);
		dicMap.bdEquCarDic(pageCtr.getRecords());
	}

	public int getObjCount(final EquCar obj) {
		int count = equCarDao.getObjCount(obj);
		return count;
	}

	public List<EquCar> findAll() {
		return equCarDao.findAll();
	}

	public List<EquCar> find(EquCar dac) throws Exception {
		List<EquCar> list = equCarDao.find(dac);
		dicMap.bdEquCarDic(list);
		return list;
	}

	public List query(String hql) {
		return equCarDao.query(hql);
	}

	public EquCar load(String id) {
		return equCarDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param dicCorptrain
	 *            ,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(EquCar car, boolean isNew) throws Exception {
		if (isNew) {
			equCarDao.saveOrUpdate(car);
		} else {
			log.debug("car.stnid : " + car.getStnid());
			equCarDao.updateEntity(car, car.getCarno());

		}
		// �жϵ�ȷ�ϱ�����ɾ���������ĳ���
		if (null != car.getIsexpire() && "" != car.getIsexpire()) {
			if (car.getIsexpire() == "1") {
				exeDcarStatDao.delZbcExeDcarstatInfo(car.getCarno());
			}
		}
	}

	public void delete(String id) {
		equCarDao.delete(id);

	}

	public Map<String, List> findByColumn(EquCar car) {
		Map map = new HashMap();
		String hql = "select distinct new EquCar(corpid, corpshrinkname) from " + EquCar.class.getName() + " order by corpid";
		List<Object> corps = equCarDao.find(hql);
		map.put("corp", corps);
		List<EquCar> list = equCarDao.find(car);
		map.put("cars", list);
		return map;
	}

	/**
	 * ����carʵ������й���ʵ�弯��
	 * 
	 * @param hql
	 * @return List
	 */
	public Map<String, List<Object>> findSubs() {
		Map<String, List<Object>> map = new HashMap();
		map.put("cartypeid", equCarDao.find("from DicCartype order by cartypename"));
		map.put("carkindid", equCarDao.find("from DicCarkind order by shortname"));
		map.put("corpid",
				equCarDao.find("from DicSinocorp where (corptype is null or corptype='1') and " + CommUtils.getCorpIds("corpid")
						+ " order by shortname"));
		map.put("goodsid", equCarDao.find("from DicGoodscategory order by goodsname"));
		map.put("tankmateid", equCarDao.find("from DicTrankMaterial order by mname"));
		map.put("rentid", equCarDao.find("from DicRentMode order by rentname"));
		map.put("carmakerid", equCarDao.find("from DicCarmaker order by shortname"));
		return map;
	}

	public List<Object> find(String hql) {
		return equCarDao.find(hql);
	}

	public List<String[]> findShrink(String corpid) {
		String sql = "select t.stationid,t.shortname from tb_zbc_dic_rwstation t,tb_zbc_dic_sinocorp d where t.bureauid = d.bureauid and d.corpid='"
				+ corpid + "' order by t.stationid";
		return equCarDao.querySql(sql);
	}

	public List<EquCar> findEquCarByCorpid(String corpid) {
		return equCarDao.findEquCarByCorpid(corpid);
	}

	public void findEquCar(final EquCar equCar, final PageCtr<EquCar> pageCtr) throws Exception {
		int totalRecord = equCarDao.getEquCarCount(equCar);
		pageCtr.setTotalRecord(totalRecord);
		equCarDao.findEquCar(equCar, pageCtr);
		dicMap.bdEquCarDic(pageCtr.getRecords());
	}

	public void update(EquCar equCar) {
		equCarDao.update(equCar);
	}

	/**
	 * �������г�װ����id-��Map��ȥ��)
	 * 
	 * @return
	 */
	public Map findGoodsMap() {
		Map goodsMapDic = dicMap.getGoodscategoryMap();
		List goodsids = equCarDao.findGoodsids();
		Map goodsMap = new LinkedHashMap();
		for (Iterator iter = goodsids.iterator(); iter.hasNext();) {
			String gid = (String) iter.next();
			String gname = (String) goodsMapDic.get(gid);
			goodsMap.put(gid, gname);
		}
		return goodsMap;
	}

	/**
	 * ����equCar�е�������ҵMap��ȥ��)
	 * 
	 * @return
	 */
	public Map findCorpidMap() {
		List corpidList = equCarDao.getCorpids();
		Map corpidMap = new LinkedHashMap();
		for (Iterator iter = corpidList.iterator(); iter.hasNext();) {
			Object[] cids = (Object[]) iter.next();
			corpidMap.put(cids[0], cids[1]);

		}
		return corpidMap;
	}

	public Map buidStatMap(Map goodsMap, Map corpidMap) {
		Map stMap = new LinkedHashMap();
		// Map goodsMap = findGoodsMap();
		stMap.put("//", goodsMap);
		// List corpidList = equCarDao.getCorpids();
		for (Iterator iter = corpidMap.keySet().iterator(); iter.hasNext();) {

			String corpid = (String) iter.next();
			String sname = (String) corpidMap.get(corpid);

			Map nmMap = (Map) stMap.get(sname);
			if (null == nmMap) {// ԭ�ƻ�clone�����Ϊ����ʵ�ַ�ʽ
				nmMap = new LinkedHashMap();
				for (Iterator itm = goodsMap.keySet().iterator(); itm.hasNext();) {
					String gdic = (String) itm.next();
					nmMap.put(gdic, null);
				}
				stMap.put(sname, nmMap);
			}

			List cnumList = equCarDao.getCarNumByCorp(corpid);
			for (Iterator it = cnumList.iterator(); it.hasNext();) {
				Object[] st = (Object[]) it.next();
				String gid = (String) st[0];
				// String gname = (String)goodsMap.get(gid);
				nmMap.put(gid, st[1]);
			}

		}
		return stMap;
	}

	/**
	 * ��ĳЩ����ҵ����ͳ��
	 * 
	 * @param corpid
	 * @return
	 */
	public Map buidStatMap(String corpid) {
		Map goodsMap = findGoodsMap();
		Map corpMap = buildCidMap(corpid);
		return buidStatMap(goodsMap, corpMap);
	}

	/**
	 * �����û��ύ�ĳ�װ����ID����Map
	 * 
	 * @param goodsIds
	 * @return
	 */
	public Map buildGidMap(String goodsIds) {
		String[] gidArr = goodsIds.split(", ");
		Map goodsMapDic = dicMap.getGoodscategoryMap();
		Map gidMap = new LinkedHashMap();
		for (int i = 0; i < gidArr.length; i++) {
			String gid = gidArr[i];
			gidMap.put(gid, goodsMapDic.get(gid));
		}
		return gidMap;
	}

	/**
	 * �����û��ύ�Ĺ�˾id����List
	 * 
	 * @param corpIds
	 * @return
	 */
	public Map buildCidMap(String corpIds) {
		String[] cidArr = null;
		if (corpIds.indexOf(", ") > -1) {
			cidArr = corpIds.split(", ");
		} else {
			cidArr = corpIds.split(",");
		}

		Map cidMap = new LinkedHashMap();

		Map corpMapDic = dicMap.getCorpMap();

		for (int i = 0; i < cidArr.length; i++) {
			String cid = cidArr[i].replaceAll("'", "");
			DicSinocorp dsp = (DicSinocorp) corpMapDic.get(cid);
			cidMap.put(cid, dsp.getShrinkname());
		}

		return cidMap;
	}

	/**
	 * ��ǰ��װ����������û��ύ���ݽ��й��ˣ����û�ύ����ȫ����ʾ)
	 * 
	 * @param
	 * @return
	 */
	public Map filterGoodsMap(Map goodsMap, String goodsIds) {
		String[] gidArr = goodsIds.split(", ");
		for (Iterator iter = goodsMap.keySet().iterator(); iter.hasNext();) {
			String gid = (String) iter.next();
			if (!isContain(gidArr, gid)) {
				iter.remove();
				goodsMap.remove(gid);
			}
		}
		return goodsMap;
	}

	private boolean isContain(String[] gidArr, String gid) {
		boolean ctflag = false;
		for (int i = 0; i < gidArr.length; i++) {
			if (gidArr[i].equals(gid)) {
				ctflag = true;
				break;
			}
		}
		return ctflag;
	}

	/**
	 * ����EXCEL
	 * 
	 * @param equCar
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(EquCar equCar, HttpServletResponse response) throws Exception {
		Map tabDefineMap = new LinkedHashMap();
		// ��ѯ��ϸ�ƻ�����
		List<EquCar> list = this.find(equCar);
		// ����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("�Ա���̨����Ϣ", sheetMap);
		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", list);
		// ����
		sheetMap.put("title", "�Ա���̨����Ϣ");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "carno", "corpidDIC", "cartypeid", "carkindname", "goodsname", "tankmatename", "carmakername",
				"madedate", "inroaddate", "isstandartrainname", "isdangerousname", "isexpirename", "expiredate", "pressuretypename",
				"isrentname", "rentidname", "stnshrinkname", "lightweight", "markloadweight", "capacity", "convlength", "carlength",
				"axisnum", "description" };
		headArr[1] = new String[] { "����", "��ҵ����", "����", "����", "��װ����", "�������", "���쳧��", "��������", "��·����", "�Ƿ��׼��", "�Ƿ�Σ��Ʒ��", "�Ƿ񱨷�", "��������",
				"ѹ������", "����״̬", "���÷�ʽ", "����վ����", "����", "�������", "�ݻ�", "����", "����", "����", "����" };
		sheetMap.put("headArr", headArr);
		// ����carno���������� ʹ��Restrictions.sqlRestriction(sql)���������ӱ���
		String carnoSql = "select A.CARNO from TB_ZBC_EQU_CAR A " + equCarDao.buildWhere(equCar);
		// -------------------------------------------------
		EquCarNpinfo equCarNpinfo = new EquCarNpinfo();
		equCarNpinfo.setCarnos(carnoSql);
		List<EquCarNpinfo> nps = equCarNpinfoDao.find(equCarNpinfo);
		Map sheetMap1 = new HashMap();
		tabDefineMap.put("��ѹ�޳��������ܱ�", sheetMap1);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap1.put("list", nps);
		// ����
		sheetMap1.put("title", "��ѹ�޳��������ܱ�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		headArr = new String[2][];
		headArr[0] = new String[] { "carno", "fixeddist", "maxwidth", "maxhight", "centerlinehight", "axisdist", "wheelidameter",
				"aixweight", "ecoghigh", "ecoghight", "brakeequipmentno", "redirectno", "semidiameter", "maxloadweight", "minloadweight",
				"totalcapacity", "validcapacity", "workpressure", "worktemperature", "thickness" };
		headArr[1] = new String[] { "����", "��������", "���������", "�������߶�", "���������߸�", "�̶����", "����ֱ��", "����", "�ճ����ĸ�", "�س����ĸ�", "�ƶ�װ���ͺ�", "ת����ͺ�",
				"ͨ����С���߰뾶", "�������", "��С����", "���ݻ�", "��Ч�ݻ�", "����ѹ��", "�����¶�", "����ں�" };
		sheetMap1.put("headArr", headArr);
		// -------------------------------------------------
		EquCarHpinfo equCarHpinfo = new EquCarHpinfo();
		equCarHpinfo.setCarnos(carnoSql);
		List<EquCarHpinfo> hps = equCarHpinfoDao.find(equCarHpinfo);
		Map sheetMap2 = new HashMap();
		tabDefineMap.put("��ѹ�޳��������ܱ�", sheetMap2);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap2.put("list", nps);
		// ����
		sheetMap2.put("title", "��ѹ�޳��������ܱ�");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		headArr = new String[2][];
		// headArr[0] = new String[] { "carno", "redirectno",
		// "brakeequipmentno", "tightnesstest",
		// "designpressure", "designtemperature", "tankweight", "rustydegree",
		// "hydrostatictest", "tanksize",
		// "liquidno", "liquidtype", "validHeight", "liquidvalveno",
		// "gasvalveno", "nominalpressure",
		// "nominaldiameter", "thermotype", "safetyvalveno", "safetyvalvetype",
		// "openpressure", "fopenpressure",
		// "backpressure", "magnetType" };
		// headArr[1] = new String[] { "����", "ת����ͺ�", "�ƶ�װ���ͺ�", "����������", "���ѹ��",
		// "����¶�", "��������", "��ʴ�̶�", "ˮѹ����",
		// "����ߴ�", "Һ����ͺ�", "Һ�����ʽ", "��Ч�߶�", "Һ�෧�ͺ�", "���෧�ͺ�", "����ѹ��", "����ֱ��",
		// "�¶ȼ��ͺ�", "��ȫ���ͺ�", "��ȫ����ʽ", "����ѹ��",
		// "ȫ��ѹ��", "����ѹ��", "�Ÿ���Һλ���ͺ�" };
		headArr[0] = new String[] { "carno", "fixeddist", "maxwidth", "maxhight", "centerlinehight", "axisdist", "wheelidameter",
				"aixweight", "ecoghigh", "ecoghight", "brakeequipmentno", "redirectno", "semidiameter", "maxloadweight", "minloadweight",
				"totalcapacity", "validcapacity", "workpressure", "worktemperature", "tankmateid", "thickness" };
		headArr[1] = new String[] { "����", "��������(mm)", "���������(mm)", "�������߶�(mm)", "���������߸�(mm)", "�̶����(mm)", "����ֱ��(mm)", "����(��)",
				"�ճ����ĸ�(mm)", "�س����ĸ�(mm)", "�ƶ�װ���ͺ�", "ת����ͺ�", "ͨ����С���߰뾶(m)", "�������(��)", "��С����(��)", "���ݻ�(m3)", "��Ч�ݻ�(m3)", "����ѹ��(MPa)",
				"�����¶�(���϶�)", "�������", "����ں�(mm)" };
		sheetMap2.put("headArr", headArr);

		// -------------------------------------------------
		EquCarHpreginfo equCarHpreginfo = new EquCarHpreginfo();
		equCarHpreginfo.setCarnos(carnoSql);
		List<EquCarHpreginfo> hprs = equCarHpreginfoDao.find(equCarHpreginfo);
		Map sheetMap3 = new HashMap();
		tabDefineMap.put("��ѹ�޳�ѹ������ʹ�õǼ�֤", sheetMap3);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap3.put("list", hprs);
		// ����
		sheetMap3.put("title", "��ѹ�޳�ѹ������ʹ�õǼ�֤");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		headArr = new String[2][];
		headArr[0] = new String[] { "carno", "useregisterno", "internalno", "containername", "makecorp", "makelicense", "usedate",
				"carchassisno", "cartankno", "registercode", "registerdepartment", "remarks" };
		headArr[1] = new String[] { "����", "ʹ�õǼ�֤���", "��λ�ڱ��", "ѹ����������", "���쵥λ", "�������֤��", "��������", "�������̱��", "����������", "ע����", "ע�����", "����" };
		sheetMap3.put("headArr", headArr);

		// -------------------------------------------------
		EquCarRent equCarRent = new EquCarRent();
		equCarRent.setCarnos(carnoSql);
		List<EquCarRent> equCarRents = equCarRentDao.find(equCarRent);
		Map sheetMap4 = new HashMap();
		tabDefineMap.put("�Ա���������Ϣ", sheetMap4);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap4.put("list", equCarRents);
		// ����
		sheetMap4.put("title", "�Ա���������Ϣ");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		headArr = new String[2][];
		headArr[0] = new String[] { "id", "carno", "rentcorpid", "startdate", "enddate", "isvalidDIC", "remarks" };
		headArr[1] = new String[] { "ID", "����", "������ҵID", "��ʼ�·�", "�����·�", "�Ƿ���Ч", "����" };
		sheetMap4.put("headArr", headArr);

		// -------------------------------------------------
		EquCarCertificate equCarCertificate = new EquCarCertificate();
		equCarCertificate.setCarnos(carnoSql);
		List<EquCarCertificate> equCarCertificates = equCarCertificateDao.find(equCarCertificate);
		Map sheetMap5 = new HashMap();
		tabDefineMap.put("��ҵ�Ա���֤��", sheetMap5);

		// ��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap5.put("list", equCarCertificates);
		// ����
		sheetMap5.put("title", "��ҵ�Ա���֤��");

		// ��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		// ������������е�����Ӧ�����ϸ�һ�µ�
		headArr = new String[2][];
		headArr[0] = new String[] { "id", "trainno", "certdate", "saftcertcode", "trancertcode", "isvalidDIC", "usercertcode" };
		headArr[1] = new String[] { "ID", "����", "֤������", "��ȫ�����ϸ�֤���", "�����������֤���", "�Ƿ���Ч", "�ƶ�ѹ������ʹ�õǼ�֤" };
		sheetMap5.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "�Ա���̨����Ϣ");

	}

	/**
	 * ��ȡÿ������˾��������ҵ���ж�������
	 * 
	 * @return
	 */
	public List<Object[]> findCarsGroupByAreaId(SysUser sysUser) {
		return equCarDao.findCarsGroupByAreaId(sysUser);
	}

	public List<EquCar> findEquCar(final List<String> carnoList) {
		return equCarDao.findEquCar(carnoList);
	}

	/**
	 * ��ѯ��ҵ������Ϣ��������carnoList ��Щ�� ��û�б��ϵĳ�
	 * 
	 * @param corpid
	 * @param carnoList
	 * @return
	 */
	public List<EquCar> findEquCar(final String corpid, final List<String> carnoList) {
		return equCarDao.findEquCar(corpid, carnoList);
	}
}
