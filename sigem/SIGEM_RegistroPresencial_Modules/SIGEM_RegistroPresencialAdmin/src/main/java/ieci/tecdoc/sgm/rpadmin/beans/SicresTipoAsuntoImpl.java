package ieci.tecdoc.sgm.rpadmin.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Gestión de la Tabla SCR_CA 
 */
public class SicresTipoAsuntoImpl {
	private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy"); 
	
	/**
	 * Identificador del Tipo de Asunto
	 */
	private int id;
	/**
	 * Código del Tipo de Asunto
	 */
	private String code;
	
	/**
	 * Texto del Tipo de Asunto
	 */
	private String matter;
	
	/**
	 * Indica si el Tipo de Asunto está habilitado para asientos de registro de Entrada
	 * 0 - No Habilitado
	 * 1 - Habilitado
	 */
	private int forEreg;
	
	/**
	 * Indica si el Tipo de Asunto está habilitado para asientos de registro de Salida
	 * 0 - No Habilitado
	 * 1 - Habilitado
	 */
	private int forSreg;
	
	/**
	 * Indica si el Tipo de Asunto puede usarse por todas las oficinas de registro
	 * 0 - No
	 * 1 - Si
	 */
	private int allOfics;
	
	/**
	 * Identificador del Archivador invesDoc en el que se almacenan los datos auxiliares de registro
	 * asociados al tipo de asunto
	 */
	private int idArch;
	
	/**
	 * Fecha de creación del tipo de Asunto
	 */
	private Date creationDate;
	
	/**
	 * Fecha de baja del Tipo de Asunto
	 */
	private Date disableDate;
	
	/**
	 * Indica si el tipo de Asunto está dado de baja o no 
	 * 0 - No
	 * 1 - Si
	 */
	private int enabled;
	
	/**
	 * Identificador de Unidad administrativa asociada
	 */
	private int idOrg;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMatter() {
		return matter;
	}
	public void setMatter(String matter) {
		this.matter = matter;
	}
	public int getForEreg() {
		return forEreg;
	}
	public void setForEreg(int forEreg) {
		this.forEreg = forEreg;
	}
	public int getForSreg() {
		return forSreg;
	}
	public void setForSreg(int forSreg) {
		this.forSreg = forSreg;
	}
	public int getAllOfics() {
		return allOfics;
	}
	public void setAllOfics(int allOfics) {
		this.allOfics = allOfics;
	}
	public int getIdArch() {
		return idArch;
	}
	public void setIdArch(int idArch) {
		this.idArch = idArch;
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
	public int getIdOrg() {
		return idOrg;
	}
	public void setIdOrg(int idOrg) {
		this.idOrg = idOrg;
	}
	
	
	public String getCreationDateVista() {
		if(this.creationDate==null){
			return "";
		} else {
			return dateFormatter.format(this.creationDate);
		}
	}
	public void setCreationDateVista(String creationDate) throws ParseException {
		if(creationDate == null || "".equals(creationDate)) {
			this.creationDate = null;
		} else {
			this.creationDate = dateFormatter.parse(creationDate);
		}
	}
	public String getDisableDateVista() {
		if(this.disableDate==null){
			return "";
		} else {
			return dateFormatter.format(this.disableDate);
		}
	}
	
	public void setDisableDateVista(String disableDate) throws ParseException {
		if(disableDate == null || "".equals(disableDate)) {
			this.disableDate = null;
		} else {
			this.disableDate = dateFormatter.parse(disableDate);
		}
	}
}
