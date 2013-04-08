package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.juntas;

import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.InitEntitySesionesRule;

public class InitJuntaGobiernoRule extends InitEntitySesionesRule{

	public int getTipoContador() {
		return SecretariaConstants.TIPO_CONTADOR_JUNTA_GOBIERNO;
	}

}
