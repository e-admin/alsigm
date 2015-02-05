package es.ieci.tecdoc.isicres.admin.business.util;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import es.ieci.tecdoc.fwktd.dir3.core.vo.DatosBasicosOficina;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosOficinaDCVO;

/**
 * Clase de utilidad para convertir los VO del módulo DIR3 a los objetos de IsicresIntercambioREgistral
 * @author iecisa
 *
 */
public class DatosBasicosOficinaDCHelper {


	public static DatosBasicosOficinaDCVO getDatosBasicosOficinaDCVO(DatosBasicosOficina datosBasicosOficina)
	{
		DatosBasicosOficinaDCVO datosBasicosOficinaDCVO = new DatosBasicosOficinaDCVO();

		try{
			BeanUtils.copyProperties(datosBasicosOficinaDCVO, datosBasicosOficina);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return datosBasicosOficinaDCVO;
	}

	public static List<DatosBasicosOficinaDCVO> getDatosBasicosOficinasDCVO(List<DatosBasicosOficina> datosOficinas)
	{
		List<DatosBasicosOficinaDCVO> datosOficinasDCVO = new ArrayList<DatosBasicosOficinaDCVO>();
		for (DatosBasicosOficina datosBasicosOficina : datosOficinas) {
			DatosBasicosOficinaDCVO datosOficinaDCVO = getDatosBasicosOficinaDCVO(datosBasicosOficina);
			datosOficinasDCVO.add(datosOficinaDCVO);
		}
		return datosOficinasDCVO;
	}


}
