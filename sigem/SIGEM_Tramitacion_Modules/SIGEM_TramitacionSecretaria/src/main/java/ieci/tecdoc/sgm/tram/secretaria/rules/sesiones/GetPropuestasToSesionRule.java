package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones;

import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.rules.propuestas.GetPropuestasRule;


/**
 * Se obtienen las propuesta del tipo ordinarias o urgente con las que este relacionado el expediente actual
 * @author IECISA
 *
 */
public abstract class GetPropuestasToSesionRule extends GetPropuestasRule{


	public abstract String getTipoPropuesta();

	public String getTablePropuesta(){
		return SecretariaConstants.ENTITY_PROPUESTA_SESION;}

	public String getTableParticipantes(){
		return SecretariaConstants.ENTITY_PARTICIPANTES_SESION;}



}
