package com.tlys.pla.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlys.comm.bas._GenericService;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelImpUtil;
import com.tlys.dic.model.DicProdsecondkind;
import com.tlys.dic.model.DicProduct;
import com.tlys.pla.dao.PlaImpDao;
import com.tlys.pla.model.PlaAmspstransportdet;
import com.tlys.pla.model.PlaAmupstransportdet;
import com.tlys.pla.model.PlaMspstransportdet;
import com.tlys.pla.model.PlaMspstransportdet2;
import com.tlys.pla.model.PlaMupstransportdet;
import com.tlys.pla.model.PlaMupstransportdet2;
import com.tlys.pla.model.PlaYuprtamount;
/**
 * @author sunshanh
 * 
 */
@Service
public class PlaImpService extends  _GenericService {
	@Autowired
	PlaImpDao plaImpDao;
	@Autowired
	DicMap dicMap;
	@Autowired
	ExcelImpUtil excelImpUtil; 

	//***********************************SQL处理方法**************************************	
	/**
	 * 执行SQL语句（单条)
	 * @param sql
	 * @return
	 */
	public int executeSql(final String sql) {
		return plaImpDao.executeSql(sql);
	}
	/**
	 * 执行SQL语句（数组)
	 * @param sql
	 * @return
	 */
	public int executeSql(final String[] sql) {
		return plaImpDao.executeSqlArray(sql);
	}
	/**
	 * 执行SQL语句（数组)
	 * @param sql
	 * @return
	 */
	public int executeCountSql(final String sql) {
		return plaImpDao.executeCountSql(sql);
	}

