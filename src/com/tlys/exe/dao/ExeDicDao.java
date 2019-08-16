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
 * �ֵ�Dao
 * 
 * @author �״���
 * 
 */
@Repository
public class ExeDicDao extends HibernateDaoSupport {
	/**
	 * ȡ��Ʒ���ֵ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoods> listDicGoods() {
		return this.getHibernateTemplate().find(" from DicGoods order by pmhz asc");
	}

	/**
	 * ����Ʒ���ֵ��ѯƷ��
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoods> listDicGoodsByCdy_type(String cdy_type) {
		return this.getHibernateTemplate().find(" from DicGoods where pldm=?", cdy_type);
	}

	/**
	 * ����ƴ������ģ����ѯƷ���ֵ�
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
	 * ȡ��Ʒ���ֵ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoodsType> listDicGoodsType() {
		return this.getHibernateTemplate().find(" from DicGoodsType order  by bxh");
	}

	/**
	 * ȡ�ó�װ�����ֵ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicGoodscategory> listDicGoodscategory() {
		return this.getHibernateTemplate().find(" from DicGoodscategory order by orderno");
	}

	/**
	 * ����idȡ�ó�װ���ʶ���
	 * 
	 * @return
	 */
	public DicGoodscategory loadDicGoodscategory(String goodsid) {
		return (DicGoodscategory) this.getHibernateTemplate().load(DicGoodscategory.class, goodsid);
	}

	/**
	 * ����idArrȡ�ó�װ���ʶ���,028,010,
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
	 * ȡ�ó����ֵ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicCartype> listDicCartype() {
		return this.getHibernateTemplate().find(" from DicCartype");
	}

	/**
	 * ���ݳ�վ��ȡ�ó�վ��Ϣ
	 * 
	 * @param stationid
	 * @return
	 */
	public DicRwstation loadDicRwstation(String stationid) {
		return (DicRwstation) this.getHibernateTemplate().get(DicRwstation.class, stationid);
	}

	/**
	 * ������·��վ,����վ���ֻ�ƴ������Ҳ���
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
	 * ȡ���ڲ���ҵ�ֵ�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicSinocorp> listDicSinocorp() {
		return this.getHibernateTemplate().find(" from DicSinocorp where corptype='1' order by shortname");
	}

	/**
	 * ȡ���ڲ���ҵ�ֵ�,�������ݷ���Ȩ��
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicSinocorp> listDicSinocorp(String dataAutho) {
		return this.getHibernateTemplate().find(
				" from DicSinocorp where" + CommUtils.getCorpIds("corpid") + " and corptype='1' order by shortname");
	}

	/**
	 * ���ݹ�˾id���ҹ�˾
	 * 
	 * @param corpid
	 * @return
	 */
	public DicSinocorp loadDicSinocorp(String corpid) {
		return (DicSinocorp) getHibernateTemplate().get(DicSinocorp.class, corpid);
	}

	/**
	 * ���ݹ�˾idArr���ҹ�˾,35000000, 31550000, 31800000
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicSinocorp> listDicSinocorpByIdArr(String corpidArr) {
		return this.getHibernateTemplate().find(
				" from DicSinocorp where corpid in (" + corpidArr + ") and corptype='1' order by shortname");
	}

	/**
	 * ���������б��ѯ��˾��
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
	 * ȡ�ó��塢������޵�λ
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicRwdepartment> listDicRwdepartment() {
		return this.getHibernateTemplate().find(" from DicRwdepartment where tpyeid='02'");
	}

	/**
	 * ȡ��·�ִ��뼰����
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicRwbureau> listAdm() {
		return this.getHibernateTemplate().find(" from DicRwbureau order  by orderno");
	}

	/**
	 * ����ƴ����ͺ���ȡ�ó�վ���룬��ѯ��ʱ����
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
	 * ���ݳ�վ�籨�������������Ӧ��ƴ���룬����BJP�ͱ����ҵ�ƴ����ΪBJ
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
	 * ȡ������˾�б�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DicAreacorp> listDicAreacorp() {
		return this.getHibernateTemplate().find(" from DicAreacorp order by areaid");
	}

	/**
	 * �����û�Ȩ��ȡ����������
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
	 * ������ҵIDarrȡ�������б�
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
	 * ����Ԥ����ʶ��ѯԤ��ʱ���
	 * 
	 * @param over_type
	 * @return
	 */
	public List<DicOverTime> listDicOverTime(String over_type) {
		return this.getHibernateTemplate().find(" from DicOverTime d where d.over_type=? order by over_id desc",
				over_type);
	}

