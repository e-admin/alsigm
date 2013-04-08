package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

public class UnidadForm extends RPAdminBaseForm {

	private static final long serialVersionUID = 1L;

	private String[] attrsToUpper = new String[] { "uid", "abreviatura",
			"nombre", "cif", "direccion", "ciudad", "provincia", "uri" };

	private String id;
	private String uid;
	private String idPadre;
	private String abreviatura;
	private String nombre;
	private String fechaAltaVista;
	private String fechaBajaVista;
	private String tipo;
	private String cif;
	private String direccion;
	private String ciudad;
	private String codigoPostal;
	private String provincia;
	private String telefono;
	private String fax;
	private String uri;

	//intercambio registral
	private int idUnidadTramit;

	private String codEntidadReg;
	private String nameEntidadReg;

	private String codeUnidadTramit;
	private String nameUnidadTramit;
	//


	public String getCodeUnidadTramit() {
		return codeUnidadTramit;
	}
	public int getIdUnidadTramit() {
		return idUnidadTramit;
	}
	public void setIdUnidadTramit(int idUnidadTramit) {
		this.idUnidadTramit = idUnidadTramit;
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

	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getFechaAltaVista() {
		return fechaAltaVista;
	}
	public void setFechaAltaVista(String fechaAltaVista) {
		this.fechaAltaVista = fechaAltaVista;
	}
	public String getFechaBajaVista() {
		return fechaBajaVista;
	}
	public void setFechaBajaVista(String fechaBajaVista) {
		this.fechaBajaVista = fechaBajaVista;
	}

	public void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		super.toUpperCase(this, request);
	}

	public String[] getAttrsToUpper() {
		return attrsToUpper;
	}


}
