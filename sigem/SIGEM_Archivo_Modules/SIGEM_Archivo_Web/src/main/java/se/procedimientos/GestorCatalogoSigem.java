package se.procedimientos;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.TramitacionException;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.OrganoProductor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.procedimientos.archigest.GestorCatalogoArchigest;
import se.procedimientos.exceptions.GestorCatalogoException;

import common.MultiEntityConstants;
import common.exceptions.SistemaExternoException;

/**
 * Gestor de catálogo para Sigem
 */
public class GestorCatalogoSigem implements GestorCatalogo{

	/** Logger de la clase. */
	protected static final Logger logger = Logger.getLogger(GestorCatalogoArchigest.class);

	/**
	 * Entidad
	 */
	private String entity=null;

	/**
	 * Constructor.
	 */
	public GestorCatalogoSigem()
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
			logger.error("Error en la creaci\u00F3n del proxy del Cat\u00E1logo", e);
			throw new GestorCatalogoException(e);
		}
	}

	public se.procedimientos.InfoBProcedimiento transformInfoB(InfoBProcedimiento obj) {
		InfoBProcedimientoImpl info = null;
		if (obj != null) {
			InfoBProcedimiento infoBProcedimiento =
				(InfoBProcedimiento) obj;
				
				info = new InfoBProcedimientoImpl();
				info.setCodigo(infoBProcedimiento.getCodigo());
				info.setCodSistProductor(infoBProcedimiento.getCodSistProductor());
				info.setId(infoBProcedimiento.getId());
				info.setNombre(infoBProcedimiento.getNombre());
				info.setNombreSistProductor(infoBProcedimiento.getNombreSistProductor());
		}
		return info;
	}
	
	public se.procedimientos.InfoBProcedimiento transformInfoB(ieci.tecdoc.sgm.tram.ws.client.dto.InfoBProcedimiento obj) {
		InfoBProcedimientoImpl info = null;
		if (obj != null) {
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoBProcedimiento infoBProcedimiento=
				(ieci.tecdoc.sgm.tram.ws.client.dto.InfoBProcedimiento) obj;
				
				info = new InfoBProcedimientoImpl();
				info.setCodigo(infoBProcedimiento.getCodigo());
				info.setCodSistProductor(infoBProcedimiento.getCodSistProductor());
				info.setId(infoBProcedimiento.getId());
				info.setNombre(infoBProcedimiento.getNombre());
				info.setNombreSistProductor(infoBProcedimiento.getNombreSistProductor());
		}
		return info;
	}
	/**
	 * Transforma una lista de organos productores procedentes del API en una lista de objetos
	 * que implemementan el interfaz de organos productores
	 * 
	 * @param organosProductores Organos productores procedentes del API
	 * @return Lista de organos productores que implementan el interfaz
	 */
	public List transform(OrganoProductor [] organosProductores) {
		List ltOrganosProductores = new ArrayList();
		if (organosProductores != null) {
			for (int i=0; i<organosProductores.length; i++){
				OrganoProductor organoProductor = (OrganoProductor) organosProductores[i];
				OrganoProductorImpl organoProductorImpl = new OrganoProductorImpl(organoProductor.getId(), organoProductor.getInicioProduccion());
				ltOrganosProductores.add(organoProductorImpl);
			}
		}
		return ltOrganosProductores;
	}
	
	/**
	 * Transforma una lista de organos productores procedentes del WS en una lista de objetos
	 * que implemementan el interfaz de organos productores
	 * 
	 * @param organosProductores Organos productores procedentes del WS
	 * @return Lista de organos productores que implementan el interfaz
	 */	
	public List transform(ieci.tecdoc.sgm.tram.ws.client.dto.OrganoProductor [] organosProductores) {
		List ltOrganosProductores = new ArrayList();
		if (organosProductores != null) {
			for (int i=0; i<organosProductores.length; i++){
				ieci.tecdoc.sgm.tram.ws.client.dto.OrganoProductor organoProductor = (ieci.tecdoc.sgm.tram.ws.client.dto.OrganoProductor) organosProductores[i];
				OrganoProductorImpl organoProductorImpl = new OrganoProductorImpl(organoProductor.getId(), organoProductor.getInicioProduccion());
				ltOrganosProductores.add(organoProductorImpl);
			}
		}
		return ltOrganosProductores;
	}

	/**
	 * Transforma una lista de información básica de procedimientos procedentes del WS o del API en una lista de objetos
	 * que implemementan el interfaz de información básica de procedimientos
	 * @param list Lista de información básica de procedimientos procedentes del WS o del API
	 * @return lista de información básica de procedimientos que implemementan el interfaz de información básica de procedimientos
	 */
	public List transformInfoB(Object [] arrayObj)
	{
		List result = new ArrayList();
		if (arrayObj!=null){
			for (int i=0; i < arrayObj.length; i++) {
				Object obj=arrayObj[i];
				if(obj instanceof InfoBProcedimiento) //API
				{
					InfoBProcedimiento infoBProcedimiento=(InfoBProcedimiento) obj;
					result.add(transformInfoB(infoBProcedimiento));
				}
				else if(obj instanceof ieci.tecdoc.sgm.tram.ws.client.dto.InfoBProcedimiento) //WS
				{
					ieci.tecdoc.sgm.tram.ws.client.dto.InfoBProcedimiento infoBProcedimiento=(ieci.tecdoc.sgm.tram.ws.client.dto.InfoBProcedimiento) obj;
					result.add(transformInfoB(infoBProcedimiento));
				}
			}
		}
		return result;
	}
	
	/**
	 * Recupera la lista de procedimientos del tipo que se indica en 
	 * tipoProc, con su información básica.
	 * @param tipoProc Tipo de procedimiento.
	 * <p>Valores posibles de tipoProc:
	 * <li>1 - Todos</li>
	 * <li>2 - Procedimientos  automatizados</li>
	 * <li>3 - Procedimientos no automatizados</li>
	 * </p>
	 * @param nombre Nombre del procedimiento.
	 * @return Lista de información de procedimientos.
	 * @throws GestorCatalogoException si ocurre algún error.
	 */
	public List recuperarInfoBProcedimientos(int tipoProc, String nombre) throws GestorCatalogoException
	{
		List list=new ArrayList();
		try
		{
			ServicioTramitacion oServicio = LocalizadorServicios.getServicioTramitacion();
			InfoBProcedimiento[] infoBProcs = oServicio.getProcedimientos(entity,tipoProc,nombre);
			if ((infoBProcs!=null)&&(infoBProcs.length>0))
				list=transformInfoB(infoBProcs);
			
/*			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				Object [] infoBProcs = servicio.getProcedimientos(entity,tipoProc,nombre);
				if ((infoBProcs!=null)&&(infoBProcs.length>0))
					list=transformInfoB(infoBProcs);
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				ListaInfoBProcedimientos listaInfoBProcedimientos=service.getProcedimientosPorTipo(entity,tipoProc,nombre);
				if(listaInfoBProcedimientos!=null)
				{
					Object [] infoBProcs = listaInfoBProcedimientos.getProcedimientos();
					if ((infoBProcs!=null)&&(infoBProcs.length>0))
						list=transformInfoB(infoBProcs);
				}
			}
		}*/
		} catch (TramitacionException e) {
			throw new GestorCatalogoException(e);
		} catch (SigemException e) {
			throw new GestorCatalogoException(e);
		} catch (Exception e) {
			throw new GestorCatalogoException(e);
		}
		return list;
	}
	
	
	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 * @throws GestorCatalogoException si ocurre algún error.
	 */
	public List recuperarInfoBProcedimientos(String[] idProcs) throws GestorCatalogoException
	{
		List list=new ArrayList();
		try
		{
			ServicioTramitacion oServicio = LocalizadorServicios.getServicioTramitacion();
			InfoBProcedimiento[] infoBProcs = oServicio.getProcedimientos(entity,idProcs);
			if ((infoBProcs!=null)&&(infoBProcs.length>0))
				list=transformInfoB(infoBProcs);
			
/*			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				Object [] infoBProcs = servicio.getProcedimientos(entity,idProcs);
				if ((infoBProcs!=null)&&(infoBProcs.length>0))
					list=transformInfoB(infoBProcs);
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				ListaInfoBProcedimientos listaInfoBProcedimientos=service.getProcedimientos(entity,idProcs);
				if(listaInfoBProcedimientos!=null)
				{
					Object [] infoBProcs = listaInfoBProcedimientos.getProcedimientos();
					if ((infoBProcs!=null)&&(infoBProcs.length>0))
						list=transformInfoB(infoBProcs);
				}
			}
		}*/
		} catch (TramitacionException e) {
			throw new GestorCatalogoException(e);
		} catch (SigemException e) {
			throw new GestorCatalogoException(e);
		} catch (Exception e) {
			throw new GestorCatalogoException(e);
		}
		
		return list;
	}

	
	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 * @throws GestorCatalogoException si ocurre algún error.
	 * @throws NotAvailableException si la funcionalidad no es aplicable.
	 */
	public IProcedimiento recuperarProcedimiento(String idProc) throws GestorCatalogoException
	{
		ProcedimientoImpl procedimiento = null;
		
		try
		{
			ServicioTramitacion oServicio = LocalizadorServicios.getServicioTramitacion();
			ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento proc = oServicio.getProcedimiento(entity,idProc);
			
			if (proc != null)
			{
				procedimiento = new ProcedimientoImpl();
				
				procedimiento.setInformacionBasica(transformInfoB(proc.getInformacionBasica()));
				procedimiento.setObjeto(proc.getObjeto());
				procedimiento.setTramites(proc.getTramites());
				procedimiento.setNormativa(proc.getNormativa());
				procedimiento.setDocumentosBasicos(proc.getDocumentosBasicos());
				procedimiento.setOrganosProductores(transform(proc.getOrganosProductores()));
			}
			
/*			if(ProcedimientoConstants.API.equalsIgnoreCase(mode))
			{
				SigemTramitacionServiceAdapter servicio=new SigemTramitacionServiceAdapter();
				ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento proc=servicio.getProcedimiento(entity,idProc);
				
				if (proc != null)
				{
					procedimiento = new ProcedimientoImpl();
					
					procedimiento.setInformacionBasica(transformInfoB(proc.getInformacionBasica()));
					procedimiento.setObjeto(proc.getObjeto());
					procedimiento.setTramites(proc.getTramites());
					procedimiento.setNormativa(proc.getNormativa());
					procedimiento.setDocumentosBasicos(proc.getDocumentosBasicos());
					procedimiento.setOrganosProductores(transform(proc.getOrganosProductores()));
				}
			}
			else
			{
				TramitacionWebServiceServiceLocator twsServiceLocator=new TramitacionWebServiceServiceLocator();
				twsServiceLocator.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service=twsServiceLocator.getTramitacionWebService();
				ieci.tecdoc.sgm.tram.ws.client.dto.Procedimiento proc=service.getProcedimiento(entity,idProc);
				
				if (proc != null)
				{
					procedimiento = new ProcedimientoImpl();
					procedimiento.setInformacionBasica(transformInfoB(proc.getInformacionBasica()));
					procedimiento.setObjeto(proc.getObjeto());
					procedimiento.setTramites(proc.getTramites());
					procedimiento.setNormativa(proc.getNormativa());
					procedimiento.setDocumentosBasicos(proc.getDocumentosBasicos());
					procedimiento.setOrganosProductores(transform(proc.getOrganosProductores()));
				}
			}
		}*/
		} catch (TramitacionException e) {
			throw new GestorCatalogoException(e);
		} catch (SigemException e) {
			throw new GestorCatalogoException(e);
		} catch (Exception e) {
			throw new GestorCatalogoException(e);
		}

		return procedimiento;
	}
}
