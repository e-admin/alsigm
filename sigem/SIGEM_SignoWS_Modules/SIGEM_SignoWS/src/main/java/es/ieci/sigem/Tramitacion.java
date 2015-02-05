package es.ieci.sigem;

import ieci.tecdoc.sgm.tram.ws.client.dto.Cadena;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.Document;
import ieci.tecdoc.sgm.registropresencial.ws.client.axis.RegisterInfo;
import ieci.tecdoc.sgm.tram.ws.client.dto.Booleano;
import ieci.tecdoc.sgm.tram.ws.client.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.tram.ws.client.dto.Expediente;
import ieci.tecdoc.sgm.tram.ws.client.dto.InteresadoExpediente;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.tram.ws.client.TramitacionWebServiceServiceLocator;
import ieci.tecdoc.sgm.tram.ws.client.TramitacionWebServiceSoapBindingStub;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;

import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.Sujeto;

public class Tramitacion
{
	public static final Logger logger = Logger.getLogger(Tramitacion.class);
	
	private TramitacionWebServiceSoapBindingStub servicio;
	
	private String endpoint;
	
	public Tramitacion(String endpoint)
	{
		setEndpoint(endpoint);
	}
	
	private TramitacionWebServiceSoapBindingStub getServicio() throws AxisFault, MalformedURLException
	{
		if (servicio == null)
		{
			servicio = creaServicio(getEndpoint());
		}
		
		return servicio;
	}
	
	private TramitacionWebServiceSoapBindingStub creaServicio(String endpoint) throws AxisFault, MalformedURLException
	{
		return new TramitacionWebServiceSoapBindingStub(new URL(endpoint), new TramitacionWebServiceServiceLocator());		
	}
	
	public Cadena crearExpediente(String idEntidad, String tipoAsunto, String datosEspecificos, String numRegistro, Date fecRegistro,
			Sujeto sujeto, Sujeto[] representantes, ArrayList<DocumentoExpediente> documentos)
	{
		Cadena retorno = null;
		
		try
		{
			// Datos comunes
			DatosComunesExpediente datosComunes = new DatosComunesExpediente();		
			datosComunes.setNumeroRegistro(numRegistro);		
			datosComunes.setIdOrganismo(idEntidad);
			datosComunes.setTipoAsunto(tipoAsunto);
			
			Calendar c = Calendar.getInstance();
			c.setTime(fecRegistro);
			datosComunes.setFechaRegistro(c.getTime());
			
			// Lista de interesados
			ArrayList<InteresadoExpediente> interesados = new ArrayList<InteresadoExpediente>();
			
			// Añadimos el interesado principal
			InteresadoExpediente interesado = new InteresadoExpediente();
			interesado.setIndPrincipal("S");
			interesado.setName(sujeto.getNombreCompleto());
			interesado.setNifcif(sujeto.getNif());
			interesado.setPostalAddress(sujeto.getCalle());
			interesado.setPlaceCity(sujeto.getPoblacion());
			interesado.setPostalCode(sujeto.getCp());
			
			interesados.add(interesado);
			
			// Añadimos los representantes
			for (int i = 0; i < representantes.length; i++)
			{
				Sujeto s = representantes[i];
				
				interesado = new InteresadoExpediente();
				interesado.setIndPrincipal("N");
				interesado.setName(s.getNombreCompleto());
				interesado.setNifcif(s.getNif());
				interesado.setPostalAddress(s.getCalle());
				interesado.setPlaceCity(s.getPoblacion());
				interesado.setPostalCode(s.getCp());
				
				interesados.add(interesado);
			}
			
			datosComunes.setInteresados(interesados.toArray(new InteresadoExpediente[]{}));
			
			retorno = getServicio().crearExpediente(idEntidad, datosComunes, datosEspecificos, documentos.toArray(new DocumentoExpediente[]{}), "");
		}
		catch (Exception e)
		{
			retorno = new Cadena();
			
			retorno.setReturnCode("ERROR");
			retorno.setErrorCode("1810010000");
			
			logger.error(e.getMessage(), e);
		}
		
		return retorno;
	}
	
