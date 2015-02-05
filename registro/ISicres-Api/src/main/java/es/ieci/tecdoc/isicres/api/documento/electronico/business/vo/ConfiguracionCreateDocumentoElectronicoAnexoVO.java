package es.ieci.tecdoc.isicres.api.documento.electronico.business.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ConfiguracionCreateDocumentoElectronicoAnexoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4095090476913194411L;

	/**
	 * nombre del clasificador sobre el que se insertara el documento electronico, vendría a ser el nombre de la carpeta 
	 */
	protected String clasificador;
	
	/**
	 * datos adicionales para dar via de ampliacion
	 */
	protected Map<String, Object> datosAdicionales;
	
	public ConfiguracionCreateDocumentoElectronicoAnexoVO(){
		clasificador="Documento Electrónico";
		datosAdicionales= new HashMap<String, Object>();
	}
	public Map<String, Object> getDatosAdicionales() {
		return datosAdicionales;
	}
	public void setDatosAdicionales(Map<String, Object> datosAdicionales) {
		this.datosAdicionales = datosAdicionales;
	}
	public String getClasificador() {
		return clasificador;
	}
	public void setClasificador(String clasificador) {
		this.clasificador = clasificador;
	}

}
