package ieci.tecdoc.sgm.rpadmin.beans;

import java.util.Date;
/*$Id*/

public class SicresOrganizacionImpl {
	
	public static final int TIPO_PROPIOS = 1;
	public static final int ENABLED = 1;
	
	private int id;
	private String code;
	private int idFather;
	private String acron;
	private String name;
	private Date creationDate;
	private Date disableDate;
	private int type;
	private int enabled;
	private String cif;
	public String getAcron() {
		return acron;
	}
	public void setAcron(String acron) {
		this.acron = acron;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
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
	public Date getDisableDate() {
		return disableDate;
	}
	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdFather() {
		return idFather;
	}
	public void setIdFather(int idFather) {
		this.idFather = idFather;
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
}
