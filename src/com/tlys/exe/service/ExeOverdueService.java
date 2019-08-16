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
 * ��ʱԤ��Service
 * 
 * @author �״���
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
		List<ExeOverdue> listExeOverdue = exeOverdueDao.listOverdue(entpr_id2, medium_id, over_id, over_type);// ������ҵid����װ����id����ʱid����ʱ���Ͳ�ѯ����
		List<DicSinocorp> listDicSinocorp = exeDicDao.listDicSinocorpByIdArr(entpr_id); // ������ҵ�б�
		List<DicGoodscategory> listDicGoodsCatrgory = exeDicDao.listDicGoodscategoryByIdArr(medium_id);// �����װ�����б�
		List<DicOverTime> listDicOverTime = exeDicDao.listDicOverTimeByIdArr(over_id);// ���峬ʱid�б�
		for (DicSinocorp dicSinocorp : listDicSinocorp) {// ��˾ѭ��
			int[] intCount = new int[listDicGoodsCatrgory.size() * listDicOverTime.size()];
			int i = 0;
			for (DicGoodscategory dicGoodscategory : listDicGoodsCatrgory)// ��װ����ѭ��
			{
				for (DicOverTime dicOverTime : listDicOverTime) {// ��ʱidѭ��
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
	 * Ĭ�ϳ�ʱ�б�
	 * 
	 * @param entpr_id
	 * @param over_id
	 * @param over_type
	 * @return
	 */
	public List<int[]> listOverdue() {
		String over_type = "0";
		String over_id = "";
		String entpr_id = "";// 'zy000010','zy000020'��ʽ������exeDicDao.listDicSinocorpByIdArr��ѯ
		String entpr_id2 = "";// ///zy000010,zy000020��ʽ������exeOverdueDao.listOverdue��ѯ
		List<DicOverTime> listDicOvertime = exeDicDao.listDicOverTime("0");// Ĭ�ϴ���·�߲�ѯ
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
		List<ExeOverdue> listExeOverdue = exeOverdueDao.listOverdue(entpr_id2, over_id, over_type);// ������ҵid����װ����id����ʱid����ʱ���Ͳ�ѯ����
		List<DicSinocorp> listDicSinocorp = exeDicDao.listDicSinocorpByIdArr(entpr_id); // ������ҵ�б�
		List<DicOverTime> listDicOverTime = exeDicDao.listDicOverTimeByIdArr(over_id);// ���峬ʱid�б�
		for (DicSinocorp dicSinocorp : listDicSinocorp) {// ��˾ѭ��
			int[] intCount = new int[listDicOverTime.size()];
			int i = 0;
			for (DicOverTime dicOverTime : listDicOverTime) {// ��ʱidѭ��
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
	 * ȡ�ó�����̬��ϸ
	 * 
	 * @param medium_id��װ����id
	 * @param entpr_id��ҵid
	 * @param over_id��ʱ���
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
