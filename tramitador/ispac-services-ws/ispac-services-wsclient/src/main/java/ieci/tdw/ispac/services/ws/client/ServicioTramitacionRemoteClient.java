package ieci.tdw.ispac.services.ws.client;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.services.ServicioTramitacion;
import ieci.tdw.ispac.services.ServiciosUtils;
import ieci.tdw.ispac.services.dto.DatosComunesExpediente;
import ieci.tdw.ispac.services.dto.DocElectronico;
import ieci.tdw.ispac.services.dto.DocFisico;
import ieci.tdw.ispac.services.dto.DocumentoExpediente;
import ieci.tdw.ispac.services.dto.Emplazamiento;
import ieci.tdw.ispac.services.dto.Expediente;
import ieci.tdw.ispac.services.dto.Firma;
import ieci.tdw.ispac.services.dto.InfoBExpediente;
import ieci.tdw.ispac.services.dto.InfoBProcedimiento;
import ieci.tdw.ispac.services.dto.InfoFichero;
import ieci.tdw.ispac.services.dto.InfoOcupacion;
import ieci.tdw.ispac.services.dto.Interesado;
import ieci.tdw.ispac.services.dto.InteresadoExpediente;
import ieci.tdw.ispac.services.dto.OrganoProductor;
import ieci.tdw.ispac.services.dto.Procedimiento;
import ieci.tdw.ispac.services.dto.RetornoServicio;

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
	public InfoBProcedimiento[] getProcedimientos(int tipoProc, String nombre) 
			throws ISPACException {

		try {
			ieci.tdw.ispac.services.ws.client.dto.ListaInfoBProcedimientos ret = 
				service.getProcedimientosPorTipo(tipoProc, nombre);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return getInfoBProcedimientos(ret);
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener los procedimientos", e);
		}
	}

	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 */
	public InfoBProcedimiento[] getProcedimientos(String[] idProcs) 
			throws ISPACException {

		try {
			ieci.tdw.ispac.services.ws.client.dto.ListaInfoBProcedimientos ret = 
				service.getProcedimientos(idProcs);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return getInfoBProcedimientos(ret);
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener los procedimientos", e);
		}
	}

	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 */
	public Procedimiento getProcedimiento(String idProc) 
			throws ISPACException {
		
		try {
			ieci.tdw.ispac.services.ws.client.dto.Procedimiento ret = 
				service.getProcedimiento(idProc);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return getProcedimiento(ret);
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener el procedimiento", e);
		}
	}

    /**
     * Obtiene el contenido del documento.
     * @param guid GUID del documento
     * @return Contenido del documento.
     * @throws ISPACException si ocurre algún error.
     */
    public byte [] getFichero(String guid) throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.Binario ret = 
				service.getFichero(guid);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.getContenido();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener el contenido del fichero", e);
		}
    }

	/**
	 * Obtiene la información de origen e integridad del documento.
	 * @param guid GUID del documento.
	 * @return Información del documento.
	 * @throws ISPACException si ocurre algún error.
	 */
	public InfoFichero getInfoFichero(String guid) throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.InfoFichero ret = 
				service.getInfoFichero(guid);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return getInfoFichero(ret);
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener la información del fichero", e);
		}
	}

	/**
	 * Obtiene la información de ocupación del repositorio.
	 * @return Información de ocupación.
	 * @throws ISPACException si ocurre algún error.
	 */
	public InfoOcupacion getInfoOcupacion() throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.InfoOcupacion ret = 
				service.getInfoOcupacion();
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return getInfoOcupacion(ret);
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener la información de ocupación", e);
		}
	}

	/**
	 * Elimina los documentos determinados por los GUIDs.
	 * @param guids Lista de GUIDs de los documentos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void eliminaFicheros(String[] guids) throws ISPACException {
		try {
			RetornoServicio ret = service.eliminaFicheros(guids);
			if (!ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al eliminar ficheros", e);
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
	public String[] getIdsExpedientes(String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws ISPACException {
		
		try {
			ieci.tdw.ispac.services.ws.client.dto.ListaIdentificadores ret = 
				service.getIdsExpedientes(idProc, fechaIni, fechaFin, tipoOrd);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.getIdentificadores();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener los identificadores de los expedientes", e);
		}
	}

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en 
	 * el parámetro idExps.
	 * @param idExps Identificadores de expedientes.
	 * @return Lista de expedientes.
	 */
	public InfoBExpediente[] getExpedientes(String[] idExps) 
			throws ISPACException {
		
		try {
			ieci.tdw.ispac.services.ws.client.dto.ListaInfoBExpedientes ret = 
				service.getExpedientes(idExps);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return getInfoBExpedientes(ret);
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener los expedientes", e);
		}
	}

	/**
	 * Recupera la información de un expediente cuyo identificador único es idExp.
	 * @param idExp Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente getExpediente(String idExp) throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.Expediente ret = 
				service.getExpediente(idExp);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return getExpediente(ret);
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener la información del expediente", e);
		}
	}
	
	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado archivado
	 * @param idExps  Array de String con los expedientes que se quieren pasar al estado archivado
	 *  @return Cierto si los expedientes se han archivado correctamente.
	 * @throws ISPACException
	 */
	public void archivarExpedientes(String[] idExps) throws ISPACException {
		try {
			RetornoServicio ret = service.archivarExpedientes(idExps);
			if (!ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al archivar expedientes", e);
		}
	}
	
    /**
     * Iniciar un expediente.
     * 
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param xmlDatosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @return Cierto si el expediente se ha iniciado correctamente.
     * @throws ISPACException Si se produce algún error al iniciar el expediente.
     */
    public boolean iniciarExpediente(DatosComunesExpediente datosComunes, 
	    		String xmlDatosEspecificos, DocumentoExpediente[] documentos) 
	    	throws ISPACException {
    	
		try {
			ieci.tdw.ispac.services.ws.client.dto.Booleano ret = 
				service.iniciarExpediente(getWSDatosComunes(datosComunes), 
						xmlDatosEspecificos, 
						getWSDocumentosExpediente(documentos));
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.isValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al iniciar el expediente", e);
		}
    }

    /**
     * Iniciar un expediente.
     * 
     * @param datosComunes Datos comunes para todos los expedientes.
     * @param xmlDatosEspecificos XML con los datos específicos del expediente.
     * @param documentos Lista de documentos asociados al expediente.
     * @param initSystem Sistema externo desde el que se inicia el expediente
     * @return Número de expediente iniciado.
     * @throws ISPACException Si se produce algún error al iniciar el expediente.
     */    
    public String iniciarExpediente(DatosComunesExpediente datosComunes, 
    		String xmlDatosEspecificos, DocumentoExpediente[] documentos, String initSystem) 
    	throws ISPACException {
	
		try {
			ieci.tdw.ispac.services.ws.client.dto.Cadena ret =
				 service.crearExpediente(getWSDatosComunes(datosComunes), 
						xmlDatosEspecificos, 
						getWSDocumentosExpediente(documentos), initSystem);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al iniciar el expediente", e);
		}
    }
    

    
    /**
     * Cambia el estado administrativo de un expediente.
     * @param numExp Número de expediente.
     * @param estadoAdm Nuevo estado administrativo.
     * @return Cierto si el cambio se ha realizado correctamente, falso en caso contrario.
     * @throws ISPACException Si se produce algún error.
     */
    public boolean cambiarEstadoAdministrativo(String numExp, String estadoAdm) 
    	throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.Booleano ret = 
				service.cambiarEstadoAdministrativo(numExp, estadoAdm);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.isValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al cambiar el estado administrativo del expediente '"+numExp+"'", e);
		}
	}    
    
    
    /**
     * Mueve un expedinete a una fase según el identificador de la misma en el catálogo. 
     * @param numExp Número de expediente.
     * @param idFaseCatalogo Identificador de la fase en el catálogo
     * @return Cierto si la operación se ha realizado con éxito, falso en caso contrario
     * @throws ISPACException
     */
    public boolean moverExpedienteAFase(String numExp, String idFaseCatalogo) throws ISPACException{
		try {
			ieci.tdw.ispac.services.ws.client.dto.Booleano ret = 
				service.moverExpedienteAFase(numExp, idFaseCatalogo);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.isValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al mover el expediente '" + numExp + "' a la fase con id en el catálogo '" + idFaseCatalogo + "'", e);
		}
	}
    
    
    
    /**
     * Añade documentos al trámite de un expediente.
     * @param numExp Número de expediente.
     * @param numReg Número de registro de entrada.
     * @param fechaReg Fecha de registro de entrada.
     * @param documentps Lista de documentos asociados al expediente.
     * @return Cierto si los documentos se han creado correctamente.
     * @throws ISPACException Si se produce algún error.
     */
    public boolean anexarDocsExpediente(String numExp, String numReg, 
    			Date fechaReg, DocumentoExpediente[] documentos) 
    		throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.Booleano ret = 
				service.anexarDocsExpediente(numExp, numReg, fechaReg, 
						getWSDocumentosExpediente(documentos));
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.isValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al anexar documentos al expediente", e);
		}
    }
	
    
    			  
	public String busquedaAvanzada(String nombreGrupo,
			String nombreFrmBusqueda, String xmlBusqueda, int dominio)
			throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.Cadena ret =
				 service.busquedaAvanzada(nombreGrupo, nombreFrmBusqueda, xmlBusqueda, dominio);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al realizar busqueda avanzada", e);
		}
	}


	public int establecerDatosRegistroEntidad(String nombreEntidad,
			String numExp, String xmlDatosEspecificos) throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.Entero ret =
				 service.establecerDatosRegistroEntidad(nombreEntidad, numExp, xmlDatosEspecificos);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al establecer datos de un registro en la entidad '"+nombreEntidad+"'", e);
		}	
	}
	
	
	public String obtenerRegistroEntidad(String nombreEntidad, String numExp,
			int idRegistro) throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.Cadena ret =
				 service.obtenerRegistroEntidad(nombreEntidad, numExp, idRegistro);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener registro de la entidad '"+nombreEntidad+"'", e);
		}
	}    
    
    

	public String obtenerRegistrosEntidad(String nombreEntidad, String numExp)
			throws ISPACException {
		try {
			ieci.tdw.ispac.services.ws.client.dto.Cadena ret =
				 service.obtenerRegistrosEntidad(nombreEntidad, numExp);
			if (ServiciosUtils.isReturnOK((RetornoServicio)ret)) {
				return ret.getValor();
			} else {
				throw getISPACException((RetornoServicio)ret);
			}
		} catch (RemoteException e) {
			throw new ISPACException("Error al obtener los registros de la entidad '"+nombreEntidad+"'", e);
		}
	}	
	
	
    
	public void setService(TramitacionWebService service) {
		this.service = service;
	}
	
	private ISPACException getISPACException(RetornoServicio ret) {
		return new ISPACException("Error en el servicio [#" + ret.getErrorCode() + "]");
	}
	
	private InfoBProcedimiento[] getInfoBProcedimientos(
			ieci.tdw.ispac.services.ws.client.dto.ListaInfoBProcedimientos procs) {
		
		InfoBProcedimiento[] procedimientos = null;
		
		if ( (procs != null) && (procs.getProcedimientos() != null) ) {
			
			ieci.tdw.ispac.services.ws.client.dto.InfoBProcedimiento[] aux = 
				procs.getProcedimientos();
			procedimientos = new InfoBProcedimiento[aux.length];
			for (int i = 0; i < aux.length; i++) {
				procedimientos[i] = getInfoBProcedimiento(aux[i]);
			}
		}
		
		return procedimientos;
	}
	
	private InfoBProcedimiento getInfoBProcedimiento(
			ieci.tdw.ispac.services.ws.client.dto.InfoBProcedimiento proc) {
		
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
			ieci.tdw.ispac.services.ws.client.dto.Procedimiento proc) {
		
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
			ieci.tdw.ispac.services.ws.client.dto.OrganoProductor[] orgs) {
		
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
			ieci.tdw.ispac.services.ws.client.dto.OrganoProductor org) {
		
		OrganoProductor organo = null;
		
		if (org != null) {
			organo = new OrganoProductor();
			organo.setId(org.getId());
			organo.setInicioProduccion(org.getInicioProduccion());
		}
		
		return organo;
	}

	private InfoFichero getInfoFichero(
			ieci.tdw.ispac.services.ws.client.dto.InfoFichero info) {
		
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
    		ieci.tdw.ispac.services.ws.client.dto.Firma[] signs) {
    	
    	Firma [] firmas = null;
		
		if (signs != null) {
			firmas = new Firma[signs.length];
			for (int i = 0; i < signs.length; i++) {
				firmas[i] = getFirma(signs[i]);
			}
		}

		return firmas;
    }

    private Firma getFirma(ieci.tdw.ispac.services.ws.client.dto.Firma sign) {
    	
		Firma firma = null;
		
		if (sign != null) {
			firma = new Firma();
			firma.setAutor(sign.getAutor());
			firma.setAutenticada(sign.isAutenticada());
		}

		return firma;
    }
	
	private InfoOcupacion getInfoOcupacion(
			ieci.tdw.ispac.services.ws.client.dto.InfoOcupacion info) {
		
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
			ieci.tdw.ispac.services.ws.client.dto.ListaInfoBExpedientes exps) {
		
		InfoBExpediente[] expedientes = null;
		
		if ( (exps != null) && (exps.getExpedientes() != null) ) {
			
			ieci.tdw.ispac.services.ws.client.dto.InfoBExpediente[] aux = 
				exps.getExpedientes();
			expedientes = new InfoBExpediente[aux.length];
			for (int i = 0; i < aux.length; i++) {
				expedientes[i] = getInfoBExpediente(aux[i]);
			}
		}
		
		return expedientes;
	}
	
	private InfoBExpediente getInfoBExpediente(
			ieci.tdw.ispac.services.ws.client.dto.InfoBExpediente exp) {
		
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
			ieci.tdw.ispac.services.ws.client.dto.Expediente exp) {
		
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
    		ieci.tdw.ispac.services.ws.client.dto.DocFisico[] doc) {
    	
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
    		ieci.tdw.ispac.services.ws.client.dto.DocFisico doc) {
    	
		DocFisico documento = null;
		
		if (doc != null) {
			documento = new DocFisico();
			documento.setTipoDocumento(doc.getTipoDocumento());
			documento.setAsunto(doc.getAsunto());
		}

		return documento;
    }

    private DocElectronico[] getDocumentosElectronicos(
    		ieci.tdw.ispac.services.ws.client.dto.DocElectronico[] doc) {
    	
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
    		ieci.tdw.ispac.services.ws.client.dto.DocElectronico doc) {
    	
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
    		ieci.tdw.ispac.services.ws.client.dto.Interesado[] inters) {
    	
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
    		ieci.tdw.ispac.services.ws.client.dto.Interesado inter) {
    	
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
    		ieci.tdw.ispac.services.ws.client.dto.Emplazamiento[] emps) {
    	
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
    		ieci.tdw.ispac.services.ws.client.dto.Emplazamiento emp) {
    	
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

    private ieci.tdw.ispac.services.ws.client.dto.DatosComunesExpediente getWSDatosComunes(
    		DatosComunesExpediente datos) {

		ieci.tdw.ispac.services.ws.client.dto.DatosComunesExpediente datosComunes = null;

		if (datos != null) {
			datosComunes = new ieci.tdw.ispac.services.ws.client.dto.DatosComunesExpediente();
			datosComunes.setIdOrganismo(datos.getIdOrganismo());
			datosComunes.setTipoAsunto(datos.getTipoAsunto());
			datosComunes.setNumeroRegistro(datos.getNumeroRegistro());
			datosComunes.setFechaRegistro(datos.getFechaRegistro());
			datosComunes.setInteresados(
					getInteresadosExpediente(datos.getInteresados()));
		}

		return datosComunes;
	}

    private ieci.tdw.ispac.services.ws.client.dto.InteresadoExpediente[] getInteresadosExpediente(
    		InteresadoExpediente[] inters) {
    	
    	ieci.tdw.ispac.services.ws.client.dto.InteresadoExpediente [] interesados = null;
		
		if (inters != null) {
			interesados = new ieci.tdw.ispac.services.ws.client.dto.InteresadoExpediente[inters.length];
			for (int i = 0; i < inters.length; i++) {
				interesados[i] = getInteresadoExpediente(inters[i]);
			}
		}

		return interesados;
    }

    private ieci.tdw.ispac.services.ws.client.dto.InteresadoExpediente getInteresadoExpediente(
    		InteresadoExpediente inter) {
    	
    	ieci.tdw.ispac.services.ws.client.dto.InteresadoExpediente interesado = null;
		
		if (inter != null) {
			interesado = new ieci.tdw.ispac.services.ws.client.dto.InteresadoExpediente();

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

	private ieci.tdw.ispac.services.ws.client.dto.DocumentoExpediente[] getWSDocumentosExpediente(
			DocumentoExpediente[] docs) {

		ieci.tdw.ispac.services.ws.client.dto.DocumentoExpediente[] documentos = null;

		if (docs != null) {
			documentos = new ieci.tdw.ispac.services.ws.client.dto.DocumentoExpediente[docs.length];
			for (int i = 0; i < docs.length; i++) {
				documentos[i] = getWSDocumentoExpediente(docs[i]);
			}
		}

		return documentos;
	}

	private ieci.tdw.ispac.services.ws.client.dto.DocumentoExpediente getWSDocumentoExpediente(
			DocumentoExpediente doc) {

		ieci.tdw.ispac.services.ws.client.dto.DocumentoExpediente documento = null;

		if (doc != null) {
			documento = new ieci.tdw.ispac.services.ws.client.dto.DocumentoExpediente();
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
