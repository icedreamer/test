/**
 * 
 */
package com.tlys.equ.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.equ.model.EquCarCertificate;
import com.tlys.equ.service.EquCarCertificateService;

/**
 * @author guojj
 * 
 */

@Controller
@Scope("prototype")
@ParentPackage("tlys-default")
@Namespace("/equ")
public class EquCarcertificateAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private EquCarCertificate equCarCertificate = new EquCarCertificate();
	private List<EquCarCertificate> equCarCertificates;
	private Long id;
	private String certdate;
	private boolean isNew;
	private String carno;

	@Autowired
	EquCarCertificateService equCarCertificateService;

	public String edit() {
		if (null != id) {
			this.equCarCertificate = equCarCertificateService.load(id);
			isNew = false;
		} else {
			equCarCertificate = null;
			isNew = true;
		}
		return "input";
	}

	public String save() throws Exception {
		if (null == equCarCertificate) {
			throw new Exception("����δ���յ���");
		} else {
			equCarCertificate.setCertdate("1949-01-01");
			equCarCertificate.setIsvalid("1");
			equCarCertificateService.save(equCarCertificate, isNew);
			equCarCertificateService.updateOther(equCarCertificate.getId(), equCarCertificate.getTrainno());
			// ��������msgҳ����ʾ������
			msg = new Msg(Msg.SUCCESS, "SUCCESS," + equCarCertificate.getId());
		}
		return MSG;
	}

	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("����ȷ��Ҫɾ���Ķ���");
		} else {
			equCarCertificateService.delete(id);
			msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���");
		}
		return MSG;
	}

	// =================================
	/**
	 * @return the EquCarCertificate
	 */
	public EquCarCertificate getEquCarCertificate() {
		return equCarCertificate;
	}

	/**
	 * @param EquCarCertificate
	 *            the EquCarCertificate to set
	 */
	public void setEquCarCertificate(EquCarCertificate EquCarCertificate) {
		this.equCarCertificate = EquCarCertificate;
	}

	/**
	 * @return the EquCarCertificates
	 */
	public List<EquCarCertificate> getEquCarCertificates() {
		return equCarCertificates;
	}

	/**
	 * @param EquCarCertificates
	 *            the EquCarCertificates to set
	 */
	public void setEquCarCertificates(List<EquCarCertificate> EquCarCertificates) {
		this.equCarCertificates = EquCarCertificates;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the isNew
	 */
	public boolean getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getCertdate() {
		return certdate;
	}

	public void setCertdate(String certdate) {
		this.certdate = certdate;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

}
