/**
 * 
 */
package com.tlys.dic.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.ExcelUtilNew;
import com.tlys.dic.dao.DicSalecorpDao;
import com.tlys.dic.dao.DicSinocorpDao;
import com.tlys.dic.dao.DicSinodepartmentDao;
import com.tlys.dic.model.DicSalecorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;

/**
 * @author fengym
 * 
 */
@Service
public class DicSalecorpService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	DicSalecorpDao dicSalecorpDao;

	@Autowired
	DicSinocorpDao dicSinocorpDao;
	
	
	@Autowired
	DicSinodepartmentDao dicSinodepartmentDao;
	

	public List<DicSalecorp> findAll() {
		return dicSalecorpDao.findAll();
	}

	public List<DicSalecorp> find(DicSalecorp dac) {
		return dicSalecorpDao.find(dac);
	}

	public DicSalecorp load(String id) {
		return dicSalecorpDao.load(id);
	}

	/**
	 * ����sinocorp��ʹѡ�еĵ�λ��Ϊsalecorp
	 * 
	 * @param 
	 */
	public void updateSalecorpsFromSinocorp(String srcorg) {
		List srcorgs = dicSinocorpDao.findAll(" where corpid in(" + CommUtils.addQuotes(srcorg)+ ")");
		for (Iterator iter = srcorgs.iterator(); iter.hasNext();) {
			DicSinocorp sinocorp = (DicSinocorp) iter.next();
			sinocorp.setIssalecorp("1");
		}

		dicSinocorpDao.saveOrUpdateAll(srcorgs);

	}
	
	/**
	 * ����Sinodepartment��ʹѡ�еĵ�λ��Ϊsalecorp
	 * 
	 * @param 
	 */
	public void updateSalecorpsFromSinodepartment(String srcorg) {
		List srcorgs = dicSinodepartmentDao.findAll(" where sinodepaid in(" + CommUtils.addQuotes(srcorg)+ ")");
		for (Iterator iter = srcorgs.iterator(); iter.hasNext();) {
			DicSinodepartment dicSinodepartment = (DicSinodepartment) iter.next();
			dicSinodepartment.setIssalecorp("1");
		}

		dicSinodepartmentDao.saveOrUpdateAll(srcorgs);

	}
	
	/**
	 * ɾ��������ҵ��ʵ�ʲ����Ǹ�����Ӧ���е�isSalecorp�ֶ�
	 * @param id
	 */
	public void delete(String id) {
		DicSalecorp dicSalecorp = dicSalecorpDao.load(id);
		String src = dicSalecorp.getSourcetab();
		String srcid = dicSalecorp.getId();
		if("COR".equals(src)){
			DicSinocorp dicSinocorp=dicSinocorpDao.load(srcid);
			dicSinocorp.setIssalecorp("0");
			dicSinocorpDao.saveOrUpdate(dicSinocorp);
		}else if("DEP".equals(src)){
			DicSinodepartment dicSinodepartment = dicSinodepartmentDao.load(srcid);
			dicSinodepartment.setIssalecorp("0");
			dicSinodepartmentDao.saveOrUpdate(dicSinodepartment);
		}
	}
	
	
	/**
	 * ���EXCEL��
	 * @param dicSalecorps
	 * @param response
	 * @throws Exception
	 */
	public void expExcel(List<DicSalecorp> dicSalecorps,
			HttpServletResponse response) throws Exception{
		Map tabDefineMap = new HashMap();
		
		//����һ��sheet�������ݴ���sheetMap��
		Map sheetMap = new HashMap();
		tabDefineMap.put("���ۻ���", sheetMap);
		
		//��sheetMap�м���list������Ҫ�������Ҫ�����б�
		sheetMap.put("list", dicSalecorps);
		//����
		sheetMap.put("title", "���ۻ����б�");
		
		//��ͷ���飬��һ��ΪҪ������������ԣ�����ȡ����)���ڶ���Ϊ�������Ե���ʾ���ƣ����ڱ�ͷ)
		//������������е�����Ӧ�����ϸ�һ�µ�
		String[][] headArr = new String[2][];
		headArr[0] = new String[]{
				"id","shortname","address",	"dutyperson","telephones",
				"fax","description"
				};
		headArr[1] = new String[]{
				"ID","���","��ַ","������","�绰",
				"����","����"};
		sheetMap.put("headArr", headArr);
		
		//Workbook workbook = ExcelUtilNew.genTable(tabDefineMap);
		
		ExcelUtilNew.writeWorkbook(response, tabDefineMap, "���ۻ���");
		
	}
}
