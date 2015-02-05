package se.repositoriosECM;

import java.util.Properties;

import se.repositoriosECM.impl.GestorRepositoriosECMExterno;
import se.repositoriosECM.impl.GestorRepositoriosECMInterno;
import se.repositoriosECM.impl.GestorRepositoriosECMInternoInvesdoc8;

import common.ConfigConstants;

public class GestorRepositoriosECMFactory {

	private static IGestorRepositoriosECM gestorRepositoriosECM;

	public static IGestorRepositoriosECM getGestorRepositoriosECM(
			Properties params) {
		// if(gestorRepositoriosECM == null){

		if (ConfigConstants.getInstance().isEcmExterno()) {
			gestorRepositoriosECM = new GestorRepositoriosECMExterno();
		} else {
			if(ConfigConstants.getInstance().isInvesdoc8()){
				gestorRepositoriosECM = new GestorRepositoriosECMInternoInvesdoc8();
			}
			else{
				gestorRepositoriosECM = new GestorRepositoriosECMInterno();
			}
		}

		gestorRepositoriosECM.initialize(params);
		// }
		return gestorRepositoriosECM;
	}

}
