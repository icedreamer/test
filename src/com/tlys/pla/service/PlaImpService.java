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

	//***********************************SQL������**************************************	
	/**
	 * ִ��SQL��䣨����)
	 * @param sql
	 * @return
	 */
	public int executeSql(final String sql) {
		return plaImpDao.executeSql(sql);
	}
	/**
	 * ִ��SQL��䣨����)
	 * @param sql
	 * @return
	 */
	public int executeSql(final String[] sql) {
		return plaImpDao.executeSqlArray(sql);
	}
	/**
	 * ִ��SQL��䣨����)
	 * @param sql
	 * @return
	 */
	public int executeCountSql(final String sql) {
		return plaImpDao.executeCountSql(sql);
	}

	//***********************************�������**************************************
	/**
	 * 0����Ƿ�������
	 * @param tbName
	 * @return
	 */
	public boolean checkStatus(final String tbName,final String currYear,final String currMth,final String currArea,final String currCorp){
		boolean status=false;
		try{
			String sql=plaImpDao.checkStatus(tbName, currYear, currMth, currArea, currCorp);
			if(log.isDebugEnabled()){
				log.debug("****************************************************���ݿ��к��м�¼��**********");
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
				log.debug("****************************************************��֤�Ƿ���Ե���ʱ�����쳣**********");
				log.debug(ex.getMessage());
			}
			status=false;
		}			
		return status;
	}
	/**
	 * 1���渽����Ŀ¼�����ļ���ַ����
	 * 
	 * @param srcFile
	 * @param destFileName
	 * @return
	 */
	public String upFile(final File srcFile,final String srcFileName) {
		ServletContext context = ServletActionContext.getServletContext();
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssms"); 
		java.util.Date currentDTime = new java.util.Date();//�õ���ǰϵͳʱ�� 
		//��ʱ�ļ�����
		String saveFileName = formatter.format(currentDTime) +"."+CommUtils.getExt(srcFileName);
		//�ϴ�Ŀ���߼�·��
		String destUrlPath= CommUtils.getString(ATTACH_URL,"pla","/","temp", "/");
		//�ϴ�Ŀ�������·��
		String destRealPath = context.getRealPath(destUrlPath);	
		//�ϴ�Ŀ���ļ��߼�ȫ·��
		String destUrlFile = destUrlPath + saveFileName;
		//�ϴ�Ŀ���ļ�����ȫ·��
		String destRealFile = destRealPath + "/" + saveFileName;
		
		//log.debug(destRealFile);

		//�ж�Ŀ¼�Ƿ���ڣ��粻���ڣ�����֮
		File file = new File(destRealPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		//ɾ���Ѵ��ڵ��ļ�
		//File existFile = new File(destRealFile);
		//if (existFile.exists()) {
		//	existFile.delete();
		//}
		
		//ִ���ϴ�
		File destFile = new File(destRealFile);		
		if (copyFile(srcFile, destFile)) {
			return destUrlFile;
		}
		return "";
	}
	/**
	 * 2���Excel����ǰ���������������ֵ�)
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
							//�жϲ����Ƿ��п����
							boolean haveNull=false;
							for (int k = 0; k < procPara.length; k++) {
								String[] row=(String[])excelData.get(j);
								
								if(log.isDebugEnabled())
								{
									//log.debug("################################��ǰ�У�"+j);
									//log.debug("################################��ǰ�У�"+Integer.parseInt(procPara[k]));
									//log.debug("################################Row��Colsֵ��"+row.length);
									//log.debug("################################Cellֵ��"+row[Integer.parseInt(procPara[k])]);									
								}
								if(row.length>Integer.parseInt(procPara[k])){
									procParaData[k]=row[Integer.parseInt(procPara[k])];
								}
								if(null==procParaData[k]||"".equals(procParaData[k])){
									haveNull=true;
								}
							}
							if(!haveNull&&"initPlanDicDataReceiver".equals(procName)){
								//�ֵ����Ƿ��и�����¼
								boolean haveInTable=false;
								//1�жϿͻ��Ƿ����
								//����ҵ���ķ����ID		
								String[] customerM=excelImpUtil.getCustomer(procParaData[0]);
								String customerID=(customerM==null||customerM[0]==null)?"":customerM[0];
								haveInTable=(customerM!=null&&customerM[0]!=null);	
								//2�ж��ջ����Ƿ����
								if(haveInTable){
									String[] customerReceiverM=excelImpUtil.getReciver(procParaData[1], customerID);								
									haveInTable=(customerReceiverM!=null&&customerReceiverM[0]!=null);	
								}
								
								//����ֵ����û���ҵ�
								if(!haveInTable){
									if(log.isDebugEnabled())
									{
										log.debug("################################�洢�����������������"+procParaData.length);
										log.debug("################################�洢�����������1��"+procParaData[0]);
										log.debug("################################�洢�����������2��"+procParaData[1]);
									}
									Object[] objs = plaImpDao.initPlanDicDataReceiver(procParaData);
									if(log.isDebugEnabled()&&null!=objs)
									{
										log.debug("################################�洢���̷���ֵ������"+objs.length);
										log.debug("################################�洢���̷���ֵ1��"+objs[0]);
										log.debug("################################�洢���̷���ֵ2��"+objs[1]);
										log.debug("################################�洢���̷���ֵ3��"+objs[2]);
										log.debug("################################�洢���̷���ֵ4��"+objs[3]);
									}
									runCount++;
//									//����Map��־λ���Ա��´�ȡMapʱ�����±���Map
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
//								log.debug("################################ִ�д洢���̣�"+method);
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
	 * 4���޸ĺõ�Excel�ļ����뵽��
	 * @return
	 * @throws Exception
	 */
	public String importExcelToDb(final String currExcelName,final String tbName,final String[] columns,final String currYear,final String currMth,final String currArea,final String currCorp,final String importerID) throws Exception
	{
		//������Ϣ
		String res="";
		//����״̬
		boolean _errFlag=false;
		//��ӦOracle���ݱ���
		String tbOracleName=getTBOracleName(tbName);
		if(null==tbOracleName||"".equals(tbOracleName))
		{_errFlag=true;}
		//String[] columns=getTBColumnCfg(tableName);
		if(null==columns||columns.length<=0)
		{_errFlag=true;}
		
		if(_errFlag){
			res="�������󣺲���ִ��д�����ݿ����������ϵͳ�Ƿ�ʱ���������Ƿ���ȷ��";
		}else{
			//ִ�б������
			try{
				//�Ƿ���Ҫ�½�����������¼��Ϣ
				Boolean isNewRecord;
				if(plaImpDao.getLastAdjustStauts(tbName, currYear, currMth, currArea, currCorp)>0){
					isNewRecord=true;
				}else{
					isNewRecord=false;
				}
				//��ȡ��ǰ�������
				String currAdjustNumber=getCurrAdjustNumber(tbName,currYear,currMth,currArea,currCorp,isNewRecord);
				//��ȡ�ƻ�����
				String planNo=getPlanNO(tbName,currYear,currMth,currArea,currCorp,currAdjustNumber);
				
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************planNo******************");
					log.debug(planNo);
					log.debug("�Ƿ��²���:"+isNewRecord);
				}
				//��������SQL���
				String sqlMainDel=plaImpDao.getTBMainDelete(tbName,currYear,currMth,currArea,currCorp,planNo);
				//�����ϸ���SQL���
				String sqlMxDel=plaImpDao.getTBMxDelete(tbName,currYear,currMth,currArea,currCorp,planNo);
				//���������SQL���
				String sqlMain=plaImpDao.getTBMainInsert(tbName,currYear,currMth,currArea,currCorp,planNo,importerID);
				//������ϸ��SQL���
				String[] sqlMx=excelImpUtil.getImportInsertSql(currExcelName, tbOracleName, columns,currYear,currMth,currArea,currCorp,planNo);
				//����ظ����ݵ�SQL���
				String sqlMxDelMore=plaImpDao.getTBMxDeleteMore(tbName,currYear,currMth,currArea,currCorp,planNo);
				
				//��SQL�������ļ�
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss"); 
				java.util.Date currentTime = new java.util.Date();//�õ���ǰϵͳʱ�� 
				ServletContext context = ServletActionContext.getServletContext();
				//�ϴ�Ŀ�������·��
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
				
				//��������¼��
				int cntDel=0;
				//�����ϸ���¼��
				int cntDelMx=0;
				//������Ӽ�¼��
				int cntMain=0;
				//��ϸ����Ӽ�¼��
				int cntMX=0;
				//��ϸ������ظ���
				int cntDelSame=0;
				//����֮ǰִ�д洢������
				int cntBefInsert=0;
				
				//ɾ��������
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************ɾ�����������");
					log.debug(sqlMainDel);
				}
				if(null!=sqlMainDel&&!"".equals(sqlMainDel)){
					cntDel=executeSql(sqlMainDel);
				}

				//ɾ������ϸ����
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************ɾ����ϸ�������");
					log.debug(sqlMxDel);
				}
				if(null!=sqlMxDel&&!"".equals(sqlMxDel)){
					cntDelMx=executeSql(sqlMxDel);
				}

				//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
				//�������֮ǰִ�еĴ洢����
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************�������֮ǰִ�еĴ洢����");
				}
				String[] initCfg = getTBBefInsertCfg(tbName);
				if (null != initCfg && initCfg.length > 0) {
					cntBefInsert = initBefImp(initCfg,currYear,currMth,currArea,currCorp,currAdjustNumber,importerID);
				}

				
				//��������
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************��������");
					log.debug(sqlMain);
				}
				if(null!=sqlMain&&!"".equals(sqlMain)&&!isNewRecord&&("00".equals(currAdjustNumber) || "".equals(currAdjustNumber))){
					cntMain=executeSql(sqlMain);	
				}

				//������ϸ��
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************������ϸ��");
					log.debug(sqlMx);
				}
				if(null!=sqlMx&&!"".equals(sqlMx)){
					cntMX=executeSql(sqlMx);						
				}
				cntMX=sqlMx.length;

				//����ظ�
				if(log.isDebugEnabled())
				{
					log.debug("*****************************************����ظ�");
					log.debug(sqlMxDelMore);
				}
				if(null!=sqlMxDelMore&&!"".equals(sqlMxDelMore)){
					cntDelSame=executeSql(sqlMxDelMore);
				}

				
				if(cntMX>0){
					res = "�ɹ�����"+cntMX+"�����ݣ�����"+cntMain+"�����ݣ����������ϸ����"+cntDelMx+"����������ظ�����"+cntDelSame+"��";
				}else{
					res ="����ʧ�ܣ�";
				}
				
				//ִ��ɾ��Excel����
