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
	private final int ExcelDataStartRowNO=2;//Excel���ݿ�ʼ��0Ϊ��һ��
	public ExcelImpUtil() {
	}
	public void main(String[] args) {
		// ExcelImpUtil cu=new ExcelImpUtil();
		// cu.doInputData("d:\\employee.xls");
	}

	/**
	 * ��ȡExcel����
	 * @param destUrlFile
	 * @return
	 * @throws Exception
	 */
	public int getExcelRowsCount(final String destUrlFile) throws Exception{
		ServletContext context = ServletActionContext.getServletContext();
		//�ϴ�Ŀ�������·��
		String destRealFile = context.getRealPath(destUrlFile);
		FileInputStream _input = null;
		Workbook workbook = null; // Excel����
		Sheet sheet = null; // �����
		Row row = null; // �ж���
		Cell cell = null; // ��Ԫ�����
		
		String[] fileNameArr = destUrlFile.split("\\.");			
		if(fileNameArr.length>1)
		{
			String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
			if ("XLS".equals(fileExtName)) {
				try {
					// ���ݶ�ȡ
					 _input=new FileInputStream(destRealFile);
					 workbook = new HSSFWorkbook(_input);
				} catch (Exception e) {
					throw e;
				}
			}else if("XLSX".equals(fileExtName)) {
				try {
					// ���ݶ�ȡ
					 _input=new FileInputStream(destRealFile);
					 workbook = new XSSFWorkbook(_input);
				} catch (Exception e) {
					throw e;
				}
			}else{
				//�ļ����ʹ���
				return -1;
			}
		}else{
			//�ļ�·������
			return -1;
		}

		if (null != _input) {
			_input.close();
		}
		sheet = workbook.getSheetAt(0);
		//��ÿ�ʼ�к�
		int rsRowsStart=ExcelDataStartRowNO;
		int rsRowsEnd=sheet.getLastRowNum();
		// �����������
		int rsRows=rsRowsEnd-rsRowsStart+1;				
		
		if(log.isDebugEnabled())
		{
			log.debug("################################��ʼ�У��߼�)��"+rsRowsStart);
			log.debug("################################�����У��߼�)��"+rsRowsEnd);
			log.debug("################################������"+rsRows);
		}
		return rsRows;
	}
	
	/**
	 * ��ȡEXcel����
	 * @param destUrlFile
	 * @return
	 * @throws Exception
	 */
	public List getExcelData(final String destUrlFile) throws Exception{
		List res = new ArrayList();
		try {
			ServletContext context = ServletActionContext.getServletContext();
			//�ϴ�Ŀ�������·��
			String destRealFile = context.getRealPath(destUrlFile);
			FileInputStream _input = null;
			Workbook workbook = null; // Excel����
			Sheet sheet = null; // �����
			Row row = null; // �ж���
			Cell cell = null; // ��Ԫ�����
			
			String[] fileNameArr = destUrlFile.split("\\.");			
			if(fileNameArr.length>1)
			{
				String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
				if ("XLS".equals(fileExtName)) {
					try {
						// ���ݶ�ȡ
						 _input=new FileInputStream(destRealFile);
						 workbook = new HSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else if("XLSX".equals(fileExtName)) {
					try {
						// ���ݶ�ȡ
						 _input=new FileInputStream(destRealFile);
						 workbook = new XSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else{
					//�ļ����ʹ���
					return res;
				}
			}else{
				//�ļ�·������
				return res;
			}

			if (null != _input) {
				_input.close();
			}
			sheet = workbook.getSheetAt(0);
			//��ÿ�ʼ�к�
			int rsRowsStart=ExcelDataStartRowNO;
			int rsRowsEnd=sheet.getLastRowNum();
			// �����������
			int rsRows=rsRowsEnd-rsRowsStart+1;				
			
			if(log.isDebugEnabled())
			{
				log.debug("################################��ʼ�У��߼�)��"+rsRowsStart);
				log.debug("################################�����У��߼�)��"+rsRowsEnd);
				log.debug("################################������"+rsRows);
			}
			
			if (rsRows>0){
				int rsCols = sheet.getRow(rsRowsStart).getPhysicalNumberOfCells();
				for (int i = rsRowsStart; i <= rsRowsEnd; i++) {
					 row = sheet.getRow(i);
					if(null==row||isNullRow(row)){
						if(log.isDebugEnabled())
						{
							log.debug("################################��Ҫɾ�����кţ�"+i);
							log.debug("################################��Ҫɾ�������кţ�"+rsRowsEnd);									
						}
						if(i+1<rsRowsEnd)
						{
							//����ɾ����
							sheet.shiftRows(i+1, rsRowsEnd, -1);
						}else{
							//ɾ���������У������)
							if(null!=row){
								sheet.removeRow(row);
							}
						}						
						continue;
					}
					//int rsCols=row.getPhysicalNumberOfCells();
					String[] rowData=new String[rsCols];
					for (int j = 0; j < rsCols; j++) {
						// ��ȡCELL�е�����
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
			
			//�����м���˹����еȲ������ʱ���һ��
			FileOutputStream _output=new FileOutputStream(destRealFile);
			workbook.write(_output);
			_output.close();
			
		}catch(Exception e){
			throw e;
		}
		return res;
	}
	/**
	 * ��ȡEXCEL����(EXCEL����)-������֤�������Ƿ�Ϊ�ա��ֶγ��ȡ��ֶ����͡��ֵ�ƥ��
	 * 
	 * @param filename
	 *            �ļ���
	 * @param obj
	 *            POJO���������ݱ���)
	 * @param columns
	 *            �������������ֶ���)��ӦEXCEL��ͷ����
	 * @param nullables
	 *            ���ԣ������ֶ�)�Ƿ��Ϊ��
	 * @throws Exception<T>
	 */
	public Map checkExcelData(final String destUrlFile, final Object obj,final String[] columns,final String currYear,final String currMth,final String currArea,final String currCorp) throws Exception {
		Map map = new HashMap();
		List dataTableInfoList = new ArrayList();// ���������Ϣ��1|||1|||222|||��������0*(���������1*)|||���ݴ���˵��)����һ��/��һ��/����/����״̬/���ݴ���˵��)
		boolean isUpdate = true;// �Ƿ�����ύ
		
		ServletContext context = ServletActionContext.getServletContext();
		Workbook workbook = null; // Excel����
		Sheet sheet = null; // �����
		Row row = null; // �ж���
		Cell cell = null; // ��Ԫ�����
		
		//�ϴ�Ŀ�������·��
		String destRealFile = context.getRealPath(destUrlFile);	
		FileInputStream _input = null;
		
		String[] fileNameArr = destUrlFile.split("\\.");			
		if(fileNameArr.length>1)
		{
			String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
			if ("XLS".equals(fileExtName)) {
				try {
					// ���ݶ�ȡ
					 _input=new FileInputStream(destRealFile);
					 workbook = new HSSFWorkbook(_input);
				} catch (Exception e) {
					throw e;
				}
			}else if("XLSX".equals(fileExtName)) {
				try {
					// ���ݶ�ȡ
					 _input=new FileInputStream(destRealFile);
					 workbook = new XSSFWorkbook(_input);
				} catch (Exception e) {
					throw e;
				}
			}else{
				//�ļ����ʹ���
				return map;
			}
		}else{
			//�ļ�·������
			return map;
		}
		
		
		try {
			
//			// ���ݶ�ȡ
//			FileInputStream _input=new FileInputStream(destRealFile);
//			HSSFWorkbook workbook = new HSSFWorkbook(_input);
			if (null != _input) {
				_input.close();				
			}
			sheet = workbook.getSheetAt(0);

			//��ÿ�ʼ�к�
			int rsRowsStart=ExcelDataStartRowNO;
			int rsRowsEnd=sheet.getLastRowNum();
			// �����������
			int rsRows=rsRowsEnd-rsRowsStart+1;
			
			if(log.isDebugEnabled())
			{
				log.debug("################################��ʼ�У��߼�)��"+rsRowsStart);
				log.debug("################################�����У��߼�)��"+rsRowsEnd);
				log.debug("################################������"+rsRows);
			}
			if (rsRows>0){
				//int rsCols = sheet.getRow(rsRowsStart).getPhysicalNumberOfCells();//���д���ᵼ�´���ע�͵���ͬʱ�����������Ϣ				
				int rsCols = columns.length;
				for (int i = rsRowsStart; i <= rsRowsEnd; i++) {
					List dataRowInfoList = new ArrayList();//���浱ǰ��������Ϣ
					row = sheet.getRow(i);
					if(row == null||isNullRow(row)){
						if(log.isDebugEnabled())
						{
							log.debug("################################��Ҫɾ�����кţ�"+i);
							log.debug("################################ʣ�ಿ�ֽ����кţ�"+rsRowsEnd);									
						}
						if(i+1<rsRowsEnd)
						{
							//����ɾ����
							sheet.shiftRows(i+1, rsRowsEnd, -1);
						}else{
							//ɾ���������У������)
							if(null!=row){
								sheet.removeRow(row);
							}
						}	
						continue;
					}
					// Map fdMap = new HashMap();//���ش�����������				
					for (int j = 0; j < columns.length; j++) {
						String dataCellInfo="############";//���浱ǰ��Ԫ��������Ϣ
						String cellDataStatus="00";//����״̬��0*�޴���1*�д���
						String cellDataErrorMsg="��ȷ����";//���ݴ�����Ϣ��ϸ
						// ��ȡCELL�е�����
						cell = row.getCell(j);
						String cellDataObj = cell==null?"":cell.toString().trim();
						if(cell!=null){
							cell.setCellValue(cellDataObj);
						}
						
						
	//				    String form = cell.getCellStyle().getDataFormatString();        
	//				    DecimalFormat format = new DecimalFormat(form);    
	//					switch(cell.getCellType())	
	//					{
	//						case HSSFCell.CELL_TYPE_FORMULA: //�����ж�һ���ǲ��ǹ�ʽ
	//							 cell.setCellType(cell.CELL_TYPE_NUMERIC); //�����䵥Ԫ������Ϊ����  
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
						
						// �ж��ֶ�
						Class c = obj.getClass();
						// ���Դ���
						
						String[] columnInfoArr=columns[j]!=null?columns[j].split("\\###"):null;
						if(null!=columnInfoArr && columnInfoArr.length>=4&&columnInfoArr[0]!="SKIP")
						{
							String columnName=columnInfoArr[0].toLowerCase();
							String columnDesc=columnInfoArr[1];
							String columnFormTagType=columnInfoArr[2];
							String columnDataType=columnInfoArr[3];		
							
							if("".equals(columnName)){//����Ҫת��
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
											
											
											//�������÷���		
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
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getCorpFromDBXML".equals(columnDDLMethod)){
											String[] res=getCorpFromDBXML(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "31";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getArea".equals(columnDDLMethod)){
											String[] res=getArea(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "31";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getCustomer".equals(columnDDLMethod)){
											//if(log.isDebugEnabled()){
											//	log.debug("*************************����customer����****************");
											//	log.debug(cellDataObj);
											//}
											String[] res=getCustomer(cellDataObj);	
											//if(log.isDebugEnabled()){
											//	log.debug("*************************����customer���****************");
											//	log.debug(res[0]);
											//	log.debug(res[1]);
											//}
											if(res==null||res[0]==null){
												cellDataStatus = "32";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getCustomerCorp".equals(columnDDLMethod)){
											String[] res=getCustomerCorp(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "32";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getProduct".equals(columnDDLMethod)){
											String[] res=getProduct(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "33";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getCarKindByKey".equals(columnDDLMethod)){
											String[] res=getCarKindByKey(cellDataObj);	//����Key����
											if(res==null||res[0]==null){
												cellDataStatus = "34";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getCarKind".equals(columnDDLMethod)){
											String[] res=getCarKind(cellDataObj);	//��������
											if(res==null||res[0]==null){
												cellDataStatus = "34";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getStartStation".equals(columnDDLMethod)){
											String[] res=getStartStation(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "35";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getGoalProvince".equals(columnDDLMethod)){
											String[] res=getGoalProvince(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "36";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getEndStation".equals(columnDDLMethod)){
											String[] res=getEndStation(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "37";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getTransport".equals(columnDDLMethod)){
											String[] res=getTransport(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "38";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getRwKind".equals(columnDDLMethod)){
											String[] res=getRwKind(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "39";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else if("getProductcategory".equals(columnDDLMethod)){
											String[] res=getProductcategory(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "40";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}		
										}else if("getProductsecond".equals(columnDDLMethod)){
											String[] res=getProductsecond(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "41";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}	
										}else if("getSaleDepartment".equals(columnDDLMethod)){
											String[] res=getSaleDepartment(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "42";
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}										
										}else if("getSender".equals(columnDDLMethod)){
											if("".equals(columnDDLInPara)){
												cellDataErrorMsg=columnDDLMethod+"����ת�����û���ṩ��Ҫ��������ҵ��";
											}else{
												String[] columnDDLInParaArr=columnDDLInPara.split("\\,");
												String columnDDLInPara0="";
												if(!"".equals(columnDDLInPara)&&columnDDLInParaArr.length>0){
													columnDDLInPara0=columnDDLInParaArr[0];
													//��ȡ��Ҫ����
													Cell cellTemp = row.getCell(Integer.parseInt(columnDDLInPara0));
													String columnDDLInPara0Data = cellTemp==null?"":cellTemp.toString();
													String[] corpM=getCorp(columnDDLInPara0Data);
													String columnDDLInParaID=(corpM==null||corpM[0]==null)?"":corpM[0];		
													if(log.isDebugEnabled())
													{
													//	log.debug("#######################################��ȡ�����˲���corpID��:"+columnDDLInPara0Data);
													//	log.debug("#######################################��ȡ�����˲���corpID��:"+columnDDLInParaID);
													}
													String[] res=getSender(cellDataObj,columnDDLInParaID);	
													if(res==null||res[0]==null){
														cellDataStatus = "43";
														cellDataErrorMsg=columnDDLMethod+"����ת�����";
														isUpdate = false;}	
												}
												
											}
										}else if("getReciver".equals(columnDDLMethod)){
											if("".equals(columnDDLInPara)){
												cellDataErrorMsg=columnDDLMethod+"����ת�����û���ṩ��Ҫ��������ҵ��";
											}else{
												String[] columnDDLInParaArr=columnDDLInPara.split("\\,");
												String columnDDLInPara0="";
												if(columnDDLInParaArr.length>0){
													columnDDLInPara0=columnDDLInParaArr[0];
												}
												//��ȡ��Ҫ����
												Cell cellTemp = row.getCell(Integer.parseInt(columnDDLInPara0));
												String columnDDLInPara0Data = cellTemp==null?"":cellTemp.toString();
												String[] customerM=getCustomer(columnDDLInPara0Data);
												String columnDDLInParaID=(customerM==null||customerM[0]==null)?"":customerM[0];	
												if(log.isDebugEnabled())
												{
												//	log.debug("#######################################��ȡ�ջ��˲���customsID��:"+columnDDLInPara0Data);
												//	log.debug("#######################################��ȡ�ջ��˲���customsID��:"+columnDDLInParaID);
												}
												String[] res=getReciver(cellDataObj,columnDDLInParaID);	
												if(res==null||res[0]==null){
													cellDataStatus = "44";
													cellDataErrorMsg=columnDDLMethod+"����ת�����";
													isUpdate = false;}		
											}
										}else{
											cellDataStatus = "30";
											cellDataErrorMsg="����ת�����δ�ҵ�ת�巽����";
											isUpdate = false;
										}									
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="���ñ�δ��DropDownList����ת������";
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
												cellDataErrorMsg=columnDDLMethod+"����ת�����";
												isUpdate = false;}
										}else{
											cellDataStatus = "30";
											cellDataErrorMsg="����ת�����δ�ҵ�ת�巽����";
											isUpdate = false;
										}
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="���ñ�δ��Tree����ת������";
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
												cellDataErrorMsg=columnDDLMethod+"����ת�����������Ʒ���������޸�";
												isUpdate = false;}		
										}else if("getProductsecond".equals(columnDDLMethod)){
											String[] res=getProductsecond(cellDataObj);	
											if(res==null||res[0]==null){
												cellDataStatus = "41";
												cellDataErrorMsg=columnDDLMethod+"����ת�����������Ʒ���������޸�";
												isUpdate = false;}	
										}else{
											cellDataStatus = "30";
											cellDataErrorMsg="����ת�����δ�ҵ�ת�巽����";
											isUpdate = false;
										}	
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="���ñ�δ��TreeRoot����ת������";
										isUpdate = false;
									}
								}else if("Variable".equals(columnFormTagType)){		
									String variableMethodStr=columnInfoArr[6];
									String[] variableMethodArr=variableMethodStr.split("\\:");
		
									if(variableMethodArr.length>0){
										String variableMethod=variableMethodArr[0];
										//String variablePara[]=variableMethodArr[1].split("\\,");
										////////////////////////////////////�������ñ�����Ϣ����ֵ������ɼ�����չ
										if("currMth".equals(variableMethod)){
											cellDataObj=currMth;
										}else if("currYear".equals(variableMethod)){
												cellDataObj=currYear;
										}else if("currArea".equals(variableMethod)){
											cellDataObj=currArea;
										}else{
											cellDataStatus = "30";
											cellDataErrorMsg="ϵͳδ���ô˱���:"+variableMethod+"��";
											isUpdate = false;
										}
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="���ñ�δ��Tree����ת������";
										isUpdate = false;
									}
								}else if("Sequence".equals(columnFormTagType)){		
									String variableMethodStr=columnInfoArr[6];
									String[] variableMethodArr=variableMethodStr.split("\\:");
									if(variableMethodArr.length>0){
										String variableMethod=variableMethodArr[0];
										//String variablePara[]=variableMethodArr[1].split("\\,");
										//������������ﲻ��������ʾԭExcel������
									}else{
										cellDataStatus = "94";
										cellDataErrorMsg="���ñ�δ��Tree����ת������";
										isUpdate = false;
									}
	
								}else{
									cellDataStatus = "93";
									cellDataErrorMsg="���ñ����Ͷ����������Ϊ�ս�����Tree��TreeRoot��DropDownList";
									isUpdate = false;
								}
								
								
							}else{//��������
								//��������ĸת��д����
								//String column = CommUtils.getString(columns[i].substring(0,1).toUpperCase(), columns[i].substring(1).toLowerCase());
								String column = CommUtils.getString(columnName.substring(0,1).toUpperCase(), columnName.substring(1).toLowerCase());
								
								//��ȡget��������
								String methodNameOfGet = CommUtils.getString("get", column);
								// get���Է���
								Method methodOfGetView = c.getMethod(methodNameOfGet, null);
								// ����ע��
								Column col = methodOfGetView.getAnnotation(Column.class);
								// ���Գ���
								int length = col != null ? col.length() : 0;
								
	//							if(log.isDebugEnabled())
	//							{
	//								log.debug("############################Column Length:"+i+"      "+column+" : "+length);
	//							}
								
								
								// �Ƿ����Ϊ��
								boolean nullable = col != null ? col.nullable() : true;
								// �Ƿ��ֵ�(��DICΪ)
								// "DIC".endsWith(arg0)
								// ��������
								Field field = c.getDeclaredField(columnName);
								String fieldType = field.getType().getSimpleName();
								//��ȡset��������
								String methodNameOfSet = CommUtils.getString("set", column);
								// set���Է���
								Method methodOfSetView = c.getMethod(methodNameOfSet, field.getType());
								
								
								if(!nullable&&"".equals(cellDataObj))
								{
									cellDataStatus = "91";
									cellDataErrorMsg="����Ϊ�մ���:�õ�Ԫ������Ϊ��\n:";
									isUpdate = false;
								}else if(cellDataObj.toString().length()>length){
									cellDataStatus = "92";
									cellDataErrorMsg="���ݳ��ȳ��޴���:�õ�Ԫ�����ݹ���\n:";
									isUpdate = false;
								}else{
									// �����������ֵ
									//֮ǰ�ϵķ�������ʱ����
			//						if ("Date".equals(fieldType)) {
			//							try {
			//								methodOfSetView.invoke(obj, (Date)cellDataObj);
			//							} catch (Exception e) {
			//								cellDataStatus = 3;
			//								cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n"+e.toString();
			//								isUpdate = false;
			//							}
									
									
									if ("Date".equals(fieldType)) {
										cellDataStatus = "03";
										try {
											if(!isYearMth(cellDataObj))
											{
												cellDataStatus = "13";
												cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n";
												isUpdate = false;
											}
										} catch (Exception e) {
											cellDataStatus = "13";
											cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n"+e.toString();
											isUpdate = false;
										}
									} else if ("Long".equals(fieldType)) {
										cellDataStatus = "04";
										try {
											if(!isLong(cellDataObj))
											{
												cellDataStatus = "14";
												cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n";
												isUpdate = false;
											}else{//��ʽ��һ������
												String[] arr=cellDataObj.split("\\.");
												if (arr.length>0)
												{
													cellDataObj=arr[0];
												}
											}
										} catch (Exception e) {
											cellDataStatus = "14";
											cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n"+e.toString();
											isUpdate = false;
										}
									} else if ("Integer".equals(fieldType)) {
										cellDataStatus = "05";
										try {
											if(!isInteger(cellDataObj))
											{
												cellDataStatus = "15";
												cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n";
												isUpdate = false;
											}else{//��ʽ��һ������
												String[] arr=cellDataObj.split("\\.");
												if (arr.length>0)
												{
													cellDataObj=arr[0];
												}
											}
										} catch (Exception e) {
											cellDataStatus = "15";
											cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n"+e.toString();
											isUpdate = false;
										}
									} else if ("Float".equals(fieldType)) {
										cellDataStatus = "06";
										try {
											if(!isFloat(cellDataObj))
											{
												cellDataStatus = "16";
												cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n";
												isUpdate = false;
											}
										} catch (Exception e) {
											cellDataStatus = "16";
											cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n"+e.toString();
											isUpdate = false;
										}
									} else if ("Double".equals(fieldType)) {
										cellDataStatus = "07";
										try {
											if(!isDouble(cellDataObj))
											{
												cellDataStatus = "17";
												cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n";
												isUpdate = false;
											}
										} catch (Exception e) {
											cellDataStatus = "17";
											cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n"+e.toString();
											isUpdate = false;
										}
									}  else {
										cellDataStatus = "08";
										try {
											methodOfSetView.invoke(obj, cellDataObj);								
										} catch (Exception e) {
											cellDataStatus = "18";
											cellDataErrorMsg="�������ʹ���:��ֵ������ȷ��"+fieldType+"����\n"+e.toString();
											isUpdate = false;
										}						
									}
								}
							}
						}else if(null!=columnInfoArr && columnInfoArr.length<4 &&columnInfoArr[0]=="SKIP"){
							cellDataStatus = "02";
							cellDataErrorMsg="�������У�������";
						}else{
							cellDataStatus = "90";
							cellDataErrorMsg="��̬�������ļ�����";
						}
						
						dataCellInfo=i+"###"+j+"###"+cellDataObj+"###"+cellDataStatus+"###"+cellDataErrorMsg;
						dataRowInfoList.add(dataCellInfo);
					}
					dataTableInfoList.add(dataRowInfoList);
				} 
			}
			//�����м���˹����еȲ������ʱ���һ��
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
	 * ����Excel��Ԫ���޸�
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
			//�ϴ�Ŀ�������·��
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
						// ���ݶ�ȡ
						 _input=new FileInputStream(destRealFile);
						 workbook = new HSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else if("XLSX".equals(fileExtName)) {
					try {
						// ���ݶ�ȡ
						 _input=new FileInputStream(destRealFile);
						 workbook = new XSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else{
					//�ļ����ʹ���
					return result;
				}
			}else{
				//�ļ�·������
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
	 * ɾ��Excel��
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
			//�ϴ�Ŀ�������·��
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
						// ���ݶ�ȡ
						 _input=new FileInputStream(destRealFile);
						 workbook = new HSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else if("XLSX".equals(fileExtName)) {
					try {
						// ���ݶ�ȡ
						 _input=new FileInputStream(destRealFile);
						 workbook = new XSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else{
					//�ļ����ʹ���
					return result;
				}
			}else{
				//�ļ�·������
				return result;
			}
			
			
			
			if (null != _input) {
				_input.close();				
			}
			 sheet = workbook.getSheetAt(0);
			//��ÿ�ʼ�к�
			int rsRowsStart=ExcelDataStartRowNO;
			int rsRowsEnd=sheet.getLastRowNum();
			// �����������
			int rsRows=rsRowsEnd-rsRowsStart+1;
			
		
			if(log.isDebugEnabled())
			{
				log.debug("################################��ʼ�У��߼�)��"+rsRowsStart);
				log.debug("################################�����У��߼�)��"+rsRowsEnd);
				log.debug("################################������"+rsRows);
			}
			
			if(log.isDebugEnabled())
			{
				log.debug("################################��Ҫɾ�����У�"+currRowNum);
				log.debug("################################ʣ�ಿ�ֽ����кţ�"+rsRowsEnd);								
			}
			
			if(currRowNum+1<rsRowsEnd)
			{
				//����ɾ����
				sheet.shiftRows(currRowNum+1, rsRowsEnd, -1);//ɾ����ǰ��,�ú�������Ƹ��ǵ�ǰ��
				//sheet.shiftRows(startRow,endRow,shiftCount)����˵��:
				//startRow �ƶ��Ŀ�ʼ�к�,��0��ʼ����, �����Ҫɾ��Excel�еĵ�8��(rownum=7),��ôstarRow�趨Ϊ7,���趨Ϊ����ɾ��������
				//endRow �ƶ��Ľ����кţ���0��ʼ���㡣ͨ��startRow��endRowѡ���ƶ��ķ�Χ��������sheet.getLastRowNum ��ȡ��sheet�Ľ�β�к�
				//shiftCount �ƶ������С������������ƶ��������������������ƶ�������
			}else{
				//ɾ���������У������)
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
	 * �ж�Excel���Ƿ��
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
	 * ɾ��Excel�ļ�
	 * @param destUrlFile
	 * @return
	 * @throws Exception
	 */
	public boolean deleteExcelFile(final String destUrlFile) throws Exception
	{
		boolean result=false;
		try{
			ServletContext context = ServletActionContext.getServletContext();
			//�ϴ�Ŀ�������·��
			String destRealPath = context.getRealPath(destUrlFile);	
			//�ж��ļ��Ƿ���ڣ�������ڣ���ɾ��֮
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
	 * ����Excel�������ݿ�Insert���
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
			//�ϴ�Ŀ�������·��
			String destRealFile = context.getRealPath(destUrlFile);	
			// ���ݶ�ȡ
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
						// ���ݶ�ȡ
						 _input=new FileInputStream(destRealFile);
						 workbook = new HSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else if("XLSX".equals(fileExtName)) {
					try {
						// ���ݶ�ȡ
						 _input=new FileInputStream(destRealFile);
						 workbook = new XSSFWorkbook(_input);
					} catch (Exception e) {
						throw e;
					}
				}else{
					//�ļ����ʹ���
					return result;
				}
			}else{
				//�ļ�·������
				return result;
			}
			
			
			if (null != _input) {
				_input.close();				
			}
			sheet = workbook.getSheetAt(0);
		

			//��ÿ�ʼ�к�
			int rsRowsStart=sheet.getFirstRowNum()+ExcelDataStartRowNO;
			int rsRowsEnd=sheet.getLastRowNum();
			// �����������
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
				//	sheet.shiftRows(i, i, -1);//ɾ����ǰ��
					continue;
				}				
				//�����������
				//int colsCount = sheet.getRow(i).getPhysicalNumberOfCells();//���excel��ĳ���ж��˾ͻ������Ϊ�������
				int colsCount = columns.length;
				
				if(log.isDebugEnabled())
				{
					log.debug("########################tdList#####��"+i+"������:"+colsCount);
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
			
			
			
			//��������Ϣ��������չ�ֶ�
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
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]="String";																
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getCorp(vv);

									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res.length>0?res[k]:"";
										if("PLANNO".equals(columnDDLPara[k].toUpperCase())){
											//rowList2[j][currCol2+k]=res[2]+"-"+currMth;//res[2]�����AreaID
											rowList2[j][currCol2+k]=planNo;
										}
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getCorpFromDBXML".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;															
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getCorpFromDBXML(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getArea".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;															
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getArea(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getCustomer".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;															
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									if(log.isDebugEnabled()){
										log.debug("*************************����customer����****************");
										log.debug(vv);
									}
									String[] res=getCustomer(vv);
									if(log.isDebugEnabled()){
										log.debug("*************************����customer���****************");
										log.debug(res[0]);
										log.debug(res[1]);
									}
									
									
									
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getCustomerCorp".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;															
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getCustomerCorp(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getProduct".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getProduct(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getCarKindByKey".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									//String[] res=getCarKind(vv);	//��������
									String[] res=getCarKindByKey(vv);	//����Key����
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getCarKind".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getCarKind(vv);	//��������
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getStartStation".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getStartStation(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getGoalProvince".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getGoalProvince(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getEndStation".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getEndStation(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getTransport".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getTransport(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getRwKind".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getRwKind(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getProductcategory".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									
									String[] res=getProductcategory(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
									
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else if("getProductsecond".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getProductsecond(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;	
							}else if("getSaleDepartment".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;								
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getSaleDepartment(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;							
								
							}else if("getSender".equals(columnDDLMethod)){
								if("".equals(columnDDLInPara)){
									//û�в���������
								}else{
									String[] columnDDLInParaArr=columnDDLInPara.split("\\,");
									String columnDDLInPara0="";
									if(columnDDLInParaArr.length>0){
										columnDDLInPara0=columnDDLInParaArr[0];
									}
									//����������չ
									for (int j = 0; j < columnDDLPara.length; j++) {
										colList2[currCol2+j]=columnDDLPara[j];
										colType2[currCol2+j]=columnDataType;								
									}
									//�������ݷ��벢д������
									for (int j = 0; j < dataSize; j++) {
										String[] oo=(String[])dataArr[j];
										
										
										//��ȡ��Ҫ����
										String cellTemp = oo[Integer.parseInt(columnDDLInPara0)];
										String columnDDLInPara0Data = cellTemp==null?"":cellTemp.toString();
										String[] corpM=getCorp(columnDDLInPara0Data);
										String columnDDLInParaID=(corpM==null||corpM[0]==null)?"":corpM[0];		
									
										String vv=oo[i];
										//������չ��������ȡ��չ����
										String[] res=getSender(vv,columnDDLInParaID);
										//���θ�ֵ
										for (int k = 0; k < columnDDLPara.length; k++) {
											rowList2[j][currCol2+k]=res[k];
										}
									}
									//���µ�ǰ��
									currCol2=currCol2+columnDDLPara.length;	
								}

							}else if("getReciver".equals(columnDDLMethod)){
								if("".equals(columnDDLInPara)){
									//û�в���������
								}else{
									String[] columnDDLInParaArr=columnDDLInPara.split("\\,");
									String columnDDLInPara0="";
									if(columnDDLInParaArr.length>0){
										columnDDLInPara0=columnDDLInParaArr[0];
									}
									//����������չ
									for (int j = 0; j < columnDDLPara.length; j++) {
										colList2[currCol2+j]=columnDDLPara[j];
										colType2[currCol2+j]=columnDataType;								
									}
									//�������ݷ��벢д������
									for (int j = 0; j < dataSize; j++) {
										String[] oo=(String[])dataArr[j];
										
										//��ȡ��Ҫ����
										String cellTemp = oo[Integer.parseInt(columnDDLInPara0)];
										String columnDDLInPara0Data = cellTemp==null?"":cellTemp.toString();
										String[] customerM=getCustomer(columnDDLInPara0Data);
										String columnDDLInParaID=(customerM==null||customerM[0]==null)?"":customerM[0];	
										
										String vv=oo[i];
										//������չ��������ȡ��չ����
										String[] res=getReciver(vv,columnDDLInParaID);
										//���θ�ֵ
										for (int k = 0; k < columnDDLPara.length; k++) {
											rowList2[j][currCol2+k]=res[k];
										}
									}
									//���µ�ǰ��
									currCol2=currCol2+columnDDLPara.length;	
								}
								

							}else{
								
							}

						}
					}else if("Tree".equals(columnFormTagType)){
						//�������ε���
						String columnDDLConf=columnInfoArr[6];						
						String[] columnDDLConfArr=columnDDLConf.split("\\:");						
						if(columnDDLConfArr.length>0){
							String columnDDLMethod=columnDDLConfArr[0];
							String columnDDLPara[]=columnDDLConfArr[1].split("\\,");							
							if("getProduct".equals(columnDDLMethod)){
								//����������չ
								for (int j = 0; j < columnDDLPara.length; j++) {
									colList2[currCol2+j]=columnDDLPara[j];
									colType2[currCol2+j]=columnDataType;							
								}
								//�������ݷ��벢д������
								for (int j = 0; j < dataSize; j++) {
									String[] oo=(String[])dataArr[j];
									String vv=oo[i];
									//������չ��������ȡ��չ����
									String[] res=getProduct(vv);
									//���θ�ֵ
									for (int k = 0; k < columnDDLPara.length; k++) {
										rowList2[j][currCol2+k]=res[k];
									}
								}
								//���µ�ǰ��
								currCol2=currCol2+columnDDLPara.length;		
							}else{
								
							}
						}
					}else if("Variable".equals(columnFormTagType)){
						//�������
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
										//���θ�ֵ
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
						//�������
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
										//���θ�ֵ
										rowList2[j][currCol2+m]=variableMethod;
										
									}
								}								
								currCol2=currCol2+variablePara.length;	
							}
						}
					}else if("Label".equals(columnFormTagType)){
						//����������д��
					}//endif
					
					
					
					
					
					
				}
				
				
				
				
			}
			
			//�ϲ���colList2
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
			
			//�ϲ�����rowList2
			if(log.isDebugEnabled())
			{
				log.debug("############################����������trList.size():"+trList.size());
			}
			String[][] newRowList=new String[trList.size()][newColsCnt];
			int i1=0;
			for (Iterator iterator = trList.iterator(); iterator.hasNext();) {
				String[] _data = (String[]) iterator.next();
				
				if(log.isDebugEnabled())
				{
					log.debug("############################��ǰ��i1:"+i1);
					log.debug("############################����_data.length:"+_data.length);
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
				//log.debug("############################����Ϣ�ֽ�");
			}
			//�ֽ���������ļ��� _column,_type
			String[] _column=new String[newColList.length];
			String[] _type=new String[newColList.length];
			for(int i=0;i<newColList.length;i++){
				String[] columnInfoArr=newColList[i].split("\\###");
				if(null!=columnInfoArr && columnInfoArr.length>0)
				{
					String columnName=columnInfoArr[0];
					String columnDataType=columnInfoArr.length<4?"SKIP":columnInfoArr[3];
					_column[i]=columnName;//���������ֶ���Ϊ�գ�������������SQLʱ����˵����Ա���ƥ������
					_type[i]=columnDataType;	
				}
			}	
	
			
			
			if(log.isDebugEnabled())
			{
				//log.debug("############################����SQL");
			}			
			//����Insert���
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
//				log.debug("############################����Ϣ�ֽ�");
//			}
//			//�ֽ���������ļ��� _column,_type
//			String[] _column=new String[columns.length];
//			String[] _type=new String[columns.length];
//			for(int i=0;i<columns.length;i++){
//				String[] columnInfoArr=columns[i].split("\\###");
//				if(columnInfoArr.length>=3)
//				{
//					String columnName=columnInfoArr[0];
//					String columnDataType=columnInfoArr[3];
//					_column[i]=columnName;//���������ֶ���Ϊ�գ�������������SQLʱ����˵����Ա���ƥ������
//					_type[i]=columnDataType;	
//				}
//			}			
//			if(log.isDebugEnabled())
//			{
//				log.debug("############################����SQL");
//			}			
//			//����Insert���
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
	 * ����Insert���
	 * @param _column����
	 * @param _data������
	 * @param _type��������
	 * @param _tableName���ݿ���
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
				if ("".equals(_column[i])){//�����Ҫת��
					//////////////////////////////////////////////////////////////////

				}else{//�����ֶ�
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
     * �ж��Ƿ�Ϊ���� ���Ϸ���ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isYearMth(final String str) throws Exception
	{
		Pattern p = Pattern.compile("^\\d{4}([0-1][0-9]$)");//������ʽ
		Matcher m = p.matcher(str);//�������ַ���
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
		//System.out.println("�ֻ�������ȷ"+b);//���true
	}
    /**  
     * �ж��Ƿ�ΪLong ���Ϸ���ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isLong(final String str) throws Exception
	{
		//�������ͣ�long ������λ����64
		//��װ�ࣺjava.lang.Long
		//��Сֵ��Long.MIN_VALUE=-9223372036854775808
		//���ֵ��Long.MAX_VALUE=9223372036854775807
		String[] arr=str.split("\\.");
		//split����Ĳ����������� regex ��Ҳ���� regular expression ��������ʽ)���������������һ���򵥵ķָ��õ��ַ�������һ��������ʽ��
		//"."��������ʽ��������ĺ��壬�������ʹ�õ�ʱ��������ת�塣str(".");��Ϊ str("\\.");		
		if (arr.length>0)
		{
			//Pattern p = Pattern.compile("^-?\\d{1,19}$");//������ʽ
			Pattern p = Pattern.compile("^-?(([1-9]\\d*$)|0)");//������ʽ
			Matcher m = p.matcher(arr[0]);//�������ַ���
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
     * �ж��Ƿ�ΪInteger ���Ϸ���ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isInteger(final String str) throws Exception
	{
		//�������ͣ�int ������λ����32
		//��װ�ࣺjava.lang.Integer
		//��Сֵ��Integer.MIN_VALUE=-2147483648
		//���ֵ��Integer.MAX_VALUE=2147483647
		String[] arr=str.split("\\.");
		//split����Ĳ����������� regex ��Ҳ���� regular expression ��������ʽ)���������������һ���򵥵ķָ��õ��ַ�������һ��������ʽ��
		//"."��������ʽ��������ĺ��壬�������ʹ�õ�ʱ��������ת�塣str(".");��Ϊ str("\\.");		
		if (arr.length>0)
		{
			//Pattern p = Pattern.compile("^-?\\d{1,10}$");//������ʽ
			Pattern p = Pattern.compile("^-?(([1-9]\\d*$)|0)");//������ʽ
			Matcher m = p.matcher(arr[0]);//�������ַ���
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
     * �ж��Ƿ�ΪFloat ���Ϸ���ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isFloat(final String str) throws Exception
	{
		//�������ͣ�float ������λ����32
		//��װ�ࣺjava.lang.Float
		//��Сֵ��Float.MIN_VALUE=1.4E-45
		//���ֵ��Float.MAX_VALUE=3.4028235E38
		//Pattern p = Pattern.compile("^[+|-]?\\d*\\.?\\d*$");//������ʽ
		Pattern p = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");//������ʽ
		Matcher m = p.matcher(str);//�������ַ���
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
     * �ж��Ƿ�ΪDouble ���Ϸ���ture  
     * @param str  
     * @return boolean  
     */  
	public boolean isDouble(final String str) throws Exception
	{
		//�������ͣ�double ������λ����64
		//��װ�ࣺjava.lang.Double
		//��Сֵ��Double.MIN_VALUE=4.9E-324
		//���ֵ��Double.MAX_VALUE=1.7976931348623157E308
		//Pattern p = Pattern.compile("^[+|-]?\\d*\\.?\\d*$");//������ʽ
		Pattern p = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");//������ʽ
		Matcher m = p.matcher(str);//�������ַ���
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
	
	
//******************************************��Map�з�����***************************
	/**
	 * ��������˾
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
	 * FromMap��������ҵ
	 * @param str
	 * @return��ҵID����ҵName
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
	 * ������ҵ�������ݿ��XMLͬʱ XML��YUPRTATSM.XML
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getCorpFromDBXML(final String str) throws Exception{
		String[] result=new String[5];//GetCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO
		result=getCorpFromXML(str);
		//���XML��û����ȥ��ʯ����ҵ��
		if(null==result||null==result[0]||""==result[0]){
			result=getCorp(str);
		}
		return result;
	}
	/**
	 * ������ҵ������XML��ȡ XML��YUPRTATSM.XML
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String[] getCorpFromXML(final String str) throws Exception{
		String[] result=new String[5];//GetCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO
		//��ȥXML�в���
		if(log.isDebugEnabled()){
			log.debug("****************************************************�����ȡYUPRTATSM.XML**********");
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
	    			log.debug("***************��YUPRTATSM.XML�л�ȡ�����ǳ��ִ���**********");
	    			log.debug("�쳣��"+e.getMessage());
	            }
	    } 
		return result;
	}
	
	/**  
	 * FromMap������ͻ�
	 * @param str
	 * @return�ͻ�ID���ͻ�Name
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
	 * FromMap������ͻ����ڲ�+�ⲿ(��ʱ������)
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
	 * FromMap�������Ʒ
	 * @param str
	 * @return��ƷID������ID��С��ID����ƷName������Name��С��Name
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
	 * FromMap�����복��
	 * @param str
	 * @return����ID,����Name
	 * @throws Exception
	 */
	public String[] getCarKind(final String str)throws Exception{
		String[] result=new String[2];//GetCarKind:CARKINDID
		try{
			Map map = dicMap.getCarkindMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//�������б��ֵ
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
	 * FromMap�����복��,����Keyֱ�ӻ�ȡ
	 * @param str
	 * @return����ID,����Name
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
	 * FromMap�����뷢վ
	 * @param str
	 * @return��վID,��վName
	 * @throws Exception
	 */
	public String[] getStartStation(final String str)throws Exception{
		String[] result=new String[2];//GetStartStation:STARTSTATIONID
		try{
			Map map = dicMap.getStartstationMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//�������б��ֵ
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
	 * FromMap�������ʹ�ʡ��
	 * @param str
	 * @return�ʹ�ʡ��ID���ʹ�ʡ��Name
	 * @throws Exception
	 */
	public String[] getGoalProvince(final String str)throws Exception{
		String[] result=new String[2];//GetGoalProvince:GOALPROVINCEID
		try{
			Map map = dicMap.getGoalprovinceMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//�������б��ֵ
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
	 * FromMap�����뵽վ
	 * @param str
	 * @return��վID����վName
	 * @throws Exception
	 */
	public String[] getEndStation(final String str)throws Exception{
		String[] result=new String[2];//GetEndStation:ENDSTATIONID
		try{
			Map map = dicMap.getEndstationMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//�������б��ֵ
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
	 * FromMap�����������
	 * @param str
	 * @return������ID��������Name
	 * @throws Exception
	 */
	public String[] getTransport(final String str)throws Exception{
		String[] result=new String[2];//GetTransport:TRANSPORTID
		try{
			Map map = dicMap.getTransporterMap();
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//�������б��ֵ
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
	 * FromMap��������·Ʒ��
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
				{//�������б��ֵ
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
	 * FromMap�������Ʒ����
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
				{//�������б��ֵ
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
	 * FromMap�������ƷС��
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
				{//�������б��ֵ
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
	 * FromMap�����뷢����
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
				{//�������б��ֵ
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
	 * FromMap�������ջ���
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
				//log.debug("#######################################�ͻ���Ӧ�ջ���map:"+map);
			}
			
			
			if(null!=map){
				for (Iterator it =  map.keySet().iterator();it.hasNext();)
				{//�������б��ֵ
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
	 * FromMap���������ۻ���
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
				{//�������б��ֵ
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
	 * FromMap����ȡ�ͻ������ڿͻ����е�����
	 * @param str
	 * @return�ͻ�ID���ͻ�Name
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
