/**
 * 
 */
package ieci.tecdoc.sgm.ws.csv.connector.server;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoCSV;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoCSVImpl;
import ieci.tecdoc.sgm.registro.util.database.RegistroDocumentoCSVDatos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class AplicacionExternaCSVConnectorWS {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(AplicacionExternaCSVConnectorWS.class);

	/*
	 * 
	 * Es necesario conocer la entidad para conocer el Datasource que hay que
	 * utilizar para la conexión (MultiEntityDataSource)
	 */

	public boolean existeDocumento(java.lang.String csv, String entidad) throws java.rmi.RemoteException {
		if (logger.isDebugEnabled()) {
			logger.debug("existeDocumento(java.lang.String) - start. CSV: [{}]",csv);
		}
		

		RegistroDocumentoCSV documentoCSV = new RegistroDocumentoCSVImpl();
		documentoCSV.setCsv(csv);
				
		RegistroDocumentoCSVDatos documentoCSVDatos = new RegistroDocumentoCSVDatos(documentoCSV);

		boolean existe = false;
		try {
			documentoCSVDatos.load(csv, entidad);
			if (documentoCSVDatos.getGuid() != null) {
				existe = true;
			} else {
				existe = false;
			}
		} catch (RegistroExcepcion e) {
			logger.error("existeDocumento(java.lang.String)", e);

			existe = false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("existeDocumento(java.lang.String) - end");
		}
		return existe;
	}

	
	public byte[] getContenidoDocumento(java.lang.String csv, String entidad)
			throws java.rmi.RemoteException {
		if (logger.isDebugEnabled()) {
			logger.debug("getContenidoDocumento(java.lang.String) - start. CSV[{}]",csv);
		}

		byte[] content = null;
		
	
		try {
			RegistroDocumentoCSV documentoCSV = new RegistroDocumentoCSVImpl();
			documentoCSV.setCsv(csv);

			RegistroDocumentoCSVDatos documentoCSVDatos = new RegistroDocumentoCSVDatos(
					documentoCSV);
			documentoCSVDatos.load(csv, entidad);

			ServicioRepositorioDocumentosTramitacion oServicioRepoDocTram = LocalizadorServicios
					.getServicioRepositorioDocumentosTramitacion();
			Entidad entidadVO = new Entidad();
			entidadVO.setIdentificador(entidad);
			DocumentoInfo documentoInfo = oServicioRepoDocTram.retrieveDocument("",
					documentoCSVDatos.getGuid(), entidadVO);
			content = documentoInfo.getContent();
		} catch (SigemException e) {
			logger.error("getContenidoDocumento(java.lang.String)", e);
		} catch (RegistroExcepcion e) {
			logger.error("getContenidoDocumento(java.lang.String)", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getContenidoDocumento(java.lang.String) - end");
		}
		return content;
	}

}
