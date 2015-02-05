package xml.config;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;


/**
 * Clase que almacena la información de una lista de productores.
 */
public class ListaProductores extends XMLObject
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String productor = null;
    private String tipo = null;
    private String id = null;


    /**
     * Constructor.
     */
    public ListaProductores()
    {
    }


	/**
	 * @return Returns the productor.
	 */
	public String getProductor()
	{
		return productor;
	}
	/**
	 * @param productor The productor to set.
	 */
	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	/**
	 * @return Returns the tipo.
	 */
	public String getTipo()
	{
		return tipo;
	}
	/**
	 * @param tipo The tipo to set.
	 */
	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}
	/**
	 * @return Returns the id.
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setIdLista(String id)
	{
		this.id = id;
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

		xml.append(tabs + "<lista productor=\"");
	    xml.append(productor != null ? productor : "");
	    xml.append("\" tipoproductor=\"");
	    xml.append(tipo != null ? tipo : "");
	    xml.append("\">");
	    xml.append(id != null ? id : "");
	    xml.append("</lista>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

}
