package com.tlys.exe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.tlys.comm.util.CommUtils;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.common.util.StringUtil;

@Repository
public class ExeTranStatDao extends HibernateDaoSupport {

	/**
	 * 车辆运输统计报表 根据日期和区域id查该区域下的所有公司及品名统计数据
	 * 
	 * @return
	 */
	public List<ExeTranStasVO> tranStatictics(String statDate, String areaid) throws Exception {
		ConnectionProvider cp = ((SessionFactoryImplementor) getSessionFactory()).getConnectionProvider();
		Connection conn = cp.getConnection();
		String areaname = this.getAreaname(conn, areaid);
		List<ExeTranStasVO> listTrans = this.getStatList(conn, statDate, areaid);
		List<DicSinocorp> listSinocorp = this.getSinocorpList(conn, areaid);
		List<ExeTranStasVO> listAllStat = new ArrayList<ExeTranStasVO>();// 定义全部公司的列表
		List<ExeTranStasVO> listRetStat = new ArrayList<ExeTranStasVO>();// 定义整个区域的列表
		ExeTranStasVO areaHJVO = new ExeTranStasVO();
		areaHJVO.setAreaid(areaid);
		areaHJVO.setAreaname(areaname);
		areaHJVO.setCorpid(areaid);
		areaHJVO.setCorpname(areaname);
		areaHJVO.setCdy_name("合计");
		areaHJVO.setCdy_code("合计");
		for (DicSinocorp corp : listSinocorp) {
			int corprowspan = 1;
			ExeTranStasVO corpHJVO = new ExeTranStasVO();
			corpHJVO.setAreaid(areaid);
			corpHJVO.setCorpid(corp.getCorpid());
			corpHJVO.setCorpname(corp.getShortname());
			corpHJVO.setCdy_code("小计");
			corpHJVO.setCdy_name("小计");
			corpHJVO.setDest_stn("");
			corpHJVO.setDest_name("");
			List<String[]> listCdy = this.getCdyByCorpid(conn, corp.getCorpid());
			List<ExeTranStasVO> tempCoprList = new ArrayList<ExeTranStasVO>();
			for (String[] cdy : listCdy)// 品名循环，获取对应的到站
			{
				ExeTranStasVO cdyHJVO = new ExeTranStasVO();
				corprowspan++;
				cdyHJVO.setAreaid(areaid);
				cdyHJVO.setCorpid(corp.getCorpid());
				cdyHJVO.setCorpname(corp.getShortname());
				cdyHJVO.setCdy_code(cdy[0]);
				cdyHJVO.setCdy_name(cdy[1]);
				cdyHJVO.setDest_name("小计");
				cdyHJVO.setDest_stn("小计");
				List<ExeTranStasVO> tempCdyList = new ArrayList<ExeTranStasVO>();
				int cdyrowspan = 1;
				for (ExeTranStasVO totalVO : listTrans) {
					if (totalVO.getCorpid().equals(corp.getCorpid()) && totalVO.getCdy_code().equals(cdy[0])) {
						ExeTranStasVO tranStasVO = new ExeTranStasVO();
						tranStasVO.setAreaid(areaid);
						tranStasVO.setCorpid(corp.getCorpid());
						tranStasVO.setCorpname(corp.getShortname());
						tranStasVO.setCdy_code(cdy[0]);
						tranStasVO.setCdy_name(totalVO.getCdy_name());
						tranStasVO.setDest_stn(totalVO.getDest_stn());
						tranStasVO.setDest_name(totalVO.getDest_name());
						tranStasVO.setCur_carnum(totalVO.getCur_carnum());
						tranStasVO.setCur_wgt(totalVO.getCur_wgt());
						tranStasVO.setTotal_carnum(totalVO.getTotal_carnum());
						tranStasVO.setTotal_wgt(totalVO.getTotal_wgt());
						// 品名合计赋值
						cdyHJVO.setCur_carnum(cdyHJVO.getCur_carnum() + totalVO.getCur_carnum());
						cdyHJVO.setCur_wgt(cdyHJVO.getCur_wgt() + totalVO.getCur_wgt());
						cdyHJVO.setTotal_carnum(cdyHJVO.getTotal_carnum() + totalVO.getTotal_carnum());
						cdyHJVO.setTotal_wgt(cdyHJVO.getTotal_wgt() + totalVO.getTotal_wgt());
						// 公司合计赋值
						corpHJVO.setCur_carnum(corpHJVO.getCur_carnum() + totalVO.getCur_carnum());
						corpHJVO.setCur_wgt(corpHJVO.getCur_wgt() + totalVO.getCur_wgt());
						corpHJVO.setTotal_carnum(corpHJVO.getTotal_carnum() + totalVO.getTotal_carnum());
						corpHJVO.setTotal_wgt(corpHJVO.getTotal_wgt() + totalVO.getTotal_wgt());
						// 区域合计赋值
						areaHJVO.setCur_carnum(areaHJVO.getCur_carnum() + totalVO.getCur_carnum());
						areaHJVO.setCur_wgt(areaHJVO.getCur_wgt() + totalVO.getCur_wgt());
						areaHJVO.setTotal_carnum(areaHJVO.getTotal_carnum() + totalVO.getTotal_carnum());
						areaHJVO.setTotal_wgt(areaHJVO.getTotal_wgt() + totalVO.getTotal_wgt());

						tempCdyList.add(tranStasVO);
						corprowspan++;
						cdyrowspan++;
					}
				}
				cdyHJVO.setCdyrowspan(cdyrowspan);
				tempCoprList.add(cdyHJVO);
				for (ExeTranStasVO vo : tempCdyList)
					tempCoprList.add(vo);
			}
			corpHJVO.setCorprowspan(corprowspan);
			listAllStat.add(corpHJVO);

			for (ExeTranStasVO vo : tempCoprList)
				listAllStat.add(vo);
		}
		listRetStat.add(areaHJVO);
		for (ExeTranStasVO vo : listAllStat)
			listRetStat.add(vo);

		conn.close();
		return listRetStat;
	}

