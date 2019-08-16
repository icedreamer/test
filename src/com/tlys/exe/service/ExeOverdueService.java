package com.tlys.exe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.dic.model.DicGoodscategory;
import com.tlys.dic.model.DicOverTime;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.exe.common.util.StringUtil;
import com.tlys.exe.dao.ExeDcarStatDao;
import com.tlys.exe.dao.ExeDicDao;
import com.tlys.exe.dao.ExeOverdueDao;
import com.tlys.exe.model.ExeDcarStat;
import com.tlys.exe.model.ExeOverdue;

/**
 * 超时预警Service
 * 
 * @author 孔垂云
 * 
 */
@Service
public class ExeOverdueService {

	@Autowired
	private ExeOverdueDao exeOverdueDao;
	@Autowired
	private ExeDcarStatDao exeDcarStatDao;
	@Autowired
	private ExeDicDao exeDicDao;//

	public List<int[]> listOverdue(String entpr_id, String entpr_id2, String medium_id, String over_id, String over_type) {
		List<int[]> listRet = new ArrayList<int[]>();
		List<ExeOverdue> listExeOverdue = exeOverdueDao.listOverdue(entpr_id2, medium_id, over_id, over_type);// 根据企业id、充装介质id、超时id，超时类型查询数据
		List<DicSinocorp> listDicSinocorp = exeDicDao.listDicSinocorpByIdArr(entpr_id); // 定义企业列表
		List<DicGoodscategory> listDicGoodsCatrgory = exeDicDao.listDicGoodscategoryByIdArr(medium_id);// 定义充装介质列表
		List<DicOverTime> listDicOverTime = exeDicDao.listDicOverTimeByIdArr(over_id);// 定义超时id列表
		for (DicSinocorp dicSinocorp : listDicSinocorp) {// 公司循环
			int[] intCount = new int[listDicGoodsCatrgory.size() * listDicOverTime.size()];
			int i = 0;
			for (DicGoodscategory dicGoodscategory : listDicGoodsCatrgory)// 充装介质循环
			{
				for (DicOverTime dicOverTime : listDicOverTime) {// 超时id循环
					boolean flag = false;
					for (ExeOverdue exeOverdue : listExeOverdue) {
						if (exeOverdue.getId().getEntpr_id().equals(dicSinocorp.getCorpid())
								&& exeOverdue.getId().getMedium_id().equals(dicGoodscategory.getGoodsid())
								&& exeOverdue.getId().getOver_id().equals(dicOverTime.getOver_id())) {
							intCount[i] = exeOverdue.getOver_car_num();
							flag = true;
							i++;
							break;
						}
					}
					if (flag == false) {
						intCount[i] = 0;
						i++;
					}
				}
			}
			listRet.add(intCount);
		}
		return listRet;
	}

	/**
	 * 默认超时列表
	 * 
	 * @param entpr_id
	 * @param over_id
	 * @param over_type
	 * @return
	 */
	public List<int[]> listOverdue() {
		String over_type = "0";
		String over_id = "";
		String entpr_id = "";// 'zy000010','zy000020'格式，用于exeDicDao.listDicSinocorpByIdArr查询
		String entpr_id2 = "";// ///zy000010,zy000020格式，用于exeOverdueDao.listOverdue查询
		List<DicOverTime> listDicOvertime = exeDicDao.listDicOverTime("0");// 默认从铁路线查询
		List<DicSinocorp> listDicSinocorp2 = exeDicDao.listDicSinocorp("dataAutho");
		for (DicOverTime overTime : listDicOvertime) {
			over_id += overTime.getOver_id() + ",";
		}
		if (over_id.length() > 0)
			over_id = over_id.substring(0, over_id.length() - 1);

		for (DicSinocorp sinocorp : listDicSinocorp2) {
			entpr_id += "'" + sinocorp.getCorpid() + "',";
			entpr_id2 += sinocorp.getCorpid() + ",";
		}
		if (entpr_id.length() > 0) {
			entpr_id = entpr_id.substring(0, entpr_id.length() - 1);
			entpr_id2 = entpr_id2.substring(0, entpr_id2.length() - 1);
		}
		List<int[]> listRet = new ArrayList<int[]>();
		List<ExeOverdue> listExeOverdue = exeOverdueDao.listOverdue(entpr_id2, over_id, over_type);// 根据企业id、充装介质id、超时id，超时类型查询数据
		List<DicSinocorp> listDicSinocorp = exeDicDao.listDicSinocorpByIdArr(entpr_id); // 定义企业列表
		List<DicOverTime> listDicOverTime = exeDicDao.listDicOverTimeByIdArr(over_id);// 定义超时id列表
		for (DicSinocorp dicSinocorp : listDicSinocorp) {// 公司循环
			int[] intCount = new int[listDicOverTime.size()];
			int i = 0;
			for (DicOverTime dicOverTime : listDicOverTime) {// 超时id循环
				for (ExeOverdue exeOverdue : listExeOverdue) {
					if (exeOverdue.getId().getEntpr_id().equals(dicSinocorp.getCorpid())
							&& exeOverdue.getId().getOver_id().equals(dicOverTime.getOver_id())) {
						intCount[i] += exeOverdue.getOver_car_num();
					}
				}
				i++;
			}
			listRet.add(intCount);
		}
		return listRet;
	}

	/**
	 * 取得车辆动态详细
	 * 
	 * @param medium_id充装介质id
	 * @param entpr_id企业id
	 * @param over_id超时编号
	 * @return
	 */
	public List<ExeDcarStat> getOverdueDetail(String medium_id, String entpr_id, String over_id) {
		DicOverTime dicOverTime = exeDicDao.loadDicOverTime(over_id);
		String s_date = "";
		if (dicOverTime.getMax_days() / 24 > 1000)
			s_date = "";
		else
			s_date = StringUtil.getOptSystemDate(new Date(), 0 - dicOverTime.getMax_days() / 24);
		String e_date = StringUtil.getOptSystemDate(new Date(), 0 - (dicOverTime.getMin_days() == 0 ? 0 : dicOverTime
				.getMin_days() / 24 + 1));
		return exeDcarStatDao.getOverdueDetail(medium_id, entpr_id, dicOverTime.getMin_days(), dicOverTime
				.getMax_days());
	}
}
