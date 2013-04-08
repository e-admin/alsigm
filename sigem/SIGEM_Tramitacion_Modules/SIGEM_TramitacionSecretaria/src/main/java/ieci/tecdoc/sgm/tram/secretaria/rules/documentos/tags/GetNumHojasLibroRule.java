package ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACRuleException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.rule.IRule;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.api.rule.helper.RuleHelper;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

import java.io.File;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * Devuelve el número de hojas del libro SIN TENER EN CUENTA LA DILIGENCIA DE
 * CIERRE
 *
 * @author IECISA
 *
 */

public abstract class GetNumHojasLibroRule implements IRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
			.getLogger(GetNumHojasLibroRule.class);
	private IItem diligenciaApertura = null;
	protected IClientContext ctx = null;

	public void cancel(IRuleContext rulectx) throws ISPACRuleException {

	}

	public Object execute(IRuleContext rulectx) throws ISPACRuleException {

		int num_hojas = 0;
		ctx = rulectx.getClientContext();
		File sourceFile = null;
		DbCnt cnt = null;
		try {

			IInvesflowAPI invesflowAPI = ctx.getAPI();
			cnt = rulectx.getClientContext().getConnection();
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			ISignAPI signAPI = invesflowAPI.getSignAPI();
			int hoja_ini = 0;
			int anio = 0;
			// Obtenemos el año del libro
			IItemCollection itemcol = entitiesAPI.getEntities(
					SecretariaConstants.ENTITY_LIBRO, rulectx.getNumExp());
			if (itemcol.next()) {
				IItem libro = itemcol.value();
				anio = libro.getInt(SecretariaConstants.FIELD_LIBRO_ANIO);
				hoja_ini = libro
						.getInt(SecretariaConstants.FIELD_LIBRO_NUM_HOJA_INI);

			} else {
				logger.warn("No hay registro de la entidad libro ");
				return "0";
			}

			// Obtenemos la diligencia de apertura y contamos el número de
			// página
			int num_hojas_dil_apertura = signAPI
					.getNumHojasDocumentSigned(diligenciaApertura
							.getString("INFOPAG_RDE"));
			if (logger.isDebugEnabled()) {
				logger
						.debug("GetNumHojasLibroRule :execute número de páginas de la diligencia Apertura"
								+ num_hojas_dil_apertura);
			}
			// Obtenemos el número de hojas del contenido del libro para el
			// ejercicio actual

			GregorianCalendar initDayGregorianCalendar = new GregorianCalendar();
			initDayGregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH,
					initDayGregorianCalendar
							.getMinimum(GregorianCalendar.DAY_OF_MONTH));
			initDayGregorianCalendar.set(GregorianCalendar.MONTH,
					initDayGregorianCalendar
							.getMinimum(GregorianCalendar.MONTH));
			initDayGregorianCalendar
					.set(GregorianCalendar.HOUR, initDayGregorianCalendar
							.getMinimum(GregorianCalendar.HOUR));
			initDayGregorianCalendar.set(GregorianCalendar.MINUTE,
					initDayGregorianCalendar
							.getMinimum(GregorianCalendar.MINUTE));
			initDayGregorianCalendar.set(GregorianCalendar.SECOND,
					initDayGregorianCalendar
							.getMinimum(GregorianCalendar.SECOND));
			initDayGregorianCalendar.set(GregorianCalendar.YEAR, anio);
			String initDay = DBUtil.getToDateByBD(cnt, initDayGregorianCalendar
					.getTime());

			GregorianCalendar finDayGregorianCalendar = new GregorianCalendar();
			finDayGregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH,
					finDayGregorianCalendar
							.getMaximum(GregorianCalendar.DAY_OF_MONTH));
			finDayGregorianCalendar
					.set(GregorianCalendar.MONTH, finDayGregorianCalendar
							.getMaximum(GregorianCalendar.MONTH));
			finDayGregorianCalendar.set(GregorianCalendar.HOUR,
					finDayGregorianCalendar.getMaximum(GregorianCalendar.HOUR));
			finDayGregorianCalendar.set(GregorianCalendar.MINUTE,
					finDayGregorianCalendar
							.getMaximum(GregorianCalendar.MINUTE));
			finDayGregorianCalendar.set(GregorianCalendar.SECOND,
					finDayGregorianCalendar
							.getMaximum(GregorianCalendar.SECOND));
			finDayGregorianCalendar.set(GregorianCalendar.YEAR, anio);
			Timestamp ts = new Timestamp(finDayGregorianCalendar
					.getTimeInMillis());
			String endDay = DBUtil.getToTimestampByBD(cnt, ts);

			int num_hojas_contenido = getNumHojasContenido(initDay, endDay,
					rulectx.getNumExp());
			if(num_hojas_contenido==-1){

				if(logger.isInfoEnabled()){
					logger.info("Error en GetNumHojasLibroRule:execute->No se puede calcular el número final de la hoja del libro porque existen " +
							"trámites de Diligencia de Corrección de error sin cerrar y/o trámties de creación de decreto");
				}
				   throw new ISPACRuleException(SecretariaResourcesHelper.getMessage(rulectx.getClientContext().getLocale(),getKeyMessage()));
			}
			num_hojas = hoja_ini + num_hojas_dil_apertura + num_hojas_contenido
					- 1; // -1 porque la hoja ini ya pertenece al libro
			if (logger.isDebugEnabled()) {
				logger.debug("Número de hoja final del ibro  de libro "
						+ num_hojas);
			}

		} catch (Exception e) {
			logger.error("Error en GetNumHojasLibroRule:execute", e);
			throw new ISPACRuleException("Error GetNumHojasLibroRule:execute"
					+ e);
		} finally {
			if (cnt != null) {
				rulectx.getClientContext().releaseConnection(cnt);
			}
			if (sourceFile != null) {
				try {
					FileTemporaryManager.getInstance().delete(sourceFile);
				} catch (ISPACException e) {
					logger.error(e);
					throw new ISPACRuleException(e);
				}
			}
		}
		return num_hojas + "";

	}

	public boolean init(IRuleContext rulectx) throws ISPACRuleException {

		return true;
	}

	public boolean validate(IRuleContext rulectx) throws ISPACRuleException {
		IClientContext ctx = rulectx.getClientContext();
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		try {
			IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
			ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
			String numexp = rulectx.getNumExp();
			String idTpDocDiligenciaApertura = catalogAPI
					.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_DILIGENCIA_APERTURA);
			IItemCollection itemCollectionApertura = entitiesAPI.queryEntities(
					ISPACEntities.DT_ID_DOCUMENTOS, "WHERE NUMEXP = '"
							+ DBUtil.replaceQuotes(numexp) + "' AND ID_TPDOC="
							+ idTpDocDiligenciaApertura + " AND ESTADOFIRMA='"
							+ DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)
							+ "' AND NUMEXP='" + rulectx.getNumExp() + "'");

			if (itemCollectionApertura.next()) {
				diligenciaApertura = itemCollectionApertura.value();

			} else {
				logger
						.warn("No se ha generado y/o firmado la diligencia de apertura");
				rulectx.setInfoMessage(SecretariaResourcesHelper.getMessage(rulectx
						.getClientContext().getLocale(),
						"errorGetNumHojasLibro.noDiligenciaApertura"));
				return false;

			}
		} catch (ISPACException e) {
			logger.error("Error en GetNumHojasLibroRule:validate", e);
			throw new ISPACRuleException("ErrorGetNumHojasLibroRule:validate"
					+ e);
		}
		return true;
	}

	public abstract int getNumHojasContenido(String initDate, String finDate,
			String numexp) throws Exception;

	public abstract String getKeyMessage() throws Exception;
}
