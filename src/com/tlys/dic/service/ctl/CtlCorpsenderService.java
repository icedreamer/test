package com.tlys.dic.service.ctl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.dic.dao.ctl.CtlCorpsenderDao;
import com.tlys.dic.model.ctl.CtlCorpsender;

@Repository
public class CtlCorpsenderService extends _GenericService<CtlCorpsender> {

	@Autowired
	CtlCorpsenderDao ctlCorpsenderDao;

	@Autowired
	DicMap dicMap;

	public List<CtlCorpsender> findCtlCorpsender(String corpid) {
		return ctlCorpsenderDao.findCtlCorpsender(corpid);
	}

	public List<CtlCorpsender> findAll() {
		return ctlCorpsenderDao.findAll();
	}

	/**
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("ctlCorpsender");
	}

	public CtlCorpsender getCtlCorpsender(String senderid) {
		return ctlCorpsenderDao.getCtlCorpsender(senderid);
	}

	public void save(CtlCorpsender ctlCorpsender) {
		ctlCorpsenderDao.save(ctlCorpsender);
	}

	/**
	 * 获取发货人序列(自编)
	 * 
	 * @return
	 */
	public String getSenderNextReq() {
		String maxReq = ctlCorpsenderDao.getMaxReq();
		String numReq = "";
		if (null == maxReq || "".equals(maxReq)) {
			numReq = "ZB000001";
		} else {
			int maxNumReq = Integer.parseInt(maxReq.substring("ZB".length()));
			numReq = "ZB" + CommUtils.getSeqStr(String.valueOf(maxNumReq + 1), 6);
		}
		return numReq;
	}
}
