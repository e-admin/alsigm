package es.ieci.plusvalias;

import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.DatosBancarios;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Transmitente;

public interface IPlusvaliaEntidadService {
	
	
	ResultadoUnitario calcular(String actoJuridico,Inmueble inmueble, Adquiriente adquiriente, Transmitente transmitente, DatosLiquidacion datosLiquidacion) throws Exception;
	Inmueble getInmueble(String refCatastral,DatosLiquidacion datosLiquidacion) throws Exception;
	boolean isDerechosCompatible(Transmitente transmitente, Adquiriente adquiriente);
	boolean isPlusvaliaHerencia(String actoJuridico) throws Exception;
	boolean isPlusvaliaDonacion(String actoJuridico) throws Exception;
	void completarPlusvalia(Plusvalia plusvalia,String actoJuridico,Inmueble inmueble, Adquiriente adquiriente, Transmitente transmitente, DatosLiquidacion datosLiquidacion) throws Exception;
	
	/**
	 * Comprobamos que no exista ninguna plusvalia ya realizada
	 */
	void comprobar(String refCatastral, Transmitente[] transmitentes, Adquiriente[] adquirientes, String code, boolean pagada) throws Exception;
	
//	public ArrayList<byte[]> registrarIniciarExpedientePagar(Plusvalia plusvalia, Sujeto[] representantes, DatosLiquidacion datosLiquidacion,
//			String xmlSolicitud, DATOSBANCARIOSType datosBancarios, MessageSource messageSource, ReportService reporter,
//			CalculoPlusvaliaHelper calculoHelper) throws Exception
	
	void almacenarPlusvalia(Plusvalia plusvalia);
	
	Plusvalia getPlusvalia(String refCatastral,String nifTrans,String nifAdq,int claseDerecho);
	
	
	void prepagoProcess(Plusvalia plusvalia, DatosBancarios datosBancarios, Liquidacion liquidacion) throws Exception;
	void postpagoProcess(Plusvalia plusvalia, DatosBancarios datosBancarios, Liquidacion liquidacion)  throws Exception;
}
