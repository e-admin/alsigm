package descripcion.model.eventos.rangos;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;

import descripcion.model.eventos.EventoFichaException;
import descripcion.model.eventos.IEventoFicha;
import descripcion.model.xml.card.Elemento;
import descripcion.model.xml.card.ElementoEtiquetaDato;
import descripcion.model.xml.card.ElementoTabla;
import descripcion.model.xml.card.Ficha;

/**
 * Implementación del interfaz para el comportamiento de los eventos generados
 * en una ficha.
 */
public class EventoEliminarRangosNormalizadosFichaImpl implements IEventoFicha {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(EventoEliminarRangosNormalizadosFichaImpl.class);

	// /** Objeto properties con los ids necesarios */
	// private Properties props = new Properties();
	//
	// /** Path para acceder al fichero de propiedades */
	// private final String PATH_PROPERTIES_FILE =
	// "descripcion/model/eventos/rangos/EventosNormalizarRangos.properties";

	// /**
	// * Constructor.
	// */
	// public EventoEliminarRangosNormalizadosFichaImpl()
	// {
	// // Cargar el fichero de propiedades
	// try {
	// props.load(this.getClass().getClassLoader().getResourceAsStream(PATH_PROPERTIES_FILE));
	// } catch (IOException e) {
	// }
	// }

	/**
	 * Ejecuta la lógica del evento.
	 * 
	 * @param tipoEvento
	 *            Tipo de evento ({link TipoEvento}).
	 * @param ficha
	 *            Ficha de descripción.
	 * @return Errores producidos
	 * @throws EventoFichaException
	 *             si ocurre algún error.
	 */
	public ActionErrors executeEvent(int tipoEvento, Ficha ficha, Locale locale)
			throws EventoFichaException {
		if (logger.isInfoEnabled())
			logger.info("executeEvent: tipoEvento=[" + tipoEvento + "]");

		// String idTabla =
		// props.getProperty(EventoNormalizarKeys.ID_TABLA_RANGOS);
		// String idCampoRangoDesdeNorm =
		// props.getProperty(EventoNormalizarKeys.ID_CAMPO_RANGO_DESDE_NORM);
		// String idCampoRangoHastaNorm =
		// props.getProperty(EventoNormalizarKeys.ID_CAMPO_RANGO_HASTA_NORM);

		String idTabla = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getIdTablaRangos();
		String idCampoRangoDesdeNorm = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoInicialNormalizado();
		String idCampoRangoHastaNorm = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoFinalNormalizado();

		// boolean idsCorrectos = (idTabla!=null)&&
		// (idCampoRangoDesdeNorm!=null)&&
		// (idCampoRangoHastaNorm!=null)&&
		// (!idTabla.equals(Constants.STRING_EMPTY))&&
		// (!idCampoRangoDesdeNorm.equals(Constants.STRING_EMPTY))&&
		// (!idCampoRangoHastaNorm.equals(Constants.STRING_EMPTY));

		if (logger.isDebugEnabled()) {
			logger.debug("Id Tabla de Rangos: " + idTabla);
			logger.debug("Id Campo Rango Inicial Normalizado: "
					+ idCampoRangoDesdeNorm);
			logger.debug("Id Campo Rango Final Normalizado: "
					+ idCampoRangoHastaNorm);
		}

		boolean idsCorrectos = true;

		if (StringUtils.isBlank(idTabla)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/Id_Tabla_Rangos");
		}

		if (StringUtils.isBlank(idCampoRangoDesdeNorm)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/RangoInicialNorm");
		}

		if (StringUtils.isBlank(idCampoRangoHastaNorm)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/RangoFinalNorm");
		}

		if (idsCorrectos) {
			ElementoTabla tabla = ficha.findElementoTablaById(idTabla);
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
											.getId()
											.equals(idCampoRangoDesdeNorm))) {
								tabla.removeElemento(elementoEtiquetaDato);
							}
							if ((elementoEtiquetaDato != null)
									&& (elementoEtiquetaDato.getEdicion() != null)
									&& (elementoEtiquetaDato.getEdicion()
											.getId() != null)
									&& (elementoEtiquetaDato.getEdicion()
											.getId()
											.equals(idCampoRangoHastaNorm))) {
								tabla.removeElemento(elementoEtiquetaDato);
							}
						}
					}
				}
			}
		} else {
			throw new EventoFichaException(
					"En la configuración falta alguno de los identificadores de los campos de rangos");
		}

		if (logger.isDebugEnabled())
			logger.debug("executeEvent: ficha=" + Constants.NEWLINE
					+ ficha.toString());

		return new ActionErrors();
	}
}
