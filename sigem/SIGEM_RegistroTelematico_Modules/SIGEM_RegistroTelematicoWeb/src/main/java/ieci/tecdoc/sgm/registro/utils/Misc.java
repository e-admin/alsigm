package ieci.tecdoc.sgm.registro.utils;

import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.autenticacion.util.TipoSolicitante;
import ieci.tecdoc.sgm.autenticacion.util.utilities.Validador;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;
import ieci.tecdoc.sgm.base.xml.core.XmlElements;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.user.web.SesionUserHelper;
import ieci.tecdoc.sgm.registro.util.Definiciones;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import xtom.parser.Element;
import xtom.parser.Parser;
import xtom.parser.XMLTree;

public class Misc {

	public static Entidad obtenerEntidad(HttpServletRequest request){
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(SesionUserHelper.obtenerIdentificadorEntidad(request));
		return oEntidad;
	}

	public static String obtenerIdioma(String xml, String codigo) {
		if (xml == null || "".equals(xml))
			return null;

		try {
			Parser parser = new Parser(xml);
			XMLTree tree = parser.parse();
			Element root = tree.getRootElement();
			Element[] elements = root.getChildren();
			for (int i=0; i<elements.length; i++) {
				if (elements[i].getAttribute("id").getValue().equals(codigo))
					return elements[i].getValue();
			}
			return null;
		} catch(Exception e) {
			return null;
		}
	}

	public static List processDocumentsWithVirus(XmlDocument xmlDoc) {

		List documentsWithVirus = new ArrayList();

	   	XmlElement rootElement = xmlDoc.getRootElement();
    	XmlElement documents = rootElement.getDescendantElement(Definiciones.XPATH_DOCUMENTS);

    	XmlElements xmlElements = documents.getChildElements();
		for(int i = 1; i <= xmlElements.getCount(); i++) {

			XmlElement document = xmlElements.getItem(i);
			XmlElement antivirus = document.getChildElement(Definiciones.ANTIVIRUS);
			if (antivirus != null) {

				String value = antivirus.getValue();
				if (value.equals(Definiciones.ANTIVIRUS_ERROR)) {

					documentsWithVirus.add(document.getChildElement(Definiciones.NAME).getValue());
				}
			}
		}

		return documentsWithVirus;
	}

	public static void addInfoSolicitante(XmlTextBuilder bdr, Solicitante solicitante) {

        bdr.addOpeningTag(Definiciones.SENDER);

        // Persona fisica o juridica
        if (solicitante.getInQuality().equals(String.valueOf(TipoSolicitante.INDIVIDUAL))) {

        	// Persona fisica (individual)
        	// Nombre completo
	        bdr.addSimpleElement(Definiciones.SENDER_NAME, solicitante.getName(), true);
	        // Nombre y apellidos segregados
	        if (StringUtils.isNotBlank(solicitante.getSurName())) {
	        	bdr.addOpeningTag(Definiciones.SENDER_NAME_SURNAMES);
	        	bdr.addSimpleElement(Definiciones.SENDER_NAME, solicitante.getFirstName(), true);
	        	bdr.addSimpleElement(Definiciones.SENDER_SURNAME, solicitante.getSurName(), true);
	        	bdr.addSimpleElement(Definiciones.SENDER_SURNAME2, solicitante.getSurName2(), true);
	        	bdr.addClosingTag(Definiciones.SENDER_NAME_SURNAMES);
	        }
	        bdr.addOpeningTag(Definiciones.ID);
	        bdr.addSimpleElement(Definiciones.SENDER_ID_TYPE, ""+Validador.validateDocumentType(solicitante.getId()));
	        bdr.addSimpleElement(Definiciones.SENDER_ID, solicitante.getId());
	        bdr.addClosingTag(Definiciones.ID);
        }
        else {
        	// Persona juridica (representante legal)
	        bdr.addSimpleElement(Definiciones.SENDER_NAME, solicitante.getSocialName(), true);
	        bdr.addOpeningTag(Definiciones.ID);
	        bdr.addSimpleElement(Definiciones.SENDER_ID_TYPE, ""+Validador.validateDocumentType(solicitante.getCIF()));
	        bdr.addSimpleElement(Definiciones.SENDER_ID, solicitante.getCIF());
	        bdr.addClosingTag(Definiciones.ID);
        }

        bdr.addSimpleElement(Definiciones.SENDER_EMAIL, solicitante.getEmail(), true);
        bdr.addClosingTag(Definiciones.SENDER);

        // Representante legal de la persona juridica
        if (solicitante.getInQuality().equals(String.valueOf(TipoSolicitante.LEGAL_REPRESENTATIVE))) {

        	bdr.addOpeningTag(Definiciones.LEGAL_REPRESENTATIVE);
        	// Nombre completo
	        bdr.addSimpleElement(Definiciones.SENDER_NAME, solicitante.getName(), true);
	        // Nombre y apellidos segregados
	        if (StringUtils.isNotBlank(solicitante.getSurName())) {
	        	bdr.addOpeningTag(Definiciones.SENDER_NAME_SURNAMES);
	        	bdr.addSimpleElement(Definiciones.SENDER_NAME, solicitante.getFirstName(), true);
	        	bdr.addSimpleElement(Definiciones.SENDER_SURNAME, solicitante.getSurName(), true);
	        	bdr.addSimpleElement(Definiciones.SENDER_SURNAME2, solicitante.getSurName2(), true);
	        	bdr.addClosingTag(Definiciones.SENDER_NAME_SURNAMES);
	        }
	        bdr.addOpeningTag(Definiciones.ID);
	        bdr.addSimpleElement(Definiciones.SENDER_ID_TYPE, ""+Validador.validateDocumentType(solicitante.getId()));
	        bdr.addSimpleElement(Definiciones.SENDER_ID, solicitante.getId());
	        bdr.addClosingTag(Definiciones.ID);
	        bdr.addClosingTag(Definiciones.LEGAL_REPRESENTATIVE);
        }
	}
	
	/**
	 * Devuelve la pila de la traza de la excepción como un string
	 * @param aThrowable Excepción
	 * @return	La pila de la traza de la excepción como un string
	 */
	 public static String getStackTrace(Throwable aThrowable) {
		    final Writer result = new StringWriter();
		    final PrintWriter printWriter = new PrintWriter(result);
		    aThrowable.printStackTrace(printWriter);
		    return result.toString();
		  }
}
