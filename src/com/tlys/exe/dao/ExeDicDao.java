package com.tlys.exe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.tlys.comm.util.CommUtils;
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
import com.tlys.exe.common.util.StringUtil;

/**
 * 字典Dao
 * 
 * @author 孔垂云
 * 
 */
@Repository
public class ExeDicDao extends HibernateDaoSupport {
	/**
	 * 取得品名字典
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoods> listDicGoods() {
		return this.getHibernateTemplate().find(" from DicGoods order by pmhz asc");
	}

	/**
	 * 根据品类字典查询品名
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoods> listDicGoodsByCdy_type(String cdy_type) {
		return this.getHibernateTemplate().find(" from DicGoods where pldm=?", cdy_type);
	}

	/**
	 * 根据拼音或汉字模糊查询品类字典
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoods> listDicGoodsByPymAndHz(String str) {
		return this.getHibernateTemplate().find(" from DicGoods where pym like ? or pmhz like ? order by pmhz",
				new String[] { "%" + str.toUpperCase() + "%", "%" + str + "%" });
	}

	/**
	 * 取得品类字典
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoodsType> listDicGoodsType() {
		return this.getHibernateTemplate().find(" from DicGoodsType order  by bxh");
	}

	/**
	 * 取得充装介质字典
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoodscategory> listDicGoodscategory() {
		return this.getHibernateTemplate().find(" from DicGoodscategory order by orderno");
	}

	/**
	 * 根据id取得充装介质对象
	 * 
	 * @return
	 */
	public DicGoodscategory loadDicGoodscategory(String goodsid) {
		return (DicGoodscategory) this.getHibernateTemplate().load(DicGoodscategory.class, goodsid);
	}

	/**
	 * 根据idArr取得充装介质对象,028,010,
	 * 
	 * @param corpid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoodscategory> listDicGoodscategoryByIdArr(final String goodsidArr) {
		return (List<DicGoodscategory>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Criteria ca = s.createCriteria(DicGoodscategory.class);
				ca.add(Restrictions.in("goodsid", goodsidArr.split(",")));
				return ca.list();
			}
		});
	}

	/**
	 * 取得车型字典
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicCartype> listDicCartype() {
		return this.getHibernateTemplate().find(" from DicCartype");
	}

	/**
	 * 根据车站码取得车站信息
	 * 
	 * @param stationid
	 * @return
	 */
	public DicRwstation loadDicRwstation(String stationid) {
		return (DicRwstation) this.getHibernateTemplate().get(DicRwstation.class, stationid);
	}

	/**
	 * 过滤铁路车站,按车站汉字或拼音码查找查找
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<DicRwstation> filterRwstation(String pycode) {
		return this.getHibernateTemplate().find(
				" from DicRwstation where shortname like ? or stationpycode like ? order by tmiscode asc",
				new String[] { "%" + pycode + "%", "%" + pycode.toUpperCase() + "%" });
	}

	/**
	 * 取得内部企业字典
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicSinocorp> listDicSinocorp() {
		return this.getHibernateTemplate().find(" from DicSinocorp where corptype='1' order by shortname");
	}

	/**
	 * 取得内部企业字典,包含数据访问权限
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicSinocorp> listDicSinocorp(String dataAutho) {
		return this.getHibernateTemplate().find(
				" from DicSinocorp where" + CommUtils.getCorpIds("corpid") + " and corptype='1' order by shortname");
	}

	/**
	 * 根据公司id查找公司
	 * 
	 * @param corpid
	 * @return
	 */
	public DicSinocorp loadDicSinocorp(String corpid) {
		return (DicSinocorp) getHibernateTemplate().get(DicSinocorp.class, corpid);
	}

