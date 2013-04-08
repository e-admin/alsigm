package ieci.tecdoc.sgm.rpadmin.beans;

import java.util.Date;
/*$Id*/

public class SicresOficinaImpl {
	private int id;
	private String code;
	private String acron;
	private String name;
	private Date creationDate;
	private Date disableDate;
	private int idOrgs;
	private String stamp;
	private int deptId;
	private int type;
	private String ldapGuid;
	private String ldapDn;
	private int typeDept;
	private String groupDn;
	
	public String getAcron() {
		return acron;
	}
	public void setAcron(String acron) {
		this.acron = acron;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public Date getDisableDate() {
		return disableDate;
	}
	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdOrgs() {
		return idOrgs;
	}
	public void setIdOrgs(int idOrgs) {
		this.idOrgs = idOrgs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLdapGuid() {
		return ldapGuid;
	}
	public void setLdapGuid(String ldapGuid) {
		this.ldapGuid = ldapGuid;
	}
	public int getTypeDept() {
		return typeDept;
	}
	public void setTypeDept(int typeDept) {
		this.typeDept = typeDept;
	}
	public String getLdapDn() {
		return ldapDn;
	}
	public void setLdapDn(String ldapDn) {
		this.ldapDn = ldapDn;
	}
	public String getGroupDn() {
		return groupDn;
	}
	public void setGroupDn(String groupDn) {
		this.groupDn = groupDn;
	}
}