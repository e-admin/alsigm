package es.ieci.tecdoc.isicres.admin.business.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosUnidadOrganica;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosUnidadOrganicaDCVO;

/**
 * Clase de utilidad para convertir los VO del módulo DIR3 a los objetos de IsicresIntercambioREgistral
 * @author iecisa
 *
 */
public class DatosBasicosUnidadOrganicaDCHelper {


	public static DatosBasicosUnidadOrganicaDCVO getDatosBasicosUnidadOrganicaDCVO(DatosBasicosUnidadOrganica datosBasicosUnidadOrganica)
	{
		DatosBasicosUnidadOrganicaDCVO datosBasicosUnidadOrganicaDCVO = new DatosBasicosUnidadOrganicaDCVO();

		try{
			BeanUtils.copyProperties(datosBasicosUnidadOrganicaDCVO, datosBasicosUnidadOrganica);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return datosBasicosUnidadOrganicaDCVO;
	}

	public static List<DatosBasicosUnidadOrganicaDCVO> getDatosBasicosOficinasDCVO(List<DatosBasicosUnidadOrganica> datosUnidadesOrganicas)
	{
		List<DatosBasicosUnidadOrganicaDCVO> datosUnidadesOrganicasDCVO = new ArrayList<DatosBasicosUnidadOrganicaDCVO>();
		for (DatosBasicosUnidadOrganica datosBasicosUnidadOrganica : datosUnidadesOrganicas) {
			DatosBasicosUnidadOrganicaDCVO datosUnidadOrganicaDCVO = getDatosBasicosUnidadOrganicaDCVO(datosBasicosUnidadOrganica);
			datosUnidadesOrganicasDCVO.add(datosUnidadOrganicaDCVO);
		}
		return datosUnidadesOrganicasDCVO;
	}

}
