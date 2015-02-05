package se.geograficos;

import java.util.ArrayList;
import java.util.List;

public class ResultadoBusquedaGeograficos {

	private List elementos;

	public ResultadoBusquedaGeograficos() {
		this.elementos = new ArrayList();
	}

	/**
	 * Devuelve una lista de elementos de tipo geografico
	 * 
	 * @return {@link ElementoGeograficoVO}
	 */
	public List getElementos() {
		return elementos;
	}

	public void setElementos(List elementos) {
		this.elementos = elementos;
	}

	public void addElemento(ElementoGeograficoVO elemento) {
		elementos.add(elemento);
	}
}
