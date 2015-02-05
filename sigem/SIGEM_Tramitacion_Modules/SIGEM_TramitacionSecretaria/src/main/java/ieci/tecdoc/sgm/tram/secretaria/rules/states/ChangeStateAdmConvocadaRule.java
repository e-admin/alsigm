package ieci.tecdoc.sgm.tram.secretaria.rules.states;

import ieci.tdw.ispac.api.rule.states.ChangeStateAdmRule;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;




/**
 * Establecer Estado del expediente  a  CONVOCADA
 *
 *
 */
 	public class ChangeStateAdmConvocadaRule extends ChangeStateAdmRule {




 	        public String getEstado() {

 	                return SecretariaConstants.VALUE_CONVOCADA;
 	        }

}