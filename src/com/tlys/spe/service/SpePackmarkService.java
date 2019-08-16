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
 * @author 冯彦明
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
	 * 由于当前对象充当字典使用
	 * 所以当前对象变动（保存，更新，删除)时
	 * 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
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
