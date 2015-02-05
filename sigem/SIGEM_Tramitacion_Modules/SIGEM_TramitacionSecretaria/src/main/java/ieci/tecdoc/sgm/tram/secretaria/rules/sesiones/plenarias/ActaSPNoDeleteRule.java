package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.plenarias;

import ieci.tdw.ispac.api.rule.docs.DocumentNoDeleteRule;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;



public class ActaSPNoDeleteRule extends DocumentNoDeleteRule {


	public String getCodTipoDocumental() {
		return SecretariaConstants.COD_TPDOC_ACTA_SP;
	}

	public String getKeyMessage(){
		return "aviso.cantdelete.document.acta.sp";
	}

}
