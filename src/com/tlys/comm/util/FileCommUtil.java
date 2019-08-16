package com.tlys.comm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCommUtil {
	private static final int BUFFER_SIZE = 512 * 1024;

	public static void main(String[] args) {

		String wkdir = "D:\\fym_work\\Eclipse\\ws_ultr_calt\\hopeweb\\WebContent\\_editor\\upload";
		String bkdir = "/opt/hopeweb_file/editor_upload_bakup";
		File wk = new File(wkdir);
		File bk = new File(bkdir);
		try {
			FileCommUtil.copy(wk, bk);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��һ���ļ�����Ϊ��һ���ļ�
	 * 
	 * @param src
	 * @param dst
	 * @return
	 * @throws IOException
	 */
	public static void copyFile(File src, File dst) {
		if (!src.exists())
			return;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}

			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	/**
	 * ��һ���ļ����ļ��и��Ƶ�һ��Ŀ���ļ�����
	 * 
	 * @param src
	 * @param dstFolder
	 */
	public static String copy(File src, File dst) throws IOException {
		String reStr = "";
		if (!src.exists())
			return "\nԴ�ļ�������:" + src.getPath();
		if (!dst.exists())
			dst.mkdirs();
		if (src.isFile()) {
			InputStream in = null;
			OutputStream out = null;
			try {
				File dstFile = new File(dst.getPath() + File.separator + src.getName());
				in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dstFile), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
			} catch (IOException e) {
				System.out.println("CommFileUtil.copy()->e=" + e);
				reStr = "\n�����ļ������쳣��" + e;
			} finally {
				if (in != null)
					in.close();
				if (out != null) {
					out.flush();
					out.close();
				}
			}
		} else {
			String dstFolderStr = dst.getPath() + File.separator + src.getName();
			File _dstFolder = new File(dstFolderStr);
			_dstFolder.mkdirs();
			for (File _src : src.listFiles()) {
				reStr += copy(_src, _dstFolder);
			}
		}

		return reStr;
	}

	public static void move(File src, File dstFolder) throws IOException {
		copy(src, dstFolder);
		delete(src);
	}

	/**
	 * ɾ���ļ����ļ��У�������ļ��У���ݹ�ɾ���������ļ����ļ���
	 * 
	 * @param dst
	 */
	public static void delete(File dst) {
		if (!dst.exists())
			return;
		if (dst.isFile()) {
			dst.delete();
		} else {
			for (File f : dst.listFiles()) {
				delete(f);
			}
			dst.delete();
		}
	}
}
