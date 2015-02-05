package se.tramites.ispac;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.services.ServicioTramitacionAdapter;
import ieci.tdw.ispac.services.dto.InfoBExpediente;
import ieci.tdw.ispac.services.ws.client.TramitacionWebService;
import ieci.tdw.ispac.services.ws.client.TramitacionWebServiceServiceLocator;
import ieci.tdw.ispac.services.ws.client.dto.ListaIdentificadores;
import ieci.tdw.ispac.services.ws.client.dto.ListaInfoBExpedientes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.procedimientos.ProcedimientoConstants;
import se.tramites.DocElectronicoImpl;
import se.tramites.DocFisicoImpl;
import se.tramites.EmplazamientoImpl;
import se.tramites.Expediente;
import se.tramites.ExpedienteImpl;
import se.tramites.InteresadoImpl;
import se.tramites.SistemaTramitador;
import se.tramites.exceptions.SistemaTramitadorException;
import util.CollectionUtils;

import common.exceptions.SistemaExternoException;

public class SistemaTramitadorIspac implements SistemaTramitador {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(SistemaTramitadorIspac.class);

	/** Constante que indica el modo de acceder a los procedimientos (API, WS) **/
	private static final String MODE = "MODE";

	/**
	 * Modo de acceso
	 */
	private String mode = null;

	/** Constante para la localización del WSDL del Servicio Web. */
	private static final String WSDL_LOCATION = "WSDL_LOCATION";

	/**
	 * Localización del WDSL
	 */
	private String wsdlLocation = null;

