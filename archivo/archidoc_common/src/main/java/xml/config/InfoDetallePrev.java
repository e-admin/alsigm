package xml.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import xml.XMLObject;

import common.Constants;

/**
 * Clase que almacena la información de una búsqueda.
 */
public class InfoDetallePrev extends XMLObject
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private int totalUI;

	private String udocsElectronicas;

    /**
     * Map de restricciones
     */
    private List volumenFormato = new ArrayList();


    /**
     * Constructor.
     */
    public InfoDetallePrev()
    {
    	super();
    }

    public String getTotalUI() {
		return new Integer(totalUI).toString();
	}

	/**
     * Permite añadir un campo de formato y volumen
     * @param infoVolumenFormato InfoVolumenFormato a añadir
     */
    public void addVolumenFormato(InfoVolumenFormato infoVolumenFormato) {
		if (infoVolumenFormato != null) {
			this.volumenFormato.add(infoVolumenFormato);
			addNumeroUnidades(infoVolumenFormato.getNumUI());
		}
    }

    /**
     * Suma el número de Unidades al total de unidades
     * @param numUnidades
     */
    private void addNumeroUnidades(String numeroUnidades) {
    	int numUnidades = 0;
    	try {
    		numUnidades = Integer.parseInt(numeroUnidades);
    	}catch(Exception e) {

    	}
    	this.totalUI += numUnidades;
    }


	/**
	 * @return el volumenFormato
	 */
	public List getVolumenFormato() {
		return volumenFormato;
	}



	/**
	 * @return el udocsElectronicas
	 */
	public String getUdocsElectronicas() {
		return udocsElectronicas;
	}

	/**
	 * @param udocsElectronicas el udocsElectronicas a establecer
	 */
	public void setUdocsElectronicas(String udocsElectronicas) {
		this.udocsElectronicas = udocsElectronicas;
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

		xml.append(tabs + "<info>");
	    xml.append(Constants.NEWLINE);

	    if(udocsElectronicas!= null && !Constants.STRING_CERO.equals(udocsElectronicas)) {
		    xml.append("<udocsElectronicas>")
		   	   .append(udocsElectronicas)
		   	   .append("</udocsElectronicas>");
	    }

	    /* Campos de entrada */
	    if ((volumenFormato!=null)&&(!volumenFormato.isEmpty())){
	    	xml.append(tabs + "  <volumen_formato>");
	    	xml.append(Constants.NEWLINE);
	    	Iterator it = volumenFormato.iterator();
	    	while (it.hasNext()){
	    		InfoVolumenFormato infoVolumenFormato = (InfoVolumenFormato) it.next();
	    		xml.append(tabs+infoVolumenFormato.toXML(4));
	    		xml.append(Constants.NEWLINE);
	    	}
	    	xml.append(tabs + "  </volumen_formato>");
	    	xml.append(Constants.NEWLINE);
	    }

	    xml.append("</info>");
	    xml.append(Constants.NEWLINE);

		return xml.toString();
	}
}