	/**
	 * 根据公司idArr查找公司,35000000, 31550000, 31800000
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorpByIdArr(String corpidArr) {
		return this.getHibernateTemplate().find(
				" from DicSinocorp where corpid in (" + corpidArr + ") and corptype='1' order by shortname");
	}

	/**
	 * 根据区域列表查询公司，
	 * 
	 * @param areaidArr
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorpByAreaIdArr(String areaidArr) {
		return this.getHibernateTemplate().find(
				" from DicSinocorp where areaid in (" + StringUtil.operStr(areaidArr)
						+ ") and corptype='1' order by shortname");
	}

	/**
	 * 取得车体、罐体检修单位
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicRwdepartment> listDicRwdepartment() {
		return this.getHibernateTemplate().find(" from DicRwdepartment where tpyeid='02'");
	}

	/**
	 * 取得路局代码及名称
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicRwbureau> listAdm() {
		return this.getHibernateTemplate().find(" from DicRwbureau order  by orderno");
	}

	/**
	 * 根据拼音码和汉字取得车站代码，查询的时候用
	 * 
	 * @param stationpycode
	 * @param shortname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DicRwstation getRwStationCode(String stationpycode, String shortname) {
		List<DicRwstation> list = this.getHibernateTemplate().find(
				" from DicRwstation where stationpycode=? and shortname=?", new String[] { stationpycode, shortname });
		if (list.size() > 0)
			return list.iterator().next();
		else
			return null;
	}

	/**
	 * 根据车站电报码和名称找它对应的拼音码，比如BJP和北京找到拼音码为BJ
	 * 
	 * @param stn_code
	 * @param stn_name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DicRwstation getRwStationPym(String stn_code, String stn_name) {
		List<DicRwstation> list = this.getHibernateTemplate().find(
				" from DicRwstation where telegramid=? and shortname=?", new String[] { stn_code, stn_name });
		if (list.size() > 0)
			return list.iterator().next();
		else
			return null;
	}

	/**
	 * 取得区域公司列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicAreacorp> listDicAreacorp() {
		return this.getHibernateTemplate().find(" from DicAreacorp order by areaid");
	}

	/**
	 * 根据用户权限取得所有区域
	 * 
	 * @param dataAutho
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicAreacorp> listDicAreacorp(String dataAutho) {
		return this.getHibernateTemplate().find(
				" from DicAreacorp where areaid in (select distinct areaid from DicSinocorp where "
						+ CommUtils.getCorpIds("corpid") + ")order by areaid");
	}

	/**
	 * 根据企业IDarr取得区域列表
	 * 
	 * @param corpidArr
	 * @return
	 */
	public List<DicAreacorp> listDicAreacorpByCorpid(String corpidArr) {
		return this.getHibernateTemplate().find(
				" from DicAreacorp where areaid in (select distinct areaid from DicSinocorp where corpid in ("
						+ corpidArr + "))order by areaid,shortname");
	}

	/**
	 * 根据预警标识查询预警时间段
	 * 
	 * @param over_type
	 * @return
	 */
	public List<DicOverTime> listDicOverTime(String over_type) {
		return this.getHibernateTemplate().find(" from DicOverTime d where d.over_type=? order by over_id desc",
				over_type);
	}

	/**
	 * 根据预警标识查询预警时间段
	 * 
	 * @param over_type
	 * @return
	 */
	public List<DicOverTime> listDicOverTimeByZbc_flag(String zbc_flag) {
		return this.getHibernateTemplate().find(
				" from DicOverTime d where d.zbc_flag=? and d.over_type='1' order by over_id desc", zbc_flag);
	}

	/**
	 * 根据超时id查找超时预警时间段
	 * 
	 * @param over_id
	 * @return
	 */
	public DicOverTime loadDicOverTime(String over_id) {
		return (DicOverTime) this.getHibernateTemplate().get(DicOverTime.class, over_id);
	}

	/**
	 * 根据超时idArr查找超时预警时间段,1,2,
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicOverTime> listDicOverTimeByIdArr(String overidArr) {
		return this.getHibernateTemplate().find(
				" from DicOverTime where over_id in (" + overidArr + ") order by over_id desc");
	}

	/**
	 * 取得车种
	 * 
	 * @return
	 */
	public List<DicCarkind> listDicCarkind() {
		return getHibernateTemplate().find("from DicCarkind");
	}

	/**
	 * 取得车辆制造企业
	 * 
	 * @return
	 */
	public List<DicCarmaker> listDicCarmaker() {
		return getHibernateTemplate().find(" from DicCarmaker");
	}

	/**
	 * 根据公司id取得企业站区列表
	 * 
	 * @param entpr_id
	 * @return
	 */
	public List<DicEntprStn> listDicEntprStn(String entpr_id) {
		return getHibernateTemplate().find(" from DicEntprStn where entpr_id=?", entpr_id);
	}

