/**
 * 
 */
package com.tlys.comm.bas;

/**
 * @author fengym
 * 此类用于定义控制msg页面上各种显示的参数
 * 此类在_BaseAction中引用，
 * 在各action的方法中赋值，如果此方法需要返回msg页面的话
 */
public class Msg {
	public static final String SUCCESS="success";
	public static final String FAILURE="failure";
	
	
	/**
	 * 当前操作结果：success/failure
	 * 此参数决定msg页面上的图标显示
	 */
	private String flag;
	
	/**
	 * 提示信息，将显示在msg页面的下方
	 * 也用于ajax提交后的返回字符串信息（需要使用jquery过滤)
	 */
	private String msgShow;
	
	/**
	 * 是否显示关闭页面按钮
	 */
	private boolean showCloseButton;
	
	/**
	 * 页面上要执行的javascript语句
	 */
	private String script;
	
	/**
	 * 页面隐藏域的名（id)－值数组
	 */
	private String[] hiddenInputNv;
	
	
	/**
	 * 通用的提示类，根据flag来确定提示语言
	 * @param flag
	 */
	public Msg(String flag){
		this.flag = flag;
		if(this.SUCCESS.equals(flag)){
			this.msgShow = "当前操作成功执行！";
		}else{
			this.msgShow ="当前操作执行失败~~";
		}
	}
	
	
	/**
	 * 使用flag和msgShow生成msg类
	 * @param flag
	 * @param msgShow
	 */
	public Msg(String flag, String msgShow) {
		this.flag = flag;
		this.msgShow = msgShow;
	}
	
	/**
	 * 按顺序依次是：操作结果（SUCCESS/FAILURE),显示提示语，要执行的脚本
	 * @param flag
	 * @param msgShow
	 * @param script
	 */
	public Msg(String flag, String msgShow,String script) {
		this.flag = flag;
		this.msgShow = msgShow;
		this.script = script;
	}



	/**
	 * 按顺序依次是：操作结果（SUCCESS/FAILURE),显示提示语，是否显示关闭当前窗口，要执行的脚本，页面隐藏域的表单名及值
	 * @param flag
	 * @param msgShow
	 * @param showCloseButton
	 * @param script
	 * @param hiddenInputNv
	 */
	public Msg(String flag, String msgShow, boolean showCloseButton,
			String script, String[] hiddenInputNv) {
		this.msgShow = msgShow;
		this.flag = flag;
		this.showCloseButton = showCloseButton;
		this.script = script;
		this.hiddenInputNv = hiddenInputNv;
	}





	/**
	 * 提示信息，将显示在msg页面的下方
	 * 也用于ajax提交后的返回字符串信息（需要使用jquery过滤)
	 * @return the msgShow
	 */
	public String getMsgShow() {
		return msgShow;
	}

	/**
	 * @param msgShow the msgShow to set
	 */
	public void setMsgShow(String msgShow) {
		this.msgShow = msgShow;
	}

	/**
	 * 当前操作结果：success/failure
	 * 此参数决定msg页面上的图标显示
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 是否显示关闭页面按钮
	 * @return the showCloseButton
	 */
	public boolean getShowCloseButton() {
		return showCloseButton;
	}

	/**
	 * @param showCloseButton the showCloseButton to set
	 */
	public void setShowCloseButton(boolean showCloseButton) {
		this.showCloseButton = showCloseButton;
	}

	/**
	 * 页面上要执行的javascript语句
	 * @return the script
	 */
	public String getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * 页面隐藏域的名（id)-值数组
	 * @return the hiddenInputNv
	 */
	public String[] getHiddenInputNv() {
		return hiddenInputNv;
	}

	/**
	 * @param hiddenInputNv the hiddenInputNv to set
	 */
	public void setHiddenInputNv(String[] hiddenInputNv) {
		this.hiddenInputNv = hiddenInputNv;
	}
}
