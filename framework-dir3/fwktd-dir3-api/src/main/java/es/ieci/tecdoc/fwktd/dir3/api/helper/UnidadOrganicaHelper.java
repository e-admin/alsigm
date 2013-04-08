package es.ieci.tecdoc.fwktd.dir3.api.helper;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.api.vo.DatosBasicosUnidadOrganicaVO;
import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosUnidadOrganica;

/**
 * Clase de utilidad para la transformación de objetos con la información de
 * unidades orgánicas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class UnidadOrganicaHelper {

	public static List<DatosBasicosUnidadOrganica> getDatosBasicosUnidadesOrganicas(
			List<DatosBasicosUnidadOrganicaVO> listaDatosBasicosUnidadOrganicaVO) {

		List<DatosBasicosUnidadOrganica> listaDatosBasicosUnidadesOrganicas = new ArrayList<DatosBasicosUnidadOrganica>();

		if (listaDatosBasicosUnidadOrganicaVO != null) {
			for (DatosBasicosUnidadOrganicaVO datosBasicosUnidadOrganicaVO : listaDatosBasicosUnidadOrganicaVO) {
				DatosBasicosUnidadOrganica datosBasicosUnidadOrganica = getDatosBasicosUnidadOrganica(datosBasicosUnidadOrganicaVO);
				if (datosBasicosUnidadOrganica != null) {
					listaDatosBasicosUnidadesOrganicas
							.add(datosBasicosUnidadOrganica);
				}
			}
		}

		return listaDatosBasicosUnidadesOrganicas;
	}

	public static DatosBasicosUnidadOrganica getDatosBasicosUnidadOrganica(
			DatosBasicosUnidadOrganicaVO datosBasicosUnidadOrganicaVO) {

		DatosBasicosUnidadOrganica datosBasicosUnidadOrganica = null;

		if (datosBasicosUnidadOrganicaVO != null) {

			datosBasicosUnidadOrganica = new DatosBasicosUnidadOrganica();

			datosBasicosUnidadOrganica.setId(datosBasicosUnidadOrganicaVO
					.getId());
			datosBasicosUnidadOrganica.setNombre(datosBasicosUnidadOrganicaVO
					.getNombre());
			datosBasicosUnidadOrganica
					.setNivelAdministracion(datosBasicosUnidadOrganicaVO
							.getNivelAdministracion());
			datosBasicosUnidadOrganica
					.setDescripcionNivelAdministracion(datosBasicosUnidadOrganicaVO
							.getDescripcionNivelAdministracion());
			datosBasicosUnidadOrganica
					.setIndicadorEntidadDerechoPublico(datosBasicosUnidadOrganicaVO
							.getIndicadorEntidadDerechoPublico());
			datosBasicosUnidadOrganica
					.setIdExternoFuente(datosBasicosUnidadOrganicaVO
							.getIdExternoFuente());

			datosBasicosUnidadOrganica
					.setIdUnidadOrganicaSuperior(datosBasicosUnidadOrganicaVO
							.getIdUnidadOrganicaSuperior());
			datosBasicosUnidadOrganica
					.setNombreUnidadOrganicaSuperior(datosBasicosUnidadOrganicaVO
							.getNombreUnidadOrganicaSuperior());
			datosBasicosUnidadOrganica
					.setIdUnidadOrganicaPrincipal(datosBasicosUnidadOrganicaVO
							.getIdUnidadOrganicaPrincipal());
			datosBasicosUnidadOrganica
					.setNombreUnidadOrganicaPrincipal(datosBasicosUnidadOrganicaVO
							.getNombreUnidadOrganicaPrincipal());
			datosBasicosUnidadOrganica
					.setIdUnidadOrganicaEntidadDerechoPublico(datosBasicosUnidadOrganicaVO
							.getIdUnidadOrganicaEntidadDerechoPublico());
			datosBasicosUnidadOrganica
					.setNombreUnidadOrganicaEntidadDerechoPublico(datosBasicosUnidadOrganicaVO
							.getNombreUnidadOrganicaEntidadDerechoPublico());
			datosBasicosUnidadOrganica
					.setNivelJerarquico(datosBasicosUnidadOrganicaVO
							.getNivelJerarquico());

			datosBasicosUnidadOrganica.setEstado(datosBasicosUnidadOrganicaVO
					.getEstado());
			datosBasicosUnidadOrganica
					.setDescripcionEstado(datosBasicosUnidadOrganicaVO
							.getDescripcionEstado());
			datosBasicosUnidadOrganica
					.setFechaAltaOficial(datosBasicosUnidadOrganicaVO
							.getFechaAltaOficial());
			datosBasicosUnidadOrganica
					.setFechaBajaOficial(datosBasicosUnidadOrganicaVO
							.getFechaBajaOficial());
			datosBasicosUnidadOrganica
					.setFechaExtincion(datosBasicosUnidadOrganicaVO
							.getFechaExtincion());
			datosBasicosUnidadOrganica
					.setFechaAnulacion(datosBasicosUnidadOrganicaVO
							.getFechaAnulacion());
		}

		return datosBasicosUnidadOrganica;
	}

}