package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;

public abstract class GenerateLibroActasSesionesAction extends
		GenerateLibroAction {

	private static final Logger logger = Logger
			.getLogger(GenerateLibroActasSesionesAction.class);

	public String getIdTpDocContenido() throws ISPACException {

		return ctx.getAPI().getCatalogAPI().getIdTpDocByCode(
				SecretariaConstants.COD_TPDOC_ACTA_SP);

	}

	public String[] getIdTpDocContenidoAdicional() throws ISPACException {

		String[] idTpDocContenidoAdicional = new String[1];
		idTpDocContenidoAdicional[0] = ctx.getAPI().getCatalogAPI()
				.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_C_ERROR_SP);
		return idTpDocContenidoAdicional;
	}

	public abstract String getCodPcd() throws ISPACException;

	protected abstract String getIdTpDocLibro() throws ISPACException;

	/**
	 * Las actas que pertenecen al libro serán aquellas que estén firmandas y
	 * pertenecezcan a expediente de seción plenaria cuya fecha de sesión
	 * pertenezca al año para el que estamos generando el ibro
	 *
	 * @throws ISPACException
	 */
	public String getQueryContenido(String initDay, String endDay,
			String idTpDocContenido, String numexp) throws ISPACException {

		return " WHERE  "
				+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
				+ ".NUMEXP IN "
				+ "( SELECT SPAC_EXPEDIENTES.NUMEXP FROM SPAC_EXPEDIENTES WHERE  SPAC_EXPEDIENTES.CODPROCEDIMIENTO='"
				+ getCodPcd()
				+ "' )AND "
				+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
				+ "."
				+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
				+ " >= "
				+ initDay
				+ " AND "
				+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
				+ "."
				+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION
				+ " <= "
				+ endDay
				+ " AND ID_TPDOC="
				+ idTpDocContenido
				+ " AND ESTADOFIRMA='"
				+ DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)
				+ "' AND  "
				+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
				+ ".NUMEXP=SPAC_DT_DOCUMENTOS.NUMEXP ORDER BY "
				+ SecretariaConstants.ENTITY_SESIONES_PLENARIAS
				+ "."
				+ SecretariaConstants.FIELD_SESIONES_PLENARIAS_FECHA_CELEBRACION;

	}

	// Diligencia de correccion error de actas
	public String getQueryContenidoAdicional(String initDay, String endDay,
			String[] idTpDocContenido, String numexp) throws ISPACException {

		if (idTpDocContenido != null && idTpDocContenido.length > 0) {
			// De momento solo soportamos un tipo docuemntal de contenido
			// adicional, por eso idTpDocCotenido[0]
			return " WHERE  SPAC_EXPEDIENTES.CODPROCEDIMIENTO='"
					+ getCodPcd()
					+ "' AND  SPAC_EXPEDIENTES.NUMEXP ='"
					+ DBUtil.replaceQuotes(numexp)
					+ "' AND "
					+ " ID_TPDOC="
					+ idTpDocContenido[0]
					+ " AND ESTADOFIRMA='"
					+ DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)
					+ "' AND SPAC_EXPEDIENTES.NUMEXP=SPAC_DT_DOCUMENTOS.NUMEXP ";

		}
		return "";

	}

	public String getTableNameToAddQuery() {
		return SecretariaConstants.ENTITY_SESIONES_PLENARIAS;
	}

}