	public int dealInt(Object obj) {
		return obj == null ? 0 : Integer.parseInt(obj.toString());
	}

	/**
	 * 取得所有统计数
	 * 
	 * @param statDate
	 * @return
	 */
	public List<ExeTranStasVO> getStatList(Connection conn, String statDate, String areaid) throws Exception {
		List<ExeTranStasVO> listTrans = new ArrayList<ExeTranStasVO>();
		String sqlTotal = "select car_user_id,(select shortname from tb_zbc_dic_sinocorp where corpid=t.car_user_id) corpname,";
		sqlTotal += " CDY_CODE,(select pmhz from tb_zbc_dic_goods where dm=t.cdy_code) cdy_name, ";
		sqlTotal += "  dest_stn,(select shortname from tb_zbc_dic_rwstation where telegramid=t.dest_stn) dest_name, ";
		sqlTotal += "  sum(CAR_ACTUAL_WGT) sum, ";
		sqlTotal += " count(*) cn ";
		sqlTotal += " from tb_zbc_exe_transport t ";
		sqlTotal += " where car_user_id in ";
		sqlTotal += "      (select corpid from tb_zbc_dic_sinocorp where areaid = ?) ";
		sqlTotal += "  and to_char(WB_DATE, 'yyyy-mm') between ? and ? ";
		sqlTotal += " group by car_user_id, CDY_CODE, dest_stn ";
		sqlTotal += "  order by car_user_id, cdy_code, dest_stn ";

		PreparedStatement pstmt = conn.prepareStatement(sqlTotal);
		pstmt.setString(1, areaid);
		pstmt.setString(2, statDate.substring(0, 4) + "-01");
		pstmt.setString(3, statDate.substring(0, 7));
		ResultSet rs = pstmt.executeQuery();
		List<ExeTranStasVO> listTotal = new ArrayList<ExeTranStasVO>();// 定义总计数
		while (rs.next()) {
			ExeTranStasVO tranStasVO = new ExeTranStasVO();
			tranStasVO.setAreaid(areaid);
			tranStasVO.setCorpid(rs.getString("car_user_id"));
			tranStasVO.setCorpname(rs.getString("corpname"));
			tranStasVO.setCdy_code(rs.getString("cdy_code"));
			tranStasVO.setCdy_name(rs.getString("cdy_name"));
			tranStasVO.setDest_stn(rs.getString("dest_stn"));
			tranStasVO.setDest_name(rs.getString("dest_name"));
			tranStasVO.setTotal_carnum(dealInt(rs.getInt("cn")));
			tranStasVO.setTotal_wgt(dealInt(rs.getInt("sum")));
			listTotal.add(tranStasVO);
		}
		String sqlCur = "select car_user_id,(select shortname from tb_zbc_dic_sinocorp where corpid=t.car_user_id) corpname,";
		sqlCur += " CDY_CODE,(select pmhz from tb_zbc_dic_goods where dm=t.cdy_code) cdy_name, ";
		sqlCur += "  dest_stn,(select shortname from tb_zbc_dic_rwstation where telegramid=t.dest_stn) dest_name, ";
		sqlCur += "  sum(CAR_ACTUAL_WGT) sum, ";
		sqlCur += " count(*) cn ";
		sqlCur += " from tb_zbc_exe_transport t ";
		sqlCur += " where car_user_id in ";
		sqlCur += "      (select corpid from tb_zbc_dic_sinocorp where areaid =?) ";
		sqlCur += "  and to_char(WB_DATE, 'yyyy-mm') =? ";
		sqlCur += " group by car_user_id, CDY_CODE, dest_stn ";
		sqlCur += "  order by car_user_id, cdy_code, dest_stn ";

		pstmt = conn.prepareStatement(sqlCur);
		pstmt.setString(1, areaid);
		pstmt.setString(2, statDate.substring(0, 7));
		rs = pstmt.executeQuery();
		List<ExeTranStasVO> listCur = new ArrayList<ExeTranStasVO>();// 定义当月数
		while (rs.next()) {
			ExeTranStasVO tranStasVO = new ExeTranStasVO();
			tranStasVO.setAreaid(areaid);
			tranStasVO.setCorpid(rs.getString("car_user_id"));
			tranStasVO.setCorpname(rs.getString("corpname"));
			tranStasVO.setCdy_code(rs.getString("cdy_code"));
			tranStasVO.setCdy_name(rs.getString("cdy_name"));
			tranStasVO.setDest_stn(rs.getString("dest_stn"));
			tranStasVO.setDest_name(rs.getString("dest_name"));
			tranStasVO.setCur_carnum(dealInt(rs.getInt("cn")));
			tranStasVO.setCur_wgt(dealInt(rs.getInt("sum")));
			listCur.add(tranStasVO);
		}

		// 把两个list合并
		for (ExeTranStasVO totalVO : listTotal) {
			boolean flag = true;
			for (ExeTranStasVO curVO : listCur) {
				if (totalVO.getCorpid().equals(curVO.getCorpid()) && totalVO.getCdy_code().equals(curVO.getCdy_code())
						&& totalVO.getDest_stn().equals(curVO.getDest_stn())) {
					totalVO.setCur_carnum(curVO.getCur_carnum());
					totalVO.setCur_wgt(curVO.getCur_wgt());
					listTrans.add(totalVO);
					flag = false;
					break;
				}
			}
			if (flag)
				listTrans.add(totalVO);
		}

		return listTrans;
	}

