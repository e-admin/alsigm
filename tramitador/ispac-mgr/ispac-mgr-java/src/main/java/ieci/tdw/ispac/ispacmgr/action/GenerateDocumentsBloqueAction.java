package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.TableDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GenerateDocumentsBloqueAction extends BaseDispatchAction {

	/** Logger de la clase. */
	private static final Logger logger = Logger
			.getLogger(GenerateDocumentsBloqueAction.class);

	public ActionForward selectParticipantes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {

		if (logger.isDebugEnabled()) {
			logger
					.debug("GenerateDocumentBloqueAction:selectParticipantes->Inicio");
		}
		ClientContext cct = session.getClientContext();
		// Se obtiene el estado de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(
				cct);
		IState currentState = managerAPI.currentState(getStateticket(request));

		// Se tratan los paramétros necesarios para la generación de documentos
		// en bloque a través de plantilla
		int documentTypeId = Integer.parseInt(request
				.getParameter("documentTypeId"));
		int templateId = Integer.parseInt(request.getParameter("templateId"));

		if (documentTypeId < 1 || templateId < 1) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("GenerateDocumentBloqueAction:selectParticipantes->Los valores parámetros templateId:"
								+ templateId
								+ "documentTypeId"
								+ documentTypeId + " no son válidos");
			}
			request.setAttribute("error", "true");
			return mapping.findForward("participantes");

		}

		// Obtener la lista de participantes: Ojo con el sustituto del rol si
		// existe y sino en vacío

		IItemCollection participantes = null;
		Property[] cols = new Property[] {
				new Property(1, "ID", "SPAC_DT_INTERVINIENTES.ID",
						Types.INTEGER),
				new Property(2, "NDOC", "SPAC_DT_INTERVINIENTES.NDOC",
						Types.VARCHAR),
				new Property(3, "NOMBRE", "SPAC_DT_INTERVINIENTES.NOMBRE",
						Types.VARCHAR),
				new Property(4, "ROL", "SPAC_TBL_002.SUSTITUTO", Types.VARCHAR), };

		final String tables = "SPAC_DT_INTERVINIENTES LEFT OUTER JOIN SPAC_TBL_002 ON SPAC_DT_INTERVINIENTES.ROL=SPAC_TBL_002.VALOR";

		DbCnt cnt = cct.getConnection();
		String conditions = " WHERE  SPAC_DT_INTERVINIENTES.NUMEXP='"
				+ currentState.getNumexp() + "'";

		try {

			CollectionDAO results = TableDAO.newCollectionDAO(TableDAO.class,
					tables, cols);

			results.query(cnt, conditions);
			participantes = results.disconnect();

		} finally {
			cct.releaseConnection(cnt);
		}

		List registros = CollectionBean.getBeanList(participantes);

		request.setAttribute("participantes", registros);
		request.setAttribute("documentTypeId", String.valueOf(documentTypeId));
		request.setAttribute("documentType", String.valueOf(templateId));

		if (logger.isDebugEnabled()) {
			logger
					.debug("GenerateDocumentBloqueAction:selectParticipantes->Fin");
		}
		return mapping.findForward("participantes");

	}

	public ActionForward generateDocumentsBloque(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {

		if (logger.isDebugEnabled()) {
			logger
					.info("GenerateDocumentBloqueAction:generateDocumentsBloques->Inicio");
		}
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();
		// Se obtiene el estado de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(
				cct);
		IState currentState = managerAPI.currentState(getStateticket(request));

		EntityForm frm = (EntityForm) form;
		// En el multibox solo llegan los ids de los participantes que están
		// checkeados
		String[] participantes = frm.getMultibox();

		if (participantes != null && participantes.length > 0) {

			String query = " ID IN (" + participantes[0];
			int i = 1;
			for (i = 1; i < participantes.length; i++) {
				query += " , " + participantes[i];
			}
			query += ") ";
			IItemCollection registros = entitiesAPI.getEntities(
					SpacEntities.SPAC_DT_INTERVINIENTES, currentState
							.getNumexp(), query);

			EntityDAO entityDAO = null;
			Iterator itr = registros.iterator();
			String id_tp_doc = request.getParameter("documentTypeId");
			String id_plant = request.getParameter("templateId");
			// Generar el documento a través de la plantilla para cada uno de
			// los registros
			Object connectorSession = null;
			connectorSession = genDocAPI.createConnectorSession();
			if (StringUtils.isBlank(id_tp_doc) || StringUtils.isBlank(id_plant)) {
				request.setAttribute("error", "true");
				return mapping.findForward("participantes");
			} else {
				IItem entityDocument = null;
				IItem entityTemplate = null;

				int taskPcdId = currentState.getTaskPcdId();
				int stagePcdId = currentState.getStagePcdId();
				int pTramite = currentState.getTaskId();
				int pFase = currentState.getStageId();

				while (itr.hasNext()) {
					// Abrir transacción para que no se pueda generar un
					// documento sin fichero
					cct.beginTX();
					boolean bCommit = false;
					try {
						entityDAO = (EntityDAO) itr.next();
						if (logger.isDebugEnabled()) {
							logger
									.debug("GenerateDocumentBloqueAction:generateDocumentsBloques->Se procederá a crear el documento");
						}

						int regId = Integer.parseInt(entityDAO.getString("ID"));
						String idExt = entityDAO.getString("ID_EXT");
						String destinoTipo = entityDAO
								.getString("TIPO_PERSONA");

						// Si no es validado
						if (StringUtils.isBlank(idExt)) {
							idExt = "0";
							destinoTipo = "N";
						}

						// Crea el registro en la entidad
						entityDocument = genDocAPI.createTaskDocument(
								currentState.getTaskId(), Integer
										.parseInt(id_tp_doc),
								SpacEntities.SPAC_DT_INTERVINIENTES, regId);

						entityDocument.set("ID_FASE", pFase);
						entityDocument.set("ID_TRAMITE", pTramite);
						entityDocument.set("ID_FASE_PCD", stagePcdId);
						entityDocument.set("ID_TRAMITE_PCD", taskPcdId);
						entityDocument.set("DESCRIPCION", entityDAO
								.getString("NOMBRE"));
						entityDocument.set("DESTINO_ID", idExt);
						entityDocument.set("DESTINO_TIPO", destinoTipo);
						entityDocument.set("DESTINO", entityDAO
								.getString("NOMBRE"));

						// Generamos el documento a partir de la plantilla
						entityTemplate = genDocAPI.attachTaskTemplate(
								connectorSession, cct.getStateContext()
										.getTaskId(), entityDocument
										.getKeyInt("ID"), Integer
										.parseInt(id_plant),
								SpacEntities.SPAC_DT_INTERVINIENTES, regId);
						// Referencia al fichero del documento en el gestor
						// documental
						String docref = entityTemplate.getString("INFOPAG");
						String sMimetype = genDocAPI.getMimeType(
								connectorSession, docref);
						entityTemplate.set("EXTENSION", MimetypeMapping
								.getExtension(sMimetype));
						entityDocument.store(cct);
						entityTemplate.store(cct);

						// Si todo ha sido correcto se hace commit de la
						// transacción
						bCommit = true;
					} catch (Throwable e) {
							logger
									.error("GenerateDocumentBloqueAction:generateDocumentsBloques->Error al crear el documento para el registro con id  "
											+ entityDAO.getString("ID") + e);
						request.setAttribute("error", "true");
						return mapping.findForward("participantes");

					} finally {

						cct.endTX(bCommit);

					}
				}
				if (connectorSession != null) {
					genDocAPI.closeConnectorSession(connectorSession);
				}
			}

		}
		if (logger.isDebugEnabled()) {
			logger
					.debug("GenerateDocumentBloqueAction:generateDocumentsBloques->Fin");
		}
		return mapping.findForward("success");
	}
}
