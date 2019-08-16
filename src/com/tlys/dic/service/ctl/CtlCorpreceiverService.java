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
 * 收貨人 服務
 * 
 * @author ccsong 2012-2-28 下午5:36:01
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
	 * 由于当前对象充当字典使用 所以当前对象变动（保存，更新，删除)时 要在内存中设置一个标志，当别的进程利用dicMap去取此字典时，
	 * 利用此标志来决定是否重新从数据库中取数据
	 */
	private void setAlterFlag() {
		dicMap.dicAlter("ctlCorpreceiver");
	}

	public void save(CtlCorpreceiver ctlCorpreceiver) {
		ctlCorpreceiverDao.save(ctlCorpreceiver);
	}

	/**
	 * 获取收货人序列
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