	public boolean iniciarExpediente(String idEntidad, String tipoAsunto, String datosEspecificos, RegisterInfo registro, Adquiriente adquiriente,
			String tipoDocumentos, Document[] documentosSolicitud)
	{
		try
		{
			// Datos comunes
			DatosComunesExpediente datosComunes = new DatosComunesExpediente();		
			datosComunes.setNumeroRegistro(registro.getNumber());		
			datosComunes.setIdOrganismo(idEntidad);
			datosComunes.setTipoAsunto(tipoAsunto);
			
			Calendar c = Calendar.getInstance();
			c.setTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(registro.getDate()));
			datosComunes.setFechaRegistro(c.getTime());
			
			ArrayList<InteresadoExpediente> interesados = new ArrayList<InteresadoExpediente>();
			
			InteresadoExpediente interesado = new InteresadoExpediente();
			interesado.setIndPrincipal("S");
			interesado.setName(adquiriente.getNombreCompleto());
			interesado.setNifcif(adquiriente.getNif());
			interesado.setPostalAddress(adquiriente.getCalle());
			interesado.setPlaceCity(adquiriente.getPoblacion());
			interesado.setPostalCode(adquiriente.getCp());
			
			interesados.add(interesado);
			
			datosComunes.setInteresados(interesados.toArray(new InteresadoExpediente[]{}));
			
			ArrayList<DocumentoExpediente> documentos = new ArrayList<DocumentoExpediente>();
			
			for (int i = 0; i < documentosSolicitud.length; i++)
			{
				Document docSolicitud = documentosSolicitud[i];
				
				DocumentoExpediente documento = new DocumentoExpediente();
				documento.setName(docSolicitud.getDocumentName());
				documento.setExtension(docSolicitud.getExtension());
				documento.setCode(tipoDocumentos);
				documento.setContent(docSolicitud.getDocumentContentB64().getBytes());
				//documento.setLenght(docSolicitud.getDocumentContentB64().length);
				//documento.setContent(docSolicitud.getDocumentContentB64());
				
				documentos.add(documento);
			}
			
			Booleano b = getServicio().iniciarExpediente(idEntidad, datosComunes, datosEspecificos, documentos.toArray(new DocumentoExpediente[]{}));
			
			if (b.getReturnCode().equals("OK"))
			{
				return true;
			}
			else
			{
				logger.error("Error iniciando expediente. ErrorCode:" + b.getErrorCode());
				
				return false;
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			return false;
		}
	}
	
	public Expediente getExpediente(String idEntidad, String numExp)
	{
		Expediente expediente = null;
		
		try
		{
			expediente = getServicio().getExpediente(idEntidad, numExp);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return expediente;		
	}
	
	public boolean moverExpedienteAFase(String idEntidad, String numExp, String idFaseCatalogo) throws Exception
	{
		Booleano b = getServicio().moverExpedienteAFase(idEntidad, numExp, idFaseCatalogo);
		
		return b.isValor();
	}
	
	public boolean archivarExpediente(String idEntidad, String numExp) throws Exception
	{		
		RetornoServicio rs = getServicio().archivarExpedientes(idEntidad, new String[]{numExp});
		
		System.out.println(rs);
		
		return true;
	}
	
	public boolean anexarDocsExpediente(String idEntidad, String numExp, byte[] content, String name, String extension, String code)
	 throws Exception
	{		
		DocumentoExpediente de = new DocumentoExpediente();
		de.setCode(code);
		de.setContent(content);
		de.setExtension(extension);
		de.setLenght(content.length);
		de.setName(name);
		
		try{
			Booleano b = getServicio().anexarDocsExpediente(idEntidad, numExp, "", null, new DocumentoExpediente[]{de});
			
			if (b.getReturnCode().equals("OK"))
			{
				return true;
			}
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage(), e);
		}
		
		return false;		
	}
	
	public String getEndpoint()
	{
		return endpoint;
	}

	public void setEndpoint(String endpoint)
	{
		this.endpoint = endpoint;
	}
}
