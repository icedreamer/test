/**
 * 
 */
package com.tlys.comm.bas;

/**
 * @author fengym
 * �������ڶ������msgҳ���ϸ�����ʾ�Ĳ���
 * ������_BaseAction�����ã�
 * �ڸ�action�ķ����и�ֵ������˷�����Ҫ����msgҳ��Ļ�
 */
public class Msg {
	public static final String SUCCESS="success";
	public static final String FAILURE="failure";
	
	
	/**
	 * ��ǰ���������success/failure
	 * �˲�������msgҳ���ϵ�ͼ����ʾ
	 */
	private String flag;
	
	/**
	 * ��ʾ��Ϣ������ʾ��msgҳ����·�
	 * Ҳ����ajax�ύ��ķ����ַ�����Ϣ����Ҫʹ��jquery����)
	 */
	private String msgShow;
	
	/**
	 * �Ƿ���ʾ�ر�ҳ�水ť
	 */
	private boolean showCloseButton;
	
	/**
	 * ҳ����Ҫִ�е�javascript���
	 */
	private String script;
	
	/**
	 * ҳ�������������id)��ֵ����
	 */
	private String[] hiddenInputNv;
	
	
	/**
	 * ͨ�õ���ʾ�࣬����flag��ȷ����ʾ����
	 * @param flag
	 */
	public Msg(String flag){
		this.flag = flag;
		if(this.SUCCESS.equals(flag)){
			this.msgShow = "��ǰ�����ɹ�ִ�У�";
		}else{
			this.msgShow ="��ǰ����ִ��ʧ��~~";
		}
	}
	
	
	/**
	 * ʹ��flag��msgShow����msg��
	 * @param flag
	 * @param msgShow
	 */
	public Msg(String flag, String msgShow) {
		this.flag = flag;
		this.msgShow = msgShow;
	}
	
	/**
	 * ��˳�������ǣ����������SUCCESS/FAILURE),��ʾ��ʾ�Ҫִ�еĽű�
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
	 * ��˳�������ǣ����������SUCCESS/FAILURE),��ʾ��ʾ��Ƿ���ʾ�رյ�ǰ���ڣ�Ҫִ�еĽű���ҳ��������ı�����ֵ
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
	 * ��ʾ��Ϣ������ʾ��msgҳ����·�
	 * Ҳ����ajax�ύ��ķ����ַ�����Ϣ����Ҫʹ��jquery����)
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
	 * ��ǰ���������success/failure
	 * �˲�������msgҳ���ϵ�ͼ����ʾ
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
	 * �Ƿ���ʾ�ر�ҳ�水ť
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
	 * ҳ����Ҫִ�е�javascript���
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
	 * ҳ�������������id)-ֵ����
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