	//***********************************导入过程**************************************
	/**
	 * 0检查是否允许导入
	 * @param tbName
	 * @return
	 */
	public boolean checkStatus(final String tbName,final String currYear,final String currMth,final String currArea,final String currCorp){
		boolean status=false;
		try{
			String sql=plaImpDao.checkStatus(tbName, currYear, currMth, currArea, currCorp);
			if(log.isDebugEnabled()){
				log.debug("****************************************************数据库中含有记录数**********");
				log.debug(sql);
			}
			int res=0;
			if(!"".equals(sql)){
				res = plaImpDao.executeCountSql(sql);
			}
			if(res>0){
				status=false;
			}else{
				status=true;
			}
		}catch(Exception ex){
			if(log.isDebugEnabled()){
				log.debug("****************************************************验证是否可以导入时出现异常**********");
				log.debug(ex.getMessage());
			}
			status=false;
		}			
		return status;
	}
	/**
	 * 1保存附件到目录，将文件地址返回
	 * 
	 * @param srcFile
	 * @param destFileName
	 * @return
	 */
	public String upFile(final File srcFile,final String srcFileName) {
		ServletContext context = ServletActionContext.getServletContext();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssms"); 
		java.util.Date currentDTime = new java.util.Date();//得到当前系统时间 
		//临时文件名称
		String saveFileName = formatter.format(currentDTime) +"."+CommUtils.getExt(srcFileName);
		//上传目标逻辑路径
		String destUrlPath= CommUtils.getString(ATTACH_URL,"pla","/","temp", "/");
		//上传目标的物理路径
		String destRealPath = context.getRealPath(destUrlPath);	
		//上传目标文件逻辑全路径
		String destUrlFile = destUrlPath + saveFileName;
		//上传目标文件物理全路径
		String destRealFile = destRealPath + "/" + saveFileName;
		
		//log.debug(destRealFile);

		//判断目录是否存在，如不存在，创建之
		File file = new File(destRealPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		//删除已存在的文件
		//File existFile = new File(destRealFile);
		//if (existFile.exists()) {
		//	existFile.delete();
		//}
		
		//执行上传
		File destFile = new File(destRealFile);		
		if (copyFile(srcFile, destFile)) {
			return destUrlFile;
		}
		return "";
	}
	/**
	 * 2检查Excel数据前所做工作（更新字典)
	 * @param tbName
	 * @param currExcelName
	 * @throws Exception
	 */
	public int initBefImp(final String[] initCfg,final String currExcelName) throws Exception{
		//String[] initCfg=getTBBefImpCfg(tbName);
		int runCount=0;
		if(null!=initCfg&&initCfg.length>0){
			List excelData=excelImpUtil.getExcelData(currExcelName);
			for (int i = 0; i < initCfg.length; i++) {
				if(null!=initCfg[i]&&!"".equals(initCfg[i])){
					String[] cfgArr=initCfg[i].split("\\:");
					if(null!=cfgArr&&cfgArr.length>=2){
						String procName=cfgArr[0];
						String[] procPara=cfgArr[1].split("\\,"); 
						String[] procParaData=new String[procPara.length];
						for (int j = 0; j < excelData.size(); j++) {
							//判断参数是否有空情况
							boolean haveNull=false;
							for (int k = 0; k < procPara.length; k++) {
								String[] row=(String[])excelData.get(j);
								
								if(log.isDebugEnabled())
								{
									//log.debug("################################当前行："+j);
									//log.debug("################################当前列："+Integer.parseInt(procPara[k]));
									//log.debug("################################Row的Cols值："+row.length);
									//log.debug("################################Cell值："+row[Integer.parseInt(procPara[k])]);									
								}
								if(row.length>Integer.parseInt(procPara[k])){
									procParaData[k]=row[Integer.parseInt(procPara[k])];
								}
								if(null==procParaData[k]||"".equals(procParaData[k])){
									haveNull=true;
								}
							}
							if(!haveNull&&"initPlanDicDataReceiver".equals(procName)){
								//字典中是否有该条记录
								boolean haveInTable=false;
								//1判断客户是否存在
								//将企业中文翻译成ID		
								String[] customerM=excelImpUtil.getCustomer(procParaData[0]);
								String customerID=(customerM==null||customerM[0]==null)?"":customerM[0];
								haveInTable=(customerM!=null&&customerM[0]!=null);	
								//2判断收货人是否存在
								if(haveInTable){
									String[] customerReceiverM=excelImpUtil.getReciver(procParaData[1], customerID);								
									haveInTable=(customerReceiverM!=null&&customerReceiverM[0]!=null);	
								}
								
								//如果字典表中没有找到
								if(!haveInTable){
									if(log.isDebugEnabled())
									{
										log.debug("################################存储过程输入参数个数："+procParaData.length);
										log.debug("################################存储过程输入参数1："+procParaData[0]);
										log.debug("################################存储过程输入参数2："+procParaData[1]);
									}
									Object[] objs = plaImpDao.initPlanDicDataReceiver(procParaData);
									if(log.isDebugEnabled()&&null!=objs)
									{
										log.debug("################################存储过程返回值个数："+objs.length);
										log.debug("################################存储过程返回值1："+objs[0]);
										log.debug("################################存储过程返回值2："+objs[1]);
										log.debug("################################存储过程返回值3："+objs[2]);
										log.debug("################################存储过程返回值4："+objs[3]);
									}
									runCount++;
//									//更新Map标志位，以便下次取Map时先重新保存Map
//									if(null!=objs&objs.length>=4&&null!=objs[2]&&!"".equals(objs[2]))
//									{									
//										dicMap.dicAlter("dicCustomer");
//										dicMap.dicAlter("dicSinocorp");
//										dicMap.dicAlter("ctlCorpreceiver");
//										dicMap.dicAlter("ctlCustomerreceiver");
//									}
								}
							}
						}	
						
						
						
//						try{
//							Object oo=new PlaImpService();
//							Class clazz=oo.getClass();
//							Method method = clazz.getMethod(procName,Array.class);							
//							for (int j = 0; j < excelData.length; j++) {
//								for (int k = 0; k < procPara.length; k++) {
//									procParaData[k]=excelData[j][Integer.parseInt(procPara[k])];
//								}
//								Object[] rtn = (Object[])method.invoke(oo, procParaData);
//							}	
//							if(log.isDebugEnabled())
//							{
//								log.debug("################################执行存储过程："+method);
//							}
//						} catch (Exception e) {
//							
//							throw e;
//						}
						
					}
				}
			}
		}
		return runCount;
	}
	/**
	 * 4将修改好的Excel文件导入到表
	 * @return
	 * @throws Exception
	 */
	public String importExcelToDb(final String currExcelName,final String tbName,final String[] columns,final String currYear,final String currMth,final String currArea,final String currCorp,final String importerID) throws Exception
	{
		//返回信息
		String res="";
		//错误状态
		boolean _errFlag=false;
		//对应Oracle数据表名
		String tbOracleName=getTBOracleName(tbName);
		if(null==tbOracleName||"".equals(tbOracleName))
		{_errFlag=true;}
		//String[] columns=getTBColumnCfg(tableName);
		if(null==columns||columns.length<=0)
		{_errFlag=true;}
		
		if(_errFlag){
			res="参数错误：不能执行写入数据库操作，请检查系统是否超时，或链接是否正确！";
		}else{
			//执行保存操作
			try{
				//是否需要新建立调整主记录信息
				Boolean isNewRecord;
				if(plaImpDao.getLastAdjustStauts(tbName, currYear, currMth, currArea, currCorp)>0){
					isNewRecord=true;
				}else{
					isNewRecord=false;
				}
				//获取当前调整编号
				String currAdjustNumber=getCurrAdjustNumber(tbName,currYear,currMth,currArea,currCorp,isNewRecord);
				//获取计划编码
				String planNo=getPlanNO(tbName,currYear,currMth,currArea,currCorp,currAdjustNumber);
				
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************planNo******************");
					log.debug(planNo);
					log.debug("是否新插入:"+isNewRecord);
				}
				//清除主表的SQL语句
				String sqlMainDel=plaImpDao.getTBMainDelete(tbName,currYear,currMth,currArea,currCorp,planNo);
				//清除明细表的SQL语句
				String sqlMxDel=plaImpDao.getTBMxDelete(tbName,currYear,currMth,currArea,currCorp,planNo);
				//插入主表的SQL语句
				String sqlMain=plaImpDao.getTBMainInsert(tbName,currYear,currMth,currArea,currCorp,planNo,importerID);
				//插入明细的SQL语句
				String[] sqlMx=excelImpUtil.getImportInsertSql(currExcelName, tbOracleName, columns,currYear,currMth,currArea,currCorp,planNo);
				//清除重复数据的SQL语句
				String sqlMxDelMore=plaImpDao.getTBMxDeleteMore(tbName,currYear,currMth,currArea,currCorp,planNo);
				
				//将SQL保存至文件
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss"); 
				java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
				ServletContext context = ServletActionContext.getServletContext();
				//上传目标的物理路径
				String destDir = CommUtils.getString(ATTACH_URL,"pla","/","log", "/");
				String destFile = CommUtils.getString(destDir ,"imp_"+formatter.format(currentTime)+".sql");
				String destRealDir = context.getRealPath(destDir);
				File dir=new File(destRealDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				String destRealFile = context.getRealPath(destFile);	
				FileOutputStream _output=new FileOutputStream(destRealFile);				
				File file=new File(destRealFile);
				FileWriter fw=new FileWriter(file);				
				StringBuffer sqlStr=new StringBuffer();
				for(int i=0;i<sqlMx.length;i++){
					sqlStr.append(sqlMx[i]);		
					sqlStr.append(";");	
				}
				StringBuffer sbFileContent=new StringBuffer();
				sbFileContent.append(sqlMainDel);
				sbFileContent.append(";commit;");
				sbFileContent.append(sqlMxDel);
				sbFileContent.append(";commit;");
				sbFileContent.append(sqlMain);
				sbFileContent.append(";commit;");
				sbFileContent.append(sqlStr.toString());
				sbFileContent.append(";commit;");
				sbFileContent.append(sqlMxDelMore);
				sbFileContent.append(";commit;");
				fw.write(sbFileContent.toString());
				fw.close();
				_output.close();
				
				//清除主表记录数
				int cntDel=0;
				//清除明细表记录数
				int cntDelMx=0;
				//主表添加记录数
				int cntMain=0;
				//明细表添加记录数
				int cntMX=0;
				//明细表清除重复数
				int cntDelSame=0;
				//插入之前执行存储过程数
				int cntBefInsert=0;
				
				//删除旧数据
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************删除主表旧数据");
					log.debug(sqlMainDel);
				}
				if(null!=sqlMainDel&&!"".equals(sqlMainDel)){
					cntDel=executeSql(sqlMainDel);
				}

				//删除旧明细数据
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************删除明细表旧数据");
					log.debug(sqlMxDel);
				}
				if(null!=sqlMxDel&&!"".equals(sqlMxDel)){
					cntDelMx=executeSql(sqlMxDel);
				}

				//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
				//处理插入之前执行的存储过程
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************处理插入之前执行的存储过程");
				}
				String[] initCfg = getTBBefInsertCfg(tbName);
				if (null != initCfg && initCfg.length > 0) {
					cntBefInsert = initBefImp(initCfg,currYear,currMth,currArea,currCorp,currAdjustNumber,importerID);
				}

				
				//插入主表
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************插入主表");
					log.debug(sqlMain);
				}
				if(null!=sqlMain&&!"".equals(sqlMain)&&!isNewRecord&&("00".equals(currAdjustNumber) || "".equals(currAdjustNumber))){
					cntMain=executeSql(sqlMain);	
				}

