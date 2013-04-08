package ws.transferencias;

/**
 * Interfaz del Servicio Web para operaciones del módulo de transferencias
 *
 * @author Iecisa
 * @version $Revision$
 */
public interface WSTransferencias {


	/**
	 * Carga de los datos descriptivos y documentos electrónicos, de un
	 * expediente electrónico, perteneciente a un procedimiento y producido por
	 * un productor vigente, en una relación de entrega automatizada en estado
	 * validada y en el cuadro de clasificación. En el caso de que no exista la
	 * serie, previsión, detalle y relación de entrega, se crean de forma
	 * dinámica.
	 *
	 * @param autenticacionInfo
	 *            Objeto que contiene los datos referentes a la autenticación:
	 * @param tramitadorInfo Objeto que contiene los datos referentes al sistema tramitador
	 * @param productorInfo Objeto que contiene los datos referentes al productor del expediente
	 * @param expedienteInfo Objeto que contiene la información del expediente y sus documentos
	 * @return True si el con
	 */
	public boolean transferirExpedienteElectronicoConDocumentos(
			String codigoTramitador, String nombreTramitador,
			int anioExpediente, String codigoProcedimiento,
			byte[] contenidoXml, int verificarUnicidad,
			String usuario, String password,String entidad) throws Exception;
}
