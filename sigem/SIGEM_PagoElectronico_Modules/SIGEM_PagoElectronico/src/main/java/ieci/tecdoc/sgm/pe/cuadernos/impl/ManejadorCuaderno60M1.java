package ieci.tecdoc.sgm.pe.cuadernos.impl;

import ieci.tecdoc.sgm.pe.ConfiguracionComun;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad1_2;
import ieci.tecdoc.sgm.pe.Liquidacion;
import ieci.tecdoc.sgm.pe.Pago;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import org.apache.log4j.Logger;

public class ManejadorCuaderno60M1 extends ManejadorCuaderno60M1y2 {
	/**
	 * Método que devuelve un Cuaderno60Modalidad1 a partir de los datos de un pago.
	 * @param poPago Datos del pago.
	 * @return Cuaderno60Modalidad1_2 Representación interna al manager de un Cuaderno60 Modalidad 1_2
	 */
	protected Cuaderno60Modalidad1_2 obtenerCuaderno60(Pago poPago){
		Cuaderno60Modalidad1_2 oCuaderno = new Cuaderno60Modalidad1_2();
		
		oCuaderno.setEjercicio(poPago.getLiquidacion().getEjercicio());
		
		// IDENT_1 : Se rellena con 3 ceros
		oCuaderno.setIdent1(ConfiguracionComun.obtenerPropiedad(ConfiguracionComun.KEY_CUADERNO60_M1_IDENT1));
		oCuaderno.setTipo(Cuaderno60Modalidad1_2.MODALIDAD_1);
		
		// IDENT_2: (3)Codigo del tributo (2)Ejercicio (2)Remesa
		StringBuffer sbAux = null;
		if(poPago.getLiquidacion().getTasa() != null){
			sbAux = new StringBuffer(poPago.getLiquidacion().getTasa().getCodigo());
		}else{
			sbAux = new StringBuffer(poPago.getIdTasa());
		}
		sbAux.append(poPago.getLiquidacion().getEjercicio().substring(2)).append(poPago.getLiquidacion().getRemesa());
		oCuaderno.setIdent2(sbAux.toString());
		oCuaderno.setIdentificacion(sbAux.toString());
		
		oCuaderno = completarDatosCuaderno60Modalidad1_2(poPago, oCuaderno);
		return oCuaderno;
	}
	
	public void validarLiquidacion(Liquidacion poLiquidacion) throws PagoElectronicoExcepcion{
		super.validarLiquidacion(poLiquidacion);
		
		Logger logger=Logger.getLogger(this.getClass());
		
		// remesa
		if(	(poLiquidacion.getRemesa() == null)
				|| (poLiquidacion.getRemesa().length() < 1)	|| (poLiquidacion.getRemesa().length() > 2) ){
			logger.error("Parámetro Remesa incorrecto");
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_ALTA_LIQUIDACION_BAD_PARAMS);			
		}
	}
	
	
}