	/**
	 * 取得公司列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DicSinocorp> getSinocorpList(Connection conn, String areaid) throws Exception {
		List<DicSinocorp> listSinocorp = new ArrayList<DicSinocorp>();// 取得该区域的所属公司
		String sqlSinocorp = "select corpid,shortname from tb_zbc_dic_sinocorp where areaid=?";
		PreparedStatement pstmt = conn.prepareStatement(sqlSinocorp);
		pstmt.setString(1, areaid);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			DicSinocorp corp = new DicSinocorp();
			corp.setCorpid(rs.getString("corpid"));
			corp.setShortname(rs.getString("shortname"));
			corp.setAreaid(areaid);
			listSinocorp.add(corp);
		}
		return listSinocorp;
	}

	/**
	 * 根据企业id取得该企业对应的品名
	 * 
	 * @param corp_id
	 * @return
	 * @throws Exception
	 */
	public List<String[]> getCdyByCorpid(Connection conn, String corp_id) throws Exception {
		List<String[]> listCdy = new ArrayList<String[]>();
		String sqlSinocorp = "select distinct cdy_code,(select pmhz from tb_zbc_dic_goods where dm=t.cdy_code) cdy_name from tb_zbc_exe_transport t where car_user_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sqlSinocorp);
		pstmt.setString(1, corp_id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			String str[] = new String[] { rs.getString("cdy_code"), rs.getString("cdy_name") };
			listCdy.add(str);
		}
		return listCdy;
	}

