package ieci.tecdoc.sgm.core.services.rpadmin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Informe {
private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy"); 
	
	/**
	 * Identificador del Informe
	 */
	private int id;
		
	/**
	 * Descripcion del informe
	 */
	private String description;
	
	/**
	 * Nombre de la plantilla de informe
	 */
	private String report;
	
	/**
	 * Tipo de informe
	 */
	private int typeReport;
	
	/**
	 * Indica el nombre del tipo de informe
	 */
	private String nameTypeReport;
	
	/**
	 * Indica si el Tipo de Asunto puede usarse por todas las oficinas de registro
	 * 0 - No
	 * 1 - Si
	 */
	private int allOfics;
	
	/**
	 * Indica si el informe puede ser usado por todos los perfiles de usuario
	 */
	private int allPerfs;
	
	/**
	 * Indica si el informe puede ser utilizado por todos los archivos del tipo especificado
	 */
	private int allArch;
	
	/**
	 * Indica el tipo de archivo que usa el informe
	 */
	private int typeArch;
	
	
	/**
	 * Fichero
	 */
	byte[] fileData;
	
	// METODOS
	
	public Informe() {}
	
	/**
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	/**
	 * @return
	 */
	public int getAllOfics() {
		return allOfics;
	}
	/**
	 * @param allOfics
	 */
	public void setAllOfics(int allOfics) {
		this.allOfics = allOfics;
	}

	/**
	 * @return the dateFormatter
	 */
	public static DateFormat getDateFormatter() {
		return dateFormatter;
	}

	/**
	 * @param dateFormatter the dateFormatter to set
	 */
	public static void setDateFormatter(DateFormat dateFormatter) {
		Informe.dateFormatter = dateFormatter;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the report
	 */
	public String getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(String report) {
		this.report = report;
	}

	/**
	 * @return the typeReport
	 */
	public int getTypeReport() {
		return typeReport;
	}

	/**
	 * @param typeReport the typeReport to set
	 */
	public void setTypeReport(int typeReport) {
		this.typeReport = typeReport;
	}

	/**
	 * @return the allPerfs
	 */
	public int getAllPerfs() {
		return allPerfs;
	}

	/**
	 * @param allPerfs the allPerfs to set
	 */
	public void setAllPerfs(int allPerfs) {
		this.allPerfs = allPerfs;
	}

	/**
	 * @return the allArch
	 */
	public int getAllArch() {
		return allArch;
	}

	/**
	 * @param allArch the allArch to set
	 */
	public void setAllArch(int allArch) {
		this.allArch = allArch;
	}

	/**
	 * @return the typeArch
	 */
	public int getTypeArch() {
		return typeArch;
	}

	/**
	 * @param typeArch the typeArch to set
	 */
	public void setTypeArch(int typeArch) {
		this.typeArch = typeArch;
	}

	/**
	 * @return the nameTypeReport
	 */
	public String getNameTypeReport() {
		return nameTypeReport;
	}

	/**
	 * @param nameTypeReport the nameTypeReport to set
	 */
	public void setNameTypeReport(String nameTypeReport) {
		this.nameTypeReport = nameTypeReport;
	}

	/**
	 * @return the fileData
	 */
	public byte[] getFileData() {
		return fileData;
	}

	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}	
	
	
	
}
