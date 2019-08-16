package com.tlys.comm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;

/**
 * ����action
 * 
 * @author fengym
 * 
 *         ���÷���������Ҫ���ص�action������һ��string������ԣ�filepath Ȼ�������ط����и�filepath��һ�������ַ��ֵ
 *         result="download" �Ϳ���ʵ�������ˡ�
 * 
 *         ע�����Ҫ�������أ�������filepath���������ļ���)�� ��Ҫ��action������һ��filename�ı��������丳ֵ
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/")
@Result(type = "stream", params = { "contentType", "application/octet-stream;charset=ISO8859-1", "inputName",
		"inputStream", "contentDisposition", "attachment;filename=${fileName}", "bufferSize", "4096" })
public class DownloadAction extends _BaseAction {

	private String fileName;
	private String axUrl;
	private String axFullPath;

	public String execute() throws Exception {
		ServletContext sct = ServletActionContext.getServletContext();
		// String axRootPath = AnnexUtil.getAxRootPath(sct);
		// System.out.println("DownloadAction.execute->axUrl=="+axUrl);
		axFullPath = sct.getRealPath(axUrl);

		if (logger.isDebugEnabled()) {
			logger.debug("fileName : " + fileName);
		}

		// ����ͻ����򴫹���fileName��ֵ�������֮�����û�д�����������ļ�·���н�ȡ
		if (null == fileName || "".equals(fileName)) {
			fileName = axFullPath.substring(axFullPath.lastIndexOf("\\") + 1);
		}
		
		// System.out.println("DownloadAction.execute->fileName=="+fileName);

		File axFile = new File(axFullPath);
		if (logger.isDebugEnabled()) {
			logger.debug("axFile.exists : " + axFile.exists());
		}
		if (!axFile.exists()) {
			msg = new Msg("failure", "���ļ�������:" + axFullPath);
			return "msg";
		} else {
			return "success";
		}

	}

	// ��������ļ������ݣ�����ֱ�Ӷ���һ�������ļ�������ݿ��л�ȡ����
	public InputStream getInputStream() throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(axFullPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fis;
	}

	// ���������е� ${fileName}, ������ر���ʱ���ļ���
	public String getFileName() {
		// *
		try {
			fileName = new String(fileName.getBytes(), "ISO8859-1");
			// fileName = new String(fileName.getBytes(), "GBK");
			// fileName = URLEncoder.encode(fileName, "ISO8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// */
		// System.out.println("DownloadAction.getFileName()->fileName=" +
		// fileName);
		return fileName;
	}

	/**
	 * @return the axFullPath
	 */
	public String getAxFullPath() {
		return axFullPath;
	}

	/**
	 * @param axFullPath
	 *            the axFullPath to set
	 */
	public void setAxFullPath(String axFullPath) {
		this.axFullPath = axFullPath;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the axUrl
	 */
	public String getAxUrl() {
		return axUrl;
	}

	/**
	 * @param axUrl
	 *            the axUrl to set
	 */
	public void setAxUrl(String axUrl) {
		this.axUrl = axUrl;
	}

}
