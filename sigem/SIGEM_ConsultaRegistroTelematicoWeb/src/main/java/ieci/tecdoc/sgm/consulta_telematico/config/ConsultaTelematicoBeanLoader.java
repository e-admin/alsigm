package ieci.tecdoc.sgm.consulta_telematico.config;

import ieci.tecdoc.sgm.consulta_telematico.manager.ObtenerDetalleManager;
import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.exception.SigemException;

public class ConsultaTelematicoBeanLoader {

	private static final String DEFAULT_OBTENER_DETALLE_IMPL = "obtenerDetalleDefaultImpl";

	public static ObtenerDetalleManager getObtenerDetalleManager() throws SigemException{
		return getObtenerDetalleManager(ConsultaTelematicoConfiguration.getConfiguration(),DEFAULT_OBTENER_DETALLE_IMPL);
	}
	
	public static ObtenerDetalleManager getObtenerDetalleManager(Config config, String bean) throws SigemException{
		if(config != null) {
			ObtenerDetalleManager obtenerDetalle = null;
			try {
				obtenerDetalle = (ObtenerDetalleManager) config.getBean(bean);
			} catch (Exception e) {
				throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION, e);
			}
			return obtenerDetalle;
		} else {
			throw new SigemException(SigemException.EXC_GENERIC_EXCEPCION);
		}
	}
}
