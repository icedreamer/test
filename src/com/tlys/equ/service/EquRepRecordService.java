package com.tlys.equ.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas.CacheService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.dao.DicReparetypeDao;
import com.tlys.dic.dao.DicRwstockdepotDao;
import com.tlys.dic.dao.DicSinocorpDao;
import com.tlys.dic.dao.DicTankrepcorpDao;
import com.tlys.dic.model.DicReparetype;
import com.tlys.dic.model.DicRwstockdepot;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicTankrepcorp;
import com.tlys.equ.dao.EquRepCarsDao;
import com.tlys.equ.dao.EquRepRecordDao;
import com.tlys.equ.model.EquRepCars;
import com.tlys.equ.model.EquRepRecord;
import com.tlys.sys.model.SysMessage;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.service.SysMessageService;
import com.tlys.sys.service.SysUserService;

@Service
public class EquRepRecordService {

	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	CacheService cacheService;
	@Autowired
	EquRepRecordDao equRepRecordDao;
	@Autowired
	DicReparetypeDao dicReparetypeDao;
	@Autowired
	DicRwstockdepotDao dicRwstockdepotDao;
	@Autowired
	DicTankrepcorpDao dicTankrepcorpDao;
	@Autowired
	DicSinocorpDao dicSinocorpDao;
	@Autowired
	EquRepCarsDao equRepCarsDao;
	@Autowired
	SysMessageService sysMessageService;
	@Autowired
	SysUserService sysUserService;

	public List<EquRepRecord> findEquRepRecord() {
		return equRepRecordDao.findEquRepRecord();
	}

	// public int getEquRepRecordCount(final EquRepRecord equRepRecord, final
	// PageCtr<EquRepRecord> pageCtr) {
	//
	// String key = "";
	// Integer count = (Integer) cacheService.get(key);
	// if (null == count) {
	// count = equRepRecordDao.getEquRepRecordCount(equRepRecord, pageCtr);
	// cacheService.put(key, count);
	// }
	// return count;
	// }
	public List<EquRepRecord> findEquRepRecord(String carno) {
		return equRepRecordDao.findEquRepRecord(carno);
	}

	public List<EquRepRecord> getEquRepRecord(String carno, String rtypeid) {
		return equRepRecordDao.getEquRepRecord(carno, rtypeid);
	}

	public List<EquRepRecord> findEquRepRecord(String carno, String suitkind) {
		return equRepRecordDao.findEquRepRecord(carno, suitkind);
	}

	public List<DicReparetype> findDicReparetype(String suitkind, String rtypeid) {
		return dicReparetypeDao.findDicReparetypes(suitkind, rtypeid);
	}

	public DicReparetype getDicReparetype(String rtypeid) {
		return dicReparetypeDao.load(rtypeid);
	}

	public EquRepRecord getEquRepRecord(Long id) {
		return equRepRecordDao.load(id);
	}

	public EquRepRecord getEquRepRecord(String rtypeid, String carno, String repflag) {
		return equRepRecordDao.getEquRepRecord(rtypeid, carno, repflag);
	}

	public EquRepRecord getPrevEquRepRecord(String rtypeid, String carno) {
		return equRepRecordDao.getPrevEquRepRecord(rtypeid, carno);
	}

	public void delete(EquRepRecord equRepRecord) {
		equRepRecordDao.delete(equRepRecord);
	}

	public void save(EquRepRecord equRepRecord) {
		equRepRecordDao.save(equRepRecord);
	}

	public void update(EquRepRecord equRepRecord) {
		equRepRecordDao.updateEntity(equRepRecord, equRepRecord.getId());
	}

	public void updateEquRepRecord(EquRepRecord equRepRecord) {
		equRepRecordDao.update(equRepRecord);
	}

	public List<DicRwstockdepot> findDicRwstockdepot() {
		return dicRwstockdepotDao.findDicRwstockdepot();
	}

	public DicRwstockdepot getFirstDicRwstockdepot() {
		return dicRwstockdepotDao.getFirstDicRwstockdepot();
	}

	public DicRwstockdepot getDicRwstockdepot(String stockdepotid) {
		return dicRwstockdepotDao.load(stockdepotid);
	}

