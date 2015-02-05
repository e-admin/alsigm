package ieci.tecdoc.sgm.pe.impl.euskadi.net;

import ieci.tecdoc.sgm.pe.ConfiguracionComun;
import ieci.tecdoc.sgm.pe.CriterioBusquedaPago;
import ieci.tecdoc.sgm.pe.Cuaderno57;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad1_2;
import ieci.tecdoc.sgm.pe.Cuaderno60Modalidad3;
import ieci.tecdoc.sgm.pe.CuadernoBase;
import ieci.tecdoc.sgm.pe.SistemaPagoElectronicoBase;
import ieci.tecdoc.sgm.pe.SistemaPasarelaPagoElectronico;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ejie.r01f.rpcdispatcher.RPCCall;
import com.ejie.r01f.rpcdispatcher.RPCFunction;
import com.ejie.r01f.rpcdispatcher.RPCParameter;
import com.ejie.r01f.util.URLEncoder;
import com.ejie.r01f.xmlproperties.XMLProperties;

import p12d.exe.pasarelapagos.services.P12DPaymentFactoryAPI;
import p12d.exe.pasarelapagos.services.P12DPaymentManagerAPI;
import p12f.exe.pasarelapagos.objects.OperationResult;
import p12f.exe.pasarelapagos.objects.PeriodoPago;
import p12f.exe.pasarelapagos.paymentrequest.ConceptoPeticion;
import p12f.exe.pasarelapagos.paymentrequest.PaymentRequestData;
import p12f.exe.pasarelapagos.paymentrequest.PeticionPago;

public class EuskadiNetSistemaPagoElectronico extends SistemaPagoElectronicoBase implements SistemaPasarelaPagoElectronico{
	public static final float PORCENTAJE_IVA=(float)0.16;
	
	
	public void configure(Map phmConfig) throws PagoElectronicoExcepcion {
		super.configure(phmConfig);
		
		System.setProperty("EJIE_PROPERTY_LOADER","classPathLoader");
		System.setProperty("EJIE_PROPERTIES_PATTERN","/ieci/tecdoc/sgm/pe/impl/euskadi/net/resources/[entityCode]/[entityCode].properties.xml");
	}

