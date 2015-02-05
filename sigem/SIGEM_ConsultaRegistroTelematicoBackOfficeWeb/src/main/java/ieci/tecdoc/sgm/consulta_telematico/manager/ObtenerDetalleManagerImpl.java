package ieci.tecdoc.sgm.consulta_telematico.manager;

import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.telematico.Definiciones;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;

public class ObtenerDetalleManagerImpl implements ObtenerDetalleManager{

	public XmlDocument getDetalleRegistro(String numRegistro, Entidad idEntidad) throws Exception{

	   		ServicioRegistroTelematico oServicioRT = LocalizadorServicios.getServicioRegistroTelematico();

	    	// Solicitud del registro
			// Obtener la solicitud no firmada ya que siempre existe para todos los trámites
			// y la solicitud firmada sólo existe en los trámites que requieren firma
	    	// byte[] solicitud = oServicioRT.obtenerPeticionRegistro(numRegistro, idEntidad);
			byte[] solicitud = oServicioRT.obtenerDocumento(numRegistro, Definiciones.REGISTRY_REQUEST_NOTSIGNED_CODE, idEntidad);
	    	
			// Obtener los datos del XML de la solicitud
	    	XmlDocument xmlDocReg = new XmlDocument();
			xmlDocReg.createFromStringText(Goodies.fromUTF8ToStr(solicitud));
			return xmlDocReg;
		
	}

}