	/**
	 * 根据企业id和企业站区查找AEI编号
	 * 
	 * @param entpr_id
	 * @param entpr_stn_code
	 * @return
	 */
	public List<Integer> getAei_noByEntpr(String entpr_id, String entpr_stn_code) {
		return getHibernateTemplate().find(
				"select distinct id.aei_no from DicEntprstnIO where id.entpr_id=? and id.entpr_stn_code=?",
				new String[] { entpr_id, entpr_stn_code });
	}

	/**
	 * 根据企业id，站区代码，进出标志，AEI 编号查询上下行
	 * 
	 * @return
	 */
	public String getTrain_dirByEntprstn(String entpr_id, String entpr_stn_code, int aei_no, String in_out_flag) {
		List<Object> list = getHibernateTemplate()
				.find(
						"select id.train_dir from DicEntprstnIO where id.entpr_id=? and id.entpr_stn_code=? and id.aei_no=? and id.in_out_flag=?",
						new Object[] { entpr_id, entpr_stn_code, aei_no, in_out_flag });
		if (list.size() > 0)
			return (String) list.get(0);
		else
			return "";
	}

	/**
	 * 取得罐体检修单位
	 * 
	 * @return
	 */
	public List<DicTankrepcorp> listDicTankrepcord() {
		return getHibernateTemplate().find(" from DicTankrepcorp");
	}

