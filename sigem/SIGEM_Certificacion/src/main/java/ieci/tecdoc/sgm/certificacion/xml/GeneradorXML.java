package ieci.tecdoc.sgm.certificacion.xml;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.util.Defs;
import ieci.tecdoc.sgm.certificacion.xml.bean.Campo;
import ieci.tecdoc.sgm.certificacion.xml.bean.DatosComunes;
import ieci.tecdoc.sgm.certificacion.xml.bean.DatosEspecificos;
import ieci.tecdoc.sgm.certificacion.xml.builder.XmlTextBuilder;

/**
 * Clase que genera el XML de datos para la creación de la certificación en PDF
 * @author José Antonio Nogales
 */
public class GeneradorXML {
		private static final Logger logger = Logger.getLogger(GeneradorXML.class);
	
		/**
		 * Método que genera el XML 
		 * @param datosComunes Datos comunes del XML
		 * @param datosEspecificos Datos específicos del XML (pagos, ...)
		 * @param header true para incluir cabecera XML, false para no incluirla
		 * @return XML con los datos comunes y específicos
		 * @throws CertificacionException En caso de producirse algún error
		 */
		public static String GenerarXML (DatosComunes datosComunes, DatosEspecificos datosEspecificos, boolean header) throws CertificacionException {
			try {	
				XmlTextBuilder bdr = new XmlTextBuilder();
				if (header)
						bdr.setStandardHeader();

				bdr.addOpeningTag(Defs.TAG_XML_CERTIFICACION);
				bdr.addOpeningTag(Defs.TAG_XML_DATOS_COMUNES);
				
				if (datosComunes != null){
					for(int i=0; i<datosComunes.getSize(); i++){
						Campo campo = datosComunes.getDatoComun(i);
						addElement(bdr, campo);
					}
				}
				
				bdr.addClosingTag(Defs.TAG_XML_DATOS_COMUNES);
				bdr.addOpeningTag(Defs.TAG_XML_DATOS_ESPECIFICOS);
				
				if (datosEspecificos != null){
					for(int i=0; i<datosEspecificos.getSize(); i++){
						Campo campo = datosEspecificos.getDatoEspecifico(i);
						addElement(bdr, campo);
					}
				}
				
				bdr.addClosingTag(Defs.TAG_XML_DATOS_ESPECIFICOS);
				bdr.addClosingTag(Defs.TAG_XML_CERTIFICACION);
		      
				return bdr.getText();
			}catch(Exception e) {
				logger.error("Se ha producido un error en la generación del XML", e);
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_GENERACION_XML,
					e.getMessage(),
					e.getCause());
			}
		}
		
		/**
		 * Método recursivo para añadir valores al XML a partir de un objeto Campo
		 * @param bdr Generador de XML
		 * @param campo Campo a añadir al XML
		 * @throws Exception En caso de producirse algún error
		 */
		private static void addElement(XmlTextBuilder bdr, Campo campo) throws Exception {
				ArrayList hijos = campo.getHijosCampo();
				if (hijos == null || hijos.size() <= 0){
					bdr.addSimpleElement(campo.getEtiquetaXML(), campo.getValor());
				}else{
					bdr.addOpeningTag(campo.getEtiquetaXML());
					for(int i=0; i<hijos.size(); i++){
						addElement(bdr, (Campo)hijos.get(i));
					}
					bdr.addClosingTag(campo.getEtiquetaXML());
				}
		}
}
