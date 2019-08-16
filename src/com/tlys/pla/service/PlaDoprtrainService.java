package com.tlys.pla.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.dao.PlaDoprtrainDao;
import com.tlys.pla.model.PlaDoprtrain;

@Service
public class PlaDoprtrainService extends _GenericService {
	@Autowired
	PlaDoprtrainDao plaDoprtrainDao;

	public PlaDoprtrain load(String id) throws Exception {
		PlaDoprtrain plaDoprtrain = plaDoprtrainDao.load(id);
		return plaDoprtrain;
	}

	public void save(PlaDoprtrain plaDoprtrain) throws Exception {
		plaDoprtrainDao.save(plaDoprtrain);
	}

	public void update(PlaDoprtrain plaDoprtrain) throws Exception {
		plaDoprtrainDao.updateEntity(plaDoprtrain, plaDoprtrain.getId());
	}

	public List<PlaDoprtrain> findAll() throws Exception {
		List<PlaDoprtrain> list = plaDoprtrainDao.findAll();
		return list;
	}

	public void delete(PlaDoprtrain plaDoprtrain) {
		if (null == plaDoprtrain) {
			return;
		}
		plaDoprtrainDao.delete(plaDoprtrain);
	}

	private int getPlaDoprtrainCount(final PlaDoprtrain plaDoprtrain) {
		int count = plaDoprtrainDao.getPlaDoprtrainCount(plaDoprtrain);
		return count;
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaDoprtrain plaDoprtrain, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaDoprtrainCount(plaDoprtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaDoprtrainDao.find(plaDoprtrain, pageCtr);
		for (Iterator iter = pageCtr.getRecords().iterator(); iter.hasNext();) {
			PlaDoprtrain pds = (PlaDoprtrain) iter.next();
			String reqdate = pds.getRequestdate();
			pds.setRequestdateDIC(FormatUtil.rectDate(reqdate));
		}
	}
	
	public List<PlaDoprtrain> find(PlaDoprtrain plaDoprtrain) throws Exception {
		List<PlaDoprtrain> list = plaDoprtrainDao.find(plaDoprtrain);
		return list;
	}

}
