package descripcion.model.eventos.rangos;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.util.ArrayUtils;
import common.util.ObjectCloner;

import descripcion.model.eventos.EventoFichaException;
import descripcion.model.eventos.IEventoFicha;
import descripcion.model.xml.card.Elemento;
import descripcion.model.xml.card.ElementoEtiquetaDato;
import descripcion.model.xml.card.ElementoTabla;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.Valor;

/**
 * Implementación del interfaz para el comportamiento de los eventos generados
 * en una ficha.
 */
public class EventoNormalizarRangosFichaImpl implements IEventoFicha {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(EventoNormalizarRangosFichaImpl.class);

	// /** Objeto properties con los ids necesarios */
	// private Properties props = new Properties();
	//
	// /** Path para acceder al fichero de propiedades */
	// private final String PATH_PROPERTIES_FILE =
	// "descripcion/model/eventos/rangos/EventosNormalizarRangos.properties";
	//
	// /**
	// * Constructor.
	// */
	// public EventoNormalizarRangosFichaImpl()
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
		// String idCampoRangoDesde =
		// props.getProperty(EventoNormalizarKeys.ID_CAMPO_RANGO_DESDE);
		// String idCampoRangoHasta =
		// props.getProperty(EventoNormalizarKeys.ID_CAMPO_RANGO_HASTA);
		// String idCampoRangoDesdeNorm =
		// props.getProperty(EventoNormalizarKeys.ID_CAMPO_RANGO_DESDE_NORM);
		// String idCampoRangoHastaNorm =
		// props.getProperty(EventoNormalizarKeys.ID_CAMPO_RANGO_HASTA_NORM);

		String idTabla = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getIdTablaRangos();
		String idCampoRangoDesde = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoInicial();
		String idCampoRangoHasta = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoFinal();
		String idCampoRangoDesdeNorm = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoInicialNormalizado();
		String idCampoRangoHastaNorm = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getRangoFinalNormalizado();

