package com.tlys.spe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.spe.dao.SpePackmarkPicnoDao;
import com.tlys.spe.model.SpePackmarkPicno;

/**
 * @author ·ëÑåÃ÷
 * 
 */

@Service
public class SpePackmarkPicnoService extends _GenericService {
	@Autowired
	SpePackmarkPicnoDao spePackmarkPicnoDao;
	

	public SpePackmarkPicno getSpePackmarkPicno(Long id) {
		return spePackmarkPicnoDao.load(id);
	}

	public void save(SpePackmarkPicno spePackmarkPicno) {
		spePackmarkPicnoDao.save(spePackmarkPicno);
	}


	public void update(SpePackmarkPicno spePackmarkPicno) {
		spePackmarkPicnoDao.updateEntity(spePackmarkPicno, spePackmarkPicno.getId());
	}



	/**
	 * @param spePackmarkPicno
	 * @param pageCtr
	 */
	public List findSpePackmarkPicno(SpePackmarkPicno spePackmarkPicno) throws Exception{
		return spePackmarkPicnoDao.findSpePackmarkPicno(spePackmarkPicno);
	}

	public List<SpePackmarkPicno> findAll() {
		return spePackmarkPicnoDao.findAll();
	}

	public void delete(SpePackmarkPicno spePackmarkPicno) throws Exception{
		if (null == spePackmarkPicno) {
			return;
		}
		// É¾³ý¼ÇÂ¼
		spePackmarkPicnoDao.delete(spePackmarkPicno);
	}

	
}
