package ieci.tecdoc.sgm.registro.form.remote;

import ieci.tecdoc.sgm.registro.form.remote.vo.DireccionTerceroVO;
import ieci.tecdoc.sgm.registro.form.remote.vo.RetornoVO;
import ieci.tecdoc.sgm.registro.form.remote.vo.TerceroVO;
import ieci.tecdoc.sgm.registro.terceros.connector.ServicioRegistroTelematicoTercerosConnector;
import ieci.tecdoc.sgm.registro.terceros.connector.config.ConnectorConfiguration;
import ieci.tecdoc.sgm.registro.terceros.connector.factory.ServicioRegistroTelematicoTercerosConnectorFactory;

import org.apache.commons.lang.StringUtils;

public class TercerosRemoteFunctions {

	/**
	 * Validación del tercero contra el Sistema de Terceros externo configurado
	 * para el Registro Telemático, siendo ésta que el identificador del tercero
	 * exista en el sistema externo.
	 *
	 * @param parametros
	 *            Tercero a validar, que también incluye la entidad y locale
	 *            para los mensajes.
	 * @return Retorno de la validación, indicando si no hay error, y si hay
	 *         error, incluye el mensaje de error.
	 */
	public RetornoVO validateTercero(TerceroVO parametros) {

		RetornoVO retorno = new RetornoVO();
		retorno.setError(false);

		if ((parametros != null)
				&& (StringUtils.isNotBlank(parametros.getIdentificador()))) {

			try {
				ServicioRegistroTelematicoTercerosConnector servicioTerceros = ServicioRegistroTelematicoTercerosConnectorFactory
						.getServicioRegistroTelematicoTercerosConnector(new ConnectorConfiguration(
								parametros.getEntidad()));
				if (servicioTerceros != null) {

					// Buscar por identificador
					ieci.tecdoc.sgm.registro.terceros.connector.vo.TerceroVO tercero = servicioTerceros
							.buscarTerceroPorEntidad(parametros.getEntidad(),
									parametros.getIdentificador());
					if (tercero == null) {
						retorno.setError(true);
						retorno
								.setMessage("El Documento de Identidad '"
										+ parametros.getIdentificador()
										+ "' no est\u00e1 registrado en el Sistema de Terceros.");
					}
				}
			} catch (Exception e) {
				// retorno.setError(true);
				// retorno.setMessage(e.getLocalizedMessage());
			}
		}

		return retorno;
	}

	/**
	 * Ejemplo de llamada para la validación de la dirección del tercero.
	 *
	 * @param parametros
	 *            Dirección del tercero a validar, que también incluye la
	 *            entidad y locale para los mensajes.
	 * @return Retorno de la validación, indicando si no hay error, y si hay
	 *         error, incluye el mensaje de error.
	 */
	public RetornoVO validateDireccionTercero(DireccionTerceroVO parametros) {

		RetornoVO retorno = new RetornoVO();
		retorno.setError(false);

		if ((parametros != null)) {

		}

		return retorno;
	}

}
