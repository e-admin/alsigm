package se.tramites;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.TramitacionException;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocElectronico;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocFisico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.tramites.archigest.SistemaTramitadorArchigest;
import se.tramites.exceptions.SistemaTramitadorException;
import util.CollectionUtils;

import common.MultiEntityConstants;
import common.exceptions.SistemaExternoException;

public class SistemaTramitadorSigem implements SistemaTramitador{

	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(SistemaTramitadorArchigest.class);

	/**
	 * Entidad
	 */	
	private String entity=null;

	/**
	 * Constructor.
	 */
	public SistemaTramitadorSigem()
	{
	}
	
	/**
	 * Inicializa con los parámetros de configuración.
	 * @param params Parámetros de configuración.
	 * @throws SistemaExternoException si ocurre algún error.
	 */
	public void initialize(Properties params) throws SistemaExternoException
	{
		try
		{
			entity = (String) params.get(MultiEntityConstants.ENTITY_PARAM);
		}
		catch (Exception e)
		{
			logger.error("Error en la creaci\u00F3n del proxy del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}
	}

	/**
	 * Transforma un objeto de información básica procedente de API o WS en un objeto que implementa
	 * el interfaz de información básica de un expediente
	 * @param infoBExp Objeto de información básica procedente de API o WS
	 * @return Objeto de información básica que implementa el interfaz de información básica de un expediente
	 */
	public se.tramites.InfoBExpediente transformInfoB(Object infoBExp)
	{
		if (infoBExp!=null) {
			if(infoBExp instanceof se.tramites.InfoBExpediente) 
				return (se.tramites.InfoBExpediente)infoBExp;
			
			else if(infoBExp instanceof ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente)
			{
				ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente expediente=
				(ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente)infoBExp;
				se.tramites.InfoBExpedienteImpl infoBExpedienteImpl=new se.tramites.InfoBExpedienteImpl();
				infoBExpedienteImpl.setDatosIdentificativos(expediente.getDatosIdentificativos());
				infoBExpedienteImpl.setId(expediente.getId());
				infoBExpedienteImpl.setNumExp(expediente.getNumExp());
				return infoBExpedienteImpl;
			} 
			else if(infoBExp instanceof ieci.tecdoc.sgm.tram.ws.client.dto.InfoBExpediente)
			{
				ieci.tecdoc.sgm.tram.ws.client.dto.InfoBExpediente expediente=
				(ieci.tecdoc.sgm.tram.ws.client.dto.InfoBExpediente) infoBExp;
				se.tramites.InfoBExpedienteImpl infoBExpedienteImpl=new se.tramites.InfoBExpedienteImpl();
				infoBExpedienteImpl.setDatosIdentificativos(expediente.getDatosIdentificativos());
				infoBExpedienteImpl.setId(expediente.getId());
				infoBExpedienteImpl.setNumExp(expediente.getNumExp());
				return infoBExpedienteImpl;
			}
		}		
		
		return null;	
	}
	
	public se.tramites.DocFisico transformDocFisico(Object docFisico){
		
		if (docFisico!=null){
			if(docFisico instanceof se.tramites.DocFisico) 
				return (se.tramites.DocFisico)docFisico;
			
			else if(docFisico instanceof DocFisico)
			{
				DocFisico doc = (DocFisico)docFisico;
				DocFisicoImpl docFisicoImpl = new DocFisicoImpl();
				docFisicoImpl.setAsunto(doc.getAsunto());
				docFisicoImpl.setTipoDocumento(doc.getTipoDocumento());
				return docFisicoImpl;
			} 
			else if(docFisico instanceof ieci.tecdoc.sgm.tram.ws.client.dto.DocFisico)
			{
				ieci.tecdoc.sgm.tram.ws.client.dto.DocFisico doc = (ieci.tecdoc.sgm.tram.ws.client.dto.DocFisico)docFisico;
				DocFisicoImpl docFisicoImpl = new DocFisicoImpl();
				docFisicoImpl.setAsunto(doc.getAsunto());
				docFisicoImpl.setTipoDocumento(doc.getTipoDocumento());
				return docFisicoImpl;
			}
		}
		
		return null;	
	}
	
	public se.tramites.DocElectronico transformDocElectronico(Object docElectronico){
		
		if(docElectronico != null){
			if(docElectronico instanceof se.tramites.DocElectronico) 
				return (se.tramites.DocElectronico)docElectronico;
			
			else if(docElectronico instanceof DocElectronico)
			{
				DocElectronico doc = (DocElectronico)docElectronico;
				DocElectronicoImpl docElectronicoImpl = new DocElectronicoImpl();
				docElectronicoImpl.setAsunto(doc.getAsunto());
				docElectronicoImpl.setTipoDocumento(doc.getTipoDocumento());
				docElectronicoImpl.setExtension(doc.getExtension());
				docElectronicoImpl.setLocalizador(doc.getLocalizador());
				docElectronicoImpl.setRepositorio(doc.getRepositorio());
				return docElectronicoImpl;
			} 
			else if(docElectronico instanceof ieci.tecdoc.sgm.tram.ws.client.dto.DocElectronico)
			{
				ieci.tecdoc.sgm.tram.ws.client.dto.DocElectronico doc = (ieci.tecdoc.sgm.tram.ws.client.dto.DocElectronico)docElectronico;
				DocElectronicoImpl docElectronicoImpl = new DocElectronicoImpl();
				docElectronicoImpl.setAsunto(doc.getAsunto());
				docElectronicoImpl.setTipoDocumento(doc.getTipoDocumento());
				docElectronicoImpl.setExtension(doc.getExtension());
				docElectronicoImpl.setLocalizador(doc.getLocalizador());
				docElectronicoImpl.setRepositorio(doc.getRepositorio());
				return docElectronicoImpl;
			}
		}

		return null;	
	}
	
	public se.tramites.Interesado transformInteresado(Object interesado){
		
		if (interesado != null) {
			if(interesado instanceof se.tramites.Interesado) 
				return (se.tramites.Interesado)interesado;
			
			else if(interesado instanceof ieci.tecdoc.sgm.core.services.tramitacion.dto.Interesado)
			{
				ieci.tecdoc.sgm.core.services.tramitacion.dto.Interesado inter = (ieci.tecdoc.sgm.core.services.tramitacion.dto.Interesado) interesado;
				InteresadoImpl interesadoImpl = new InteresadoImpl();
				interesadoImpl.setIdEnTerceros(inter.getIdEnTerceros());
				interesadoImpl.setInteresadoPrincipal(inter.isInteresadoPrincipal());
				interesadoImpl.setNombre(inter.getNombre());
				interesadoImpl.setNumIdentidad(inter.getNumIdentidad());
				interesadoImpl.setRol(inter.getRol());
				return interesadoImpl;
			} 
			else if(interesado instanceof ieci.tecdoc.sgm.tram.ws.client.dto.Interesado)
			{
				ieci.tecdoc.sgm.tram.ws.client.dto.Interesado inter = (ieci.tecdoc.sgm.tram.ws.client.dto.Interesado) interesado;
				InteresadoImpl interesadoImpl = new InteresadoImpl();
				interesadoImpl.setIdEnTerceros(inter.getIdEnTerceros());
				interesadoImpl.setInteresadoPrincipal(inter.isInteresadoPrincipal());
				interesadoImpl.setNombre(inter.getNombre());
				interesadoImpl.setNumIdentidad(inter.getNumIdentidad());
				interesadoImpl.setRol(inter.getRol());
				return interesadoImpl;
			}
		}
		
		return null;	
	}
	
	public se.tramites.Emplazamiento transformEmplazamiento(Object emplazamiento){
		
		if (emplazamiento != null) {
			if(emplazamiento instanceof se.tramites.Emplazamiento) 
				return (se.tramites.Emplazamiento)emplazamiento;
			
			else if(emplazamiento instanceof ieci.tecdoc.sgm.core.services.tramitacion.dto.Emplazamiento)
			{
				ieci.tecdoc.sgm.core.services.tramitacion.dto.Emplazamiento empl = (ieci.tecdoc.sgm.core.services.tramitacion.dto.Emplazamiento) emplazamiento;
				EmplazamientoImpl emplazamientoImpl = new EmplazamientoImpl();
				emplazamientoImpl.setComunidad(empl.getComunidad());
				emplazamientoImpl.setConcejo(empl.getConcejo());
				emplazamientoImpl.setLocalizacion(empl.getLocalizacion());
				emplazamientoImpl.setPais(empl.getPais());
				emplazamientoImpl.setPoblacion(empl.getPoblacion());
				return emplazamientoImpl;
			} 
			else if(emplazamiento instanceof ieci.tecdoc.sgm.tram.ws.client.dto.Interesado)
			{
				ieci.tecdoc.sgm.tram.ws.client.dto.Emplazamiento empl = (ieci.tecdoc.sgm.tram.ws.client.dto.Emplazamiento) emplazamiento;
				EmplazamientoImpl emplazamientoImpl = new EmplazamientoImpl();
				emplazamientoImpl.setComunidad(empl.getComunidad());
				emplazamientoImpl.setConcejo(empl.getConcejo());
				emplazamientoImpl.setLocalizacion(empl.getLocalizacion());
				emplazamientoImpl.setPais(empl.getPais());
				emplazamientoImpl.setPoblacion(empl.getPoblacion());
				return emplazamientoImpl;
			}
		}
		
		return null;	
	}

	public List transformInfoB(Object [] infoBExps) {
		List ltInfoBExpedientes = new ArrayList();
		if (infoBExps != null) {
			for (int i=0; i<infoBExps.length; i++){
				ltInfoBExpedientes.add(transformInfoB(infoBExps[i]));
			}
		}
		return ltInfoBExpedientes;
	}
	
	public List transformDocsFisicos(Object [] docFisicos) {
		List ltDocFisicos = new ArrayList();
		if (docFisicos != null) {
			for (int i=0; i<docFisicos.length; i++){
				ltDocFisicos.add(transformDocFisico(docFisicos[i]));
			}
		}
		return ltDocFisicos;
	}
	
	public List transformDocsElectronicos(Object [] docElectronicos) {
		List ltDocElectronicos = new ArrayList();
		if (docElectronicos != null) {
			for (int i=0; i<docElectronicos.length; i++){
				ltDocElectronicos.add(transformDocElectronico(docElectronicos[i]));
			}
		}
		return ltDocElectronicos;
	}

	public List transformInteresados(Object [] interesados) {
		List ltInteresados = new ArrayList();
		if (interesados != null) {
			for (int i=0; i<interesados.length; i++){
				ltInteresados.add(transformInteresado(interesados[i]));
			}
		}
		return ltInteresados;
	}
	
	public List transformEmplazamientos(Object [] emplazamientos) {
		List ltEmplazamientos = new ArrayList();
		if (emplazamientos != null) {
			for (int i=0; i<emplazamientos.length; i++){
				ltEmplazamientos.add(transformEmplazamiento(emplazamientos[i]));
			}
		}
		return ltEmplazamientos;
	}
	
	/**
	 * Recupera los identificadores de los expedientes,  del procedimiento identificado 
	 * por idProc, que hayan finalizado en el rango de fechas comprendido entre fechaIni
	 * y fechaFin ordenados por lo que indique el parámetro tipoOrd.
	 * @param idProc Identificador del procedimiento.
	 * @param fechaIni Fecha de inicio.
	 * @param fechaFin Fecha de fin.
	 * @param tipoOrd Tipo de ordenación.
	 * <p>Valores posibles:
	 * <li>1 - Número de expediente</li>
	 * <li>2 - Fecha finalización</li>
	 * </p>
	 * @return Lista de identificadores de expedientes.
	 * @throws SistemaTramitadorException si ocurre algún error.
	 */
	public List recuperarIdsExpedientes(String idProc, Date fechaIni, Date fechaFin, int tipoOrd)
		throws SistemaTramitadorException
	{
		
		List list=new ArrayList();
		try
		{
			ServicioTramitacion oServicio = LocalizadorServicios.getServicioTramitacion();
			String [] ids =  oServicio.getIdsExpedientes(entity, idProc, fechaIni, fechaFin, tipoOrd);

			if ((ids!=null)&&(ids.length>0))
				list=CollectionUtils.createList(ids);
			
			
/*			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				String [] ids = servicio.getIdsExpedientes(entity, idProc, fechaIni, fechaFin, tipoOrd);
				if ((ids!=null)&&(ids.length>0))
					list=CollectionUtils.createList(ids);
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				ListaIdentificadores listaIdentificadores=service.getIdsExpedientes(entity, idProc, fechaIni, fechaFin, tipoOrd);
				if(listaIdentificadores!=null)
				{
					String [] ids = listaIdentificadores.getIdentificadores();
					if ((ids!=null)&&(ids.length>0))
						list=CollectionUtils.createList(ids);
				}
				
			}*/
		} catch (TramitacionException e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (SigemException e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}
		
		return list;
	}

	
	
	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en 
	 * el parámetro idExps.
	 * @param idExps Identificadores de expedientes.
	 * @return Lista de expedientes.
	 * <p>Los objetos de la lista tienen que implementar el 
	 * interface {@link InfoBExpediente}.</p>
	 * @throws SistemaTramitadorException si ocurre algún error.
	 * @throws NotAvailableException si la funcionalidad no es aplicable.
	 */
	public List  recuperarInfoBExpedientes(String[] idExps) throws SistemaTramitadorException
	{
	
		List list=new ArrayList();
		try
		{

			ServicioTramitacion oServicio = LocalizadorServicios.getServicioTramitacion();
			Object [] infoBExps = oServicio.getExpedientes(entity,idExps);

			if ((infoBExps!=null)&&(infoBExps.length>0))
				list=transformInfoB(infoBExps);
			
/*			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				Object [] infoBExps = servicio.getExpedientes(entity,idExps);

			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				ListaInfoBExpedientes listaInfoBExpedientes = service.getExpedientes(entity,idExps);
				if(listaInfoBExpedientes!=null)
				{
					Object [] infoBExps = listaInfoBExpedientes.getExpedientes();
					if ((infoBExps!=null)&&(infoBExps.length>0))
						list=transformInfoB(infoBExps);
				}
				
			}*/
		} catch (TramitacionException e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (SigemException e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}
		
		return list;
	}
	
	
	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 * @throws SistemaTramitadorException si ocurre algún error.
	 * @throws NotAvailableException si la funcionalidad no es aplicable.
	 */
	public Expediente recuperarExpediente(String idExp) throws SistemaTramitadorException
	{
			
		ExpedienteImpl expediente = null;
		
		try
		{

			ServicioTramitacion oServicio = LocalizadorServicios.getServicioTramitacion();
			ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente exp = oServicio.getExpediente(entity,idExp);
			
			if (exp != null)
			{
				expediente = new ExpedienteImpl();
				expediente.setFechaInicio(exp.getFechaInicio());
				expediente.setFechaFin(exp.getFechaFinalizacion());
				expediente.setAsunto(exp.getAsunto());
				expediente.setOrganoProductor(exp.getIdOrgProductor(), exp.getNombreOrgProductor());
				expediente.setDocumentosFisicos(transformDocsFisicos(exp.getDocumentosFisicos()));
				expediente.setDocumentosElectronicos(transformDocsElectronicos(exp.getDocumentosElectronicos()));
				expediente.setInteresados(transformInteresados(exp.getInteresados()));
				expediente.setEmplazamientos(transformEmplazamientos(exp.getEmplazamientos()));
				expediente.setInfoExpediente(transformInfoB(exp.getInformacionBasica()));
			}
			
/*			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente exp=servicio.getExpediente(entity,idExp);
				
				
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				ieci.tecdoc.sgm.tram.ws.client.dto.Expediente exp=service.getExpediente(entity,idExp);
				
				if (exp != null)
				{
					expediente = new ExpedienteImpl();
					expediente.setFechaInicio(exp.getFechaInicio());
					expediente.setFechaFin(exp.getFechaFinalizacion());
					expediente.setAsunto(exp.getAsunto());
					expediente.setOrganoProductor(exp.getIdOrgProductor(), exp.getNombreOrgProductor());
					expediente.setDocumentosFisicos(transformDocsFisicos(exp.getDocumentosFisicos()));
					expediente.setDocumentosElectronicos(transformDocsElectronicos(exp.getDocumentosElectronicos()));
					expediente.setInteresados(transformInteresados(exp.getInteresados()));
					expediente.setEmplazamientos(transformEmplazamientos(exp.getEmplazamientos()));
					expediente.setInfoExpediente(transformInfoB(exp.getInformacionBasica()));
				}
			}
		}*/
		} catch (TramitacionException e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (SigemException e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}
		
		return expediente;
	}
	
	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado archivado
	 * @param idExps  Array de String con los expedientes que se quieren pasar al estado archivado
	 * @throws SistemaTramitadorException si ocurre algún error.
	 * @throws NotAvailableException si la funcionalidad no es aplicable.
	 */
	public void archivarExpedientes(String[] idExps) throws SistemaTramitadorException, NotAvailableException {
		
		try
		{

			ServicioTramitacion oServicio = LocalizadorServicios.getServicioTramitacion();
			oServicio.archivarExpedientes(entity, idExps);
			
			
		} catch (TramitacionException e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (SigemException e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio web del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}

	}
}
