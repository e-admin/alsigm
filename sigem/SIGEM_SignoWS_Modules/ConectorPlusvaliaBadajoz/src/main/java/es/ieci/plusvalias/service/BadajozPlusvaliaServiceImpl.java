package es.ieci.plusvalias.service;

import es.ieci.plusvalias.IPlusvaliaEntidadService;
import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.DatosLiquidacion;
import es.ieci.plusvalias.api.Inmueble;
import es.ieci.plusvalias.api.Plusvalia;
import es.ieci.plusvalias.api.ResultadoUnitario;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.manager.CalculoPlusvaliaManager;
import es.ieci.plusvalias.manager.PagoPlusvaliaManager;
import es.ieci.plusvalias.util.DateUtils;

public class BadajozPlusvaliaServiceImpl extends BasePlusvaliaService implements IPlusvaliaEntidadService{
	private CalculoPlusvaliaManager calculoManager;
	private PagoPlusvaliaManager pagoManager;
	
	public void comprobar(String refCatastral, Transmitente[] transmitentes, Adquiriente[] adquirientes,String code,boolean pagada) throws Exception{
		if(!pagada){
			calculoManager.comprobar(refCatastral,transmitentes,adquirientes,code);
		}else{
			pagoManager.comprobar(refCatastral,transmitentes,adquirientes,code);
		}
	}
	
	public void almacenarPlusvalia(Plusvalia plusvalia) {
		if(!plusvalia.getPagada()){
			calculoManager.registrar(plusvalia);
		}else{
			pagoManager.pagar(plusvalia);
		}
	}

	public Plusvalia getPlusvalia(String refCatastral,String nifTrans,String nifAdq,int claseDerecho){
		return pagoManager.getPago(refCatastral,nifTrans,nifAdq,claseDerecho);
	}
	
	
	public void setCalculoManager(CalculoPlusvaliaManager calculoManager) {
		this.calculoManager = calculoManager;
	}
	public void setPagoManager(PagoPlusvaliaManager pagoManager) {
		this.pagoManager = pagoManager;
	}

	public ResultadoUnitario calcular(String actoJuridico, Inmueble inmueble,
			Adquiriente adquiriente, Transmitente transmitente, DatosLiquidacion datosLiquidacion)
			throws Exception {
		calculoManager.calcular(actoJuridico, inmueble, adquiriente, transmitente,datosLiquidacion);
		return null;
	}

	public Inmueble getInmueble(String refCatastral, DatosLiquidacion datosLiquidacion) throws Exception {
		int anyoLiquidacionActual = DateUtils.getYearFrom(datosLiquidacion.getFechaTransActual());
		return calculoManager.getImueble(refCatastral, anyoLiquidacionActual);
	}

	public boolean isDerechosCompatible(Transmitente transmitente, Adquiriente adquiriente) {
		return calculoManager.isDerechosCompatible(transmitente,adquiriente);
	}

	public boolean isPlusvaliaDonacion(String actoJuridico) throws Exception {
		return checkTipoPlusvalia(actoJuridico,"D");
	}

	public boolean isPlusvaliaHerencia(String actoJuridico) throws Exception {
		return checkTipoPlusvalia(actoJuridico,"H");
	}

	private boolean checkTipoPlusvalia(String actoJuridico,String tipoPluvalia) throws Exception {
		String tipoAncert=calculoManager.getTipoAncert(actoJuridico);
		if(tipoAncert!=null && tipoAncert.equals(tipoPluvalia)) return true;
		return false;
	}
}
