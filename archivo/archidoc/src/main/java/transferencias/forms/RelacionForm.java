package transferencias.forms;

import common.forms.CustomForm;

/**
 * Formulario de recogida de datos en la creación y edición de relaciones de
 * entrega
 */
public class RelacionForm extends CustomForm {
	private static final long serialVersionUID = 1L;
	String idprevisionseleccionada;
	String iddetalleseleccionado;
	String idserieseleccionada;
	String idrelacionseleccionada;
	String observaciones;
	String idformatoseleccionado;
	String formaDocumental;
	String relacionesseleccionadas[];
	String idubicacionseleccionada;
	String iddescriptorproductor;
	String nombreproductor;
	String idseriemostrarproductores;
	String idfondoorigen;
	String[] elementosseleccionados;
	String[] elementosElectronicosSel;
	String[] elementosUdocSeleccionados;
	String idingresoseleccionado;
	String idNivelDocumental;
	String selCajas;
	String idFicha;

	public String getIdubicacionseleccionada() {
		return this.idubicacionseleccionada;
	}

	public void setIdubicacionseleccionada(String idubicacionseleccionada) {
		this.idubicacionseleccionada = idubicacionseleccionada;
	}

	public String[] getRelacionesseleccionadas() {
		return this.relacionesseleccionadas;
	}

	public void setRelacionesseleccionadas(String[] relacionesseleccionadas) {
		this.relacionesseleccionadas = relacionesseleccionadas;
	}

	public String getIdformatoseleccionado() {
		return this.idformatoseleccionado;
	}

	public void setIdformatoseleccionado(String idformatoseleccionado) {
		this.idformatoseleccionado = idformatoseleccionado;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdrelacionseleccionada() {
		return idrelacionseleccionada;
	}

	public void setIdrelacionseleccionada(String idrelacionseleccionada) {
		this.idrelacionseleccionada = idrelacionseleccionada;
	}

	public String getIdserieseleccionada() {
		return idserieseleccionada;
	}

	public void setIdserieseleccionada(String idserieseleccionada) {
		this.idserieseleccionada = idserieseleccionada;
	}

	public String getIddetalleseleccionado() {
		return iddetalleseleccionado;
	}

	public void setIddetalleseleccionado(String iddetalleseleccionado) {
		this.iddetalleseleccionado = iddetalleseleccionado;
	}

	public String getIdprevisionseleccionada() {
		return idprevisionseleccionada;
	}

	public void setIdprevisionseleccionada(String idprevisionseleccionada) {
		this.idprevisionseleccionada = idprevisionseleccionada;
	}

	public String getFormaDocumental() {
		return formaDocumental;
	}

	public void setFormaDocumental(String idFomaDocumental) {
		this.formaDocumental = idFomaDocumental;
	}

	public String getIddescriptorproductor() {
		return iddescriptorproductor;
	}

	public void setIddescriptorproductor(String iddescriptorproductor) {
		this.iddescriptorproductor = iddescriptorproductor;
	}

	public String getNombreproductor() {
		return nombreproductor;
	}

	public void setNombreproductor(String nombreproductor) {
		this.nombreproductor = nombreproductor;
	}

	public String getIdseriemostrarproductores() {
		return idseriemostrarproductores;
	}

	public void setIdseriemostrarproductores(String idseriemostrarproductores) {
		this.idseriemostrarproductores = idseriemostrarproductores;
	}

	public String getIdfondoorigen() {
		return idfondoorigen;
	}

	public void setIdfondoorigen(String idfondoorigen) {
		this.idfondoorigen = idfondoorigen;
	}

	public String[] getElementosseleccionados() {
		return elementosseleccionados;
	}

	public void setElementosseleccionados(String[] elementosseleccionados) {
		this.elementosseleccionados = elementosseleccionados;
	}

	public String[] getElementosUdocSeleccionados() {
		return elementosUdocSeleccionados;
	}

	public void setElementosUdocSeleccionados(
			String[] elementosUdocSeleccionados) {
		this.elementosUdocSeleccionados = elementosUdocSeleccionados;
	}

	public String getIdingresoseleccionado() {
		return idingresoseleccionado;
	}

	public void setIdingresoseleccionado(String idingresoseleccionado) {
		this.idingresoseleccionado = idingresoseleccionado;
	}

	public String getIdNivelDocumental() {
		return idNivelDocumental;
	}

	public void setIdNivelDocumental(String idNivelDocumental) {
		this.idNivelDocumental = idNivelDocumental;
	}

	public String[] getElementosElectronicosSel() {
		return elementosElectronicosSel;
	}

	public void setElementosElectronicosSel(String[] elementosElectronicosSel) {
		this.elementosElectronicosSel = elementosElectronicosSel;
	}

	public String getSelCajas() {
		return selCajas;
	}

	public void setSelCajas(String selCajas) {
		this.selCajas = selCajas;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

}