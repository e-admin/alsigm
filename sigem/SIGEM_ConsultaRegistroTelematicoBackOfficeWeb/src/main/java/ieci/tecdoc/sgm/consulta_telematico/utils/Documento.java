package ieci.tecdoc.sgm.consulta_telematico.utils;

import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento;

public class Documento extends RegistroDocumento {
	
	public Documento() {
		extension = null;
		name = null;
	}
	
    /**
     * Devuelve el nombre del documento.
     * @return String Nombre del documento.
     */
	public String getName() {
		return name;
	}

    /**
     * Establece el nombre del documento.
     * @param name Nombre del documento.
     */   
	public void setName(String name) {
		this.name = name;
	}
	
    /**
     * Devuelve la extensión del documento.
     * @return String Extensión del documento.
     */
	public String getExtension() {
		return extension;
	}

    /**
     * Establece la extensión del documento.
     * @param extension Extensión del documento.
     */  
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	protected String name;
	protected String extension;

}
