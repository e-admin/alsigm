package ieci.tecdoc.sgm.pe.pasarela.impl;

import ieci.tecdoc.sgm.pe.Constantes;
import ieci.tecdoc.sgm.pe.pasarela.ConectorPasarelaPago;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import p12d.exe.pasarelapagos.services.P12DPaymentFactoryAPI;
import p12d.exe.pasarelapagos.services.P12DPaymentManagerAPI;
import p12f.exe.pasarelapagos.objects.ProtocolData;
import p12f.exe.pasarelapagos.objects.Url;
import p12f.exe.pasarelapagos.paymentrequest.PaymentGatewayData;
import p12f.exe.pasarelapagos.paymentrequest.PaymentMode;
import p12f.exe.pasarelapagos.paymentrequest.PaymentRequestData;
import p12f.exe.pasarelapagos.paymentrequest.PresentationRequestData;

public class ConectorPasarelaPagoEuskadiNet implements ConectorPasarelaPago{
	private String urlRetorno;
	
	public void redireccionarAPasarela(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String peticionPagoPasarela=(String)request.getAttribute(Constantes.KEY_REQUEST_PASARELA_XML_PETICION_PAGO);
			PaymentRequestData returnedPymntReqData = PaymentRequestData.getObject(peticionPagoPasarela);
		
			if(returnedPymntReqData  == null 
					|| returnedPymntReqData.peticionesPago == null
					|| returnedPymntReqData.peticionesPago.size()==0) {
				throw new Exception();
			} 
			
			String idPago=returnedPymntReqData.peticionesPago.keySet().iterator().next().toString();
			
			// PAS0 3: ---- Redirigir al ciudadano a la Pasarela de Pagos
			//              Para ello componer un objeto PaymentGatewayData que engloba:
			//				- PaymentRequestData: Datos de Pago
			//				- ProtocolData: Datos de Protocolo
			//				- PresentationData: Datos de Presentación
			PaymentGatewayData dataToSend  =  new PaymentGatewayData();
			dataToSend.paymentRequestData  = returnedPymntReqData;
			dataToSend.protocolData =  composeProtocolData(request,idPago);
			dataToSend.presentationData = composePresentationData();
				
			P12DPaymentManagerAPI paymentAPI  = P12DPaymentFactoryAPI.getPaymentManagerAPIByOID("paymentManagerBZDByRPC");
			paymentAPI.sendUsingPost(dataToSend,request,response);
		}catch(Exception e){
			Logger.getLogger(this.getClass()).error(e);
		}
	}
	
	/** 
	* Obtiene la información de protocolo del pago
	*/
	private ProtocolData composeProtocolData(HttpServletRequest request,String idPago){
		// Componer la estructura con los datos de Protocolo como la url 
		// a la que hay que volver después de pagar
		String referenciaPeticionPago=(String)request.getAttribute(Constantes.KEY_REQUEST_PASARELA_REFERENCIA_PETICION_PAGO);
		ProtocolData protocolData = new ProtocolData();
		protocolData.responseURL="";
		protocolData.sourceSessionId = (request.getSession()).getId();
		protocolData.timeStamp = new Long(System.currentTimeMillis()).longValue();
		protocolData.sourceOperationNumber = "1";
		String servidorRetorno=request.getServerName();
		int puertoRetorno=request.getServerPort();
		String protocoloRetorno=(request.isSecure())?"https":"http";
		String urlRetornoBase=protocoloRetorno+"://"+servidorRetorno+":"+puertoRetorno + getUrlRetorno();
		Url urlVuelta = new Url("urlVuelta",urlRetornoBase+"?"+Constantes.REFERENCIA_KEY +"="+referenciaPeticionPago+
				"&"+Constantes.ID_PAGO_KEY+"="+idPago); // url vuelta por defecto
		protocolData.urls.put(urlVuelta.id, urlVuelta);
		return protocolData;
	}
	/** 
	* Obtiene la información de presentación de los pagos en el interfaz
	* de usuario de la Pasarela de Pagos
	*/	
	private PresentationRequestData composePresentationData(){
		// Componer la estructura con los datos de Presentación
		PresentationRequestData presentationRequestData = new PresentationRequestData();
		presentationRequestData.idioma = "es";	    
		PaymentMode modeON = new  PaymentMode();
		modeON.oid =PaymentMode.BANCA_ONLINE;
		PaymentMode modeOFF = new  PaymentMode();
		modeOFF.oid = PaymentMode.LIQUIDACION;
		presentationRequestData.paymentModes.put(modeON.oid, modeON);
		presentationRequestData.paymentModes.put(modeOFF.oid, modeOFF);
	
        // Se introducen entidades financieras
//        presentationRequestData.finantialOrgs = new HashMap();        
//        FinantialOrg bankoa = new FinantialOrg();
//        bankoa.oid = "9999";
//        presentationRequestData.finantialOrgs.put(bankoa.oid, bankoa);        
//        FinantialOrg entidad = new FinantialOrg();
//        entidad.oid = "3050";
//        presentationRequestData.finantialOrgs.put(entidad.oid, entidad);
		
		return presentationRequestData;
	}

	public String getUrlRetorno() {
		return urlRetorno;
	}

	public void setUrlRetorno(String urlRetorno) {
		this.urlRetorno = urlRetorno;
	}
}
