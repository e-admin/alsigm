package ws.transferencias;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import transferencias.TransferenciaElectronicaUtils;
import transferencias.TransferenciasElectronicasConstants;
import transferencias.vos.TransferenciaElectronicaInfo;
import ws.WSException;
import ws.transferencias.vo.AutenticacionInfo;
import ws.transferencias.vo.TramitadorInfo;
import ws.transferencias.vo.TransferenciaInfo;

import common.bi.GestionTransferenciasElectronicasBI;
import common.exceptions.TransferenciaElectronicaException;
import common.webservices.WSBase;

public class WSTransferenciasImpl extends WSBase implements WSTransferencias {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(WSTransferenciasImpl.class);

	public WSTransferenciasImpl() {
		super();
	}

	private boolean transferirExpedienteElectronicoConDocumentosInterno(
			AutenticacionInfo autenticacionInfo,
			TransferenciaInfo transferenciaInfo) throws Exception {

		if (logger.isInfoEnabled()) {
			logger.info("Inicio de la operación de transferirExpedienteElectronicoConDocumentos");
		}

		if (autenticacionInfo != null) {
			setUserName(autenticacionInfo.getUsuario());
			setPassword(autenticacionInfo.getPassword());
			setEntity(autenticacionInfo.getCodigoEntidad());

			try {
				authenticate();

			} catch (LoginException le) {
				try {
					setPassword(getPasswordByBase64(autenticacionInfo
							.getPassword()));
					authenticate();
				} catch (Exception e) {
					logger.error("Error al autenticar el usuario", e);
					throw new WSException(
							TransferenciasElectronicasConstants.ERROR_AUTENTICACION_USUARIO,
							e);
				}
			}

		} else {
			logger.error("No se ha especificado la información del usuario");
			throw new WSException(
					TransferenciasElectronicasConstants.ERROR_AUTENTICACION_USUARIO,
					null);
		}

		TransferenciaElectronicaInfo transferenciaElectronicaInfo = null;

		try {
			GestionTransferenciasElectronicasBI bi = getServiceRepository()
					.lookupGestionTransferenciasElectronicasBI();

			transferenciaElectronicaInfo = TransferenciaElectronicaUtils
					.getTransferenciaElectronicaInfo(getAppUser(),
							transferenciaInfo);

			boolean retorno = bi
					.transferirExpedienteElectronicoConDocumentos(transferenciaElectronicaInfo);

			if (logger.isInfoEnabled()) {
				logger.info("Fin de la operación de transferirExpedienteElectronicoConDocumentos");
			}

			return retorno;
		} catch (TransferenciaElectronicaException e) {
			throw new WSException(e);
		} catch (Exception e) {
			throw new WSException(e);
		} finally {
			// liberar el objeto transfernecia electronica
			transferenciaElectronicaInfo = null;
			System.gc();
			finalizarSesion();
		}
	}


	public boolean transferirExpedienteElectronicoConDocumentos(
			String codigoTramitador, String nombreTramitador,
			int anioExpediente, String codigoProcedimiento,
			byte[] contenidoXml, int verificarUnicidad, String usuario,
			String password, String entidad) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Codigo Tramitador:" + codigoTramitador);
			logger.debug("Nombre Tramitador:" + nombreTramitador);
			logger.debug("Año Expediente" + anioExpediente);
			logger.debug("Código Procedimiento" + codigoProcedimiento);
			logger.debug("Usuario:" + usuario);
			logger.debug("Entidad:" + entidad);
		}

		AutenticacionInfo autenticacionInfo = new AutenticacionInfo(entidad,
				usuario, password);
		TramitadorInfo tramitadorInfo = new TramitadorInfo(codigoTramitador,
				nombreTramitador);

		TransferenciaInfo transferenciaInfo = new TransferenciaInfo(
				anioExpediente, codigoProcedimiento, tramitadorInfo,
				contenidoXml, verificarUnicidad);

		return transferirExpedienteElectronicoConDocumentosInterno(
				autenticacionInfo, transferenciaInfo);
	}

}
