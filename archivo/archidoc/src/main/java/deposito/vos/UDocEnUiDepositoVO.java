package deposito.vos;

import transferencias.vos.UDocEnUIReeaCRVO;

/**
 * Unidad documental ubicada en deposito
 */
public class UDocEnUiDepositoVO {
	String idunidaddoc;
	String iduinstalacion;
	int posudocenui;
	String identificacion;
	String idudocre;
	String signaturaudoc;
	String descripcion;
	String enRelacion;

	String titulo;
	String numExp;
	String fechaIni;
	String fechaFin;

	public UDocEnUiDepositoVO(String idUinstalacion, String identificacionUDoc,
			UDocEnUIReeaCRVO udocEnUIReeaCRVO) {
		this.idunidaddoc = udocEnUIReeaCRVO.getIdUnidadDoc();
		this.iduinstalacion = idUinstalacion;
		this.posudocenui = udocEnUIReeaCRVO.getPosUdocEnUI();
		this.identificacion = identificacionUDoc;
		this.idudocre = udocEnUIReeaCRVO.getId();
		this.signaturaudoc = udocEnUIReeaCRVO.getSignaturaUdoc();
		this.descripcion = udocEnUIReeaCRVO.getDescripcion();
		this.titulo = udocEnUIReeaCRVO.getAsunto();
		this.numExp = udocEnUIReeaCRVO.getNumExp();
		this.fechaIni = udocEnUIReeaCRVO.getValorFechaInicio();
		this.fechaFin = udocEnUIReeaCRVO.getValorFechaInicio();
	}

	public UDocEnUiDepositoVO() {
		super();
	}

	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getSignaturaudoc() {
		return this.signaturaudoc;
	}

	public void setSignaturaudoc(String signaturaudoc) {
		this.signaturaudoc = signaturaudoc;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getIdudocre() {
		return this.idudocre;
	}

	public void setIdudocre(String idudocre) {
		this.idudocre = idudocre;
	}

	public String getIduinstalacion() {
		return this.iduinstalacion;
	}

	public void setIduinstalacion(String iduinstalacion) {
		this.iduinstalacion = iduinstalacion;
	}

	public String getIdunidaddoc() {
		return this.idunidaddoc;
	}

	public void setIdunidaddoc(String idunidaddoc) {
		this.idunidaddoc = idunidaddoc;
	}

	public int getPosudocenui() {
		return this.posudocenui;
	}

	public void setPosudocenui(int posudocenui) {
		this.posudocenui = posudocenui;
	}

	public boolean isValidada() {
		return idunidaddoc != null;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEnRelacion() {
		return enRelacion;
	}

	public void setEnRelacion(String enRelacion) {
		this.enRelacion = enRelacion;
	}

	/**
	 * @return el titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            el titulo a establecer
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public boolean equals(Object o) {
		boolean result = false;

		if (o != null) {
			result = iduinstalacion.equals(((UDocEnUiDepositoVO) o)
					.getIduinstalacion());
		}

		return result;

	}
}