package es.ieci.tecdoc.fwktd.csv.api.helper;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSV;
import es.ieci.tecdoc.fwktd.csv.core.vo.AplicacionCSVForm;

/**
 * Clase de utilidad para la transformación de objetos con la información de
 * aplicaciones externas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionHelper {

	public static List<AplicacionCSV> getListaAplicacionCSV(List<AplicacionVO> aplicacionesVO) {

		List<AplicacionCSV> aplicacionesCSV = new ArrayList<AplicacionCSV>();

		if (aplicacionesVO != null) {
			for (AplicacionVO aplicacionVO : aplicacionesVO) {
				aplicacionesCSV.add(getAplicacionCSV(aplicacionVO));
			}
		}

		return aplicacionesCSV;
	}

	public static AplicacionCSV getAplicacionCSV(AplicacionVO aplicacionVO) {

		AplicacionCSV aplicacionCSV = null;

		if (aplicacionVO != null) {
			aplicacionCSV = new AplicacionCSV();
			aplicacionCSV.setId(aplicacionVO.getId());
			aplicacionCSV.setCodigo(aplicacionVO.getCodigo());
			aplicacionCSV.setNombre(aplicacionVO.getNombre());
			aplicacionCSV.setInfoConexion(aplicacionVO.getInfoConexion());
		}

		return aplicacionCSV;
	}

	public static AplicacionVO getAplicacionVO(AplicacionCSVForm aplicacionCSVForm) {

		AplicacionVO aplicacionVO = null;

		if (aplicacionCSVForm != null) {
			aplicacionVO = new AplicacionVO();

			aplicacionVO.setCodigo(aplicacionCSVForm.getCodigo());
			aplicacionVO.setNombre(aplicacionCSVForm.getNombre());
			aplicacionVO.setInfoConexion(aplicacionCSVForm.getInfoConexion());
		}

		return aplicacionVO;
	}

	public static AplicacionVO getAplicacionVO(AplicacionCSV aplicacionCSV) {

		AplicacionVO aplicacionVO = null;

		if (aplicacionCSV != null) {
			aplicacionVO = new AplicacionVO();
			aplicacionVO.setId(aplicacionCSV.getId());
			aplicacionVO.setCodigo(aplicacionCSV.getCodigo());
			aplicacionVO.setNombre(aplicacionCSV.getNombre());
			aplicacionVO.setInfoConexion(aplicacionCSV.getInfoConexion());
		}

		return aplicacionVO;
	}

}