package es.ieci.tecdoc.sicres.terceros.web.delegate.impl.legacy.xml.mapper.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IECISA
 *
 */
public class Personas {

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRango() {
		return rango;
	}

	public void setRango(String rango) {
		this.rango = rango;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public void addPersona(Persona persona) {
		if (null == getPersonas()) {
			this.setPersonas(new ArrayList<Persona>());
		}
		this.personas.add(persona);
	}

	protected String inicio;
	protected String fin;
	protected String total;
	protected String rango;
	protected List<Persona> personas;

}
