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
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
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
	 * ��ȡ����������(�Ա�)
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
