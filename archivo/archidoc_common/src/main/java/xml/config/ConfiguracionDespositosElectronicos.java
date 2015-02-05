package xml.config;

import org.apache.commons.lang.StringUtils;

import common.Constants;

public class ConfiguracionDespositosElectronicos extends Sistema
{

	private static final long serialVersionUID = 317352319203304855L;
	private String claseConectorServicioWeb=null;
	
	
	public ConfiguracionDespositosElectronicos(){
		
	}

	public String getClaseConectorServicioWeb() {
		return claseConectorServicioWeb;
	}

	public void setClaseConectorServicioWeb(String claseConectorServicioWeb) {
		this.claseConectorServicioWeb = claseConectorServicioWeb;
	}

	public String toXML(int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<Configuracion_Depositos>");
		xml.append(Constants.NEWLINE);
		
		
		xml.append(tabs + "<Depositos_Electronicos>");
		xml.append(Constants.NEWLINE);
		
		// Clase_Conector_ServicioWeb
		xml.append(tabs + "  <Clase_Conector_ServicioWeb>");
		xml.append(claseConectorServicioWeb != null ? claseConectorServicioWeb : "");
		xml.append("</Clase_Conector_ServicioWeb>");
		xml.append(Constants.NEWLINE);
		
		xml.append(tabs + "</Depositos_Electronicos>");
		xml.append(Constants.NEWLINE);
		
		// Tag de cierre
		xml.append(tabs + "</Configuracion_Depositos>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	
	
	
}
