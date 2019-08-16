package com.tlys.test;

import org.hibernate.annotations.Check;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tlys.bit.model.BitCarcu;
import com.tlys.bit.service.BitCarcuService;
import com.tlys.sys.model.SysUser;

public class BitCarcuTest extends BaseTest{
	@Autowired
	BitCarcuService bitCarcuService;
	
	
	
	@Test()
	@Check(constraints = "testJsonTree")
	public void getBitCarcu(){
		
		
		try {
			SysUser sysUser = new SysUser();
			sysUser.setCorpid("35000032");
			sysUser.setCorptab("1");
			BitCarcu bitCarcu =	bitCarcuService.getBitCarcu(sysUser);
			System.out.print("-----------------");
			System.out.print(String.valueOf(bitCarcu.getDcu()));
			System.out.print("================");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
