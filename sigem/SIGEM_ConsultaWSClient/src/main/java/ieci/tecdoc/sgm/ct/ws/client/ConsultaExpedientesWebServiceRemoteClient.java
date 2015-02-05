package ieci.tecdoc.sgm.ct.ws.client;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.consulta.ConsultaExpedientesException;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

public class ConsultaExpedientesWebServiceRemoteClient implements ServicioConsultaExpedientes {

	private static final String TEMP_FILE_PREFIX = "CONTEMP_";

	private ConsultaExpedientesWebService service;

	public void altaNotificacion(ieci.tecdoc.sgm.core.services.consulta.Notificacion poNotificacion, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().altaNotificacion(getNotificacionWS(poNotificacion), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public String obtenerURLPagoTasas() throws ConsultaExpedientesException {
		try{
			Url url = getService().obtenerURLPagoTasas();
			if(ServiciosUtils.isReturnOK((IRetornoServicio)url)){
				return getUrlServicio(url);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)url);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.consulta.InfoDocumento recogerDocumento(String guid, Entidad entidad) throws ConsultaExpedientesException {
		try{
			InfoDocumento poDoc = new InfoDocumento();
			poDoc.setGuid(guid);
			poDoc = getService().recogerDocumento(poDoc, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)poDoc)){
				return getInfoDocumentoServicio(poDoc);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)poDoc);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public boolean recogerNotificaciones(String NIF, String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			ieci.tecdoc.sgm.ct.ws.client.Expediente oExp = new ieci.tecdoc.sgm.ct.ws.client.Expediente();
			oExp.setNumero(numeroExpediente);
			oExp.setNif(NIF);
			NotificacionesPendientes oResult = getService().recogerNotificaciones(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oResult)){
				return getNotificacionesPendientes(oResult);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oResult);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void altaSolicitudPago(ieci.tecdoc.sgm.core.services.consulta.Pago poPago, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().altaSolicitudPago(getPagoWS(poPago), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void altaSolicitudSubsanacion(ieci.tecdoc.sgm.core.services.consulta.Subsanacion poSubsanacion, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().altaSolicitudSubsanacion(getSubsanacionWS(poSubsanacion), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void anexarFicherosHitoActual(ieci.tecdoc.sgm.core.services.consulta.FicherosHito ficheros, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().anexarFicherosHitoActual(
						getFicherosHitoWS(ficheros), getEntidadWS(entidad)
				);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Expediente busquedaExpediente(String NIF, String expediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(expediente);
			oExp.setNif(NIF);
			oExp = getService().busquedaExpediente(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oExp)){
				return getExpedienteServicio(oExp);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oExp);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Expedientes busquedaExpedientes(ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes oCriterio, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expedientes oExps = getService().busquedaExpedientes(
					getCriterioBusquedaWS(oCriterio), getEntidadWS(entidad)
			);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oExps)){
				return getExpedientesServicio(oExps);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oExps);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public String cargarDocumento(String guid, Entidad entidad) throws ConsultaExpedientesException {
		try{
			InfoDocumento oInfo = new InfoDocumento();
			oInfo.setGuid(guid);
			oInfo = getService().cargarDocumento(oInfo, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oInfo)){
				try {
					return obtenerArchivoLocal(oInfo).getAbsolutePath();
				} catch (IOException e) {
					throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
				}
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oInfo);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Expedientes consultarExpedientes(ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes poCriterio, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expedientes oExps = getService().consultarExpedientes(
					getCriterioBusquedaWS(poCriterio), getEntidadWS(entidad)
			);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oExps)){
				return getExpedientesServicio(oExps);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oExps);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Expedientes consultarExpedientesNIF(String NIF, Entidad entidad) throws ConsultaExpedientesException {
		try{
			CriterioBusquedaExpedientes oCriterio = new CriterioBusquedaExpedientes();
			oCriterio.setNIF(NIF);
			Expedientes oExps = getService().consultarExpedientesNIF(oCriterio, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oExps)){
				return getExpedientesServicio(oExps);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oExps);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void eliminarExpediente(ieci.tecdoc.sgm.core.services.consulta.Expediente expediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().eliminarExpediente(getExpedienteWS(expediente), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void eliminarHitoActual(String numExp, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(numExp);
			RetornoServicio oRetorno =
				getService().eliminarHitoActual(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void eliminarHitoHistorico(String numExp, Entidad entidad) throws ConsultaExpedientesException {
		try{
			HitoExpediente oHito = new HitoExpediente();
			oHito.setNumeroExpediente(numExp);
			RetornoServicio oRetorno =
				getService().eliminarHitoHistorico(oHito, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void eliminarInteresado(ieci.tecdoc.sgm.core.services.consulta.Interesado interesado, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().eliminarInteresado(getInteresadoWS(interesado), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void eliminarInteresadoExpediente(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().eliminarInteresadoExpediente(numeroExpediente, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void establecerHitoActual(ieci.tecdoc.sgm.core.services.consulta.HitoExpediente hito, ieci.tecdoc.sgm.core.services.consulta.FicherosHito fichero, boolean historico, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno = getService().establecerHitoActual(
							getHitoExpedienteWS(hito),
							getFicherosHitoWS(fichero),
							getHistoricoWS(historico),
							getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public boolean existenNotificaciones(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(numeroExpediente);
			RetornoLogico oRetorno = getService().existenNotificaciones(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoLogicoServicio(oRetorno);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public boolean existenPagos(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(numeroExpediente);
			RetornoLogico oRetorno = getService().existenPagos(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoLogicoServicio(oRetorno);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public boolean existenSubsanaciones(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(numeroExpediente);
			RetornoLogico oRetorno = getService().existenSubsanaciones(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoLogicoServicio(oRetorno);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void nuevoExpediente(ieci.tecdoc.sgm.core.services.consulta.Expediente expediente, ieci.tecdoc.sgm.core.services.consulta.Interesado interesado, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno = getService().nuevoExpediente(
							getExpedienteWS(expediente),
							getInteresadoWS(interesado),
							getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void nuevoHitoHistorico(ieci.tecdoc.sgm.core.services.consulta.HitoExpediente hito, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().nuevoHitoHistorico(getHitoExpedienteWS(hito), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public void nuevoInteresado(ieci.tecdoc.sgm.core.services.consulta.Interesado interesado, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoServicio oRetorno =
				getService().nuevoInteresado(getInteresadoWS(interesado), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Expediente obtenerDetalle(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(numeroExpediente);
			oExp = getService().obtenerDetalle(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oExp)){
				return getExpedienteServicio(oExp);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oExp);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.FicherosHito obtenerFicherosHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException {
		try{
			HitoExpediente oHito = new HitoExpediente();
			oHito.setGuid(guidHito);
			FicherosHito oFichs = getService().obtenerFicherosHito(oHito, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oFichs)){
				return getFicherosHitoServicio(oFichs);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oFichs);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.FicherosHitos obtenerFicherosHitos(ieci.tecdoc.sgm.core.services.consulta.HitosExpediente hitos, Entidad entidad) throws ConsultaExpedientesException {
		try{
			FicherosHitos oHitos = getService().obtenerFicherosHitos(getHitosExpedienteWS(hitos), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oHitos)){
				return getFicherosHitos(oHitos);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oHitos);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.HitosExpediente obtenerHistoricoExpediente(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(numeroExpediente);
			HitosExpediente oHitos = getService().obtenerHistoricoExpediente(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oHitos)){
				return getHitosExpedienteServicio(oHitos);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oHitos);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.HitoExpediente obtenerHitoEstado(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(numeroExpediente);
			HitoExpediente oHito = getService().obtenerHitoEstado(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oHito)){
				return getHitoExpedienteServicio(oHito);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oHito);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Notificaciones obtenerNotificionesHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException {
		try{
			HitoExpediente hito = new HitoExpediente();
			hito.setGuid(guidHito);
			Notificaciones oNots = getService().obtenerNotificionesHito(hito, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oNots)){
				return getNotificacionesServicio(oNots);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oNots);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Notificaciones obtenerNotificionesHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(expediente);
			Notificaciones oNots = getService().obtenerNotificionesHitoActual(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oNots)){
				return getNotificacionesServicio(oNots);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oNots);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Pagos obtenerPagosHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException {
		try{
			HitoExpediente oHito = new HitoExpediente();
			oHito.setGuid(guidHito);
			Pagos oPagos = getService().obtenerPagosHito(oHito, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oPagos)){
				return getPagosServicio(oPagos);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oPagos);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Pagos obtenerPagosHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(expediente);
			Pagos oPagos = getService().obtenerPagosHitoActual(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oPagos)){
				return getPagosServicio(oPagos);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oPagos);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Subsanaciones obtenerSubsanacionesHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException {
		try{
			HitoExpediente oHito = new HitoExpediente();
			oHito.setGuid(guidHito);
			Subsanaciones oSubs = getService().obtenerSubsanacionesHito(oHito, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oSubs)){
				return getSubsanacionesServicio(oSubs);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oSubs);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public ieci.tecdoc.sgm.core.services.consulta.Subsanaciones obtenerSubsanacionesHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException {
		try{
			Expediente oExp = new Expediente();
			oExp.setNumero(expediente);
			Subsanaciones oSubs = getService().obtenerSubsanacionesHitoActual(oExp, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oSubs)){
				return getSubsanacionesServicio(oSubs);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oSubs);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public String obtenerURLAportacionExpedientes() throws ConsultaExpedientesException {
		try{
			Url url = getService().obtenerURLAportacionExpedientes();
			if(ServiciosUtils.isReturnOK((IRetornoServicio)url)){
				return getUrlServicio(url);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)url);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	public String obtenerURLNotificacionExpedientes() throws ConsultaExpedientesException {
		try{
			Url url = getService().obtenerURLNotificacionExpedientes();
			if(ServiciosUtils.isReturnOK((IRetornoServicio)url)){
				return getUrlServicio(url);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)url);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public boolean actualizarEstadoLocalGIS(String idExpediente, String estado, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoLogico oRetorno = getService().actualizarEstadoLocalGIS(idExpediente, estado, getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoLogicoServicio(oRetorno);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public boolean publicarExpedienteLocalGIS(String idExpediente, String nif, String tipoExpediente, String estado, Date fecha, Entidad entidad) throws ConsultaExpedientesException {
		try{
			RetornoLogico oRetorno = getService().publicarExpedienteLocalGIS(idExpediente, nif, tipoExpediente, estado, DateTimeUtil.getDateTime(fecha, ConstantesServicios.DATE_PATTERN), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoLogicoServicio(oRetorno);
			}else{
				throw getConsultaExpedientesException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ConsultaExpedientesWebService getService() {
		return service;
	}

	public void setService(ConsultaExpedientesWebService service) {
		this.service = service;
	}

	private ConsultaExpedientesException getConsultaExpedientesException(IRetornoServicio oReturn){
		return new ConsultaExpedientesException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}

	private Notificacion getNotificacionWS(ieci.tecdoc.sgm.core.services.consulta.Notificacion poNot){
		if(poNot == null){
			return null;
		}
		Notificacion oNot = new Notificacion();
		oNot.setDescripcion(poNot.getDescripcion());
		oNot.setDeu(poNot.getDEU());
		oNot.setExpediente(poNot.getExpediente());
		oNot.setFechaNotificacion(poNot.getFechaNotificacion());
		oNot.setHitoId(poNot.getHitoId());
		oNot.setNotificacionId(poNot.getNotificacionId());
		oNot.setServicioNotificionesId(poNot.getServicioNotificionesId());
		return oNot;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Notificacion getNotificacionServicio(Notificacion poNot){
		if(poNot == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Notificacion oNot = new ieci.tecdoc.sgm.core.services.consulta.Notificacion();
		oNot.setDescripcion(poNot.getDescripcion());
		oNot.setDEU(poNot.getDeu());
		oNot.setExpediente(poNot.getExpediente());
		oNot.setFechaNotificacion(poNot.getFechaNotificacion());
		oNot.setHitoId(poNot.getHitoId());
		oNot.setNotificacionId(poNot.getNotificacionId());
		oNot.setServicioNotificionesId(poNot.getServicioNotificionesId());
		return oNot;
	}

	private Expediente getExpedienteWS(ieci.tecdoc.sgm.core.services.consulta.Expediente poExp){
		if(poExp == null){
			return null;
		}
		Expediente oExp = new Expediente();
		oExp.setAportacion(poExp.getAportacion());
		oExp.setCodigoPresentacion(poExp.getCodigoPresentacion());
		oExp.setEstado(poExp.getEstado());
		oExp.setFechaInicio(DateTimeUtil.getDateTime(poExp.getFechaInicio(), ConstantesServicios.DATE_PATTERN));
		oExp.setFechaRegistro(DateTimeUtil.getDateTime(poExp.getFechaRegistro(), ConstantesServicios.DATE_PATTERN));
		oExp.setInformacionAuxiliar(poExp.getInformacionAuxiliar());
		oExp.setNotificacion(poExp.getNotificacion());
		oExp.setNumero(poExp.getNumero());
		oExp.setNumeroRegistro(poExp.getNumeroRegistro());
		oExp.setProcedimiento(poExp.getProcedimiento());
		return oExp;
	}

	private boolean getNotificacionesPendientes(NotificacionesPendientes poNots){
		if( (poNots == null) || (poNots.getNotificacionesPendientes() == null)
				|| ("".equals(poNots.getNotificacionesPendientes())
				|| (ConstantesServicios.LABEL_FALSE.equals(poNots.getNotificacionesPendientes())) )
		){
			return false;
		} else{
			return true;
		}
	}

	private ieci.tecdoc.sgm.core.services.consulta.InfoDocumento getInfoDocumentoServicio(InfoDocumento poInfo){
		if(poInfo == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.InfoDocumento oInfo = new ieci.tecdoc.sgm.core.services.consulta.InfoDocumento();
		if(poInfo.getContent() != null){
			oInfo.setContent(Base64.decode(poInfo.getContent()));
		}
		oInfo.setExtension(poInfo.getExtension());
		oInfo.setGuid(poInfo.getGuid());
		oInfo.setMimeType(poInfo.getMimeType());
		return oInfo;
	}

	private String getUrlServicio(Url poUrl){
		if(poUrl == null){
			return null;
		}
		return poUrl.getUrl();
	}

	private Pago getPagoWS(ieci.tecdoc.sgm.core.services.consulta.Pago poPago){
		if(poPago == null){
			return null;
		}
		Pago oPago = new Pago();
		oPago.setAutoliquidacionId(poPago.getAutoliquidacionId());
		oPago.setEntidadEmisoraId(poPago.getEntidadEmisoraId());
		oPago.setFecha(poPago.getFecha());
		oPago.setIdDocumento(poPago.getIdDocumento());
		oPago.setIdentificador(poPago.getIdentificador());
		oPago.setIdentificadorHito(poPago.getIdentificadorHito());
		oPago.setImporte(poPago.getImporte());
		oPago.setMensajeParaElCiudadano(poPago.getMensajeParaElCiudadano());
		oPago.setNumeroExpediente(poPago.getNumeroExpediente());
		return oPago;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Pago getPagoServicio(Pago poPago){
		if(poPago == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Pago oPago = new ieci.tecdoc.sgm.core.services.consulta.Pago();
		oPago.setAutoliquidacionId(poPago.getAutoliquidacionId());
		oPago.setEntidadEmisoraId(poPago.getEntidadEmisoraId());
		oPago.setFecha(poPago.getFecha());
		oPago.setIdDocumento(poPago.getIdDocumento());
		oPago.setIdentificador(poPago.getIdentificador());
		oPago.setIdentificadorHito(poPago.getIdentificadorHito());
		oPago.setImporte(poPago.getImporte());
		oPago.setMensajeParaElCiudadano(poPago.getMensajeParaElCiudadano());
		oPago.setNumeroExpediente(poPago.getNumeroExpediente());
		return oPago;
	}

	private Subsanacion getSubsanacionWS(ieci.tecdoc.sgm.core.services.consulta.Subsanacion poSub){
		if(poSub == null){
			return null;
		}
		Subsanacion oSub = new Subsanacion();
		oSub.setFecha(poSub.getFecha());
		oSub.setIdDocumento(poSub.getIdDocumento());
		oSub.setIdentificador(poSub.getIdentificador());
		oSub.setIdentificadorHito(poSub.getIdentificadorHito());
		oSub.setMensajeParaElCiudadano(poSub.getMensajeParaElCiudadano());
		oSub.setNumeroExpediente(poSub.getNumeroExpediente());
		return oSub;
	}

	private File obtenerArchivoLocal(InfoDocumento poInfo) throws IOException{
		File ofile = File.createTempFile(TEMP_FILE_PREFIX, poInfo.getExtension());
		Base64.decodeToFile(poInfo.getContent(), ofile.getAbsolutePath());
		return ofile;
	}

	private boolean getRetornoLogicoServicio(RetornoLogico poLogico){
		if( (poLogico == null) || (poLogico.getValor() == null)
				|| ("".equals(poLogico.getValor())
				|| (ConstantesServicios.LABEL_FALSE.equals(poLogico.getValor())) )
		){
			return false;
		} else{
			return true;
		}
	}

	private ieci.tecdoc.sgm.core.services.consulta.Expedientes getExpedientesServicio(Expedientes poExps){
		if(poExps == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Expedientes oExps = new ieci.tecdoc.sgm.core.services.consulta.Expedientes();
		List lExps = new ArrayList();
		if(poExps.getExpedientes() != null){
			for(int i=0; i<poExps.getExpedientes().length; i++){
				lExps.add((ieci.tecdoc.sgm.core.services.consulta.Expediente)getExpedienteServicio(poExps.getExpedientes()[i]));
			}
		}
		oExps.setExpedientes(lExps);
		return oExps;
	}

	private Expedientes getExpedientesWS(ieci.tecdoc.sgm.core.services.consulta.Expedientes poExps){
		if(poExps == null){
			return null;
		}
		Expedientes oExps = new Expedientes();
		if(poExps.getExpedientes() != null){
			Expediente[] lExps = new Expediente[poExps.getExpedientes().size()];
			Iterator oIterador = poExps.getExpedientes().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				lExps[i] = getExpedienteWS((ieci.tecdoc.sgm.core.services.consulta.Expediente)oIterador.next());
				i++;
			}
			oExps.setExpedientes(lExps);
		}
		return oExps;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Expediente getExpedienteServicio(Expediente poExp){
		if(poExp == null){
			return null;
		}

		ieci.tecdoc.sgm.core.services.consulta.Expediente oExp = new ieci.tecdoc.sgm.core.services.consulta.Expediente();
		try{
			oExp.setAportacion(poExp.getAportacion());
			oExp.setCodigoPresentacion(poExp.getCodigoPresentacion());
			oExp.setEstado(poExp.getEstado());
			oExp.setFechaInicio(DateTimeUtil.getDate(poExp.getFechaInicio(), ConstantesServicios.DATE_PATTERN));
			oExp.setFechaRegistro(DateTimeUtil.getDate(poExp.getFechaRegistro(), ConstantesServicios.DATE_PATTERN));
			oExp.setInformacionAuxiliar(poExp.getInformacionAuxiliar());
			oExp.setNotificacion(poExp.getNotificacion());
			oExp.setNumero(poExp.getNumero());
			oExp.setNumeroRegistro(poExp.getNumeroRegistro());
			oExp.setProcedimiento(poExp.getProcedimiento());
		}catch(Exception e){
			return null;
		}
		return oExp;
	}

	private CriterioBusquedaExpedientes getCriterioBusquedaWS(ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes poCriterio){
		if(poCriterio == null){
			return null;
		}
		CriterioBusquedaExpedientes oCriterio = new CriterioBusquedaExpedientes();
		oCriterio.setEstado(poCriterio.getEstado());
		oCriterio.setFechaDesde(poCriterio.getFechaDesde());
		oCriterio.setFechaHasta(poCriterio.getFechaHasta());
		oCriterio.setNIF(poCriterio.getNIF());
		oCriterio.setOperadorConsulta(poCriterio.getOperadorConsulta());

		oCriterio.setExpediente(poCriterio.getExpediente());
		oCriterio.setProcedimiento(poCriterio.getProcedimiento());
		oCriterio.setNumeroRegistroInicial(poCriterio.getNumeroRegistroInicial());
		oCriterio.setFechaRegistroInicialDesde(poCriterio.getFechaRegistroInicialDesde());
		oCriterio.setFechaRegistroInicialHasta(poCriterio.getFechaRegistroInicialHasta());
		oCriterio.setOperadorConsultaFechaInicial(poCriterio.getOperadorConsultaFechaInicial());
		return oCriterio;
	}

	private HitoExpediente getHitoExpedienteWS(ieci.tecdoc.sgm.core.services.consulta.HitoExpediente poHito){
		if(poHito == null){
			return null;
		}
		HitoExpediente oHito = new HitoExpediente();
		oHito.setCodigo(poHito.getCodigo());
		oHito.setDescripcion(poHito.getDescripcion());
		oHito.setFecha(poHito.getFecha());
		oHito.setGuid(poHito.getGuid());
		oHito.setInformacionAuxiliar(poHito.getInformacionAuxiliar());
		oHito.setNumeroExpediente(poHito.getNumeroExpediente());
		return oHito;
	}

	private ieci.tecdoc.sgm.core.services.consulta.HitoExpediente getHitoExpedienteServicio(ieci.tecdoc.sgm.ct.ws.client.HitoExpediente poHito){
		if(poHito == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.HitoExpediente oHito = new ieci.tecdoc.sgm.core.services.consulta.HitoExpediente();
		oHito.setCodigo(poHito.getCodigo());
		oHito.setDescripcion(poHito.getDescripcion());
		oHito.setFecha(poHito.getFecha());
		oHito.setGuid(poHito.getGuid());
		oHito.setInformacionAuxiliar(poHito.getInformacionAuxiliar());
		oHito.setNumeroExpediente(poHito.getNumeroExpediente());
		return oHito;
	}

	private HitosExpediente getHitosExpedienteWS(ieci.tecdoc.sgm.core.services.consulta.HitosExpediente poHitos){
		if(poHitos == null){
			return null;
		}
		HitosExpediente oHitos = new HitosExpediente();
		if(poHitos.getHitosExpediente() != null){
			HitoExpediente[] lHitos = new HitoExpediente[poHitos.getHitosExpediente().size()];
			Iterator oIterador = poHitos.getHitosExpediente().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				lHitos[i] = getHitoExpedienteWS((ieci.tecdoc.sgm.core.services.consulta.HitoExpediente)oIterador.next());
				i++;
			}
			oHitos.setExpedientes(lHitos);
		}
		return oHitos;
	}

	private Interesado getInteresadoWS(ieci.tecdoc.sgm.core.services.consulta.Interesado poInt){
		if(poInt == null){
			return null;
		}
		Interesado oInt = new Interesado();
		oInt.setInformacionAuxiliar(poInt.getInformacionAuxiliar());
		oInt.setExpedientes(getExpedientesWS(poInt.getExpedientes()));
		oInt.setNIF(poInt.getNIF());
		oInt.setNombre(poInt.getNombre());
		oInt.setNumeroExpediente(poInt.getNumeroExpediente());
		oInt.setPrincipal(poInt.getPrincipal());
		return oInt;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Notificaciones getNotificacionesServicio(Notificaciones poNots){
		if(poNots == null){
			return null;
		}

		ieci.tecdoc.sgm.core.services.consulta.Notificaciones oNots = new ieci.tecdoc.sgm.core.services.consulta.Notificaciones();
		if(poNots.getNotificaciones() != null){
			ArrayList lNots = new ArrayList();
			for(int i=0; i<poNots.getNotificaciones().length; i++){
				lNots.add(getNotificacionServicio(poNots.getNotificaciones()[i]));
			}
			oNots.setNotificaciones(lNots);
		}else{
			oNots.setNotificaciones(new ArrayList());
		}
		return oNots;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Pagos getPagosServicio(Pagos poPagos){
		if(poPagos == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Pagos oPagos = new ieci.tecdoc.sgm.core.services.consulta.Pagos();
		ArrayList lPagos = null;
		if(poPagos.getPagos() != null){
			lPagos = new ArrayList(poPagos.getPagos().length);
			for(int i=0; i < poPagos.getPagos().length; i++){
				lPagos.add(getPagoServicio(poPagos.getPagos()[i]));
			}
		}else{
			lPagos = new ArrayList();
		}
		oPagos.setPagos(lPagos);
		return oPagos;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Subsanaciones getSubsanacionesServicio(Subsanaciones poSubs){
		if(poSubs == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Subsanaciones oSubs = new ieci.tecdoc.sgm.core.services.consulta.Subsanaciones();
		ArrayList lSubs = null;
		if(poSubs.getSubsanaciones() != null){
			lSubs = new ArrayList(poSubs.getSubsanaciones().length);
			for(int i=0; i < poSubs.getSubsanaciones().length; i++){
				lSubs.add(getSubsanacionServicio(poSubs.getSubsanaciones()[i]));
			}
		}else{
			lSubs = new ArrayList();
		}
		oSubs.setSubsanaciones(lSubs);
		return oSubs;
	}

	private ieci.tecdoc.sgm.core.services.consulta.Subsanacion getSubsanacionServicio(Subsanacion poSub){
		if(poSub == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.Subsanacion oSub = new ieci.tecdoc.sgm.core.services.consulta.Subsanacion();
		oSub.setFecha(poSub.getFecha());
		oSub.setIdDocumento(poSub.getIdDocumento());
		oSub.setIdentificador(poSub.getIdentificador());
		oSub.setIdentificadorHito(poSub.getIdentificadorHito());
		oSub.setMensajeParaElCiudadano(poSub.getMensajeParaElCiudadano());
		oSub.setNumeroExpediente(poSub.getNumeroExpediente());
		return oSub;
	}

	private ieci.tecdoc.sgm.core.services.consulta.HitosExpediente getHitosExpedienteServicio(HitosExpediente poHitos){
		if(poHitos == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.HitosExpediente oHitos = new ieci.tecdoc.sgm.core.services.consulta.HitosExpediente();
		ArrayList lHitos = null;
		if(poHitos.getHitosExpedientes() != null){
			lHitos = new ArrayList(poHitos.getHitosExpedientes().length);
			for(int i=0; i < poHitos.getHitosExpedientes().length; i++){
				lHitos.add(getHitoExpedienteServicio(poHitos.getHitosExpedientes()[i]));
			}
		}else{
			lHitos = new ArrayList();
		}
		oHitos.setHitosExpediente(lHitos);
		return oHitos;
	}

	private ieci.tecdoc.sgm.core.services.consulta.FicheroHito getFicheroHitoServicio(FicheroHito poFichero){
		if(poFichero == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.FicheroHito oFichero = new ieci.tecdoc.sgm.core.services.consulta.FicheroHito();
		oFichero.setGuid(poFichero.getGuid());
		oFichero.setGuidHito(poFichero.getGuidHito());
		oFichero.setTitulo(poFichero.getTitulo());
		return oFichero;
	}

	private ieci.tecdoc.sgm.core.services.consulta.FicherosHito getFicherosHitoServicio(FicherosHito poFicheros){
		if(poFicheros == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.FicherosHito oFicheros = new ieci.tecdoc.sgm.core.services.consulta.FicherosHito();
		ArrayList lFicheros = null;
		if(poFicheros.getFicherosHito() != null){
			lFicheros = new ArrayList(poFicheros.getFicherosHito().length);
			for(int i=0; i < poFicheros.getFicherosHito().length; i++){
				lFicheros.add(getFicheroHitoServicio(poFicheros.getFicherosHito()[i]));
			}
		}else{
			lFicheros = new ArrayList();
		}
		oFicheros.setFicheros(lFicheros);
		oFicheros.setGuidHito(poFicheros.getGuid());
		return oFicheros;
	}

	private ieci.tecdoc.sgm.core.services.consulta.FicherosHitos getFicherosHitos(FicherosHitos poFichs){
		if(poFichs == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.FicherosHitos oFichs = new ieci.tecdoc.sgm.core.services.consulta.FicherosHitos();
		ArrayList lFichs = null;
		if(poFichs.getFicherosHitos() != null){
			lFichs = new ArrayList(poFichs.getFicherosHitos().length);
			for(int i=0; i < poFichs.getFicherosHitos().length; i++){
				lFichs.add(getFicherosHitoServicio(poFichs.getFicherosHitos()[i]));
			}
		}else{
			lFichs = new ArrayList();
		}
		oFichs.setFicherosHitos(lFichs);
		return oFichs;
	}

	private Historico getHistoricoWS(boolean pbHistorico){
		Historico oHistorico = new Historico();
		if(pbHistorico){
			oHistorico.setPasoHistoricoHitoActual(ConstantesServicios.LABEL_TRUE);
		}else{
			oHistorico.setPasoHistoricoHitoActual(ConstantesServicios.LABEL_FALSE);
		}
		return oHistorico;
	}

	private FicheroHito getFicheroHitoWS(ieci.tecdoc.sgm.core.services.consulta.FicheroHito poFich){
		if(poFich == null){
			return null;
		}
		FicheroHito oFich = new FicheroHito();
		oFich.setGuid(poFich.getGuid());
		oFich.setGuidHito(poFich.getGuidHito());
		oFich.setTitulo(poFich.getTitulo());
		return oFich;
	}

	private FicherosHito getFicherosHitoWS(ieci.tecdoc.sgm.core.services.consulta.FicherosHito poFichs){
		if(poFichs == null){
			return null;
		}
		FicherosHito oFichs = new FicherosHito();
		if(poFichs.getFicheros() != null){
			FicheroHito[] lFichs = new FicheroHito[poFichs.getFicheros().size()];
			Iterator oIterador = poFichs.getFicheros().iterator();
			int i = 0;
			while(oIterador.hasNext()){
				lFichs[i] = getFicheroHitoWS((ieci.tecdoc.sgm.core.services.consulta.FicheroHito)oIterador.next());
				i++;
			}
			oFichs.setFicherosHito(lFichs);
		}
		return oFichs;
	}

	private ieci.tecdoc.sgm.ct.ws.client.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad ==  null){
			return null;
		}
		ieci.tecdoc.sgm.ct.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.ct.ws.client.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		oEntidad.setNombre(poEntidad.getNombre());
		return oEntidad;
	}
}
