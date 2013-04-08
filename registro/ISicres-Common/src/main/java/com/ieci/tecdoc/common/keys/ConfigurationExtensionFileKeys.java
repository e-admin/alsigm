package com.ieci.tecdoc.common.keys;
/**
 * Clase que contiene la definicion de los nodos que componen el fichero de configuracion ISicres-Extension-Files-Configuration.xml
 *
 * @author Blimea
 *
 */
public interface ConfigurationExtensionFileKeys {

	//Operativa de forzar cuadro de dialogo (abrir/descargar) por defecto
	public static String KEY_SHOWDIALOG_BY_DEFAULT = "/ISicres-Extension-Files-Configuration/showDialogDownloadFileByDefault";

	//Nodo que contiene la definicion de configuracion para cada fichero
	public static String KEY_EXTENSIONS_FILES_CONFIGURATION = "/ISicres-Extension-Files-Configuration/extensions-configuration/extension-configuration";

	//Nodo que indica la extension del fichero
	public static String KEY_EXTENSION_FILE = "extension";

	//Nodo que indica si se debe mostrar el cuadro de dialogo (abrir/descargar) para la visualizacion de los ficheros
	public static String KEY_SHOW_DIALOG_DOWNLOAD_FILE = "showDialogDownloadFile";
}
