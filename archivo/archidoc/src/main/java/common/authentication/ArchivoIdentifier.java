package common.authentication;

import java.io.Serializable;
import java.security.Principal;

import common.util.StringUtils;

/**
 * Clase que implementa el interfaz Principal para representar una entidad, como
 * por ejemplo un individuo, una empresa, y un identificador de acceso.
 */
public class ArchivoIdentifier implements Principal, Serializable, Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// =======================================================================
	// Constantes para los nombres de los principales
	// =======================================================================
	/** Identificador del usuario en el Sistema Gestor de Usuarios. */
	public static final String EXTERNAL_USER_ID = "EXTERNAL_USER_ID";

	/** Login del usuario. */
	public static final String USER_LOGIN = "USER_LOGIN";

	/** Tipo de usuario. */
	public static final String USER_TYPE = "USER_TYPE";

	/** Dirección IP desde la que se conecta el usuario. */
	public static final String USER_IP_ADDRESS = "USER_IP_ADDRESS";

	/** Entidad del usuario. */
	public static final String ENTITY = "ENTITY";

	/** Sesión del usuario. */
	public static final String SESSION = "SESSION";

	/** Sesión del usuario Administrador. */
	public static final String SESSION_ADM = "SESSION_ADM";

	/** Idioma del usuario. */
	public static final String IDIOMA = "IDIOMA";

	// =======================================================================

	/** Nombre del principal. */
	private String name;

	/** Valor del principal. */
	private String value;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Nombre del principal.
	 * @param value
	 *            Valor del principal.
	 */
	public ArchivoIdentifier(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * Obtiene el nombre del principal.
	 * 
	 * @return Nombre del principal.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del principal.
	 * 
	 * @param name
	 *            Nombre del principal.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el valor del principal.
	 * 
	 * @return Valor del principal.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Establece el valor del principal.
	 * 
	 * @param value
	 *            Valor del principal.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Obtiene una representación del objeto.
	 * 
	 * @return Representación del objeto.
	 */
	public String toString() {
		return getName() + ":" + getValue();
	}

	/**
	 * Copia la información del principal especificado al actual.
	 * 
	 * @param identifier
	 *            Principal.
	 */
	public void copy(ArchivoIdentifier identifier) {
		setName(identifier.getName());
		setValue(identifier.getValue());
	}

	/**
	 * Compara este principal con el objeto especificado.
	 * 
	 * @param identifier
	 *            Principal.
	 * @return true si el principal especificado es igual que el objeto actual,
	 *         false en caso contrario.
	 */
	public boolean equals(Object identifier) {
		return (identifier instanceof ArchivoIdentifier)
				&& StringUtils.equals(name,
						((ArchivoIdentifier) identifier).getName())
				&& StringUtils.equals(value,
						((ArchivoIdentifier) identifier).getValue());
	}
}
