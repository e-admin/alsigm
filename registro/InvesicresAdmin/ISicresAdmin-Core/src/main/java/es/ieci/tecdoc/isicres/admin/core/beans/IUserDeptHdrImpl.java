package es.ieci.tecdoc.isicres.admin.core.beans;

import java.util.Date;
/*$Id*/

public class IUserDeptHdrImpl {
	
	private int id;
	private String name;
	private int parentid;
	private int mgrid;
	private int type;
	private int crtrid;
	private Date crtndate;
	private int updrid;
	private Date upddate;
	
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCrtrid() {
		return crtrid;
	}
	public void setCrtrid(int crtrid) {
		this.crtrid = crtrid;
	}
	public Date getCrtndate() {
		return crtndate;
	}
	public void setCrtndate(Date crtndate) {
		this.crtndate = crtndate;
	}
	public Date getUpddate() {
		return upddate;
	}
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public int getMgrid() {
		return mgrid;
	}
	public void setMgrid(int mgrid) {
		this.mgrid = mgrid;
	}
	public int getUpdrid() {
		return updrid;
	}
	public void setUpdrid(int updrid) {
		this.updrid = updrid;
	}
	
}