	public Cuaderno60Modalidad3[] consultaCuaderno60Modalidad3(
			CriterioBusquedaPago oCriterio) throws PagoElectronicoExcepcion {
		throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_AL3_NOT_SUPPORTED);
	}

	private CuadernoBase pagoCuaderno(
			CuadernoBase poCuaderno,CuadernoBase cuadernoRespuesta) throws PagoElectronicoExcepcion {
		try{
			// PAS0 1: ---- Componer los objetos de pago
			PaymentRequestData paymentRequestData = new PaymentRequestData();       
			paymentRequestData.peticionesPago = new java.util.HashMap();
			
			PeticionPago peticionPago = generaPeticionPago(poCuaderno);
			paymentRequestData.peticionesPago.put(peticionPago.id,peticionPago);    
			
			// PAS0 2: ---- Enviar la petición de pago al Gestor de Pagos utilizando el API
			// Instanciar el API y llamar al método initializePayment
			P12DPaymentManagerAPI paymentAPI  = P12DPaymentFactoryAPI.getPaymentManagerAPIByOID("paymentManagerBZDByRPC");			
			OperationResult opResult = paymentAPI.initializePayment(paymentRequestData);
			
			// Obtener la respuesta del Gestor de Pagos encapsulada en el objeto operationResult
			if(opResult.resultado.resultadoOK) {
				cuadernoRespuesta.setPeticionPagoPasarelaExternaConRedireccion(opResult.resultado.returnValue);
			} 
			return cuadernoRespuesta;

		}catch(Exception e){
			Logger.getLogger(this.getClass()).error(e);
			throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_OPERACION_PAGO);
		}	
	}
	
	public Cuaderno60Modalidad3 pagoCuaderno60Modalidad3(
			Cuaderno60Modalidad3 poCuaderno) throws PagoElectronicoExcepcion {
		throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_PAGO_AL3_NOT_SUPPORTED);
	}
	
	public Cuaderno57 pagoCuaderno57(Cuaderno57 poCuaderno)
			throws PagoElectronicoExcepcion {
		Cuaderno57 cuadernoRespuesta=new Cuaderno57();
		poCuaderno.setOrganismoEmisor(poCuaderno.getOrganismoEmisor()+"-"+poCuaderno.getCodTributo());
		return (Cuaderno57)pagoCuaderno(poCuaderno,cuadernoRespuesta);
	}
	
	public Cuaderno60Modalidad1_2 pagoCuaderno60Modalidad1_2(
			Cuaderno60Modalidad1_2 poCuaderno) throws PagoElectronicoExcepcion {
		Cuaderno60Modalidad1_2 cuadernoRespuesta=new Cuaderno60Modalidad1_2();
		return (Cuaderno60Modalidad1_2)pagoCuaderno(poCuaderno,cuadernoRespuesta);
	}

	private PeticionPago generaPeticionPago(CuadernoBase cuaderno){
		PeticionPago peticionPago = new PeticionPago();
			
		/*****************  DATOS PETICION PAGO ************************* */
		p12f.exe.pasarelapagos.objects.DatosPago datosPeticionPago 
			= new p12f.exe.pasarelapagos.objects.DatosPago();
		datosPeticionPago.cpr     = ConfiguracionComun.obtenerCPRModalidad(cuaderno.getTipo());
		datosPeticionPago.formato = datosPeticionPago.cpr.substring(2,5);
			datosPeticionPago.emisor =  cuaderno.getOrganismoEmisor();
				
		datosPeticionPago.validar = 1;
			datosPeticionPago.tipo = cuaderno.getCodTributo();
		datosPeticionPago.referencia=cuaderno.getReferencia();
		
		/***************** PERIODOS PETICION DE PAGO *****************/
		datosPeticionPago.periodosPago = new HashMap();
		PeriodoPago periodoPago = new PeriodoPago();
		periodoPago.id = PeriodoPago.PERIODO_NORMAL;
		periodoPago.fechaFin =    cuaderno.getFecCaducidad();
		periodoPago.fechaInicio = cuaderno.getFechaIncio();
		periodoPago.importe = Long.parseLong(cuaderno.getImporte());

		periodoPago.identificacion = cuaderno.getIdentificacion();
		periodoPago.activo = true;      
		periodoPago.validarFechaFin = true;
		datosPeticionPago.periodosPago.put(periodoPago.id,periodoPago);		
		
		/***************** CONCEPTOS PETICION DE PAGO *****************/			
		peticionPago.conceptos = getConceptosPeticionPago(cuaderno);			
		
		/**************  DESCRIPCION PETICION DE PAGO **************************/
		Map descripciones = new HashMap();
		descripciones.put("es", cuaderno.getDescripcion());
		//descripciones.put("eu", "EU Pago de prueba Cuaderno 60 modalidad 2 Corto");     
		peticionPago.descripcion = descripciones;
		
		peticionPago.id = datosPeticionPago.cpr + 
			datosPeticionPago.emisor +
			datosPeticionPago.referencia;
		peticionPago.datosPago = datosPeticionPago;	
		
		return peticionPago;
	}		
		
	/** 
	* Funcion _getConceptosPeticionPago ()
	* Funcion que devuelve un MAP de dos Conceptos de Pago para el siguiente ejemplo 
	*/	
	private java.util.List getConceptosPeticionPago(CuadernoBase cuaderno){
		java.util.List conceptosPeticionPagoMap = new ArrayList();
		
		ConceptoPeticion conceptoPeticionPago  = new ConceptoPeticion();
		conceptoPeticionPago.numeroLinea =1;
		conceptoPeticionPago.descripcion.put("es",cuaderno.getTextoConceptoPago());
		//conceptoPeticionPago.descripcion.put("eu","Tasa de prueba EU");
		conceptoPeticionPago.unidades=1;
		conceptoPeticionPago.baseImponible=Integer.parseInt(cuaderno.getImporte());
		conceptoPeticionPago.importeIVA=(long)(PORCENTAJE_IVA*conceptoPeticionPago.baseImponible);
		conceptoPeticionPago.importe =conceptoPeticionPago.baseImponible+conceptoPeticionPago.importeIVA;		
		conceptosPeticionPagoMap.add(conceptoPeticionPago);
		
		return	conceptosPeticionPagoMap;
	}

	public String getNRCByIdPago(String idPago) {
		String nrc=null; 
		try {
			RPCCall rpcCall = new RPCCall();
			RPCFunction rpcFuncion = new RPCFunction();
			rpcCall.setModule("IJ");
			rpcFuncion.setName("iniciarPago");
			rpcFuncion.putParameter(new RPCParameter("oids", "String", idPago));
			rpcFuncion.putParameter(new RPCParameter("idioma", "String", "es"));
			rpcFuncion.putParameter(new RPCParameter("CPR", "String", ""));
	    	rpcFuncion.putParameter(new RPCParameter("lotes", "String", "true"));
	    	rpcCall.addFunction(rpcFuncion);

	    	System.out.println("rpcCall=" + rpcCall.toXML());
	    
	    	String data = URLEncoder.encode("xmlRPC", "UTF-8") + "=" + URLEncoder.encode(rpcCall.toXML(), "UTF-8");
    	    data += "&" + URLEncoder.encode("function", "UTF-8") + "=" + URLEncoder.encode("iniciarPago", "UTF-8");

    	    String urlPasarela = XMLProperties.get("p12d", "urls/urlPasarela");
    	    // Send data
    	    URL url = new URL(urlPasarela);
    	    URLConnection conn = url.openConnection();
    	    
    	    conn.setDoOutput(true);
    	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    	    wr.write(data);
    	    wr.flush();

    	    // Get the response
    	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	    StringBuffer html=new StringBuffer();
    	    String line;
    	    while ((line = rd.readLine()) != null) {
    	    	html.append(line);
    	    }
    	    wr.close();
    	    rd.close();
    	    
    	    int posIni=html.toString().toLowerCase().lastIndexOf("<nrc>");
    	    int posFin=html.toString().toLowerCase().lastIndexOf("</nrc>");
    	    nrc=html.toString().substring(posIni+5,posFin);
    	} catch (Throwable e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    	return nrc;
	}
}
