package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IReportsAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventInformeVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTReportOrgDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.util.FileReportManager;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

/**
 * Implementación del API de gestión de informes.
 * 
 */
public class ReportsAPI implements IReportsAPI {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ReportsAPI.class);

	/**
	 * Contexto del cliente.
	 */
	private ClientContext context;
	private IspacAuditoriaManager auditoriaManager;

	/**
	 * Constructor.
	 * 
	 * @param context
	 *            Contexto del cliente.
	 */
	public ReportsAPI(ClientContext context) {
		this.context = context;
		this.auditoriaManager = new IspacAuditoriaManagerImpl();
	}

	/**
	 * Comprueba que el XML de la plantilla del informe sea válido.
	 * 
	 * @param xml
	 *            XML de la plantilla del informe.
	 * @return true si el XML es válido, false en caso contrario.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public boolean checkReport(String xml) throws ISPACException {

		boolean isValid = false;
		File templateFile = null;

		try {

			FileReportManager fileReportManager = new FileReportManager();
			templateFile = fileReportManager.createTemplateFile(xml);

			JasperCompileManager.compileReport(new FileInputStream(templateFile));

			isValid = true;

		} catch (ISPACException e) {
			logger.error("La plantilla del informe no es válida", e);
			throw e;
		} catch (JRException e) {
			logger.error("La plantilla del informe no es válida", e);
			throw new ISPACException("La plantilla del informe no es válida", e);
		} catch (Exception e) {
			logger.error("Error al validar la plantilla del informe", e);
			throw new ISPACException("Error al validar la plantilla del informe", e);
		} finally {
			if (templateFile != null) {
				templateFile.delete();
			}
		}

		return isValid;
	}

	/**
	 * Genera el informe en formato PDF.
	 * 
	 * @param ctReport
	 *            Información del informe.
	 * @param position
	 *            Posición que se imprime.
	 * @param numExp
	 *            Número de expediente.
	 * @param locale
	 *            Locale del cliente.
	 * @param out
	 *            Salida del informe.
	 * @param stageId
	 *            Fase actual sobre la que se esta trabajando
	 * @param taskId
	 *            Traámite actual sobre el que se esta trabajando
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void generatePDFReport(IItem ctReport, String position, String numExp, Locale locale,
			OutputStream out, int stageId, int taskId, String[] numExpedientes)
			throws ISPACException {

		generatePDFReport(ctReport, position, numExp, locale, out, stageId, taskId, numExpedientes,
				null);

	}

	/**
	 * Compila la plantilla de un informe.
	 * 
	 * @param ctReport
	 *            Información del informe.
	 * @return Fichero de la plantilla compilada.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public File compileReport(IItem ctReport) throws ISPACException {

		File templateFile = null;

		try {

			FileReportManager fileReportMgr = new FileReportManager();

			// Crear el fichero con la plantilla original
			templateFile = fileReportMgr.createTemplateFile(ctReport);

			// Fichero para generar la plantilla compilada
			File jasperFile = fileReportMgr.getJasperFile(ctReport);

			// Compilar la plantilla original
			JasperCompileManager.compileReportToStream(new FileInputStream(templateFile),
					new FileOutputStream(jasperFile));

			// Eliminar la plantilla original
			templateFile.delete();

			return jasperFile;

		} catch (Exception e) {
			logger.error("Error al compilar la plantilla del informe." + e.getMessage(), e);
			throw new ISPACException("Error al compilar la plantilla del informe", e);
		} finally {
			if (templateFile != null) {
				templateFile.delete();
			}
		}
	}

	/**
	 * Genera un informe en formato PDF a partir de una plantilla.
	 * 
	 * @param template
	 *            Plantilla en formato jrxml.
	 * @param params
	 *            Parámetros del informe.
	 * @param con
	 *            Conexión con la base de datos.
	 * @param out
	 *            Informe en PDF.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void generatePDFReport(InputStream template, Map params, Connection con, OutputStream out)
			throws ISPACException {
		if (logger.isDebugEnabled()) {
			logger.debug("generatePDFReport(InputStream, Map, Connection, OutputStream) - start");
		}

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(template, params, con);
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			// TODO: Auditar
			auditGeneracionInforme(params);
		} catch (JRException e) {
			logger.error("Error al generar el informe en PDF", e);
			throw new ISPACException("Error al genera el informe", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("generatePDFReport(InputStream, Map, Connection, OutputStream) - end");
		}
	}

	/**
	 * 
	 * Audita la generación del informe
	 * 
	 * @param params
	 */
	private void auditGeneracionInforme(Map params) {
		AuditContext auditContext = AuditContextHolder.getAuditContext();

		IspacAuditEventInformeVO evento = new IspacAuditEventInformeVO();
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		evento.setInformeEjecutado(params.toString());
		evento.setFecha(new Date());
		evento.setUser("");
		evento.setIdUser("");

		if (auditContext != null) {
			evento.setIdUser(auditContext.getUserId());
			evento.setUser(auditContext.getUser());
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la ejecución del informe");
		auditoriaManager.audit(evento);
	}

	/**
	 * Genera un informe en formato RTF a partir de una plantilla compilada.
	 * 
	 * @param template
	 *            Plantilla compilada.
	 * @param params
	 *            Parámetros del informe.
	 * @param con
	 *            Conexión con la base de datos.
	 * @param out
	 *            Informe en PDF.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public void generateRTFReport(InputStream template, Map params, Connection con, OutputStream out)
			throws ISPACException {
		try {

			JasperPrint jasperPrint = JasperFillManager.fillReport(template, params, con);

			JRRtfExporter exporter = new JRRtfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			exporter.exportReport();
			
			//Auditar ejecución informe
			auditGeneracionInforme(params);

		} catch (JRException e) {
			logger.error("Error al generar el informe en PDF", e);
			throw new ISPACException("Error al generar el informe en PDF", e);
		}
	}

	/**
	 * Obtiene la lista de responsables para los que se ha asignado el informe.
	 * 
	 * @param reportId
	 *            Identificador del informe.
	 * @return Lista de responsables asignados al informe.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public List getReportOrganization(int reportId) throws ISPACException {

		IInvesflowAPI invesflowAPI = context.getAPI();
		ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();

		String query = " WHERE ID_INFORME = " + reportId;
		IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_INFORMES_ORG,
				query);

		IRespManagerAPI respAPI = context.getAPI().getRespManagerAPI();

		// Obtener los responsables
		IItemCollection permissionObjectOrganizaction = respAPI.getInfoResponsibles(itemcol);
		List permissionObjectOrganizactionList = CollectionBean
				.getBeanList(permissionObjectOrganizaction);
		Collections.sort(permissionObjectOrganizactionList, Responsible.getRespComparator());

		return permissionObjectOrganizactionList;
	}

	/**
	 * Elimina los responsables recibidos en responsiblesIds del informe cuyo id
	 * sea el recibido en idReport
	 * 
	 * @param idReport
	 *            Identificador del informe
	 * @param responsibleIds
	 *            Array con la lista de responsable a desasociar del informe
	 * @throws ISPACException
	 */
	public void deleteResponsiblesReport(String idReport, String[] responsibleIds)
			throws ISPACException {

		DbCnt cnt = context.getConnection();

		try {
			CTReportOrgDAO.deletePermission(cnt, idReport, responsibleIds);
		} catch (ISPACException ie) {
			throw new ISPACException("Error en CustomAPI:deleteResponsiblesReport(" + idReport
					+ ", responsibleIds[" + responsibleIds + "]" + ")", ie);
		} finally {
			context.releaseConnection(cnt);
		}

	}

	public void validatePDFReportParams(Map values, Map types) throws ISPACException {

		Map extraParams = new HashMap();

		DbCnt cnt = context.getConnection();

		try {
			Iterator it = values.entrySet().iterator();
			while (it.hasNext()) {

				Entry entry = (Entry) it.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();

				String type = (String) types.get(key);
				if (type != null) {

					if (type.equals("interval")) {

						String initDate = getInitIntervalDate(value, ";");
						String endDate = getEndIntervalDate(value, ";");

						if (!GenericValidator.isDate(initDate, "dd/MM/yyyy", true)
								|| !GenericValidator.isDate(endDate, "dd/MM/yyyy", true)) {

							throw new ISPACInfo("exception.date.invalid", value, false);
						}

						try {
							if (DateUtil.compare(DateUtil.getDate(initDate),
									DateUtil.getDate(endDate)) == 1) {

								throw new ISPACInfo("exception.date.interval.invalid", value, false);
							}
						} catch (ISPACInfo ii) {
							throw ii;
						} catch (Exception e) {
							throw new ISPACException(e);
						}

						entry.setValue(DBUtil.getToTimestampByBD(cnt, initDate + " 00:00:00")
								+ " AND " + DBUtil.getToTimestampByBD(cnt, endDate + " 23:59:59"));

						extraParams.put(key + "_INIT_DATE", initDate);
						extraParams.put(key + "_END_DATE", endDate);
					} else if (type.equals("number")) {

						if (!GenericValidator.isInt(value) || !GenericValidator.isLong(value)) {

							throw new ISPACInfo("exception.number.invalid", value, false);
						}
					}
				}
			}
		} finally {
			context.releaseConnection(cnt);
		}

		values.putAll(extraParams);
	}

	public void generatePDFReport(IItem ctReport, String position, String numExp, Locale locale,
			OutputStream out, int stageId, int taskId, String[] numExpedientes, Map extraParams)
			throws ISPACException {

		DbCnt cnt = context.getConnection();

		try {

			ISPACConfiguration parameters = ISPACConfiguration.getInstance();

			String myWhere = "";
			if (!ArrayUtils.isEmpty(numExpedientes)) {
				myWhere = "(SPAC_EXPEDIENTES.NUMEXP='" + DBUtil.replaceQuotes(numExpedientes[0])
						+ "'";
				for (int i = 1; i < numExpedientes.length; i++) {
					myWhere += " OR SPAC_EXPEDIENTES.NUMEXP='"
							+ DBUtil.replaceQuotes(numExpedientes[i]) + "'";
				}
				myWhere += ")";
			}

			// Parámetros para la generación de informes
			Map params = new HashMap();
			params.put(JRParameter.REPORT_LOCALE, locale);
			params.put("IMAGES_REPOSITORY_PATH",
					parameters.get(ISPACConfiguration.IMAGES_REPOSITORY_PATH));
			params.put("NUM_EXP", numExp);
			params.put("STAGE_ID", String.valueOf(stageId));
			params.put("TASK_ID", String.valueOf(taskId));
			params.put("MY_WHERE", myWhere);
			params.put("QUERY_RESULTS", numExpedientes);

			Responsible user = context.getUser();
			if (user != null) {
				params.put("USER_UID", user.getUID());
				params.put("USER_NAME", user.getName());
				params.put("USER_RESP", user.getRespString());
				params.put("DEPT_UID", user.getOrgUnit().getUID());
				params.put("DEPT_NAME", user.getOrgUnit().getName());
				params.put("USER_RESP_STRING", user.getRespString());

				Collection groups = user.getUserGroups();
				if ((groups != null) && !groups.isEmpty()) {
					String userGroups = "";
					for (Iterator it = groups.iterator(); it.hasNext();) {
						Responsible group = (Responsible) it.next();
						if (StringUtils.isNotBlank(userGroups)) {
							userGroups += ",";
						}
						userGroups += "'" + DBUtil.replaceQuotes(group.getUID()) + "'";
					}

					params.put("USER_GROUPS", userGroups);
				}
			}

			if (position != null) {
				params.put("POSICION", position);
			}

			if (extraParams != null) {
				params.putAll(extraParams);
			}
			// Fecha de versión del informe
			Date ctReportDate = ctReport.getDate("FECHA");

			// Comprobar si existe una plantilla compilada
			FileReportManager fileReportMgr = new FileReportManager();
			File jasperFile = fileReportMgr.getJasperFile(ctReport);
			if ((jasperFile == null) || !jasperFile.exists()
					|| (jasperFile.lastModified() < ctReportDate.getTime())) {
				jasperFile = compileReport(ctReport);
			}

			// Generar el informe en formato PDF
			generatePDFReport(new FileInputStream(jasperFile), params, cnt.getConnection(), out);

		} catch (Exception e) {
			logger.error("Error al generar el informe", e);
			throw new ISPACException("Error al generar el informe", e);
		} finally {
			context.releaseConnection(cnt);
		}
	}

	private String getInitIntervalDate(String value, String separator) {

		return StringUtils.substring(value, 0, StringUtils.indexOf(value, separator));
	}

	private String getEndIntervalDate(String value, String separator) {

		return StringUtils.substring(value, StringUtils.indexOf(value, separator) + 1);
	}

}
