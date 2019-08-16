package com.tlys.pla.action;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.CommUtils;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.ExcelImpUtil;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicCustomer;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.pla.service.PlaImpService;
import com.tlys.sys.model.SysUser;

/**
 * @author sunshanh
 * 
 */
@Controller
@ParentPackage("tlys-default")
@Namespace("/pla")
public class PlaImpAction extends _BaseAction {
	@Autowired
	PlaImpService plaImpService;	
	@Autowired
	DicMap dicMap;
	@Autowired
	ExcelImpUtil excelImpUtil;
	
	protected final Log log = LogFactory.getLog(this.getClass());
	//Excel涓崟鍏冩牸涓虹┖鏃� 椤甸潰鏄剧ず淇℃伅
	protected final String ExcelNullShowStr="{绌烘暟鎹畗";
	//涓婁紶鏁版嵁骞村害
	private String currYear;
	//涓婁紶鏁版嵁鏈堜唤
	private String currMth;
	//涓婁紶鏁版嵁鎵�灞炲尯鍩�
	private String currArea;
	//涓婁紶鏁版嵁鎵�灞炰紒涓�
	private String currCorp;
	//涓婁紶鍚庤繑鍥為〉闈�
	private String returnPage;
	

	private String currIndexPageShowTags="currMth,currArea";
	// 鍖哄煙鍏徃
	private Map<String, DicAreacorp> dicAreaMap;
	// 鍐呴儴鍏徃
	private Map<String,DicSinocorp> dicCorpMap;
	//涓婁紶鏁版嵁瀵瑰簲鏁版嵁搴撶墿鐞嗚〃
	private String tableName;
	//涓婁紶鏂囦欢
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	//涓婁紶鏄惁鎴愬姛
	private boolean isUploadSuccess=false;
	//鏄惁鍏佽鎻愪氦
	private boolean CommitStatus=false;

	//鏄剧ず淇℃伅
	private String msgInfo;
	//椤甸潰鏄剧ずExcel杞琀tml
	private String excelTableStr;
	//褰撳墠Excel琛�
	private String currExcelRowNum;
	//褰撳墠Excel鍒�
	private String currExcelColNum;
	//褰撳墠Excel鍗曞厓鏍兼柊鍊�
	private String currExcelValue;
	//褰撳墠Excel鍚嶇О锛堝甫閫昏緫璺緞锛�
	private String currExcelName;

	//鍗曞厓鏍煎厓绱犺〃鍗旾D
	private String formTagID;
	//鍗曞厓鍏冪礌鏍奸粯璁ゅ��
	private String formTagSelected;
	//鍗曞厓鏍糋etDicMap鏂规硶鍚嶇О
	private String formTagDicMapName;
	//鍗曞厓鏍糋etDicMap鏂规硶鍙傛暟
	private String formTagDicMapInPara;
	//鍗曞厓鏍糋etDicMap鏂规硶杩斿洖鍙傛暟
	private String formTagDicMapOutPara;

	//鍗曞厓鏍艰〃鍗曞厓绱犵被鍨�
	private String formTagType;
	//鍗曞厓鏍艰〃鍗曞厓绱犳暟鎹被鍨�
	private String formTagDataType;

	//褰撳墠浜у搧澶х被ID
	private String currProdCateID;
	//褰撳墠浜у搧灏忕被ID
	private String currProdSecCateID;
	//褰撳墠浜у搧ID
	private String currSelected;

