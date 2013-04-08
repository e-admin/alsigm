package ieci.tecdoc.sgm.tram.secretaria.rules.decretos;

import java.util.Locale;

import ieci.tdw.ispac.api.rule.docs.CheckDocumentSignedRule;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaResourcesHelper;

public class CheckDocumentDiligenciaAperturaSignedRule extends CheckDocumentSignedRule {


    public  String getNameVarCodTipoDocumental(){
    	return SecretariaConstants.COD_TPDOC_DILIGENCIA_APERTURA;

    }



	public String getMessage(Locale locale) {
		return SecretariaResourcesHelper.getMessage(locale,"aviso.cantclose.diligenciaApertura.notSigned") ;
	}



}
