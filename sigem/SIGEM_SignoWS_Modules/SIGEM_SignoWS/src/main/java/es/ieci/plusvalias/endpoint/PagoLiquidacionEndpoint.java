package es.ieci.plusvalias.endpoint;

import org.apache.log4j.Logger;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACION;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REPLYType;
import org.notariado.ancert.xml.plusvalias.liquidacion.PAGOLIQUIDACIONType.REQUESTType;

import es.ieci.plusvalias.service.PagoLiquidacionService;

/**
 * @author angel_castro@ieci.es - 28/07/2010
 */
public class PagoLiquidacionEndpoint extends AbstractPlusvaliaEndpoint {
	public static Logger logger = Logger.getLogger(CalculoLiquidacionEndpoint.class);
	private PagoLiquidacionService service;

	public Object invoke(Object requestObject, String xmlMessage) throws Exception{
		PAGOLIQUIDACION pagoLiquidacion = (PAGOLIQUIDACION) requestObject;
		REQUESTType request = pagoLiquidacion.getREQUEST(); 
		REPLYType response = service.pagoLiquidacion(request, xmlMessage);
	
		pagoLiquidacion.setREPLY(response);
		pagoLiquidacion.setREQUEST(null);
		
		if (logger.isDebugEnabled()) {
			logger.debug("Incoming PagoLiquidacion request");
		}
		return pagoLiquidacion;
	}
	
	public void setService(PagoLiquidacionService service) {
		this.service = service;
	}
}
