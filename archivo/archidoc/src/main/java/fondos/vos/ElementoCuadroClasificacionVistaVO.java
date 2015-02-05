/**
 *
 */
package fondos.vos;

import java.util.Date;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ElementoCuadroClasificacionVistaVO {

	private String id;
	private int tipo;
	private String idNivel;
	private String codigo;
	private String titulo;
	private String idPadre;
	private String idFondo;
	private String codRefFondo;
	private String finalCodRefPadre;
	private String codReferencia;
	private int estado;
	private String idEliminacion;
	private String idFichaDescr;
	private String tieneDescr;
	private String editClfDocs;
	private String idArchivo;
	private int nivelAcceso;
	private String idLCA;
	private int subtipo;
	private String ordPos;

	// ADVCFECHACF IDCAMPO=3
	private String valorFI;
	private Date fechaIniFI;
	private Date fechaFinFI;
	private String calificadorFI;
	private String formatoFI;
	private String sepFI;

	// ADVCFECHACF IDCAMPO=4
	private String valorFF;
	private Date fechaIniFF;
	private Date fechaFinFF;
	private String calificadorFF;
	private String formatoFF;
	private String sepFF;

	public String getValorFI() {
		return valorFI;
	}

	public void setValorFI(String valorFI) {
		this.valorFI = valorFI;
	}

	public Date getFechaIniFI() {
		return fechaIniFI;
	}

	public void setFechaIniFI(Date fechaIniFI) {
		this.fechaIniFI = fechaIniFI;
	}

	public Date getFechaFinFI() {
		return fechaFinFI;
	}

	public void setFechaFinFI(Date fechaFinFI) {
		this.fechaFinFI = fechaFinFI;
	}

	public String getCalificadorFI() {
		return calificadorFI;
	}

	public void setCalificadorFI(String calificadorFI) {
		this.calificadorFI = calificadorFI;
	}

	public String getFormatoFI() {
		return formatoFI;
	}

	public void setFormatoFI(String formatoFI) {
		this.formatoFI = formatoFI;
	}

	public String getSepFI() {
		return sepFI;
	}

	public void setSepFI(String sepFI) {
		this.sepFI = sepFI;
	}

	public String getValorFF() {
		return valorFF;
	}

	public void setValorFF(String valorFF) {
		this.valorFF = valorFF;
	}

	public Date getFechaIniFF() {
		return fechaIniFF;
	}

	public void setFechaIniFF(Date fechaIniFF) {
		this.fechaIniFF = fechaIniFF;
	}

	public Date getFechaFinFF() {
		return fechaFinFF;
	}

	public void setFechaFinFF(Date fechaFinFF) {
		this.fechaFinFF = fechaFinFF;
	}

	public String getCalificadorFF() {
		return calificadorFF;
	}

	public void setCalificadorFF(String calificadorFF) {
		this.calificadorFF = calificadorFF;
	}

	public String getFormatoFF() {
		return formatoFF;
	}

	public void setFormatoFF(String formatoFF) {
		this.formatoFF = formatoFF;
	}

	public String getSepFF() {
		return sepFF;
	}

	public void setSepFF(String sepFF) {
		this.sepFF = sepFF;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getIdFondo() {
		return idFondo;
	}

	public void setIdFondo(String idFondo) {
		this.idFondo = idFondo;
	}

	public String getCodRefFondo() {
		return codRefFondo;
	}

	public void setCodRefFondo(String codRefFondo) {
		this.codRefFondo = codRefFondo;
	}

	public String getFinalCodRefPadre() {
		return finalCodRefPadre;
	}

	public void setFinalCodRefPadre(String finalCodRefPadre) {
		this.finalCodRefPadre = finalCodRefPadre;
	}

	public String getCodReferencia() {
		return codReferencia;
	}

	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getIdEliminacion() {
		return idEliminacion;
	}

	public void setIdEliminacion(String idEliminacion) {
		this.idEliminacion = idEliminacion;
	}

	public String getIdFichaDescr() {
		return idFichaDescr;
	}

	public void setIdFichaDescr(String idFichaDescr) {
		this.idFichaDescr = idFichaDescr;
	}

	public String getTieneDescr() {
		return tieneDescr;
	}

	public void setTieneDescr(String tieneDescr) {
		this.tieneDescr = tieneDescr;
	}

	public String getEditClfDocs() {
		return editClfDocs;
	}

	public void setEditClfDocs(String editClfDocs) {
		this.editClfDocs = editClfDocs;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public String getIdLCA() {
		return idLCA;
	}

	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	public int getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(int subtipo) {
		this.subtipo = subtipo;
	}

	public String getOrdPos() {
		return ordPos;
	}

	public void setOrdPos(String ordPos) {
		this.ordPos = ordPos;
	}

}
