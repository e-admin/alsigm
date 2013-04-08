package ieci.tecdoc.sgm.entidades.ws.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.entidades.CriterioBusquedaEntidades;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.EntidadesException;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.pago.PagoTelematicoException;

public class EntidadesWSRemoteClient implements ServicioEntidades{

	private EntidadesWebService service;
	
	public Entidad nuevaEntidad(Entidad poEntidad) throws EntidadesException {
		try{
			ieci.tecdoc.sgm.entidades.ws.client.Entidad oEntidad = service.nuevaEntidad(getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oEntidad)){
				return getEntidadServicio(oEntidad);
			}else{
				throw getEntidadesException((IRetornoServicio)oEntidad);
			}
		} catch (RemoteException e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public Entidad actualizarEntidad(Entidad poEntidad) throws EntidadesException {
		try{
			ieci.tecdoc.sgm.entidades.ws.client.Entidad oEntidad = service.actualizarEntidad(getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oEntidad)){
				return getEntidadServicio(oEntidad);
			}else{
				throw getEntidadesException((IRetornoServicio)oEntidad);
			}
		} catch (RemoteException e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public Entidad obtenerEntidad(String identificador) throws EntidadesException {
		try{
			ieci.tecdoc.sgm.entidades.ws.client.Entidad oEntidad = service.obtenerEntidad(identificador);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oEntidad)){
				return getEntidadServicio(oEntidad);
			}else{
				throw getEntidadesException((IRetornoServicio)oEntidad);
			}
		} catch (RemoteException e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public List buscarEntidades(CriterioBusquedaEntidades poCriterio) throws EntidadesException {
		try{
			ieci.tecdoc.sgm.entidades.ws.client.Entidades oEntidades = service.buscarEntidades(getCriterioBusquedaEntidadesWS(poCriterio));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oEntidades)){
				return getListaEntidadesServicio(oEntidades);
			}else{
				throw getEntidadesException((IRetornoServicio)oEntidades);
			}
		} catch (RemoteException e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public void eliminarEntidad(Entidad poEntidad) throws EntidadesException {
		try{
			ieci.tecdoc.sgm.entidades.ws.client.RetornoServicio oRetorno = service.eliminarEntidad(getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getEntidadesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	public List obtenerEntidades() throws EntidadesException {
		try{
			ieci.tecdoc.sgm.entidades.ws.client.Entidades oEntidades = service.obtenerEntidades();
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oEntidades)){
				return getListaEntidadesServicio(oEntidades);
			}else{
				throw getEntidadesException((IRetornoServicio)oEntidades);
			}
		} catch (RemoteException e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	
	public String obtenerIdentificadorEntidad() throws EntidadesException {
		try{
			ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena oIdentificador = service.obtenerIdentificadorEntidad();
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oIdentificador)){
				return getRetornoCadenaServicio(oIdentificador);
			}else{
				throw getEntidadesException((IRetornoServicio)oIdentificador);
			}
		} catch (RemoteException e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	
	public EntidadesWebService getService() {
		return service;
	}

	public void setService(EntidadesWebService service) {
		this.service = service;
	}

	private EntidadesException getEntidadesException(IRetornoServicio oReturn){
		return new EntidadesException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private ieci.tecdoc.sgm.entidades.ws.client.Entidad getEntidadWS(Entidad oEntidad) {
		if (oEntidad == null)
			return null;
		
		ieci.tecdoc.sgm.entidades.ws.client.Entidad poEntidad = new ieci.tecdoc.sgm.entidades.ws.client.Entidad();
		
		poEntidad.setIdentificador(oEntidad.getIdentificador());
		poEntidad.setNombreCorto(oEntidad.getNombreCorto());
		poEntidad.setNombreLargo(oEntidad.getNombreLargo());
		poEntidad.setCodigoINE(oEntidad.getCodigoINE());
		
		return poEntidad;
	}
	
	private Entidad getEntidadServicio(ieci.tecdoc.sgm.entidades.ws.client.Entidad oEntidad) {
		if (oEntidad == null)
			return null;
		
		Entidad poEntidad = new Entidad();
		
		poEntidad.setIdentificador(oEntidad.getIdentificador());
		poEntidad.setNombreCorto(oEntidad.getNombreCorto());
		poEntidad.setNombreLargo(oEntidad.getNombreLargo());
		poEntidad.setCodigoINE(oEntidad.getCodigoINE());
		
		return poEntidad;
	}
	
	private ieci.tecdoc.sgm.entidades.ws.client.CriterioBusquedaEntidades getCriterioBusquedaEntidadesWS(CriterioBusquedaEntidades oCriterio) {
		if (oCriterio == null)
			return null;
		
		ieci.tecdoc.sgm.entidades.ws.client.CriterioBusquedaEntidades poCriterio = new ieci.tecdoc.sgm.entidades.ws.client.CriterioBusquedaEntidades();
		
		poCriterio.setNombreCorto(oCriterio.getNombreCorto());
		poCriterio.setNombreLargo(oCriterio.getNombreLargo());
		
		return poCriterio;
	}
	
	private List getListaEntidadesServicio(Entidades oEntidades) {
		if(oEntidades == null || oEntidades.getEntidades() == null)
			return new ArrayList();
		
		List poEntidades = new ArrayList(oEntidades.getEntidades().length);
		for(int i=0; i<oEntidades.getEntidades().length; i++) {
			poEntidades.add(getEntidadServicio((ieci.tecdoc.sgm.entidades.ws.client.Entidad)oEntidades.getEntidades()[i]));
		}
		
		return poEntidades;
	}
	
	private String getRetornoCadenaServicio(ieci.tecdoc.sgm.entidades.ws.client.RetornoCadena oIdentificador) {
		if (oIdentificador == null)
			return null;
		
		String poIdentificador = new String();
		
		poIdentificador = oIdentificador.getValor();
		
		return poIdentificador;
	}
}
