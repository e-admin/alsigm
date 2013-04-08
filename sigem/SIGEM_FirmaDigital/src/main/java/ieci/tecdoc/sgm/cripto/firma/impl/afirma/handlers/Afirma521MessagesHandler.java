package ieci.tecdoc.sgm.cripto.firma.impl.afirma.handlers;

import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.Constantes;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.SignManagerAFirmaImpl;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.Util;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.config.AFirmaConfiguration;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.utils.CertificateUtils;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.InformacionAuxiliar;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.misc.Valida;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.Firmante;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.Nombre;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.ResultadoVerificarFirma;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.xml.verificarfirma.VerificarFirma;

import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/** 
 * Implementación del gestor de mensajes contra los servicios de afirma v5.2.1 
 *
 */
public class Afirma521MessagesHandler extends Afirma501MessagesHandler {

    /**
     * Constructor
     */
    public Afirma521MessagesHandler() {
    	super();
    }
    
	public String createRequestFirmarUsuario(SignManagerAFirmaImpl af, String firmaUsuario, String certFirmante, String hash) throws FirmaDigitalException{
		AFirmaConfiguration config = af.loadConfig();
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement(MENSAJE_ENTRADA);
		for (int i = 0; i < MENSAJE_ENTRADA_FIRMAR_ATTRS.length; i++) {
			root.addAttribute(MENSAJE_ENTRADA_FIRMAR_ATTRS[i][0],
					MENSAJE_ENTRADA_FIRMAR_ATTRS[i][1]);

		}
		
		Element elem = root.addElement(PETICION);
		elem.setText(FIRMAUSUARIO);

		elem = root.addElement(VERSION_MSG);
		elem.setText("1.0");

		elem = root.addElement(PARAMETROS);

		elem.addElement(ID_APLICACION).addText(config.getIdAplicacion());
		elem.addElement(FIRMA_ELECTRONICA).addCDATA(firmaUsuario);
		elem.addElement(CERT_FIRMANTE).addCDATA(certFirmante);

		elem.addElement(FORMATO_FIRMA).addText(config.getFormatoFirma());
		// elem.addElement(DOCUMENTO).addCDATA("");
		// elem.addElement(NOMBRE_DOCUMENTO).addText("Justificante"); // XXX mal
		// elem.addElement(TIPO_DOCUMENTO).addText("PDF"); // XXX mal

		// elem.addElement(HASH).addCDATA(hash); // hash de los datos cuya firma
		// se va a validar, codificado en Base 64, puede estar vacio

		elem.addElement(ALGORITMO_HASH).addText(config.getAlgoritmoHash()); // si se
																		// envia
																		// el
																		// hash,
																		// formato
																		// del
																		// mismo

		elem.addElement(CUSTODIAR_DOCUMENTO).addText("false");

		return document.asXML();
	}

	public void checkResponseVerificar(VerificarFirma vf, String s) throws Exception {
		s = Util.cambiarCabeceraXml(s);
		java.io.InputStream is = new java.io.ByteArrayInputStream(new String(s
				.getBytes(), "utf-8").getBytes());
		SAXReader reader = new SAXReader();

		Document document = reader.read(is);

		// Comprobar si hay error
		Node node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA + "/" + ESTADO);
		if (node == null) { 
			throwAFirmaException(document);
		}

		ResultadoVerificarFirma resultado = new ResultadoVerificarFirma();
		String desc = null;

		String estado = node.getStringValue();
		if (Constantes.TRUE.equalsIgnoreCase(estado)) {
			
			// la validacion de la firma es verdadera
			resultado.setValida(new Valida("0")); // true

			node = document.selectSingleNode("/" + MENSAJE_SALIDA + "/"
					+ RESPUESTA + "/" + rESPUESTA + "/" + DESCRIPCION + "/"
					+ VALIDACION_FIRMA_ELECTRONICA + "/"
					+ INFORMACION_ADICIONAL + "/" + FIRMANTE + "/"
					+ CERTIFICADO);

			if (node != null) {

				Firmante firmante = new Firmante();
				firmante.setNombre(new Nombre(CertificateUtils.getCertificateSubjectName(node.getStringValue())));

				ArrayList firmantes = new ArrayList();
				firmantes.add(firmante);

				resultado.setFirmantes(firmantes);
				desc = "Firma digital correcta";
			} else {
				desc="No se ha encontrado ningún firmante";
			}
		    
		} else {
			
			// la validacion de la firma es falsa
			resultado.setValida(new Valida("-1"));
			// aqui se puede obtener mas informacion
			desc = document.selectSingleNode("/" + MENSAJE_SALIDA + "/" + RESPUESTA + "/" + rESPUESTA
					+ "/" + DESCRIPCION).getStringValue();
			// throw new ExcepcionAFirma(desc, "?", "?");
		}

		resultado.setInformacionAuxiliar(new InformacionAuxiliar(desc));

		vf.setResultado(resultado);

	}

}
