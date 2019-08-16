package com.tlys.spe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas.CacheService;
import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.spe.dao.SpePackmarkDao;
import com.tlys.spe.model.SpePackmark;

/**
 * @author ������
 * 
 */

@Service
public class SpePackmarkService extends _GenericService {
	@Autowired
	SpePackmarkDao spePackmarkDao;
	
	@Autowired
	SpePackmarkPicService spePackmarkPicService;
	
	@Autowired
	CacheService cacheService;
	
	@Autowired
	DicMap dicMap;

	public SpePackmark getSpePackmark(String id) {
		return spePackmarkDao.load(id);
	}

	public void save(SpePackmark spePackmark) {
		if(null==spePackmark.getMarkid()|| "".equals(spePackmark.getMarkid())){
			spePackmark.setMarkid(CommUtils.getSeqStr(spePackmarkDao.getSeq(), 4));
		}
		spePackmarkDao.save(spePackmark);
		setAlterFlag();
	}


	public void update(SpePackmark spePackmark) {
		spePackmarkDao.updateEntity(spePackmark, spePackmark.getMarkid());
		setAlterFlag();
	}
	
	
	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ��
	 * ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ
	 * Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag(){
		dicMap.dicAlter("spePackmark");
	}
	
	


	public List<SpePackmark> find(SpePackmark spePackmark) throws Exception{
		return spePackmarkDao.find(spePackmark);
	}
	
	public List<SpePackmark> findAll() {
		return spePackmarkDao.findAll();
	}

	
	

	public void delete(SpePackmark spePackmark) throws Exception{
		if (null == spePackmark) {
			return;
		}
		spePackmarkPicService.deleteByMarkid(spePackmark.getMarkid());
		spePackmarkDao.delete(spePackmark);
		setAlterFlag();
	}
	
}
