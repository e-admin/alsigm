package ieci.tecdoc.sgm.antivirus;

import ieci.tecdoc.sgm.core.services.antivirus.AntivirusException;
import ieci.tecdoc.sgm.core.services.antivirus.ServicioAntivirus;

public class NoAntivirus extends Antivirus implements ServicioAntivirus {

	protected boolean comprobarFichero(String rutaFichero) throws AntivirusException {
		//return true;
		throw new AntivirusException(AntivirusException.EXC_NO_ANTIVIRUS);
	}
	
}
