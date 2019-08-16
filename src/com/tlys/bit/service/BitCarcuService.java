package com.tlys.bit.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.bit.dao.BitCarcuDao;
import com.tlys.bit.model.BitCarcu;
import com.tlys.comm.util.CommUtils;
import com.tlys.sys.model.SysUser;

@Service
public class BitCarcuService {
	
	@Autowired
	BitCarcuDao bitCarcuDao;
	
	public BitCarcu getBitCarcu(SysUser sysUser) throws Exception{
		 		String datadate = CommUtils.shortDateFormat(new Date());
		 		String corptype = null;
		 		if(sysUser.getCorptab().equals("0")){//总部
		 			corptype = "G";
		 		}else if(sysUser.getCorptab().equals("1")){//区域公司
		 			corptype = "A";
		 		}else if(sysUser.getCorptab().equals("2")){//企业
		 			corptype = "C";
		 		}else{
		 			corptype = "C";
		 		}
				return bitCarcuDao.getBitCarcu(sysUser.getCorpid(), corptype, datadate);
		
	}

}
