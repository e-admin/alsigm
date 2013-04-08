package com.tsol.modulos.buscador.beans;

public class VolumeBean 
{
	/* Nombre del fichero. */
	String loc;
	
	/* Tamaño del fichero. */
	int filesize;
	
	/* Nombre del volumen. */
	String volName;
	
	/* Información de la conexión FTP. */
	String conInfo;

	public VolumeBean() {
		super();
		
		loc = null;
		filesize = 0;
		volName = null;
		conInfo = null;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	public String getVolName() {
		return volName;
	}

	public void setVolName(String volName) {
		this.volName = volName;
	}

	public String getConInfo() {
		return conInfo;
	}

	public void setConInfo(String conInfo) {
		this.conInfo = conInfo;
	}
	
}