	public List<DicTankrepcorp> findDicTankrepcorp() {
		return dicTankrepcorpDao.findAll();
	}

	public DicTankrepcorp getDicTankrepcorp(String repcorpid) {
		return dicTankrepcorpDao.load(repcorpid);
	}

	public void exportToExcel(List<EquRepRecord> equRepRecords, Map<String, DicReparetype> dicReparetypeByRtypeidMap,
			HSSFWorkbook workbook) {
		if (null == equRepRecords || equRepRecords.isEmpty()) {
			return;
		}
		List<DicSinocorp> dicSinocorps = dicSinocorpDao.findDicSinocorp();

		for (EquRepRecord equRepRecord : equRepRecords) {

		}
	}

	public void findEquRepCars(EquRepCars equRepCars, PageCtr<EquRepCars> pageCtr) {
		int totalRecord = equRepCarsDao.getEquRepCarsCount(equRepCars);
		pageCtr.setTotalRecord(totalRecord);
		equRepCarsDao.findEquRepCars(equRepCars, pageCtr);
	}

	public Object[] call_P_ZBC_REP_GENCAR() {
		return equRepCarsDao.call_P_ZBC_REP_GENCAR();
	}

	public List<Object> findCarnos(EquRepRecord equRepRecord) {
		return equRepRecordDao.findCarnos(equRepRecord);
	}

	public void sendMessage(EquRepRecord equRepRecord, SysUser sysUser) {
		String corpid = equRepRecord.getCorpid();
		if (logger.isDebugEnabled()) {
			logger.debug("corpid : " + corpid);
		}
		List<Object[]> carsNums = equRepCarsDao.findEquRepCarsNum(corpid);
		List<Object[]> allCarsNums = equRepCarsDao.findEquRepCarsNum(null);
		// 企业本月待检车数
		String corpidMpCarsNums = "";
		// 企业超期未检车数
		String corpidEpCarsNums = "";
		// 所有企业本月待检车数
		String mpCarsNums = "";
		// 所有企业超期未检车数
		String epCarsNums = "";
		if (null != carsNums && !carsNums.isEmpty()) {
			for (int i = 0; i < carsNums.size(); i++) {
				Object[] nums = carsNums.get(i);
				if (nums[0].toString().equals("MP")) {
					corpidMpCarsNums = nums[1].toString();
				} else if (nums[0].toString().equals("EP")) {
					corpidEpCarsNums = nums[1].toString();
				}
			}
		}

		if (null != allCarsNums && !allCarsNums.isEmpty()) {
			for (int i = 0; i < allCarsNums.size(); i++) {
				Object[] nums = allCarsNums.get(i);
				if (nums[0].toString().equals("MP")) {
					mpCarsNums = nums[1].toString();
				} else if (nums[0].toString().equals("EP")) {
					epCarsNums = nums[1].toString();
				}
			}
		}
		List<String> useridList = sysUserService.getUserIdsByCorpids(CommUtils.getCorpId());
		String userid = sysUser.getUserid();
		useridList.remove(userid);
		if (null != useridList && !useridList.isEmpty()) {
			for (int i = 0; i < useridList.size(); i++) {
				SysMessage sysMessage = new SysMessage();
				sysMessage.setMsgaction("提交");
				String content = "本月待检车数：" + corpidMpCarsNums + ";超期未检车数：" + corpidEpCarsNums;
				sysMessage.setMsgcontent(content);
				sysMessage.setMsgtypeid("004");
				sysMessage.setSenderid(userid);
				sysMessage.setReceiverid(useridList.get(i));
				sysMessage.setSendtime(new Date());
				sysMessage.setStatus("0");
				sysMessage.setUrl("/dispatch.action?target=equ/equ-rep-record-frame");
				sysMessageService.save(sysMessage);
			}
			sysMessageService.sendMessage(useridList.toArray());
		}
	}

