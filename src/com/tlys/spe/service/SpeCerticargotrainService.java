package com.tlys.spe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.spe.dao.SpeCerticargotrainDao;
import com.tlys.spe.model.SpeCerticargotrain;

@Service
public class SpeCerticargotrainService extends _GenericService {
	@Autowired
	SpeCerticargotrainDao speCerticargotrainDao;
	

	public SpeCerticargotrain getSpeCerticargotrain(Long id) throws Exception{
		SpeCerticargotrain speCerticargotrain = speCerticargotrainDao.load(id);
		rectDate(speCerticargotrain);
		return speCerticargotrain;
	}

	public void save(SpeCerticargotrain speCerticargotrain) throws Exception{
		rectDate(speCerticargotrain);
		speCerticargotrainDao.save(speCerticargotrain);
	}

	public void update(SpeCerticargotrain speCerticargotrain) throws Exception{
		rectDate(speCerticargotrain);
		speCerticargotrainDao.updateEntity(speCerticargotrain, speCerticargotrain.getId());
	}



	/**
	 * @param speCerticargotrain
	 */
	public List<SpeCerticargotrain> find(SpeCerticargotrain speCerticargotrain) throws Exception{
		List<SpeCerticargotrain> trainList = speCerticargotrainDao.find(speCerticargotrain);
		for (SpeCerticargotrain train : trainList) {
			rectDate(train);
		}
		return trainList;
	}

	public List<SpeCerticargotrain> findAll() {
		return speCerticargotrainDao.findAll();
	}
	

	public void delete(SpeCerticargotrain speCerticargotrain) {
		if (null == speCerticargotrain) {
			return;
		}
		speCerticargotrainDao.delete(speCerticargotrain);
	}
	
	/**
	 * 进行字符串型日期转换
	 * 如果是yyyy-mm-dd格式，转为yyyymmdd;反之，则转为反之。
	 * @param sct
	 * @throws Exception
	 */
	private void rectDate(SpeCerticargotrain sct) throws Exception{
		if(null==sct) return;
		String startdate = sct.getStartdate();
		String enddate = sct.getEnddate();
		String startdateStr=sct.getStartdateStr();
		String enddateStr=sct.getEnddateStr();
		if(null != startdate){
			startdateStr = sct.normalFormat.format(sct.shortFormat.parse(startdate));
			sct.setStartdateStr(startdateStr);
		}
		if(null != enddate){
				//enddateNew = sct.shortFormat.format(sct.normalFormat.parse(enddate));
			enddateStr = sct.normalFormat.format(sct.shortFormat.parse(enddate));
			sct.setEnddateStr(enddateStr);
		}
		
		if(null != startdateStr){
			startdate = sct.shortFormat.format(sct.normalFormat.parse(startdateStr));
			sct.setStartdate(startdate);
		}
		if(null != enddateStr){
			enddate = sct.shortFormat.format(sct.normalFormat.parse(enddateStr));
			sct.setEnddate(enddate);
		}
		
	}
}
