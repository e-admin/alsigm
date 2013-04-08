package ieci.tecdoc.sgm.ct;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.consulta.ConsultaExpedientesException;
import ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes;
import ieci.tecdoc.sgm.core.services.consulta.Expediente;
import ieci.tecdoc.sgm.core.services.consulta.Expedientes;
import ieci.tecdoc.sgm.core.services.consulta.FicherosHito;
import ieci.tecdoc.sgm.core.services.consulta.FicherosHitos;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.HitosExpediente;
import ieci.tecdoc.sgm.core.services.consulta.InfoDocumento;
import ieci.tecdoc.sgm.core.services.consulta.Interesado;
import ieci.tecdoc.sgm.core.services.consulta.Notificacion;
import ieci.tecdoc.sgm.core.services.consulta.Notificaciones;
import ieci.tecdoc.sgm.core.services.consulta.Pago;
import ieci.tecdoc.sgm.core.services.consulta.Pagos;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.consulta.Subsanacion;
import ieci.tecdoc.sgm.core.services.consulta.Subsanaciones;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.ct.database.datatypes.ExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.FicheroHitoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.HitoExpedienteImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.InteresadoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.NotificacionImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.PagoImpl;
import ieci.tecdoc.sgm.ct.database.datatypes.SubsanacionImpl;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;
import ieci.tecdoc.sgm.rde.database.DocumentoInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class ServicioConsultaExpedientesAdapter implements
		ServicioConsultaExpedientes {

	private static final Logger logger = Logger.getLogger(ServicioConsultaExpedientesAdapter.class);

	public Expedientes consultarExpedientesNIF(
			String NIF, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			ieci.tecdoc.sgm.ct.database.datatypes.Expedientes oExps = GestorConsulta.consultarExpedientes(null, NIF, entidad.getIdentificador());
			return getExpedientesServicio(oExps);
		} catch (ConsultaExcepcion e) {
			logger.error("Error en consulta de expedientes por NIF.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en consulta de expedientes por NIF.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Expedientes consultarExpedientes(CriterioBusquedaExpedientes poCriterio, Entidad entidad) throws ConsultaExpedientesException{
		try {
			ieci.tecdoc.sgm.ct.database.datatypes.Expedientes oExps =
				GestorConsulta.consultarExpedientes(null,
													poCriterio.getNIF(),
													poCriterio.getFechaDesde(),
													poCriterio.getFechaHasta(),
													poCriterio.getOperadorConsulta(),
													entidad.getIdentificador());
			return getExpedientesServicio(oExps);
		} catch (ConsultaExcepcion e) {
			logger.error("Error en consulta de expedientes.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en consulta de expedientes.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarExpediente(Expediente expediente, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			GestorConsulta.eliminarExpediente(getExpedienteImpl(expediente), entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error eliminando expediente.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error eliminando expediente.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarHitoActual(String numExp, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			GestorConsulta.eliminarHitoActual(numExp, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error eliminando hito actual.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error eliminando hito actual.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarInteresado(Interesado interesado, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			GestorConsulta.eliminarInteresado(getInteresadoImpl(interesado), entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error eliminando interesado.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error eliminando interesado.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarInteresadoExpediente(String numeroExpediente, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			GestorConsulta.eliminarInteresadoExpediente(numeroExpediente, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error eliminando interesado de expediente.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error eliminando interesado de expediente.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void establecerHitoActual(HitoExpediente hito, FicherosHito ficheros,
			boolean historico, Entidad entidad) throws ConsultaExpedientesException {
		try {
			if( (ficheros == null) || (ficheros.count() == 0)){
				GestorConsulta.establecerHitoActual(getHitoExpedienteImpl(hito), historico, entidad.getIdentificador());
			}else if((ficheros == null) || (ficheros.count() == 1)){
				GestorConsulta.establecerHitoActual(
						getHitoExpedienteImpl(hito),
						getFicheroHitoImpl((ieci.tecdoc.sgm.core.services.consulta.FicheroHito)ficheros.get(0)),
						historico,
						entidad.getIdentificador());
			}else{
				GestorConsulta.establecerHitoActual(
						getHitoExpedienteImpl(hito),
						getFicherosHitoImpl(ficheros),
						historico,
						entidad.getIdentificador());
			}
		} catch (ConsultaExcepcion e) {
			logger.error("Error estableciendo hito actual.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error estableciendo hito actual.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void nuevoExpediente(Expediente expediente, Interesado interesado, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			GestorConsulta.nuevoExpediente(
							getExpedienteImpl(expediente),
							getInteresadoImpl(interesado),
							entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error creando nuevo expediente.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error creando nuevo expediente.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void nuevoInteresado(Interesado interesado, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			GestorConsulta.nuevoInteresado(getInteresadoImpl(interesado), entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error creando nuevo interesado.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error creando nuevo interesado.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Expediente obtenerDetalle(String numeroExpediente, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			return getExpedienteServicio(GestorConsulta.obtenerDetalle(null, numeroExpediente, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo detalle de expediente.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error obteniendo detalle de expediente.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public FicherosHito obtenerFicherosHito(String guidHito, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			return getFicherosHitoServicio(GestorConsulta.obtenerFicherosHito(guidHito, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo ficheros de hito.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error obteniendo ficheros de hito.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public FicherosHitos obtenerFicherosHitos(HitosExpediente hitos, Entidad entidad) throws ConsultaExpedientesException{
		try {
			Hashtable otable = GestorConsulta.obtenerFicherosHitos(getHitosExpedienteImpl(hitos), entidad.getIdentificador());
			FicherosHitos oFichs = new FicherosHitos();
			List lfichs = new ArrayList();
			if(otable != null){
				FicherosHito oFich = null;
				Enumeration oKeys = otable.keys();
				String key = null;
				while(oKeys.hasMoreElements()){
					key = (String)oKeys.nextElement();
					oFich = getFicherosHitoServicio((ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito)otable.get(key));
					oFich.setGuidHito(key);
					lfichs.add(oFich);
				}
			}
			oFichs.setFicherosHitos(lfichs);
			return oFichs;
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo ficheros de hito.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error obteniendo ficheros de hito.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public HitosExpediente obtenerHistoricoExpediente(String numeroExpediente, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			return getHitosExpedienteServicio(GestorConsulta.obtenerHistoricoExpediente(null, numeroExpediente, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo histórico de expediente.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error obteniendo histórico de expediente.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public HitoExpediente obtenerHitoEstado(String numeroExpediente, Entidad entidad)
			throws ConsultaExpedientesException {
		try {
			return getHitoExpedienteServicio(GestorConsulta.obtenerHitoEstado(null, numeroExpediente, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo hito estado.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo hito estado.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String obtenerURLAportacionExpedientes()
			throws ConsultaExpedientesException {
		try {
			return GestorConsulta.obtenerURLAportacionExpedientes();
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo URL aportación de expedientes.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo URL aportación de expedientes.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String obtenerURLNotificacionExpedientes()
			throws ConsultaExpedientesException {
		try {
			return GestorConsulta.obtenerURLNotificacionExpedientes();
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo URL notificación expedientes.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo URL notificación de expedientes.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String obtenerURLPagoTasas()
			throws ConsultaExpedientesException {
		try {
			return GestorConsulta.obtenerURLPagoTasas();
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo URL pago de tasas.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo URL pago de tasas.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void nuevoHitoHistorico(HitoExpediente hito, Entidad entidad) throws ConsultaExpedientesException{
		try {
			GestorConsulta.nuevoHitoHistorico(getHitoExpedienteImpl(hito), entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error creando nuevo hito histórico.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado creando nuevo hito histórico.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void eliminarHitoHistorico(String numExp, Entidad entidad) throws ConsultaExpedientesException{
		try {
			GestorConsulta.eliminarHitoHistorico(numExp, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error creando eliminando hito histórico.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado eliminando nuevo hito histórico.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public String cargarDocumento(String guid, Entidad entidad)	throws ConsultaExpedientesException{
		try {
			return GestorConsulta.cargarDocumento(null, guid, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error cargando documento.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado cargando documento.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public InfoDocumento recogerDocumento(String guid, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return getInfoDocumento(GestorConsulta.recogerDocumento(null, guid, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error recuperando documento.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado recuperando documento.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Expedientes busquedaExpedientes(CriterioBusquedaExpedientes oCriterio, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return getExpedientesServicio(GestorConsulta.busquedaExpedientes(oCriterio, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error en búsqueda de expedientes.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en búsqueda de expedientes.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Expediente busquedaExpediente(String NIF, String expediente, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return getExpedienteServicio((ExpedienteImpl) GestorConsulta.busquedaExpediente(null, NIF, expediente, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error en búsqueda de expediente.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado en búsqueda de expediente.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public boolean recogerNotificaciones(String NIF, String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return GestorConsulta.recogerNotificaciones(null, NIF, numeroExpediente, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error consultando notificaciones.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado consultando notificaciones.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}


	public boolean existenNotificaciones(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return GestorConsulta.existenNotificaciones(null, numeroExpediente, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error consultando notificaciones.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado consultando notificaciones.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public boolean existenPagos(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return GestorConsulta.existenPagos(null, numeroExpediente, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error consultando pagos.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado consultando pagos.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public boolean existenSubsanaciones(String numeroExpediente, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return GestorConsulta.existenSubsanaciones(null, numeroExpediente, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error consultando subsanaciones.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado consultando subsanaciones.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public boolean actualizarEstadoLocalGIS(String idExpediente, String estado, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return GestorConsulta.actualizarEstadoLocalGIS(idExpediente, estado, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error al actualizar el estado del expediente (LocalGIS).", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al actualizar el estado del expediente (LocalGIS).", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public boolean publicarExpedienteLocalGIS(String idExpediente, String nif, String tipoExpediente, String estado, Date fecha, Entidad entidad) throws ConsultaExpedientesException{
		try {
			return GestorConsulta.publicarExpedienteLocalGIS(idExpediente, nif, tipoExpediente, estado, fecha, entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error al publicar información del expediente (LocalGIS).", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado al publicar información del expediente (LocalGIS).", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	private ConsultaExpedientesException getConsultaExpedientesException(ConsultaExcepcion poException){
		if(poException == null){
			return new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION);
		}
		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_QUERY_EXPS_ERROR_PREFIX);
		cCodigo.append(String.valueOf(poException.getErrorCode()));
		return new ConsultaExpedientesException(Long.valueOf(cCodigo.toString()).longValue(), poException);

	}

	private Expedientes getExpedientesServicio(ieci.tecdoc.sgm.ct.database.datatypes.Expedientes poExps){
		if(poExps == null){
			return null;
		}
		Expedientes oExps = new Expedientes();
		for(int i = 0; i < poExps.count(); i++){
			oExps.add(getExpedienteServicio((ExpedienteImpl)(poExps.get(i))));
		}
		return oExps;
	}

	private ieci.tecdoc.sgm.ct.database.datatypes.Expedientes getExpedientesImpl(Expedientes poExps){
		if(poExps == null){
			return null;
		}
		ieci.tecdoc.sgm.ct.database.datatypes.Expedientes oExps = new ieci.tecdoc.sgm.ct.database.datatypes.Expedientes();
		for(int i = 0; i < poExps.count(); i++){
			oExps.add(getExpedienteImpl((Expediente)(poExps.get(i))));
		}
		return oExps;
	}

	private Expediente getExpedienteServicio(ExpedienteImpl poExp){
		if(poExp == null){
			return null;
		}
		Expediente oExpediente = new Expediente();
		oExpediente.setAportacion(poExp.getAportacion());
		oExpediente.setCodigoPresentacion(poExp.getCodigoPresentacion());
		oExpediente.setEstado(poExp.getEstado());
		oExpediente.setFechaInicio(poExp.getFechaInicio());
		oExpediente.setFechaRegistro(poExp.getFechaRegistro());
		oExpediente.setInformacionAuxiliar(poExp.getInformacionAuxiliar());
		oExpediente.setNotificacion(poExp.getNotificacion());
		oExpediente.setNumero(poExp.getNumero());
		oExpediente.setNumeroRegistro(poExp.getNumeroRegistro());
		oExpediente.setProcedimiento(poExp.getProcedimiento());
		return oExpediente;
	}

	private ExpedienteImpl getExpedienteImpl(Expediente poExp){
		if(poExp == null){
			return null;
		}
		ExpedienteImpl oExpediente = new ExpedienteImpl();
		oExpediente.setAportacion(poExp.getAportacion());
		oExpediente.setCodigoPresentacion(poExp.getCodigoPresentacion());
		oExpediente.setEstado(poExp.getEstado());
		oExpediente.setFechaInicio(poExp.getFechaInicio());
		oExpediente.setFechaRegistro(poExp.getFechaRegistro());
		oExpediente.setInformacionAuxiliar(poExp.getInformacionAuxiliar());
		oExpediente.setNotificacion(poExp.getNotificacion());
		oExpediente.setNumero(poExp.getNumero());
		oExpediente.setNumeroRegistro(poExp.getNumeroRegistro());
		oExpediente.setProcedimiento(poExp.getProcedimiento());
		return oExpediente;
	}

	private InteresadoImpl getInteresadoImpl(Interesado poInteresado){
		if(poInteresado == null){
			return null;
		}
		InteresadoImpl oInteresado = new InteresadoImpl();
		oInteresado.setExpedientes(getExpedientesImpl(poInteresado.getExpedientes()));
		oInteresado.setInformacionAuxiliar(poInteresado.getInformacionAuxiliar());
		oInteresado.setNIF(poInteresado.getNIF());
		oInteresado.setNombre(poInteresado.getNombre());
		oInteresado.setNumeroExpediente(poInteresado.getNumeroExpediente());
		oInteresado.setPrincipal(poInteresado.getPrincipal());

		return oInteresado;
	}

	private HitoExpedienteImpl getHitoExpedienteImpl(HitoExpediente poHito){
		if(poHito == null){
			return null;
		}
		HitoExpedienteImpl oHito = new HitoExpedienteImpl();
		oHito.setCodigo(poHito.getCodigo());
		oHito.setDescripcion(poHito.getDescripcion());

		// Hito con fecha y hora
 		try {
 			oHito.setFecha(DateTimeUtil.getDate(poHito.getFecha(), ConstantesServicios.DATE_PATTERN));
 		} catch (Exception e) {
 			try {
	 			// Hito con sólo fecha (versiones anteriores)
	 			oHito.setFecha(DateTimeUtil.getDate(poHito.getFecha(), "dd-MM-yyyy"));
 			}catch(Exception ex){}
 		}

		oHito.setGuid(poHito.getGuid());
		oHito.setInformacionAuxiliar(poHito.getInformacionAuxiliar());
		oHito.setNumeroExpediente(poHito.getNumeroExpediente());
		return oHito;
	}

	private HitoExpediente getHitoExpedienteServicio(HitoExpedienteImpl poHito){
		if(poHito == null){
			return null;
		}
		HitoExpediente oHito = new HitoExpediente();
		oHito.setCodigo(poHito.getCodigo());
		oHito.setDescripcion(poHito.getDescripcion());

		// Hito con fecha y hora
		oHito.setFecha(DateTimeUtil.getDateTime(poHito.getFecha(), ConstantesServicios.DATE_PATTERN));

		oHito.setGuid(poHito.getGuid());
		oHito.setInformacionAuxiliar(poHito.getInformacionAuxiliar());
		oHito.setNumeroExpediente(poHito.getNumeroExpediente());
		return oHito;
	}

	private ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito getFicherosHitoImpl(FicherosHito poFicheros){
		if((poFicheros == null) || (poFicheros.getFicheros() == null)){
			return null;
		}
		ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito oFicheros = new ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito();
		Iterator oIterador = poFicheros.getFicheros().iterator();
		while(oIterador.hasNext()){
			oFicheros.add(getFicheroHitoImpl((ieci.tecdoc.sgm.core.services.consulta.FicheroHito)oIterador.next()));
		}
		return oFicheros;
	}

	private FicherosHito getFicherosHitoServicio(ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito poFicheros){
		if(poFicheros == null){
			return null;
		}
		FicherosHito oFicheros = new FicherosHito();
		for(int i = 0; i < poFicheros.count(); i++){
			oFicheros.add(getFicheroHitoServicio((FicheroHitoImpl)poFicheros.get(i)));
		}
		return oFicheros;
	}

	private FicheroHitoImpl getFicheroHitoImpl(ieci.tecdoc.sgm.core.services.consulta.FicheroHito poFichero){
		if(poFichero == null){
			return null;
		}
		FicheroHitoImpl oFichero = new FicheroHitoImpl();
		oFichero.setGuid(poFichero.getGuid());
		oFichero.setGuidHito(poFichero.getGuidHito());
		oFichero.setTitulo(poFichero.getTitulo());
		return oFichero;
	}

	private ieci.tecdoc.sgm.core.services.consulta.FicheroHito getFicheroHitoServicio(FicheroHitoImpl poFichero){
		if(poFichero == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.consulta.FicheroHito oFichero = new ieci.tecdoc.sgm.core.services.consulta.FicheroHito();
		oFichero.setGuid(poFichero.getGuid());
		oFichero.setGuidHito(poFichero.getGuidHito());
		oFichero.setTitulo(poFichero.getTitulo());
		return oFichero;
	}

	private HitosExpediente getHitosExpedienteServicio(ieci.tecdoc.sgm.ct.database.datatypes.HitosExpediente poHitos){
		if(poHitos == null){
			return null;
		}
		HitosExpediente oHitos = new HitosExpediente();
		List oLista = new ArrayList();
		for(int i = 0; i < poHitos.count(); i++){
			oLista.add((HitoExpediente)getHitoExpedienteServicio((HitoExpedienteImpl)poHitos.get(i)));
		}
		oHitos.setHitosExpediente(oLista);
		return oHitos;
	}

	private ieci.tecdoc.sgm.ct.database.datatypes.HitosExpediente getHitosExpedienteImpl(HitosExpediente poHitos){
		if(poHitos == null){
			return null;
		}
		ieci.tecdoc.sgm.ct.database.datatypes.HitosExpediente oHitos = new ieci.tecdoc.sgm.ct.database.datatypes.HitosExpediente();
		for(int i = 0; i < poHitos.count(); i++){
			oHitos.add(getHitoExpedienteImpl((HitoExpediente)poHitos.get(i)));
		}
		return oHitos;
	}

	private InfoDocumento getInfoDocumento(DocumentoInfo poDocumento){
		if(poDocumento == null){
			return null;
		}
		InfoDocumento oDocumento = new InfoDocumento();
		oDocumento.setContent(poDocumento.getContent());
		oDocumento.setExtension(poDocumento.getExtension());
		oDocumento.setGuid(poDocumento.getGuid());
		oDocumento.setMimeType(poDocumento.getMimeType());
		return oDocumento;
	}


	public void anexarFicherosHitoActual(FicherosHito ficheros, Entidad entidad)
	throws ConsultaExpedientesException {
		try {
			if (ficheros != null){
				GestorConsulta.anexarFicherosHitoActual(getFicherosHitoImpl(ficheros), entidad.getIdentificador());
			}
		} catch (ConsultaExcepcion e) {
			logger.error("Error anexando ficheros al hito actual.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error anexando ficheros al hito actual.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}


	public void altaNotificacion(Notificacion poNotificacion, Entidad entidad) throws ConsultaExpedientesException {
		try {
			GestorConsulta.altaNotificacion(getNotificacionImpl(poNotificacion), entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error dando de alta notificacion.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado dando de alta notificacion.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void altaSolicitudPago(Pago poPago, Entidad entidad) throws ConsultaExpedientesException {
		try {
			GestorConsulta.altaSolicitudPago(getPagoImpl(poPago), entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error dando de alta pago.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado dando de alta pago.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public void altaSolicitudSubsanacion(Subsanacion poSubsanacion, Entidad entidad) throws ConsultaExpedientesException {
		try {
			GestorConsulta.altaSolicitudSubsanacion(getSubsanacionImpl(poSubsanacion), entidad.getIdentificador());
		} catch (ConsultaExcepcion e) {
			logger.error("Error dando de alta subsanacion.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado dando de alta subsanacion.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Notificaciones obtenerNotificionesHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException {
		try {
			return getNotificacionesServicio(GestorConsulta.obtenerNotificionesHito(guidHito, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo notificaciones de hito.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo notificaciones de hito.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Notificaciones obtenerNotificionesHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException {
		try {
			return getNotificacionesServicio(GestorConsulta.obtenerNotificionesHitoActual(expediente, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo notificaciones de hito actual.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo notificaciones de hito actual.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Pagos obtenerPagosHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException {
		try {
			return getPagosServicio(GestorConsulta.obtenerPagosHito(guidHito, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo pagos de hito.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo pagos de hito.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Pagos obtenerPagosHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException {
		try {
			return getPagosServicio(GestorConsulta.obtenerPagosHitoActual(expediente, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo pagos de hito actual.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo pagos de hito actual.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Subsanaciones obtenerSubsanacionesHito(String guidHito, Entidad entidad) throws ConsultaExpedientesException {
		try {
			return getSubsanacionesServicio(GestorConsulta.obtenerSubsanacionesHito(guidHito, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo subsanaciones de hito.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo subsanaciones de hito.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}

	public Subsanaciones obtenerSubsanacionesHitoActual(String expediente, Entidad entidad) throws ConsultaExpedientesException {
		try {
			return getSubsanacionesServicio(GestorConsulta.obtenerSubsanacionesHitoActual(expediente, entidad.getIdentificador()));
		} catch (ConsultaExcepcion e) {
			logger.error("Error obteniendo subsanaciones de hito actual.", e);
			throw getConsultaExpedientesException(e);
		} catch (Throwable e){
			logger.error("Error inesperado obteniendo subsanaciones de hito actual.", e);
			throw new ConsultaExpedientesException(ConsultaExpedientesException.EXC_GENERIC_EXCEPCION, e);
		}
	}


	private ieci.tecdoc.sgm.ct.database.datatypes.NotificacionImpl getNotificacionImpl(Notificacion poNotificacion){
		if(poNotificacion == null){
			return null;
		}
		NotificacionImpl oNotificacion = new NotificacionImpl();
		oNotificacion.setDescripcion(poNotificacion.getDescripcion());
		oNotificacion.setDEU(poNotificacion.getDEU());
		oNotificacion.setExpediente(poNotificacion.getExpediente());
		try{
			oNotificacion.setFechaNotificacion(DateTimeUtil.getDate(poNotificacion.getFechaNotificacion(), ConstantesServicios.DATE_PATTERN));
		}catch(Exception e){}
		oNotificacion.setHitoId(poNotificacion.getHitoId());
		oNotificacion.setNotificacionId(poNotificacion.getNotificacionId());
		oNotificacion.setServicioNotificacionesId(poNotificacion.getServicioNotificionesId());

		return oNotificacion;
	}

	private ieci.tecdoc.sgm.ct.database.datatypes.SubsanacionImpl getSubsanacionImpl(Subsanacion poSubsanacion){
		if(poSubsanacion == null){
			return null;
		}
		SubsanacionImpl oSubsanacion = new SubsanacionImpl();
		oSubsanacion.setIdDocumento(poSubsanacion.getIdDocumento());
		oSubsanacion.setIdentificador(poSubsanacion.getIdentificador());
		oSubsanacion.setIdentificadorHito(poSubsanacion.getIdentificadorHito());
		oSubsanacion.setMensajeParaElCiudadano(poSubsanacion.getMensajeParaElCiudadano());
		oSubsanacion.setNumeroExpediente(poSubsanacion.getNumeroExpediente());
		try{
			oSubsanacion.setFecha(DateTimeUtil.getDate(poSubsanacion.getFecha(), ConstantesServicios.DATE_PATTERN));
		}catch(Exception e){}

		return oSubsanacion;
	}

	private ieci.tecdoc.sgm.ct.database.datatypes.PagoImpl getPagoImpl(Pago poPago){
		if(poPago == null){
			return null;
		}
		PagoImpl oPago = new PagoImpl();
		oPago.setAutoliquidacionId(poPago.getAutoliquidacionId());
		oPago.setEntidadEmisoraId(poPago.getEntidadEmisoraId());
		oPago.setImporte(poPago.getImporte());
		try{
			oPago.setFecha(DateTimeUtil.getDate(poPago.getFecha(), ConstantesServicios.DATE_PATTERN));
		}catch(Exception e){}
		oPago.setIdDocumento(poPago.getIdDocumento());
		oPago.setIdentificador(poPago.getIdentificador());
		oPago.setIdentificadorHito(poPago.getIdentificadorHito());
		oPago.setMensajeParaElCiudadano(poPago.getMensajeParaElCiudadano());
		oPago.setNumeroExpediente(poPago.getNumeroExpediente());
		return oPago;
	}

	private Pagos getPagosServicio(ieci.tecdoc.sgm.ct.database.datatypes.Pagos poPagos){
		Pagos oPagos = new Pagos();
		if(poPagos == null){
			return oPagos;
		}
		for(int i=0; i<poPagos.count(); i++)
			oPagos.add(getPagoServicio((PagoImpl)((ieci.tecdoc.sgm.ct.database.datatypes.Pagos)poPagos).get(i)));

		return oPagos;
	}

	private Notificaciones getNotificacionesServicio(ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones poNotificaciones){
		Notificaciones oNotificaciones = new Notificaciones();
		if(poNotificaciones == null){
			return oNotificaciones;
		}
		for(int i=0; i<poNotificaciones.count(); i++)
			oNotificaciones.add(getNotificacionServicio((NotificacionImpl)((ieci.tecdoc.sgm.ct.database.datatypes.Notificaciones)poNotificaciones).get(i)));

		return oNotificaciones;
	}

	private Subsanaciones getSubsanacionesServicio(ieci.tecdoc.sgm.ct.database.datatypes.Subsanaciones poSubsanaciones){
		Subsanaciones oSubsanaciones = new Subsanaciones();
		if(poSubsanaciones == null){
			return oSubsanaciones;
		}
		for(int i=0; i<poSubsanaciones.count(); i++)
			oSubsanaciones.add(getSubsanacionServicio((SubsanacionImpl)((ieci.tecdoc.sgm.ct.database.datatypes.Subsanaciones)poSubsanaciones).get(i)));

		return oSubsanaciones;
	}

	private Subsanacion getSubsanacionServicio(ieci.tecdoc.sgm.ct.database.datatypes.SubsanacionImpl poSubsanacion){
		Subsanacion oSubsanacion = new Subsanacion();
		if (poSubsanacion == null)
			return oSubsanacion;

		oSubsanacion.setFecha(poSubsanacion.getFecha().toString());
		oSubsanacion.setIdDocumento(poSubsanacion.getIdDocumento());
		oSubsanacion.setIdentificador(poSubsanacion.getIdentificador());
		oSubsanacion.setIdentificadorHito(poSubsanacion.getIdentificadorHito());
		oSubsanacion.setMensajeParaElCiudadano(poSubsanacion.getMensajeParaElCiudadano());
		oSubsanacion.setNumeroExpediente(poSubsanacion.getNumeroExpediente());

		return oSubsanacion;
	}

	private Notificacion getNotificacionServicio(ieci.tecdoc.sgm.ct.database.datatypes.NotificacionImpl poNotificacion){
		Notificacion oNotificacion = new Notificacion();
		if (poNotificacion == null)
			return oNotificacion;

		oNotificacion.setFechaNotificacion(poNotificacion.getFechaNotificacion().toString());
		oNotificacion.setDescripcion(poNotificacion.getDescripcion());
		oNotificacion.setExpediente(poNotificacion.getExpediente());
		oNotificacion.setHitoId(poNotificacion.getHitoId());
		oNotificacion.setNotificacionId(poNotificacion.getNotificacionId());
		oNotificacion.setDEU(poNotificacion.getDEU());

		return oNotificacion;
	}

	private Pago getPagoServicio(ieci.tecdoc.sgm.ct.database.datatypes.PagoImpl poPago){
		Pago oPago = new Pago();
		if (poPago == null)
			return oPago;

		oPago.setAutoliquidacionId(poPago.getAutoliquidacionId());
		oPago.setEntidadEmisoraId(poPago.getEntidadEmisoraId());
		oPago.setFecha(poPago.getFecha().toString());
		oPago.setIdDocumento(poPago.getIdDocumento());
		oPago.setIdentificador(poPago.getIdentificador());
		oPago.setIdentificadorHito(poPago.getIdentificadorHito());
		oPago.setImporte(poPago.getImporte());
		oPago.setMensajeParaElCiudadano(poPago.getMensajeParaElCiudadano());
		oPago.setNumeroExpediente(poPago.getNumeroExpediente());

		return oPago;
	}
}
