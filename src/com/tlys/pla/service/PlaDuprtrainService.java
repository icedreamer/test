package com.tlys.pla.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.dao.PlaDuprtrainDao;
import com.tlys.pla.model.PlaDuprtrain;

@Service
public class PlaDuprtrainService extends _GenericService {
	@Autowired
	PlaDuprtrainDao PlaDuprtrainDao;

	public PlaDuprtrain load(String id) throws Exception {
		PlaDuprtrain plaDuprtrain = PlaDuprtrainDao.load(id);
		return plaDuprtrain;
	}

	public void save(PlaDuprtrain plaDuprtrain) throws Exception {
		PlaDuprtrainDao.save(plaDuprtrain);
	}

	public void update(PlaDuprtrain plaDuprtrain) throws Exception {
		PlaDuprtrainDao.updateEntity(plaDuprtrain, plaDuprtrain.getId());
	}

	/**
	 * @param PlaDuprtrain
	 */
	public List<PlaDuprtrain> find(PlaDuprtrain plaDuprtrain) throws Exception {
		List<PlaDuprtrain> list = PlaDuprtrainDao.find(plaDuprtrain);
		return list;
	}

	/**
	 * 通过批准要车分号查询所有数据
	 * 
	 * @param acceptcarno
	 */
	public List<PlaDuprtrain> find(String acceptcarno) throws Exception {
		List<PlaDuprtrain> list = PlaDuprtrainDao.find(acceptcarno);
		return list;
	}

	public List<PlaDuprtrain> findAll() throws Exception {
		List<PlaDuprtrain> list = PlaDuprtrainDao.findAll();
		return list;
	}

	public void delete(PlaDuprtrain plaDuprtrain) {
		if (null == plaDuprtrain) {
			return;
		}
		PlaDuprtrainDao.delete(plaDuprtrain);
	}

	private int getPlaDuprtrainCount(final PlaDuprtrain plaDuprtrain) {
		int count = PlaDuprtrainDao.getPlaDuprtrainCount(plaDuprtrain);
		return count;
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaDuprtrain plaDuprtrain, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaDuprtrainCount(plaDuprtrain);
		pageCtr.setTotalRecord(totalRecord);
		PlaDuprtrainDao.find(plaDuprtrain, pageCtr);
		for (Iterator iter = pageCtr.getRecords().iterator(); iter.hasNext();) {
			PlaDuprtrain pdu = (PlaDuprtrain) iter.next();
			String reqdate = pdu.getRequestdate();
			pdu.setRequestdateDIC(FormatUtil.rectDate(reqdate));
		}
	}

}
