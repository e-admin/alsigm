package descripcion.model.xml.format;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un vínculo.
 */
public class DefFmtVinculo extends DefFmtEtiqueta {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** URL del vínculo. */
	private String url = null;

	/** Target de la URL. */
	private String target = null;

	/** Parametro del vínculo. */
	private String parameter = null;

	/** Id Valor campo de la URL. */
	private String idCampo = null;

	/**
	 * Constructor.
	 */
	public DefFmtVinculo() {
		super();
	}

	/**
	 * Obtiene la URL del vínculo.
	 * 
	 * @return URL del vínculo.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Establece la URL del vínculo.
	 * 
	 * @param url
	 *            URL del vínculo.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Obtiene el target de la URL.
	 * 
	 * @return Target de la URL.
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Establece el target de la URL.
	 * 
	 * @param target
	 *            Target de la URL.
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            the parameter to set
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the idCamp
	 */
	public String getIdCampo() {
		return idCampo;
	}

	/**
	 * @param idCamp
	 *            the idCamp to set
	 */
	public void setIdCampo(String idCampo) {
		this.idCampo = idCampo;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * 
	 * @param indent
	 *            Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<" + DefFmtTags.TAG_VINCULO_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		// Titulo
		xml.append(getTitulo().toXML(indent + 2));

		// Estilo
		xml.append(tabs + "  <" + DefFmtTags.TAG_ESTILO + ">");
		xml.append(getEstilo() != null ? getEstilo() : "");
		xml.append("</" + DefFmtTags.TAG_ESTILO + ">");
		xml.append(Constants.NEWLINE);

		// URL
		xml.append(tabs + "  <" + DefFmtTags.TAG_URL_ELEMENTO + ">");
		xml.append(url != null ? url : "");
		xml.append("</" + DefFmtTags.TAG_URL_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		// Target
		if (StringUtils.isNotBlank(target)) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_TARGET_ELEMENTO + ">");
			xml.append(target);
			xml.append("</" + DefFmtTags.TAG_TARGET_ELEMENTO + ">");
			xml.append(Constants.NEWLINE);
		}

		// Parameter
		if (StringUtils.isNotBlank(parameter)) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_PARAMETRO + ">");
			xml.append(parameter);
			xml.append("</" + DefFmtTags.TAG_PARAMETRO + ">");
			xml.append(Constants.NEWLINE);
		}

		// Id campo valor
		if (StringUtils.isNotBlank(idCampo)) {
			xml.append(tabs + "  <" + DefFmtTags.TAG_VALOR_PARAMETRO + ">");
			xml.append(idCampo);
			xml.append("</" + DefFmtTags.TAG_VALOR_PARAMETRO + ">");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</" + DefFmtTags.TAG_VINCULO_ELEMENTO + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}