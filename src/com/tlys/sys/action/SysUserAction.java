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
 * @author 冯彦明
 * 
 */

@Controller
// by spring,定义bean
@Scope("prototype")
// by struts2，定义action为原型模式
@ParentPackage("tlys-default")
// //by struts2，定义此pachage继承tlys-default(tlys-default已在struts.xml中定义)
@Namespace("/sys")
// by struts2，定义action的namespace
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
	 * 用户分层显示Map
	 */
	private Map<Integer, List<SysUser>> userMap;

	private String[] ugsel;
	private String[] ursel;

	/**
	 * 当前用户单位ID所对应的表标志(area,corp,dept，后改为0,1,2,3)
	 */
	private String corptab;
	/**
	 * 当前用户单位ID,corppop页面要用
	 */
	private String corpid;

	/**
	 * 当前用户所在单位的map（id=shortname) 可能是areacorp,sinocorp,sinodepartment
	 */
	private Map corpMap;

	// 从页面传回来的查询索引，用于导出表时得到当前查询条件
	private String schobjkey;

	/**
	 * 标志当前页面的标志，用于corpListPop页面 由于这一页面可由两个父页面打开 一个是input页面，一个是sch页面，所以要进行区别
	 * 以便在返回值时对合适的页面表单赋值
	 */
	private String pageflag;

	/**
	 * 从页面接收到的当前用户的数据权限(对各公司)
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
	 * 如果采用了struts2的校验，此注释使本方法跳过校验。
	 */
	@SkipValidation
	/**
	 * 使用注释定义此方法的result,用于定义一些特殊需求的跳转
	 */
	@Action(results = { @Result(name = "pass", location = "/sys-iframe.jsp", type = "redirect") })
	public String login() throws Exception {
		String rst = MSG;
		SysUser sysUserDb = null;
		if (null == sysUser || null == sysUser.getLoginid() || "".equals(sysUser.getLoginid())) {
			msg = new Msg(Msg.FAILURE, "用户不存在或用户名为空！");
		} else {
			String loginid = sysUser.getLoginid();
			String loginpwd = sysUser.getLoginpwd();
			sysUserDb = sysUserService.loadByLoginid(loginid);
			if (null == sysUserDb) {
				msg = new Msg(Msg.FAILURE, "系统中没有此用户！");
			} else {
				if (log.isDebugEnabled()) {
					log.debug("SysUserAction.login->sysUserDb.getIslocked()==" + sysUserDb.getIslocked());
				}
				String lockStr = sysUserDb.getIslocked();
				if ("1".equals(lockStr)) {// 用户已锁定
					msg = new Msg(Msg.FAILURE, "当前用户已被锁定，请联系管理员解锁后再登录。");
				} else {
					// 在此对比密码,通过
					if (loginpwd != null && sysUserDb.getLoginpwd().equals(loginpwd)) {
						rst = "pass";
					} else {
						msg = new Msg(Msg.FAILURE, "用户密码错误，请重新登录！");
						rst = "failure";
					}
				}
			}
		}

		if ("pass".equals(rst)) {
			// 在用记登录成功后，即开始在新开线程中运行首页初始化存储过程
			// 避免在加载首页时运行，影响首页打开速度

			new Thread() {
				public void run() {
					System.out.println("SysUserAction.login->开始执行存储过程new Date()==" + new Date());
					sysDatafillinfoService.exeProc();
					System.out.println("SysUserAction.login->存储过程执行完毕new Date()==" + new Date());
				}
			}.start();

			setSessionAttr("sysUserSess", sysUserDb);
			// 重新润色用户权限和单处登录，及日志保存问题
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
		// 遍历dicAreacorpMap集合获取区域分公司ID
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
	 * 分页列表，暂不公开使用
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

			// 当有查询条件时,将当前查询条件对象存入session中，并且将索引存在pageCtr中
			// 在生成页码跳转html时，应带上此参数
			if (null != sysUser) {
				String schObjKey = "sysUser_" + new Date().getTime();
				pageCtr.setSchObjKey(schObjKey);
				setSessionAttr(schObjKey, sysUser);
			}
		} else {
			// 当在前台点页码进入时，应同时传来schObjKey
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
	 * 导出excel
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
			throw new Exception("不能确定要删除的对象！");
		} else {
			sysUserService.delete(id);
			msg = new Msg(Msg.SUCCESS, "删除对象成功！", false, "parent.leftFrame.location.reload();", new String[] {
					"curTitle", "用户" });
		}
		return MSG;
	}

	/**
	 * 修改密码
	 */
	/*
	 * @SkipValidation public String changePwd() throws Exception { sysUser =
	 * sysUserService.load(id); if (!oldpwd.equals(sysUser.getLoginpwd())) { msg
	 * = new Msg(Msg.FAILURE, "输入的旧密码不正确。"); } else {
	 * sysUser.setLoginpwd(newpwd); sysUserService.saveOrUpdate(sysUser); msg =
	 * new Msg(Msg.SUCCESS, "密码已成功重置。"); } return MSG; }
	 */

	/**
	 * 重置密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String resetPwd() throws Exception {
		sysUser = sysUserService.load(id);
		sysUser.setLoginpwd("123456");
		sysUserService.saveOrUpdate(sysUser);
		msg = new Msg(Msg.SUCCESS, "密码已成功重置。");
		return MSG;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	@Action(value = "userpwd", results = { @Result(name = "modpage", location = "/sys/sys-user-pwd-input.jsp") })
	public String modPwd() throws Exception {
		sysUser = sysUserService.load(getCurUser().getUserid());
		if (!oldpwd.equals(sysUser.getLoginpwd())) {
			msg = new Msg(Msg.FAILURE, "输入的原密码不正确。");
			return "modpage";
		} else {
			if (sysUser.getUsername().indexOf("系统管理") > -1) {
				msg = new Msg(Msg.FAILURE, "对不起，系统管理员目前暂不能修改密码。");
				return "modpage";
			} else {
				if (null != newpwd) {
					sysUser.setLoginpwd(newpwd);
					sysUserService.saveOrUpdate(sysUser);
					msg = new Msg(Msg.SUCCESS, "密码已成功修改。", bdScript());
				} else {
					msg = new Msg(Msg.FAILURE, "密码修改失败。", bdScript());
				}
			}
			return MSG;
		}
	}

	/**
	 * 只用于修改密码后的页面脚本生成（去掉窗口下方的确定按钮)
	 * 
	 * @return
	 */
	private String bdScript() {
		return "frameElement.lhgDG.removeBtn('modok');";
	}

	/**
	 * 重置BI密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String resetBiPwd() throws Exception {
		sysUser = sysUserService.load(id);
		sysUser.setBipwd("123456");
		sysUserService.saveOrUpdate(sysUser);
		msg = new Msg(Msg.SUCCESS, "BI密码已成功重置。");
		return MSG;
	}

	/**
	 * 校验注释，此处提供服务器端校验
	 */
	@Validations(requiredFields = { @RequiredFieldValidator(type = ValidatorType.SIMPLE,
			fieldName = "sysUser.username", message = "姓名为必填。") }, emails = { @EmailValidator(
			type = ValidatorType.SIMPLE, fieldName = "sysUser.email", message = "email地址必须填写正确。") },
			stringLengthFields = { @StringLengthFieldValidator(type = ValidatorType.SIMPLE, trim = true,
					minLength = "3", maxLength = "8", fieldName = "sysUser.corpid", message = "单位代码长度必须在3-8之间。") })
	public String save() throws Exception {
		if (null == sysUser) {
			msg = new Msg(Msg.FAILURE, "未接收到用户信息！");
		} else {
			boolean isNew = false;
			if (null == sysUser.getUserid() || "".equals(sysUser.getUserid())) {
				isNew = true;
				String seqStr = sysUserService.getSeqStr();
				String loginid = sysUser.getLoginid();
				boolean isex = sysUserService.isexist(loginid);
				if (isex) {
					msg = new Msg(Msg.FAILURE, "当前用户登录名已存在，请更换用户登录名重试！");
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
			// 以下用于msg页面显示的设置

			msg = new Msg(Msg.SUCCESS, "添加/编辑用户成功！", false, "parent.leftFrame.location.reload();", new String[] {
					"curTitle", "用户" });
		}

		return "input";
	}

	@SkipValidation
	public String updug() throws Exception {
		sysUserService.updateUserGroup(ugsel, id, "userid");
		msg = new Msg(Msg.SUCCESS, "更新成功！");
		return MSG;
	}

	@SkipValidation
	public String updur() throws Exception {
		sysUserService.updateUserRole(ursel, id);
		msg = new Msg(Msg.SUCCESS, "更新成功！");
		return MSG;
	}

	@SkipValidation
	/**
	 * 锁定用户
	 */
	public String lock() throws Exception {
		sysUser = sysUserService.load(id);
		sysUser.setIslocked("1");
		sysUser.setLockedtime(new Date());
		sysUserService.saveOrUpdate(sysUser);
		msg = new Msg(Msg.SUCCESS, "当前用户已被锁定。");
		return "msg";
	}

	/**
	 * 解锁用户
	 */
	@SkipValidation
	public String unlock() throws Exception {
		sysUser = sysUserService.load(id);
		sysUser.setIslocked("0");
		sysUserService.saveOrUpdate(sysUser);
		msg = new Msg(Msg.SUCCESS, "当前用户已被解锁。");
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
		msg = new Msg(Msg.SUCCESS, "当前用户成功退出。");
		return "loginpage";
	}

	@SkipValidation
	public String msg() {
		msg = new Msg(Msg.SUCCESS, "当前用户成功退出。");
		return "msg";
	}

	/**
	 * 更新当前用户的数据权限
	 * 
	 * @return
	 * @throws Exception
	 */
	@SkipValidation
	public String updUserData() throws Exception {
		// sysUserService.updateUserData(corpids, id);
		sysUserService.updateUserdatas(corpids, id, menuid);
		msg = new Msg(Msg.SUCCESS, "更新成功！", false, "alert('更新成功！');frameElement.lhgDG.curWin.dgcorp.cancel();", null);
		return "msg";
	}

	/**
	 * 根据所选菜单，查看当前用户所有
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
	 * 显示用户单位弹出层
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