	public void importRepRec(File file) {
		List<DicReparetype> dicReparetypes = dicReparetypeDao.findDicReparetypes(null, null);
		Map<String, DicReparetype> dicReparetypeMap = new HashMap<String, DicReparetype>();
		if (null != dicReparetypes && !dicReparetypes.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("dicReparetypes.size : " + dicReparetypes.size());
			}
			for (DicReparetype dicReparetype : dicReparetypes) {
				dicReparetypeMap.put(dicReparetype.getRtypeid(), dicReparetype);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("dicReparetypeMap : " + dicReparetypeMap);
		}
		Workbook workbook = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
		} catch (Exception e) {
			try {
				fis = new FileInputStream(file);
				workbook = new HSSFWorkbook(fis);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		sheet = workbook.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		if (logger.isDebugEnabled()) {
			logger.debug("rowNum : " + rowNum);
		}
		for (int i = 1; i < rowNum; i++) {
			row = sheet.getRow(i);
			int cellNum = row.getLastCellNum();
			if (logger.isDebugEnabled()) {
				logger.debug("cellNum : " + cellNum);
			}
			// 序号
			// /企业代码/企业名称/车号/检修类型ID/检修名称/扣修地点/扣修时间/送修时间/修竣时间/检修单位/检修单位ID/下次检修时间
			cell = row.getCell(1);
			String corpid = cell.getStringCellValue();
			cell = row.getCell(2);
			String corpName = cell.getStringCellValue();
			cell = row.getCell(3);
			String carno = cell.getStringCellValue();
			cell = row.getCell(4);
			String rtypeid = cell.getStringCellValue();
			cell = row.getCell(5);
			String rtypename = cell.getStringCellValue();
			// 扣修地点
			cell = row.getCell(6);
			String distrainaddress = cell.getStringCellValue();
			// 扣修时间
			cell = row.getCell(7);
			Date distraindate = cell.getDateCellValue();
			// 送修时间
			cell = row.getCell(8);
			Date deliverdate = cell.getDateCellValue();
			// 修竣时间
			cell = row.getCell(9);
			Date finishdate = cell.getDateCellValue();
			// 检修单位
			cell = row.getCell(10);
			String repcorpname = cell.getStringCellValue();
			// 检修单位ID
			cell = row.getCell(11);
			String repcorpid = cell.getStringCellValue();

			cell = row.getCell(12);
			Date nextrepdate = cell.getDateCellValue();

			if (logger.isDebugEnabled()) {
				logger.debug(CommUtils.getString(corpid, ",", corpName, ",", carno, ",", rtypeid, ",", rtypename, ",",
						distrainaddress, ",", distraindate, ",", deliverdate, ",", finishdate, ",", repcorpname, ",",
						repcorpid, ",", nextrepdate));
			}

			List<EquRepRecord> equRepRecords = equRepRecordDao.getEquRepRecord(carno, rtypeid);
			if (null != equRepRecords && !equRepRecords.isEmpty()) {
				if (logger.isDebugEnabled()) {
					logger.debug("equRepRecords : " + equRepRecords.size());
				}
				for (int j = 0; j < equRepRecords.size(); j++) {
					EquRepRecord equRepRecord = equRepRecords.get(j);
					String isrecently = equRepRecord.getIsrecently();
					if (null != isrecently && "1".equals(isrecently)) {
						equRepRecord.setIsrecently("0");
						equRepRecordDao.update(equRepRecord);
					}
				}
			}
			EquRepRecord equRepRecord = new EquRepRecord();
			equRepRecord.setActive("1");
			equRepRecord.setCarno(carno);
			equRepRecord.setRepcorpid(repcorpid);
			equRepRecord.setRepcorpname(repcorpname);
			equRepRecord.setCreatedtime(new Date());
			equRepRecord.setDeliverdate(deliverdate);
			DicReparetype dicReparetype = dicReparetypeMap.get(rtypeid);
			equRepRecord.setDicReparetype(dicReparetype);
			equRepRecord.setDistrainaddress(distrainaddress);
			equRepRecord.setDistraindate(distraindate);
			equRepRecord.setFinishdate(finishdate);
			equRepRecord.setIsrecently("1");
			equRepRecord.setNextrepdate(nextrepdate);
			equRepRecord.setRtypename(rtypename);
			equRepRecord.setWarnstatus("0");
			equRepRecord.setCorpid(corpid);
			equRepRecordDao.save(equRepRecord);
		}
	}
}
