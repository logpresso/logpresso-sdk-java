package com.logpresso.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	// required parameters
	private String loginName;
	private String name;
	private String password;

	// optional parameters
	private String department;
	private String description;
	private String otpSeed;
	private String title;
	private String email;
	private String phone;
	private String role = "member";
	private String lang;
	private String menuProfile = "member";
	private List<String> grantedTables = new ArrayList<String>();
	private List<String> grantedMenuProfiles;
	private List<String> trustHosts = new ArrayList<String>();
	private boolean enforcePasswordChange;
	private boolean useLoginLock = true;
	private boolean useIdleTimeout = true;
	private boolean useOtp;
	private boolean useAcl;
	private boolean enabled = true;
	private int idleTimeout = 300;
	private int loginLockCount = 3;

	// read-only parameters
	private String hashType;
	private int loginFailures;
	private Date lastPasswordChange;
	private Date lastLoginSuccess;
	private Date lastLoginFailure;
	private Date created;
	private Date updated;

	public Map<String, Object> toMap() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("login_name", loginName);
		m.put("name", name);
		m.put("description", description);
		m.put("password", password);
		m.put("org_unit_name", department);
		m.put("otp_seed", otpSeed);
		m.put("hash_type", hashType);
		m.put("title", title);
		m.put("email", email);
		m.put("phone", phone);
		m.put("role", role);
		m.put("lang", lang);
		m.put("program_profile", menuProfile);
		m.put("table_names", grantedTables);
		m.put("granted_program_profiles", grantedMenuProfiles);
		m.put("trust_hosts", trustHosts);
		m.put("enforce_password_change", enforcePasswordChange);
		m.put("use_login_lock", useLoginLock);
		m.put("use_idle_timeout", useIdleTimeout);
		m.put("use_otp", useOtp);
		m.put("use_acl", useAcl);
		m.put("is_enabled", enabled);
		m.put("idle_timeout", idleTimeout);
		m.put("login_lock_count", loginLockCount);
		m.put("login_failures", loginFailures);
		m.put("last_password_change", lastPasswordChange);
		m.put("last_login_success", lastLoginSuccess);
		m.put("last_login_failure", lastLoginFailure);
		m.put("created", created);
		m.put("updated", updated);

		return m;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOtpSeed() {
		return otpSeed;
	}

	public void setOtpSeed(String otpSeed) {
		this.otpSeed = otpSeed;
	}

	public String getHashType() {
		return hashType;
	}

	public void setHashType(String hashType) {
		this.hashType = hashType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getMenuProfile() {
		return menuProfile;
	}

	public void setMenuProfile(String menuProfile) {
		this.menuProfile = menuProfile;
	}

	public List<String> getGrantedTables() {
		return grantedTables;
	}

	public void setGrantedTables(List<String> grantedTables) {
		this.grantedTables = grantedTables;
	}

	public List<String> getGrantedMenuProfiles() {
		return grantedMenuProfiles;
	}

	public void setGrantedMenuProfiles(List<String> grantedMenuProfiles) {
		this.grantedMenuProfiles = grantedMenuProfiles;
	}

	public List<String> getTrustHosts() {
		return trustHosts;
	}

	public void setTrustHosts(List<String> trustHosts) {
		this.trustHosts = trustHosts;
	}

	public boolean isEnforcePasswordChange() {
		return enforcePasswordChange;
	}

	public void setEnforcePasswordChange(boolean enforcePasswordChange) {
		this.enforcePasswordChange = enforcePasswordChange;
	}

	public boolean isUseLoginLock() {
		return useLoginLock;
	}

	public void setUseLoginLock(boolean useLoginLock) {
		this.useLoginLock = useLoginLock;
	}

	public boolean isUseIdleTimeout() {
		return useIdleTimeout;
	}

	public void setUseIdleTimeout(boolean useIdleTimeout) {
		this.useIdleTimeout = useIdleTimeout;
	}

	public boolean isUseOtp() {
		return useOtp;
	}

	public void setUseOtp(boolean useOtp) {
		this.useOtp = useOtp;
	}

	public boolean isUseAcl() {
		return useAcl;
	}

	public void setUseAcl(boolean useAcl) {
		this.useAcl = useAcl;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public int getLoginLockCount() {
		return loginLockCount;
	}

	public void setLoginLockCount(int loginLockCount) {
		this.loginLockCount = loginLockCount;
	}

	public int getLoginFailures() {
		return loginFailures;
	}

	public void setLoginFailures(int loginFailures) {
		this.loginFailures = loginFailures;
	}

	public Date getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(Date lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public Date getLastLoginSuccess() {
		return lastLoginSuccess;
	}

	public void setLastLoginSuccess(Date lastLoginSuccess) {
		this.lastLoginSuccess = lastLoginSuccess;
	}

	public Date getLastLoginFailure() {
		return lastLoginFailure;
	}

	public void setLastLoginFailure(Date lastLoginFailure) {
		this.lastLoginFailure = lastLoginFailure;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
