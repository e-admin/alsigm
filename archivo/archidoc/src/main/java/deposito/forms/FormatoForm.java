package deposito.forms;

import common.forms.CustomForm;

import deposito.actions.FormatoHuecoPO;
import deposito.vos.FormatoHuecoVO;

public class FormatoForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String nombre;
	String tipo;
	boolean multidoc;
	boolean regular;
	double longitud;
	String xinfo;
	boolean vigente;
	String[] formatosSeleccionados;
	String formatoSeleccionado;

	public String getFormatoSeleccionado() {
		return formatoSeleccionado;
	}

	public void setFormatoSeleccionado(String formatoSeleccionado) {
		this.formatoSeleccionado = formatoSeleccionado;
	}

	public String[] getFormatosSeleccionados() {
		return formatosSeleccionados;
	}

	public void setFormatosSeleccionados(String[] formatosSeleccionados) {
		this.formatosSeleccionados = formatosSeleccionados;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public boolean isMultidoc() {
		return multidoc;
	}

	public void setMultidoc(boolean multidoc) {
		this.multidoc = multidoc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isRegular() {
		return regular;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isVigente() {
		return vigente;
	}

	public void setVigente(boolean vigente) {
		this.vigente = vigente;
	}

	public String getXinfo() {
		return xinfo;
	}

	public void setXinfo(String xinfo) {
		this.xinfo = xinfo;
	}

	public void populate(FormatoHuecoVO formato) {
		setId(formato.getId());
		setNombre(formato.getNombre());
		// setTipo(formato.getTipo());
		setMultidoc(formato.isMultidoc());
		setRegular(formato.isRegular());
		setLongitud(formato.getLongitud().doubleValue());
		setXinfo(formato.getXinfo());
		setVigente(formato.isVigente());
	}

	public void fillVOInUpdate(FormatoHuecoPO formatoHueco) {
		if (formatoHueco.isModificable()) {
			formatoHueco.setNombre(getNombre());
			formatoHueco.setMultidoc(isMultidoc());
			formatoHueco.setLongitud(new Double(getLongitud()));
			formatoHueco.setVigente(isVigente());

		} else {
			formatoHueco.setNombre(getNombre());
		}
	}

	public void fillVO(FormatoHuecoVO formatoHueco) {
		formatoHueco.setNombre(getNombre());
		// formatoHuecoVO.setTipo(getTipo());
		formatoHueco.setMultidoc(isMultidoc());
		formatoHueco.setRegular(isRegular());
		formatoHueco.setLongitud(new Double(getLongitud()));
		formatoHueco.setXinfo(getXinfo());
		formatoHueco.setVigente(isVigente());
	}
}
