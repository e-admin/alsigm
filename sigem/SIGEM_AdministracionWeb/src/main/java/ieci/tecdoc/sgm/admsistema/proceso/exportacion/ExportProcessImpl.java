package ieci.tecdoc.sgm.admsistema.proceso.exportacion;

import ieci.tecdoc.sgm.admsistema.proceso.clonar.CloneProcessImpl;

import java.util.Map;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

public class ExportProcessImpl extends CloneProcessImpl {

	/**
	 * Constructor.
	 */
	public ExportProcessImpl() {
		super();
	}
		
	/**
	 * Realiza la exportación del repositorio documental de una entidad
	 * @param options Parámetros para la exportación
	 * @return true si la exportación se ha realizado con éxito. 
	 * @throws Exception si ocurre algún error.
	 */
	public boolean execute(Map options) throws Exception {

		try {
			executeExport(options);
			
			if (isOk()) {
				getExportLogger().info("[FIN] Proceso de exportación ha finalizado correctamente");
			} else  {
				getExportLogger().warn("[FIN] Proceso de exportación ha finalizado con ERRORES");
			}
		} finally {
			releaseLogger(getExportLogger());
		}
		
		return isOk();
	}

}
