package ieci.tecdoc.sgm.tram.secretaria.rules.libro;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags.GetNumHojasLibroRule;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

public class GetNumHojasLibroActasRule extends GetNumHojasLibroRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
			.getLogger(GetNumHojasLibroActasRule.class);

	public int getNumHojasContenido(String initDate, String finDate,
			String numexp) throws Exception {
		// Obtenemos los id's de los tipos documentales

		DbCnt cnt = new DbCnt();
		DbResultSet dbRs = null;
		int num_hojas = 0;

		String codPcd = ConfigurationMgr.getVarGlobal(ctx,
				SecretariaConstants.COD_PCD_SESIONES_PLENARIAS);
		// String
		// codPcdLibroActasJG=ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_LIBRO_ACTAS_JG
		// );

		IInvesflowAPI invesflowAPI = ctx.getAPI();
		IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();
		ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
		IItemCollection itemcol = entitiesAPI.getEntities(
				SpacEntities.SPAC_EXPEDIENTES, numexp);
		if (itemcol.next()) {

			IItem expediente = itemcol.value();

			if (StringUtils.equalsIgnoreCase(expediente
					.getString("CODPROCEDIMIENTO"), ConfigurationMgr
					.getVarGlobal(ctx,
							SecretariaConstants.COD_PCD_LIBRO_ACTAS_JG))) {
				codPcd = ConfigurationMgr.getVarGlobal(ctx,
						SecretariaConstants.COD_PCD_JUNTA_GOBIERNO);
			}

		}

		try {
			cnt.getConnection();
			// Acta firmada y fecha de SESION (NO DE FIRMA) de la sesión entre
			// el 1-enero al 31-diciembre.
			// Las Diligencia de corrección de error que se incluyen son las
			// diligencias de las actas que satisfacen las condiciones del punto
			// anterior

			String idTpDocActa = catalogAPI
					.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_ACTA_SP);
			String idTpDocCEActa = catalogAPI
					.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_C_ERROR_SP);

			// Primero compruebo que no hay ningún decreto y/o diligencia
			// corrección de error firmada pero sin cerrar el trámite
			// En ese caso se devuelve -1 para mostrar mensaje de error.

			String query = " SELECT count("
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".ID) AS TRAM_SINCERRAR "
					+ " FROM "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ",SPAC_EXPEDIENTES WHERE "
					+ " SPAC_EXPEDIENTES.NUMEXP="
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP IN ( SELECT SPAC_EXPEDIENTES.NUMEXP FROM SPAC_DT_DOCUMENTOS , SPAC_TRAMITES, SPAC_EXPEDIENTES WHERE "
					+ " SPAC_EXPEDIENTES.CODPROCEDIMIENTO='"
					+ codPcd
					+ "' AND SPAC_DT_DOCUMENTOS.NUMEXP=SPAC_EXPEDIENTES.NUMEXP  AND SPAC_DT_DOCUMENTOS.ID_TRAMITE=SPAC_TRAMITES.ID  AND "
					+ "SPAC_DT_DOCUMENTOS.ESTADOFIRMA='"
					+ DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)
					+ "' AND ID_TPDOC="
					+ idTpDocActa
					+ " )"
					+ "AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
					+ ">="
					+ initDate
					+ "AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
					+ "<=" + finDate;
			dbRs = cnt.executeQuery(query);
			ResultSet rs = dbRs.getResultSet();
			if (rs.next()) {
				int sinCerrar = rs.getInt("TRAM_SINCERRAR");
				if (sinCerrar > 0) {
					return -1;
				}

			}

			query = " SELECT count("
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".ID) AS TRAM_SINCERRAR "
					+ " FROM "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ","
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR
					+ ",SPAC_EXPEDIENTES WHERE "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP="
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR
					+ ".NUMEXP AND SPAC_EXPEDIENTES.NUMEXP="
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP IN ( SELECT SPAC_EXPEDIENTES.NUMEXP FROM SPAC_DT_DOCUMENTOS , SPAC_TRAMITES, SPAC_EXPEDIENTES WHERE "
					+ " SPAC_EXPEDIENTES.CODPROCEDIMIENTO='"
					+ codPcd
					+ "' AND SPAC_DT_DOCUMENTOS.NUMEXP=SPAC_EXPEDIENTES.NUMEXP  AND SPAC_DT_DOCUMENTOS.ID_TRAMITE=SPAC_TRAMITES.ID  AND "
					+ "SPAC_DT_DOCUMENTOS.ESTADOFIRMA='"
					+ DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)
					+ "' AND ID_TPDOC="
					+ idTpDocCEActa
					+ ")"
					+ "AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
					+ ">="
					+ initDate
					+ "AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
					+ "<=" + finDate;
			dbRs = cnt.executeQuery(query);
			rs = dbRs.getResultSet();
			if (rs.next()) {
				int sinCerrar = rs.getInt("TRAM_SINCERRAR");
				if (sinCerrar > 0) {
					return -1;
				}

			}

			query = " SELECT SUM("
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_NUM_HOJAS_ACTA
					+ ")AS NUM_HOJAS "
					+ " FROM "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ",SPAC_EXPEDIENTES WHERE SPAC_EXPEDIENTES.NUMEXP="
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP IN ( SELECT SPAC_EXPEDIENTES.NUMEXP FROM SPAC_DT_DOCUMENTOS , SPAC_EXPEDIENTES WHERE "
					+ " SPAC_EXPEDIENTES.CODPROCEDIMIENTO='"
					+ codPcd
					+ "' AND SPAC_DT_DOCUMENTOS.NUMEXP=SPAC_EXPEDIENTES.NUMEXP AND "
					+ "SPAC_DT_DOCUMENTOS.ESTADOFIRMA='"
					+ DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)
					+ "' AND ID_TPDOC="
					+ idTpDocActa
					+ " )"
					+ "AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
					+ ">="
					+ initDate
					+ "AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
					+ "<=" + finDate;

			// Ejecutamos la consulta
			dbRs = cnt.executeQuery(query);
			rs = dbRs.getResultSet();
			if (rs.next()) {
				num_hojas = rs.getInt("NUM_HOJAS");
			}

			query = " SELECT SUM("
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR
					+ "."
					+ SecretariaConstants.FIELD_DILIGENCIA_C_ERROR_NUM_HOJAS
					+ ")AS NUM_HOJAS "
					+ " FROM "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ","
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR
					+ ",SPAC_EXPEDIENTES WHERE "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP="
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR
					+ ".NUMEXP AND SPAC_EXPEDIENTES.NUMEXP="
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ ".NUMEXP IN ( SELECT SPAC_EXPEDIENTES.NUMEXP FROM SPAC_DT_DOCUMENTOS , SPAC_EXPEDIENTES WHERE "
					+ " SPAC_EXPEDIENTES.CODPROCEDIMIENTO='"
					+ codPcd
					+ "' AND SPAC_DT_DOCUMENTOS.NUMEXP=SPAC_EXPEDIENTES.NUMEXP AND "
					+ "SPAC_DT_DOCUMENTOS.ESTADOFIRMA='"
					+ DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)
					+ "' AND ID_TPDOC="
					+ idTpDocActa
					+ " )"
					+ "AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
					+ ">="
					+ initDate
					+ "AND "
					+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
					+ "."
					+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
					+ "<=" + finDate;

			// Ejecutamos la consulta
			dbRs = cnt.executeQuery(query);
			rs = dbRs.getResultSet();
			if (rs.next()) {
				num_hojas += rs.getInt("NUM_HOJAS");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("GetNumHojasLibroActasRule:getNumHojasContenido"
						+ num_hojas);
			}

		} catch (Exception e) {
			logger.error("GetNumHojasLibroActasRule:getNumHojasContenido" + e);
			throw (e);
		} finally {
			if (cnt != null) {
				cnt.closeConnection();
			}
		}

		return num_hojas;
	}

	public String getKeyMessage() throws Exception {

		return "error.calculate.hojaFin.acta";
	}

}
