/**
 * 
 */
package com.tlys.comm.bas;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

/**
 * @author fengym 原计划使用泛型在service级别实现通用curd基本操作 让所有service均继承此类但不成功
 *         此处注入_GenericDao时报错，说是 No unique bean of type
 *         [com.tlys.sys.dao._GenericDao] is defined: expected single matching
 *         bean but found 2: [sysRoleDao, sysUserDao]
 * 
 * 弃用，放在此供研究
 */
@Service
public abstract class _GenericService<T> {
	protected final Log log = LogFactory.getLog(this.getClass());
	protected static final int BUFFER_SIZE = 16 * 1024;
	protected static final String ATTACH_URL = "/attach/";
	protected static final String Flash_URL = "/_inc/FlashPaper2.2/FlashPrinter.exe";

	public boolean copyFile(File srcFile, File destFile) {
		boolean result = false;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(srcFile),
					BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(destFile),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			result = true;
		} catch (Exception e) {
			log.error("copy file error.", e);
			result = false;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public boolean toSwf(String oldFile, String newFile) {
		ServletContext context = ServletActionContext.getServletContext();
		Process process = null;
		try {
			Runtime rt = Runtime.getRuntime();
			String programPath = context.getRealPath(Flash_URL);
			String command = programPath + " " + oldFile + " -o " + newFile;
			System.out
					.print("===================================================>swf error:"
							+ command);
			process = rt.exec(command);
			InputStream inputStream = process.getErrorStream();
			InputStreamReader inputReader = new InputStreamReader(inputStream,
					"GBK");
			BufferedReader bReader = new BufferedReader(inputReader);
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = bReader.readLine()) != null) {
				buffer.append(line).append("\r\n");
			}
			System.out
					.print("===================================================>swf error:"
							+ buffer);
			bReader.close();
			inputReader.close();
			inputStream.close();
			process.destroy();
		} catch (Exception e) {
			log.error("===================================>to swf error:", e);
		} finally {
			if (process != null) {
				process.destroy();
			}

		}
		return true;
	}
}
