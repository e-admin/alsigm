package es.ieci.tecdoc.isicres.admin.beans;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Extensión de organismos para gestionar un organismo y su dirección
 *
 *
 *
 */
public class OrganizacionBean {

	private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	public static final int TIPO_PROPIOS = 1;
	private int id;
	private String uid;
	private int idPadre;
	private String abreviatura;
	private String nombre;
	private Date fechaAlta;
	private Date fechaBaja;
	private boolean enabled;
	private int tipo;
	private String cif;
	private String direccion;
	private String ciudad;
	private String codigoPostal;
	private String provincia;
	private String telefono;
	private String fax;
	private String uri;

	//Intercambio Registral
	private int idUnidadTramit;

	private String codEntidadReg;
	private String nameEntidadReg;

	private String codeUnidadTramit;
	private String nameUnidadTramit;
	//


	/**
	 * @return
	 */
	public String getAbreviatura() {
		return abreviatura;
	}

	public String getCodEntidadReg() {
		return codEntidadReg;
	}

	public void setCodEntidadReg(String codEntidadReg) {
		this.codEntidadReg = codEntidadReg;
	}

	public String getNameEntidadReg() {
		return nameEntidadReg;
	}

	public void setNameEntidadReg(String nameEntidadReg) {
		this.nameEntidadReg = nameEntidadReg;
	}

	public int getIdUnidadTramit() {
		return idUnidadTramit;
	}

	public void setIdUnidadTramit(int idUnidadTramit) {
		this.idUnidadTramit = idUnidadTramit;
	}

	public String getCodeUnidadTramit() {
		return codeUnidadTramit;
	}

	public void setCodeUnidadTramit(String codeUnidadTramit) {
		this.codeUnidadTramit = codeUnidadTramit;
	}

	public String getNameUnidadTramit() {
		return nameUnidadTramit;
	}

	public void setNameUnidadTramit(String nameUnidadTramit) {
		this.nameUnidadTramit = nameUnidadTramit;
	}

	/**
	 * @param abreviatura
	 */
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	/**
	 * @return
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}

	/**
	 * @return
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @param fechaAlta
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public int getIdPadre() {
		return idPadre;
	}

	/**
	 * @param idPadre
	 */
	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return
	 */
	public String getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return
	 */
	public Date getFechaBaja() {
		return fechaBaja;
	}

	/**
	 * @param fechaBaja
	 */
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	/**
	 * @return
	 */
	public String getFechaAltaVista() {
		if (fechaAlta == null) {
			return "";
		} else {
			return dateFormatter.format(fechaAlta);
		}
	}

	/**
	 * @param fechaAltaVista
	 * @throws ParseException
	 */
	public void setFechaAltaVista(String fechaAltaVista) throws ParseException {
		if (fechaAltaVista == null || "".equals(fechaAltaVista)) {
			fechaAlta = null;
		} else {
			fechaAlta = dateFormatter.parse(fechaAltaVista);
		}
	}

	/**
	 * @return
	 */
	public String getFechaBajaVista() {
		if (fechaBaja == null) {
			return "";
		} else {
			return dateFormatter.format(fechaBaja);
		}
	}

	/**
	 * @param fechaBajaVista
	 * @throws ParseException
	 */
	public void setFechaBajaVista(String fechaBajaVista) throws ParseException {
		if (fechaBajaVista == null || "".equals(fechaBajaVista)) {
			fechaBaja = null;
		} else {
			fechaBaja = dateFormatter.parse(fechaBajaVista);
		}
	}

}
