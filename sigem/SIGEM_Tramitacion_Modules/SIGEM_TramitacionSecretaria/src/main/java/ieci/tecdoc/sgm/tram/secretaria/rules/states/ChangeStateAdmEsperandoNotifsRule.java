package ieci.tecdoc.sgm.tram.secretaria.rules.states;

import ieci.tdw.ispac.api.rule.states.ChangeStateAdmRule;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;




/**
 * Establecer Estado del expedient  a ESPERANDO_NOTIFICACIONES
 *
 *
 */
 public class ChangeStateAdmEsperandoNotifsRule extends ChangeStateAdmRule {




 	        public String getEstado() {

 	                return SecretariaConstants.VALUE_ESPERANDO_NOTIFICACIONES;
 	        }

}