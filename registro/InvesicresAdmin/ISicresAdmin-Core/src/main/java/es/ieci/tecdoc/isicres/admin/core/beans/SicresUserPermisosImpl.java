package es.ieci.tecdoc.isicres.admin.core.beans;
/*$Id*/

import java.util.Date;

public class SicresUserPermisosImpl {
	private int idUsr;
	private Date tmstamp;
	private int perms;

	public int getIdUsr() {
		return idUsr;
	}
	public void setIdUsr(int idUsr) {
		this.idUsr = idUsr;
	}
	public int getPerms() {
		return perms;
	}
	public void setPerms(int perms) {
		this.perms = perms;
	}
	public Date getTmstamp() {
		return tmstamp;
	}
	public void setTmstamp(Date tmstamp) {
		this.tmstamp = tmstamp;
	}

	
	
}
