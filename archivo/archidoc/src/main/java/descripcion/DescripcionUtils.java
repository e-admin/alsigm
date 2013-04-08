package descripcion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import xml.config.ConfiguracionDescripcion;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.util.CustomDate;
import common.util.StringUtils;

import descripcion.vos.CampoDatoBusquedaVO;

public class DescripcionUtils {
	public static String getIdCampoDescripcionFechaIni() {
		ConfiguracionDescripcion confDescrip = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();
		return confDescrip.getFechaExtremaInicial();
	}

	public static boolean isCampoDescripcionFechaIni(String idCampo) {
		return getIdCampoDescripcionFechaIni().equals(idCampo);
	}

	public static String getIdCampoDescripcionFechaFin() {
		ConfiguracionDescripcion confDescrip = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();
		return confDescrip.getFechaExtremaFinal();
	}

	public static boolean isCampoDescripcionFechaFin(String idCampo) {
		return getIdCampoDescripcionFechaFin().equals(idCampo);
	}

	public static Date getFechaFromRangoIniFin(String rangoValor,
			String rangoFormato, boolean isFechaIni) {
		CustomDate periodoFecha = new CustomDate(rangoValor, rangoFormato, "/",
				null);

		Date fecha = null;
		if (isFechaIni)
			fecha = periodoFecha.getMinDate();
		else
			fecha = periodoFecha.getMaxDate();
		return fecha;
	}

	public static List getCamposReemplazables(List listaCamposDatoBusqueda) {
		HashMap idCamposNoReemplazables = (HashMap) ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getCamposNoReemplazables();
		if (idCamposNoReemplazables != null) {
			List camposReemplazables = new ArrayList();
			String idCampo = null;
			for (Iterator it = listaCamposDatoBusqueda.iterator(); it.hasNext();) {
				Object obj = it.next();
				if (obj instanceof CampoDatoBusquedaVO)
					idCampo = ((CampoDatoBusquedaVO) obj).getId();
				// else if(obj instanceof CampoDatoVO)
				// idCampo=((CampoDatoVO)obj).getId();
				if (isCampoReemplazable(idCampo)) {
					camposReemplazables.add(obj);
				}
			}
			return camposReemplazables;
		}
		return listaCamposDatoBusqueda;
	}

	public static boolean isCampoReemplazable(String idCampo) {
		HashMap idCamposNoReemplazables = (HashMap) ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getCamposNoReemplazables();
		if (!StringUtils.isEmpty(idCampo)
				&& !idCamposNoReemplazables.containsKey(idCampo)) {
			return true;
		}
		return false;
	}
}
