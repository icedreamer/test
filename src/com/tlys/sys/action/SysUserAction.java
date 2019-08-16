/**
 * 
 */
package com.tlys.sys.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import com.tlys.comm.bas.Msg;
import com.tlys.comm.bas._BaseAction;
import com.tlys.comm.util.DicMap;
import com.tlys.comm.util.page.PageCtr;
import com.tlys.dic.model.DicAreacorp;
import com.tlys.dic.model.DicSinocorp;
import com.tlys.dic.model.DicSinodepartment;
import com.tlys.dic.service.DicAreacorpService;
import com.tlys.dic.service.DicSinocorpService;
import com.tlys.dic.service.DicSinodepartmentService;
import com.tlys.sys.model.SysGroup;
import com.tlys.sys.model.SysRole;
import com.tlys.sys.model.SysUser;
import com.tlys.sys.model.SysUserdatas;
import com.tlys.sys.service.SysDatafillinfoService;
import com.tlys.sys.service.SysGroupService;
import com.tlys.sys.service.SysLogService;
import com.tlys.sys.service.SysRoleService;
import com.tlys.sys.service.SysUserService;

/**
 * @author ������
 * 
 */

@Controller
// by spring,����bean
@Scope("prototype")
// by struts2������actionΪԭ��ģʽ
@ParentPackage("tlys-default")
// //by struts2�������pachage�̳�tlys-default(tlys-default����struts.xml�ж���)
@Namespace("/sys")
// by struts2������action��namespace
public class SysUserAction extends _BaseAction {

	private static final long serialVersionUID = -1170734002472705216L;
	private final Logger log = Logger.getLogger(this.getClass());

	private SysUser sysUser;
	private List<SysUser> sysUsers;
	private String id;

	private String newpwd;
	private String newpwd2;
	private String oldpwd;

	private List<SysGroup> sysGroups;
	private Map<String, List<SysGroup>> gmap;
	private Map<String, List<SysRole>> rmap;

	@Autowired
	SysDatafillinfoService sysDatafillinfoService;

	/**
	 * �û��ֲ���ʾMap
	 */
	private Map<Integer, List<SysUser>> userMap;

	private String[] ugsel;
	private String[] ursel;

	/**
	 * ��ǰ�û���λID����Ӧ�ı��־(area,corp,dept�����Ϊ0,1,2,3)
	 */
	private String corptab;
	/**
	 * ��ǰ�û���λID,corppopҳ��Ҫ��
	 */
	private String corpid;

	/**
	 * ��ǰ�û����ڵ�λ��map��id=shortname) ������areacorp,sinocorp,sinodepartment
	 */
	private Map corpMap;

	// ��ҳ�洫�����Ĳ�ѯ���������ڵ�����ʱ�õ���ǰ��ѯ����
	private String schobjkey;

	/**
	 * ��־��ǰҳ��ı�־������corpListPopҳ�� ������һҳ�����������ҳ��� һ����inputҳ�棬һ����schҳ�棬����Ҫ��������
	 * �Ա��ڷ���ֵʱ�Ժ��ʵ�ҳ�����ֵ
	 */
	private String pageflag;

	/**
	 * ��ҳ����յ��ĵ�ǰ�û�������Ȩ��(�Ը���˾)
	 */
	private String corpids;

	private Map<String, DicAreacorp> dicAreacorpMap;

	private Map<String, DicSinocorp> dicSinocorpMap;

	Map<String, List<SysUser>> userByCorpIdMap;
	@Autowired
	SysUserService sysUserService;

	@Autowired
	SysGroupService sysGroupService;
	@Autowired
	SysRoleService sysRoleService;

	@Autowired
	DicSinocorpService dicSinocorpService;
	@Autowired
	DicAreacorpService dicAreacorpService;
	@Autowired
	DicSinodepartmentService dicSinodepartmentService;

	@Autowired
	SysLogService logService;

	@Autowired
	DicMap dicMap;

