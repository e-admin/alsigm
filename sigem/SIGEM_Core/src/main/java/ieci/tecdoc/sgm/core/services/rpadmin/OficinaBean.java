package ieci.tecdoc.sgm.core.services.rpadmin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Extensión de oficina registrar para la gestión de oficinas, departamentos y
 * direcciones
 *
 *
 *
 */
public class OficinaBean {

	private int id;
	private int deptId;
	private String codigo;
	private String abreviatura;
	private String nombre;
	private int idTipoOficina;
	private Date fecha;
	private Date fechaBaja;
	private int idOrgs;
	private String sello;
	private String representante;
	private String direccion;
	private String ciudad;
	private String codigoPostal;
	private String provincia;
	private String telefono;
	private String fax;
	private String uri;
	private int tipoDepartamento = -1;
	private String ldapGuid;
	private String ldapDn;
	private String ldapDescription;

	//intercambio registral
	private int idEntidadRegistral;
	private String codEntidadReg;
	private String nameEntidadReg;

	private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * @return
	 */
	public String getAbreviatura() {
		return abreviatura;
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
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return
	 */
	public String getFechaVista() {
		if (fecha == null) {
			return "";
		} else {
			return dateFormatter.format(fecha);
		}
	}

	/**
	 * @param fechaVista
	 * @throws ParseException
	 */
	public void setFechaVista(String fechaVista) throws ParseException {
		if (fechaVista == null || "".equals(fechaVista)) {
			fecha = null;
		} else {
			fecha = dateFormatter.parse(fechaVista);
		}
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
	public int getIdTipoOficina() {
		return idTipoOficina;
	}

	/**
	 * @param idTipoOficina
	 */
	public void setIdTipoOficina(int idTipoOficina) {
		this.idTipoOficina = idTipoOficina;
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
	public String getRepresentante() {
		return representante;
	}

	/**
	 * @param representante
	 */
	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	/**
	 * @return
	 */
	public String getSello() {
		return sello;
	}

	/**
	 * @param sello
	 */
	public void setSello(String sello) {
		this.sello = sello;
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
	public int getTipoDepartamento() {
		return tipoDepartamento;
	}

	/**
	 * @return
	 */
	public int getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId
	 */
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	/**
	 * Establece el tipo de departamento 1=Invesdoc 2=Ldap
	 *
	 * @param tipoDepartamento
	 */
	/**
	 * @param tipoDepartamento
	 */
	public void setTipoDepartamento(int tipoDepartamento) {
		this.tipoDepartamento = tipoDepartamento;
	}

	/**
	 * @param ldapGuid
	 */
	public void setLdapGuid(String ldapGuid) {
		this.ldapGuid = ldapGuid;
	}

	/**
	 * @return
	 */
	public String getLdapGuid() {
		return ldapGuid;
	}

	/**
	 * @return
	 */
	public String getLdapDn() {
		return ldapDn;
	}

	/**
	 * @param ldapDn
	 */
	public void setLdapDn(String ldapDn) {
		this.ldapDn = ldapDn;
	}

	/**
	 * @return
	 */
	public String getLdapDescription() {
		return ldapDescription;
	}

	/**
	 * @param ldapDescription
	 */
	public void setLdapDescription(String ldapDescription) {
		this.ldapDn = ldapDescription;
	}

	/**
	 *
	 * @return
	 */
	public int getIdOrgs() {
		return idOrgs;
	}

	/**
	 *
	 * @param idOrgs
	 */
	public void setIdOrgs(int idOrgs) {
		this.idOrgs = idOrgs;
	}

	/**
	 *
	 * @return
	 */
	public int getIdEntidadRegistral() {
		return idEntidadRegistral;
	}

	/**
	 *
	 * @param idEntidadRegistral
	 */
	public void setIdEntidadRegistral(int idEntidadRegistral) {
		this.idEntidadRegistral = idEntidadRegistral;
	}

	/**
	 *
	 * @return
	 */
	public String getCodEntidadReg() {
		return codEntidadReg;
	}

	/**
	 *
	 * @param codEntidadReg
	 */
	public void setCodEntidadReg(String codEntidadReg) {
		this.codEntidadReg = codEntidadReg;
	}

	/**
	 *
	 * @return
	 */
	public String getNameEntidadReg() {
		return nameEntidadReg;
	}

	/**
	 *
	 * @param nameEntidadReg
	 */
	public void setNameEntidadReg(String nameEntidadReg) {
		this.nameEntidadReg = nameEntidadReg;
	}

}
