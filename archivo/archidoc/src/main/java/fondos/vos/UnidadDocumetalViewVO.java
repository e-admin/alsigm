/**
 *
 */
package fondos.vos;

import java.util.Date;
import java.util.List;

import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IUnidadDocumental;

/**
 * Interfaz para la vista de unidades documentales
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UnidadDocumetalViewVO extends ElementoCuadroClasificacion
		implements IUnidadDocumental {
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
	private String idRepEcm;
	private String idArchivo;
	private int nivelAcceso;
	private String idlca;
	private int subtipo;
	private String ordPos;
	private Date fiFechaIni;
	private Date fiFechaFin;
	private String fiCalificador;
	private String fiFormato;
	private String fiSep;
	private String fiValor;
	private Date ffFechaIni;
	private Date ffFechaFin;
	private String ffFormato;
	private String ffSep;
	private String ffCalificador;
	private String ffValor;
	private String valor;
	private String numExp;
	private String nombreNivel;
	private List interesados;

	String idElementocf = null;
	String idSerie = null;
	String codSistemaProductor = null;
	String tipoDocumental = null;
	int marcasBloqueo = 0;

	public String getId() {
		return id;
	}

	public int getTipo() {
		return tipo;
	}

	public String getIdNivel() {
		return idNivel;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public String getIdFondo() {
		return idFondo;
	}

	public String getCodRefFondo() {
		return codRefFondo;
	}

	public String getFinalCodRefPadre() {
		return finalCodRefPadre;
	}

	public String getCodReferencia() {
		return codReferencia;
	}

	public int getEstado() {
		return estado;
	}

	public String getIdEliminacion() {
		return idEliminacion;
	}

	public String getIdFichaDescr() {
		return idFichaDescr;
	}

	public String getTieneDescr() {
		return tieneDescr;
	}

	public String getEditClfDocs() {
		return editClfDocs;
	}

	public String getIdRepEcm() {
		return idRepEcm;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public String getIdlca() {
		return idlca;
	}

	public int getSubtipo() {
		return subtipo;
	}

	public String getOrdPos() {
		return ordPos;
	}

	public Date getFiFechaIni() {
		return fiFechaIni;
	}

	public Date getFiFechaFin() {
		return fiFechaFin;
	}

	public String getFiFormato() {
		return fiFormato;
	}

	public String getFiSep() {
		return fiSep;
	}

	public String getFiValor() {
		return fiValor;
	}

	public Date getFfFechaIni() {
		return ffFechaIni;
	}

	public Date getFfFechaFin() {
		return ffFechaFin;
	}

	public String getFfFormato() {
		return ffFormato;
	}

	public String getFfSep() {
		return ffSep;
	}

	public String getValor() {
		return valor;
	}

	public String getNumExp() {
		return numExp;
	}

	public String getNombreNivel() {
		return nombreNivel;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public void setIdFondo(String idFondo) {
		this.idFondo = idFondo;
	}

	public void setCodRefFondo(String codRefFondo) {
		this.codRefFondo = codRefFondo;
	}

	public void setFinalCodRefPadre(String finalCodRefPadre) {
		this.finalCodRefPadre = finalCodRefPadre;
	}

	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public void setIdEliminacion(String idEliminacion) {
		this.idEliminacion = idEliminacion;
	}

	public void setIdFichaDescr(String idFichaDescr) {
		this.idFichaDescr = idFichaDescr;
	}

	public void setTieneDescr(String tieneDescr) {
		this.tieneDescr = tieneDescr;
	}

	public void setEditClfDocs(String editClfDocs) {
		this.editClfDocs = editClfDocs;
	}

	public void setIdRepEcm(String idRepEcm) {
		this.idRepEcm = idRepEcm;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public void setIdlca(String idlca) {
		this.idlca = idlca;
	}

	public void setSubtipo(int subtipo) {
		this.subtipo = subtipo;
	}

	public void setOrdPos(String ordPos) {
		this.ordPos = ordPos;
	}

	public void setFiFechaIni(Date fiFechaIni) {
		this.fiFechaIni = fiFechaIni;
	}

	public void setFiFechaFin(Date fiFechaFin) {
		this.fiFechaFin = fiFechaFin;
	}

	public void setFiFormato(String fiFormato) {
		this.fiFormato = fiFormato;
	}

	public void setFiSep(String fiSep) {
		this.fiSep = fiSep;
	}

	public void setFiValor(String fiValor) {
		this.fiValor = fiValor;
	}

	public void setFfFechaIni(Date ffFechaIni) {
		this.ffFechaIni = ffFechaIni;
	}

	public void setFfFechaFin(Date ffFechaFin) {
		this.ffFechaFin = ffFechaFin;
	}

	public void setFfFormato(String ffFormato) {
		this.ffFormato = ffFormato;
	}

	public void setFfSep(String ffSep) {
		this.ffSep = ffSep;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.model.IUnidadDocumental#setDenominacion(java.lang.String)
	 */
	public void setDenominacion(String denominacion) {
		this.titulo = denominacion;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.model.IUnidadDocumental#setIdElementocf(java.lang.String)
	 */
	public void setIdElementocf(String idElementocf) {
		this.id = idElementocf;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.model.IUnidadDocumental#setIdSerie(java.lang.String)
	 */
	public void setIdSerie(String idSerie) {
		setIdPadre(idSerie);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.model.IUnidadDocumental#setTipoDocumental(java.lang.String)
	 */
	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.UnidadDocumentalVO#getDenominacion()
	 */
	public String getDenominacion() {
		return getTitulo();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.UnidadDocumentalVO#getIdElementocf()
	 */
	public String getIdElementocf() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.UnidadDocumentalVO#getIdSerie()
	 */
	public String getIdSerie() {
		return getIdPadre();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.UnidadDocumentalVO#getTipoDocumental()
	 */
	public String getTipoDocumental() {
		return tipoDocumental;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.model.IUnidadDocumental#setCodSistProductor(java.lang.String)
	 */
	public void setCodSistProductor(String codSistemaProductor) {
		this.codSistemaProductor = codSistemaProductor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.vos.UnidadDocumentalVO#getCodSistProductor()
	 */
	public String getCodSistProductor() {
		return this.codSistemaProductor;
	}

	public String getFiCalificador() {
		return fiCalificador;
	}

	public String getFfCalificador() {
		return ffCalificador;
	}

	public void setFiCalificador(String fiCalificador) {
		this.fiCalificador = fiCalificador;
	}

	public void setFfCalificador(String ffCalificador) {
		this.ffCalificador = ffCalificador;
	}

	public void setFfValor(String ffValor) {
		this.ffValor = ffValor;
	}

	public String getFfValor() {
		return ffValor;
	}

	public void setInteresados(List interesados) {
		this.interesados = interesados;
	}

	public List getInteresados() {
		return interesados;
	}

}
