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
 * 下载action
 * 
 * @author fengym
 * 
 *         调用方法，在需要下载的action中设置一个string类的属性：filepath 然后，在下载方法中给filepath赋一个物理地址的值
 *         result="download" 就可以实现下载了。
 * 
 *         注：如果要改名下载（即不用filepath中所给的文件名)， 则要在action中设置一个filename的变量并给其赋值
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

		// 如果客户程序传过来fileName的值，则就用之；如果没有传过来，则从文件路径中截取
		if (null == fileName || "".equals(fileName)) {
			fileName = axFullPath.substring(axFullPath.lastIndexOf("\\") + 1);
		}
		
		// System.out.println("DownloadAction.execute->fileName=="+fileName);

		File axFile = new File(axFullPath);
		if (logger.isDebugEnabled()) {
			logger.debug("axFile.exists : " + axFile.exists());
		}
		if (!axFile.exists()) {
			msg = new Msg("failure", "此文件不存在:" + axFullPath);
			return "msg";
		} else {
			return "success";
		}

	}

	// 获得下载文件的内容，可以直接读入一个物理文件或从数据库中获取内容
	public InputStream getInputStream() throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(axFullPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fis;
	}

	// 对于配置中的 ${fileName}, 获得下载保存时的文件名
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