//				String _delMsg="";
//				//����֮��ɾ��ԭExcel��ʱ����
//				try{
//					if(excelImpUtil.deleteExcelFile(currExcelName)){
//						_delMsg="��ʱ�ļ�ɾ���ɹ���";
//					}else{
//						_delMsg="��ʱ�ļ�ɾ��ʧ�ܣ�";
//					}
//				}catch(Exception e)
//				{
//					_delMsg="ɾ����ʱ�ļ�ʱ�����쳣��"+e.getMessage();
//					throw e;
//				}
				
				
			}catch(Exception e)
			{
				res="����ʱ�����쳣��"+e.toString();
				throw e;
			}	
			
		}
		return res;
	}
	
	/**
	 * 5����֮ǰ��������������׼������)
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
								log.debug("################################�洢�����������������"+procParaData.length);
								log.debug("################################�洢�����������1��"+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanPadjMspstransport(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################�洢���̷���ֵ������"+objs.length);
								log.debug("################################�洢���̷���ֵ1��"+objs[0]);
								log.debug("################################�洢���̷���ֵ2��"+objs[1]);
							}
							runCount++;
						}else if("initPlanPadjMupstransport".equals(procName)){
							procParaData[0]=currArea;	
							procParaData[1]=currMth;	
							procParaData[2]=currAdjustNumber;	
							procParaData[3]=currAdjuster;	
							
							if(log.isDebugEnabled())
							{
								log.debug("################################�洢�����������������"+procParaData.length);
								log.debug("################################�洢�����������1��"+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanPadjMupstransport(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################�洢���̷���ֵ������"+objs.length);
								log.debug("################################�洢���̷���ֵ1��"+objs[0]);
								log.debug("################################�洢���̷���ֵ2��"+objs[1]);
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
	 * 5����֮��������������������)
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
								log.debug("################################�洢�����������������"+procParaData.length);
								log.debug("################################�洢�����������1��"+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanDataAtImpComplate(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################�洢���̷���ֵ������"+objs.length);
								log.debug("################################�洢���̷���ֵ1��"+objs[0]);
								log.debug("################################�洢���̷���ֵ2��"+objs[1]);
							}
							runCount++;
						}else if("initPlanGenMspstransport".equals(procName)){
							procParaData[0]=currMth;	
							if(log.isDebugEnabled())
							{
								log.debug("################################�洢�����������������"+procParaData.length);
								log.debug("################################�洢�����������1��"+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanGenMspstransport(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################�洢���̷���ֵ������"+objs.length);
								log.debug("################################�洢���̷���ֵ1��"+objs[0]);
								log.debug("################################�洢���̷���ֵ2��"+objs[1]);
							}
							runCount++;
						}else if("initPlanGenMupstransport".equals(procName)){
							procParaData[0]=currMth;	
							if(log.isDebugEnabled())
							{
								log.debug("################################�洢�����������������"+procParaData.length);
								log.debug("################################�洢�����������1��"+procParaData[0]);
							}
							Object[] objs = plaImpDao.initPlanGenMupstransport(procParaData);
							if(log.isDebugEnabled()&&null!=objs)
							{
								log.debug("################################�洢���̷���ֵ������"+objs.length);
								log.debug("################################�洢���̷���ֵ1��"+objs[0]);
								log.debug("################################�洢���̷���ֵ2��"+objs[1]);
							}
							runCount++;
						}

					}
				}
			}
		}
		return runCount;
	}
	
	//***********************************Checkҳ�����跽��**************************************
	/**
	 * ���ݴ����ȡС��Model����
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
				{//�������б��ֵ
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
	 * ����С���ȡ��ƷModel����
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
				{//�������б��ֵ
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
	 * ���ݴ���ƴ��С��Options��
	 * @param prodCateID
	 * @param selected
	 * @return
	 * @throws Exception
	 */
	public String getProdSecCateOptsByCateID(final String prodCateID,final String selected) throws Exception{
		StringBuffer _result=new StringBuffer();
		_result.append("<option value=\"\">��ѡ��С��...</option>");
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
	 * ����С��ƴ�Ӳ�ƷOptions��
	 * @param secProdCateID
	 * @param selected
	 * @return
	 * @throws Exception
	 */
	public String getProdListBySecCateID(final String secProdCateID,final String selected) throws Exception{
		StringBuffer _result=new StringBuffer();
		ArrayList list=getProdListBySecCateID(secProdCateID);
		_result.append("<option value=\"\">��ѡ���Ʒ...</option>");
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

	//***********************************�ڲ����÷���**************************************
	/**
	 * ������ݱ�Object
	 * @param tbName
	 * @return
	 */
	public Object getTBObject(final String tbName)
	{
		//Class clazz=Class.forName("com.tlys.pla.model.PlaMupstransportdet");//����������ȫ�����а�Ҫ���ϰ�
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
	 * ��ȡ���ݿ��Oracle����
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
	 * ��ȡ�ϴ���ҳ��ʾ�����
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
	 * ��ȡ�ƻ����
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
	 * ��ȡ��ǰʹ�õĵ�������
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
	
	
	//***********************************��������**************************************	
	/**
	 * XML����ȡExcel����������Ϣ
	 * @param tbName
	 * @return
	 * 
	 * 1.XML�ļ��ṹ
	 * <root>
	 * <table name="PlaMupstransportdet">
	 * <items count="18">
	 * <item id="0">XXXXXXXX</item>......
	 * ...
	 * 
	 * ��ʽ˵��
	 * ע�⣺SKIP��ʾ��������
	 * 0��ID
	 * 1������
	 * 2�����ͣ�TextBox��DropDownList��Label��TreeRoot��Tree��Variable��Sequence
	 * 			TextBox�ɱ༭�����ó���4��������ʡ��  ��ʽ�磺"REMARKS###����###TextBox###String"
	 * 			Label ����ʾ�����ɱ༭����д��)���ó���4��������ʡ��  ��ʽ�磺"###�ͻ�����###Label###String"
	 * 			DropDownList ���ó���7 ������ʡ��  ��ʽ�磺"###��ҵ###DropDownList###String###corp###DicSinocorp###getCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO"
	 * 			TreeRoot ���ó���7 ������ʡ��,ֻ�����ɱ༭��ˢ��ȡ����Tree  ��ʽ�磺"###��Ʒ����###TreeRoot###String###productcategory###DicProdcategory###getProductcategory:PRODUCTCATEGORYID"
	 * 			Tree ���ó���7 ������ʡ��  ��ʽ�磺"###��Ʒ###Tree###String###product:6,4,5:6,4,5###DicProduct###getProduct:PRODDUCTID,PRODUCTCATEGORYID,PRODUCTSECONDID:6,4,5:6,4,5"
	 * 			Variable����������ʾExcel��Ԫ����ʵ����)��ʽ�磺"###�¶�###Variable###String#########currMth:MONTH"
	 * 			Sequence���У���ʾExcel��Ԫ���Ӧ����)��ʽ�磺"###���###Sequence###Long#########SEQ_ZBC_PLA_MUPSTRANSPORTDET.nextval:ID"
	 * 3�ı����ͣ�String��Long��Integer��Float��Double��Date
	 * 4������Դ��dicMap��map����,map������Ӧ������±꣬���⴦��sender��receiver��Ҫ������carkind customer�ж�ID��ȫ�ơ����,corpfromdbxml �����ݿ��XMLͬʱ��ȡ��ҵ
	 * 5����Դ���ͣ�
	 * 6��������÷���:������:������Ҫ��Ӧ�ֶΣ�map������Ӧ������±꣬���⴦��sender��receiver
	 * 			Tree:��������������Ҫ��Ӧ�ֶΣ���Ӧ���������±�
	 * 
	 * ע����ǰϵͳ���ñ���currMth��currArea
	 */
	public String[] getTBColumnCfg(final String tbName)
	{
		if(log.isDebugEnabled()){
			log.debug("****************************************************�����ȡXML**********");
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
        			log.debug("***************��XML�л�ȡ�����ǳ��ִ���**********");
        			log.debug("table��ǩnameΪ��"+tbName);
        			log.debug("�쳣��"+e.getMessage());
                }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel��������**********");
			log.debug(tbName);
			log.debug("***************XML���ݻ�ȡExcel�����ļ�**********");
			if(null!=arr&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					log.debug(arr[i]);
				}
			}
		}
		return arr;		
		
		
//********************************************���ﱸ��ԭʼ������Ϣ*************************************
//		String[] arr=null;
//		if("plaMupstransportdet".equals(tbName))//TB_ZBC_PLA_MUPSTRANSPORTDET
//		{	
//����
//			arr=new String[22];
//			//arr[0]="ID###���###TextBox###Long";
//			arr[0]="###���###Sequence###Long#########SEQ_ZBC_PLA_MUPSTRANSPORTDET.nextval:ID";
//			arr[1]="###��ҵ###DropDownList###String###corp###DicSinocorp###getCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO";	//===����  PLANNO���⴦��
//			arr[2]="###�ͻ�###DropDownList###String###customercorp###DicCustomer###getCustomer:CUSTOMERID,COUSTOMERSHORTNAME";	 		//===����
//			arr[3]="###������###DropDownList###String###sender:1###CtlCorpsender###getSender:SENDERID:1";
//			arr[4]="###��Ʒ����###TreeRoot###String###productcategory###DicProdcategory###getProductcategory:PRODUCTCATEGORYID";
//			arr[5]="###��ƷС��###TreeRoot###String###productsecond###productsecondMap###getProductsecond:PRODUCTSECONDID";
//			arr[6]="###��Ʒ###Tree###String###product:6,4,5:6,4,5###DicProduct###getProduct:PRODDUCTID,PRODUCTCATEGORYID,PRODUCTSECONDID:6,4,5:6,4,5";				//===����	
//			arr[7]="###��·Ʒ��###DropDownList###String###rwkind###DicGoods###getRwKind:RWKINDID,RKINDNAME";	
//			arr[8]="AMOUNT###����###TextBox###Double";	
//			arr[9]="CARNUMBER###����###TextBox###Long";		
//			arr[10]="###����###DropDownList###String###carkind###DicCarkind###getCarKindByKey:CARKINDID";	 						//===����
//			arr[11]="###��վ###DropDownList###String###startstation###DicRwstation###getStartStation:STARTSTATIONID";		//===����
//			arr[12]="###�ջ���###DropDownList###String###receiver:2###CtlCorpreceiver###getReciver:RECEIVERID,RECEIVERNAME:2";	
//			//arr[13]="###�ʹ�ʡ��###DropDownList###String###goalprovince###DicProvince###getGoalProvince:GOALPROVINCEID";		//===����	
//			//arr[14]="GOALADDRESS###�ʹ��###TextBox###String";		
//			arr[13]="SKIP";
//			arr[14]="SKIP";
//			arr[15]="###��վ###DropDownList###String###endstation###DicRwstation###getEndStation:ENDSTATIONID";				//===����		 
//			//arr[16]="###������###DropDownList###String###transporter###DicTransporter###getTransport:TRANSPORTID";		 		//===����
//			arr[16]="SKIP";
//			arr[17]="SALER###�ͻ�����###TextBox###String";	
//			arr[18]="###���ۻ���###DropDownList###String###saledepartment###DicSalecorp###getSaleDepartment:SALEDEPARTMENT";	
//			arr[19]="REMARKS###����###TextBox###String";		
//			//arr[20]="MONTH###�¶ȣ�YYYYMM)###TextBox###String";
//			arr[20]="SKIP";
//			arr[21]="###�¶�###Variable###String#########currMth:MONTH";
//			//arr[22]="###����###Variable###String#########currArea:AREAID";
			
			
//����
//			arr=new String[18];
//			//arr[0]="ID###���###TextBox###Long";
//			arr[0]="###���###Sequence###Long#########SEQ_ZBC_PLA_MUPSTRANSPORTDET.nextval:ID";
//			arr[1]="###��ҵ###DropDownList###String###corp###DicSinocorp###getCorp:CORPID,CORPSHORTNAME,AREAID,AREASHORTNAME,PLANNO";	//===����  PLANNO���⴦��
//			arr[2]="###�ͻ�###DropDownList###String###customer###DicCustomer###getCustomer:CUSTOMERID,COUSTOMERSHORTNAME";	 		//===����
//			arr[3]="###������###DropDownList###String###sender:1###CtlCorpsender###getSender:SENDERID:1";
//			arr[4]="###��Ʒ����###TreeRoot###String###productcategory###DicProdcategory###getProductcategory:PRODUCTCATEGORYID";
//			arr[5]="###��ƷС��###TreeRoot###String###productsecond###productsecondMap###getProductsecond:PRODUCTSECONDID";
//			arr[6]="###��Ʒ###Tree###String###product:6,4,5:6,4,5###DicProduct###getProduct:PRODDUCTID,PRODUCTCATEGORYID,PRODUCTSECONDID:6,4,5:6,4,5";				//===����	
//			arr[7]="###��·Ʒ��###DropDownList###String###rwkind###DicGoods###getRwKind:RWKINDID,RKINDNAME";	
//			arr[8]="AMOUNT###����###TextBox###Double";	
//			arr[9]="CARNUMBER###����###TextBox###Long";		
//			arr[10]="###����###DropDownList###String###carkind###DicCarkind###getCarKindByKey:CARKINDID";	 						//===����
//			arr[11]="###��վ###DropDownList###String###startstation###DicRwstation###getStartStation:STARTSTATIONID";		//===����
//			arr[12]="###�ջ���###DropDownList###String###receiver:2###CtlCorpreceiver###getReciver:RECEIVERID,RECEIVERNAME:2";	
//			arr[13]="###��վ###DropDownList###String###endstation###DicRwstation###getEndStation:ENDSTATIONID";				//===����		 
//			arr[14]="SALER###�ͻ�����###TextBox###String";	
//			arr[15]="###���ۻ���###DropDownList###String###saledepartment###DicSalecorp###getSaleDepartment:SALEDEPARTMENT";	
//			arr[16]="REMARKS###����###TextBox###String";		
//			arr[17]="###�¶�###Variable###String#########currMth:MONTH";
//			//arr[22]="###����###Variable###String#########currArea:AREAID";
//			
//		}else{
//			
//		}
//		return arr;
		
	}
	/**
	 * XML����ȡ��������ʱ�ȴ�����¼������ֵ����������)
	 * @param tbName ����XXX��ʱ�жϣ�
	 * @return
	 * ���ã�arr[0]="�洢�������ƣ�����1������2...";
	 */
	public String[] getTBBefImpCfg(final String tbName){
		if(log.isDebugEnabled()){
			log.debug("****************************************************�����ȡXML**********");
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
    			log.debug("***************��XML�л�ȡ�����ǳ��ִ���**********");
    			log.debug("table��ǩnameΪ��"+tbName);
    			log.debug("�쳣��"+e.getMessage());
            }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel��������**********");
			log.debug(tbName);
			log.debug("***************XML���ݻ�ȡExcelԤִ�������ļ�**********");
			if(null!=arr&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					log.debug(arr[i]);
				}
			}
		}
		return arr;		
		
		
