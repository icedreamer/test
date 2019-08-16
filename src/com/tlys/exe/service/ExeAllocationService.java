package com.tlys.exe.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.page.PageView;
import com.tlys.exe.dao.ExeAllocSearchField;
import com.tlys.exe.dao.ExeAllocationDao;
import com.tlys.exe.dao.ExeAllocationStatVO;
import com.tlys.exe.model.ExeAllocation;
import com.tlys.exe.model.ExeAllocationId;

/**
 * 调配信息Service
 * 
 * @author 孔垂云
 * 
 */
@Service
public class ExeAllocationService {

	@Autowired
	ExeAllocationDao exeAllocationDao;

	/**
	 * 分新增和修改调用不同的dao方法
	 * 
	 * @param sysUser
	 */
	public void save(ExeAllocation allocInfo, boolean isNew) {
		if (true == isNew) {
			exeAllocationDao.saveOrUpdate(allocInfo);
		} else {
			exeAllocationDao.updateEntity(allocInfo);
		}
	}

	public void saveExeAllocation(ExeAllocation ea) {
		exeAllocationDao.saveExeAllocation(ea);
	}

	public void delete(String CarNo, String ContractNo) {
		exeAllocationDao.delete(CarNo, ContractNo);
	}

	public void delete(ExeAllocationId eai) {
		exeAllocationDao.delete(eai);
	}

	public void updateStatus(String status, Date modiDate, ExeAllocationId ei) {
		exeAllocationDao.updateStatus(status, modiDate, ei);
	}

	public ExeAllocationDao getAllocInfoDao() {
		return exeAllocationDao;
	}

	public void setAllocInfoDao(ExeAllocationDao exeAllocationDao) {
		this.exeAllocationDao = exeAllocationDao;
	}

	public List<ExeAllocation> findAll() {
		return exeAllocationDao.findExeAllocations();
	}

	public ExeAllocation findExeAllocation(ExeAllocationId eai) {
		return exeAllocationDao.findExeAllocation(eai);

	}

	public ExeAllocation findExeAllocation(String carNo, String contractNo) {
		return exeAllocationDao.findExeAllocation(carNo, contractNo);
	}

	public PageView<ExeAllocation> findExeAllocations(ExeAllocSearchField esf, final String pageUrl,
			final int totalRecord, final int currentPage, final int pageSize) {
		return exeAllocationDao.findExeAllocations(esf, pageUrl, totalRecord, currentPage, pageSize);
	}

	public int getExeAllocationCount(ExeAllocSearchField esf) {
		return exeAllocationDao.getExeAllocationCount(esf);
	}

	/**
	 * 调配信息统计
	 * 
	 * @return
	 */
	public List<ExeAllocationStatVO> statisticsAllocation() {
		return exeAllocationDao.statisticsAllocation();
	}

	/**
	 * 调配信息统计界面查询详细
	 * 
	 * @param lessor_id
	 * @param lessee_id
	 * @param medium_id
	 * @return
	 */
	public List<ExeAllocation> listAllocationsStatDetail(String lessor_id, String lessee_id, String medium_id) {
		return exeAllocationDao.listAllocationsStatDetail(lessor_id, lessee_id, medium_id);
	}

	/**
	 * 取得该租赁方为该用户的所有合同号
	 * 
	 * @return
	 */
	public List<String> getAllContract_no(String corpId) {
		return exeAllocationDao.getAllContract_no(corpId);
	}

	/**
	 * 根据合同号取得这个合同号的所有信息
	 * 
	 * @param contract_no
	 * @return
	 */
	public List<ExeAllocation> getAllocationByContract_no(String contract_no,String car_no) {
		return exeAllocationDao.getAllocationByContract_no(contract_no,car_no);
	}

	/**
	 * 根据合同号取得这个合同号的所有信息
	 * 
	 * @param contract_no
	 * @return
	 */
	public List<ExeAllocation> getAllocationByContract_no(String contract_no) {
		return exeAllocationDao.getAllocationByContract_no(contract_no);
	}
}
