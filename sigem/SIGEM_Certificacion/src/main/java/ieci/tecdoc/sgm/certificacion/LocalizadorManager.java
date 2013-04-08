package ieci.tecdoc.sgm.certificacion;

import ieci.tecdoc.sgm.certificacion.config.impl.Config;
import ieci.tecdoc.sgm.certificacion.config.impl.DefaultConfiguration;
import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.manager.CertificacionPagoManager;

public class LocalizadorManager {

	private static final String CERTIFICACION_PAGO_MANAGER_DEFAULT_IMPL = "CERTIFICACION_PAGO_MANAGER_DEFAULT_IMPL";
	
	public static Object getServicio(String psImplName) throws CertificacionException{
		return getServicio(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static Object getServicio(Config poConfig, String pcImpl) throws CertificacionException{
		if(poConfig != null){
			Object oService = null;
			try {
				oService = poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e);
			}
			return oService;
		}else{
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION);
		}
	}

	
	public static CertificacionPagoManager getManagerCertificacionPago() throws CertificacionException{
		return getManagerCertificacionPago(DefaultConfiguration.getConfiguration(), CERTIFICACION_PAGO_MANAGER_DEFAULT_IMPL);
	}

	public static CertificacionPagoManager getManagerCertificacionPago(String psImplName) throws CertificacionException{
		return getManagerCertificacionPago(DefaultConfiguration.getConfiguration(), psImplName);
	}

	public static CertificacionPagoManager getManagerCertificacionPago(Config poConfig) throws CertificacionException{
		return getManagerCertificacionPago(poConfig, CERTIFICACION_PAGO_MANAGER_DEFAULT_IMPL);
	}
	
	public static CertificacionPagoManager getManagerCertificacionPago(Config poConfig, String pcImpl) throws CertificacionException{
		if(poConfig != null){
			CertificacionPagoManager oManager = null;
			try {
				oManager = (CertificacionPagoManager)poConfig.getBean(pcImpl);
			} catch (Exception e) {
				throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION, e);
			}
			return oManager;
		}else{
			throw new CertificacionException(CertificacionException.EXC_GENERIC_EXCEPCION);
		}
	}
}
