package ieci.tecdoc.sgm.tram.secretaria.rules.libro;

import ieci.tdw.ispac.api.rule.docs.DocumentNoDeleteRule;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;



public class DiligenciaCEDecretoNoDeleteRule extends DocumentNoDeleteRule {


	public String getCodTipoDocumental() {
		return SecretariaConstants.COD_TPDOC_C_ERROR_DEC;
	}

	public String getKeyMessage(){
		return "aviso.cantdelete.document.correccion.dec";
	}

}
