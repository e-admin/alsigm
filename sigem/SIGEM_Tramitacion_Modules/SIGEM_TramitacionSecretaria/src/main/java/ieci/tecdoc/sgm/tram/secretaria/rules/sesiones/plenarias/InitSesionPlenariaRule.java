package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.plenarias;

import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.InitEntitySesionesRule;

public class InitSesionPlenariaRule extends InitEntitySesionesRule{

	public int getTipoContador() {
		return SecretariaConstants.TIPO_CONTADOR_SESIONES_PLENARIAS;
	}

}
