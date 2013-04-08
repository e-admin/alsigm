package fondos.model;

import fondos.vos.FondoVO;

public class Fondo extends ElementoCuadroClasificacion implements FondoVO {

	public Fondo() {
	};

	String codPais;
	String codComunidad;
	String codArchivo;
	int tipofondo;
	String idEntProductora;

	boolean puedeSerEliminado = false;
	boolean puedeSerMovido = false;
	boolean permitidoModificarEntidadProductora = false;
	boolean permitidoModificarCodigo = false;
	boolean permitidoModificarArchivo = false;

	public String getCodArchivo() {
		return this.codArchivo;
	}

	public void setCodArchivo(String codArchivo) {
		this.codArchivo = codArchivo;
	}

	public String getCodComunidad() {
		return this.codComunidad;
	}

	public void setCodComunidad(String codComunidad) {
		this.codComunidad = codComunidad;
	}

	public String getCodPais() {
		return this.codPais;
	}

	public void setCodPais(String codPais) {
		this.codPais = codPais;
	}

	public String getDenominacion() {
		return this.titulo;
	}

	public void setDenominacion(String denominacion) {
		this.titulo = denominacion;
	}

	public String getIdEntProductora() {
		return this.idEntProductora;
	}

	public void setIdEntProductora(String idEntProductora) {
		this.idEntProductora = idEntProductora;
	}

	public int getTipofondo() {
		return tipofondo;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getCodRefFondo() {
		return codRefFondo;
	}

	public void setCodRefFondo(String codRefFondo) {
		this.codRefFondo = codRefFondo;
	}

	public String getIdFondo() {
		return super.getId();
	}

	public String getTitulo() {
		return titulo;
	}

	public int getTipo() {
		return ElementoCuadroClasificacion.TIPO_FONDO;
	}

	public String getIdElementoCF() {
		return getId();
	}

	public void setIdElementoCF(String id) {
		setId(id);
	}

	// public Object clone(){
	// IFondo ret = new Fondo();
	// ret.setCodArchivo(codArchivo);
	// ret.setCodComunidad(codComunidad);
	// ret.setCodPais(codPais);
	// ret.setDenominacion(titulo);
	// // ret.setFinalCodigo(finalCodigo);
	// ret.setIdEntProductora(idEntProductora);
	// // ret.setInicioCodigo(inicioCodigo);
	// //codigo de referencia y codigo de fondo no hace falta(son dinamicos)
	//
	// return ret;
	// }

	public void setTipofondo(int tipoFondo) {
		this.tipofondo = tipoFondo;
	}

	public boolean isPublico() {
		return tipofondo == PUBLICO;
	}

	public boolean isEntidadProductoraModificable(
			boolean tieneUnidadesDocumentales) {
		return !tieneUnidadesDocumentales;
	}

	public String getCodReferencia() {
		return getCodRefFondo();
	}

	public boolean getPermitidoModificarCodigo() {
		return permitidoModificarCodigo;
	}

	public void setPermitidoModificarCodigo(boolean permitidoModificarCodigo) {
		this.permitidoModificarCodigo = permitidoModificarCodigo;
	}

	public boolean getPermitidoModificarEntidadProductora() {
		return permitidoModificarEntidadProductora;
	}

	public void setPermitidoModificarEntidadProductora(
			boolean permitidoModificarEntidadProductora) {
		this.permitidoModificarEntidadProductora = permitidoModificarEntidadProductora;
	}

	public boolean getPuedeSerEliminado() {
		return puedeSerEliminado;
	}

	public void setPuedeSerEliminado(boolean puedeSerEliminado) {
		this.puedeSerEliminado = puedeSerEliminado;
	}

	public boolean getPuedeSerMovido() {
		return puedeSerMovido;
	}

	public void setPuedeSerMovido(boolean puedeSerMovido) {
		this.puedeSerMovido = puedeSerMovido;
	}

	public boolean isPermitidoModificarArchivo() {
		return permitidoModificarArchivo;
	}

	public boolean getPermitidoModificarArchivo() {
		return permitidoModificarArchivo;
	}

	public void setPermitidoModificarArchivo(boolean permitidoModificarArchivo) {
		this.permitidoModificarArchivo = permitidoModificarArchivo;
	}
}
