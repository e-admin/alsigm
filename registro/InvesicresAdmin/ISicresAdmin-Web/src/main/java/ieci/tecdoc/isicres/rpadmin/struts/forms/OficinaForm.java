package ieci.tecdoc.isicres.rpadmin.struts.forms;

import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class OficinaForm extends RPAdminBaseForm {

	private String[] attrsToUpper = new String[] { "codigo", "abreviatura",
			"nombre", "representante", "direccion", "ciudad", "provincia",
			"uri" };

	private static final long serialVersionUID = 1L;
	private String id;
	private String deptId;
	private String codigo;
	private String abreviatura;
	private String nombre;
	private String idTipoOficina;
	private Date fecha;
	private Date fechaBaja;
	private String idOrgs;
	private String sello;
	private String representante;
	private String direccion;
	private String ciudad;
	private String codigoPostal;
	private String provincia;
	private String telefono;
	private String fax;
	private String uri;
	private String nombreDept;
	private String tipoDepartamento;
	private String ldapDescription;

	//intercambio registral
	private String idEntidadRegistral;
	private String codEntidadReg;
	private String nameEntidadReg;

	public String getNombreDept() {
		return nombreDept;
	}
	public void setNombreDept(String nombreDept) {
		this.nombreDept = nombreDept;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public String getFechaVista() {
		if(fecha==null){
			return "";
		} else {
			return Utils.formatDate(fecha);
		}
	}
	public void setFechaVista(String fechaVista) throws ParseException {
		if(fechaVista == null || "".equals(fechaVista)) {
			fecha = null;
		} else {
			fecha = Utils.formatString(fechaVista);
		}
	}
	public String getFechaBajaVista() {
		if(fechaBaja==null){
			return "";
		} else {
			return Utils.formatDate(fechaBaja);
		}
	}

	public void setFechaBajaVista(String fechaBajaVista) throws ParseException {
		if(fechaBajaVista == null || "".equals(fechaBajaVista)) {
			fechaBaja = null;
		} else {
			fechaBaja = Utils.formatString(fechaBajaVista);
		}
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdOrgs() {
		return idOrgs;
	}
	public void setIdOrgs(String idOrgs) {
		this.idOrgs = idOrgs;
	}

	public String getIdTipoOficina() {
		return idTipoOficina;
	}
	public void setIdTipoOficina(String idTipoOficina) {
		this.idTipoOficina = idTipoOficina;
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
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public String getSello() {
		return sello;
	}
	public void setSello(String sello) {
		this.sello = sello;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTipoDepartamento() {
		return tipoDepartamento;
	}
	public void setTipoDepartamento(String tipoDepartamento) {
		this.tipoDepartamento = tipoDepartamento;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	/**
	 * @return the ldapDescription
	 */
	public String getLdapDescription() {
		return ldapDescription;
	}
	/**
	 * @param ldapDescription the ldapDescription to set
	 */
	public void setLdapDescription(String ldapDescription) {
		this.ldapDescription = ldapDescription;
	}
	public void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		super.toUpperCase(this, request);
	}

	public String[] getAttrsToUpper() {
		return attrsToUpper;
	}

	/**
	 *
	 * @return the intercambio registral
	 *
	 */

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
	public String getIdEntidadRegistral() {
		return idEntidadRegistral;
	}
	public void setIdEntidadRegistral(String idEntidadRegistral) {
		this.idEntidadRegistral = idEntidadRegistral;
	}

}
