package com.tlys.dic.service.ctl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.dic.dao.ctl.CtlCorpreceiverDao;
import com.tlys.dic.model.ctl.CtlCorpreceiver;
import com.tlys.pla.dao.PlaImpDao;

/**
 * ��؛�� ����
 * 
 * @author ccsong 2012-2-28 ����5:36:01
 */
@Service
public class CtlCorpreceiverService extends _GenericService {

	@Autowired
	CtlCorpreceiverDao ctlCorpreceiverDao;

	@Autowired
	DicMap dicMap;

	@Autowired
	PlaImpDao plaImpDao;

	public CtlCorpreceiver getCtlCorpreceiver(String receiverid) {
		return ctlCorpreceiverDao.getCtlCorpreceiver(receiverid);
	}

	public List<CtlCorpreceiver> findAll() {
		return ctlCorpreceiverDao.findAll();
	}

	/**
	 * ���ڵ�ǰ����䵱�ֵ�ʹ�� ���Ե�ǰ����䶯�����棬���£�ɾ��)ʱ Ҫ���ڴ�������һ����־������Ľ�������dicMapȥȡ���ֵ�ʱ��
	 * ���ô˱�־�������Ƿ����´����ݿ���ȡ����
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("ctlCorpreceiver");
	}

	public void save(CtlCorpreceiver ctlCorpreceiver) {
		ctlCorpreceiverDao.save(ctlCorpreceiver);
	}

	/**
	 * ��ȡ�ջ�������
	 * 
	 * @return
	 */
	public String getReceiverNextReq() {
		String maxReq = ctlCorpreceiverDao.getMaxReq();
		String numReq = "";
		if (null == maxReq || "".equals(maxReq)) {
			numReq = "ZB000001";
		} else {
			int maxNumReq = Integer.parseInt(maxReq.substring("ZB".length()));
			numReq = "ZB" + CommUtils.getSeqStr(String.valueOf(maxNumReq + 1), 6);
		}
		return numReq;
	}

	public List<CtlCorpreceiver> findCtlCorpreceiverByCorpid(String corpid) {
		return ctlCorpreceiverDao.findCtlCorpreceiverByCorpid(corpid);
	}

	public Object[] getCtlCorpReceiver(String[] datas) {
		return plaImpDao.initPlanDicDataReceiver(datas);
	}
}