	/**
	 * Constructor.
	 */
	public SistemaTramitadorIspac() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws SistemaExternoException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params) throws SistemaExternoException {
		try {
			mode = params.getProperty(MODE);
			if (mode == null)
				mode = ProcedimientoConstants.API;
			if (ProcedimientoConstants.WS.equalsIgnoreCase(mode))
				wsdlLocation = params.getProperty(WSDL_LOCATION);
		} catch (Exception e) {
			logger.error("Error en la creaci\u00F3n del proxy del Tramitador",
					e);
			throw new SistemaTramitadorException(e);
		}
	}

	/**
	 * Transforma un objeto de información básica procedente de API o WS en un
	 * objeto que implementa el interfaz de información básica de un expediente
	 * 
	 * @param infoBExp
	 *            Objeto de información básica procedente de API o WS
	 * @return Objeto de información básica que implementa el interfaz de
	 *         información básica de un expediente
	 */
	public se.tramites.InfoBExpediente transformInfoB(Object infoBExp) {
		if (infoBExp != null) {
			if (infoBExp instanceof se.tramites.InfoBExpediente)
				return (se.tramites.InfoBExpediente) infoBExp;
			else if (infoBExp instanceof ieci.tdw.ispac.services.dto.InfoBExpediente) {
				ieci.tdw.ispac.services.dto.InfoBExpediente expediente = (ieci.tdw.ispac.services.dto.InfoBExpediente) infoBExp;
				se.tramites.InfoBExpedienteImpl infoBExpedienteImpl = new se.tramites.InfoBExpedienteImpl();
				infoBExpedienteImpl.setDatosIdentificativos(expediente
						.getDatosIdentificativos());
				infoBExpedienteImpl.setId(expediente.getId());
				infoBExpedienteImpl.setNumExp(expediente.getNumExp());
				return infoBExpedienteImpl;
			} else if (infoBExp instanceof ieci.tdw.ispac.services.ws.client.dto.InfoBExpediente) {
				ieci.tdw.ispac.services.ws.client.dto.InfoBExpediente expediente = (ieci.tdw.ispac.services.ws.client.dto.InfoBExpediente) infoBExp;
				se.tramites.InfoBExpedienteImpl infoBExpedienteImpl = new se.tramites.InfoBExpedienteImpl();
				infoBExpedienteImpl.setDatosIdentificativos(expediente
						.getDatosIdentificativos());
				infoBExpedienteImpl.setId(expediente.getId());
				infoBExpedienteImpl.setNumExp(expediente.getNumExp());
				return infoBExpedienteImpl;
			}
		}

		return null;
	}

	public se.tramites.DocFisico transformDocFisico(Object docFisico) {

		if (docFisico != null) {
			if (docFisico instanceof se.tramites.DocFisico)
				return (se.tramites.DocFisico) docFisico;
			else if (docFisico instanceof ieci.tdw.ispac.services.dto.DocFisico) {
				ieci.tdw.ispac.services.dto.DocFisico doc = (ieci.tdw.ispac.services.dto.DocFisico) docFisico;
				DocFisicoImpl docFisicoImpl = new DocFisicoImpl();
				docFisicoImpl.setAsunto(doc.getAsunto());
				docFisicoImpl.setTipoDocumento(doc.getTipoDocumento());
				return docFisicoImpl;
			} else if (docFisico instanceof ieci.tdw.ispac.services.ws.client.dto.DocFisico) {
				ieci.tdw.ispac.services.ws.client.dto.DocFisico doc = (ieci.tdw.ispac.services.ws.client.dto.DocFisico) docFisico;
				DocFisicoImpl docFisicoImpl = new DocFisicoImpl();
				docFisicoImpl.setAsunto(doc.getAsunto());
				docFisicoImpl.setTipoDocumento(doc.getTipoDocumento());
				return docFisicoImpl;
			}
		}

		return null;
	}

	public se.tramites.DocElectronico transformDocElectronico(
			Object docElectronico) {

		if (docElectronico != null) {
			if (docElectronico instanceof se.tramites.DocElectronico)
				return (se.tramites.DocElectronico) docElectronico;
			else if (docElectronico instanceof ieci.tdw.ispac.services.dto.DocElectronico) {
				ieci.tdw.ispac.services.dto.DocElectronico doc = (ieci.tdw.ispac.services.dto.DocElectronico) docElectronico;
				DocElectronicoImpl docElectronicoImpl = new DocElectronicoImpl();
				docElectronicoImpl.setAsunto(doc.getAsunto());
				docElectronicoImpl.setTipoDocumento(doc.getTipoDocumento());
				docElectronicoImpl.setExtension(doc.getExtension());
				docElectronicoImpl.setLocalizador(doc.getLocalizador());
				docElectronicoImpl.setRepositorio(doc.getRepositorio());
				return docElectronicoImpl;
			} else if (docElectronico instanceof ieci.tdw.ispac.services.ws.client.dto.DocElectronico) {
				ieci.tdw.ispac.services.ws.client.dto.DocElectronico doc = (ieci.tdw.ispac.services.ws.client.dto.DocElectronico) docElectronico;
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

	public se.tramites.Interesado transformInteresado(Object interesado) {

		if (interesado != null) {
			if (interesado instanceof se.tramites.Interesado)
				return (se.tramites.Interesado) interesado;
			else if (interesado instanceof ieci.tdw.ispac.services.dto.Interesado) {
				ieci.tdw.ispac.services.dto.Interesado inter = (ieci.tdw.ispac.services.dto.Interesado) interesado;
				InteresadoImpl interesadoImpl = new InteresadoImpl();
				interesadoImpl.setIdEnTerceros(inter.getIdEnTerceros());
				interesadoImpl.setInteresadoPrincipal(inter
						.isInteresadoPrincipal());
				interesadoImpl.setNombre(inter.getNombre());
				interesadoImpl.setNumIdentidad(inter.getNumIdentidad());
				interesadoImpl.setRol(inter.getRol());
				return interesadoImpl;
			} else if (interesado instanceof ieci.tdw.ispac.services.ws.client.dto.Interesado) {
				ieci.tdw.ispac.services.ws.client.dto.Interesado inter = (ieci.tdw.ispac.services.ws.client.dto.Interesado) interesado;
				InteresadoImpl interesadoImpl = new InteresadoImpl();
				interesadoImpl.setIdEnTerceros(inter.getIdEnTerceros());
				interesadoImpl.setInteresadoPrincipal(inter
						.isInteresadoPrincipal());
				interesadoImpl.setNombre(inter.getNombre());
				interesadoImpl.setNumIdentidad(inter.getNumIdentidad());
				interesadoImpl.setRol(inter.getRol());
				return interesadoImpl;
			}
		}

		return null;
	}

	public se.tramites.Emplazamiento transformEmplazamiento(Object emplazamiento) {

		if (emplazamiento != null) {
			if (emplazamiento instanceof se.tramites.Emplazamiento)
				return (se.tramites.Emplazamiento) emplazamiento;
			else if (emplazamiento instanceof ieci.tdw.ispac.services.dto.Emplazamiento) {
				ieci.tdw.ispac.services.dto.Emplazamiento empl = (ieci.tdw.ispac.services.dto.Emplazamiento) emplazamiento;
				EmplazamientoImpl emplazamientoImpl = new EmplazamientoImpl();
				emplazamientoImpl.setComunidad(empl.getComunidad());
				emplazamientoImpl.setConcejo(empl.getConcejo());
				emplazamientoImpl.setLocalizacion(empl.getLocalizacion());
				emplazamientoImpl.setPais(empl.getPais());
				emplazamientoImpl.setPoblacion(empl.getPoblacion());
				return emplazamientoImpl;
			} else if (emplazamiento instanceof ieci.tdw.ispac.services.ws.client.dto.Emplazamiento) {
				ieci.tdw.ispac.services.ws.client.dto.Emplazamiento empl = (ieci.tdw.ispac.services.ws.client.dto.Emplazamiento) emplazamiento;
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

	public List transformInfoB(Object[] infoBExps) {
		List ltInfoBExpedientes = new ArrayList();
		if (infoBExps != null) {
			for (int i = 0; i < infoBExps.length; i++) {
				ltInfoBExpedientes.add(transformInfoB(infoBExps[i]));
			}
		}
		return ltInfoBExpedientes;
	}

	public List transformDocsFisicos(Object[] docFisicos) {
		List ltDocFisicos = new ArrayList();
		if (docFisicos != null) {
			for (int i = 0; i < docFisicos.length; i++) {
				ltDocFisicos.add(transformDocFisico(docFisicos[i]));
			}
		}
		return ltDocFisicos;
	}

	public List transformDocsElectronicos(Object[] docElectronicos) {
		List ltDocElectronicos = new ArrayList();
		if (docElectronicos != null) {
			for (int i = 0; i < docElectronicos.length; i++) {
				ltDocElectronicos
						.add(transformDocElectronico(docElectronicos[i]));
			}
		}
		return ltDocElectronicos;
	}

	public List transformInteresados(Object[] interesados) {
		List ltInteresados = new ArrayList();
		if (interesados != null) {
			for (int i = 0; i < interesados.length; i++) {
				ltInteresados.add(transformInteresado(interesados[i]));
			}
		}
		return ltInteresados;
	}

	public List transformEmplazamientos(Object[] emplazamientos) {
		List ltEmplazamientos = new ArrayList();
		if (emplazamientos != null) {
			for (int i = 0; i < emplazamientos.length; i++) {
				ltEmplazamientos.add(transformEmplazamiento(emplazamientos[i]));
			}
		}
		return ltEmplazamientos;
	}

	/**
	 * Recupera los identificadores de los expedientes, del procedimiento
	 * identificado por idProc, que hayan finalizado en el rango de fechas
	 * comprendido entre fechaIni y fechaFin ordenados por lo que indique el
	 * parámetro tipoOrd.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param fechaIni
	 *            Fecha de inicio.
	 * @param fechaFin
	 *            Fecha de fin.
	 * @param tipoOrd
	 *            Tipo de ordenación.
	 *            <p>
	 *            Valores posibles:
	 *            <li>1 - Número de expediente</li>
	 *            <li>2 - Fecha finalización</li>
	 *            </p>
	 * @return Lista de identificadores de expedientes.
	 * @throws SistemaTramitadorException
	 *             si ocurre algún error.
	 */
	public List recuperarIdsExpedientes(String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws SistemaTramitadorException {

		List list = new ArrayList();
		try {
			if (ProcedimientoConstants.API.equalsIgnoreCase(mode)) {
				ServicioTramitacionAdapter servicio = new ServicioTramitacionAdapter();
				String[] ids = servicio.getIdsExpedientes(idProc, fechaIni,
						fechaFin, tipoOrd);
				if ((ids != null) && (ids.length > 0))
					list = CollectionUtils.createList(ids);
			} else {
				TramitacionWebServiceServiceLocator twsServiceLocator = new TramitacionWebServiceServiceLocator();
				twsServiceLocator
						.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service = twsServiceLocator
						.getTramitacionWebService();
				ListaIdentificadores listaIdentificadores = service
						.getIdsExpedientes(idProc, fechaIni, fechaFin, tipoOrd);
				if (listaIdentificadores != null) {
					String[] ids = listaIdentificadores.getIdentificadores();
					if ((ids != null) && (ids.length > 0))
						list = CollectionUtils.createList(ids);
				}
			}
		} catch (ISPACException e) {
			logger.error("Error en la llamada al servicio del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}

		return list;
	}

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en el
	 * parámetro idExps.
	 * 
	 * @param idExps
	 *            Identificadores de expedientes.
	 * @return Lista de expedientes.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link InfoBExpediente}.
	 *         </p>
	 * @throws SistemaTramitadorException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public List recuperarInfoBExpedientes(String[] idExps)
			throws SistemaTramitadorException {

		List list = new ArrayList();
		try {
			if (ProcedimientoConstants.API.equalsIgnoreCase(mode)) {
				ServicioTramitacionAdapter servicio = new ServicioTramitacionAdapter();
				Object[] infoBExps = servicio.getExpedientes(idExps);
				if ((infoBExps != null) && (infoBExps.length > 0))
					list = transformInfoB(infoBExps);
			} else {
				TramitacionWebServiceServiceLocator twsServiceLocator = new TramitacionWebServiceServiceLocator();
				twsServiceLocator
						.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service = twsServiceLocator
						.getTramitacionWebService();
				ListaInfoBExpedientes listaInfoBExpedientes = service
						.getExpedientes(idExps);
				if (listaInfoBExpedientes != null) {
					Object[] infoBExps = listaInfoBExpedientes.getExpedientes();
					if ((infoBExps != null) && (infoBExps.length > 0))
						list = transformInfoB(infoBExps);
				}
			}
		} catch (ISPACException e) {
			logger.error("Error en la llamada al servicio del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}

		return list;
	}

	/**
	 * Recupera la información de un expediente cuyo identificador único es
	 * idExp.
	 * 
	 * @param idExp
	 *            Identificador del expediente.
	 * @return Información de un expediente.
	 * @throws SistemaTramitadorException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public Expediente recuperarExpediente(String idExp)
			throws SistemaTramitadorException {

		ExpedienteImpl expediente = null;

		try {
			if (ProcedimientoConstants.API.equalsIgnoreCase(mode)) {
				ServicioTramitacionAdapter servicio = new ServicioTramitacionAdapter();
				ieci.tdw.ispac.services.dto.Expediente exp = servicio
						.getExpediente(idExp);

				if (exp != null) {
					expediente = new ExpedienteImpl();
					expediente.setFechaInicio(exp.getFechaInicio());
					expediente.setFechaFin(exp.getFechaFinalizacion());
					expediente.setAsunto(exp.getAsunto());
					expediente.setOrganoProductor(exp.getIdOrgProductor(),
							exp.getNombreOrgProductor());
					expediente.setDocumentosFisicos(transformDocsFisicos(exp
							.getDocumentosFisicos()));
					expediente
							.setDocumentosElectronicos(transformDocsElectronicos(exp
									.getDocumentosElectronicos()));
					expediente.setInteresados(transformInteresados(exp
							.getInteresados()));
					expediente.setEmplazamientos(transformEmplazamientos(exp
							.getEmplazamientos()));
					expediente.setInfoExpediente(transformInfoB(exp
							.getInformacionBasica()));
				}
			} else {
				TramitacionWebServiceServiceLocator twsServiceLocator = new TramitacionWebServiceServiceLocator();
				twsServiceLocator
						.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service = twsServiceLocator
						.getTramitacionWebService();
				ieci.tdw.ispac.services.ws.client.dto.Expediente exp = service
						.getExpediente(idExp);

				if (exp != null) {
					expediente = new ExpedienteImpl();
					expediente.setFechaInicio(exp.getFechaInicio());
					expediente.setFechaFin(exp.getFechaFinalizacion());
					expediente.setAsunto(exp.getAsunto());
					expediente.setOrganoProductor(exp.getIdOrgProductor(),
							exp.getNombreOrgProductor());
					expediente.setDocumentosFisicos(transformDocsFisicos(exp
							.getDocumentosFisicos()));
					expediente
							.setDocumentosElectronicos(transformDocsElectronicos(exp
									.getDocumentosElectronicos()));
					expediente.setInteresados(transformInteresados(exp
							.getInteresados()));
					expediente.setEmplazamientos(transformEmplazamientos(exp
							.getEmplazamientos()));
					expediente.setInfoExpediente(transformInfoB(exp
							.getInformacionBasica()));
				}
			}
		} catch (ISPACException e) {
			logger.error("Error en la llamada al servicio del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}

		return expediente;
	}

	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado
	 * archivado
	 * 
	 * @param idExps
	 *            Array de String con los expedientes que se quieren pasar al
	 *            estado archivado
	 * @throws SistemaTramitadorException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public void archivarExpedientes(String[] idExps)
			throws SistemaTramitadorException, NotAvailableException {
		try {
			if (ProcedimientoConstants.API.equalsIgnoreCase(mode)) {
				ServicioTramitacionAdapter servicio = new ServicioTramitacionAdapter();
				servicio.archivarExpedientes(idExps);
			} else {
				TramitacionWebServiceServiceLocator twsServiceLocator = new TramitacionWebServiceServiceLocator();
				twsServiceLocator
						.setTramitacionWebServiceEndpointAddress(wsdlLocation);
				TramitacionWebService service = twsServiceLocator
						.getTramitacionWebService();
				service.archivarExpedientes(idExps);
			}
		} catch (ISPACException e) {
			logger.error("Error en la llamada al servicio del Tramitador", e);
			throw new SistemaTramitadorException(e);
		} catch (Exception e) {
			logger.error("Error en la llamada al servicio del Tramitador", e);
			throw new SistemaTramitadorException(e);
		}
	}
}
