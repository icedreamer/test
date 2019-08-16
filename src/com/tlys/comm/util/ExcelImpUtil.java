package com.tlys.comm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCarkind;
import com.tlys.dic.model.DicCustomer;
import com.tlys.dic.model.DicGoods;
import com.tlys.dic.model.DicProdcategory;
import com.tlys.dic.model.DicProdsecondkind;
import com.tlys.dic.model.DicProduct;
import com.tlys.dic.model.DicProvince;
import com.tlys.dic.model.DicRwstation;
import com.tlys.dic.model.DicSalecorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicTransporter;
import com.tlys.dic.model.ctl.CtlCorpreceiver;
import com.tlys.dic.model.ctl.CtlCorpsender;
import com.tlys.dic.model.ctl.CtlCustomerreceiver;
/**
 * @author sunshanh
 * 
 */
@Component
public class ExcelImpUtil {//extends GenericsUtils {//HibernateDaoSupport
	// DBCnn dbCnn = new DBCnn();
	@Autowired
	DicMap dicMap;
	
	protected final Log log = LogFactory.getLog(this.getClass());
	private final int ExcelDataStartRowNO=2;//Excel数据开始行0为第一行
	public ExcelImpUtil() {
	}
	public void main(String[] args) {
		// ExcelImpUtil cu=new ExcelImpUtil();
		// cu.doInputData("d:\\employee.xls");
	}

	/**
	 * 获取Excel行数
	 * @param destUrlFile
	 * @return
	 * @throws Exception
	 */
	public int getExcelRowsCount(final String destUrlFile) throws Exception{
		ServletContext context = ServletActionContext.getServletContext();
		//上传目标的物理路径
		String destRealFile = context.getRealPath(destUrlFile);
		FileInputStream _input = null;
		Workbook workbook = null; // Excel对象
		Sheet sheet = null; // 表对象
		Row row = null; // 行对象
		Cell cell = null; // 单元格对象
		
		String[] fileNameArr = destUrlFile.split("\\.");			
		if(fileNameArr.length>1)
		{
			String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
			if ("XLS".equals(fileExtName)) {
				try {
					// 数据读取
					 _input=new FileInputStream(destRealFile);
					 workbook = new HSSFWorkbook(_input);
				} catch (Exception e) {
					throw e;
				}
			}else if("XLSX".equals(fileExtName)) {
				try {
					// 数据读取
					 _input=new FileInputStream(destRealFile);
					 workbook = new XSSFWorkbook(_input);
				} catch (Exception e) {
					throw e;
				}
			}else{
				//文件类型错误
				return -1;
			}
		}else{
			//文件路径错误
			return -1;
		}

		if (null != _input) {
			_input.close();
		}
		sheet = workbook.getSheetAt(0);
		//获得开始行号
		int rsRowsStart=ExcelDataStartRowNO;
		int rsRowsEnd=sheet.getLastRowNum();
		// 获得数据行数
		int rsRows=rsRowsEnd-rsRowsStart+1;				
		
		if(log.isDebugEnabled())
		{
			log.debug("################################开始行（逻辑)："+rsRowsStart);
			log.debug("################################结束行（逻辑)："+rsRowsEnd);
			log.debug("################################行数："+rsRows);
		}
		return rsRows;
	}
	