	//***********************************Action澶勭悊**************************************	
	/**
	 * Action:娴忚鏂囦欢
	 * @return
	 * @throws Exception
	 */
	public String index() throws Exception {
		//java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMM"); 
		java.text.SimpleDateFormat formatterYr = new java.text.SimpleDateFormat("yyyy"); 
		java.util.Date currentTime = new java.util.Date();//寰楀埌褰撳墠绯荤粺鏃堕棿 
		//String str_date1 = formatter.format(currentTime); //灏嗘棩鏈熸椂闂存牸寮忓寲 
		//String str_date2 = currentTime.toString(); //灏咲ate鍨嬫棩鏈熸椂闂磋浆鎹㈡垚瀛楃涓插舰寮� 
		currYear= formatterYr.format(currentTime); //灏嗘棩鏈熸椂闂存牸寮忓寲 
		currMth= formatter.format(currentTime); //灏嗘棩鏈熸椂闂存牸寮忓寲 
		dicAreaMap = dicMap.getAreaMap();
		
		SysUser sysUser = getCurUser();
		dicCorpMap = CommUtils.getUserCorpMap(sysUser, dicMap);
		//dicCorpMap = dicMap.getCorpMap();
		if("".equals(tableName)){
			tableName="plaMupstransportdet";
		}
		
		currIndexPageShowTags=plaImpService.getPageShowTags(tableName);
		return "index";
	}
	/**
	 * Action:鏂囦欢涓婁紶纭
	 * @return
	 * @throws Exception
	 */
	public String upload() throws Exception {
		String res = "";
		boolean _flag = false;
		isUploadSuccess=false;		
		if (testNull(uploadFileName)) {
			res += ". 璇烽�夋嫨Excel鏂囦欢锛乗r\n";
			_flag = true;
		}
		
		//String fileExtName=uploadFileName.substring(uploadFileName.length()-3).toUpperCase();
		String[] fileNameArr = uploadFileName.split("\\.");
		
		if(fileNameArr.length>1)
		{			
			String fileExtName=fileNameArr[fileNameArr.length-1].toUpperCase();
			if (!("XLS".equals(fileExtName)||"XLSX".equals(fileExtName))) {
				res += ". 涓婁紶鏂囦欢鍙兘鏄疎xcel鏂囦欢锛屾墿灞曞悕涓篨LS(Excel2003)鎴朮LSX(Excel2007)锛岃妫�鏌ユ偍鐨勬枃浠�(鎮ㄧ殑鏂囦欢鏍煎紡鏄�."+fileExtName+")锛乗r\n";
				_flag = true;
			}
		}else{
			res += ". 璇烽�夋嫨姝ｇ‘鐨凟xcel鏂囦欢锛侊紝璇锋鏌r\n";
			_flag = true;
		}
		
		
		if (testNull(tableName)) {
			res += ". 鍙傛暟閿欒锛氭暟鎹簱琛ㄥ悕锛坱ableName锛夊繀濉紒\r\n";
			_flag = true;
		}
	if (currIndexPageShowTags.indexOf("currYear") >= 0 && testNull(currYear)) {
			res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹勾浠斤紙currYear锛夊繀濉紒\r\n";
			_flag = true;
	}
	if (currIndexPageShowTags.indexOf("currMth") >= 0 && testNull(currMth)) {
			res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹湀浠斤紙currMth锛夊繀濉紒\r\n";
			_flag = true;
	}
	if (currIndexPageShowTags.indexOf("currArea") >= 0 && testNull(currArea)) {
			res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹尯鍩燂紙currArea锛夊繀濉紒\r\n";
			_flag = true;
	}
	if (currIndexPageShowTags.indexOf("currCorp") >= 0 && testNull(currCorp)) {
			res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹崟浣嶏紙currCorp锛夊繀濉紒\r\n";
			_flag = true;
	}
	
	
	//鍒ゆ柇鏄惁鍏佽涓婁紶璇ユ湀
	if(!plaImpService.checkStatus(tableName,currYear,currMth,currArea,currCorp))	{
		res = ". 璇ユ湀鏁版嵁宸茬粡鎻愪氦鎴栧凡缁忓鏍搁�氳繃锛屼笉鑳藉啀瀵煎叆锛乗r\n";
		_flag = true;
	}
	
		if (_flag) {
			res = ". 鏂囦欢涓婁紶澶辫触锛佸師鍥狅細\r\n:" + res;
		} else {
			try {
				// 涓存椂Excel璺緞
				currExcelName = plaImpService.upFile(upload, uploadFileName);
				isUploadSuccess = true;
				res += "鏂囦欢涓婁紶鎴愬姛";
			} catch (Exception e) {
				res += "涓婁紶鏂囦欢鏃跺嚭鐜板紓甯�" + e.getMessage();
				throw e;
			}
		}
		msgInfo = res;
		return "upload";
	}
	/**
	 * Action:鏂囦欢妫�鏌�
	 * @return
	 * @throws Exception
	 */
	public String check() throws Exception {
		String res = "";
		boolean _flag = false;
		CommitStatus = false;

		if (testNull(currExcelName)) {
			res += ". 鏈嶅姟绔疎xcel鏂囦欢锛坈urrExcelName锛夊繀濉紒\r\n";
			_flag = true;
		}
		if (testNull(tableName)) {
			res += ". 鍙傛暟閿欒锛氭暟鎹簱琛ㄥ悕锛坱ableName锛夊繀濉紒\r\n";
			_flag = true;
		}
	if (currIndexPageShowTags.indexOf("currYear") >= 0 && testNull(currYear)) {
			res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹勾浠斤紙currYear锛夊繀濉紒\r\n";
			_flag = true;
	}
	if (currIndexPageShowTags.indexOf("currMth") >= 0 && testNull(currMth)) {
			res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹湀浠斤紙currMth锛夊繀濉紒\r\n";
			_flag = true;
	}
	if (currIndexPageShowTags.indexOf("currArea") >= 0 && testNull(currArea)) {
			res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹尯鍩燂紙currArea锛夊繀濉紒\r\n";
			_flag = true;
	}
	if (currIndexPageShowTags.indexOf("currCorp") >= 0 && testNull(currCorp)) {
			res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹崟浣嶏紙currCorp锛夊繀濉紒\r\n";
			_flag = true;
	}

		if (_flag) {
			res = ". 鏂囦欢涓婁紶澶辫触锛佸師鍥狅細\r\n:"+res;
		}else {
			// 鑾峰彇琛ㄧ粨鏋�
			Object obj = plaImpService.getTBObject(tableName);
			// 鑾峰彇琛ㄥ垪淇℃伅
			String[] columns = plaImpService.getTBColumnCfg(tableName);
			//鑾峰彇瀵煎叆鍚庤繑鍥炵殑椤甸潰鍦板潃
			returnPage=plaImpService.getTBImpRtnPage(tableName);
			
			if (null == obj || null == columns) {
				res += ". 绯荤粺閰嶇疆閿欒锛岃妫�鏌ュ鍏ML閰嶇疆淇℃伅鏄惁鍖呭惈" + tableName + "鐨則able鑺傜偣";
			} else {
				
				//鍒ゆ柇琛屾暟鏄惁瓒呰繃闄愰
				int excelRowsCount = excelImpUtil.getExcelRowsCount(currExcelName);
				if (excelRowsCount>2000)
				{
					res += ". 瀵煎叆鏂囦欢宸茬粡鎹熷潖锛�<li>璇嗗埆鍑哄綋鍓嶆枃浠惰鏁颁负"+excelRowsCount+"琛岋紝瓒呰繃2000琛岋紝</li><li>璇锋鏌ユ枃浠舵槸鍚︽崯鍧忥紝</li><li>鎮ㄤ篃鍙互鏂板缓涓�涓狤xcel鏂囦欢锛岀劧鍚庝粎灏嗗師Excel鏂囦欢涓湁鏁版嵁鐨勮澶嶅埗鍒版柊Excel涓紝鍐嶄笂浼犺瘯璇曪紒</li><li>濡傛灉纭疄瑕佷笂浼�2000琛屼互涓婄殑鏁版嵁璇蜂笌杞欢寮�鍙戦」鐩粍鑱旂郴锛�</li>";
				}else{
				
					// 妫�鏌xcel鏁版嵁鍓嶆墍鍋氬伐浣滐紙鏇存柊瀛楀吀锛�
					try{
						if(log.isDebugEnabled())
						{
							log.debug("##################################鏍￠獙涔嬪墠澶勭悊瀛樺偍杩囩▼");
						}	
						String[] initCfg = plaImpService.getTBBefImpCfg(tableName);
						if (null != initCfg && initCfg.length > 0) {
							int runCount = plaImpService.initBefImp(initCfg,
									currExcelName);
							if (runCount > 0) {
								// 鏇存柊Map鏍囧織浣嶏紝浠ヤ究涓嬫鍙朚ap鏃跺厛閲嶆柊淇濆瓨Map
								dicMap.dicAlter("dicCustomer");
								dicMap.dicAlter("dicSinocorp");
								dicMap.dicAlter("ctlCorpreceiver");
								dicMap.dicAlter("ctlCustomerreceiver");
							}
						}
		
						// 妫�鏌xcel鏁版嵁骞惰幏寰楁鏌ョ粨鏋�
						if(log.isDebugEnabled())
						{
							log.debug("##################################鏍￠獙杩囩▼");
						}	
						Map map = excelImpUtil.checkExcelData(currExcelName, obj,
								columns, currYear, currMth, currArea,currCorp);
						CommitStatus = new Boolean(map.get("CommitStatus").toString());
		
						Object excelObj = map.get("List");
						if (!CommitStatus) {
							res += ". 缁忔牎楠岋紝鏁版嵁涓嶅尮閰嶏紝闇�瑕佷慨鏀�";
						} else {
							res += ". 鏁版嵁鍙互瀵煎叆";
						}
						if(log.isDebugEnabled())
						{
							log.debug("##################################鐢熸垚鏍￠獙HTML浠ｇ爜");
						}	
						excelTableStr = getDataTable(!CommitStatus, columns, excelObj);
					}catch(Exception e){
						if(log.isDebugEnabled()){
							log.debug("################################妫�鏌ユ枃浠舵椂鍊欏嚭鐜伴敊璇紒");
							log.debug("################################"+e.getMessage());
						}
					}
				}
			}
		}
		
		msgInfo = res;
		return "check";
	}
	
	