	/**
	 * ����Ԥ����ʶ��ѯԤ��ʱ���
	 * 
	 * @param over_type
	 * @return
	 */
	public List<DicOverTime> listDicOverTimeByZbc_flag(String zbc_flag) {
		return this.getHibernateTemplate().find(
				" from DicOverTime d where d.zbc_flag=? and d.over_type='1' order by over_id desc", zbc_flag);
	}

	/**
	 * ���ݳ�ʱid���ҳ�ʱԤ��ʱ���
	 * 
	 * @param over_id
	 * @return
	 */
	public DicOverTime loadDicOverTime(String over_id) {
		return (DicOverTime) this.getHibernateTemplate().get(DicOverTime.class, over_id);
	}

	/**
	 * ���ݳ�ʱidArr���ҳ�ʱԤ��ʱ���,1,2,
	 * 
	 * @param corpid
	 * @return
	 */
	public List<DicOverTime> listDicOverTimeByIdArr(String overidArr) {
		return this.getHibernateTemplate().find(
				" from DicOverTime where over_id in (" + overidArr + ") order by over_id desc");
	}

	/**
	 * ȡ�ó���
	 * 
	 * @return
	 */
	public List<DicCarkind> listDicCarkind() {
		return getHibernateTemplate().find("from DicCarkind");
	}

	/**
	 * ȡ�ó���������ҵ
	 * 
	 * @return
	 */
	public List<DicCarmaker> listDicCarmaker() {
		return getHibernateTemplate().find(" from DicCarmaker");
	}

	/**
	 * ���ݹ�˾idȡ����ҵվ���б�
	 * 
	 * @param entpr_id
	 * @return
	 */
	public List<DicEntprStn> listDicEntprStn(String entpr_id) {
		return getHibernateTemplate().find(" from DicEntprStn where entpr_id=?", entpr_id);
	}

	/**
	 * ������ҵid����ҵվ������AEI���
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
	 * ������ҵid��վ�����룬������־��AEI ��Ų�ѯ������
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
	 * ȡ�ù�����޵�λ
	 * 
	 * @return
	 */
	public List<DicTankrepcorp> listDicTankrepcord() {
		return getHibernateTemplate().find(" from DicTankrepcorp");
	}

	/**
	 * �����������Ʋ��Ҹ�����
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
	 * ������ҵ���Ʋ����ҵ
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
	 * ���ݵ籨��ȡ����·��վ�ֵ�
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
	 * ���ݳ�վidȡ�ó�վ�ֵ�
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
	 * ���ݳ���ȡ���Ա�����Ϣ
	 * 
	 * @param car_no
	 * @return
	 */
	public EquCar getEquCarByCarno(String car_no) {
		return (EquCar) getHibernateTemplate().get(EquCar.class, car_no);
	}

	/**
	 * ���ݳ���Arrȡ���Ա����б�1000001,1000002
	 * 
	 * @param car_noArr
	 * @return
	 */
	public List<EquCar> getEquCarByCarnoArr(String car_noArr) {
		return this.getHibernateTemplate().find(" from EquCar where carno in (" + StringUtil.operStr(car_noArr) + ")");
	}

	/**
	 * ���ݳ���ȡ����ҵ���ƣ����Ա���̨���������,���ڴ�ǰ̨ҳ��ֱ�ӻ�ȡ�Ա��������ʹ����ҵ
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
	 * ȡ��������ҵվ��
	 * 
	 * @return
	 */
	public List<DicEntprStn> listEntprStn() {
		return getHibernateTemplate().find(" from DicEntprStn order by entpr_stn_code");
	}

	/**
	 * ���ݳ���Arrȡ���Ա����б�1000001,1000002
	 * 
	 * @param car_noArr
	 * @return
	 */
	public List<DicEntprStn> getEntprStnByStnIdArr(String stnidArr) {
		return this.getHibernateTemplate().find(
				" from DicEntprStn where entpr_stn_code in (" + StringUtil.operStr(stnidArr) + ")");
	}

	/**
	 * ���ݳ���Arrȡ�ö�Ӧ��������ҵ
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
	 * ������ҵվ���������ҵվ�����֣�������ѯ�������õ�
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
	 * ���ݳ���arr��ѯ��Ӧ�ĳ�װ����
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
	 * ���ݳ���arr��ѯ��Ӧ�Ľ�����ʶ
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
	 * ����lis��Ʒ������ȡ����·Ʒ������
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
	 * ȡ��webservice�з���������ǰ�������ڼ��
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
	 * ȡ��վ����ҵ,AEI������ѯ����õ�
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
