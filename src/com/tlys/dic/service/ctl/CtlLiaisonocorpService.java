/**
 * 
 */
package com.tlys.dic.service.ctl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.dic.dao.ctl.CtlLiaisonocorpDao;
import com.tlys.dic.model.ctl.CtlLiaisonocorp;

/**
 * @author guojj
 * 
 */
@Service
public class CtlLiaisonocorpService {
	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	CtlLiaisonocorpDao ctlLiaisonocorpDao;

	public List<CtlLiaisonocorp> findAll() {
		return ctlLiaisonocorpDao.findAll();
	}

	public List<CtlLiaisonocorp> find(CtlLiaisonocorp dac) {
		return ctlLiaisonocorpDao.find(dac);
	}

	public CtlLiaisonocorp load(String id) {
		return ctlLiaisonocorpDao.load(id);
	}

	/**
	 * ���淽�����������͸���ʱ�ֱ����Dao�в�ͬ�ķ���������
	 * 
	 * @param CtlLiaisonocorp
	 *            ,isNew:��־��ǰ���������������޸Ĳ���
	 */
	public void save(CtlLiaisonocorp CtlLiaisonocorp, boolean isNew) {
		if (isNew) {
			ctlLiaisonocorpDao.saveOrUpdate(CtlLiaisonocorp);
		} else {
			ctlLiaisonocorpDao.updateEntity(CtlLiaisonocorp, CtlLiaisonocorp.getId());
		}
	}

	public void delete(String id) {
		ctlLiaisonocorpDao.delete(id);

	}

	public List<String> findCorpidsByBranchid(String branchid) {
		return ctlLiaisonocorpDao.findCorpidsByBranchid(branchid);
	}
}
