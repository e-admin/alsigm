package transferencias;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;

import se.usuarios.AppUser;
import transferencias.electronicas.udoc.ContenidoUDocXML;
import transferencias.vos.TransferenciaElectronicaInfo;
import ws.transferencias.vo.TransferenciaInfo;

import common.Globals;
import common.exceptions.TransferenciaElectronicaException;
import common.util.XmlFacade;


public class TransferenciaElectronicaUtils {

	/** Logger de la clase */
	private final static Logger logger = Logger
			.getLogger(TransferenciaElectronicaUtils.class);

	/**
	 * @param autenticacionInfo
	 * @param transferenciaInfo
	 * @return
	 */
	public static TransferenciaElectronicaInfo getTransferenciaElectronicaInfo(AppUser appUser, TransferenciaInfo transferenciaInfo) throws TransferenciaElectronicaException {

		if(logger.isDebugEnabled()){
			logger.debug("Obtener Información de la transferencia electrónica");
		}

		TransferenciaElectronicaInfo vo = new TransferenciaElectronicaInfo();

		try {
			vo.setContenidoUDocXML(getContenidoUDocXML(transferenciaInfo.getContenidoXml()));
			vo.setAnio(transferenciaInfo.getAnioExpediente());
			vo.setCodigoProcedimiento(transferenciaInfo.getCodigoProcedimiento());
			vo.setSistemaTramitador(transferenciaInfo.getSistemaTramitador());
			vo.setAppUser(appUser);
			vo.setVerificarUnicidad(transferenciaInfo.getVerificarUnicidad());

		} catch (Exception e) {
			logger.error(e);
			throw new TransferenciaElectronicaException(TransferenciasElectronicasConstants.ERROR_PROCESAR_CONTENIDO_XML,e);
		}

		return vo;

	}

	/**
	 * Obtiene el contenido del fichero xml de contenido de la transferencia
	 * electronica.
	 *
	 * @return
	 */
	public static ContenidoUDocXML getContenidoUDocXML(byte[] contenidoXml)
			throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("Inicio de procesamiento de fichero XML");
		}

		ContenidoUDocXML contenidoVO = new ContenidoUDocXML();
		URL digesterRulesFile = TransferenciaElectronicaUtils.class
				.getResource(Globals.RULES_TRANSFERENCIA_ELECTRONICA);
		Digester digester = DigesterLoader.createDigester(digesterRulesFile);

		contenidoVO = (ContenidoUDocXML) digester
				.parse(new ByteArrayInputStream(contenidoXml));
		return contenidoVO;
	}

	public static boolean validarXmlTransferencia(String xml, InputStream inputStreamXsd) throws IllegalArgumentException{

		InputSource isXml = new InputSource(new StringReader(xml.trim()));
		InputSource isXsd = new InputSource(inputStreamXsd);

		new XmlFacade(isXml, isXsd);

		return true;
	}

}
