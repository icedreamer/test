package com.tlys.spe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.FormatUtil;
import com.tlys.spe.dao.SpeCertishiptrainDao;
import com.tlys.spe.model.SpeCertishiptrain;

@Service
public class SpeCertishiptrainService extends _GenericService {
	@Autowired
	SpeCertishiptrainDao speCertishiptrainDao;
	

	public SpeCertishiptrain getSpeCertishiptrain(Long id) throws Exception{
		SpeCertishiptrain speCertishiptrain = speCertishiptrainDao.load(id);
		rectDate(speCertishiptrain);
		return speCertishiptrain;
	}

	public void save(SpeCertishiptrain speCertishiptrain) throws Exception{
		rectDate(speCertishiptrain);
		//System.out.println("SpeCertishiptrainService.save->speCertishiptrain.getEnddate()=="+speCertishiptrain.getEnddate());
		speCertishiptrainDao.save(speCertishiptrain);
	}

	public void update(SpeCertishiptrain speCertishiptrain) throws Exception{
		rectDate(speCertishiptrain);
		speCertishiptrainDao.updateEntity(speCertishiptrain, speCertishiptrain.getId());
	}



	/**
	 * @param speCertishiptrain
	 */
	public List<SpeCertishiptrain> find(SpeCertishiptrain speCertishiptrain) throws Exception{
		List<SpeCertishiptrain> trainList = speCertishiptrainDao.find(speCertishiptrain);
		for (SpeCertishiptrain train : trainList) {
			rectDate(train);
			if (log.isDebugEnabled()) {
				log.debug("SpeCertishiptrainService.find->train.getEnddate()=="+train.getEnddate());
			}
		}
		
		return trainList;
	}

	public List<SpeCertishiptrain> findAll() {
		return speCertishiptrainDao.findAll();
	}
	

	public void delete(SpeCertishiptrain speCertishiptrain) {
		if (null == speCertishiptrain) {
			return;
		}
		speCertishiptrainDao.delete(speCertishiptrain);
	}
	
	/**
	 * 进行字符串型日期转换
	 * 如果是yyyy-mm-dd格式，转为yyyymmdd;反之，则转为反之。
	 * @param sct
	 * @throws Exception
	 */
	private void rectDate(SpeCertishiptrain sct) throws Exception{
		if(null==sct) return;
		String startdate = sct.getStartdate();
		String enddate = sct.getEnddate();
		String startdateStr=sct.getStartdateStr();
		String enddateStr=sct.getEnddateStr();
		
		if(null != startdate){
			startdateStr = FormatUtil.rectDate(startdate);
			sct.setStartdateStr(startdateStr);
		}
		if(null != enddate){
				//enddateNew = sct.shortFormat.format(sct.normalFormat.parse(enddate));
			enddateStr = FormatUtil.rectDate(enddate);
			sct.setEnddateStr(enddateStr);
		}
		
		if(null != startdateStr){
			startdate = FormatUtil.rectDate(startdateStr);
			sct.setStartdate(startdate);
		}
		if(null != enddateStr){
			enddate = FormatUtil.rectDate(enddateStr);
			sct.setEnddate(enddate);
		}
		
	}
}
