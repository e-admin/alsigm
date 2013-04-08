package es.ieci.plusvalias.service;

import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import es.ieci.plusvalias.IPlusvaliaEntidadService;
import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.DatosBancarios;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.Transmitente;

public abstract class BasePlusvaliaService implements IPlusvaliaEntidadService{
	public void prepagoProcess(Plusvalia plusvalia, DatosBancarios datosBancarios, Liquidacion liquidacion) throws Exception{
		
	}
	
	public void postpagoProcess(Plusvalia plusvalia, DatosBancarios datosBancarios, Liquidacion liquidacion) throws Exception{
		
	}
	
	public void completarPlusvalia(Plusvalia plusvalia,String actoJuridico,Inmueble inmueble, Adquiriente adquiriente, Transmitente transmitente, DatosLiquidacion datosLiquidacion) throws Exception{
		
	}
}
