package ieci.tecdoc.sgm.pe;

import org.apache.log4j.Logger;

/*
 * $Id: SistemaPagoElectronicoFactory.java,v 1.1.2.2 2008/06/30 11:43:12 jnogales Exp $
 */
public class SistemaPagoElectronicoFactory {
	
	private static final Logger logger = Logger.getLogger(SistemaPagoElectronicoFactory.class);
			
	private static final String KEY_IMPLEMENTACION = "pago.SPT.impl";
	
	private static SistemaPagoElectronicoFactory oInstance;
	
	private SistemaPagoElectronicoFactory(){
	}
	
	public static SistemaPagoElectronicoFactory getInstance(){
		if(oInstance == null){
			synchronized (SistemaPagoElectronicoFactory.class) {
				if(oInstance == null){
					oInstance = new SistemaPagoElectronicoFactory();
				}
			}
		}
		return oInstance;
	}
	
	public SistemaPagoElectronico obtenerSistemaPagoElectronico(){
		SistemaPagoElectronico spe = null;
		try {
			String connectorBeanName=ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_CONECTOR_BEAN);
			spe = (SistemaPagoElectronico)ConfiguracionComun.getConfiguracion().getBean(connectorBeanName);
			spe.configure(null);
		} catch (Exception e1) {
			logger.error("Error en la obtencion del servicio de Pago [obtenerSistemaPagoElectronico][Exception]", e1.fillInStackTrace());
		}
		
		return spe;
	}
}
