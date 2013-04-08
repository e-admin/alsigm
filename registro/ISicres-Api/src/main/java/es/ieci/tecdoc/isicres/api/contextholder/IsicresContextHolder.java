package es.ieci.tecdoc.isicres.api.contextholder;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;

public class IsicresContextHolder {

	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(IsicresContextHolder.class);
	
	
	
	private static ThreadLocal contextoAplicacion = new ThreadLocal();
	
	
	public static void setContextoAplicacion(ContextoAplicacionVO contexto) {
		
		if (logger.isDebugEnabled()) {
				logger.debug("Seteando contextoAplicacion Isicres" + contextoAplicacion.get() + " a " + contexto);
			}
		
		contextoAplicacion.set(contexto);
		
	}

	public static ContextoAplicacionVO getContextoAplicacion() {
		
		ContextoAplicacionVO result=(ContextoAplicacionVO) contextoAplicacion.get();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Recuperado  contextoAplicacion Isicres - result=" + result);
		}
				
		return result;
	}

	
}