	/**
	 * 根据区域id取得该区域的名称
	 * 
	 * @param corp_id
	 * @return
	 * @throws Exception
	 */
	public String getAreaname(Connection conn, String areaid) throws Exception {
		String name = "";
		String sqlSinocorp = "select shortname from tb_zbc_dic_areacorp where areaid=?";
		PreparedStatement pstmt = conn.prepareStatement(sqlSinocorp);
		pstmt.setString(1, areaid);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			name = rs.getString("shortname");
		}
		return name;
	}
	/**
	 * 查询全部区域ID和区域名称
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<DicAreacorp> getDicAreacorpInfo()throws Exception{
		
		List<DicAreacorp> list = new ArrayList<DicAreacorp>(); 
		
		list = getHibernateTemplate().find("from DicAreacorp");
		
		return list;
	}

	/**
	 * 自备车运用统计表
	 * 
	 * @return
	 */
	public List<Object[]> getTransYYStat(String stat_type, String year, String quarter, String month, String entpr_id,
			String medium_id) {
		String sqlDate = "";
		if (stat_type.equals("0"))// 按月统计
		{
			sqlDate = " to_char(wb_date,'yyyy-mm') = '" + year + "-" + month + "'";
		} else if (stat_type.equals("1"))// 按季统计
		{
			if (quarter.equals("1"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-01' and '" + year + "-03'";
			else if (quarter.equals("2"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-04' and '" + year + "-06'";
			else if (quarter.equals("3"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-07' and '" + year + "-09'";
			else if (quarter.equals("4"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-10' and '" + year + "-12'";
		} else if (stat_type.equals("2"))// 按年统计
		{
			sqlDate = " to_char(wb_date,'yyyy') = '" + year + "'";
		}
		String sqlEntpr = "";
		if (!entpr_id.equals(""))
			sqlEntpr = " car_user_id in (" + StringUtil.operStr(entpr_id) + ")";
		else {
			sqlEntpr = CommUtils.getCorpIds("car_user_id");
		}
		String sqlMedium = "";
		if (!medium_id.equals(""))
			sqlMedium = " and  car_no in (select distinct carno from EquCar where goodsid in ("
					+ StringUtil.operStr(medium_id) + "))";
		String sqlString = "select car_user_id,count(distinct car_no),count(car_no) from ExeTransport where zbc_flag=1 and cdy_code  in ('1721011','1721012','1721013','1721014','1721999') and "
				+ sqlDate + " and " + sqlEntpr + sqlMedium + "  group by car_user_id order by car_user_id";
		List<Object[]> list = getHibernateTemplate().find(sqlString);
		return list;
	}

	/**
	 * 查询未运行的车辆数，从自备车台账里面统计
	 * 
	 * @param stat_type
	 * @param year
	 * @param quarter
	 * @param month
	 * @param entpr_id
	 * @param medium_id
	 * @return
	 */
	public List<Object[]> getTransYYStatNoCycle(String stat_type, String year, String quarter, String month,
			String entpr_id, String medium_id) {
		String sqlDate = "";
		if (stat_type.equals("0"))// 按月统计
		{
			sqlDate = " to_char(wb_date,'yyyy-mm') = '" + year + "-" + month + "'";
		} else if (stat_type.equals("1"))// 按季统计
		{
			if (quarter.equals("1"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-01' and '" + year + "-03'";
			else if (quarter.equals("2"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-04' and '" + year + "-06'";
			else if (quarter.equals("3"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-07' and '" + year + "-09'";
			else if (quarter.equals("4"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-10' and '" + year + "-12'";
		} else if (stat_type.equals("2"))// 按年统计
		{
			sqlDate = " to_char(wb_date,'yyyy') = '" + year + "'";
		}
		String sqlEntpr = "";
		if (!entpr_id.equals(""))
			sqlEntpr = " car_user_id in (" + StringUtil.operStr(entpr_id) + ")  and car_user_id=e.corpid";
		else {
			sqlEntpr = CommUtils.getCorpIds("car_user_id") + "  and car_user_id=e.corpid";
		}
		String sqlMedium = "";
		if (!medium_id.equals(""))
			sqlMedium = " and  car_no in (select distinct carno from EquCar where goodsid in ("
					+ StringUtil.operStr(medium_id) + "))";
		String sqlString = "select corpid,count(carno) from EquCar e where carno not in (select car_no from ExeTransport where zbc_flag=1  and cdy_code in ('1721011','1721012','1721013','1721014','1721999')  and "
				+ sqlDate + " and " + sqlEntpr + sqlMedium + " ) group by corpid order by corpid";
		List<Object[]> list = getHibernateTemplate().find(sqlString);
		return list;
	}

	/**
	 * 查询未运行的车辆数，从自备车台账里面统计
	 * 
	 * @param stat_type
	 * @param year
	 * @param quarter
	 * @param month
	 * @param entpr_id企业ID
	 * @param medium_id
	 * @return
	 */
	public List<Object[]> getTransYYStatNoCycleDetail(String stat_type, String year, String quarter, String month,
			String entpr_id, String medium_id, String area_id) {
		String sqlDate = "";
		if (stat_type.equals("0"))// 按月统计
		{
			sqlDate = " to_char(wb_date,'yyyy-mm') = '" + year + "-" + month + "'";
		} else if (stat_type.equals("1"))// 按季统计
		{
			if (quarter.equals("1"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-01' and '" + year + "-03'";
			else if (quarter.equals("2"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-04' and '" + year + "-06'";
			else if (quarter.equals("3"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-07' and '" + year + "-09'";
			else if (quarter.equals("4"))
				sqlDate = " to_char(wb_date,'yyyy-mm') between '" + year + "-10' and '" + year + "-12'";
		} else if (stat_type.equals("2"))// 按年统计
		{
			sqlDate = " to_char(wb_date,'yyyy') = '" + year + "'";
		}
		String sqlCorp = "";
		if (entpr_id.equals("HJCorp"))
			sqlCorp = " and corpid in (select corpid from DicSinocorp where areaid='" + area_id + "')";
		else {
			sqlCorp = "  and corpid='" + entpr_id + "' ";
		}
		String sqlMedium = "";
		if (!medium_id.equals(""))
			sqlMedium = " and  car_no in (select distinct carno from EquCar where goodsid in ("
					+ StringUtil.operStr(medium_id) + "))";
		String sqlString = "select corpid,carno,goodsid,(select goodsname from DicGoodscategory g where g.goodsid=c.goodsid) from EquCar c where carno not in (select car_no from ExeTransport where zbc_flag=1  and cdy_code  in ('1721011','1721012','1721013','1721014','1721999')  and "
				+ sqlDate + " " + sqlMedium + "  and car_user_id=c.corpid) " + sqlCorp + " order by carno";
		List<Object[]> list = getHibernateTemplate().find(sqlString);
		return list;
	}
}