//********************************************���ﱸ��ԭʼ������Ϣ*************************************
//		String[] arr=null;
//		if("plaMupstransportdet".equals(tbName))//TB_ZBC_PLA_MUPSTRANSPORTDET
//		{	
//			arr=new String[1];
//			arr[0]="initPlanDicDataReceiver:2,12";
//		}
//		return arr;
	}
	/**
	 * XML����ȡ��������֮ǰ������¼���������������)
	 * @param tbName
	 * @return
	 */
	public String[] getTBBefInsertCfg(final String tbName){
		if(log.isDebugEnabled()){
			log.debug("****************************************************�����ȡXML**********");
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
    			log.debug("***************��XML�л�ȡ�����ǳ��ִ���**********");
    			log.debug("table��ǩnameΪ��"+tbName);
    			log.debug("�쳣��"+e.getMessage());
            }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel��������**********");
			log.debug(tbName);
			log.debug("***************XML���ݻ�ȡExcelԤִ�������ļ�**********");
			if(null!=arr&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					log.debug(arr[i]);
				}
			}
		}
		return arr;		

	}
	/**
	 * XML����ȡ��������֮������¼�����������)
	 * @param tbName
	 * @return
	 */
	public String[] getTBAftImpCfg(final String tbName){
		if(log.isDebugEnabled()){
			log.debug("****************************************************�����ȡXML**********");
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
    			log.debug("***************��XML�л�ȡ�����ǳ��ִ���**********");
    			log.debug("table��ǩnameΪ��"+tbName);
    			log.debug("�쳣��"+e.getMessage());
            }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel��������**********");
			log.debug(tbName);
			log.debug("***************XML���ݻ�ȡExcelԤִ�������ļ�**********");
			if(null!=arr&&arr.length>0){
				for (int i = 0; i < arr.length; i++) {
					log.debug(arr[i]);
				}
			}
		}
		return arr;		

	}
	/**
	 * XML����ȡ��������֮�󷵻�ҳ��
	 * @param tbName
	 * @return
	 */
	public String getTBImpRtnPage(final String tbName){
		if(log.isDebugEnabled()){
			log.debug("****************************************************�����ȡXML**********");
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
    			log.debug("***************��XML�л�ȡ�����ǳ��ִ���**********");
    			log.debug("table��ǩnameΪ��"+tbName);
    			log.debug("�쳣��"+e.getMessage());
            }
        } 

		if(log.isDebugEnabled()){
			log.debug("***************Excel��������**********");
			log.debug(tbName);
			log.debug("***************XML���ݻ�ȡExcelԤִ�������ļ�returnPage**********");
			log.debug(returnPage);
		}
		return returnPage;		
	}
	/**
	 * XML����ȡ��ҵ��Ӫ ��˾�б���XML
	 * @return
	 */
	public String[] getCorpFromXML(){
		String[] arr=null;			
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
            	arr=new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                	Element e = (Element) list.get(i);         	
					arr[i]=e.attributeValue("id")+"###"+e.getText();
				} 
            }
	    } catch (DocumentException e) { 
	            //e.printStackTrace(); 
	            if(log.isDebugEnabled()){
	    			log.debug("***************��YUPRTATSM.XML�л�ȡ�����ǳ��ִ���**********");
	    			log.debug("�쳣��"+e.getMessage());
	            }
	    } 
		return arr;
	}

}
