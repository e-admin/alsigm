package ieci.tecdoc.sgm.pe.cuadernos.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ieci.tecdoc.sgm.pe.ConfiguracionComun;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad1_2;
import ieci.tecdoc.sgm.pe.Liquidacion;
import ieci.tecdoc.sgm.pe.Pago;
import ieci.tecdoc.sgm.pe.Util;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import org.apache.log4j.Logger;

public class ManejadorCuaderno60M2 extends ManejadorCuaderno60M1y2 {
	
	/**
	 * Método que devuelve un Cuaderno60Modalidad2 a partir de los datos de un pago.
	 * @param poPago Datos del pago.
	 * @return Cuaderno60Modalidad1_2 Representación interna al manager de un Cuaderno60 Modalidad 1_2
	 */
	protected Cuaderno60Modalidad1_2 obtenerCuaderno60(Pago poPago){
		Cuaderno60Modalidad1_2 oCuaderno = new Cuaderno60Modalidad1_2();
		
		// IDENT_1: (3)Código del concepto
		oCuaderno.setIdent1(poPago.getIdTasa());
		oCuaderno.setTipo(Cuaderno60Modalidad1_2.MODALIDAD_2);
		
		String cAnoVencimiento = poPago.getLiquidacion().getVencimiento().substring(0, 4);		
		String cMesVencimiento = poPago.getLiquidacion().getVencimiento().substring(4, 6);
		String cDiaVencimiento = poPago.getLiquidacion().getVencimiento().substring(6, 8);
		Calendar cal = GregorianCalendar.getInstance();
		((GregorianCalendar)cal).setGregorianChange(new Date(Long.MAX_VALUE));
		cal.set(Integer.valueOf(cAnoVencimiento).intValue(),
				Integer.valueOf(cMesVencimiento).intValue() - 1, 
				Integer.valueOf(cDiaVencimiento).intValue(), 
				23, 59, 59);
		String fechaJuliana = String.valueOf(cal.get(Calendar.DAY_OF_YEAR));
		fechaJuliana=Util.rellenarConCerosIzquierda(fechaJuliana, 3);
		
		StringBuffer sbAux = new StringBuffer();
		if(ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()){
			//la pasarela externa utiliza un formato del identificador del periodo distinta a red.es
			// IDENT_2: (1)discriminante periodo (3) tributo (2)Ejercicio (1)Último digito año vencimiento (3)Fecha juliana vencimiento tributo 
			sbAux.append(poPago.getLiquidacion().getDiscriminante());
			sbAux.append(poPago.getLiquidacion().getIdTasa());
			sbAux.append(poPago.getLiquidacion().getEjercicio().substring(poPago.getLiquidacion().getEjercicio().length() -2));		
			sbAux.append(cAnoVencimiento.charAt(3));
			sbAux.append(fechaJuliana);
		}else{
			// IDENT_2: (2)Ejercicio (1)Último digito año vencimiento (3)Fecha juliana vencimiento tributo (1)discriminante periodo
			sbAux.append(poPago.getLiquidacion().getEjercicio().substring(poPago.getLiquidacion().getEjercicio().length() -2));	
			sbAux.append(cAnoVencimiento.charAt(3));
			sbAux.append(fechaJuliana);
			sbAux.append(poPago.getLiquidacion().getDiscriminante());
		}
		oCuaderno.setIdent2(sbAux.toString());
		
		oCuaderno = completarDatosCuaderno60Modalidad1_2(poPago, oCuaderno);
		return oCuaderno;
	}
	
	public void validarLiquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
		super.validarLiquidacion(poLiquidacion);
		Logger logger=Logger.getLogger(this.getClass());
		
		// vencimiento
		if(	(poLiquidacion.getVencimiento() == null)
				|| (poLiquidacion.getVencimiento().length() < 1) || (poLiquidacion.getVencimiento().length() > 8) ){
			logger.error("Parámetro Vencimiento incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}			

		// discriminante periodo
		if(poLiquidacion.getDiscriminante() != null){
			if( (!Liquidacion.DISCRIMINANTE_UN_PERIODO.equals(poLiquidacion.getDiscriminante()))
				&&(!Liquidacion.DISCRIMINANTE_DOS_PERIODOS_PRIMER_IMPORTE.equals(poLiquidacion.getDiscriminante()))
				&&(!Liquidacion.DISCRIMINANTE_DOS_PERIODOS_SEGUNDO_IMPORTE.equals(poLiquidacion.getDiscriminante())) ){
				logger.error("Parámetro Discriminante incorrecto.");
				throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);
			}
		}else{
			poLiquidacion.setDiscriminante(Liquidacion.DISCRIMINANTE_UN_PERIODO);
		}
		
	}
}
