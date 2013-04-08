package ieci.tecdoc.sgm.pe;
/*
 * $Id: SistemaPagoElectronico.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import java.util.Map;

import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

public interface SistemaPagoElectronico {

	void configure(Map phmConfig) throws PagoElectronicoExcepcion;
	
	Cuaderno60Modalidad1_2 pagoCuaderno60Modalidad1_2(Cuaderno60Modalidad1_2 poCuaderno) throws PagoElectronicoExcepcion;
	
	Cuaderno60Modalidad1_2[] consultaCuaderno60Modalidad1_2(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion;
	
	Cuaderno60Modalidad3 pagoCuaderno60Modalidad3(Cuaderno60Modalidad3 poCuaderno) throws PagoElectronicoExcepcion;
	
	Cuaderno60Modalidad3[] consultaCuaderno60Modalidad3(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion;
	
	Cuaderno57 pagoCuaderno57(Cuaderno57 poCuaderno) throws PagoElectronicoExcepcion;
	
	Cuaderno57[] consultaCuaderno57(CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion;
	
}
