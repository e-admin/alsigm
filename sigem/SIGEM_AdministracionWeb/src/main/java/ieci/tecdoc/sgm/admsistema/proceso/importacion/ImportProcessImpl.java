package ieci.tecdoc.sgm.admsistema.proceso.importacion;

import ieci.tecdoc.sgm.admsistema.proceso.clonar.CloneProcessImpl;

import java.util.Map;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class ImportProcessImpl extends CloneProcessImpl {

	/**
	 * Constructor.
	 */
	public ImportProcessImpl() {
		super();
	}

	/**
	 * Realiza la importación del repositorio documental de una entidad
	 * @param options Parámetros para la importación
	 * @return true si la importación se ha realizado con éxito. 
	 * @throws Exception si ocurre algún error.
	 */
	public boolean execute(Map options) throws Exception {

		try {
			executeImport(options);
			
			if (isOk()) {
				getImportLogger().info("[FIN] Proceso de importación ha finalizado correctamente");
			} else  {
				getImportLogger().warn("[FIN] Proceso de importación ha finalizado con ERRORES");
			}
		} finally {
			releaseLogger(getImportLogger());
		}
		
		return isOk();
	}

}
