package ieci.tecdoc.sgm.autenticacion.utils;

import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.autenticacion.util.utilities.Validador;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

public class DatosSolicitud {
	public static String prepararSolicitud(Solicitante solicitante){
		XmlTextBuilder bdr;
 
		bdr = new XmlTextBuilder();
		
        bdr.setStandardHeader();
        bdr.addOpeningTag(TAG_DATOS_REGISTRO);
        bdr.addOpeningTag(TAG_REMITENTE); 
        bdr.addSimpleElement(TAG_NOMBRE, solicitante.getName());
        //bdr.addSimpleElement(TAG_APELLIDOS, solicitante.getSurName());
        bdr.addOpeningTag(TAG_DOCUMENTO_IDENTIFICACION);
        bdr.addSimpleElement(TAG_DI_TIPO, ""+Validador.validateDocumentType(solicitante.getCIF()));
        bdr.addSimpleElement(TAG_DI_NUMERO, solicitante.getCIF());
        bdr.addClosingTag(TAG_DOCUMENTO_IDENTIFICACION);
        bdr.addSimpleElement(TAG_EMAIL, solicitante.getEmail());
        bdr.addClosingTag(TAG_REMITENTE);
        bdr.addClosingTag(TAG_DATOS_REGISTRO);
	     
        return bdr.getText();
	}

	private final static String TAG_DATOS_REGISTRO = "Datos_Registro";
	private final static String TAG_REMITENTE = "Remitente";
	private final static String TAG_NOMBRE = "Nombre";
	//private final static String TAG_APELLIDOS = "Apellidos";
	private final static String TAG_DOCUMENTO_IDENTIFICACION = "Documento_Identificacion";
	private final static String TAG_DI_TIPO = "Tipo";
	private final static String TAG_DI_NUMERO = "Numero";
	private final static String TAG_EMAIL = "Correo_Electronico";
}
