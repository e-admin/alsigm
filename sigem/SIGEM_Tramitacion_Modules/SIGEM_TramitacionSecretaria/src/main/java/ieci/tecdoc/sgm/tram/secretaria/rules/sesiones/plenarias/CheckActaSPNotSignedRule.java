package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.plenarias;

import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.CheckActaNotSignedRule;

/**
 * Estable el tipo documental asociado al acta de Sesiones Plenarias y
 * el mensaje a mostrar en caso de que no se permita la eliminación
 * @author IECISA
 *
 */
public class CheckActaSPNotSignedRule extends CheckActaNotSignedRule{

	public String getCodTipoDocumental() {
		return SecretariaConstants.COD_TPDOC_ACTA_SP;
	}

	public String getKeyMessage(){
		return "aviso.not.allowed.delete.exp.sp";
	}
}
