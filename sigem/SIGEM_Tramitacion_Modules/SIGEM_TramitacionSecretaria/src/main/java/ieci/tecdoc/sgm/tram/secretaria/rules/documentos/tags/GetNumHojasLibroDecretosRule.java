package ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbResultSet;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

/**
 * Devuelve el número de hoja fin del libro de decretos sin tener en cuenta la
 * diligencia de cierre.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */

public class GetNumHojasLibroDecretosRule extends GetNumHojasLibroRule {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger
			.getLogger(GetNumHojasLibroDecretosRule.class);


	public int getNumHojasContenido(String initDate, String finDate,
			String numexp) throws Exception {

		DbCnt cnt = new DbCnt();
		DbResultSet dbRs = null;
		int numHojas = 0;
		try {
			cnt.getConnection();
			IInvesflowAPI invesflowAPI = ctx.getAPI();
			ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
			String idTpDocCe = catalogAPI
					.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_C_ERROR_DEC);
			String idTpDocContenido = catalogAPI
					.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_MODELO_DECRETO);

			//Primero compruebo que no hay ningún decreto y/o diligencia corrección de error firmada pero sin cerrar el trámite
			//En ese caso se devuelve -1 para mostrar mensaje de error.
			String query = " SELECT count("+SecretariaConstants.ENTITY_DECRETO + ".ID ) AS TRAM_SINCERRAR"
				+" FROM "
				+ SecretariaConstants.ENTITY_DECRETO + " WHERE "
				+ SecretariaConstants.ENTITY_DECRETO + ".ID "
				+ "IN (SELECT  DISTINCT "
				+ SecretariaConstants.ENTITY_DECRETO + ".ID  FROM "
				+ SecretariaConstants.ENTITY_DECRETO
				+ " , SPAC_DT_DOCUMENTOS , SPAC_TRAMITES WHERE SPAC_DT_DOCUMENTOS.NUMEXP="
				+ SecretariaConstants.ENTITY_DECRETO + ".NUMEXP AND SPAC_DT_DOCUMENTOS.ID_TRAMITE=SPAC_TRAMITES.ID"
				+ " AND  "+SecretariaConstants.ENTITY_DECRETO + "."
				+ SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA + ">= "
				+ initDate + " AND "
				+ SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA + "<= "
				+ finDate + " AND" + " ID_TPDOC=" + idTpDocContenido
				+ " ) AND " + SecretariaConstants.ENTITY_DECRETO + "."
				+ SecretariaConstants.FIELD_DECRETO_NUM_DECRETO
				+ " IS NOT NULL ";

			dbRs = cnt.executeQuery(query);
			ResultSet rs = dbRs.getResultSet();
			if (rs.next()) {
				int sinCerrar = rs.getInt("TRAM_SINCERRAR");
				if(sinCerrar>0){
					return -1;
				}

			}


			 query = " SELECT SUM("
					+ SecretariaConstants.FIELD_DECRETO_NUM_HOJAS
					+ ")as NUM_HOJAS_DECRETOS FROM "
					+ SecretariaConstants.ENTITY_DECRETO + " WHERE "
					+ SecretariaConstants.ENTITY_DECRETO + ".ID "
					+ "IN (SELECT  DISTINCT "
					+ SecretariaConstants.ENTITY_DECRETO + ".ID  FROM "
					+ SecretariaConstants.ENTITY_DECRETO
					+ " , SPAC_DT_DOCUMENTOS WHERE SPAC_DT_DOCUMENTOS.NUMEXP="
					+ SecretariaConstants.ENTITY_DECRETO + ".NUMEXP AND "
					+ SecretariaConstants.ENTITY_DECRETO + "."
					+ SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA + ">= "
					+ initDate + " AND "
					+ SecretariaConstants.FIELD_DECRETO_FECHA_FIRMA + "<= "
					+ finDate + " AND" + " ID_TPDOC=" + idTpDocContenido
					+ " ) AND " + SecretariaConstants.ENTITY_DECRETO + "."
					+ SecretariaConstants.FIELD_DECRETO_NUM_DECRETO
					+ " IS NOT NULL";
			// Ejecutamos la consulta
			dbRs = cnt.executeQuery(query);
			 rs = dbRs.getResultSet();
			if (rs.next()) {
				numHojas = rs.getInt("NUM_HOJAS_DECRETOS");
			}


			query="SELECT COUNT("
			+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR + ".ID) AS TRAM_SINCERRAR "
			+ " FROM "+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR + " , "
			+ SecretariaConstants.ENTITY_DECRETO + " WHERE "
			+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR + ".ID "
			+ "IN (SELECT  DISTINCT SPAC_DT_DOCUMENTOS.ID_REG_ENTIDAD "
			+ " FROM  SPAC_DT_DOCUMENTOS   , SPAC_TRAMITES WHERE ID_TPDOC=" + idTpDocCe
			+ "  AND SPAC_DT_DOCUMENTOS.ID_TRAMITE=SPAC_TRAMITES.ID) " + "AND " + SecretariaConstants.ENTITY_DECRETO
			+ ".NUMEXP="
			+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR
			+ ".NUMEXP " + "AND " + SecretariaConstants.ENTITY_DECRETO
			+ "." + SecretariaConstants.FIELD_DECRETO_NUM_DECRETO
			+ " IS NOT NULL";
		dbRs = cnt.executeQuery(query);
		 rs = dbRs.getResultSet();
		if (rs.next()) {
			int sinCerrar = rs.getInt("TRAM_SINCERRAR");
			if(sinCerrar>0){
				return -1;
			}

		}
			// Calculamos el número de hojas de la correción de error
			query = "SELECT SUM("
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR + "."
					+ SecretariaConstants.FIELD_DILIGENCIA_C_ERROR_NUM_HOJAS
					+ ") AS NUM_HOJAS_CE " + " FROM "
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR + " , "
					+ SecretariaConstants.ENTITY_DECRETO + " WHERE "
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR + ".ID "
					+ "IN (SELECT  DISTINCT SPAC_DT_DOCUMENTOS.ID_REG_ENTIDAD "
					+ " FROM  SPAC_DT_DOCUMENTOS WHERE ID_TPDOC=" + idTpDocCe
					+ " ) " + "AND " + SecretariaConstants.ENTITY_DECRETO
					+ ".NUMEXP="
					+ SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR
					+ ".NUMEXP " + "AND " + SecretariaConstants.ENTITY_DECRETO
					+ "." + SecretariaConstants.FIELD_DECRETO_NUM_DECRETO
					+ " IS NOT NULL";

			dbRs = cnt.executeQuery(query);
			rs = dbRs.getResultSet();
			if (rs.next()) {
				numHojas += rs.getInt("NUM_HOJAS_CE");
			}

		} catch (Exception e) {
			logger.error("GetNumHojasLibroDecretosRule:getNumHojasContenido");
			throw (e);
		} finally {
			if (cnt != null) {
				cnt.closeConnection();
			}
		}

		return numHojas;
	}


	public String getKeyMessage() {
		return "error.calculate.hojaFin.decretos" 	;
	}
}
