package es.ieci.plusvalias.service;

import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REQUESTType;

/**
 * @author angel_castro@ieci.es - 20/09/2010
 */
public interface ICalculoLiquidacion {
	REPLYType calculoLiquidacion(REQUESTType request, String xmlMessage) throws Exception;
}