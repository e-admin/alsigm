package es.ieci.plusvalias.endpoint;

import org.apache.log4j.Logger;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACION;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REQUESTType;

import es.ieci.plusvalias.service.ICalculoLiquidacion;

/**
 * @author angel_castro@ieci.es - 28/07/2010
 */
public class CalculoLiquidacionEndpoint extends AbstractPlusvaliaEndpoint
{
	public static Logger logger = Logger.getLogger(CalculoLiquidacionEndpoint.class);
	private ICalculoLiquidacion service;

	public Object invoke(Object requestObject, String xmlMessage) throws Exception
	{
		CALCULOLIQUIDACION calculoLiquidacion = (CALCULOLIQUIDACION) requestObject;
		REQUESTType request = calculoLiquidacion.getREQUEST();
		REPLYType response = service.calculoLiquidacion(request, xmlMessage);

		calculoLiquidacion.setREPLY(response);
		calculoLiquidacion.setREQUEST(null);

		logger.debug("Incoming CalculoLiquidacion request");

		return calculoLiquidacion;
	}

	public void setService(ICalculoLiquidacion service)
	{
		this.service = service;
	}
}
