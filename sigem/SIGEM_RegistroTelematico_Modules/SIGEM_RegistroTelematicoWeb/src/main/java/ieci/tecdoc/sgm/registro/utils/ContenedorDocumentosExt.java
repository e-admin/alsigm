package ieci.tecdoc.sgm.registro.utils;

import org.apache.struts.upload.FormFile;

public class ContenedorDocumentosExt {
	private FormFile[] ficheros;
	private String[] codigosFichero;
	
	public ContenedorDocumentosExt(int size){
		ficheros = new FormFile[size];
		codigosFichero = new String[size];
	}
	
	public void addInfo(FormFile fichero, String codigo, int index){
		ficheros[index] = fichero;
		codigosFichero[index] = codigo;
	}
	
	public FormFile getFile(int index){
		return ficheros[index];
	}
	
	public String getCode(int index){
		return codigosFichero[index];
	}
	
	public String[] getCodigosFichero() {
		return codigosFichero;
	}
	public void setCodigosFichero(String[] codigosFichero) {
		this.codigosFichero = codigosFichero;
	}
	public FormFile[] getFicheros() {
		return ficheros;
	}
	public void setFicheros(FormFile[] ficheros) {
		this.ficheros = ficheros;
	}
	
	
}