	/**
	 * ���������struts2��У�飬��ע��ʹ����������У�顣
	 */
	@SkipValidation
	/**
	 * ʹ��ע�Ͷ���˷�����result,���ڶ���һЩ�����������ת
	 */
	@Action(results = { @Result(name = "pass", location = "/sys-iframe.jsp", type = "redirect") })
	public String login() throws Exception {
		String rst = MSG;
		SysUser sysUserDb = null;
		if (null == sysUser || null == sysUser.getLoginid() || "".equals(sysUser.getLoginid())) {
			msg = new Msg(Msg.FAILURE, "�û������ڻ��û���Ϊ�գ�");
		} else {
			String loginid = sysUser.getLoginid();
			String loginpwd = sysUser.getLoginpwd();
			sysUserDb = sysUserService.loadByLoginid(loginid);
			if (null == sysUserDb) {
				msg = new Msg(Msg.FAILURE, "ϵͳ��û�д��û���");
			} else {
				if (log.isDebugEnabled()) {
					log.debug("SysUserAction.login->sysUserDb.getIslocked()==" + sysUserDb.getIslocked());
				}
				String lockStr = sysUserDb.getIslocked();
				if ("1".equals(lockStr)) {// �û�������
					msg = new Msg(Msg.FAILURE, "��ǰ�û��ѱ�����������ϵ����Ա�������ٵ�¼��");
				} else {
					// �ڴ˶Ա�����,ͨ��
					if (loginpwd != null && sysUserDb.getLoginpwd().equals(loginpwd)) {
						rst = "pass";
					} else {
						msg = new Msg(Msg.FAILURE, "�û�������������µ�¼��");
						rst = "failure";
					}
				}
			}
		}

		if ("pass".equals(rst)) {
			// ���üǵ�¼�ɹ��󣬼���ʼ���¿��߳���������ҳ��ʼ���洢����
			// �����ڼ�����ҳʱ���У�Ӱ����ҳ���ٶ�

			new Thread() {
				public void run() {
					System.out.println("SysUserAction.login->��ʼִ�д洢����new Date()==" + new Date());
					sysDatafillinfoService.exeProc();
					System.out.println("SysUserAction.login->�洢����ִ�����new Date()==" + new Date());
				}
			}.start();

			setSessionAttr("sysUserSess", sysUserDb);
			// ������ɫ�û�Ȩ�޺͵�����¼������־��������
			sysUserService.renderSysUser(sysUserDb);
		}

		return rst;
	}