		if (logger.isDebugEnabled()) {
			logger.debug("Id Tabla de Rangos: " + idTabla);
			logger.debug("Id Campo Rango Inicial: " + idCampoRangoDesde);
			logger.debug("Id Campo Rango Final: " + idCampoRangoHasta);
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

		if (StringUtils.isBlank(idCampoRangoDesde)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/RangoInicial");
		}

		if (StringUtils.isBlank(idCampoRangoHasta)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/RangoFinal");
		}

		if (StringUtils.isBlank(idCampoRangoDesdeNorm)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/RangoInicialNorm");
		}

		if (StringUtils.isBlank(idCampoRangoHastaNorm)) {
			idsCorrectos = false;
			logger.error("En el archivo-cfg no está definido el valor Campos_Descriptivos/RangoFinalNorm");
		}

		// boolean idsCorrectos = (idTabla!=null)&&
		// (idCampoRangoDesde!=null)&&
		// (idCampoRangoHasta!=null)&&
		// (idCampoRangoDesdeNorm!=null)&&
		// (idCampoRangoHastaNorm!=null)&&
		// (!idTabla.equals(Constants.STRING_EMPTY))&&
		// (!idCampoRangoDesde.equals(Constants.STRING_EMPTY))&&
		// (!idCampoRangoHasta.equals(Constants.STRING_EMPTY))&&
		// (!idCampoRangoDesdeNorm.equals(Constants.STRING_EMPTY))&&
		// (!idCampoRangoHastaNorm.equals(Constants.STRING_EMPTY));

		if (idsCorrectos) {
			ElementoTabla tabla = ficha.findElementoTablaById(idTabla);

			if (logger.isDebugEnabled())
				logger.debug("Tabla: " + tabla);

			if (tabla != null) {
				Elemento[] elementos = tabla.getElementos();

				if (logger.isDebugEnabled())
					logger.debug("Elementos: " + elementos);

				if (elementos != null) {
					for (int i = 0; i < elementos.length; i++) {
						Elemento elemento = (Elemento) elementos[i];
						if (logger.isDebugEnabled())
							logger.debug("Elemento[" + i + "]: " + elemento);
						if (elemento instanceof ElementoEtiquetaDato) {
							ElementoEtiquetaDato elementoEtiquetaDato = (ElementoEtiquetaDato) elemento;
							if (logger.isDebugEnabled())
								logger.debug("ElementoEtiquetaDato: "
										+ elementoEtiquetaDato);

							if ((elementoEtiquetaDato != null)
									&& (elementoEtiquetaDato.getEdicion() != null)
									&& (elementoEtiquetaDato.getEdicion()
											.getId() != null)
									&& (elementoEtiquetaDato.getEdicion()
											.getId().equals(idCampoRangoDesde))
									&& (!ArrayUtils
											.isEmpty(elementoEtiquetaDato
													.getValores()))) {

								ElementoEtiquetaDato elementoEtiquetaDatoNorm = (ElementoEtiquetaDato) ObjectCloner
										.deepCopy(elementoEtiquetaDato);

								if (logger.isDebugEnabled())
									logger.debug("elementoEtiquetaDatoNorm: "
											+ elementoEtiquetaDatoNorm);

								elementoEtiquetaDatoNorm.getEdicion().setId(
										idCampoRangoDesdeNorm);
								Valor[] valores = elementoEtiquetaDatoNorm
										.getValores();

								if (logger.isDebugEnabled())
									logger.debug("Valores de elementoEtiquetaDatoNorm: "
											+ valores);

								if (valores != null && valores.length > 0) {
									if (logger.isDebugEnabled())
										logger.debug("Recorrer los valores...");
									for (int j = 0; j < valores.length; j++) {
										Valor valor = valores[j];
										if (logger.isDebugEnabled())
											logger.debug("Valor[" + j + "]:"
													+ valor);

										String textoNormalizado = common.util.StringUtils
												.normalizarTexto(valor
														.getValor());

										if (logger.isDebugEnabled())
											logger.debug("Texto Normalizado: "
													+ textoNormalizado);

										elementoEtiquetaDatoNorm.getValor(j)
												.setValor(textoNormalizado);
										if (logger.isDebugEnabled())
											logger.debug("Modificado valor de elementoEtiquetaDatoNorm");

										// elementoEtiquetaDatoNorm.getValor(0).setValor(common.util.StringUtils.normalizarTexto(elementoEtiquetaDato.getValor(0).getValor()));
										tabla.addElemento(elementoEtiquetaDatoNorm);
										if (logger.isDebugEnabled())
											logger.debug("Añadido Elemento a Tabla: "
													+ elementoEtiquetaDatoNorm);
									}
									if (logger.isDebugEnabled())
										logger.debug("Fin de Recorrer valores");
								}
							}
							if ((elementoEtiquetaDato != null)
									&& (elementoEtiquetaDato.getEdicion() != null)
									&& (elementoEtiquetaDato.getEdicion()
											.getId() != null)
									&& (elementoEtiquetaDato.getEdicion()
											.getId().equals(idCampoRangoHasta))
									&& (!ArrayUtils
											.isEmpty(elementoEtiquetaDato
													.getValores()))) {

								if (logger.isDebugEnabled())
									logger.debug("Entro en comprobación:  "
											+ "if ((elementoEtiquetaDato!=null)&& "
											+ "(elementoEtiquetaDato.getEdicion()!=null)&& "
											+ " (elementoEtiquetaDato.getEdicion().getId()!=null)&& "
											+ " (elementoEtiquetaDato.getEdicion().getId().equals(idCampoRangoHasta))&& "
											+ " (!ArrayUtils.isEmpty(elementoEtiquetaDato.getValores())))");

								ElementoEtiquetaDato elementoEtiquetaDatoNorm = (ElementoEtiquetaDato) ObjectCloner
										.deepCopy(elementoEtiquetaDato);

								if (logger.isDebugEnabled()) {
									logger.debug("elementoEtiquetaDatoNorm: "
											+ elementoEtiquetaDatoNorm);
									logger.debug("Establecer el id del elementoEtiquetaDatoNorm:"
											+ idCampoRangoHastaNorm);
								}

								elementoEtiquetaDatoNorm.getEdicion().setId(
										idCampoRangoHastaNorm);

								if (logger.isDebugEnabled())
									logger.debug("Establecido el id del elementoEtiquetaDatoNorm");

								Valor[] valores = elementoEtiquetaDatoNorm
										.getValores();

								if (logger.isDebugEnabled())
									logger.debug("valores elementoEtiquetaDatoNorm: "
											+ valores);
								if (valores != null && valores.length > 0) {
									if (logger.isDebugEnabled())
										logger.debug("Recorrer valores de elementoEtiquetaDatoNorm...");

									for (int j = 0; j < valores.length; j++) {
										Valor valor = valores[j];
										if (logger.isDebugEnabled())
											logger.debug("valor[" + j + "]: "
													+ valor);

										String valorNormalizado = common.util.StringUtils
												.normalizarTexto(valor
														.getValor());

										if (logger.isDebugEnabled())
											logger.debug("valor Normalizado:"
													+ valorNormalizado);

										if (logger.isDebugEnabled())
											logger.debug("Estableciendo valor elementoEtiquetaDatoNorm.getValor(j).setValor(valorNormalizado);");

										elementoEtiquetaDatoNorm.getValor(j)
												.setValor(valorNormalizado);

										if (logger.isDebugEnabled())
											logger.debug("Establecido valor elementoEtiquetaDatoNorm.getValor(j).setValor(valorNormalizado);");

										tabla.addElemento(elementoEtiquetaDatoNorm);

										if (logger.isDebugEnabled())
											logger.debug("Añadido elemento:"
													+ elementoEtiquetaDatoNorm
													+ " a la tabla:" + tabla);
									}

									if (logger.isDebugEnabled())
										logger.debug("Fin de Recorrer valores de elementoEtiquetaDatoNorm...");
								}
								// elementoEtiquetaDatoNorm.getValor(0).setValor(common.util.StringUtils.normalizarTexto(elementoEtiquetaDato.getValor(0).getValor()));
								// tabla.addElemento(elementoEtiquetaDatoNorm);
							}
						}
					}
				}
			}
		} else {
			throw new EventoFichaException(
					"En la configuración falta alguno de los identificadores de los campos de rangos. Revise los logs para ver más información");
		}

		if (logger.isDebugEnabled())
			logger.debug("executeEvent: ficha=" + Constants.NEWLINE
					+ ficha.toString());

		return new ActionErrors();
	}
}
