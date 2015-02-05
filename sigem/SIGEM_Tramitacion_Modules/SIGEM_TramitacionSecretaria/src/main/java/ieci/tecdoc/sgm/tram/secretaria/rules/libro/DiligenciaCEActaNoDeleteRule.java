package ieci.tecdoc.sgm.tram.secretaria.rules.libro;

import ieci.tdw.ispac.api.rule.docs.DocumentNoDeleteRule;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;



public class DiligenciaCEActaNoDeleteRule extends DocumentNoDeleteRule {


	public String getCodTipoDocumental() {
		return SecretariaConstants.COD_TPDOC_C_ERROR_SP;
	}

	public String getKeyMessage(){
		return "aviso.cantdelete.document.correccion.acta";
	}

}
