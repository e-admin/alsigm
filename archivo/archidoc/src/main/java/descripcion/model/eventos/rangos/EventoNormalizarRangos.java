package descripcion.model.eventos.rangos;

import xml.config.ConfiguracionSistemaArchivoFactory;

/**
 * clase singleton para acceder a los datos del fichero
 * EventosNormalizarRangos.properties
 */
public class EventoNormalizarRangos {

	private static EventoNormalizarRangos instancia = null;

	/**
	 * Constructor.
	 */
	private EventoNormalizarRangos() {
	}

	public static EventoNormalizarRangos getInstance() {
		if (instancia == null)
			instancia = new EventoNormalizarRangos();
		return instancia;
	}

	public String getIdCampoNormalizado(String idCampo) {
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

		if (idCampo.equals(idCampoRangoDesde)) {
			return idCampoRangoDesdeNorm;
		} else if (idCampo.equals(idCampoRangoHasta)) {
			return idCampoRangoHastaNorm;
		}
		return null;
	}
}