	@SkipValidation
	@Action(value = "sysUser", results = { @Result(name = "list", location = "/sys/sys-rgu-left.jsp") })
	public String list() throws Exception {
		// sysUsers = sysUserService.findAll();
		getCurUser();
		// userMap = sysUserService.findUserMap();
		List<SysUser> sysUserList = sysUserService.findSysUser(sysUser);

		if (sysUserList != null && !sysUserList.isEmpty()) {
			userByCorpIdMap = new HashMap<String, List<SysUser>>();
			userMap = new HashMap<Integer, List<SysUser>>();
			for (SysUser sysUser : sysUserList) {
				String corpid = sysUser.getCorpid();
				String corptab = sysUser.getCorptab();
				List<SysUser> SubSysUserList = userByCorpIdMap.get(corpid);
				if (SubSysUserList == null) {
					SubSysUserList = new ArrayList<SysUser>();
				}
				SubSysUserList.add(sysUser);
				userByCorpIdMap.put(corpid, SubSysUserList);
				if (corptab.equals("3")) {

					Map<String, DicSinodepartment> dicSinodepartmentMap = dicMap.getDeptMap();

					if (dicSinodepartmentMap.containsKey(corpid)) {
						DicSinodepartment dicSinodepartment = dicSinodepartmentMap.get(corpid);
						String areaid = dicSinodepartment.getParentid();
						List<SysUser> departSysUserList = userByCorpIdMap.get(areaid);
						if (departSysUserList == null) {
							departSysUserList = new ArrayList<SysUser>();
						}
						departSysUserList.add(sysUser);
						userByCorpIdMap.put(areaid, departSysUserList);
					}

				} else {

				}

				List<SysUser> SubList = userMap.get(Integer.parseInt(corptab));
				if (SubList == null) {
					SubList = new ArrayList<SysUser>();
				}
				SubList.add(sysUser);

				userMap.put(Integer.parseInt(corptab), SubList);

			}
		}

		if (log.isDebugEnabled()) {
			log.debug("userMap : " + userMap);
		}

		// if (userMap != null && !userMap.isEmpty()) {
		// Set<String> keySet = userMap.keySet();
		// userByCorpIdMap = new HashMap<String, List<SysUser>>();
		// for (String corptabName : keySet) {
		// List<SysUser> sysUsers = userMap.get(corptabName);
		// log.debug("sysUsers : " + sysUsers);
		// if (sysUsers != null && !sysUsers.isEmpty()) {
		// for (SysUser sysUser : sysUsers) {
		// String corpid = sysUser.getCorpid();
		// log.debug("corpid : " + corpid);
		// List<SysUser> subUserList = userByCorpIdMap.get(corpid);
		//
		// if (subUserList == null) {
		// subUserList = new ArrayList<SysUser>();
		// }
		// subUserList.add(sysUser);
		// userByCorpIdMap.put(corpid, sysUsers);
		// }
		// }
		// }
		//
		// }

		dicAreacorpMap = dicMap.getAreaMap();
		// ����dicAreacorpMap���ϻ�ȡ����ֹ�˾ID
		// Set set = dicAreacorpMap.keySet();
		// Iterator it=set.iterator();
		// while(it.hasNext()){
		//
		// }
		// for(){
		//
		// }

		dicSinocorpMap = dicMap.getCorpMap();

		return "list";
	}

	// private Map<String, List<SysUser>> get

