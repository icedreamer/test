package com.tlys.exe.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.dao.ExeAreaDistDao;
import com.tlys.exe.dao.ExeDcarStatDao;
import com.tlys.exe.dao.ExeDicDao;
import com.tlys.exe.model.ExeAreaDist;
import com.tlys.exe.model.ExeAreaDistId;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.sys.model.SysUser;

/**
 * 自备车区域分布Service
 * 
 * @author 孔垂云
 * 
 */
@Service
public class ExeAreaDistService {

	@Autowired
	ExeDicDao exeDicDao;

	@Autowired
	ExeAreaDistDao exeAreaDistDao;

	@Autowired
	ExeDcarStatDao exeDcarStatDao;

	/**
	 * 取得区域自备车分布列表数据
	 * 
	 * @param stat_date
	 * @param stat_point
	 * @return
	 */
	public List<ExeAreaDistVO> listAreaDist(String stat_date, int stat_point, String le_code) {
		List<ExeAreaDistVO> list = new ArrayList<ExeAreaDistVO>();
		List<DicAreacorp> listDicAreacorp = exeDicDao.listDicAreacorp("dataAutho");
		List<DicSinocorp> listDicSinocorp = exeDicDao.listDicSinocorp("dataAutho");
		List<ExeAreaDist> listExeAeaDist = null;
		if (le_code.equals("3"))
			listExeAeaDist = this.statListExeAreaDist(stat_date, stat_point);
		else
			listExeAeaDist = exeAreaDistDao.listAreaDist(stat_date, (Integer) stat_point, le_code);
		ExeAreaDistVO allTotal = new ExeAreaDistVO();// 定义全部合计数
		allTotal.setArea("全部区域");
		allTotal.setCorp("合计");
		for (DicAreacorp areacorp : listDicAreacorp) {// 区域公司循环
			ExeAreaDistVO areaTotal = new ExeAreaDistVO();
			areaTotal.setArea(areacorp.getShrinkname());
			areaTotal.setCorp("合计");
			for (DicSinocorp sinocorp : listDicSinocorp) {// 子公司循环
				if (sinocorp.getAreaid().equals(areacorp.getAreaid())) {
					ExeAreaDistVO areaDistVO = new ExeAreaDistVO();// 定义区域合计数
					areaDistVO.setArea(areacorp.getShrinkname());
					areaDistVO.setCorp(sinocorp.getShortname());
					int total = 0;
					for (ExeAreaDist exeAreaDist : listExeAeaDist) {// 统计信息循环
						if (exeAreaDist.getId().getUser_area_id().equals(areacorp.getAreaid())
								&& exeAreaDist.getId().getCar_user_id().equals(sinocorp.getCorpid())) {
							if (exeAreaDist.getId().getCur_adm().equals("B")) {
								areaDistVO.setBcount(exeAreaDist.getZbc_car_num());
								areaTotal.setBcount(areaTotal.getBcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("T")) {
								areaDistVO.setTcount(exeAreaDist.getZbc_car_num());
								areaTotal.setTcount(areaTotal.getTcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("P")) {
								areaDistVO.setPcount(exeAreaDist.getZbc_car_num());
								areaTotal.setPcount(areaTotal.getPcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("V")) {
								areaDistVO.setVcount(exeAreaDist.getZbc_car_num());
								areaTotal.setVcount(areaTotal.getVcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("C")) {
								areaDistVO.setCcount(exeAreaDist.getZbc_car_num());
								areaTotal.setCcount(areaTotal.getCcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("F")) {
								areaDistVO.setFcount(exeAreaDist.getZbc_car_num());
								areaTotal.setFcount(areaTotal.getFcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("N")) {
								areaDistVO.setNcount(exeAreaDist.getZbc_car_num());
								areaTotal.setNcount(areaTotal.getNcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("Y")) {
								areaDistVO.setYcount(exeAreaDist.getZbc_car_num());
								areaTotal.setYcount(areaTotal.getYcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("K")) {
								areaDistVO.setKcount(exeAreaDist.getZbc_car_num());
								areaTotal.setKcount(areaTotal.getKcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("H")) {
								areaDistVO.setHcount(exeAreaDist.getZbc_car_num());
								areaTotal.setHcount(areaTotal.getHcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("G")) {
								areaDistVO.setGcount(exeAreaDist.getZbc_car_num());
								areaTotal.setGcount(areaTotal.getGcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("Q")) {
								areaDistVO.setQcount(exeAreaDist.getZbc_car_num());
								areaTotal.setQcount(areaTotal.getQcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("Z")) {
								areaDistVO.setZcount(exeAreaDist.getZbc_car_num());
								areaTotal.setZcount(areaTotal.getZcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("W")) {
								areaDistVO.setWcount(exeAreaDist.getZbc_car_num());
								areaTotal.setWcount(areaTotal.getWcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("M")) {
								areaDistVO.setMcount(exeAreaDist.getZbc_car_num());
								areaTotal.setMcount(areaTotal.getMcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("J")) {
								areaDistVO.setJcount(exeAreaDist.getZbc_car_num());
								areaTotal.setJcount(areaTotal.getJcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("R")) {
								areaDistVO.setRcount(exeAreaDist.getZbc_car_num());
								areaTotal.setRcount(areaTotal.getRcount() + exeAreaDist.getZbc_car_num());
							} else if (exeAreaDist.getId().getCur_adm().equals("O")) {
								areaDistVO.setOcount(exeAreaDist.getZbc_car_num());
								areaTotal.setOcount(areaTotal.getOcount() + exeAreaDist.getZbc_car_num());
							}
							total += exeAreaDist.getZbc_car_num();
							areaTotal.setTotal(areaTotal.getTotal() + exeAreaDist.getZbc_car_num());
						}
					}
					areaDistVO.setTotal(total);
					list.add(areaDistVO);
				}
			}
			list.add(areaTotal);
			allTotal.setTotal(allTotal.getTotal() + areaTotal.getTotal());// 计算全部公司合计
			allTotal.setBcount(allTotal.getBcount() + areaTotal.getBcount());
			allTotal.setTcount(allTotal.getTcount() + areaTotal.getTcount());
			allTotal.setPcount(allTotal.getPcount() + areaTotal.getPcount());
			allTotal.setVcount(allTotal.getVcount() + areaTotal.getVcount());
			allTotal.setCcount(allTotal.getCcount() + areaTotal.getCcount());
			allTotal.setFcount(allTotal.getFcount() + areaTotal.getFcount());
			allTotal.setNcount(allTotal.getNcount() + areaTotal.getNcount());
			allTotal.setYcount(allTotal.getYcount() + areaTotal.getYcount());
			allTotal.setKcount(allTotal.getKcount() + areaTotal.getKcount());
			allTotal.setHcount(allTotal.getHcount() + areaTotal.getHcount());
			allTotal.setGcount(allTotal.getGcount() + areaTotal.getGcount());
			allTotal.setQcount(allTotal.getQcount() + areaTotal.getQcount());
			allTotal.setZcount(allTotal.getZcount() + areaTotal.getZcount());
			allTotal.setWcount(allTotal.getWcount() + areaTotal.getWcount());
			allTotal.setMcount(allTotal.getMcount() + areaTotal.getMcount());
			allTotal.setJcount(allTotal.getJcount() + areaTotal.getJcount());
			allTotal.setRcount(allTotal.getRcount() + areaTotal.getRcount());
			allTotal.setOcount(allTotal.getOcount() + areaTotal.getOcount());

		}
		list.add(allTotal);
		return list;
	}

	/**
	 * 如果查询选择全部，从另一个group by方法获取列表
	 * 
	 * @param listExeAeaDist
	 * @return
	 */
	public List<ExeAreaDist> statListExeAreaDist(String stat_date, int stat_point) {
		List<Object[]> list = exeAreaDistDao.listAreaDist(stat_date, stat_point);
		List<ExeAreaDist> listExeAreaDist = new ArrayList<ExeAreaDist>();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			ExeAreaDist areaDist = new ExeAreaDist();
			ExeAreaDistId id = new ExeAreaDistId();
			id.setUser_area_id((String) obj[0]);
			id.setCar_user_id((String) obj[1]);
			id.setCur_adm((String) obj[2]);
			areaDist.setId(id);
			areaDist.setZbc_car_num(Integer.parseInt(obj[3].toString()));
			listExeAreaDist.add(areaDist);
		}
		return listExeAreaDist;
	}

	/**
	 * 取得区域查询详细
	 * 
	 * @param flag
	 * @param car_user
	 * @param le_code
	 * @param cur_adm
	 * @return
	 */
	public List<ExeDcarStat> getAreaDetail(String flag, String area, String car_user, String le_code, String cur_adm) {
		String car_user_id = "";// 根据flag查询car_user_id
		String area_id = "";// 区域ID
		if (flag.equals("A"))
			if (area.equals("全部区域"))
				area_id = "00000000";
			else
				area_id = exeDicDao.loadDicAreacorpByShrinkname(area).getAreaid();// 取得区域ID
		if (flag.equals("C")) {
			if (car_user.equals("合计"))
				car_user_id = "00000000";
			else
				car_user_id = exeDicDao.loadDicSinocorpByShortname(car_user).getCorpid();// 取得区域名称
		}
		List<ExeDcarStat> list = exeDcarStatDao.getAreaDistDetail(flag, area_id, car_user_id, le_code, cur_adm);
		return list;
	}

	/**
	 * 取得所有区域的公司数
	 * 
	 * @return
	 */
	public int[] getListRowspan() {
		List<DicAreacorp> listAreacorp = exeDicDao.listDicAreacorp("dataAutho");
		List<DicSinocorp> listSinocorp = exeDicDao.listDicSinocorp("dataAutho");
		int[] rowspan = new int[listAreacorp.size() + 1];// 区域公司+合计数
		int i = 0;
		for (DicAreacorp area : listAreacorp) {
			int k = 0;
			for (DicSinocorp corp : listSinocorp) {
				if (area.getAreaid().equals(corp.getAreaid())) {
					k++;
				}
			}
			rowspan[i] = k + 1;
			i++;
		}
		rowspan[listAreacorp.size()] = 1;
		return rowspan;
	}
	/**
	 * 获取重空等车在某个点的数据
	 * @param date
	 * @param point
	 * @return
	 */
	public List<Object[]> findCarsOfCode(String date,int point){
		return exeAreaDistDao.findCarsOfCode(date, point);
	}
	
	/**
	 * 获取重空车一周数据(生成曲线图使用)
	 * @param date
	 * @param point
	 * @return
	 */
	public List<Object[]> findWeekCarsOfLecode(String date ,int point){
		return exeAreaDistDao.findWeekCarsOfLecode(date, point);
	}
	
	/**
	 * 选择某个日期显示相应区域公司的车辆情况
	 * @param date
	 * @param point
	 * @return
	 */
	 public List<Object[]> findDayCarsOfAreas(String date, int point, SysUser sysUser) {
		return exeAreaDistDao.findDayCarsOfAreas(date, point, sysUser);
	}
}
