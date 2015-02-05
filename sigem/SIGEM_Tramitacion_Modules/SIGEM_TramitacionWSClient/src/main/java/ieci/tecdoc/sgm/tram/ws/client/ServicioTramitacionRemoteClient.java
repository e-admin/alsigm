package ieci.tecdoc.sgm.tram.ws.client;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.TramitacionException;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocElectronico;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocFisico;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Emplazamiento;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Firma;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoBProcedimiento;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoFichero;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InfoOcupacion;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Interesado;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.OrganoProductor;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Procedimiento;
import ieci.tecdoc.sgm.tram.ws.client.dto.Cadena;

import java.rmi.RemoteException;
import java.util.Date;

public class ServicioTramitacionRemoteClient implements ServicioTramitacion {

	private TramitacionWebService service = null;
	

	/**
	 * Recupera la lista de procedimientos del tipo que se indica en 
	 * tipoProc, con su información básica.
	 * @param tipoProc Tipo de procedimiento.
	 * <p>Valores posibles de tipoProc (@see InfoBProcedimiento):
	 * <li>1 - Todos</li>
	 * <li>2 - Procedimientos  automatizados</li>
	 * <li>3 - Procedimientos no automatizados</li>
	 * </p>
	 * @param nombre Nombre del procedimiento.
	 * @return Lista de información de procedimientos.
	 */
	public InfoBProcedimiento[] getProcedimientos(String idEntidad, int tipoProc, String nombre) 
			throws TramitacionException {

		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.ListaInfoBProcedimientos ret = 
				service.getProcedimientosPorTipo(idEntidad, tipoProc, nombre);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getInfoBProcedimientos(ret);
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 */
	public InfoBProcedimiento[] getProcedimientos(String idEntidad, String[] idProcs) 
			throws TramitacionException {

		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.ListaInfoBProcedimientos ret = 
				service.getProcedimientos(idEntidad, idProcs);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getInfoBProcedimientos(ret);
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 */
	public Procedimiento getProcedimiento(String idEntidad, String idProc) 
			throws TramitacionException {
		
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Procedimiento ret = 
				service.getProcedimiento(idEntidad, idProc);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getProcedimiento(ret);
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

    /**
     * Obtiene el contenido del documento.
     * @param guid GUID del documento
     * @return Contenido del documento.
     * @throws TramitacionException si ocurre algún error.
     */
    public byte [] getFichero(String idEntidad, String guid) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Binario ret = 
				service.getFichero(idEntidad, guid);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.getContenido();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
    }

	/**
	 * Obtiene la información de origen e integridad del documento.
	 * @param guid GUID del documento.
	 * @return Información del documento.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public InfoFichero getInfoFichero(String idEntidad, String guid) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoFichero ret = 
				service.getInfoFichero(idEntidad, guid);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getInfoFichero(ret);
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * @return Información de ocupación.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public InfoOcupacion getInfoOcupacion(String idEntidad) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoOcupacion ret = 
				service.getInfoOcupacion(idEntidad);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getInfoOcupacion(ret);
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	/**
	 * Elimina los documentos determinados por los GUIDs.
	 * @param guids Lista de GUIDs de los documentos.
	 * @throws TramitacionException si ocurre algún error.
	 */
	public void eliminaFicheros(String idEntidad, String[] guids) throws TramitacionException {
		try {
			RetornoServicio ret = service.eliminaFicheros(idEntidad, guids);
			if (!ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
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
	 */
	public String[] getIdsExpedientes(String idEntidad, String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws TramitacionException {
		
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.ListaIdentificadores ret = 
				service.getIdsExpedientes(idEntidad, idProc, fechaIni, fechaFin, tipoOrd);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.getIdentificadores();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en 
	 * el parámetro idExps.
	 * @param idExps Identificadores de expedientes.
	 * @return Lista de expedientes.
	 */
	public InfoBExpediente[] getExpedientes(String idEntidad, String[] idExps) 
			throws TramitacionException {
		
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.ListaInfoBExpedientes ret = 
				service.getExpedientes(idEntidad, idExps);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getInfoBExpedientes(ret);
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente getExpediente(String idEntidad, String idExp) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Expediente ret = 
				service.getExpediente(idEntidad, idExp);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return getExpediente(ret);
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}
		
	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado archivado
	 * @param idExps  Array de String con los expedientes que se quieren pasar al estado archivado
	 *  @return Cierto si los expedientes se han archivado correctamente.
	 * @throws ISPACException
	 */
	public void archivarExpedientes(String idEntidad, String[] idExps) throws TramitacionException {
		try {
			RetornoServicio ret = service.archivarExpedientes(idEntidad, idExps);
			if (!ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}

	/**
     * Iniciar un expediente.
     * 
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param xmlDatosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @return Cierto si el expediente se ha iniciado correctamente.
     * @throws TramitacionException Si se produce algún error al iniciar el expediente.
     */
    public boolean iniciarExpediente(String idEntidad, DatosComunesExpediente datosComunes, 
	    		String xmlDatosEspecificos, DocumentoExpediente[] documentos) 
	    	throws TramitacionException {
    	
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Booleano ret = 
				service.iniciarExpediente(idEntidad, getWSDatosComunes(datosComunes), 
						xmlDatosEspecificos, 
						getWSDocumentosExpediente(documentos));
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.isValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
    }

    /**
     * Añade documentos al trámite de un expediente.
     * @param numExp Número de expediente.
     * @param numReg Número de registro de entrada.
     * @param fechaReg Fecha de registro de entrada.
     * @param documentps Lista de documentos asociados al expediente.
     * @return Cierto si los documentos se han creado correctamente.
     * @throws TramitacionException Si se produce algún error.
     */
    public boolean anexarDocsExpediente(String idEntidad, String numExp, String numReg, 
    			Date fechaReg, DocumentoExpediente[] documentos) 
    		throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Booleano ret = 
				service.anexarDocsExpediente(idEntidad, numExp, numReg, fechaReg, 
						getWSDocumentosExpediente(documentos));
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.isValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
    }
	
    
	public boolean cambiarEstadoAdministrativo(String idEntidad, String numExp,
			String estadoAdm) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Booleano ret = 
				service.cambiarEstadoAdministrativo(idEntidad, numExp, estadoAdm);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.isValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}

	}

	/**
     * Iniciar un expediente.
     * 
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param xmlDatosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @param initSystem Sistema desde el que se inicia
     * @return Numero expediente
     * @throws TramitacionException Si se produce algún error al iniciar el expediente.
     */
	public String iniciarExpediente(String idEntidad,
			DatosComunesExpediente datosComunes, String datosEspecificos,
			DocumentoExpediente[] documentos, String initSystem)
			throws TramitacionException {
    	
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Cadena ret = 
				service.crearExpediente(idEntidad, getWSDatosComunes(datosComunes), 
					datosEspecificos, 
					getWSDocumentosExpediente(documentos),
					initSystem);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
    }

	public boolean moverExpedienteAFase(String idEntidad, String numExp,
			String idFaseCatalogo) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Booleano ret = 
				service.moverExpedienteAFase(idEntidad, numExp, idFaseCatalogo);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.isValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}    
    
    
	public String busquedaAvanzada(String idEntidad, String nombreGrupo,
			String nombreFrmBusqueda, String xmlBusqueda, int dominio)
			throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Cadena ret =
				 service.busquedaAvanzada(idEntidad, nombreGrupo, nombreFrmBusqueda, xmlBusqueda, dominio);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}


	public int establecerDatosRegistroEntidad(String idEntidad, String nombreEntidad,
			String numExp, String xmlDatosEspecificos) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Entero ret =
				 service.establecerDatosRegistroEntidad(idEntidad, nombreEntidad, numExp, xmlDatosEspecificos);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}	
	}
	
	
	public String obtenerRegistroEntidad(String idEntidad, String nombreEntidad, String numExp,
			int idRegistro) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Cadena ret =
				 service.obtenerRegistroEntidad(idEntidad, nombreEntidad, numExp, idRegistro);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}    
    
    

	public String obtenerRegistrosEntidad(String idEntidad, String nombreEntidad, String numExp)
			throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Cadena ret =
				 service.obtenerRegistrosEntidad(idEntidad, nombreEntidad, numExp);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION, 
					e.getMessage(), e);
		}
	}		
    
	public String recibirDocumentoFirmado(String idEntidad, String numExp,
			String idDocumento) throws TramitacionException {
		try {
			ieci.tecdoc.sgm.tram.ws.client.dto.Cadena ret = service.recibirDocumentoFirmado(idEntidad, numExp, idDocumento);
			if (ServiciosUtils.isReturnOK((IRetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getTramitacionException((IRetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new TramitacionException(
					TramitacionException.EXC_GENERIC_EXCEPCION,
					e.getMessage(), e);
		}
	}
    
	public void setService(TramitacionWebService service) {
		this.service = service;
	}
	
	private TramitacionException getTramitacionException(IRetornoServicio ret) {
		return new TramitacionException(Long.valueOf(ret.getErrorCode()).longValue());
	}
	
	private InfoBProcedimiento[] getInfoBProcedimientos(
			ieci.tecdoc.sgm.tram.ws.client.dto.ListaInfoBProcedimientos procs) {
		
		InfoBProcedimiento[] procedimientos = null;
		
		if ( (procs != null) && (procs.getProcedimientos() != null) ) {
			
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoBProcedimiento[] aux = 
				procs.getProcedimientos();
			procedimientos = new InfoBProcedimiento[aux.length];
			for (int i = 0; i < aux.length; i++) {
				procedimientos[i] = getInfoBProcedimiento(aux[i]);
			}
		}
		
		return procedimientos;
	}
	
	private InfoBProcedimiento getInfoBProcedimiento(
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoBProcedimiento proc) {
		
		InfoBProcedimiento procedimiento = null;
		
		if (proc != null) {
			procedimiento = new InfoBProcedimiento();
			procedimiento.setId(proc.getId());
			procedimiento.setCodigo(proc.getCodigo());
			procedimiento.setNombre(proc.getNombre());
			procedimiento.setCodSistProductor(proc.getCodSistProductor());
			procedimiento.setNombreSistProductor(proc.getNombreSistProductor());
		}
		
		return procedimiento;
	}

	private Procedimiento getProcedimiento(
			ieci.tecdoc.sgm.tram.ws.client.dto.Procedimiento proc) {
		
		Procedimiento procedimiento = null;
		
		if (proc != null) {
			procedimiento = new Procedimiento();
			
			procedimiento.getInformacionBasica().setId(
					proc.getInformacionBasica().getId());
			procedimiento.getInformacionBasica().setCodigo(
					proc.getInformacionBasica().getCodigo());
			procedimiento.getInformacionBasica().setNombre(
					proc.getInformacionBasica().getNombre());
			procedimiento.getInformacionBasica().setCodSistProductor(
					proc.getInformacionBasica().getCodSistProductor());
			procedimiento.getInformacionBasica().setNombreSistProductor(
					proc.getInformacionBasica().getNombreSistProductor());
			
			procedimiento.setObjeto(proc.getObjeto());
			procedimiento.setTramites(proc.getTramites());
			procedimiento.setNormativa(proc.getNormativa());
			procedimiento.setDocumentosBasicos(proc.getDocumentosBasicos());
			procedimiento.setOrganosProductores(
					getOrganosProductores(proc.getOrganosProductores())); 
			//= new OrganoProductor[0];
			
		}
		
		return procedimiento;
	}
	
	private OrganoProductor[] getOrganosProductores(
			ieci.tecdoc.sgm.tram.ws.client.dto.OrganoProductor[] orgs) {
		
		OrganoProductor[] organos = null;
		
		if (orgs != null) {
			organos = new OrganoProductor[orgs.length];
			for (int i = 0; i < orgs.length; i++) {
				organos[i] = getOrganoProductor(orgs[i]);
			}
		}
		
		return organos;
	}
	
	private OrganoProductor getOrganoProductor(
			ieci.tecdoc.sgm.tram.ws.client.dto.OrganoProductor org) {
		
		OrganoProductor organo = null;
		
		if (org != null) {
			organo = new OrganoProductor();
			organo.setId(org.getId());
			organo.setInicioProduccion(org.getInicioProduccion());
		}
		
		return organo;
	}

	private InfoFichero getInfoFichero(
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoFichero info) {
		
		InfoFichero infoFichero = null;
		
		if (info != null) {
			infoFichero = new InfoFichero();
			infoFichero.setNombre(info.getNombre());
			infoFichero.setFechaAlta(info.getFechaAlta());
			infoFichero.setFirmas(getFirmas(info.getFirmas()));
		}
		
		return infoFichero;
	}
	
    private Firma[] getFirmas(
    		ieci.tecdoc.sgm.tram.ws.client.dto.Firma[] signs) {
    	
    	Firma [] firmas = null;
		
		if (signs != null) {
			firmas = new Firma[signs.length];
			for (int i = 0; i < signs.length; i++) {
				firmas[i] = getFirma(signs[i]);
			}
		}

		return firmas;
    }

    private Firma getFirma(ieci.tecdoc.sgm.tram.ws.client.dto.Firma sign) {
    	
		Firma firma = null;
		
		if (sign != null) {
			firma = new Firma();
			firma.setAutor(sign.getAutor());
			firma.setAutenticada(sign.isAutenticada());
		}

		return firma;
    }
	
	private InfoOcupacion getInfoOcupacion(
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoOcupacion info) {
		
		InfoOcupacion infoOcupacion = null;
		
		if (info != null) {
			infoOcupacion = new InfoOcupacion();
			infoOcupacion.setEspacioOcupado(info.getEspacioOcupado());
			infoOcupacion.setEspacioTotal(info.getEspacioTotal());
			infoOcupacion.setNumeroFicheros(info.getNumeroFicheros());
		}
		
		return infoOcupacion;
	}

	private InfoBExpediente[] getInfoBExpedientes(
			ieci.tecdoc.sgm.tram.ws.client.dto.ListaInfoBExpedientes exps) {
		
		InfoBExpediente[] expedientes = null;
		
		if ( (exps != null) && (exps.getExpedientes() != null) ) {
			
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoBExpediente[] aux = 
				exps.getExpedientes();
			expedientes = new InfoBExpediente[aux.length];
			for (int i = 0; i < aux.length; i++) {
				expedientes[i] = getInfoBExpediente(aux[i]);
			}
		}
		
		return expedientes;
	}
	
	private InfoBExpediente getInfoBExpediente(
			ieci.tecdoc.sgm.tram.ws.client.dto.InfoBExpediente exp) {
		
		InfoBExpediente expediente = null;
		
		if (exp != null) {
			expediente = new InfoBExpediente();
			expediente.setId(exp.getId());
			expediente.setNumExp(exp.getNumExp());
			expediente.setDatosIdentificativos(exp.getDatosIdentificativos());
		}
		
		return expediente;
	}

	private Expediente getExpediente(
			ieci.tecdoc.sgm.tram.ws.client.dto.Expediente exp) {
		
		Expediente expediente = null;
		
		if (exp != null) {
			expediente = new Expediente();
			
			expediente.getInformacionBasica().setId(
					exp.getInformacionBasica().getId());
			expediente.getInformacionBasica().setNumExp(
					exp.getInformacionBasica().getNumExp());
			expediente.getInformacionBasica().setDatosIdentificativos(
					exp.getInformacionBasica().getDatosIdentificativos());

			expediente.setFechaInicio(exp.getFechaInicio());
			expediente.setFechaFinalizacion(exp.getFechaFinalizacion());
			expediente.setIdOrgProductor(exp.getIdOrgProductor());
			expediente.setNombreOrgProductor(exp.getNombreOrgProductor());
			expediente.setAsunto(exp.getAsunto());

			expediente.setDocumentosFisicos(
					getDocumentosFisicos(exp.getDocumentosFisicos()));
			expediente.setDocumentosElectronicos(
					getDocumentosElectronicos(exp.getDocumentosElectronicos()));
			expediente.setInteresados(getInteresados(exp.getInteresados()));
			expediente.setEmplazamientos(
					getEmplazamientos(exp.getEmplazamientos()));
		}
		
		return expediente;
	}

    private DocFisico[] getDocumentosFisicos(
    		ieci.tecdoc.sgm.tram.ws.client.dto.DocFisico[] doc) {
    	
    	DocFisico [] documentos = null;
		
		if (doc != null) {
			documentos = new DocFisico[doc.length];
			for (int i = 0; i < doc.length; i++) {
				documentos[i] = getDocFisico(doc[i]);
			}
		}

		return documentos;
    }

    private DocFisico getDocFisico(
    		ieci.tecdoc.sgm.tram.ws.client.dto.DocFisico doc) {
    	
		DocFisico documento = null;
		
		if (doc != null) {
			documento = new DocFisico();
			documento.setTipoDocumento(doc.getTipoDocumento());
			documento.setAsunto(doc.getAsunto());
		}

		return documento;
    }

    private DocElectronico[] getDocumentosElectronicos(
    		ieci.tecdoc.sgm.tram.ws.client.dto.DocElectronico[] doc) {
    	
    	DocElectronico [] documentos = null;
		
		if (doc != null) {
			documentos = new DocElectronico[doc.length];
			for (int i = 0; i < doc.length; i++) {
				documentos[i] = getDocElectronico(doc[i]);
			}
		}

		return documentos;
    }

    private DocElectronico getDocElectronico(
    		ieci.tecdoc.sgm.tram.ws.client.dto.DocElectronico doc) {
    	
		DocElectronico documento = null;
		
		if (doc != null) {
			documento = new DocElectronico();
			documento.setTipoDocumento(doc.getTipoDocumento());
			documento.setAsunto(doc.getAsunto());
			documento.setRepositorio(doc.getRepositorio());
			documento.setLocalizador(doc.getLocalizador());
			documento.setExtension(doc.getExtension());
		}

		return documento;
    }

    private Interesado[] getInteresados(
    		ieci.tecdoc.sgm.tram.ws.client.dto.Interesado[] inters) {
    	
    	Interesado [] interesados = null;
		
		if (inters != null) {
			interesados = new Interesado[inters.length];
			for (int i = 0; i < inters.length; i++) {
				interesados[i] = getInteresado(inters[i]);
			}
		}

		return interesados;
    }

    private Interesado getInteresado(
    		ieci.tecdoc.sgm.tram.ws.client.dto.Interesado inter) {
    	
		Interesado interesado = null;
		
		if (inter != null) {
			interesado = new Interesado();
			interesado.setNombre(inter.getNombre());
			interesado.setNumIdentidad(inter.getNumIdentidad());
			interesado.setRol(inter.getRol());
			interesado.setInteresadoPrincipal(inter.isInteresadoPrincipal());
			interesado.setIdEnTerceros(inter.getIdEnTerceros());
		}

		return interesado;
    }

    private Emplazamiento[] getEmplazamientos(
    		ieci.tecdoc.sgm.tram.ws.client.dto.Emplazamiento[] emps) {
    	
    	Emplazamiento [] emplazamientos = null;
		
		if (emps != null) {
			emplazamientos = new Emplazamiento[emps.length];
			for (int i = 0; i < emps.length; i++) {
				emplazamientos[i] = getEmplazamiento(emps[i]);
			}
		}

		return emplazamientos;
    }

    private Emplazamiento getEmplazamiento(
    		ieci.tecdoc.sgm.tram.ws.client.dto.Emplazamiento emp) {
    	
		Emplazamiento emplazamiento = null;
		
		if (emp != null) {
			emplazamiento = new Emplazamiento();
			emplazamiento.setPais(emp.getPais());
			emplazamiento.setComunidad(emp.getComunidad());
			emplazamiento.setConcejo(emp.getConcejo());
			emplazamiento.setPoblacion(emp.getPoblacion());
			emplazamiento.setLocalizacion(emp.getLocalizacion());
		}

		return emplazamiento;
    }

    private ieci.tecdoc.sgm.tram.ws.client.dto.DatosComunesExpediente getWSDatosComunes(
    		DatosComunesExpediente datos) {

		ieci.tecdoc.sgm.tram.ws.client.dto.DatosComunesExpediente datosComunes = null;

		if (datos != null) {
			datosComunes = new ieci.tecdoc.sgm.tram.ws.client.dto.DatosComunesExpediente();
			datosComunes.setIdOrganismo(datos.getIdOrganismo());
			datosComunes.setTipoAsunto(datos.getTipoAsunto());
			datosComunes.setNumeroRegistro(datos.getNumeroRegistro());
			datosComunes.setFechaRegistro(datos.getFechaRegistro());
			datosComunes.setInteresados(
					getInteresadosExpediente(datos.getInteresados()));
		}

		return datosComunes;
	}

    private ieci.tecdoc.sgm.tram.ws.client.dto.InteresadoExpediente[] getInteresadosExpediente(
    		InteresadoExpediente[] inters) {
    	
    	ieci.tecdoc.sgm.tram.ws.client.dto.InteresadoExpediente [] interesados = null;
		
		if (inters != null) {
			interesados = new ieci.tecdoc.sgm.tram.ws.client.dto.InteresadoExpediente[inters.length];
			for (int i = 0; i < inters.length; i++) {
				interesados[i] = getInteresadoExpediente(inters[i]);
			}
		}

		return interesados;
    }

    private ieci.tecdoc.sgm.tram.ws.client.dto.InteresadoExpediente getInteresadoExpediente(
    		InteresadoExpediente inter) {
    	
    	ieci.tecdoc.sgm.tram.ws.client.dto.InteresadoExpediente interesado = null;
		
		if (inter != null) {
			interesado = new ieci.tecdoc.sgm.tram.ws.client.dto.InteresadoExpediente();

			interesado.setThirdPartyId(inter.getThirdPartyId());
			interesado.setIndPrincipal(inter.getIndPrincipal());
			interesado.setNifcif(inter.getNifcif());
			interesado.setName(inter.getName());
			interesado.setPostalAddress(inter.getPostalAddress());
			interesado.setPostalCode(inter.getPostalCode());
			interesado.setPlaceCity(inter.getPlaceCity());
			interesado.setRegionCountry(inter.getRegionCountry());
			interesado.setTelematicAddress(inter.getTelematicAddress());
			interesado.setNotificationAddressType(inter.getNotificationAddressType());
			interesado.setPhone(inter.getPhone());
			interesado.setMobilePhone(inter.getMobilePhone());
		}

		return interesado;
    }

	private ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente[] getWSDocumentosExpediente(
			DocumentoExpediente[] docs) {

		ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente[] documentos = null;

		if (docs != null) {
			documentos = new ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente[docs.length];
			for (int i = 0; i < docs.length; i++) {
				documentos[i] = getWSDocumentoExpediente(docs[i]);
			}
		}

		return documentos;
	}

	private ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente getWSDocumentoExpediente(
			DocumentoExpediente doc) {

		ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente documento = null;

		if (doc != null) {
			documento = new ieci.tecdoc.sgm.tram.ws.client.dto.DocumentoExpediente();
			documento.setId(doc.getId());
			documento.setCode(doc.getCode());
			documento.setName(doc.getName());
			documento.setContent(doc.getContent());
			documento.setExtension(doc.getExtension());
			documento.setLenght(doc.getLenght());
		}

		return documento;
	}


}
