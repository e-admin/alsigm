package ieci.tecdoc.sgm.calendario.ws.server; 
/*
 * $Id: CatalogoTramitesWebService.java,v 1.2.2.3 2008/10/13 08:51:13 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.calendario.CalendarioExcepcion;
import ieci.tecdoc.sgm.core.services.calendario.ServicioCalendario;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

public class CalendarioWebService {

	private static final Logger logger = Logger.getLogger(CalendarioWebService.class);

	private static final String SERVICE_NAME_CALENDAR = ConstantesServicios.SERVICE_CALENDAR;
	
	private ServicioCalendario getServicioCalendario() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioCalendario();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME_CALENDAR).append(".").append(cImpl);
			return LocalizadorServicios.getServicioCalendario(sbImpl.toString());
		}
	}
	
	public Calendario obtenerCalendario(Entidad entidad) {
		try {	
			Calendario calendario = getCalendarioWS(
					getServicioCalendario().obtenerCalendario(entidad)
				);
			return (Calendario)ServiciosUtils.completeReturnOK(calendario);
		} catch (CalendarioExcepcion e) {
			logger.error("Error al obtener el calendario.", e);
			return (Calendario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Calendario()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el calendario.", e);
			return (Calendario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Calendario()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener el calendario.", e);
			return (Calendario)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Calendario()));			
		}
	}
	
	public RetornoServicio nuevoCalendario(Calendario calendario, Entidad entidad) {
		try {	
			getServicioCalendario().nuevoCalendario(getCalendarioServicio(calendario), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CalendarioExcepcion e) {
			logger.error("Error al dar de alta el calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al dar de alta el calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al dar de alta el calendario.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio actualizarCalendario(Calendario calendario, Entidad entidad) {
		try {	
			getServicioCalendario().actualizarCalendario(getCalendarioServicio(calendario), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CalendarioExcepcion e) {
			logger.error("Error al actualizar el calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar el calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar el calendario.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio eliminarCalendario(boolean borrarDias, Entidad entidad) {
		try {	
			getServicioCalendario().eliminarCalendario(borrarDias, entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CalendarioExcepcion e) {
			logger.error("Error al eliminar el calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar el calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar el calendario.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public CalendarioDia obtenerDiaCalendario(String id, Entidad entidad) {
		try {	
			CalendarioDia calendarioDia = getCalendarioDiaWS(
					getServicioCalendario().obtenerDiaCalendario(id, entidad)
				);
			return (CalendarioDia)ServiciosUtils.completeReturnOK(calendarioDia);
		} catch (CalendarioExcepcion e) {
			logger.error("Error al obtener un dia del calendario.", e);
			return (CalendarioDia)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new CalendarioDia()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener un dia del calendario.", e);
			return (CalendarioDia)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new CalendarioDia()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener un dia del calendario.", e);
			return (CalendarioDia)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new CalendarioDia()));			
		}
	}
	
	public CalendarioDia nuevoDiaCalendario(CalendarioDia dia, Entidad entidad) {
		try {	
			CalendarioDia calendarioDia = getCalendarioDiaWS(
					getServicioCalendario().nuevoDiaCalendario(getCalendarioDiaServicio(dia), entidad)
				);
			return (CalendarioDia)ServiciosUtils.completeReturnOK(calendarioDia);
		} catch (CalendarioExcepcion e) {
			logger.error("Error al obtener un dia del calendario.", e);
			return (CalendarioDia)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new CalendarioDia()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener un dia del calendario.", e);
			return (CalendarioDia)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new CalendarioDia()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener un dia del calendario.", e);
			return (CalendarioDia)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new CalendarioDia()));			
		}
	}
	
	public RetornoServicio actualizarDiaCalendario(CalendarioDia dia, Entidad entidad) {
		try {	
			getServicioCalendario().actualizarDiaCalendario(getCalendarioDiaServicio(dia), entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CalendarioExcepcion e) {
			logger.error("Error al actualizar un dia del calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al actualizar un dia del calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al actualizar un dia del calendario.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public RetornoServicio eliminarDiaCalendario(String id, Entidad entidad) {
		try {	
			getServicioCalendario().eliminarDiaCalendario(id, entidad);
			return ServiciosUtils.createReturnOK();
		}  catch (CalendarioExcepcion e) {
			logger.error("Error al eliminar un dia del calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar un dia del calendario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar un dia del calendario.", e);
			return ServiciosUtils.createReturnError();		
		}
	}
	
	public CalendarioDias obtenerDiasCalendario(int tipo, Entidad entidad) {
		try {	
			CalendarioDias calendarioDias = getCalendarioDiasWS(
					getServicioCalendario().obtenerDiasCalendario(tipo, entidad)
				);
			return (CalendarioDias)ServiciosUtils.completeReturnOK(calendarioDias);
		} catch (CalendarioExcepcion e) {
			logger.error("Error al obtener los dia del calendario.", e);
			return (CalendarioDias)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new CalendarioDias()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los dia del calendario.", e);
			return (CalendarioDias)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new CalendarioDias()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado los obtener un dia del calendario.", e);
			return (CalendarioDias)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new CalendarioDias()));			
		}
	}

	public RetornoCalendario proximoLaborable(String fecha, Entidad entidad) {
		try {	
			RetornoCalendario retornoCalendario = getRetornoCalendarioWS(
					getServicioCalendario().proximoLaborable(DateTimeUtil.getDate(fecha, ConstantesServicios.DATE_PATTERN), entidad)
				);
			return (RetornoCalendario)ServiciosUtils.completeReturnOK(retornoCalendario);
		} catch (CalendarioExcepcion e) {
			logger.error("Error al obtener el siguiente laborable del calendario.", e);
			return (RetornoCalendario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCalendario()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener el siguiente laborable del calendario.", e);
			return (RetornoCalendario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCalendario()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado los obtener el siguiente laborable del calendario.", e);
			return (RetornoCalendario)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoCalendario()));			
		}
	}
	
	private Calendario getCalendarioWS(ieci.tecdoc.sgm.core.services.calendario.Calendario poCalendario){
		if (poCalendario == null)
			return null;
		
		Calendario oCalendario = new Calendario();
		oCalendario.setDias(poCalendario.getDias());
		oCalendario.setHoraInicio(poCalendario.getHoraInicio());
		oCalendario.setHoraFin(poCalendario.getHoraFin());
		oCalendario.setMinutoFin(poCalendario.getMinutoFin());
		oCalendario.setMinutoInicio(poCalendario.getMinutoInicio());
		
		return oCalendario;
	}
	
	private ieci.tecdoc.sgm.core.services.calendario.Calendario getCalendarioServicio(Calendario poCalendario){
		if (poCalendario == null)
			return null;
		
		ieci.tecdoc.sgm.core.services.calendario.Calendario oCalendario = new ieci.tecdoc.sgm.core.services.calendario.Calendario();
		oCalendario.setDias(poCalendario.getDias());
		oCalendario.setHoraInicio(poCalendario.getHoraInicio());
		oCalendario.setHoraFin(poCalendario.getHoraFin());
		oCalendario.setMinutoInicio(poCalendario.getMinutoInicio());
		oCalendario.setMinutoFin(poCalendario.getMinutoFin());
		
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
		oCalendarioDia.setFijo(poCalendarioDia.getFijo());
		oCalendarioDia.setId(poCalendarioDia.getId());
		
		return oCalendarioDia;
	}
	
	private CalendarioDias getCalendarioDiasWS(ieci.tecdoc.sgm.core.services.calendario.CalendarioDias poCalendarioDias){
		if (poCalendarioDias == null)
			return null;
		
		CalendarioDias oCalendarioDias = new CalendarioDias();
		CalendarioDia[] poCalendarioDiasArray = new CalendarioDia[poCalendarioDias.count()];
		for (int i=0; i<poCalendarioDias.count(); i++)
			poCalendarioDiasArray[i] = getCalendarioDiaWS((ieci.tecdoc.sgm.core.services.calendario.CalendarioDia)poCalendarioDias.get(i));
		oCalendarioDias.setDias(poCalendarioDiasArray);
		return oCalendarioDias;
	}
	
	private RetornoCalendario getRetornoCalendarioWS(ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario poRetornoCalendario){
		if (poRetornoCalendario == null)
			return null;
		
		RetornoCalendario oRetornoCalendario = new RetornoCalendario();
		oRetornoCalendario.setLaborable(poRetornoCalendario.isLaborable());
		oRetornoCalendario.setProximo(poRetornoCalendario.getProximo().toString());
		
		return oRetornoCalendario;
	}
}

