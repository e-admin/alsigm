package ieci.tecdoc.sgm.antivirus.mcafee;

import java.io.File;

import ieci.tecdoc.sgm.antivirus.Antivirus;
import ieci.tecdoc.sgm.core.services.antivirus.AntivirusException;
import ieci.tecdoc.sgm.core.services.antivirus.ServicioAntivirus;

public class AntivirusMcAFee extends Antivirus implements ServicioAntivirus {

	protected boolean comprobarFichero(String rutaFichero) throws AntivirusException {
		File f = new File(rutaFichero);
		if (f.exists()) {
			f = null;
			cmd = new String[3];
			cmd[0] = getRutaAnivirus();
			cmd[1] = getParametros();
			cmd[2] = "\"" + rutaFichero + "\"";
			int retorno = ejecucionAntivirus();
			
			switch (retorno) {
				case 0:
					//si el fichero existe es q no lo movio o borro=> NO TIENE VIRUS
					f = new File(rutaFichero);
					if (f.exists())
						return true;
					else
						return false;
				default:
					throw new AntivirusException(AntivirusException.EXC_EJECUCION_NO_VALIDA);
			}
		} else {
			throw new AntivirusException(AntivirusException.EXC_FICHERO_NO_ENCONTRADO);
		}
	}
	
}
