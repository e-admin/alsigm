package ieci.tecdoc.sgm.pe.cuadernos;

import java.util.Map;

import ieci.tecdoc.sgm.pe.ConfiguracionComun;

import org.apache.log4j.Logger;

/*
 * $Id: SistemaPagoElectronicoFactory.java,v 1.1.2.2 2008/06/30 11:43:12 jnogales Exp $
 */
public class ManejadorCuadernosFactory {
	
	private static final Logger logger = Logger.getLogger(ManejadorCuadernosFactory.class);
			
	private static final String KEY_IMPLEMENTACION = "pago.SPT.cuadernos";
	
	private static ManejadorCuadernosFactory oInstance;
	
	private ManejadorCuadernosFactory(){
	}
	
	public static ManejadorCuadernosFactory getInstance(){
		if(oInstance == null){
			synchronized (ManejadorCuadernosFactory.class) {
				if(oInstance == null){
					oInstance = new ManejadorCuadernosFactory();
				}
			}
		}
		return oInstance;
	}
	
	public IManejadorCuaderno obtenerManejadorCuaderno(String tipo){
		IManejadorCuaderno manejador = null;
		try {
			manejador = (IManejadorCuaderno)((Map)ConfiguracionComun
					.getConfiguracion().getBean(KEY_IMPLEMENTACION)).get(tipo.replaceFirst("AL","C60M"));
		} catch (Exception e1) {
			logger.error("Error en la obtencion del servicio de Pago [obtenerSistemaPagoElectronico][Exception]", e1.fillInStackTrace());
		}
		
		return manejador;
	}
}
