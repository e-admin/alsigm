package solicitudes.consultas.view;

import java.util.Iterator;
import java.util.List;

import solicitudes.consultas.vos.DetalleConsultaVO;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.util.StringUtils;

import descripcion.model.TipoCampo;
import descripcion.model.xml.card.TipoFicha;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.RangoVO;
import fondos.model.ElementoCuadroClasificacion;

/**
 * 
 * Clase para mostrar en la vista detalles de préstamos, unidades documentales
 * en préstamos
 * 
 */
public class DetalleConsultaPO extends DetalleConsultaVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private GestionCuadroClasificacionBI cclfBI = null;
	private GestionDescripcionBI descripcionBI = null;

	private String nombreRangos = null;
	private boolean yaConsultado = false;
	private String denominacionExpediente = null;
	private boolean tieneUDocsRelacionadas = false;

	public DetalleConsultaPO(GestionCuadroClasificacionBI cclfBI,
			GestionDescripcionBI descripcionBI) {
		this.cclfBI = cclfBI;
		this.descripcionBI = descripcionBI;
	}

	/**
	 * Permite obtener los rangos
	 * 
	 * @return Cadena con los rangos
	 */
	public String getNombreRangos() {
		String ret = Constants.STRING_EMPTY;

		if (yaConsultado) {
			return nombreRangos;
		} else {
			// Obtener el nombre de los rangos sólo para fracciones de serie
			if (getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
				if (nombreRangos == null) {
					// LLamar y obtener la lista de rangos
					if (this.rangos == null || this.rangos.size() == 0) {
						String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getRangoInicial();
						String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion().getRangoFinal();

						if ((idCampoRangoInicial != null)
								&& (idCampoRangoFinal != null))
							this.rangos = cclfBI.getRangosElementoCF(
									getIdElementoCF(), idCampoRangoInicial,
									idCampoRangoFinal);
					}

					if ((this.rangos != null) && (!this.rangos.isEmpty())) {
						Iterator it = this.rangos.iterator();
						while (it.hasNext()) {
							RangoVO rango = (RangoVO) it.next();
							ret += rango.getRangoInicial()
									+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
									+ rango.getRangoFinal();
							if (it.hasNext())
								ret += Constants.DELIMITADOR_RANGOS;
						}
					}

					nombreRangos = ret;
				}
			}

			yaConsultado = true;
		}
		return ret;
	}

	/**
	 * Permite obtener el campo descriptivo denominación de número de expediente
	 * 
	 * @return Cadena con la denominación del número de expediente
	 */
	public String getDenominacionExpediente() {
		String ret = Constants.STRING_EMPTY;

		if (StringUtils.isEmpty(this.denominacionExpediente)) {
			String idCampoDenominacionExpediente = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDescripcion().getDenominacionExpediente();
			if ((idCampoDenominacionExpediente != null)) {
				List campos = descripcionBI.getValues(
						TipoFicha.FICHA_ELEMENTO_CF,
						TipoCampo.TEXTO_CORTO_VALUE, this.idElementoCF,
						idCampoDenominacionExpediente);

				if (campos != null && campos.size() > 0) {
					CampoTextoVO campoTexto = (CampoTextoVO) campos.get(0);
					ret = campoTexto.getValor();
				}

			}
		}

		this.denominacionExpediente = ret;
		return ret;
	}

	public boolean getTieneUDocsRelacionadas() {

		String idCampoUDocsRel = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getUdocsRel();
		int numCampos = descripcionBI.countValues(TipoFicha.FICHA_ELEMENTO_CF,
				TipoCampo.REFERENCIA_VALUE, this.idElementoCF, idCampoUDocsRel);

		if (numCampos > 0)
			this.tieneUDocsRelacionadas = true;
		else
			this.tieneUDocsRelacionadas = false;

		return this.tieneUDocsRelacionadas;

	}

}