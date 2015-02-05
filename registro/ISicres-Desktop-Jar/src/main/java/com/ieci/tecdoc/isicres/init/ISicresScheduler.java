package com.ieci.tecdoc.isicres.init;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.essiembre.library.scheduler.SchedulerTask;
import com.ieci.tecdoc.common.isicres.ConfigFilePathResolverIsicres;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.isicres.desktopweb.utils.ISicresContextPathUtils;

/*
 * @author LMVICENTE @creationDate 15-nov-2004 12:46:29
 *
 * @version
 * @since
 */
public class ISicresScheduler extends SchedulerTask {

    private static final Logger _log = Logger.getLogger(ISicresScheduler.class);

    public void run() {
		if (Configurator.getInstance().getPropertyBoolean(
				ConfigurationKeys.KEY_DESKTOP_USESCHEDULERCLEANERTHREAD)) {

			File cleanDirectory = null;

			String basePathApplication = ISicresContextPathUtils
					.getWebServletContextPath(this.getServletConfig()
							.getServletContext());

			if (StringUtils.isNotBlank(basePathApplication)) {

				//obtenemos el directorio de los informes
				cleanDirectory = getDirectoryNameForReports(basePathApplication);

				//borramos el directorio
				cleanerDirectory(cleanDirectory);

				// borramos el directorio temporal de ISicres si el directorio
				// es relativo
				if (Configurator.getInstance().getPropertyBoolean(
						ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_TEMPORAL_DIR)) {
					cleanDirectory = new File(
							basePathApplication
									+ Configurator
											.getInstance()
											.getProperty(
													ConfigurationKeys.KEY_DESKTOP_TEMPORALDIRECTORYNAME));

					cleanerAllDirectory(cleanDirectory);
				}
			}
		} else {
			_log.info("Scheduler de limpieza de directorio no activado.");
		}
	}

    /**
     * Metodo que obtiene el directorio de los informes, en referencia a si es relativo o absoluto a la aplicación
     * @param basePathApplication
     * @return File
     */
	private File getDirectoryNameForReports(String basePathApplication) {
		File cleanDirectory;
		// borramos el directorio temporal de Informes comprobamos antes
		// si es relativo o no
		if (Configurator
				.getInstance()
				.getPropertyBoolean(
						ConfigurationKeys.KEY_DESKTOP_ISRELATIVE_DIRECTORYNAMEFORREPORTS)) {
			//directorio relativo a la aplicacion
			cleanDirectory = new File(
					basePathApplication
							+ Configurator
									.getInstance()
									.getProperty(
											ConfigurationKeys.KEY_DESKTOP_TEMPORALRELATIVEDIRECTORYNAMEFORREPORTS));

		} else {
			//directorio absoluto
			cleanDirectory = new File(
					Configurator
							.getInstance()
							.getProperty(
									ConfigurationKeys.KEY_DESKTOP_TEMPORALRELATIVEDIRECTORYNAMEFORREPORTS));
		}

		return cleanDirectory;
	}

    //este metodo borra los ficheros pdfs de un directorio
    private void cleanerDirectory(File cleanDirectory){
    	if (cleanDirectory.exists()) {
            File[] files = cleanDirectory.listFiles();

            int size = 0;
            if (files != null) {
                size = files.length;
            }

            _log
                    .info("Ejecutando limpieza de directorio [" + cleanDirectory + "] con [" + size
                            + "] ficheros.");

            for (int i = 0; i < size; i++) {
                String name = files[i].getName();
                name = name.substring(name.lastIndexOf(".") + 1, name.length()).toUpperCase();
                if (name.equals("PDF")) {
                    files[i].delete();
                }
            }
        }
    }

    //este metodo borra todos los ficheros del directorio
    private void cleanerAllDirectory(File cleanDirectory){
    	if (cleanDirectory.exists()) {
            File[] files = cleanDirectory.listFiles();

            int size = 0;
            if (files != null) {
                size = files.length;
            }

            _log
                    .info("Ejecutando limpieza de directorio [" + cleanDirectory + "] con [" + size
                            + "] ficheros.");

            for (int i = 0; i < size; i++) {
            	//obtenemos la fecha de hoy
            	Date date = new Date();
            	String dateHoy = formatterDate(date);

            	//obtenemos la fecha de la ultima modificacion del fichero
            	Date dateDocumento = new Date();
            	dateDocumento.setTime(files[i].lastModified());
            	String dateDoc = formatterDate(dateDocumento);

            	//comparamos las fechas si son diferentes borramos
            	if (!dateHoy.equals(dateDoc)){
            		files[i].delete();
            	}
            }
        }
    }

    private String formatterDate(Date date){
    	SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMdd");
    	return FORMATTER.format(date);
    }
}