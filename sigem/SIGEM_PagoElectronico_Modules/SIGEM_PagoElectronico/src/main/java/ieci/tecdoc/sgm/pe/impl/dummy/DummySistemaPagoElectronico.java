package ieci.tecdoc.sgm.pe.impl.dummy;
/*
 * $Id: DummySistemaPagoElectronico.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import ieci.tecdoc.sgm.pe.CriterioBusquedaPago;
import ieci.tecdoc.sgm.pe.Cuaderno57;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad1_2;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad3;
import ieci.tecdoc.sgm.pe.DocumentoIngresoAutoliquidacion;
import ieci.tecdoc.sgm.pe.DocumentoIngresoLiquidacion;
import ieci.tecdoc.sgm.pe.SistemaPagoElectronicoBase;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.util.Map;

public class DummySistemaPagoElectronico extends SistemaPagoElectronicoBase {

	public DocumentoIngresoAutoliquidacion pagoAutoliquidacion12(
			DocumentoIngresoAutoliquidacion poDocIngreso)
			throws PagoElectronicoExcepcion {
		
		poDocIngreso.setNumDoc(String.valueOf(getNumDocNumber()));
		return poDocIngreso;
	}

	public DocumentoIngresoAutoliquidacion pagoAutoliquidacion3(
			DocumentoIngresoAutoliquidacion poDocIngreso)
			throws PagoElectronicoExcepcion {

		poDocIngreso.setNumDoc(String.valueOf(getNumDocNumber()));
		return poDocIngreso;
	}

	public DocumentoIngresoLiquidacion pagoLiquidacion(
			DocumentoIngresoLiquidacion poDocIngreso) 
			throws PagoElectronicoExcepcion{
		
			poDocIngreso.setNumDoc(String.valueOf(getNumDocNumber()));
			return poDocIngreso;
	}
	
	private long getNumDocNumber(){
		return System.currentTimeMillis();
	}

	public void configure(Map phmConfig) throws PagoElectronicoExcepcion {
		// TODO Auto-generated method stub		
	}

//	@Override
//	public DocumentoIngresoAutoliquidacion[] consultaAutoliquidacion12(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Cuaderno60Modalidad1_2 pagoCuaderno60_1_2(Cuaderno60Modalidad1_2 poCuaderno) throws PagoElectronicoExcepcion {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public Cuaderno60Modalidad1_2[] consultaCuaderno60Modalidad1_2(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion {
		// TODO Auto-generated method stub
		return null;
	}

	public Cuaderno60Modalidad1_2 pagoCuaderno60Modalidad1_2(Cuaderno60Modalidad1_2 poCuaderno) throws PagoElectronicoExcepcion {
		// TODO Auto-generated method stub
		return null;
	}

	public Cuaderno60Modalidad3[] consultaCuaderno60Modalidad3(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion {
		// TODO Auto-generated method stub
		return null;
	}

	public Cuaderno60Modalidad3 pagoCuaderno60Modalidad3(Cuaderno60Modalidad3 poCuaderno) throws PagoElectronicoExcepcion {
		// TODO Auto-generated method stub
		return null;
	}

	public Cuaderno57 pagoCuaderno57(Cuaderno57 poCuaderno)
			throws PagoElectronicoExcepcion {
		// TODO Auto-generated method stub
		return null;
	}
	
}
