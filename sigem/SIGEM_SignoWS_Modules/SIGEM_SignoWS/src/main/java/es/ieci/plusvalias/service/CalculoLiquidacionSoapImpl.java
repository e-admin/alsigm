package es.ieci.plusvalias.service;

import org.apache.log4j.Logger;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACION;
import org.notariado.ancert.xml.plusvalias.liquidacion.ObjectFactory;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REPLYType;
import org.notariado.ancert.xml.plusvalias.liquidacion.CALCULOLIQUIDACIONType.REQUESTType;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * @author angel_castro@ieci.es - 20/09/2010
 */
public class CalculoLiquidacionSoapImpl implements ICalculoLiquidacion {
	private WebServiceTemplate template;	
	public static final Logger logger = Logger.getLogger(CalculoLiquidacionSoapImpl.class);

	public REPLYType calculoLiquidacion(REQUESTType request, String xmlMessage) throws Exception{
		ObjectFactory factory = new ObjectFactory();

		CALCULOLIQUIDACION liquidacion = factory.createCALCULOLIQUIDACION();
		liquidacion.setREQUEST(request);

		CALCULOLIQUIDACION liqResponse = (CALCULOLIQUIDACION) template.marshalSendAndReceive(liquidacion);

		return liqResponse.getREPLY();
	}

	public void setTemplate(WebServiceTemplate template) {
		this.template = template;
	}
}