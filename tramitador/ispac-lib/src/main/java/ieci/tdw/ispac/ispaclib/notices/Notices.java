/*
 * Created on Mar 9, 2005
 */
package ieci.tdw.ispac.ispaclib.notices;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.IspacAuditoriaValorModificado;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoBajaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventAvisoModificacionVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispactx.TXConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Bandeja de avisos para el tramitador
 * 
 */
public class Notices {

	/**
	 * 
	 */
	private static final String ESTADO_AVISO = "ESTADO_AVISO";
	public static final int TIPO_AVISO_SIGEM = 1;
	public static final int TIPO_AVISO_RT = 2;
	public static final int TIPO_AVISO_EXTERNO = 3;
	public static final int TIPO_AVISO_TRAMITE_DELEGADO = 4;
	public static final int TIPO_AVISO_EXPEDIENTE_INICIADO_WS = 5;
	public static final int TIPO_AVISO_DOCS_ANEXADOS_WS = 6;
	public static final int TIPO_AVISO_EXP_REUBICADO_WS = 7;
	public static final int TIPO_AVISO_EXP_CAMBIO_ESTADO_ADM_WS = 8;
	public static final int TIPO_AVISO_FASE_DELEGADA = 9;
	public static final int TIPO_AVISO_ACTIVIDAD_DELEGADA = 10;
	public static final int TIPO_AVISO_TRAMITE_FINALIZADO = 11;

	private static final long serialVersionUID = -894977694134548019L;
	public static final int STATE_PENDIENTE = 0;
	public static final int STATE_RECIBIDO = 1;
	public static final int STATE_FINALIZADO = 2;

