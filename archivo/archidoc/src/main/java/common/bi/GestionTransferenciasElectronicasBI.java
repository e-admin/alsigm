package common.bi;

import transferencias.vos.TransferenciaElectronicaInfo;

import common.exceptions.TransferenciaElectronicaException;


/**
 * Gestión de Transferencias Electrónicas
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface GestionTransferenciasElectronicasBI {

	public boolean transferirExpedienteElectronicoConDocumentos(TransferenciaElectronicaInfo transferenciaElectronicaInfo) throws TransferenciaElectronicaException;
}
