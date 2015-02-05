package com.ieci.tecdoc.isicres.desktopweb.helper;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.conf.FileExtensionConf;
import com.ieci.tecdoc.common.keys.ConfigurationExtensionFileKeys;
import com.ieci.tecdoc.common.utils.ConfiguratorExtensionFile;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
/**
 * Clase de ayuda/utilidades para obtener las configuraciones de los ficheros segun la extensión
 *
 * @author 66194663
 *
 */
public class ConfigExtensionFileHelper {

	private static Logger _logger = Logger.getLogger(ConfigExtensionFileHelper.class);

	/**
	 * Metodo que devuelve que operativa que se utiliza para la visualizacion de
	 * los ficheros segun su extension
	 *
	 * @param fileExt - Extension del fichero
	 *
	 * @return boolean - TRUE  - se fuerza la descarga del fichero /
	 *                   FALSE - se visualiza el fichero desde el navegador
	 *
	 */
	public static boolean getShowDialogSaveOpenFile(String fileExt){
		boolean showDialogSaveOpen;

		//comprobamos si el fichero tiene extension
		if((StringUtils.isBlank(fileExt)) || (Keys.EXTENSION_DEFECTO.equalsIgnoreCase(fileExt))){
		    // si el fichero no tiene extension se fuerza la descarga del mismo
			showDialogSaveOpen = true;
        	if(_logger.isDebugEnabled()){
        		StringBuffer buffer = new StringBuffer();
				buffer.append("Fichero ")
						.append("sin extensión o con extensión por defecto [")
						.append(fileExt)
						.append("] se fuerza cuadro de diálogo para guardar fichero");

				_logger.debug(buffer);
        	}
		}else{
		    //Tratamiento por defecto que se debe hacer para las extension
			showDialogSaveOpen = ConfiguratorExtensionFile
					.getInstance()
					.getPropertyBoolean(
							ConfigurationExtensionFileKeys.KEY_SHOWDIALOG_BY_DEFAULT);

			if(_logger.isDebugEnabled()){
				StringBuffer buffer = new StringBuffer();
				buffer.append(
						"showDialogSaveOpen por defecto para las extensiones [")
						.append(showDialogSaveOpen).append("]");
				_logger.debug(buffer);

			}

		    //Obtenemos la coleccion con las configuraciones para las diferentes extensiones
		    Map configExtensionFile = ConfiguratorExtensionFile.getInstance()
		            .getExtensionFileConfig();

		    //Comprobamos si la coleccion contiene datos
		    if(configExtensionFile!=null && !configExtensionFile.isEmpty()){
		        //Buscamos en la coleccion, la extension que estamos tratando, para comprobar si se ha definido alguna configuracion para ella
		        FileExtensionConf fileConfig = (FileExtensionConf) configExtensionFile.get(fileExt.toLowerCase());
		        if(fileConfig != null){
		            // para la extension a tratar se ha definido una configuracion por lo tanto seteamos los datos
		            showDialogSaveOpen = fileConfig.isShowDialogDownloadFile();
                    if(_logger.isDebugEnabled()){
                    	StringBuffer buffer = new StringBuffer();
						buffer.append("Extension [")
								.append(fileExt)
								.append("con configuracion propia ShowDialogSaveOpen [")
								.append(showDialogSaveOpen).append("]");
						_logger.debug(buffer);
                    }
		        }
		    }
		}

		return showDialogSaveOpen;
	}

}