	/**
	 * 获取EXcel数据
	 * @param destUrlFile
	 * @return
	 * @throws Exception
	 */
	public List getExcelData(final String destUrlFile) throws Exception{
		List res = new ArrayList();
		try {
			ServletContext context = ServletActionContext.getServletContext();
			//上传目标的物理路径
			String destRealFile = context.getRealPath(destUrlFile);
			FileInputStream _input = null;
			Workbook workbook = null; // Excel对象
			Sheet sheet = null; // 表对象
			Row row = null; // 行对象
			Cell cell = null; // 单元格对象
			
			String[] fileNameArr = destUrlFile.split("\\.");			
			if(fileNameArr.length>1)
			{
				String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
				if ("XLS".equals(fileExtName)) {
					try {
						// 数据读取
						 _input=new FileInputStream(destRealFile);
						 workbook = new HSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else if("XLSX".equals(fileExtName)) {
					try {
						// 数据读取
						 _input=new FileInputStream(destRealFile);
						 workbook = new XSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else{
					//文件类型错误
					return res;
				}
			}else{
				//文件路径错误
				return res;
			}

			if (null != _input) {
				_input.close();
			}
			sheet = workbook.getSheetAt(0);
			//获得开始行号
			int rsRowsStart=ExcelDataStartRowNO;
			int rsRowsEnd=sheet.getLastRowNum();
			// 获得数据行数
			int rsRows=rsRowsEnd-rsRowsStart+1;				
			
			if(log.isDebugEnabled())
			{
				log.debug("################################开始行（逻辑)："+rsRowsStart);
				log.debug("################################结束行（逻辑)："+rsRowsEnd);
				log.debug("################################行数："+rsRows);
			}
			
			if (rsRows>0){
				int rsCols = sheet.getRow(rsRowsStart).getPhysicalNumberOfCells();
				for (int i = rsRowsStart; i <= rsRowsEnd; i++) {
					 row = sheet.getRow(i);
					if(null==row||isNullRow(row)){
						if(log.isDebugEnabled())
						{
							log.debug("################################需要删除的行号："+i);
							log.debug("################################需要删除结束行号："+rsRowsEnd);									
						}
						if(i+1<rsRowsEnd)
						{
							//物理删除行
							sheet.shiftRows(i+1, rsRowsEnd, -1);
						}else{
							//删除但保留行（即清空)
							if(null!=row){
								sheet.removeRow(row);
							}
						}						
						continue;
					}
					//int rsCols=row.getPhysicalNumberOfCells();
					String[] rowData=new String[rsCols];
					for (int j = 0; j < rsCols; j++) {
						// 获取CELL中的数据
						 cell = row.getCell(j);
						String cellDataObj = cell==null?"":cell.toString().trim();
						rowData[j]=cellDataObj;
						
						if(cell!=null){
							cell.setCellValue(cellDataObj);
						}
					}
					res.add(rowData);
				}
			}
			
			//由于中间过滤过空行等操作，故保存一下
			FileOutputStream _output=new FileOutputStream(destRealFile);
			workbook.write(_output);
			_output.close();
			
		}catch(Exception e){
			throw e;
		}
		return res;
	}
	/**
	 * 获取EXCEL数据(EXCEL定制)-数据验证包含：是否为空、字段长度、字段类型、字典匹配
	 * 
	 * @param filename
	 *            文件名
	 * @param obj
	 *            POJO类名（数据表名)
	 * @param columns
	 *            属性名（数据字段名)对应EXCEL表头中文
	 * @param nullables
	 *            属性（数据字段)是否可为空
	 * @throws Exception<T>
	 */
	public Map checkExcelData(final String destUrlFile, final Object obj,final String[] columns,final String currYear,final String currMth,final String currArea,final String currCorp) throws Exception {
		Map map = new HashMap();
		List dataTableInfoList = new ArrayList();// 保存错误信息（1|||1|||222|||数据类型0*(或错误类型1*)|||数据错误说明)（第一行/第一列/数据/数据状态/数据错误说明)
		boolean isUpdate = true;// 是否可以提交
		
		ServletContext context = ServletActionContext.getServletContext();
		Workbook workbook = null; // Excel对象
		Sheet sheet = null; // 表对象
		Row row = null; // 行对象
		Cell cell = null; // 单元格对象
		
		//上传目标的物理路径
		String destRealFile = context.getRealPath(destUrlFile);	
		FileInputStream _input = null;
		
		String[] fileNameArr = destUrlFile.split("\\.");			
		if(fileNameArr.length>1)
		{
			String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
			if ("XLS".equals(fileExtName)) {
				try {
					// 数据读取
					 _input=new FileInputStream(destRealFile);
					 workbook = new HSSFWorkbook(_input);
				} catch (Exception e) {
					throw e;
				}
			}else if("XLSX".equals(fileExtName)) {
				try {
					// 数据读取
					 _input=new FileInputStream(destRealFile);
					 workbook = new XSSFWorkbook(_input);
				} catch (Exception e) {
					throw e;
				}
			}else{
				//文件类型错误
				return map;
			}
		}else{
			//文件路径错误
			return map;
		}
		
		
		try {
			
//			// 数据读取
//			FileInputStream _input=new FileInputStream(destRealFile);
//			HSSFWorkbook workbook = new HSSFWorkbook(_input);
			if (null != _input) {
				_input.close();				
			}
			sheet = workbook.getSheetAt(0);

			//获得开始行号
			int rsRowsStart=ExcelDataStartRowNO;
			int rsRowsEnd=sheet.getLastRowNum();
			// 获得数据行数
			int rsRows=rsRowsEnd-rsRowsStart+1;
			
			if(log.isDebugEnabled())
			{
				log.debug("################################开始行（逻辑)："+rsRowsStart);
				log.debug("################################结束行（逻辑)："+rsRowsEnd);
				log.debug("################################行数："+rsRows);
			}
			if (rsRows>0){
				//int rsCols = sheet.getRow(rsRowsStart).getPhysicalNumberOfCells();//此行代码会导致错误，注释掉，同时开启下面的信息				
				int rsCols = columns.length;
				for (int i = rsRowsStart; i <= rsRowsEnd; i++) {
					List dataRowInfoList = new ArrayList();//保存当前行数据信息
					row = sheet.getRow(i);
					if(row == null||isNullRow(row)){
						if(log.isDebugEnabled())
						{
							log.debug("################################需要删除的行号："+i);
							log.debug("################################剩余部分结束行号："+rsRowsEnd);									
						}
						if(i+1<rsRowsEnd)
						{
							//物理删除行
							sheet.shiftRows(i+1, rsRowsEnd, -1);
						}else{
							//删除但保留行（即清空)
							if(null!=row){
								sheet.removeRow(row);
							}
						}	
						continue;
					}
					// Map fdMap = new HashMap();//返回错误数据行列				
					for (int j = 0; j < columns.length; j++) {
						String dataCellInfo="############";//保存当前单元格数据信息
						String cellDataStatus="00";//数据状态：0*无错误、1*有错误
						String cellDataErrorMsg="正确数据";//数据错误信息明细
						// 获取CELL中的数据
						cell = row.getCell(j);
						String cellDataObj = cell==null?"":cell.toString().trim();
						if(cell!=null){
							cell.setCellValue(cellDataObj);
						}
						
						
	//				    String form = cell.getCellStyle().getDataFormatString();        
	//				    DecimalFormat format = new DecimalFormat(form);    
	//					switch(cell.getCellType())	
	//					{
	//						case HSSFCell.CELL_TYPE_FORMULA: //首先判断一下是不是公式
	//							 cell.setCellType(cell.CELL_TYPE_NUMERIC); //设置其单元格类型为数字  
	//							 cellDataObj = format.format(cell.getNumericCellValue());
	//							 break; 
	//						case HSSFCell.CELL_TYPE_NUMERIC:							
	//						    cellDataObj = format.format(cell.getNumericCellValue());
	//						    break;
	//						case HSSFCell.CELL_TYPE_STRING:
	//						    cellDataObj = cell.getStringCellValue();
	//						    break;
	//						default:
	//							cellDataObj = cell.toString();
	//							break;
	//					}
					
	//					if(log.isDebugEnabled())
	//					{
	//						log.debug("cellDataObj:"+cellDataObj);
	//					}
					
						//String cellDataStr = cell.toString();
						
						// 判断字段
						Class c = obj.getClass();
						// 属性处理
						
						String[] columnInfoArr=columns[j]!=null?columns[j].split("\\###"):null;
						if(null!=columnInfoArr && columnInfoArr.length>=4&&columnInfoArr[0]!="SKIP")
						{
							String columnName=columnInfoArr[0].toLowerCase();
							String columnDesc=columnInfoArr[1];
							String columnFormTagType=columnInfoArr[2];
							String columnDataType=columnInfoArr[3];		
							
							if("".equals(columnName)){//列需要转义
								//////////////////////////////////////////////////////////////////
	
								if("DropDownList".equals(columnFormTagType)){
									String columnDDLConf=columnInfoArr[6];
									String[] columnDDLConfArr=columnDDLConf.split("\\:");
									if(columnDDLConfArr.length>0){
										String columnDDLMethod=columnDDLConfArr[0];
										String columnDDLPara[]=columnDDLConfArr[1].split("\\,");
										String columnDDLInPara=columnDDLConfArr.length>2?columnDDLConfArr[2]:"";
		
										if("getCorp".equals(columnDDLMethod)){
											String[] res=getCorp(cellDataObj);	
											
											
											//测试利用反射		
//											try{
//												Object classObj = new ExcelImpUtil();
//												Class c2=classObj.getClass();
//												Method method = c2.getMethod(columnDDLMethod,new Class[]{String.class});
//												if(null!=cellDataObj&&!"".equals(cellDataObj)){
//													Object rtn = method.invoke(classObj,new Object[]{cellDataObj.toString()});
//													res=(String[])rtn;
//												}
//
//											}catch(InvocationTargetException e){
//												throw e;
//											}
											
											
											if(res==null||res[0]==null){
												cellDataStatus = "31";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getCorpFromDBXML".equals(columnDDLMethod)){
											String[] res=getCorpFromDBXML(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "31";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getArea".equals(columnDDLMethod)){
											String[] res=getArea(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "31";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getCustomer".equals(columnDDLMethod)){
											//if(log.isDebugEnabled()){
											//	log.debug("*************************翻译customer输入****************");
											//	log.debug(cellDataObj);
											//}
											String[] res=getCustomer(cellDataObj);	
											//if(log.isDebugEnabled()){
											//	log.debug("*************************翻译customer输出****************");
											//	log.debug(res[0]);
											//	log.debug(res[1]);
											//}
											if(res==null||res[0]==null){
												cellDataStatus = "32";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getCustomerCorp".equals(columnDDLMethod)){
											String[] res=getCustomerCorp(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "32";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getProduct".equals(columnDDLMethod)){
											String[] res=getProduct(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "33";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getCarKindByKey".equals(columnDDLMethod)){
											String[] res=getCarKindByKey(cellDataObj);	//根据Key测试
											if(res==null||res[0]==null){
												cellDataStatus = "34";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getCarKind".equals(columnDDLMethod)){
											String[] res=getCarKind(cellDataObj);	//根据名称
											if(res==null||res[0]==null){
												cellDataStatus = "34";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getStartStation".equals(columnDDLMethod)){
											String[] res=getStartStation(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "35";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getGoalProvince".equals(columnDDLMethod)){
											String[] res=getGoalProvince(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "36";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getEndStation".equals(columnDDLMethod)){
											String[] res=getEndStation(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "37";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getTransport".equals(columnDDLMethod)){
											String[] res=getTransport(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "38";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getRwKind".equals(columnDDLMethod)){
											String[] res=getRwKind(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "39";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else if("getProductcategory".equals(columnDDLMethod)){
											String[] res=getProductcategory(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "40";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}		
										}else if("getProductsecond".equals(columnDDLMethod)){
											String[] res=getProductsecond(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "41";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}	
										}else if("getSaleDepartment".equals(columnDDLMethod)){
											String[] res=getSaleDepartment(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "42";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}										
										}else if("getSender".equals(columnDDLMethod)){
											if("".equals(columnDDLInPara)){
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！没有提供必要参数，企业！";
											}else{
												String[] columnDDLInParaArr=columnDDLInPara.split("\\,");
												String columnDDLInPara0="";
												if(!"".equals(columnDDLInPara)&&columnDDLInParaArr.length>0){
													columnDDLInPara0=columnDDLInParaArr[0];
													//获取必要参数
													Cell cellTemp = row.getCell(Integer.parseInt(columnDDLInPara0));
													String columnDDLInPara0Data = cellTemp==null?"":cellTemp.toString();
													String[] corpM=getCorp(columnDDLInPara0Data);
													String columnDDLInParaID=(corpM==null||corpM[0]==null)?"":corpM[0];		
													if(log.isDebugEnabled())
													{
													//	log.debug("#######################################获取发货人参数corpID的:"+columnDDLInPara0Data);
													//	log.debug("#######################################获取发货人参数corpID的:"+columnDDLInParaID);
													}
													String[] res=getSender(cellDataObj,columnDDLInParaID);	
													if(res==null||res[0]==null){
														cellDataStatus = "43";
														cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
														isUpdate = false;}	
												}
												
											}
										}else if("getReciver".equals(columnDDLMethod)){
											if("".equals(columnDDLInPara)){
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！没有提供必要参数，企业！";
											}else{
												String[] columnDDLInParaArr=columnDDLInPara.split("\\,");
												String columnDDLInPara0="";
												if(columnDDLInParaArr.length>0){
													columnDDLInPara0=columnDDLInParaArr[0];
												}
												//获取必要参数
												Cell cellTemp = row.getCell(Integer.parseInt(columnDDLInPara0));
												String columnDDLInPara0Data = cellTemp==null?"":cellTemp.toString();
												String[] customerM=getCustomer(columnDDLInPara0Data);
												String columnDDLInParaID=(customerM==null||customerM[0]==null)?"":customerM[0];	
												if(log.isDebugEnabled())
												{
												//	log.debug("#######################################获取收货人参数customsID的:"+columnDDLInPara0Data);
												//	log.debug("#######################################获取收货人参数customsID的:"+columnDDLInParaID);
												}
												String[] res=getReciver(cellDataObj,columnDDLInParaID);	
												if(res==null||res[0]==null){
													cellDataStatus = "44";
													cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
													isUpdate = false;}		
											}
										}else{
											cellDataStatus = "30";
											cellDataErrorMsg="数据转义错误，未找到转义方法！";
											isUpdate = false;
										}									
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="配置表未对DropDownList进行转义配置";
										isUpdate = false;
									}
								}else if("Tree".equals(columnFormTagType)){								
									String columnDDLConf=columnInfoArr[6];
									String[] columnDDLConfArr=columnDDLConf.split("\\:");
									if(columnDDLConfArr.length>0){
										String columnDDLMethod=columnDDLConfArr[0];
										String columnDDLPara[]=columnDDLConfArr[1].split("\\,");
		
										if("getProduct".equals(columnDDLMethod)){
											String[] res=getProduct(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "33";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！";
												isUpdate = false;}
										}else{
											cellDataStatus = "30";
											cellDataErrorMsg="数据转义错误，未找到转义方法！";
											isUpdate = false;
										}
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="配置表未对Tree进行转义配置";
										isUpdate = false;
									}
								}else if("TreeRoot".equals(columnFormTagType)){								
									String columnDDLConf=columnInfoArr[6];
									String[] columnDDLConfArr=columnDDLConf.split("\\:");
									if(columnDDLConfArr.length>0){
										String columnDDLMethod=columnDDLConfArr[0];
										String columnDDLPara[]=columnDDLConfArr[1].split("\\,");
		
										if("getProductcategory".equals(columnDDLMethod)){
											String[] res=getProductcategory(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "40";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！请点击产品进行联动修改";
												isUpdate = false;}		
										}else if("getProductsecond".equals(columnDDLMethod)){
											String[] res=getProductsecond(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "41";
												cellDataErrorMsg=columnDDLMethod+"数据转义错误！请点击产品进行联动修改";
												isUpdate = false;}	
										}else{
											cellDataStatus = "30";
											cellDataErrorMsg="数据转义错误，未找到转义方法！";
											isUpdate = false;
										}	
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="配置表未对TreeRoot进行转义配置";
										isUpdate = false;
									}
								}else if("Variable".equals(columnFormTagType)){		
									String variableMethodStr=columnInfoArr[6];
									String[] variableMethodArr=variableMethodStr.split("\\:");
		
									if(variableMethodArr.length>0){
										String variableMethod=variableMethodArr[0];
										//String variablePara[]=variableMethodArr[1].split("\\,");
										////////////////////////////////////根据配置变量信息，赋值，这里可继续扩展
										if("currMth".equals(variableMethod)){
											cellDataObj=currMth;
										}else if("currYear".equals(variableMethod)){
												cellDataObj=currYear;
										}else if("currArea".equals(variableMethod)){
											cellDataObj=currArea;
										}else{
											cellDataStatus = "30";
											cellDataErrorMsg="系统未配置此变量:"+variableMethod+"！";
											isUpdate = false;
										}
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="配置表未对Tree进行转义配置";
										isUpdate = false;
									}
								}else if("Sequence".equals(columnFormTagType)){		
									String variableMethodStr=columnInfoArr[6];
									String[] variableMethodArr=variableMethodStr.split("\\:");
									if(variableMethodArr.length>0){
										String variableMethod=variableMethodArr[0];
										//String variablePara[]=variableMethodArr[1].split("\\,");
										//如果是序列这里不做处理显示原Excel中内容
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="配置表未对Tree进行转义配置";
										isUpdate = false;
									}
	
								}else{
									cellDataStatus = "93";
									cellDataErrorMsg="配置表类型定义错误！列名为空仅允许Tree、TreeRoot和DropDownList";
									isUpdate = false;
								}
								
								
							}else{//列名正常
								//列名首字母转大写处理
								//String column = CommUtils.getString(columns[i].substring(0,1).toUpperCase(), columns[i].substring(1).toLowerCase());
								String column = CommUtils.getString(columnName.substring(0,1).toUpperCase(), columnName.substring(1).toLowerCase());
								
								//获取get方法名称
								String methodNameOfGet = CommUtils.getString("get", column);
								// get属性方法
								Method methodOfGetView = c.getMethod(methodNameOfGet, null);
								// 属性注解
								Column col = methodOfGetView.getAnnotation(Column.class);
								// 属性长度
								int length = col != null ? col.length() : 0;
								
	//							if(log.isDebugEnabled())
	//							{
	//								log.debug("############################Column Length:"+i+"      "+column+" : "+length);
	//							}
								
								
								// 是否可以为空
								boolean nullable = col != null ? col.nullable() : true;
								// 是否字典(以DIC为)
								// "DIC".endsWith(arg0)
								// 属性类型
								Field field = c.getDeclaredField(columnName);
								String fieldType = field.getType().getSimpleName();
								//获取set方法名称
								String methodNameOfSet = CommUtils.getString("set", column);
								// set属性方法
								Method methodOfSetView = c.getMethod(methodNameOfSet, field.getType());
								
								
								if(!nullable&&"".equals(cellDataObj))
								{
									cellDataStatus = "91";
									cellDataErrorMsg="数据为空错误:该单元格不允许为空\n:";
									isUpdate = false;
								}else if(cellDataObj.toString().length()>length){
									cellDataStatus = "92";
									cellDataErrorMsg="数据长度超限错误:该单元格数据过长\n:";
									isUpdate = false;
								}else{
									// 根据类型添加值
									//之前老的方法，暂时保留
			//						if ("Date".equals(fieldType)) {
			//							try {
			//								methodOfSetView.invoke(obj, (Date)cellDataObj);
			//							} catch (Exception e) {
			//								cellDataStatus = 3;
			//								cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n"+e.toString();
			//								isUpdate = false;
			//							}
									
									
									if ("Date".equals(fieldType)) {
										cellDataStatus = "03";
										try {
											if(!isYearMth(cellDataObj))
											{
												cellDataStatus = "13";
												cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n";
												isUpdate = false;
											}
										} catch (Exception e) {
											cellDataStatus = "13";
											cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n"+e.toString();
											isUpdate = false;
										}
									} else if ("Long".equals(fieldType)) {
										cellDataStatus = "04";
										try {
											if(!isLong(cellDataObj))
											{
												cellDataStatus = "14";
												cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n";
												isUpdate = false;
											}else{//格式化一下整数
												String[] arr=cellDataObj.split("\\.");
												if (arr.length>0)
												{
													cellDataObj=arr[0];
												}
											}
										} catch (Exception e) {
											cellDataStatus = "14";
											cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n"+e.toString();
											isUpdate = false;
										}
									} else if ("Integer".equals(fieldType)) {
										cellDataStatus = "05";
										try {
											if(!isInteger(cellDataObj))
											{
												cellDataStatus = "15";
												cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n";
												isUpdate = false;
											}else{//格式化一下整数
												String[] arr=cellDataObj.split("\\.");
												if (arr.length>0)
												{
													cellDataObj=arr[0];
												}
											}
										} catch (Exception e) {
											cellDataStatus = "15";
											cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n"+e.toString();
											isUpdate = false;
										}
									} else if ("Float".equals(fieldType)) {
										cellDataStatus = "06";
										try {
											if(!isFloat(cellDataObj))
											{
												cellDataStatus = "16";
												cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n";
												isUpdate = false;
											}
										} catch (Exception e) {
											cellDataStatus = "16";
											cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n"+e.toString();
											isUpdate = false;
										}
									} else if ("Double".equals(fieldType)) {
										cellDataStatus = "07";
										try {
											if(!isDouble(cellDataObj))
											{
												cellDataStatus = "17";
												cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n";
												isUpdate = false;
											}
										} catch (Exception e) {
											cellDataStatus = "17";
											cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n"+e.toString();
											isUpdate = false;
										}
									}  else {
										cellDataStatus = "08";
										try {
											methodOfSetView.invoke(obj, cellDataObj);								
										} catch (Exception e) {
											cellDataStatus = "18";
											cellDataErrorMsg="数据类型错误:该值不是正确的"+fieldType+"类型\n"+e.toString();
											isUpdate = false;
										}						
									}
								}
							}
						}else if(null!=columnInfoArr && columnInfoArr.length<4 &&columnInfoArr[0]=="SKIP"){
							cellDataStatus = "02";
							cellDataErrorMsg="跳过该列，不导入";
						}else{
							cellDataStatus = "90";
							cellDataErrorMsg="表静态列配置文件错误！";
						}
						
						dataCellInfo=i+"###"+j+"###"+cellDataObj+"###"+cellDataStatus+"###"+cellDataErrorMsg;
						dataRowInfoList.add(dataCellInfo);
					}
					dataTableInfoList.add(dataRowInfoList);
				} 
			}
			//由于中间过滤过空行等操作，故保存一下
			FileOutputStream _output=new FileOutputStream(destRealFile);
			workbook.write(_output);
			_output.close();
			
			map.put("List", dataTableInfoList);
			map.put("CommitStatus", isUpdate);
			
		} catch (Exception e) {
			throw e;
		}
		
		return map;
	}

	/**
	 * 保存Excel单元格修改
	 * @param currExcelRowNum
	 * @param currExcelColNum
	 * @param currExcelValue
	 * @param destUrlFile
	 * @return
	 * @throws Exception
	 */
	public boolean saveExcelCellEdit(final String currExcelRowNum,final String currExcelColNum,final String currExcelValue,final String destUrlFile) throws Exception
	{
		boolean result=false;
		ServletContext context = ServletActionContext.getServletContext();
		try {
			//上传目标的物理路径
			String destRealFile = context.getRealPath(destUrlFile);
			Workbook workbook = null;
			Sheet sheet = null;
			Row row = null;
			Cell cell = null;
			FileInputStream _input= null;
			
			String[] fileNameArr = destUrlFile.split("\\.");			
			if(fileNameArr.length>1)
			{
				String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
				if ("XLS".equals(fileExtName)) {
					try {
						// 数据读取
						 _input=new FileInputStream(destRealFile);
						 workbook = new HSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else if("XLSX".equals(fileExtName)) {
					try {
						// 数据读取
						 _input=new FileInputStream(destRealFile);
						 workbook = new XSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else{
					//文件类型错误
					return result;
				}
			}else{
				//文件路径错误
				return result;
			}
			
			
			
			if (null != _input) {
				_input.close();				
			}
			sheet = workbook.getSheetAt(0);
			 row = sheet.getRow(Integer.parseInt(currExcelRowNum));
			 cell=row.getCell(Integer.parseInt(currExcelColNum));
			cell.setCellValue(currExcelValue);
			FileOutputStream _output=new FileOutputStream(destRealFile);
			workbook.write(_output);
			_output.close();
			result=true;
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}
	/**
	 * 删除Excel行
	 * @param currExcelRowNum
	 * @param destUrlFile
	 * @return
	 * @throws Exception
	 */
	public boolean deleteExcelRow(final String currExcelRowNum,final String destUrlFile) throws Exception{
		boolean result=false;
		ServletContext context = ServletActionContext.getServletContext();
		try {
			int currRowNum=Integer.parseInt(currExcelRowNum);
			//上传目标的物理路径
			String destRealFile = context.getRealPath(destUrlFile);	
			FileInputStream _input = null;
			Workbook workbook = null;
			Sheet sheet = null;
			Row row = null;
			Cell cell = null;
			
			String[] fileNameArr = destUrlFile.split("\\.");			
			if(fileNameArr.length>1)
			{
				String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
				if ("XLS".equals(fileExtName)) {
					try {
						// 数据读取
						 _input=new FileInputStream(destRealFile);
						 workbook = new HSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else if("XLSX".equals(fileExtName)) {
					try {
						// 数据读取
						 _input=new FileInputStream(destRealFile);
						 workbook = new XSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else{
					//文件类型错误
					return result;
				}
			}else{
				//文件路径错误
				return result;
			}
			
			
			
			if (null != _input) {
				_input.close();				
			}
			 sheet = workbook.getSheetAt(0);
			//获得开始行号
			int rsRowsStart=ExcelDataStartRowNO;
			int rsRowsEnd=sheet.getLastRowNum();
			// 获得数据行数
			int rsRows=rsRowsEnd-rsRowsStart+1;
			
		
			if(log.isDebugEnabled())
			{
				log.debug("################################开始行（逻辑)："+rsRowsStart);
				log.debug("################################结束行（逻辑)："+rsRowsEnd);
				log.debug("################################行数："+rsRows);
			}
			
			if(log.isDebugEnabled())
			{
				log.debug("################################需要删除的行："+currRowNum);
				log.debug("################################剩余部分结束行号："+rsRowsEnd);								
			}
			
			if(currRowNum+1<rsRowsEnd)
			{
				//物理删除行
				sheet.shiftRows(currRowNum+1, rsRowsEnd, -1);//删除当前行,用后面的上移覆盖当前行
				//sheet.shiftRows(startRow,endRow,shiftCount)参数说明:
				//startRow 移动的开始行号,从0开始计算, 如果想要删除Excel中的第8行(rownum=7),那么starRow设定为7,即设定为你想删除的那行
				//endRow 移动的结束行号，从0开始计算。通过startRow和endRow选定移动的范围。可以用sheet.getLastRowNum 来取得sheet的结尾行号
				//shiftCount 移动多少行。正数是往下移动的行数，负数是往上移动的行数
			}else{
				//删除但保留行（即清空)
				 row = sheet.getRow(Integer.parseInt(currExcelRowNum));
				if(null!=row){
					sheet.removeRow(row);
				}
			}
			FileOutputStream _output=new FileOutputStream(destRealFile);
			workbook.write(_output);
			_output.close();
			result=true;
		} catch (Exception e) {
			throw e;
		}
		
		return result;
	}

	/**
	 * 判断Excel行是否空
	 * @param row
	 * @return
	 */
	public boolean isNullRow(final Row row){
		boolean result=true;
		int colCnt = row.getPhysicalNumberOfCells();
		for (int i = 0; i < colCnt; i++) {
			Cell cell=row.getCell(i);
			if(null!=cell){
				result=false;	
				break;
			}
		}			
		return result;
	}
	/**
	 * 删除Excel文件
	 * @param destUrlFile
	 * @return
	 * @throws Exception
	 */
	public boolean deleteExcelFile(final String destUrlFile) throws Exception
	{
		boolean result=false;
		try{
			ServletContext context = ServletActionContext.getServletContext();
			//上传目标的物理路径
			String destRealPath = context.getRealPath(destUrlFile);	
			//判断文件是否存在，如果存在，则删除之
			File file = new File(destRealPath);
			if (file.exists()) {
				file.delete();
			}
			result=true;
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	

	/**
	 * 生成Excel导入数据库Insert语句
	 * @param destUrlFile
	 * @param tbOracleName
	 * @param columns
	 * @return
	 * @throws Exception
	 */
	public String[] getImportInsertSql(final String destUrlFile, final String tbOracleName,final String[] columns,final String currYear,final String currMth,final String currArea,final String currCorp,final String planNo) throws Exception
	{
		String[] result=null;
		ServletContext context = ServletActionContext.getServletContext();
		try {
			//上传目标的物理路径
			String destRealFile = context.getRealPath(destUrlFile);	
			// 数据读取
			FileInputStream _input=null;
			Workbook workbook = null;
			Sheet sheet = null;
			Row row = null;
			Cell cell = null;
		
			
			String[] fileNameArr = destUrlFile.split("\\.");			
			if(fileNameArr.length>1)
			{
				String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
				if ("XLS".equals(fileExtName)) {
					try {
						// 数据读取
						 _input=new FileInputStream(destRealFile);
						 workbook = new HSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else if("XLSX".equals(fileExtName)) {
					try {
						// 数据读取
						 _input=new FileInputStream(destRealFile);
						 workbook = new XSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else{
					//文件类型错误
					return result;
				}
			}else{
				//文件路径错误
				return result;
			}
			
			
			if (null != _input) {
				_input.close();				
			}
			sheet = workbook.getSheetAt(0);
		

			//获得开始行号
			int rsRowsStart=sheet.getFirstRowNum()+ExcelDataStartRowNO;
			int rsRowsEnd=sheet.getLastRowNum();
			// 获得数据行数
			int rsRows=rsRowsEnd-rsRowsStart+1;

			if(log.isDebugEnabled())
			{
				log.debug("############################rsRowsStart:"+(rsRowsStart));
				log.debug("############################rsRowsEnd:"+(rsRowsEnd));
				log.debug("############################rsRowsEnd-rsRowsStart+1:"+(rsRowsEnd-rsRowsStart+1));
				log.debug("############################sheet.getLastRowNum() - sheet.getFirstRowNum() + 1:"+(sheet.getLastRowNum() - sheet.getFirstRowNum() + 1));
				log.debug("############################sheet.getPhysicalNumberOfRows()-1:"+(sheet.getPhysicalNumberOfRows()-1));
			}
			
			
			ArrayList trList=new ArrayList();
			String[] tdList;			
			for(int i=rsRowsStart;i<=rsRowsEnd;i++)
			{
				 row = sheet.getRow(i);
				if(row == null||isNullRow(row)){
				//	sheet.shiftRows(i, i, -1);//删除当前行
					continue;
				}				
				//获得数据列数
				//int colsCount = sheet.getRow(i).getPhysicalNumberOfCells();//如果excel中某行列多了就会出错，该为下面代码
				int colsCount = columns.length;
				
				if(log.isDebugEnabled())
				{
					log.debug("########################tdList#####第"+i+"行列数:"+colsCount);
				}
					
					
				tdList=new String[colsCount];				
			
				for(int j=0;j<colsCount;j++)
				{					
					 cell=row.getCell(j);
					tdList[j]=cell==null?"":cell.toString().trim();
				}
				trList.add(tdList);
			}
			
			
			Object[] dataArr=trList.toArray();
			
			
			
			//整理列信息，处理扩展字段
			int dataSize=trList.size();
			int dataColSize=30;
			String[] colList2=new String[dataColSize];
			String[] colType2=new String[dataColSize];
			String[][] rowList2=new String[dataSize][dataColSize];
			int currCol2=0;
			for(int i=0;i<columns.length;i++){
				String[] columnInfoArr=columns[i].split("\\###");
				if(null!=columnInfoArr && columnInfoArr.length>=4)
				{
					String columnName=columnInfoArr[0];
					String columnDesc=columnInfoArr[1];
					String columnFormTagType=columnInfoArr[2];
					String columnDataType=columnInfoArr[3];					
					if("DropDownList".equals(columnFormTagType)){						
						if(log.isDebugEnabled())
						{
							//log.debug("############################columnInfoArr:"+columnInfoArr.length);
						}
						String columnDDLConf=columnInfoArr[6];						
						if(log.isDebugEnabled())
						{
							//log.debug("############################columnDDLConf:"+columnDDLConf);
						}						
						String[] columnDDLConfArr=columnDDLConf.split("\\:");						
						if(log.isDebugEnabled())
						{
							//log.debug("############################columnDDLConfArr:"+columnDDLConfArr.length);
						}						
						if(columnDDLConfArr.length>0){
							String columnDDLMethod=columnDDLConfArr[0];
							String columnDDLPara[]=columnDDLConfArr[1].split("\\,");	
							String columnDDLInPara=columnDDLConfArr.length>2?columnDDLConfArr[2]:"";
							
							if(log.isDebugEnabled())
							{
								//log.debug("############################columnDDLPara:"+columnDDLPara.length);
							}							
							//GetCorp,GetCustomer,GetProduct,GetCarKind,GetStartStation,GetGoalProvince,GetEndStation,GetTransport
							if("getCorp".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]="String";																
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getCorp(vv);

									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res.length>0?res[k]:"";
										if("PLANNO".equals(columnDDLPara[k].toUpperCase())){
											//rowList2[j][currCol2+k]=res[2]+"-"+currMth;//res[2]存的是AreaID
											rowList2[j][currCol2+k]=planNo;
										}
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getCorpFromDBXML".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;															
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getCorpFromDBXML(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getArea".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;															
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getArea(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getCustomer".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;															
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									if(log.isDebugEnabled()){
										log.debug("*************************翻译customer输入****************");
										log.debug(vv);
									}
									String[] res=getCustomer(vv);
									if(log.isDebugEnabled()){
										log.debug("*************************翻译customer输出****************");
										log.debug(res[0]);
										log.debug(res[1]);
									}
									
									
									
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getCustomerCorp".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;															
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getCustomerCorp(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getProduct".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getProduct(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getCarKindByKey".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									//String[] res=getCarKind(vv);	//根据名称
									String[] res=getCarKindByKey(vv);	//根据Key测试
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getCarKind".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getCarKind(vv);	//根据名称
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getStartStation".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getStartStation(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getGoalProvince".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getGoalProvince(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getEndStation".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getEndStation(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getTransport".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getTransport(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getRwKind".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getRwKind(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getProductcategory".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									
									String[] res=getProductcategory(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
									
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getProductsecond".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getProductsecond(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getSaleDepartment".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getSaleDepartment(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;							
								
							}else if("getSender".equals(columnDDLMethod)){
								if("".equals(columnDDLInPara)){
									//没有参数，错误
								}else{
									String[] columnDDLInParaArr=columnDDLInPara.split("\\,");
									String columnDDLInPara0="";
									if(columnDDLInParaArr.length>0){
										columnDDLInPara0=columnDDLInParaArr[0];
									}
									//处理列名扩展
									for (int j = 0; j < columnDDLPara.length; j++) {
										colList2[currCol2+j]=columnDDLPara[j];
										colType2[currCol2+j]=columnDataType;								
									}
									//处理数据翻译并写入新列
									for (int j = 0; j < dataSize; j++) {
										String[] oo=(String[])dataArr[j];
										
										
										//获取必要参数
										String cellTemp = oo[Integer.parseInt(columnDDLInPara0)];
										String columnDDLInPara0Data = cellTemp==null?"":cellTemp.toString();
										String[] corpM=getCorp(columnDDLInPara0Data);
										String columnDDLInParaID=(corpM==null||corpM[0]==null)?"":corpM[0];		
									
										String vv=oo[i];
										//处理扩展方法，获取扩展数据
										String[] res=getSender(vv,columnDDLInParaID);
										//依次赋值
										for (int k = 0; k < columnDDLPara.length; k++) {
											rowList2[j][currCol2+k]=res[k];
										}
									}
									//更新当前列
									currCol2=currCol2+columnDDLPara.length;	
								}

							}else if("getReciver".equals(columnDDLMethod)){
								if("".equals(columnDDLInPara)){
									//没有参数，错误
								}else{
									String[] columnDDLInParaArr=columnDDLInPara.split("\\,");
									String columnDDLInPara0="";
									if(columnDDLInParaArr.length>0){
										columnDDLInPara0=columnDDLInParaArr[0];
									}
									//处理列名扩展
									for (int j = 0; j < columnDDLPara.length; j++) {
										colList2[currCol2+j]=columnDDLPara[j];
										colType2[currCol2+j]=columnDataType;								
									}
									//处理数据翻译并写入新列
									for (int j = 0; j < dataSize; j++) {
										String[] oo=(String[])dataArr[j];
										
										//获取必要参数
										String cellTemp = oo[Integer.parseInt(columnDDLInPara0)];
										String columnDDLInPara0Data = cellTemp==null?"":cellTemp.toString();
										String[] customerM=getCustomer(columnDDLInPara0Data);
										String columnDDLInParaID=(customerM==null||customerM[0]==null)?"":customerM[0];	
										
										String vv=oo[i];
										//处理扩展方法，获取扩展数据
										String[] res=getReciver(vv,columnDDLInParaID);
										//依次赋值
										for (int k = 0; k < columnDDLPara.length; k++) {
											rowList2[j][currCol2+k]=res[k];
										}
									}
									//更新当前列
									currCol2=currCol2+columnDDLPara.length;	
								}
								

							}else{
								
							}

						}
					}else if("Tree".equals(columnFormTagType)){
						//处理树形导入
						String columnDDLConf=columnInfoArr[6];						
						String[] columnDDLConfArr=columnDDLConf.split("\\:");						
						if(columnDDLConfArr.length>0){
							String columnDDLMethod=columnDDLConfArr[0];
							String columnDDLPara[]=columnDDLConfArr[1].split("\\,");							
							if("getProduct".equals(columnDDLMethod)){
								//处理列名扩展
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//处理数据翻译并写入新列
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//处理扩展方法，获取扩展数据
									String[] res=getProduct(vv);
									//依次赋值
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//更新当前列
								currCol2=currCol2+columnDDLPara.length;		
							}else{
								
							}
						}
					}else if("Variable".equals(columnFormTagType)){
						//处理变量
						String variableMethodStr=columnInfoArr[6];
						String[] variableMethodArr=variableMethodStr.split("\\:");
						if(variableMethodArr.length>0){
							String variableMethod=variableMethodArr[0];
							String[] variablePara=variableMethodArr[1].split("\\,");
							if(variablePara.length>0){
								for (int m = 0; m < variablePara.length; m++) {
									colList2[currCol2+m]=variablePara[m];
									colType2[currCol2+m]=columnDataType;	
									for (int j = 0; j < dataSize; j++) {
										//依次赋值
										if("currMth".equals(variableMethod)){
											rowList2[j][currCol2+m]=currMth;
										}else if("currYear".equals(variableMethod)){
											rowList2[j][currCol2+m]=currYear;
										}else if("currArea".equals(variableMethod)){
											rowList2[j][currCol2+m]=currArea;											
										}else{
											rowList2[j][currCol2+m]="";
										}
										
									}
								}								
								currCol2=currCol2+variablePara.length;	
							}
						}

					}else if("Sequence".equals(columnFormTagType)){
						//处理变量
						String variableMethodStr=columnInfoArr[6];
						String[] variableMethodArr=variableMethodStr.split("\\:");
						if(variableMethodArr.length>0){
							String variableMethod=variableMethodArr[0];
							String[] variablePara=variableMethodArr[1].split("\\,");
							if(variablePara.length>0){
								for (int m = 0; m < variablePara.length; m++) {
									colList2[currCol2+m]=variablePara[m];
									colType2[currCol2+m]=columnDataType;	
									for (int j = 0; j < dataSize; j++) {
										//依次赋值
										rowList2[j][currCol2+m]=variableMethod;
										
									}
								}								
								currCol2=currCol2+variablePara.length;	
							}
						}
					}else if("Label".equals(columnFormTagType)){
						//不操作，不写入
					}//endif
					
					
					
					
					
					
				}
				
				
				
				
			}
			
			//合并列colList2
			int newColsCnt=columns.length+currCol2;
			String[] newColList=new String[newColsCnt];			
			for (int j = 0; j < columns.length; j++) {
				newColList[j]=columns[j];
			}
			for (int j = 0; j < currCol2; j++) {
				newColList[columns.length+j]=colList2[j]+"#########"+colType2[j];
			}
			if(log.isDebugEnabled())
			{
				log.debug("############################newColList:"+newColList.length);
			}
			
			//合并数据rowList2
			if(log.isDebugEnabled())
			{
				log.debug("############################新数组行数trList.size():"+trList.size());
			}
			String[][] newRowList=new String[trList.size()][newColsCnt];
			int i1=0;
			for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
				String[] _data = (String[]) iterator.next();
				
				if(log.isDebugEnabled())
				{
					log.debug("############################当前行i1:"+i1);
					log.debug("############################列数_data.length:"+_data.length);
					log.debug("############################newRowList[i1]:"+newRowList[i1]);
				}
				
				for(int j=0;j<_data.length;j++){
					newRowList[i1][j]=_data[j];
				}
				i1++;
			}
			for(int i=0;i<rowList2.length;i++){
				for(int j=0;j<currCol2;j++){
					newRowList[i][j+columns.length]=rowList2[i][j];
				}
			}
			if(log.isDebugEnabled())
			{
				log.debug("############################newRowList:"+newRowList.length);
			}
			
			
			if(log.isDebugEnabled())
			{
				//log.debug("############################列信息分解");
			}
			//分解表列配置文件到 _column,_type
			String[] _column=new String[newColList.length];
			String[] _type=new String[newColList.length];
			for(int i=0;i<newColList.length;i++){
				String[] columnInfoArr=newColList[i].split("\\###");
				if(null!=columnInfoArr && columnInfoArr.length>0)
				{
					String columnName=columnInfoArr[0];
					String columnDataType=columnInfoArr.length<4?"SKIP":columnInfoArr[3];
					_column[i]=columnName;//这里允许字段名为空，接下来在生成SQL时候过滤掉，以便于匹配数据
					_type[i]=columnDataType;	
				}
			}	
	
			
			
			if(log.isDebugEnabled())
			{
				//log.debug("############################构建SQL");
			}			
			//构建Insert语句
			//StringBuffer sql=new StringBuffer();
			result=new String[newRowList.length];
			for(int i=0;i<newRowList.length;i++){
				result[i]=getInsertSql(_column,newRowList[i],_type,tbOracleName);
				//sql.append(getInsertSql(_column,newRowList[i],_type,tableName));				
			}

			if(log.isDebugEnabled())
			{
				//log.debug("############################SQL:"+sql.toString());
			}
			
			
			
			
			
//			if(log.isDebugEnabled())
//			{
//				log.debug("############################列信息分解");
//			}
//			//分解表列配置文件到 _column,_type
//			String[] _column=new String[columns.length];
//			String[] _type=new String[columns.length];
//			for(int i=0;i<columns.length;i++){
//				String[] columnInfoArr=columns[i].split("\\###");
//				if(columnInfoArr.length>=3)
//				{
//					String columnName=columnInfoArr[0];
//					String columnDataType=columnInfoArr[3];
//					_column[i]=columnName;//这里允许字段名为空，接下来在生成SQL时候过滤掉，以便于匹配数据
//					_type[i]=columnDataType;	
//				}
//			}			
//			if(log.isDebugEnabled())
//			{
//				log.debug("############################构建SQL");
//			}			
//			//构建Insert语句
//			StringBuffer sql=new StringBuffer();
//			for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
//				String[] _data = (String[]) iterator.next();
//				sql.append(getInsertSql(_column,_data,_type,tableName));				
//			}
			
			
			
			
			
			//result = sql;
		} catch (Exception e) {
			throw e;
		}		
		return result;
	}
	/**
	 * 生成Insert语句
	 * @param _column列名
	 * @param _data数据行
	 * @param _type数据类型
	 * @param _tableName数据库名
	 * @return
	 */
	public String getInsertSql(final String[] _column,final String[] _data,final String[] _type,final String _tableName)
	{
		StringBuffer sql = new StringBuffer();
		StringBuffer value=new StringBuffer();
		if(_column.length==_data.length&&_column.length==_type.length){
			sql.append("insert into " + _tableName + "(");
			value.append(") values(");
			for(int i=0;i<_column.length;i++){
				if ("".equals(_column[i])){//如果需要转义
					//////////////////////////////////////////////////////////////////

				}else{//正常字段
					if(!"SKIP".equals(_type[i])){
						sql.append(_column[i]+",");
						if("Integer".equals(_type[i])||"Long".equals(_type[i])||"Float".equals(_type[i])||"Double".equals(_type[i])){
							value.append(_data[i]+",");
						}else{
							value.append("'"+_data[i]+"',");
						}
					}
				}
			}
			sql.deleteCharAt(sql.length() - 1);
			value.deleteCharAt(value.length() - 1);
			sql.append(value);
			sql.append(")");
			//sql.append(";");
		}
		return sql.toString();
	}
	
	


    /**  
     * 判断是否为年月 符合返回ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isYearMth(final String str) throws Exception
	{
		Pattern p = Pattern.compile("^\\d{4}([0-1][0-9]$)");//正则表达式
		Matcher m = p.matcher(str);//操作的字符串
		boolean b = m.matches();
		if(b)
		{
			try
			{
				int test=Integer.parseInt(str.substring(str.length()-2));
				if(test>12)
				{
					b=false;
				}
			} catch (Exception e) {
				b=false;
				throw e;
			}
		}
		
		return b;
		//System.out.println("手机号码正确"+b);//输出true
	}
    /**  
     * 判断是否为Long 符合返回ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isLong(final String str) throws Exception
	{
		//基本类型：long 二进制位数：64
		//包装类：java.lang.Long
		//最小值：Long.MIN_VALUE=-9223372036854775808
		//最大值：Long.MAX_VALUE=9223372036854775807
		String[] arr=str.split("\\.");
		//split这里的参数的名称是 regex ，也就是 regular expression （正则表达式)。这个参数并不是一个简单的分割用的字符，而是一个正则表达式，
		//"."在正则表达式中有特殊的含义，因此我们使用的时候必须进行转义。str(".");改为 str("\\.");		
		if (arr.length>0)
		{
			//Pattern p = Pattern.compile("^-?\\d{1,19}$");//正则表达式
			Pattern p = Pattern.compile("^-?(([1-9]\\d*$)|0)");//正则表达式
			Matcher m = p.matcher(arr[0]);//操作的字符串
			boolean b = m.matches();
			if(b)
			{
				try
				{
					long test=Long.parseLong(arr[0]);
				} catch (Exception e) {
					b=false;
					throw e;
				}
			}
			return b;
		}else{
			return false;
		}
	}
    /**  
     * 判断是否为Integer 符合返回ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isInteger(final String str) throws Exception
	{
		//基本类型：int 二进制位数：32
		//包装类：java.lang.Integer
		//最小值：Integer.MIN_VALUE=-2147483648
		//最大值：Integer.MAX_VALUE=2147483647
		String[] arr=str.split("\\.");
		//split这里的参数的名称是 regex ，也就是 regular expression （正则表达式)。这个参数并不是一个简单的分割用的字符，而是一个正则表达式，
		//"."在正则表达式中有特殊的含义，因此我们使用的时候必须进行转义。str(".");改为 str("\\.");		
		if (arr.length>0)
		{
			//Pattern p = Pattern.compile("^-?\\d{1,10}$");//正则表达式
			Pattern p = Pattern.compile("^-?(([1-9]\\d*$)|0)");//正则表达式
			Matcher m = p.matcher(arr[0]);//操作的字符串
			boolean b = m.matches();
			if(b)
			{
				try
				{
					int test=Integer.parseInt(arr[0]);
				} catch (Exception e) {
					b=false;
					throw e;
				}
			}
			return b;
		}else{
			return false;
		}
	}
    /**  
     * 判断是否为Float 符合返回ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isFloat(final String str) throws Exception
	{
		//基本类型：float 二进制位数：32
		//包装类：java.lang.Float
		//最小值：Float.MIN_VALUE=1.4E-45
		//最大值：Float.MAX_VALUE=3.4028235E38
		//Pattern p = Pattern.compile("^[+|-]?\\d*\\.?\\d*$");//正则表达式
		Pattern p = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");//正则表达式
		Matcher m = p.matcher(str);//操作的字符串
		boolean b = m.matches();
		if(b)
		{
			try
			{
				float test = Float.parseFloat(str);
			} catch (Exception e) {
				b=false;
				throw e;
			}
		}
		return b;
	}
    /**  
     * 判断是否为Double 符合返回ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isDouble(final String str) throws Exception
	{
		//基本类型：double 二进制位数：64
		//包装类：java.lang.Double
		//最小值：Double.MIN_VALUE=4.9E-324
		//最大值：Double.MAX_VALUE=1.7976931348623157E308
		//Pattern p = Pattern.compile("^[+|-]?\\d*\\.?\\d*$");//正则表达式
		Pattern p = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");//正则表达式
		Matcher m = p.matcher(str);//操作的字符串
		boolean b = m.matches();
		if(b)
		{
			try
			{
				double test = Double.parseDouble(str);
			} catch (Exception e) {
				b=false;
				throw e;
			}
		}
		
		return b;
	}
	
	
//******************************************从Map中反向翻译***************************
	/**
	 * 翻译区域公司
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getArea(final String str) throws Exception{
		String[] result=new String[2];
		try{
			Map map = dicMap.getAreaMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{
					Object key = it.next();
					DicAreacorp obj=(DicAreacorp)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：翻译企业
	 * @param str
	 * @return企业ID，企业Name
	 * @throws Exception
	 */
	public String[] getCorp(final String str) throws Exception{
		String[] result=new String[5];//GetCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO
		try{
			Map map = dicMap.getCorpMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{
					Object key = it.next();
					DicSinocorp obj=(DicSinocorp)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						result[2]=obj.getAreaid();
						Map map2=dicMap.getAreaMap();
						if(null!=map2){
							DicAreacorp obj2=(DicAreacorp)map2.get(result[2]) ;					
							result[3]=obj2==null?"":obj2.getShortname();
							//result[4]=obj.getCorpid();
						}
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	
	/**
	 * 翻译企业，从数据库和XML同时 XML：YUPRTATSM.XML
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getCorpFromDBXML(final String str) throws Exception{
		String[] result=new String[5];//GetCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO
		result=getCorpFromXML(str);
		//如果XML中没有则去中石化企业找
		if(null==result||null==result[0]||""==result[0]){
			result=getCorp(str);
		}
		return result;
	}
	/**
	 * 翻译企业，仅从XML读取 XML：YUPRTATSM.XML
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getCorpFromXML(final String str) throws Exception{
		String[] result=new String[5];//GetCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO
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
                for (int i = 0; i < list.size(); i++) {
                	Element e = (Element) list.get(i);
                	if(e.getText()!=null && str.equals(e.getText())){
	                	result[0]=e.attributeValue("code");
	                	result[1]=e.getText();
	                	result[2]=e.attributeValue("areaID");
	                	result[3]=e.attributeValue("areaName");
	                	break;
                	}
				} 
            }
	    } catch (DocumentException e) { 
	            //e.printStackTrace(); 
	            if(log.isDebugEnabled()){
	    			log.debug("***************从YUPRTATSM.XML中获取配置是出现错误**********");
	    			log.debug("异常："+e.getMessage());
	            }
	    } 
		return result;
	}
	
	/**  
	 * FromMap：翻译客户
	 * @param str
	 * @return客户ID，客户Name
	 * @throws Exception
	 */
	public String[] getCustomer(final String str)throws Exception{
		String[] result=new String[2];//GetCustomer:CUSTOMERID,COUSTOMERSHORTNAME
		try{
			Map map = dicMap.getCustomerMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{
					Object key = it.next();
					DicCustomer obj=(DicCustomer)map.get(key);
					if(obj!=null && (str.equals(obj.getFullname())||str.equals(obj.getShortname()))){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * FromMap：翻译客户，内部+外部(暂时不用了)
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getCustomerCorp(final String str)throws Exception{
		String[] result=new String[2];//GetCustomer:CUSTOMERID,COUSTOMERSHORTNAME
		try{
			Map map = dicMap.getCustomercorpMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{
					Object key = it.next();
					DicCustomer obj=(DicCustomer)map.get(key);
					if(obj!=null && (str.equals(obj.getFullname())||str.equals(obj.getShortname()))){
						result[0]=key.toString();
						result[1]=obj.getFullname();
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：翻译产品
	 * @param str
	 * @return产品ID，大类ID，小类ID，产品Name，大类Name，小类Name
	 * @throws Exception
	 */
	public String[] getProduct(final String str)throws Exception{
		String[] result=new String[6];//GetProduct:PRODDUCTID,PRODUCTCATEGORYID,PRODUCTSECONDID,......
		try{
			Map map = dicMap.getProductMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{
					Object key = it.next();
					DicProduct obj=(DicProduct)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=obj.getCategoryid();
						result[2]=obj.getSecondkindid();
						result[3]=str;					
						
						Map map2 = dicMap.getProductcategoryMap();
						if(null!=map2){
							DicProdcategory obj2=(DicProdcategory)map2.get(result[1]);
							result[4]=obj2==null?"":obj2.getShortname();	
						}
						
						Map map3 = dicMap.getProductsecondMap();
						if(null!=map2){
							DicProdsecondkind obj3=(DicProdsecondkind)map3.get(result[2]);
							result[5]=obj3==null?"":obj3.getShortname();	
						}
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：翻译车种
	 * @param str
	 * @return车种ID,车种Name
	 * @throws Exception
	 */
	public String[] getCarKind(final String str)throws Exception{
		String[] result=new String[2];//GetCarKind:CARKINDID
		try{
			Map map = dicMap.getCarkindMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicCarkind obj=(DicCarkind)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：翻译车种,根据Key直接获取
	 * @param str
	 * @return车种ID,车种Name
	 * @throws Exception
	 */
	public String[] getCarKindByKey(final String key)throws Exception{
		String[] result=new String[2];//GetCarKind:CARKINDID
		try{
			Map map = dicMap.getCarkindMap();
			if(null!=map){
				DicCarkind obj=(DicCarkind)map.get(key);
				if(obj!=null){
					result[0]=key.toString();
					result[1]=obj.getShortname();
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：翻译发站
	 * @param str
	 * @return发站ID,发站Name
	 * @throws Exception
	 */
	public String[] getStartStation(final String str)throws Exception{
		String[] result=new String[2];//GetStartStation:STARTSTATIONID
		try{
			Map map = dicMap.getStartstationMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicRwstation obj=(DicRwstation)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：翻译送达省份
	 * @param str
	 * @return送达省份ID，送达省份Name
	 * @throws Exception
	 */
	public String[] getGoalProvince(final String str)throws Exception{
		String[] result=new String[2];//GetGoalProvince:GOALPROVINCEID
		try{
			Map map = dicMap.getGoalprovinceMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicProvince obj=(DicProvince)map.get(key);
					if(obj!=null && str.equals(obj.getName())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：翻译到站
	 * @param str
	 * @return到站ID，到站Name
	 * @throws Exception
	 */
	public String[] getEndStation(final String str)throws Exception{
		String[] result=new String[2];//GetEndStation:ENDSTATIONID
		try{
			Map map = dicMap.getEndstationMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicRwstation obj=(DicRwstation)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：翻译承运人
	 * @param str
	 * @return承运人ID，承运人Name
	 * @throws Exception
	 */
	public String[] getTransport(final String str)throws Exception{
		String[] result=new String[2];//GetTransport:TRANSPORTID
		try{
			Map map = dicMap.getTransporterMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicTransporter obj=(DicTransporter)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * FromMap：翻译铁路品名
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getRwKind(final String str)throws Exception{
		String[] result=new String[2];//GetTransport:TRANSPORTID
		try{
			Map map = dicMap.getRwkindMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicGoods obj=(DicGoods)map.get(key);
					if(obj!=null && str.equals(obj.getPmhz())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * FromMap：翻译产品大类
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getProductcategory(final String str)throws Exception{
		String[] result=new String[2];//GetTransport:TRANSPORTID
		try{
			Map map = dicMap.getProductcategoryMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicProdcategory obj=(DicProdcategory)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * FromMap：翻译产品小类
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getProductsecond(final String str)throws Exception{
		String[] result=new String[2];//GetTransport:TRANSPORTID
		try{
			Map map = dicMap.getProductsecondMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicProdsecondkind obj=(DicProdsecondkind)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * FromMap：翻译发货人
	 * @param str
	 * @param corpid
	 * @return
	 * @throws Exception
	 */
	public String[] getSender(final String str,final String corpid)throws Exception{
		String[] result=new String[2];//GetTransport:TRANSPORTID
		try{
			Map map = dicMap.getSenderMap(corpid);
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					CtlCorpsender obj=(CtlCorpsender)map.get(key);
					if(obj!=null && (str.equals(obj.getSendername())||str.equals(obj.getSendershortname()))){
						result[0]=key.toString();
						result[1]=obj.getSendername();
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * FromMap：翻译收货人
	 * @param str
	 * @param corpid
	 * @return
	 * @throws Exception
	 */
	public String[] getReciver(final String str,final String corpid)throws Exception{
		String[] result=new String[2];//GetTransport:TRANSPORTID
		try{
			Map map = dicMap.getReceiverMap(corpid);
			
			if(log.isDebugEnabled())
			{
				//log.debug("#######################################客户对应收货人map:"+map);
			}
			
			
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					Object o=map.get(key);
					if(o instanceof CtlCustomerreceiver ){
						CtlCustomerreceiver obj=(CtlCustomerreceiver)map.get(key);
						if(obj!=null && str.equals(obj.getReceivername())){
							result[0]=key.toString();
							result[1]=obj.getReceivername();
							break;
						}
					}else
					{
						CtlCorpreceiver obj=(CtlCorpreceiver)map.get(key);
						if(obj!=null && (str.equals(obj.getReceivername())||str.equals(obj.getReceivershortname()))){
							result[0]=key.toString();
							result[1]=obj.getReceivername();
							break;
						}
					}
					
					
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * FromMap：翻译销售机构
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getSaleDepartment(final String str)throws Exception{
		String[] result=new String[2];//GetTransport:TRANSPORTID
		try{
			Map map = dicMap.getSaledepartmentMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//给下拉列表框赋值
					Object key = it.next();
					DicSalecorp obj=(DicSalecorp)map.get(key);
					if(obj!=null && str.equals(obj.getShortname())){
						result[0]=key.toString();
						result[1]=str;
						break;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**  
	 * FromMap：获取客户名称在客户表中的数量
	 * @param str
	 * @return客户ID，客户Name
	 * @throws Exception
	 */
	public int getCustomerCnt(final String str)throws Exception{
		int cnt=0;
		try{
			Map map = dicMap.getCustomerMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{
					Object key = it.next();
					DicCustomer obj=(DicCustomer)map.get(key);
					if(obj!=null && (str.equals(obj.getFullname())||str.equals(obj.getShortname()))){
						cnt++;
					}
				}
			}
		}catch(Exception e){
			throw e;
		}
		return cnt;
	}
	
}
