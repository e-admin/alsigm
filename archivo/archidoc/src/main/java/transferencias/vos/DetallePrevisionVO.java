/*
 * Created on 09-feb-2005
 *
 */
package transferencias.vos;

import common.Constants;

/**
 * Value Object para un detalle de prevision
 * 
 */
public class DetallePrevisionVO {

	protected String id = null;
	protected String idPrevision = null;
	protected int numeroOrden;
	protected String codSistemaProductor;
	protected String nombreSistemaProductor;
	protected String idProcedimiento;
	protected String idSerieDestino;
	protected String anoInicioExpedientes = null;
	protected String anoFinExpedientes = null;
	protected int numUInstalacion = 0;
	protected String idFormato;
	protected String observaciones = null;
	protected boolean cerrada = false;
	protected int numrentrega = 0;
	protected String idOrgProdDef = null;
	protected String idSerieOrigen;

	/* Control de acciones sobre detalle de prevision */
	protected boolean editable = false;
	protected boolean eliminable = false;
	protected String info;

	public DetallePrevisionVO() {
	}

	public DetallePrevisionVO(String idPrevision) {
		this.idPrevision = idPrevision;
	}

	public int getNumrentrega() {
		return this.numrentrega;
	}

	public void setNumrentrega(int numrentrega) {
		this.numrentrega = numrentrega;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPrevision() {
		return idPrevision;
	}

	public void setIdPrevision(String idPrevision) {
		this.idPrevision = idPrevision;
	}

	public void setNumUInstalacion(int numUInstalacion) {
		this.numUInstalacion = numUInstalacion;
	}

	public int getNumUInstalacion() {
		return numUInstalacion;
	}

	/*
	 * public String getCodigoFuncion() { return codigoFuncion; } public void
	 * setCodigoFuncion(String codigoFuncion) { this.codigoFuncion =
	 * codigoFuncion; }
	 */
	public String getIdProcedimiento() {
		return idProcedimiento;
	}

	public void setIdProcedimiento(String idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}

	/*
	 * public void setProcedimiento(String idProcedimiento, String
	 * nombreProcedimiento) { this.procedimiento = new
	 * ParClaveValor(idProcedimiento, nombreProcedimiento); }
	 */

	/*
	 * public String getNombreProcedimiento() { return
	 * (String)(procedimiento!=null?procedimiento.valor:null); } public void
	 * setNombreProcedimiento(String nombreProcedimiento) { if
	 * (this.procedimiento != null) this.procedimiento.valor =
	 * nombreProcedimiento; else this.procedimiento = new ParClaveValor(null,
	 * nombreProcedimiento); }
	 */

	/*
	 * public String getDenominacionSerie() { return
	 * (String)(serie!=null?serie.valor:null); } public void setSerie(String
	 * idSerie, String nombreSerie) { this.serie = new ParClaveValor(idSerie,
	 * nombreSerie); }
	 */

	public String getCodSistProductor() {
		return this.codSistemaProductor;
	}

	public String getNombreSistProductor() {
		return this.nombreSistemaProductor;
	}

	public void setCodSistProductor(String codigoSistemaProductor) {
		this.codSistemaProductor = codigoSistemaProductor;
	}

	public void setNombreSistProductor(String nombreSistemaProductor) {
		this.nombreSistemaProductor = nombreSistemaProductor;
	}

	/*
	 * public void setSistemaProductor(String codigoSistemaProductor, String
	 * nombreSistemaProductor) { this.sistemaProductor = new
	 * ParClaveValor(codigoSistemaProductor, nombreSistemaProductor); }
	 */

	public String getAnoFinUdoc() {
		return anoFinExpedientes;
	}

	public void setAnoFinUdoc(String fechaFinExpedientes) {
		this.anoFinExpedientes = fechaFinExpedientes;
	}

	public String getAnoIniUdoc() {
		return anoInicioExpedientes;
	}

	public void setAnoIniUdoc(String fechaInicioExpedientes) {
		this.anoInicioExpedientes = fechaInicioExpedientes;
	}

	public String getIdFormatoUI() {
		return this.idFormato;
	}

	public void setIdFormatoUI(String idFormato) {
		this.idFormato = idFormato;
	}

	/*
	 * public String getNombreFormato() { return
	 * (String)(formato!=null?formato.valor:null); } public void
	 * setNombreFormato(String nombreFormato) { if (formato != null)
	 * this.formato.valor = nombreFormato; } public void setFormato(String
	 * idFormato, String nombreFormato) { this.formato = new
	 * ParClaveValor(idFormato, nombreFormato); }
	 */

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(int numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getIdOrgProdDef() {
		return idOrgProdDef;
	}

	public void setIdOrgProdDef(String idOrgProdDef) {
		this.idOrgProdDef = idOrgProdDef;
	}

	public boolean isCerrada() {
		return cerrada;
	}

	public void setCerrado(String cerrada) {
		this.cerrada = "S".equals(cerrada);
	}

	public String getCerrado() {
		return cerrada ? Constants.TRUE_STRING : Constants.FALSE_STRING;
	}

	public boolean equals(Object oObject) {
		if (this == oObject)
			return true;
		if (oObject == null || oObject.getClass() != this.getClass())
			return false;
		DetallePrevisionVO oDetallePrevision = (DetallePrevisionVO) oObject;
		return this.id.equals(oDetallePrevision.id);
	}

	public boolean getPuedeSerEditado() {
		return editable;
	}

	public void setPuedeSerEditado(boolean editable) {
		this.editable = editable;
	}

	public boolean getPuedeSerEliminado() {
		return eliminable;
	}

	public void setPuedeSerEliminado(boolean eliminable) {
		this.eliminable = eliminable;
	}

	public String getIdSerieDestino() {
		return idSerieDestino;
	}

	public void setIdSerieDestino(String idSerieDestino) {
		this.idSerieDestino = idSerieDestino;
	}

	public String getIdSerieOrigen() {
		return idSerieOrigen;
	}

	public void setIdSerieOrigen(String idSerieOrigen) {
		this.idSerieOrigen = idSerieOrigen;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isSinDocumentosFisicos() {
		if (Constants.FORMATO_UI_TRANSFERENCIA_ELECTRONICA.equals(idFormato)) {
			return true;
		}
		return false;
	}

}