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
 * @author 郭建军
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
	// 新增
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
	 * 保存方法，当新增和更新时分别调用Dao中不同的方法来操作
	 * 
	 * @param dicCorptrain
	 *            ,isNew:标志当前是新增操作还是修改操作
	 */
	public void save(EquCar car, boolean isNew) throws Exception {
		if (isNew) {
			equCarDao.saveOrUpdate(car);
		} else {
			log.debug("car.stnid : " + car.getStnid());
			equCarDao.updateEntity(car, car.getCarno());

		}
		// 判断当确认报销就删除报销车的车号
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
	 * 查找car实体的所有关联实体集合
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
	 * 查找所有充装介质id-名Map（去重)
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
	 * 查找equCar中的所有企业Map（去重)
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
			if (null == nmMap) {// 原计划clone，后改为下述实现方式
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
	 * 对某些个企业进行统计
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
	 * 根据用户提交的充装介质ID生成Map
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
	 * 根据用户提交的公司id生成List
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
	 * 当前充装介质项（根据用户提交数据进行过滤，如果没提交，则全部显示)
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
	 * 导出EXCEL
	 * 
	 * @param equCar
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(EquCar equCar, HttpServletResponse response) throws Exception {
		Map tabDefineMap = new LinkedHashMap();
		// 查询详细计划数据
		List<EquCar> list = this.find(equCar);
		// 增加一个sheet，其数据存在sheetMap中
		Map sheetMap = new HashMap();
		tabDefineMap.put("自备车台账信息", sheetMap);
		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap.put("list", list);
		// 标题
		sheetMap.put("title", "自备车台账信息");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		String[][] headArr = new String[2][];
		headArr[0] = new String[] { "carno", "corpidDIC", "cartypeid", "carkindname", "goodsname", "tankmatename", "carmakername",
				"madedate", "inroaddate", "isstandartrainname", "isdangerousname", "isexpirename", "expiredate", "pressuretypename",
				"isrentname", "rentidname", "stnshrinkname", "lightweight", "markloadweight", "capacity", "convlength", "carlength",
				"axisnum", "description" };
		headArr[1] = new String[] { "车号", "企业名称", "车型", "车种", "充装介质", "罐体材料", "制造厂商", "制造日期", "入路日期", "是否标准车", "是否危险品车", "是否报废", "报废日期",
				"压力类型", "租用状态", "租用方式", "过轨站名称", "自重", "标记载重", "容积", "换长", "车长", "轴数", "描述" };
		sheetMap.put("headArr", headArr);
		// 车号carno数据量过大 使用Restrictions.sqlRestriction(sql)方法过滤子表车号
		String carnoSql = "select A.CARNO from TB_ZBC_EQU_CAR A " + equCarDao.buildWhere(equCar);
		// -------------------------------------------------
		EquCarNpinfo equCarNpinfo = new EquCarNpinfo();
		equCarNpinfo.setCarnos(carnoSql);
		List<EquCarNpinfo> nps = equCarNpinfoDao.find(equCarNpinfo);
		Map sheetMap1 = new HashMap();
		tabDefineMap.put("常压罐车技术性能表", sheetMap1);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap1.put("list", nps);
		// 标题
		sheetMap1.put("title", "常压罐车技术性能表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		headArr = new String[2][];
		headArr[0] = new String[] { "carno", "fixeddist", "maxwidth", "maxhight", "centerlinehight", "axisdist", "wheelidameter",
				"aixweight", "ecoghigh", "ecoghight", "brakeequipmentno", "redirectno", "semidiameter", "maxloadweight", "minloadweight",
				"totalcapacity", "validcapacity", "workpressure", "worktemperature", "thickness" };
		headArr[1] = new String[] { "车号", "车辆定距", "车辆最大宽度", "车辆最大高度", "车钩中心线高", "固定轴距", "车轮直径", "轴重", "空车重心高", "重车重心高", "制动装置型号", "转向架型号",
				"通过最小曲线半径", "最大载重", "最小载重", "总容积", "有效容积", "工作压力", "工作温度", "罐体壁厚" };
		sheetMap1.put("headArr", headArr);
		// -------------------------------------------------
		EquCarHpinfo equCarHpinfo = new EquCarHpinfo();
		equCarHpinfo.setCarnos(carnoSql);
		List<EquCarHpinfo> hps = equCarHpinfoDao.find(equCarHpinfo);
		Map sheetMap2 = new HashMap();
		tabDefineMap.put("高压罐车技术性能表", sheetMap2);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap2.put("list", nps);
		// 标题
		sheetMap2.put("title", "高压罐车技术性能表");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
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
		// headArr[1] = new String[] { "车号", "转向架型号", "制动装置型号", "气密性试验", "设计压力",
		// "设计温度", "罐体重量", "腐蚀程度", "水压试验",
		// "罐体尺寸", "液面计型号", "液面计型式", "有效高度", "液相阀型号", "气相阀型号", "公称压力", "公称直径",
		// "温度计型号", "安全阀型号", "安全阀型式", "开启压力",
		// "全开压力", "回座压力", "磁浮球液位计型号" };
		headArr[0] = new String[] { "carno", "fixeddist", "maxwidth", "maxhight", "centerlinehight", "axisdist", "wheelidameter",
				"aixweight", "ecoghigh", "ecoghight", "brakeequipmentno", "redirectno", "semidiameter", "maxloadweight", "minloadweight",
				"totalcapacity", "validcapacity", "workpressure", "worktemperature", "tankmateid", "thickness" };
		headArr[1] = new String[] { "车号", "车辆定距(mm)", "车辆最大宽度(mm)", "车辆最大高度(mm)", "车钩中心线高(mm)", "固定轴距(mm)", "车轮直径(mm)", "轴重(吨)",
				"空车重心高(mm)", "重车重心高(mm)", "制动装置型号", "转向架型号", "通过最小曲线半径(m)", "最大载重(吨)", "最小载重(吨)", "总容积(m3)", "有效容积(m3)", "工作压力(MPa)",
				"工作温度(摄氏度)", "罐体材质", "罐体壁厚(mm)" };
		sheetMap2.put("headArr", headArr);

		// -------------------------------------------------
		EquCarHpreginfo equCarHpreginfo = new EquCarHpreginfo();
		equCarHpreginfo.setCarnos(carnoSql);
		List<EquCarHpreginfo> hprs = equCarHpreginfoDao.find(equCarHpreginfo);
		Map sheetMap3 = new HashMap();
		tabDefineMap.put("高压罐车压力容器使用登记证", sheetMap3);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap3.put("list", hprs);
		// 标题
		sheetMap3.put("title", "高压罐车压力容器使用登记证");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		headArr = new String[2][];
		headArr[0] = new String[] { "carno", "useregisterno", "internalno", "containername", "makecorp", "makelicense", "usedate",
				"carchassisno", "cartankno", "registercode", "registerdepartment", "remarks" };
		headArr[1] = new String[] { "车号", "使用登记证编号", "单位内编号", "压力容器名称", "制造单位", "制造许可证号", "启用日期", "车辆底盘编号", "车辆罐体编号", "注册编号", "注册机构", "描述" };
		sheetMap3.put("headArr", headArr);

		// -------------------------------------------------
		EquCarRent equCarRent = new EquCarRent();
		equCarRent.setCarnos(carnoSql);
		List<EquCarRent> equCarRents = equCarRentDao.find(equCarRent);
		Map sheetMap4 = new HashMap();
		tabDefineMap.put("自备车租用信息", sheetMap4);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap4.put("list", equCarRents);
		// 标题
		sheetMap4.put("title", "自备车租用信息");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		headArr = new String[2][];
		headArr[0] = new String[] { "id", "carno", "rentcorpid", "startdate", "enddate", "isvalidDIC", "remarks" };
		headArr[1] = new String[] { "ID", "车号", "租赁企业ID", "起始月份", "结束月份", "是否有效", "描述" };
		sheetMap4.put("headArr", headArr);

		// -------------------------------------------------
		EquCarCertificate equCarCertificate = new EquCarCertificate();
		equCarCertificate.setCarnos(carnoSql);
		List<EquCarCertificate> equCarCertificates = equCarCertificateDao.find(equCarCertificate);
		Map sheetMap5 = new HashMap();
		tabDefineMap.put("企业自备车证书", sheetMap5);

		// 在sheetMap中加入list，这是要输出的主要数据列表
		sheetMap5.put("list", equCarCertificates);
		// 标题
		sheetMap5.put("title", "企业自备车证书");

		// 表头数组，第一行为要输出的数据属性（用于取数据)，第二行为数据属性的显示名称（用于表头)
		// 这个数组中两行的列数应该是严格一致的
		headArr = new String[2][];
		headArr[0] = new String[] { "id", "trainno", "certdate", "saftcertcode", "trancertcode", "isvalidDIC", "usercertcode" };
		headArr[1] = new String[] { "ID", "车号", "证书日期", "安全技术合格证编号", "过轨运输许可证编号", "是否有效", "移动压力容器使用登记证" };
		sheetMap5.put("headArr", headArr);

		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "自备车台账信息");

	}

	/**
	 * 获取每个区域公司的下属企业都有多少辆车
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
	 * 查询企业车辆信息，不包括carnoList 这些车 且没有报废的车
	 * 
	 * @param corpid
	 * @param carnoList
	 * @return
	 */
	public List<EquCar> findEquCar(final String corpid, final List<String> carnoList) {
		return equCarDao.findEquCar(corpid, carnoList);
	}
}
