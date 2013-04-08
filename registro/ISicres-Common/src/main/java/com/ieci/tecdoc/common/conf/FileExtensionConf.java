package com.ieci.tecdoc.common.conf;

import java.io.Serializable;

/**
 * Clase que contiene la configuración de los ficheros segun su extension
 *
 * @author Blimea
 *
 */
public class FileExtensionConf implements Serializable{

	// Extension del fichero a tratar
	private String extension;

	// Indica si se debe mostrar el cuadro de dialogo que fuerza a abrir/guardar
	// fichero
	private boolean showDialogDownloadFile;


    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("\n\t\t FileConfig extension[");
        buffer.append(extension);
        buffer.append("] showDialogDownloadFile [");
        buffer.append(showDialogDownloadFile);
        buffer.append("]");

        return buffer.toString();
    }

	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public boolean isShowDialogDownloadFile() {
		return showDialogDownloadFile;
	}
	public void setShowDialogDownloadFile(boolean showDialogDownloadFile) {
		this.showDialogDownloadFile = showDialogDownloadFile;
	}



}
