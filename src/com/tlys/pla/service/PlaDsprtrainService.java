package com.tlys.pla.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.FormatUtil;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.pla.dao.PlaDsprtrainDao;
import com.tlys.pla.model.PlaDsprtrain;

@Service
public class PlaDsprtrainService extends _GenericService {
	@Autowired
	PlaDsprtrainDao plaDsprtrainDao;

	public PlaDsprtrain load(String id) throws Exception {
		PlaDsprtrain plaDsprtrain = plaDsprtrainDao.load(id);
		return plaDsprtrain;
	}

	public void save(PlaDsprtrain plaDsprtrain) throws Exception {
		plaDsprtrainDao.save(plaDsprtrain);
	}

	public void update(PlaDsprtrain plaDsprtrain) throws Exception {
		plaDsprtrainDao.updateEntity(plaDsprtrain, plaDsprtrain.getId());
	}

	public List<PlaDsprtrain> findAll() throws Exception {
		List<PlaDsprtrain> list = plaDsprtrainDao.findAll();
		return list;
	}

	public void delete(PlaDsprtrain plaDsprtrain) {
		if (null == plaDsprtrain) {
			return;
		}
		plaDsprtrainDao.delete(plaDsprtrain);
	}

	private int getPlaDsprtrainCount(final PlaDsprtrain plaDsprtrain) {
		int count = plaDsprtrainDao.getPlaDsprtrainCount(plaDsprtrain);
		return count;
	}

	/**
	 * 分页查找，返回的list直接注入到pageCtr对象中
	 * 
	 * @param speCertishipping
	 * @param pageCtr
	 */
	public void find(PlaDsprtrain plaDsprtrain, PageCtr pageCtr)
			throws Exception {
		int totalRecord = getPlaDsprtrainCount(plaDsprtrain);
		pageCtr.setTotalRecord(totalRecord);
		plaDsprtrainDao.find(plaDsprtrain, pageCtr);
		for (Iterator iter = pageCtr.getRecords().iterator(); iter.hasNext();) {
			PlaDsprtrain pds = (PlaDsprtrain) iter.next();
			String reqdate = pds.getRequestdate();
			pds.setRequestdateDIC(FormatUtil.rectDate(reqdate));
		}
	}
	
	public List<PlaDsprtrain> find(PlaDsprtrain plaDsprtrain) throws Exception {
		List<PlaDsprtrain> list = plaDsprtrainDao.find(plaDsprtrain);
		return list;
	}

}
