package fondos.actions;

import java.util.Iterator;
import java.util.List;

import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.GestionUnidadDocumentalBI;
import common.util.StringUtils;

import fondos.model.ElementoCuadroClasificacion;
import fondos.model.SubtipoNivelCF;
import fondos.vos.INivelCFVO;
import fondos.vos.UDocEnFraccionSerieVO;
import fondos.vos.UnidadDocumentalVO;

/**
 * Métodos de obtención de los valores de los campos de una unidad documental en
 * una división de fracción de serie del cuadro de clasificación de fondos
 * documentales
 */
public class UDocEnFraccionSeriePO extends UDocEnFraccionSerieVO {

	GestionUnidadDocumentalBI udocBI = null;
	GestionCuadroClasificacionBI cuadroBI = null;
	GestionFraccionSerieBI fraccionSerieBI = null;
	UnidadDocumentalVO udocEnCuadroClasificacion = null;
	private String nombreRangos = null;
	private boolean yaConsultado = false;
	private int subtipo = SubtipoNivelCF.UDOCSIMPLE.getIdentificador();
	UDocEnFraccionSerieVO nextUdocEnDivisionFs = null;
	UDocEnFraccionSerieVO prevUdocEnDivisionFs = null;

	UDocEnFraccionSeriePO(GestionUnidadDocumentalBI udocBI,
			GestionFraccionSerieBI fraccionSerieBI,
			GestionCuadroClasificacionBI cuadroBI) {
		this.udocBI = udocBI;
		this.fraccionSerieBI = fraccionSerieBI;
		this.cuadroBI = cuadroBI;
	}

	public UnidadDocumentalVO getUdocEnCuadroClasificacion() {
		// Como hemos almacenado el id de unidad documental en fracción de serie
		// en el campo
		// de idudocre de la tabla asgdudocenui
		if (udocEnCuadroClasificacion == null)
			udocEnCuadroClasificacion = udocBI
					.getUdocXUdocEnDivisionFS(getIdUDoc());
		return udocEnCuadroClasificacion;
	}

	/**
	 * Permite obtener los rangos
	 * 
	 * @return Cadena con los rangos
	 */
	public String getNombreRangos() {

		if (!yaConsultado) {
			String ret = Constants.STRING_EMPTY;
			// Obtener el nombre de los rangos sólo para fracciones de serie
			if (getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
				if (StringUtils.isEmpty(nombreRangos)) {
					// Si está validada, la información ya está en xinfo
					if (!this.validada) {
						// LLamar y obtener la lista de rangos
						if (this.xinfo.getRangos() == null
								|| this.xinfo.getRangos().size() == 0) {
							String idCampoRangoInicial = ConfiguracionSistemaArchivoFactory
									.getConfiguracionSistemaArchivo()
									.getConfiguracionDescripcion()
									.getRangoInicial();
							String idCampoRangoFinal = ConfiguracionSistemaArchivoFactory
									.getConfiguracionSistemaArchivo()
									.getConfiguracionDescripcion()
									.getRangoFinal();

							if ((idCampoRangoInicial != null)
									&& (idCampoRangoFinal != null))
								this.xinfo.setRangos(fraccionSerieBI
										.getRangosUDoc(idUDoc,
												idCampoRangoInicial,
												idCampoRangoFinal));
						}
					}

					if ((this.xinfo.getRangos() != null)
							&& (!this.xinfo.getRangos().isEmpty())) {
						Iterator it = this.xinfo.getRangos().iterator();
						while (it.hasNext()) {
							transferencias.vos.RangoVO rango = (transferencias.vos.RangoVO) it
									.next();
							ret += rango.getDesde()
									+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
									+ rango.getHasta();
							if (it.hasNext())
								ret += Constants.DELIMITADOR_RANGOS;
						}
					}

					nombreRangos = ret;
				}
			}

			yaConsultado = true;
		}

		return nombreRangos;
	}

	public int getSubtipo() {

		if (!yaConsultado) {
			if (StringUtils.isNotEmpty(idNivelDocumental)) {
				INivelCFVO nivelCFVO = cuadroBI.getNivelCF(idNivelDocumental);
				if (nivelCFVO != null)
					this.subtipo = nivelCFVO.getSubtipo();
			}
		}

		return this.subtipo;
	}

	public List getRangos() {
		return this.xinfo.getRangos();
	}

	/**
	 * Devuelve la siguiente unidad documental en la division de fraccion de
	 * serie
	 * 
	 * @return {@link UDocEnFraccionSerieVO}
	 */
	public UDocEnFraccionSerieVO getNextUdocEnDivisionFs() {
		if (nextUdocEnDivisionFs == null)
			nextUdocEnDivisionFs = fraccionSerieBI.getNextUdocEnDivisionFs(
					getIdFS(), getOrden());
		return nextUdocEnDivisionFs;
	}

	/**
	 * Devuelve la anterior unidad documental en la division de fraccion de
	 * serie
	 * 
	 * @return {@link UDocEnFraccionSerieVO}
	 */
	public UDocEnFraccionSerieVO getPrevUdocEnDivisionFs() {
		if (prevUdocEnDivisionFs == null)
			prevUdocEnDivisionFs = fraccionSerieBI.getPrevUdocEnDivisionFs(
					getIdFS(), getOrden());
		return prevUdocEnDivisionFs;
	}
}
