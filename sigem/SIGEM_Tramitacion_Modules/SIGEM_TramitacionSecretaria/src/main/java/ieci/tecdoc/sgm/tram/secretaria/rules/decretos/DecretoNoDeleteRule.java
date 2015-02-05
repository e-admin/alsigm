package ieci.tecdoc.sgm.tram.secretaria.rules.decretos;

import ieci.tdw.ispac.api.rule.docs.DocumentNoDeleteRule;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

import java.util.Locale;

public class DecretoNoDeleteRule extends DocumentNoDeleteRule {

	public String getCodTipoDocumental() {
		return SecretariaConstants.COD_TPDOC_DECRETO;
	}

	public String getMessage(Locale locale){
		return SecretariaResourcesHelper.getMessage(locale,"aviso.cantdelete.document.decreto") ;
	}

}
