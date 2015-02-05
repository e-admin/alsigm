package ieci.tecdoc.sgm.pe.cuadernos;

import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.pe.Liquidacion;
import ieci.tecdoc.sgm.pe.CriterioBusquedaPago;
import ieci.tecdoc.sgm.pe.PagoImpl;
import ieci.tecdoc.sgm.pe.Tasa;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

public interface IManejadorCuaderno {
	void validarPago(PagoImpl pago) throws PagoElectronicoExcepcion;
	void validarLiquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion;
	//PagoImpl obtenerPago(CriterioBusquedaPago criterio,PagoImpl pago);
	PagoImpl invocarSistemaPago(PagoImpl Pago) throws PagoElectronicoExcepcion;
	PagoImpl consultaCuadernos(CriterioBusquedaPago poCriterio) throws PagoElectronicoExcepcion;
	String obtenerDocumentoPago(PagoImpl pago,Liquidacion liquidacion,boolean pbCabeceraEstandar);
	
	String generarNumeroReferencia(DbConnection conn, Liquidacion poLiquidacion) throws PagoElectronicoExcepcion;
}