	/**
	 * ��ҳ�б��ݲ�����ʹ��
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String listpage() throws Exception {
		String pageUrl = "/sys/sys-user!listpage.action";
		if (null == pageCtr) {
			pageCtr = new PageCtr();
			getCurUser();

			// ���в�ѯ����ʱ,����ǰ��ѯ�����������session�У����ҽ���������pageCtr��
			// ������ҳ����תhtmlʱ��Ӧ���ϴ˲���
			if (null != sysUser) {
				String schObjKey = "sysUser_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysUser);
			}
		} else {
			// ����ǰ̨��ҳ�����ʱ��Ӧͬʱ����schObjKey
			if (null != pageCtr.getSchObjKey()) {
				sysUser = (SysUser) getSessionAttr(pageCtr.getSchObjKey());
			}
		}
		pageCtr.setPageUrl(pageUrl);
		pageCtr.setPageSize(15);
		sysUserService.find(sysUser, pageCtr);
		return "listpage";
	}

	/**
	 * ����excel
	 * 
	 * @return
	 */
	@SkipValidation
	public String expExcel() throws Exception {
		if (null != schobjkey) {
			sysUser = (SysUser) getSessionAttr(schobjkey);
		} else {
			sysUser = null;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		sysUserService.expExcel(sysUser, response);
		msg = new Msg(Msg.SUCCESS);
		return MSG;
	}

	@SkipValidation
	public String edit() throws Exception {
		if (null != id) {
			detail();
		} else {
			sysUser = null;
			sysUser = new SysUser();
			sysUser.setCorpid("00000000");
			sysUser.setEmail("ce@sinopec.com.cs");
		}
		return "input";
	}

	@SkipValidation
	public String detail() throws Exception {
		this.sysUser = sysUserService.load(id);
		this.gmap = sysGroupService.findUsergroupsSel(id);
		this.rmap = sysRoleService.findUserrolesSel(id);
		return "detail";
	}

	@SkipValidation
	public String delete() throws Exception {
		if (null == id) {
			throw new Exception("����ȷ��Ҫɾ���Ķ���");
		} else {
			sysUserService.delete(id);
			msg = new Msg(Msg.SUCCESS, "ɾ������ɹ���", false, "parent.leftFrame.location.reload();", new String[] {
					"curTitle", "�û�" });
		}
		return MSG;
	}

	/**
	 * �޸�����
	 */
	/*
	 * @SkipValidation public String changePwd() throws Exception { sysUser =
	 * sysUserService.load(id); if (!oldpwd.equals(sysUser.getLoginpwd())) { msg
	 * = new Msg(Msg.FAILURE, "����ľ����벻��ȷ��"); } else {
	 * sysUser.setLoginpwd(newpwd); sysUserService.saveOrUpdate(sysUser); msg =
	 * new Msg(Msg.SUCCESS, "�����ѳɹ����á�"); } return MSG; }
	 */

	/**
	 * ��������
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String resetPwd() throws Exception {
		sysUser = sysUserService.load(id);
		sysUser.setLoginpwd("123456");
		sysUserService.saveOrUpdate(sysUser);
		msg = new Msg(Msg.SUCCESS, "�����ѳɹ����á�");
		return MSG;
	}

	/**
	 * �޸�����
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	@Action(value = "userpwd", results = { @Result(name = "modpage", location = "/sys/sys-user-pwd-input.jsp") })
	public String modPwd() throws Exception {
		sysUser = sysUserService.load(getCurUser().getUserid());
		if (!oldpwd.equals(sysUser.getLoginpwd())) {
			msg = new Msg(Msg.FAILURE, "�����ԭ���벻��ȷ��");
			return "modpage";
		} else {
			if (sysUser.getUsername().indexOf("ϵͳ����") > -1) {
				msg = new Msg(Msg.FAILURE, "�Բ���ϵͳ����ԱĿǰ�ݲ����޸����롣");
				return "modpage";
			} else {
				if (null != newpwd) {
					sysUser.setLoginpwd(newpwd);
					sysUserService.saveOrUpdate(sysUser);
					msg = new Msg(Msg.SUCCESS, "�����ѳɹ��޸ġ�", bdScript());
				} else {
					msg = new Msg(Msg.FAILURE, "�����޸�ʧ�ܡ�", bdScript());
				}
			}
			return MSG;
		}
	}

	/**
	 * ֻ�����޸�������ҳ��ű����ɣ�ȥ�������·���ȷ����ť)
	 * 
	 * @return
	 */
	private String bdScript() {
		return "frameElement.lhgDG.removeBtn('modok');";
	}

	/**
	 * ����BI����
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String resetBiPwd() throws Exception {
		sysUser = sysUserService.load(id);
		sysUser.setBipwd("123456");
		sysUserService.saveOrUpdate(sysUser);
		msg = new Msg(Msg.SUCCESS, "BI�����ѳɹ����á�");
		return MSG;
	}

	/**
	 * У��ע�ͣ��˴��ṩ��������У��
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(type = ValidatorType.SIMPLE,
			fieldName = "sysUser.username", message = "����Ϊ���") }, emails = { @EmailValidator(
			type = ValidatorType.SIMPLE, fieldName = "sysUser.email", message = "email��ַ������д��ȷ��") },
			stringLengthFields = { @StringLengthFieldValidator(type = ValidatorType.SIMPLE, trim = true,
					minLength = "3", maxLength = "8", fieldName = "sysUser.corpid", message = "��λ���볤�ȱ�����3-8֮�䡣") })
	public String save() throws Exception {
		if (null == sysUser) {
			msg = new Msg(Msg.FAILURE, "δ���յ��û���Ϣ��");
		} else {
			boolean isNew = false;
			if (null == sysUser.getUserid() || "".equals(sysUser.getUserid())) {
				isNew = true;
				String seqStr = sysUserService.getSeqStr();
				String loginid = sysUser.getLoginid();
				boolean isex = sysUserService.isexist(loginid);
				if (isex) {
					msg = new Msg(Msg.FAILURE, "��ǰ�û���¼���Ѵ��ڣ�������û���¼�����ԣ�");
					return "input";
				}
				sysUser.setUserid(seqStr);
				sysUser.setLoginpwd("123456");
				sysUser.setIslocked("0");
				// sysUser.setEmail(sysUser.getLoginid()+"@sinopec.com.cs");
				sysUser.setFirstpage("welcome");
				sysUser.setBiuser(sysUser.getLoginid());
			}
			// System.out.println("SysUserAction.save->sysUser.getIslocked()=="+sysUser.getIslocked());
			sysUser.setIslocked("0");
			sysUserService.save(sysUser, isNew);

			if (isNew) {
				sysUser = null;
			}
			// ��������msgҳ����ʾ������

			msg = new Msg(Msg.SUCCESS, "���/�༭�û��ɹ���", false, "parent.leftFrame.location.reload();", new String[] {
					"curTitle", "�û�" });
		}

		return "input";
	}

	@SkipValidation
	public String updug() throws Exception {
		sysUserService.updateUserGroup(ugsel, id, "userid");
		msg = new Msg(Msg.SUCCESS, "���³ɹ���");
		return MSG;
	}

	@SkipValidation
	public String updur() throws Exception {
		sysUserService.updateUserRole(ursel, id);
		msg = new Msg(Msg.SUCCESS, "���³ɹ���");
		return MSG;
	}

	@SkipValidation
	/**
	 * �����û�
	 */
	public String lock() throws Exception {
		sysUser = sysUserService.load(id);
		sysUser.setIslocked("1");
		sysUser.setLockedtime(new Date());
		sysUserService.saveOrUpdate(sysUser);
		msg = new Msg(Msg.SUCCESS, "��ǰ�û��ѱ�������");
		return "msg";
	}

	/**
	 * �����û�
	 */
	@SkipValidation
	public String unlock() throws Exception {
		sysUser = sysUserService.load(id);
		sysUser.setIslocked("0");
		sysUserService.saveOrUpdate(sysUser);
		msg = new Msg(Msg.SUCCESS, "��ǰ�û��ѱ�������");
		return "msg";
	}

	@SkipValidation
	@Action(value = "sysadm", results = { @Result(name = "loginpage", location = "/login.jsp") })
	public String exit() throws Exception {
		ServletActionContext.getRequest().getServletPath();
		HttpSession sess = ServletActionContext.getRequest().getSession();
		SysUser su = (SysUser) sess.getAttribute("sysUserSess");
		// System.out.println("SysUserAction.exit->su=="+su);
		sess.invalidate();
		msg = new Msg(Msg.SUCCESS, "��ǰ�û��ɹ��˳���");
		return "loginpage";
	}

	@SkipValidation
	public String msg() {
		msg = new Msg(Msg.SUCCESS, "��ǰ�û��ɹ��˳���");
		return "msg";
	}

	/**
	 * ���µ�ǰ�û�������Ȩ��
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String updUserData() throws Exception {
		// sysUserService.updateUserData(corpids, id);
		sysUserService.updateUserdatas(corpids, id, menuid);
		msg = new Msg(Msg.SUCCESS, "���³ɹ���", false, "alert('���³ɹ���');frameElement.lhgDG.curWin.dgcorp.cancel();", null);
		return "msg";
	}

	/**
	 * ������ѡ�˵����鿴��ǰ�û�����
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String corpdatas() throws Exception {
		String userid = sysUser.getUserid();
		List<SysUserdatas> sysUserdatases = sysUserService.findSysUserdatas(userid, menuid);
		String corpids = "";
		if (null != sysUserdatases && !sysUserdatases.isEmpty()) {
			for (SysUserdatas sysUserdatas : sysUserdatases) {
				corpids += "," + sysUserdatas.getDatacorpid();
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("corpids : " + corpids);
		}
		if (null != corpids && corpids.startsWith(",")) {
			corpids = corpids.substring(1);
		}
		msg = new Msg(Msg.SUCCESS, corpids);
		return MSG;
	}

	/**
	 * ��ʾ�û���λ������
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String corpListPop() throws Exception {
		if (null == corptab || "".equals(corptab)) {
			corptab = "2";
		}
		corpMap = sysUserService.findCorpMap(corptab);
		return "corppop";
	}

	// =================================

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public List<SysUser> getSysUsers() {
		return sysUsers;
	}

	public void setSysUsers(List<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub

		return super.execute();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the sysGroups
	 */
	public List<SysGroup> getSysGroups() {
		return sysGroups;
	}

	/**
	 * @param sysGroups
	 *            the sysGroups to set
	 */
	public void setSysGroups(List<SysGroup> sysGroups) {
		this.sysGroups = sysGroups;
	}

	/**
	 * @return the gmap
	 */
	public Map<String, List<SysGroup>> getGmap() {
		return gmap;
	}

	/**
	 * @param gmap
	 *            the gmap to set
	 */
	public void setGmap(Map<String, List<SysGroup>> gmap) {
		this.gmap = gmap;
	}

	/**
	 * @return the rmap
	 */
	public Map<String, List<SysRole>> getRmap() {
		return rmap;
	}

	/**
	 * @param rmap
	 *            the rmap to set
	 */
	public void setRmap(Map<String, List<SysRole>> rmap) {
		this.rmap = rmap;
	}

	/**
	 * @return the ugsel
	 */
	public String[] getUgsel() {
		return ugsel;
	}

	/**
	 * @param ugsel
	 *            the ugsel to set
	 */
	public void setUgsel(String[] ugsel) {
		this.ugsel = ugsel;
	}

	/**
	 * @return the ursel
	 */
	public String[] getUrsel() {
		return ursel;
	}

	/**
	 * @param ursel
	 *            the ursel to set
	 */
	public void setUrsel(String[] ursel) {
		this.ursel = ursel;
	}

	/**
	 * @return the newpwd
	 */
	public String getNewpwd() {
		return newpwd;
	}

	/**
	 * @param newpwd
	 *            the newpwd to set
	 */
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	/**
	 * @return the oldpwd
	 */
	public String getOldpwd() {
		return oldpwd;
	}

	/**
	 * @param oldpwd
	 *            the oldpwd to set
	 */
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tlys.comm.bas._BaseAction#prepare()
	 */
	@Override
	public void prepare() throws Exception {
		initOpraMap("SYS-RGU");
	}

	public String getCorpids() {
		return corpids;
	}

	public void setCorpids(String corpids) {
		this.corpids = corpids;
	}

	public Map getCorpMap() {
		return corpMap;
	}

	public void setCorpMap(Map corpMap) {
		this.corpMap = corpMap;
	}

	public String getCorptab() {
		return corptab;
	}

	public void setCorptab(String corptab) {
		this.corptab = corptab;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public Map<Integer, List<SysUser>> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<Integer, List<SysUser>> userMap) {
		this.userMap = userMap;
	}

	public String getNewpwd2() {
		return newpwd2;
	}

	public void setNewpwd2(String newpwd2) {
		this.newpwd2 = newpwd2;
	}

	public String getSchobjkey() {
		return schobjkey;
	}

	public void setSchobjkey(String schobjkey) {
		this.schobjkey = schobjkey;
	}

	public String getPageflag() {
		return pageflag;
	}

	public void setPageflag(String pageflag) {
		this.pageflag = pageflag;
	}

	public Map<String, DicAreacorp> getDicAreacorpMap() {
		return dicAreacorpMap;
	}

	public void setDicAreacorpMap(Map<String, DicAreacorp> dicAreacorpMap) {
		this.dicAreacorpMap = dicAreacorpMap;
	}

	public Map<String, DicSinocorp> getDicSinocorpMap() {
		return dicSinocorpMap;
	}

	public void setDicSinocorpMap(Map<String, DicSinocorp> dicSinocorpMap) {
		this.dicSinocorpMap = dicSinocorpMap;
	}

	public Map<String, List<SysUser>> getUserByCorpIdMap() {
		return userByCorpIdMap;
	}

	public void setUserByCorpIdMap(Map<String, List<SysUser>> userByCorpIdMap) {
		this.userByCorpIdMap = userByCorpIdMap;
	}
}
