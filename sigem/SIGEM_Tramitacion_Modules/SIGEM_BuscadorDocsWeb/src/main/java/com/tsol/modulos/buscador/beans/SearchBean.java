package com.tsol.modulos.buscador.beans;

public class SearchBean 
{
	/* Código de cotejo.  */
	private String codCotejo;
	
	/* Nombre del documento.  */
	private String nombre;
	
	/* Número del expediente.  */
	private String numExp;
	
	/* Fecha de alta del documento.  */
	private String fechaDoc;
	
	/* Tipo de registro.  */
	private String tpReg;

	/* Identificador del documento.  */
	private String id;

	/* Infopag del documento.  */
	private String infopag;

	
	public SearchBean() {
		super();
		
		codCotejo = null;
		nombre = null;
		numExp = null;
		fechaDoc = null;
		tpReg = null;
	}

	public String getCodCotejo() {
		return codCotejo;
	}

	public void setCodCotejo(String codCotejo) {
		this.codCotejo = codCotejo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public String getFechaDoc() {
		return fechaDoc;
	}

	public void setFechaDoc(String fechaDoc) {
		this.fechaDoc = fechaDoc;
	}

	public String getTpReg() {
		return tpReg;
	}

	public void setTpReg(String tpReg) {
		this.tpReg = tpReg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInfopag() {
		return infopag;
	}

	public void setInfopag(String infopag) {
		this.infopag = infopag;
	}

}
