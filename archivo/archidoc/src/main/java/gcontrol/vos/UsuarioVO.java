package gcontrol.vos;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import se.usuarios.TipoUsuario;

import common.Constants;
import common.vos.BaseVO;
import common.vos.IKeyValue;

/**
 * Clase que almacena la información de un usuario en archivo.
 */
public class UsuarioVO extends BaseVO implements IKeyValue {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del usuario. */
	private String id = null;

	/** Nombre del usuario. */
	private String nombre = null;

	/** Apellidos del usuario. */
	private String apellidos = null;

	/** Dirección de correo electrónico del usuario. */
	private String email = null;

	/** Dirección del usuario. */
	private String direccion = null;

	/** Tipo de usuario. */
	private String tipo = null;

	/** Indica si el usuario está habilitado. */
	private String habilitado = null;

	/** Fecha tope de vigencia del usuario. */
	private Date fMaxVigencia = null;

	/** Identificador del usuario en el Sistema Gestor de Usuarios. */
	private String idUsrsExtGestor = null;

	/** Identificador del usuario en el Sistema Gestor de Organización. */
	private String idUsrSistOrg = null;

	/** Descripción del usuario. */
	private String descripcion = null;

	/**
	 * Constructor
	 */
	public UsuarioVO() {
		super();
	}

	/**
	 * @return Returns the apellidos.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos
	 *            The apellidos to set.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the direccion.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            The direccion to set.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the fMaxVigencia.
	 */
	public Date getFMaxVigencia() {
		return fMaxVigencia;
	}

	/**
	 * @param maxVigencia
	 *            The fMaxVigencia to set.
	 */
	public void setFMaxVigencia(Date maxVigencia) {
		fMaxVigencia = maxVigencia;
	}

	/**
	 * @return Returns the habilitado.
	 */
	public String getHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado
	 *            The habilitado to set.
	 */
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the idUsrsExtGestor.
	 */
	public String getIdUsrsExtGestor() {
		return idUsrsExtGestor;
	}

	/**
	 * @param idUsrsExtGestor
	 *            The idUsrsExtGestor to set.
	 */
	public void setIdUsrsExtGestor(String idUsrsExtGestor) {
		this.idUsrsExtGestor = idUsrsExtGestor;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the idUsrSistOrg.
	 */
	public String getIdUsrSistOrg() {
		return idUsrSistOrg;
	}

	/**
	 * @param idUsrSistOrg
	 *            The idUsrSistOrg to set.
	 */
	public void setIdUsrSistOrg(String idUsrSistOrg) {
		this.idUsrSistOrg = idUsrSistOrg;
	}

	/**
	 * @return Returns the tipo.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            The tipo to set.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombreCompleto() {
		StringBuffer nombreCompleto = new StringBuffer(Constants.STRING_EMPTY);

		if (StringUtils.isNotBlank(apellidos))
			nombreCompleto.append(getApellidos()).append(", ");

		if (StringUtils.isNotBlank(nombre))
			nombreCompleto.append(getNombre());
		return nombreCompleto.toString();
	}

	public String getNombreYApellidos() {
		StringBuffer nombreCompleto = new StringBuffer();
		nombreCompleto.append(getNombre());

		if (StringUtils.isNotBlank(getApellidos())) {
			nombreCompleto.append(Constants.STRING_SPACE)
					.append(getApellidos());
		}

		return nombreCompleto.toString();

	}

	public boolean equals(Object obj) {
		boolean result = false;
		if (obj == this)
			result = true;
		else if (obj instanceof UsuarioVO) {
			UsuarioVO objAsUsuario = (UsuarioVO) obj;
			result = StringUtils.equals(this.getId(), objAsUsuario.getId());
		}
		return result;
	}

	public boolean isAdministrador() {
		return TipoUsuario.ADMINISTRADOR.equals(tipo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getKey()
	 */
	public String getKey() {
		if (StringUtils.isNotBlank(id)) {
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
