package descripcion.model.eventos.interesados;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.util.ArrayUtils;
import common.util.StringUtils;

import descripcion.model.eventos.EventoFichaException;
import descripcion.model.eventos.IEventoFicha;
import descripcion.model.xml.card.Elemento;
import descripcion.model.xml.card.ElementoEtiquetaDato;
import descripcion.model.xml.card.ElementoTabla;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.Valor;
import fondos.FondosConstants;

public class EventoComprobarInteresadoPrincipal implements IEventoFicha {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(EventoComprobarInteresadoPrincipal.class);

	// /** Objeto properties con los ids necesarios */
	// private Properties props = new Properties();
	//
	// /** Path para acceder al fichero de propiedades */
	// private final String PATH_PROPERTIES_FILE =
	// "descripcion/model/eventos/interesados/EventoComprobarInteresadoPrincipal.properties";

	/**
	 * Constructor.
	 */
	public EventoComprobarInteresadoPrincipal() {
	}

	public ActionErrors executeEvent(int tipoEvento, Ficha ficha, Locale locale)
			throws EventoFichaException {
		if (logger.isInfoEnabled())
			logger.info("executeEvent: tipoEvento=[" + tipoEvento + "]");

		String idTablaInteresados = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getIdTablaInteresados();
		String idCampoPrincipal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getInteresadoPrincipal();
		String valorInteresadoPrincipal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getValorInteresadoPrincipal();

		if (logger.isDebugEnabled()) {
			logger.debug("Id Tabla de Interesados: " + idTablaInteresados);
			logger.debug("Id Campo Interesado Principal: " + idCampoPrincipal);
			logger.debug("Valor Interesado Principal: "
					+ valorInteresadoPrincipal);
		}

		boolean idsCorrectos = true;

		if (StringUtils.isBlank(idTablaInteresados)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/Id_Tabla_Interesados");
		}

		if (StringUtils.isBlank(idCampoPrincipal)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/Id_Interesado_Principal");
		}

		if (StringUtils.isBlank(valorInteresadoPrincipal)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/Valor_Interesado_Principal");
		}

		// boolean idsCorrectos = !StringUtils.isEmpty(idTablaInteresados) &&
		// !StringUtils.isEmpty(idCampoPrincipal) &&
		// !StringUtils.isEmpty(valorInteresadoPrincipal);

		ActionErrors errors = new ActionErrors();
		if (idsCorrectos) {
			ElementoTabla tabla = ficha
					.findElementoTablaById(idTablaInteresados);
			int numeroInteresadosPrincipales = 0;
			if (tabla != null) {
				Elemento[] elementos = tabla.getElementos();
				if (elementos != null) {
					for (int i = 0; i < elementos.length; i++) {
						Elemento elemento = (Elemento) elementos[i];
						if (elemento instanceof ElementoEtiquetaDato) {
							ElementoEtiquetaDato elementoEtiquetaDato = (ElementoEtiquetaDato) elemento;
							if ((elementoEtiquetaDato != null)
									&& (elementoEtiquetaDato.getEdicion() != null)
									&& (elementoEtiquetaDato.getEdicion()
											.getId() != null)
									&& (elementoEtiquetaDato.getEdicion()
											.getId().equals(idCampoPrincipal))
									&& (!ArrayUtils
											.isEmpty(elementoEtiquetaDato
													.getValores()))) {

								Valor[] valores = elementoEtiquetaDato
										.getValores();
								if (valores != null && valores.length > 0) {
									for (int j = 0; j < valores.length; j++) {
										Valor valor = valores[j];
										if (valorInteresadoPrincipal
												.equals(valor.getValor()))
											numeroInteresadosPrincipales++;

										if (numeroInteresadosPrincipales > 1) {
											errors.add(
													Constants.ERROR_GENERAL_MESSAGE,
													new ActionError(
															FondosConstants.MSG_INTERESADOS_PRINCIPALES_MAYOR_QUE,
															"1"));
											return errors;
										}

									}
								}
							}
						}
					}
				}
			}
		}

		if (logger.isDebugEnabled())
			logger.debug("executeEvent: ficha=" + Constants.NEWLINE
					+ ficha.toString());

		return errors;
	}

}
