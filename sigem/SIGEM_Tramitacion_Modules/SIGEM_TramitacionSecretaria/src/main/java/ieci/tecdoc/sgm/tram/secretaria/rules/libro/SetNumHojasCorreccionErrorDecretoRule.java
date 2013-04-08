package ieci.tecdoc.sgm.tram.secretaria.rules.libro;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

/**
 *Establece el número de hojas de la corrección Error de los decretos
 *
 */
public class SetNumHojasCorreccionErrorDecretoRule extends SetNumHojasRule {

	public String getEntityName() {
		return SecretariaConstants.ENTITY_DILIGENCIA_C_ERROR;
	}

	public String getFieldNumHojasName() {
		return SecretariaConstants.FIELD_DILIGENCIA_C_ERROR_NUM_HOJAS;
	}

	public String getIdTpDoc() throws ISPACException {
		IInvesflowAPI invesflowAPI = ctx.getAPI();
		ICatalogAPI catalogAPI = invesflowAPI.getCatalogAPI();
		return catalogAPI
				.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_C_ERROR_DEC);

	}

	/**
	 * Indica si es un documento asociado a nivel de entidad
	 *
	 * @return
	 */
	public boolean isDocReg() {
		return true;
	}

	public String getKeyMessage(){
		return "aviso.cant.close.diligenciaCorreccionError.sinFirma";
	}


}
