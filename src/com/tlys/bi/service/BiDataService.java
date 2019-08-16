package com.tlys.bi.service;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import com.tlys.comm.util.CommUtils;
/**
 * @author fengym
 * 
 */
@Service
public class BiDataService{
	protected final Log log = LogFactory.getLog(this.getClass());

	
	public void genXML(){		
		ServletContext context = ServletActionContext.getServletContext();
		String destDir = CommUtils.getString("/","bit","/","xml", "/");
		String destFile = CommUtils.getString(destDir ,"clfbdtdata.xml");
		
		


		String destRealDir = context.getRealPath(destDir);
		File dir=new File(destRealDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String destRealFile = context.getRealPath(destFile);	
		File file=new File(destRealFile);
		
		
        try { 
        	Element root = (Element) DocumentHelper.createElement("model");
        	Element childElement = DocumentHelper.createElement("firstPieData");
        	Element schildElement = DocumentHelper.createElement("dataset").addAttribute("label", "ÖØ³µ");
        	
        	childElement.add(schildElement);
        	root.add(childElement);

        	
        	Document responseDoc = DocumentHelper.createDocument(root);


        	OutputFormat outputFormat = OutputFormat.createPrettyPrint();


        	outputFormat.setEncoding("utf-8");


        	XMLWriter xMLWriter = new XMLWriter(new FileOutputStream(destRealFile),outputFormat);


        	xMLWriter.write(responseDoc);


        	xMLWriter.close();
               
        } catch (Exception e) { 
                e.printStackTrace();   
        } 
	}

}
