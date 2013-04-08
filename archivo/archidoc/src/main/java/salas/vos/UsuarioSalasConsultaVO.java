/**
 *
 */
package salas.vos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.Constants;
import common.util.DateUtils;
import common.vos.BaseVO;
import common.vos.IKeyValue;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UsuarioSalasConsultaVO extends BaseVO implements IKeyValue {

	private static final long serialVersionUID = 1L;

	private String id;
	private Integer tipoDocIdentificacion;
	private String numDocIdentificacion;
	private String nombre;
	private String apellidos;
	private String nacionalidad;
	private String telefonos;
	private String email;
	private String direccion;
	private Date fechaAlta;
	private String vigente;
	private String idscausr;

	private String nombreCompletoUsuarioAplicacion;

	/**
	 * Lista de Archivos a los que pertenece el usuario
	 */
	private List listaArchivos = new ArrayList();

	/**
	 * Archivos a los que pertenece el usuario.
	 */
	private String[] idsArchivos = new String[0];

	private String nombreArchivo;

	private String nombreTema;

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el tipoDocIdentificacion
	 */
	public Integer getTipoDocIdentificacion() {
		return tipoDocIdentificacion;
	}

	/**
	 * @param tipoDocIdentificacion
	 *            el tipoDocIdentificacion a fijar
	 */
	public void setTipoDocIdentificacion(Integer tipoDocIdentificacion) {
		this.tipoDocIdentificacion = tipoDocIdentificacion;
	}

	/**
	 * @return el numDocIdentificacion
	 */
	public String getNumDocIdentificacion() {
		return numDocIdentificacion;
	}

	/**
	 * @param numDocIdentificacion
	 *            el numDocIdentificacion a fijar
	 */
	public void setNumDocIdentificacion(String numDocIdentificacion) {
		this.numDocIdentificacion = numDocIdentificacion;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos
	 *            el apellidos a fijar
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return el nacionalidad
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}

	/**
	 * @param nacionalidad
	 *            el nacionalidad a fijar
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	/**
	 * @return el telefonos
	 */
	public String getTelefonos() {
		return telefonos;
	}

	/**
	 * @param telefonos
	 *            el telefonos a fijar
	 */
	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}

	/**
	 * @return el email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            el email a fijar
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return el direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            el direccion a fijar
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return el fAlta
	 */
	public Date getFechaAlta() {
		return fechaAlta;
	}

	/**
	 * @return el fAlta
	 */
	public String getFechaAltaString() {
		return DateUtils.formatDate(fechaAlta);
	}

	/**
	 * @param fAlta
	 *            el fAlta a fijar
	 */
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	/**
	 * @return el vigente
	 */
	public String getVigente() {
		return vigente;
	}

	/**
	 * @param vigente
	 *            el vigente a fijar
	 */
	public void setVigente(String vigente) {
		this.vigente = vigente;
	}

	/**
	 * @return el idscausr
	 */
	public String getIdscausr() {
		return idscausr;
	}

	/**
	 * @param idscausr
	 *            el idscausr a fijar
	 */
	public void setIdscausr(String idscausr) {
		this.idscausr = idscausr;
	}

	public void setListaArchivos(List listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	public List getListaArchivos() {
		return listaArchivos;
	}

	public void setIdsArchivos(String[] idsArchivos) {
		this.idsArchivos = idsArchivos;
	}

	public String[] getIdsArchivos() {
		return idsArchivos;
	}

	public void setNombreCompletoUsuarioAplicacion(
			String nombreCompletoUsuarioAplicacion) {
		this.nombreCompletoUsuarioAplicacion = nombreCompletoUsuarioAplicacion;
	}

	public String getNombreCompletoUsuarioAplicacion() {
		return nombreCompletoUsuarioAplicacion;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getNombreTema() {
		return nombreTema;
	}

	public void setNombreTema(String nombreTema) {
		this.nombreTema = nombreTema;
	}

	public String getNombreCompleto() {
		StringBuffer nombreCompleto = new StringBuffer("");
		if (StringUtils.isNotBlank(apellidos))
			nombreCompleto.append(getApellidos()).append(", ");

		if (StringUtils.isNotBlank(nombre)) {
			nombreCompleto.append(getNombre());
		}

		return nombreCompleto.toString();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getKey()
	 */
	public String getKey() {
		if (StringUtils.isNotBlank(this.id)) {
			return this.id;
		} else {
			return Constants.STRING_EMPTY;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getValue()
	 */
	public String getValue() {
		return getNombreCompleto();
	}

}