	/**
	 * 根据区域名称查找该区域
	 * 
	 * @param areaname
	 * @return
	 */
	public DicAreacorp loadDicAreacorpByShrinkname(final String areaname) {
		return (DicAreacorp) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(" from DicAreacorp where shrinkname=:shrinkname");
				query.setString("shrinkname", areaname);
				return query.uniqueResult();
			}
		});
	}

	/**
	 * 根据企业名称查该企业
	 * 
	 * @param shortname
	 * @return
	 */
	public DicSinocorp loadDicSinocorpByShortname(final String shortname) {
		return (DicSinocorp) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(" from DicSinocorp where shortname=:shortname");
				query.setString("shortname", shortname);
				return query.uniqueResult();
			}
		});
	}

	/**
	 * 根据电报码取得铁路车站字典
	 * 
	 * @param shortname
	 * @return
	 */
	public DicRwstation getRwStationByTelegramid(final String telegramid) {
		return (DicRwstation) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(" from DicRwstation where telegramid=:telegramid");
				query.setString("telegramid", telegramid);
				return query.uniqueResult();
			}
		});
	}

	/**
	 * 根据车站id取得车站字典
	 * 
	 * @param stationid
	 * @return
	 */
	public DicRwstation getRwStationByStationid(final String stationid) {
		return (DicRwstation) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(" from DicRwstation where stationid=:stationid");
				query.setString("stationid", stationid);
				return query.uniqueResult();
			}
		});
	}

	/**
	 * 根据车号取得自备车信息
	 * 
	 * @param car_no
	 * @return
	 */
	public EquCar getEquCarByCarno(String car_no) {
		return (EquCar) getHibernateTemplate().get(EquCar.class, car_no);
	}

	/**
	 * 根据车号Arr取得自备车列表，1000001,1000002
	 * 
	 * @param car_noArr
	 * @return
	 */
	public List<EquCar> getEquCarByCarnoArr(String car_noArr) {
		return this.getHibernateTemplate().find(" from EquCar where carno in (" + StringUtil.operStr(car_noArr) + ")");
	}

	/**
	 * 根据车号取得企业名称，从自备车台账里面查找,用于从前台页面直接获取自备车里面的使用企业
	 * 
	 * @param stationid
	 * @return
	 */
	public HashMap<String, String> getCorpNameByCar_noArr(String car_noArr) {
		List<EquCar> list = this.getEquCarByCarnoArr(car_noArr);
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (EquCar car : list) {
			hashMap.put(car.getCarno(), car.getCorpshrinkname());
		}
		return hashMap;
	}

	/**
	 * 取得所有企业站区
	 * 
	 * @return
	 */
	public List<DicEntprStn> listEntprStn() {
		return getHibernateTemplate().find(" from DicEntprStn order by entpr_stn_code");
	}

	/**
	 * 根据车号Arr取得自备车列表，1000001,1000002
	 * 
	 * @param car_noArr
	 * @return
	 */
	public List<DicEntprStn> getEntprStnByStnIdArr(String stnidArr) {
		return this.getHibernateTemplate().find(
				" from DicEntprStn where entpr_stn_code in (" + StringUtil.operStr(stnidArr) + ")");
	}

	/**
	 * 根据车号Arr取得对应的所属企业
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getCorpNameByCar_noArr2(String car_noArr) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String sql = "select CORPSHRINKNAME,carno from TB_ZBC_EQU_CAR where carno in (" + StringUtil.operStr(car_noArr)
				+ ")";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				hashMap.put(rs.getString("carno"), rs.getString("CORPSHRINKNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hashMap;

	}

	/**
	 * 根据企业站区代码查企业站区名字，车辆查询界面里用到
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getEntprStnByStnCode(String stn_codeArr) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String sql = "select entpr_stn_name,entpt_stn_code from tb_zbc_dic_entpr_stn where entpr_stn_code in ("
				+ StringUtil.operStr(stn_codeArr) + ")";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				hashMap.put(rs.getString("entpt_stn_code"), rs.getString("entpr_stn_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hashMap;

	}

	/**
	 * 根据车号arr查询对应的充装介质
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getMediumIdByCar_noArr(String car_noArr) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String sql = "select (select goodsname from TB_ZBC_DIC_GOODSCATEGORY where goodsid=c.goodsid) goodsname,c.carno from TB_ZBC_EQU_CAR c where c.carno in ("
				+ StringUtil.operStr(car_noArr) + ")";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				hashMap.put(rs.getString("carno"), rs.getString("goodsname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hashMap;
	}

	/**
	 * 根据车号arr查询对应的进出标识
	 * 
	 * @param car_noArr
	 * @return
	 */
	public HashMap<String, String> getInOutFlagByRptNameArr(String rptNameArr) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String sql = "select c.rptname,in_out_flag from tb_zbc_exe_stntrn t,tb_zbc_exe_stncar c where  t.rptname=c.rptname and c.rptname in ("
				+ StringUtil.operStr(rptNameArr) + ")";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				hashMap.put(rs.getString("rptname"), rs.getString("in_out_flag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hashMap;
	}

	/**
	 * 根据lis的品名代码取得铁路品名代码
	 * 
	 * @param lis_cdy_code
	 * @return
	 */
	public String getCdy_codeByList(String lis_cdy_code) {
		String sql = "select g.dm, g.pmhz, g.pym, g.pldm  from tb_zbc_dic_product_lis l, tb_zbc_dic_product t, tb_zbc_dic_goods g where l.liscode =?  and l.productid = t.prodid  and t.rwkindid = g.dm";
		String cdy_code = "";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lis_cdy_code);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				cdy_code = rs.getString("dm");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cdy_code;
	}

	/**
	 * 取得webservice中发货日期往前后推日期间隔
	 * 
	 * @return
	 */
	public int[] getCdy_date_diff() {
		int[] diff = new int[] { 0, 0 };
		String sql = "select * from tb_zbc_exe_webserviceconfig";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				diff[0] = rs.getInt("cdy_date_pre");
				diff[1] = rs.getInt("cdy_date_last");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return diff;
	}

	/**
	 * 取得站区企业,AEI车辆查询左侧用到
	 * 
	 * @return
	 */
	public List<DicSinocorp> listZqqy(int type) {
		List<DicSinocorp> list = new ArrayList<DicSinocorp>();
		String sql = "";
		if (type == 1)
			sql = "select *  from tb_zbc_dic_sinocorp where corpid in (select entpr_id from tb_zbc_dic_entpr_stn) order by shortname";
		else if (type == 2)
			sql = "select * from tb_zbc_dic_sinocorp where corpid in (select distinct corpid from tb_zbc_equ_car where isexpire='0') order by shortname";
		else if (type == 3)
			sql = "select * from tb_zbc_dic_sinocorp where corpid in (select distinct corpid from tb_zbc_equ_car where isexpire='0') order by areaid,shortname";
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = null;
		try {
			conn = cp.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				DicSinocorp dicSinocorp = new DicSinocorp();
				dicSinocorp.setCorpid(rs.getString("corpid"));
				dicSinocorp.setFullname(rs.getString("fullname"));
				dicSinocorp.setShortname(rs.getString("shortname"));
				dicSinocorp.setShrinkname(rs.getString("shrinkname"));
				list.add(dicSinocorp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cp.closeConnection(conn);
				cp.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
