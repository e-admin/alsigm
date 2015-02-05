package transferencias.actions;

import java.util.List;

import transferencias.vos.UnidadDocumentalVO;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionUnidadDocumentalBI;

/**
 * Objeto para presentacion de unidades documentales
 */
public class TransferenciasUnidadDocumentalPO extends UnidadDocumentalVO {

	GestionRelacionesEntregaBI relacionBI = null;
	GestionUnidadDocumentalBI udocBI = null;
	List partesUdoc = null;
	fondos.vos.UnidadDocumentalVO udocEnCuadroClasificacion = null;
	List rangos = null;
	UnidadDocumentalVO nextUnidadDocumental = null;
	UnidadDocumentalVO prevUnidadDocumental = null;

	public class InfoVolumen {
		String soporte = null;
		int volumen = 0;

		public InfoVolumen(String soporte, int volumen) {
			super();
			this.soporte = soporte;
			this.volumen = volumen;
		}

		public String getSoporte() {
			return soporte;
		}

		public int getVolumen() {
			return volumen;
		}
	}

	public class InfoRango {
		String desde = null;
		String hasta = null;

		public InfoRango(String desde, String hasta) {
			super();
			this.desde = desde;
			this.hasta = hasta;
		}

		public String getDesde() {
			return desde;
		}

		public String getHasta() {
			return hasta;
		}
	}

	TransferenciasUnidadDocumentalPO(GestionRelacionesEntregaBI relacionBI,
			GestionUnidadDocumentalBI udocBI) {
		super(ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaInicial(), ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaFinal(), ConfigConstants.getInstance()
				.getSeparadorDefectoFechasRelacion());

		this.relacionBI = relacionBI;
		this.udocBI = udocBI;
	}

	public List getPartesUdoc() {
		if (partesUdoc == null)
			partesUdoc = relacionBI.getPartesUnidadDocumental(this);
		return partesUdoc;
	}

	public fondos.vos.UnidadDocumentalVO getUdocEnCuadroClasificacion() {
		if (udocEnCuadroClasificacion == null)
			udocEnCuadroClasificacion = udocBI
					.getUdocXUdocEnTransferencia(getId());
		return udocEnCuadroClasificacion;
	}

	public List getRangos() {
		/*
		 * if ((rangos == null || rangos.size() == 0) &&
		 * StringUtils.isNotEmpty(numeroExpediente)) { rangos = new ArrayList();
		 * StringTokenizer tokenizerRangos = new
		 * StringTokenizer(numeroExpediente, Constants.DELIMITADOR_RANGOS); if
		 * (tokenizerRangos != null) { while (tokenizerRangos.hasMoreTokens()) {
		 * String rango = tokenizerRangos.nextToken(); if (rango != null) {
		 * String desde = StringUtils.EMPTY, hasta = StringUtils.EMPTY;
		 * 
		 * StringTokenizer tokenizerDesdeHasta = new StringTokenizer(rango,
		 * Constants.DELIMITADOR_RANGO_INICIAL_FINAL); if (tokenizerDesdeHasta
		 * != null) { while (tokenizerDesdeHasta.hasMoreTokens()) { desde =
		 * tokenizerDesdeHasta.nextToken(); if
		 * (tokenizerDesdeHasta.hasMoreTokens()) hasta =
		 * tokenizerDesdeHasta.nextToken(); InfoRango infoRango = new
		 * InfoRango(desde, hasta); rangos.add(infoRango); } } } } }
		 * 
		 * }
		 */

		return this.xinfo.getRangos();

		// return rangos;
	}

	public void setRangos(List rangos) {
		this.xinfo.setRangos(rangos);
	}

	public UnidadDocumentalVO getNextUnidadDocumental() {
		if (nextUnidadDocumental == null)
			nextUnidadDocumental = relacionBI.getNextUnidadDocumental(
					getIdRelEntrega(), getOrden());
		return nextUnidadDocumental;
	}

	public UnidadDocumentalVO getPrevUnidadDocumental() {
		if (prevUnidadDocumental == null)
			prevUnidadDocumental = relacionBI.getPrevUnidadDocumental(
					getIdRelEntrega(), getOrden());
		return prevUnidadDocumental;
	}

}