	private static final String NOTICES_TBL = "SPAC_AVISOS_ELECTRONICOS";
	private static final String IDKEY = "ID_AVISO";
	private static final String IDSEQUENCE = "SPAC_SQ_ID_AVISOS_ELECTRONICOS";

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Notices.class);

	final ClientContext mctx;
	private static IspacAuditoriaManager auditoriaManager = new IspacAuditoriaManagerImpl();

	public Notices(ClientContext ctx) {
		mctx = ctx;
	}

	/**
	 * @return el identificador de entidad Avisos.
	 */
	private int mapNoticeEntityId() throws ISPACException {

		DbCnt cnt = mctx.getConnection();
		try {
			// TODO: Poder cambiar el nombre en propiedades de la app
			CTEntityDAO eDAO = (CTEntityDAO) ObjectDAO.getByName(cnt, CTEntityDAO.class,
					NOTICES_TBL);
			return eDAO.getInt("ID");
		} finally {
			mctx.releaseConnection(cnt);
		}
	}

	public IItemCollection getNotices() throws ISPACException {
		Responsible user = mctx.getUser();
		String querynotices = "WHERE "
				+ DBUtil.addInResponsibleCondition("UID_DESTINATARIO", user.getRespString())
				+ " AND ESTADO_AVISO IN (0,1) AND SPAC_PROCESOS.ESTADO!="
				+ TXConstants.STATUS_DELETED + " AND ID_EXPEDIENTE=SPAC_PROCESOS.NUMEXP"
				+ " AND SPAC_PROCESOS.TIPO = " + IProcess.PROCESS_TYPE;

		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable("SPAC_V_AVISOS_ELECTRONICOS", "SPAC_AVISOS_ELECTRONICOS");
		factory.addTable("SPAC_PROCESOS", "SPAC_PROCESOS");

		IItemCollection collection = factory.queryDistinctTableJoin(mctx.getConnection(),
				querynotices).disconnect();

		return collection;
	}

	public int countNotices() throws ISPACException {
		Responsible user = mctx.getUser();
		String querynotices = "WHERE "
				+ DBUtil.addInResponsibleCondition("UID_DESTINATARIO", user.getRespString())
				+ " AND ESTADO_AVISO IN (0,1) AND SPAC_PROCESOS.ESTADO!="
				+ TXConstants.STATUS_DELETED + " AND ID_EXPEDIENTE=SPAC_PROCESOS.NUMEXP"
				+ " AND SPAC_PROCESOS.TIPO = " + IProcess.PROCESS_TYPE;

		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(NOTICES_TBL, NOTICES_TBL);
		factory.addTable("SPAC_PROCESOS", "SPAC_PROCESOS");

		return factory.countTableJoin(mctx.getConnection(), querynotices);

	}

	public void modifyNotice(int noticeId, int newstate) throws ISPACException {
		Responsible user = mctx.getUser();

		String querynotices = "WHERE ID_AVISO = " + noticeId;
		IInvesflowAPI invesflowAPI = mctx.getAPI();
		IItemCollection notices = invesflowAPI.getEntitiesAPI().queryEntities(mapNoticeEntityId(),
				querynotices);
		if (!notices.next())
			return;

		IItem notice = notices.value();

		int currentstate = notice.getInt(ESTADO_AVISO);
		if (currentstate == Notices.STATE_PENDIENTE && newstate == Notices.STATE_RECIBIDO) {
			// TODO Rellenar además ID_RESP ?
			notice.set("UID_RESP", user.getUID());
		}

		notice.set(ESTADO_AVISO, newstate);
		notice.store(mctx);

		// TODO: Auditar la modificación del estado del aviso
		auditModifyNotice(noticeId, currentstate, newstate);

	}

	/**
	 * @param newstate
	 */
	private void auditModifyNotice(int noticeId, int currentstate, int newstate) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventAvisoModificacionVO evento = new IspacAuditEventAvisoModificacionVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setIdAviso(String.valueOf(noticeId));
		List<IspacAuditoriaValorModificado> valoresModificados = new ArrayList<IspacAuditoriaValorModificado>();
		IspacAuditoriaValorModificado valorModificado = new IspacAuditoriaValorModificado();
		valorModificado.setFieldName(ESTADO_AVISO);
		valorModificado.setNewValue(String.valueOf(newstate));
		valorModificado.setOldValue(String.valueOf(currentstate));
		valoresModificados.add(valorModificado);

		evento.setValoresModificados(valoresModificados);
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la consulta del aviso");
		auditoriaManager.audit(evento);
	}

	/**
	 * Elimina el aviso del expedediente que recibe por parámetro
	 * 
	 * @param cnt
	 * @param numExp
	 * @throws ISPACException
	 */
	public static void deleteExpOfAllNotices(DbCnt cnt, String numExp) throws ISPACException {

		
		
		String sql = "DELETE FROM " + NOTICES_TBL + " WHERE ID_EXPEDIENTE = '" + numExp + "' ";
		cnt.directExec(sql);

		// TODO: Auditar. Problema: Se eliminan en bloque y no sabemos las que
		// se eliminan por lo que se guarda el número de expediente.

		auditEliminacionAviso(numExp);

		if (logger.isDebugEnabled()) {
			logger.debug("Notices: deleteExpOfAllNotices--> Se han eleminados todos los avisos electrónicos relativos al numExp:"
					+ numExp);
		}

	}

	/**
	 * @param numExp
	 */
	private static void auditEliminacionAviso(String numExp) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();
		IspacAuditEventAvisoBajaVO evento = new IspacAuditEventAvisoBajaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setIdAviso(numExp);
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());			
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la eliminación del aviso");
		auditoriaManager.audit(evento);
	}

	public static int generateNotice(IClientContext cct, int processId, int stageId, int taskId,
			String numexp, String message, String idResp, int tipoAviso) throws ISPACException {
		int taskPcdId = 0;
		int stagePcdId = 0;
		try {
			IItem itemTask = cct.getAPI().getTask(taskId);
			taskPcdId = itemTask.getInt("ID_TRAMITE");
		} catch (ISPACNullObject e) {
		}
		try {
			IItem itemStage = cct.getAPI().getStage(stageId);
			stagePcdId = itemStage.getInt("ID_FASE");
		} catch (ISPACNullObject e) {
		}

		return generateNotice(cct, processId, stageId, taskId, numexp, message, idResp, tipoAviso,
				stagePcdId, taskPcdId);
	}

	public static int generateNotice(IClientContext cct, int processId, int stageId, int taskId,
			String numexp, String message, String idResp, int tipoAviso, int stagePcdId,
			int taskPcdId) throws ISPACException {

		IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();
		IItem notice = entitiesAPI.createEntity(SpacEntities.SPAC_AVISOS_ELECTRONICOS);

		notice.set("ID_PROC", processId);
		notice.set("TIPO_DESTINATARIO", 1);
		notice.set("FECHA", new Date());
		notice.set("ID_EXPEDIENTE", numexp);
		if (stageId != 0) {
			notice.set("ID_FASE", stageId);
		}
		if (taskId != 0) {
			notice.set("ID_TRAMITE", taskId);
		}
		notice.set(ESTADO_AVISO, STATE_PENDIENTE);
		notice.set("MENSAJE", message);
		notice.set("TIPO_AVISO", tipoAviso);

		// Generar el aviso al responsable del proceso
		notice.set("UID_DESTINATARIO", idResp);

		notice.set("ID_FASE_PCD", stagePcdId);
		notice.set("ID_TRAMITE_PCD", taskPcdId);
		notice.store(cct);

		// TODO: Auditar creación del aviso

		auditGenerateNotice(cct, notice);

		return notice.getKeyInt();
	}

	/**
	 * @param cct
	 * @param notice
	 * @throws ISPACException
	 */
	private static void auditGenerateNotice(IClientContext cct, IItem notice) throws ISPACException {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventAvisoAltaVO evento = new IspacAuditEventAvisoAltaVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setIdAviso(notice.getKey().toString());
		evento.setNewValue(notice.toXml());
		evento.setUser("");
		evento.setIdUser("");
		evento.setUserHostName("");
		evento.setUserIp("");

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la creación del aviso");
		auditoriaManager.audit(evento);
	}

	public int generateDelegateObjectNotice(int processId, int stageId, int taskId, String numexp,
			String message, String idResp, int tipoAviso) throws ISPACException {
		if (taskId != 0 && tipoAviso != Notices.TIPO_AVISO_ACTIVIDAD_DELEGADA) {
			archiveDelegateTaskNotice(stageId, taskId);
		} else {
			archiveDelegateStageNotice(stageId);
		}
		return generateNotice(mctx, processId, stageId, taskId, numexp, message, idResp, tipoAviso);
	}

	public void archiveDelegateTaskNotice(int stageId, int taskId) throws ISPACException {
		String query = "WHERE ID_TRAMITE = " + taskId + " AND TIPO_AVISO = '"
				+ TIPO_AVISO_TRAMITE_DELEGADO + "' AND ESTADO_AVISO IN (" + STATE_PENDIENTE + ","
				+ STATE_RECIBIDO + ")";
		archiveDelegateObjectNotice(query);
	}

	public void archiveDelegateStageNotice(int stageId) throws ISPACException {
		String query = "WHERE ID_FASE = " + stageId + " AND TIPO_AVISO  IN ('"
				+ TIPO_AVISO_FASE_DELEGADA + "', '" + TIPO_AVISO_ACTIVIDAD_DELEGADA
				+ "') AND ESTADO_AVISO IN (" + STATE_PENDIENTE + "," + STATE_RECIBIDO + ")";
		archiveDelegateObjectNotice(query);
	}

	private void archiveDelegateObjectNotice(String query) throws ISPACException {
		IEntitiesAPI entitiesAPI = mctx.getAPI().getEntitiesAPI();

		IItemCollection itemcol = entitiesAPI.queryEntities(SpacEntities.SPAC_AVISOS_ELECTRONICOS,
				query);
		while (itemcol.next()) {
			IItem item = itemcol.value();
			modifyNotice(item.getKeyInt(), STATE_FINALIZADO);
		}
	}

}