	//***********************************AJAX澶勭悊**************************************	
	/**
	 * Action:AJAX鏋勫缓Excel鏍￠獙缁撴灉鐨凥tml涓�
	 * @return
	 * @throws Exception
	 */
	public String getDataTable(final boolean _editAllEnable,final String[] columns,final Object excelObj) throws Exception
	{
		Class c=dicMap.getClass();		
		ArrayList dataTableInfoList=(ArrayList)excelObj;
		Object[] _dataTable=dataTableInfoList.toArray();		
		StringBuilder _result=new StringBuilder();
		try
		{
			//鍒濆鍖栬〃澶�
			_result.append("<tr>");
			if(columns.length>0)
			{
				for(int i=0;i<columns.length;i++)
				{			
					String _columnStr=columns[i].toString();
					String[] _columnStrArr=_columnStr.split("\\###");	
					if(_columnStrArr.length>=4&&_columnStrArr[0]!="SKIP")
					{
						_result.append("<th class=\"cellTh\" nowrap>");
						_result.append(_columnStrArr[1]);
						_result.append("</th>");
					}
					if(i==columns.length-1){
						_result.append("<th class=\"cellTh\" nowrap>");
						_result.append("鎿嶄綔");
						_result.append("</th>");
					}
				}
			}else{
				_result.append("<td>娌℃湁鎵惧埌琛ㄩ厤缃枃浠讹紒</td>");
			}
			_result.append("</tr>");
			
			//鍒濆鍖栨暟鎹�
			for(int i=0;i<_dataTable.length;i++)
			{
				ArrayList dataRowInfoList=(ArrayList)_dataTable[i];
				Object[] _dataRow=dataRowInfoList.toArray();
				
				if(i%2==0)
				{
					_result.append("<tr class=\"odd\" id=\"tr_"+i+"\">");
				}else{
					_result.append("<tr class=\"even\" id=\"tr_"+i+"\">");
				}
				for(int j=0;j<_dataRow.length;j++)
				{
					String _dataStr=_dataRow[j].toString();
					String[] _dataStrArr=_dataStr.split("\\###");
	
					String _dataID=i+"_"+j;
					String _dataValue=_dataStrArr[2];
					String _dataRowNum=_dataStrArr[0];
					String _dataColNum=_dataStrArr[1];
					String _dataType=_dataStrArr[3];
					String _dataErrMsg=_dataStrArr.length>=5?_dataStrArr[4]:"";
					
					//鍐嶆璇诲垪灞炴�э紝鐢ㄤ簬鑾峰彇瀛楀吀
					String _columnStr=columns[j].toString();
					String[] _columnStrArr=_columnStr.split("\\###");
					//Object _dropDownListObj=null;
					String _formTagType=(_columnStrArr.length>=3&&null!=_columnStrArr[2]&&!"".equals(_columnStrArr[2]))?_columnStrArr[2]:"";
					String _formTagDataType=(_columnStrArr.length>=4&&null!=_columnStrArr[3]&&!"".equals(_columnStrArr[3]))?_columnStrArr[3]:"";
					
					//String _dicMapName=(_columnStrArr.length>=5&&null!=_columnStrArr[4]&&!"".equals(_columnStrArr[4]))?_columnStrArr[4]:"";

///////////////////////////////////////鐗规畩澶勭悊
					String _dicMapNameStr=(_columnStrArr.length>=5&&null!=_columnStrArr[4]&&!"".equals(_columnStrArr[4]))?_columnStrArr[4]:"";
					String[] _dicMapNameArr=_dicMapNameStr.split("\\:");
					String _dicMapName=(null!=_dicMapNameArr&&_dicMapNameArr.length>=1)?_dicMapNameArr[0]:"";
					String _dicMapInPara=(null!=_dicMapNameArr&&_dicMapNameArr.length>=2)?_dicMapNameArr[1]:"";
					String _dicMapOutPara=(null!=_dicMapNameArr&&_dicMapNameArr.length>=3)?_dicMapNameArr[2]:"";
					String[] _dicMapInParaArr=_dicMapInPara.split("\\,");
					String[] _dicMapOutParaArr=_dicMapInPara.split("\\,");
					String _dicMapInParaData="";
					//String _dicMapOutParaData="";
					if(!"".equals(_dicMapInPara)&&null!=_dicMapInParaArr&&_dicMapInParaArr.length>0)
					{
						for (int k = 0; k < _dicMapInParaArr.length; k++) {
							if(k>0){
								_dicMapInParaData+=",";
							}
							_dicMapInParaData+=_dataRow[Integer.parseInt(_dicMapInParaArr[k])].toString().split("\\###")[2];;
						}
					}
//					if(!"".equals(_dicMapOutPara)&&null!=_dicMapOutParaArr&&_dicMapOutParaArr.length>0)
//					{
//						for (int k = 0; k < _dicMapOutParaArr.length; k++) {
//							if(k>0){
//								_dicMapOutParaData+=",";
//							}
//							_dicMapOutParaData+=_dataRow[Integer.parseInt(_dicMapOutParaArr[k])].toString().split("\\###")[2];;
//						}
//					}

					boolean _editCellEnable=!"Label".equals(_formTagType)&&!"TreeRoot".equals(_formTagType)&&!"Variable".equals(_formTagType)&&!"Sequence".equals(_formTagType);
					if(!"02".equals(_dataType))
					{//02涓鸿鍒楁槸榛樿璺宠繃涓嶅鍏ョ殑鏃跺��
						_result.append("<td class=\"cellTd\" nowrap>");
						_result.append("<div id=\"b_"+_dataID+"\" ");
						_result.append(" style=\"display:block;\" ");
						_result.append(" class=\"" + ("0".equals(_dataStrArr[3].substring(0,1)) ? "text_right" : "text_error") + "\" ");
						//if(!"Label".equals(_formTagType)&&!"TreeRoot".equals(_formTagType)&&!"Variable".equals(_formTagType)&&!"Sequence".equals(_formTagType)){
						if(_editAllEnable&&_editCellEnable){
							//濡傛灉鍏佽缂栬緫
							_result.append(" ondblclick=\"javascript:EditOn('" + i + "','" + j + "','"+_dataValue+"','"+_formTagType+"','"+_formTagDataType+"','"+_dicMapName+"','"+_dicMapInParaData+"','"+_dicMapOutPara+"','" + _dataRowNum + "','" + _dataColNum + "','"+currExcelName+"')\" ");
						}
						_result.append(" alt=\""+_dataErrMsg+"\" title=\""+_dataErrMsg+"\" ");
						_result.append(">");
						//鍗曞厓鏍兼樉绀哄唴瀹�
						if("".equals(_dataValue))
						{
							_result.append(ExcelNullShowStr);
						}else{
							_result.append(_dataValue);
						}
						_result.append("</div>");
		
						
						
						if(_editAllEnable&&_editCellEnable){
							//缂栬緫妗嗛殣钘忓眰
							_result.append("<div id=\"e_" + _dataID + "\"");
							_result.append(" style=\"display:none;\"");
							_result.append(">" );	
							_result.append("璇风◢鍊�.");
							_result.append("<input type=\"button\" onclick=\"javascript:CancleEdit('" + i + "','" + j + "')\" class=\"btn_value_cancle\" value=\"閫�\">");
							_result.append("</div>");
						}
						
						_result.append("&nbsp;</td>");
					}
					
					if(j==_dataRow.length-1){
						if(_editAllEnable){
							_result.append("<td>");
							_result.append("<input type=\"button\" onclick=\"javascript:DeleteRow('"+currExcelName+"','" + _dataRowNum + "','tr_"+i+"')\" class=\"btn_value_delete\" value=\"鍒燶\">");
							_result.append("&nbsp;</td>");
						}else{
							_result.append("<td>&nbsp;</td>");
						}
						
					}
				}
				_result.append("</tr>");
			}
		} catch (Exception e) {
			throw e;
		}
		
		return _result.toString();
		
	}
	/**
	 * Action:AJAX鏋勫缓椤甸潰琛ㄥ崟鍏冪礌
	 * @return
	 * @throws Exception
	 */
	public String getFormTagHtml() throws Exception{
		//formTagID,formTagSelected,formTagDicMapName,formTagType
		boolean _flag=false;
		if(null==formTagID||"".equals(formTagID)||"undefined".equals(formTagID))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氳〃鍗曞厓绱營D鍚嶇О锛坒ormTagID锛夊繀濉紒");_flag=true;}
		//if(null==formTagSelected||"".equals(formTagSelected)||"undefined".equals(formTagSelected))
		//{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氳〃鍗曞厓绱犻�変腑椤癸紙formTagSelected锛夊繀濉紒");_flag=true;}
		if(null==formTagType||"".equals(formTagType)||"undefined".equals(formTagType))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氳〃鍗曞厓绱犵被鍨嬶紙formTagType锛夊繀濉紒");_flag=true;}
		if(null==formTagDataType||"".equals(formTagDataType)||"undefined".equals(formTagDataType))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氳〃鍗曞厓绱犳暟鎹簮Map鍚嶅瓧锛坒ormTagDataType锛夊繀濉紒");_flag=true;}
		
		if("DropDownList".equals(formTagType)||"Tree".equals(formTagType))
		{
			if(null==formTagDicMapName||"".equals(formTagDicMapName)||"undefined".equals(formTagDicMapName))
			{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氳〃鍗曞厓绱犳暟鎹簮Map鍚嶅瓧锛坒ormTagDicMapName锛夊繀濉紒");_flag=true;}
			
///////////////////////////////////////鐗规畩澶勭悊
			if("sender".equals(formTagDicMapName)&&"".equals(formTagDicMapInPara))
			{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氳〃鍗曞厓绱犳暟鎹簮GetSenderMap鎵�闇�杈撳叆鍙傛暟浼佷笟蹇呭～锛�");_flag=true;}		
			if("receiver".equals(formTagDicMapName)&&"".equals(formTagDicMapInPara))
			{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氳〃鍗曞厓绱犳暟鎹簮GetReceiverMap鎵�闇�杈撳叆鍙傛暟浼佷笟蹇呭～锛�");_flag=true;}	
			
			if("Tree".equals(formTagType)&&("".equals(formTagDicMapOutPara)||"".equals(formTagDicMapInPara)))
			{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氳〃鍗曞厓绱燭ree鏁版嵁婧愭墍闇�杈撳叆杈撳嚭鍙傛暟蹇呭～锛�");_flag=true;}	
			
		}

		
		if(!_flag)
		{		
			if("DropDownList".equals(formTagType))
			{//涓嬫媺妗嗘瀯寤�
				
				Object _dropDownListObj=null;
				String _column = CommUtils.getString(formTagDicMapName.substring(0,1).toUpperCase(), formTagDicMapName.substring(1).toLowerCase());
				//鑾峰彇get鏂规硶鍚嶇О
				String methodNameOfGet = CommUtils.getString("get", _column,"Map");
				// getMap灞炴�ф柟娉�
				try{
					Class c=dicMap.getClass();
//					Method _methodOfGetView = c.getMethod(methodNameOfGet, null);						
//					_dropDownListObj=_methodOfGetView.invoke(dicMap, null);
					
///////////////////////////////////////鐗规畩澶勭悊
					
					Method _methodOfGetView;
					
					if("sender".equals(formTagDicMapName)){
						//formTagDicMapInPara------浼佷笟涓枃鍚嶇О
						String[] paraDataArr=formTagDicMapInPara.split("\\,");
						String paraData0="";
						if(paraDataArr.length>0){
							paraData0=paraDataArr[0];
						}
						//灏嗕紒涓氫腑鏂囩炕璇戞垚ID
						String[] corpM=excelImpUtil.getCorp(paraData0);
						String formTagDicMapInParaID=(corpM==null||corpM[0]==null)?"":corpM[0];		
						
						_methodOfGetView = c.getMethod(methodNameOfGet, String.class);						
						_dropDownListObj=_methodOfGetView.invoke(dicMap, formTagDicMapInParaID);
					}else if("receiver".equals(formTagDicMapName)){
						//formTagDicMapInPara------浼佷笟涓枃鍚嶇О
						String[] paraDataArr=formTagDicMapInPara.split("\\,");
						String paraData0="";
						if(paraDataArr.length>0){
							paraData0=paraDataArr[0];
						}
						//灏嗕紒涓氫腑鏂囩炕璇戞垚ID		
						String[] customerM=excelImpUtil.getCustomer(paraData0);
						String formTagDicMapInParaID=(customerM==null||customerM[0]==null)?"":customerM[0];	
						
						_methodOfGetView = c.getMethod(methodNameOfGet, String.class);						
						_dropDownListObj=_methodOfGetView.invoke(dicMap, formTagDicMapInParaID);
					}else if("corpfromdbxml".equals(formTagDicMapName)){
						_methodOfGetView = c.getMethod("getCorpMap", null);						
						_dropDownListObj=_methodOfGetView.invoke(dicMap, null);
					}else{		
						_methodOfGetView = c.getMethod(methodNameOfGet, null);						
						_dropDownListObj=_methodOfGetView.invoke(dicMap, null);
					}

					if(null!=_dropDownListObj){
						StringBuilder _result=new StringBuilder();
						_result.append("<select id=\""+formTagID+"\" name=\""+formTagID+"\"");
						//_result.append(" onChange=\"xx()\" ");
						_result.append(">");
						Map map=(Map)_dropDownListObj;
						for (Iterator it =  map.keySet().iterator();it.hasNext();)
						{//缁欎笅鎷夊垪琛ㄦ璧嬪��
							Object key = it.next();
							//System.out.println( key+"="+ map.get(key));
							Object _keyValueObj=map.get(key);
//							//String _keyVlaueStr=key.toString();//鏆傛椂娴嬭瘯浣跨敤
//							String _keyVlaueStr=_keyValueObj.toString();//DicMap涓噸鍐欎簡toString()鏂规硶锛岃繑鍥炵殑鏄悕绉�	
							
///////////////////////////////////////鐗规畩澶勭悊carkind customer
							String _keyVlaueStr="";
							String _keyValueStr2="";
							if("carkind".equals(formTagDicMapName)){
								_keyVlaueStr=key.toString();//鐩存帴鏄剧ずID鍖归厤
							}else if("customer".equals(formTagDicMapName)){
								DicCustomer customerT=(DicCustomer)_keyValueObj;
								_keyVlaueStr=customerT.getFullname();
								_keyValueStr2=customerT.getShortname();
							}else{
								_keyVlaueStr=_keyValueObj.toString();//DicMap涓噸鍐欎簡toString()鏂规硶锛岃繑鍥炵殑鏄悕绉�	
							}
							
							
							
							_result.append("<option ");
							_result.append(" value=\""+key+"\"");
							
//							if(log.isDebugEnabled())
//							{
//								log.debug("############################_keyVlaueStr:"+_keyVlaueStr);
//								log.debug("############################formTagSelected:"+formTagSelected);
//								log.debug("############################_keyVlaueStr.equals(formTagSelected):"+_keyVlaueStr.equals(formTagSelected));
//							}
							
							if(_keyVlaueStr.equals(formTagSelected)||_keyValueStr2.equals(formTagSelected))
							{
								_result.append(" selected");
							}
							_result.append(">");
							_result.append(_keyVlaueStr);
							//_result.append(formTagSelected);
							_result.append("</option>");
						}
						
						
///////////////////////////////////////鐗规畩澶勭悊corpfromdbxml
						//濡傛灉鏄璇诲彇鏁版嵁搴撲腑鐨勪紒涓氾紝鍚屾椂璇诲彇XML閰嶇疆涓殑浼佷笟鍒欏鍔犳墽琛岋細
						if("corpfromdbxml".equals(formTagDicMapName)){
							String strxml[]=plaImpService.getCorpFromXML();
							if(strxml.length>0){
								for (int i = 0; i < strxml.length; i++) {
									String[] strArr=strxml[i].split("\\###");
									if(strArr.length>=2){
										_result.append("<option ");
										_result.append(" value=\""+strArr[0]+"\"");
										if(strArr[1].equals(formTagSelected))
										{
											_result.append(" selected");
										}
										_result.append(">");
										_result.append(strArr[1]);
										_result.append("</option>");
									}
								}
							}
						}
						
				        _result.append("</select>");
				        msg = new Msg(Msg.SUCCESS,_result.toString());
					}else{
						msg = new Msg(Msg.FAILURE,"涓嬫媺妗嗘暟鎹簮涓虹┖锛�");
					}
			        
				} catch (Exception e) {
					msg = new Msg(Msg.FAILURE,"鐢熸垚琛ㄥ崟鍏冪礌涓嬫媺妗嗘椂鍑虹幇閿欒");
					throw e;
				}
			}else if("Tree".equals(formTagType))
			{//鏍戝眰
				if("product".equals(formTagDicMapName)){
					String ProdName="";
					String ProdID="";
					String ProdCateName="";
					String ProdCateID="";
					String ProdSecondCateName="";
					String ProdSecondCateID="";
					
//鐩存帴鏍规嵁浜у搧鑾峰彇澶х被灏忕被锛屼笉鍙傜収Excel涓殑澶х被灏忕被
//					try{
//						Map prodMap=dicMap.getProductMap();
//						if(null!=prodMap){
//							for (Iterator it =  prodMap.keySet().iterator();it.hasNext();){
//								Object key = it.next();
//								DicProduct obj=(DicProduct)prodMap.get(key);
//								if(formTagSelected.equals(obj.getShortname()))
//								{
//									ProdName=formTagSelected;
//									ProdID=key.toString();
//									ProdCateID=obj.getCategoryid();
//									ProdSecondCateID=obj.getSecondkindid();
//									break;
//								}
//							}							
//						}
//					} catch (Exception e) {
//						msg = new Msg(Msg.FAILURE,"鐢熸垚琛ㄥ崟鍏冪礌"+formTagDicMapName+" "+formTagType+"鏃跺嚭鐜伴敊璇�"+"鑾峰彇浜у搧淇℃伅閿欒"+e.getMessage());
//						throw e;
//					}
					
					//澶х被灏忕被涔熷弬鐓xcel涓殑鏁版嵁锛屼笉渚濊禆浜庝骇鍝�
					try{
						String[] inParaDataArr=formTagDicMapInPara.split("\\,");
						if(inParaDataArr.length>=3){
							ProdName=inParaDataArr[0];
							ProdCateName=inParaDataArr[1];
							ProdSecondCateName=inParaDataArr[2];
						}
						String[] prodArr=excelImpUtil.getProduct(ProdName);
						String[] prodCateArr=excelImpUtil.getProductcategory(ProdCateName);
						String[] prodSecondCateArr=excelImpUtil.getProductsecond(ProdSecondCateName);
						if(null!=prodArr&&prodArr.length>0&&null!=prodArr[0]){
							ProdID=prodArr[0];
						}
						if(null!=prodCateArr&&prodCateArr.length>0&&null!=prodCateArr[0]){
							ProdCateID=prodCateArr[0];
						}
						if(null!=prodSecondCateArr&&prodSecondCateArr.length>0&&null!=prodSecondCateArr[0]){
							ProdSecondCateID=prodSecondCateArr[0];
						}
					} catch (Exception e) {
						msg = new Msg(Msg.FAILURE,"鐢熸垚琛ㄥ崟鍏冪礌"+formTagDicMapName+" "+formTagType+"鏃跺嚭鐜伴敊璇�"+"鑾峰彇浜у搧淇℃伅閿欒"+e.getMessage());
						throw e;
					}
					
					
					
					if(log.isDebugEnabled()){
						log.debug("############################ProdName:"+ProdName);
						log.debug("############################ProdID:"+ProdID);
						log.debug("############################ProdCateID:"+ProdCateID);
						log.debug("############################ProdSecondCateID:"+ProdSecondCateID);
					}
					
					StringBuilder _result=new StringBuilder();		
					
					//鏋勫缓澶х被涓嬫媺
					_result.append("<select id=\""+formTagID+"_big\" name=\""+formTagID+"_big\"");
					_result.append(" onChange=\"getTreeProdSecondCate('"+formTagID+"_small',this.options[this.selectedIndex].value,'')\" ");
					_result.append(">");
					_result.append("<option value=\"\">璇烽�夋嫨澶х被...</option>");
					try{
						Map map=dicMap.getProductcategoryMap();									
						if(null!=map){							
							for (Iterator it =  map.keySet().iterator();it.hasNext();)
							{//缁欎笅鎷夊垪琛ㄦ璧嬪��
								Object key = it.next();
								Object _keyValueObj=map.get(key);
								String _keyVlaueStr=_keyValueObj.toString();//DicMap涓噸鍐欎簡toString()鏂规硶锛岃繑鍥炵殑鏄悕绉�	
								_result.append("<option value=\""+key+"\"");
								if(key.equals(ProdCateID))
								{
									_result.append(" selected");
								}
								_result.append(">");
								_result.append(_keyVlaueStr);
								_result.append("</option>");
							}
					       
					        msg = new Msg(Msg.SUCCESS,_result.toString());
						}else{
							msg = new Msg(Msg.FAILURE,"鏍戝舰鎺т欢澶х被涓嬫媺妗嗘暟鎹簮涓虹┖锛�");
						}
					} catch (Exception e) {
						msg = new Msg(Msg.FAILURE,"鐢熸垚琛ㄥ崟鍏冪礌"+formTagDicMapName+" "+formTagType+"鏃跺嚭鐜伴敊璇�"+"鐢熸垚澶х被閿欒");
						throw e;
					}
					 _result.append("</select>");
					 
					//鏋勫缓灏忕被涓嬫媺
					_result.append("<select id=\""+formTagID+"_small\" name=\""+formTagID+"_small\"");
			        _result.append(" onChange=\"getTreeProdList('"+formTagID+"',this.options[this.selectedIndex].value,'')\" ");
			        _result.append(">");
			        try{
			        	_result.append(plaImpService.getProdSecCateOptsByCateID(ProdCateID,ProdSecondCateID));
					}catch(Exception e){
						_result.append("<option>鑾峰彇灏忕被寮傚父"+e.getMessage()+"</option>");	
						throw e;
					}
					_result.append("</select>");
					
					//鏋勫缓浜у搧涓嬫媺
					 _result.append("<select id=\""+formTagID+"\" name=\""+formTagID+"\">");
					 try{
						 _result.append(plaImpService.getProdListBySecCateID(ProdSecondCateID,ProdID));
					}catch(Exception e){
							_result.append("<option>鑾峰彇浜у搧寮傚父"+e.getMessage()+"</option>");
							throw e;
					}
					_result.append("</select>");
					msg = new Msg(Msg.SUCCESS,_result.toString());
				}else{
					msg = new Msg(Msg.FAILURE,"鐢熸垚琛ㄥ崟鍏冪礌"+formTagType+"鏃跺嚭鐜伴敊璇�,绯荤粺鏈畾涔�"+formTagDicMapName+"绫诲瀷鏍�");
				}
				
				
				
				
			}else if("TextBox".equals(formTagType))
			{//鏂囨湰妗嗘瀯寤�
				try{
					StringBuilder _result=new StringBuilder();
					_result.append("<input id=\""+formTagID+"\" type=\"text\" class=\"btn_value_text\" value=\""+formTagSelected+"\" " );                
					if("Long".equals(formTagDataType)||"Integer".equals(formTagDataType))
					{//鏁存暟
						_result.append(" onkeyup=\"value=value.replace(/[^\\d]/g,'') \" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d]/g,''))\" ");
					}else if("Double".equals(formTagDataType)||"Float".equals(formTagDataType))
					{//灏忔暟
						_result.append(" onkeyup=\"if(isNaN(value))execCommand('undo')\" onafterpaste=\"if(isNaN(value))execCommand('undo')\" ");
					}else if("DateTime".equals(formTagDataType))
					{//鏃ユ湡绫诲瀷
					//	_result.append(" onkeyup=\"value=value.replace(/[^\\d{4}([0-1][0-9]$)]/g,'') \" onbeforepaste=\"clipboardData.setData('text',clipboardData.getData('text').replace(/[^\\d{4}([0-1][0-9]$)]/g,''))\" ");
						_result.append(" class=\"Wdate\" onfocus=\"WdatePicker({dateFmt:'yyyyMM'})\"");				
					}
					_result.append(">");
					
					msg = new Msg(Msg.SUCCESS,_result.toString());
				} catch (Exception e) {
					msg = new Msg(Msg.FAILURE,"鐢熸垚琛ㄥ崟鍏冪礌鏂囨湰妗嗘椂鍑虹幇閿欒");
					throw e;
				}
			}else if("Label".equals(formTagType)||"TreeRoot".equals(formTagType)||"Variable".equals(formTagType)||"Sequence".equals(formTagType))
			{//涓嶈兘鐐瑰嚮鐨凜ell闅愯棌灞�
				try{
					StringBuilder _result=new StringBuilder();
					_result.append("<span>");
					_result.append(formTagSelected );   
					_result.append("<input id=\""+formTagID+"\" type=\"hidden\" class=\"btn_value_text\" value=\""+formTagSelected+"\" >" );   
					_result.append("</span>");					
					msg = new Msg(Msg.SUCCESS,_result.toString());
				} catch (Exception e) {
					msg = new Msg(Msg.FAILURE,"鐢熸垚琛ㄥ崟鍏冪礌"+formTagType+"鏃跺嚭鐜伴敊璇�");
					throw e;
				}
			}else{
				msg = new Msg(Msg.FAILURE,"琛ㄥ崟鍏冪礌绫诲瀷閿欒");
			}
			
			
			
		}

		return MSG;
	}
	/**
	 * Action:AJAX鏍规嵁澶х被鎷兼帴灏忕被Options涓�
	 * @return
	 * @throws Exception
	 */
	public String getSelProdSecCateOpts() throws Exception{
		boolean _flag=false;
		if(null==currProdCateID||"".equals(currProdCateID)||"undefined".equals(currProdCateID))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氬ぇ绫籌D锛坈urrProdCateID锛夊繀濉紒");_flag=true;}
		if(!_flag)
		{	
			try{
				msg = new Msg(Msg.SUCCESS,plaImpService.getProdSecCateOptsByCateID(currProdCateID,currSelected));
			}catch(Exception e){
				msg = new Msg(Msg.FAILURE,"<option>鑾峰彇灏忕被寮傚父"+e.getMessage()+"</option>");	
				throw e;
			}
		}
		return MSG;
	}
	/**
	 * Action:AJAX鏍规嵁灏忕被鎷兼帴浜у搧Options涓�
	 * @return
	 * @throws Exception
	 */
	public String getSelProdListOpts() throws Exception{
		boolean _flag=false;
		if(null==currProdSecCateID||"".equals(currProdSecCateID)||"undefined".equals(currProdSecCateID))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氬皬绫籌D锛坈urrProdSecCateID锛夊繀濉紒");_flag=true;}
		if(!_flag)
		{		
			try{
				msg = new Msg(Msg.SUCCESS,plaImpService.getProdListBySecCateID(currProdSecCateID,currSelected));
			}catch(Exception e){
				msg = new Msg(Msg.FAILURE,"<option>鑾峰彇浜у搧寮傚父"+e.getMessage()+"</option>");	
				throw e;
			}
		}
		return MSG;
	}
	/**
	 * Action:AJAX淇濆瓨Excel鍗曞厓鏍间慨鏀�
	 * @return
	 * @throws Exception
	 */
	public String saveExcelCellValue() throws Exception
	{
		boolean _flag=false;
		if(null==currExcelRowNum||"".equals(currExcelRowNum)||"undefined".equals(currExcelRowNum))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氬綋鍓嶈锛坈urrExcelName锛夊繀濉紒");_flag=true;}
		if(null==currExcelColNum||"".equals(currExcelColNum)||"undefined".equals(currExcelColNum))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氬綋鍓嶅垪锛坈urrExcelColNum锛夊繀濉紒");_flag=true;}
		if(null==currExcelName||"".equals(currExcelName)||"undefined".equals(currExcelName))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氭湇鍔＄Excel鏂囦欢锛坈urrExcelName锛夊繀濉紒");_flag=true;}

		if(!_flag)
		{
			if(ExcelNullShowStr.equals(currExcelValue)){
				currExcelValue="";
			}
			
			//鎵ц淇濆瓨鎿嶄綔
			try{
				if(excelImpUtil.saveExcelCellEdit(currExcelRowNum, currExcelColNum, currExcelValue, currExcelName)){
					msg = new Msg(Msg.SUCCESS,"淇濆瓨鎴愬姛锛�");
				}else{
					msg = new Msg(Msg.FAILURE,"淇濆瓨澶辫触锛�");
				}
			}catch(Exception e)
			{
				msg = new Msg(Msg.FAILURE,"淇濆瓨鏃跺嚭鐜板紓甯革紒"+e.getMessage());
				throw e;
			}			
		}
		return MSG;
	}
	/**
	 * Action:AJAX鍒犻櫎Excel琛�
	 * @return
	 */
	public String deleteExcelRow() throws Exception{
		boolean _flag=false;
		if(null==currExcelRowNum||"".equals(currExcelRowNum)||"undefined".equals(currExcelRowNum))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氬綋鍓嶈锛坈urrExcelName锛夊繀濉紒");_flag=true;}
		if(null==currExcelName||"".equals(currExcelName)||"undefined".equals(currExcelName))
		{msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛氭湇鍔＄Excel鏂囦欢锛坈urrExcelName锛夊繀濉紒");_flag=true;}

		if(!_flag)
		{
			//鎵ц鍒犻櫎鎿嶄綔
			try{
				if(excelImpUtil.deleteExcelRow(currExcelRowNum, currExcelName)){
					msg = new Msg(Msg.SUCCESS,"鍒犻櫎鎴愬姛锛�");
				}else{
					msg = new Msg(Msg.FAILURE,"鍒犻櫎澶辫触锛�");
				}
			}catch(Exception e)
			{
				msg = new Msg(Msg.FAILURE,"鍒犻櫎鏃跺嚭鐜板紓甯革紒"+e.getMessage());
				throw e;
			}			
		}
		return MSG;
	}
	/**
	 * Action:AJAX灏嗕慨鏀瑰ソ鐨凟xcel鏂囦欢瀵煎叆鍒拌〃
	 * @return
	 * @throws Exception
	 */
	public String importExcelToDb() throws Exception {
		String res = "";
		boolean _flag = false;
		if (testNull(currExcelName)) {
			res += ". 鏈嶅姟绔疎xcel鏂囦欢锛坈urrExcelName锛夊繀濉紒\r\n";
			_flag = true;
		}
		if (testNull(tableName)) {
			res += ". 鍙傛暟閿欒锛氭暟鎹簱琛ㄥ悕锛坱ableName锛夊繀濉紒\r\n";
			_flag = true;
		}
		if (currIndexPageShowTags.indexOf("currYear") >= 0 && testNull(currYear)) {
				res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹勾浠斤紙currYear锛夊繀濉紒\r\n";
				_flag = true;
		}
		if (currIndexPageShowTags.indexOf("currMth") >= 0 && testNull(currMth)) {
				res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹湀浠斤紙currMth锛夊繀濉紒\r\n";
				_flag = true;
		}
		if (currIndexPageShowTags.indexOf("currArea") >= 0 && testNull(currArea)) {
				res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹尯鍩燂紙currArea锛夊繀濉紒\r\n";
				_flag = true;
		}
		if (currIndexPageShowTags.indexOf("currCorp") >= 0 && testNull(currCorp)) {
				res += ". 鍙傛暟閿欒锛氫笂浼犳暟鎹崟浣嶏紙currCorp锛夊繀濉紒\r\n";
				_flag = true;
		}

		String[] columns = plaImpService.getTBColumnCfg(tableName);
		if (null == columns || columns.length <= 0) {
			res += ". 鍙傛暟閿欒锛氭暟鎹簱琛ㄥ悕锛坱ableName锛夐敊璇紝绯荤粺鏃犳硶鑾峰彇鍒楀璞★紒\r\n";
			_flag = true;
		}

		if (_flag) {
			msg = new Msg(Msg.FAILURE, "鍙傛暟閿欒锛�" + res);
		} else {
			// 鎵ц淇濆瓨鎿嶄綔
			try {
				SysUser sysUser = getCurUser();
				res = plaImpService.importExcelToDb(currExcelName, tableName,
						columns, currYear, currMth, currArea,currCorp,sysUser.getUserid());
				msg = new Msg(Msg.SUCCESS, res);
				
				
				// 瀵煎叆鎴愬姛鍚庢搷浣滐紙鏁寸悊鏁版嵁锛�
				String[] initCfg = plaImpService.getTBAftImpCfg(tableName);
				if (null != initCfg && initCfg.length > 0) {
					int runCount = plaImpService.initAftImp(initCfg,
							currYear,currMth,currArea,currCorp);
				}
				
			} catch (Exception e) {
				msg = new Msg(Msg.FAILURE, "瀵煎叆鏃跺嚭鐜板紓甯革細" + e.toString());
				throw e;
			}

			
			
			

			
			
			
		}
		return MSG;
	}
	/**
	 * Action:AJAX鏂囦欢鍒犻櫎
	 * @return
	 * @throws Exception
	 */
	public String delTmpExcelFile() throws Exception
	{
		String res="";
		boolean _flag=false;
		if(null==currExcelName||"".equals(currExcelName)||"undefined".equals(currExcelName))
		{res=". 鏈嶅姟绔疎xcel鏂囦欢锛坈urrExcelName锛夊繀濉紒\r\n";_flag=true;}

		if(_flag){
			msg = new Msg(Msg.FAILURE,"鍙傛暟閿欒锛�"+res);
		}else{
			//鎵ц鍒犻櫎鎿嶄綔
			try{
				if(excelImpUtil.deleteExcelFile(currExcelName)){
					msg = new Msg(Msg.SUCCESS,"鍒犻櫎鎴愬姛锛�");
				}else{
					msg = new Msg(Msg.FAILURE,"鍒犻櫎澶辫触锛�");
				}
			}catch(Exception e)
			{
				msg = new Msg(Msg.FAILURE,"鍒犻櫎鏃跺嚭鐜板紓甯革紒"+e.getMessage());
				throw e;
			}			
		}
		return MSG;
	}
	/**
	 * 娴嬭瘯閾炬帴鍙傛暟鏄惁涓虹┖
	 * @param str
	 * @return
	 */
	private boolean testNull(final String str){
		if(null == str || "".equals(str)|| "undefined".equals(str)){
			return true;
		}else{
			return false;
		}
	}


	
	//***********************************SET GET 鏂规硶**************************************	
	public String getCurrYear() {
		return currYear;
	}
	public void setCurrYear(String CurrYear) {
		this.currYear = CurrYear;
	}
	public String getCurrMth() {
		return currMth;
	}
	public void setCurrMth(String CurrMth) {
		this.currMth = CurrMth;
	}
	public String getCurrCorp() {
		return currCorp;
	}
	public void setCurrCorp(String currCorp) {
		this.currCorp = currCorp;
	}
	public String getCurrArea() {
		return currArea;
	}
	public void setCurrArea(String currArea) {
		this.currArea = currArea;
	}	
	public String getReturnPage() {
		return returnPage;
	}
	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}
	public String getCurrIndexPageShowTags() {
		return currIndexPageShowTags;
	}
	public void setCurrIndexPageShowTags(String currIndexPageShowTags) {
		this.currIndexPageShowTags = currIndexPageShowTags;
	}	
	public Map<String, DicAreacorp> getDicAreaMap() {
		return dicAreaMap;
	}
	public void setDicAreaMap(Map<String, DicAreacorp> dicAreaMap) {
		this.dicAreaMap = dicAreaMap;
	}
	public Map<String, DicSinocorp> getDicCorpMap() {
		return dicCorpMap;
	}
	public void setDicCorpMap(Map<String, DicSinocorp> dicCorpMap) {
		this.dicCorpMap = dicCorpMap;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getMsgInfo() {
		return msgInfo;
	}
	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getExcelTableStr() {
		return excelTableStr;
	}
	public void setExcelTableStr(String excelTableStr) {
		this.excelTableStr = excelTableStr;
	}
	public String getCurrExcelRowNum() {
		return currExcelRowNum;
	}
	public void setCurrExcelRowNum(String currExcelRowNum) {
		this.currExcelRowNum = currExcelRowNum;
	}
	public String getCurrExcelColNum() {
		return currExcelColNum;
	}
	public void setCurrExcelColNum(String currExcelColNum) {
		this.currExcelColNum = currExcelColNum;
	}
	public String getCurrExcelValue() {
		return currExcelValue;
	}
	public void setCurrExcelValue(String currExcelValue) {
		this.currExcelValue = currExcelValue;
	}
	public String getCurrExcelName() {
		return currExcelName;
	}
	public void setCurrExcelName(String currExcelName) {
		this.currExcelName = currExcelName;
	}
	public boolean getIsUploadSuccess() {
		return isUploadSuccess;
	}
	public void setIsUploadSuccess(boolean isUploadSuccess) {
		this.isUploadSuccess = isUploadSuccess;
	}
	public String getFormTagID() {
		return formTagID;
	}
	public void setFormTagID(String formTagID) {
		this.formTagID = formTagID;
	}
	public String getFormTagSelected() {
		return formTagSelected;
	}
	public void setFormTagSelected(String formTagSelected) {
		this.formTagSelected = formTagSelected;
	}
	public String getFormTagDicMapName() {
		return formTagDicMapName;
	}
	public void setFormTagDicMapName(String formTagDicMapName) {
		this.formTagDicMapName = formTagDicMapName;
	}
	public String getFormTagDicMapInPara() {
		return formTagDicMapInPara;
	}
	public void setFormTagDicMapInPara(String formTagDicMapInPara) {
		this.formTagDicMapInPara = formTagDicMapInPara;
	}
	public String getFormTagDicMapOutPara() {
		return formTagDicMapOutPara;
	}
	public void setFormTagDicMapOutPara(String formTagDicMapOutPara) {
		this.formTagDicMapOutPara = formTagDicMapOutPara;
	}
	public String getFormTagType() {
		return formTagType;
	}
	public void setFormTagType(String formTagType) {
		this.formTagType = formTagType;
	}
	public String getFormTagDataType() {
		return formTagDataType;
	}
	public void setFormTagDataType(String formTagDataType) {
		this.formTagDataType = formTagDataType;
	}
	public boolean isCommitStatus() {
		return CommitStatus;
	}
	public void setCommitStatus(boolean commitStatus) {
		CommitStatus = commitStatus;
	}
	public String getCurrProdCateID() {
		return currProdCateID;
	}
	public void setCurrProdCateID(String currProdCateID) {
		this.currProdCateID = currProdCateID;
	}
	public String getCurrProdSecCateID() {
		return currProdSecCateID;
	}
	public void setCurrProdSecCateID(String currProdSecCateID) {
		this.currProdSecCateID = currProdSecCateID;
	}
	public String getCurrSelected() {
		return currSelected;
	}
	public void setCurrSelected(String currSelected) {
		this.currSelected = currSelected;
	}
}
