package fondos.model;

import java.util.ArrayList;
import java.util.List;

import common.util.ListUtils;

import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.InteresadoUdocVO;

/**
 * Unidad documental del cuadro de clasificación de fondos documentales
 */
public class UnidadDocumental extends ElementoCuadroClasificacion implements
		IUnidadDocumental {
	String idElementocf = null;
	String idSerie = null;
	String numExp = null;
	String codSistemaProductor = null;
	String tipoDocumental = null;
	int marcasBloqueo = 0;

	/**
	 * Lista de {@link InteresadoUdocVO}
	 */
	private List interesados = new ArrayList();

	public UnidadDocumental() {
	}

	UnidadDocumental(ElementoCuadroClasificacionVO elementoCF) {
		this.idElementocf = elementoCF.getId();
		this.id = elementoCF.getId();
		this.estado = elementoCF.getEstado();
		this.idNivel = elementoCF.getIdNivel();
		this.titulo = elementoCF.getTitulo();
		this.codigo = elementoCF.getCodigo();
		this.idFondo = elementoCF.getIdFondo();
		this.codRefFondo = elementoCF.getCodRefFondo();
		this.idPadre = elementoCF.getIdPadre();
		this.idSerie = elementoCF.getIdPadre();
		this.tipo = TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador();
		this.idFichaDescr = elementoCF.getIdFichaDescr();
	}

	public String getCodSistProductor() {
		return codSistemaProductor;
	}

	public void setCodSistProductor(String codSistemaProductor) {
		this.codSistemaProductor = codSistemaProductor;
	}

	public String getDenominacion() {
		return titulo;
	}

	public void setDenominacion(String denominacion) {
		this.titulo = denominacion;
	}

	public String getIdElementocf() {
		return idElementocf;
	}

	public void setIdElementocf(String idElementocf) {
		this.idElementocf = idElementocf;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getNumExp() {
		return numExp;
	}

	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

	public String getTipoDocumental() {
		return tipoDocumental;
	}

	public void setTipoDocumental(String tipoDocumental) {
		this.tipoDocumental = tipoDocumental;
	}

	public int getMarcasBloqueo() {
		return marcasBloqueo;
	}

	public void setMarcasBloqueo(int marcasBloqueo) {
		this.marcasBloqueo = marcasBloqueo;
	}

	public void setInteresados(List interesados) {
		this.interesados = interesados;
	}

	public List getInteresados() {
		return interesados;
	}

	public void addInteresado(InteresadoUdocVO interesado) {
		if (interesados == null)
			interesados = new ArrayList();

		if (interesado.isPrincipal()) {
			interesados.add(0, interesado);
		} else {
			interesados.add(interesado);
		}
	}

	public boolean isConInteresados() {
		if (ListUtils.isNotEmpty(interesados)) {
			return true;
		}

		return false;

	}

}