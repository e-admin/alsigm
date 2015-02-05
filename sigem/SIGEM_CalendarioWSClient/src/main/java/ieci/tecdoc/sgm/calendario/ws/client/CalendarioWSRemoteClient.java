package ieci.tecdoc.sgm.calendario.ws.client;
/*
 * $Id: CalendarioWSRemoteClient.java,v 1.2.2.4 2008/10/13 08:51:13 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.calendario.CalendarioExcepcion;
import ieci.tecdoc.sgm.core.services.calendario.ServicioCalendario;
import ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

import java.rmi.RemoteException;
import java.util.Date;

public class CalendarioWSRemoteClient implements ServicioCalendario {

	private CalendarioWebService service;
	
	
	public CalendarioWebService getService() {
		return service;
	}

	public void setService(CalendarioWebService service) {
		this.service = service;
	}
	
	public void actualizarCalendario(ieci.tecdoc.sgm.core.services.calendario.Calendario calendario, Entidad entidad) throws CalendarioExcepcion {
		try{
			RetornoServicio oRetorno =  getService().actualizarCalendario(getCalendarioWS(calendario), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void actualizarDiaCalendario(ieci.tecdoc.sgm.core.services.calendario.CalendarioDia dia, Entidad entidad) throws CalendarioExcepcion {
		try{
			RetornoServicio oRetorno =  getService().actualizarDiaCalendario(getCalendarioDiaWS(dia), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void eliminarCalendario(boolean borrarDias, Entidad entidad) throws CalendarioExcepcion {
		try{
			RetornoServicio oRetorno =  getService().eliminarCalendario(borrarDias, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void eliminarDiaCalendario(String id, Entidad entidad) throws CalendarioExcepcion {
		try{
			RetornoServicio oRetorno =  getService().eliminarDiaCalendario(id, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void nuevoCalendario(ieci.tecdoc.sgm.core.services.calendario.Calendario calendario, Entidad entidad) throws CalendarioExcepcion {
		try{
			RetornoServicio oRetorno =  getService().nuevoCalendario(getCalendarioWS(calendario), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}	
	}

	public ieci.tecdoc.sgm.core.services.calendario.CalendarioDia nuevoDiaCalendario(ieci.tecdoc.sgm.core.services.calendario.CalendarioDia dia, Entidad entidad) throws CalendarioExcepcion {
		try{
			CalendarioDia oRetorno =  getService().nuevoDiaCalendario(getCalendarioDiaWS(dia), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getCalendarioDiaServicio(oRetorno);
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.calendario.Calendario obtenerCalendario(Entidad entidad) throws CalendarioExcepcion {
		try{
			Calendario oRetorno =  getService().obtenerCalendario(getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getCalendarioServicio(oRetorno);
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.calendario.CalendarioDia obtenerDiaCalendario(String id, Entidad entidad) throws CalendarioExcepcion {
		try{
			CalendarioDia oRetorno =  getService().obtenerDiaCalendario(id, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getCalendarioDiaServicio(oRetorno);
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.calendario.CalendarioDias obtenerDiasCalendario(int tipo, Entidad entidad) throws CalendarioExcepcion {
		try{
			CalendarioDias oRetorno =  getService().obtenerDiasCalendario(tipo, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getCalendarioDiasServicio(oRetorno);
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario proximoLaborable(Date fecha, Entidad entidad) throws CalendarioExcepcion {
		try{
			RetornoCalendario oRetorno =  getService().proximoLaborable(fecha.toString(), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoCalendarioServicio(oRetorno);
			}else{
				throw getCalendarioException((IRetornoServicio)oRetorno);
			}
		}catch (RemoteException e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}catch (Exception e) {
			throw new CalendarioExcepcion(CatalogoTramitesExcepcion.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}
	
	
	private CalendarioExcepcion getCalendarioException(IRetornoServicio oReturn){
		return new CalendarioExcepcion(Long.valueOf(oReturn.getErrorCode()).longValue());
	}
	
	private ieci.tecdoc.sgm.calendario.ws.client.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad ==  null){
			return null;
		}
		ieci.tecdoc.sgm.calendario.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.calendario.ws.client.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		oEntidad.setNombre(poEntidad.getNombre());
		return oEntidad;
	}
	
	private Calendario getCalendarioWS(ieci.tecdoc.sgm.core.services.calendario.Calendario poCalendario){
		if (poCalendario == null)
			return null;
		
		Calendario oCalendario = new Calendario();
		oCalendario.setDias(poCalendario.getDias());
		oCalendario.setHoraFin(poCalendario.getHoraFin());
		oCalendario.setHoraInicio(poCalendario.getHoraInicio());
		oCalendario.setMinutoFin(poCalendario.getMinutoFin());
		oCalendario.setMinutoInicio(poCalendario.getMinutoInicio());
		
		return oCalendario;
	}
	
	private ieci.tecdoc.sgm.core.services.calendario.Calendario getCalendarioServicio(Calendario poCalendario){
		if (poCalendario == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.calendario.Calendario oCalendario = new ieci.tecdoc.sgm.core.services.calendario.Calendario();
		oCalendario.setDias(poCalendario.getDias());
		oCalendario.setHoraFin(poCalendario.getHoraFin());
		oCalendario.setHoraInicio(poCalendario.getHoraInicio());
		oCalendario.setMinutoFin(poCalendario.getMinutoFin());
		oCalendario.setMinutoInicio(poCalendario.getMinutoInicio());
		
		return oCalendario;
	}
	
	private CalendarioDia getCalendarioDiaWS(ieci.tecdoc.sgm.core.services.calendario.CalendarioDia poCalendarioDia){
		if (poCalendarioDia == null)
			return null;
		
		CalendarioDia oCalendarioDia = new CalendarioDia();
		oCalendarioDia.setDescripcion(poCalendarioDia.getDescripcion());
		oCalendarioDia.setFecha(poCalendarioDia.getFecha());
		oCalendarioDia.setFijo(poCalendarioDia.getFijo());
		oCalendarioDia.setId(poCalendarioDia.getId());
		
		return oCalendarioDia;
	}
	
	private ieci.tecdoc.sgm.core.services.calendario.CalendarioDia getCalendarioDiaServicio(CalendarioDia poCalendarioDia){
		if (poCalendarioDia == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.calendario.CalendarioDia oCalendarioDia = new ieci.tecdoc.sgm.core.services.calendario.CalendarioDia();
		oCalendarioDia.setDescripcion(poCalendarioDia.getDescripcion());
		oCalendarioDia.setFecha(poCalendarioDia.getFecha());
		oCalendarioDia.setFijo(poCalendarioDia.isFijo());
		oCalendarioDia.setId(poCalendarioDia.getId());
		
		return oCalendarioDia;
	}
	
	private ieci.tecdoc.sgm.core.services.calendario.CalendarioDias getCalendarioDiasServicio(CalendarioDias poCalendarioDias){
		if (poCalendarioDias == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.calendario.CalendarioDias oCalendarioDias = new ieci.tecdoc.sgm.core.services.calendario.CalendarioDias();
		CalendarioDia[] poCalendarioDiasArray = poCalendarioDias.getDias();
		for (int i=0; i<poCalendarioDiasArray.length; i++)
			oCalendarioDias.add(getCalendarioDiaServicio(poCalendarioDiasArray[i]));
		
		return oCalendarioDias;
	}
	
	private ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario getRetornoCalendarioServicio(RetornoCalendario poRetornoCalendario) throws Exception {
		if (poRetornoCalendario == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario oRetornoCalendario = new ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario();
		oRetornoCalendario.setLaborable(poRetornoCalendario.isLaborable());
		oRetornoCalendario.setProximo(DateTimeUtil.getDate(poRetornoCalendario.getProximo(), ConstantesServicios.DATE_PATTERN));
		
		return oRetornoCalendario;
	}
}
