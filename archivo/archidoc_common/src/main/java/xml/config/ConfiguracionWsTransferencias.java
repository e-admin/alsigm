package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la configuración del ws de transferencias.
 */
public class ConfiguracionWsTransferencias extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Url del ws de transferencias. */
	private String url = null;


	/**
	 * Identificador de la fichas de Series
	 */
	private String idFichaSeries = "2";

	/**
	 * Identificador del nivel de series
	 */
	private String idNivelSerie = "00000000000000000000000000000008";

	/**
	 * Identificador del nivel Unidad Documental
	 */
	private String idNivelUdoc = "00000000000000000000000000000009";

	private String aniadirProductores = "S";

	private String transferenciasAutomatizadas = "S";

	/**
	 * Constructor.
	 */
	public ConfiguracionWsTransferencias()
	{
		super();
	}

	/**
	 * Permite obtener la url del ws de transferencias
	 * @return url del ws de transferencias
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Permite establecer la url del ws de transferencias
	 * @param url Url del ws de transferencias
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<Configuracion_Ws_Transferencias>");
		xml.append(Constants.NEWLINE);

		// Url
		xml.append(tabs + "  <Url>");
		xml.append(url != null ? url : "");
		xml.append("</Url>");
		xml.append(Constants.NEWLINE);

		// Tag de cierre
		xml.append(tabs + "</Configuracion_Ws_Transferencias>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	/**
	 * @param idFichaSeries el idFichaSeries a fijar
	 */
	public void setIdFichaSeries(String idFichaSeries) {
		this.idFichaSeries = idFichaSeries;
	}

	/**
	 * @return el idFichaSeries
	 */
	public String getIdFichaSeries() {
		return idFichaSeries;
	}

	/**
	 * @param idNivelSerie el idNivelSerie a fijar
	 */
	public void setIdNivelSerie(String idNivelSerie) {
		this.idNivelSerie = idNivelSerie;
	}

	/**
	 * @return el idNivelSerie
	 */
	public String getIdNivelSerie() {
		return idNivelSerie;
	}

	/**
	 * @param idNivelUdoc el idNivelUdoc a fijar
	 */
	public void setIdNivelUdoc(String idNivelUdoc) {
		this.idNivelUdoc = idNivelUdoc;
	}

	/**
	 * @return el idNivelUdoc
	 */
	public String getIdNivelUdoc() {
		return idNivelUdoc;
	}

	public boolean isAniadirProductoresInexistentes(){
		return Constants.TRUE_STRING.equals(aniadirProductores);
	}

	public boolean isGestionarAutomatizadas(){
		return Constants.TRUE_STRING.equals(transferenciasAutomatizadas);
	}

}