				//插入明细表
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************插入明细表");
					log.debug(sqlMx);
				}
				if(null!=sqlMx&&!"".equals(sqlMx)){
					cntMX=executeSql(sqlMx);						
				}
				cntMX=sqlMx.length;

				//清除重复
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************清除重复");
					log.debug(sqlMxDelMore);
				}
				if(null!=sqlMxDelMore&&!"".equals(sqlMxDelMore)){
					cntDelSame=executeSql(sqlMxDelMore);
				}

				
				if(cntMX>0){
					res = "成功插入"+cntMX+"条数据！主表"+cntMain+"条数据！清除已有明细数据"+cntDelMx+"条，清除新重复数据"+cntDelSame+"条";
				}else{
					res ="导入失败！";
				}
				
				//执行删除Excel操作
//				String _delMsg="";
//				//导入之后删除原Excel临时数据
//				try{
//					if(excelImpUtil.deleteExcelFile(currExcelName)){
//						_delMsg="临时文件删除成功！";
//					}else{
//						_delMsg="临时文件删除失败！";
//					}
//				}catch(Exception e)
//				{
//					_delMsg="删除临时文件时出现异常！"+e.getMessage();
//					throw e;
//				}
				
				
			}catch(Exception e)
			{
				res="导入时出现异常！"+e.toString();
				throw e;
			}	
			
		}
		return res;
	}
	
	/**
	 * 5导入之前所做工作（清理并准备数据)
	 * @param initCfg
	 * @param currYear
	 * @param currMth
	 * @param currArea
	 * @param currCorp
	 * @return
	 */
	public int initBefImp(final String[] initCfg,final String currYear,final String currMth,final String currArea,final String currCorp,final String currAdjustNumber,final String currAdjuster){
		//String[] initCfg=getTBBefImpCfg(tbName);
		int runCount=0;
		if(null!=initCfg&&initCfg.length>0){
			for (int i = 0; i < initCfg.length; i++) {
				if(null!=initCfg[i]&&!"".equals(initCfg[i])){
					String[] cfgArr=initCfg[i].split("\\:");
					if(null!=cfgArr&&cfgArr.length>=2){
						String procName=cfgArr[0];
						String[] procPara=cfgArr[1].split("\\,"); 
						String[] procParaData=new String[procPara.length];
						if("initPlanPadjMspstransport".equals(procName)){
							procParaData[0]=currCorp;	
							procParaData[1]=currMth;	
							procParaData[2]=currAdjustNumber;	
							procParaData[3]=currAdjuster;	
							
							if(log.isDebugEnabled())
							{
								log.debug("################################存储过程输入参数个数："+procParaData.length);
								log.debug("################################存储过程输入参数1："+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanPadjMspstransport(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################存储过程返回值个数："+objs.length);
								log.debug("################################存储过程返回值1："+objs[0]);
								log.debug("################################存储过程返回值2："+objs[1]);
							}
							runCount++;
						}else if("initPlanPadjMupstransport".equals(procName)){
							procParaData[0]=currArea;	
							procParaData[1]=currMth;	
							procParaData[2]=currAdjustNumber;	
							procParaData[3]=currAdjuster;	
							
							if(log.isDebugEnabled())
							{
								log.debug("################################存储过程输入参数个数："+procParaData.length);
								log.debug("################################存储过程输入参数1："+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanPadjMupstransport(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################存储过程返回值个数："+objs.length);
								log.debug("################################存储过程返回值1："+objs[0]);
								log.debug("################################存储过程返回值2："+objs[1]);
							}
							runCount++;
						}

					}
				}
			}
		}
		return runCount;
	}
	
	
	/**
	 * 5导入之后所做工作（整理数据)
	 * @param initCfg
	 * @param currYear
	 * @param currMth
	 * @param currArea
	 * @param currCorp
	 * @return
	 */
	public int initAftImp(final String[] initCfg,final String currYear,final String currMth,final String currArea,final String currCorp){
		//String[] initCfg=getTBBefImpCfg(tbName);
		int runCount=0;
		if(null!=initCfg&&initCfg.length>0){
			for (int i = 0; i < initCfg.length; i++) {
				if(null!=initCfg[i]&&!"".equals(initCfg[i])){
					String[] cfgArr=initCfg[i].split("\\:");
					if(null!=cfgArr&&cfgArr.length>=2){
						String procName=cfgArr[0];
						String[] procPara=cfgArr[1].split("\\,"); 
						String[] procParaData=new String[procPara.length];
						if("initPlanDataAtImpComplate".equals(procName)){
							procParaData[0]=currMth;							
							if(log.isDebugEnabled())
							{
								log.debug("################################存储过程输入参数个数："+procParaData.length);
								log.debug("################################存储过程输入参数1："+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanDataAtImpComplate(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################存储过程返回值个数："+objs.length);
								log.debug("################################存储过程返回值1："+objs[0]);
								log.debug("################################存储过程返回值2："+objs[1]);
							}
							runCount++;
						}else if("initPlanGenMspstransport".equals(procName)){
							procParaData[0]=currMth;	
							if(log.isDebugEnabled())
							{
								log.debug("################################存储过程输入参数个数："+procParaData.length);
								log.debug("################################存储过程输入参数1："+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanGenMspstransport(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################存储过程返回值个数："+objs.length);
								log.debug("################################存储过程返回值1："+objs[0]);
								log.debug("################################存储过程返回值2："+objs[1]);
							}
							runCount++;
						}else if("initPlanGenMupstransport".equals(procName)){
							procParaData[0]=currMth;	
							if(log.isDebugEnabled())
							{
								log.debug("################################存储过程输入参数个数："+procParaData.length);
								log.debug("################################存储过程输入参数1："+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanGenMupstransport(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################存储过程返回值个数："+objs.length);
								log.debug("################################存储过程返回值1："+objs[0]);
								log.debug("################################存储过程返回值2："+objs[1]);
							}
							runCount++;
						}

					}
				}
			}
		}
		return runCount;
	}
	
	//***********************************Check页面所需方法**************************************
	/**
	 * 根据大类获取小类Model集合
	 * @param cateID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getProdSecCateByCateID(final String cateID) throws Exception{
		ArrayList list=new ArrayList();
		try{
			Map map = dicMap.getProductsecondMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicProdsecondkind obj=(DicProdsecondkind)map.get(key);
					if(obj!=null && cateID.equals(obj.getCategoryid())){
						list.add(obj);
					}
				}
			}
		}catch(Exception e){
			throw e;			
		}
		return list;
	}
	/**
	 * 根据小类获取产品Model集合
	 * @param secCateID
	 * @return
	 * @throws Exception
	 */
	public ArrayList getProdListBySecCateID(final String secCateID) throws Exception{
		ArrayList list=new ArrayList();
		try{
			Map map = dicMap.getProductMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicProduct obj=(DicProduct)map.get(key);
					if(obj!=null && secCateID.equals(obj.getSecondkindid())){
						list.add(obj);
					}
				}
			}
		}catch(Exception e){
			throw e;			
		}
		return list;
	}
	/**
	 * 根据大类拼接小类Options串
	 * @param prodCateID
	 * @param selected
	 * @return
	 * @throws Exception
	 */
	public String getProdSecCateOptsByCateID(final String prodCateID,final String selected) throws Exception{
		StringBuffer _result=new StringBuffer();
		_result.append("<option value=\"\">请选择小类...</option>");
		ArrayList list=getProdSecCateByCateID(prodCateID);
		try{
			if(list.size()>0){
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					DicProdsecondkind obj = (DicProdsecondkind) iterator.next();
					if(obj!=null){
						String key=obj.getSecondkindid();
						String _keyVlaueStr=obj.getShortname();
						_result.append("<option value=\""+key+"\"");
						if(key.equals(selected))
						{
							_result.append(" selected");
						}
						_result.append(">");
						_result.append(_keyVlaueStr);
						_result.append("</option>");
					}
				}
			}
		}catch(Exception e){
			throw e;			
		}
		return _result.toString();
	}
	/**
	 * 根据小类拼接产品Options串
	 * @param secProdCateID
	 * @param selected
	 * @return
	 * @throws Exception
	 */
	public String getProdListBySecCateID(final String secProdCateID,final String selected) throws Exception{
		StringBuffer _result=new StringBuffer();
		ArrayList list=getProdListBySecCateID(secProdCateID);
		_result.append("<option value=\"\">请选择产品...</option>");
		try{
			if(list.size()>0){
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					DicProduct obj = (DicProduct) iterator.next();
					if(obj!=null){
						String key=obj.getProdid();
						String _keyVlaueStr=obj.getShortname();
						_result.append("<option value=\""+key+"\"");
						if(key.equals(selected))
						{
							_result.append(" selected");
						}
						_result.append(">");
						_result.append(_keyVlaueStr);
						_result.append("</option>");
					}
				}
			}
		}catch(Exception e){
			throw e;			
		}
		return _result.toString();
	}

	//***********************************内部配置方法**************************************
	/**
	 * 获得数据表Object
	 * @param tbName
	 * @return
	 */
	public Object getTBObject(final String tbName)
	{
		//Class clazz=Class.forName("com.tlys.pla.model.PlaMupstransportdet");//这里是类名全名，有包要加上包
		//Object obj=clazz.newInstance();	
		if("plaMupstransportdet".equals(tbName)){//TB_ZBC_PLA_MUPSTRANSPORTDET
			return new PlaMupstransportdet();
		}else if("plaAmupstransportdet".equals(tbName)){//TB_ZBC_PLA_AMUPSTRANSPORTDET
			return new PlaAmupstransportdet();
		}else if("plaMspstransportdet".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET
			return new PlaMspstransportdet();
		}else if("plaAmspstransportdet".equals(tbName)){//TB_ZBC_PLA_AMSPSTRANSPORTDET
			return new PlaAmspstransportdet();
		}else if("plaMspstransportdet2".equals(tbName)){//TB_ZBC_PLA_MSPSTRANSPORTDET2
			return new PlaMspstransportdet2();
		}else if("plaMupstransportdet2".equals(tbName)){//TB_ZBC_PLA_MUPSTRANSPORTDET2
			return new PlaMupstransportdet2();
		}else if("plaYuprtamount".equals(tbName)){
			return new PlaYuprtamount();
		}else{
			return null;
		}
	}
	/**
	 * 获取数据库表Oracle表名
	 * @param tbName
	 * @return
	 */
	private String getTBOracleName(final String tbName){
		if("plaMupstransportdet".equals(tbName)){
			return "TB_ZBC_PLA_MUPSTRANSPORTDET";
		}else if("plaAmupstransportdet".equals(tbName)){
			return "TB_ZBC_PLA_AMUPSTRANSPORTDET";
		}else if("plaMspstransportdet".equals(tbName)){
			return "TB_ZBC_PLA_MSPSTRANSPORTDET";
		}else if("plaAmspstransportdet".equals(tbName)){
			return "TB_ZBC_PLA_AMSPSTRANSPORTDET";
		}else if("plaMspstransportdet2".equals(tbName)){
			return "TB_ZBC_PLA_MSPSTRANSPORTDET2";
		}else if("plaMupstransportdet2".equals(tbName)){
			return "TB_ZBC_PLA_MUPSTRANSPORTDET2";
		}else if("plaYuprtamount".equals(tbName)){
			return "TB_ZBC_PLA_YUPRTAMOUNT";			
		}else{
			return "";
		}
	}
	/**
	 * 获取上传首页显示表单情况
	 * @param tbName
	 * @return
	 */
	public String getPageShowTags(final String tbName){
		String currIndexPageShowTags="";
		if("plaYuprtamount".equals(tbName)){
			currIndexPageShowTags="currYear";
		}else if("plaMupstransportdet".equals(tbName)){
			currIndexPageShowTags="currMth,currArea";
		}else if("plaAMupstransportdet".equals(tbName)){
			currIndexPageShowTags="currMth,currArea";
		}else if("plaMspstransportdet".equals(tbName)){
			currIndexPageShowTags="currMth,currCorp";
		}else if("plaAmspstransportdet".equals(tbName)){
			currIndexPageShowTags="currMth,currCorp";
		}else if("plaMspstransportdet2".equals(tbName)){
			currIndexPageShowTags="currMth,currCorp";
		}else if("plaMupstransportdet2".equals(tbName)){
			currIndexPageShowTags="currMth,currArea";
		}else{
			currIndexPageShowTags="currMth,currArea";
		}
		return currIndexPageShowTags;
	}
	/**
	 * 获取计划编号
	 * @param tbName
	 * @param currYear
	 * @param currMth
	 * @param currArea
	 * @param currCorp
	 * @return
	 */
	public String getPlanNO(final String tbName,final String currYear,final String currMth,final String currArea,final String currCorp,final String currAdjustNumber){
		String PlanNO="";
		if("plaYuprtamount".equals(tbName)){
			PlanNO="";
		}else if("plaMupstransportdet".equals(tbName)){
			PlanNO=currArea+"-"+currMth;
		}else if("plaAMupstransportdet".equals(tbName)){
			PlanNO=currArea+"-"+currMth;
		}else if("plaMspstransportdet".equals(tbName)){
			PlanNO=currCorp+"-"+currMth;
		}else if("plaAmspstransportdet".equals(tbName)){
			PlanNO=currCorp+"-"+currMth;
		}else if("plaMspstransportdet2".equals(tbName)){
			PlanNO=currCorp+"-"+currMth+"-"+currAdjustNumber;
		}else if("plaMupstransportdet2".equals(tbName)){
			PlanNO=currArea+"-"+currMth+"-"+currAdjustNumber;
		}else{
			PlanNO=currArea+"-"+currMth;
		}
		return PlanNO;
	}
	/**
	 * 获取当前使用的调整次数
	 * @param tbName
	 * @param currYear
	 * @param currMth
	 * @param currArea
	 * @param currCorp
	 * @param isNewRecord
	 * @return
	 */
	public String getCurrAdjustNumber(final String tbName,final String currYear,final String currMth,final String currArea,final String currCorp,final Boolean isNewRecord){
		String adjustNumber="";
		if("plaYuprtamount".equals(tbName)){
			adjustNumber="";
		}else if("plaMupstransportdet".equals(tbName)){
			adjustNumber="";
		}else if("plaAMupstransportdet".equals(tbName)){
			adjustNumber="";
		}else if("plaMspstransportdet".equals(tbName)){
			adjustNumber="";
		}else if("plaAmspstransportdet".equals(tbName)){
			adjustNumber="";
		}else if("plaMspstransportdet2".equals(tbName)){
			int maxNum=plaImpDao.getMaxAdjustNumber(tbName, currYear, currMth, currArea, currCorp);
			if(isNewRecord){
				maxNum=maxNum+1;
			}
			String nextNumStr="0"+maxNum;
			nextNumStr=nextNumStr.substring(nextNumStr.length()-2,nextNumStr.length());
			adjustNumber=nextNumStr;
		}else if("plaMupstransportdet2".equals(tbName)){
			int maxNum=plaImpDao.getMaxAdjustNumber(tbName, currYear, currMth, currArea, currCorp);
			if(isNewRecord){
				maxNum=maxNum+1;
			}
			String nextNumStr="0"+maxNum;
			nextNumStr=nextNumStr.substring(nextNumStr.length()-2,nextNumStr.length());
			adjustNumber=nextNumStr;
		}else{
			adjustNumber="";
		}
		return adjustNumber;
	}
	
	
	//***********************************程序配置**************************************	
	/**
	 * XML：获取Excel导入配置信息
	 * @param tbName
	 * @return
	 * 
	 * 1.XML文件结构
	 * <root>
	 * <table name="PlaMupstransportdet">
	 * <items count="18">
	 * <item id="0">XXXXXXXX</item>......
	 * ...
	 * 
	 * 格式说明
	 * 注意：SKIP表示跳过该列
	 * 0列ID
	 * 1列名称
	 * 2表单类型：TextBox、DropDownList、Label、TreeRoot、Tree、Variable、Sequence
	 * 			TextBox可编辑，配置长度4列名不可省略  格式如："REMARKS###描述###TextBox###String"
	 * 			Label 仅显示（不可编辑、不写入)配置长度4列名不可省略  格式如："###客户经理###Label###String"
	 * 			DropDownList 配置长度7 列名可省略  格式如："###企业###DropDownList###String###corp###DicSinocorp###getCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO"
	 * 			TreeRoot 配置长度7 列名可省略,只读不可编辑，刷新取决于Tree  格式如："###产品大类###TreeRoot###String###productcategory###DicProdcategory###getProductcategory:PRODUCTCATEGORYID"
	 * 			Tree 配置长度7 列名可省略  格式如："###产品###Tree###String###product:6,4,5:6,4,5###DicProduct###getProduct:PRODDUCTID,PRODUCTCATEGORYID,PRODUCTSECONDID:6,4,5:6,4,5"
	 * 			Variable变量（不显示Excel单元格真实内容)格式如："###月度###Variable###String#########currMth:MONTH"
	 * 			Sequence序列（显示Excel单元格对应内容)格式如："###编号###Sequence###Long#########SEQ_ZBC_PLA_MUPSTRANSPORTDET.nextval:ID"
	 * 3文本类型：String、Long、Integer、Float、Double、Date
	 * 4表单数据源：dicMap：map名称,map参数对应数组的下标，特殊处理sender，receiver需要参数，carkind customer判断ID、全称、简称,corpfromdbxml 从数据库和XML同时读取企业
	 * 5数据源类型：
	 * 6下拉框调用方法:方法名:返回需要对应字段：map参数对应数组的下标，特殊处理sender，receiver
	 * 			Tree:方法名：返回需要对应字段：对应参数数组下标
	 * 
	 * 注：当前系统可用变量currMth，currArea
	 */
	public String[] getTBColumnCfg(final String tbName)
	{
		if(log.isDebugEnabled()){
			log.debug("****************************************************进入读取XML**********");
		}
		String[] arr=null;			
		ServletContext context = ServletActionContext.getServletContext();
		String destDir = CommUtils.getString("/","pla","/","xml", "/");
		String destFile = CommUtils.getString(destDir ,"pla-imp-config.xml");
		String destRealDir = context.getRealPath(destDir);
		File dir=new File(destRealDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String destRealFile = context.getRealPath(destFile);	
		File file=new File(destRealFile); 
        SAXReader reader=new SAXReader(); 
        try { 
                Document doc=reader.read(file); 
                Element root=doc.getRootElement(); 
                Node tableNode = root.selectSingleNode("table[@name='"+tbName+"']");
                if(null!=tableNode){
	                List list = tableNode.selectNodes("items/item");
	                if(null!=list&&list.size()>0){
		                arr=new String[list.size()];
		                for (int i = 0; i < list.size(); i++) {
		                	Element e = (Element) list.get(i);
		                	//String attr=e.attributeValue("id");                	
							arr[i]=e.getText();
						} 
//                		for(Object o:list){                    
//                    		Element e = (Element) o;
//                    		String show=e.attributeValue("id");
//                    		String value=e.getText();
//                		}
	                }
                }
        } catch (DocumentException e) { 
                //e.printStackTrace(); 
                arr=null;
                if(log.isDebugEnabled()){
        			log.debug("***************从XML中获取配置是出现错误**********");
        			log.debug("table标签name为："+tbName);
        			log.debug("异常："+e.getMessage());
                }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel配置名称**********");
			log.debug(tbName);
			log.debug("***************XML内容获取Excel配置文件**********");
			if(null!=arr&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					log.debug(arr[i]);
				}
			}
		}
		return arr;		
		
		
//********************************************这里备份原始配置信息*************************************
//		String[] arr=null;
//		if("plaMupstransportdet".equals(tbName))//TB_ZBC_PLA_MUPSTRANSPORTDET
//		{	
//备份
//			arr=new String[22];
//			//arr[0]="ID###编号###TextBox###Long";
//			arr[0]="###编号###Sequence###Long#########SEQ_ZBC_PLA_MUPSTRANSPORTDET.nextval:ID";
//			arr[1]="###企业###DropDownList###String###corp###DicSinocorp###getCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO";	//===下拉  PLANNO特殊处理
//			arr[2]="###客户###DropDownList###String###customercorp###DicCustomer###getCustomer:CUSTOMERID,COUSTOMERSHORTNAME";	 		//===下拉
//			arr[3]="###发货人###DropDownList###String###sender:1###CtlCorpsender###getSender:SENDERID:1";
//			arr[4]="###产品大类###TreeRoot###String###productcategory###DicProdcategory###getProductcategory:PRODUCTCATEGORYID";
//			arr[5]="###产品小类###TreeRoot###String###productsecond###productsecondMap###getProductsecond:PRODUCTSECONDID";
//			arr[6]="###产品###Tree###String###product:6,4,5:6,4,5###DicProduct###getProduct:PRODDUCTID,PRODUCTCATEGORYID,PRODUCTSECONDID:6,4,5:6,4,5";				//===下拉	
//			arr[7]="###铁路品名###DropDownList###String###rwkind###DicGoods###getRwKind:RWKINDID,RKINDNAME";	
//			arr[8]="AMOUNT###数量###TextBox###Double";	
//			arr[9]="CARNUMBER###车数###TextBox###Long";		
//			arr[10]="###车种###DropDownList###String###carkind###DicCarkind###getCarKindByKey:CARKINDID";	 						//===下拉
//			arr[11]="###发站###DropDownList###String###startstation###DicRwstation###getStartStation:STARTSTATIONID";		//===下拉
//			arr[12]="###收货人###DropDownList###String###receiver:2###CtlCorpreceiver###getReciver:RECEIVERID,RECEIVERNAME:2";	
//			//arr[13]="###送达省份###DropDownList###String###goalprovince###DicProvince###getGoalProvince:GOALPROVINCEID";		//===下拉	
//			//arr[14]="GOALADDRESS###送达地###TextBox###String";		
//			arr[13]="SKIP";
//			arr[14]="SKIP";
//			arr[15]="###到站###DropDownList###String###endstation###DicRwstation###getEndStation:ENDSTATIONID";				//===下拉		 
//			//arr[16]="###承运人###DropDownList###String###transporter###DicTransporter###getTransport:TRANSPORTID";		 		//===下拉
//			arr[16]="SKIP";
//			arr[17]="SALER###客户经理###TextBox###String";	
//			arr[18]="###销售机构###DropDownList###String###saledepartment###DicSalecorp###getSaleDepartment:SALEDEPARTMENT";	
//			arr[19]="REMARKS###描述###TextBox###String";		
//			//arr[20]="MONTH###月度（YYYYMM)###TextBox###String";
//			arr[20]="SKIP";
//			arr[21]="###月度###Variable###String#########currMth:MONTH";
//			//arr[22]="###区域###Variable###String#########currArea:AREAID";
			
			
//正常
//			arr=new String[18];
//			//arr[0]="ID###编号###TextBox###Long";
//			arr[0]="###编号###Sequence###Long#########SEQ_ZBC_PLA_MUPSTRANSPORTDET.nextval:ID";
//			arr[1]="###企业###DropDownList###String###corp###DicSinocorp###getCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO";	//===下拉  PLANNO特殊处理
//			arr[2]="###客户###DropDownList###String###customer###DicCustomer###getCustomer:CUSTOMERID,COUSTOMERSHORTNAME";	 		//===下拉
//			arr[3]="###发货人###DropDownList###String###sender:1###CtlCorpsender###getSender:SENDERID:1";
//			arr[4]="###产品大类###TreeRoot###String###productcategory###DicProdcategory###getProductcategory:PRODUCTCATEGORYID";
//			arr[5]="###产品小类###TreeRoot###String###productsecond###productsecondMap###getProductsecond:PRODUCTSECONDID";
//			arr[6]="###产品###Tree###String###product:6,4,5:6,4,5###DicProduct###getProduct:PRODDUCTID,PRODUCTCATEGORYID,PRODUCTSECONDID:6,4,5:6,4,5";				//===下拉	
//			arr[7]="###铁路品名###DropDownList###String###rwkind###DicGoods###getRwKind:RWKINDID,RKINDNAME";	
//			arr[8]="AMOUNT###数量###TextBox###Double";	
//			arr[9]="CARNUMBER###车数###TextBox###Long";		
//			arr[10]="###车种###DropDownList###String###carkind###DicCarkind###getCarKindByKey:CARKINDID";	 						//===下拉
//			arr[11]="###发站###DropDownList###String###startstation###DicRwstation###getStartStation:STARTSTATIONID";		//===下拉
//			arr[12]="###收货人###DropDownList###String###receiver:2###CtlCorpreceiver###getReciver:RECEIVERID,RECEIVERNAME:2";	
//			arr[13]="###到站###DropDownList###String###endstation###DicRwstation###getEndStation:ENDSTATIONID";				//===下拉		 
//			arr[14]="SALER###客户经理###TextBox###String";	
//			arr[15]="###销售机构###DropDownList###String###saledepartment###DicSalecorp###getSaleDepartment:SALEDEPARTMENT";	
//			arr[16]="REMARKS###描述###TextBox###String";		
//			arr[17]="###月度###Variable###String#########currMth:MONTH";
//			//arr[22]="###区域###Variable###String#########currArea:AREAID";
//			
//		}else{
//			
//		}
//		return arr;
		
	}
	/**
	 * XML：获取导入数据时先处理的事件（向字典表补充新内容)
	 * @param tbName 导入XXX表时判断；
	 * @return
	 * 配置：arr[0]="存储过程名称：参数1，参数2...";
	 */
	public String[] getTBBefImpCfg(final String tbName){
		if(log.isDebugEnabled()){
			log.debug("****************************************************进入读取XML**********");
		}
		String[] arr=null;			
		ServletContext context = ServletActionContext.getServletContext();
		String destDir = CommUtils.getString("/","pla","/","xml", "/");
		String destFile = CommUtils.getString(destDir ,"pla-imp-config.xml");
		String destRealDir = context.getRealPath(destDir);
		File dir=new File(destRealDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String destRealFile = context.getRealPath(destFile);	
		File file=new File(destRealFile); 
        SAXReader reader=new SAXReader(); 
        try { 
                Document doc=reader.read(file); 
                Element root=doc.getRootElement(); 
                Node tableNode = root.selectSingleNode("table[@name='"+tbName+"']");
                if(null!=tableNode){
	                List list = tableNode.selectNodes("inits/init");
	                if(null!=list&&list.size()>0){
		                arr=new String[list.size()];
		                for (int i = 0; i < list.size(); i++) {
		                	Element e = (Element) list.get(i);
		                	//String attr=e.attributeValue("id");                	
							arr[i]=e.getText();
						} 
	                }
                }

        } catch (DocumentException e) { 
            //e.printStackTrace(); 
            arr=null;
            if(log.isDebugEnabled()){
    			log.debug("***************从XML中获取配置是出现错误**********");
    			log.debug("table标签name为："+tbName);
    			log.debug("异常："+e.getMessage());
            }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel配置名称**********");
			log.debug(tbName);
			log.debug("***************XML内容获取Excel预执行配置文件**********");
			if(null!=arr&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					log.debug(arr[i]);
				}
			}
		}
		return arr;		
		
		
//********************************************这里备份原始配置信息*************************************
//		String[] arr=null;
//		if("plaMupstransportdet".equals(tbName))//TB_ZBC_PLA_MUPSTRANSPORTDET
//		{	
//			arr=new String[1];
//			arr[0]="initPlanDicDataReceiver:2,12";
//		}
//		return arr;
	}
	/**
	 * XML：获取插入数据之前处理的事件（清理配置数据)
	 * @param tbName
	 * @return
	 */
	public String[] getTBBefInsertCfg(final String tbName){
		if(log.isDebugEnabled()){
			log.debug("****************************************************进入读取XML**********");
		}
		String[] arr=null;			
		ServletContext context = ServletActionContext.getServletContext();
		String destDir = CommUtils.getString("/","pla","/","xml", "/");
		String destFile = CommUtils.getString(destDir ,"pla-imp-config.xml");
		String destRealDir = context.getRealPath(destDir);
		File dir=new File(destRealDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String destRealFile = context.getRealPath(destFile);	
		File file=new File(destRealFile); 
        SAXReader reader=new SAXReader(); 
        try { 
                Document doc=reader.read(file); 
                Element root=doc.getRootElement(); 
                Node tableNode = root.selectSingleNode("table[@name='"+tbName+"']");
                if(null!=tableNode){
	                List list = tableNode.selectNodes("befInserts/befInsert");
	                if(null!=list&&list.size()>0){
		                arr=new String[list.size()];
		                for (int i = 0; i < list.size(); i++) {
		                	Element e = (Element) list.get(i);
		                	//String attr=e.attributeValue("id");                	
							arr[i]=e.getText();
						} 
	                }
                }

        } catch (DocumentException e) { 
            //e.printStackTrace(); 
            arr=null;
            if(log.isDebugEnabled()){
    			log.debug("***************从XML中获取配置是出现错误**********");
    			log.debug("table标签name为："+tbName);
    			log.debug("异常："+e.getMessage());
            }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel配置名称**********");
			log.debug(tbName);
			log.debug("***************XML内容获取Excel预执行配置文件**********");
			if(null!=arr&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					log.debug(arr[i]);
				}
			}
		}
		return arr;		

	}
	/**
	 * XML：获取导入数据之后处理的事件（整理数据)
	 * @param tbName
	 * @return
	 */
	public String[] getTBAftImpCfg(final String tbName){
		if(log.isDebugEnabled()){
			log.debug("****************************************************进入读取XML**********");
		}
		String[] arr=null;			
		ServletContext context = ServletActionContext.getServletContext();
		String destDir = CommUtils.getString("/","pla","/","xml", "/");
		String destFile = CommUtils.getString(destDir ,"pla-imp-config.xml");
		String destRealDir = context.getRealPath(destDir);
		File dir=new File(destRealDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String destRealFile = context.getRealPath(destFile);	
		File file=new File(destRealFile); 
        SAXReader reader=new SAXReader(); 
        try { 
                Document doc=reader.read(file); 
                Element root=doc.getRootElement(); 
                Node tableNode = root.selectSingleNode("table[@name='"+tbName+"']");
                if(null!=tableNode){
	                List list = tableNode.selectNodes("complates/complate");
	                if(null!=list&&list.size()>0){
		                arr=new String[list.size()];
		                for (int i = 0; i < list.size(); i++) {
		                	Element e = (Element) list.get(i);
		                	//String attr=e.attributeValue("id");                	
							arr[i]=e.getText();
						} 
	                }
                }

        } catch (DocumentException e) { 
            //e.printStackTrace(); 
            arr=null;
            if(log.isDebugEnabled()){
    			log.debug("***************从XML中获取配置是出现错误**********");
    			log.debug("table标签name为："+tbName);
    			log.debug("异常："+e.getMessage());
            }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel配置名称**********");
			log.debug(tbName);
			log.debug("***************XML内容获取Excel预执行配置文件**********");
			if(null!=arr&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					log.debug(arr[i]);
				}
			}
		}
		return arr;		

	}
	/**
	 * XML：获取导入数据之后返回页面
	 * @param tbName
	 * @return
	 */
	public String getTBImpRtnPage(final String tbName){
		if(log.isDebugEnabled()){
			log.debug("****************************************************进入读取XML**********");
		}
		String returnPage="";			
		ServletContext context = ServletActionContext.getServletContext();
		String destDir = CommUtils.getString("/","pla","/","xml", "/");
		String destFile = CommUtils.getString(destDir ,"pla-imp-config.xml");
		String destRealDir = context.getRealPath(destDir);
		File dir=new File(destRealDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String destRealFile = context.getRealPath(destFile);	
		File file=new File(destRealFile); 
        SAXReader reader=new SAXReader(); 
        try { 
                Document doc=reader.read(file); 
                Element root=doc.getRootElement(); 
                Node tableNode = root.selectSingleNode("table[@name='"+tbName+"']");
                if(null!=tableNode){
                	Element e = (Element) tableNode;
                	returnPage=e.attributeValue("returnPage");
                }

        } catch (DocumentException e) { 
            //e.printStackTrace(); 
        	returnPage="";
            if(log.isDebugEnabled()){
    			log.debug("***************从XML中获取配置是出现错误**********");
    			log.debug("table标签name为："+tbName);
    			log.debug("异常："+e.getMessage());
            }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel配置名称**********");
			log.debug(tbName);
			log.debug("***************XML内容获取Excel预执行配置文件returnPage**********");
			log.debug(returnPage);
		}
		return returnPage;		
	}
	/**
	 * XML：获取企业自营 公司列表，从XML
	 * @return
	 */
	public String[] getCorpFromXML(){
		String[] arr=null;			
		//先去XML中查找
		if(log.isDebugEnabled()){
			log.debug("****************************************************进入读取YUPRTATSM.XML**********");
		}
		ServletContext context = ServletActionContext.getServletContext();
		String destDir = CommUtils.getString("/","pla","/","xml", "/");
		String destFile = CommUtils.getString(destDir ,"YUPRTATSM.xml");
		String destRealDir = context.getRealPath(destDir);
		File dir=new File(destRealDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String destRealFile = context.getRealPath(destFile);	
		File file=new File(destRealFile); 
        SAXReader reader=new SAXReader(); 
        try { 
            Document doc=reader.read(file); 
            Element root=doc.getRootElement(); 
            List list = root.selectNodes("item");
            if(null!=list&&list.size()>0){
            	arr=new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                	Element e = (Element) list.get(i);         	
					arr[i]=e.attributeValue("id")+"###"+e.getText();
				} 
            }
	    } catch (DocumentException e) { 
	            //e.printStackTrace(); 
	            if(log.isDebugEnabled()){
	    			log.debug("***************从YUPRTATSM.XML中获取配置是出现错误**********");
	    			log.debug("异常："+e.getMessage());
	            }
	    } 
		return arr;
	}

}
