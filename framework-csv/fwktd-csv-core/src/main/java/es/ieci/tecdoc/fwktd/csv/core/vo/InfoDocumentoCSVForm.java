package es.ieci.tecdoc.fwktd.csv.core.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;

/**
 * Información del documento para generar el CSV.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class InfoDocumentoCSVForm extends BaseValueObject {

	private static final long serialVersionUID = 2625674065935223667L;

	/**
	 * Nombre del documento.
	 */
	private String nombre = null;

	/**
	 * Descripciones del documento en distintos idiomas.
	 */
	private Map<String, String> descripciones = new HashMap<String, String>();

	/**
	 * Tipo MIME del documento.
	 */
	private String tipoMime = null;

	/**
	 * Fecha de creación del documento.
	 */
	private Date fechaCreacion = null;

	/**
	 * Fecha de caducidad del documento.
	 */
	private Date fechaCaducidad = null;

	/**
	 * Código de la aplicación que almacena el documento.
	 */
	private String codigoAplicacion = null;

	/**
	 * Indica la disponibilidad del documento en la aplicación externa.
	 */
	private boolean disponible = true;

	/**
	 * Constructor.
	 */
	public InfoDocumentoCSVForm() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoMime() {
		return tipoMime;
	}

	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}

	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Map<String, String> getDescripciones() {
		return descripciones;
	}

	public void addDescripciones(Map<String, String> descripciones) {
		if (descripciones != null) {
			this.descripciones.putAll(descripciones);
		}
	}

	/**
	 * Añade la descripción por defecto del documento.
	 * @param descripcion Descripción del documento.
	 */
	public void addDescripcionPorDefecto(String descripcion) {
		getDescripciones().put("default", descripcion);
	}

	/**
	 * Añade una descripción del documento.
	 * @param locale Locale.
	 * @param descripcion Descripción del documento.
	 */
	public void addDescripcion(Locale locale, String descripcion) {
		if (locale != null) {
			getDescripciones().put(locale.toString(), descripcion);
			if (getDescripciones().get("default") == null) {
				getDescripciones().put("default", descripcion);
			}
		} else {
			getDescripciones().put("default", descripcion);
		}
	}

	/**
	 * Obtiene la descripción del documento
	 * @param locale Locale.
	 * @return Descripción del documento.
	 */
	public String getDescripcion(Locale locale) {

		String descripcion = null;

		if (locale == null) {

			// Obtener la descripción marcada por defecto
			descripcion = getDescripciones().get("default");

		} else {

			// Obtener la descripción en el locale indicado: idioma_pais_variante
			descripcion = getDescripciones().get(locale.toString());
			if (descripcion == null) {

				// Obtener la descripción en el locale: idioma_pais
				descripcion = getDescripciones().get(locale.getLanguage() + "_" + locale.getCountry());
				if (descripcion == null) {

					// Obtener la descripción en el locale: idioma
					descripcion = getDescripciones().get(locale.getLanguage());
					if (descripcion == null) {

						// Obtener la descripción marcada por defecto
						descripcion = getDescripciones().get("default");
					}
				}
			}
		}

		return descripcion;
	}

}
