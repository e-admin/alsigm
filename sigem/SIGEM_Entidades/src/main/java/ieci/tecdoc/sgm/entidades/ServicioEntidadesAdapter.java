package ieci.tecdoc.sgm.entidades;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.entidades.CriterioBusquedaEntidades;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.EntidadesException;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.entidades.exception.EntidadException;

public class ServicioEntidadesAdapter implements ServicioEntidades {
	
	private static final Logger logger = Logger.getLogger(ServicioEntidadesAdapter.class);
	
	public Entidad nuevaEntidad(Entidad poEntidad) throws EntidadesException {
		try {
			return getEntidadServicio(
					EntidadesManager.nuevaEntidad(getEntidadImpl(poEntidad))
				);			
		} catch(EntidadException e) {
			throw getEntidadesException(e);
		} catch(Throwable e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e);
		}
		
	}
	
	public void eliminarEntidad(Entidad poEntidad) throws EntidadesException {
		try {
			EntidadesManager.eliminarEntidad(getEntidadImpl(poEntidad));
		} catch(EntidadException e) {
			throw getEntidadesException(e);
		} catch(Throwable e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public Entidad actualizarEntidad(Entidad poEntidad) throws EntidadesException {
		try {
			return getEntidadServicio(
					EntidadesManager.actualizarEntidad(getEntidadImpl(poEntidad))
				);	
		} catch(EntidadException e) {
			throw getEntidadesException(e);
		} catch(Throwable e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public Entidad obtenerEntidad(String identificador) throws EntidadesException {
		try {
			return getEntidadServicio(
					EntidadesManager.obtenerEntidad(identificador)
				);	
		} catch(EntidadException e) {
			throw getEntidadesException(e);
		} catch(Throwable e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public List buscarEntidades(CriterioBusquedaEntidades poCriterio) throws EntidadesException {
		try {
			return getEntidadesServicio(
					EntidadesManager.buscarEntidades(getCriterioBusquedaEntidadesImpl(poCriterio))
				);	
		} catch(EntidadException e) {
			throw getEntidadesException(e);
		} catch(Throwable e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public List obtenerEntidades() throws EntidadesException {
		try {
			return getEntidadesServicio(
					EntidadesManager.obtenerEntidades()
				);	
		} catch(EntidadException e) {
			throw getEntidadesException(e);
		} catch(Throwable e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	public String obtenerIdentificadorEntidad() throws EntidadesException {
		try {
			return EntidadesManager.obtenerIdentificadorEntidad();	
		} catch(EntidadException e) {
			throw getEntidadesException(e);
		} catch(Throwable e) {
			throw new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	private EntidadesException getEntidadesException(EntidadException poException){
		if(poException == null){
			return new EntidadesException(EntidadesException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_BACK_OFFICE_USER_MANAGER_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new EntidadesException(Long.valueOf(cCodigo.toString()).longValue(), poException);
	}
	
	private Entidad getEntidadServicio(ieci.tecdoc.sgm.entidades.beans.Entidad oEntidad) {
		if (oEntidad == null)
			return null;
		
		Entidad poEntidad = new Entidad();
		
		poEntidad.setIdentificador(oEntidad.getIdentificador());
		poEntidad.setNombreCorto(oEntidad.getNombreCorto());
		poEntidad.setNombreLargo(oEntidad.getNombreLargo());
		poEntidad.setCodigoINE(oEntidad.getCodigoINE());
		
		return poEntidad;
	}
	
	private ieci.tecdoc.sgm.entidades.beans.Entidad getEntidadImpl(Entidad oEntidad) {
		if (oEntidad == null)
			return null;
		
		ieci.tecdoc.sgm.entidades.beans.Entidad poEntidad = new ieci.tecdoc.sgm.entidades.beans.Entidad();
		
		poEntidad.setIdentificador(oEntidad.getIdentificador());
		poEntidad.setNombreCorto(oEntidad.getNombreCorto());
		poEntidad.setNombreLargo(oEntidad.getNombreLargo());
		poEntidad.setCodigoINE(oEntidad.getCodigoINE());
		
		return poEntidad;
	}
	
	private ieci.tecdoc.sgm.entidades.beans.CriterioBusquedaEntidades getCriterioBusquedaEntidadesImpl(CriterioBusquedaEntidades oCriterio) {
		if (oCriterio == null)
			return null;
		
		ieci.tecdoc.sgm.entidades.beans.CriterioBusquedaEntidades poCriterio = new ieci.tecdoc.sgm.entidades.beans.CriterioBusquedaEntidades();
		
		poCriterio.setNombreCorto(oCriterio.getNombreCorto());
		poCriterio.setNombreLargo(oCriterio.getNombreLargo());
		
		return poCriterio;
	}
	
	private List getEntidadesServicio(List oEntidades) {
		if (oEntidades == null)
			return new ArrayList();
		
		List poEntidades = new ArrayList(oEntidades.size());
		for(int i=0; i<oEntidades.size(); i++)
			poEntidades.add(getEntidadServicio((ieci.tecdoc.sgm.entidades.beans.Entidad)oEntidades.get(i)));
		
		return poEntidades;
	}
}
