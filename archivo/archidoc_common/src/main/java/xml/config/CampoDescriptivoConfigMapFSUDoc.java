package xml.config;

import org.apache.commons.lang.StringUtils;

import common.Constants;

/**
 * Clase que almacena la información de un campo de mapeo de fracción de serie a unidad documental
 */
public class CampoDescriptivoConfigMapFSUDoc extends CampoDescriptivoConfig
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  Valor destino
	 */
	private String valorDestino = null;

    /**
     * Constructor.
     */
    public CampoDescriptivoConfigMapFSUDoc()
    {
    	super();
    }

    /**
	 * @return el valorOrigen
	 */
	public String getValorOrigen() {
		return valor;
	}

	/**
	 * @return el valorDestino
	 */
	public String getValorDestino() {
		return valorDestino;
	}

	/**
	 * @param valor el valor origen a establecer
	 */
	public void setValorOrigen(String valorOrigen) {
		this.valor = valorOrigen;
	}

	/**
	 * @param valor el valor destino a establecer
	 */
	public void setValorDestino(String valorDestino) {
		this.valorDestino = valorDestino;
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * @param indent Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public String toXML(int indent)
	{
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat("  ", indent);

		xml.append(tabs + "<campo");
	    xml.append(nombre != null ? " descripcion=\"" + nombre +"\"" : "");
	    xml.append(valor != null ? " idCampoOrigen=\"" + valor +"\"" : "");
	    xml.append(valorDestino != null ? " idCampoDestino=\"" + valorDestino +"\"" : "");
	    xml.append(tipo != null ? " tipo=\"" + tipo +"\"" : "");
	   	xml.append("/>");

		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
