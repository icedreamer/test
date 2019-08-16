package com.tlys.spe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.spe.dao.SpeLoadMeastypeDao;
import com.tlys.spe.model.SpeLoadMeastype;

@Service
public class SpeLoadMeastypeService extends _GenericService<SpeLoadMeastype> {

	@Autowired
	SpeLoadMeastypeDao speLoadMeastypeDao;

	public SpeLoadMeastype getSpeLoadMeastype(String id) {
		return speLoadMeastypeDao.load(id);
	}

	public void save(SpeLoadMeastype speLoadMeastype) {
		if (null == speLoadMeastype.getMeastypeid()
				|| "".equals(speLoadMeastype.getMeastypeid())) {
			speLoadMeastype.setMeastypeid(CommUtils.getSeqStr(
					speLoadMeastypeDao.getSeq(), 2));
		}
		speLoadMeastypeDao.save(speLoadMeastype);
	}

	public void update(SpeLoadMeastype speLoadMeastype) {
		speLoadMeastypeDao.updateEntity(speLoadMeastype, speLoadMeastype
				.getMeastypeid());
	}

	public List<SpeLoadMeastype> findAll() {
		return speLoadMeastypeDao.findAll();
	}

	public void delete(SpeLoadMeastype speLoadMeastype) {
		if (null == speLoadMeastype) {
			return;
		}
		speLoadMeastypeDao.delete(speLoadMeastype);
	}

	public List<SpeLoadMeastype> find(SpeLoadMeastype speLoadMeastype) {
		return speLoadMeastypeDao.find(speLoadMeastype);
	}

}
