package ieci.tecdoc.sgm.tram.secretaria.rules.libro;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;



public class SetNumHojasActaRule extends SetNumHojasRule {



	public String getEntityName() {
		return SecretariaConstants.ENTITY_SESIONES_PLENARIAS;
	}

	public String getFieldNumHojasName() {
		return SecretariaConstants.FIELD_SESIONES_PLENARIAS_NUM_HOJAS_ACTA;
	}

	public String getIdTpDoc() throws ISPACException {

		return  ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_ACTA_SP);
	}


	public String getKeyMessage(){
		return "aviso.cant.close.acta.sinFirma";
	}

}
