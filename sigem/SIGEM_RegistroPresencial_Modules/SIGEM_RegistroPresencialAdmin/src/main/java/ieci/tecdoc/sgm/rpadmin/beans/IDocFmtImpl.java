package ieci.tecdoc.sgm.rpadmin.beans;

import java.util.Date;
/*$Id*/

public class IDocFmtImpl {
	private int id;
	private String name;
	private int archId;
	private int type;
	private int subtype;
	private String data;
	private String remarks;
	private int accesstype;
	private int acsid;
	private int crtrid;
	private Date crtnDate;

	public int getAccesstype() {
		return accesstype;
	}
	public void setAccesstype(int accesstype) {
		this.accesstype = accesstype;
	}
	public int getAcsid() {
		return acsid;
	}
	public void setAcsid(int acsid) {
		this.acsid = acsid;
	}
	public int getArchId() {
		return archId;
	}
	public void setArchId(int archId) {
		this.archId = archId;
	}
	public Date getCrtnDate() {
		return crtnDate;
	}
	public void setCrtnDate(Date crtnDate) {
		this.crtnDate = crtnDate;
	}
	public int getCrtrid() {
		return crtrid;
	}
	public void setCrtrid(int crtrid) {
		this.crtrid = crtrid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getSubtype() {
		return subtype;
	}
	public void setSubtype(int subtype) {
		this.subtype = subtype;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
