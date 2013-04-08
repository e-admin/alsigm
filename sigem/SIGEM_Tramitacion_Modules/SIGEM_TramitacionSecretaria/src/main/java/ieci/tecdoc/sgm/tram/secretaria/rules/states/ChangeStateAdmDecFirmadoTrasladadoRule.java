package ieci.tecdoc.sgm.tram.secretaria.rules.states;

import ieci.tdw.ispac.api.rule.states.ChangeStateAdmRule;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;



/**
 * Establecer Estado del expedient  a DECRETO_FIRMADO_TRASLADADO
 *
 *
 */
 	public class ChangeStateAdmDecFirmadoTrasladadoRule extends ChangeStateAdmRule {




 	        public String getEstado() {

 	                return SecretariaConstants.VALUE_DECRETO_FIRMADO_TRASLADADO;
 	        }

}